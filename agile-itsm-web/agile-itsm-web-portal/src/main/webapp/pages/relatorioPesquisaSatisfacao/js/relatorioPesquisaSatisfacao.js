
	var temporizador;
	
	$(window).load(function() {
		
		$("#POPUP_SOLICITANTE").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		
	});
	

	function LOOKUP_SOLICITANTE_select(id, desc){
		document.getElementById("idSolicitante").value = id;
		document.getElementById("nomeSolicitante").value = desc;
		$("#POPUP_SOLICITANTE").dialog("close");
	}

	function imprimirRelatorioQuantitativo(tipo){
		if(validaData()){
			JANELA_AGUARDE_MENU.show();
			document.getElementById("tipoRelatorio").value = tipo;
			document.form.fireEvent("imprimirRelatorioPesquisaSatisfacao");
		}
	}

	function abrePopupUsuario(){
		$("#POPUP_SOLICITANTE").dialog("open");
	}


