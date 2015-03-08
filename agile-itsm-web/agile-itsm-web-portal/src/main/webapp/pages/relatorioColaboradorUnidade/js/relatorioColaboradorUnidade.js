
		function inserirNaListaColaborador(id,nome){
			var aux = new Option(nome, id);
			var options = document.getElementById("idColaborador").options;
			for(var i = 0; i < options.length; i++)
				if(parseInt(options[i].value) === id){
					alert(i18n_message("relatorioColaboradorUnidade.colaboradorDuplicado"));
					limparColaborador();
					return;
				}

			$("#idColaborador").append(aux);
			limparColaborador();
		}

		function limparLista(lista){
			$("#"+lista+" > option").each(function(i){
				$(this).remove();
		    });
		}


		function limparColaborador(){
			$('#colaboradorBusca').val('');
		}

		/**Autocomplete **/
		var completeColaborador;
		$(document).ready(function() {
			$('#colaboradorBusca').on('click', function(){
				verificaContrato();
				montaParametrosAutocompleteColaborador();
			});

			completeColaborador = $('#colaboradorBusca').autocomplete({
				serviceUrl:'pages/autoCompleteSolicitante/autoCompleteSolicitante.load',
				noCache: true,
				onSelect: function(value, data){
					inserirNaListaColaborador(data, value);
					document.form.idUnidade.focus();
				}
			});

		});
		var contrato;

		/**Monta os parametros para a buscas do autocomplete**/
		function montaParametrosAutocompleteColaborador(){
		 	contrato =  $("#idContrato").val();
		 	unidade =  $("#idUnidade").val();
		 	completeColaborador.setOptions({params: {contrato: contrato, unidade: unidade} });
		}

		function RetirarDaLista(lista1, lista2){
			var texto = $("#"+lista2+" option:selected").text();
			var valor = $("#"+lista2+" option:selected").val();
			if(texto!="" & valor!=""){
				$('#'+lista1).append("<option value='"+valor+"' selected='selected'>"+texto+"</option>");
				$('#'+lista2+' option:selected').remove();
			}
		}

		function retirarTodosDaLista(lista1, lista2){
			$("#"+lista2+" > option").each(function(i){
				$('#'+lista1).append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");
				$(this).remove();
		    });
		}

		function limpar() {
			limparLista("idColaborador");
			document.form.clear();
		}

		function reportEmpty(){
			alert(i18n_message("citcorpore.comum.relatorioVazio"));
		}

		function verificaContrato() {
			if (document.form.idContrato.value == '' ||  document.form.idContrato.value == '-- Todos --'){
				alert(i18n_message("solicitacaoservico.validacao.contrato"));
				document.form.idContrato.focus();
				return false;
			}
			return true;
		}

		function preencherComboUnidade() {
			document.form.fireEvent('carregaUnidade');
		}

		function imprimirRelatorio(formato) {
			var contrato = document.getElementById("idContrato");

			if (contrato.value == ""){
				alert(i18n_message("solicitacaoservico.validacao.contrato"));
				contrato.focus();
				return false;
			}

			document.form.formatoArquivoRelatorio.value = formato.value;
			$("#idColaborador > option").attr("selected", true);

			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("imprimirRelatorio");
		}
  	
