/**
 * 
 */
var objTab = null;
addEvent(window, "load", load, false);

function load() {
	document.formOrigemOcorrencia.afterRestore = function() {
		$('.tabsbar li:eq(0) a').tab('show')
	}
}

function excluir() {
	if (document.getElementById("idOrigemOcorrencia").value != "") {
		if (confirm(i18n_message("citcorpore.comum.deleta") ) ) {
			document.formOrigemOcorrencia.fireEvent("delete");
		}
	}
}
function LOOKUP_ORIGEM_OCORRENCIA_select(id, desc) {
	document.formOrigemOcorrencia.restore({
		idOrigemOcorrencia: id
	});
}
