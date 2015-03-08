
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);

		}

	}

	function LOOKUP_RISCO_select(id, desc) {
		document.form.restore({
			idRisco : id
		});
	}

	function atualizaData() {
		var idEmpresa = document.getElementById("idRisco");

		if (idEmpresa != null && idEmpresa.value == 0) {
			alert(i18n_message("citcorpore.comum.necessarioSelecionarRegistro"));
			return false;
		}
		if (confirm(i18n_message("citcorpore.comum.deleta")))
			document.form.fireEvent("atualizaData");
	}

