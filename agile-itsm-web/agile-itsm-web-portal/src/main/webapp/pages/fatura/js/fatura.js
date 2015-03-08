
var objTab = null;

addEvent(window, "load", load, false);
function load(){
	document.form.afterRestore = function () {
		document.getElementById('tabTela').tabber.tabShow(0);
	}
}

/* JQUERY  */

$(function() {
	$('.datepicker').datepicker();
});

/* JQUERY */

function LOOKUP_OS_select(id,desc){
	document.form.restore({idOS:id});
}
function gravarSituacao(){
	document.form.fireEvent('updateSituacao');
}

function gravarForm(){
	var count = GRID_ITENS.getMaxIndex();
	var existeErro = false;
	var contadorAux = 0;
	var objs = new Array();
	for (var i = 1; i <= count; i++){
		var trObj = document.getElementById('GRID_ITENS_TD_' + NumberUtil.zerosAEsquerda(i,5));
		if (!trObj){
			continue;
		}
		var idAcordoObj = document.getElementById('idAcordoNivelServicoContrato' + NumberUtil.zerosAEsquerda(i,5));
		var descricaoAcordoObj = document.getElementById('descricaoAcordo' + NumberUtil.zerosAEsquerda(i,5));
		var detalhamentoObj = document.getElementById('detalhamento' + NumberUtil.zerosAEsquerda(i,5));
		var valorApuradoObj = document.getElementById('valorApurado' + NumberUtil.zerosAEsquerda(i,5));
		var percentualGlosaObj = document.getElementById('percentualGlosa' + NumberUtil.zerosAEsquerda(i,5));
		var valorGlosaObj = document.getElementById('valorGlosa' + NumberUtil.zerosAEsquerda(i,5));
		trObj.bgColor = 'white';
		valorApuradoObj.style.backgroundColor = 'white';
		if (valorApuradoObj.value == ''){
			trObj.bgColor = 'orange';
			descricaoAcordoObj.style.backgroundColor = 'orange';
			valorApuradoObj.style.backgroundColor = 'orange';
			detalhamentoObj.style.backgroundColor = 'orange';
			percentualGlosaObj.style.backgroundColor = 'orange';
			valorGlosaObj.style.backgroundColor = 'orange';
			alert('Informe o valor apurado! Linha: ' + i);
			return;
		}
		var objItem = new CIT_FaturaApuracaoANSDTO();
		objItem.idAcordoNivelServicoContrato = idAcordoObj.value;
		objItem.valorApurado = valorApuradoObj.value;
		objItem.detalhamento = detalhamentoObj.value;
		objItem.percentualGlosa = percentualGlosaObj.value;
		objItem.valorGlosa = valorGlosaObj.value;
		objs[contadorAux] = objItem;
		contadorAux = contadorAux + 1;
	}
	if (existeErro){
		return;
	}
	document.form.colItens_Serialize.value = ObjectUtils.serializeObjects(objs);
	document.form.save();
}
var seqSelecionada = '';
function setaRestoreItem(desc, det, valorApur, percGlosa, valorGlosa, compl, detAcordo, idAcordoNivel){
	if (seqSelecionada != ''){
		eval('document.form.idAcordoNivelServicoContrato' + seqSelecionada + '.value = \'' + idAcordoNivel + '\'');
		eval('document.form.descricaoAcordo' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + desc + '\')');
		eval('document.form.detalhamento' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + det + '\')');
		eval('document.form.valorApurado' + seqSelecionada + '.value = "' + valorApur + '"');
		eval('document.form.percentualGlosa' + seqSelecionada + '.value = "' + percGlosa + '"');
		eval('document.form.valorGlosa' + seqSelecionada + '.value = "' + valorGlosa + '"');
		eval('document.form.complemento' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + compl + '\')');
		eval('document.getElementById("inform' + seqSelecionada + '").innerHTML = ObjectUtils.decodificaEnter(\'' + detAcordo + '\')');
	}
}
function setaRestoreDesc(det, compl, detAcordo, idAcordoNivel){
	if (seqSelecionada != ''){
		eval('document.form.idAcordoNivelServicoContrato' + seqSelecionada + '.value = \'' + idAcordoNivel + '\'');
		eval('document.form.descricaoAcordo' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + det + '\')');
		eval('document.form.complemento' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + compl + '\')');
		eval('document.getElementById("inform' + seqSelecionada + '").innerHTML = ObjectUtils.decodificaEnter(\'' + detAcordo + '\')');
	}
}
function selecionaServicoContrato(){
	JANELA_AGUARDE_MENU.show();
	document.form.fireEvent('restoreInfoServicoContrato');
}
function GRID_ITENS_onDeleteRowByImgRef(objImg){
	alert(i18n_message("citcorpore.comum.naoPermitidoExclusaoItens"));
	return false;
}
function mostrarOSParaFaturamento(){
	document.getElementById('divOsSelecao').innerHTML = 'Aguarde... carregando...';
	$('#POPUP_LISTA_OS_FATURAMENTO').modal('show');
	document.form.fireEvent('listOSParaFaturamento');
}
function chamaAssociarOS(){
	window.setTimeout('associarOS()', 1000);
}
function associarOS(){
	$('#POPUP_LISTA_OS_FATURAMENTO').modal('hide');
	document.getElementById('divOsSelecionadas').innerHTML = 'Aguarde... carregando...';
	document.formAssociar.idOSExcluir.value = '';
	document.formAssociar.idFatura.value = document.form.idFatura.value;
	document.formAssociar.fireEvent('associarOSParaFaturamento');
}
function retiraOSDaFatura(idOsParm){
	document.getElementById('divOsSelecionadas').innerHTML = 'Aguarde... carregando...';
	document.formAssociar.idOSExcluir.value = idOsParm;
	document.formAssociar.idFatura.value = document.form.idFatura.value;
	document.formAssociar.fireEvent('associarOSParaFaturamento');
}
function calculaFormulaANS(seq, objFieldName){
	JANELA_AGUARDE_MENU.show();
	document.form.seqANS.value = seq;
	document.form.fieldANS.value = objFieldName;
	eval('document.form.idANS.value = document.form.idAcordoNivelServicoContrato' + NumberUtil.zerosAEsquerda(seq,5) + '.value');
	document.form.fireEvent('calculaFormulaANS');
}
if (window.matchMedia("screen and (-ms-high-contrast: active), (-ms-high-contrast: none)").matches) {
    document.documentElement.className += " " + "ie10";
}

document.form.onClear = function(){
			GRID_ITENS.deleteAllRows();
		};