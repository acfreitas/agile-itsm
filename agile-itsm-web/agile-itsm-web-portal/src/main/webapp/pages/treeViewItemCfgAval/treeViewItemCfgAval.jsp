<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>
<%
			//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
			String iframe = "";
			iframe = request.getParameter("iframe");

	String divInfo = (String)request.getAttribute("divInfo");
	divInfo = UtilStrings.nullToVazio(divInfo);
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<!--<![endif]-->
<title><fmt:message key="citcorpore.comum.title" /></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript">
	var ctx = "${ctx}";
</script>
<script type="text/javascript" src="./js/treeViewItemCfgAval.js"></script>
<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
<link rel="stylesheet" type="text/css" href="./css/treeViewItemCfgAval.css" />
<%}%>
</head>
<body>
	<div id="wrapper">
		<%if (iframe == null) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%if (iframe == null) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>

			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="treeViewItemCfgAval.titulo" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="treeViewItemCfgAval.titulo" />
					</a>
					</li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' method="post"
								action='${ctx}/pages/treeViewItemCfgAval/treeViewItemCfgAval'>
								<div class="columns clearfix">
									<input type='hidden' name='idItemConfiguracao' />
									<input type='hidden' name='processar' value="S"/>
									<div class="col_100">
										<div class="col_50">
											<fieldset>
												<label><fmt:message key="treeViewItemCfgAval.estrutura"/></label>
												<div>
													<select name='idGrupoItemConfiguracao' id='idGrupoItemConfiguracao'>
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label><fmt:message key="treeViewItemCfgAval.status" /></label>
												<div>
													<select name='status' id='status'>
													</select>
												</div>
											</fieldset>
										</div>
									</div>
									<div class="col_100">
										<div class="col_50">
											<fieldset>
												<label><fmt:message key="treeViewItemCfgAval.sistemaOperacional"/>
												<div>
													<select name='sistemaOperacional' id='sistemaOperacional'>
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label><fmt:message key="treeViewItemCfgAval.grupoTrabalho"/></label>
												<div>
													<select name='grupoTrabalho' id='grupoTrabalho'>
													</select>
												</div>
											</fieldset>
										</div>
									</div>
									<div class="col_100">
										<div class="col_50">
											<fieldset>
												<label><fmt:message key="treeViewItemCfgAval.tipoMebroDominio"/></label>
												<div>
													<select name='tipoMembroDominio' id='tipoMembroDominio'>
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label><fmt:message key="treeViewItemCfgAval.usuario"/></label>
												<div>
													<select name='usuario' id='usuario'>
													</select>
												</div>
											</fieldset>
										</div>
									</div>
									<div class="col_100">
										<div class="col_50">
											<fieldset>
												<label><fmt:message key="treeViewItemCfgAval.processador"/></label>
												<div>
													<select name='processador' id='processador'>
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label><fmt:message key="treeViewItemCfgAval.criticidade" /></label>
												<div>
													<select name='criticidade' id='criticidade'>
													</select>
												</div>
											</fieldset>
										</div>
									</div>
									<div class="col_100">
										<div class="col_75">
											<fieldset>
												<label><fmt:message key="treeViewItemCfgAval.softwareInstalado"/></label>
												<div>
													<textarea rows="3" cols="150" name='softwares' id='softwares'></textarea>
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<table>
												<tr>
													<td>
														<button type='button' class="light" onclick='submeter()'><span><fmt:message key="citcorpore.comum.pesquisar"/></span></button>
														<!-- /*
														Rodrigo Pecci Acorse - 03/12/2013 17h30 - #126233
														Adicionado a função setaProcessar para após o clear não limpar o valor do input hidden processa.
														*/ -->
														<button type="button" class="light" name="btnLimpar" id="btnLimpar" onclick="document.form.clear();setaProcessar('S');"><span><fmt:message key="botaoacaovisao.limpar_dados" /></span></button>
													</td>
												</tr>
											</table>
										</div>
									</div>
								</div>
								<div id='divInfo'>
									<%=divInfo %>
								</div>
								<br> <br>
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

	<div id="POPUP_SOFTS" title="Itens">
		<div id='divInfoSoftware'></div>
	</div>
</body>
</html>

