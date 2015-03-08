package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
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
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoProblemaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ProblemaService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
@SuppressWarnings("rawtypes")
public class RelatorioQuantitativoProblema extends AjaxFormAction {

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

	
	@Override
	public Class getBeanClass() {
		// TODO Auto-generated method stub
		return RelatorioQuantitativoProblemaDTO.class;
	}
	
	public void imprimirRelatorio(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto = (RelatorioQuantitativoProblemaDTO) document.getBean();
		
		RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDtoAux =  new RelatorioQuantitativoProblemaDTO();
		
		usuario = WebUtil.getUsuario(request);
		
		ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);
		
		Collection<RelatorioQuantitativoProblemaDTO> listaDadosGeral = new ArrayList<RelatorioQuantitativoProblemaDTO>();
		Collection<RelatorioQuantitativoProblemaDTO> listaQuantitativoProblemaPorSituacao = problemaService.listaQuantidadeProblemaPorSituacao(relatorioQuantitativoProblemaDto);
		Collection<RelatorioQuantitativoProblemaDTO> listaQuantitativoProblemaPorGrupo = problemaService.listaQuantidadeProblemaPorGrupo(relatorioQuantitativoProblemaDto);
		Collection<RelatorioQuantitativoProblemaDTO> listaQuantitativoProblemaPorOrigem = problemaService.listaQuantidadeProblemaPorOrigem(relatorioQuantitativoProblemaDto);
		Collection<RelatorioQuantitativoProblemaDTO> listaQuantitativoProblemaPorSolicitante = problemaService.listaQuantidadeProblemaPorSolicitante(relatorioQuantitativoProblemaDto);
		Collection<RelatorioQuantitativoProblemaDTO> listaQuantitativoProblemaPorPrioridade = problemaService.listaQuantidadeProblemaPorPrioridade(relatorioQuantitativoProblemaDto);
		Collection<RelatorioQuantitativoProblemaDTO> listaQuantitativoProblemaPorCategoria = problemaService.listaQuantidadeProblemaPorCategoria(relatorioQuantitativoProblemaDto);
		Collection<RelatorioQuantitativoProblemaDTO> listaQuantitativoProblemaPorProprietario = problemaService.listaQuantidadeProblemaPorProprietario(relatorioQuantitativoProblemaDto);
		Collection<RelatorioQuantitativoProblemaDTO> listaQuantitativoProblemaPorImpacto = problemaService.listaQuantidadeProblemaPorImpacto(relatorioQuantitativoProblemaDto);
		Collection<RelatorioQuantitativoProblemaDTO> listaQuantitativoProblemaPorUrgencia = problemaService.listaQuantidadeProblemaPorUrgencia(relatorioQuantitativoProblemaDto);
		
		
		if(listaQuantitativoProblemaPorSituacao!=null){
			relatorioQuantitativoProblemaDtoAux.setListaQuantidadeProblemaPorSituacao(listaQuantitativoProblemaPorSituacao);
		}
		
		if(listaQuantitativoProblemaPorGrupo!=null){
			relatorioQuantitativoProblemaDtoAux.setListaQuantidadeProblemaPorGrupo(listaQuantitativoProblemaPorGrupo);
		}
		
		if(listaQuantitativoProblemaPorOrigem!=null){
			relatorioQuantitativoProblemaDtoAux.setListaQuantidadeProblemaPorOrigem(listaQuantitativoProblemaPorOrigem);
		}
		
		if(listaQuantitativoProblemaPorSolicitante!=null){
			relatorioQuantitativoProblemaDtoAux.setListaQuantidadeProblemaPorSolicitante(listaQuantitativoProblemaPorSolicitante);
		}
		
		if(listaQuantitativoProblemaPorPrioridade!=null){
			relatorioQuantitativoProblemaDtoAux.setListaQuantidadeProblemaPorPrioridade(listaQuantitativoProblemaPorPrioridade);
		}
		if(listaQuantitativoProblemaPorCategoria!=null){
			relatorioQuantitativoProblemaDtoAux.setListaQuantidadeProblemaPorCategoriaProblema(listaQuantitativoProblemaPorCategoria);
		}
		
		if(listaQuantitativoProblemaPorProprietario!=null){
			relatorioQuantitativoProblemaDtoAux.setListaQuantidadeProblemaPorProprietario(listaQuantitativoProblemaPorProprietario);
		}
		if(listaQuantitativoProblemaPorImpacto!=null){
			relatorioQuantitativoProblemaDtoAux.setListaQuantidadeProblemaPorImpacto(listaQuantitativoProblemaPorImpacto);
			
		}
		if(listaQuantitativoProblemaPorUrgencia!=null){
			relatorioQuantitativoProblemaDtoAux.setListaQuantidadeProblemaPorUrgencia(listaQuantitativoProblemaPorUrgencia);
		}
		
		if(relatorioQuantitativoProblemaDtoAux.getListaQuantidadeProblemaPorSituacao() !=null || relatorioQuantitativoProblemaDtoAux.getListaQuantidadeProblemaPorGrupo()!=null
				|| relatorioQuantitativoProblemaDtoAux.getListaQuantidadeProblemaPorOrigem()!=null || relatorioQuantitativoProblemaDtoAux.getListaQuantidadeProblemaPorSolicitante()!=null
				|| relatorioQuantitativoProblemaDtoAux.getListaQuantidadeProblemaPorPrioridade() !=null || relatorioQuantitativoProblemaDtoAux.getListaQuantidadeProblemaPorCategoriaProblema()!=null
				|| relatorioQuantitativoProblemaDtoAux.getListaQuantidadeProblemaPorProprietario()!=null || relatorioQuantitativoProblemaDtoAux.getListaQuantidadeProblemaPorImpacto()!=null
				|| relatorioQuantitativoProblemaDtoAux.getListaQuantidadeProblemaPorUrgencia()!=null){
			listaDadosGeral.add(relatorioQuantitativoProblemaDtoAux);
		}else{
			listaDadosGeral = null;
		}
		
		if(listaDadosGeral != null){
			Date dt = new Date();
			
			String strCompl = "" + dt.getTime();
			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioQuantitativoProblema.jasper";
			String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

			parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioQuantitativoProblema.relatorioQuantitativoProblema"));
			parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
			parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
			parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
			parametros.put("dataInicio", relatorioQuantitativoProblemaDto.getDataInicio());
			parametros.put("dataFim", relatorioQuantitativoProblemaDto.getDataFim());
			parametros.put("totalProblema", listaDadosGeral.size());
			parametros.put("Logo", LogoRel.getFile());
			
			if(relatorioQuantitativoProblemaDto.getIdContrato() != null && relatorioQuantitativoProblemaDto.getIdContrato().intValue() > 0){
				parametros.put("contrato", this.getContrato(relatorioQuantitativoProblemaDto.getIdContrato()));
			}else{
				parametros.put("contrato", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
			}
			
			if (relatorioQuantitativoProblemaDto.getFormatoArquivoRelatorio().equalsIgnoreCase("pdf")) {

				this.gerarRelatorioFormatoPdf(listaDadosGeral, caminhoJasper, parametros, diretorioReceita, strCompl, document, diretorioRelativoOS, usuario);

			} else {

				this.gerarRelatorioFormatoXls(listaDadosGeral, parametros, diretorioReceita, strCompl, document, diretorioRelativoOS, usuario);

			}
		}else{
			document.executeScript("reportEmpty();");
		}
		
		document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}
	
	public void gerarRelatorioFormatoPdf(Collection<RelatorioQuantitativoProblemaDTO> listaDadosGeral, String caminhoJasper, Map<String, Object> parametros, String diretorioReceita, String strCompl,
			DocumentHTML document, String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {

		JRDataSource dataSource = new JRBeanCollectionDataSource(listaDadosGeral);

		JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);

		JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioQuantitativoProblema" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioQuantitativoProblema" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");

	}

	
	public void gerarRelatorioFormatoXls(Collection<RelatorioQuantitativoProblemaDTO> listaDadosGeral, Map<String, Object> parametros, String diretorioReceita, String strCompl, DocumentHTML document,
			String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {

		JRDataSource dataSource = new JRBeanCollectionDataSource(listaDadosGeral);

		JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioQuantitativoProblemaXls.jrxml");

		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioQuantitativoProblemaXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls");

		exporter.exportReport();

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioQuantitativoProblemaXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls')");

	}
	
	public void preencherComboContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		HTMLSelect comboContrato = document.getSelectById("idContrato");
		comboContrato.removeAllOptions();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContrato = contratoService.listAtivos();
		comboContrato.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		comboContrato.addOptions(colContrato, "idContrato", "numero", null);
	}
	
	private String getContrato(Integer id) throws ServiceException, Exception{
		ContratoDTO contrato = new ContratoDTO();
		contrato.setIdContrato(id);
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		contrato = (ContratoDTO) contratoService.restore(contrato);
		if(contrato != null){
			return contrato.getNumero();
		}
		return null;
	}

}
