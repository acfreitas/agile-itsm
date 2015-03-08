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
<%@include file="/include/security/security.jsp"%>
<%@include file="/include/header.jsp"%>

<title><fmt:message key="citcorpore.comum.title"/></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript" src="./js/tipoMudanca.js"></script>
<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
<link rel="stylesheet" type="text/css" href="./css/tipoMudanca.css" />
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
					<fmt:message key="tipoMudanca.tipoDeMudanca" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="tipoMudanca.cadastro" />
					</a></li>
					<li><a href="#tabs-2" class="round_top"><fmt:message key="tipoMudanca.pesquisa" />
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='${ctx}/pages/tipoMudanca/tipoMudanca'>
								<input type='hidden' name='idTipoMudanca' />
								<input type='hidden' name='dataInicio' id="dataInicio" />
								<input type='hidden' name='dataFim' id="dataFim" />
								<div class="columns clearfix">
									<div class="col_40">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="tipoMudanca.nome" /></label>
											<div>
												<input type='text' maxlength="100"  name="nomeTipoMudanca" id="nomeTipoMudanca" class="Valid[Required] Description[tipoMudanca.nome]" />
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="tipoMudanca.nomeFluxo" /></label>
											<div>
												<select name="idTipoFluxo" id="idTipoFluxo"
													class="Valid[Required] Description[tipoMudanca.nomeFluxo]">
													</select>
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="solicitacaoServico.impacto" /></label>
										<div>
										<select  id="impacto" name="impacto">
											<option value="B"><fmt:message key="citcorpore.comum.baixa"/></option>
											<option value="M"><fmt:message key="citcorpore.comum.media"/></option>
											<option value="A"><fmt:message key="citcorpore.comum.alta"/></option>
										</select>
										</div>
										</fieldset>
										</div>

									<div class="col_20" >
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="solicitacaoServico.urgencia"/></label>
											<div>
												<select  id="urgencia" name="urgencia">
													<option value="B"><fmt:message key="citcorpore.comum.baixa"/></option>
													<option value="M"><fmt:message key="citcorpore.comum.media"/></option>
													<option value="A"><fmt:message key="citcorpore.comum.alta"/></option>
												</select>
											</div>
										</fieldset>
									</div>
									</div>
									<div class="col_100">
									<div class="col_20">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="tipoMudanca.nomeModeloEmailCriacao" /></label>
											<div>
												<select name="idModeloEmailCriacao" id="idModeloEmailCriacao"
													class="Valid[Required] Description[tipoMudanca.nomeModeloEmailCriacao]">
												</select>
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="tipoMudanca.nomeModeloEmailFinalizacao" /></label>
											<div>
												<select name="idModeloEmailFinalizacao" id="idModeloEmailFinalizacao"
													class="Valid[Required] Description[tipoMudanca.nomeModeloEmailFinalizacao]">
												</select>
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="tipoMudanca.nomeModeloEmailAcoes" /></label>
											<div>
												<select name="idModeloEmailAcoes" id="idModeloEmailAcoes"
													class="Valid[Required] Description[tipoMudanca.nomeModeloEmailAcoes]">
												</select>
											</div>
										</fieldset>
									</div>

									<div class="col_20">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="tipoMudanca.nomeGrupoExecutor" /></label>
											<div>
												<select name="idGrupoExecutor" id="idGrupoExecutor"
													class="Valid[Required] Description[tipoMudanca.nomeGrupoExecutor]">
												</select>
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="tipoMudanca.nomeCalendario" /></label>
											<div>
												<select name="idCalendario" id="idCalendario"
													class="Valid[Required] Description[tipoMudanca.nomeCalendario]">
												</select>
											</div>
										</fieldset>
									</div>

								</div>
								<div>
									<fieldset>
										<label style='cursor:pointer'><input type='checkbox' value='S' name='exigeAprovacao'/><fmt:message key="requisicaoMudanca.exigeAprovacao" /></label>
									</fieldset>
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
								<cit:findField formName='formPesquisa'lockupName='LOOKUP_TIPOMUDANCA' id='LOOKUP_TIPOMUDANCA' top='0'left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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

