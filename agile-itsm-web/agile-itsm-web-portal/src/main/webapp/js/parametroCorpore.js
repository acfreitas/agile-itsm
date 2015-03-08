var objTab = null;

addEvent(window, "load", load, false);

function load(){
	if(document.form) {
		
		document.form.afterRestore = function () {
			$('.tabs').tabs('select', 0);
		}
	}
}

function excluirParametroCorpore(){
	
	if (confirm("Deseja realmente excluir ?")) {
		document.form.fireEvent("excluirParametroCorpore");
	}
}

function LOOKUP_PARAMETROCORPORE_select(idParam,desc){
	document.form.idAux.value = idParam;
	document.form.restore({id:idParam});
}