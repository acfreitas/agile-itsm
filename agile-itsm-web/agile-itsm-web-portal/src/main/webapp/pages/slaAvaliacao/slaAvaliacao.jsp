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

<script type="text/javascript" src="${ctx}/js/UploadUtils.js"></script>
<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>


<script type="text/javascript" src="./js/slaAvaliacao.js"></script>

<%
//se for chamado por iframe deixa apenas a parte de cadastro da página
if(iframe != null){%>
<link rel="stylesheet" type="text/css" href="./css/slaAvaliacao.css" />
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

		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix" style='height: 0px!important;top: 5%!important;'>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="menu.nome.avaliarSLA" /></a></li>
				</ul>
				<div class="toggle_container">
					<div class="block" >
						<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='${ctx}/pages/slaAvaliacao/slaAvaliacao'>
								<fieldset>
									<legend><fmt:message key="citcorpore.comum.filtros" /></legend>
									<table>
										<tr>
											<td>
												<fmt:message key="avaliacaocontrato.periodo" /><font color="red">*</font>
											</td>
											<td>
												<input type='text' name='dataInicio' id ='dataInicio'  size="10" maxlength="10" class="Valid[Required,Date] Description[avaliacao.fornecedor.dataInicio] Format[Date] datepicker" />
											</td>
											<td>
												<input type='text' name='dataFim' id = 'dataFim' size="10" maxlength="10" class="Valid[Required,Date] Description[avaliacao.fornecedor.dataFim] Format[Date] datepicker" />
											</td>
											<td>
												&nbsp;
											</td>
											<td style='vertical-align: top;'>
												<button type="button" onclick='gerarInformacoes()'>
													<fmt:message key="citcorpore.comum.gerarInformacoes" />
												</button>
											</td>
										</tr>
									</table>
								</fieldset>
								<div id='divInfo' style="overflow: auto!important;">
								</div>
							</form>
							</div>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->

	<%@include file="/include/footer.jsp"%>
</body>
</html>

