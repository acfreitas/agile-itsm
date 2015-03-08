<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>
	<%@include file="/include/security/security.jsp" %>
	<%@include file="/include/header.jsp"%>
	<title><fmt:message key="citcorpore.comum.title" /></title>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>

<style type="text/css">
	.table {
		border-left:1px solid #ddd;
	}

	.table th {
		border:1px solid #ddd;
		padding:4px 10px;
		border-left:none;
		background:#eee;
	}

	.table td {
		border:1px solid #ddd;
		padding:4px 10px;
		border-top:none;
		border-left:none;
	}
</style>
<script>

	//addEvent(window, "load", load, false);
	//function load() {};

	function atualizarEmail(){
		document.form.fireEvent("carregarEmails");
	}

	function toggleDiv(id){
		var div = document.getElementById(id);
		div.style.display = div.style.display == "none" ? "block" : "none";
	}


</script>
</head>
<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>

			<div class="flat_area grid_16">
				<h2>E-mails - Central de Serviços</h2>
			</div>
			<form name='form' action='${ctx}/pages/ClienteEmailCentralServico/ClienteEmailCentralServico'>
				<div class="box grid_16 tabs">
					<table>
						<tr>
							<td>Trazer mensagen(s) do(s) último(s)</td>
							<td><input size="3" type="text" id="ultimosXDias" name="ultimosXDias" /></td>
							<td>dia(s)</td>
							<td> <a href="#" onclick="atualizarEmail()">atualizar</a> </td>
						</tr>
					</table>
				</div>
				<div class="box grid_16 tabs" id="emails">
					<!--<table id="emails" class='table'>
						<tr>
							<th>Data</th>
							<th>De</th>
							<th>Assunto</th>
						</tr>
					</table>
				--></div>
			</form>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>
