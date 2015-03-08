<%@page import="br.com.citframework.util.UtilI18N"%>
<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.centralit.citcorpore.bean.NotificacaoDTO"%>
<%@page import="br.com.citframework.util.UtilFormatacao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.centralit.citcorpore.bean.ServicoContratoDTO"%>
<%@page import="br.com.citframework.util.UtilDatas"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<%
Collection listaNotificacoes = (Collection)request.getAttribute("listaNotificacoes");
%>

<link type="text/css" rel="stylesheet" href="css/visualizarNotificacoes.css"/>

<div>
	<button type='button' name='btnGravar' class="light"  onclick='abrirPopupNotificacoesServicos();'>
		<img src='${ctx}/template_new/images/icons/small/grey/plane_suitcase.png'>
		<span><fmt:message key="notificacao.adicionarNotificacao"/></span>
	</button>
</div>
<div class='tituloDiv'><fmt:message key="contrato.notificacoesContrato"/></div>

<table cellpadding="0" cellspacing="0" width="100%" class="table table-bordered table-striped">
	<tr >
		<th width="8%">
			&nbsp;
		</th>
		<th width="60%">
			<fmt:message key="notificacao.notificacao"/>
		</th>
		<th width="32%">
			<fmt:message key="notificacao.tipoNotificacao"/>
		</th>		
	</tr>

	
	<%
	String corLinha = "";
	if (listaNotificacoes != null && listaNotificacoes.size() > 0){
		for(Iterator it = listaNotificacoes.iterator(); it.hasNext();){
			if (!corLinha.trim().equalsIgnoreCase("#f5f5f5")){
				corLinha = "#f5f5f5";
			}else{
				corLinha = "white";
			}
			NotificacaoDTO servicoNotificacoesDto = (NotificacaoDTO)it.next();
			
			
			out.print("<tr>");
					out.print("<td >");
						out.print("<table >");
							out.print("<tr style='text-align: center;'>");
							out.print("<td style='text-align: center; border: 0px' align='center'>");
							out.print("<img src='" + br.com.citframework.util.Constantes
									.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
									.getValue("CONTEXTO_APLICACAO") + "/imagens/write.png' border='0' style='cursor:pointer' title='" 
									+ UtilI18N.internacionaliza(request, "contrato.editarNotificacao") + "' onclick='editarNotificaoServico(" + servicoNotificacoesDto.getIdNotificacao() + ")'/>");					
							out.print("</td>");
							out.print("<td style='text-align: center; border: 0px'>&nbsp;&nbsp;&nbsp;</td>");
							out.print("<td style='text-align: center; border: 0px' align='center'>");
							out.print("<img src='" + br.com.citframework.util.Constantes
									.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
									.getValue("CONTEXTO_APLICACAO") + "/imagens/button_cancel.png' border='0' style='cursor:pointer' title='" 
									+ UtilI18N.internacionaliza(request, "contrato.excluirNotificacao") + "' onclick='excluiNotificaoServico(" + servicoNotificacoesDto.getIdNotificacao() + ")'/>");					
							out.print("</td>");
							out.print("</tr>");
						out.print("</table>");
					out.print("</td>");
					out.print("<td>");
						out.print(servicoNotificacoesDto.getTitulo());
					out.print("</td>");
					out.print("<td>");
						out.print(UtilStrings.nullToVazio(servicoNotificacoesDto.getNomeTipoNotificacao()));
					out.print("</td>");				
					
				out.print("</tr>");
			}
		}

	%>
</table>