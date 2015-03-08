<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<%
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<!doctype html public "">
<html>
	<head>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<%@include file="/novoLayout/common/include/titulo.jsp" %>
		<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/template.css"/>
		<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/theme/css/menu.css"/>

		<script type="text/javascript" src="./js/externalConnection.js"></script>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div class="<%=(iframe == null) ? "container-fluid fixed" : "" %>">

			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<div class="navbar <%=(iframe == null) ? "main" : "nomain" %> hidden-print">

				<% if(iframe == null) { %>
					<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
					<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>
				<% } %>

			</div>

			<div id="wrapper" class="<%=(iframe == null) ? "" : "nowrapper" %>">

				<!-- Inicio conteudo -->
				<div id="content">
					<div class="separator top"></div>
					<div class="row-fluid">
						<div class="innerLR">
							<div class="widget">
								<div class="widget-head">
									<h4 class="heading"><fmt:message key="externalconnection.conexoes"/></h4>
								</div>
								<div class="widget-body collapse in">
									<div class="tabsbar">
										<ul>
											<li class="active"><a href="#tab1-3" data-toggle="tab"><fmt:message key="externalconnection.cadastro"/></a></li>
											<li><a href="#tab2-3" data-toggle="tab" ><fmt:message key="externalconnection.pesquisa"/></a></li>
										</ul>
									</div>
									<div class="tab-content">
										<div class="tab-pane active" id="tab1-3">
											<form name='form' action='${ctx}/pages/externalConnection/externalConnection'>
												<input type='hidden' name='idExternalConnection' />
												<input type='hidden' name='deleted' />
												<input type='hidden' name='fileName' />

												<div class='row-fluid'>
													<div class='span12'>
														<div class='row-fluid'>
															<div class='span6'>
																<label class="campoObrigatorio"><fmt:message key="externalconnection.nome" /></label>
																<input type='text' name="nome" maxlength="80" class="Valid[Required] Description[externalconnection.nome]"/>
															</div>
															<div class='span3'>
																<label class="campoObrigatorio"><fmt:message key="externalconnection.tipo" /></label>
																	<select name="tipo" id="tipo" class="Valid[Required] Description[externalconnection.tipo] span12">
																	</select>
															</div>
															<div class='span3'>
																<label class="campoObrigatorio"><fmt:message key="externalconnection.banco" /></label>
																<input type='text' name="jdbcDbName" maxlength="255" class="Valid[Required] Description[externalconnection.banco]"/>
															</div>
														</div>
													</div>
												</div>
												<div class='row-fluid'>
													<div class='span12'>
														<div class='row-fluid'>
															<div class='span6'>
																<label class="campoObrigatorio"><fmt:message key="externalconnection.url" /></label>
																<input type='text' name="urlJdbc" maxlength="255" class="Valid[Required] Description[externalconnection.url]" />
															</div>
															<div class='span3'>
																<label class="campoObrigatorio"><fmt:message key="externalconnection.driver" /></label>
																<input type='text' name="jdbcDriver" maxlength="255" class="Valid[Required] Description[externalconnection.driver]" />
															</div>
															<div class='span3'>
																<label class="campoObrigatorio"><fmt:message key="externalconnection.schemaDb" /></label>
																<input type='text' name="schemaDb" maxlength="255" class="Valid[Required] Description[externalconnection.schemaDb]" />
															</div>
														</div>
													</div>
												</div>

												<div class='row-fluid'>
													<div class='span12'>
														<div class='row-fluid'>
														<div class='span3'>
															<label class="campoObrigatorio"><fmt:message key="externalconnection.usuario" /></label>
															<input type='text' name="jdbcUser" maxlength="255" class="Valid[Required] Description[externalconnection.usuario]"/>
														</div>
															<div class='span3' id="divSenha">
																<label class="campoObrigatorio"><fmt:message key="externalconnection.senha" /></label>
																<input id="senha" type="password" name="jdbcPassword" maxlength="255" class="Valid[Required] Description[externalconnection.senha]" />
															</div>
														</div>
													</div>
												</div>
												<div class='row-fluid' id="divAlterarSenha" style="display:none;">
													<label onclick="alterarSenha()" style="cursor: pointer; margin-top: 5px; margin-bottom: 5px;"><img alt="" src="${ctx}/template_new/images/icons/small/util/alterarsenha.png"><fmt:message key="usuario.alterarSenha"/></label>
												</div>

												<div class='row-fluid'>
													<div class='span12'>
														<button type='button' name='btnGravar' class="lFloat btn btn-icon btn-primary" onclick='document.form.save();'>
															<i></i><fmt:message key="citcorpore.comum.gravar" />
														</button>
														<button type="button" name='btnLimpar' class="lFloat btn btn-icon btn-primary" onclick='document.form.clear(); fechaAlterarSenha();'>
															<i></i><fmt:message key="citcorpore.comum.limpar" />
														</button>
														<button type='button' name='btnUpDate' class="lFloat btn btn-icon btn-primary" onclick='excluir();'>
															<i></i><fmt:message	key="citcorpore.comum.excluir" />
														</button>
														<button type='button' id='btnTestar' name='btnGerar' class="lFloat btn btn-icon btn-primary" onclick='document.form.fireEvent("testeConexao");'>
															<i></i><fmt:message	key="externalconnection.teste.conexao" />
														</button>
													</div>
												</div>
											</form>
										</div>
										<div class="tab-pane" id="tab2-3">
											<div class="section"><fmt:message key="citcorpore.comum.pesquisa" />
												<form name='formPesquisa'>
													<cit:findField formName='formPesquisa' lockupName='LOOKUP_CONEXOES' id='LOOKUP_CONEXOES' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
												</form>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

				</div>
				<!-- Fim conteudo-->
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
			</div>
		</div>

	</body>
</html>

