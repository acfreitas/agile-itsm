  $(function(){
	  if(LOCALE_SISTEMA=="en"){	
			$("#cpf").unmask();
		}else{
			$("#cpf").mask("999.999.999-99");
		}
		
  });

function validar() {
	if(!StringUtils.isBlank(StringUtils.trim($('#metodoAutenticacao').val()))) {
		if(StringUtils.trim($('#user').val()) == "") {
			alert(i18n_message("login.digite_login"));
			$('#user').focus();
			return false;
		}
		if(StringUtils.trim($('#senha').val()) == "") {
			alert(i18n_message("login.digite_senha"));
			$('#user').focus();
			return false;
		}
		
		mostrar_aguarde();
		document.form.fireEvent("autenticarCandidato");
	} else {
		// Validação login
		if(!$('#cpf').val()) {
			alert(i18n_message("informe.cpf"));
			$('#cpf').focus();
			return false;
		} else if ($('#cpf').val().length < 14) {
			alert(i18n_message("cpf.invalido"));
			$('#cpf').focus();
			return false;
		} else if(!$('#senha').val()) {
			alert(i18n_message("informe.senha"));
			$('#senha').focus();
			return false;
		} else {
			mostrar_aguarde();
			document.form.fireEvent("autenticarCandidato");
		}
	}
	return true;
}

mostrar_aguarde = function() {
	JANELA_AGUARDE_MENU.show();
}

fechar_aguarde = function() {
	JANELA_AGUARDE_MENU.hide();
}