
	addEvent(window, "load", load, false);
	function load(){
		document.form.afterRestore = function () {
			$('.tabs').tabs('select', 0);
		}
	}
	function LOOKUP_IMPORTANCIANEGOCIO_select(id,desc){
		document.form.restore({idImportanciaNegocio:id});
	}
	function excluir(){
		if(document.getElementById("idImportanciaNegocio").value != ""){
			if(confirm(i18n_message("citcorpore.comum.deleta"))){
				document.form.fireEvent("delete");
			}
		}
	}

