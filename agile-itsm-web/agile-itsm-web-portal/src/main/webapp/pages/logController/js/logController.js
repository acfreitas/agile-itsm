
			addEvent(window, "load", load, false);
			function load() {
				$("#POPUP_SOLICITANTE").dialog({
					autoOpen : false,
					width : 600,
					height : 400,
					modal : true
				});
			}
			function LOOKUP_SOLICITANTE_select(id, desc) {
				document.getElementById("idUsuario").value = id;
				document.getElementById("nomeUsuario").value = desc;
				$("#POPUP_SOLICITANTE").dialog("close");
			}
			function abrePopupUsuario() {
				$("#POPUP_SOLICITANTE").dialog("open");
			}
			function imprimirRelatorio() {
				var dataInicio = document.getElementById("dataInicio").value;
				var dataFim = document.getElementById("dataFim").value;

				if (dataInicio != "") {
					if (DateTimeUtil.isValidDate(dataInicio) == false) {
						alert(i18n_message("citcorpore.comum.validacao.datainicio"));
						document.getElementById("dataInicio").value = '';
						return false;
					}
				}
				if (dataFim != "") {
					if (DateTimeUtil.isValidDate(dataFim) == false) {
						alert(i18n_message("citcorpore.comum.validacao.datafim"));
						document.getElementById("dataFim").value = '';
						return false;
					}
				} else {
					if (DateTimeUtil.isValidDate(dataInicio) == false) {
						alert(i18n_message("citcorpore.comum.validacao.datainicio"));
						document.getElementById("dataInicio").value = '';
						return false;
					}
					if (DateTimeUtil.isValidDate(dataFim) == false) {
						alert(i18n_message("citcorpore.comum.validacao.datafim"));
						document.getElementById("dataFim").value = '';
						return false;
					}
				}
				if (validaData(dataInicio, dataFim)) {
					JANELA_AGUARDE_MENU.show();
					document.form.fireEvent("imprimirRelatorio");

				}
			}

			function filtrar() {
				var dataInicio = document.getElementById("dataInicio").value;
				var dataFim = document.getElementById("dataFim").value;

				if (dataInicio != "") {
					if (DateTimeUtil.isValidDate(dataInicio) == false) {
						alert(i18n_message("citcorpore.comum.validacao.datainicio"));
						document.getElementById("dataInicio").value = '';
						return false;
					}
				}
				if (dataFim != "") {
					if (DateTimeUtil.isValidDate(dataFim) == false) {
						alert(i18n_message("citcorpore.comum.validacao.datafim"));
						document.getElementById("dataFim").value = '';
						return false;
					}
				} else {
					if (DateTimeUtil.isValidDate(dataInicio) == false) {
						alert(i18n_message("citcorpore.comum.validacao.datainicio"));
						document.getElementById("dataInicio").value = '';
						return false;
					}
					if (DateTimeUtil.isValidDate(dataFim) == false) {
						alert(i18n_message("citcorpore.comum.validacao.datafim"));
						document.getElementById("dataFim").value = '';
						return false;
					}
				}
				if (validaData(dataInicio, dataFim)) {
					document.form.fireEvent("filtrar");
				}
			}

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
		
