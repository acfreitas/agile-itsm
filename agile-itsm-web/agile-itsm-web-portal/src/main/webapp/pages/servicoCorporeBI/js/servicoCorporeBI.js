    addEvent(window, "load", load, false);
    function load() {
		document.form.afterRestore = function () {
			$('.tabs').tabs('select', 0);
		}
    }
    
	function LOOKUP_SERVICOCORPOREBI_select(idParam, desc) {
		$('.tabsbar a[href="#tab1-3"]').tab('show');
		document.form.restore({idServicoCorpore:idParam});
	}
	
	function excluir() {
		if (document.getElementById("idServicoCorpore").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("excluir");
			}
		}
	}