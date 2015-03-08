
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function LOOKUP_FORMAPAGAMENTO_select(id, desc) {
		document.form.restore({
			idFormaPagamento : id
		});
	}

	function excluir() {
		if (document.getElementById("idFormaPagamento").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}


