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
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.RelatorioOrdemServicoUstDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.OSService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked" ,"unused"})
public class RelatorioOrdemServicoUst extends AjaxFormAction {

	private UsuarioDTO usuario;
	private  String localeSession = null;
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		this.preencherComboAno(document, request, response);

		document.getSelectById("idContrato").removeAllOptions();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContrato = contratoService.list();
		document.getSelectById("idContrato").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		document.getSelectById("idContrato").addOptions(colContrato, "idContrato", "numero", null);
	}

	public void imprimirRelatorioOrdemServicoUst(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		usuario = WebUtil.getUsuario(request);
		RelatorioOrdemServicoUstDTO relatorioOrdemServicoUstDTO = (RelatorioOrdemServicoUstDTO) document.getBean();
		HttpSession session = ((HttpServletRequest) request).getSession();
		OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, WebUtil.getUsuarioSistema(request));
		
		ContratoDTO contratoDto = new ContratoDTO();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);

		Collection<RelatorioOrdemServicoUstDTO> listaCustoAtividadePorPeriodo = osService.listaCustoAtividadeOrdemServicoPorPeriodo(relatorioOrdemServicoUstDTO);

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioUtilizacaoUSTs.relatorioUtilizacaoUSTs"));
		parametros.put("CIDADE",UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("ano", (relatorioOrdemServicoUstDTO.getAnoInteger() != null ? relatorioOrdemServicoUstDTO.getAnoInteger().toString() : ""));
		parametros.put("Logo", LogoRel.getFile());
		if(relatorioOrdemServicoUstDTO.getIdContrato()!=null){
			contratoDto.setIdContrato(relatorioOrdemServicoUstDTO.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto) ;
			parametros.put("contrato", contratoDto.getNumero());
		}else{
			parametros.put("contrato", null);
		}	
		

		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioGraficoOrdemServicoUSTs.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		if (listaCustoAtividadePorPeriodo == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		
		try
		{
			JRDataSource dataSource = new JRBeanCollectionDataSource(listaCustoAtividadePorPeriodo);
			
			JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			//JasperViewer.viewReport(print,false);
			
			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioGraficoOrdemServicoUSTs" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");
			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
					+ diretorioRelativoOS + "/RelatorioGraficoOrdemServicoUSTs" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
		} catch(OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		} 
		
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}

	public void imprimirRelatorioOrdemServicoUstXls(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		usuario = WebUtil.getUsuario(request);
		RelatorioOrdemServicoUstDTO relatorioOrdemServicoUstDTO = (RelatorioOrdemServicoUstDTO) document.getBean();
		HttpSession session = ((HttpServletRequest) request).getSession();
		OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, WebUtil.getUsuarioSistema(request));
		
		ContratoDTO contratoDto = new ContratoDTO();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);

		Collection<RelatorioOrdemServicoUstDTO> listaCustoAtividadePorPeriodo = osService.listaCustoAtividadeOrdemServicoPorPeriodo(relatorioOrdemServicoUstDTO);

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioUtilizacaoUSTs.relatorioUtilizacaoUSTs"));
		parametros.put("CIDADE",UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("ano", (relatorioOrdemServicoUstDTO.getAnoInteger() != null ? relatorioOrdemServicoUstDTO.getAnoInteger().toString() : ""));
		parametros.put("Logo", LogoRel.getFile());
		if(relatorioOrdemServicoUstDTO.getIdContrato()!=null){
			contratoDto.setIdContrato(relatorioOrdemServicoUstDTO.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto) ;
			parametros.put("contrato", contratoDto.getNumero());
		}else{
			parametros.put("contrato", null);
		}

		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioGraficoOrdemServicoUSTs.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		if (listaCustoAtividadePorPeriodo == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		
		

		JRDataSource dataSource = new JRBeanCollectionDataSource(listaCustoAtividadePorPeriodo);
		
		JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioGraficoOrdemServicoUSTsXls.jrxml");

		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED,Boolean.TRUE); 
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioGraficoOrdemServicoUSTsXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls");

		exporter.exportReport();
		

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
				+ diretorioRelativoOS + "/RelatorioGraficoOrdemServicoUSTsXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls')");
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}
	
	public void preencherComboAno(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
		HTMLSelect comboAno = (HTMLSelect) document.getSelectById("ano");
		ArrayList<RelatorioOrdemServicoUstDTO> listaAnos = (ArrayList) osService.listaAnos();
		inicializaCombo(comboAno,request);
		if(listaAnos!=null)
		{
			for (RelatorioOrdemServicoUstDTO relatorioUst : listaAnos)
				comboAno.addOption(relatorioUst.getAnoInteger().toString(), relatorioUst.getAnoInteger().toString());
		}
	}

	private void inicializaCombo(HTMLSelect componenteCombo,HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("",UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	@Override
	public Class getBeanClass() {
		return RelatorioOrdemServicoUstDTO.class;
	}

}
