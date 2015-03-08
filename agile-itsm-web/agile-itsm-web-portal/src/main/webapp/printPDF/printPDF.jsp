<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<%
	String fileURL = request.getParameter("url");
%>

<%@page import="br.com.citframework.util.Constantes"%>
<html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
</head>

<body>
	<script>
		function recarrega(){
			document.getElementById('frameVIEW').src = '<%=fileURL%>';
		}
		function fechar(){
			window.close();
		}
	</script>
	<table width="100%" height="100%">
		<tr>
			<td width="100%">
				<input type='button' style="background-color: #ffff00" name='btnRecarregar' value='Caso não seja apresentado o documento, clique aqui para recarregar o documento'
						onclick='recarrega();'/>
				<input type='button' style='color: #444; font-weight: bold; background: url("${ctx}/imagens/fecharLookup.gif") no-repeat scroll top left' name='btnFechar' value='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Fechar&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
						onclick='fechar();'/>
			</td>
		</tr>
		<tr>
			<td width="100%" height="98%">
				<iframe id='frameVIEW' width="100%" height="100%" src="<%=fileURL%>"></iframe>
			</td>
		</tr>
	</table>
</body>
	<script>
		//window.moveTo(0,0);
		//window.resizeTo(screen.width,screen.height);
	</script>
</html>