function importar() {
	if (confirm(i18n_message("citcorpore.comum.confirmaimportacao"))) {
		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("importar");
	}
}