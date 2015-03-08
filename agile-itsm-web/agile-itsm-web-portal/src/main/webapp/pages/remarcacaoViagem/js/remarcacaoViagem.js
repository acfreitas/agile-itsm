addEvent(window, "load", load, false);
	
function load(){ 
	document.form.afterRestore = function() {
		$('.tabs').tabs('select', 0);
	}
	fecharPopupItens();
}  

function serializarTabelas(){
	var tabela = HTMLUtils.getObjectsByTableId('tblItemRemarcacaoViagem');
	document.form.colDespesaViagemSerialize.value = ObjectUtils.serializeObjects(tabela);
}	

function remarcarViagem(){
	if(document.getElementById("idCidadeOrigemAux").value == document.getElementById("origem").value &&	document.getElementById("idCidadeDestinoAux").value == document.getElementById("destino").value &&
		document.getElementById("idaAux").value == document.getElementById("ida").value && document.getElementById("voltaAux").value == document.getElementById("volta").value){
		document.getElementById("remarcarRoteiro").value = "N";
	}else{
		document.getElementById("remarcarRoteiro").value = "S";
	}
		serializarTabelas();
		document.form.fireEvent("remarcarViagem");	
}

function fctValidaData(obj){
	if(obj.value != null && obj.value != ""){
		var data = obj.value;
	    var dia = data.substring(0,2)
	    var mes = data.substring(3,5)
	    var ano = data.substring(6,10)
	 
	    //Criando um objeto Date usando os valores ano, mes e dia.
	    var novaData = new Date(ano,(mes-1),dia);
	 
	    var mesmoDia = parseInt(dia,10) == parseInt(novaData.getDate());
	    var mesmoMes = parseInt(mes,10) == parseInt(novaData.getMonth())+1;
	    var mesmoAno = parseInt(ano) == parseInt(novaData.getFullYear());
	 
	    if (!((mesmoDia) && (mesmoMes) && (mesmoAno))){
	        alert(i18n_message("requisicaoViagem.dataInformadaInvalida"));   
	        obj.value = "";
	        obj.focus(); 
	        return false;
	    }  
	    return true;
	}
	return true;
}

function pesquisarRequisicoes() {
	
	if(document.getElementById("idSolicitacaoServico").value == null || document.getElementById("idSolicitacaoServico").value == ""){
		
		if(document.getElementById("idEmpregado").value == null || document.getElementById("idEmpregado").value == ""){
			
			if(document.getElementById("nomeEmpregado").value == null || document.getElementById("nomeEmpregado").value == ""){
			
				if(document.getElementById("dataInicio").value == null || document.getElementById("dataInicio").value == ""){
					
					if(document.getElementById("dataInicioAux").value == null || document.getElementById("dataInicioAux").value == ""){
				
						if(document.getElementById("dataFim").value == null || document.getElementById("dataFim").value == ""){
							
							if(document.getElementById("dataFimAux").value == null || document.getElementById("dataFimAux").value == ""){
							
								alert(i18n_message("MSG11"));	
								return;
							}
						} 
					} 
				}
			}	
		}
	}
	
	
	var dataI = document.getElementById("dataInicio").value;
	var dataIA = document.getElementById("dataInicioAux").value;
	var dataF = document.getElementById("dataFim").value;
	var dataFA = document.getElementById("dataFimAux").value;
	
	if((dataI != null && dataI != "") && (dataIA == null || dataIA == "")){
		alert(i18n_message("remarcacaoViagem.preencherIntervaloInicio"));
		return false;
	}
	
	if((dataIA != null && dataIA != "") && (dataI == null || dataI == "")){
		alert(i18n_message("remarcacaoViagem.preencherIntervaloInicio"));
		return false;
	}
	
	if((dataF != null && dataF != "") && (dataFA == null || dataFA == "")){
		alert(i18n_message("remarcacaoViagem.preencherIntervaloTermino"));
		return false;
	}
	
	if((dataFA != null && dataFA != "") && (dataF == null || dataF == "")){
		alert(i18n_message("remarcacaoViagem.preencherIntervaloTermino"));
		return false;
	}
	
	if((dataI != null && dataI != "") && (dataIA != null && dataIA != "")){
		if (!DateTimeUtil.comparaDatas(document.form.dataInicio, document.form.dataInicioAux, i18n_message("citcorpore.comum.validacao.periodoInicioRemarcacao"))){
			return false;
		} 
	}
	
	if((dataF != null && dataF != "") && (dataFA != null && dataFA != "")){
		if (!DateTimeUtil.comparaDatas(document.form.dataFim, document.form.dataFimAux, i18n_message("citcorpore.comum.validacao.periodoFimRemarcacao"))){
			return false;
		} 
	}
	
	if((dataI != null && dataI != "") && (dataFA != null && dataFA != "") && ($('#e').is(":checked"))){
		if (!DateTimeUtil.comparaDatas(document.form.dataInicio, document.form.dataFimAux, i18n_message("rh.dataInicioMaior"))){
			return false;
		} 
	}
	
	document.form.fireEvent("pesquisaRequisicoesViagem");
}

function adicionarItem(){
	if(!camposObrigatoriosPreenchidos())
		return;
	
	var dataFim = document.getElementById("prazoCotacao").value;
	var horaFim = document.getElementById("horaCotacao").value;
	var validade = (document.getElementById("prazoCotacao").value +" "+ horaFim + ":00");
	
	var hoje = new Date();
	var dia = hoje.getDate()
	var mes = hoje.getMonth()
	var ano = hoje.getFullYear()
	
	if (dia < 10)
		dia = "0" + dia
	
	if (ano < 2000)
		ano = "19" + ano

	if(!validaData((dia+"/"+(mes+1)+"/"+ano), dataFim, 2))
		return;
	
	var obj = new CIT_DespesaViagemDTO();
	
	

	if(document.getElementById('rowIndexItem').value == null ||  document.getElementById('rowIndexItem').value == undefined || document.getElementById('rowIndexItem').value < 1){
		obj.idRoteiro = document.getElementById("idRoteiro").value;
		obj.idSolicitacaoServico  = document.getElementById("idSolicitacao").value;
		obj.idTipo = document.getElementById("idTipo").value;
		obj.tipoDespesa = document.getElementById("tipoDespesa").options[document.getElementById("tipoDespesa").selectedIndex].text;
		obj.idFornecedor = document.getElementById("idFornecedor").value;
		obj.nomeFornecedor = document.getElementById("nomeFornecedor").value;
		obj.idMoeda  = document.getElementById("idMoeda").value;
		obj.nomeMoeda  = document.getElementById("nomeMoeda").value;
		obj.valor  = document.getElementById("valor").value;
		obj.valorTotal  = document.getElementById("valorAdiantamento").value;
		obj.quantidade  = document.getElementById("quantidade").value;
		obj.observacoes  = document.getElementById("observacoes").value;
		obj.idFormaPagamento  = document.getElementById("idFormaPagamento").value;
		obj.nomeFormaPagamento  = document.getElementById("idFormaPagamento").options[document.getElementById("idFormaPagamento").selectedIndex].text;
		obj.dataInicio  = DateTimeUtil.formatDate(new Date(), "dd/MM/yyyy");
		obj.prazoCotacao = document.getElementById("prazoCotacao").value;
		obj.horaCotacao = document.getElementById("horaCotacao").value;
		obj.horaCotacaoAux = document.getElementById("horaCotacao").value;
		obj.PrazoCotacaoAux = document.getElementById("prazoCotacao").value;
		
		HTMLUtils.addRow('tblItemRemarcacaoViagem', document.form, null, obj, ["dataInicio","tipoDespesa", "nomeFornecedor", "quantidade", "valorTotal", "nomeFormaPagamento", "nomeMoeda", "" ], null, "", [gerarImgItem], null, null, false);	
		
	} else {
		var obj = HTMLUtils.getObjectByTableIndex('tblItemRemarcacaoViagem', document.getElementById('rowIndexItem').value);
		
		obj.idRoteiro = document.getElementById("idRoteiro").value;
		obj.idSolicitacaoServico  = document.getElementById("idSolicitacao").value;
		obj.idTipo = document.getElementById("idTipo").value;
		obj.tipoDespesa = document.getElementById("tipoDespesa").options[document.getElementById("tipoDespesa").selectedIndex].text;
		obj.idFornecedor = document.getElementById("idFornecedor").value;
		obj.nomeFornecedor = document.getElementById("nomeFornecedor").value;
		obj.idMoeda  = document.getElementById("idMoeda").value;
		obj.nomeMoeda  = document.getElementById("nomeMoeda").value;
		obj.valor  = document.getElementById("valor").value;
		obj.valorTotal  = document.getElementById("valorAdiantamento").value;
		obj.quantidade  = document.getElementById("quantidade").value;
		obj.observacoes  = document.getElementById("observacoes").value;
		obj.idFormaPagamento  = document.getElementById("idFormaPagamento").value;
		obj.nomeFormaPagamento  = document.getElementById("idFormaPagamento").options[document.getElementById("idFormaPagamento").selectedIndex].text;
		obj.dataInicio  = DateTimeUtil.formatDate(new Date(), "dd/MM/yyyy");
		obj.prazoCotacao = document.getElementById("prazoCotacao").value;
		obj.horaCotacao = document.getElementById("horaCotacao").value;
		obj.horaCotacaoAux = document.getElementById("horaCotacao").value;
		obj.PrazoCotacaoAux = document.getElementById("prazoCotacao").value;
		
		HTMLUtils.updateRow('tblItemRemarcacaoViagem', document.form, null, obj, ["dataInicio","tipoDespesa", "nomeFornecedor", "quantidade", "valorTotal", "nomeFormaPagamento", "nomeMoeda", "" ], null, '', [gerarImgItem], null, null, document.getElementById('rowIndexItem').value, false);
		
		document.getElementById('rowIndexItem').value = null;
	}
	
	
	HTMLUtils.clearForm(document.formItem);
	fecharPopupItens();
}

function gerarImgItem(row, obj){
    row.cells[7].innerHTML = '<span onclick="excluirItem(this.parentNode.parentNode.rowIndex)"class="btn-action glyphicons remove_2 btn-danger titulo"><i></i></span>'+' '+'<span onclick="funcaoClickRowItem(this.parentNode.parentNode.rowIndex)" class="btn-action btn-success glyphicons edit titulo"><i></i></span>';
};

function excluirItem(rowIndex){
	if(window.confirm(i18n_message("despesaViagem.excluirDespesa")+"?")) {
		HTMLUtils.deleteRow('tblItemRemarcacaoViagem', rowIndex);
	}
};

function funcaoClickRowItem(row) {
		obj = HTMLUtils.getObjectByTableIndex("tblItemRemarcacaoViagem", row);
		document.getElementById("rowIndexItem").value = row;
		
		document.getElementById("moeda").disabled = false;
		
		document.getElementById("idRoteiro").value = obj.idRoteiro;
		document.getElementById("idSolicitacao").value = obj.idSolicitacaoServico;
		document.getElementById("idTipo").value =	obj.idTipo;
		$('#tipoDespesa').find('option[value="' + obj.idTipo + '"]').attr("selected", true);
		document.getElementById("idFornecedor").value = obj.idFornecedor; 
		document.getElementById("nomeFornecedor").value = obj.nomeFornecedor;
		document.getElementById("idMoeda").value = obj.idMoeda;
		document.getElementById("valor").value = obj.valor;
		document.getElementById("quantidade").value = obj.quantidade;
		document.getElementById("observacoes").value = obj.observacoes;
		$('#idFormaPagamento').find('option[value="' + obj.idFormaPagamento + '"]').attr("selected", true);
		document.getElementById("prazoCotacao").value = obj.prazoCotacao;
		document.getElementById("horaCotacao").value = obj.horaCotacao;
		document.formItem.fireEvent('calcularTotal');
		document.formItem.fireEvent('preencherComboMoeda');
		
		$("#POPUP_ITEMDESPESA").dialog("open");
};

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

function camposObrigatoriosPreenchidos(){
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
	
	if(!document.getElementById("prazoCotacao").disabled && StringUtils.isBlank(StringUtils.trim(document.getElementById("prazoCotacao").value))){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("despesaViagem.dataCotacaoExpira")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		return false;
	}
	
	if(!document.getElementById("horaCotacao").disabled && StringUtils.isBlank(StringUtils.trim(document.getElementById("horaCotacao").value))){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("despesaViagem.horaCotacaoExpira")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		return false;
	}
	
	if(!document.getElementById("prazoCotacao").disabled && !DateTimeUtil.comparaDatas({value: DateTimeUtil.formatDate(new Date(), "dd/MM/yyyy")}, document.getElementById("prazoCotacao"), "Data da cotacao e menor que a data atual!")) {
		return false;
	}
	
	if(!document.getElementById("prazoCotacao").disabled && !DateTimeUtil.comparaDatas(document.getElementById("prazoCotacao"), document.getElementById("volta"), "Data da cotacao e maior que a data fim da viagem!")) {
		return false;
	}
	
	if(!document.getElementById("horaCotacao").disabled && StringUtils.isBlank(StringUtils.trim(document.getElementById("horaCotacao").value))){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("despesaViagem.horaCotacaoExpira")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		return false;
	}
	
	if(!document.getElementById("horaCotacao").disabled && !DateTimeUtil.comparaHora(DateTimeUtil.formatDate(calculaMinutos(new Date(), 30), "HH:mm"), document.getElementById("horaCotacao").value) && DateTimeUtil.converteData(DateTimeUtil.formatDate(new Date(), "dd/MM/yyyy")).getTime() == DateTimeUtil.converteData(document.getElementById("prazoCotacao").value).getTime()) {
		alert(i18n_message("despesaViagem.horaCotacaoMenorAtual")+"!");
		return false;
	}
	
	if(StringUtils.isBlank(StringUtils.trim(document.getElementById("valor").value))){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("itemControleFinanceiroViagem.valorUnitario")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		return false;
	}
	
	if(StringUtils.isBlank(StringUtils.trim(document.getElementById("quantidade").value))){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("itemPedidoCompra.quantidade")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		return false;
	}
	return true;
}

function addItens(row, obj){
	limparTabela('tblItemRemarcacaoViagem');
	limparTabela('tblDespesaHist');
	limparTabela('tblItemDespesaOriginal');
	
	document.getElementById("idSolicitacao").value = obj.idSolicitacaoServico;
	document.getElementById("idIntegranteViagem").value = obj.idIntegranteViagem;
	
	document.form.fireEvent('recuperaInformacoesIntegrante');
	
	var tabela = document.getElementById("tblRequisicoesViagem");
	var count = tabela.rows.length;
	
	if(count >= 1)
		contemItensAdd = true;
	else 
		contemItensAdd = false;

	$('.tabsbar a[href="#tab2-3"]').tab('show');
	
}

$('#tab2').click(function(){
	if(document.getElementById("idSolicitacaoServico").value != null && document.getElementById("idSolicitacaoServico").value != ""
		&&	document.getElementById("idSolicitacaoServico").value != null && document.getElementById("idSolicitacaoServico").value != ""){
		document.form.fireEvent('recuperaInformacoesIntegrante');
		
		var tabela = document.getElementById("tblRequisicoesViagem");
		var count = tabela.rows.length;
		
		if(count >= 1)
			contemItensAdd = true;
		else 
			contemItensAdd = false;
		
		$('.tabsbar a[href="#tab2-3"]').tab('show');
	}else{
		alert(i18n_message("remarcaoViagem.nenhumIntegranteSelecionado"));
		return false;
	}
	
});

function calcularQuantidadeDias() {

	var dataInicio = document.getElementById("ida").value;
	var dataFim = document.getElementById("volta").value;

	var dtInicio = new Date();
	var dtFim = new Date();

	if (dataInicio != "" & dataFim != "") {

		if (validaData(dataInicio, dataFim)){
			dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/"))).setFullYear;
			dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/"))).setFullYear;
			var dias = DateTimeUtil.diferencaEmDias(dtInicio, dtFim);
			document.form.qtdeDias.value = dias + 1;
		}
	}
}

//Valida se a dataFim � maior que a dataInicio
//Parametro opcao serve para alterar a mensagem que ser� exibida para o usuario
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
			alert(i18n_message("solicitacaoservico.validacao.datainiciomenorfinal"));
		return false;
	}else
		return true;
}

function limpaIntegrante(){
	if(document.getElementById("nomeEmpregado").value == "")
		document.getElementById("idEmpregado").value = "";
}

function gerarImg(row, obj) {
	
	var id = obj.idIntegranteViagem;
	var nome = obj.nome;
	
	if(!id)
		id = 0;
	
	if(!nome)
		nome = "vazio";
	
	var funcao = "addItemIntegrante(" + id + ", \"" + nome + "\")";
	
	row.cells[7].innerHTML = "<a href='#' class='btn-action btn-success glyphicons edit' title='Remarcar viagem do integrante' " +
			"onclick='" + funcao + "' ><i></i></a> ";
};	

function cancelar(){
	limparFormulario();
	$('.tabsbar a[href="#tab1-3"]').tab('show');
}

function limparFormulario(){
	HTMLUtils.clearForm(document.form);
	HTMLUtils.clearForm(document.formItem);
	$('#e').attr("checked",true);
	limparTabela('tblRequisicoesViagem');
	limparTabela('tblItemRemarcacaoViagem');
	limparTabela('tblDespesaHist');
	limparTabela('tblItemDespesaOriginal');
	
}

function limparTabela(nomeDaTabela){
	var tabela = document.getElementById(nomeDaTabela);
	var count = tabela.rows.length;
	
	while (count > 1) {
		tabela.deleteRow(count - 1);
		count--;
	}
}

/**Autocompletes **/
var completeEmpregado; 
var completeCidadeOrigem;
var completeCidadeDestino; 
var completeFornecedor;

/** Inicializa��o da tela **/
$(document).ready(function() {
	$('#horaCotacao').mask('99:99');
	
	$('.format-money').maskMoney({
		thousands: '',
		decimal: ',',
		allowNegative: true
	});
	
	completeCidadeOrigem = $('#nomeCidadeOrigem').autocomplete({ 
		serviceUrl:'pages/autoCompleteCidade/autoCompleteCidade.load',
		noCache: true,
		onSelect: function(value, data){
			$('#origem').val(data);
			$('#nomeCidadeEUfOrigem').val(value);
		}
	});
	
	completeCidadeDestino = $('#nomeCidadeDestino').autocomplete({ 
		serviceUrl:'pages/autoCompleteCidade/autoCompleteCidade.load',
		noCache: true,
		onSelect: function(value, data){
			$('#destino').val(data);
			$('#nomeCidadeEUfDestino').val(value);
		}
	});
	
	completeFornecedor = $('#nomeFornecedor').autocomplete({ 
		serviceUrl:'pages/autoCompleteParceiro/autoCompleteParceiro.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idFornecedor').val(data);
			$('#nomeFornecedor').val(value);
		}
	});
});

$("#POPUP_ITEMDESPESA").dialog({
	autoOpen : false,
	width : "90%",
//	height : $(window).height() - 20,
	modal : true
});

function abrePopupItens(){
	document.formItem.fireEvent("carregaPopup");
	$("#POPUP_ITEMDESPESA").dialog("open");
	
}

function fecharPopupItens(){
	$("#POPUP_ITEMDESPESA").dialog("close");
}

function tratarValoresTipoMovimentacao() {
	var selectTipo = document.getElementById("tipoDespesa");
	document.getElementById("idTipo").value = selectTipo.options[selectTipo.selectedIndex].value;
	document.form.fireEvent("tratarValoresTipoMovimentacao");
}
