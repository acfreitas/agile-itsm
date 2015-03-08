<%@page import="br.com.citframework.util.UtilFormatacao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.centralit.citcorpore.bean.ServicoContratoDTO"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.citframework.util.UtilI18N"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<%
Collection listaServicos = (Collection)request.getAttribute("listaServicos");
%>

<link type="text/css" rel="stylesheet" href="css/visualizarDesempenhoServicosContrato.css"/>

<fmt:message key="avaliacaoContrato.avaliacaoContratoDesempenhoServico"/>
<table cellpadding="0" cellspacing="0" width="100%" style='width: 98%'>
	<tr style='text-align: center;' class=''>
		<td class="linhaSubtituloGrid">
			&nbsp;
		</td>
		<td class="linhaSubtituloGrid">
			<b style='line-height: 20px;font-size:13px; padding:0.5em;font-family: arial;'><fmt:message key="citcorpore.comum.servico"/></b>
		</td>
		<td class="linhaSubtituloGrid">
			<b style='line-height: 25px;font-size:13px; padding:0.5em;font-family: arial;'><fmt:message key="citcorpore.comum.situacao"/></b>
		</td>		
		<td class="linhaSubtituloGrid">
			<b style='line-height: 25px;font-size:13px; padding:0.5em;font-family: arial;'><fmt:message key="avaliacao.fornecedor.servico.demanda"/></b>
		</td>	
		<td class="linhaSubtituloGrid">
			<b style='line-height: 25px;font-size:13px; padding:0.5em;font-family: arial;'><fmt:message key="avaliacao.fornecedor.sla"/></b>
		</td>			
		<td class="linhaSubtituloGrid">
			<b style='line-height: 25px;font-size:13px; padding:0.5em;font-family: arial;'><fmt:message key="citcorpore.comum.datainicio"/></b>
		</td>
		<td class="linhaSubtituloGrid">
			<b style='line-height: 25px;font-size:13px; padding:0.5em;font-family: arial;'><fmt:message key="citcorpore.comum.datafim"/></b>
		</td>				
	</tr>
	<%
	String corLinha = "";
	if (listaServicos != null && listaServicos.size() > 0){
		for(Iterator it = listaServicos.iterator(); it.hasNext();){
			if (!corLinha.trim().equalsIgnoreCase("#f5f5f5")){
				corLinha = "#f5f5f5";
			}else{
				corLinha = "white";
			}
			ServicoContratoDTO servicoContratoAux = (ServicoContratoDTO)it.next();
			out.print("<tr style='border: none;background:" + corLinha + ";'>");
			
				out.print("<td style='padding:0.2em; text-align: center;' align='center'>");
			/*	
					out.print("<table style='text-align: center; align='center''>");
					out.print("<tr style='text-align: center;'>");
					out.print("<td style='text-align: center;' align='center'>");
					out.print("<img src='" + br.com.citframework.util.Constantes
							.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
							.getValue("CONTEXTO_APLICACAO") + "/imagens/write.png' border='0' style='cursor:pointer' title='Editar o serviço' onclick='editarServicoContrato(" + servicoContratoAux.getIdServicoContrato() + ")'/>");					
					out.print("</td>");					
					out.print("</tr>");
					out.print("</table>");
					
				*/
				out.print("</td>");
				out.print("<td>");
					out.print(servicoContratoAux.getNomeServico());
				out.print("</td>");
				out.print("<td>");
				if (servicoContratoAux.getSituacaoServico().intValue() == 1){
					if (servicoContratoAux.getDataFim() != null && servicoContratoAux.getDataFim().before(UtilDatas.getDataAtual())){
						out.print("<img src='" + br.com.citframework.util.Constantes
								.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
								.getValue("CONTEXTO_APLICACAO") + "/imagens/bolavermelha.png' border='0'  title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.servicoInativo") + "'/>");					
						out.print(UtilI18N.internacionaliza(request, "avalicaoContrato.Inativo(Datafim)"));
					}else{
						out.print("<img src='" + br.com.citframework.util.Constantes
								.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
								.getValue("CONTEXTO_APLICACAO") + "/imagens/bolaverde.png' border='0'  title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.servicoAtivo") + "'/>");					
						out.print(UtilI18N.internacionaliza(request, "planoMelhoria.situacao.ativo"));
					}
				}else if (servicoContratoAux.getSituacaoServico().intValue() == 2){
					out.print("<img src='" + br.com.citframework.util.Constantes
							.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
							.getValue("CONTEXTO_APLICACAO") + "/imagens/bolavermelha.png' border='0'  title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.servicoInativo") + "'/>");					
					out.print(UtilI18N.internacionaliza(request, "citcorpore.comum.inativo"));
				}else {
					String strSituacao = "";
					if (servicoContratoAux.getSituacaoServico().intValue() == -999){
						strSituacao = UtilI18N.internacionaliza(request, "avaliacaoContrato.emAnalise");
					}
					if (servicoContratoAux.getSituacaoServico().intValue() == 3){
						strSituacao = UtilI18N.internacionaliza(request, "perfil.criacao");
					}
					if (servicoContratoAux.getSituacaoServico().intValue() == 4){
						strSituacao = UtilI18N.internacionaliza(request,"baseconhecimento.emdesenho");
					}					
					out.print("<img src='" + br.com.citframework.util.Constantes
							.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
							.getValue("CONTEXTO_APLICACAO") + "/imagens/ball_gray__.gif' border='0'  title='" + strSituacao + "'/>");					
					out.print(strSituacao);					
				}
				out.print("</td>");				
				out.print("<td>");
					out.print(servicoContratoAux.getNomeTipoDemandaServico());
				out.print("</td>");		
				out.print("<td>");
				if (servicoContratoAux.getTemSLA()){
					out.print("<img src='" + br.com.citframework.util.Constantes
							.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
							.getValue("CONTEXTO_APLICACAO") + "/imagens/tick.png' border='0'  title='SLA OK'/>");					
				}else{
					out.print("&nbsp;");					
				}
				out.print("</td>");
				out.print("<td>");
					out.print(UtilDatas.dateToSTR(servicoContratoAux.getDataInicio()));
				out.print("</td>");	
				out.print("<td>");
				if (servicoContratoAux.getDataFim() != null){
					out.print(UtilDatas.dateToSTR(servicoContratoAux.getDataFim()));
				}else{
					out.print("--");
				}
				out.print("</td>");				
			out.print("</tr>");
			if ((servicoContratoAux.getQtdeDentroPrazo() != null && servicoContratoAux.getQtdeDentroPrazo().intValue() > 0) 
					|| (servicoContratoAux.getQtdeForaPrazo() != null && servicoContratoAux.getQtdeForaPrazo().intValue() > 0)){
			out.print("<tr style='border: none;background:" + corLinha + ";'>");
				out.print("<td>");
					out.print("&nbsp;");
				out.print("</td>");
				out.print("<td colspan='20'>");
				out.print("<table width='100%'>");
				out.print("<tr>");
					out.print("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>");
					out.print("<td>");
					out.print("<div style='border: none;background:" + corLinha + ";' id='divContratoServico_" + servicoContratoAux.getIdServicoContrato() + "'>");
					out.print("<table>");
						out.print("<tr>");
							out.print("<td>&nbsp;</td>");
						out.print("</tr>");
						out.print("<tr>");
							out.print("<td>" + UtilI18N.internacionaliza(request, "avaliacao.fornecedor.dentroPrazo") + "</td>");
							out.print("<td> <b><u>" + servicoContratoAux.getQtdeDentroPrazo() + " (" + UtilFormatacao.formatDouble(servicoContratoAux.getDentroPrazo(),2) + "%)</u></b></td>");
							out.print("<td>&nbsp;</td>");
							out.print("<td>&nbsp;" + UtilI18N.internacionaliza(request, "avaliacao.fornecedor.foraPrazo") + "</td>");
							out.print("<td> <b><u>" + servicoContratoAux.getQtdeForaPrazo() + " (" + UtilFormatacao.formatDouble(servicoContratoAux.getForaPrazo(),2) + "%)</u></b></td>");
						out.print("</tr>");
					out.print("</table>");
					out.print("<table width='100%' border='1'>");
						out.print("<tr>");
							for (int i = 1; i <= 100; i++){
								String cor = "";
								if (i <= servicoContratoAux.getDentroPrazo().intValue()){
									cor = "green";
								}else{
									cor = "red";
								}
								out.print("<td style='border:1px solid black;background-color:" + cor + ";width:1%' title='" + i + "%'>&nbsp;</td>");
							}
						out.print("</tr>");
					out.print("</table>");
					out.print("</div>");
					out.print("</td>");
				out.print("</tr>");
				out.print("</table>");
				out.print("</td>");
			out.print("</tr>");
			}else{
				out.print("<tr style='border: none;background:" + corLinha + ";'>");
					out.print("<td>");
						out.print("&nbsp;");
					out.print("</td>");
					out.print("<td colspan='20'>");
						out.print("<table width='100%'>");
							out.print("<tr>");
								out.print("<td>&nbsp;</td>");
							out.print("</tr>");						
							out.print("<tr>");
								out.print("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>");
								out.print("<td>" + UtilI18N.internacionaliza(request, "avaliacao.fornecedor.sla.erro") + ".</td>");				
							out.print("</tr>");
						out.print("</table>");
					out.print("</td>");
				out.print("</tr>");
			}
		}
	}else{
		out.print("<tr>");
		out.print("<td>");
		out.print("<b>" + UtilI18N.internacionaliza(request, "avaliacaoContrato.erro") + "!</b>");	
		out.print("</td>");
		out.print("</tr>");
	}
	%>
</table>