package br.com.centralit.citcorpore.ajaxForms;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
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
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RelatorioMudancaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaItemConfiguracaoDao;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes" })
public class RelatorioMudancaItemConfiguracao extends AjaxFormAction {

	private UsuarioDTO usuario;
	private String localeSession = null;

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		this.preencherComboContrato(document, request, response);

	}

	public void imprimirRelatorio(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		HttpSession session = ((HttpServletRequest) request).getSession();

		RelatorioMudancaItemConfiguracaoDTO relatorioMudancaItemConfiguracaoDTO = (RelatorioMudancaItemConfiguracaoDTO) document.getBean();
		usuario = WebUtil.getUsuario(request);

		RequisicaoMudancaItemConfiguracaoDao requisicaoMudancaItemConfiguracaoDao = new RequisicaoMudancaItemConfiguracaoDao();// dao da requisicaoMudancaItem

		Collection<RelatorioMudancaItemConfiguracaoDTO> listaRelatorioMudancaItemConfiguracao = null;// lista mudanca/itemConfig
		Collection<RelatorioMudancaItemConfiguracaoDTO> listaParaEnvio = new ArrayList<RelatorioMudancaItemConfiguracaoDTO>();
		// Parâmetros da visão
		int idItem = 0;
		int idMudanca = 0;
		int idContrato = 0;
		
		if (relatorioMudancaItemConfiguracaoDTO.getIdItemConfig() != null) {
			idItem = relatorioMudancaItemConfiguracaoDTO.getIdItemConfig();// id item Configuração
		}
		if (relatorioMudancaItemConfiguracaoDTO.getIdContrato() != null) {
			idContrato = relatorioMudancaItemConfiguracaoDTO.getIdContrato();
		}
		if (relatorioMudancaItemConfiguracaoDTO.getIdNumeroMudanca() != null) {
			idMudanca = relatorioMudancaItemConfiguracaoDTO.getIdNumeroMudanca();// id mudança
		}

		Date dtInicial = relatorioMudancaItemConfiguracaoDTO.getDataInicio();// parametro data incial
		Date dtFinal = relatorioMudancaItemConfiguracaoDTO.getDataFim();// parâmetro data fim
		
		
		
	
		listaRelatorioMudancaItemConfiguracao = requisicaoMudancaItemConfiguracaoDao.listMudancaItemConfigRelatorio(idMudanca, idItem, dtInicial, dtFinal,idContrato);// Retorna valores dos atributos já filtrados
		
	
		
		if (listaRelatorioMudancaItemConfiguracao != null && !listaRelatorioMudancaItemConfiguracao.isEmpty()) {
				
			
			
			for(RelatorioMudancaItemConfiguracaoDTO i : listaRelatorioMudancaItemConfiguracao){
				i.setListaItensConfiguracao((Collection<ItemConfiguracaoDTO>)requisicaoMudancaItemConfiguracaoDao.listItemConfiguracaoByIdMudanca(i.getIdNumeroMudanca()));
				listaParaEnvio.add(i);
			}

			

				String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioMudancaItemConfiguracao.jasper";
				String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
				String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

				Map<String, Object> parametros = new HashMap<String, Object>();
				/*String codigoItemCOnfiguracao  = UtilI18N.internacionaliza(request,"citcorporeRelatorio.MudancaItemConfiguracao.codigo");
				String nomeItemconfiguracao    = UtilI18N.internacionaliza(request,"MudancaItemConfiguracao.NomeItem");*/
				parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
				
				  parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "MudancaItemConfiguracao.RelatorioMudancaItenConfig")); 
				  
				  parametros.put("CIDADE", UtilI18N.internacionaliza(request,"citcorpore.comum.relatorioCidade")); 
				  parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual()); 
				  parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
				  parametros.put("dataInicio", relatorioMudancaItemConfiguracaoDTO.getDataInicio()); 
				  parametros.put("dataFim", relatorioMudancaItemConfiguracaoDTO.getDataFim());
				  parametros.put("contrato", this.getContrato(idContrato));
				  parametros.put("SUBREPORT_DIR", CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS")+"RelatorioMudancaItemConfiguracao_subreport3.jasper");
				  parametros.put("Logo", LogoRel.getFile());
				  parametros.put("titulo", UtilI18N.internacionaliza(request,"citcorpore.comum.titulo"));
				  parametros.put("idItemConfig", UtilI18N.internacionaliza(request,"lookup.id"));
				if (relatorioMudancaItemConfiguracaoDTO.getIdContrato() != null && relatorioMudancaItemConfiguracaoDTO.getIdContrato().intValue() > 0) {
					parametros.put("contrato", this.getContrato(relatorioMudancaItemConfiguracaoDTO.getIdContrato()));
				}

				if (relatorioMudancaItemConfiguracaoDTO.getFormatoArquivoRelatorio().equalsIgnoreCase("pdf")) {

					this.gerarRelatorioFormatoPdf(listaParaEnvio, caminhoJasper, parametros, diretorioReceita, document, diretorioRelativoOS, usuario);

				} else {

					this.gerarRelatorioFormatoXls(listaParaEnvio, parametros, diretorioReceita, document, diretorioRelativoOS, usuario);

				}
			

		} else {
			document.executeScript("reportEmpty();");
		}

		document.executeScript("JANELA_AGUARDE_MENU.hide();");

	}

	@Override
	public Class getBeanClass() {
		return RelatorioMudancaItemConfiguracaoDTO.class;
	}

	public void preencherComboContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLSelect comboContrato = document.getSelectById("idContrato");
		comboContrato.removeAllOptions();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContrato = contratoService.listAtivos();
		comboContrato.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		comboContrato.addOptions(colContrato, "idContrato", "numero", null);
	}

	public void gerarRelatorioFormatoPdf(Collection<RelatorioMudancaItemConfiguracaoDTO> listaRelatorioMudancaItemConfiguracao, String caminhoJasper, Map<String, Object> parametros,
			String diretorioReceita, DocumentHTML document, String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {

		JRDataSource dataSource = new JRBeanCollectionDataSource(listaRelatorioMudancaItemConfiguracao);

		JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);

		JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioMudancaItemConfiguracao" + "_" + usuario.getIdUsuario() + ".pdf");

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioMudancaItemConfiguracao" + "_" + usuario.getIdUsuario() + ".pdf')");

	}

	public void gerarRelatorioFormatoXls(Collection<RelatorioMudancaItemConfiguracaoDTO> listaRelatorioMudancaItemConfiguracao, Map<String, Object> parametros, String diretorioReceita,
			DocumentHTML document, String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {

		JRDataSource dataSource = new JRBeanCollectionDataSource(listaRelatorioMudancaItemConfiguracao);

		JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioMudancaItemConfiguracao.jrxml");

		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioMudancaItemConfiguracao" + "_" + usuario.getIdUsuario() + ".xls");

		exporter.exportReport();

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioMudancaItemConfiguracao" + "_" + usuario.getIdUsuario() + ".xls')");

	}

	private String getContrato(Integer id) throws ServiceException, Exception {
		ContratoDTO contrato = new ContratoDTO();
		contrato.setIdContrato(id);
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		contrato = (ContratoDTO) contratoService.restore(contrato);
		if (contrato != null) {
			return contrato.getNumero();
		}
		return null;
	}

	private String getDescricaoGrupo(int idGrupo) throws ServiceException, Exception {

		GrupoDTO dto = new GrupoDTO();
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		dto = grupoService.listGrupoById(idGrupo);
		return dto.getDescricao();
	}

}
