
var objTab = null;

addEvent(window, "load", load, false);

function load() {
	document.form.afterRestore = function() {
		$('.tabs').tabs('select', 0);
	};
}

function gravar() {
    document.form.save();
}

pesquisar = function() {
	JANELA_AGUARDE_MENU.show();
	document.form.fireEvent('pesquisa');
}

function LOOKUP_EMPREGADO_select(id, desc) {
	document.form.idSolicitante.value = id;
	document.form.nomeEmpregado.value = desc;
	$("#POPUP_EMPREGADO").dialog("close");
}

$(function() {
	$("#POPUP_EMPREGADO").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true,
		show: "fade",
		hide: "fade"
	});
});

function adicionarEmpregado() {
	$("#POPUP_EMPREGADO").dialog("open");
}


