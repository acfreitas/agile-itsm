var $tabsAssociadas;
$(document).ready(function() {
	$( "#POPUP_OBJ" ).dialog({
		title: i18n_message("visaoAdm.campoVisao"),
		width: 800,
		height: 600,
		modal: true,
		autoOpen: false,
		resizable: false,
		show: "fade",
		hide: "fade"
		});

	$("#POPUP_OBJ").hide();

	$( "#POPUP_SCRIPT" ).dialog({
		title: 'Scripts',
		width: 800,
		height: 600,
		modal: true,
		autoOpen: false,
		resizable: false,
		show: "fade",
		hide: "fade"
		});

	$("#POPUP_SCRIPT").hide();

	$( "#POPUP_HTMLCODE" ).dialog({
		title: 'Html Code',
		width: 800,

		height: 600,
		modal: true,
		autoOpen: false,
		resizable: false,
		show: "fade",
		hide: "fade"
		});

	$("#POPUP_HTMLCODE").hide();

	$( "#POPUP_BOTOES" ).dialog({
		title: i18n_message("visaoAdm.botoes"),
		width: 800,
		height: 600,
		modal: true,
		autoOpen: false,
		resizable: false,
		show: "fade",
		hide: "fade"
		});

	$("#POPUP_BOTOES").hide();

	$( "#POPUP_IMPORTAR" ).dialog({
		title: i18n_message("visaoAdm.importarVisao"),
		width: 500,
		height: 300,
		modal: true,
		autoOpen: false,
		resizable: false,
		show: "fade",
		hide: "fade"
		});

	$("#POPUP_IMPORTAR").hide();

	$("#POPUP_EXPORTARVISOES").dialog({
		title: i18n_message("visaoAdm.exportarVisoes"),
		autoOpen : false,
		width : 700,
		height : 500,
		modal : true,
		show: "fade",
		hide: "fade"
	});

	$("#btnFechaExportacoes").click(function(){
		$('#POPUP_EXPORTARVISOES').dialog('close');
	});

	$("#btnFechaTodasImportacoes").click(function(){
		$('#POPUP_IMPORTARTODASVISOES').dialog('close');
	});

	$("#POPUP_IMPORTARVISOES").dialog({
		title: i18n_message("visaoAdm.importarVisoes"),
		autoOpen : false,
		width : 700,
		height : 320,
		modal : true,
		show: "fade",
		hide: "fade"
	});

	$("#POPUP_IMPORTARTODASVISOES").dialog({
		title: i18n_message("visaoAdm.importarTodasVisoes"),
		autoOpen : false,
		width : 700,
		height : 320,
		modal : true,
		show: "fade",
		hide: "fade"
	});

	$("#btnFechaImportacoes").click(function(){
		$('#POPUP_IMPORTARVISOES').dialog('close');
	});

	$( "#sortable" ).sortable({
		cancel: ".ui-state-disabled"
	});
	$( "#sortable" ).disableSelection();

	$tabsAssociadas = $( "#tabsAssociadas").tabs({
		tabTemplate: template_tab,
		add: function( event, ui ) {
			var tab_content = document.getElementById('divOcultaVisaoRelacionada').innerHTML;
			tab_content = StringUtils.replaceAll(tab_content, '#SEQ#', '' + tab_counter);
			$( ui.panel ).append( "<p>" + tab_content + "</p>" );
		}
	});

	$( "#tabsAssociadas span.ui-icon-close" ).live( "click", function() {
		var index = $( "li", $tabsAssociadas ).index( $( this ).parent() );
		$tabsAssociadas.tabs( "remove", index );
	});

	var tab_content = document.getElementById('divOcultaVisaoRelacionada').innerHTML;
	tab_content = StringUtils.replaceAll(tab_content, '#SEQ#', '1');
	document.getElementById('tabsAssociadas-1').innerHTML = tab_content;
});

var tab_counter = 2;

function addTab() {
	var tab_title = i18n_message("visaoAdm.visaoRelacionada");
	$tabsAssociadas.tabs( "add", "#tabsAssociadas-" + tab_counter, tab_title );
	tab_counter++;
}

function geraSortable(id){
	$( "#" + id ).sortable();
}

function mostraAddObj(){
	var idObj = document.formItem.idObjetoNegocio.value;
	document.formItem.clear();
	document.formItem.idObjetoNegocio.value = idObj;
	document.formItem.numeroEdicao.value = '';
	$( "#POPUP_OBJ" ).dialog( 'open' );
}

function adicionaAtualizaItem(){
	if (document.formItem.validate()){
		selecionaTudo();
		if (document.formItem.numeroEdicao.value == ''){
			document.formItem.fireEvent('addItem');
		}else{
			document.formItem.fireEvent('atualizaItem');
		}
	}
}
function excluir(){
	if (document.formItem.numeroEdicao.value == ''){
		alert(i18n_message("visaoAdm.naoHaItemExcluir"));
	}else{
		document.formItem.fireEvent('deleteItem');
	}
}
function excluirVisao(){
	if (document.form.idVisao.value == ''){
		alert(i18n_message("visaoAdm.naoHaVisaoExcluir"));
	}else{
		if (confirm(i18n_message("visaoAdm.desejaRealmenteExcluirVisao"))){
			document.form.fireEvent('deleteVisao');
		}
	}
}

function adicionaAtualizaScript(){
	if (document.formScript.validate()){
		document.formScript.fireEvent('atualizaScript');
	}
}

function adicionaAtualizaHTMLCode(){
	if (document.formHtmlCode.validate()){
		document.formHtmlCode.fireEvent('atualizaHTMLCode');
	}
}

function adicionaAtualizaBotao(){
	if (document.formBotoes.validate()){
		document.formBotoes.fireEvent('atualizaBotao');
	}
}

function excluirBotao(){
	if (confirm(i18n_message("visaoAdm.confirmaExclusaoBotao"))){
		document.formBotoes.fireEvent('excluirBotao');
	}
}

function limparBotao(){
	document.formBotoes.clear();
}

function selecionaAcaoBotao(obj){
	if (obj.value == btn_action.btn_action){
		document.formBotoes.texto.value =i18n_message("botaoacaovisao.gravar_dados");
	}
	if (obj.value == btn_action.limpar){
		document.formBotoes.texto.value = i18n_message("botaoacaovisao.limpar_dados");
	}
	if (obj.value == btn_action.excluir){
		document.formBotoes.texto.value = i18n_message("botaoacaovisao.excluir");
	}
	if (obj.value == btn_action.script){
		document.formBotoes.texto.value = '';
	}
}

function limparItem(){
	var num = document.formItem.numeroEdicao.value;
	document.formItem.clear();
	document.formItem.numeroEdicao.value = num;
}

function editar(num){
	document.formItem.numeroEdicao.value = num;
	document.formItem.fireEvent('recuperaItem');
}

function escondeDivs(){
	document.getElementById("divCampoRadioSelect").style.display = 'none';
	document.getElementById("divCampoNumeroDecimais").style.display = 'none';
	document.getElementById("divCampoRelacao").style.display = 'none';
	document.getElementById("divCampoHTMLCode").style.display = 'none';
}

function selecionaObjNegocio(obj){
	document.formItem.fireEvent('listarCamposObjNegocio');
}

function selecionaObjNegocioLigacao(obj){
	document.formItem.fireEvent('listarCamposObjNegocioLigacao');
}

function selecionaCampoObjNegocio(obj){
	escondeDivs();
	document.formItem.fireEvent('aplicaCfgCampoObjNegocioVisao');
}

function gravar(){
	var numTabs = '';
	for (var i = 0; i < tab_counter; i++){
		if (document.getElementById('tabsAssociadas-' + i)){
			numTabs = numTabs + i + ",";

			var cbo = document.getElementById('vinculosVisaoPaiNN_' + i);
			if (cbo != null && cbo != undefined){
				for(var x = cbo.length -1; x >= 0; x--){
					cbo.options[x].selected = true;
				}
			}
			var cbo = document.getElementById('vinculosVisaoFilhoNN_' + i);
			if (cbo != null && cbo != undefined){
				for(var x = cbo.length -1; x >= 0; x--){
					cbo.options[x].selected = true;
				}
			}
		}
	}

	var result = $('#sortable').sortable('toArray');
	var ordemStr = '';
	for(var i = 0; i < result.length; i++){
		ordemStr = ordemStr + StringUtils.onlyNumbers(result[i]) + ',';
	}
	document.form.ordemCampos.value = ordemStr;
	document.form.numTabs.value = numTabs;
	document.form.save();
}

function showScripts(){
	$( "#POPUP_SCRIPT" ).dialog( 'open' );
}

function showHTMLCode(){
	$( "#POPUP_HTMLCODE" ).dialog( 'open' );
}

function showBotoes(){
	$( "#POPUP_BOTOES" ).dialog( 'open' );
}

function fechar(){
	$( "#POPUP_OBJ" ).dialog( 'close' );
}

function fecharScript(){
	$( "#POPUP_SCRIPT" ).dialog( 'close' );
}

function fecharBotao(){
	$( "#POPUP_BOTOES" ).dialog( 'close' );
}

function fecharHTMLCode(){
	$( "#POPUP_HTMLCODE" ).dialog( 'close' );
}

function LOOKUP_VISAO_select(id,desc){
	document.getElementById("sortable").innerHTML = i18n_message("citcorpore.comum.aguardecarregando");
	window.location = ctx + '/pages/visaoAdm/visaoAdm.load?idVisao=' + id;
}

function limparTela(){
	document.getElementById("sortable").innerHTML = i18n_message("citcorpore.comum.aguardecarregando");
	window.location = ctx + '/pages/visaoAdm/visaoAdm.load';
}

function addOpcao(){
	var v = document.formItem.valorOpcao.value;
	var d = document.formItem.descricaoOpcao.value;
	HTMLUtils.addOptionIfNotExists('valoresOpcoes', v + ' - ' + d, v + '#' + d);
}

function removeOpcao(){
	var cbo = document.getElementById('valoresOpcoes');
	for(var i = cbo.length -1; i >= 0; i--){
		if (cbo.options[i].selected){
			cbo.options[i] = null;
		}
	}
}

function selecionaTudo(){
	var cbo = document.getElementById('valoresOpcoes');
	for(var i = cbo.length -1; i >= 0; i--){
		cbo.options[i].selected = true;
	}
}

function mudaTipoVinculo(obj, seq, fire){
	document.getElementById('divLabelVinculoVisao1_' + seq).style.display = 'none';
	document.getElementById('divLabelVinculoVisao2_' + seq).style.display = 'none';
	document.getElementById('divVinculoVisaoNN1_' + seq).style.display = 'none';
	document.getElementById('divVinculoVisaoNN2_' + seq).style.display = 'none';
	if (obj.value == '1'){
		document.getElementById('divVinculoVisaoNN1_' + seq).style.display = 'block';

		document.form.seq.value = seq;
		if (fire){
			document.form.idVisaoRel.value = HTMLUtils.getValue('divVisaoRelacionada_' + seq, document.form);
			document.form.fireEvent('listaCamposVisao');
			document.form.fireEvent('getCampos1ToN');
		}
	}
	if (obj.value == '2'){
		document.getElementById('divLabelVinculoVisao1_' + seq).style.display = 'block';
		document.getElementById('divLabelVinculoVisao2_' + seq).style.display = 'block';
		document.getElementById('divVinculoVisaoNN1_' + seq).style.display = 'block';
		document.getElementById('divVinculoVisaoNN2_' + seq).style.display = 'block';

		document.form.seq.value = seq;
		if (fire){
			document.form.fireEvent('listaCamposVisao');
		}
	}
}

function mudaCampoObjNN(obj, seq){
	document.form.idObjetoNegocio.value = '';
	document.form.idObjetoNegocio.value = HTMLUtils.getValue('idObjetoNegocioNN_' + seq, document.form);
	document.form.nomeCombo.value = 'idCamposObjetoNegocioObjNN1_' + seq;
	document.form.fireEvent('listarCamposObjNegocioNN');

	document.form.nomeCombo.value = 'idCamposObjetoNegocioObjNN2_' + seq;
	document.form.fireEvent('listarCamposObjNegocioNN');
}

function mudaCampoVisaoRel(obj, seq){
	document.form.idVisaoRel.value = HTMLUtils.getValue('divVisaoRelacionada_' + seq, document.form);
	document.form.seq.value = seq;
	document.form.fireEvent('getCamposFromVisaoRel');
}

function addVincObjNNPai(seq){
	var idCpNegPai = HTMLUtils.getValue('idCamposObjetoNegocioPai_' + seq, document.form);
	if (idCpNegPai == '' || idCpNegPai == 0){
		alert(i18n_message("visaoAdm.informeCampoNegocioVisaoPai"));
		return;
	}
	var idCpNegNN = HTMLUtils.getValue('idCamposObjetoNegocioObjNN1_' + seq, document.form);
	if (idCpNegNN == '' || idCpNegNN == 0){
		alert(i18n_message("visaoAdm.campoNegocioNN"));
		return;
	}

	var obj = document.getElementById('idCamposObjetoNegocioPai_' + seq);
	var t1 = obj.options[obj.selectedIndex].text;
	var obj = document.getElementById('idCamposObjetoNegocioObjNN1_' + seq);
	var t2 = obj.options[obj.selectedIndex].text;

	HTMLUtils.addOptionIfNotExists('vinculosVisaoPaiNN_' + seq, t1 + ' - ' + t2, idCpNegPai + '#' + idCpNegNN);
}

function addVincObjNNFilho(seq){
	var idCpNegPai = HTMLUtils.getValue('idCamposObjetoNegocioFilho_' + seq, document.form);
	if (idCpNegPai == '' || idCpNegPai == 0){
		alert(i18n_message("visaoAdm.informeCampoNegocioVisaoPai"));
		return;
	}
	var idCpNegNN = HTMLUtils.getValue('idCamposObjetoNegocioObjNN2_' + seq, document.form);
	if (idCpNegNN == '' || idCpNegNN == 0){
		alert(i18n_message("visaoAdm.campoNegocioNN"));
		return;
	}

	var obj = document.getElementById('idCamposObjetoNegocioFilho_' + seq);
	var t1 = obj.options[obj.selectedIndex].text;
	var obj = document.getElementById('idCamposObjetoNegocioObjNN2_' + seq);
	var t2 = obj.options[obj.selectedIndex].text;

	HTMLUtils.addOptionIfNotExists('vinculosVisaoFilhoNN_' + seq, t1 + ' - ' + t2, idCpNegPai + '#' + idCpNegNN);
}

function remVincObjNNPai(seq){
	var cbo = document.getElementById('vinculosVisaoPaiNN_' + seq);
	for(var i = cbo.length -1; i >= 0; i--){
		if (cbo.options[i].selected){
			cbo.options[i] = null;
		}
	}
}

function remVincObjNNFilho(seq){
	var cbo = document.getElementById('vinculosVisaoFilhoNN_' + seq);
	for(var i = cbo.length -1; i >= 0; i--){
		if (cbo.options[i].selected){
			cbo.options[i] = null;
		}
	}
}

function showMessageScript(msg){
	document.getElementById('divMensagemScript').innerHTML = msg;
	window.setTimeout('ocultaMessageScript()', 5000)
}

function ocultaMessageScript(){
	document.getElementById('divMensagemScript').innerHTML = '';
}

function showMessageHtmlCode(msg){
	document.getElementById('divMensagemHTMLCode').innerHTML = msg;
	window.setTimeout('ocultaMessageHtmlCode()', 5000)
}

function ocultaMessageHtmlCode(){
	document.getElementById('divMensagemHTMLCode').innerHTML = '';
}

function showMessageBotao(msg){
	document.getElementById('divMensagemBotao').innerHTML = msg;
	window.setTimeout('ocultaMessageBotao()', 5000)
}

function ocultaMessageBotao(){
	document.getElementById('divMensagemBotao').innerHTML = '';
}

function recuperaScript(obj){
	document.getElementById('divMensagemScript').innerHTML = i18n_message("visaoAdm.aguardeRecuperandoScript");
	document.formScript.fireEvent('recuperaScript');
}

function recuperaHtmlCode(obj){
	document.getElementById('divMensagemHTMLCode').innerHTML = i18n_message("visaoAdm.aguardeRecuperandoHTMLCode");
	document.formHtmlCode.fireEvent('recuperaHtmlCode');
}

function recuperaBotao(obj){
	document.getElementById('divMensagemBotao').innerHTML = i18n_message("visaoAdm.aguardeRecuperandoBotaoAcao");
	document.formBotoes.texto.value = obj.value;
	document.formBotoes.fireEvent('recuperaBotao');
}

var infoBotaoSel = -1;
function guardaInfoBotao(){
	infoBotaoSel = document.formBotoes.botaoCadastrado.selectedIndex;
}

function setInfoBotao(){
	document.formBotoes.botaoCadastrado.selectedIndex = infoBotaoSel;
}

function verificaTipoVisao(obj){
	if (obj.value == visao_dto.external_class){
		document.getElementById('sortable').style.display = 'none';
		document.getElementById('classeExterna').style.display = 'block';
	}else if (obj.value == visao_dto.matriz){
		document.getElementById('sortable').style.display = 'block';
		document.getElementById('matriz').style.display = 'block';
		document.getElementById('classeExterna').style.display = 'none';
	}else{
		document.getElementById('sortable').style.display = 'block';
		document.getElementById('classeExterna').style.display = 'none';
	}
}

function exportarXML(){
	var numTabs = '';
	for (var i = 0; i < tab_counter; i++){
		if (document.getElementById('tabsAssociadas-' + i)){
			//alert('tabsAssociadas-' + i);
			numTabs = numTabs + i + ",";

			var cbo = document.getElementById('vinculosVisaoPaiNN_' + i);
			if (cbo != null && cbo != undefined){
				for(var x = cbo.length -1; x >= 0; x--){
					cbo.options[x].selected = true;
				}
			}
			var cbo = document.getElementById('vinculosVisaoFilhoNN_' + i);
			if (cbo != null && cbo != undefined){
				for(var x = cbo.length -1; x >= 0; x--){
					cbo.options[x].selected = true;
				}
			}
		}
	}

	var result = $('#sortable').sortable('toArray');
	var ordemStr = '';
	for(var i = 0; i < result.length; i++){
		ordemStr = ordemStr + StringUtils.onlyNumbers(result[i]) + ',';
	}
	document.form.ordemCampos.value = ordemStr;
	document.form.numTabs.value = numTabs;
	document.form.fireEvent('exportXML');
}

function importarVisoesXML(){
	if (!confirm(i18n_message("visaoAdm.importarVisoesMensagem")+" "+i18n_message("visaoAdm.importarVisoesMensagemCont"))){
		return;
	}
	document.form.fireEvent('importarVisoesXML');
}

function importarTodasVisoesXML() {
	desabilita();
	document.form.fireEvent('importarTodasVisoesXML');
}

function mostrarImportarVisoesXML(){
	if (!confirm(i18n_message("visaoAdm.mostrarImportarVisoes"))){
		return;
	}
	$( "#POPUP_IMPORTARVISOES" ).dialog( 'open' );
	uploadAnexos.refresh();
}

function mostrarImportarTodasVisoesXML() {
	document.getElementById("listaTodasVisoesTb").innerHTML="";
	$( "#POPUP_IMPORTARTODASVISOES" ).dialog( 'open' );
}

function exportarVisoesXML(){
	if (!confirm(i18n_message("visaoAdm.desejaContinuarExportacaoArquivoSelecionado"))){
		return;
	}
	exportarSelecionados();
}

function mostrarExportarVisoesXML(){
	$( "#POPUP_EXPORTARVISOES" ).dialog( 'open' );
	document.form.fireEvent('listaVisoesTb');
}

function ordenaBaixo(){
	var obj = document.formBotoes.botaoCadastrado;
	var v = '';
	var t = '';
	var v2 = '';
	var t2 = '';
	if (obj.selectedIndex > 0){
		v = obj[obj.selectedIndex].value;
		t = obj[obj.selectedIndex].text;

		v2 = obj[obj.selectedIndex - 1].value;
		t2 = obj[obj.selectedIndex - 1].text;

		obj[obj.selectedIndex].value = v2;
		obj[obj.selectedIndex].text = t2;

		obj[obj.selectedIndex - 1].value = v;
		obj[obj.selectedIndex - 1].text = t;

		obj[obj.selectedIndex - 1].selected = true;
	}
	var valores = '';
	for(var i = 0; i < obj.options.length; i++){
		if (valores != ''){
			valores =  valores + ',';
		}
		valores = valores + obj.options[i].value;
	}
	document.formBotoes.ordemBotoes.value = valores;
	document.getElementById('divMensagemBotao').innerHTML = i18n_message("visaoAdm.aguardeOrdenando");
	document.formBotoes.fireEvent('setaOrdemBotoes');
}

function ordenaCima(){
	var obj = document.formBotoes.botaoCadastrado;
	var v = '';
	var t = '';
	var v2 = '';
	var t2 = '';
	if (obj.selectedIndex < obj.options.length){
		v = obj[obj.selectedIndex].value;
		t = obj[obj.selectedIndex].text;

		v2 = obj[obj.selectedIndex + 1].value;
		t2 = obj[obj.selectedIndex + 1].text;

		obj[obj.selectedIndex].value = v2;
		obj[obj.selectedIndex].text = t2;

		obj[obj.selectedIndex + 1].value = v;
		obj[obj.selectedIndex + 1].text = t;

		obj[obj.selectedIndex + 1].selected = true;
	}
	var valores = '';
	for(var i = 0; i < obj.options.length; i++){
		if (valores != ''){
			valores =  valores + ',';
		}
		valores = valores + obj.options[i].value;
	}
	document.formBotoes.ordemBotoes.value = valores;
	document.getElementById('divMensagemBotao').innerHTML = i18n_message("visaoAdm.aguardeOrdenando");
	document.formBotoes.fireEvent('setaOrdemBotoes');
}

function verificaBrancos(obj){
	var str = obj.value;
	var retorno = '';
	for(var i = 0; i < str.length; i++){
		if (str.charAt(i) != ' '){
			if (str.charAt(i) != '@' && str.charAt(i) != '#' && str.charAt(i) != '$' && str.charAt(i) != '%'
				&& str.charAt(i) != '*' && str.charAt(i) != '&' && str.charAt(i) != '!' && str.charAt(i) != '('
				&& str.charAt(i) != ')' && str.charAt(i) != '+' && str.charAt(i) != '-' && str.charAt(i) != '='
				&& str.charAt(i) != 'ç' && str.charAt(i) != 'ã' && str.charAt(i) != 'á' && str.charAt(i) != 'õ'
				&& str.charAt(i) != 'ó' && str.charAt(i) != 'ú' && str.charAt(i) != 'ê' && str.charAt(i) != 'é'){
				retorno = retorno + str.charAt(i);
			}else{
				retorno = retorno + '_';
			}
		}else{
			retorno = retorno + '_';
		}
	}
	obj.value = retorno;
}

function selecionaObjetoNegocioMatriz(obj){
	document.form.fireEvent('listarCamposObjNegocioMatriz');
}

/*Exporta visões*/
function exportarSelecionados() {
	var tabela = document.getElementById('tbVisoes');
	var count = tabela.rows.length;
	var contadorAux = 0;
	var visoesList = new Array();
	for ( var i = 1; i <= count; i++) {
		var trObj = document.getElementById('idVisao' + i);
		if (!trObj) {
			continue;
		}
		visoesList[contadorAux] = getVisoes(i);
		contadorAux = contadorAux + 1;
	}
	serializaVisoes();
	document.formExport.fireEvent("exportarVisoesXML");
}

var seqBaseline = '';
var aux = '';
serializaVisoes = function() {
	var tabela = document.getElementById('tbVisoes');
	var count = tabela.rows.length;
	var contadorAux = 0;
	var visoesList = new Array();
	for ( var i = 0; i <= count; i++) {
		var trObj = document.getElementById('idVisaoCheck' + i);
		if (!trObj) {
			continue;
		}else if(trObj.checked){
			visoesList[contadorAux] = getVisoes(i);
			contadorAux = contadorAux + 1;
			continue;
		}

	}
	var visoesSerializadas = ObjectUtils.serializeObjects(visoesList);
	document.formExport.visoesSerializadas.value = visoesSerializadas;
	return true;
}

getVisoes = function(seq) {
	var VisaoDTO = new CIT_VisaoDTO();
	VisaoDTO.sequencia = seq;
	VisaoDTO.idVisao = eval('document.formExport.idVisao' + seq + '.value');
	return VisaoDTO;
}

function marcarTodosCheckbox() {
	var itens = document.formExport.idVisaoCheck;
	var marcar = document.formExport.marcarTodos;
	for ( var i = 0; i < itens.length; i++) {
		if (marcar.checked) {
			if (!itens[i].checked) {
				itens[i].checked = true;
			}
		} else {
			itens[i].checked = false;
		}
	}
}

function marcarCheck(count){
	var item = document.formExport.idVisaoCheck;
	if(item[count].checked){
		item[count].checked = false;
	}else{
		item[count].checked = true;
	}
}

function desabilita() {
	$('#concluir').attr('disabled', true);
	$('#Throbber').css('visibility','visible');
}

function habilita() {
	$('#concluir').attr('disabled', false);
	$('#Throbber').css('visibility','hidden');
}
