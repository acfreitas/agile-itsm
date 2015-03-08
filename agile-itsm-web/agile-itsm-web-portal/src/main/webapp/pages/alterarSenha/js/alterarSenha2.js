 function validar(){
	 var senha = document.getElementById("senha").value;
     var senha2 = document.getElementById("senhaNovamente").value;
		if (senha == senha2){
			document.form.fireEvent("alterarSenha");
		}
		else{
			alert(i18n_message("usuario.senhaDiferente"));
			document.getElementById("senha").value = "";
			document.getElementById("senha_novamente").value = "";
			document.getElementById("senha").focus();
		}
	}