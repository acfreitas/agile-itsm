

	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function LOOKUP_CONDICAOOPERACAO_select(id, desc) {
		document.form.restore({
			idCondicaoOperacao : id
		});
	}

	function deleteObj() {
		if (document.getElementById("idCondicaoOperacao").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))){
				document.form.fireEvent("delete");
			}
		}
	}


