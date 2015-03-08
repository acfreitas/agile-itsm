
		var temporizador;
		addEvent(window, "load", load, false);
		function load() {
			$("#generationType").attr('value', '');
		}

		function gerarCausaSolucao(generationFormat, generationType){
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
			if ($("#idContrato").attr("value") == "") {
				alert(i18n_message("citcorpore.comum.selecioneContrato"));
				return false;
			}
			if ($("#idGrupos option").length == 0) {
				alert(i18n_message("relatorioCausaSolucao.selecioneGrupo") + ".");
				return false;
			}
			if ($("#idCausas option").length == 0) {
				alert(i18n_message("relatorioCausaSolucao.selecioneCausa") + ".");
				return false;
			}
			if ($("#idSolucoes option").length == 0) {
				alert(i18n_message("relatorioCausaSolucao.selecioneSolucao") + ".");
				return false;
			}

			if(validaData(dataInicio,dataFim)){
				JANELA_AGUARDE_MENU.show();

				$("#generationType").attr('value', generationType);

				//Faz o select de todos os options do select informado. Utilizado para select multiplos que precisam de todos os options selecionados ao fazer o post.
				markAllOptionsSelectedForSelectMultiple('idServicos');
				markAllOptionsSelectedForSelectMultiple('idGrupos');
				markAllOptionsSelectedForSelectMultiple('idCausas');
				markAllOptionsSelectedForSelectMultiple('idSolucoes');

				if (generationFormat == "grafico") {
					document.form.fireEvent("gerarCausaSolucaoGraficoBarras");
				} else {
					document.form.fireEvent("gerarCausaSolucaoAnalitico");
				}
			}
		}

		function preencherGrupo(opcao){
			document.form.fireEvent("preencherComboGrupo");
			$('#idGrupos option').remove();
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
					inserirNaListaServico(data, value + "   ("+tipoDemanda+")");
				}
			});

		});

		/**Monta os parametros para a buscas do autocomplete**/
		function montaParametrosAutocompleteServico(){
			tipoDemanda = $("#idTipoDemandaServico").val();
		 	contrato =  $("#idContrato").val() ;
		 	completeServico.setOptions({params: {contrato: contrato, tipoDemanda: tipoDemanda} });
		}

		function inserirNaListaServico(id,nome){
			$("#idServicos").append(new Option(nome, id));
			$('#servicoBusca').val('');
		}

		function inserirNaLista(lista1, lista2,usuarios){
			var texto = $("#"+lista1+" option:selected").text();
			var valor = $("#"+lista1+" option:selected").val();
			if(texto!="" & valor!=""){
				$('#'+lista2).append("<option value='"+valor+"' selected='selected'>"+texto+"</option>");
				$('#'+lista1+' option:selected').remove();
				/* if(usuarios!=true)
					preencherComboUsuarios(lista2); */
			}
		}

		function RetirarDaLista(lista1, lista2,usuarios){
			var texto = $("#"+lista2+" option:selected").text();
			var valor = $("#"+lista2+" option:selected").val();
			if(texto!="" & valor!=""){
				$('#'+lista1).append("<option value='"+valor+"' selected='selected'>"+texto+"</option>");
				$('#'+lista2+' option:selected').remove();
				/* if(usuarios!=true)
					preencherComboUsuarios(lista2); */
			}
		}

		function inserirTodosDaLista(lista1, lista2,usuarios){
			$("#"+lista1+" > option").each(function(i){
				$('#'+lista2).append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");
				$(this).remove();
		    });
			/* if(usuarios!=true)
				preencherComboUsuarios(lista2); */
		}

		function retirarTodosDaLista(lista1, lista2,usuarios){
			$("#"+lista2+" > option").each(function(i){
				$('#'+lista1).append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");
				$(this).remove();
		    });
			/* if(usuarios!=true)
				preencherComboUsuarios(lista2); */
		}

		function limparLista(lista){
			$("#"+lista+" > option").each(function(i){
				$(this).remove();
		    });
		}

		function RetirarDaListaSimples(lista, usuarios){
			var texto = $("#"+lista+" option:selected").text();
			var valor = $("#"+lista+" option:selected").val();
			if(texto!="" & valor!=""){
				$('#'+lista+' option:selected').remove();
			}
		}

		function retirarTodosDaListaSimples(lista, usuarios){
			$("#"+lista+" > option").each(function(i){
				$(this).remove();
		    });
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

		function limpar(){
			//document.form.clear();
			location.reload();
		}

		function verificaContratoETipoServico() {
			if (document.form.idContrato.value == '' ||  document.form.idContrato.value == '-- Todos --'){
				alert(i18n_message("solicitacaoservico.validacao.contrato"));
				document.form.idContrato.focus();
				return false;
			}
			if (document.form.idTipoDemandaServico.value == '' ||  document.form.idTipoDemandaServico.value == '-- Todos --'){
				alert(i18n_message("citcorpore.comum.informeTipoSolicitacao"));
				document.form.idTipoDemandaServico.focus();
				return false;
			}

			return true;
		}

		function limparServico(){
			$('#servicoBusca').val('');
			$('#idServicos').empty();
		}
  	
