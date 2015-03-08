var objTab = null;

load = function() {
	document.form.afterRestore = function() {
		$('.tabsbar a[href="#tab1-3"]').tab('show');
	}
};

addEvent(window, "load", load, false);

LOOKUP_MOTIVONEGACAOCHECKIN_select = function(id, desc) {
	$('.tabsbar a[href="#tab1-3"]').tab('show');
	document.form.restore({
		idMotivo : id
	});
};

excluir = function() {
	var idMotivo = document.getElementById("idMotivo");

	if (idMotivo != null && idMotivo.value == 0) {
		alert(i18n_message("citcorpore.comum.necessarioSelecionarRegistro"));
		return false;
	}
	if (confirm(i18n_message("citcorpore.comum.deleta"))) {
		document.form.fireEvent('delete');
	}
};
