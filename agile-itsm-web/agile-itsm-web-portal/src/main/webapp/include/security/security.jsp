<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%UsuarioDTO usuarioValidacaoSeguranca = WebUtil.getUsuario(request);%>

<%
if (usuarioValidacaoSeguranca == null){
	//response.sendRedirect(CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/login/login.load");
}
%>