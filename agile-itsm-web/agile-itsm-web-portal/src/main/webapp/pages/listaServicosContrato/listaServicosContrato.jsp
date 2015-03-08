<%@page import="br.com.centralit.citcorpore.util.Enumerados.TipoDate"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="com.lowagie.text.Utilities"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.centralit.citcorpore.bean.ServicoContratoDTO"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.citframework.util.UtilI18N"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<%
	Collection listaServicos = (Collection)request.getAttribute("listaServicos");
	Integer totalPag = 0; 
	Integer pagAtual = 1;
	Integer totalItens = 0;
	if(request.getSession(true).getAttribute("totalPag") != null){
		totalPag = new Integer( request.getSession(true).getAttribute("totalPag").toString() );	
		pagAtual = new Integer( request.getSession(true).getAttribute("pagAtualAux").toString());
		totalItens = new Integer( request.getSession(true).getAttribute("totalItens").toString() );		
	}	
%>

<div id="retPesq" style='width: 98%'>
	<button type='button' name='btnGravar' class="light"  onclick='adicionaServico();'>
		<img src='${ctx}/template_new/images/icons/small/grey/plane_suitcase.png'>
		<span><fmt:message key="citcorpore.comum.adicionarServicoContrato" /></span>
	</button>
	<button type='button' name='btnGravar' class="light"  onclick='adicionaVariosServico();'>
		<img src='${ctx}/template_new/images/icons/small/grey/plane_suitcase.png'>
		<span><fmt:message key="citcorpore.comum.adicionarVariosServicoContrato" /></span>
	</button>	
	<button type='button' name='btnGravar' class="light"  onclick='showDesempenhoContrato();'>
		<img src='${ctx}/template_new/images/icons/small/grey/plane_suitcase.png'>
		<span><fmt:message key="citcorpore.comum.desempenhoContrato" /></span>
	</button>	
	<button type='button' name='btnExcluir' class="light" onclick='excluirCheckedBoxes();'>
		<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
		<span><fmt:message key="citcorpore.comum.removerServicosContrato" /></span>
	</button>		
	<button type='button' name='btnGerenciarSlas' class="light" onclick='gerenciarSlas();'>
		<img src="${ctx}/template_new/images/icons/small/grey/plane_suitcase.png">
		<span><fmt:message key="citcorpore.comum.gerenciarSlas" /></span>
	</button>
<br/>
<table cellpadding="0" cellspacing="0" width="100%" style='width: 98%' class="table table-bordered table-striped" id="tabelaServicoContrato">
	<tr style='text-align: center;' class=''>
		<td>
			&nbsp;
		</td>
		<td>
			<b><fmt:message key="citcorpore.comum.servico"/></b>
		</td>
		<td>
			<b><fmt:message key="citcorpore.comum.situacao"/></b>
		</td>		
		<td>
			<b><fmt:message key="avaliacao.fornecedor.servico.demanda"/></b>
		</td>	
		<td>
			<b><fmt:message key="avaliacao.fornecedor.sla"/></b>
		</td>			
		<td>
			<b><fmt:message key="citcorpore.comum.datainicio"/></b>
		</td>
		<td>
			<b><fmt:message key="citcorpore.comum.datafim"/></b>
		</td>	
			<td>
			<b><input type="checkbox" title='<fmt:message key="citcorpore.comum.selecionarTodos" />' id="marcarExcluir" onclick="marcarTodosCheckbox(this);" /></b>
		</td>				
	</tr>
	<%
	if (listaServicos != null && listaServicos.size() > 0){
		for(Iterator it = listaServicos.iterator(); it.hasNext();){
			ServicoContratoDTO servicoContratoAux = (ServicoContratoDTO) it.next();
			out.print("<tr >");
				out.print("<td >");
				
					out.print("<table class='table table-bordered table-condensed'>");
					out.print("<tr >");
					out.print("<td >");
					out.print("<img src='" + br.com.citframework.util.Constantes
							.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
							.getValue("CONTEXTO_APLICACAO") + "/imagens/write.png' border='0' style='cursor:pointer' title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.editarServico") 
							+ "' onclick='editarServicoContrato(" + servicoContratoAux.getIdServicoContrato() + ")'/>");					
					out.print("</td>");
					/* out.print("<td>&nbsp;</td>");	 */				
					out.print("<td>");
					out.print("<img src='" + br.com.citframework.util.Constantes
							.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
							.getValue("CONTEXTO_APLICACAO") + "/imagens/competitors-icon.png' border='0' style='cursor:pointer' title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.acordosNivelServico") 
							+ "' onclick='acordosServicoContrato(" + servicoContratoAux.getIdServicoContrato() + ")'/>");					
					out.print("</td>");
					/* out.print("<td>&nbsp;</td>"); */
					out.print("<td>");
					out.print("<img src='" + br.com.citframework.util.Constantes
							.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
							.getValue("CONTEXTO_APLICACAO") + "/imagens/general_options.png' border='0' style='cursor:pointer' title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.atividadesServico") 
							+ "' onclick='atividadesServicoContrato(" + servicoContratoAux.getIdServicoContrato() + ")'/>");
					out.print("</td>");		
					
					/* out.print("<td>&nbsp;</td>"); */
 					out.print("<td>");
 					out.print("<img src='" + br.com.citframework.util.Constantes
 							.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
 							.getValue("CONTEXTO_APLICACAO") + "/imagens/man_grey.png' border='0' style='cursor:pointer' title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.valorServico") 
 							+ "' onclick='valorServicoContrato(" + servicoContratoAux.getIdServicoContrato() + ")'/>");
 					out.print("</td>");					
					out.print("</tr>");
					out.print("</table>");
					
				out.print("</td>");			
				out.print("<td>");
					if(servicoContratoAux.getServicoDto().getSiglaAbrev() != null && !servicoContratoAux.getServicoDto().getSiglaAbrev().equals("")){
						out.print(servicoContratoAux.getServicoDto().getSiglaAbrev());
						out.print(" - ");
						out.print(servicoContratoAux.getNomeServico());
					}else{
						out.print(servicoContratoAux.getNomeServico());
					}
				out.print("</td>");
				out.print("<td>");
				if (servicoContratoAux.getSituacaoServico().intValue() == 1){
					if (servicoContratoAux.getDataFim() != null && servicoContratoAux.getDataFim().before(UtilDatas.getDataAtual())){
						out.print("<img src='" + br.com.citframework.util.Constantes
								.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
								.getValue("CONTEXTO_APLICACAO") + "/imagens/bolavermelha.png' border='0'  title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.servicoInativo") + "'/> ");					
						out.print(UtilI18N.internacionaliza(request, "avalicaoContrato.Inativo(Datafim)"));						
					}else{
						out.print("<img src='" + br.com.citframework.util.Constantes
								.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
								.getValue("CONTEXTO_APLICACAO") + "/imagens/bolaverde.png' border='0'  title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.servicoAtivo") + " '/> ");					
						 out.print(UtilI18N.internacionaliza(request, "citcorpore.comum.ativo"));						
					}
				}else if (servicoContratoAux.getSituacaoServico().intValue() == 2){
					out.print("<img src='" + br.com.citframework.util.Constantes
							.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
							.getValue("CONTEXTO_APLICACAO") + "/imagens/bolavermelha.png' border='0'  title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.servicoInativo") + "'/> ");					
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
						strSituacao = UtilI18N.internacionaliza(request, "baseconhecimento.emdesenho");
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
							.getValue("CONTEXTO_APLICACAO") + "/imagens/tick.png' border='0'  title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.SLAOk") + "'/>");					
				}else{
					out.print("<img src='" + br.com.citframework.util.Constantes
							.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
							.getValue("CONTEXTO_APLICACAO") + "/imagens/b.gif' border='0'  title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.naoHaSLA") + "'/>");					
				}
				out.print("</td>");
				out.print("<td>");
					out.print(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT,servicoContratoAux.getDataInicio(), WebUtil.getLanguage(request)));
				out.print("</td>");	
				out.print("<td>");
				if (servicoContratoAux.getDataFim() != null){
					out.print(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, servicoContratoAux.getDataFim(),WebUtil.getLanguage(request)));
				}else{
					out.print("--");
				}
				out.print("</td>");	
				out.print("<td>");
				out.print("<input  type='checkbox' class='excluir' name='excluir' id='excluir' value="+servicoContratoAux.getIdServicoContrato()+"></input>");
			out.print("</td>");	
			out.print("</tr>");
			out.print("<tr>");
				out.print("<td>");
					out.print("&nbsp;");
				out.print("</td>");
				out.print("<td colspan='20'>");
					out.print("<div   class='divResServ' id='divContratoServico_" + servicoContratoAux.getIdServicoContrato() + "' style='display:none'></div>");
				out.print("</td>");
			out.print("</tr>");
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
</div>
<div id="pag" style="text-align: center; display: block; width: 100%; margin-bottom: 5px;">
		<input id="btfirst" class="ui-widget ui-state-default botao corner-all" type="button" onclick="paginacao(0)" value='<fmt:message key="citcorpore.comum.primeiro" />' title='<fmt:message key="citcorpore.comum.primeiro" />' style=" cursor: pointer" name="btfirst">
		<input id="btprevius" class="ui-widget ui-state-default botao corner-all" type="button" onclick="paginacao(-1)" value='<fmt:message key="citcorpore.comum.anterior" />' title='<fmt:message key="citcorpore.comum.anterior" />' style=" cursor: pointer" name="btprevius">
		<label><fmt:message key="citcorpore.comum.mostrandoPagina" /></label> <%=pagAtual%> <label><fmt:message key="citcorpore.comum.mostrandoPaginaDe" /></label> <%=totalPag%> 
	<input id="btnext" class="ui-widget ui-state-default botao corner-all" type="button" onclick="paginacao(1)" value='<fmt:message key="citcorpore.comum.proximo" />' title='<fmt:message key="citcorpore.comum.proximo" />' style=" cursor: pointer" name="btnext">
		<input id="btlast" class="ui-widget ui-state-default botao corner-all" type="button" onclick="paginacao(<%=totalItens%>);" value='<fmt:message key="citcorpore.comum.ultimo" />' title='<fmt:message key="citcorpore.comum.ultimo" />' style=" cursor: pointer" name="btlast">
</div>