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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.RelatorioDocumentosAcessadosBaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.RelatorioDocumentosAcessadosBaseConhecimentoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * @author euler.ramos
 *
 */
public class RelatorioDocumentosAcessadosBaseConhecimento extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		preencherComboOrdenacao(document, request, response);
	}
	
	public void abreRelatorioPDF(JRDataSource dataSource, Map<String, Object> parametros, String diretorioTemp, String caminhoJasper,String jasperArqRel, String diretorioRelativo, String arquivoRelatorio, DocumentHTML document, HttpServletRequest request, HttpServletResponse response){
		try
		{
			JRSwapFile arquivoSwap = new JRSwapFile(diretorioTemp, 4096, 25);
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper+jasperArqRel+".jasper", parametros, dataSource);
			
			JasperExportManager.exportReportToPdfFile(print, diretorioTemp + arquivoRelatorio + ".pdf");
	
			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
					+ diretorioRelativo + arquivoRelatorio + ".pdf')");
		} catch(OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	public void abreRelatorioXLS(JRDataSource dataSource, Map<String, Object> parametros, String diretorioTemp, String caminhoJasper, String jasperArqRel, String diretorioRelativo, String arquivoRelatorio, DocumentHTML document, HttpServletRequest request, HttpServletResponse response){
		try
		{
			JasperDesign desenho = JRXmlLoader.load(caminhoJasper + jasperArqRel +".jrxml");
			JasperReport relatorio = JasperCompileManager.compileReport(desenho);
			JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioTemp + arquivoRelatorio + ".xls");
			exporter.exportReport();
			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativo + arquivoRelatorio + ".xls')");
		} catch(OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		} catch (JRException e) {
			e.printStackTrace();
		}		
	}
	
	public void geraRelatorioDocumentosAcessadosBaseConhecimento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			JRDataSource dataSource;
			RelatorioDocumentosAcessadosBaseConhecimentoDTO relatorioDocumentosAcessadosBaseConhecimentoDTO =  (RelatorioDocumentosAcessadosBaseConhecimentoDTO)document.getBean();
			Integer idUsuario = relatorioDocumentosAcessadosBaseConhecimentoDTO.getIdUsuario();
			
			UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, WebUtil.getUsuarioSistema(request));
			UsuarioDTO usuario = (UsuarioDTO) usuarioService.restoreByID(idUsuario);
			
			//Obtendo informações
			RelatorioDocumentosAcessadosBaseConhecimentoService relatorioDocumentosAcessadosBaseConhecimentoService = (RelatorioDocumentosAcessadosBaseConhecimentoService) ServiceLocator.getInstance().getService(RelatorioDocumentosAcessadosBaseConhecimentoService.class, WebUtil.getUsuarioSistema(request));
			ArrayList<RelatorioDocumentosAcessadosBaseConhecimentoDTO> listaDocumentos = relatorioDocumentosAcessadosBaseConhecimentoService.listarDocumentosAcessadosBaseConhecimentoResumido(relatorioDocumentosAcessadosBaseConhecimentoDTO);
			if (listaDocumentos.size() == 0) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio") );
				return;
			} else {
				if (!relatorioDocumentosAcessadosBaseConhecimentoDTO.getVisualizacao().equalsIgnoreCase("R")){
					Collection<RelatorioDocumentosAcessadosBaseConhecimentoDTO> listaDetalhe;
					for (RelatorioDocumentosAcessadosBaseConhecimentoDTO documentosAcessadosBaseConhecimentoDTO : listaDocumentos) {
						
						documentosAcessadosBaseConhecimentoDTO.setDataInicial(relatorioDocumentosAcessadosBaseConhecimentoDTO.getDataInicial());
						documentosAcessadosBaseConhecimentoDTO.setDataFinal(relatorioDocumentosAcessadosBaseConhecimentoDTO.getDataFinal());
						if ((relatorioDocumentosAcessadosBaseConhecimentoDTO.getIdUsuario()!=null)&&(relatorioDocumentosAcessadosBaseConhecimentoDTO.getIdUsuario().intValue()>0)){
							documentosAcessadosBaseConhecimentoDTO.setIdUsuario(relatorioDocumentosAcessadosBaseConhecimentoDTO.getIdUsuario());
						} else {
							documentosAcessadosBaseConhecimentoDTO.setIdUsuario(0);
						}
						documentosAcessadosBaseConhecimentoDTO.setOrdenacao(relatorioDocumentosAcessadosBaseConhecimentoDTO.getOrdenacao());
						
						listaDetalhe = relatorioDocumentosAcessadosBaseConhecimentoService.listarDocumentosAcessadosBaseConhecimentoAnalitico(documentosAcessadosBaseConhecimentoDTO);
						dataSource = new JRBeanCollectionDataSource(listaDetalhe);
						documentosAcessadosBaseConhecimentoDTO.setListaDetalhe(dataSource);
					}
				}
			}
			
			dataSource = new JRBeanCollectionDataSource(listaDocumentos);
			
			//Alimentando os parâmetros de filtragem para serem mostrados no relatório
			Map<String, Object> parametros = this.alimentaParametros(relatorioDocumentosAcessadosBaseConhecimentoDTO, usuario, UtilI18N.internacionaliza(request, "relatorioDocumentosAcessadosBaseConhecimento.titulo"), document, request, response);
			
			//Configurando dados para geração do Relatório
			StringBuilder jasperArqRel = new StringBuilder();
			jasperArqRel.append("RelatorioDocumentosAcessadosBaseConhecimento");
			if (relatorioDocumentosAcessadosBaseConhecimentoDTO.getVisualizacao().equalsIgnoreCase("R")){
				jasperArqRel.append("Resumido");
			} else {
				jasperArqRel.append("Detalhado");
			}
			Date dt = new Date();
			String strMiliSegundos = Long.toString(dt.getTime());
			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS");
			String diretorioTemp = CITCorporeUtil.CAMINHO_REAL_APP + "tempFiles";
			String diretorioRelativo = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";
			String arquivoRelatorio = "/"+ jasperArqRel + strMiliSegundos;
			
			//Chamando o relatório
			if (relatorioDocumentosAcessadosBaseConhecimentoDTO.getFormato().equalsIgnoreCase("PDF")){
				abreRelatorioPDF(dataSource, parametros, diretorioTemp, caminhoJasper, jasperArqRel.toString(), diretorioRelativo, arquivoRelatorio, document, request, response);
			} else {
				abreRelatorioXLS(dataSource, parametros, diretorioTemp, caminhoJasper, jasperArqRel.toString(), diretorioRelativo, arquivoRelatorio, document, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		}
	}
	
	private Map<String, Object> alimentaParametros(RelatorioDocumentosAcessadosBaseConhecimentoDTO relatorioDocumentosAcessadosBaseConhecimentoDTO, UsuarioDTO usuario, String titulo, DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		HttpSession session = ((HttpServletRequest) request).getSession();
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		parametros.put("TITULO_RELATORIO", titulo);
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		
		if (usuario!=null){
			parametros.put("nomeUsuario", usuario.getNomeUsuario());
		} else {
			parametros.put("nomeUsuario", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		}
		
		//Tratamento para internacionalização do intervalo de datas
		StringBuilder intervaloDasDatas = new StringBuilder();
		String pattern;
		String locale = UtilI18N.getLocale();
		if (locale.contains("en")){
			pattern = "MM/dd/yyyy";
		} else {
			pattern = "dd/MM/yyyy";
		}
		intervaloDasDatas.append(UtilDatas.dateToSTRWithFormat(relatorioDocumentosAcessadosBaseConhecimentoDTO.getDataInicial(), pattern)+" "+UtilI18N.internacionaliza(request,"citcorpore.comum.a")+" "+UtilDatas.dateToSTRWithFormat(relatorioDocumentosAcessadosBaseConhecimentoDTO.getDataFinal(), pattern));
		parametros.put("Periodo", intervaloDasDatas.toString());
		
		parametros.put("DATA_HORA", UtilDatas.dateToSTRWithFormat(UtilDatas.getDataAtual(),pattern));
		
		parametros.put("Logo", LogoRel.getFile());
		
		parametros.put("SUBREPORT_DIR", CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS"));
		
		UsuarioDTO usuarioImprimiu = WebUtil.getUsuario(request);
		if ((usuarioImprimiu!=null)&&(usuarioImprimiu.getNomeUsuario()!=null)){
			parametros.put("usuarioImprimiu",usuarioImprimiu.getNomeUsuario());
		}
		
		return parametros;
	}
	
	private void preencherComboOrdenacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response){
		HTMLSelect comboOrdenacao;
		try {
			comboOrdenacao = document.getSelectById("ordenacao");
			if (comboOrdenacao!=null){
				comboOrdenacao.removeAllOptions();
				comboOrdenacao.addOption("1", UtilI18N.internacionaliza(request, "relatorioBaseConhecimento.qtdeAcessosDecrescente"));
				comboOrdenacao.addOption("2", UtilI18N.internacionaliza(request, "baseConhecimento.titulo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Class getBeanClass() {
		return RelatorioDocumentosAcessadosBaseConhecimentoDTO.class;
	}

}