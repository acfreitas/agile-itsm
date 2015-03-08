<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%@page import="br.com.centralit.citcorpore.ajaxForms.Internacionalizar" %>
<%@page import="java.util.Collection" %>
<%@page import="java.util.Iterator" %>
<%@page import="br.com.citframework.util.Campo" %>
<%@page import="br.com.citframework.util.LookupFieldUtil" %>
<%@page import="br.com.citframework.util.UtilHTML" %>
<%@page import="br.com.citframework.util.UtilI18N" %>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<%
	Collection col = (Collection) request.getAttribute("retorno");
	String nomeLookup = request.getParameter("nomeLookup");
	String checkbox = request.getParameter("checkbox");//verificar se a lookup é do tipo radio ou seja retorna um unico valor ou se é checkbox que retorna array com valores
	Integer totalPag = 0;
	Integer pagAtual = 1;
	Integer totalItens = 0;
	if(request.getSession(true).getAttribute("totalPag_" + nomeLookup) != null){
		totalPag = new Integer( request.getSession(true).getAttribute("totalPag_" + nomeLookup).toString() );
		pagAtual = new Integer( request.getSession(true).getAttribute("pagAtualAux_" + nomeLookup).toString() );
		totalItens = new Integer( request.getSession(true).getAttribute("totalItens_" + nomeLookup).toString() );
	}
	String nomeLookupExec = nomeLookup;
	String id = request.getParameter("id");
	if (nomeLookup == null)
		nomeLookup = "";
	if (id != null) {
		if (!id.equalsIgnoreCase("")) {
			nomeLookup = id;
		}
	}
%>

<link type="text/css" rel="stylesheet" href="${ctx}/pages/lookup/css/retornoAjax.css" />

<table id='topoRetorno' class='dynamicTable table table-striped table-bordered table-condensed dataTable' width="100%">
<%
	boolean b;
	Campo cp;
	LookupFieldUtil lookUpField = new LookupFieldUtil();
	Collection colCamposRet = lookUpField.getCamposRetorno(nomeLookupExec);
	String strSeparaCampos = lookUpField.getSeparaCampos(nomeLookupExec);
	if (colCamposRet != null) {
		Iterator itRet = colCamposRet.iterator();
		out.print("<tr>");
		b = false;
		while (itRet.hasNext()) {
			cp = (Campo) itRet.next();
			if (b) {
				if (!"IGNORE".equalsIgnoreCase(cp.getDescricao())) {
					out.print("<td>");
					out.print("<b style='line-height: 25px;'>" + UtilHTML.encodeHTML(cp.getDescricao()) + "</b>");
					out.print("</td>");
				}
			} else {
				if (checkbox != null && checkbox.equalsIgnoreCase("true")){
					out.print("<td ><input  style='width: 20px !important; align: center;' type='checkbox' name='selTodosPagina' value='' onclick=\""+nomeLookup.toUpperCase()+"_marcarTodosCheckbox(this)\";></td>");
				}else {
					out.print("<td >&nbsp;</td>");
				}
			}
			b = true;
		}
		out.print("</tr>");
	}

	if (col != null) {
	Iterator it = col.iterator();
	Iterator it2;
	Collection colAux;
	if (!it.hasNext()) {
		out.print("<tr>");
		out.print("<td>&nbsp;</td><td>");
		out.print("<B><font color='RED'>"+UtilI18N.internacionaliza(request, "MSG04")+"</font></B>");
		out.print("</td>");
		out.print("</tr>");
	}
	int i = 0;
	String cor = "";
	while (it.hasNext()) {
			colAux = (Collection) it.next();
			it2 = colAux.iterator();
			if ((i % 2) == 0) {
				cor = "";
			} else {
				cor = "";
			}
			i++;
			String ret = "";
			b = false;

			while (it2.hasNext()) {

				cp = (Campo) it2.next();
				String strVal = "";
				if (cp.getObjValue() != null) {
					strVal = cp.getObjValue().toString();
				} else {
					cp.setObjValue("");
				}
				if (strVal.indexOf('\n') >= 0
						|| strVal.indexOf('\r') >= 0) {
					strVal = "";
				}
				if (!b) {
					b = true;
				} else {
					if (strSeparaCampos.equalsIgnoreCase("S")) {
						if (!ret.equalsIgnoreCase("")) {
							ret = ret + ", ";
						}
						ret = ret + "'" + strVal + "'";
					} else {
						if (!ret.equalsIgnoreCase("")) {
							ret = ret + " - ";
						}
						ret = ret + strVal;
					}
				}
			}
			if (!strSeparaCampos.equalsIgnoreCase("S")) {
				ret = "'" + ret + "'";
			}
			//
			it2 = colAux.iterator();
			out.print("<tr style='padding: 2px 0 3px 0; height: 20px;' class='"
					+ cor + "'>");
			b = false;
			if (checkbox == null || checkbox.equalsIgnoreCase("false")){
				while (it2.hasNext()) {
					cp = (Campo) it2.next();
					if (!b) {
						out.print("<td width='2' colspan='1'>");
						out.print("<input  style='width: 20px !important; align: center;' type='radio' name='sel' value='"+cp.getObjValue()+"'  onclick=\"setRetorno_"+nomeLookup.toUpperCase()+"('"+cp.getObjValue()+"',"+ret.replace("\\", "\\\\")+");\">");
						out.print("</td>");
						b=true;
					} else {
						if (!"IGNORE".equalsIgnoreCase(cp.getDescricao())) {
							out.print("<td >");
							if (cp.getObjValue().equals("")) {
								out.print("&nbsp;");
							} else {
								if (nomeLookup.equalsIgnoreCase("LOOKUP_PARAMETROCORPORE")) {
									out.print(Internacionalizar.internacionalizaOptionSN(request, "restore", cp.getObjValue().toString()));
								} else {
									out.print(cp.getObjValue());
								}
							}
							out.print("</td>");
						}
					}
				}
			}else {
				while (it2.hasNext()) {
					cp = (Campo) it2.next();
					if (!b) {
						out.print("<td width='2' colspan='1'>");
						out.print("<input  class='check' style='width: 20px !important; align: center;' type='checkbox' name='sel' value='"+cp.getObjValue()+"' onclick=concatenarValoresCheckados_"+nomeLookup+"(this);>");
						out.print("</td>");
						b=true;
						} else {
						if (!"IGNORE".equalsIgnoreCase(cp.getDescricao())) {
							out.print("<td >");
							if (cp.getObjValue().equals("")) {
								out.print("&nbsp;");
							} else {
								if (nomeLookup.equalsIgnoreCase("LOOKUP_PARAMETROCORPORE")) {
									out.print(Internacionalizar.internacionalizaOptionSN(request, "restore", cp.getObjValue().toString()));
								} else {
									out.print(cp.getObjValue());
								}
							}
							out.print("</td>");
						}
					}
				}
			}
			out.print("</tr>");
		}
	} else {
		out.print("<tr >");
		out.print("<td  colspan='11'>");
		out.print("<B><font size='2' color='red'>"+UtilI18N.internacionaliza(request, "MSG04")+"</font></B>");
		out.print("</td>");
		out.print("</tr>");
	}
%>
</table>
<script>
$(function() {
	$(".botao").button();
});
</script>
<div id="pag" style="text-align: center; display: block; width: 100%; margin-bottom: 5px;">
	<input id="btfirst" class="ui-widget ui-state-default botao corner-all" type="button" onclick="paginacao<%=nomeLookup%>(0);" value='<fmt:message key="citcorpore.comum.primeiro" />' title='<fmt:message key="citcorpore.comum.primeiro" />' style=" cursor: pointer" name="btfirst">
	<input id="btprevius" class="ui-widget ui-state-default botao corner-all" type="button" onclick="paginacao<%=nomeLookup%>(-1);" value='<fmt:message key="citcorpore.comum.anterior" />' title='<fmt:message key="citcorpore.comum.anterior" />' style=" cursor: pointer" name="btprevius">
	<output><fmt:message key="citcorpore.comum.mostrandoPagina" /></output> <%=pagAtual%> <output><fmt:message key="citcorpore.comum.mostrandoPaginaDe" /></output> <%=totalPag%>
	<input id="btnext" class="ui-widget ui-state-default botao corner-all" type="button" onclick="paginacao<%=nomeLookup%>(1);" value='<fmt:message key="citcorpore.comum.proximo" />' title='<fmt:message key="citcorpore.comum.proximo" />' style=" cursor: pointer" name="btnext">
	<input id="btlast" class="ui-widget ui-state-default botao corner-all" type="button" onclick="paginacao<%=nomeLookup%>(<%=totalItens%>);" value='<fmt:message key="citcorpore.comum.ultimo" />' title='<fmt:message key="citcorpore.comum.ultimo" />' style=" cursor: pointer" name="btlast">
</div>
