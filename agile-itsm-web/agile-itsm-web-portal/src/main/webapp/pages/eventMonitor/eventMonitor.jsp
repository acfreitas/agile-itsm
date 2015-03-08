<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<html>
<head>
<%@include file="/include/security/security.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>

<script type="text/javascript" src="./js/eventMonitor.js"></script>
</head>

<body>
	<form name='form' id='form' action="${ctx}/pages/eventMonitor/eventMonitor" method="post">
		<input type='hidden' name='idGrupoRecurso'/>
		<input type='hidden' name='nomeGrupoRecurso'/>
		<table width='100%'>
			<tr>
				<td>
					<img src='${ctx}/imagens/logo/logo.png' border='0'/>
				</td>
				<td style='border:1px solid black; background-color: lightgray'>
					<div id='timeRefresh'>
					</div>
				</td>
				<td>
					<div id='divServicesCritical' style='width: 100%;border:1px solid black; height: 60px; overflow: auto'>
					</div>
				</td>
			</tr>
		</table>
		<table width='100%'>
			<tr>
				<td>
					<div id='divGrupos' style='width: 100%;'>

					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id='divDetalhamento' style='width: 100%;'>

					</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>

