
	var temporizador;
	addEvent(window, "load", load, false);
	function load() {
		$("#POPUP_SOLICITANTE").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});

		$("#POPUP_RESPONSAVEL").dialog({
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

		$("#POPUP_OCORRENCIAS").dialog({
			autoOpen : false,
			width : 800,
			height : 600,
			modal : true
		});

		$("#POPUP_UNIDADE").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		$("#POPUP_menuAnexos").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		$("#POPUP_EMPREGADO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});



	}

	function LOOKUP_UNIDADE_SOLICITACAO_select(id, desc) {
		document.form.idUnidade.value = id;
		document.form.nomeUnidade.value = desc.split(" - ")[0];
		$("#POPUP_UNIDADE").dialog("close");
	}

	function LOOKUP_PESQUISAITEMCONFIGURACAO_select(id, desc){
		document.getElementById("idItemConfiguracao").value = id;
		document.getElementById("nomeItemConfiguracao").value = desc;
		$("#POPUP_ITEMCONFIG").dialog("close");
	}

	function abrePopupIC(){
		$("#POPUP_ITEMCONFIG").dialog("open");
	}
	function inicializarTemporizador(){
		if(temporizador == null){
			temporizador = new Temporizador("imgAtivaTimer");
		} else {
			temporizador = null;
			try{
				temporizador.listaTimersAtivos = [];
			}catch(e){}
			try{
				temporizador.listaTimersAtivos.length = 0;
			}catch(e){}
			temporizador = new Temporizador("imgAtivaTimer");
		}
	}

	function pesquisaProblema(){

		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
		var numero = document.getElementById("idProblema").value;

		if(numero != "") {
			if (dataInicio != ""){
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("citcorpore.comum.validacao.datainicio"));
				 	document.getElementById("dataInicio").value = '';
					return false;
				}

			}

			if (dataFim != ""){
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("citcorpore.comum.validacao.datafim"));
					 document.getElementById("dataFim").value = '';
					return false;
				}
			}
		}
		else {
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("citcorpore.comum.validacao.datainicio"));
				 	document.getElementById("dataInicio").value = '';
					return false;
				}
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("citcorpore.comum.validacao.datafim"));
					document.getElementById("dataFim").value = '';
					return false;
				}
		}
		if(validaData(dataInicio,dataFim)){
			inicializarTemporizador();
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("pesquisaProblema");
		}
	}



	function imprimirRelatorioProblema(){
		//geber.costa
		var problema = document.getElementById("idProblema").value;
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;

		if(problema >0 && dataInicio == "" && dataFim == ""){
			JANELA_AGUARDE_MENU.show();
			document.form.formatoArquivoRelatorio.value = 'pdf';
			document.form.fireEvent("imprimirRelatorioProblema");
			return false;
		}

		if(DateTimeUtil.isValidDate(dataInicio) == false){
			alert(i18n_message("citcorpore.comum.validacao.datainicio"));
		 	document.getElementById("dataInicio").value = '';
		 	return false;
		}
		if(DateTimeUtil.isValidDate(dataFim) == false){
			 alert(i18n_message("citcorpore.comum.validacao.datafim"));
			 document.getElementById("dataFim").value = '';
			return false;
		}
		if(validaData(dataInicio,dataFim)){
			JANELA_AGUARDE_MENU.show();
			document.form.formatoArquivoRelatorio.value = 'pdf';
			document.form.fireEvent("imprimirRelatorioProblema");

		}
	}

	function imprimirRelatorioProblemaXls(){
		//geber.costa
		var problema = document.getElementById("idProblema").value;
		if(problema>0){
			JANELA_AGUARDE_MENU.show();
			document.form.formatoArquivoRelatorio.value = 'xls';
			document.form.fireEvent("imprimirRelatorioProblema");
			return false;

		}
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
		if(DateTimeUtil.isValidDate(dataInicio) == false){
			alert(i18n_message("citcorpore.comum.validacao.datainicio"));
		 	document.getElementById("dataInicio").value = '';
		 	return false;
		}
		if(DateTimeUtil.isValidDate(dataFim) == false){
			 alert(i18n_message("citcorpore.comum.validacao.datafim"));
			 document.getElementById("dataFim").value = '';
			return false;
		}
		if(validaData(dataInicio,dataFim)){
			JANELA_AGUARDE_MENU.show();
			document.form.formatoArquivoRelatorio.value = 'xls';
			document.form.fireEvent("imprimirRelatorioProblema");

		}

	}


	function limpar(){
		$('#divTblProblema').hide();
		document.form.clear();


	}

	/**
	* @author rodrigo.oliveira
	*/
	function validaData(dataInicio, dataFim) {
		if (typeof(locale) === "undefined") locale = '';

		var dtInicio = new Date();
		var dtFim = new Date();

		var dtInicioConvert = '';
		var dtFimConvert = '';
		var dtInicioSplit = dataInicio.split("/");
		var dtFimSplit = dataFim.split("/");

		if (locale == 'en') {
			dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[0] + "/" + dtInicioSplit[1];
			dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[0] + "/" + dtFimSplit[1];
		} else {
			dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[1] + "/" + dtInicioSplit[0];
			dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[1] + "/" + dtFimSplit[0];
		}

		dtInicio.setTime(Date.parse(dtInicioConvert)).setFullYear;
		dtFim.setTime(Date.parse(dtFimConvert)).setFullYear;

		if (dtInicio > dtFim){
			alert(i18n_message("citcorpore.comum.dataInicioMenorFinal"));
			return false;
		}else
			return true;
	}

	function pageLoad()
	{
		$(function()
		{
			$('input.datepicker').datepicker();
		});
	}

//	popup para pesquisar de unidade

	 $(function() {
		$("#addUnidade").click(function() {
			$("#POPUP_UNIDADE").dialog("open");
		});
	});


	 /*
	 	 * Reaproveitamento da lookup EMPREGADO
	 	 */
		function selecionarSolicitante(){
			LOOKUP_EMPREGADO_select =  function (id, desc){
				document.form.idSolicitante.value = id;
				document.form.nomeSolicitante.value = desc.split("-")[0];
				$("#POPUP_EMPREGADO").dialog("close");
			}

			$("#POPUP_EMPREGADO").dialog("open");
		}

		function selecionarProprietario(){
			limpar_LOOKUP_EMPREGADO();
			LOOKUP_EMPREGADO_select =  function (id, desc){
				document.form.idProprietario.value = id;
				document.form.nomeProprietario.value = desc.split("-")[0];
				$("#POPUP_EMPREGADO").dialog("close");
			}

			$("#POPUP_EMPREGADO").dialog("open");
		}

	    function mostrarCategoria(){

	    	document.form.fireEvent('validacaoCategoriaMudanca');

        }


