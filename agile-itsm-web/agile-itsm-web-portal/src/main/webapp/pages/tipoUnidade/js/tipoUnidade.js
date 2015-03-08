

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function LOOKUP_TIPOUNIDADE_select(id, desc) {
		document.form.restore({
			idTipoUnidade : id
		});
	}

	function excluir() {
		if (document.getElementById("idTipoUnidade").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))){
				document.form.fireEvent("delete");
			}
		}
	}

