
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);

		}
	};

	function excluir() {
		if (document.getElementById("idEventoMonitoramento").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}

	function LOOKUP_EVENTO_MONITORAMENTO_select(id, desc) {
		document.form.restore({
			idEventoMonitoramento : id
		});
	}

	function LOOKUP_USUARIO_select(id, desc) {
		document.form.restore({
			criadoPor : desc
		});
	}


