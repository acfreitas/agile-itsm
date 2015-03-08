  $(function(){
	  if(LOCALE_SISTEMA=="en"){	
			$("#cpf").unmask();
		}else{
			$("#cpf").mask("999.999.999-99");
		}
		$("#divAlterarSenha").hide();
		$("#divSenha").show();
		
  });
 
	function LOOKUP_CANDIDATO_select(id,desc){
		$("#divSenha").hide();
		$("#divAlterarSenha").show();
		$('.tabsbar a[href="#tab1-3"]').tab('show');
		document.form.restore({idCandidato:id});
	}
	
	function excluir() {
		if (document.getElementById("idCandidato").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
				$("#divAlterarSenha").hide();
				$("#divSenha").show();
			}
		}
	}
	
	function limpar(){
		document.form.clear();
		$("#divAlterarSenha").hide();
		$("#divSenha").show();
	}
	
	
	function testaSenha(){
		if(	$('#senha').val() == $('#senha2').val() || $('#senha2').val() == "" || $('#senha').val() == ""){
		}else{
			alert(i18n_message("usuario.senhaDiferente"));
			$('#senha2').val("");
			$('#senha').val("");
		}
	}
	function validaEmail(){
		document.getElementById('email').value = document.getElementById('email').value.replace(/^\s+|\s+$/i,"");
		var resultado = ValidacaoUtils.validaEmail(document.getElementById('email'),'');
		if (resultado == false) {
			return;
		}		
	}
	
	function alterarSenha(){
		$("#divAlterarSenha").hide();
		$("#divSenha").show();
		
	}	
	
	function gravar(){
		if($('#senha').val() == "" || $('#senha').val() == null){
			document.form.save();
			
		}
		else{
			if($('#senha2').val() == "" || $('#senha2').val() == null){
				alert(i18n_message("candidato.repetirSenha"));
			}else{
				document.form.save();
				abre_aguarde();
				
			}
		}
	}
	
	function validaCpf(){
		if($("#cpf").val() == "___.___.___-__" || $("#cpf").val() == null){
			return;
		}else{
			var teste = TestaCPF($("#cpf").val().replace(".","").replace(".","").replace("-",""));
			if(teste){
				return;
			}else{
				alert(i18n_message("citcorpore.validacao.numeroCPFInvalido"));
				$("#cpf").val("");
			}
		}
		
	}
	
	function TestaCPF(strCPF) {
		var Soma; 
		var Resto; 
		Soma = 0; 
		if (strCPF == "00000000000" || strCPF == "11111111111" || strCPF == "22222222222" || strCPF == "33333333333" || strCPF == "44444444444" || strCPF == "55555555555" || strCPF == "66666666666" || strCPF == "77777777777" || strCPF == "88888888888" || strCPF == "99999999999" ) 
			return false;
		for (i=1; i<=9; i++) 
			Soma = Soma + parseInt(strCPF.substring(i-1, i)) * (11 - i); Resto = (Soma * 10) % 11;
			if ((Resto == 10) || (Resto == 11))
				Resto = 0; 
			if (Resto != parseInt(strCPF.substring(9, 10)) )
				return false;
			Soma = 0;
			for (i = 1; i <= 10; i++)
				Soma = Soma + parseInt(strCPF.substring(i-1, i)) * (12 - i);
			Resto = (Soma * 10) % 11;
			if ((Resto == 10) || (Resto == 11)) Resto = 0;
			if (Resto != parseInt(strCPF.substring(10, 11) ) )
				return false; 
			return true;
			
	}
	
	function fecha_aguarde(){
		JANELA_AGUARDE_MENU.hide();
	}
	
	function abre_aguarde(){
		JANELA_AGUARDE_MENU.show();
	}
	

	