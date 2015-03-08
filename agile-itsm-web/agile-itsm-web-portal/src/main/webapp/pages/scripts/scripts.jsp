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
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<!--<![endif]-->
<title><fmt:message key="citcorpore.comum.title" /></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript" src="./js/scripts.js"></script>

<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
<link rel="stylesheet" type="text/css" href="./css/scripts.css" />
<%}%>
<link rel="stylesheet" type="text/css" href="./css/scripts.css" />
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
	title="Aguarde... Processando..."
	style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

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
					<fmt:message key="scripts.scripts" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="scripts.cadastroScripts" />
					</a>
					</li>
					<li><a href="#tabs-2" class="round_top"><fmt:message key="scripts.pesquisaScripts" />
					</a>
					</li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='${ctx}/pages/scripts/scripts'>
								<input type='hidden' name='idScript' id='idScript' />
								<input type='hidden' name='dataInicio' />
								<input type='hidden' name='dataFim' />
								<div class="columns clearfix">
									<div class="col_50">
										<div class="columns clearfix">
											<div class="col_100">
												<fieldset>
													<label class="campoObrigatorio"><fmt:message key="scripts.nome" /></label>
													<div>
													  <input type='text' name="nome" id="nome" maxlength="70" size="70" class="Valid[Required] Description[scripts.nome]" onchange="descricaoOuQueryAlterada=true;" />
													</div>
												</fieldset>
											</div>
										</div>
										<div class="columns clearfix">
											<div class="col_100">
												<fieldset>
													<label><fmt:message key="scripts.descricao" /></label>
													<div>
													  <textarea name="descricao" cols='70' rows='2' onchange="descricaoOuQueryAlterada=true;"></textarea>
													</div>
												</fieldset>
											</div>
										</div>
										<div class="columns clearfix">
											<div  class="col_100">
												<fieldset>
													<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.tipo"/></label>
													<div >
														<select name='tipo' id="tipo" class="Valid[Required] Description[citcorpore.comum.tipo]" onchange="descricaoOuQueryAlterada=true;"></select>
													</div>
												</fieldset>
											</div>
										</div>
									</div>
									<div class="col_50">
										<fieldset>
											<label><fmt:message key="scripts.historico" /></label>
											<div>
											  <textarea name='historico' id='historico' cols='70' rows='8' readonly="readonly"></textarea>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
								  <div class="col_100">
									<fieldset>
										<label class="campoObrigatorio"><fmt:message key="scripts.sqlQuery" /></label>
										<div>
										  <textarea name="sqlQuery" cols='70' rows='5' class="Valid[Required] Description[scripts.sqlQuery]" onchange="descricaoOuQueryAlterada=true;"></textarea>
										</div>
									</fieldset>
									</div>
								</div>
								<br> <br>
								<button type='button' name='btnGravar' class="light" onclick='document.form.save();'>
									<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar" /></span>
								</button>
								<button type='button' name='btnLimpar' class="light" onclick='document.form.clear();'>
									<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar" /></span>
								</button>
								<button type='button' name='btnExcluir' id="btnExcluir" class="light" onclick='excluir();'>
									<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
									<span><fmt:message key="citcorpore.comum.excluir" /></span>
								</button>
								<button type='button' name='btnExecutar' id="btnExecutar" class="light" onclick='executar();'>
									<img src="${ctx}/template_new/images/icons/small/grey/cog_2.png">
									<span><fmt:message key="scripts.executar" /></span>
								</button>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<fmt:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField
									formName='formPesquisa'
									lockupName='LOOKUP_SCRIPTS'
									id='LOOKUP_SCRIPTS'
									top='0'
									left='0'
									len='550'
									heigth='400'
									javascriptCode='true'
									htmlCode='true'
								/>
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<div id="POPUP_RESULTADO_CONSULTA" title="<fmt:message key='scripts.resultadoConsulta' />">
		<div id='headerResultadoConsulta'></div>
		<div id='contentResultadoConsulta'></div>

	</div>
	<div id="POPUP_MENSAGEM_FALTA_PERMISSAO" title="<fmt:message key='citcorpore.comum.seguintesItensNaoTratados' />">
		<div id='divPopupVerificacaoPermissoes'></div>
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>



