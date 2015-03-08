package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.PesquisaRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.TipoMudancaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.TipoMudancaService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({"rawtypes","unchecked"})
public class PesquisaRequisicaoMudanca extends AjaxFormAction {

	UsuarioDTO usuario;
	private  String localeSession = null;

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.executeScript("$('#divTblRequisicaoMudanca').hide();");

		this.preencherComboCidade(document, request, response);

		this.preencherComboTipoMudanca(document, request, response);

	}

	public void pesquisaRequisicaoMudanca(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PesquisaRequisicaoMudancaDTO pesquisaRequisicaoMudancaDto = (PesquisaRequisicaoMudancaDTO) document.getBean();
	
		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
		
		Collection<PesquisaRequisicaoMudancaDTO> listRequisicaoMudancaByCriterios = requisicaoMudancaService.listRequisicaoMudancaByCriterios(pesquisaRequisicaoMudancaDto);
	
		if (listRequisicaoMudancaByCriterios != null) {
			HTMLTable tblRequisicaoMudanca = document.getTableById("tblRequisicaoMudanca");
			tblRequisicaoMudanca.deleteAllRows();

			for (PesquisaRequisicaoMudancaDTO requisicaoMudanca : listRequisicaoMudancaByCriterios) {
				requisicaoMudanca.setDataHoraInicioStr(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, requisicaoMudanca.getDataHoraInicio(), WebUtil.getLanguage(request)));

				tblRequisicaoMudanca.addRow(requisicaoMudanca, new String[] { "idRequisicaoMudanca", "tipo", "status", "titulo", "motivo", "descricao", "nomeProprietario", "nomeSolicitante",
						"nomeCategoriaMudanca", "dataHoraInicioStr", "nomeGrupoAtual" }, null, null, null, null, null);
			
				document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblRequisicaoMudanca', 'tblRequisicaoMudanca');");

			}

			document.executeScript("$('#divTblRequisicaoMudanca').show();");

		} else {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.resultado"));
			document.executeScript("$('#divTblRequisicaoMudanca').hide();");
		}

		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}

	/**
	 * fireEvent para impressão dos relatorio.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void imprimirRelatorioRequisicaoMudanca(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		PesquisaRequisicaoMudancaDTO pesquisaRequisicaoMudancaDto = (PesquisaRequisicaoMudancaDTO) document.getBean();
		
		GrupoDTO grupoDto = new GrupoDTO();

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		usuario = WebUtil.getUsuario(request);

		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);

		Collection<PesquisaRequisicaoMudancaDTO> listRequisicaoMudancaByCriterios = requisicaoMudancaService.listRequisicaoMudancaByCriterios(pesquisaRequisicaoMudancaDto);

		if (listRequisicaoMudancaByCriterios != null) {
			
			
			Date dt = new Date();

			String strCompl = "" + dt.getTime();
			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioPesquisaRequisicaoMudanca.jasper";
			String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

			Map<String, Object> parametros = new HashMap<String, Object>();

			parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
			
			parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "pesquisaRequisicaoMudanca.tituloRelatorioRequisicaoMudanca"));
			parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
			parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
			parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
			parametros.put("dataInicio", pesquisaRequisicaoMudancaDto.getDataInicio());
			parametros.put("dataFim", pesquisaRequisicaoMudancaDto.getDataFim());
			parametros.put("totalRequisicaoMudanca", listRequisicaoMudancaByCriterios.size());
			parametros.put("numero", pesquisaRequisicaoMudancaDto.getIdRequisicaoMudanca());
			parametros.put("status", pesquisaRequisicaoMudancaDto.getStatus());
			parametros.put("nomeSolicitante", pesquisaRequisicaoMudancaDto.getNomeSolicitante());
			parametros.put("proprietario", pesquisaRequisicaoMudancaDto.getNomeProprietario());
			parametros.put("exibirCampoDescricao", pesquisaRequisicaoMudancaDto.getExibirCampoDescricao());
			parametros.put("tipo", pesquisaRequisicaoMudancaDto.getTipo());
			parametros.put("nomeItemConfiguracao", pesquisaRequisicaoMudancaDto.getNomeItemConfiguracao());
			parametros.put("nomeCategoriaMudanca", pesquisaRequisicaoMudancaDto.getNomeCategoriaMudanca());

			if (pesquisaRequisicaoMudancaDto.getIdGrupoAtual() != null) {
				grupoDto.setIdGrupo(pesquisaRequisicaoMudancaDto.getIdGrupoAtual());
				grupoDto = (GrupoDTO) grupoService.restore(grupoDto);
				parametros.put("grupoSolucionador", grupoDto.getNome());
			} else {
				parametros.put("grupoSolucionador", "");
			}
			
			/*
			if (pesquisaRequisicaoMudancaDto.getIdCategoriaMudanca() != null) {
				categoriaMudancaDto.setIdCategoriaMudanca(pesquisaRequisicaoMudancaDto.getIdCategoriaMudanca());
				categoriaMudancaDto = (CategoriaMudancaDTO) categoriaMudancaService.restore(categoriaMudancaDto);
				parametros.put("categoria", categoriaMudancaDto.getNomeCategoria());
			}else{
				parametros.put("categoria", "");
			}*/

			if (pesquisaRequisicaoMudancaDto.getFormatoArquivoRelatorio().equalsIgnoreCase("pdf")) {

				this.gerarRelatorioFormatoPdf(listRequisicaoMudancaByCriterios, caminhoJasper, parametros, diretorioReceita, strCompl, document, diretorioRelativoOS, usuario);

			} else {

				this.gerarRelatorioFormatoXls(listRequisicaoMudancaByCriterios, parametros, diretorioReceita, strCompl, document, diretorioRelativoOS, usuario);

			}

		} else {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}

	/**
	 * Gerar relatório no formato .pdf
	 * 
	 * @param listRequisicaoMudancaByCriterios
	 * @param caminhoJasper
	 * @param parametros
	 * @param diretorioReceita
	 * @param strCompl
	 * @param document
	 * @param diretorioRelativoOS
	 * @param usuario
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void gerarRelatorioFormatoPdf(Collection<PesquisaRequisicaoMudancaDTO> listRequisicaoMudancaByCriterios, String caminhoJasper, Map<String, Object> parametros, String diretorioReceita,
			String strCompl, DocumentHTML document, String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {

		JRDataSource dataSource = new JRBeanCollectionDataSource(listRequisicaoMudancaByCriterios);

		JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);

		JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioPesquisaRequisicaoMudanca" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioPesquisaRequisicaoMudanca" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");

	}

	/**
	 * Gerar relatório no formato .xls
	 * 
	 * @param listRequisicaoMudancaByCriterios
	 * @param parametros
	 * @param diretorioReceita
	 * @param strCompl
	 * @param document
	 * @param diretorioRelativoOS
	 * @param usuario
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void gerarRelatorioFormatoXls(Collection<PesquisaRequisicaoMudancaDTO> listRequisicaoMudancaByCriterios, Map<String, Object> parametros, String diretorioReceita, String strCompl,
			DocumentHTML document, String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {

		JRDataSource dataSource = new JRBeanCollectionDataSource(listRequisicaoMudancaByCriterios);

		JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioPesquisaRequisicaoMudancaXls.jrxml");

		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioPesquisaRequisicaoMudanca" + strCompl + "_" + usuario.getIdUsuario() + ".xls");

		exporter.exportReport();

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioPesquisaRequisicaoMudanca" + strCompl + "_" + usuario.getIdUsuario() + ".xls')");

	}

	public void preencherComboCidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.getSelectById("idGrupoAtual").removeAllOptions();

		GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		Collection colGrupos = grupoSegurancaService.findGruposAtivos();

		document.getSelectById("idGrupoAtual").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");

		document.getSelectById("idGrupoAtual").addOptions(colGrupos, "idGrupo", "nome", null);
	}
	
	
	
	public void preencherComboTipoMudanca(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.getSelectById("comboTipo").removeAllOptions();
		
		TipoMudancaService tipoMudancaService = (TipoMudancaService) ServiceLocator.getInstance().getService(TipoMudancaService.class, null);
		
		Collection<TipoMudanca> listTipoMudanca = tipoMudancaService.getAtivos();
		
		document.getSelectById("comboTipo").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		
		document.getSelectById("comboTipo").addOptions(listTipoMudanca, "idTipomudanca", "nomeTipoMudanca", null);
	}
	
	public Class getBeanClass() {
		return PesquisaRequisicaoMudancaDTO.class;
	}
	
	public void validacaoCategoriaMudanca(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PesquisaRequisicaoMudancaDTO pesquisaRequisicaoMudancaDto = (PesquisaRequisicaoMudancaDTO) document.getBean();
		
		TipoMudancaService tipoMudancaService = (TipoMudancaService)ServiceLocator.getInstance().getService(TipoMudancaService.class, null);
		
		TipoMudancaDTO tipoMudancaDto = new TipoMudancaDTO();
		
		if(pesquisaRequisicaoMudancaDto.getIdTipoMudanca()!= null){
			tipoMudancaDto.setIdTipoMudanca(pesquisaRequisicaoMudancaDto.getIdTipoMudanca());
			
			tipoMudancaDto = (TipoMudancaDTO) tipoMudancaService.restore(tipoMudancaDto);
			
			if(tipoMudancaDto.getNomeTipoMudanca()!=null && tipoMudancaDto.getNomeTipoMudanca().equalsIgnoreCase("Normal")){
				document.executeScript("$('#nomeCategoriaMudanca').attr('disabled', " + false + ");");
				document.executeScript("$('#div_categoria').show();");
			}else{
				document.executeScript("$('#div_categoria').hide();");
				document.executeScript("$('#nomeCategoriaMudanca').attr('disabled', " + true + ");");
			}
		}
		
		
	}
	

}
