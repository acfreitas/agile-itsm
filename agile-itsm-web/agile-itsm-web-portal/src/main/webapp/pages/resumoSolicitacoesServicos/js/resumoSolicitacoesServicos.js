
	var temporizador;
	addEvent(window, "load", load, false);
	function load() {
		$("#POPUP_SOLICITANTE").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});

		$("#POPUP_ITEMCONFIG").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
	}

	function LOOKUP_SOLICITANTE_select(id, desc){
		document.getElementById("idSolicitante").value = id;
		document.getElementById("nomeSolicitante").value = desc;
		$("#POPUP_SOLICITANTE").dialog("close");
	}

	function LOOKUP_PESQUISAITEMCONFIGURACAO_select(id, desc){
		document.getElementById("idItemConfiguracao").value = id;
		document.getElementById("nomeItemConfiguracao").value = desc;
		$("#POPUP_ITEMCONFIG").dialog("close");
	}

	function abrePopupUsuario(){
		$("#POPUP_SOLICITANTE").dialog("open");
	}
	function abrePopupIC(){
		$("#POPUP_ITEMCONFIG").dialog("open");
	}

	function filtrar(){
 		//alert(temporizador);
 		if(temporizador == null){
 			temporizador = "ativo";
			temporizador = new Temporizador("imgAtivaTimer");
			temporizador.init();
 		} else {
 			temporizador = "ativo";
 			temporizador = new Temporizador("imgAtivaTimer");
 			temporizador.listaTimersAtivos = [];
			temporizador.listaTimersAtivos.length = 0;
		}
 		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("preencheSolicitacoesRelacionadas");
	}

	function limpar(){
		document.form.clear();
		document.getElementById("tblResumo").innerHTML="";
	}


	
