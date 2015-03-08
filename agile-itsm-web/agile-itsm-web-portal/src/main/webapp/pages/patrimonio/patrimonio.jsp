<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.CaracteristicaDTO"%>

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
			var objTab = null;

			addEvent(window, "load", load, false);

			function load() {
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				}
			}

			function update() {
				if (document.getElementById("idItemConfiguracao").value != "") {
					if (confirm(i18n_message("citcorpore.comum.deleta"))) {
						document.form.fireEvent("delete");
					}
				}
			}

			var countCaracteristica = 0;
			function insereRow(id, desc) {
				var tabela = document.getElementById('tabelaCaracteristica');
				var lastRow = tabela.rows.length;

				var row = tabela.insertRow(lastRow);
				countCaracteristica++;

				var valor = desc.split(' - ');

				var coluna = row.insertCell(0);
				coluna.innerHTML = '<input type="hidden" id="imgExcluiCaracteristica"/>';

				coluna = row.insertCell(1);
				coluna.innerHTML = valor[0] + '<input type="hidden" id="idCaracteristica'
						+ countCaracteristica + '" name="idCaracteristica" value="' + id
						+ '" />';

				coluna = row.insertCell(2);
				coluna.innerHTML = valor[1];

				coluna = row.insertCell(3);
				coluna.innerHTML = valor[2]
			}

			function insereRowCaracteristicaItemFilho(id, desc) {
				var tabela = document.getElementById('tabelaCaracteristicaItemFilho');
				var lastRow = tabela.rows.length;

				var row = tabela.insertRow(lastRow);
				countCaracteristica++;

				var valor = desc.split(' - ');

				var coluna = row.insertCell(0);
				coluna.innerHTML = '<input type="hidden" id="imgExcluiCaracteristica"/>';

				coluna = row.insertCell(1);
				coluna.innerHTML = valor[0] + '<input type="hidden" id="idCaracteristicaItemFilho'
						+ countCaracteristica + '" name="idCaracteristicaItemFilho" value="' + id
						+ '" />';

				coluna = row.insertCell(2);
				coluna.innerHTML = valor[1];

				coluna = row.insertCell(3);
				coluna.innerHTML = valor[2]
			}

			function restoreRow() {
				var tabela = document.getElementById('tabelaCaracteristica');
				var lastRow = tabela.rows.length;

				var row = tabela.insertRow(lastRow);
				countCaracteristica++;

				var coluna = row.insertCell(0);
				coluna.innerHTML = '<input type="hidden" id="imgExcluiCaracteristica"/>';

				coluna = row.insertCell(1);
				coluna.innerHTML = '<input type="hidden" id="idCaracteristica'
						+ countCaracteristica + '" name="idCaracteristica"/><input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="caracteristica'
						+ countCaracteristica + '" name="caracteristica"/>';

				coluna = row.insertCell(2);
				coluna.innerHTML = '<input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="tagCaracteristica'
						+ countCaracteristica + '" name="tagCaracteristica"/>';

				coluna = row.insertCell(3);
				coluna.innerHTML = '<input style="width: 100%; border: 1 none;" type="text" id="valorString' + countCaracteristica + '" name="valorString"/>';

				coluna = row.insertCell(4);
				coluna.innerHTML = '<input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="descricao'
						+ countCaracteristica + '" name="descricao"/>';
			}

			function restoreRowCaracteristicaItemFilho() {
				var tabela = document.getElementById('tabelaCaracteristicaItemFilho');
				var lastRow = tabela.rows.length;

				var row = tabela.insertRow(lastRow);
				countCaracteristica++;

				var coluna = row.insertCell(0);
				coluna.innerHTML = '<input type="hidden" id="imgExcluiCaracteristica"/>';

				coluna = row.insertCell(1);
				coluna.innerHTML = '<input type="hidden" id="idCaracteristicaItemFilho'
						+ countCaracteristica + '" name="idCaracteristicaItemFilho"/><input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="caracteristicaItemFilho'
						+ countCaracteristica + '" name="caracteristicaItemFilho"/>';

				coluna = row.insertCell(2);
				coluna.innerHTML = '<input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="tagCaracteristicaItemFilho'
						+ countCaracteristica + '" name="tagCaracteristicaItemFilho"/>';

				coluna = row.insertCell(3);
				coluna.innerHTML = '<input style="width: 100%; border: 1 none;" type="text" id="valorStringItemFilho' + countCaracteristica + '" name="valorStringItemFilho"/>';

				coluna = row.insertCell(4);
				coluna.innerHTML = '<input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="descricaoItemFilho'
						+ countCaracteristica + '" name="descricaoItemFilho"/>';
			}

			var seqSelecionada = '';
			function setRestoreCaracteristica(idCaracteristica, caracteristica, tag, valorString, descricao, idEmpresa, dataInicio, dataFim) {
				if (seqSelecionada != '') {
					eval('document.form.idCaracteristica' + seqSelecionada + '.value = "' + idCaracteristica + '"');
					eval('document.form.caracteristica' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + caracteristica + '\')');
					eval('document.form.tagCaracteristica' + seqSelecionada	+ '.value = ObjectUtils.decodificaEnter(\'' + tag + '\')');
					eval('document.form.valorString' + seqSelecionada	+ '.value = ObjectUtils.decodificaEnter(\'' + valorString + '\')');
					eval('document.form.descricao' + seqSelecionada	+ '.value = ObjectUtils.decodificaEnter(\'' + descricao + '\')');
				}
				exibeGrid();
			}

			function setRestoreCaracteristicaItemConfiguracaoFilho(idCaracteristica, caracteristica, tag, valorString, descricao, idEmpresa, dataInicio, dataFim) {
				if (seqSelecionada != '') {
					eval('document.form.idCaracteristicaItemFilho' + seqSelecionada + '.value = "' + idCaracteristica + '"');
					eval('document.form.caracteristicaItemFilho' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + caracteristica + '\')');
					eval('document.form.tagCaracteristicaItemFilho' + seqSelecionada	+ '.value = ObjectUtils.decodificaEnter(\'' + tag + '\')');
					eval('document.form.valorStringItemFilho' + seqSelecionada	+ '.value = ObjectUtils.decodificaEnter(\'' + valorString + '\')');
					eval('document.form.descricaoItemFilho' + seqSelecionada	+ '.value = ObjectUtils.decodificaEnter(\'' + descricao + '\')');
				}
				exibeGrid();
			}

			function deleteAllRows() {
				var tabela = document.getElementById('tabelaCaracteristica');
				var count = tabela.rows.length;

				while (count > 1) {
					tabela.deleteRow(count - 1);
					count--;
				}
				ocultaGrid();
			}

			function deleteAllRowsItemFilho() {
				var tabela = document.getElementById('tabelaCaracteristicaItemFilho');
				var count = tabela.rows.length;

				while (count > 1) {
					tabela.deleteRow(count - 1);
					count--;
				}
				ocultaGrid();
			}

			function gravar() {
				var tabela = document.getElementById('tabelaCaracteristica');

				var tabela2 = document.getElementById('tabelaCaracteristicaItemFilho');

				var count = tabela.rows.length;
				var count2 = tabela2.rows.length;

				var contadorAux = 0;
				var contadorAuxItemFilho = 0;
				var caracteristicas = new Array();
				var caracteristicasItemFilho = new Array();

				for ( var i = 1; i <= count; i++) {
					var trObj = document.getElementById('idCaracteristica' + i);

					if (!trObj) {
						continue;
					}

					caracteristicas[contadorAux] = getCaracteristica(i);
					contadorAux = contadorAux + 1;
				}

				for ( var i = 1; i <= count2; i++) {
					var trObj = document.getElementById('idCaracteristicaItemFilho' + i);

					if (!trObj) {
						continue;
					}

					caracteristicasItemFilho[contadorAuxItemFilho] = getCaracteristicaItemFilho(i);
					contadorAuxItemFilho = contadorAuxItemFilho + 1;
				}

				serializa();
				serializaItemFilho();

				document.form.save();
			}

			serializa = function() {

				var tabela = document.getElementById('tabelaCaracteristica');

				var count = tabela.rows.length;

				var contadorAux = 0;

				var caracteristicas = new Array();

				for ( var i = 1; i <= count; i++) {

					var trObj = document.getElementById('idCaracteristica' + i);

					if (!trObj) {

						continue;

					}

					caracteristicas[contadorAux] = getCaracteristica(i);
					contadorAux = contadorAux + 1;
				}

				var caracteristicasSerializadas = ObjectUtils.serializeObjects(caracteristicas);

				document.form.caracteristicasSerializadas.value = caracteristicasSerializadas;

				return true;
			}

			serializaItemFilho = function() {

				var tabela = document.getElementById('tabelaCaracteristicaItemFilho');

				var count = tabela.rows.length;

				var contadorAux = 0;

				var caracteristicasItemFilho = new Array();

				for ( var i = 1; i <= count; i++) {

					var trObj = document.getElementById('idCaracteristicaItemFilho' + i);

					if (!trObj) {

						continue;

					}

					caracteristicasItemFilho[contadorAux] = getCaracteristicaItemFilho(i);

					contadorAux = contadorAux + 1;

				}

				var caracteristicasSerializadasItemFilho = ObjectUtils.serializeObjects(caracteristicasItemFilho);

				document.form.caracteristicasSerializadasItemFilho.value = caracteristicasSerializadasItemFilho;

				return true;
			}

			getCaracteristica = function(seq) {
				var CaracteristicaDTO = new CIT_CaracteristicaDTO();
				CaracteristicaDTO.sequencia = seq;
				CaracteristicaDTO.idCaracteristica = eval('document.form.idCaracteristica' + seq + '.value');
				CaracteristicaDTO.valorString = eval('document.form.valorString' + seq + '.value');
				return CaracteristicaDTO;
			}

			getCaracteristicaItemFilho = function(seq) {
				var CaracteristicaDTO = new CIT_CaracteristicaDTO();
				CaracteristicaDTO.sequencia = seq;
				CaracteristicaDTO.idCaracteristica = eval('document.form.idCaracteristicaItemFilho' + seq + '.value');
				CaracteristicaDTO.valorString = eval('document.form.valorStringItemFilho' + seq + '.value');
				return CaracteristicaDTO;
			}

			function LOOKUP_PESQUISAITEMCONFIGURACAOPAI_PATRIMONIO_select(idItemConfiguracaoPai, desc) {
				document.form.idItemConfiguracao.value = idItemConfiguracaoPai;
				document.form.nomeItemConfiguracaoPai.value = desc;
				document.getElementById("pesqLockupLOOKUP_PESQUISAITEMCONFIGURACAOFILHO_PATRIMONIO_iditemconfiguracaopai").value = '';
				document.getElementById("pesqLockupLOOKUP_PESQUISAITEMCONFIGURACAOFILHO_PATRIMONIO_iditemconfiguracaopai").value = idItemConfiguracaoPai;

				document.form.fireEvent("restoreTipoItemConfiguracao");

				$("#addTipoItemConfiguracao").val("");

				$("#addItemConfiguracaoFilho").removeAttr('disabled');

				$("addItemConfiguracaoFilho").disabled = "";

				$("#POPUP_ITEMCONFIGPAI").dialog("close");

			}

			function LOOKUP_TIPOITEMCONFIGURACAO_PATRIMONIO_select(idTipo, desc) {
				document.form.nomeTipoItemConfiguracao.value = desc;
				document.getElementById("pesqLockupLOOKUP_PESQUISAITEMCONFIGURACAOFILHO_PATRIMONIO_idtipoitemconfiguracao").value = '';
				document.getElementById("pesqLockupLOOKUP_PESQUISAITEMCONFIGURACAOFILHO_PATRIMONIO_idtipoitemconfiguracao").value = idTipo;

				$("#POPUP_TIPOITEMCONFIGURACAO").dialog("close");
			}

			function LOOKUP_PESQUISAITEMCONFIGURACAOFILHO_PATRIMONIO_select(id, desc) {
				document.form.idItemConfiguracaoFilho.value = id;
				document.form.nomeItemConfiguracaoFilho.value = desc;

				document.form.fireEvent("restoreTipoItemConfiguracao");

				$("#POPUP_ITEMCONFIGFILHO").dialog("close");
			}


			function consultarTipoItemConfiguracao(){
				$("#POPUP_TIPOITEMCONFIGURACAO").dialog("open");
			}

			function fecharPopup(){
				$("#POPUP_TIPOITEMCONFIGURACAO").dialog("close");
			}

			function limpar() {
				$("#addItemConfiguracaoFilho").attr('disabled', 'disable');
				deleteAllRows();
				deleteAllRowsItemFilho()
				ocultaGrid();
				document.form.clear();
			}

			function exibeGrid() {
				document.getElementById('gridCaracteristica').style.display = 'block';
			}

			function exibeGridPatrimonioItemFilho(){
				document.getElementById('gridCaracteristicaItemFilho').style.display = 'block';
			}

			function ocultaGrid() {
				document.getElementById('gridCaracteristica').style.display = 'none';
				document.getElementById('gridCaracteristicaItemFilho').style.display = 'none';
			}

			function ocultaGridPatrimonioItemFilho(){
				document.getElementById('gridCaracteristicaItemFilho').style.display = 'block';
			}

			function ocultarItemConfiguracaoFilho(){
				$('#divItemConfiguracaoFilho').hide();
				$('#divTipoItemConfiguracao').hide();
				$('#opcaoItemConfiguracaoPai').attr("checked", true);

			}

			function visualizarItemConfiguracaoFilho(){
				$('#divTipoItemConfiguracao').show();
				$('#divItemConfiguracaoFilho').show();
				$('#divItemConfiguracaoPai').show();
				$('#opcaoItemConfiguracaoFilho').attr("checked", true);
			}


			$(function() {
				$("#POPUP_ITEMCONFIGPAI").dialog({
					autoOpen : false,
					width : 600,
					height : 400,
					modal : true
				});
			});

			$(function() {
				$("#POPUP_TIPOITEMCONFIGURACAO").dialog( {
					autoOpen : false,
					width : 705,
					height : 500,
					modal : true
				});
			});

			$(function() {
				$("#POPUP_ITEMCONFIGFILHO").dialog({
					autoOpen : false,
					width : 600,
					height : 400,
					modal : true
				});
			});

			$(function() {
				$("#addItemConfiguracaoPai").click(function() {
					$("#POPUP_ITEMCONFIGPAI").dialog("open");
				});
			});

			$(function() {
				$("#divTipoItemConfiguracao").click(function() {
					$("#POPUP_TIPOITEMCONFIGURACAO").dialog("open");
				});
			});

			$(function() {
				$("#addItemConfiguracaoFilho").click(function() {
					$("#POPUP_ITEMCONFIGFILHO").dialog("open");
				});
			});

		</script>
	</head>
	<body>
		<script type="text/javascript" src="../../cit/objects/CaracteristicaDTO.js"></script>
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>

				<div class="flat_area grid_16">
					<h2><fmt:message key="patrimonio.cadastro_patrimonio"/></h2>
				</div>

				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><fmt:message key="patrimonio.cadastro_patrimonio"/></a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name='form' action='${ctx}/pages/patrimonio/patrimonio'>

									<div class="columns clearfix">
										<input type='hidden' id="idEmpresa" name='idEmpresa' />
										<input type='hidden' id="idItemConfiguracao" name='idItemConfiguracao' />
										<input type='hidden' id="idItemConfiguracaoFilho" name='idItemConfiguracaoFilho'/>
										<input type='hidden' name='dataInicio'/>
										<input type='hidden' name='dataFim'/>
										<input type='hidden' id="caracteristicasSerializadas" name='caracteristicasSerializadas'/>
										<input type='hidden' id="caracteristicasSerializadasItemFilho" name='caracteristicasSerializadasItemFilho'/>

										<div class="columns clearfix">

											<div class="col_100">

												<div id="divItemConfiguracaoPai" class="col_15">
													<fieldset>

														<label ><fmt:message key="patrimonio.selecione_item"/></label>

														<div style="height: 39px">
															<label>
																<input type="radio" checked="checked" id="opcaoItemConfiguracaoPai" value="1" name="opcao" onclick="limpar(); ocultarItemConfiguracaoFilho(); " />
																<fmt:message key="patrimonio.item_pai"/>
															</label>

															<label>
																<input type="radio" id="opcaoItemConfiguracaoFilho" name="opcao" value="2" onclick="limpar();visualizarItemConfiguracaoFilho()" />
																<fmt:message key="patrimonio.item_filho"/>
															</label>
														</div>

													</fieldset>
												</div>

												<div id="divItemConfiguracaoPai" class="col_25">
													<fieldset>
														<label class="campoObrigatorio"><fmt:message key="patrimonio.item_config_pai"/></label>
														<div style="height: 36px">
															<input id="addItemConfiguracaoPai" style="width: 90% !important;" type='text' name="nomeItemConfiguracaoPai" maxlength="70" size="70"/>
															<img id="addItemConfiguracaoPai" style="cursor: pointer; vertical-align: middle;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
														</div>
													</fieldset>
												</div>

												<div id="divTipoItemConfiguracao" class="col_25">
													<fieldset>
														<label class="campoObrigatorio"><fmt:message key="patrimonio.tipo_item"/></label>
															<div>
															  	<input id="addTipoItemConfiguracao" style="width: 90% !important; cursor: pointer; " type='text' name="nomeTipoItemConfiguracao" maxlength="70" size="70" />
																<img onclick="consultarTipoItemConfiguracao()" style=" vertical-align: middle;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
															</div>
													</fieldset>
												</div>

												<div id="divItemConfiguracaoFilho" class="col_25">
													<fieldset>
														<label class="campoObrigatorio"><fmt:message key="patrimonio.item_config_filho"/></label>
														<div style="height: 36px">
															<input id="addItemConfiguracaoFilho" style="width: 90% !important;" type='text' name="nomeItemConfiguracaoFilho" maxlength="70" size="70" disabled="disabled"/>
															<img id="addItemConfiguracaoFilho" style="cursor: pointer; vertical-align: middle;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
														</div>
													</fieldset>
												</div>
											</div>
										</div>

										<br>

										<div id="gridCaracteristica" class="columns clearfix" style="display: none;">
											<h1><fmt:message key="patrimonio.patrimonio_item_pai"/></h1>
											<table class="table" id="tabelaCaracteristica" style="width: 100%" title="Patrimônio ">
												<tr>
													<th style="width: 16px;"></th>
													<th style="width: 30%;"><fmt:message key="citcorpore.comum.caracteristica"/></th>
													<th style="width: 20%;"><fmt:message key="citcorpore.comum.tag"/></th>
													<th style="width: 20%;"><fmt:message key="citcorpore.comum.valor"/></th>
													<th style="width: 30%;"><fmt:message key="citcorpore.comum.descricao"/></th>
												</tr>
											</table>
										</div>

										<br>

										<div id="gridCaracteristicaItemFilho" class="columns clearfix" style="display: none;">
											<h1><fmt:message key="patrimonio.patrimonio_item_filho"/></h1>
											<table class="table" id="tabelaCaracteristicaItemFilho" style="width: 100%" title="Patrimônio Item Configuração Filho">
												<tr>
													<th style="width: 16px;"></th>
													<th style="width: 30%;"><fmt:message key="citcorpore.comum.caracteristica"/></th>
													<th style="width: 20%;"><fmt:message key="citcorpore.comum.tag"/></th>
													<th style="width: 20%;"><fmt:message key="citcorpore.comum.valor"/></th>
													<th style="width: 30%;"><fmt:message key="citcorpore.comum.descricao"/></th>
												</tr>
											</table>
										</div>
									</div>

									<br>
									<button type='button' name='btnGravar' class="light"  onclick='gravar();'>
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
					</div>
				</div>
			</div>
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>

	<div id="POPUP_ITEMCONFIGPAI" title="Consulta Item Configuração Pai">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaItemConfiguracaoPai' style="width: 540px">
							<cit:findField formName='formPesquisaItemConfiguracaoPai'
	 							lockupName='LOOKUP_PESQUISAITEMCONFIGURACAOPAI_PATRIMONIO' id='LOOKUP_PESQUISAITEMCONFIGURACAOPAI_PATRIMONIO' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_TIPOITEMCONFIGURACAO" title="Consulta Tipo de Item Configuração">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section" style="padding: 33px;">
						<form name='formPesquisaTipoItemConfiguracao'>
							<cit:findField formName='formPesquisaTipoItemConfiguracao'
							lockupName='LOOKUP_TIPOITEMCONFIGURACAO_PATRIMONIO'
							id='LOOKUP_TIPOITEMCONFIGURACAO_PATRIMONIO' top='0' left='0' len='550' heigth='400'
							javascriptCode='true'
							htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_ITEMCONFIGFILHO" title="Consulta Item Configuração Filho">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaItemConfiguracaoFilho' style="width: 540px">

							<cit:findField formName='formPesquisaItemConfiguracaoFilho'
	 							lockupName='LOOKUP_PESQUISAITEMCONFIGURACAOFILHO_PATRIMONIO' id='LOOKUP_PESQUISAITEMCONFIGURACAOFILHO_PATRIMONIO' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</html>