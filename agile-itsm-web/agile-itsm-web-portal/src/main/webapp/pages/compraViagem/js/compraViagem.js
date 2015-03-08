addEvent(window, "load", load, false);
function load(){        
	document.form.afterLoad = function () {
	    parent.escondeJanelaAguarde();                    
	}    
}

function getObjetoSerializado() {
	var obj = new CIT_DespesaViagemDTO();
	HTMLUtils.setValuesObject(document.form, obj);
	return ObjectUtils.serializeObject(obj);
}

function confirmaExecucao(){
	if($('#confirmaExec').is(':checked')){
		document.form.confirma.value = "S";
	}else{
		document.form.confirma.value = "N";
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