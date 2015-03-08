
		/**
		* @author Bruno.Aquino
		*/

		function inserirNaListaServico(id,nome){
			$("#listaServico").append(new Option(nome, id));
			limparServico();
		}
		function mudarTipoDemanda(){
			 $("#idTipoDemandaServico option:first").attr('selected','selected');
		}

		function validaData(dataInicio, dataFim) {
			if (typeof(locale) === "undefined") locale = '';

			var dataInicio = document.getElementById("dataInicio").value;
			var dataFim = document.getElementById("dataFim").value;

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
			limparListaEnvio();
			limparLista("listaServico");
			dataInicio.focus();

		}

		function reportEmpty(){
			alert(i18n_message("citcorpore.comum.relatorioVazio"));
		}
		function RetirarDaLista(lista1, lista2,usuarios){
			var texto = $("#"+lista2+" option:selected").text();
			var valor = $("#"+lista2+" option:selected").val();
			if(texto!="" & valor!=""){
				$('#'+lista1).append("<option value='"+valor+"' selected='selected'>"+texto+"</option>");
				$('#'+lista2+' option:selected').remove();
				if(usuarios!=true)
					preencherComboUsuarios(lista2);
			}
		}

		function retirarTodosDaLista(lista1, lista2,usuarios){
			$("#"+lista2+" > option").each(function(i){
				$('#'+lista1).append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");
				$(this).remove();
		    });
			if(usuarios!=true)
				preencherComboUsuarios(lista2);
		}

		function limparListaEnvio(){
			$("#listaServico > option").each(function(i){
				$(this).remove();
		    });
		}

		function limparLista(lista){
			$("#"+lista+" > option").each(function(i){
				$(this).remove();
		    });
		}

		function preencherComboUsuarios(segundaTabela){
			var valorTipoFitroUsuarios = document.getElementById("selecionarGrupoUnidade").value;
			var listaGrupoUnidade = "";
			if(valorTipoFitroUsuarios=="grupo"){
				$("#"+segundaTabela+" > option").each(function(i){
					listaGrupoUnidade+=this.value+";"
			    });
			}else{
				$("#"+segundaTabela+" > option").each(function(i){
					listaGrupoUnidade+=this.value+";"
			    });
			}
			document.form.listaGrupoUnidade.value = listaGrupoUnidade;
			document.form.fireEvent("preencherComboUsuarios");
		}

		function imprimirRelatorio(formato) {
			var dataInicio = document.getElementById("dataInicio");
			var dataFim = document.getElementById("dataFim");
			var contrato = document.getElementById("idContrato");
			var listaServicos = document.getElementById("listaServico");

			if (validaData(dataInicio,dataFim) ) {
				var listaServicos = "";
				if (dataInicio.value == ""){
					alert(i18n_message("citcorpore.comum.validacao.datainicio"));
					document.getElementById("dataInicio").value = '';
					dataInicio.focus();
					return false;
				}
				if (dataFim.value == ""){
					alert(i18n_message("citcorpore.comum.validacao.datafim"));
					document.getElementById("dataFim").value = '';
					dataFim.focus();
					return false;
				}
				if (contrato.value == ""){
					alert(i18n_message("solicitacaoservico.validacao.contrato"));
					contrato.focus();
					return false;
				}

				$("#listaServico > option").each(function(i){
					listaServicos+=this.value+";"
				});

				if(listaServicos!=""){
					document.form.listaServicos.value = listaServicos;
					document.form.formatoArquivoRelatorio.value = formato.value;
					document.form.fireEvent("imprimirRelatorio");
				}else{
					alert(i18n_message("citcorpore.comum.selecioneUmServico"));
					return false;
				}
				JANELA_AGUARDE_MENU.show();

			}
		}

		function limparServico(){
			$('#servicoBusca').val('');
			$( "#idServico" ).val( '' );

		}

		/**Autocomplete **/
		var completeServico;
		$(document).ready(function() {

			$('#servicoBusca').on('click', function(){
				montaParametrosAutocompleteServico();
			});

			completeServico = $('#servicoBusca').autocomplete({
				serviceUrl:'pages/autoCompleteServico/autoCompleteServico.load',
				noCache: true,
				onSelect: function(value, data){
					tipoDemanda = $('#idTipoDemandaServico option:selected').text();
					$('#idServico').val(data);
					$('#servicoBusca').val(value);
					inserirNaListaServico($('#idServico').val() , $('#servicoBusca').val() + "   ("+tipoDemanda+")");
				}
			});

		});
		var tipoDemanda;
		var contrato;

		/**Monta os parametros para a buscas do autocomplete**/
		function montaParametrosAutocompleteServico(){
			tipoDemanda = $("#idTipoDemandaServico").val();
		 	contrato =  $("#idContrato").val() ;
		 	completeServico.setOptions({params: {contrato: contrato, tipoDemanda: tipoDemanda} });
		}
  	
