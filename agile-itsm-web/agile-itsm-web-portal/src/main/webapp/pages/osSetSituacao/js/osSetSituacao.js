var objTab = null;

addEvent(window, "load", load, false);
function load(){
	document.form.afterRestore = function () {
		document.getElementById('tabTela').tabber.tabShow(0);
	}
}

function LOOKUP_OS_select(id,desc){
	document.form.restore({idOS:id});
}

$(function() {
	$('.datepicker').datepicker();

	$("#POPUP_LISTA_INCIDENTES").dialog({
		title: i18n_message('citcorpore.comum.associarIncidente'),
		autoOpen : false,
		height : 300,
		width : 800,
		modal : true
	});

});

function gravarForm(){
	var count = GRID_ITENS.getMaxIndex();
	var existeErro = false;
	var contadorAux = 0;
	var objs = new Array();
	document.getElementById("executadoOS").value = formataStringToDouble(document.getElementById("executadoOS").value);
	for (var i = 1; i <= count; i++){
		var trObj = document.getElementById('GRID_ITENS_TD_' + NumberUtil.zerosAEsquerda(i,5));
		if (!trObj){
			continue;
		}
		var idAtividadesOSObj = document.getElementById('idAtividadesOS' + NumberUtil.zerosAEsquerda(i,5));
		var quantidadeObj = document.getElementById('quantidade' + NumberUtil.zerosAEsquerda(i,5));
		var qtdeExecutadaObj = document.getElementById('qtdeExecutada' + NumberUtil.zerosAEsquerda(i,5));
		var glosaAtividadeObj = document.getElementById('glosa' + NumberUtil.zerosAEsquerda(i,5));
		var complexidadeObj = document.getElementById('complexidade' + NumberUtil.zerosAEsquerda(i,5));
		var demandaObj = document.getElementById('demanda' + NumberUtil.zerosAEsquerda(i,5));
		var objObj = document.getElementById('obs' + NumberUtil.zerosAEsquerda(i,5));
		trObj.bgColor = 'white';
		complexidadeObj.style.backgroundColor = 'white';
		quantidadeObj.style.backgroundColor = 'white';
		glosaAtividadeObj.style.backgroundColor = 'white';
		demandaObj.style.backgroundColor = 'white';
		objObj.style.backgroundColor = 'white';
		if (complexidadeObj.value == ''){
			trObj.bgColor = 'orange';
			complexidadeObj.style.backgroundColor = 'orange';
			quantidadeObj.style.backgroundColor = 'orange';
			glosaAtividadeObj.style.backgroundColor = 'orange';
			demandaObj.style.backgroundColor = 'orange';
			objObj.style.backgroundColor = 'orange';
			alert('Informe a complexidade! Linha: ' + i);
			return;
		}
		if (demandaObj.value == ''){
			trObj.bgColor = 'orange';
			complexidadeObj.style.backgroundColor = 'orange';
			quantidadeObj.style.backgroundColor = 'orange';
			glosaAtividadeObj.style.backgroundColor = 'orange';
			demandaObj.style.backgroundColor = 'orange';
			objObj.style.backgroundColor = 'orange';
			alert('Informe a demanda! Linha: ' + i);
			return;
		}
		if (quantidadeObj.value == ''){
			trObj.bgColor = 'orange';
			complexidadeObj.style.backgroundColor = 'orange';
			quantidadeObj.style.backgroundColor = 'orange';
			glosaAtividadeObj.style.backgroundColor = 'orange';
			demandaObj.style.backgroundColor = 'orange';
			objObj.style.backgroundColor = 'orange';
			alert('Informe o custo! Linha: ' + i);
			return;
		}
		var objItem = new CIT_DemandaDTO();
		objItem.idAtividadesOS = idAtividadesOSObj.value;
		objItem.complexidade = complexidadeObj.value;
		objItem.custoAtividade = quantidadeObj.value;
		objItem.glosaAtividade = glosaAtividadeObj.value;
		objItem.descricaoAtividade = demandaObj.value;
		objItem.qtdeExecutada = qtdeExecutadaObj.value;
		objItem.obsAtividade = objObj.value;
		objs[contadorAux] = objItem;
		contadorAux = contadorAux + 1;

		if (properties.permiteValorZeroAtv == "S"){
			if (quantidadeObj.value == '' || quantidadeObj.value == '0,00' || quantidadeObj.value == '0'){
				trObj.bgColor = 'orange';
				complexidadeObj.style.backgroundColor = 'orange';
				quantidadeObj.style.backgroundColor = 'orange';
				glosaAtividadeObj.style.backgroundColor = 'orange';
				demandaObj.style.backgroundColor = 'orange';
				objObj.style.backgroundColor = 'orange';
				alert('Falta definir custo da atividade ! Linha: ' + i);
				existeErro = true;
			}
		}
	}
	if (existeErro){
		return;
	}
	document.form.colItens_Serialize.value = ObjectUtils.serializeObjects(objs);

	count = GRID_GLOSAS.getMaxIndex();
	existeErro = false;
	contadorAux = 0;
	objs = new Array();
	for (var i = 1; i <= count; i++){
		var trObj = document.getElementById('GRID_GLOSAS_TD_' + NumberUtil.zerosAEsquerda(i,5));
		if (!trObj){
			continue;
		}
		var descricaoGlosaObj = document.getElementById('descricaoGlosa' + NumberUtil.zerosAEsquerda(i,5));
		var numeroOcorrenciasObj = document.getElementById('numeroOcorrencias' + NumberUtil.zerosAEsquerda(i,5));
		var percAplicadoObj = document.getElementById('percAplicado' + NumberUtil.zerosAEsquerda(i,5));
		var custoGlosaObj = document.getElementById('custoGlosa' + NumberUtil.zerosAEsquerda(i,5));
		var ocorrenciasObj = document.getElementById('ocorrencias' + NumberUtil.zerosAEsquerda(i,5));
		var idGlosaOSObj = document.getElementById('idGlosaOS' + NumberUtil.zerosAEsquerda(i,5));
		trObj.bgColor = 'white';
		complexidadeObj.style.backgroundColor = 'white';
		quantidadeObj.style.backgroundColor = 'white';
		glosaAtividadeObj.style.backgroundColor = 'white';
		demandaObj.style.backgroundColor = 'white';
		objObj.style.backgroundColor = 'white';
		if (descricaoGlosaObj.value == ''){
			trObj.bgColor = 'orange';
			descricaoGlosaObj.style.backgroundColor = 'orange';
			numeroOcorrenciasObj.style.backgroundColor = 'orange';
			percAplicadoObj.style.backgroundColor = 'orange';
			custoGlosaObj.style.backgroundColor = 'orange';
			ocorrenciasObj.style.backgroundColor = 'orange';
			alert('Informe a descrição da Glosa! Linha: ' + i);
			return;
		}
		if (numeroOcorrenciasObj.value == ''){
			trObj.bgColor = 'orange';
			descricaoGlosaObj.style.backgroundColor = 'orange';
			numeroOcorrenciasObj.style.backgroundColor = 'orange';
			percAplicadoObj.style.backgroundColor = 'orange';
			custoGlosaObj.style.backgroundColor = 'orange';
			ocorrenciasObj.style.backgroundColor = 'orange';
			alert('Informe o número de ocorrências para Glosa! Linha: ' + i);
			return;
		}
		var objItem = new CIT_GlosaOSDTO();
		objItem.idGlosaOS = idGlosaOSObj.value;
		objItem.descricaoGlosa = descricaoGlosaObj.value;
		objItem.ocorrencias = ocorrenciasObj.value;
		objItem.percAplicado = percAplicadoObj.value;
		objItem.custoGlosa = custoGlosaObj.value;
		objItem.numeroOcorrencias = numeroOcorrenciasObj.value;
		objs[contadorAux] = objItem;
		contadorAux = contadorAux + 1;
	}
	document.form.colGlosas_Serialize.value = ObjectUtils.serializeObjects(objs);
	document.form.save();
}
var seqSelecionada = '';
function setaRestoreItem(complex, det, obs, formula, custo, glosa, qtdeExec, idAtvOS, exibirBotao, idServicoContratoContabil, divAssociacao){
	if (seqSelecionada != ''){
		eval('HTMLUtils.setValue(\'complexidade' + seqSelecionada + '\', \'' + complex + '\')');
		eval('document.form.demanda' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + det + '\')');
		eval('document.form.obs' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + obs + '\')');
		eval('document.form.quantidade' + seqSelecionada + '.value = "' + custo + '"');
		eval('document.form.glosa' + seqSelecionada + '.value = "' + glosa + '"');
		eval('document.form.qtdeExecutada' + seqSelecionada + '.value = "' + qtdeExec + '"');
		eval('document.form.idAtividadesOS' + seqSelecionada + '.value = "' + idAtvOS + '"');
		eval('document.form.idServicoContratoContabil' + seqSelecionada + '.value = "' + idServicoContratoContabil + '"');
		document.getElementById("divDemanda"+seqSelecionada).innerHTML = ObjectUtils.decodificaEnter(det) + "<div style='font-weight:bold; padding-top: 6px;'>"+formula+"</div>";
		document.getElementById("divAssociacaoInc"+seqSelecionada).innerHTML = divAssociacao;
		if(exibirBotao == "true"){
			exibirBotaoVincInc(seqSelecionada);
		}
	}
}
var seqSelecionadaGlosa = '';
function setaRestoreItemGlosa(idGlosa, det, obs, numOc, perc, glosa){
	if (seqSelecionadaGlosa != ''){
		eval('document.form.idGlosaOS' + seqSelecionadaGlosa + '.value = "' + idGlosa + '"');
		eval('document.form.descricaoGlosa' + seqSelecionadaGlosa + '.value = ObjectUtils.decodificaEnter(\'' + det + '\')');
		eval('document.form.ocorrencias' + seqSelecionadaGlosa + '.value = ObjectUtils.decodificaEnter(\'' + obs + '\')');
		eval('document.form.numeroOcorrencias' + seqSelecionadaGlosa + '.value = "' + numOc + '"');
		eval('document.form.percAplicado' + seqSelecionadaGlosa + '.value = "' + perc + '"');
		eval('document.form.custoGlosa' + seqSelecionadaGlosa + '.value = "' + glosa + '"');
	}
}
function selecionaServicoContrato(){
	JANELA_AGUARDE_MENU.show();
	document.form.fireEvent('restoreInfoServicoContrato');
}
function desabilitaObsFinal(){
	document.form.obsFinalizacao.disabled=false;
	document.form.obsFinalizacao.onkeydown = null;
}

function carregaGlosas(){
	document.form.fireEvent('preencheItensGlosa');
}

function setaGlosaItem(descricao){
	if (seqSelecionada != ''){
		eval('document.form.descricaoGlosa' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + descricao + '\')');
		eval('document.form.numeroOcorrencias' + seqSelecionada + '.value = 0');
	}
}

function exibirBotaoVincInc(seq){
	document.getElementById('divAssociacaoInc'+seq).style.display = 'block';
}

function mostrarIncidentesParaAssociar(idServicoContrato){
	document.form.idServicoContratoContabil.value = idServicoContrato;
	document.form.fireEvent('listaIncidentesParaVincular');
}

function associarIncidentes(){
	document.formAssociar.idOS.value = document.form.idOS.value;
	document.formAssociar.idServicoContratoContabil.value = document.form.idServicoContratoContabil.value;

	var tabela = document.getElementById('tableIncidentes');
	var count = tabela.rows.length;
	var contadorAux = 0;
	var incidentes = new Array();

	for ( var i = 1; i <= count; i++) {
		var trObj = document.getElementById('idSolicitacaoServico' + i);
		if (!trObj) {
			continue;
		}
		incidentes[contadorAux] = getIncidentes(i);
		contadorAux = contadorAux + 1;
	}
	serializaIncidentes();
	//document.getElementById('divOsSelecao').innerHTML = 'Aguarde... carregando...';
	document.formAssociar.fireEvent("associarOSIncidente");
}

serializaIncidentes = function() {
	var tabela = document.getElementById('tableIncidentes');
	var count = tabela.rows.length;
	var contadorAux = 0;
	var incidentes = new Array();
	for ( var i = 1; i <= count; i++) {
		var trObj = document.getElementById('idSolicitacaoServico' + i);
		if (!trObj) {
			continue;
		}else if(trObj.checked){
			incidentes[contadorAux] = getIncidentes(i);
			contadorAux = contadorAux + 1;
			continue;
		}

	}
	var incidentesSerializadas = ObjectUtils.serializeObjects(incidentes);
	document.formAssociar.incidentesSerializadas.value = incidentesSerializadas;
	return true;
}

getIncidentes = function(seq) {
	var VinculaOsIncidenteDTO = new CIT_VinculaOsIncidenteDTO();
	VinculaOsIncidenteDTO.sequencia = seq;
	VinculaOsIncidenteDTO.idSolicitacaoServico = eval('document.formAssociar.idSolicitacaoServico' + seq + '.value');
	return VinculaOsIncidenteDTO;
}

function preencheNumeracaoItens(){
	var count = GRID_ITENS.getMaxIndex();
	flag = false;
	for (var i = 1; i <= count; i++){
		if(!flag){
			var item = document.getElementById('item' + NumberUtil.zerosAEsquerda(i,5));
			if(!item){
				flag = true;
				var item = document.getElementById('item' + NumberUtil.zerosAEsquerda(i+1,5));
				if(!item){
					continue;
				}
			}
			item.innerHTML = i < 10 ? NumberUtil.zerosAEsquerda(i,2) : i;
		}else{
			var item = document.getElementById('item' + NumberUtil.zerosAEsquerda(i+1,5));
			if(!item){
				continue;
			}
			item.innerHTML = i < 10 ? NumberUtil.zerosAEsquerda(i,2) : i;
		}
	}
}

function unlockGlosas(){
	for(var i = 0; i < document.form.length; i++){
		var elem = document.form.elements[i];
		var ok = false;
		if (elem.name.indexOf("idGlosaOS") > -1){
			ok = true;
		}
		if (elem.name.indexOf("descricaoGlosa") > -1){
			ok = true;
			HTMLUtils.unlockField(elem);
		}
		if (elem.name.indexOf("numeroOcorrencias") > -1){
			ok = true;
		}
		if (elem.name.indexOf("ocorrencias") > -1){
			ok = true;
			HTMLUtils.unlockField(elem);
		}
		if (elem.name.indexOf("percAplicado") > -1){
			ok = true;
		}
		if (elem.name.indexOf("custoGlosa") > -1){
			elem.readOnly = true;
			ok = true;
		}
		if (ok){
			elem.disabled = false;
		}
	}
}
function calculaFormulaANS(seq, objFieldName){
	JANELA_AGUARDE_MENU.show();
	document.form.seqANS.value = seq;
	document.form.fieldANS.value = objFieldName;
	document.form.fireEvent('calculaFormulaANS');
}

function alterarValorAtiviade(){
	var count = GRID_ITENS.getMaxIndex();
	var existeErro = false;
	var contadorAux = 0;
	var objs = new Array();
	for (var i = 1; i <= count; i++){
		var trObj = document.getElementById('GRID_ITENS_TD_' + NumberUtil.zerosAEsquerda(i,5));
		if (!trObj){
			continue;
		}
		var idAtividadesOSObj = document.getElementById('idAtividadesOS' + NumberUtil.zerosAEsquerda(i,5));
		var quantidadeObj = document.getElementById('quantidade' + NumberUtil.zerosAEsquerda(i,5));
		var qtdeExecutadaObj = document.getElementById('qtdeExecutada' + NumberUtil.zerosAEsquerda(i,5));
		var glosaAtividadeObj = document.getElementById('glosa' + NumberUtil.zerosAEsquerda(i,5));
		var complexidadeObj = document.getElementById('complexidade' + NumberUtil.zerosAEsquerda(i,5));
		var demandaObj = document.getElementById('demanda' + NumberUtil.zerosAEsquerda(i,5));
		var objObj = document.getElementById('obs' + NumberUtil.zerosAEsquerda(i,5));
		trObj.bgColor = 'white';
		complexidadeObj.style.backgroundColor = 'white';
		quantidadeObj.style.backgroundColor = 'white';
		glosaAtividadeObj.style.backgroundColor = 'white';
		demandaObj.style.backgroundColor = 'white';
		objObj.style.backgroundColor = 'white';
		if (complexidadeObj.value == ''){
			trObj.bgColor = 'orange';
			complexidadeObj.style.backgroundColor = 'orange';
			quantidadeObj.style.backgroundColor = 'orange';
			glosaAtividadeObj.style.backgroundColor = 'orange';
			demandaObj.style.backgroundColor = 'orange';
			objObj.style.backgroundColor = 'orange';
			alert('Informe a complexidade! Linha: ' + i);
			return;
		}
		if (demandaObj.value == ''){
			trObj.bgColor = 'orange';
			complexidadeObj.style.backgroundColor = 'orange';
			quantidadeObj.style.backgroundColor = 'orange';
			glosaAtividadeObj.style.backgroundColor = 'orange';
			demandaObj.style.backgroundColor = 'orange';
			objObj.style.backgroundColor = 'orange';
			alert('Informe a demanda! Linha: ' + i);
			return;
		}
		if (quantidadeObj.value == ''){
			trObj.bgColor = 'orange';
			complexidadeObj.style.backgroundColor = 'orange';
			quantidadeObj.style.backgroundColor = 'orange';
			glosaAtividadeObj.style.backgroundColor = 'orange';
			demandaObj.style.backgroundColor = 'orange';
			objObj.style.backgroundColor = 'orange';
			alert('Informe o custo! Linha: ' + i);
			return;
		}
		var objItem = new CIT_DemandaDTO();
		objItem.idAtividadesOS = idAtividadesOSObj.value;
		objItem.complexidade = complexidadeObj.value;
		objItem.custoAtividade = quantidadeObj.value;
		objItem.glosaAtividade = glosaAtividadeObj.value;
		objItem.descricaoAtividade = demandaObj.value;
		objItem.qtdeExecutada = qtdeExecutadaObj.value;
		objItem.obsAtividade = objObj.value;
		objs[contadorAux] = objItem;
		contadorAux = contadorAux + 1;

		if (properties.permiteValorZeroAtv == "S"){
			if (quantidadeObj.value == '' || quantidadeObj.value == '0,00' || quantidadeObj.value == '0'){
				trObj.bgColor = 'orange';
				complexidadeObj.style.backgroundColor = 'orange';
				quantidadeObj.style.backgroundColor = 'orange';
				glosaAtividadeObj.style.backgroundColor = 'orange';
				demandaObj.style.backgroundColor = 'orange';
				objObj.style.backgroundColor = 'orange';
				alert('Falta definir custo da atividade ! Linha: ' + i);
				existeErro = true;
			}
		}
	}
	if (existeErro){
		return;
	}
	document.form.colItens_Serialize.value = ObjectUtils.serializeObjects(objs);

	document.form.fireEvent('calculaValorTotalAtividade');
}

function serealizaCustoExecutado(){

	var count = GRID_ITENS.getMaxIndex();
	var contadorAux = 0;
	var objs = new Array();
	for (var i = 1; i <= count; i++){
		var trObj = document.getElementById('GRID_ITENS_TD_' + NumberUtil.zerosAEsquerda(i,5));
		if (!trObj){
			continue;
		}
		var qtdeExecutadaObj = document.getElementById('qtdeExecutada' + NumberUtil.zerosAEsquerda(i,5));
		var objItem = new CIT_DemandaDTO();
		objItem.qtdeExecutada = qtdeExecutadaObj.value;

		objs[contadorAux] = objItem;
		contadorAux = contadorAux + 1;

	}


	if (objs.length > 0){

		document.form.colQtdExec_Serialize.value = ObjectUtils.serializeObjects(objs);

		document.form.fireEvent('atualizaTotalExecutado');

	}


}

function GRID_ITENS_onDeleteRowByImgRef(){
	alert('Não é permitido efetuar essa exclusão!');
	return false;
}

	function GRID_GLOSAS_onDeleteRowByImgRef(objImg){
	alert('Não é permitido efetuar essa exclusão!');
	return false;
}

function formataStringToDouble(num){
	while(num.indexOf(".") !== -1)
		num = num.replace(".", "");
	return num.replace(",", ".");
}

document.form.onClear = function(){
	GRID_ITENS.deleteAllRows();
};
