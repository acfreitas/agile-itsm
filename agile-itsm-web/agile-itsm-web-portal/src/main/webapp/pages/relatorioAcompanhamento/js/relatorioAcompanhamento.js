
	var temporizador;
	addEvent(window, "load", load, false);


	function imprimirRelatorioDeAcompanhamento(){
		 if(document.getElementById("idContrato").value != ""){
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("imprimirRelatorioDeAcompanhamento");
		}else {
			alert(i18n_message("citcorpore.comum.selecioneContrato"));
			return;
		}
	}

	function imprimirRelatorioDeAcompanhamentoXls(){
		 if( document.getElementById("idContrato").value != ""){
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("imprimirRelatorioDeAcompanhamentoXls");
		}else {
			alert(i18n_message("citcorpore.comum.selecioneContrato"));
			return;
		}
	}

	function limpar(){
		document.form.clear();
	}


