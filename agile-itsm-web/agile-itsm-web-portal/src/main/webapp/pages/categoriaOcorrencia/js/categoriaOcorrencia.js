var objTab = null;
addEvent(window, "load", load, false);

function load() {
	document.formCategoriaOcorrencia.afterRestore = function() {
		$('.tabsbar li:eq(0) a').tab('show')
	}
}

function excluir() {
	if (document.getElementById("idCategoriaOcorrencia").value != "") {
		if (confirm(i18n_message("citcorpore.comum.deleta") ) ) {
			document.formCategoriaOcorrencia.fireEvent("delete");
		}
	}
}
function LOOKUP_CATEGORIA_OCORRENCIA_select(id, desc) {
	document.formCategoriaOcorrencia.restore({
		idCategoriaOcorrencia: id
	});
}