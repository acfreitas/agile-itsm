<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>

<title><fmt:message key="citcorpore.comum.title"/></title>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript" src="./js/prioridade.js"></script>
</head>
<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>

			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="prioridade.prioridade" />
				</h2>
			</div>

			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message
								key="prioridade.cadastroPrioridade" />
					</a></li>
					<li><a href="#tabs-2" class="round_top"><fmt:message
								key="prioridade.pesquisaPrioridade" />
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='${ctx}/pages/prioridade/prioridade'>
								<input type='hidden' name='idPrioridade' />
								<input type='hidden'name='idEmpresa' />
								<input type="hidden" name="situacao" />
								<div class="columns clearfix">
									<div class="col_66">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="prioridade.nomePrioridade" /></label>
											<div>
												<input type='text' name="nomePrioridade" maxlength="100"
													class="Valid[Required] Description[prioridade.nomePrioridade]" />
											</div>
										</fieldset>
									</div>

									<div class="col_33">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="prioridade.grupoPrioridade" /></label>
											<div>
												<select name="grupoPrioridade" id="grupoPrioridade"
													class="Valid[Required] Description[prioridade.grupoPrioridade]"></select>
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
								<button type='button' name='btnExcluir' class="light"
									onclick='alterarSituacao();'>
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
									lockupName='LOOKUP_PRIORIDADE' id='LOOKUP_PRIORIDADE' top='0'
									left='0' len='550' heigth='400' javascriptCode='true'
									htmlCode='true' />
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


