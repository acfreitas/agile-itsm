package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.LogDados;
import br.com.citframework.service.LogDadosService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * @author Flávio
 *
 */
public class LogController extends AjaxFormAction {
	
	UsuarioDTO usuario;
	private  String localeSession = null;
	
	public void load (DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
    	UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}
    	
    	document.getSelectById("nomeTabela").removeAllOptions();
    	LogDadosService logsService = (LogDadosService) ServiceLocator.getInstance().getService(LogDadosService.class, null);
		Collection<LogDados> col = logsService.listNomeTabela();
		document.getSelectById("nomeTabela").addOption("", "-- "+UtilI18N.internacionaliza(request, "citcorpore.comum.todos")+" --");
		document.getSelectById("nomeTabela").addOptions(col, "nomeTabela", "nomeTabela", null);
    	
     }
    
    public void contentLog(DocumentHTML document, List<LogDados> listLog, HttpServletRequest request) throws Exception {
    	
    	StringBuilder html = new StringBuilder();
		html.append("<div id='table'><table class='table' width='1850px' cellpadding='3' cellspacing='1' style='letter-spacing: 0px;'>");
		html.append(
				"<thead>" +
				"	<tr style='background-color: #eaeaea !important;'>" +
				"		<th style='width:15%;'>" + UtilI18N.internacionaliza(request, "logs.nomeUsuario") + "</th>" +
				"		<th style='width:10%;'>" + UtilI18N.internacionaliza(request, "logs.tabela") + "</th>" +
				"		<th style='width:5%;'>" + UtilI18N.internacionaliza(request, "logs.operacao") + "</th>" +
				"		<th style='width:10%;'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.datahora") + "</th>" +
				"		<th style='width:60%;'>" + UtilI18N.internacionaliza(request, "logs.dados") + "</th>" +
				"		</tr>" +
				"</thead><tbody>");
		
    	if(!listLog.isEmpty()) {
	    	for (LogDados logDados : listLog) 	{
	    		html.append(
	    				
	    				"<tr bgcolor='#ededed'>" +
		    				"<td style='text-align: left;'>" + logDados.getNomeUsuario() + "</td>" +
		    				"<td style='text-align: left;'>" + logDados.getNomeTabela() + "</td>" +
		    				"<td style='text-align: left;'>" + (logDados.getOperacao().equals("I") ? UtilI18N.internacionaliza(request, "logs.inclusao") : UtilI18N.internacionaliza(request, "logs.alteracao")) + "</td>" +
		    				"<td style='text-align: left;'>" + (logDados.getDataLog() == null ? "" : UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, logDados.getDataLog(), WebUtil.getLanguage(request))) + "</td>" +
							"<td style='text-align: left;'>" + logDados.getDados() + "</td>" +
						"</tr>");
			}
    	} else {
	    	html.append("<tr><td colspan='3'>" + UtilI18N.internacionaliza(request, "MSG04") + "</td></tr>");
    	}
    	html.append("</tbody></table>");
    	HTMLElement page = document.getElementById("page") ;
    	page.setInnerHTML(html.toString());
    }
	
    public void filtrar (DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UsuarioDTO usuario = (UsuarioDTO) WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		LogDados log = (LogDados) document.getBean();
		LogDadosService logsService = (LogDadosService) ServiceLocator.getInstance().getService(LogDadosService.class, null);

		if (log.getDataInicio() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datainicio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		if (log.getDataFim() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		
		ArrayList<LogDados> colLogs = (ArrayList<LogDados>) logsService.listLogs(log);
    	if(colLogs == null){
    		colLogs = new ArrayList<LogDados>();
    	}
    	contentLog(document, colLogs, request);
    }
    
	/**
	 * Faz a impressão do relatório no formato pdf.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Flavio.santana
	 */
	public void imprimirRelatorio(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = ((HttpServletRequest) request).getSession();
		LogDados logDados = (LogDados) document.getBean();
		LogDadosService logDadosService = (LogDadosService) ServiceLocator.getInstance().getService(LogDadosService.class, null);
		
		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (logDados.getDataInicio() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datainicio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		if (logDados.getDataFim() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		
		/*Iterando a lista*/
		ArrayList<LogDados> colLogs = (ArrayList<LogDados>) logDadosService.listLogs(logDados);
    	if(colLogs == null)
    	{
    		colLogs = new ArrayList<LogDados>();
    	}
    	
		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioLogs.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioLogs.titulo"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("dataInicio", logDados.getDataInicio());
		parametros.put("dataFim", logDados.getDataFim());

		if (colLogs.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		JRDataSource dataSource = new JRBeanCollectionDataSource(colLogs);

		JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
		JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioLogs" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
				+ diretorioRelativoOS + "/RelatorioLogs" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");

		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}
    
    public Class<LogDados> getBeanClass(){
    	return LogDados.class;
    }
   
}
	