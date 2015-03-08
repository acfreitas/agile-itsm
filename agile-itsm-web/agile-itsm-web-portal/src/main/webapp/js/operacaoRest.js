$(function() {
	$("#POPUP_GRUPO").dialog({
		autoOpen : false,
		width : 650,
		height : 400,
		modal : true
	});
	$("#POPUP_PARAMETRO").dialog({
		autoOpen : false,
		width : 650,
		height : 270,
		modal : true
	});
	$("#POPUP_PESQUISA_PARAMETRO").dialog({
		autoOpen : false,
		width : 650,
		height : 400,
		modal : true
	});
	$("#addGrupo").click(function() {
		$("#POPUP_GRUPO").dialog("open");
	});
	$("#addParametro").click(function() {
		document.formCadastroParam.clear();
		$("#POPUP_PARAMETRO").dialog("open");
	});
});

fecharPopup = function() {
	$('.tabs').tabs('select', 0);
};

limpar = function() {
	document.form.clear();
	GRID_PARAMETROS.deleteAllRows();
	HTMLUtils.deleteAllRows('tabelaGrupo');

	$("#javaDiv").hide();
	$("#javaScriptDiv").hide();
};

var seqParametro = '';
incluirParametro = function() {
	GRID_PARAMETROS.addRow();
	seqParametro = NumberUtil.zerosAEsquerda(GRID_PARAMETROS.getMaxIndex(), 5);
	eval('document.form.idRestParameter' + seqParametro + '.focus()');
};

exibeParametro = function(serializeParametro) {
	if (seqParametro !== '') {
		if (!StringUtils.isBlank(serializeParametro)) {
			var obj = new RestDomain();
			obj = ObjectUtils.deserializeObject(serializeParametro);
			try{
				eval('HTMLUtils.setValue("idRestParameter' + seqParametro + '",' + obj.idRestParameter + ')');
			} catch(e) {}
			eval('document.form.value' + seqParametro + '.value = "' + obj.value + '"');
		}
	}
};

RestDomain = function() {
	this.idRestParameter = 0;
	this.idRestOperation = 0;
	this.value = '';
};

getParametro = function(seq) {
	var obj = new RestDomain();

	seqParametro = NumberUtil.zerosAEsquerda(seq,5);
	obj.sequencia = seq;
	obj.idRestParameter = parseInt(eval('document.form.idRestParameter' + seqParametro + '.value'));
	obj.value = eval('document.form.value' + seqParametro + '.value');
	return obj;
};

verificarParametro = function(seq) {
	var idRestParameter = eval('document.form.idRestParameter' + seq + '.value');
	var count = GRID_PARAMETROS.getMaxIndex();
	for (var i = 1; i <= count; i++) {
		if (parseInt(seq) != i) {
			var trObj = document.getElementById('GRID_PARAMETROS_TD_' + NumberUtil.zerosAEsquerda(i,5));
			if (!trObj) {
				continue;
			}
			var idAux = eval('document.form.idRestParameter' + NumberUtil.zerosAEsquerda(i,5) + '.value');
			if (idAux == idRestParameter) {
				alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
				eval('document.form.idRestParameter' + seq + '.focus()');
				return false;
			}
		}
	}
	return true;
};

tratarParametros = function() {
	var count = GRID_PARAMETROS.getMaxIndex();
	var contadorAux = 0;
	var objs = new Array();
	for (var i = 1; i <= count; i++) {
		var trObj = document.getElementById('GRID_PARAMETROS_TD_' + NumberUtil.zerosAEsquerda(i,5));
		if (!trObj) {
			continue;
		}

		var obj = getParametro(i);
		if (parseInt(obj.idRestParameter) > 0) {
			if (!verificarParametro(NumberUtil.zerosAEsquerda(i, 5))) {
				return false;
			}
			objs[contadorAux] = obj;
			contadorAux = contadorAux + 1;
		} else {
			alert(i18n_message("citcorpore.comum.nenhumaSelecao"));
			eval('document.form.idRestParameter' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');
			return false;
		}
	}
	document.form.colParametros_Serialize.value = ObjectUtils.serializeObjects(objs);
	return true;
};

validOnSave = function() {
	var classTypeValue = $("#classType:checked").val();
	if (classTypeValue === "Java" && !$("#javaClass").val()) {
		alert(i18n_message("operacaoRest.javaClass") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
		return false;
	} else if (classTypeValue === "JavaScript" && !$("#javaScript").val()) {
		alert(i18n_message("operacaoRest.javaScript") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
		return false;
	}
	return true;
};

gravar = function() {
	if (!validOnSave()) {
		return;
	}

	if (!tratarParametros()) {
		return;
	}

	var objs = HTMLUtils.getObjectsByTableId('tabelaGrupo');
	document.form.colGrupoSerialize.value = ObjectUtils.serializeObjects(objs);

	document.form.save();
	GRID_PARAMETROS.deleteAllRows();
	HTMLUtils.deleteAllRows('tabelaGrupo');

	$("#javaDiv").hide();
	$("#javaScriptDiv").hide();
};

var nomeGrupo;
LOOKUP_GRUPO_EVENTO_select = function(id, desc) {
	$("#divGrupo").show();
	document.form.idGrupo.value = id;
	nomeGrupo = desc;
	addGridGrupo();
	$("#POPUP_GRUPO").dialog("close");
};

LOOKUP_OPERACAO_REST_select = function(id, desc) {
	JANELA_AGUARDE_MENU.show();
	document.form.restore({idRestOperation: id});
};

addGridGrupo = function() {
	var tbl = document.getElementById('tabelaGrupo');
	tbl.style.display = 'block';
	var lastRow = tbl.rows.length;

	if(document.getElementById('rowIndex').value == null || document.getElementById('rowIndex').value == undefined || document.getElementById('rowIndex').value < 1) {
		var obj = new CIT_GrupoDTO();
		obj.idGrupo = document.form.idGrupo.value;
		obj.nome = nomeGrupo;

		HTMLUtils.addRow('tabelaGrupo', document.form, null, obj, ['','idGrupo','nome'], ['idGrupo'], i18n_message('citcorpore.comum.registroJaAdicionado'), [gerarButtonDeleteGrupo], null, null, false);
		nomeGrupo = "";
		document.form.idGrupo.value = "";
		novoItem();
	} else {
		var obj = HTMLUtils.getObjectByTableIndex('tabelaGrupo', document.getElementById('rowIndex').value);
		obj.idGrupo = document.form.idGrupo.value;
		obj.nome = nomeGrupo;

		HTMLUtils.updateRow('tabelaGrupo', document.form, null, obj, ['','idGrupo','nome'], null, '', [gerarButtonDeleteGrupo], null, null, document.getElementById('rowIndex').value, false);
		nomeGrupo = "";
		document.form.idGrupo.value = "";
		novoItem();
	}
	HTMLUtils.applyStyleClassInAllCells('tabelaGrupo', 'celulaGrid');
};

gravarParametro = function() {
	if (document.formCadastroParam.validate()) {
		document.formCadastroParam.fireEvent('gravarParametro');
	}
};

var contGrupo = 0;
deleteAllRows = function() {
	var tabela = document.getElementById('tabelaGrupo');
	var count = tabela.rows.length;

	while (count > 1) {
		tabela.deleteRow(count - 1);
		count--;
	}
	contGrupo = 0;
};

gerarButtonDeleteGrupo = function(row) {
	var message = i18n_message("citcorpore.comum.excluir");
	row.cells[0].innerHTML = '<img id="imgDelInfo" style="cursor: pointer;" title="' + message + '" src="' + properties.context + '/imagens/delete.png" onclick="deleteLinha(\'tabelaGrupo\', this.parentNode.parentNode.rowIndex);">'
}

novoItem = function() {
	document.form.rowIndex.value = "";
};

deleteLinha = function(table, index) {
	if (table === "tabelaGrupo") {
		if (confirm(i18n_message("citcorpore.comum.deleta"))) {
			HTMLUtils.deleteRow(table, index);
		}
	}
};

adicionaParam = function(id,desc) {
	HTMLUtils.addOption('idRestParameter#SEQ#', desc, id);
	var count = GRID_PARAMETROS.getMaxIndex();
	var contadorAux = 0;
	var objs = new Array();
	for (var i = 1; i <= count; i++) {
		var trObj = document.getElementById('GRID_PARAMETROS_TD_' + NumberUtil.zerosAEsquerda(i, 5));
		if (!trObj) {
			continue;
		}
		HTMLUtils.addOption('idRestParameter' + NumberUtil.zerosAEsquerda(i, 5), desc, id);
	}
};
