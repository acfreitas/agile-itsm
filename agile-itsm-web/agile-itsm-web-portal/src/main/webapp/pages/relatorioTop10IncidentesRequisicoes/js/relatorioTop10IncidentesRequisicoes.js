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

$(document).ready(function() {
	$('#nomeServico').on('click', function(){
		montaParametrosAutocompleteServico();
	});
	completeServico = $('#nomeServico').autocomplete({ 
		serviceUrl:'pages/autoCompleteServico/autoCompleteServico.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idServico').val(data);
			$('#nomeServico').val(value);
		}
	});
	
	$('#solicitante').on('click', function(){
		montaParametrosAutocompleteSolicitante();
	});
	completeSolicitante = $('#solicitante').autocomplete({ 
		serviceUrl:'pages/autoCompleteSolicitante/autoCompleteSolicitante.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idSolicitante').val(data);
			$('#solicitante').val(value);
		}
	});
});

function limpar(){
	document.form.clear();
	document.getElementById("Resumida").checked=true;
}

function alimentaVisualizacao(){
	if (document.getElementById("Resumida").checked){
		document.getElementById("visualizacao").value = "R";
	} else {
		document.getElementById("visualizacao").value = "A";
	}
}

function valida(){
	if ((document.getElementById('idRelatorio').value=='')||(document.getElementById('idRelatorio').value=='0')){
		alert(i18n_message("relatorioTop10IncidentesRequisicoes.selecioneRelatorio"));
		return false;
	}
	if (document.getElementById('dataInicial').value==''){
		alert(i18n_message("citcorpore.comum.validacao.datainicio"));
		return false;
	}
	if (document.getElementById('dataFinal').value==''){
		alert(i18n_message("citcorpore.comum.validacao.datafim"));
		return false;
	}
	if (document.getElementById('solicitante').value==''){
		document.getElementById('idSolicitante').value='0';
	}
	if (document.getElementById('nomeServico').value==''){
		document.getElementById('idServico').value='0';
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
		alimentaVisualizacao();
		document.getElementById('formato').value=formato;
		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("gerarRelatorio");
	}
}

function preencherComboUnidade(opcao){
	if(opcao.value!=""){
		document.form.fireEvent("preencherComboUnidade");
	}
}

function configurarObjetos(relatorio){
	if(relatorio.value!=""){
		document.form.fireEvent("configuraObjetos");
	}
}