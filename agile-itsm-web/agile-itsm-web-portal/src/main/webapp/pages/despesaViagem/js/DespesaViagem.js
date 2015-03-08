$("#POPUP_ITEMCONTROLEFINANCEIRO").dialog({
	autoOpen : false,
	width : "98%",
	modal : true
});

function getObjetoSerializado() {
	var obj = new CIT_DespesaViagemDTO();
	obj.colIntegrantesViagem_Serialize = ObjectUtils.deserializeCollectionFromString(document.getElementById("colIntegrantesViagem_Serialize").value);
	HTMLUtils.setValuesObject(document.form, obj);
	return ObjectUtils.serializeObject(obj);
}

function expandirItemDespesa() {
	$('.browser ul').show(0);
	$('.expandable').addClass('collapsable').removeClass('expandable');
	$('.lastExpandable').addClass('lastCollapsable').removeClass('lastExpandable');
}
function retrairItemDespesa() {
	$('.browser ul').hide(0);
	$('.collapsable').addClass('expandable').removeClass('collapsable');
	$('.lastCollapsable').addClass('lastExpandable').removeClass('lastCollapsable');
}

function abrirPopupItemControleFinanceiro() {
	document.getElementById("idDespesaViagem").value = "";
	$("#POPUP_ITEMCONTROLEFINANCEIRO").dialog("open");
	document.form.fireEvent('carregarPopup');
}

function fecharPopupItemControleFinanceiro() {
	$("#POPUP_ITEMCONTROLEFINANCEIRO").dialog("close");
}

function tratarValoresTipoMovimentacao() {
	var selectTipo = document.getElementById("tipoDespesa");
	document.getElementById("idTipo").value = selectTipo.options[selectTipo.selectedIndex].value;
	document.formItem.fireEvent("tratarValoresTipoMovimentacao");
}

function adicionarDespesaViagem() {
	if(!validarCamposObrigatorios()) {
		return false;
	}
	
	if(StringUtils.isBlank(StringUtils.trim(document.getElementById("idDespesaViagem").value))) {
		var listIntegrantesViagem = new Array();
		var integranteViagemDTO = "";
		
		var integrantes = $('.idIntegrate:checked');
		for(i = 0; i < integrantes.length; i++) {
			integranteViagemDTO = new CIT_IntegranteViagemDTO();
			integranteViagemDTO.idIntegranteViagem = integrantes.eq(i).val();
			listIntegrantesViagem.push(integranteViagemDTO);
		}
		
		document.getElementById("colIntegrantesViagem_SerializeAux").value = ObjectUtils.serializeObjects(listIntegrantesViagem);
	}
	
	document.formItem.fireEvent("adicionarDespesaViagem");
	
	fecharPopupItemControleFinanceiro();
}

function editarDespesaViagem(idDespesaViagem) {
	document.getElementById("idDespesaViagem").value = idDespesaViagem;
	$("#POPUP_ITEMCONTROLEFINANCEIRO").dialog("open");
	document.formItem.fireEvent("carregarPopup");
}

function excluirDespesaViagem(idDespesaViagem) {
	if(window.confirm(i18n_message("despesaViagem.excluirDespesa")+"?")) {
		document.getElementById("idDespesaViagem").value = idDespesaViagem;
		document.getElementById("idSolicitacaoServicoAux").value = document.getElementById("idSolicitacaoServico").value;
		document.formItem.fireEvent("excluirDespesaViagem");
	}
}

function calculaMinutos(data, minutos) {
	var hours = data.getHours();
	var min = data.getMinutes()+minutos;
	
	if(min < 0) {
		while(diffMin < 0) {
			hours--;
			min += 60;
		}
	} else {
		while(min > 60) {
			hours++;
			min -= 60;
		}
	}
	
	return new Date(data.getFullYear(), data.getMonth(), data.getDay(), hours, min, data.getSeconds(), data.getMilliseconds());
};

//Valida se os campos obrigatorios da Popup item estao preenchidos (o Valid[Required] nao funciona nesse caso)
//False + mensagem caso algum campo obrigatorio nao esteja preenchido
//True caso todos os campos obrigatorios estejam preenchidos
function validarCamposObrigatorios(){
	if(document.getElementById("tipoDespesa").value == ""){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("requisicaoViagem.tipoDespesa")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		return false;
	}
	
	if(StringUtils.isBlank(StringUtils.trim(document.getElementById("nomeFornecedor").value)) || StringUtils.isBlank(StringUtils.trim(document.getElementById("idFornecedor").value))){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("itemControleFinanceiroViagem.fornecedor")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		return false;
	}
	
	if(StringUtils.isBlank(StringUtils.trim(document.getElementById("idMoeda").value))){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("contrato.moeda")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		return false;
	}
	
	if(StringUtils.isBlank(StringUtils.trim(document.getElementById("idFormaPagamento").value))){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("itemControleFinanceiroViagem.formaPagamento")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		return false;
	}
	
	if(!document.getElementById("prazoCotacaoAux").disabled && StringUtils.isBlank(StringUtils.trim(document.getElementById("prazoCotacaoAux").value))){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("despesaViagem.dataCotacaoExpira")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		return false;
	}
	
	if(!document.getElementById("prazoCotacaoAux").disabled && !DateTimeUtil.comparaDatas({value: DateTimeUtil.formatDate(new Date(), "dd/MM/yyyy")}, document.getElementById("prazoCotacaoAux"), i18n_message("despesaViagem.dataCotacaoMenorAtual")+"!")) {
		return false;
	}
	
	if(!document.getElementById("prazoCotacaoAux").disabled && !DateTimeUtil.comparaDatas(document.getElementById("prazoCotacaoAux"), document.getElementById("dataFimViagem"), i18n_message("despesaViagem.dataCotacaoMaiorFim")+"!")) {
		return false;
	}
	
	if(!document.getElementById("horaCotacaoAux").disabled && StringUtils.isBlank(StringUtils.trim(document.getElementById("horaCotacaoAux").value))){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("despesaViagem.horaCotacaoExpira")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		return false;
	}
	
	if(!document.getElementById("horaCotacaoAux").disabled && !DateTimeUtil.comparaHora(DateTimeUtil.formatDate(calculaMinutos(new Date(), 30), "HH:mm"), document.getElementById("horaCotacaoAux").value) && DateTimeUtil.converteData(DateTimeUtil.formatDate(new Date(), "dd/MM/yyyy")).getTime() == DateTimeUtil.converteData(document.getElementById("prazoCotacaoAux").value).getTime()) {
		alert(i18n_message("despesaViagem.horaCotacaoMenorAtual")+"!");
		return false;
	}
	
	if(StringUtils.isBlank(StringUtils.trim(document.getElementById("valor").value))){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("itemControleFinanceiroViagem.valorUnitario")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		return false;
	}
	
	if(StringUtils.isBlank(StringUtils.trim(document.getElementById("quantidade").value))){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("carrinho.quantidade")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		return false;
	}
	
	if(!$('.idIntegrate:checked').length > 0) {
		alert(i18n_message("despesaViagem.selecioneUmIntegrante")+"!");
		return false;
	}
	
	return true;
}

//Masks
$('#horaCotacaoAux').mask('99:99');
$('.format-money').maskMoney({
	thousands: '',
	decimal: ',',
	allowNegative: true
});

// Auto Complete Fornecedor
var completeFornecedor = $('#nomeFornecedor').autocomplete({ 
	serviceUrl:'pages/autoCompleteParceiro/autoCompleteParceiro.load',
	noCache: true,
	onSelect: function(value, data){
		$('#idFornecedor').val(data);
		$('#nomeFornecedor').val(value);
	}
});

// Marcar todos
$('body').on('click', '#idIntegranteMarcaTodos', function() {
	if(this.checked) {
		$('.idIntegrate').attr('checked', 'checked');
	} else {
		$('.idIntegrate').removeAttr('checked');
	}
});

$('body').on('click', '.idIntegrate', function() {
	var checkedCount = $('.idIntegrate:checked').length;
	var checkCount = $('.idIntegrate').length;
	if((document.getElementById('idIntegranteMarcaTodos').checked == false) && (checkedCount == checkCount)) {
		document.getElementById('idIntegranteMarcaTodos').checked = true;
	} else {
		document.getElementById('idIntegranteMarcaTodos').checked = false;
	}
});