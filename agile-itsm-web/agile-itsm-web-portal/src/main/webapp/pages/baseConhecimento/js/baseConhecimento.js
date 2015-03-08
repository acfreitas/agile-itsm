var contUsuario = 0;
var contGrupo = 0;
var contConhecimentoRelacionado = 0;

var contUsuarioNotificacao = 0;
var contGrupoNotificacao = 0;

var contEvento = 0;

var objTab = null;

var oFCKeditor = new FCKeditor( 'conteudoBaseConhecimento' ) ;
function onInitQuestionario(){
	oFCKeditor.BasePath = ctx+'/fckeditor/';

	oFCKeditor.ToolbarSet   = 'Default' ;
	oFCKeditor.Width = '100%' ;
	oFCKeditor.Height = '300' ;
	oFCKeditor.ReplaceTextarea() ;
}

addEvent(window, "load", load, false);

function load() {

	document.form.afterRestore = function() {
		$('.tabs').tabs('select', 0);
	}

	$("#POPUP_USUARIO").dialog({
		title: i18n_message("citcorpore.comum.pesquisarUsuario"),
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});

	$("#POPUP_EVENTOMONITORAMENTO").dialog({
		title: i18n_message("citcorpore.comum.pesquisarEvento"),
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true,
		show: "fade",
		hide: "fade"
	});

	$("#POPUP_GRUPO").dialog({
		title: i18n_message("citcorpore.comum.pesquisarGrupo"),
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true

	});

	$("#POPUP_CONHECIMENTO").dialog({
		title: i18n_message("citcorpore.comum.pesquisarConhecimento"),
		autoOpen : false,
		width : 600,
		height : 750,
		modal : true,
		show: "fade",
		hide: "fade"
	});

	$(function() {
		$("#POPUP_NOTIFICACAO").dialog({
			title: i18n_message("baseconhecimento.notificacoes"),
			autoOpen : false,
			width : 1300,
			height : 600,
			modal : true,
			show: "fade",
			hide: "fade"
		});
	});

	$("#POPUP_GRAUDEIMPORTANCIA").dialog({
		title: i18n_message("baseconhecimento.graudeimportancia"),
		autoOpen : false,
		width : 1300,
		height : 600,
		modal : true,
		show: "fade",
		hide: "fade"
	});

	$("#POPUP_EVENTOMONITCONHECIMENTO").dialog({
		title: i18n_message('baseConhecimento.associarDocumentoEventos'),
		autoOpen : false,
		width : 800,
		height : 600,
		modal : true,
		show: "fade",
		hide: "fade"
	});

	$("#POPUP_CONHECIMENTORELACIONADO").dialog({
		title: i18n_message('baseConhecimento.conhecimentoRelacionado'),
		autoOpen : false,
		width : 800,
		height : 600,
		modal : true,
		show: "fade",
		hide: "fade"
	});

	$(function() {
		$("#POPUP_INCIDENTE").dialog({
			autoOpen : false,
			width : 1500,
			height : 1005,
			modal : true
		});
	});

	$(function() {
		$("#POPUP_MUDANCA").dialog({
			autoOpen : false,
			width : 1500,
			height : 1005,
			modal : true
		});
	});

	$("#POPUP_LOOKUPEVENTOMONITORAMENTO").dialog({
		title: i18n_message('baseConhecimento.pesquisarEventoMonitoramento'),
		autoOpen : false,
		width : 600,
		height : 750,
		modal : true,
		show: "fade",
		hide: "fade"
	});

	$('#POPUP_GRAUDEIMPORTANCIA').hide();
	$('#POPUP_EVENTOMONITCONHECIMENTO').hide();
	$('#POPUP_CONHECIMENTORELACIONADO').hide();
	ocultarBtnGravarImportancia();
	ocultarBtnGravarConhecimentoRelacionado();
	$('#idLabelTituloFAQ').hide();
	$('#idLabelConteudoFAQ').hide();
	ocultarBotaoArquivar();
	ocultarBotaoRestaurar();
	ocultarArquivado();

	onInitQuestionario();
}

function fecharSolicitacao(){
	$("#POPUP_INCIDENTE").dialog('close');
}

function LOOKUP_BASECONHECIMENTO_select(idBase, desc) {
	document.getElementById("identificadorBase").value = idBase;
		document.form.fireEvent("restore");
}

function chamarRestore(idBase){
	document.getElementById("identificadorBase").value = idBase
	docucment.form.restore();
}

function limpar(){
	document.form.fireEvent("limpar");
	document.getElementById("fraUpload_uploadAnexos").src = "";
	document.getElementById("file_uploadAnexos").value = "";
	document.getElementById("descUploadFile_uploadAnexos").value = "";
	document.form.clear();
	deleteAllRows();

	if (oFCKeditor._IsCompatibleBrowser()) {
		var oEditor = FCKeditorAPI.GetInstance( 'conteudoBaseConhecimento' ) ;
		oEditor.SetData('');
	}

	document.getElementById('titulo').readOnly = false;
	ocultarDivPublicacao();
	habilitarComboPasta();
	deleteAllRowsUsuario();
	deleteAllRowsGrupo();
	deleteAllRowsUsuarioNotificacao();
	deleteAllRowsGrupoNotificacao();
	deleteAllRowsConhecimento();
	ocultarBtnGravarImportancia();
	ocultarBtnGravarConhecimentoRelacionado();
	ocultarArquivado();
	ocultarBotaoArquivar();
	ocultarBotaoRestaurar();
}

function verificarPermissoesDeAcesso(){
	document.form.fireEvent("verificarPermissoesDeAcesso");
}

function marcaRadioButton(){
	var flag1 = document.getElementById("aprovaBaseConhecimentoTrue").checked;
	var flag2 = document.getElementById("aprovaBaseConhecimentoFalse").checked;

	if(flag1==false && flag2==false){
		document.getElementById("aprovaBaseConhecimentoFalse").checked = true;
	}
}

function excluir(){
	if(document.getElementById("identificadorBase").value != ""){
		if (confirm(i18n_message("citcorpore.comum.deleta"))) {
			document.form.fireEvent("excluir");
		}
	}
}

$(function() {
	$('.datepicker').datepicker();
});

function restoreRow() {
	var tabela = document.getElementById('tabelaComentarios');
	var lastRow = tabela.rows.length;

	var row = tabela.insertRow(lastRow);
	countComentario++;

	var coluna = row.insertCell(0);
	coluna.innerHTML = '<img id="imgExcluiComentario' + countComentario	+ '" style="cursor: pointer;" title="i18n_message("citcorpore.comum.excluirComentario")" src="'+ctx+'/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaComentarios\', this.parentNode.parentNode.rowIndex);">';

	coluna = row.insertCell(1);
	coluna.innerHTML = '<input type="hidden" id="idComentario' + countComentario + '" name="idComentario"/><input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="nome' + countComentario + '" name="nome"/>';

	coluna = row.insertCell(2);
	coluna.innerHTML = '<input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="email' + countComentario + '" name="email"/>';

	coluna = row.insertCell(3);
	coluna.innerHTML = '<textarea style="width: 100%; border: 0 none;" readonly="readonly" id="comentario' + countComentario + '" name="comentario"/>';

	coluna = row.insertCell(4);
	coluna.innerHTML =  '<input class="star" type="radio" id="nota1' + countComentario + '" name="nota' + countComentario + '" value="1" disabled="disabled" /> 1' +
						'<input class="star" type="radio" id="nota2' + countComentario + '" name="nota' + countComentario + '" value="2" disabled="disabled" /> 2' +
					  	'<input class="star" type="radio" id="nota3' + countComentario + '" name="nota' + countComentario + '" value="3" disabled="disabled" /> 3' +
						'<input class="star" type="radio" id="nota4' + countComentario + '" name="nota' + countComentario + '" value="4" disabled="disabled" /> 4' +
						'<input class="star" type="radio" id="nota5' + countComentario + '" name="nota' + countComentario + '" value="5" disabled="disabled" /> 5';
}

var seqSelecionada = '';
function setRestoreComentario(idComentario, comentario, nome, email, nota, dataInicio) {
	if (seqSelecionada != '') {
		eval('document.form.idComentario' + seqSelecionada + '.value = "' + idComentario + '"');
		eval('document.form.comentario' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + comentario + '\')');
		eval('document.form.nome' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + nome + '\')');
		eval('document.form.email' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + email + '\')');

		if (ObjectUtils.decodificaEnter(nota) == 1){
			document.getElementById('nota1' + seqSelecionada).checked = true;
		}
		if (ObjectUtils.decodificaEnter(nota) == 2){
			document.getElementById('nota2' + seqSelecionada).checked = true;
		}
		if (ObjectUtils.decodificaEnter(nota) == 3){
			document.getElementById('nota3' + seqSelecionada).checked = true;
		}
		if (ObjectUtils.decodificaEnter(nota) == 4){
			document.getElementById('nota4' + seqSelecionada).checked = true;
		}
		if (ObjectUtils.decodificaEnter(nota) == 5){
			document.getElementById('nota5' + seqSelecionada).checked = true;
			document.getElementById('nota5' + seqSelecionada).class = "star";
		}
	}
	exibeGrid();
}

function removeLinhaTabela(idTabela, rowIndex) {
	if (confirm(i18n_message("citcorpore.comum.deleta"))) {
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
	ocultarGridComentario();
}

function gravar() {
	aguarde();
	serializaUsuario();
	serializaGrupo();
	serializaConhecimentoRelacionado();
	serializaUsuarioNotificacao();
	serializaGrupoNotificacao();
	serializaEventoMonitoramento();
	if ($("#comboPasta").val() == 0){
		alert(i18n_message("pasta.selecione"));
		fechar_aguarde();
	} else{
		var tituloNotificacao = $('#notificacaoTitulo').val()
		$('#tituloNotificacao').val(tituloNotificacao);
		var tipoNotificacao = $('#tipo').val();
		$('#tipoNotificacao').val(tipoNotificacao);

		if (oFCKeditor._IsCompatibleBrowser()) {
			var oEditor = FCKeditorAPI.GetInstance( 'conteudoBaseConhecimento' ) ;
			document.form.conteudoBaseConhecimento.value = oEditor.GetXHTML();
		}

		document.form.save();
	}
}

function bloquearTitulo(){
	document.getElementById('titulo').readOnly = true;
}

function liberarTitulo(){
	document.getElementById('titulo').readOnly = false;
}

function desabilitarComboPasta(){
	$('#comboPasta').attr('disabled', true);
}

function habilitarComboPasta(){
	$('#comboPasta').attr('disabled', false);
}

function exibeGrid() {
	document.getElementById('gridComentario').style.display = 'block';
}

function ocultarGridComentario() {
	document.getElementById('gridComentario').style.display = 'none';
}

function ocultarDivPublicacao(){
	$('#publicacao').hide();
}

function ocultarBotoes(){
	$('#btnGravar').hide();
	$('#btnExcluir').hide();
	$('#btnLimpar').hide();
	ocultarBotaoArquivar();
	ocultarBotaoRestaurar();
}

function ocultarBotaoArquivar(){
	$('#btnArquivar').hide();
}

function ocultarBotaoRestaurar(){
	$('#btnRestaurar').hide();
}

function exibirBotaoArquivar(){
	$('#btnArquivar').show();
}

function exibirBotaoRestaurar(){
	$('#btnRestaurar').show();
}

function exibirBotoes(){
	$('#btnExcluir').show();
	$('#btnGravar').show();
}

function desabilitaCamposFrame(){

	$(".col_20").children().attr("disabled","disabled");//desabilitando o campo tipo de documento(faq, erro conhecido, base conhecimento)
	$(".col_10").children().attr("disabled","disabled");//desabilitando Identificador, Notifica��es, Import�ncia, Documento Relacionado, Req Servi�o, Mudan�a, Evento Monitoramento
	$("#titulo").attr("disabled","disabled");//desabilitando t�tulo
	$("#fonteReferencia").attr("disabled","disabled");
	$("#comboPasta").attr("disabled","disabled");
	$("#comboOrigem").attr("disabled","disabled");
	$("#justificativaObservacao").attr("disabled","disabled");
	$("#comboPrivacidade").attr("disabled","disabled");
	$("#comboSituacao").attr("disabled","disabled");
	$("#dataExpiracao").attr("disabled","disabled");
	$(".col_33").children().attr("disabled","disabled");
	$("#file_uploadAnexos").attr("disabled","disabled");
	$("#btnAdduploadAnexos").attr("disabled","disabled");
	$("#descUploadFile_uploadAnexos").attr("disabled","disabled");
	$('#btnAdduploadAnexos').attr('style','display:none');

}

function abrirPopupNotificacao(){
	$('#POPUP_NOTIFICACAO').dialog("open");
}

	function adicionarUsuario(isNotificacao) {
		if (isNotificacao == true){
			$('#isNotificacao').val(true);
		} else{
			if (isNotificacao == false){
				$('#isNotificacao').val(false);
			}
		}

	$("#POPUP_USUARIO").dialog("open");
}

function adicionarGrupo (isNotificacao) {
	if (isNotificacao == true){
		$('#isNotificacaoGrupo').val(true);
	} else{
		if (isNotificacao == false){
			$('#isNotificacaoGrupo').val(false);
		}
	}
	$("#POPUP_GRUPO").dialog("open");
}

	function adicionarConhecimentoRelacionado() {
	$("#POPUP_CONHECIMENTO").dialog("open");
}

function abrirPopupImportanciaConhecimento(){
	$('#POPUP_GRAUDEIMPORTANCIA').dialog("open");
}

function fecharPopupGrauDeImportancia(){
	$('#POPUP_GRAUDEIMPORTANCIA').dialog("close");
}

function abrirPopupEventoMonitConhecimento(){
	$('#POPUP_EVENTOMONITCONHECIMENTO').dialog("open");
}

function fecharPopupEventoMonitConhecimento(){
	$('#POPUP_EVENTOMONITCONHECIMENTO').dialog("close");
}

function abrirPopupConhecimentoRelacionado(){
	$('#POPUP_CONHECIMENTORELACIONADO').dialog("open");
}

function fecharPopupConhecimentoRelacionado(){
	$('#POPUP_CONHECIMENTORELACIONADO').dialog("close");
}

function LOOKUP_USUARIO_select(id, desc) {
	if ($('#isNotificacao').val() == "true"){
		addLinhaTabelaUsuarioNotificacao(id, desc, true);
	} else{
		addLinhaTabelaUsuario(id, desc, true);
	}
}

function LOOKUP_GRUPO_select(id, desc) {
	if($('#isNotificacaoGrupo').val()=="true"){
		addLinhaTabelaGrupoNotificacao(id, desc, true);
	}else{
		addLinhaTabelaGrupo(id, desc, true);
	}
}

function LOOKUP_CONHECIMENTO_RELACIONADO_select(id, desc) {

	if (id == $('#identificadorBase').val() ){
		alert(i18n_message("baseConhecimento.autorelacionamento"));
	} else{
		$('#idConhecimentoRelacionado').val(id);
		document.form.fireEvent("validarRelacionamentoConhecimento");
	}
}

function addLinhaTabelaUsuarioNotificacao(id, desc, valida){
	var tbl = document.getElementById('tabelaUsuarioNotificacao');
	$('#tabelaUsuarioNotificacao').show();
	$('#gridUsuarioNotificacao').show();
	var lastRow = tbl.rows.length;
	if (valida){
		if (!validaAddLinhaTabelaUsuarioNotificacao(lastRow, id)){
			return;
		}
	}
	var row = tbl.insertRow(lastRow);
	var coluna = row.insertCell(0);
	contUsuarioNotificacao++;
	coluna.innerHTML = '<img id="imgDelUsuario' + contUsuarioNotificacao + '" style="cursor: pointer;" title="i18n_message("citcorpore.comum.cliquaParaExcluir")" src="'+ctx+'/imagens/delete.png" onclick="removeLinhaTabelaUsuarioNotificacao(\'tabelaUsuarioNotificacao\', this.parentNode.parentNode.rowIndex);">';
	coluna = row.insertCell(1);
	coluna.innerHTML = desc + '<input type="hidden" id="idUsuarioNotificacao' + contUsuarioNotificacao + '" name="idUsuarioNotificacao" value="' + id + '" />';
	$("#POPUP_USUARIO").dialog("close");
}

function validaAddLinhaTabelaUsuarioNotificacao(lastRow, id){
	if (lastRow > 2){
		var arrayIdUsuario = document.getElementsByName("idUsuarioNotificacao");
		for (var i = 0; i < arrayIdUsuario.length; i++){
			if (arrayIdUsuario[i].value == id){
				alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
				return false;
			}
		}
	} else if (lastRow == 2){
		var idUsuario = document.getElementsByName("idUsuarioNotificacao");
		if (idUsuario[0].value == id){
			alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
			return false;
		}
	}
	return true;
}

function removeLinhaTabelaUsuarioNotificacao(idTabela, rowIndex) {
	if (confirm(i18n_message("citcorpore.comum.deleta"))){
		HTMLUtils.deleteRow(idTabela, rowIndex);
		var tabela = document.getElementById(idTabela);
		if (tabela.rows.length == 1){
			if (idTabela == 'tabelaUsuarioNotificacao'){
				$('#gridUsuarioNotificacao').hide();
				return;
			}
			$('#tabelaUsuarioNotificacao').hide();
		}
	}
}

function addLinhaTabelaGrupoNotificacao(id, desc, valida){
	var tbl = document.getElementById('tabelaGrupoNotificacao');
	$('#tabelaGrupoNotificacao').show();
	$('#gridGrupoNotificacao').show();
	var lastRow = tbl.rows.length;
	if (valida){
		if (!validaAddLinhaTabelaGrupoNotificacao(lastRow, id)){
			return;
		}
	}
	var row = tbl.insertRow(lastRow);
	var coluna = row.insertCell(0);
	contGrupoNotificacao++;
	coluna.innerHTML = '<img id="imgDelGrupo' + contGrupoNotificacao + '" style="cursor: pointer;" title="i18n_message("citcorpore.comum.cliquaParaExcluir")" src="'+ctx+'/imagens/delete.png" onclick="removeLinhaTabelaGrupo(\'tabelaGrupoNotificacao\', this.parentNode.parentNode.rowIndex);">';
	coluna = row.insertCell(1);
	coluna.innerHTML = desc
		+ '<input type="hidden" id="idGrupoNotificacao' + contGrupoNotificacao + '" name="idGrupoNotificacao" value="' + id + '" />';
		$("#POPUP_GRUPO").dialog("close");
}

function validaAddLinhaTabelaGrupoNotificacao(lastRow, id) {
	if (lastRow > 2) {
		var arrayIdGrupo = document.getElementsByName("idGrupoNotificacao");
		for ( var i = 0; i < arrayIdGrupo.length; i++) {
			if (arrayIdGrupo[i].value == id) {
				alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
				return false;
			}
		}
	} else if (lastRow == 2) {
		var idGrupo =  document.getElementsByName("idGrupoNotificacao");
		if (idGrupo[0].value == id) {
			alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
			return false;
		}
	}
	return true;
}

function removeLinhaTabelaGrupoNotificacao(idTabela, rowIndex) {
	if (confirm(i18n_message("citcorpore.comum.deleta"))) {
		HTMLUtils.deleteRow(idTabela, rowIndex);
		var tabela = document.getElementById(idTabela);
		if (tabela.rows.length == 1) {
			if (idTabela == 'tabelaGrupoNotificacao') {
				$('#gridGrupoNotificacao').hide();
				return;
			}
			$('#tabelaGrupoNotificacao').hide();
		}
	}
}

function addLinhaTabelaUsuario(id, desc, valida){
	var tbl = document.getElementById('tabelaUsuario');
	$('#tabelaUsuario').show();
	$('#gridUsuario').show();

	var lastRow = tbl.rows.length;

	if (valida){
		if (!validaAddLinhaTabelaUsuario(lastRow, id)){
			return;
		}
	}

	var row = tbl.insertRow(lastRow);
	var coluna = row.insertCell(0);
	contUsuario++;

	coluna.innerHTML = '<img id="imgDelUsuario' + contUsuario + '" style="cursor: pointer;" title="i18n_message("citcorpore.comum.cliquaParaExcluir")" src="'+ctx+'/imagens/delete.png" onclick="removeLinhaTabelaUsuario(\'tabelaUsuario\', this.parentNode.parentNode.rowIndex);">';

	coluna = row.insertCell(1);

	coluna.innerHTML = desc + '<input type="hidden" id="idUsuario' + contUsuario + '" name="idUsuario" value="' + id + '" />';

	coluna = row.insertCell(2);

	coluna.innerHTML = '<span style="padding-right: 30px;"><input style="margin-right: 5px;" type="radio" id="baixoUsuario' + contUsuario + '" name="grauImportanciaUsuario' + contUsuario + '" value="1"/>'+i18n_message("requisitosla.baixo")+'</span>' +
						'<span style="padding-right: 30px;"><input style="margin-right: 5px;" type="radio" id="medioUsuario' + contUsuario + '" name="grauImportanciaUsuario' + contUsuario + '" value="2" checked= checked;/>'+i18n_message("requisitosla.medio")+'</span>' +
						'<span style="padding-right: 30px;"><input style="margin-right: 5px;" type="radio" id="altoUsuario' + contUsuario + '" name="grauImportanciaUsuario' + contUsuario + '" value="3"/>'+i18n_message("requisitosla.alto")+'</span>';

	$('#POPUP_USUARIO').dialog("close");
}

function addLinhaTabelaGrupo(id, desc, valida){
	var tbl = document.getElementById('tabelaGrupo');
	$('#tabelaGrupo').show();
	$('#gridGrupo').show();

	var lastRow = tbl.rows.length;

	if (valida){
		if (!validaAddLinhaTabelaGrupo(lastRow, id)){
			return;
		}
	}

	var row = tbl.insertRow(lastRow);
	var coluna = row.insertCell(0);
	contGrupo++;

	coluna.innerHTML = '<img id="imgDelGrupo' + contGrupo + '" style="cursor: pointer;" title="i18n_message("citcorpore.comum.cliquaParaExcluir")" src="'+ctx+'/imagens/delete.png" onclick="removeLinhaTabelaGrupo(\'tabelaGrupo\', this.parentNode.parentNode.rowIndex);">';

	coluna = row.insertCell(1);

	coluna.innerHTML = desc + '<input type="hidden" id="idGrupo' + contGrupo + '" name="idGrupo" value="' + id + '" />';

	coluna = row.insertCell(2);

	coluna.innerHTML = '<span style="padding-right: 30px;"><input style="margin-right: 5px;" type="radio" id="baixoGrupo' + contGrupo + '" name="grauImportanciaGrupo' + contGrupo + '" value="1"/>'+i18n_message("requisitosla.baixo")+'</span>' +
						'<span style="padding-right: 30px;"><input style="margin-right: 5px;" type="radio" id="medioGrupo' + contGrupo + '" name="grauImportanciaGrupo' + contGrupo + '" value="2" checked= checked;/>'+i18n_message("requisitosla.medio")+'</span>' +
						'<span style="padding-right: 30px;"><input style="margin-right: 5px;" type="radio" id="altoGrupo' + contGrupo + '" name="grauImportanciaGrupo' + contGrupo + '" value="3"/>'+i18n_message("requisitosla.alto")+'</span>';

	$('#POPUP_GRUPO').dialog("close");
}

function addLinhaTabelaConhecimentoRelacionado(id, desc, valida){
	var tbl = document.getElementById('tabelaConhecimentoRelacionado');
	$('#tabelaConhecimentoRelacionado').show();
	$('#gridConhecimentoRelacionado').show();

	var lastRow = tbl.rows.length;

	if (valida){
		if (!validaAddLinhaTabelaConhecimentoRelacionado(lastRow, id)){
			return;
		}
	}

	var row = tbl.insertRow(lastRow);
	var coluna = row.insertCell(0);
	contConhecimentoRelacionado++;

	coluna.innerHTML = '<img id="imgDelConhecimento' + contConhecimentoRelacionado + '" style="cursor: pointer;" title="i18n_message("citcorpore.comum.cliquaParaExcluir")" src="'+ctx+'/imagens/delete.png" onclick="removeLinhaTabelaConhecimentoRelacionado(\'tabelaConhecimentoRelacionado\', this.parentNode.parentNode.rowIndex);">';

	coluna = row.insertCell(1);

	coluna.innerHTML = desc + '<input type="hidden" id="idConhecimento' + contConhecimentoRelacionado + '" name="idConhecimento" value="' + id + '" />';

	$('#POPUP_CONHECIMENTO').dialog("close");
}

 function validaAddLinhaTabelaUsuario(lastRow, id){
	if (lastRow > 2){
		var arrayIdUsuario = document.getElementsByName("idUsuario");
		for (var i = 0; i < arrayIdUsuario.length; i++){
			if (arrayIdUsuario[i].value == id){
				alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
				return false;
			}
		}
	} else if (lastRow == 2){
		var idUsuario = document.getElementsByName("idUsuario");
		if (idUsuario[0].value == id){
			alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
			return false;
		}
	}
	return true;
}

 function validaAddLinhaTabelaGrupo(lastRow, id){
	if (lastRow > 2){
		var arrayIdGrupo = document.getElementsByName("idGrupo");
		for (var i = 0; i < arrayIdGrupo.length; i++){
			if (arrayIdGrupo[i].value == id){
				alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
				return false;
			}
		}
	} else if (lastRow == 2){
		var idGrupo = document.getElementsByName("idGrupo");
		if (idGrupo[0].value == id){
			alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
			return false;
		}
	}
	return true;
}

function validaAddLinhaTabelaConhecimentoRelacionado(lastRow, id){
		if (lastRow > 2){
			var arrayIdConhecimento = document.getElementsByName("idConhecimento");
			for (var i = 0; i < arrayIdConhecimento.length; i++){
				if (arrayIdConhecimento[i].value == id){
					alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
					return false;
				}
			}
		} else if (lastRow == 2){
			var idConhecimento = document.getElementsByName("idConhecimento");
			if (idConhecimento[0].value == id){
				alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
				return false;
			}
		}
		return true;
}

function removeLinhaTabelaUsuario(idTabela, rowIndex) {
	if (confirm(i18n_message("citcorpore.comum.deleta"))){
		HTMLUtils.deleteRow(idTabela, rowIndex);
		var tabela = document.getElementById(idTabela);
		if (tabela.rows.length == 1){
			if (idTabela == 'tabelaUsuario'){
				$('#gridUsuario').hide();
				return;
			}
			$('#tabelaUsuario').hide();
		}
	}
}

function removeLinhaTabelaGrupo(idTabela, rowIndex) {
	if (confirm(i18n_message("citcorpore.comum.deleta"))){
		HTMLUtils.deleteRow(idTabela, rowIndex);
		var tabela = document.getElementById(idTabela);
		if (tabela.rows.length == 1){
			if (idTabela == 'tabelaGrupo'){
				$('#gridGrupo').hide();
				return;
			}
			$('#tabelaGrupo').hide();
		}
	}
}

function removeLinhaTabelaConhecimentoRelacionado(idTabela, rowIndex) {
	if (confirm(i18n_message("citcorpore.comum.deleta"))){
		HTMLUtils.deleteRow(idTabela, rowIndex);
		var tabela = document.getElementById(idTabela);
		if (tabela.rows.length == 1){
			if (idTabela == 'tabelaConhecimentoRelacionado'){
				$('#gridConhecimentoRelacionado').hide();
				return;
			}
			$('#tabelaConhecimentoRelacionado').hide();
		}
	}
}

function ImportanciaConhecimentoUsuarioDTO(idBaseConhecimento, _id, grau){
	this.idBaseConhecimento = idBaseConhecimento;
	this.idUsuario = _id;
	this.grauImportanciaUsuario = grau;
}

function ImportanciaConhecimentoGrupoDTO(idBaseConhecimento, _id, grau){
	this.idBaseConhecimento = idBaseConhecimento;
	this.idGrupo = _id;
	this.grauImportanciaGrupo = grau;
}

function serializaUsuario() {
	var tabela = document.getElementById('tabelaUsuario');
	var count = contUsuario + 1;
	var listUsuario = [];

	var idBaseConhecimento = $('#identificadorBase').val();

	for ( var i = 1; i < count; i++) {
		if (document.getElementById('idUsuario' + i) != "" && document.getElementById('idUsuario' + i) != null) {
			var idUsuario = document.getElementById('idUsuario' + i).value;
			var grauImportancia;

			if ($('#baixoUsuario' + i).is(":checked")){
				grauImportancia = "1";
			} else{
				if ($('#medioUsuario' + i).is(":checked")){
					grauImportancia = "2";
				} else{
					if ($('#altoUsuario' + i).is(":checked")){
						grauImportancia = "3";
					}
				}
			}
			var usuario = new ImportanciaConhecimentoUsuarioDTO(idBaseConhecimento, idUsuario, grauImportancia);
			listUsuario.push(usuario);
		}
	}
	document.form.listImportanciaConhecimentoUsuarioSerializado.value = ObjectUtils.serializeObjects(listUsuario);
}

function serializaGrupo() {
	var tabela = document.getElementById('tabelaGrupo');
	var count = contGrupo + 1;
	var listGrupo = [];

	var idBaseConhecimento = $('#identificadorBase').val();

	for ( var i = 1; i < count; i++) {
		if (document.getElementById('idGrupo' + i) != "" && document.getElementById('idGrupo' + i) != null) {
			var idGrupo = document.getElementById('idGrupo' + i).value;
			var grauImportancia;

			if ($('#baixoGrupo' + i).is(":checked")){
				grauImportancia = "1";
			} else{
				if ($('#medioGrupo' + i).is(":checked")){
					grauImportancia = "2";
				} else{
					if ($('#altoGrupo' + i).is(":checked")){
						grauImportancia = "3";
					}
				}
			}
			var grupo = new ImportanciaConhecimentoGrupoDTO(idBaseConhecimento, idGrupo, grauImportancia);
			listGrupo.push(grupo);
		}
	}
	document.form.listImportanciaConhecimentoGrupoSerializado.value = ObjectUtils.serializeObjects(listGrupo);
}

function BaseConhecimentoRelacionadoDTO(idBaseConhecimento, _id){
	this.idBaseConhecimento = idBaseConhecimento;
	this.idBaseConhecimentoRelacionado = _id;
}

function serializaConhecimentoRelacionado() {
	var tabela = document.getElementById('tabelaConhecimentoRelacionado');
	var count = contConhecimentoRelacionado + 1;
	var listConhecimentoRelacionado = [];

	var idBaseConhecimento = $('#identificadorBase').val();

	for ( var i = 1; i < count; i++) {
		if (document.getElementById('idConhecimento' + i) != "" && document.getElementById('idConhecimento' + i) != null) {
			var idConhecimentoRelacionado = document.getElementById('idConhecimento' + i).value;

			var baseConhecimentoRelacionado = new BaseConhecimentoRelacionadoDTO(idBaseConhecimento, idConhecimentoRelacionado);
			listConhecimentoRelacionado.push(baseConhecimentoRelacionado);
		}
	}
	document.form.listConhecimentoRelacionadoSerializado.value = ObjectUtils.serializeObjects(listConhecimentoRelacionado);
}

function deleteAllRowsUsuario() {
	var tabela = document.getElementById('tabelaUsuario');
	var count = tabela.rows.length;

	while (count > 1) {
		tabela.deleteRow(count - 1);
		count--;
	}
	$('#gridUsuario').hide();
}

function deleteAllRowsUsuarioNotificacao() {
	var tabela = document.getElementById('tabelaUsuarioNotificacao');
	var count = tabela.rows.length;

	while (count > 1) {
		tabela.deleteRow(count - 1);
		count--;
	}
	$('#gridUsuarioNotificacao').hide();
}

function deleteAllRowsGrupo() {
	var tabela = document.getElementById('tabelaGrupo');
	var count = tabela.rows.length;

	while (count > 1) {
		tabela.deleteRow(count - 1);
		count--;
	}
	$('#gridGrupo').hide();
}

function deleteAllRowsGrupoNotificacao() {
	var tabela = document.getElementById('tabelaGrupoNotificacao');
	var count = tabela.rows.length;

	while (count > 1) {
		tabela.deleteRow(count - 1);
		count--;
	}
	$('#gridGrupoNotificacao').hide();
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

function deleteAllRowsConhecimento() {
	var tabela = document.getElementById('tabelaConhecimentoRelacionado');
	var count = tabela.rows.length;

	while (count > 1) {
		tabela.deleteRow(count - 1);
		count--;
	}
	$('#gridConhecimentoRelacionado').hide();
}

function atribuirCheckedUsuario(grauImportanciaUsuario){
	if (grauImportanciaUsuario == "1"){
		$('#baixoUsuario' + contUsuario).attr('checked', true);
	} else{
		if (grauImportanciaUsuario == "2"){
			$('#medioUsuario' + contUsuario).attr('checked', true);
		} else{
			$('#altoUsuario' + contUsuario).attr('checked', true);
		}
	}
}

function atribuirCheckedGrupo(grauImportanciaGrupo){
	if (grauImportanciaGrupo == "1"){
		$('#baixoGrupo' + contGrupo).attr('checked', true);
	} else{
		if (grauImportanciaGrupo == "2"){
			$('#medioGrupo' + contGrupo).attr('checked', true);
		} else{
			$('#altoGrupo' + contGrupo).attr('checked', true);
		}
	}
}

function gravarImportanciaConhecimento(){
	serializaUsuario();
	serializaGrupo();
	document.form.fireEvent("gravarImportanciaConhecimento");
}

function gravarConhecimentoRelacionado(){
	serializaConhecimentoRelacionado();
	document.form.fireEvent("gravarConhecimentoRelacionado");
}

function gravarEventoMonitConhecimento (){
	serializaEventoMonitoramento();
	document.form.fireEvent("gravarEventoMonitConhecimento");
}

function ocultarBtnGravarImportancia(){
	$('#btnGravarImportancia').hide();
}

function exibirBtnGravarImportancia(){
	$('#btnGravarImportancia').show();
}

function ocultarBtnGravarConhecimentoRelacionado(){
	$('#btnGravarConhecimentoRelacionado').hide();
}

function exibirBtnGravarConhecimentoRelacionado(){
	$('#btnGravarConhecimentoRelacionado').show();
}

function NotificacaoUsuarioDTO(_id, i) {
	this.idUsuario = _id;
}

function serializaUsuarioNotificacao() {
	var tabela = document.getElementById('tabelaUsuarioNotificacao');
	var count = contUsuarioNotificacao + 1;
	var listaDeUsuario = [];
	for ( var i = 1; i < count; i++) {
		if (document.getElementById('idUsuarioNotificacao' + i) != ""
				&& document.getElementById('idUsuarioNotificacao' + i) != null) {
			var trObj = document.getElementById('idUsuarioNotificacao' + i).value;
			var usuario = new NotificacaoUsuarioDTO(trObj, i);
			listaDeUsuario.push(usuario);
		}
	}
	var serializaUsuario = ObjectUtils.serializeObjects(listaDeUsuario);
	document.form.listUsuariosNotificacaoSerializados.value = serializaUsuario;
}

function NotificacaoGrupoDTO(_id, i) {
	this.idGrupo = _id;
}

function serializaGrupoNotificacao() {
	var tabela = document.getElementById('tabelaGrupoNotificacao');
	var count = contGrupoNotificacao + 1;
	var listaDeGrupo = [];
	for ( var i = 1; i < count; i++) {
		if (document.getElementById('idGrupoNotificacao' + i) != ""
				&& document.getElementById('idGrupoNotificacao' + i) != null) {
			var trObj = document.getElementById('idGrupoNotificacao' + i).value;
			var grupo = new NotificacaoGrupoDTO(trObj, i);
			listaDeGrupo.push(grupo);
		}
	}
	var serializaGrupo = ObjectUtils.serializeObjects(listaDeGrupo);
	document.form.listGruposNotificacaoSerializados.value = serializaGrupo;
}

function setarValoresPopupNotificacao() {
	$('#notificacaoTitulo').val($('#tituloNotificacao').val());
	var tipoNotificacao = $('#tipoNotificacao').val();
	$('#tipo').val(tipoNotificacao);
}

function fecharNotificacao(){
	$("#POPUP_NOTIFICACAO").dialog("close");
}

function gravarNotificacao() {
	var tituloNotificacao = $('#notificacaoTitulo').val()
	$('#tituloNotificacao').val(tituloNotificacao);
	var tipoNotificacao = $('#tipo').val();
	$('#tipoNotificacao').val(tipoNotificacao);
	serializaUsuarioNotificacao();
	serializaGrupoNotificacao();
	document.form.fireEvent("gravarNotificacao");
}

function tamanhoCampo(obj, limit) {
		if (obj.value.length >= limit) {
			obj.value = obj.value.substring(0, limit-1);
		}
	}

function habilitarPergunta(){
	if ($('#faq').is(":checked")){
		$('#idLabelTituloFAQ').show();
		$('#idLabelConteudoFAQ').show();
		$('#idLabelTitulo').hide();
		$('#idLabelConteudo').hide();
		ocultarAnexos();
	}else{
		$('#idLabelTituloFAQ').hide();
		$('#idLabelConteudoFAQ').hide();
		$('#idLabelTitulo').show();
		$('#idLabelConteudo').show();
		exibirAnexos();
	}
}

function ocultarAnexos(){
	$('#formularioDeAnexos').hide();
}

function exibirAnexos(){
	$('#formularioDeAnexos').show();
}

function ckeckarFaq(){
	$('#faq').attr('checked', true);
}

aguarde = function(){
	JANELA_AGUARDE_MENU.show();
}

fechar_aguarde = function(){
	JANELA_AGUARDE_MENU.hide();
}

function arquivar(){
	document.form.fireEvent('arquivarConhecimento');
}

function restaurar(){
	document.form.fireEvent('restaurarConhecimento');
}

function exibirArquivado(){
	$('#idArquivado').show();
}

function ocultarArquivado(){
	$('#idArquivado').hide();
}

function gravarIncidentesRequisicao(idBaseConhecimento){
	if(idBaseConhecimento=="" ||idBaseConhecimento == null ){
		alert(i18n_message("baseConhecimento.validacao.selecionebase"));
		return;
	}

	var id =  parseInt(idBaseConhecimento);

	var pagina = PAGE_CADADTRO_SOLICITACAOSERVICO;

	if(pagina == null || pagina==" "){
		document.getElementById('iframeSolicitacaoServico').src ='${ctx}/pages/solicitacaoServico/solicitacaoServico.load?id=' + id;
	}else{
		document.getElementById('iframeSolicitacaoServico').src =ctx+'/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?id=' + id;
	}

	$("#POPUP_INCIDENTE").dialog("open");
}

function gravarGestaoMudanca(idBaseConhecimento){
	if(idBaseConhecimento=="" ||idBaseConhecimento == null ){
		alert(i18n_message("baseConhecimento.validacao.selecionebase"));
		return;
	}

	var id =  parseInt(idBaseConhecimento);

	document.getElementById('iframeMudanca').src =ctx+'/pages/requisicaoMudanca/requisicaoMudanca.load?idBaseConhecimento=' + id;

	$("#POPUP_MUDANCA").dialog("open");
}

function fecharMudanca(){
	$("#POPUP_MUDANCA").dialog('close');
}

function adicionarEvento() {
	$("#POPUP_LOOKUPEVENTOMONITORAMENTO").dialog("open");
}

function LOOKUP_EVENTO_MONITORAMENTO_select(id, desc) {
	addLinhaTabelaEvento(id,desc,true);
}

function addLinhaTabelaEvento(id, desc, valida){
	var tbl = document.getElementById('tabelaEvento');
	var descricao = desc.split("-");
	$('#tabelaEvento').show();
	$('#gridEvento').show();

	var lastRow = tbl.rows.length;

	if (valida){
		if (!validaAddLinhaTabelaEvento(lastRow, id)){
			return;
		}
	}

	var row = tbl.insertRow(lastRow);
	var coluna = row.insertCell(0);
	contEvento++;

	coluna.innerHTML = '<img id="imgDelEvento' + contEvento + '" style="cursor: pointer;" title="i18n_message("citcorpore.comum.cliquaParaExcluir")" src="'+ctx+'/imagens/delete.png" onclick="removeLinhaTabelaEventoMonitoramento(\'tabelaEvento\', this.parentNode.parentNode.rowIndex);">';

	coluna = row.insertCell(1);

	coluna.innerHTML = descricao[0] + '<input type="hidden" id="idEventoMonitoramento' + contEvento + '" name="idEventoMonitoramento" value="' + id + '" />';

	coluna = row.insertCell(2);

	coluna.innerHTML = descricao[1] ;

	coluna = row.insertCell(3);

	coluna.innerHTML = descricao[4] + "/" + descricao[3] + "/" + descricao[2];

	$('#POPUP_LOOKUPEVENTOMONITORAMENTO').dialog("close");

}

function validaAddLinhaTabelaEvento(lastRow, id) {
	if (lastRow > 2) {
		var arrayIdEvento = document.getElementsByName("idEventoMonitoramento");
		for ( var i = 0; i < arrayIdEvento.length; i++) {
			if (arrayIdEvento[i].value == id) {
				alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
				return false;
			}
		}
	} else if (lastRow == 2) {
		var idEventoMonitoramento =  document.getElementsByName("idEventoMonitoramento");
		if (idEventoMonitoramento[0].value == id) {
			alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
			return false;
		}
	}
	return true;
}

function limpaTabelaEventoMonitoramento() {
	var tabela = document.getElementById('tabelaEvento');
	var count = tabela.rows.length;

	while (count > 1) {
		tabela.deleteRow(count - 1);
		count--;
	}
}

function removeLinhaTabelaEventoMonitoramento(idTabela, rowIndex) {
	if (confirm(i18n_message("citcorpore.comum.deleta"))) {
		HTMLUtils.deleteRow(idTabela, rowIndex);
		var tabela = document.getElementById(idTabela);
		if (tabela.rows.length == 1) {
			if (idTabela == 'tabelaEvento') {
				$('#gridEvento').hide();
				return;
			}
			$('#tabelaEvento').hide();
		}
	}
}

function EventoMonitConhecimentoDTO(_id, i) {
	this.idEventoMonitoramento = _id;
}

function serializaEventoMonitoramento() {
	 var tabela = document.getElementById('tabelaEvento');
	var count = contEvento + 1;
	var listaDeEventoMonitoramento = [];
	for ( var i = 1; i < count; i++) {
		if (document.getElementById('idEventoMonitoramento' + i) != ""
				&& document.getElementById('idEventoMonitoramento' + i) != null) {
			var trObj = document.getElementById('idEventoMonitoramento' + i).value;
			var eventoMonitoramento = new EventoMonitConhecimentoDTO(trObj, i);
			listaDeEventoMonitoramento.push(eventoMonitoramento);
		}
	}
	var serializaEventoMonitoramento = ObjectUtils.serializeObjects(listaDeEventoMonitoramento);
	document.form.listEventoMonitoramentoSerializado.value = serializaEventoMonitoramento;
}

function deleteAllRowsEventoMonitoramento() {
	var tabela = document.getElementById('tabelaEvento');
	var count = tabela.rows.length;

	while (count > 1) {
		tabela.deleteRow(count - 1);
		count--;
	}
	$('#gridEvento').hide();
}

function validaFaq(){
	$('#faq').attr('checked',false);
	$('#documento').attr('checked',false);
	$('#erroConhecido').attr('checked',true);
	habilitarPergunta();
}

function validaErroConhecido(){
	$('#erroConhecido').attr('checked',false);
	$('#documento').attr('checked',false);
	$('#faq').attr('checked',true);
}

function validaDocumento(){
	$('#faq').attr('checked',false);
	$('#erroConhecido').attr('checked',false);
	$('#documento').attr('checked',true);
	habilitarPergunta();
}

function fechar(){
	if(iframe != null){
		if(typeof parent.fecharBaseConhecimento == 'function')
			parent.fecharBaseConhecimento();
	}else{
		return;
	}
}

function exibirAbaPesquisa(){
	if(document.form.idBaseConhecimento.value === null || document.form.idBaseConhecimento.value === undefined ||
			document.form.idBaseConhecimento.value === "")
		document.getElementById("tabs-2").style.display = "block";
}

function setDataEditor(){
	if (oFCKeditor._IsCompatibleBrowser()) {
		var oEditor = FCKeditorAPI.GetInstance( 'conteudoBaseConhecimento' );
		oEditor.SetData(document.form.conteudoBaseConhecimento.value);
	}
}
