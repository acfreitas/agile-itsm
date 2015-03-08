<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="/include/header.jsp"%>

	<%@include file="/include/security/security.jsp"%>

	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
	<title><fmt:message key="citcorpore.comum.title" /></title>
	<script type="text/javascript" src="./js/categoriaServico.js"></script>
</head>
<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>

			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="categoriaServico.categoriaServico" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message	key="categoriaServico.cadastroCategoriaServico" /></a></li>
					<li><a href="#tabs-2" class="round_top"><fmt:message key="categoriaServico.pesquisaCategoriaServico" />	</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='${ctx}/pages/categoriaServico/categoriaServico'>
								<div class="columns clearfix">
									<input type='hidden' name='idCategoriaServico' />
									<input type='hidden' name='idEmpresa' id="idEmpresa" />
										<input type='hidden' name='dataInicio'  id="dataInicio"/>
										<input type='hidden' name='dataFim' />
										<input type='hidden' name='idCategoriaServicoPai' /> <input
										type='hidden' name='idEmpresa' />
									<div class="col_66">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.nome" /></label>
											<div>
												<input type='text' name="nomeCategoriaServico" maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset style="height: 61px">
											<label><fmt:message key="categoriaServico.categoriaServicoPai" /></label>
											<div>
												<!-- <select name="idCategoriaServicoPai"></select> -->
											<div>
												<input onclick="consultarCategoriaServicoSuperior()" readonly="readonly" style="width: 90% !important;" type='text' name="nomeCategoriaServicoPai" maxlength="70" size="70"  />
												<img onclick="consultarCategoriaServicoSuperior()" style=" vertical-align: middle;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
											</div>
											</div>
										</fieldset>
									</div>
									<div class="col_66">
										<fieldset>
											<label ><fmt:message key="categoriaServico.hierarquia" /></label>
											<div>
												<input type='text' readonly="readonly" name="nomeCategoriaServicoConcatenado" maxlength="1024" />
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
									onclick='document.form.clear();document.form.fireEvent("load");'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type='button' name='btnExcluir' class="light"
									onclick='atualizaData();'>
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
								<cit:findField formName='formPesquisa'
									lockupName='LOOKUP_CATEGORIASERVICO'
									id='LOOKUP_CATEGORIASERVICO' top='0' left='0' len='550'
									heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<div id="POPUP_CATEGORIASERVICO_SUPERIOR" title="<fmt:message key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs" style="width: auto !important">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section" style="padding: 33px; width: 100% !important;">
						<form name='formCategoriaServicoSuperior'>
							<cit:findField formName='formCategoriaServicoSuperior'
							lockupName='LOOKUP_CATEGORIASERVICO_SUPERIOR'
							id='LOOKUP_CATEGORIASERVICO_SUPERIOR' top='0' left='0' len='550' heigth='400'
							javascriptCode='true'
							htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>


