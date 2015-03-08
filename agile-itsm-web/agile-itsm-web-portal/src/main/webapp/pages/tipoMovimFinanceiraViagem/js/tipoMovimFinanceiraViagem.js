
 	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function LOOKUP_TIPOMOVIMFINANCEIRAVIAGEM_select(id, desc) {
		document.form.restore({
			idtipoMovimFinanceiraViagem : id
		});
	}

	function excluir() {
		if (document.getElementById("idtipoMovimFinanceiraViagem").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}

