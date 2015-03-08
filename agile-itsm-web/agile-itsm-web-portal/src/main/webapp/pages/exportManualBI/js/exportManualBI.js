	function exportar() {
		if (confirm(i18n_message("citcorpore.comum.confirmaexportacao"))) {
			JANELA_AGUARDE_MENU.show();

			if ($('input[name="exportType"]:checked').attr('value') == "path") {
				document.form.fireEvent("exportar");
			} else if ($('input[name="exportType"]:checked').attr('value') == "download") {
				document.form.fireEvent("exportarDownload");
			}
		}
	}
	
	function submitForm (f) {
		$("form[name=" + f + "]").submit();
		//document.f.submit();
	}