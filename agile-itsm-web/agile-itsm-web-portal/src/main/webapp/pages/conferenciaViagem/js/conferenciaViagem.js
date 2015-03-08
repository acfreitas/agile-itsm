addEvent(window, "load", load, false);
function load() {
	document.form.afterLoad = function() {
		$('#complemJustificativaAutorizacao').val('');
		$('#divJustificativa').hide();
		parent.escondeJanelaAguarde();
	}
}

function getObjetoSerializado() {
	var obj = new CIT_PrestacaoContasViagemDTO();
	HTMLUtils.setValuesObject(document.form, obj);
	return ObjectUtils.serializeObject(obj);
}

function configuraJustificativa(aprovado) {
	HTMLUtils.setOptionSelected('idJustificativaAutorizacao',0);
	$('#complemJustificativaAutorizacao').val('');
	
	if (aprovado == 'N') {
		$('#divJustificativa').show();
		document.form.fireEvent('preencherComboJustificativaAutorizacao');
	} else {
		$('#divJustificativa').hide();
	}
}

