
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function LOOKUP_LINGUA_select(id, desc) {
		document.form.restore({
			idLingua : id
		});
	}

	function excluir() {
		if (document.getElementById("idLingua").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}

