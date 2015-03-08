
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {


		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function LOOKUP_UNIDADEMEDIDA_select(id, desc) {
		document.form.restore({
			idUnidadeMedida : id
		});
	}

	function excluir() {
		if (document.getElementById("idUnidadeMedida").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}






