<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html>
<html>
<head>
<%
	//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
	String iframe = "";
	iframe = request.getParameter("iframe");

%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp" %>
<title><fmt:message key="citcorpore.comum.title"/></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript" src="./js/recursoAvaliacao.js"></script>

<%
//se for chamado por iframe deixa apenas a parte de cadastro da página
if(iframe != null){%>
<link rel="stylesheet" type="text/css" href="./css/recursoAvaliacao.css" />
<%}%>

</head>

<body>
	<div id="wrapper">
	<%if(iframe == null){%>
		<%@include file="/include/menu_vertical.jsp"%>
	<%}%>
<!-- Conteudo -->
	<div id="main_container">
	<%if(iframe == null){%>
		<%@include file="/include/menu_horizontal.jsp"%>
	<%}%>

			<form name='form' action='${ctx}/pages/recursoAvaliacao/recursoAvaliacao'>
				<div>
					<fieldset>
						<legend><fmt:message key='citcorpore.comum.filtros'/></legend>
						<table>
							<tr>
								<td>
									<label  class="campoObrigatorio"><fmt:message key="citcorpore.comum.periodo" /></label>
								</td>
								<td>
									<input type='text' name='dataInicio' size="10" maxlength="10" class="Valid[Required,Date] Description[visao.dataDeInicio] Format[Date] datepicker" />
								</td>
								<td>
									<input type='text' name='dataFim' size="10" maxlength="10" class="Valid[Required,Date] Description[avaliacao.fornecedor.dataFim] Format[Date] datepicker" />
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td style='vertical-align: top;'>
									<label><fmt:message key="controle.grupo"/>:</label>
								</td>
								<td colspan="2" style='vertical-align: top;'>
									<select name='idGrupoRecurso' class="Description[Grupo]">
									</select>
								</td>
								<td style='vertical-align: top;'>
									<button type="button" onclick='gerarInformacoes()'>
										<fmt:message key="citcorpore.comum.gerarInformacoes"/>
									</button>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
				<div id='divInfo'>
				</div>
			</form>

		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>

