$(function() {
	
	$("#POPUP_ITEMCONFIGPAI").dialog({
		autoOpen : false,
		width : 700,
		height : 500,
		modal : true
	});

	$("#POPUP_TIPOITEMCONFIGURACAO").dialog( {
		autoOpen : false,
		width : 705,
		height : 500,
		modal : true
	});

	$("#POPUP_GRUPOITEMCONFIGURACAO").dialog( {
		autoOpen : false,
		width : 705,
		height : 500,
		modal : true
	});
					
	$("#POPUP_ITEMCONFIGPAI").dialog({
		autoOpen : false,
		width : 700,
		height : 500,
		modal : true
	});

	$("#POPUP_TIPOITEMCONFIGURACAO").dialog( {
		autoOpen : false,
		width : 705,
		height : 500,
		modal : true
	});
	
	$(".dialog").dialog({
		autoOpen : false,
		modal : true
	});
	
	$("#POPUP_EMPREGADO").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});
	/**
	* Aumentando o dialog de incicente	
	* @autor flavio.santana
	* 25/10/2013 10:50
	*/
	$("#POPUP_INCIDENTE").dialog({
		autoOpen : false,
		width : 800,
		height : 600,
		modal : true
	});
	$("#POPUP_PROBLEMA").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});
	$("#POPUP_MUDANCA").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});
	$("#POPUP_LIBERACAO").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});
	$("#POPUP_MIDIASOFTWARE").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});
	$("#POPUP_GRUPOITEMCONFIGURACAO").dialog( {
		autoOpen : false,
		width : 705,
		height : 500,
		modal : true
	});
	
	$("#POPUP_IMPACTO").dialog( {
		autoOpen : false,
		width : 705,
		height : 500,
		modal : true
	});
	$("#POPUP_RESPONSAVEL").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});
	$("#POPUP_RESPONSAVEL_GRUPO").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});
	$("#POPUP_EDITARPROBLEMA").dialog({
		autoOpen : false,
		width : "98%",
		height : 1000,
		modal : true
	});
	
	$("#addItemConfiguracaoPai").click(function() {
		$("#POPUP_ITEMCONFIGPAI").dialog("open");
	});

	$("#divAlterarSenha").hide();
				
	initPopups();

	//para visualiza��o r�pida do mapaDesenhoServico
	popupManager = new PopupManager(0 , 0, URL_INITIAL + "/pages/");
	//solicitcaoservico
	popupManagerSolicitacaoServico = new PopupManager(0 , 0, URL_INITIAL + "/pages/");

$( "#tabs" ).tabs();
	

});

/**
* Motivo: Conformar a Popup com o tamanho da tela
* Autor: luiz.borges
* Data/Hora: 22/11/2013 11:32
*/
function abrePopupSolicitacaoServico(){
	
	popupManagerSolicitacaoServico.titulo= i18n_message('solicitacaoServico.solicitacao');
	redimensionarTamanho("#popupCadastroRapido", "XG");
	popupManagerSolicitacaoServico.abrePopupParms('solicitacaoServicoMultiContratos', '', '&selecaoIc=true');
	
}


function redimensionarTamanho(identificador, tipo_variacao){
	var h;
	var w;
	switch(tipo_variacao)
	{
	case "PEQUENO":
		w = parseInt($(window).width() * 0.25);
		h = parseInt($(window).height() * 0.35);
	  break;
	case "MEDIO":
		w = parseInt($(window).width() * 0.5);
		h = parseInt($(window).height() * 0.6);
	  break;
	case "GRANDE":
		w = parseInt($(window).width() * 0.75);
		h = parseInt($(window).height() * 0.85);
	  break;
	case "XG":
		w = parseInt($(window).width() * 0.95);
		h = parseInt($(window).height() * 0.95);
	  break;
	default:
		w = parseInt($(window).width() * 0.5);
		h = parseInt($(window).height() * 0.6);
}

	$(identificador).dialog("option","width", w)
	$(identificador).dialog("option","height", h)
}

function event() {
	$( ".even" ).click(function() {
		var s = $(this).attr('id').split('-');
		if(s[0]=="even")
			$( "#sel-" + s[1] ).toggle();
		else if(s[0]=="evenM")
			$( "#selM-" + s[1] ).toggle();
		else if(s[0]=="evenP")
			$( "#selP-" + s[1] ).toggle();
	});
}


	var popup;
    function load(){
    	/**
* Aumentando o dialog geral de varios frames
* @autor flavio.santana
* 25/10/2013 10:50
*/
popup = new PopupManager(880, 550, "" + URL_INITIAL + "/pages/");
document.form.afterRestore = function () {
	$('.tabs').tabs('select', 0);
	}
}
addEvent(window, "load", load, false);

/* 	function consultarTipoItemConfiguracao(){
		$("#POPUP_TIPOITEMCONFIGURACAO").dialog("open");
	} */

function consultarTipoItemConfiguracao(){
	$("#POPUP_TIPOITEMCONFIGURACAO").dialog("open");
}

function abrePopupNovoTipoItemConfiguracao(){
	popup.titulo= i18n_message('tipoItemConfiguracao.cadastro');
	redimensionarTamanho("#popupCadastroRapido", "GRANDE");
	popup.abrePopup('tipoItemConfiguracao')
}



/*function consultarTipoItemConfiguracao(){
	$("#POPUP_TIPOITEMCONFIGURACAO").dialog("open");
}*/

function LOOKUP_TIPOITEMCONFIGURACAO_select(idTipo, desc) {
	var valor = desc.split('-');
	var nome = "";		

	for(var i = 0 ; i < (valor.length-1); i++){
		if(i < (valor.length - 2)){
			nome += valor[i];
		}
		if(i < (valor.length - 3) && valor.length > 2){
			nome += "-";
		}
	}
	document.form.nomeTipoItemConfiguracao.value = nome;
	
	document.getElementById('idTipoItemConfiguracao').value = valor[(valor.length)-1].replace(/\D/g, "");
	
	document.form.fireEvent("restoreTipoItemConfiguracao");
	
	$("#POPUP_TIPOITEMCONFIGURACAO").dialog("close");	
}


function fecharPopup(){
	$("#POPUP_TIPOITEMCONFIGURACAO").dialog("close");
}

function novoTipo(){
	var idItem = id.replace(/\D/g, "");

document.getElementById('iframeOpcoes').src = URL_INITIAL + '/pages/tipoItemConfiguracaoTree/tipoItemConfiguracaoTree.load?idItemConfiguracao=';
$("#FRAME_OPCOES").dialog("open");
}

function alterarSenha(){
	$("#divSenha").show();
}

abrePopupUsuario = function(){
	$("#POPUP_EMPREGADO").dialog("open");
}
abrePopupNovoUsuario = function(){
	popup.titulo= i18n_message('colaborador.cadastroColaborador');
	redimensionarTamanho("#popupCadastroRapido", "GRANDE");
	popup.abrePopup('empregado')
}
abrePopupNovoGrupo = function(){
	popup.titulo= i18n_message('unidade.cadastroGrupo');
	redimensionarTamanho("#popupCadastroRapido", "GRANDE");
	popup.abrePopup('grupo')
}
abrePopupIncidente = function(){
	$("#POPUP_INCIDENTE").dialog("open");
}
abrePopupProblema = function(){
	$("#POPUP_PROBLEMA").dialog("open");
}
abrePopupNovoProblema = function(){
	popup.titulo= i18n_message('problema.registro_problema');
	redimensionarTamanho("#popupCadastroRapido", "XG");
	popup.abrePopup('problema');
}
abrePopupMudanca = function(){
	$("#POPUP_MUDANCA").dialog("open");
}
abrePopupNovaMudanca = function(){
	popup.titulo= i18n_message('requisicaoMudanca.requisicaoMudanca');
	redimensionarTamanho("#popupCadastroRapido", "XG");
	popup.abrePopup('requisicaoMudanca');
}
abrePopupLiberacao = function(){
	$("#POPUP_LIBERACAO").dialog("open");
}
abrePopupNovaLiberacao = function(){
	popup.titulo= i18n_message('requisicaoLiberacao.requisicaoLiberacao');
	redimensionarTamanho("#popupCadastroRapido", "XG");
	popup.abrePopup('requisicaoLiberacao');
}
abrePopupMidia= function(){
	$("#POPUP_MIDIASOFTWARE").dialog("open");
}
abrePopupNovaMidia= function(){
	popup.titulo= i18n_message('midiaSoftware.cadastro');
	redimensionarTamanho("#popupCadastroRapido", "GRANDE");
	popup.abrePopup('midiaSoftware');
}
abrePopupGrupoItemConfiguracao = function(){
	$("#POPUP_GRUPOITEMCONFIGURACAO").dialog("open");
}
abrePopupNovoGrupoItemConfiguracao = function(){
	popup.titulo= i18n_message('grupoItemConfiguracao.cadastro');
	redimensionarTamanho("#popupCadastroRapido", "MEDIO");
	popup.abrePopup('grupoItemConfiguracao');
}
abrePopupResponsavel = function(){
	if(document.getElementById('tipoResponsavel').value === 'U')
		$("#POPUP_RESPONSAVEL").dialog("open");
	else if(document.getElementById('tipoResponsavel').value === 'G')
		$("#POPUP_RESPONSAVEL_GRUPO").dialog("open");
}

function LOOKUP_MIDIASOFTWARE_select(id, desc) {
	document.form.idMidiaSoftware.value = id;
	document.getElementById("nomeMidia").value = desc;

$("#POPUP_MIDIASOFTWARE").dialog("close");
}
	
function LOOKUP_EMPREGADO_select(id, desc){
	document.form.idProprietario.value = id;
	document.getElementById("nomeUsuario").value = desc;

$("#POPUP_EMPREGADO").dialog("close");
}
function LOOKUP_SOLICITACAOSERVICO_select(id, desc){
	document.form.idIncidente.value = id;
	document.getElementById("numeroIncidente").value = desc;
$("#POPUP_INCIDENTE").dialog("close");
}
function LOOKUP_PROBLEMA_select(id, desc){
	document.form.idProblema.value = id;
	document.getElementById("numeroProblema").value = desc;
$("#POPUP_PROBLEMA").dialog("close");
}
function LOOKUP_MUDANCA_select(id, desc){
	document.form.idMudanca.value = id;
	document.getElementById("numeroMudanca").value = desc;
$("#POPUP_MUDANCA").dialog("close");
}
function LOOKUP_LIBERACAO_select(id, desc){
	document.form.idLiberacao.value = id;
	document.getElementById("tituloLiberacao").value = desc;
$("#POPUP_LIBERACAO").dialog("close");
}
function LOOKUP_RESPONSAVEL_select(id, desc){
	document.form.idResponsavel.value = id;
	document.getElementById("nomeResponsavel").value = desc;

$("#POPUP_RESPONSAVEL").dialog("close");
}
function LOOKUP_RESPONSAVEL_GRUPO_select(id, desc){
	document.form.idGrupoResponsavel.value = id;
	document.getElementById("nomeResponsavel").value = desc;

$("#POPUP_RESPONSAVEL_GRUPO").dialog("close");
}

function abrePopupServicos(){
	$("#POPUP_SERVICO").dialog("open");
}

function adicionarServico(){
	abrePopupServicos();	
}

function abrePopupIcs(){
	$("#POPUPITEMCONFIGURACAO").dialog("open");
}

function adicionarIC(){			
	abrePopupIcs();
}

function initPopups(){
	$(".POPUP_LOOKUP").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});			
}

function fecharAddSolicitante() {
	$("#popupCadastroRapido").dialog("close");
}

function limparMidiaSoftware(){
	document.getElementById("idMidiaSoftware").value = '';
	document.getElementById("nomeMidia").value = '';
}
function limparSolicitacaoServico(){
	document.getElementById("idIncidente").value = '';
	document.getElementById("numeroIncidente").value = '';
}
function limparProblema(){
	document.getElementById("idProblema").value = '';
	document.getElementById("numeroProblema").value = '';
}
function limparRequisicaoMudanca(){
	document.getElementById("idMudanca").value = '';
	document.getElementById("numeroMudanca").value = '';
}
function limparRequisicaoLiberacao(){
	document.getElementById("idLiberacao").value = '';
	document.getElementById("tituloLiberacao").value = '';
}
/**
* Adicionando o item de limpar lookup
* @autor flavio.santana
* 25/10/2013 10:50
*/
function limparColaborador() {
	document.getElementById("idProprietario").value = '';
document.getElementById("nomeUsuario").value = '';
}
function limparTipoItemConfiguracao() {
	document.getElementById("idTipoItemConfiguracao").value = '';
document.getElementById("nomeTipoItemConfiguracao").value = '';
}
function limparGrupoItemConfiguracao(){
	document.getElementById("idGrupoItemConfiguracao").value = '';
document.getElementById("nomeGrupoItemConfiguracao").value = '';
}
function limparResponsavel() {
	document.getElementById("idResponsavel").value = '';
	document.getElementById("idGrupoResponsavel").value = '';
document.getElementById("nomeResponsavel").value = '';
}
function verificaImpactos(){
	JANELA_AGUARDE_MENU.show();
	$("#relacionamentos").hide();
document.form.fireEvent('verificaImpactos');
}

function verificaHistoricoAlteracao(){
	JANELA_AGUARDE_MENU.show();
	$("#relacionamentos").hide();
	$("#divHistoricoAlteacao").show();
	JANELA_AGUARDE_MENU.hide()
}

function pesquisiarHistoricoItemConfiguracao(){
	
	document.form.dataInicioHistorico.value = document.getElementById("dataInicioH").value;
	document.form.dataFimHistorico.value = document.getElementById("dataFimH").value;
	
	var dataIncio = document.form.dataInicioHistorico.value;
	var dataFim = document.form.dataFimHistorico.value;
	
	if(DateTimeUtil.isValidDate(dataIncio) == false){
		alert(i18n_message("citcorpore.comum.validacao.datainicio"));
	document.getElementById("dataInicioH").value = '';
	 	return false;
	}
	if(DateTimeUtil.isValidDate(dataFim) == false){
		 alert(i18n_message("citcorpore.comum.validacao.datafim"));
	 document.getElementById("dataFimH").value = '';
		return false;					
	}
	
	if( !verificaData(dataIncio ,dataFim ))
		return false;
	
	JANELA_AGUARDE_MENU.show();
	
	document.form.fireEvent('verificaHistoricoAlteracao'); 
}

function verificaData(dataInicio, dataFim) {
	
	var dtInicio = new Date();
	var dtFim = new Date();
	
	dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/"))).setFullYear;
	dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/"))).setFullYear;

	if (dtInicio > dtFim){
		alert(i18n_message("citcorpore.comum.dataInicioMenorFinal"));
		return false;
	}else
		return true;
}

function showRelacionamentos(){
	$("#relacionamentos").show();
}
/**
* Fun��o para fechar modal de nova solicita��o
* @autor flavio.santana
* 25/10/2013 10:50
*/
function fecharModalNovaSolicitacao() {
	$("#popupCadastroRapido").dialog('close');
}

function fecharModalFilha() {
	$("#popupCadastroRapido").dialog('close');
}

function fecharProblema() {
	$("#popupCadastroRapido").dialog('close');
}

function fecharMudanca() {
	$("#popupCadastroRapido").dialog('close');
}

function fecharVisao() {
	$("#popupCadastroRapido").dialog('close');
}

function getBotaoEditarProblema(id){
	var botaoVisualizarErrosConhecidos = new Image();

	botaoVisualizarErrosConhecidos.src = URL_INITIAL + '/template_new/images/icons/small/grey/pencil.png';
	botaoVisualizarErrosConhecidos.setAttribute("style", "cursor: pointer;");
	botaoVisualizarErrosConhecidos.id = id;
	botaoVisualizarErrosConhecidos.addEventListener("click", function(evt){CarregarProblema(id)}, true);

	return botaoVisualizarErrosConhecidos;
}

function CarregarProblema(idProblema){
	document.getElementById('iframeEditarProblema').src = URL_INITIAL + "/pages/problema/problema.load?iframe=true&chamarTelaProblema=S&acaoFluxo=E&idProblema="+idProblema;
	$("#POPUP_EDITARPROBLEMA").dialog("open");
}

function fecharFrameProblema(){
	$("#POPUP_EDITARPROBLEMA").dialog("close");
}
/**
* Fun��es essenciais para funcionamento do cadastro de nova solicita��o	
* @autor flavio.santana
* 25/10/2013 10:50
*/
function fecharJanelaAguarde() {
	JANELA_AGUARDE_MENU.hide();
}

function atualizarLista(){
		
}
var seqSelecionada = '';
var aux = '';
serializa = function() {
	var tabela = document.getElementById('tabelaCaracteristica');
	var count = tabela.rows.length;
	var contadorAux = 0;
	var caracteristicas = new Array();
	for ( var i = 1; i <= count; i++) {
		var trObj = document.getElementById('idCaracteristica' + i);

		if (!trObj) {
			continue;
		}

		caracteristicas[contadorAux] = getCaracteristica(i);
		contadorAux = contadorAux + 1;
	}
	var caracteristicasSerializadas = ObjectUtils.serializeObjects(caracteristicas);
	document.form.caracteristicasSerializadas.value = caracteristicasSerializadas;
	return true;
}

getCaracteristica = function(seq) {
	var CaracteristicaDTO = new CIT_CaracteristicaDTO();
	CaracteristicaDTO.sequencia = seq;
	
	/*Motido: Retirado por efeitos negativos em rela��o a seu uso
	* Autor: flavio.santana
	* Data/Hora: 02/11/2013 13:26
	*/
	//CaracteristicaDTO.idCaracteristica = eval('document.form.idCaracteristica' + seq + '.value');
	//CaracteristicaDTO.valorString = eval('document.form.valorString' + seq + '.value');
	
	CaracteristicaDTO.idCaracteristica = $('#idCaracteristica' + seq).val();
	CaracteristicaDTO.valorString = $('#valorString' + seq).val();
	return CaracteristicaDTO;
}
/*Gravar baseline*/
function gravarBaseline() {
	var tabela = document.getElementById('tblBaselines');
	var count = tabela.rows.length;
	var contadorAux = 0;
	var baselines = new Array();

	for ( var i = 1; i <= count; i++) {
		var trObj = document.getElementById('idHistoricoIC' + i);	
		if (!trObj) {
			continue;
		}	
		baselines[contadorAux] = getbaseline(i);
		contadorAux = contadorAux + 1;
	}
	serializaBaseline();
	/**
	* Mostra o janela aguarde
	* @author flavio.santana
	* 25/10/2013
	*/
	JANELA_AGUARDE_MENU.show();
	document.formBaseline.fireEvent("saveBaseline");
}

var seqBaseline = '';
var aux = '';
serializaBaseline = function() {
	var tabela = document.getElementById('tblBaselines');
	var count = tabela.rows.length;
	var contadorAux = 0;
	var baselines = new Array();
	for ( var i = 1; i <= count; i++) {
		var trObj = document.getElementById('idHistoricoIC' + i);
		if (!trObj) {
			continue;
		}else if(trObj.checked){
			baselines[contadorAux] = getbaseline(i);
			contadorAux = contadorAux + 1;
			continue;
		}	
		
	}
	var baselinesSerializadas = ObjectUtils.serializeObjects(baselines);
	document.formBaseline.baselinesSerializadas.value = baselinesSerializadas;
	return true;
}

getbaseline = function(seq) {
	var HistoricoItemConfiguracaoDTO = new CIT_HistoricoItemConfiguracaoDTO();
	HistoricoItemConfiguracaoDTO.sequencia = seq;
	/*Motido: Retirado por efeitos negativos em rela��o a seu uso
	* Autor: flavio.santana
	* Data/Hora: 02/11/2013 13:26
	*/
	//HistoricoItemConfiguracaoDTO.idHistoricoIC = eval('document.formBaseline.idHistoricoIC' + seq + '.value');
	HistoricoItemConfiguracaoDTO.idHistoricoIC = $('#idHistoricoIC' + seq).val();
	return HistoricoItemConfiguracaoDTO;
}


/*
 * Alterado por
 * desenvolvedor: rcs (Rafael César Soyer)
 * data: 12/01/2015
 */
function restaurar(id){
	document.formBaseline.idHistoricoIC.value = id;
	if(confirm(i18n_message("itemConfiguracaoTree.restaurarVersao"))){
		JANELA_AGUARDE_MENU.show();
		document.formBaseline.fireEvent("restaurarBaseline");
	}
}


function fecharPopup(){
	$("#POPUP_TIPOITEMCONFIGURACAO").dialog("close");
}

function fecharPopupGrupo(){
	$("#POPUP_GRUPOITEMCONFIGURACAO").dialog("close");
}

/*
 * Autor: Edu Rodrigues Braz	
 * Data:01/04/2014
 * Descri��o: limpar toda a aba de auditoria incluindo a tabela
 */
function limparDadosAuditoria(){
	limparTabelas("tblHistoricoAlteracao");
	document.getElementById("historicoAlteracaoItemConfiguracao").innerHTML=""; 
	document.formHistoricoAlteracao.clear();
}

function limpar() {
	deleteAllRows();
	var arr = ["tblLiberacoes", "tblMudancas", "tblIC", "tblProblemas", "tbIncidentes", "tblBaselines"];
	limparTabelas(arr);
	document.getElementById('gridCaracteristica').style.display = 'none';
	document.getElementById('divInventario').style.display = 'none';
	document.form.clear();
}

function exibeGrid() {
	document.getElementById('gridCaracteristica').style.display = 'block';
}

var objTab = null;

function update() {
	if (document.getElementById("idItemConfiguracao").value != "") {
		if (confirm(i18n_message("citcorpore.comum.deleta"))) {
			document.form.fireEvent("delete");
		}
	}
}

function LOOKUP_PESQUISAITEMCONFIGURACAOTODOS_select(id, desc) {
	document.form.restore( {
		idItemConfiguracao: id
	});
}

function LOOKUP_GRUPOITEMCONFIGURACAO_select(id,desc){
	document.form.idGrupoItemConfiguracao.value = id;
	document.form.nomeGrupoItemConfiguracao.value = desc;
	$("#POPUP_GRUPOITEMCONFIGURACAO").dialog("close");
	document.form.fireEvent("restoreGrupoItemConfiguracao");
}

function LOOKUP_PESQUISAITEMCONFIGURACAO_select(idItemConfiguracaoPai, desc) {
	
	if(confirm(i18n_message("itemConfiguracaoTree.ICrelacionado"))) {
		document.form.idItemConfiguracaoPai.value = idItemConfiguracaoPai;	
		$("#nomeItemConfiguracaoPai").text(desc);
			$("#POPUP_ITEMCONFIGPAI").dialog("close");		
		gravar();
	}
	
}

/*
 * Duplicado
 * function LOOKUP_TIPOITEMCONFIGURACAO_select(idTipo, desc) {
	document.form.idTipoItemConfiguracao.value =	 idTipo;
	document.form.fireEvent("restoreTipoItemConfiguracao");
}*/

var countCaracteristica = 0;
function insereRow(id, desc) {
	var tabela = document.getElementById('tabelaCaracteristica');
	var lastRow = tabela.rows.length;

	var row = tabela.insertRow(lastRow);
	countCaracteristica++;

	var valor = desc.split(' - ');

	var coluna = row.insertCell(0);
	coluna.innerHTML = valor[0] + '<input type="hidden" id="idCaracteristica' + countCaracteristica + '" name="idCaracteristica" value="' + id + '" />';

	coluna = row.insertCell(1);
	coluna.innerHTML = valor[1];

	coluna = row.insertCell(2);
	coluna.innerHTML = valor[2]
}

function restoreRow() {
	var tabela = document.getElementById('tabelaCaracteristica');
	var lastRow = tabela.rows.length;

	var row = tabela.insertRow(lastRow);
	countCaracteristica++;
	var coluna = row.insertCell(0);
	coluna.innerHTML = '<input type="hidden" id="idCaracteristica' + countCaracteristica + '" name="idCaracteristica"/>' + 
						'<div id="caracteristica'+ countCaracteristica +'"></div>';	
	coluna = row.insertCell(1);
	coluna.innerHTML = '<div id="descricao'+ countCaracteristica +'"></div>';
	coluna = row.insertCell(2);
	coluna.innerHTML = '<input style="width: 98%; " type="text" id="valorString' + countCaracteristica + '" name="valorString" maxlength="3999"/>';	
	
}

var seqSelecionada = '';
function setRestoreCaracteristica(idCaracteristica, caracteristica, tag, valorString, descricao, idEmpresa, dataInicio, dataFim) {
	if (seqSelecionada != '') {
		/*Motido: Retirado por efeitos negativos em rela��o a seu uso
		* Autor: flavio.santana
		* Data/Hora: 02/11/2013 13:26
		*/
		//eval('document.form.idCaracteristica' + seqSelecionada + '.value = "' + idCaracteristica + '"');
		$('#idCaracteristica' + seqSelecionada).val(idCaracteristica);
		$('#caracteristica' + seqSelecionada).text(ObjectUtils.decodificaEnter(caracteristica));
		$('#descricao' + seqSelecionada).text(ObjectUtils.decodificaEnter(descricao));
		$('#valorString' + seqSelecionada).val(valorString);
		
		/*Motido: Retirado por efeitos negativos em rela��o a seu uso
		* Autor: flavio.santana
		* Data/Hora: 02/11/2013 13:26
		*/
		//eval('document.form.valorString' + seqSelecionada + '.value = "' + valorString + '"');					
	}
}

function deleteAllRows() {
	var tabela = document.getElementById('tabelaCaracteristica');
	var count = tabela.rows.length;

	while (count > 1) {
		tabela.deleteRow(count - 1);
		count--;
	}
	ocultaGrid();
}
/*
* Limpar todas as linhas das tabelas
* @autor flavio.santana
* 28/10/2013
*/
function limparTabelas(listTabela) {
	var tabela, count; 
	for(var i=0; i<listTabela.length;i++){
		tabela = document.getElementById(listTabela[i]);
		if(tabela != undefined) {
			count = tabela.rows.length;

			while (count > 1) {
				tabela.deleteRow(count - 1);
				count--;
			}	
		}
	}
}

function gravar() {
	parent.JANELA_AGUARDE_MENU.show();
	var tabela = document.getElementById('tabelaCaracteristica');
	var count = tabela.rows.length;
	var contadorAux = 0;
	var caracteristicas = new Array();
	document.getElementById('divInventario').style.display = 'block';
	document.getElementById('idGrupoResponsavel').value;
	for ( var i = 1; i <= count; i++) {
		var trObj = document.getElementById('idCaracteristica' + i);

		if (!trObj) {
			continue;
		}	
		caracteristicas[contadorAux] = getCaracteristica(i);
		contadorAux = contadorAux + 1;
	}				
	serializa();
	if(document.getElementById('identificacao').value.toLowerCase() === i18n_message("gerenciaConfiguracaoTree.novoItem").toLowerCase() 
			|| document.getElementById('nome').value.toLowerCase() === i18n_message("gerenciaConfiguracaoTree.novoItem").toLowerCase()){
		alert(i18n_message("gerenciaConfiguracaoTree.nomeInvalidoItem"));
		parent.JANELA_AGUARDE_MENU.hide();
	}
	else{
		document.getElementById('idGrupoResponsavel').value;
		document.form.save();
	}
}