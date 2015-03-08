
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function LOOKUP_CRITERIOAVALIACAO_select(id, desc) {
		document.form.restore({
			idCriterio : id
		});
	}

	function removerCategoria() {
		var idCriterio = document.getElementById("idCriterio");

		if (idCriterio != null && idCriterio.value == 0) {
			alert(i18n_message("citcorpore.comum.necessarioSelecionarRegistro"));
			return false;
		}
		if (confirm(i18n_message("citcorpore.comum.deleta")))
			document.form.fireEvent("remove");
	}

