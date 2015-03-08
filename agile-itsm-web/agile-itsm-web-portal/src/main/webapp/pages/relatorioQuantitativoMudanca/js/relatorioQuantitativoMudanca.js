
			var temporizador;

			addEvent(window, "load", load, false);

			function load() {


			}

			function imprimirRelatorioQuantitativo() {
				var dataInicio = document.getElementById("dataInicio").value;
				var dataFim = document.getElementById("dataFim").value;

				if (validaData(dataInicio,dataFim) ) {
					JANELA_AGUARDE_MENU.show();
					document.form.fireEvent("imprimirRelatorioQuantitativo");
				}
			}

			function imprimirRelatorioQuantitativoXls() {
				var dataInicio = document.getElementById("dataInicio").value;
				var dataFim = document.getElementById("dataFim").value;


				if (validaData(dataInicio,dataFim) ) {
					JANELA_AGUARDE_MENU.show();
					document.form.fireEvent("imprimirRelatorioQuantitativoXls");
				}
			}

			/**
			* @author rodrigo.oliveira
			*/
			function validaData(dataInicio, dataFim) {
				if (typeof(locale) === "undefined") locale = '';

				if (dataInicio === null || dataInicio === undefined || dataInicio === "") {
					alert(i18n_message("citcorpore.comum.validacao.datainicio") );
				 	return false;
				}

				if (dataFim === null || dataFim === undefined || dataFim === "") {
					alert(i18n_message("citcorpore.comum.validacao.datafim") );
				 	return false;
				}

				if (DateTimeUtil.isValidDate(dataInicio) == false) {
					alert(i18n_message("citcorpore.comum.datainvalida") );
				 	document.getElementById("dataInicio").value = '';
				 	return false;
				}

				if (DateTimeUtil.isValidDate(dataFim) == false) {
					alert(i18n_message("citcorpore.comum.dataFinalInvalida") );
					 document.getElementById("dataFim").value = '';
					return false;
				}

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

	
