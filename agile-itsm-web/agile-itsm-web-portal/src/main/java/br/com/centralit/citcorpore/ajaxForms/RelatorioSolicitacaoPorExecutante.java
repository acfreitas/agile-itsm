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
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.RelatorioSolicitacaoPorExecutanteDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
@SuppressWarnings({"rawtypes","unused"})
public class RelatorioSolicitacaoPorExecutante extends AjaxFormAction {
	
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
		
		/**
		 * Preenche combo TOP List
		 * 
		 * @author thyen.chang
		 */
		for(Enumerados.TopListEnum valor : Enumerados.TopListEnum.values())
			document.getSelectById("topList").addOption(valor.getValorTopList().toString(), UtilI18N.internacionaliza(request, valor.getNomeTopList()));
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void imprimirRelatorio(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		HttpSession session = ((HttpServletRequest) request).getSession();
		usuario = WebUtil.getUsuario(request);
		
		RelatorioSolicitacaoPorExecutanteDTO relatorioSolicitacaoPorExecutanteDto = (RelatorioSolicitacaoPorExecutanteDTO) document.getBean();	
		
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService)ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		
		
		
		Collection<RelatorioSolicitacaoPorExecutanteDTO> listaSolicitacaoPorExecutanteDto = solicitacaoServicoService.listaSolicitacaoPorExecutante(relatorioSolicitacaoPorExecutanteDto);
		
		if(listaSolicitacaoPorExecutanteDto != null){
			Date dt = new Date();
			
			String strCompl = "" + dt.getTime();
			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioSolicitacaoPorExecutante.jasper";
			String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

			parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioSolicitacaoPorExecutante.relatorioSolicitacaoPorExecutante"));
			parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
			parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
			parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
			parametros.put("dataInicio", relatorioSolicitacaoPorExecutanteDto.getDataInicio());
			parametros.put("dataFim", relatorioSolicitacaoPorExecutanteDto.getDataFim());
			parametros.put("Logo", LogoRel.getFile());
			parametros.put("numero", relatorioSolicitacaoPorExecutanteDto.getIdSolicitacaoServico());
			
			if(relatorioSolicitacaoPorExecutanteDto.getNomeResponsavelAtual() != null && !relatorioSolicitacaoPorExecutanteDto.getNomeResponsavelAtual().equalsIgnoreCase("")){
				parametros.put("nomeResponsavelAtual", relatorioSolicitacaoPorExecutanteDto.getNomeResponsavelAtual());
			}else{
				parametros.put("nomeResponsavelAtual", null);
			}
			
			if(relatorioSolicitacaoPorExecutanteDto.getSituacao() != null && !relatorioSolicitacaoPorExecutanteDto.getSituacao().equalsIgnoreCase("")){
				parametros.put("situacao", relatorioSolicitacaoPorExecutanteDto.getSituacao());
			}else{
				parametros.put("situacao", null);
			}
			
			if(relatorioSolicitacaoPorExecutanteDto.getIdSolicitacaoServico()!=null){
				parametros.put("idSolicitacaoServico", relatorioSolicitacaoPorExecutanteDto.getIdSolicitacaoServico());
			}
			
			if(relatorioSolicitacaoPorExecutanteDto.getIdContrato() != null && relatorioSolicitacaoPorExecutanteDto.getIdContrato().intValue() > 0){
				parametros.put("contrato", this.getContrato(relatorioSolicitacaoPorExecutanteDto.getIdContrato()));
			}
			
			if (relatorioSolicitacaoPorExecutanteDto.getFormatoArquivoRelatorio().equalsIgnoreCase("pdf")) {

				this.gerarRelatorioFormatoPdf(listaSolicitacaoPorExecutanteDto, caminhoJasper, parametros, diretorioReceita, strCompl, document, diretorioRelativoOS, usuario);

			} else {

				this.gerarRelatorioFormatoXls(listaSolicitacaoPorExecutanteDto, parametros, diretorioReceita, strCompl, document, diretorioRelativoOS, usuario);

			}
		}else{
			document.executeScript("reportEmpty();");
		}
		
		document.executeScript("JANELA_AGUARDE_MENU.hide();");
		
		
		
	}
	
	
	@Override
	public Class getBeanClass() {
		// TODO Auto-generated method stub
		return RelatorioSolicitacaoPorExecutanteDTO.class;
	}
	
	public void preencherComboContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		HTMLSelect comboContrato = document.getSelectById("idContrato");
		comboContrato.removeAllOptions();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContrato = contratoService.listAtivos();
		comboContrato.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		comboContrato.addOptions(colContrato, "idContrato", "numero", null);
	}
	
	public void gerarRelatorioFormatoPdf(Collection<RelatorioSolicitacaoPorExecutanteDTO> listProblemaIncidentes, String caminhoJasper, Map<String, Object> parametros, String diretorioReceita, String strCompl,
			DocumentHTML document, String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {

		JRDataSource dataSource = new JRBeanCollectionDataSource(listProblemaIncidentes);

		JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);

		JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioSolicitacaoPorExecutante" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioSolicitacaoPorExecutante" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");

	}

	
	public void gerarRelatorioFormatoXls(Collection<RelatorioSolicitacaoPorExecutanteDTO> listProblemaIncidentes, Map<String, Object> parametros, String diretorioReceita, String strCompl, DocumentHTML document,
			String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {

		JRDataSource dataSource = new JRBeanCollectionDataSource(listProblemaIncidentes);

		JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioSolicitacaoPorExecutanteXls.jrxml");

		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioSolicitacaoPorExecutante" + strCompl + "_" + usuario.getIdUsuario() + ".xls");

		exporter.exportReport();

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioSolicitacaoPorExecutante" + strCompl + "_" + usuario.getIdUsuario() + ".xls')");

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
