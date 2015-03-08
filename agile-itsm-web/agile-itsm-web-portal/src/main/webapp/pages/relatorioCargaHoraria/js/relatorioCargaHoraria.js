
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

	function imprimirRelatorioQuantitativo(formatoRelatorio){
		var contrato = document.getElementById("idContrato").value;
		var tipo     = document.getElementById("tipoRelatorio").value;
		var str_nomeDoRelatorio = "";

		if(contrato == ""){
			alert(i18n_message("solicitacaoservico.validacao.contrato"));
			return false;
		}

		if(tipo == 1){
			str_nomeDoRelatorio = "imprimirCargaHorariaUsuario" + formatoRelatorio;
			imprimirRelatorioPeloNomeDoRelatorio(str_nomeDoRelatorio);
		}else{
			str_nomeDoRelatorio = "imprimirCargaHorariaGrupo" + formatoRelatorio;
			imprimirRelatorioPeloNomeDoRelatorio(str_nomeDoRelatorio);
		}
	}

	function abrePopupUsuario(){
		$("#POPUP_SOLICITANTE").dialog("open");
	}

	function preencherComboGrupo(){
		document.form.fireEvent("preencherComboGrupo");
	}

