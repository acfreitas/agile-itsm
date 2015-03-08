

	function verificaContrato(){
		if($("#idContrato").val()==""){
			alert(i18n_message("solicitacaoServico.selecioneContrato"));
			idContrato.focus();
		}

	}

	function limpar() {
		document.form.clear();
		limparLista("segundaListaGrupo");
		limparLista("primeiraListaGrupo");
		limparLista("listaServico");
		dataInicio.focus();

	}

	function reportEmpty(){
		alert(i18n_message("citcorpore.comum.relatorioVazio"));
	}
	function mostrarFuncionario(valor){
		if(valor.value=="S"){
			$("#funcionario").show();
		}else{
			$("#funcionario").hide();
			inserirTodosDaLista();
		}
	}

	function mostrarGrupoUnidade(valor){
		if(valor.value=="grupo"){
			$("#Grupo").show();
			$("#Unidade").hide();
		}else{
			$("#Unidade").show();
			$("#Grupo").hide();
			inserirTodosDaLista();
		}
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

	function preencherGrupo(opcao){
		document.form.fireEvent("preencherComboGrupo");
	}

/*
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
	} */

	function imprimirRelatorio(formato) {
		var dataReferencia = document.getElementById("dataReferencia");
		var diasAbertos = document.getElementById("qtdDiasAbertos");

		var contrato = document.getElementById("idContrato");
		var listaGrupo = document.getElementById("segundaListaGrupo");
		var listaServico = document.getElementById("listaServico");


		var listaGrupos = "";
		var listaServicos = "";

		if (contrato.value == ""){
			alert(i18n_message("solicitacaoservico.validacao.contrato"));
			contrato.focus();
			return false;
		}
		if (dataReferencia.value == ""){
			alert("Data de Referência: Obrigatório");
			document.getElementById("dataReferencia").value = '';
			dataReferencia.focus();
			return false;
		}
		if (diasAbertos.value == ""){
			alert(i18n_message("relatorioKpi.diasAbertoObrigatorio"));
			document.getElementById("qtdDiasAbertos").value = '';
			diasAbertos.focus();
			return false;
		}

		$("#segundaListaGrupo > option").each(function(i){
			listaGrupos+=this.value+";"
		});

		$("#listaServico > option").each(function(i){
			listaServicos+=this.value+";"
		});

		if(listaGrupos==""){
			alert(i18n_message("controle.grupoObrigatorio"));
			listaGrupo.focus();
			return false;
		}
		if(listaServicos!=""){
			document.form.listaGrupos.value = listaGrupos;
			document.form.listaServicos.value = listaServicos;
			document.form.formatoArquivoRelatorio.value = formato.value;
			document.form.fireEvent("imprimirRelatorio");
		}else{
			alert(i18n_message("citcorpore.comum.selecioneUmServico"));
			listaServico.focus();
			return false;
		}
		JANELA_AGUARDE_MENU.show();
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

	function inserirNaListaServico(id,nome){
		$("#listaServico").append(new Option(nome, id));
		limparServico();
	}
	function mudarTipoDemanda(){
		 $("#idTipoDemandaServico option:first").attr('selected','selected');
	}
  	
