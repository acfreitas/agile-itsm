
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function LOOKUP_TIPOSERVICO_select(id, desc) {
		document.form.restore({
			idTipoServico : id
		});
	}

	$(function() {
		$('.datepicker').datepicker();
	});

	function alterarSituacao() {

		var idTipoServico = document.getElementById("idTipoServico");

		if (idTipoServico.value != '') {
			if (confirm(i18n_message("citcorpore.comum.deleta")))
				document.form.fireEvent("alterarSituacao");
		}

	}

