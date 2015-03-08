
	var temporizador;
	addEvent(window, "load", load, false);
	function load() {
		$("#POPUP_SERVICOCONTRATO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});

	}

	$(function() {
		$("#addServicoContrato").click(function() {
			if(verificaContrato()){
				var idContrato = document.form.idContrato.value;
				document.formPesquisaLocalidade.pesqLockupLOOKUP_SERVICOCONTRATO_IDCONTRATO.value = idContrato;
				$("#POPUP_SERVICOCONTRATO").dialog("open");
			}
		});
	});

	function LOOKUP_SERVICOCONTRATO_select(id, desc){
		document.form.idServico.value = id;
		document.form.addServicoContrato.value = desc;
		$("#POPUP_SERVICOCONTRATO").dialog("close");
	}

	function fecharPopup(){
		$("#POPUP_SERVICOCONTRATO").dialog("close");
	}

	function imprimirRelatorioQuantitativo(){
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
			document.form.fireEvent("imprimirRelatorioControleSla");

		}
	}


	function imprimirRelatorioQuantitativoXls(){
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

			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("imprimirRelatorioControleSlaXls");

	}


	function validaData(dataInicio, dataFim) {
		if (typeof(locale) === "undefined") locale = '';

		var dtInicio = new Date();
		var dtFim = new Date();
		var dtInicioConvert = '';
		var dtFimConvert = '';

		if (locale == '' || locale == 'pt' || locale == 'es') {
			var dateSplit = dataInicio.split("/");
			dtInicioConvert = dateSplit[2] + "/" + dateSplit[1] + "/" + dateSplit[0];

			var dateSplit = dataFim.split("/");
			dtFimConvert = dateSplit[2] + "/" + dateSplit[1] + "/" + dateSplit[0];
		} else if (locale == 'en') {
			var dateSplit = dataInicio.split("/");
			dtInicioConvert = dateSplit[2] + "/" + dateSplit[0] + "/" + dateSplit[1];

			var dateSplit = dataFim.split("/");
			dtFimConvert = dateSplit[2] + "/" + dateSplit[0] + "/" + dateSplit[1];
		}

		dtInicio.setTime(Date.parse(dtInicioConvert)).setFullYear;
		dtFim.setTime(Date.parse(dtFimConvert)).setFullYear;

		if (dtInicio > dtFim){
			alert(i18n_message("citcorpore.comum.dataInicioMenorFinal"));
			return false;
		}else
			return true;
	}

	function limpar(){
		document.form.clear();
	}

	function verificaContrato() {
		if (document.form.idContrato.value == '' ||  document.form.idContrato.value == '-- Todos --'){
			alert(i18n_message("solicitacaoservico.validacao.contrato"));
			return false;
		}
		return true;
	}

	function limparServico(){
		document.form.idServico.value = "";
		document.form.addServicoContrato.value = "";
		document.getElementById("idUnidade").value= "0";
	}

	function montaParametrosAutocompleteUnidade(){
		$( "#unidadeDes" ).autocomplete({
			  source: function (request, response) {
			  $.ajax({
		          	  url: "pages/autoCompleteUnidade/autoCompleteUnidade.load",
		          	  dataType: "json",
		          		data: {
		        			query: request.term, colection : 1, idContrato : document.form.idContrato.value
		         		},
		          		success: function(data) {
                    	response($.map(data, function(item) {
                              return {
                                  		label: item.nome,
                                  		value: item.nome.replace(/-*/, ""),
                                  		id: item.idUnidade,
                                  	 };
                        	}));
                    	}
			        });
			  },
			  minLength: 3,
			  select: function(e, ui) {
				  $('#idUnidade').val(ui.item.id);
			  }
		});
	}

	function onkeypressUnidadeDes(){
		document.getElementById("idUnidade").value= "0";
	}

