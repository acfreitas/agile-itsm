

			var objTab = null;

			addEvent(window, "load", load, false);

			function load() {
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				}
			}

			/*alterado por
			 desenvolvedor: rcs (Rafael César Soyer)
			 data: 24/12/2014
			*/
			function filtrar(){
				var dataInicio = document.getElementById("dataInicio").value;
				var dataFim = document.getElementById("dataFim").value;

                if (dataInicio.trim() == ""){
                    alert(i18n_message("citcorpore.comum.validacao.datainicio"));
                    return false;
                }
                else if(DateTimeUtil.isValidDate(dataInicio) == false){
                    alert(i18n_message("citcorpore.comum.datainvalida"));
                    document.getElementById("dataInicio").value = '';
                    return false;
                }

                if (dataFim.trim() == ""){
                    alert(i18n_message("citcorpore.comum.validacao.datafim"));
                    return false;
                }
                else if(DateTimeUtil.isValidDate(dataFim) == false){
                    alert(i18n_message("citcorpore.comum.dataFinalInvalida"));
                     document.getElementById("dataFim").value = '';
                    return false;
                }

				if(validaData(dataInicio,dataFim)){
					setGantt(i18n_message("citcorpore.comum.aguardecarregando"));
					document.form.fireEvent("filtrarGantt");
				}
			}

			function setGantt(txt) {
				$('.gantt').html(txt);
			}

			function ocultarInformacao(){
				$('#informacao').hide();
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

			/* desenvolvedor: rcs (Rafael César Soyer)
		      Limpa tudo na tela, campos preenchidos pelo usuário e também o que foi filtrado na tabela "gantt".
		      Chama o método limpar que vêm via include do "javaScriptsComuns.jsp".
		      data: 26/12/2014
		    */
			function meulimpar(){
				limpar();
				$('.gantt').empty();
		    }

		
