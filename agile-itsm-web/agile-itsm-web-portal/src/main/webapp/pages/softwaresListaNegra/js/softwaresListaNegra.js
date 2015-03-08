
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function LOOKUP_SOFTWARESLISTANEGRA_select(id, desc) {
		document.form.restore({
			idSoftwaresListaNegra : id
		});
	}

	function excluir() {
		if (document.getElementById("idSoftwaresListaNegra").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}


