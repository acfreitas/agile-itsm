<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.CaracteristicaDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.HistoricoItemConfiguracaoDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.InformacaoItemConfiguracaoDTO"%>
<html>
<head>
<%@include file="/include/header.jsp"%>
<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>


<%
	request.setCharacterEncoding("UTF-8");
	response.setHeader("Content-Language","lang");

	String iframe = "";
	iframe = request.getParameter("iframe"); %> <link rel="stylesheet" type="text/css" href="${ctx}/css/IC.css">

<script  charset="ISO-8859-1" type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
<script type="text/javascript" src="../../cit/objects/CaracteristicaDTO.js"></script>
<script type="text/javascript" src="../../cit/objects/HistoricoItemConfiguracaoDTO.js"></script>

<style type="text/css">
	.tableLess {
	  font-family: arial, helvetica !important;
	  font-size: 10pt !important;
	  cursor: default !important;
	  margin: 0 !important;
	  background: white !important;
	  border-spacing: 0  !important;
	  width: 100%  !important;
	}

	.tableLess tbody {
	  background: transparent  !important;
	}

	.tableLess * {
	  margin: 0 !important;
	  vertical-align: middle !important;
	  padding: 2px !important;
	}

	.tableLess thead th {
	  font-weight: bold  !important;
	  background: #fff url(../../imagens/title-bg.gif) repeat-x left bottom  !important;
	  text-align: center  !important;
	}

	.tableLess tbody tr:ACTIVE {
	  background-color: #fff  !important;
	}

	.tableLess tbody tr:HOVER {
	  background-color: #e7e9f9 ;
	  cursor: pointer;
	}

	.tableLess th {
	  border: 1px solid #BBB  !important;
	  padding: 6px  !important;
	}

	.tableLess td{
	  border: 1px solid #BBB  !important;
	  padding: 6px 10px  !important;
	}

	.sel {
		display: none;
		background: none  !important;;
		cursor:auto;
		padding: 0;
		margin: 0;
	}
	.sel td {
		padding: 0;
		margin: 0;
	}

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

	.sel-s {
	 	padding-left:20px !important;
		border: 0px !important;
		width: 50% !important;
	}
	.form{
		width: 100%;
		float: right;
	}

	.formHead{
		float: left;
		width: 99%;
		border: 1px solid #ccc;
		padding: 5px;
	}

	.formBody{
		float: left;
		width: 99%;
		border: 1px solid #ccc;
		padding: 5px;
	}

	.formRelacionamentos div{
		float: left;
		width: 99%;
		border: 1px solid #ccc;
		padding: 5px;
	}

	.formFooter{
		float: left;
		width: 99%;
		border: 1px solid #ccc;
		padding: 5px;
	}

	.divEsquerda{
		float: left;
		width: 47%;
		border: 1px solid #ccc;
		padding: 5px;
	}

	.divDireita{
		float: right;
		width: 47%;
		border: 1px solid #ccc;
		padding: 5px;
	}

	.ui-tabs .ui-tabs-nav li a{
		background-color: #fff !important;
	}

	.ui-state-active{
		background-color: #aaa ;
	}

	#tabs div{
		background-color: #fff;
	}


	.ui-state-hover{
		background-color: #ccc !important;
	}
	#contentBaseline {
		padding: 0 !important;
		margin: 0 !important;
		border: 0 !important;
	}
	.padd10 {
		padding: 0 10px;
	}
	.lFloat{
		float: left;
	}
	#divGrupoItemConfiguracao {
		display: none;
	}

</style>
<script type="text/javascript">
			var objTab = null;

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
				$("#POPUP_GRUPOITEMCONFIGURACAO").dialog("close");
				document.form.fireEvent("restoreGrupoItemConfiguracao");
			}

			function LOOKUP_PESQUISAITEMCONFIGURACAO_select(idItemConfiguracaoPai, desc) {

				if(confirm(i18n_message("itemConfiguracaoTree.ICrelacionado"))) {
					document.form.idItemConfiguracaoPai.value = idItemConfiguracaoPai;
					$("#nomeItemConfiguracaoPai").text(desc);
/* 					document.form.localidade.value = "";
 */					$("#POPUP_ITEMCONFIGPAI").dialog("close");
					gravar();
				}

			}

			function LOOKUP_TIPOITEMCONFIGURACAO_select(idTipo, desc) {
				document.form.idTipoItemConfiguracao.value =	 idTipo;
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
				coluna.innerHTML = valor[0] + '<input type="hidden" id="idCaracteristica' + countCaracteristica + '" name="idCaracteristica" value="' + id + '" />';

				coluna = row.insertCell(1);
				coluna.innerHTML = valor[1];

				coluna = row.insertCell(2);
				coluna.innerHTML = valor[2]
			}

			function restoreRow() {
				var tabela = document.getElementById('tabelaCaracteristica');
				var lastRow = tabela.rows.length;

				var row = tabela.insertRow(lastRow);
				countCaracteristica++;
				var coluna = row.insertCell(0);
				coluna.innerHTML = '<input type="hidden" id="idCaracteristica' + countCaracteristica + '" name="idCaracteristica"/>' +
									'<div id="caracteristica'+ countCaracteristica +'"></div>';
				coluna = row.insertCell(1);
				coluna.innerHTML = '<div id="descricao'+ countCaracteristica +'"></div>';
				coluna = row.insertCell(2);
				coluna.innerHTML = '<input style="width: 100%; border: 1 none;" type="text" id="valorString' + countCaracteristica + '" name="valorString"/>';

			}

			var seqSelecionada = '';
			function setRestoreCaracteristica(idCaracteristica, caracteristica, tag, valorString, descricao, idEmpresa, dataInicio, dataFim) {
				if (seqSelecionada != '') {
					eval('document.form.idCaracteristica' + seqSelecionada + '.value = "' + idCaracteristica + '"');
					$('#caracteristica' + seqSelecionada).text(ObjectUtils.decodificaEnter(caracteristica));
					$('#descricao' + seqSelecionada).text(ObjectUtils.decodificaEnter(descricao));
					$('#valorString' + seqSelecionada).val(ObjectUtils.decodificaEnter(valorString));
					eval('document.form.valorString' + seqSelecionada + '.value = "' + valorString + '"');
				}
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


			function reload(idItem) {
				<%
			    //se for chamado por iframe deixa apenas a parte de cadastro da página
			    if (iframe == null) {
				%>
					parent.reloadItem(idItem);
				<%
			    }
 				%>
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
			/*Gravar baseline*/
			function gravarBaseline() {
				var tabela = document.getElementById('tblBaselines');
				var count = tabela.rows.length;
				var contadorAux = 0;
				var baselines = new Array();

				for ( var i = 1; i <= count; i++) {
					var trObj = document.getElementById('idHistoricoIC' + i);
					if (!trObj) {
						continue;
					}
					baselines[contadorAux] = getbaseline(i);
					contadorAux = contadorAux + 1;
				}
				serializaBaseline();
				document.formBaseline.fireEvent("saveBaseline");
			}

			var seqBaseline = '';
			var aux = '';
			serializaBaseline = function() {
				var tabela = document.getElementById('tblBaselines');
				var count = tabela.rows.length;
				var contadorAux = 0;
				var baselines = new Array();
				for ( var i = 1; i <= count; i++) {
					var trObj = document.getElementById('idHistoricoIC' + i);
					if (!trObj) {
						continue;
					}else if(trObj.checked){
						baselines[contadorAux] = getbaseline(i);
						contadorAux = contadorAux + 1;
						continue;
					}

				}
				var baselinesSerializadas = ObjectUtils.serializeObjects(baselines);
				document.formBaseline.baselinesSerializadas.value = baselinesSerializadas;
				return true;
			}

			getbaseline = function(seq) {
				var HistoricoItemConfiguracaoDTO = new CIT_HistoricoItemConfiguracaoDTO();
				HistoricoItemConfiguracaoDTO.sequencia = seq;
				HistoricoItemConfiguracaoDTO.idHistoricoIC = eval('document.formBaseline.idHistoricoIC' + seq + '.value');
				return HistoricoItemConfiguracaoDTO;
			}

			function restaurar(id){
				document.formBaseline.idHistoricoIC.value = id;
				if(confirm(i18n_message("itemConfiguracaoTree.restaurarVersao")))
					document.formBaseline.fireEvent("restaurarBaseline");
			}


			function fecharPopup(){
				$("#POPUP_TIPOITEMCONFIGURACAO").dialog("close");
			}

			function fecharPopupGrupo(){
				$("#POPUP_GRUPOITEMCONFIGURACAO").dialog("close");
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
				<%
			    //se for chamado por iframe deixa apenas a parte de cadastro da página
			    if (iframe == null) {
				%>
					document.getElementById('gridCaracteristica').style.display = 'none';
				<%
			    }
 				%>
			}

			$(function() {

				$("#POPUP_ITEMCONFIGPAI").dialog({
					autoOpen : false,
					width : 700,
					height : 500,
					modal : true
				});

				$("#POPUP_TIPOITEMCONFIGURACAO").dialog( {
					autoOpen : false,
					width : 705,
					height : 500,
					modal : true
				});

				$("#POPUP_GRUPOITEMCONFIGURACAO").dialog( {
					autoOpen : false,
					width : 705,
					height : 500,
					modal : true
				});

				$("#POPUP_ITEMCONFIGPAI").dialog({
					autoOpen : false,
					width : 700,
					height : 500,
					modal : true
				});

				$("#addItemConfiguracaoPai").click(function() {
					$("#POPUP_ITEMCONFIGPAI").dialog("open");
				});

				$("#POPUP_TIPOITEMCONFIGURACAO").dialog( {
					autoOpen : false,
					width : 705,
					height : 500,
					modal : true
				});

				$(".dialog").dialog({
					autoOpen : false,
					modal : true
				});

				$("#POPUP_EMPREGADO").dialog({
					autoOpen : false,
					width : 600,
					height : 400,
					modal : true
				});
				$("#POPUP_INCIDENTE").dialog({
					autoOpen : false,
					width : 700,
					height : 500,
					modal : true
				});
				$("#POPUP_PROBLEMA").dialog({
					autoOpen : false,
					width : 600,
					height : 400,
					modal : true
				});
				$("#POPUP_MUDANCA").dialog({
					autoOpen : false,
					width : 600,
					height : 400,
					modal : true
				});
				$("#POPUP_LIBERACAO").dialog({
					autoOpen : false,
					width : 600,
					height : 400,
					modal : true
				});
				$("#POPUP_MIDIASOFTWARE").dialog({
					autoOpen : false,
					width : 600,
					height : 400,
					modal : true
				});
				$("#POPUP_GRUPOITEMCONFIGURACAO").dialog( {
					autoOpen : false,
					width : 705,
					height : 500,
					modal : true
				});

				$("#POPUP_IMPACTO").dialog( {
					autoOpen : false,
					width : 705,
					height : 500,
					modal : true
				});
				$("#POPUP_RESPONSAVEL").dialog({
					autoOpen : false,
					width : 600,
					height : 400,
					modal : true
				});


				$("#divAlterarSenha").hide();

				initPopups();

	    		//para visualização rápida do mapaDesenhoServico
	    		popupManager = new PopupManager(window.screen.width - 100 , window.screen.height - 100, "${ctx}/pages/");
	    		//solicitcaoservico
	    		popupManagerSolicitacaoServico = new PopupManager(window.screen.width - 100 , window.screen.height - 100, "${ctx}/pages/");

				$( "#tabs" ).tabs();


			});

			function event() {
				$( ".even" ).click(function() {
					var s = $(this).attr('id').split('-');
					if(s[0]=="even")
						$( "#sel-" + s[1] ).toggle();
					else if(s[0]=="evenM")
						$( "#selM-" + s[1] ).toggle();
					else if(s[0]=="evenP")
						$( "#selP-" + s[1] ).toggle();
				});
			}

		</script>

</head>

	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
		title="Aguarde... Processando..."
		style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
	</cit:janelaAguarde>

	<body id="bodyInf">

		<div class="tabs">
			<ul class="tab_header clearfix">
				<li><a href="#tabs-1" onclick="showRelacionamentos()"><fmt:message	key="tipoItemConfiguracao.informacoesItemConfiguracao"  /></a></li>
<%-- 				<li><a href="#tabs-2" onclick="verificaImpactos()" class="round_top"><fmt:message key="itemConfiguracaoTree.impactos" /></a></li> --%>
<%-- 				<li><a href="#tabs-3" class="round_top"><fmt:message key="inventario.invetario" /></a></li> --%>
			</ul>
			<a href="#" class="toggle">&nbsp;</a>
			<div class="toggle_container">
				<div id="tabs-1" class="block">
					<div class="section">
		<form name='form'  id='form' action='${ctx}/pages/informacoesItemConfiguracaoMudanca/informacoesItemConfiguracaoMudanca'>
			<input type='hidden' id='idItemConfiguracao' name='idItemConfiguracao'/>
			<input type='hidden' id="idTipoItemConfiguracao" name='idTipoItemConfiguracao'/>
			<input type='hidden' id="idItemConfiguracaoPai" name='idItemConfiguracaoPai'/>
			<input type='hidden' id="idGrupoItemConfiguracao" name='idGrupoItemConfiguracao'/>
			<input type='hidden' name='dataInicio'/>
			<input type='hidden' name='dataFim'/>
			<input type='hidden' id="caracteristicasSerializadas" name='caracteristicasSerializadas'/>
			<input type='hidden' id='idIncidente' name='idIncidente'/>
			<input type='hidden' id='idProblema' name='idProblema'/>
			<input type='hidden' id='idMudanca' name='idMudanca'/>
			<input type='hidden' id='idProprietario' name='idProprietario'/>
			<input type='hidden' id='idMidiaSoftware' name='idMidiaSoftware'/>
			<input type='hidden' id='idLiberacao' name='idLiberacao'/>
			<input type='hidden' id='idResponsavel' name='idResponsavel'/>

			<div id="principalInf" style="display: none;"></div>
				<div id="itemConfiguracaoCorpo">
					<table>
						<tr>
							<td>
								<h2 id="titleITem"></h2>
							</td>
							<td>
								&nbsp;
							</td>
							<%-- <td>
								<img src='${ctx}/imagens/linkimpacto.png' border='0' style='cursor:pointer' onclick='verificaImpactos()'/>
							</td> --%>
						</tr>
					</table>
					<div id="itemPai" style="line-height: 20px;display: none;">
						<input type="button" id="addItemConfiguracaoPai" name="addItemConfiguracaoPai" class="padd10 lFloat" maxlength="70" size="70" value='<fmt:message key="itemConfiguracaoTree.mudarItemRelacionado"/>' />
						<h2 class="padd10 lFloat" id="nomeItemConfiguracaoPai"></h2>
					</div>
				</div>
				<div class="columns clearfix">

				    <div class="col_66">
						<fieldset>
							<label><fmt:message key="contrato.contrato" /></label>
								<div>
								   <select id="idContrato" name="idContrato" class="Valid[Required] Description[<fmt:message key="contrato.contrato"/>]"></select>
								</div>
						</fieldset>
					</div>

					<div class="col_40">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.identificacao"/></label>
								<div>
								  	<input type='text' name="identificacao" class="Valid[Required] Description[<fmt:message key="citcorpore.comum.identificacao"/>]" maxlength="70" size="40"/>
								</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="itemConfiguracaoTree.familia"/></label>
								<div>
								  	<input type='text' name="familia" class="Valid[Required] Description[<fmt:message key="itemConfiguracaoTree.familia"/>]" maxlength="70" size="40"/>
								</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="itemConfiguracaoTree.classe"/></label>
								<div>
								  	<input type='text' name="classe" class="Valid[Required] Description[<fmt:message key="itemConfiguracaoTree.classe"/>]" maxlength="70" size="40"/>
								</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="itemConfiguracaoTree.localidade"/></label>
								<div>
								  	<input type='text' name="localidade" class="Valid[Required] Description[<fmt:message key="itemConfiguracaoTree.localidade"/>]" maxlength="70" size="40"/>
								</div>
						</fieldset>
					</div>

					<div class="col_20">
						<fieldset>
							<label><fmt:message key="itemConfiguracaoTree.dataExpiracao"/></label>
								<div>
								  	<input type='text' name="dataExpiracao" class="Description[<fmt:message key="itemConfiguracaoTree.dataExpiracao"/>] Format[Date] Valid[Date] datepicker" maxlength="30" size="30"/>
								</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label style="cursor: pointer;" class="campoObrigatorio"><fmt:message key="colaborador.colaborador"/></label>
							<div>
								<input id="nomeUsuario" type='text' readonly="readonly" name="nomeUsuario" onfocus='abrePopupUsuario();' style="width: 70% !important;" maxlength="80" class="Valid[Required] Description[<fmt:message key="colaborador.colaborador"/>]" />
								</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="itemConfiguracaoTree.versao"/></label>
								<div>	<input type='text' name="versao" class="Valid[Required] Description[<fmt:message key="itemConfiguracaoTree.versao"/>]" maxlength="30" size="30"/>	</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="itemConfiguracaoTree.nSerie"/></label>
								<div>	<input type='text' name="numeroSerie" class="Valid[Required] Description[<fmt:message key="itemConfiguracaoTree.nSerie"/>]" maxlength="30" size="30"/>	</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="itemConfiguracaoTree.criticidadeDoServico"/></label>
								<div>	<select name="criticidade" class="Valid[Required] Description[<fmt:message key="itemConfiguracaoTree.criticidadeDoServico"/>]"></select>	</div>
						</fieldset>
					</div>
				</div>
				<div class="columns clearfix">
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="itemConfiguracaoTree.urgencia"/></label>
							<div>
								<select id="urgencia" name="urgencia" class="Description[<fmt:message key="itemConfiguracaoTree.urgencia"/>]">
								</select>
							</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="itemConfiguracaoTree.impacto"/></label>
							<div>
								<select id="impacto" name="impacto" class="Description[<fmt:message key="itemConfiguracaoTree.impacto"/>]">
								</select>
							</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="itemConfiguracaoTree.status"/></label>
								<div>
								  	<select  name="status" class="Valid[Required] Description[<fmt:message key="itemConfiguracaoTree.status"/>]"></select>
								</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="tipoItemConfiguracao.tipoItemConfiguracao"/></label>
							<div>
							  	<input class="Valid[Required] Description[<fmt:message key="tipoItemConfiguracao.tipoItemConfiguracao"/>]" onclick="consultarTipoItemConfiguracao()" readonly="readonly" style="width: 70% !important;" type='text' name="nomeTipoItemConfiguracao" maxlength="70" size="70"  />
								</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label style="cursor: pointer;"><fmt:message key="itemConfiguracaoTree.midia"/></label>
							<div>
								<input id="nomeMidia" type='text' readonly="readonly" name="nomeMidia" onfocus='abrePopupMidia();' style="width: 70% !important;" maxlength="80" class="Description[<fmt:message key="itemConfiguracaoTree.midia"/>]" />
								</div>
						</fieldset>
					</div>
				</div>
				<div class="columns clearfix">
					<div class="col_20">
						<fieldset>
							<label style="cursor: pointer;"><fmt:message key="itemConfiguracaoTree.incidenteRequisicao"/></label>
							<div>
								<input id="numeroIncidente" type='text' readonly="readonly" name="numeroIncidente" onfocus='abrePopupIncidente();' style="width: 70% !important;" maxlength="80" class="Description[<fmt:message key="itemConfiguracaoTree.incidenteRequisicao"/>]" />
								</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label style="cursor: pointer;"><fmt:message key="itemConfiguracaoTree.problema"/></label>
							<div>
								<input id="numeroProblema" type='text' readonly="readonly" name="numeroProblema" onfocus='abrePopupProblema();' style="width: 70% !important;" maxlength="80" class="Description[<fmt:message key="itemConfiguracaoTree.problema"/>]" />
								</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label id="Mudancarequired" style="cursor: pointer;" ><fmt:message key="itemConfiguracaoTree.mudanca"/></label>
							<div>
								<input id="numeroMudanca" type='text' readonly="readonly" name="numeroMudanca" onfocus='abrePopupMudanca();' style="width: 70% !important;" maxlength="80" class="Description[<fmt:message key="itemConfiguracaoTree.mudanca"/>]" />
							</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label id="liberacao" style="cursor: pointer;" ><fmt:message key="liberacao"/></label>
							<div>
								<input id="tituloLiberacao" type='text' readonly="readonly" name="tituloLiberacao" onfocus='abrePopupLiberacao();' style="width: 70% !important;" maxlength="80" class="Description[<fmt:message key="liberacao"/>]" />
							</div>
						</fieldset>
					</div>
					<div class="col_20" id="divGrupoItemConfiguracao">
						<fieldset>
							<label><fmt:message key="itemConfiguracaoTree.grupo"/></label>
								<div>
								  	<input onclick="abrePopupGrupoItemConfiguracao()" readonly="readonly" style="width: 70% !important;" type='text' name="nomeGrupoItemConfiguracao" maxlength="70" size="70"  />
									</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label><fmt:message key="itemConfiguracaoTree.ativofixo"/></label>
								<div>
								  	<input type='text' name="ativoFixo" class="Description[<fmt:message key="itemConfiguracaoTree.ativofixo"/>]" maxlength="70" size="40"/>
								</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label style="cursor: pointer;"><fmt:message key="citcorpore.comum.responsavel"/></label>
							<div>
								<input id="nomeResponsavel" type='text' readonly="readonly" name="nomeResponsavel" onfocus='abrePopupResponsavel();' style="width: 70% !important;" maxlength="80" class="Valid[Required] Description[<fmt:message key="citcorpore.comum.responsavel"/>]" />
								</div>
						</fieldset>
					</div>

				</div>
				<br>
				<div id="gridCaracteristica" class="columns clearfix" style="display: none;">
					<h2><fmt:message key="itemConfiguracaoTree.caracteristicas"/></h2>
					<table class="table" id="tabelaCaracteristica" style="width: 100%">
						<tr>
							<th style="width: 20%;"><fmt:message key="citcorpore.comum.caracteristica"/></th>
							<th style="width: 30%;"><fmt:message key="citcorpore.comum.descricao"/></th>
							<th style="width: 20%;"><fmt:message key="citcorpore.comum.valor"/></th>
						</tr>
					</table>
				</div>
		</form>
		</div>
		</div>
			<div id="tabs-2" class="block" >
				<div class="section">
					<form><div id='divImpactos'  style='width: 600px'></div></form>
				</div>
			</div>

			<div id="tabs-3" class="block" >
				<div class="section">
					<!--  <iframe  width="100%" height="90%" src="${ctx}/pages/informacaoItemConfiguracao/informacaoItemConfiguracao.load?id=<%=request.getParameter("idItemConfiguracao")%>&mostraItensVinculados=false"></iframe>		-->
				</div>
			</div>

		</div>
	</div>

	<div id="popupCadastroRapido">
		<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
	</div>
		<div id="POPUP_GRUPOITEMCONFIGURACAO" title="<fmt:message key="itemConfiguracaoTree.consultaGrupoItemConfiguracao"/>">
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section" style="padding: 33px;">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<fmt:message key="itemConfiguracaoTree.grupoItemConfiguracao"/>
									<img src="${ctx}/imagens/add.png" onclick="popup.abrePopup('grupoItemConfiguracao')">
								</label>
							</div>
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

		<div id="POPUP_TIPOITEMCONFIGURACAO" title="<fmt:message key="itemConfiguracaoTree.consultaTipoItemConfiguracao"/>" style="display: none;">
			<div class="box grid_16 tabs">
					<div class="toggle_container">
						<div id="tabs-2" class="block">
							<div class="section">
								<div  align="right">
									<label  style="cursor: pointer; ">
										<fmt:message key="itemConfiguracaoTree.tipoItemConfiguracao"/>
										<img src="${ctx}/imagens/add.png" onclick="popup.abrePopup('tipoItemConfiguracao')">
									</label>
								</div>
								<form name='formPesquisaTipoItemConfiguracao'>
									<cit:findField formName='formPesquisaTipoItemConfiguracao'
									lockupName='LOOKUP_TIPOITEMCONFIGURACAO'
									id='LOOKUP_TIPOITEMCONFIGURACAO' top='0' left='0' len='550' heigth='600'
									javascriptCode='true'
									htmlCode='true' />
								</form>
							</div>
						</div>
					</div>
				</div>

		</div>

		<div id="POPUP_MIDIASOFTWARE" title='<fmt:message key="itemConfiguracaoTree.pesquisaMidia"/>' style="display: none;">
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<fmt:message key="itemConfiguracaoTree.midia"/>
									<img src="${ctx}/imagens/add.png" onclick="popup.abrePopup('midiaSoftware')">
								</label>
							</div>
							<form name='formPesquisaMidiaSoftware'>
								<cit:findField formName='formPesquisaMidiaSoftware'
								lockupName='LOOKUP_MIDIASOFTWARE'
								id='LOOKUP_MIDIASOFTWARE' top='0' left='0' len='550' heigth='400'
								javascriptCode='true'
								htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="POPUP_EMPREGADO" title='<fmt:message key="itemConfiguracaoTree.pesquisaEmpregado"/>'>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<fmt:message key="colaborador.colaborador"/>
									<img src="${ctx}/imagens/add.png" onclick="popup.abrePopup('empregado')">
								</label>
							</div>
							<form name='formPesquisaEmp' style="width: 540px">
								<cit:findField formName='formPesquisaEmp'
									lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0'
									left='0' len='1050' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_RESPONSAVEL" title='<fmt:message key="citcorpore.comum.responsavel"/>'>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<fmt:message key="colaborador.colaborador"/>
									<img src="${ctx}/imagens/add.png" onclick="popup.abrePopup('responsavel')">
								</label>
							</div>
							<form name='formPesquisaRes' style="width: 540px">
								<cit:findField formName='formPesquisaRes'
									lockupName='LOOKUP_RESPONSAVEL_ITEM' id='LOOKUP_RESPONSAVEL' top='0'
									left='0' len='1050' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_INCIDENTE" title="<fmt:message key="solicitacaoServico.solicitacao" />">
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section" style='overflow: auto'>
							<div  align="right">
								<label  style="cursor: pointer; ">
										<fmt:message key="solicitacaoServico.solicitacao" />
										<img id='botaoSolicitante' src="${ctx}/imagens/add.png" onclick="popupManagerSolicitacaoServico.abrePopupParms('solicitacaoServicoMultiContratos', '', '&selecaoIc=true');">
								</label>
							</div>
							<form name='formPesquisaSolicitacaoServico' style="width: 540px">
								<cit:findField formName='formPesquisaSolicitacaoServico'
									lockupName='LOOKUP_SOLICITACAOSERVICO' id='LOOKUP_SOLICITACAOSERVICO' top='0'
									left='0' len='550' heigth='280' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="POPUP_PROBLEMA" title='<fmt:message key="itemConfiguracaoTree.pesquisaProblema"/>'>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<fmt:message key="itemConfiguracaoTree.problemas"/>
									<img src="${ctx}/imagens/add.png" onclick="popup.abrePopup('problema')">
								</label>
							</div>
							<form name='formPesquisaProblema' style="width: 540px">
								<cit:findField formName='formPesquisaProblema'
									lockupName='LOOKUP_PROBLEMA' id='LOOKUP_PROBLEMA' top='0'
									left='0' len='1050' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_MUDANCA" title='<fmt:message key="itemConfiguracaoTree.pesquisaMudanca"/>'>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<fmt:message key="itemConfiguracaoTree.mudancas"/>
								<img src="${ctx}/imagens/add.png" onclick="popup.abrePopup('requisicaoMudanca')">
								</label>
							</div>
							<form name='formPesquisaMudanca' style="width: 540px">
								<cit:findField formName='formPesquisaMudanca'
									lockupName='LOOKUP_MUDANCA' id='LOOKUP_MUDANCA' top='0'
									left='0' len='1050' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_LIBERACAO" title='<fmt:message key="gerenciarequisicao.pesquisaliberacao"/>'>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<fmt:message key="liberacao"/>
								<img src="${ctx}/imagens/add.png" onclick="popup.abrePopup('requisicaoLiberacao')">
								</label>
							</div>
							<form name='formPesquisaLiberacao' style="width: 540px">
								<cit:findField formName='formPesquisaLiberacao'
									lockupName='LOOKUP_LIBERACAO' id='LOOKUP_LIBERACAO' top='0'
									left='0' len='1050' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="POPUP_IMPACTO" title="<fmt:message key="itemConfiguracaoTree.impactos"/>">
			<div id='divImpactos'  style='width: 100%'>
			</div>
		</div>

		<div id="POPUP_EDITARPROBLEMA"  style="overflow: hidden;" title="<fmt:message key="problema.problema"/>">
		<iframe id='iframeEditarProblema' src='about:blank' width="100%" height="100%" style='width: 100%; height: 100%; border:none;' onload="resize_iframe()">

		</iframe>
	</div>

	</body>

</html>
