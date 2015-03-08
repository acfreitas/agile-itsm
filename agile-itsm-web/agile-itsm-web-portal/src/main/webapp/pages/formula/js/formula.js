
	addEvent(window, "load", load, false);
	function load(){
		document.form.afterRestore = function () {
			$('.tabs').tabs('select', 0);
		}
	}
	function LOOKUP_FORMULA_select(id,desc){
		document.form.restore({idFormula:id});
	}
	function excluir(){
		if(document.getElementById("idFormula").value != ""){
			if(confirm(i18n_message("citcorpore.comum.deleta"))){
				document.form.fireEvent("delete");
			}
		}
	}

