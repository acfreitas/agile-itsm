function geraDatepicker(id, seq){
	if(seq == null || seq == undefined || seq == ''){
		$('#'+id).datepicker();
	}else{
		$('#'+id+seq).datepicker();
	}
}

var objTab = null;

addEvent(window, "load", load, false);

function load() {
	document.form.afterRestore = function() {
		$('.tabs').tabs('select', 0);
	};
}

function LOOKUP_CENTRORESULTADO_select(id, desc) {
	document.form.restore({
		idCentroResultado: id
	});
}

function LOOKUP_CENTRORESULTADOPAI_select(id, desc) {
	document.form.idCentroResultadoPai.value = id;

	var valor = desc.split('-');
	var nomeConcatenado = "";

	for (var i = 0 ; i < valor.length; i++) {
		if (i == 0) {
			document.form.nomeCentroResultadoPai.value = valor[i];
		}
	}
	$("#POPUP_CENTRORESULTADOPAI").dialog("close");
}

function excluir() {
	var idCentroResultado = document.getElementById("idCentroResultado");

	if (idCentroResultado.value != "") {
		if (confirm(i18n_message("citcorpore.comum.deleta") ) ) {
			document.form.fireEvent("delete");
		}
	} else {
		alert(i18n_message("citcorpore.comum.necessarioSelecionarRegistro") );
		return false;
	}
}

$(function() {
	$("#POPUP_CENTRORESULTADOPAI").dialog({
		autoOpen: false,
		width: 715,
		height: 550,
		modal: true
	});
	$('#POPUP_HIERARQUIA_CENTRORESULTADO').dialog({
		autoOpen: false,
		width: 705,
		height: 500,
		modal: true
	});
	$("#POPUP_EMPREGADO").dialog({
		title: i18n_message("colaborador.pesquisacolaborador"),
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true,
		show: "fade",
		hide: "fade"
	});
});

function consultarCentroResultadoPai() {
	$("#POPUP_CENTRORESULTADOPAI").dialog("open");
}

function visualizarHierarquiaCentrosResultado() {
	document.form.fireEvent("visualizarHierarquiaCentrosResultado");
	$('#POPUP_HIERARQUIA_CENTRORESULTADO').dialog('open');
}

var seqResponsavel = '';
incluirResponsavel = function() {
	GRID_RESPONSAVEIS.addRow();
	seqResponsavel = NumberUtil.zerosAEsquerda(GRID_RESPONSAVEIS.getMaxIndex(),5);
	eval('document.form.idProcessoNegocio' + seqResponsavel + '.focus()');
}

exibeResponsavel = function(serializeResponsavel) {
	if (seqResponsavel != '') {
		if (!StringUtils.isBlank(serializeResponsavel)) {
			var responsavelDto = new CIT_ResponsavelCentroResultadoDTO();
			responsavelDto = ObjectUtils.deserializeObject(serializeResponsavel);
			if(responsavelDto.idProcessoNegocioStr != undefined && responsavelDto.idProcessoNegocioStr != ''){
				var ids = responsavelDto.idProcessoNegocioStr.split(',');
				var idProcessoNegocio = eval('document.form.idProcessoNegocio' + seqResponsavel);
				for(x = 0; x < ids.length; x++){
					for(i = 0; i < idProcessoNegocio.length; i++){
						if(idProcessoNegocio[i].value == ids[x]){
							idProcessoNegocio[i].checked = true;
							break;
						}
					}
				}
			}
			eval('document.form.idResponsavel' + seqResponsavel + '.value = ' + responsavelDto.idResponsavel);
			eval('document.form.nomeEmpregado' + seqResponsavel + '.value = "' + responsavelDto.nomeEmpregado + '"');
		}
	}
}

getResponsavel = function(seq) {
	var responsavelDto = new CIT_ResponsavelCentroResultadoDTO();

	seqResponsavel = NumberUtil.zerosAEsquerda(seq,5);
	responsavelDto.sequencia = seq;
	var ids = '';
	var idProcessoNegocio = eval('document.form.idProcessoNegocio' + seqResponsavel);
	if(idProcessoNegocio != undefined && idProcessoNegocio.length > 0){
		for(i = 0; i < idProcessoNegocio.length; i++){
			if(idProcessoNegocio[i].checked){
				if (ids != '')
					ids += ',';
				ids += idProcessoNegocio[i].value;
			}
		}
	}
	responsavelDto.idProcessoNegocioStr = ids;
	responsavelDto.idResponsavel = eval('document.form.idResponsavel' + seqResponsavel + '.value');
	return responsavelDto;
}

verificarResponsavel = function(seq) {
	var idResponsavel = eval('document.form.idResponsavel' + seq + '.value');
	var count = GRID_RESPONSAVEIS.getMaxIndex();
	for (var i = 1; i <= count; i++){
		if (parseInt(seq) != i) {
			var trObj = document.getElementById('GRID_RESPONSAVEIS_TD_' + NumberUtil.zerosAEsquerda(i,5));
			if (!trObj){
				continue;
			}
			var idAux = eval('document.form.idResponsavel' + NumberUtil.zerosAEsquerda(i,5) + '.value');
			if (idAux == idResponsavel) {
				alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
				eval('document.form.idResponsavel' + seq + '.focus()');
				return false;
			}
		}
	}
	return true;
}

function tratarResponsaveis(){
	try{
		var count = GRID_RESPONSAVEIS.getMaxIndex();
		var contadorAux = 0;
		var objs = new Array();
		for (var i = 1; i <= count; i++){
			var trObj = document.getElementById('GRID_RESPONSAVEIS_TD_' + NumberUtil.zerosAEsquerda(i,5));
			if (!trObj){
				continue;
			}

			var responsavelDto = getResponsavel(i);
			if(responsavelDto.idProcessoNegocioStr == undefined || responsavelDto.idProcessoNegocioStr == ''){
				alert(i18n_message("processoNegocio")+" "+i18n_message("citcorpore.comum.naoInformado"));
				eval('document.form.idProcessoNegocio' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');
				return false;
			}
			if  (!verificarResponsavel(NumberUtil.zerosAEsquerda(i,5))) {
				return false;
			}
			if (StringUtils.isBlank(responsavelDto.idResponsavel)){
				alert(i18n_message("lookup.nomeResp")+" "+i18n_message("citcorpore.comum.naoInformado"));
				eval('document.form.idResponsavel' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');
				return false;
			}
			objs[contadorAux] = responsavelDto;
			contadorAux = contadorAux + 1;
		}
		document.form.colResponsaveis_Serialize.value = ObjectUtils.serializeObjects(objs);
		return true;
	}catch(e){
	}
}

function gravar() {
	if (!tratarResponsaveis()){
		return;
	}

	document.form.save();
}

var seqAtual = '';
function adicionarEmpregado(seq) {
	seqAtual = seq;
	$("#POPUP_EMPREGADO").dialog("open");
}

function LOOKUP_EMPREGADO_select(id, desc) {
	eval('document.form.idResponsavel' + seqAtual + '.value = ' + id);
	eval('document.form.nomeEmpregado' + seqAtual + '.value = "' + desc + '"');
	$("#POPUP_EMPREGADO").dialog("close");
}