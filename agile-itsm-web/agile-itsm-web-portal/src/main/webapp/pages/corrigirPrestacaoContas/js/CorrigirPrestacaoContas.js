addEvent(window, "load", load, false);
function load() {
	document.form.afterLoad = function() {
		if (document.form.editar.value != '' && document.form.editar.value != 'S') {
			desabilitarTela();
		}
		parent.escondeJanelaAguarde();
	}
	
	$('#data').datepicker('option', 'onSelect', function() {
		$(this).focus();
	});
}

function getObjetoSerializado() {
	var obj = new CIT_PrestacaoContasViagemDTO();
	var integrante = new CIT_IntegranteViagemDTO();
	
	integrante.idSolicitacaoServico = $('#idSolicitacaoServico').val();
	integrante.idEmpregado = $('#idEmpregado').val();	
	integrante.idRespPrestacaoContas = $('#idRespPrestacaoContas').val();
	integrante.idTarefa = $('#idTarefa').val();
	
	HTMLUtils.setValuesObject(document.form, obj);
	var itemPrestacaoContas = HTMLUtils.getObjectsByTableId('tabelaItemPrestacaoContasViagem');
	
	obj.itensPrestacaoContasViagemSerialize = ObjectUtils.serializeObjects(itemPrestacaoContas);
	obj.integranteSerialize = ObjectUtils.serializeObjects(integrante);
	
	return ObjectUtils.serializeObject(obj);
}

/* Funcoes para salvar itens */
function adicionarItem() {
	var numeroDocumento = document.getElementById("numeroDocumento").value;
	var data = document.getElementById("data").value;
	var nomeFornecedor = document.getElementById("nomeFornecedor").value;
	var valor = document.getElementById("valor").value;
	var descricao = document.getElementById("descricao").value;
	
	if (StringUtils.isBlank(StringUtils.trim(numeroDocumento))) {
		alert(i18n_message("itemPrestacaoContasViagem.numeroDocumento") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
		return;
	}
	
	if (StringUtils.isBlank(StringUtils.trim(data))) {
		alert(i18n_message("itemPrestacaoContasViagem.data") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
		return;
	}
	
	if (StringUtils.isBlank(StringUtils.trim(nomeFornecedor))) {
		alert(i18n_message("itemPrestacaoContasViagem.nomeFornecedor") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
		return;
	}
	
	if (StringUtils.isBlank(StringUtils.trim(valor))) {
		alert(i18n_message("citcorpore.comum.valor") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
		return;
	}
	
	if (parseFloat(StringUtils.trim(valor)) <= 0) {
		alert(i18n_message("citcorpore.comum.valor") + ': Valor Inválido');
		return;
	}
	
	if(StringUtils.isBlank(StringUtils.trim(descricao))) {
		alert(i18n_message("citcorpore.comum.descricao") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
		return;
	}
	
	addLinhaTabelaItem(nomeFornecedor, numeroDocumento, data, valor, descricao);
	
	atualizarValores();
	
	limparCamposFormulario();
}

function addLinhaTabelaItem(nomeFornecedor, numeroDocumento, data, valor, descricao) {
	var tbl = document.getElementById('tabelaItemPrestacaoContasViagem');
	var tamanhoTabela = tbl.rows.length;
	var obj = new CIT_ItemPrestacaoContasViagemDTO();
	var rowIndex = document.getElementById("rowIndexItemPrestacaoContas").value;
	var valorAux = valor.replace(',', '.');
	
	obj.numeroDocumento = numeroDocumento;
	obj.data = data;
	obj.nomeFornecedor = nomeFornecedor;
	obj.valor = valor;
	obj.valorAux = valorAux;
	obj.descricao = descricao;
	
	if(StringUtils.isBlank(StringUtils.trim(rowIndex)) || rowIndex == null) {
		HTMLUtils.addRow('tabelaItemPrestacaoContasViagem', document.form, null, obj, ["numeroDocumento", "data", "nomeFornecedor", "valor", "descricao", ""],
				[ "numeroDocumento" ], i18n_message("citcorpore.comum.registroJaAdicionado"), [gerarButtonsTable], null, null, false);
	} else {
		var itemPrestacaoContas = HTMLUtils.getObjectByTableIndex("tabelaItemPrestacaoContasViagem", rowIndex);
		
		document.getElementById("valor").value = valorAux - itemPrestacaoContas.valorAux;
		
		obj.idItemPrestContasViagem = itemPrestacaoContas.idItemPrestContasViagem;
		obj.idPrestacaoContasViagem = itemPrestacaoContas.idPrestacaoContasViagem;
		
		if(itemPrestacaoContas.numeroDocumento == obj.numeroDocumento) {
			HTMLUtils.updateRow('tabelaItemPrestacaoContasViagem', document.form, null, obj, ["numeroDocumento", "data", "nomeFornecedor", "valor", "descricao", ""],
					null, '', [gerarButtonsTable], null, null, document.getElementById("rowIndexItemPrestacaoContas").value, false);
		} else {
			HTMLUtils.updateRow('tabelaItemPrestacaoContasViagem', document.form, null, obj, ["numeroDocumento", "data", "nomeFornecedor", "valor", "descricao", ""],
					[ "numeroDocumento" ], i18n_message("citcorpore.comum.registroJaAdicionado"), [gerarButtonsTable], null, null, document.getElementById("rowIndexItemPrestacaoContas").value, false);
		}
	}
}

function removeLinhaTabela(rowIndex) {
	if (window.confirm(i18n_message("citcorpore.comum.deleta"))) {
		var obj = HTMLUtils.getObjectByTableIndex("tabelaItemPrestacaoContasViagem", rowIndex);
		
		document.getElementById('retirarValor').value = "S";
		document.getElementById('valor').value = obj.valor;
		
		atualizarValores();
		
		HTMLUtils.deleteRow("tabelaItemPrestacaoContasViagem", rowIndex);
	}
}

function editarLinhaTabela(rowIndex) {
	var obj = HTMLUtils.getObjectByTableIndex("tabelaItemPrestacaoContasViagem", rowIndex);
	
	document.getElementById("numeroDocumento").value = obj.numeroDocumento;
	document.getElementById("data").value = obj.data;
	document.getElementById("nomeFornecedor").value = obj.nomeFornecedor;
	document.getElementById("valor").value = (obj.valor).replace('.', '');
	document.getElementById("descricao").value = obj.descricao;
	
	document.getElementById("rowIndexItemPrestacaoContas").value = rowIndex;
}

function gerarButtonsTable(row) {
	row.cells[5].innerHTML += "<a href='#' class='btn-action btn-success glyphicons edit titulo' title="+ i18n_message('requisicaoViagem.EditarItem') +" onclick='editarLinhaTabela(this.parentNode.parentNode.rowIndex);' ><i></i></a> ";
	row.cells[5].innerHTML += "<a href='#' class='btn-action glyphicons remove_2 btn-danger titulo' title="+ i18n_message('requisicaoViagem.excluirItem') +" onclick='removeLinhaTabela(this.parentNode.parentNode.rowIndex);' ><i></i></a>";
}

function atualizarValores() {
	document.form.fireEvent('atualizarValores');
}

function limparCamposFormulario() {
	document.getElementById("numeroDocumento").value = "";
	document.getElementById("data").value = "";
	document.getElementById("nomeFornecedor").value = "";
	document.getElementById("valor").value = "";
	document.getElementById("valorAux").value = "";
	document.getElementById("descricao").value = "";
	document.getElementById("rowIndexItemPrestacaoContas").value = "";
}

//Masks
$('.format-money').maskMoney({
	thousands: '',
	decimal: ','
});