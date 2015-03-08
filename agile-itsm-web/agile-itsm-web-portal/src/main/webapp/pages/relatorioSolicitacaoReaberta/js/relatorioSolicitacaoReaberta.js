/**Autocomplete **/
var completeServico;
var completeSolicitante;
var tipoDemanda;
var contrato;

/**Monta os parametros para a buscas do autocomplete**/
function montaParametrosAutocompleteServico(){
	contrato =  $("#idContrato").val();
	tipoDemanda = $("#idTipoDemandaServico").val();
	completeServico.setOptions({params: {contrato: contrato, tipoDemanda: tipoDemanda} });
}

function montaParametrosAutocompleteSolicitante(){
	contrato =  $("#idContrato").val();
	completeSolicitante.setOptions({params: {contrato: contrato} });
}

function valida(){
	if (document.getElementById('dataInicialReabertura').value==''){
		alert(i18n_message("relatorio.solicitacaoReaberta.informeDataInicioReabertura"));
		return false;
	}
	if (document.getElementById('dataFinalReabertura').value==''){
		alert(i18n_message("relatorio.solicitacaoReaberta.informeDataFimReabertura"));
		return false;
	}
/*
   Não está validando porque ainda precisamos criar uma estrutura para validar a data na internacionalização
  	if (!DateTimeUtil.isValidDate(document.getElementById('dataInicial').value)){
		alert(i18n_message("citcorpore.comum.datainvalida"));
		return false;
	}

  	if (!DateTimeUtil.isValidDate(document.getElementById('dataFinal').value)){
		alert(i18n_message("citcorpore.comum.dataFinalInvalida"));
		return false;
	}
*/	
	return true;
}

function gerarRelatorio(formato){
	if (valida()){
		//alimentaVisualizacao();
		document.getElementById('formato').value=formato;
		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("gerarRelatorio");
	}
}

function configurarObjetos(relatorio){
	if(relatorio.value!=""){
		document.form.fireEvent("configuraObjetos");
	}
}

function limpar(){
	document.form.clear();
}