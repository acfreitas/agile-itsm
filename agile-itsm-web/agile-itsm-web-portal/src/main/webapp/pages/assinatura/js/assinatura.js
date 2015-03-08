var completeResponsavel;

$(document).ready(function() {
	completeResponsavel = $('#nomeResponsavel').autocomplete({ 
		serviceUrl:'pages/autoCompleteEmpregado/autoCompleteEmpregado.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idEmpregado').val(data);
			$('#nomeResponsavel').val(value);
		}
	});
});

function onkeypressNomeResponsavel(){
	document.getElementById("idEmpregado").value= "0";
}

function gravar(){
	document.form.save();
}

function limpar(){
	document.form.clear();
}

function excluir() {
	if (confirm(i18n_message("citcorpore.comum.deleta"))) {
		document.form.fireEvent("excluir");
	}
}

function LOOKUP_ASSINATURA_select(idParam, desc) {
	$('.tabsbar a[href="#tab1-3"]').tab('show');
	document.form.restore({
		idAssinatura : idParam
	});
}
