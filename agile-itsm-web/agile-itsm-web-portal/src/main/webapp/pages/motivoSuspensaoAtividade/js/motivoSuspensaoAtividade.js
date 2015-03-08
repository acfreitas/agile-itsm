var objTab = null;

addEvent(window, "load", load, false);
function load() {
	document.form.afterRestore = function() {
		$('.tabsbar a[href="#tab1-3"]').tab('show');
	}

}

function LOOKUP_MOTIVOSUSPENSAOATIVIDADE_select(id, desc) {
	$('.tabsbar a[href="#tab1-3"]').tab('show');
	document.form.restore({idMotivo:id});
}

function atualizaData() {
	var idEmpresa = document.getElementById("idMotivo");

	if (idEmpresa != null && idEmpresa.value == 0) {
		alert(i18n_message("citcorpore.comum.necessarioSelecionarRegistro"));
		return false;
	}
	if (confirm(i18n_message("citcorpore.comum.deleta")))
		document.form.fireEvent("atualizaData");
}