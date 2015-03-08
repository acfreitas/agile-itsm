
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

		$("#POPUP_SERVICO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		$("#POPUP_CONTATO").dialog({
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
	function LOOKUP_CONTATOLIBERACAO_select(id, desc){
		document.getElementById("idContato").value = id;
		document.getElementById("nomeContato").value = desc;
		$("#POPUP_CONTATO").dialog("close");
	}
	function LOOKUP_SOLICITANTE_select(id, desc){
		document.getElementById("idSolicitante").value = id;
		document.getElementById("nomeSolicitante").value = desc;
		$("#POPUP_SOLICITANTE").dialog("close");
	}
	function LOOKUP_SERVICO_select(id, desc){
		document.getElementById("idServico").value = id;
		document.getElementById("servico").value = desc;
		$("#POPUP_SERVICO").dialog("close");
	}

	function LOOKUP_PESQUISAITEMCONFIGURACAO_select(id, desc){
		document.getElementById("idItemConfiguracao").value = id;
		document.getElementById("nomeItemConfiguracao").value = desc;
		$("#POPUP_ITEMCONFIG").dialog("close");
	}

	function abrePopupContato(){
		$("#POPUP_CONTATO").dialog("open");
	}

	function abrePopupUsuario(){
		$("#POPUP_SOLICITANTE").dialog("open");
	}
	function abrePopupIC(){
		$("#POPUP_ITEMCONFIG").dialog("open");
	}

	function abrePopupServico(){
		$("#POPUP_SERVICO").dialog("open");
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

	function filtrar(){
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
		var numero = document.getElementById("idRequisicaoLiberacaoPesquisa").value;
		var dataInicioFechamento = document.getElementById("dataInicioFechamento").value;
		var dataFimFechamento = document.getElementById("dataFimFechamento").value;

		if(numero != "") {
			if (dataInicio != ""){
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
				 	document.getElementById("dataInicio").value = '';
					return false;
				}

			}

			if (dataFim != ""){
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
					 document.getElementById("dataFim").value = '';
					return false;
				}
			}
		}
		else {
				if(dataInicioFechamento != "" || dataFimFechamento != ""){
					if(DateTimeUtil.isValidDate(dataInicioFechamento) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioEncerramento"));
					 	document.getElementById("dataInicioFechamento").value = '';
						return false;
					}
					if(DateTimeUtil.isValidDate(dataFimFechamento) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimEncerramento"));
						document.getElementById("dataFimFechamento").value = '';
						return false;
					}
					if (dataInicio != ""){
						if(DateTimeUtil.isValidDate(dataFim) == true){
						if(DateTimeUtil.isValidDate(dataInicio) == false){
							alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
						 	document.getElementById("dataInicio").value = '';
							return false;
						}
						}
						else{
							alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
							 document.getElementById("dataFim").value = '';
							return false;
						}
					}

					if (dataFim != ""){
						if(DateTimeUtil.isValidDate(dataInicio) == true){
						if(DateTimeUtil.isValidDate(dataFim) == false){
							alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
							 document.getElementById("dataFim").value = '';
							return false;
						}
					}
						else{
							alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
							 document.getElementById("dataInicio").value = '';
							return false;
						}
					}



				}
				else{
					if(DateTimeUtil.isValidDate(dataInicio) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
					 	document.getElementById("dataInicio").value = '';
						return false;
					}
					if(DateTimeUtil.isValidDate(dataFim) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						document.getElementById("dataFim").value = '';
						return false;
					}

				}
		}

		if(dataInicioFechamento != "" || dataFimFechamento != ""){
			if(validaData(dataInicioFechamento,dataFimFechamento)){
				if(validaData(dataInicio,dataFim)){
					inicializarTemporizador();
					JANELA_AGUARDE_MENU.show();
					document.form.fireEvent("preencheSolicitacoesRelacionadas");
				}
			}
		}
		else{
			if(validaData(dataInicio,dataFim)){
				inicializarTemporizador();
				JANELA_AGUARDE_MENU.show();
				document.form.fireEvent("preencheSolicitacoesRelacionadas");
			}

		}

	}
	function imprimirRelatorio(){
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
		var numero = document.getElementById("idRequisicaoLiberacaoPesquisa").value;
		var dataInicioFechamento = document.getElementById("dataInicioFechamento").value;
		var dataFimFechamento = document.getElementById("dataFimFechamento").value;
		var mostrarDescricao = document.getElementById("exibirCampoDescricao");

		if(mostrarDescricao.checked == true){
			mostrarDescricao.value = 'S'
		}else{
			mostrarDescricao.value = 'N'
		}

		if(numero != "") {
			if (dataInicio != ""){
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
				 	document.getElementById("dataInicio").value = '';
					return false;
				}

			}

			if (dataFim != ""){
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
					 document.getElementById("dataFim").value = '';
					return false;
				}
			}
		}
		else {
				if(dataInicioFechamento != "" || dataFimFechamento != ""){
					if(DateTimeUtil.isValidDate(dataInicioFechamento) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioEncerramento"));
					 	document.getElementById("dataInicioFechamento").value = '';
						return false;
					}
					if(DateTimeUtil.isValidDate(dataFimFechamento) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimEncerramento"));
						document.getElementById("dataFimFechamento").value = '';
						return false;
					}
					if (dataInicio != ""){
						if(DateTimeUtil.isValidDate(dataFim) == true){
						if(DateTimeUtil.isValidDate(dataInicio) == false){
							alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
						 	document.getElementById("dataInicio").value = '';
							return false;
						}
						}
						else{
							alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
							 document.getElementById("dataFim").value = '';
							return false;
						}
					}

					if (dataFim != ""){
						if(DateTimeUtil.isValidDate(dataInicio) == true){
						if(DateTimeUtil.isValidDate(dataFim) == false){
							alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
							 document.getElementById("dataFim").value = '';
							return false;
						}
					}
						else{
							alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
							 document.getElementById("dataInicio").value = '';
							return false;
						}
					}



				}
				else{
					if( dataInicio != '' && DateTimeUtil.isValidDate(dataInicio) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
					 	document.getElementById("dataInicio").value = '';
						return false;
					}
					if(dataFim != '' && DateTimeUtil.isValidDate(dataFim) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						document.getElementById("dataFim").value = '';
						return false;
					}

				}
		}

		if(dataInicioFechamento != "" || dataFimFechamento != ""){
			if(validaData(dataInicioFechamento,dataFimFechamento)){
				if(validaData(dataInicio,dataFim)){
					inicializarTemporizador();
					JANELA_AGUARDE_MENU.show();
					document.form.fireEvent("imprimirRelatorio");
				}
			}
		}
		else{
			if(validaData(dataInicio,dataFim)){
				inicializarTemporizador();
				JANELA_AGUARDE_MENU.show();
				document.form.fireEvent("imprimirRelatorio");
			}

		}
	}

	function imprimirRelatorioXls(){
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
		var numero = document.getElementById("idRequisicaoLiberacaoPesquisa").value;
		var dataInicioFechamento = document.getElementById("dataInicioFechamento").value;
		var dataFimFechamento = document.getElementById("dataFimFechamento").value;

		if(numero != "") {
			if (dataInicio != ""){
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
				 	document.getElementById("dataInicio").value = '';
					return false;
				}

			}

			if (dataFim != ""){
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
					 document.getElementById("dataFim").value = '';
					return false;
				}
			}
		}
		else {
				if(dataInicioFechamento != "" || dataFimFechamento != ""){
					if(DateTimeUtil.isValidDate(dataInicioFechamento) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioEncerramento"));
					 	document.getElementById("dataInicioFechamento").value = '';
						return false;
					}
					if(DateTimeUtil.isValidDate(dataFimFechamento) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimEncerramento"));
						document.getElementById("dataFimFechamento").value = '';
						return false;
					}
					if (dataInicio != ""){
						if(DateTimeUtil.isValidDate(dataFim) == true){
						if(DateTimeUtil.isValidDate(dataInicio) == false){
							alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
						 	document.getElementById("dataInicio").value = '';
							return false;
						}
						}
						else{
							alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
							 document.getElementById("dataFim").value = '';
							return false;
						}
					}

					if (dataFim != ""){
						if(DateTimeUtil.isValidDate(dataInicio) == true){
						if(DateTimeUtil.isValidDate(dataFim) == false){
							alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
							 document.getElementById("dataFim").value = '';
							return false;
						}
					}
						else{
							alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
							 document.getElementById("dataInicio").value = '';
							return false;
						}
					}



				}
				else{
					if(DateTimeUtil.isValidDate(dataInicio) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
					 	document.getElementById("dataInicio").value = '';
						return false;
					}
					if(DateTimeUtil.isValidDate(dataFim) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						document.getElementById("dataFim").value = '';
						return false;
					}

				}
		}

		if(dataInicioFechamento != "" || dataFimFechamento != ""){
			if(validaData(dataInicioFechamento,dataFimFechamento)){
				if(validaData(dataInicio,dataFim)){
					inicializarTemporizador();
					JANELA_AGUARDE_MENU.show();
					document.form.fireEvent("imprimirRelatorioXls");
				}
			}
		}
		else{
			if(validaData(dataInicio,dataFim)){
				inicializarTemporizador();
				JANELA_AGUARDE_MENU.show();
				document.form.fireEvent("imprimirRelatorioXls");
			}

		}
	}

	 function anexos(idRequisicao){

		 document.form.idRequisicaoLiberacao.value = idRequisicao;
		 document.form.fireEvent("restoreUpload");
	 }
	function reabrir(id){
		if (!confirm(i18n_message("requisicaoLiberacao.confirmaReaberturaLiberacao"))){
			return;
		}
		document.form.idRequisicaoLiberacao.value = id;
		document.form.fireEvent("reabre");
	}

	function consultarOcorrencias(idLiberacao){
		document.formOcorrencias.idOcorrencia.value = idLiberacao;
		document.formOcorrencias.fireEvent("listOcorrenciasSituacao");
		$("#POPUP_OCORRENCIAS").dialog("open");
	}

	function limpar(){
		document.form.clear();
		$('#situacao').attr('disabled', false)

		document.getElementById("tblResumo").innerHTML="";
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

//	popup para pesquisar de unidade

	 $(function() {
		$("#addUnidade").click(function() {
			$("#POPUP_UNIDADE").dialog("open");
		});
	});




