<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/include/header.jsp"%>
<script>
	function voltar(){
		parent.window.location = '${ctx}/pages/index/index.load';
	}
	function salvar(){
		parent.direita.saveDash();
	}
	function novo(){
		parent.direita.novoDash();
	}
</script>
</head>
<body>
<table>
	<tr>
		<td>
			<button type='button' onclick='novo()' class='light'><fmt:message key="dashboard.novo" /></button>
		</td>
		<td>
			<button type='button' onclick='salvar()' class='light'><fmt:message key="dashboard.salvar" /></button>
		</td>
		<td>
			<button type='button' onclick='voltar()' class='light'><fmt:message key="questionario.voltar" /></button>
		</td>
	</tr>
</table>
</body>
</html>
