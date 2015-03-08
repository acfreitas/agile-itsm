addEvent(window, "load", load, false);
function load() {
	document.form.afterLoad = function() {
		$('#divJustificativa').hide();
		$('#autorizado').val('');
		$('#complemJustificativaAutorizacao').val('');
		parent.escondeJanelaAguarde();
	}
}

function getObjetoSerializado() {
	var obj = new CIT_RequisicaoViagemDTO();
	HTMLUtils.setValuesObject(document.form, obj);
	return ObjectUtils.serializeObject(obj);
}

function configuraJustificativa(aprovado) {
	HTMLUtils.setOptionSelected('idJustificativaAutorizacao',0);
	$('#complemJustificativaAutorizacao').val('');
	if (aprovado == 'N') {
		$('#divJustificativa').show();
		$('#autorizado').val('N');
		document.form.fireEvent('preencherComboJustificativaAutorizacao');
	} else {
		$('#divJustificativa').hide();
		$('#autorizado').val('S');
	}
}

function expandirItemDespesa() {
	$('.browser .filetree-inner, .browser .filetree-inner ul').show(0);
	$('.expandable').addClass('collapsable').removeClass('expandable');
	$('.lastExpandable').addClass('lastCollapsable').removeClass('lastExpandable');
}
function retrairItemDespesa() {
	$('.browser .filetree-inner, .browser .filetree-inner ul').hide(0);
	$('.collapsable').addClass('expandable').removeClass('collapsable');
	$('.lastCollapsable').addClass('lastExpandable').removeClass('lastCollapsable');
}