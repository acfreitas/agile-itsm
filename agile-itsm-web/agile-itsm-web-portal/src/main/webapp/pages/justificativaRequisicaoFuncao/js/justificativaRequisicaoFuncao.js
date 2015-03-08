    addEvent(window, "load", load, false);
    function load() {
		document.form.afterRestore = function () {
			$('.tabs').tabs('select', 0);
		}
    }
    
   function LOOKUP_JUSTIFICATIVAREQUISICAOFUNCAO_select(id,desc){
		$('.tabsbar a[href="#tab1-3"]').tab('show');
		document.form.restore({idJustificativa:id});
	}
   
	function excluir() {
		if (document.getElementById("idJustificativa").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("update");
			}
		}
	}
	
	function limpar(){
		document.form.clear();
	}
	
	function gravar(){
		document.form.save();
	}
