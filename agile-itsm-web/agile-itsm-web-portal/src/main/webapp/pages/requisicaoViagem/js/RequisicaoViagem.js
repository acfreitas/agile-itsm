var idResponsavel;
var responsavel;
var idEmpregadoAux;
var nomeImpregadoAux;
var indexAux;

addEvent(window, "load", load, false);
function load() {
	
	document.form.afterLoad = function() {
		parent.escondeJanelaAguarde();
	}
	
}

function decodificaTextarea() {
	var infoNaoFuncionario = document.getElementById("infoNaoFuncionario").value;
	var infoNaoFuncionarioAux = document.getElementById("infoNaoFuncionarioAux").value;
	
	document.getElementById("infoNaoFuncionario").value = ObjectUtils.decodificaEnter(infoNaoFuncionario);
	document.getElementById("infoNaoFuncionarioAux").value = ObjectUtils.decodificaEnter(infoNaoFuncionarioAux);
}

function tooltip() {
	$(document).ready(function() {
		$('.titulo').tooltip();
	});
}

function gerarButtonEditDelete(row, obj){
	
	row.cells[3].innerHTML += '<a href="#" class="btn-action btn-success glyphicons edit" onclick="editarLinhaTabela(this.parentNode.parentNode.rowIndex)"><i></i></a>  ';
	row.cells[3].innerHTML += '<a href="#" class="btn-action glyphicons remove_2 btn-danger titulo" onclick="removerLinhaTabela(this.parentNode.parentNode.rowIndex)"><i></i></a>';

	if(obj.integranteFuncionario == 'N') {
		var html = $('#' + obj.idControleCITFramework + ' td:eq(0)').html();
		html = '<div>' + html + '</div>'
		$('#' + obj.idControleCITFramework + ' td:eq(0)').empty().append(html);
		
		var tooltipText = "<strong>" + i18n_message("dinamicview.informacoescomplementares") + "</strong><br />" + (obj.infoNaoFuncionario).replace(/\n/g, "<br />");
		$('#' + obj.idControleCITFramework + ' td:eq(0) div').tooltip({
			title: tooltipText,
			html: true
		});
	}
}

function deleteLinha(table, index) {
	HTMLUtils.deleteRow(table, index);
}

function getObjetoSerializado() {
	var obj = new CIT_RequisicaoViagemDTO();
	HTMLUtils.setValuesObject(document.form, obj);
	var itegranteViagem = HTMLUtils.getObjectsByTableId('tblIntegranteViagem');
	obj.integranteViagemSerialize = ObjectUtils.serializeObjects(itegranteViagem);
	return ObjectUtils.serializeObject(obj);
}

function calcularQuantidadeDias(obj) {
	
	if(document.getElementById("dataInicioViagem").value != ""){
		fctValidaData(document.getElementById("dataInicioViagem"));
	}
	if(document.getElementById("dataFimViagem").value != ""){
		fctValidaData(document.getElementById("dataFimViagem"));
	}
	
	
	var dataInicio = document.getElementById("dataInicioViagem").value;
	var dataFim = document.getElementById("dataFimViagem").value;

	var dtInicio = new Date();
	var dtFim = new Date();
	
	if(dataInicio != "" && dataFim == ""){
		document.form.qtdeDias.value = 1;
	}

	if (dataInicio != "" & dataFim != "") {
		
		if (validaData(dataInicio, dataFim, obj)) {
			dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/"))).setFullYear;
			dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/"))).setFullYear;
			var dias = DateTimeUtil.diferencaEmDias(dtInicio, dtFim);

			document.form.qtdeDias.value = dias + 1;
		}
		
	}
}

function fctValidaData(obj){
	
	if(obj == null){
		return false;
	}
	
    var data = obj.value;
    var dia = data.substring(0,2);
    var mes = data.substring(3,5);
    var ano = data.substring(6,10);
 
    //Criando um objeto Date usando os valores ano, mes e dia.
    var novaData = new Date(ano,(mes-1),dia);
 
    var mesmoDia = parseInt(dia,10) == parseInt(novaData.getDate());
    var mesmoMes = parseInt(mes,10) == parseInt(novaData.getMonth())+1;
    var mesmoAno = parseInt(ano) == parseInt(novaData.getFullYear());
 
    if (!((mesmoDia) && (mesmoMes) && (mesmoAno))){
        alert(i18n_message("si.comum.dataDe")+" "+obj.descricao+" "+i18n_message("si.comum.eInvalida"));  
        obj.value = "";
        obj.focus();   
        return false;
    } 
    return true;
}

/**
 * @author rodrigo.oliveira
 */
function validaData(dataInicio, dataFim, obj) {
	var dtInicio = new Date();
	var dtFim = new Date();

	dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/"))).setFullYear;
	dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/"))).setFullYear;

	if (dtInicio > dtFim) {
		alert(i18n_message("solicitacaoservico.validacao.datainiciomenorfinal"));
		$("#"+obj.id+"").val("");
		
		return false;
	} else
		return true;
}

function atribuiResponsavel(index, idEmp, nomeEmp) {
	idEmpregadoAux = idEmp;
	nomeImpregadoAux = nomeEmp;
	indexAux = index;

	$('#idIntegranteAux').val(idEmpregadoAux);

	document.form.fireEvent("validaAtribuicao");
}

function removerLinhaTabela(indice) {
	HTMLUtils.deleteRow('tblIntegranteViagem', indice);
	$(".tooltip").hide();
}

function editarLinhaTabela(rowIndex) {
	obj = HTMLUtils.getObjectByTableIndex('tblIntegranteViagem', rowIndex);	
	
	if (obj.integranteFuncionario == 'S'){
		limpaCamposIntegrante();
		$('#integranteFuncionario').click();
		$('#idEmpregado').val(obj.idEmpregado);
		$('#idRespPrestacaoContas').val(obj.idRespPrestacaoContas);
		$('#nomeEmpregado').val(obj.nome);
		$('#respPrestacaoContas').val(obj.respPrestacaoContas);
		
		document.form.fireEvent("validaAtribuicao");
	}else{
		limpaCamposIntegrante();
		$('#integranteNaoFuncionario').attr('checked', 'checked');
		$('#divEmpregado').hide();
		$('#divNaoFuncionario').show();
		$('#divResponsavelEmpregado').show();
		$('#divInfoNaoFuncionario').show();
		
		$('#idEmpregado').val(obj.idEmpregado);
		$('#idRespPrestacaoContas').val(obj.idRespPrestacaoContas);
		$('#nomeNaoFuncionario').val(obj.nomeNaoFuncionario);
		$('#respPrestacaoContas').val(obj.respPrestacaoContas);
		$('#infoNaoFuncionario').val(obj.infoNaoFuncionario);
	}
	
	document.getElementById("rowIndexIntegrante").value = rowIndex;
}

function startLoading() {
	
	document.getElementById('tdResultadoSLAPrevisto').style.display = 'none';
	document.getElementById('divMini_loading').style.display = 'block';
		
}


/** Autocompletes * */
var completeCidadeOrigem;
var completeCidadeDestino;
var completeEmpregado;
var completeNaoEmpregado;
var completeResponsavelEmpregado;


/** Inicializa��o da tela * */
$(document).ready(function() {
	
	completeCidadeOrigem = $('#nomeCidadeEUfOrigem').autocomplete({ 
		serviceUrl:'pages/autoCompleteCidade/autoCompleteCidade.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idCidadeOrigem').val(data);
			$('#nomeCidadeEUfOrigem').val(value);
		}
	});
	
	completeCidadeDestino = $('#nomeCidadeEUfDestino').autocomplete({ 
		serviceUrl:'pages/autoCompleteCidade/autoCompleteCidade.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idCidadeDestino').val(data);
			$('#nomeCidadeEUfDestino').val(value);
		}
	});
	
	completeEmpregado = $('#nomeEmpregado').autocomplete({ 
		serviceUrl:'pages/autoCompleteFuncionario/autoCompleteFuncionario.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idEmpregado').val(data);
			$('#nomeEmpregado').val(value);
			document.form.fireEvent("validaAtribuicao");
		}
	});
	
	completeResponsavelEmpregado = $('#respPrestacaoContas').autocomplete({ 
		serviceUrl:'pages/autoCompleteFuncionario/autoCompleteFuncionario.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idRespPrestacaoContas').val(data);
			$('#respPrestacaoContas').val(value);
		}
	});
	
	completeNaoEmpregado = $('#nomeNaoFuncionario').autocomplete({ 
		serviceUrl:'pages/autoCompleteNaoEmpregado/autoCompleteNaoEmpregado.load',
		noCache: true,
		onSelect: function(value, data){
				$('#idEmpregado').val(data);
				$('#nomeNaoFuncionario').val(value);
				$('#nomeNaoFuncionarioAux').val(value);
				document.form.fireEvent("restoreInfNaoFuncionario");
		}
	});

	
	// Esconde o campo de responsavel
	$('#divResponsavelEmpregado').hide();
	$('#divInfoNaoFuncionario').hide();
	
// $('#divEmpregadoOuNao').hide('slow');
	
	$('#divNaoFuncionario').hide();
	
	$('#integranteFuncionario').change(function() {
		
		$('#divNaoFuncionario').hide();
		$('#divEmpregado').show();
		
		$('#divResponsavelEmpregado').hide();
		
		$('#divInfoNaoFuncionario').hide();
	});
	
	$('#integranteNaoFuncionario').change(function() {
		$('#divNaoFuncionario').show();
		$('#divEmpregado').hide();
		
		$('#divResponsavelEmpregado').show();
		
		$('#divInfoNaoFuncionario').show();
	});
	
});


function adicionarEmpregado() {
	var obj = new CIT_IntegranteViagemDTO();
	obj.idEmpregado = $("#idEmpregado").val();
	obj.nome = $('#nomeEmpregado').val();
	
	var rowIndex = document.getElementById("rowIndexIntegrante").value;
	
	var eFunc = document.form.integranteFuncionario[0].checked;
	if(eFunc == true){
		var idResp = $('#idRespPrestacaoContas').val();
		if(idResp){
			obj.idRespPrestacaoContas = idResp;
			obj.respPrestacaoContas = $('#respPrestacaoContas').val();
		}else{
			obj.respPrestacaoContas = $('#nomeEmpregado').val();
		}
		
		if((obj.idEmpregado == null || obj.nome == null) || (obj.idEmpregado == "" || obj.nome == "")){
			alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("categoriaProblema.nome")+"] "+i18n_message("rh.alertEObrigatorio")+"!");			
			return;
		}
		
		obj.integranteFuncionario = 'S';
	} else {
		var naoFunc = $('#nomeNaoFuncionario').val();
		var idNaoFunc = $('#idEmpregado').val();
		
		if(idNaoFunc == null || idNaoFunc == "" && naoFunc != null && naoFunc != ""){
			document.form.fireEvent("createNaoFuncionario");
			return;
		}
		
		if(($('#nomeNaoFuncionario').val() != $('#nomeNaoFuncionarioAux').val()) || ($('#infoNaoFuncionario').val() != $('#infoNaoFuncionarioAux').val())){
			document.form.fireEvent("createNaoFuncionario");
			return;
		}
		
		if(naoFunc != null && naoFunc != "" && idNaoFunc != null && idNaoFunc != ""){
			obj.nome = naoFunc;
			obj.nomeNaoFuncionario = naoFunc;
			obj.idEmpregado = idNaoFunc;
		} else {
			alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("categoriaProblema.nome")+"] "+i18n_message("rh.alertEObrigatorio")+"!");		
			return;
		}
		
		var idResp = $('#idRespPrestacaoContas').val();
		if(idResp){
			obj.idRespPrestacaoContas = idResp;
			obj.respPrestacaoContas = $('#respPrestacaoContas').val();
		} else {
			alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("requisicaoViagem.responsavelPrestacaoDeContas")+"] "+i18n_message("rh.alertEObrigatorio")+"!");		
			return;
		}
		
		obj.infoNaoFuncionario = $("#infoNaoFuncionario").val();
		obj.integranteFuncionario = 'N';
	}
	
	if(StringUtils.isBlank(StringUtils.trim(rowIndex))) {
		if(eFunc == true) {
			HTMLUtils.addRow('tblIntegranteViagem', null, '', obj, [ 'nome', 'integranteFuncionario', 'respPrestacaoContas', '' ], ['idEmpregado'], i18n_message("citcorpore.comum.registroJaAdicionado"), [ gerarButtonEditDelete ], null, null, false);
		} else {
			HTMLUtils.addRow('tblIntegranteViagem', null, '', obj, [ 'nome', 'integranteFuncionario', 'respPrestacaoContas', '' ], ['nome'], i18n_message("citcorpore.comum.registroJaAdicionado"), [ gerarButtonEditDelete ], null, null, false);
		}
	} else {
		var integranteViagem = HTMLUtils.getObjectByTableIndex('tblIntegranteViagem', rowIndex);
		
		if(obj.idEmpregado == integranteViagem.idEmpregado) {
			HTMLUtils.updateRow('tblIntegranteViagem', null, '', obj, [ 'nome', 'integranteFuncionario', 'respPrestacaoContas', '' ], null, '', [ gerarButtonEditDelete ], null, null, rowIndex, false);
		} else {
			HTMLUtils.updateRow('tblIntegranteViagem', null, '', obj, [ 'nome', 'integranteFuncionario', 'respPrestacaoContas', '' ], ['idEmpregado'], i18n_message("citcorpore.comum.registroJaAdicionado"), [ gerarButtonEditDelete ], null, null, rowIndex, false);
		}
	}
	
	if(eFunc)
		$('#divResponsavelEmpregado').hide();
	
	limpaCamposIntegrante();
}

/*
 * Oculta a coluna de responsaveis por presta��o de contas na
 * tblIntegrantesViagem
 */
function ocultaColunaResp(obj){
	if(obj == true){
		$('td:nth-child(2),th:nth-child(2)').hide();
	} else {
		$('td:nth-child(2),th:nth-child(2)').show();
	}
}

function limpaCamposIntegrante(){
	$('#idEmpregado').val("");
	$('#nomeEmpregado').val("");
	$('#idRespPrestacaoContas').val("");
	$('#respPrestacaoContas').val("");
	$('#nomeNaoFuncionario').val("");
	$('#infoNaoFuncionario').val("");
	$('#nomeNaoFuncionarioAux').val("");
	$('#infoNaoFuncionarioAux').val("");
	document.getElementById("rowIndexIntegrante").value = "";
}

function validaCampos(){
	if($('#nomeNaoFuncionario').val() == null || $('#nomeNaoFuncionario').val() == ""){
		limpaCamposIntegrante();
	}
}
