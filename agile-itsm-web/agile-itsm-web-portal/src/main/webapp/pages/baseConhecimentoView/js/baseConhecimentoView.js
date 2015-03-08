var popup;
var popup4;
var popup5;
var popup6;
var controle = 0;
var contConhecimentoRelacionado = 0;
var countComentario = 0;

var retorno = ctx + "/pages/index/index.load";
var fileref_src = ctx + "/template_new/js/star-rating/jquery.rating.js";
var row_cells_3_img_src = ctx + "/template_new/images/icons/small/grey/graph.png";

$(window).load(function(){
	JANELA_AGUARDE_MENU.show()
	popup = new PopupManager(1800, 900, ctx + "/pages/");
	popup4 = new PopupManager(1100, 600, ctx + "/pages/");
	popup5 = new PopupManager(1100, 600, ctx + "/pages/");
	popup6 = new PopupManager(1100, 600, ctx + "/pages/");

	document.form.afterRestore = function() {
		$('.tabs').tabs('select', 0);
	}

	$("#POPUP_USUARIO").dialog({
		title: i18n_message("citcorpore.comum.pesquisarUsuario"),
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true,
		show: "fade",
		hide: "fade"
	});

	$("#POPUP_HISTORICOVERSAO").dialog({
		title: i18n_message("pesquisaBaseConhecimento.historicoVersoes"),
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true,
		show: "fade",
		hide: "fade"
	});

	$("#POPUP_ALTERACAO").dialog({
		title: i18n_message("pesquisaBaseConhecimento.historicoAlteracao"),
		autoOpen : false,
		width : 1000,
		height : 400,
		modal : true,
		show: "fade",
		hide: "fade"
	});

	$("#POPUP_ATIVOS").dialog({
		autoOpen : false,
		width : 1005,
		height : 565,
		modal : true
	});

	if (fecharpesquisa && fecharpesquisa=="true") {
		document.getElementById('divpesquisa').style.display = 'none';
	}

	tree("#browser");
});

$(document).ready(
	function() {
		$('#divMostraUpload_uploadAnexos').closest('#divMostraUpload_uploadAnexos').remove();
	}
);

function tituloBaseConhecimentoView(idItem) {
	document.form2.idBaseConhecimento.value = idItem;
	document.form2.fireEvent("restore");

	$('#conhecimento').show();
}

function verificarPermissaoDeAcesso(idPasta,idBaseConhecimento) {
	document.form2.idPasta.value = idPasta;
	document.form2.idBaseConhecimento.value = idBaseConhecimento;
	document.form2.fireEvent("verificarPermissaoDeAcesso");
}

function corTitulo(idItem) {
	var browser = document.getElementById("browser");
	var lista = browser.getElementsByTagName("a");
	for ( var i = 0; i < lista.length; i++) {
		if (lista[i] == null) {
			continue;
		}
		if (lista[i].id != "idTitulo" + idItem) {
			lista[i].style.backgroundColor = "#FFF";
		} else {
			lista[i].style.backgroundColor = "#B0C4DE";
			lista[i].style.color = "#000";
		}
	}
}

function executeJS() {
	if (document.getElementById('jsOnDemand')) {
		document.getElementsByTagName("head")[0].removeChild(document.getElementById('jsOnDemand'));
	}

	var fileref = document.createElement('script');
	fileref.setAttribute("id", "jsOnDemand");
	fileref.setAttribute("type", "text/javascript");
	fileref.setAttribute("src", fileref_src);

	document.getElementsByTagName("head")[0].appendChild(fileref);

	try{
		$('input[type=radio].star').rating();
	}catch(e){}
}

function createDiv(nota){
	var str = '';
	if (nota == '1.0'){
		str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media1_' + controle + '" checked="checked" value="1.0" disabled="disabled"/>';
	}else{
		str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media1_' + controle + '" value="1.0" disabled="disabled"/>';
	}
	if (nota == '2.0'){
		str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media2_' + controle + '" checked="checked" value="2.0" disabled="disabled"/>';
	}else{
		str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media2_' + controle + '" value="2.0" disabled="disabled"/>';
	}
	if (nota == '3.0'){
		str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media3_' + controle + '" checked="checked" value="3.0" disabled="disabled"/>';
	}else{
		str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media3_' + controle + '" value="3.0" disabled="disabled"/>';
	}
	if (nota == '4.0'){
		str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media4_' + controle + '" checked="checked" value="4.0" disabled="disabled"/>';
	}else{
		str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media4_' + controle + '" value="4.0" disabled="disabled"/>';
	}
	if (nota == '5.0'){
		str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media5_' + controle + '" checked="checked" value="5.0" disabled="disabled"/>';
	}else{
		str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media5_' + controle + '" value="5.0" disabled="disabled"/>';
	}

	document.getElementById('divMostraNota').innerHTML = str;
}

function getStringStarsClean(){
	var str = '';
	str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media1_' + controle + '" value="1.0" disabled="disabled"/>';
	str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media2_' + controle + '" value="2.0" disabled="disabled"/>';
	str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media3_' + controle + '" value="3.0" disabled="disabled"/>';
	str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media4_' + controle + '" value="4.0" disabled="disabled"/>';
	str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media5_' + controle + '" value="5.0" disabled="disabled"/>';
	return str;
}

function getStringStars(){
	var str = '';
	str = str + '<input id="nota" class="star required" type="radio" name="nota" value="1" title="'+i18n_message("citcorpore.comum.fraco")+'/>';
	str = str + '<input  class="star" type="radio" name="nota" value="2" title="'+i18n_message("citcorpore.comum.regular")+'"/>';
	str = str + '<input  class="star" type="radio" name="nota" value="3" title="'+i18n_message("citcorpore.comum.bom")+'"/>';
	str = str + '<input  class="star" type="radio" name="nota" value="4" title="'+i18n_message("citcorpore.comum.otimo")+'"/>';
	str = str + '<input  class="star" type="radio" name="nota" value="5" title="'+i18n_message("citcorpore.comum.excelente")+'"/>';
	return str;
}

function tree(id) {
	$(id).treeview();
}

function gravar() {
	if (document.form.titulo.value == '' ){
		alert(i18n_message("baseConhecimento.validacao.selecionebase"));
		return fecharPopup();}
	else
	document.formPopup.save();
}

$(function() {
	$("#POPUP_COMENTARIOS").dialog({
		autoOpen : false,
		height : 458,
		width : 560,
		modal : true
	});
});

$(function() {
	$("#addComentarios").click(function() {
		$("#POPUP_COMENTARIOS").dialog("open");
	});
});

function fecharPopup(){
		$("#POPUP_COMENTARIOS").dialog("close");
}

function limpar() {
	document.formPopup.clear();
	document.getElementById("notaEnviarComentario").innerHTML = getStringStarsClean();
	$('input[type=radio].star').rating();
	document.getElementById("notaEnviarComentario").innerHTML = getStringStars();
	$('input[type=radio].star').rating();
}

function restoreRow(comentario,nome,email,nota) {
	controle++;
	var tabela = document.getElementById('tabelaComentarios');
	var lastRow = tabela.rows.length;

	var row = tabela.insertRow(lastRow);
	countComentario++;

	var coluna = row.insertCell(0);
	coluna.innerHTML = '<input type="hidden" id="idComentario' + countComentario + '" name="idComentario"/><input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="nome' + countComentario + '" name="nome" value="'+nome+'"/>';

	coluna = row.insertCell(1);
	coluna.innerHTML ='<input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="email' + countComentario + '" name="email" value="'+email+'"/>';

	coluna = row.insertCell(2);
	coluna.innerHTML = '<textarea style="width: 100%; border: 0 none;"  id="comentario' + countComentario + '" name="comentario" >'+comentario+'</textarea>';

	coluna = row.insertCell(3);
	var str = '';
	if (nota == '1'){
		str = str + '<input class="star" type="radio" name="nota_' + controle + '" id="nota1_' + controle + '" checked="checked" value="1.0" disabled="disabled"/>';
	}else{
		str = str + '<input class="star" type="radio" name="nota_' + controle + '" id="nota1_' + controle + '" value="1.0" disabled="disabled"/>';
	}
	if (nota == '2'){
		str = str + '<input class="star" type="radio" name="nota_' + controle + '" id="nota2_' + controle + '" checked="checked" value="2.0" disabled="disabled"/>';
	}else{
		str = str + '<input class="star" type="radio" name="nota_' + controle + '" id="nota2_' + controle + '" value="2.0" disabled="disabled"/>';
	}
	if (nota == '3'){
		str = str + '<input class="star" type="radio" name="nota_' + controle + '" id="nota3_' + controle + '" checked="checked" value="3.0" disabled="disabled"/>';
	}else{
		str = str + '<input class="star" type="radio" name="nota_' + controle + '" id="nota3_' + controle + '" value="3.0" disabled="disabled"/>';
	}
	if (nota == '4'){
		str = str + '<input class="star" type="radio" name="nota_' + controle + '" id="nota4_' + controle + '" checked="checked" value="4.0" disabled="disabled"/>';
	}else{
		str = str + '<input class="star" type="radio" name="nota_' + controle + '" id="nota4_' + controle + '" value="4.0" disabled="disabled"/>';
	}
	if (nota == '5'){
		str = str + '<input class="star" type="radio" name="nota_' + controle + '" id="nota5_' + controle + '" checked="checked" value="5.0" disabled="disabled"/>';
	}else{
		str = str + '<input class="star" type="radio" name="nota_' + controle + '" id="nota5_' + controle + '" value="5.0" disabled="disabled"/>';
	}
	coluna.innerHTML = str;
	executeJS();
}

var seqSelecionada = '';
function setRestoreComentario(idComentario, comentario, nome, email, nota, dataInicio) {
	if (seqSelecionada != '') {
		eval('document.form.idComentario' + seqSelecionada + '.value = "' + idComentario + '"');
		eval('document.form.comentario' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + comentario + '\')');
		eval('document.form.nome' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + nome + '\')');
		eval('document.form.email' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + email + '\')');

		if (ObjectUtils.decodificaEnter(nota) == 1){
			document.getElementById('nota1' + controle + '_' + seqSelecionada).checked = true;
		}

		if (ObjectUtils.decodificaEnter(nota) == 2){
			document.getElementById('nota2' + controle + '_' + seqSelecionada).checked = true;
		}

		if (ObjectUtils.decodificaEnter(nota) == 3){
			document.getElementById('nota3' + controle + '_' + seqSelecionada).checked = true;
		}

		if (ObjectUtils.decodificaEnter(nota) == 4){
			document.getElementById('nota4' + controle + '_' + seqSelecionada).checked = true;
		}

		if (ObjectUtils.decodificaEnter(nota) == 5){
			document.getElementById('nota5' + controle + '_' + seqSelecionada).checked = true;
			document.getElementById('nota5' + controle + '_' + seqSelecionada).className = "star";
		}

	}
	exibeGrid();
}

function removeLinhaTabela(idTabela, rowIndex) {
	if (confirm(i18n_message("baseConhecimento.confirme.excluircomentario"))) {
		HTMLUtils.deleteRow(idTabela, rowIndex);

		document.form.comentarioSerializado.value = eval('document.form.idComentario' + rowIndex + '.value');

		document.form.fireEvent("excluirAssociacaoComentarioBaseConhecimento");
	}
}

function deleteAllRows() {
	var tabela = document.getElementById('tabelaComentarios');
	var count = tabela.rows.length;

	while (count > 1) {
		tabela.deleteRow(count - 1);
		count--;
	}
	ocultaGrid();
}

function exibeGrid() {
	document.getElementById('gridComentario').style.display = 'block';
}

function ocultaGrid() {
	document.getElementById('gridComentario').style.display = 'none';
}

function fecharPesquisa(){
	$("#resultPesquisaPai").dialog("close");
}

function mostraPesquisaBaseConhecimento(){
	document.getElementById("resultPesquisaPai").style.display="block";
}

$(function() {
	$("#resultPesquisaPai").dialog({
		title : i18n_message("citcorpore.comum.resultadopesquisa"),
		autoOpen : false,
		width : 700,
		height : 390,
		modal : true
	});

	$('#conhecimento').hide();
});

pesquisarBaseConhecimento = function(){
	if (document.formPesquisa.nomeUsuarioAutor.value == "") {
		document.formPesquisa.idUsuarioAutorPesquisa.value = "0";
	}
	if (document.formPesquisa.nomeUsuarioAprovador.value == "") {
		document.formPesquisa.idUsuarioAprovadorPesquisa.value = "0";
	}

	$('#conhecimento').hide();
	document.formPesquisa.fireEvent('pesquisaBaseConhecimento');

};

function fecharBaseConhecimentoView(){
	parent.fechaPopupIframe();
}

function voltar(){
	verificarAbandonoSistema = false;
	window.location = retorno;
}

function contadorClicks(idBaseConhecimento){
	document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
	document.formPesquisa.fireEvent('contadorDeClicks');
}

function incidentesAbertosPorBaseConhecimnto(idBaseConhecimento){
	document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
	document.formPesquisa.fireEvent('mostrarQuantidadeDeIncidentesAbertosPorBaseConhecimento');
}

function problemasAbertosPorBaseConhecimnto(idBaseConhecimento){
	document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
	document.formPesquisa.fireEvent('mostrarQuantidadeDeProblemasAbertosPorBaseConhecimento');
}
function mudancasAbertasPorBaseConhecimnto(idBaseConhecimento){
	document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
	document.formPesquisa.fireEvent('mostrarQuantidadeDeMudancasAbertasPorBaseConhecimento');
}
function comentariosAbertosPorBaseConhecimnto(idBaseConhecimento){
	document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
	document.formPesquisa.fireEvent('mostrarQuantidadeDeComentariosAbertosPorBaseConhecimento');
}
function itensConfiguracoesAbertosPorBaseConhecimnto(idBaseConhecimento){
	document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
	document.formPesquisa.fireEvent('mostrarQuantidadeDeItensConfiguracoesAbertosPorBaseConhecimento');
}

function gravarIncidentesRequisicao(idBaseConhecimento){
	if(idBaseConhecimento=="" ||idBaseConhecimento == null ){
		alert(i18n_message("baseConhecimento.validacao.selecionebase"));
		return;
	}

	var id =  parseInt(idBaseConhecimento);

	if(pagina == null || pagina==" "){
		document.getElementById('iframeSolicitacaoServico').src =ctx+'/pages/solicitacaoServico/solicitacaoServico.load?id=' + id;
	}else{
		document.getElementById('iframeSolicitacaoServico').src =ctx+'/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?id=' + id;
	}

	$("#POPUP_INCIDENTE").dialog("open");
}

$(function() {
	$("#POPUP_INCIDENTE").dialog({
		autoOpen : false,
		width : 1500,
		height : 1005,
		modal : true
	});
});

function fecharSolicitacao(){
	$("#POPUP_INCIDENTE").dialog('close');
}

function gravarGestaoProblema(idBaseConhecimento){
	if(idBaseConhecimento=="" ||idBaseConhecimento == null ){
		alert(i18n_message("baseConhecimento.validacao.selecionebase"));
		return;
	}

	var id =  parseInt(idBaseConhecimento);

	document.getElementById('iframeProblema').src =ctx+'/pages/problema/problema.load?idBaseConhecimento=' + id;

	$("#POPUP_CADASTROPROBLEMA").dialog("open");
}

$(function() {
	$("#POPUP_CADASTROPROBLEMA").dialog({
		autoOpen : false,
		width : 1500,
		height : 1005,
		modal : true
	});
});

function fecharProblemaAtualizaGrid() {
	$("#POPUP_PROBLEMA").dialog("close");
}

function gravarGestaoMudanca(idBaseConhecimento){
	if(idBaseConhecimento=="" ||idBaseConhecimento == null ){
		alert(i18n_message("baseConhecimento.validacao.selecionebase"));
		return;
	}

	var id =  parseInt(idBaseConhecimento);

	document.getElementById('iframeMudanca').src =ctx+'/pages/requisicaoMudanca/requisicaoMudanca.load?idBaseConhecimento=' + id;

	$("#POPUP_CADASTROMUDANCA").dialog("open");
}

function gravarGestaoLiberacao(idBaseConhecimento){
	if(idBaseConhecimento=="" ||idBaseConhecimento == null ){
		alert(i18n_message("baseConhecimento.validacao.selecionebase"));
		return;
	}

	var id =  parseInt(idBaseConhecimento);

	document.getElementById('iframeLiberacao').src =ctx+'/pages/requisicaoLiberacao/requisicaoLiberacao.load?idBaseConhecimento=' + id;

	$("#POPUP_CADASTROLIBERACAO").dialog("open");
}

$(function() {
	$("#POPUP_CADASTROMUDANCA").dialog({
		autoOpen : false,
		width : 1500,
		height : 1005,
		modal : true
	});

	$("#POPUP_CADASTROLIBERACAO").dialog({
		autoOpen : false,
		width : 1500,
		height : 1005,
		modal : true
	});
});

function fecharMudancaAtualizaGrid(){
	$("#POPUP_MUDANCA").dialog('close');
}

function fecharLiberacao(){
	$("#POPUP_CADASTROLIBERACAO").dialog('close');
}

function gravarGestaoItemConfiguracao(idBaseConhecimento){
	if(idBaseConhecimento=="" ||idBaseConhecimento == null ){
		alert(i18n_message("baseConhecimento.validacao.selecionebase"));
		return;
	}

	var id =  parseInt(idBaseConhecimento);

	document.getElementById('iframeItemConfiguracao').src =ctx+'/pages/gerenciaConfiguracaoTree/gerenciaConfiguracaoTree.load?iframe=true&idBaseConhecimento=' + id;

	$("#POPUP_ITEMCONFIGURACAO").dialog("open");
}

$(function() {
	$("#POPUP_ITEMCONFIGURACAO").dialog({
		autoOpen : false,
		width : 1500,
		height : 1005,
		modal : true
	});
});

function fecharItemConfiguracao(){
	$("#POPUP_ITEMCONFIGURACAO").dialog('close');
}

function consultarSolicitacao(idBaseConhecimento){
	document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
	document.formPesquisa.fireEvent('listarSolicitacaoServico');
}

function consultarProblemas(idBaseConhecimento){
	document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
	document.formPesquisa.fireEvent('listarProblema');
}

function consultarMudancas(idBaseConhecimento){
	document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
	document.formPesquisa.fireEvent('listarMudanca');
}

function consultarComentarios(idBaseConhecimento){
	document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
	document.formPesquisa.fireEvent('listarComentario');
}

function consultarItensConfiguracoes(idBaseConhecimento){
	document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
	document.formPesquisa.fireEvent('listarItemConfiguracao');
}

$(function() {
	$("#POPUP_DADOSSOLICITCAO").dialog({
		autoOpen : false,
		width : 1000,
		height : 500,
		modal : true
	});
});

$(function() {
	$("#POPUP_DADOSPROBLEMA").dialog({
		autoOpen : false,
		width : 1000,
		height : 500,
		modal : true
	});
});

$(function() {
	$("#POPUP_DADOSMUDANCA").dialog({
		autoOpen : false,
		width : 1000,
		height : 500,
		modal : true
	});
});

$(function() {
	$("#POPUP_DADOSCOMENTARIO").dialog({
		autoOpen : false,
		width : 1000,
		height : 500,
		modal : true
	});
});

$(function() {
	$("#POPUP_DADOSITEMCONFIGURACAO").dialog({
		autoOpen : false,
		width : 1000,
		height : 200,
		modal : true
	});
});

function LOOKUP_USUARIO_select(id, desc) {
	if ($('#isNotificacao').val() == "true"){
		document.formPesquisa.idUsuarioAutorPesquisa.value = id;
		document.formPesquisa.nomeUsuarioAutor.value = desc;
	} else{
		document.formPesquisa.idUsuarioAprovadorPesquisa.value = id;
		document.formPesquisa.nomeUsuarioAprovador.value = desc;
	}

	$("#POPUP_USUARIO").dialog("close");
}

function abrirPopupUsuario(isNotificacao){
	if (isNotificacao == true){

		$('#isNotificacao').val(true);

	} else{
		if (isNotificacao == false){
			$('#isNotificacao').val(false);
		}
	}

	$("#POPUP_USUARIO").dialog("open");
}

function addLinhaTabelaConhecimentoRelacionado(id, desc, valida, idPasta){
	var tbl = document.getElementById('tabelaConhecimentoRelacionado');
	$('#tabelaConhecimentoRelacionado').show();
	$('#gridConhecimentoRelacionado').show();

	var lastRow = tbl.rows.length;
	var row = tbl.insertRow(lastRow);
	var coluna = row.insertCell(0);
	contConhecimentoRelacionado++;

	coluna.innerHTML = "";
	coluna = row.insertCell(1);
	coluna.innerHTML = "<a  onclick='verificarPermissaoDeAcesso("+idPasta+","+id+");incidentesAbertosPorBaseConhecimnto("+id+");'   id='idTitulo"+id+"' href='javascript:;' >" + desc +"</a>";
}

function verificarPermissaoDeAcessoIdBaseConhecimento(idBaseConhecimento) {
	var idBaseConhecimento =  parseInt(idBaseConhecimento);
	document.form2.idBaseConhecimento.value = idBaseConhecimento;
	document.form2.fireEvent("verificarPermissaoDeAcesso");
}

function deleteAllRowsConhecimento() {
	var tabela = document.getElementById('tabelaConhecimentoRelacionado');
	var count = tabela.rows.length;

	while (count > 1) {
		tabela.deleteRow(count - 1);
		count--;
	}
	$('#gridConhecimentoRelacionado').hide();
}

$(function() {
	$("#exibirHistorioVersao").click(function() {
		document.form2.fireEvent("exibirHistoricoVersoesBaseConhecimento");
	});
});

$(function() {
	$("#exibirHistorioAlteracao").click(function() {
		document.form2.fireEvent("exibirHistoricoAlteracaoBaseConhecimento");
	});
});

function addLinhaTabelaHistorioVersoes(id, desc, valida, versao){
	var tbl = document.getElementById('tabelaHistoricoVersoes');
	$('#tabelaHistoricoVersoes').show();
	$('#gridHistoricoVersoes').show();

	var lastRow = tbl.rows.length;
	var row = tbl.insertRow(lastRow);
	var coluna = row.insertCell(0);

	coluna.innerHTML = desc ;

	coluna = row.insertCell(1);

	coluna.innerHTML = versao;
}

function deleteAllRowsHistoricoVersoes() {
	var tabela = document.getElementById('tabelaHistoricoVersoes');
	var count = tabela.rows.length;

	while (count > 1) {
		tabela.deleteRow(count - 1);
		count--;
	}
	$('#gridHistoricoVersoes').hide();
}

function deleteAllRowsHistoricoAlteracao() {
	var tabela = document.getElementById('tabelaHistoricoAlteracao');
	var count = tabela.rows.length;

	while (count > 1) {
		tabela.deleteRow(count - 1);
		count--;
	}
	$('#gridHistoricoAlteracao').hide();
}

function addLinhaTabelaHistorioAlteracao(titulo, pasta, versao, origem, usuario, dataHoraAlteracao, status){
	var tbl = document.getElementById('tabelaHistoricoAlteracao');
	$('#tabelaHistoricoAlteracao').show();
	$('#gridHistoricoAlteracao').show();

	var lastRow = tbl.rows.length;
	var row = tbl.insertRow(lastRow);
	var coluna = row.insertCell(0);

	coluna.innerHTML = titulo ;

	coluna = row.insertCell(1);
	coluna.innerHTML = pasta;

	coluna = row.insertCell(2);
	coluna.innerHTML = versao;

	coluna = row.insertCell(3);
	coluna.innerHTML = origem;

	coluna = row.insertCell(4);
	coluna.innerHTML = status;

	coluna = row.insertCell(5);
	coluna.innerHTML = usuario;

	coluna = row.insertCell(6);
	coluna.innerHTML = dataHoraAlteracao;
}

function selectedItemConfiguracao(idItemCfg){
	document.formPesquisa.idItemConfiguracao.value = idItemCfg;
	document.formPesquisa.fireEvent("restoreItemConfiguracao");
}

function LOOKUP_PROBLEMA_select(id, desc){
	document.formPesquisa.idProblema.value = id;
	document.formPesquisa.fireEvent('atualizaGridProblema');
}

function LOOKUP_MUDANCA_select(id, desc){
	document.formPesquisa.idRequisicaoMudanca.value = id;
	document.formPesquisa.fireEvent('atualizaGridMudanca');
}
function LOOKUP_LIBERACAO_select(id, desc){
	document.formPesquisa.idRequisicaoLiberacao.value = id;
	document.formPesquisa.fireEvent('atualizaGridLiberacao');
}

function LOOKUP_SOLICITACAOSERVICO_select(id, desc){
	document.formPesquisa.idSolicitacaoServico.value = id;
	document.formPesquisa.fireEvent('atualizaGridSolicitacao');
}

function fecharPopupPesquisaItemCfg(){
	$("#popupCadastroRapido").dialog("close");
}

exibeIconesIC = function(row, obj){
	var id = obj.idItemConfiguracao;

	obj.sequenciaIC = row.rowIndex;
	row.cells[0].innerHTML = '<img src="'+ctx+'/imagens/delete.png" border="0" onclick="excluiIC('+ row.rowIndex + ',this)" style="cursor:pointer"/>';

	if(obj.idItemConfiguracaoPai == ""){
		row.cells[3].innerHTML = '<img src="' + row_cells_3_img_src + '" border="0" onclick="popupAtivos( '+ id + ')" style="cursor:pointer"/>';
	}
}

function gravarItensConfiguracaoConhecimento(){
	document.formPesquisa.idItemConfiguracao.value = "";

	var objsIC = HTMLUtils.getObjectsByTableId('tblIC');
	document.formPesquisa.colItensIC_Serialize.value = ObjectUtils.serializeObjects(objsIC);

	document.formPesquisa.fireEvent("gravarItensConfiguracaoConhecimento");
}

$(function() {
	$("#POPUP_MUDANCA").dialog({
		autoOpen : false,
		width : 1000,
		height : 500,
		modal : true
	});

	$("#POPUP_LIBERACAO").dialog({
		autoOpen : false,
		width : 1000,
		height : 500,
		modal : true
	});

	$("#POPUP_PROBLEMA").dialog({
		autoOpen : false,
		width : 1000,
		height : 500,
		modal : true
	});

	$("#POPUP_SOLICITACAO").dialog({
		autoOpen : false,
		width : 1000,
		height : 500,
		modal : true
	});

	$("#imagenMudanca").click(function() {
		$("#POPUP_MUDANCA").dialog("open");
	});

	$("#imagenProblema").click(function() {
		$("#POPUP_PROBLEMA").dialog("open");
	});

	$("#imagenSolicitacao").click(function() {
		$("#POPUP_SOLICITACAO").dialog("open");
	});
	$("#imagenLiberacao").click(function() {
		$("#POPUP_LIBERACAO").dialog("open");
	});
});

function chamaPopupCadastroProblema(){
var idSolicitacaoServico = 0;
	try{
		idSolicitacaoServico = document.form.idSolicitacaoServico.value;
	}catch(e){
}
popup4.abrePopupParms('problema', '', 'idSolicitacaoServico=' + idSolicitacaoServico);
}

function chamaPopupCadastroMudanca(){
var idSolicitacaoServico = 0;
	try{
		idSolicitacaoServico = document.form.idSolicitacaoServico.value;
	}catch(e){
}
popup5.abrePopupParms('requisicaoMudanca', '', '');
}

function chamaPopupCadastroLiberacao(){
var idSolicitacaoServico = 0;
	try{
		idSolicitacaoServico = document.form.idSolicitacaoServico.value;
	}catch(e){
}
popup6.abrePopupParms('requisicaoLiberacao', '', '');
}

function fecharProblema(){
	$("#POPUP_CADASTROPROBLEMA").dialog('close');
	$("#popupCadastroRapido").dialog('close');
}

function fecharMudanca(){
	$("#POPUP_CADASTROMUDANCA").dialog('close');
	$("#popupCadastroRapido").dialog('close');
}
function fecharLiberacao(){
	$("#POPUP_LIBERACAO").dialog("close");
}

function fecharSolicitacaoServico(){
	$("#POPUP_SOLICITACAO").dialog('close');
}

exibeIconesProblema = function(row, obj){
	var id = obj.idProblema;
	obj.sequenciaOS = row.rowIndex;
	row.cells[0].innerHTML = '<img src="'+ctx+'/imagens/delete.png" border="0" onclick="excluiProblema('+ row.rowIndex + ',this)" style="cursor:pointer"/>';
	row.cells[1].innerHTML = '<img src="'+ctx+'/imagens/viewCadastro.png" border="0" onclick="carregarProblema(row, obj)" style="cursor:pointer"/>';
}

exibeIconesMudanca = function(row, obj){
	var id = obj.idRequisicaoMudanca;
	obj.sequenciaOS = row.rowIndex;
	row.cells[0].innerHTML = '<img src="'+ctx+'/imagens/delete.png" border="0" onclick="excluiMudanca('+ row.rowIndex + ',this)" style="cursor:pointer"/>';
}
exibeIconesLiberacao = function(row, obj){
	var id = obj.idRequisicaoLiberacao;
	obj.sequenciaOS = row.rowIndex;
	row.cells[0].innerHTML = '<img src="'+ctx+'/imagens/delete.png" border="0" onclick="excluiLiberacao('+ row.rowIndex + ',this)" style="cursor:pointer"/>';
}

exibeIconesSolicitacao = function(row, obj){
	var id = obj.idRequisicaoMudanca;
	obj.sequenciaOS = row.rowIndex;
	row.cells[0].innerHTML = '<img src="'+ctx+'/imagens/delete.png" border="0" onclick="excluiSolicitacao('+ row.rowIndex + ',this)" style="cursor:pointer"/>';
}

function gravarSolicitacoesConhecimento(){
	JANELA_AGUARDE_MENU.show();
	var objs = HTMLUtils.getObjectsByTableId('tblSolicitacao');
	document.formPesquisa.colItensINC_Serialize.value = ObjectUtils.serializeObjects(objs);
	document.formPesquisa.fireEvent("gravarSolicitacoesConhecimento");
}

function gravarProblemasConhecimento(){
	JANELA_AGUARDE_MENU.show();
	var objs = HTMLUtils.getObjectsByTableId('tblProblema');
	document.formPesquisa.colItensProblema_Serialize.value = ObjectUtils.serializeObjects(objs);
	document.formPesquisa.fireEvent("gravarProblemasConhecimento");
}

function gravarMudancaConhecimento(){
	JANELA_AGUARDE_MENU.show();
	var objsMudanca = HTMLUtils.getObjectsByTableId('tblMudanca');
	document.formPesquisa.colItensMudanca_Serialize.value = ObjectUtils.serializeObjects(objsMudanca);
	document.formPesquisa.fireEvent("gravarMudancaConhecimento");
}
function gravarLiberacaoConhecimento(){
	JANELA_AGUARDE_MENU.show();
	var objsLiberacao = HTMLUtils.getObjectsByTableId('tblLiberacao');
	document.formPesquisa.colItensLiberacao_Serialize.value = ObjectUtils.serializeObjects(objsLiberacao);
	document.formPesquisa.fireEvent("gravarLiberacaoConhecimento");
}

function gravarICConhecimento(){
	JANELA_AGUARDE_MENU.show();
	var objsIC = HTMLUtils.getObjectsByTableId('tblIC');
	document.formPesquisa.colItensIC_Serialize.value = ObjectUtils.serializeObjects(objsIC);
	document.formPesquisa.fireEvent("gravarICConhecimento");
}

function popupAtivos(id){
	var idItem = id;
	document.getElementById('iframeAtivos').src =ctx+'/pages/informacaoItemConfiguracao/informacaoItemConfiguracao.load?id=' + idItem;
	$("#POPUP_ATIVOS").dialog("open");
}

excluiProblema = function(indice) {
	if (indice > 0 && confirm(i18n_message("citcorpore.ui.confirmacao.mensagem.Confirma_exclusao"))) {
		HTMLUtils.deleteRow('tblProblema', indice);
	}
}

excluiMudanca = function(indice) {
	if (indice > 0 && confirm(i18n_message("citcorpore.ui.confirmacao.mensagem.Confirma_exclusao"))) {
		HTMLUtils.deleteRow('tblMudanca', indice);
	}
}
excluiLiberacao = function(indice) {
	if (indice > 0 && confirm(i18n_message("citcorpore.ui.confirmacao.mensagem.Confirma_exclusao"))) {
		HTMLUtils.deleteRow('tblLiberacao', indice);
	}
}

excluiIC = function(indice) {
	if (indice > 0 && confirm(i18n_message("citcorpore.ui.confirmacao.mensagem.Confirma_exclusao"))) {
		HTMLUtils.deleteRow('tblIC', indice);
	}
}

excluiSolicitacao = function(indice) {
	if (indice > 0 && confirm(i18n_message("citcorpore.ui.confirmacao.mensagem.Confirma_exclusao"))) {
		HTMLUtils.deleteRow('tblSolicitacao', indice);
	}
}

function getBotaoEditarProblema(row, obj){
	var botaoVisualizarProblemas = new Image();

	botaoVisualizarProblemas.src = ctx+'/template_new/images/icons/small/grey/pencil.png';
	botaoVisualizarProblemas.setAttribute("style", "cursor: pointer;");
	botaoVisualizarProblemas.id = obj.idProblema;
	botaoVisualizarProblemas.addEventListener("click", function(evt){CarregarProblema(id)}, true);

	return botaoVisualizarProblemas;
}

function carregarProblema(row, obj){
	var idProblema = obj.idProblema;
	document.getElementById('iframeEditarProblema').src = ctx+"/pages/problema/problema.load?iframe=true&chamarTelaProblema=S&acaoFluxo=E&idProblema="+idProblema;
	$("#POPUP_EDITARPROBLEMA").dialog("open");
}
$(function() {
	$("#POPUP_EDITARPROBLEMA").dialog({
		autoOpen : false,
		width : "98%",
		height : 1000,
		modal : true
	});

	jQuery.fn.toggleText = function(a,b) {
		return this.html(this.html().replace(new RegExp("("+a+"|"+b+")"),function(x){return(x==a)?b:a;}));
	}

	$('#divpesquisa').css('display', 'block');

	$('.manipulaDiv', '#tabs-1').click(function() {
		$(this).next().slideToggle('slow') .siblings('#divpesquisa:visible').slideToggle('fast');
		$(this).toggleText(i18n_message("baseConhecimento.esconder"),i18n_message("baseConhecimento.mostrar")).siblings('span').next('#divpesquisa:visible').prev().toggleText(i18n_message("baseConhecimento.esconder"),i18n_message("baseConhecimento.mostrar"));
	});

	$('#modulos').css('display', 'block');

	$('span', '#modulosPai').click(function() {
		$(this).next().slideToggle('slow').siblings('#modulos:visible').slideToggle('fast');

		$(this).toggleText(i18n_message("baseConhecimento.esconder") ,i18n_message("baseConhecimento.mostrar")).siblings('span').next('#modulos:visible').prev().toggleText(i18n_message("baseConhecimento.esconder"),i18n_message("baseConhecimento.mostrar") )
	});

});

function habilitaDivPesquisa(){
	$('#divpesquisa').css('display', 'block');
	$('#spanPesq').text(i18n_message("baseConhecimento.esconderPesquisa" ),i18n_message("baseConhecimento.mostrarPesquisa") );
}

function desabilitaDivPesquisa(){
	$('#divpesquisa').css('display', 'none');
	$('#spanPesq').text(i18n_message("baseConhecimento.mostrarPesquisa"),i18n_message("baseConhecimento.esconderPesquisa" ));
}

function fecharFrameProblema(){
	$("#POPUP_EDITARPROBLEMA").dialog("close");
}

function buscaProblema(row, object){
	carregarProblema(row, object);
}

$(function() {
	var myLayout;
	myLayout = $('body').layout({
		north__resizable: false // OVERRIDE the pane-default of 'resizable=true'
		, north__spacing_open: 0 // no resizer-bar when open (zero height)
		, north__spacing_closed: 350
		, north__minSize: 110
		, west: {
			minSize: 310
			,onclose_end: function(){
				$('#baseconhecimento').css('width', '98%');
			}
			,onopen_end: function(){
				$('#baseconhecimento').css('width', '98%');
			}
		}
	});
});

function pesquisarItensFiltro() {
	$("#POPUP_INCIDENTE").dialog("close");
}

function fecharVisao() {
	$("#POPUP_CADASTROLIBERACAO").dialog("close");
	$("#popupCadastroRapido").dialog('close');
}

if (window.matchMedia("screen and (-ms-high-contrast: active), (-ms-high-contrast: none)").matches) {
	document.documentElement.className += "ie10";
}
