<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>

<!doctype html public "">
<html>
<head>
<%
	String iframe = "";
	iframe = request.getParameter("iframe");
	if (iframe == null) {
		iframe = "false";
	}
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<script type="text/javascript" src="./js/grupoItemConfiguracao.js"></script>
</head>
<body>
	<div id="wrapper">
		<%
			if (iframe == null || iframe.equalsIgnoreCase("false")) {
		%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%
			}
		%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%
				if (iframe == null || iframe.equalsIgnoreCase("false")) {
			%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%
				}
			%>

			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="grupoItemConfiguracao.grupo" />
				</h2>
			</div>

			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message
								key="grupoItemConfiguracao.cadastro" /></a></li>
					<li><a href="#tabs-2" class="round_top"><fmt:message
								key="grupoItemConfiguracao.pesquisa" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='${ctx}/pages/grupoItemConfiguracao/grupoItemConfiguracao'>
								<div class="columns clearfix">
									<input type='hidden' id="idGrupoItemConfiguracao"
										name='idGrupoItemConfiguracao' /> <input type='hidden'
										name='dataInicio' /> <input type='hidden' name='dataFim' />

									<div class="columns clearfix">
										<div class="col_40">
											<fieldset>
												<label class="campoObrigatorio"><fmt:message
														key="unidade.grupo" /></label>
												<div>
													<input type='text' id="nomeGrupoItemConfiguracao"
														name="nomeGrupoItemConfiguracao" maxlength="100" size="100"
														class="Valid[Required] Description[Grupo]" />
												</div>
											</fieldset>
										</div>
										<div class="col_40">
											<fieldset>
												<label class="campoObrigatorio"><fmt:message
														key="citcorpore.comum.email" /></label>
												<div>
													<input type='text' id="emailGrupoItemConfiguracao"
														name="emailGrupoItemConfiguracao" maxlength="256" size="256"
														class="Valid[Required] Description[Email Grupo]" />
												</div>
											</fieldset>
										</div>
									</div>
									<br>
								</div>
								<br>
								<br>
								<button type='button' name='btnGravar' class="light"
									onclick='gravar();'>
									<img
										src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar" /></span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='limpar();'>
									<img
										src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar" /></span>
								</button>
								<button type='button' name='btnUpDate' class="light"
									onclick='excluir();'>
									<img
										src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
									<span><fmt:message key="citcorpore.comum.excluir" /></span>
								</button>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<fmt:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'
									lockupName='LOOKUP_GRUPOITEMCONFIGURACAO'
									id='LOOKUP_GRUPOITEMCONFIGURACAO' top='0' left='0' len='550'
									heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>

