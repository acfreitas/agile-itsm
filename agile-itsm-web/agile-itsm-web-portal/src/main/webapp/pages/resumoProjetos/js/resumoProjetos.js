var objTab = null;

addEvent(window, "load", load, false);

function load() {
	document.form.afterRestore = function() {
	}
}

function pesquisarProjetos(){
	document.getElementById('divInfoProjetos').innerHTML = i18n_message("resumoProjeto.aguarde");
	document.form.fireEvent('pesquisa');
}

$(function() {
});


