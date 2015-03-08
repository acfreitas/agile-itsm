
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function LOOKUP_PRIORIDADE_select(id, desc) {
		document.form.restore({
			idPrioridade : id
		});
	}

	$(function() {
		$('.datepicker').datepicker();
	});

	function alterarSituacao() {
		var idPrioridade = document.getElementById("idPrioridade");

		if (idPrioridade.value != '') {
			if (confirm(i18n_message("citcorpore.comum.deleta")))
				document.form.fireEvent("alterarSituacao");
		}

	}

