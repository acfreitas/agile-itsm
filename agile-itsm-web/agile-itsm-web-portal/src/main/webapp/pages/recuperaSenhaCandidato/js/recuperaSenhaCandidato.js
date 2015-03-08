function validar() {
	// Validação email
	if(!$('#email').val()) {
		alert(i18n_message("informe.email"));
		$('#email').focus();
		return false;
	} else {
		mostrar_aguarde();
		document.formRecuperaSenhaCandidato.fireEvent("validacaoEmail");
	}
	return true;
}

mostrar_aguarde = function() {
	JANELA_AGUARDE_MENU.show();
}

fechar_aguarde = function() {
	JANELA_AGUARDE_MENU.hide();
}