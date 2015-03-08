var popup = new PopupManager(800, 600, ctx+"/pages/");

document.form.afterRestore = function() {
	$(".tabs").tabs("select", 0);
	updateMapaUnidade();
}
var contLocalidade = 0;

$(function() {
	$("#cep").mask("99999999");
		
	$("#addLocalidade").click(function() {
		$("#POPUP_LOCALIDADE").dialog("open");
	});

	$("#POPUP_LOCALIDADE").dialog( {
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});

	$("#addServico").click(function() {
		$("#POPUP_SERVICO").dialog("open");
	});

	$("#POPUP_SERVICO").dialog({
		autoOpen : false,
		width : "90%" ,
		height : $(window).height() - 100,
		modal : true
	});
});

updatePositions = function(position) {
	mapsParams.marker.setPosition(position);
	mapsParams.map.panTo(position);
	mapsParams.map.setZoom(defaultParams.zoom);
	$("#btnUpdateUnidadeLocation").addClass("disabled").prop("disabled", true);
};

updateMapaUnidade = function() {
	var latitude = $("#latitude").val();
	var longitude = $("#longitude").val();
	if (latitude && longitude) {
		var position = new google.maps.LatLng(latitude, longitude);
		$("#latitude").val(position.lat());
		$("#latitude-dms").val(GeoUtils.decimalLatitudeToDMS(position.lat()));
		$("#longitude").val(position.lng());
		$("#longitude-dms").val(GeoUtils.decimalLongitudeToDMS(position.lng()));

		updatePositions(position);
	} else {
		updatePositions(defaultParams.latLng);
	}
};

LocalidadeUnidadeDTO = function(_id, i) {
	this.idLocalidade = _id;
};

deleteAllRows = function() {
	var tabela = document.getElementById("tabelaServico");
	var count = tabela.rows.length;

	while (count > 1) {
		tabela.deleteRow(count - 1);
		count--;

	}
	ocultaGrid();
};

deleteAllRowsLocalidade = function() {
	var tabela2 = document.getElementById("tabelaLocalidade");
	var cont2 = tabela2.rows.length;

	if(cont2 != null) {
		while (cont2 > 1) {
			tabela2.deleteRow(cont2 - 1);
			cont2--;
		}
		ocultaGridLocalidade();
	}
};

var countServico = 0;

insereRow = function(id, desc) {
	var tabela = document.getElementById("tabelaServico");
	var lastRow = tabela.rows.length;

	var row = tabela.insertRow(lastRow);
	countServico++;

	var valor = desc.split(" - ");

	var coluna = row.insertCell(0);
	var comumExcluir = i18n_message("citcorpore.comum.excluir");
	coluna.innerHTML = '<img id="imgExcluiServico' + countServico + '" style="cursor: pointer;" title="' + comumExcluir + '" src="' + properties.context + '/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaServico\', this.parentNode.parentNode.rowIndex);">';

	coluna = row.insertCell(1);
	coluna.innerHTML = valor[0] + '<input type="hidden" id="idServico' + countServico + '" name="idServico" value="' + id + '" />';

	coluna = row.insertCell(2);
	coluna.innerHTML = valor[1];
};

restoreRow = function() {
	var tabela = document.getElementById("tabelaServico");
	var lastRow = tabela.rows.length;

	var row = tabela.insertRow(lastRow);
	countServico++;

	var coluna = row.insertCell(0);
	var comumExcluir = i18n_message("citcorpore.comum.excluir");
	coluna.innerHTML = '<img id="imgExcluiServico' + countServico + '" style="cursor: pointer;" title="' + comumExcluir + '" src="' + properties.context + '/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaServico\', this.parentNode.parentNode.rowIndex);">';

	coluna = row.insertCell(1);

	coluna.innerHTML = '<input type="hidden" id="idServico' + countServico + '" name="idServico"/><input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="nomeServico' + countServico + '" name="nomeServico"/>';

	coluna = row.insertCell(2);

	coluna.innerHTML = '<input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="descricaoServico' + countServico + '" name="descricaoServico"/>';
};

janelaAguarde = function() {
	JANELA_AGUARDE_MENU.show();
};

janelaAguardeFechar = function() {
	JANELA_AGUARDE_MENU.hide();
};

gravar = function() {
	serializa();
	serializaLocalidade();
	document.form.save();
};

update = function() {
	if (document.getElementById("idUnidade").value !== "") {
		if (confirm(i18n_message("citcorpore.comum.deleta"))) {
			serializaLocalidade();
			document.form.fireEvent("delete");
			deleteAllRows();
			updatePositions(defaultParams.latLng);
		}
	}
};

limpar = function() {
	deleteAllRows();
	deleteAllRowsLocalidade();
	document.getElementById("gridServicos").style.display = "none";
	ocultaGridLocalidade();
	$("#tabelaLocalidade").hide();
	document.form.clear();
	updatePositions(defaultParams.latLng);
};

exibeGrid = function() {
	document.getElementById("gridServicos").style.display = "block";
};

exibeGridLocalidade = function() {
	$("#gridLocalidade").show();
};

ocultaGrid = function() {
	document.getElementById("gridServicos").style.display = "none";
};

ocultaGridLocalidade = function() {
	$("#gridLocalidade").hide();
};

listaContrato = function() {
	if (document.getElementById("idUnidadePai").value !== "") {
		document.getElementById("divListaContratos").setVisible(false);
	} else {
		document.getElementById("divListaContratos").setVisible(true);
	}
};

addLinhaTabelaLocalidade = function(id, desc, valida){
	var tabelaLocalidade = document.getElementById("tabelaLocalidade");
	$("#tabelaLocalidade").show();
	exibeGridLocalidade();
	var lastRow = tabelaLocalidade.rows.length;
	if (valida){
		if (!validaAddLinhaTabelaLocalidade(lastRow, id)){
			return;
		}
	}
	var row = tabelaLocalidade.insertRow(lastRow);
	var coluna = row.insertCell(0);
	contLocalidade++;

	var comumExcluir = i18n_message("citcorpore.comum.excluir");
	coluna.innerHTML = '<img id="imgDelLocalidade' + contLocalidade + '" style="cursor: pointer;" title="' + comumExcluir + '" src="' + properties.context + '/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaLocalidade\', this.parentNode.parentNode.rowIndex);">';
	coluna = row.insertCell(1);
	coluna.innerHTML = desc + '<input type="hidden" id="idLocalidade' + contLocalidade + '" name="idLocalidade" value="' + id + '" />';
	$("#POPUP_LOCALIDADE").dialog("close");
}

validaAddLinhaTabelaLocalidade = function(lastRow, id) {
	if (lastRow > 2) {
		var arrayIdLocalidade = document.form.idLocalidade;
		for (var i = 0; i < arrayIdLocalidade.length; i++) {
			if (arrayIdLocalidade[i].value == id) {
				alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
				return false;
			}

		}
	}
	if (lastRow == 2) {
		var idLocalidade = document.form.idLocalidade;
		if (idLocalidade.value == id) {
			alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
			return false;
		}
	}
	return true;
};

removeLinhaTabela = function(idTabela, rowIndex) {
	if (confirm(i18n_message("citcorpore.comum.deleta"))) {
		HTMLUtils.deleteRow(idTabela, rowIndex);
		document.form.servicoSerializado.value = eval("document.form.idServico" + rowIndex + ".value");
		document.form.fireEvent("excluirAssociacaoServico");
	}
};

removeLinhaTabela = function(idTabela, rowIndex) {
	if (confirm(i18n_message("citcorpore.comum.deleta"))) {
		HTMLUtils.deleteRow(idTabela, rowIndex);
		var tabela = document.getElementById(idTabela);
		if (tabela.rows.length == 1) {
			if (idTabela == "tabelaLocalidade") {
				document.getElementById("dvItemConfig").style.display = "none";
				return;
			}
			tabela.style.display = "none";
		}
	}
};

getServico = function(seq) {
	var ServicoDTO = new CIT_ServicoDTO();
	ServicoDTO.sequencia = seq;
	ServicoDTO.idServico = eval("document.form.idServico" + seq + ".value");
	return ServicoDTO;
}

setRestoreServico = function(idServico, nome, descricao) {
	if (seqSelecionada !== "") {
		eval('document.form.idServico' + seqSelecionada + '.value = "' + idServico + '"');
		eval('document.form.nomeServico' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + nome + '\')');
		eval('document.form.descricaoServico' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + descricao + '\')');
	}
	exibeGrid();
};

serializa = function() {
	var tabela = document.getElementById("tabelaServico");
	var count = tabela.rows.length;
	var contadorAux = 0;
	var servico = new Array();
	for ( var i = 1; i <= count; i++) {
		var trObj = document.getElementById("idServico" + i);

		if (!trObj) {
			continue;
		}
		servico[contadorAux] = getServico(i);
		contadorAux = contadorAux + 1;
	}

	var servicosSerializados = ObjectUtils.serializeObjects(servico);

	document.form.servicosSerializados.value = servicosSerializados;

	return true;
};

serializaLocalidade = function() {
	var tabela = document.getElementById("tabelaLocalidade");
	var count = contLocalidade + 1;
	var listaDeLocalidades = [];
	for(var i = 1; i < count ; i++) {
		if (document.getElementById("idLocalidade" + i) !== "" && document.getElementById("idLocalidade" + i) !== null) {
			var trObj = document.getElementById("idLocalidade" + i).value;
			var localidade = new LocalidadeUnidadeDTO(trObj, i);
			listaDeLocalidades.push(localidade);
		}
	}
	var serializaLocalidade = ObjectUtils.serializeObjects(listaDeLocalidades);
	document.form.localidadesSerializadas.value = serializaLocalidade;
};

LOOKUP_LOCALIDADE_select = function(id, desc) {
	addLinhaTabelaLocalidade(id, desc, true);
};

LOOKUP_UNIDADE_select = function(id, desc) {
	document.form.restore({idUnidade : id});
};

LOOKUP_SERVICO_select = function(id, desc) {
	var tabela = document.getElementById("tabelaServico");
	var lastRow = tabela.rows.length;

	if (lastRow > 2) {
		var arrayIdServico = document.form.idServico;
		for (var i = 0; i < arrayIdServico.length; i++) {
			if (arrayIdServico[i].value == id) {
				alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
				return;
			}
		}
	}
	if (lastRow == 2) {
		var idServico = document.form.idServico;
		if (idServico.value == id) {
			alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
			return;
		}
	}

	insereRow(id, desc);

	$("#POPUP_SERVICO").dialog("close");
	exibeGrid();
};
