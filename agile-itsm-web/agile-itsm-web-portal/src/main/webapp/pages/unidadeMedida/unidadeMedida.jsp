<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>
<%
			String iframe = "";
			iframe = request.getParameter("iframe");
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>

<title><fmt:message key="citcorpore.comum.title"/></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript" src="./js/unidadeMedida.js"></script>
<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
<link rel="stylesheet" type="text/css" href="./css/unidadeMedida.css" />
<%}%>
</head>
<body>



	<div id="wrapper">
		<%if (iframe == null) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
		<div id="main_container" class="main_container container_16 clearfix">
		<%if (iframe == null) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>

			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="unidadeMedida" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="unidadeMedida.cadastro" />
					</a></li>
					<li><a href="#tabs-2" class="round_top"><fmt:message key="unidadeMedida.pesquisa" />
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='${ctx}/pages/unidadeMedida/unidadeMedida'>
								<input type='hidden' name='idUnidadeMedida' id = "idUnidadeMedida" />
								<div class="columns clearfix">
									<div class="col_33">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="unidadeMedida.nome" /></label>
											<div>
												<input type='text' name="nomeUnidadeMedida" id = "nomeUnidadeMedida" maxlength="100"
													class="Valid[Required] Description[unidadeMedida.nome]" />
											</div>
										</fieldset>
									</div>
								<div class="col_33">
										<fieldset>
											<label class="campoObrigatorio" ><fmt:message key="unidadeMedida.sigla" /></label>
											<div>
												<input type='text' name="siglaUnidadeMedida" id = "sigla" maxlength="10"
													class="Valid[Required] Description[unidadeMedida.sigla]" />
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset style="height: 55px">
											<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.situacao" /></label>
											<div  class="inline clearfix">
											<label><fmt:message key="citcorpore.comum.ativo" /><input type="radio"  name="situacao" value="A" checked="checked" /></label>
											<label><fmt:message key="citcorpore.comum.inativo" /><input type="radio"  name="situacao" value="I" /></label>
											</div>
										</fieldset>
									</div>
								</div>
								<br>
								<br>
								<button type='button' name='btnGravar' class="light"
									onclick='document.form.save();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='document.form.clear();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type='button' name='btnExcluir' id="btnExcluir"
									class="light" onclick='excluir();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
									<span><fmt:message key="citcorpore.comum.excluir" />
									</span>
								</button>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<fmt:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'lockupName='LOOKUP_UNIDADEMEDIDA' id='LOOKUP_UNIDADEMEDIDA' top='0'left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
</body>

</html>


