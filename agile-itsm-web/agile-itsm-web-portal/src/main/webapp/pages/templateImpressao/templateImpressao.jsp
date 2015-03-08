<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.CaracteristicaDTO"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@include file="/include/header.jsp"%>

		<%@include file="/include/security/security.jsp" %>
		<title><fmt:message key="citcorpore.comum.title"/></title>

		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

    	<script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>
		<script type="text/javascript">

			var objTab = null;

			addEvent(window, "load", load, false);

			function load() {
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				}
			}

			function LOOKUP_TEMPLATEIMPRESSAO_select(id, desc) {
				document.form.restore( {
					idTemplateImpressao:id
				});
			}

	        function onInitQuestionario(){
	        }
	        HTMLUtils.addEvent(window, "load", onInitQuestionario, false);

			function setDataEditor(){
			}

			function limpar(){
				document.form.clear();
			}

			function salvar(){
				document.form.save();
			}

		</script>

	</head>
	<body>
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>

				<div class="flat_area grid_16">
					<h2>Template de Impressão</h2>
				</div>

				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><fmt:message key="citcorpore.ui.aba.titulo.Cadastro"/></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top"><fmt:message key="citcorpore.ui.aba.titulo.Pesquisa"/></a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name="form" action='${ctx}/pages/templateImpressao/templateImpressao'>
									<div class="columns clearfix">
										<input type='hidden' name='idTemplateImpressao'/>

										<div class="columns clearfix">
											<div class="col_100">
												<fieldset>
													<label class="campoObrigatorio" ><fmt:message key="baseConhecimento.titulo"/></label>
														<div>
														  	<input type='text' name="nomeTemplate" maxlength="60" size="65" class="Valid[Required] Description[baseConhecimento.titulo]" />
														</div>
												</fieldset>
											</div>
										</div>

										<div class="columns clearfix">
											<div class="col_100">
												<fieldset>
													<label><fmt:message key="templateProjeto.cabecalho"/></label>
														<div>
														  	<textarea id="htmlCabecalho" name="htmlCabecalho" rows="8" cols="80" style="display: block;"></textarea>
														</div>
												</fieldset>
											</div>
										</div>

										<div class="columns clearfix">
											<div class="col_100">
												<fieldset>
													<label><fmt:message key="templateProjeto.corpo"/></label>
														<div>
														  	<textarea id="htmlCorpo" name="htmlCorpo" rows="8" cols="80" style="display: block;"></textarea>
														</div>
												</fieldset>
											</div>
										</div>

										<div class="columns clearfix">
											<div class="col_100">
												<fieldset>
													<label><fmt:message key="templateProjeto.rodape"/></label>
														<div>
														  	<textarea id="htmlRodape" name="htmlRodape" rows="8" cols="80" style="display: block;"></textarea>
														</div>
												</fieldset>
											</div>
										</div>

										<div class="columns clearfix">
											<div class="col_50">
												<fieldset>
													<label><fmt:message key="templateProjeto.tamanhoCabecalho"/></label>
														<div>
														  	<input type='text' size="4" maxlength="4" name="tamCabecalho" id="tamCabecalho" class="Format[Number]"/>
														</div>
												</fieldset>
											</div>
											<div class="col_50">
												<fieldset>
													<label><fmt:message key="templateProjeto.tamanhoRodape"/></label>
														<div>
														  	<input type='text' size="4" maxlength="4" name="tamRodape" id="tamRodape" class="Format[Number]"/>
														</div>
												</fieldset>
											</div>
										</div>
									</div>

									<button type='button' name='btnGravar' class="light"  onclick='salvar();'>
										<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar"/></span>
									</button>
									<button type='button' name='btnLimpar' class="light" onclick='limpar();'>
										<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="citcorpore.comum.limpar"/></span>
									</button>
								</form>
							</div>
						</div>

						<div id="tabs-2" class="block">
							<div class="section"><fmt:message key="citcorpore.comum.pesquisa"/>
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa' lockupName='LOOKUP_TEMPLATEIMPRESSAO' id='LOOKUP_TEMPLATEIMPRESSAO'  top='0' left='0' len='550' heigth='400'
													javascriptCode='true' htmlCode='true' />
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