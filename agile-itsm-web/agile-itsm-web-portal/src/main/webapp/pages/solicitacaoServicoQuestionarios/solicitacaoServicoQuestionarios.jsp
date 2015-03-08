<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="java.util.Collection"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="java.util.Iterator"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<%
response.setCharacterEncoding("ISO-8859-1");

String include = (String)request.getAttribute("includeQuestionario");
String idContratoQuestionario = (String)request.getAttribute("idContratoQuestionario");
String idQuestionario = (String)request.getAttribute("idQuestionario");
String subForm = (String)request.getAttribute("subForm");
String aba = (String)request.getAttribute("aba");
String situacao = (String)request.getAttribute("situacao");
String HASH_CONTEUDO = (String)request.getSession().getAttribute("HASH_CONTEUDO");
String idContrato = (String)request.getParameter("idContrato");
String dataQuestionario = (String)request.getParameter("dataQuestionario");
%>

<%
if (include == null){
	include = RedirectQuestionarioConfig.getInstance().getIncludeCorrespondente("PADRAO", "P");
}
%>

<%@page import="br.com.centralit.citcorpore.util.RedirectQuestionarioConfig"%>
OK!
