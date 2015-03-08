
	var temporizador;
	addEvent(window, "load", load, false);


	function imprimirRelatorioOrdemServicoUst(){
		 if(document.getElementById("ano").value != "" && document.getElementById("idContrato").value != ""){
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("imprimirRelatorioOrdemServicoUst");
		}else {
			 if(document.getElementById("ano").value == ""){
				alert(i18n_message("citcorpore.comum.selecioneAno"));
				return;
			}
			if(document.getElementById("idContrato").value ==""){
				alert(i18n_message("citcorpore.comum.selecioneContrato"));
				return;
			}
		}
	}

	function imprimirRelatorioOrdemServicoUstXls(){
		 if(document.getElementById("ano").value != "" && document.getElementById("idContrato").value != ""){
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("imprimirRelatorioOrdemServicoUstXls");
		}else {
			if(document.getElementById("ano").value == ""){
				alert(i18n_message("citcorpore.comum.selecioneAno"));
				return;
			}
			if(document.getElementById("idContrato").value ==""){
				alert(i18n_message("citcorpore.comum.selecioneContrato"));
				return;
			}

		}
	}

	function limpar(){
		document.form.clear();
	}


