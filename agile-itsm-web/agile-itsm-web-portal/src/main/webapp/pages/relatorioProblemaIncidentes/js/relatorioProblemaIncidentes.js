
			var temporizador;

			addEvent(window, "load", load, false);

			function load() {


			}

			function imprimirRelatorioPdf() {
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

				if (validaData(dataInicio,dataFim) ) {
					JANELA_AGUARDE_MENU.show();
					document.form.formatoArquivoRelatorio.value = 'pdf';
					document.form.fireEvent("imprimirRelatorio");
				}
			}

			function imprimirRelatorioXls() {
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

				if (validaData(dataInicio,dataFim) ) {
					JANELA_AGUARDE_MENU.show();
					document.form.formatoArquivoRelatorio.value = 'xls';
					document.form.fireEvent("imprimirRelatorio");
				}
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

			function limpar() {
				document.form.clear();
			}

			function reportEmpty(){
				alert(i18n_message("citcorpore.comum.relatorioVazio"));
			}

	
