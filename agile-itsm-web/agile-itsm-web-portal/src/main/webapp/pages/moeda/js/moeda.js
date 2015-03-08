
	var objTab = null;

	addEvent(window, "load", load, false);

	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function limpar() {
		document.form.clear();
	}

	function LOOKUP_MOEDA_select(id, desc) {
		document.form.restore({
			idMoeda : id
		});
	}

	function excluir() {
		if (document.getElementById("idMoeda").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))){
				document.form.fireEvent("delete");
			}
		}

	}

