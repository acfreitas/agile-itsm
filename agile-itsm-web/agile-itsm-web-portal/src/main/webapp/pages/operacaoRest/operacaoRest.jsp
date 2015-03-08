<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>
<%@page import="br.com.centralit.citsmart.rest.bean.RestParameterDTO"%>
<%@page import="java.util.Collection"%>

<!DOCTYPE html>
<html>
<head>
	<%
		String iframe = request.getParameter("iframe");
		Collection<RestParameterDTO> colParametros = (Collection)request.getAttribute("colParametros");
	%>
	<%@include file="/include/header.jsp"%>

	<%@include file="/include/security/security.jsp" %>
	<title><fmt:message key="citcorpore.comum.title" /></title>
	<%@include file="/include/menu/menuConfig.jsp" %>

	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>

	<script type="text/javascript" src="../../cit/objects/GrupoDTO.js"></script>

	<link rel="stylesheet" type="text/css" href="./css/operacaoRestAll.css" />
	
	<script type="text/javascript" src="./js/operacaoRest.js"></script>
	

	<script type="text/javascript" src="${ctx}/js/operacaoRest.js"></script>

	<% if (iframe != null) {%>
	<link rel="stylesheet" type="text/css" href="./css/operacaoRestIframe.css" />
	<%}%>
</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>

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
				<h2><fmt:message key="operacaoRest.operacao" /></h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li>
						<a href="#tabs-1"><fmt:message key="operacaoRest.cadastroOperacao" /></a>
					</li>
					<li>
						<a href="#tabs-2" class="round_top"><fmt:message key="operacaoRest.pesquisaOperacao" /></a>
					</li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name="form" action="${ctx}/pages/operacaoRest/operacaoRest">
								<div class="columns clearfix">
									<input type="hidden" name="idRestOperation" id="idRestOperation" />
									<input type="hidden" name="colGrupoSerialize" />
									<input type="hidden" name="colParametros_Serialize" />
									<input type="hidden" id="rowIndex" name="rowIndex" />
									<input type="hidden" name="idGrupo" id="idGrupo" />

									<div class="col_100">
										<div class="col_80">
											<fieldset>
												<label class="campoObrigatorio" style="cursor: pointer;"><fmt:message key="operacaoRest.nome" /></label>
												<div>
													<input id="name" id="name" type="text"name="name" maxlength="80" class="Valid[Required] Description[operacaoRest.nome]" />
												</div>
											</fieldset>
										</div>
										<div class="col_20">
											<fieldset>
												<label class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="operacaoRest.tipoOperacao" /></label>
												<div>
													<input type="radio" id="operationType" name="operationType" value="Sync" class="Valid[Required] Description[operacaoRest.tipoOperacao]" /><fmt:message key="operacaoRest.sincrona" />
													<input type="radio" id="operationType" name="operationType" value="Async" class="Valid[Required] Description[operacaoRest.tipoOperacao]" /><fmt:message key="operacaoRest.assincrona" />
												</div>
											</fieldset>
										</div>
									</div>

									<div class="col_100">
										<div class="col_80">
											<fieldset>
												<label class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="operacaoRest.descricao" /></label>
												<div>
													<textarea rows="3" cols="5" name="description" class="Valid[Required] Description[operacaoRest.descricao]"></textarea>
												</div>
											</fieldset>
										</div>

										<div class="col_20">
											<fieldset>
												<label class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="operacaoRest.geraLog" />
												</label>
												<div>
													<input type="radio" id="generateLog" name="generateLog" value="Y" class="Valid[Required] Description[operacaoRest.geraLog]" /><fmt:message key="citcorpore.comum.sim" />
													<input type="radio" id="generateLog" name="generateLog" value="N" class="Valid[Required] Description[operacaoRest.geraLog]" /><fmt:message key="citcorpore.comum.nao" />
												</div>
											</fieldset>
										</div>
									</div>

									<div class="col_20">
										<fieldset>
											<label class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="citcorpore.comum.situacao" />
											</label>
											<div>
												<input type="radio" id="status" name="status" value="A" class="Valid[Required] Description[citcorpore.comum.situacao]" /><fmt:message key="operacaoRest.situacao.ativa" />
												<input type="radio" id="status" name="status" value="I" class="Valid[Required] Description[citcorpore.comum.situacao]" /><fmt:message key="operacaoRest.situacao.inativa" />
											</div>
										</fieldset>
									</div>

									<div class="col_20">
										<fieldset>
											<label class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="operacaoRest.tipoClasse" /></label>
											<div>
												<input type="radio" id="classType" onclick="validaDiv('JavaScript')" name="classType" value="JavaScript" class="Valid[Required] Description[operacaoRest.tipoClasse]" /><fmt:message key="operacaoRest.javaScript" />
												<input type="radio" id="classType" onclick="validaDiv('Java')" name="classType" value="Java" class="Valid[Required] Description[operacaoRest.tipoClasse]" /><fmt:message key="operacaoRest.java" />
											</div>
										</fieldset>
									</div>

									<div class="col_100" id="javaScriptDiv" style="display: none">
										<fieldset>
											<label class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="operacaoRest.javaScript" /></label>
											<div>
												<textarea rows="30" cols="7" name="javaScript" ></textarea>
											</div>
										</fieldset>
									</div>

									<div class="col_60" id="javaDiv" style="display: none">
										<fieldset>
											<label class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="operacaoRest.javaClass" /></label>
											<div>
												<input id="javaClass" type="text" name="javaClass" maxlength="80" />
											</div>
										</fieldset>
									</div>

									<div class="col_80">
										<div class="col_55">
											<h2 class="section">
												<fmt:message key="parametros.parametros" />
											</h2>
										</div>
										<div class="col_20">
											<button type="button" class="light img_icon has_text" onclick="incluirParametro();">
											<fmt:message key="operacaoRest.novoParametro" />
											</button>
										</div>
										<div class="col_25">
											<button type="button" class="light img_icon has_text" id="addParametro">
											<fmt:message key="operacaoRest.cadastrarParametro" />
											</button>
										</div>
									</div>

									<div class="col_80">
										<fieldset>
											<div style="overflow: auto;">
												<cit:grid id="GRID_PARAMETROS" columnHeaders="operacaoRest.cabecalhoGridParametros" styleCells="linhaGrid">
													<cit:column idGrid="GRID_PARAMETROS" number="001">
														<select name="idRestParameter#SEQ#" id="idRestParameter#SEQ#" style="border:none; width: 200px" onchange="verificarParametro('#SEQ#');">
															<option value=""><fmt:message key="citcorpore.comum.selecione" /></option>
															<%
															if (colParametros != null){
																for (RestParameterDTO obj : colParametros) {
																	out.println("<option value='" + obj.getIdRestParameter() + "'>" +
																	obj.getDescription() + "</option>");
																}
															}
															%>
														</select>
													</cit:column>
													<cit:column idGrid="GRID_PARAMETROS" number="002">
														<input id="value#SEQ#" type="text"name="value#SEQ#" />
													</cit:column>
												</cit:grid>
											</div>
										</fieldset>
									</div>

									<div class="col_80">
										<div class="col_80">
											<h2 class="section">
												<fmt:message key="operacaoRest.grupos" />
											</h2>
										</div>
										<div class="col_20">
											<button type="button" id="addGrupo" class="light img_icon has_text">
												<fmt:message key="operacaoRest.novoGrupo" />
											</button>
										</div>
									</div>

									<div class="col_80">
										<div class = "columns clearfix">
											<div id ="divGrupo">
												<table class="table" id="tabelaGrupo">
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 35%;"><fmt:message key="grupo.idgrupo" /></th>
														<th style="width: 85%;"><fmt:message key="controle.grupo" /></th>
													</tr>
												</table>
											</div>
										</div>
									</div>
								</div>
								<br><br>
								<button type="button" name="btnGravar" class="light text_only has_text" onclick="gravar();">
									<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar" /></span>
								</button>
								<button type="button" name="btnLimpar" class="light text_only has_text" onclick="limpar();">
									<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar" /></span>
								</button>
								<div id="popupCadastroRapido">
									<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="99%"></iframe>
								</div>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section"><fmt:message key="citcorpore.comum.pesquisa" />
							<form name="formPesquisa">
								<cit:findField formName="formPesquisa" lockupName="LOOKUP_OPERACAO_REST" id="LOOKUP_OPERACAO_REST" top="0"
									left="0" len="550" heigth="400" javascriptCode="true" htmlCode="true" />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<%@include file="/include/footer.jsp"%>
</body>

<div id="POPUP_PARAMETRO" title="<fmt:message key="parametros.parametro" />">
	<div class="section">
		<form name="formCadastroParam" action="${ctx}/pages/operacaoRest/operacaoRest">
			<div class="col_100">
				<fieldset>
					<label class="campoObrigatorio"><fmt:message key="parametros.identificador" /></label>
					<div>
						<input id="identifier" type="text" name="identifier" class="Valid[Required] Description[parametros.identificador]" />
					</div>
				</fieldset>
			</div>
			<div class="col_100">
				<fieldset>
					<label class="campoObrigatorio"><fmt:message key="operacaoRest.descricao" /></label>
					<div>
						<input id="descriptionParameter" type="text" name="descriptionParameter" class="Valid[Required] Description[operacaoRest.descricao]" />
					</div>
				</fieldset>
			</div><br><br><br><br>
			<button type="button" name="btnGravar" class="light text_only has_text" onclick="gravarParametro();">
				<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
				<span><fmt:message key="citcorpore.comum.gravar" /></span>
			</button>
		</form>
	</div>
</div>

<div id="POPUP_PESQUISA_PARAMETRO" title="<fmt:message key="parametros.parametro" />">
	<div class="box grid_16 tabs" style="width: 570px !important;">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name="formPesquisaParametro" style="width: 540px !important;">
						<cit:findField formName="formPesquisaParametro" lockupName="LOOKUP_PESQUISA_PARAMETRO" id="LOOKUP_PESQUISA_PARAMETRO" top="0"
							left="0" len="1050" heigth="400" javascriptCode="true" htmlCode="true" />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_GRUPO" title="<fmt:message key="citcorpore.comum.pesquisa" />">
	<div class="box grid_16 tabs" style="width: 570px !important;">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name="formPesquisaGrupo" style="width: 540px !important;">
						<cit:findField formName="formPesquisaGrupo" lockupName="LOOKUP_GRUPO_EVENTO" id="LOOKUP_GRUPO_EVENTO" top="0"
							left="0" len="1050" heigth="400" javascriptCode="true" htmlCode="true" />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</html>
