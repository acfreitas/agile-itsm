<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>

<!doctype html public "">
<html>
	<head>
		<%@include file="/include/header.jsp"%>

		<%@include file="/include/security/security.jsp" %>
		<title><fmt:message key="citcorpore.comum.title"/></title>

		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

		<style type="text/css">
			.table {
			border-left:1px solid #ddd;
			}

			.table th {
			border:1px solid #ddd;
			padding:4px 10px;
			border-left:none;
			background:#eee;
			}

			.table td {
			border:1px solid #ddd;
			padding:4px 10px;
			border-top:none;
			border-left:none;
			}
		</style>

		<script type="text/javascript">

			addEvent(window, "load", load, false);

			var installDesabilitado = false;
			var desinstallDesabilitado = false;

			function limpar() {
				installDesabilitado = false;
				desinstallDesabilitado = false;
				document.getElementById('btnUpDate').style.display = 'none';
				limparTodosCampos();
				resetarTodosCheckbox();
				exibirUnicaDivExecucao('divExecucaoInstalacao');
	    		document.getElementById('tipoExecucaoDesinstalacao').removeAttribute('disabled');
	    		document.getElementById('tipoExecucaoInstalacao').removeAttribute('disabled');
				document.getElementById('id').value = '';
				document.getElementById('dataInicio').value = '';
			}

			function limparTelaDesinstalacao() {
				limparCamposDesinstalacao();
				resetarCheckboxDesinstalacao();
				exibirUnicaDivExecucao('divExecucaoInstalacao');
			}

			function limparTelaInstalacao() {
				limparCamposInstalacao();
	    		document.getElementById('tipoExecucaoInstalacao').checked = false;
				document.getElementById('divExecucaoInstalacao').style.display = 'none';
	    		document.getElementById('comandosIguais').disabled = 'disabled';
	    		document.getElementById('comandosIguais').checked = 'checked';
				exibirUnicaDivExecucao('divExecucaoDesinstalacao');

			}

			function limparCamposInstalacao() {
				document.getElementById('executavelInstalacao').value = "";
				document.getElementById('comandoInstalacao').value = "";
			}

			function limparCamposDesinstalacao() {
				document.getElementById('executavelDesinstalacao').value = "";
				document.getElementById('comandoDesinstalacao').value = "";
			}

			function limparTodosCampos() {
				document.getElementById('nome').value = "";
				limparCamposInstalacao();
				limparCamposDesinstalacao();
			}

			function resetarCheckboxInstalacao() {
	    		document.getElementById('tipoExecucaoInstalacao').checked = 'checked';
				document.getElementById('divExecucaoInstalacao').style.display = 'block';
			}

			function resetarCheckboxDesinstalacao() {
	    		document.getElementById('tipoExecucaoDesinstalacao').checked = false;
				document.getElementById('divExecucaoDesinstalacao').style.display = 'none';
				document.getElementById('comandosIguais').removeAttribute('disabled');
				document.getElementById('comandosIguais').checked = false;
		    	document.getElementById('divComandoDesinstalacao').style.display = 'none';
		    	document.getElementById('labelComandoDesinstalacao').style.display = 'none';
			}

			function resetarTodosCheckbox() {
				resetarCheckboxInstalacao();
				resetarCheckboxDesinstalacao();
			}

			function esconderInstalacao() {
	    		document.getElementById('tipoExecucaoInstalacao').checked = false;
				document.getElementById('divExecucaoInstalacao').style.display = 'none';
			}

			function checarExecucaoInstalacao(e) {
			    if (e.target.checked) {
			    	document.getElementById('divExecucaoInstalacao').style.display = 'block';
			    	if (document.getElementById('divExecucaoDesinstalacao').style.display == 'block') {
			    		document.getElementById('comandosIguais').removeAttribute('disabled');
			    		exibirTodasDivsExecucao();
			    	} else {
			    		exibirUnicaDivExecucao('divExecucaoInstalacao');
			    	}
			    } else {
			    	document.getElementById('divExecucaoInstalacao').style.display = 'none';
			    	if (document.getElementById('divExecucaoDesinstalacao').style.display == 'block') {
			    		exibirSomenteDivDesinstalacao();
			    	}
			    }
			}

			function checarExecucaoDesinstalacao(e) {
			    if (e.target.checked) {
			    	document.getElementById('divExecucaoDesinstalacao').style.display = 'block';
			    	if (document.getElementById('divExecucaoInstalacao').style.display == 'block') {
			    		document.getElementById('comandosIguais').removeAttribute('disabled');
			    		exibirTodasDivsExecucao();
			    	} else {
			    		exibirSomenteDivDesinstalacao();
			    	}
			    } else {
			    	document.getElementById('divExecucaoDesinstalacao').style.display = 'none';
			    	if (document.getElementById('divExecucaoInstalacao').style.display == 'block') {
			    		exibirUnicaDivExecucao('divExecucaoInstalacao');
			    	}
			    }
			}

			function exibirSomenteDivDesinstalacao() {
	    		document.getElementById('comandosIguais').disabled = 'disabled';
	    		document.getElementById('comandosIguais').checked = 'checked';
		    	document.getElementById('divComandoDesinstalacao').style.display = 'block';
		    	document.getElementById('labelComandoDesinstalacao').style.display = 'block';
	    		exibirUnicaDivExecucao('divExecucaoDesinstalacao');
			}

			function exibirTodasDivsExecucao() {
	    		document.getElementById('divExecucaoDesinstalacao').setAttribute('class', 'col_50');
	    		document.getElementById('divExecucaoInstalacao').setAttribute('class', 'col_50');
			}

			function exibirUnicaDivExecucao(div) {
	    		document.getElementById(div).setAttribute('class', 'col_100');
			}

			function checarComandosIguais(e) {
			    if (e.target.checked) {
			    	document.getElementById('divComandoDesinstalacao').style.display = 'block';
			    	document.getElementById('labelComandoDesinstalacao').style.display = 'block';
			    } else {
			    	document.getElementById('divComandoDesinstalacao').style.display = 'none';
			    	document.getElementById('labelComandoDesinstalacao').style.display = 'none';
			    }
			}

			function restorePai(idBase, dataInicio, nomeSoftware) {
				document.getElementById('btnUpDate').style.display = 'block';
				document.getElementById('id').value = idBase;
				document.getElementById('dataInicio').value = dataInicio;
				document.getElementById('nome').value = nomeSoftware;
			}

			function restoreInstalacao(executavel, comando) {
				installDesabilitado = true;
	    		document.getElementById('tipoExecucaoInstalacao').disabled = 'disabled';
				document.getElementById('executavelInstalacao').value = executavel;
				document.getElementById('comandoInstalacao').value = comando;
			}

			function restoreDesinstalacao(executavel, comando, comandosDiferentes) {
				desinstallDesabilitado = true;
	    		document.getElementById('tipoExecucaoDesinstalacao').disabled = 'disabled';
				document.getElementById('executavelDesinstalacao').value = executavel;
				document.getElementById('comandoDesinstalacao').value = comando;
				document.getElementById('tipoExecucaoDesinstalacao').checked = 'checked';
		    	document.getElementById('divExecucaoDesinstalacao').style.display = 'block';
		    	if (comandosDiferentes) {
		    		document.getElementById('comandosIguais').checked = 'checked';
			    	document.getElementById('divComandoDesinstalacao').style.display = 'block';
			    	document.getElementById('labelComandoDesinstalacao').style.display = 'block';
		    	}
			}

			function load() {
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				}
			}

			function desabilitarChecks() {
				if (desinstallDesabilitado) {
		    		document.getElementById('tipoExecucaoDesinstalacao').disabled = 'disabled';
				}
				if (installDesabilitado) {
		    		document.getElementById('tipoExecucaoInstalacao').disabled = 'disabled';
				}
			}

			function update() {
				if (document.getElementById("id").value != "") {
					if (confirm(i18n_message("citcorpore.comum.deleta"))) {
						document.form.fireEvent("excluirTipoItemConfiguracao");
					}
				}

			}

			function atualizaData() {
				if (document.getElementById("id") != null && document.getElementById("id").value == 0) {
					alert(i18n_message("citcorpore.comum.necessarioSelecionarRegistro"));
					return false;
				}
				if (confirm(i18n_message("citcorpore.comum.deleta")))
					document.form.fireEvent("atualizaData");
			}

			function LOOKUP_BASEITEMCONFIGURACAO_select(idBaseItem, desc) {
				document.form.restore( {
					id : idBaseItem
				});
			}

			var seqSelecionada = '';

			function gravar() {

				if (validarDados()) {
		    		document.getElementById('tipoExecucaoInstalacao').removeAttribute('disabled');
		    		document.getElementById('tipoExecucaoDesinstalacao').removeAttribute('disabled');
					document.form.save();
				}

			}

			function validarDados() {
				if (!validarInput("nome", "baseItemConfiguracao.mensagens.nomeObrigatorio")) {
					return false;
				}
				if (document.getElementById("tipoExecucaoInstalacao").checked == false
						&& document.getElementById("tipoExecucaoDesinstalacao").checked == false) {
					alert(i18n_message("baseItemConfiguracao.mensagens.tipoExecucaoObrigatorio"));
					return false;
				}
				if (document.getElementById("tipoExecucaoInstalacao").checked == true
						&& !validarInput("executavelInstalacao", "baseItemConfiguracao.mensagens.executavelObrigatorio")) {
					return false;
				}
				if (document.getElementById("tipoExecucaoDesinstalacao").checked == true
						&& !validarInput("executavelDesinstalacao", "baseItemConfiguracao.mensagens.executavelDesinstalacaoObrigatorio")) {
					return false;
				}
				return true;
			}

			function validarInput(idInput, keyMensagem) {
				if (document.getElementById(idInput).value == "") {
					alert(i18n_message(keyMensagem));
					setFocus(idInput);
					return false;
				}
				return true;
			}

			function setFocus(idElemento) {
				document.getElementById(idElemento).focus();
			}

		</script>
	</head>
	<body>
		<script type="text/javascript" src="../../cit/objects/CaracteristicaDTO.js"></script>
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>

				<div class="flat_area grid_16">
					<h2><fmt:message key="baseItemConfiguracao.software"/></h2>
				</div>

				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><fmt:message key="baseItemConfiguracao.cadastro"/></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top"><fmt:message key="baseItemConfiguracao.pesquisa"/></a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name='form' action='${ctx}/pages/baseItemConfiguracao/baseItemConfiguracao'>
									<div class="columns clearfix">
										<input type='hidden' id="id" name='id'/>
										<input type='hidden' name='idTipoItemConfiguracao'/>
										<input type='hidden' name='dataInicio' id="dataInicio"/>

										<div class="columns clearfix">
											<div class="col_100">
												<fieldset>
													<label class="campoObrigatorio"><fmt:message key="baseItemConfiguracao.software"/></label>
														<div>
														  	<input type='text' name="nome" id="nome" maxlength="70" size="70" class="Valid[Required] Description[Nome do Software]" />
														</div>
												</fieldset>
											</div>
											<div class="col_100">
												<fieldset>
													<label class="campoObrigatorio"><fmt:message key="baseItemConfiguracao.tipoExecucao"/></label>
													<div class="columns clearfix" style="border: none;">
														<div class="col_50" style="border: none;">
													  		<input type="checkbox" name="tipoExecucaoInstalacao" id="tipoExecucaoInstalacao" checked="checked" onclick="checarExecucaoInstalacao(event)" /><fmt:message key="baseItemConfiguracao.instalacao"/>
												  		</div>
													  	<div class="col_50">
													  		<input type="checkbox" name="tipoExecucaoDesinstalacao" id="tipoExecucaoDesinstalacao" onclick="checarExecucaoDesinstalacao(event)" /><fmt:message key="baseItemConfiguracao.desinstalacao"/>
												  		</div>
													</div>
												</fieldset>
											</div>

											<div class="col_100" id="divExecucaoInstalacao" style="display: block;">
												<div class="columns clearfix" style="border: none;">
													<div class="col_100">
														<fieldset>
															<label class="campoObrigatorio"><fmt:message key="baseItemConfiguracao.executavel"/></label>
																<div>
																  	<input type='text' name="executavelInstalacao" id="executavelInstalacao" maxlength="70" size="70" />
																</div>
														</fieldset>
													</div>
													<div class="col_100">
														<fieldset style="height: 78px;">
															<label><fmt:message key="baseItemConfiguracao.comando"/></label>
																<div>
																  	<input type='text' name="comandoInstalacao" maxlength="70" size="70"  />
																</div>
														</fieldset>
													</div>

												</div>
											</div>

											<div class="col_50" id="divExecucaoDesinstalacao" style="display: none;">
												<div class="columns clearfix" style="border: none;">
													<div class="col_100">
														<fieldset>
															<label class="campoObrigatorio"><fmt:message key="baseItemConfiguracao.executavelDesinstalacao"/></label>
																<div>
																  	<input type='text' name="executavelDesinstalacao" id="executavelDesinstalacao" maxlength="70" size="70" />
																</div>
														</fieldset>
													</div>
													<div class="col_100" id="divComandosIguais">
														<fieldset style="border: none;">
															<div>
															  	<input type='checkbox' name="comandosIguais" id="comandosIguais" onclick="checarComandosIguais(event);" /><fmt:message key="baseItemConfiguracao.comandosIguais"/>
															</div>
														</fieldset>
													</div>
													<div class="col_100" >
														<fieldset style="border-top: none; height: 55px;">
															<label style="display: none;" id="labelComandoDesinstalacao"><fmt:message key="baseItemConfiguracao.comandoDesinstalacao"/></label>
																<div style="display: none;" id="divComandoDesinstalacao">
																  	<input type='text' name="comandoDesinstalacao" maxlength="70" size="70"  />
																</div>
														</fieldset>
													</div>

												</div>
											</div>
											<div class="columns clearfix">
											</div>
											<br>
										</div>
										<br><br>
										<button type='button' name='btnGravar' class="light"  onclick='gravar();'>
											<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
											<span><fmt:message key="citcorpore.comum.gravar"/></span>
										</button>
										<button type='button' name='btnLimpar' class="light" onclick='limpar();'>
											<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
											<span><fmt:message key="citcorpore.comum.limpar"/></span>
										</button>
										<button type='button' name='btnUpDate' class="light" onclick='atualizaData();' style="display: none;" id="btnUpDate">
											<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
											<span><fmt:message key="citcorpore.comum.excluir"/></span>
										</button>
									</div>
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section"><fmt:message key="citcorpore.comum.pesquisa"/>
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa'
													lockupName='LOOKUP_BASEITEMCONFIGURACAO'
													id='LOOKUP_BASEITEMCONFIGURACAO' top='0' left='0' len='550' heigth='400'
													javascriptCode='true'
													htmlCode='true' />
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