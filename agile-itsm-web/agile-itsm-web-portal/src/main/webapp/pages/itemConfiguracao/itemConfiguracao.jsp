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

			function LOOKUP_PESQUISAITEMCONFIGURACAOTODOS_select(id, desc) {
				document.form.restore( {
					idItemConfiguracao: id
				});
			}

			function LOOKUP_GRUPOITEMCONFIGURACAO_select(id,desc){
				document.form.idGrupoItemConfiguracao.value = id;
				document.form.nomeGrupoItemConfiguracao.value = desc;
				$("#LOOKUP_GRUPOITEMCONFIGURACAO").dialog("close");
				document.form.fireEvent("restoreGrupoItemConfiguracao");
			}

			function LOOKUP_PESQUISAITEMCONFIGURACAO_select(idItemConfiguracaoPai, desc) {
				document.form.idItemConfiguracaoPai.value = idItemConfiguracaoPai;
				document.form.nomeItemConfiguracaoPai.value = desc;
				$("#POPUP_ITEMCONFIGPAI").dialog("close");

			}

			function LOOKUP_TIPOITEMCONFIGURACAO_select(idTipo, desc) {
				document.form.idTipoItemConfiguracao.value = idTipo;
				document.form.fireEvent("restoreTipoItemConfiguracao");
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

			var seqSelecionada = '';
			function setRestoreCaracteristica(idCaracteristica, caracteristica, tag, valorString,
					descricao, idEmpresa, dataInicio, dataFim) {
				if (seqSelecionada != '') {
					eval('document.form.idCaracteristica' + seqSelecionada + '.value = "' + idCaracteristica + '"');
					eval('document.form.caracteristica' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + caracteristica + '\')');
					eval('document.form.tagCaracteristica' + seqSelecionada	+ '.value = ObjectUtils.decodificaEnter(\'' + tag + '\')');
					eval('document.form.valorString' + seqSelecionada	+ '.value = ObjectUtils.decodificaEnter(\'' + valorString + '\')');
					eval('document.form.descricao' + seqSelecionada	+ '.value = ObjectUtils.decodificaEnter(\'' + descricao + '\')');
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

			function gravar() {
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
				serializa();
				document.form.save();
			}

			var seqSelecionada = '';
			var aux = '';
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

			getCaracteristica = function(seq) {
				var CaracteristicaDTO = new CIT_CaracteristicaDTO();
				CaracteristicaDTO.sequencia = seq;
				CaracteristicaDTO.idCaracteristica = eval('document.form.idCaracteristica' + seq + '.value');
				CaracteristicaDTO.valorString = eval('document.form.valorString' + seq + '.value');
				return CaracteristicaDTO;
			}

			$(function() {
				$("#POPUP_TIPOITEMCONFIGURACAO").dialog( {
					autoOpen : false,
					width : 705,
					height : 500,
					modal : true
				});
			});

			function consultarTipoItemConfiguracao(){
				$("#POPUP_TIPOITEMCONFIGURACAO").dialog("open");
			}

			function fecharPopup(){
				$("#POPUP_TIPOITEMCONFIGURACAO").dialog("close");
			}

			function fecharPopupGrupo(){
				$("#POPUP_GRUPOITEMCONFIGURACAO").dialog("close");
			}

			$(function() {
				$("#POPUP_GRUPOITEMCONFIGURACAO").dialog( {
					autoOpen : false,
					width : 705,
					height : 500,
					modal : true
				});
			});

			function consultarGrupoItemConfiguracao(){
				$("#POPUP_GRUPOITEMCONFIGURACAO").dialog("open");
			}

			function limpar() {
				deleteAllRows();
				document.getElementById('gridCaracteristica').style.display = 'none';
				document.form.clear();
			}

			function exibeGrid() {
				document.getElementById('gridCaracteristica').style.display = 'block';
			}

			function ocultaGrid() {
				document.getElementById('gridCaracteristica').style.display = 'none';
			}

			function ocultarItemConfiguracao(){
				$('#divTipoItemConfiguracao').hide();
				$('#divItemConfiguracaoPai').hide();
				$('#divGrupoItemConfiguracao').show();
				$('#opcaoItemConfiguracaoPai').attr("checked", true);

			}

			function visualizarItemConfiguracaoPai(){
				$('#divTipoItemConfiguracao').show();
				$('#divItemConfiguracaoPai').show();
				$('#divGrupoItemConfiguracao').hide();
				$('#opcaoItemConfiguracaoFilho').attr("checked", true);
			}


//		 	popup para pesquisar de iTem Configuracao Pai
			$(function() {
				$("#POPUP_ITEMCONFIGPAI").dialog({
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


		</script>
	</head>
	<body>
		<script type="text/javascript" src="../../cit/objects/CaracteristicaDTO.js"></script>
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>

				<div class="flat_area grid_16">
					<h2><fmt:message key="itemConfiguracao.itemConfiguracao"/></h2>
				</div>

				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><fmt:message key="itemConfiguracao.cadastro"/></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top"><fmt:message key="itemConfiguracao.pesquisa"/></a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name='form' action='${ctx}/pages/itemConfiguracao/itemConfiguracao'>
									<div class="columns clearfix">
										<input type='hidden' id="idItemConfiguracao" name='idItemConfiguracao'/>
										<input type='hidden' id="idItemConfiguracaoPai" name='idItemConfiguracaoPai'/>
										<input type='hidden' id="idTipoItemConfiguracao" name='idTipoItemConfiguracao'/>
										<input type='hidden' name='dataInicio'/>
										<input type='hidden' name='dataFim'/>
										<input type='hidden' id="caracteristicasSerializadas" name='caracteristicasSerializadas'/>
										<input type='hidden' id ="idGrupoItemConfiguracao" name='idGrupoItemConfiguracao' />

										<div class="columns clearfix">
										<div class="col_33">
											<fieldset>
											<label ><fmt:message key="itemConfiguracao.opcao" /></label>
											<div style="height: 35px"  class="inline clearfix">
												<label>
												<input type="radio" checked="checked" id="opcaoItemConfiguracaoPai" value="1" name="opcao" onclick="limpar(); ocultarItemConfiguracao(); " /><fmt:message  key="itemConfiguracao.itemConfiguracaoPai" /></label>
												<label>
												<input type="radio" id="opcaoItemConfiguracaoFilho" name="opcao" value="2" onclick="limpar();visualizarItemConfiguracaoPai()" /><fmt:message key="itemConfiguracao.itemConfiguracaoFilho" /></label>
											</div>
											</fieldset>
										</div>
										<div class="col_16">
											<fieldset>
												<label class="campoObrigatorio"><fmt:message key="itemConfiguracao.itemConfiguracao"/></label>
													<div>
													  	<input type='text' name="identificacao" maxlength="70" size="70" class="Valid[Required] Description[Nome do Item Configuração]" />
													</div>
											</fieldset>
										</div>
										<div class="col_33" id="divGrupoItemConfiguracao">
											<fieldset>
													<label>Grupo:</label>
														<div>
														  	<input onclick="consultarGrupoItemConfiguracao()" readonly="readonly" style="width: 90% !important;" type='text' name="nomeGrupoItemConfiguracao" maxlength="70" size="70"  />
															<img onclick="consultarGrupoItemConfiguracao()" style=" vertical-align: middle;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
														</div>
												</fieldset>
										</div>
									</div>
									<div id="divItemConfiguracaoPai" class="col_25">
											<fieldset>
												<label class="campoObrigatorio"><fmt:message key="itemConfiguracao.itemConfiguracaoPai" /></label>
												<div style="height: 36px">
													<input id="addItemConfiguracaoPai" style="width: 90% !important;" type='text'
														name="nomeItemConfiguracaoPai" maxlength="70" size="70"/> <img
														id="addItemConfiguracaoPai"
														style="cursor: pointer; vertical-align: middle;"
														src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
												</div>
											</fieldset>
										</div>
										<div id="divTipoItemConfiguracao" class="columns clearfix">
											<div class="col_50">
												<fieldset>
													<label class="campoObrigatorio"><fmt:message key="tipoItemConfiguracao.tipoItemConfiguracao"/></label>
														<div>
														  	<input onclick="consultarTipoItemConfiguracao()" readonly="readonly" style="width: 90% !important;" type='text' name="nomeTipoItemConfiguracao" maxlength="70" size="70"  />
															<img onclick="consultarTipoItemConfiguracao()" style=" vertical-align: middle;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
														</div>
												</fieldset>
											</div>
										</div>
										<br>
										<div id="gridCaracteristica" class="columns clearfix" style="display: none;">
											<table class="table" id="tabelaCaracteristica" style="width: 100%">
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
									<br><br>
									<button type='button' name='btnGravar' class="light"  onclick='gravar();'>
										<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar"/></span>
									</button>
									<button type='button' name='btnLimpar' class="light" onclick='limpar();'>
										<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="citcorpore.comum.limpar"/></span>
									</button>
									<button type='button' name='btnUpDate' class="light" onclick='update();'>
										<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
										<span><fmt:message key="citcorpore.comum.excluir"/></span>
									</button>
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section"><fmt:message key="citcorpore.comum.pesquisa"/>
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa'
													lockupName='LOOKUP_PESQUISAITEMCONFIGURACAOTODOS'
													id='LOOKUP_PESQUISAITEMCONFIGURACAOTODOS' top='0' left='0' len='550' heigth='400'
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
	<div id="POPUP_TIPOITEMCONFIGURACAO" title="Consulta Tipo de Item Configuração">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
										<div id="tabs-1" class="block">
							<div class="section">
								<form name='form' action='${ctx}/pages/tipoItemConfiguracao/tipoItemConfiguracao'>
									<div class="columns clearfix">
										<input type='hidden' name='id'/>
										<input type='hidden' name='idEmpresa' value="<%=WebUtil.getIdEmpresa(request)%>"/>
										<input type='hidden' name='dataInicio'/>
										<input type='hidden' name='dataFim'/>
										<input type='hidden' name='caracteristicasSerializadas'/>
										<input type='hidden' name='caracteristicasDeserializadas'/>
										<input type='hidden' id='caracteristicaSerializada' name='caracteristicaSerializada'/>

										<div class="columns clearfix">
											<div class="col_50">
												<fieldset>
													<label class="campoObrigatorio"><fmt:message key="tipoItemConfiguracao.tipoItemConfiguracao"/></label>
														<div>
														  <input type='text' name="nome" maxlength="70" size="70" class="Valid[Required] Description[Nome Tipo de Item Configuração" />
														</div>
												</fieldset>
											</div>
											<div class="col_50">
												<fieldset>
													<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.tag"/></label>
														<div>
														  <input type='text' name="tag" maxlength="50" size="70" class="Valid[Required] Description[TAG do Tipo de Item Configuração" />
														</div>
												</fieldset>
											</div>
										</div>
										<div class="columns clearfix">
											<div class="col_100" style="padding-left: 20px; padding-top: 10px;">
												<button id="addCaracteristica" type='button' name='botaoCaracteristica' class="light">
													<img src="${ctx}/template_new/images/icons/small/util/adcionar.png">
													<span><fmt:message key="citcorpore.comum.caracteristica"/></span>
												</button>
												<button type='button' name='botaoCaracteristica' class="light" onclick="popupA.abrePopup('caracteristica', 'preencherComboUnidade')" >
													<img src="${ctx}/template_new/images/icons/small/util/adcionar.png">
													<span>
														Inserir Características
													</span>
												</button>
											</div>
										</div>
										<br>
										<div id="gridCaracteristica" class="columns clearfix" style="display: none;">
											<table class="table" id="tabelaCaracteristica" style="width: 100%">
												<tr>
													<th style="width: 16px;"></th>
													<th style="width: 40%;"><fmt:message key="citcorpore.comum.caracteristica"/></th>
													<th style="width: 20%;"><fmt:message key="citcorpore.comum.tag"/></th>
													<th style="width: 40%;"><fmt:message key="citcorpore.comum.descricao"/></th>
												</tr>
											</table>
										</div>
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
									<button type='button' name='btnUpDate' class="light" onclick='update();'>
										<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
										<span><fmt:message key="citcorpore.comum.excluir"/></span>
									</button>
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section"><fmt:message key="citcorpore.comum.pesquisa"/>
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa'
													lockupName='LOOKUP_TIPOITEMCONFIGURACAO'
													id='LOOKUP_TIPOITEMCONFIGURACAO' top='0' left='0' len='550' heigth='400'
													javascriptCode='true'
													htmlCode='true' />
								</form>
							</div>
						</div>
			</div>
		</div>
	</div>
	<div id="POPUP_GRUPOITEMCONFIGURACAO" title="Consulta Grupo de Item Configuração">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section" style="padding: 33px;">
					<form name='formPesquisaGrupoItemConfiguracao'>
						<cit:findField formName='formPesquisaGrupoItemConfiguracao'
						lockupName='LOOKUP_GRUPOITEMCONFIGURACAO'
						id='LOOKUP_GRUPOITEMCONFIGURACAO' top='0' left='0' len='550' heigth='400'
						javascriptCode='true'
						htmlCode='true' />
					</form>
				</div>
			</div>
			</div>
	</div>
<div id="POPUP_ITEMCONFIGPAI" title="<fmt:message key="citcorpore.comum.identificacao" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisaItemConfiguracaoPai' style="width: 540px">
						<cit:findField formName='formPesquisaItemConfiguracaoPai'
 							lockupName='LOOKUP_PESQUISAITEMCONFIGURACAO' id='LOOKUP_PESQUISAITEMCONFIGURACAO' top='0'
							left='0' len='550' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</html>