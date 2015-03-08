<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>
<%@include file="/include/security/security.jsp" %>
<!--[if lt IE 7]> <html lang="en-us" class="no-js ie6"> <![endif]-->
<!--[if IE 7]>    <html lang="en-us" class="no-js ie7"> <![endif]-->
<!--[if IE 8]>    <html lang="en-us" class="no-js ie8"> <![endif]-->
<!--[if IE 9]>    <html lang="en-us" class="no-js ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en-us" class="no-js"> <!--<![endif]-->
<%@include file="/include/header.jsp"%>

<title><fmt:message key="citcorpore.comum.title" /></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
<script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
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
<script>
	var defineLookup = '';
	var contGrupo = 0;
	var contIConfig = 0;
	var contItemConfig = 0;

	addEvent(window, "load", load, false);
	function load(){
		document.form.afterRestore = function () {
			$('.tabs').tabs('select', 0);
		}
	}

	function gravar() {
		if (document.getElementById('tblGrupo').rows.length == 1 && document.getElementById('tblIConfig').rows.length == 1){
			alert('Adicione pelo menos um "Grupo" ou "Item de Configuração"!');
			return;
		}
		if (document.getElementById('tblItemConfig').rows.length == 1){
			alert('Adicione pelo menos um item de execução!');
			return;
		}

// 		if (document.form.idItemCfg.value != ''){
// 			alert('Adicione o item de configuração antes de gravar!');
// 			return;
// 		}

		document.form.save();
	}

	function excluir(){
		if (confirm('Deseja realmente excluir o evento')){
			document.form.fireEvent("delete");
		}
	}

	function LOOKUP_EVENTOS_select(id, desc){
		document.form.restore({idEvento:id});
	}

	function LOOKUP_GRUPOITEMCONFIGURACAO_select(id, desc) {
		addLinhaTabelaGrupoItemConfig(id, desc, true);
	}

	function LOOKUP_ITEMCONFIGURACAO_MAQUINA_select(id, desc) {
		addLinhaTabelaItemConfig(id, desc, true);
	}

	function LOOKUP_BASEITEMCONFIGURACAO_DESINSTALACAO_select(id, desc){
		document.form.idItemCfg.value = id;
		document.form.nomeItemCfg.value = desc;
		$("#POPUP_BASEITEMCONFIGURACAO_DES").dialog("close");
	}

	function LOOKUP_BASEITEMCONFIGURACAO_INSTALACAO_select(id, desc){
		document.form.idItemCfg.value = id;
		document.form.nomeItemCfg.value = desc;
		document.form.fireEvent("restoreIdentificacao");
		$("#POPUP_BASEITEMCONFIGURACAO_INS").dialog("close");
	}


	function addLinhaTabelaGrupoItemConfig(id, desc, valida) {
		var tbl = document.getElementById('tblGrupo');
		tbl.style.display = 'block';
		var lastRow = tbl.rows.length;
		if (valida){
			if (!validaAddLinhaTabelaGrupoItemConfig(lastRow, id)){
				return;
			}
		}
		var row = tbl.insertRow(lastRow);
		var coluna = row.insertCell(0);
		contGrupo++;
		coluna.innerHTML = '<img id="imgDelGrupo' + contGrupo + '" style="cursor: pointer;" title="Clique para excluir o grupo!" src="${ctx}/imagens/delete.png" onclick="removeLinhaTabela(\'tblGrupo\', this.parentNode.parentNode.rowIndex);">';
		coluna = row.insertCell(1);
		coluna.innerHTML = desc + '<input type="hidden" id="idGrupo' + contGrupo + '" name="idGrupo" value="' + id + '" />';
		$("#POPUP_GRUPO_ITEM_CONFIG").dialog("close");

	}

	function addLinhaTabelaItemConfig(id, desc, valida) {
		var tbl = document.getElementById('tblIConfig');
		tbl.style.display = 'block';
		var lastRow = tbl.rows.length;
		if (valida){
			if (!validaAddLinhaTabelaItemConfig(lastRow, id)){
				return;
			}
		}
		var row = tbl.insertRow(lastRow);
		var coluna = row.insertCell(0);
		contIConfig++;
		coluna.innerHTML = '<img id="imgDelItemConfig' + contIConfig + '" style="cursor: pointer;" title="Clique para excluir o item de configuração!" src="${ctx}/imagens/delete.png" onclick="removeLinhaTabela(\'tblIConfig\', this.parentNode.parentNode.rowIndex);">';
		coluna = row.insertCell(1);
		coluna.innerHTML = desc + '<input type="hidden" id="idItemConfiguracao' + contIConfig + '" name="idItemConfiguracao" value="' + id + '" />';
		$("#POPUP_ITEM_CONFIG").dialog("close");

	}

	function validaAddLinhaTabelaGrupoItemConfig(lastRow, id) {
		if (lastRow > 2){
			var arrayIdGrupo = document.form.idGrupo;
			for (var i = 0; i < arrayIdGrupo.length; i++){
				if (arrayIdGrupo[i].value == id){
					alert('Grupo já adicionado!');
					return false;
				}
			}
		} else if (lastRow == 2){
			var idGrupo = document.form.idGrupo;
			if (idGrupo.value == id){
				alert('Grupo já adicionado!');
				return false;
			}
		}
		return true;
	}

	function validaAddLinhaTabelaItemConfig(lastRow, id) {
		if (lastRow > 2){
			var arrayidItemConfiguracao = document.form.idItemConfiguracao;
			for (var i = 0; i < arrayidItemConfiguracao.length; i++){
				if (arrayidItemConfiguracao[i].value == id){
					alert('Item de configuração já adicionado!');
					return false;
				}
			}
		} else if (lastRow == 2){
			var idItemConfiguracao = document.form.idItemConfiguracao;
			if (idItemConfiguracao.value == id){
				alert('Item de configuração já adicionado!');
				return false;
			}
		}
		return true;
	}

	function alteraLinhaTabela(idTabela, rowIndex, hiddenIndex){
		if (eval('document.getElementById("tipoExecucao' + hiddenIndex + '").value') == 'I'){
			document.form.tipoExec[0].checked = true;
			//document.form.execucaoPadrao.disabled = false;
		} else if (eval('document.getElementById("tipoExecucao' + hiddenIndex + '").value') == 'D'){
			document.form.tipoExec[1].checked = true;
			//document.form.execucaoPadrao.disabled = true;
		}/* else {
			document.form.tipoExec[2].checked = true;
			document.form.execucaoPadrao.disabled = false;
		} */

		document.form.idItemCfg.value = eval('document.getElementById("idItemConfig' + hiddenIndex + '").value');
		document.form.nomeItemCfg.value = eval('document.getElementById("nomeItemConfig' + hiddenIndex + '").value');
		document.form.nomeItemCfg.disabled = false;

		if (eval('document.getElementById("disparaEvento' + hiddenIndex + '").value') == 'A'){
			document.form.gerarQuando[0].checked = true;
		} else {
			document.form.gerarQuando[1].checked = true;
			document.form.data.disabled = false;
			document.form.hora.disabled = false;
		}
		document.form.data.value = eval('document.getElementById("dataEvento' + hiddenIndex + '").value');
		document.form.hora.value = eval('document.getElementById("horaEvento' + hiddenIndex + '").value');
		if (eval('document.getElementById("comando' + hiddenIndex + '").value') != ''){
			//document.form.execucaoPadrao.checked = false;
			document.form.linhaComando.value = eval('document.getElementById("comando' + hiddenIndex + '").value');
			//document.form.linhaComando.disabled = false;
		}

		HTMLUtils.deleteRow(idTabela, rowIndex);
		var tbl = document.getElementById(idTabela);
		if (tbl.rows.length == 1){
			if (idTabela == 'tblItemConfig'){
				document.getElementById('dvItemConfig').style.display = 'none';
				return;
			}
			tbl.style.display = 'none';
		}
	}

	function removeLinhaTabela(idTabela, rowIndex) {
		if (confirm('Deseja realmente excluir')){
			HTMLUtils.deleteRow(idTabela, rowIndex);
			var tbl = document.getElementById(idTabela);
			if (tbl.rows.length == 1){
				if (idTabela == 'tblItemConfig'){
					document.getElementById('dvItemConfig').style.display = 'none';
					return;
				}
				tbl.style.display = 'none';
			}
		}
	}

	function abreLookupItemConfig(){
		if (document.form.tipoExec[0].checked){
			$("#POPUP_BASEITEMCONFIGURACAO_INS").dialog("open");
		} else {
			$("#POPUP_BASEITEMCONFIGURACAO_DES").dialog("open");
		}
	}

	function limpaFormulario() {
		document.form.clear();
		contGrupo = 0;
		contIConfig = 0;
		contItemConfig = 0;
		HTMLUtils.deleteAllRows('tblGrupo');
		HTMLUtils.deleteAllRows('tblIConfig');
		HTMLUtils.deleteAllRows('tblItemConfig');
		limpar_LOOKUP_GRUPOITEMCONFIGURACAO();
		limpar_LOOKUP_ITEMCONFIGURACAO_MAQUINA();
		limpar_LOOKUP_BASEITEMCONFIGURACAO_DESINSTALACAO();
		limpar_LOOKUP_BASEITEMCONFIGURACAO_INSTALACAO();
		document.getElementById('tblGrupo').style.display = 'none';
		document.getElementById('tblIConfig').style.display = 'none';
		document.getElementById('dvItemConfig').style.display = 'none';
		document.form.data.disabled = true;
		document.form.hora.disabled = true;
		document.form.nomeItemCfg.disabled = true;
		//document.form.linhaComando.disabled = true;
		//document.form.execucaoPadrao.checked = true;
		//document.form.execucaoPadrao.disabled = false;
		document.form.btnExcluir.style.display = 'none';
		limparDivLinux();
	}

	function camposDisabled(aux) {
		if (aux){
			document.form.data.disabled = false;
			document.form.hora.disabled = false;
			document.form.data.focus();
		} else {
			document.form.data.value = '';
			document.form.hora.value = '';
			document.form.data.disabled = true;
			document.form.hora.disabled = true;
		}
	}

	function disabledLinhaComando(obj) {
		if (!obj.checked){
			document.form.linhaComando.disabled = false;
			document.form.linhaComando.focus();
		} else {
			document.form.linhaComando.disabled = true;
			document.form.linhaComando.value = '';
		}
	}

	function limpaCamposItemConfig(obj){
		document.form.idItemCfg.value = '';
		document.form.nomeItemCfg.value = '';
		document.form.nomeItemCfg.disabled = false;
		/*if (obj.value == 'D'){
			document.form.execucaoPadrao.checked = false;
			document.form.execucaoPadrao.disabled = true;
			document.form.linhaComando.disabled = false;
		} else {
			document.form.execucaoPadrao.disabled = false;
		}*/
	}

	function addItemConfig() {
		//if (!document.form.tipoExec[0].checked && !document.form.tipoExec[1].checked && !document.form.tipoExec[2].checked){
		if (!document.form.tipoExec[0].checked && !document.form.tipoExec[1].checked){
			alert('Informe o tipo de execução!');
			document.form.tipoExec[0].focus();
			return;
		}
		if (document.form.nomeItemCfg.value == '' || document.form.idItemCfg.value == ''){
			alert('Informe o item de configuração!');
			return;
		}
		if (!document.form.gerarQuando[0].checked && !document.form.gerarQuando[1].checked){
			alert('Informe quando gerar!');
			document.form.gerarQuando[0].focus();
			return;
		}
		if (document.form.gerarQuando[1].checked){
			if (document.form.data.value == ''){
				alert('Informe a data!');
				document.form.data.focus();
				return;
			}
			if (document.form.hora.value == ''){
				alert('Informe a hora!');
				document.form.hora.focus();
				return;
			}
			if (!ValidacaoUtils.validaData(document.form.data, 'Campo Data: ')){
				return;
			}
			if (!ValidacaoUtils.validaHora(document.form.hora, 'Campo Hora: ')){
				return;
			}
			if (comparaComDataAtual(document.form.data) == -1){
				alert('A data não pode ser menor que a data atual!');
				return;
			}
			if (comparaComDataAtual(document.form.data) == 0){
				if (!comparaComHoraAtual(document.form.hora.value)){
					alert('A hora não pode ser menor que a hora atual!');
					return;
				}
			}
		}
		/*if (!document.form.execucaoPadrao.checked && document.form.linhaComando.value == ''){
			alert('Informe a linha de comando!');
			return;
		}*/
		if (document.form.linhaComando.value == ''){
			alert('Informe a linha de comando!');
			return;
		}
		if (document.form.eLinux.checked == true) {
			if (document.form.linhaComandoLinux.value == '') {
				alert('Informe a Linha de Comando Antecessora!');
				return;
			}
		}
		document.getElementById('dvItemConfig').style.display = 'block';
		var tbl = document.getElementById('tblItemConfig');
		var lastRow = tbl.rows.length;
		if (lastRow > 2){
			var arrayIdItemConfig = document.form.idItemConfig;
			var arrayTipoExecucao = document.form.tipoExecucao;
			for (var i = 0; i < arrayIdItemConfig.length; i++){
				if (document.form.idItemCfg.value == arrayIdItemConfig[i].value){
					if (document.form.tipoExec[0].checked){
						if (arrayTipoExecucao[i].value == 'I'){
							alert('Este Item de Configuração já foi adicionado para Instalação!');
							return;
						}
					} else if (document.form.tipoExec[1].checked) {
						if (arrayTipoExecucao[i].value == 'D'){
							alert('Este Item de Configuração já foi adicionado para Desinstalação!');
							return;
						}
					}/* else {
						if (arrayTipoExecucao[i].value == 'A'){
							alert('Este Item de Configuração já foi adicionado para Alteração!');
							return;
						}
					} */
				}
			}
		} else if (lastRow == 2){
			var idItemConfig = document.form.idItemConfig;
			var tipoExecucao = document.form.tipoExecucao;
			if (document.form.idItemCfg.value == idItemConfig.value){
				if (document.form.tipoExec[0].checked){
					if (tipoExecucao.value == 'I'){
						alert('Este Item de Configuração já foi adicionado para Instalação!');
						return;
					}
				} else if (document.form.tipoExec[1].checked) {
					if (tipoExecucao.value == 'D'){
						alert('Este Item de Configuração já foi adicionado para Desinstalação!');
						return;
					}
				}/* else {
					if (tipoExecucao.value == 'A'){
						alert('Este Item de Configuração já foi adicionado para Alteração!');
						return;
					}
				} */
			}
		}
		var row = tbl.insertRow(lastRow);
		var coluna = row.insertCell(0);
		contItemConfig++;
		coluna.innerHTML = '<img id="imgAltItemConfig' + contItemConfig + '" style="cursor: pointer;" title="Clique para alterar o item de configuração!" src="${ctx}/imagens/edit.png" onclick="alteraLinhaTabela(\'tblItemConfig\', this.parentNode.parentNode.rowIndex, ' + contItemConfig + ');">';
		coluna = row.insertCell(1);
		coluna.innerHTML = '<img id="imgDelItemConfig' + contItemConfig + '" style="cursor: pointer;" title="Clique para excluir o item de configuração!" src="${ctx}/imagens/delete.png" onclick="removeLinhaTabela(\'tblItemConfig\', this.parentNode.parentNode.rowIndex);">';
		coluna = row.insertCell(2);
		if (document.form.tipoExec[0].checked){
			coluna.innerHTML = 'Instalação' + '<input type="hidden" id="tipoExecucao' + contItemConfig + '" name="tipoExecucao" value="' + document.form.tipoExec[0].value + '" />';
			document.form.tipoExec[0].checked = false;
		} else if (document.form.tipoExec[1].checked) {
			coluna.innerHTML = 'Desinstalação' + '<input type="hidden" id="tipoExecucao' + contItemConfig + '" name="tipoExecucao" value="' + document.form.tipoExec[1].value + '" />';
			document.form.tipoExec[1].checked = false;
		}/* else {
			coluna.innerHTML = 'Atualização' + '<input type="hidden" id="tipoExecucao' + contItemConfig + '" name="tipoExecucao" value="' + document.form.tipoExec[2].value + '" />';
			document.form.tipoExec[2].checked = false;
		} */
		coluna = row.insertCell(3);
		coluna.innerHTML = document.form.nomeItemCfg.value + '<input type="hidden" id="idItemConfig' + contItemConfig + '" name="idItemConfig" value="' + document.form.idItemCfg.value + '" /><input type="hidden" id="nomeItemConfig' + contItemConfig + '" name="nomeItemConfig" value="' + document.form.nomeItemCfg.value + '" />';
		document.form.nomeItemCfg.value = '';
		document.form.idItemCfg.value = '';
		document.form.nomeItemCfg.disabled = true;
		coluna = row.insertCell(4);
		if (document.form.gerarQuando[0].checked){
			coluna.innerHTML = 'Agora' + '<input type="hidden" id="disparaEvento' + contItemConfig + '" name="disparaEvento" value="' + document.form.gerarQuando[0].value + '" />';
			document.form.gerarQuando[0].checked = false;
			coluna = row.insertCell(5);
			coluna.innerHTML = '<input type="hidden" id="dataEvento' + contItemConfig + '" name="dataEvento" value="" />';
			coluna = row.insertCell(6);
			coluna.innerHTML = '<input type="hidden" id="horaEvento' + contItemConfig + '" name="horaEvento" value="" />';
		} else {
			coluna.innerHTML = 'Data Futura' + '<input type="hidden" id="disparaEvento' + contItemConfig + '" name="disparaEvento" value="' + document.form.gerarQuando[1].value + '" />';
			document.form.gerarQuando[1].checked = false;
			coluna = row.insertCell(5);
			coluna.innerHTML = '<div align="center">' + document.form.data.value + '</div><input type="hidden" id="dataEvento' + contItemConfig + '" name="dataEvento" value="' + document.form.data.value + '" />';
			document.form.data.disabled = true;
			coluna = row.insertCell(6);
			coluna.innerHTML = '<div align="center">' + document.form.hora.value + '</div><input type="hidden" id="horaEvento' + contItemConfig + '" name="horaEvento" value="' + document.form.hora.value + '" />';
			document.form.hora.disabled = true;
		}
		document.form.data.value = '';
		document.form.hora.value = '';

		coluna = row.insertCell(7);
		coluna.innerHTML = document.form.linhaComando.value + '<input type="hidden" id="comando' + contItemConfig + '" name="comando" value="' + document.form.linhaComando.value + '" />';
		coluna = row.insertCell(8);
		coluna.innerHTML = document.form.linhaComandoLinux.value + '<input type="hidden" id="comandoLinux' + contItemConfig + '" name="comandoLinux" value="' + document.form.linhaComandoLinux.value + '" />';
		document.form.linhaComando.value = '';
		//document.form.linhaComando.disabled = true;
		//document.form.execucaoPadrao.checked = true;
		//document.form.execucaoPadrao.disabled = false;
		document.form.linhaComandoLinux.value = '';
		document.form.eLinux.checked = false;
		limparDivLinux();
	}

	function addItemConfigRestore(idItemConfig, identificacao, tipoExecucao, gerarQuando, data, hora, linhaComando, linhaComandoLinux){
		document.getElementById('dvItemConfig').style.display = 'block';
		var tbl = document.getElementById('tblItemConfig');
		var lastRow = tbl.rows.length;
		var row = tbl.insertRow(lastRow);
		var coluna = row.insertCell(0);
		contItemConfig++;
		coluna.innerHTML = '<img id="imgAltItemConfig' + contItemConfig + '" style="cursor: pointer;" title="Clique para alterar o item de configuração!" src="${ctx}/imagens/edit.png" onclick="alteraLinhaTabela(\'tblItemConfig\', this.parentNode.parentNode.rowIndex, ' + contItemConfig + ');">';
		coluna = row.insertCell(1);
		coluna.innerHTML = '<img id="imgDelItemConfig' + contItemConfig + '" style="cursor: pointer;" title="Clique para excluir o item de configuração!" src="${ctx}/imagens/delete.png" onclick="removeLinhaTabela(\'tblItemConfig\', this.parentNode.parentNode.rowIndex);">';
		coluna = row.insertCell(2);
		if (tipoExecucao == 'I'){
			coluna.innerHTML = 'Instalação' + '<input type="hidden" id="tipoExecucao' + contItemConfig + '" name="tipoExecucao" value="' + tipoExecucao + '" />';
		} else if (tipoExecucao == 'D') {
			coluna.innerHTML = 'Desinstalação' + '<input type="hidden" id="tipoExecucao' + contItemConfig + '" name="tipoExecucao" value="' + tipoExecucao + '" />';
		}/* else {
			coluna.innerHTML = 'Atualização' + '<input type="hidden" id="tipoExecucao' + contItemConfig + '" name="tipoExecucao" value="' + tipoExecucao + '" />';
		} */
		coluna = row.insertCell(3);
		coluna.innerHTML = identificacao + '<input type="hidden" id="idItemConfig' + contItemConfig + '" name="idItemConfig" value="' + idItemConfig + '" /><input type="hidden" id="nomeItemConfig' + contItemConfig + '" name="nomeItemConfig" value="' + identificacao + '" />';
		coluna = row.insertCell(4);
		if (gerarQuando == 'A'){
			coluna.innerHTML = 'Agora' + '<input type="hidden" id="disparaEvento' + contItemConfig + '" name="disparaEvento" value="' + gerarQuando + '" />';
		} else {
			coluna.innerHTML = 'Data Futura' + '<input type="hidden" id="disparaEvento' + contItemConfig + '" name="disparaEvento" value="' + gerarQuando + '" />';
		}
		coluna = row.insertCell(5);
		coluna.innerHTML = '<div align="center">' + data + '</div><input type="hidden" id="dataEvento' + contItemConfig + '" name="dataEvento" value="' + data + '" />';
		coluna = row.insertCell(6);
		coluna.innerHTML = '<div align="center">' + hora + '</div><input type="hidden" id="horaEvento' + contItemConfig + '" name="horaEvento" value="' + hora + '" />';
		coluna = row.insertCell(7);
		coluna.innerHTML = linhaComando + '<input type="hidden" id="comando' + contItemConfig + '" name="comando" value="' + linhaComando + '" />';
		coluna = row.insertCell(8);
		coluna.innerHTML = linhaComandoLinux + '<input type="hidden" id="comandoLinux' + contItemConfig + '" name="comandoLinux" value="' + linhaComandoLinux + '" />';
	}

	function verificarComandoLinux(e) {
	    if (e.target.checked) {
	    	document.getElementById('divComandoLinux').style.display = 'block';
	    	document.getElementById('divComando').setAttribute('class', 'col_33');
	    } else {
	    	limparDivLinux();
	    }
	}

	function limparDivLinux() {
    	document.getElementById('divComandoLinux').style.display = 'none';
    	document.getElementById('divComando').setAttribute('class', 'col_66');
    	document.getElementById('eLinux').removeAttribute('checked');
	}

	$(function() {
		$('.datepicker').datepicker();
	});

	$(function() {
		$("#POPUP_GRUPO_ITEM_CONFIG").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true,
			show: "fade",
			hide: "fade"
		});
	});

	$(function() {
		$("#POPUP_ITEM_CONFIG").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true,
			show: "fade",
			hide: "fade"
		});
	});

	$(function() {
		$("#POPUP_BASEITEMCONFIGURACAO_DES").dialog({
			autoOpen : false,
			width : 600,
			height : 600,
			modal : true,
			show: "fade",
			hide: "fade"
		});
	});

	$(function() {
		$("#POPUP_BASEITEMCONFIGURACAO_INS").dialog({
			autoOpen : false,
			width : 600,
			height : 600,
			modal : true,
			show: "fade",
			hide: "fade"
		});
	});

	$(function() {
		$("#addGrupoItemConfig").click(function() {
			$("#POPUP_GRUPO_ITEM_CONFIG").dialog("open");
		});
	});

	$(function() {
		$("#addItemConfig").click(function() {
			$("#POPUP_ITEM_CONFIG").dialog("open");
		});
	});
</script>
</head>
<body>

	<div class="flat_area grid_16">
		<h2>Eventos</h2>
	</div>

	<div class="box grid_16 tabs">
		<ul class="tab_header clearfix">
			<li>
				<a href="#tabs-1"><fmt:message key="eventoItemConfiguracao.cadastro" /></a>
			</li>
			<li>
				<a href="#tabs-2" class="round_top"><fmt:message key="eventoItemConfiguracao.pesquisa" /></a>
			</li>
		</ul>
	<a href="#" class="toggle">&nbsp;</a>
	<div class="toggle_container">
	<div id="tabs-1" class="block">
	<div class="section">
	<form name='form' action='${ctx}/pages/eventoItemConfig/eventoItemConfig'>
	<div class="columns clearfix">
		<input type='hidden' name='idEvento'/>
		<input type='hidden' name='dataInicio'/>
		<div class="col_66">
			<fieldset>
				<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.descricao" /></label>
				<div>
					<input type='text' id="descricao" name="descricao" maxlength="100" class="Valid[Required] Description[Descrição]" />
				</div>
			</fieldset>
		</div>
		<div class="col_33">
			<fieldset>
				<label class="campoObrigatorio"><fmt:message key="eventoItemConfiguracao.ligarMaquina" /></label>
				<div class="inline clearfix">
					<label><input type='radio' id="ligarCasoDesl" name="ligarCasoDesl" value="S" class="Valid[Required] Description[Ligar a máquina]" /><fmt:message key="eventoItemConfiguracao.ligarMaquinaSim" /></label>
					<label><input type='radio' id="ligarCasoDesl" name="ligarCasoDesl" value="N" class="Valid[Required] Description[Ligar a máquina]" /><fmt:message key="eventoItemConfiguracao.ligarMaquinaNao" /></label>
				</div>
			</fieldset>
		</div>
	</div>

	<div class="columns clearfix">
		<div class="col_50">
			<fieldset>
				<label class="campoObrigatorio"><fmt:message key="eventoItemConfiguracao.usuario" /></label>
				<div>
					<input type='text' id="usuario" name="usuario" maxlength="100" class="Valid[Required] Description[Usuário]" />
				</div>
			</fieldset>
		</div>
		<div class="col_50">
			<fieldset>
				<label class="campoObrigatorio"><fmt:message key="eventoItemConfiguracao.senha" /></label>
				<div>
					<input type="password" id="senha" name="senha" maxlength="100" class="Valid[Required] Description[Senha]" />
				</div>
			</fieldset>
		</div>
	</div>

	<h2 class="section"><fmt:message key="eventoItemConfiguracao.alvos" /></h2>

	<div class="columns clearfix">
		<div class="col_100">
			<fieldset>
				<label class="campoObrigatorio" id="addGrupoItemConfig" style="cursor: pointer;" title="Clique para adicionar um grupo de item de configuração!"><fmt:message key="eventoItemConfiguracao.grupos" /><img src="${ctx}/imagens/add.png"></label>
				<div>
					<table class="table" id="tblGrupo" style="display: none;">
						<tr>
							<th style="width: 1%;"></th>
							<th style="width: 99%;"><fmt:message key="eventoItemConfiguracao.grupos" /></th>
						</tr>
					</table>
				</div>
			</fieldset>
		</div>
	</div>

	<div class="columns clearfix">
		<div class="col_100">
			<fieldset>
				<label class="campoObrigatorio" id="addItemConfig" style="cursor: pointer;" title="Clique para adicionar um item de configuração!"><fmt:message key="eventoItemConfiguracao.itensConfiguracao" /><img src="${ctx}/imagens/add.png"></label>
				<div>
					<table class="table" id="tblIConfig" style="display: none;">
						<tr>
							<th style="width: 1%;"></th>
							<th style="width: 99%;"><fmt:message key="eventoItemConfiguracao.itensConfiguracao" /></th>
						</tr>
					</table>
				</div>
			</fieldset>
		</div>
	</div>

	<h2 class="section"><fmt:message key="eventoItemConfiguracao.itensExecucao"/></h2>

	<div class="columns clearfix">
		<div class="col_33">
			<fieldset>
				<label class="campoObrigatorio"><fmt:message key="eventoItemConfiguracao.tipoExecucao"/></label>
				<div class="inline clearfix">
					<label><input type='radio' id="tipoExec" name="tipoExec" value="I" onchange="limpaCamposItemConfig(this);" /><fmt:message key="eventoItemConfiguracao.instalacao"/></label>
					<label><input type='radio' id="tipoExec" name="tipoExec" value="D" onchange="limpaCamposItemConfig(this);" /><fmt:message key="eventoItemConfiguracao.desinstalacao"/></label>
					<!-- <label><input type='radio' id="tipoExec" name="tipoExec" value="A" onchange="limpaCamposItemConfig(this);" />Atualização</label> -->
				</div>
			</fieldset>
		</div>
		<div class="col_66">
			<fieldset>
				<label class="campoObrigatorio"><fmt:message key="eventoItemConfiguracao.identificacao"/></label>
				<div>
					<input type="text" id="nomeItemCfg" name="nomeItemCfg" disabled="disabled" readonly="readonly" onfocus="abreLookupItemConfig()" />
					<input type="hidden" name="idItemCfg" />
				</div>
			</fieldset>
		</div>
	</div>

	<div class="columns clearfix">
		<div class="col_33">
			<fieldset>
				<label class="campoObrigatorio"><fmt:message key="eventoItemConfiguracao.gerarQuando"/></label>
				<div class="inline clearfix">
					<label><input type="radio" id="gerarQuando" name="gerarQuando" value="A" onchange="camposDisabled(false);" /><fmt:message key="eventoItemConfiguracao.agora"/></label>
					<label><input type="radio" id="gerarQuando" name="gerarQuando" value="F" onchange="camposDisabled(true);" /><fmt:message key="eventoItemConfiguracao.dataFutura"/></label>
				</div>
			</fieldset>
		</div>
		<div class="col_33">
			<fieldset>
				<label><fmt:message key="eventoItemConfiguracao.data"/></label>
				<div>
					<input type='text' id="data" name="data" maxlength="10" disabled="disabled" class="Format[Data] datepicker" />
				</div>
			</fieldset>
		</div>
		<div class="col_33">
			<fieldset>
				<label><fmt:message key="eventoItemConfiguracao.hora"/></label>
				<div>
					<input type='text' id="hora" name="hora" maxlength="5" disabled="disabled" class="Format[Hora]" />
				</div>
			</fieldset>
		</div>
	</div>

	<div class="columns clearfix">
		<!-- <div class="col_33">
			<fieldset>
				<label>Execução Padrão</label>
				<div style="margin-top: 10px;">
					<label><input type="checkbox" id="execucaoPadrao" name="execucaoPadrao" checked="checked" onclick="disabledLinhaComando(this);" /></label>
				</div>
			</fieldset>
		</div> -->
		<div class="col_33">
			<fieldset>
				<label ><fmt:message key="eventoItemConfiguracao.linhaComando"/></label>
				<div class="inline clearfix">
					<label><input type="checkbox" id="eLinux" name="eLinux" onclick="verificarComandoLinux(event)" /><fmt:message key="eventoItemConfiguracao.eLinux"/></label>
				</div>
			</fieldset>
		</div>
		<div class="col_33" id="divComandoLinux" style="display: none;">
			<fieldset>
				<label class="campoObrigatorio"><fmt:message key="eventoItemConfiguracao.linhaComandoLinux"/><img src="${ctx}/imagens/help_ico.gif" title="Linha de comando que virá antes do nome do arquivo de instalação no Linux"></label>
				<div>
					<input type='text' id="linhaComandoLinux" name="linhaComandoLinux" maxlength="200" />
				</div>
			</fieldset>
		</div>
		<div class="col_66" id="divComando">
			<fieldset>
				<label class="campoObrigatorio"><fmt:message key="eventoItemConfiguracao.linhaComando"/></label>
				<div>
					<!-- <input type='text' id="linhaComando" name="linhaComando" maxlength="150" disabled="disabled" /> -->
					<input type='text' id="linhaComando" name="linhaComando" maxlength="200" />
				</div>
			</fieldset>
		</div>
	</div>

	<br>

	<button type='button' id="btnAdicionar" name='btnAdicionar' class="light" onclick="addItemConfig()">
		<img src="${ctx}/template_new/images/icons/small/grey/computer_imac.png">
		<span><fmt:message key="eventoItemConfiguracao.adicionar"/></span>
	</button>

	<br><br>

	<div class="columns clearfix" id="dvItemConfig" style="display: none;">
		<div class="col_100">
			<fieldset>
			<label><fmt:message key="eventoItemConfiguracao.itensAdicionados"/></label>
				<div>
				<table class="table" id="tblItemConfig">
					<tr>
						<th style="width: 1%;"></th>
						<th style="width: 1%;"></th>
						<th style="width: 12%;"><fmt:message key="eventoItemConfiguracao.tipoExecucao"/></th>
						<th style="width: 25%;"><fmt:message key="eventoItemConfiguracao.identificacao"/></th>
						<th style="width: 8%;"><fmt:message key="eventoItemConfiguracao.gerarQuando"/></th>
						<th style="width: 7%;"><fmt:message key="eventoItemConfiguracao.data"/></th>
						<th style="width: 6%;"><fmt:message key="eventoItemConfiguracao.hora"/></th>
						<th style="width: 20%;"><fmt:message key="eventoItemConfiguracao.linhaComando"/></th>
						<th style="width: 20%;"><fmt:message key="eventoItemConfiguracao.linhaComandoLinux"/></th>
					</tr>
				</table>
				</div>
			</fieldset>
		</div>
	</div>

	<br>

	<button type='button' name='btnGravar' class="light" onclick='gravar();'>
		<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
		<span><fmt:message key="citcorpore.comum.gravar"/></span>
	</button>
	<button type='reset' name='btnLimpar' class="light" onclick="limpaFormulario();">
		<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
		<span><fmt:message key="citcorpore.comum.limpar"/></span>
	</button>
	<button type='button' name='btnExcluir' class="light" onclick='excluir();' style="display: none;">
		<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
		<span><fmt:message key="citcorpore.comum.excluir"/></span>
	</button>

	</form>
	</div>
	</div>
	<div id="tabs-2" class="block">
	<div class="section">
	<fmt:message key="citcorpore.comum.pesquisa"/>
	<form name='formPesquisa'>
		<cit:findField formName='formPesquisa' lockupName='LOOKUP_EVENTOS' id='LOOKUP_EVENTOS' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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

	<!-- POPUP ITEM CONFIGURAÇÃO -->
	<div id="POPUP_ITEM_CONFIG" title="Pesquisa Item de Configuração">
		<div class="box grid_16 tabs">
		<div class="toggle_container">
		<div id="tabs-2" class="block">
		<div class="section">
			<form name='formPesquisaItemConfig' style="width: 540px">
				<cit:findField formName='formPesquisaItemConfig' lockupName='LOOKUP_ITEMCONFIGURACAO_MAQUINA' id='LOOKUP_ITEMCONFIGURACAO_MAQUINA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
			</form>
		</div>
		</div>
		</div>
		</div>
	</div>
	<!-- Fim POPUP ITEM CONFIGURAÇÃO-->

	<!-- POPUP GRUPO ITEM CONFIGURAÇÃO -->
	<div id="POPUP_GRUPO_ITEM_CONFIG" title="Pesquisa Grupo de Item de Configuração">
		<div class="box grid_16 tabs">
		<div class="toggle_container">
		<div id="tabs-2" class="block">
		<div class="section">
			<form name='formPesquisaGrupoItemConfig' style="width: 540px">
				<cit:findField formName='formPesquisaGrupoItemConfig' lockupName='LOOKUP_GRUPOITEMCONFIGURACAO' id='LOOKUP_GRUPOITEMCONFIGURACAO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
			</form>
		</div>
		</div>
		</div>
		</div>
	</div>
	<!-- Fim POPUP GRUPO ITEM CONFIGURAÇÃO-->

	<!-- POPUP BASE ITEM CONFIGURACAO DESINSTALACAO -->
	<div id="POPUP_BASEITEMCONFIGURACAO_DES" title="Pesquisa Software Desinstalação">
		<div class="box grid_16 tabs">
		<div class="toggle_container">
		<div id="tabs-2" class="block">
		<div class="section">
			<form name='formPesquisaBaseItemCfgDes' style="width: 540px">
				<cit:findField formName='formPesquisaBaseItemCfgDes' lockupName='LOOKUP_BASEITEMCONFIGURACAO_DESINSTALACAO' id='LOOKUP_BASEITEMCONFIGURACAO_DESINSTALACAO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
			</form>
		</div>
		</div>
		</div>
		</div>
	</div>
	<!-- Fim POPUP BASE ITEM CONFIGURACAO DESINSTALACAO -->

	<!-- POPUP BASE ITEM CONFIGURACAO INSTALACAO -->
	<div id="POPUP_BASEITEMCONFIGURACAO_INS" title="Pesquisa Software Instalação">
		<div class="box grid_16 tabs">
		<div class="toggle_container">
		<div id="tabs-2" class="block">
		<div class="section">
			<form name='formPesquisaBaseItemCfgIns' style="width: 540px">
				<cit:findField formName='formPesquisaBaseItemCfgIns' lockupName='LOOKUP_BASEITEMCONFIGURACAO_INSTALACAO' id='LOOKUP_BASEITEMCONFIGURACAO_INSTALACAO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
			</form>
		</div>
		</div>
		</div>
		</div>
	</div>
	<!-- Fim POPUP BASE ITEM CONFIGURACAO INSTALACAO -->

</body>
</html>