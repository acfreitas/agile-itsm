<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>
<!doctype html public "">
<html>
<head>
<%
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
	<%@include file="/include/header.jsp" %>
    <%@include file="/include/security/security.jsp" %>
	<title><fmt:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/menu/menuConfig.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>

	<script type="text/javascript" src="../../cit/objects/GrupoDTO.js"></script>

	<link rel="stylesheet" type="text/css" href="./css/usuario.css" />
	<script type="text/javascript" src="./js/usuario.js"></script>

<%//se for chamado por iframe deixa apenas a parte de cadastro da pÃ¡gina
			if (iframe != null) {%>
<link rel="stylesheet" type="text/css" href="./css/usuario.css" />

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
				<h2><fmt:message key="usuario.usuario"/></h2>
		</div>
  <div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1"><fmt:message key="usuario.cadastroUsuario"/></a>
				</li>
				<li>
					<a href="#tabs-2" class="round_top"><fmt:message key="usuario.pesquisaUsuario"/></a>
				</li>
			</ul>
	<a href="#" class="toggle">&nbsp;</a>
	 			<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='${ctx}/pages/usuario/usuario'>

									<div class="columns clearfix">
										<input type='hidden' name='idUsuario' id='idUsuario'/>
										<input type='hidden' name='idEmpresa' id='idEmpresa'  />
										<input type='hidden' name='idEmpregado' id='idEmpregado' />
										<input type='hidden' name='dataInicio' id='dataInicio' />
										<input type='hidden' name='dataFim' id='dataFim' />
										<input type='hidden' name='status' id='status'  />
										<input type='hidden' name='colGrupoSerialize'/>
										<input type="hidden" id="rowIndex" name="rowIndex"/>
										<input type='hidden' name='idGrupo' id='idGrupo'/>
										<input type='hidden' name='ldap' id='ldap'/>


										<div class="col_66">
											<fieldset>
												<label  class="campoObrigatorio" style="cursor: pointer;"><fmt:message key="colaborador.colaborador"/></label>
												<div>
													<input id="addEmpregado" id="nomeUsuario" type='text' readonly="readonly" name="nomeUsuario" maxlength="256" class="Valid[Required] Description[colaborador.colaborador]" />
												</div>
											</fieldset>
										</div>

										<div class="col_66">
											<div class="col_50" >
												<fieldset style="height: 61px">
													<label  class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="usuario.login"/></label>
													<div>
														<input id="login" type='text' name="login" maxlength="256" class="Valid[Required] Description[usuario.login]" />
													</div>
												</fieldset>
											</div>

											<div class="col_50">
												<fieldset style="height: 61px">
													<label class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="usuario.perfilAcesso"/>
													</label>
													<div>
														<select name="idPerfilAcessoUsuario" id ="idPerfilAcessoUsuario"  class="Valid[Required] Description[usuario.perfilAcesso]">
														</select>
													</div>
												</fieldset>
											</div>
										</div>

										<div class="col_66" id="divAlterarSenha">
											<fieldset>
												<label onclick="alterarSenha()" style="cursor: pointer; margin-top: 5px; margin-bottom: 5px;"><img alt="" src="${ctx}/template_new/images/icons/small/util/alterarsenha.png"><fmt:message key="usuario.alterarSenha"/></label>
											</fieldset>
										</div>

										<div class="col_66" id="divSenha">
											<div class="col_50" >
												<fieldset>
													<label  class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="usuario.senha"/></label>
													<div>
														<input id="senha" type="password" name="senha"  maxlength="300"  />
													</div>
												</fieldset>
											</div>
											<div class="col_50" >
												<fieldset>
													<label class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="usuario.senhaNovamente"/></label>
													<div>
														<input id="senhaNovamente" type="password" name="senhaNovamente" maxlength="300" />
													</div>
												</fieldset>
											</div>
										</div>

										<div class="col_66">
										<div class = "columns clearfix">
										<div>
											<fieldset style="border-bottom: none; padding-bottom: 5px; padding-top: 5px;">
												<label id="addGrupo" style="cursor: pointer;"
														title="<fmt:message  key="citcorpore.comum.cliqueParaAdicinar" />"><fmt:message key="controle.grupo" /><img	src="${ctx}/imagens/add.png"></label>
											</fieldset>
											</div>
											<div id ="divGrupo">
														<table class="table" id="tabelaGrupo" style="width: 850px; margin-left: 15px;">
															<tr>
															   <th style="width: 1%;"></th>
																<th style="width: 35%;"><fmt:message  key="grupo.idgrupo" /></th>
																<th style="width: 85%;"><fmt:message  key="controle.grupo" /></th>
															</tr>
														</table>
														</div>
											</div>
										</div>


			                     </div>

			                    <br><br>
									<button type='button' name='btnGravar' class="light text_only has_text" onclick='validar();'>
										<img
											src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar" />
										</span>
									</button>
									<button type="button" name='btnLimpar' class="light text_only has_text" onclick='limpar();'>
										<img
											src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="citcorpore.comum.limpar" />
										</span>
									</button>
									<button type='button' name='btnUpDate' class="light text_only has_text"
										onclick='excluir();'>
										<img
											src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
										<span><fmt:message key="citcorpore.comum.excluir" />
										</span>
									</button>
									<button type='button' name='btnImportar' class="light text_only has_text" onclick="popup.abrePopup('cargaUsuarioAd', ' ')">
										<span><fmt:message key="parametroCorpore.importarDados"/></span>
									</button>

								<div id="popupCadastroRapido">
			                           <!-- ## Desenvolvedor: Euler Data: 28/10/2013 Horário: 09h45min ID Citsmart: 120393 Motivo/Comentário: Eliminar multiplas scrolls ## -->
			                           <iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="99%"></iframe>
		                        </div>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section"><fmt:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_USUARIO' id='LOOKUP_USUARIO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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
<div id="POPUP_EMPREGADO" title="<fmt:message key="citcorpore.comum.pesquisa" />">
	<div class="box grid_16 tabs"  style="width: 570px !important;">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisaEmp' style="width: 540px !important;">
						<cit:findField formName='formPesquisaEmp'
							lockupName='LOOKUP_EMPREGADO_USUARIO' id='LOOKUP_EMPREGADO_USUARIO' top='0'
							left='0' len='1050' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="POPUP_GRUPO" title="<fmt:message key="citcorpore.comum.pesquisa" />">
	<div class="box grid_16 tabs"  style="width: 570px !important;">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisaGrupo' style="width: 540px !important;">
						<cit:findField formName='formPesquisaGrupo'
							lockupName='LOOKUP_GRUPO_EVENTO' id='LOOKUP_GRUPO_EVENTO' top='0'
							left='0' len='1050' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</html>


