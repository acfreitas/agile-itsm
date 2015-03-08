addEvent(window, "load", load, false);

function load() {
	document.formGerenciamento.afterRestore = function() {
		
	}
}

/**
 * Motivo: Criando flag de atualização 
 * Autor: flavio.santana
 * Data/Hora: 13/11/2013 15:56
 */
var flagModalAtualizacao = false;

$(function(){
	
	$('.modal').on('shown', function() {
		 flagModalAtualizacao = true;
	});
	
	$('.modal').on('hidden', function () {
		 flagModalAtualizacao = false;
	});

});

function importacaoManual(idConexaoBI) {
	document.getElementById('frameImportacaoManualBI').src = '../../pages/importManualBI/importManualBI.load?iframe=true&editar=true&idConexaoBI='+idConexaoBI;
	$('#modal_ImportacaoManualBI').modal();
	
	return false;
}

function logExecucao(idConexaoBI) {
	document.getElementById('frameLogImportacaoBI').src = '../../pages/logImportacaoBI/logImportacaoBI.load?iframe=true&idConexaoBI='+idConexaoBI;
	$('#modal_LogImportacaoBI').modal();
	
	return false;
}

function agenda(idConexaoBI, abriuAgendamentoExcecao) {
	document.formGerenciamento.abriuAgendamentoExcecao.value = abriuAgendamentoExcecao;
	document.getElementById('frameAgendamentoExecucaoEspecificaBI').src = '../../pages/agendamentoExecucaoBI/agendamentoExecucaoBI.load?iframe=true&idConexaoBI='+idConexaoBI+'&abriuAgendamentoExcecao='+abriuAgendamentoExcecao;
	$('#modal_AgendamentoExecucaoEspecificaBI').modal();
	
	return false;
}

function testarConexao(idConexaoBI) {
	JANELA_AGUARDE_MENU.show();
	document.formGerenciamento.idConexaoBI.value = idConexaoBI;
	document.formGerenciamento.fireEvent('testarConexao');
	
	return false;
}

function desativarCliente(idConexaoBI, status) {
	if (confirm(i18n_message("bi.painelControle.conexao.confirmaAltStatus"))){
		document.formGerenciamento.idConexaoBI.value = idConexaoBI;
		document.formGerenciamento.status.value = status;
		document.formGerenciamento.fireEvent('alterarStatus');
	}
	
	return false;
}

function janelaAguarde(){
	JANELA_AGUARDE_MENU.show();
}

function AbrirModalNovaConexaoBI(){
	document.getElementById('frameNovaConexaoBI').src = '../../pages/cadastroConexaoBI/cadastroConexaoBI.load?iframe=true';
	$('#modal_novaConexaoBI').modal();
}

function AbrirModalEditarConexaoBI(idConexaoBI){
	document.formGerenciamento.idConexaoBI.value = idConexaoBI;
	document.getElementById('frameEditarConexaoBI').src = '../../pages/cadastroConexaoBI/cadastroConexaoBI.load?iframe=true&editar=true&idConexaoBI='+idConexaoBI;
	$('#modal_editarConexaoBI').modal();
}

function fechaJanelaAguarde(){
	JANELA_AGUARDE_MENU.hide();
}

function pesquisarItensFiltro() {
	JANELA_AGUARDE_MENU.show();
	$('#paginaSelecionada').val('1');
	document.formGerenciamento.fireEvent('pesquisarItensFiltro');
}
	
function fecharModalConexaoBI() {
	$('#modal_novaConexaoBI').modal('hide');
	$('#modal_editarConexaoBI').modal('hide');
	window.setInterval(function atualizaLista(){document.location.reload();}, 1000);
}

function fecharModalAgendamento(){
	$('#modal_AgendamentoExecucaoEspecificaBI').modal('hide');
	$('#modal_AgendamentoExecucaoExcecaoBI').modal('hide');
	window.setInterval(function atualizaLista(){document.location.reload();}, 1000);
}

function fecharModalImportacaoManual () {
	$('#modal_ImportacaoManualBI').modal('hide');
	document.location.reload();
}


function msgNomeExiste(){
	alert(i18n_message("bi.painelControle.conexao.nomeJaExiste"));
}

function msgLinkExiste(){
	alert(i18n_message("bi.painelControle.conexao.linkJaExiste"));
}

function executarAgora (idConexaoBI){
	if (confirm(i18n_message("bi.painelControle.conexao.confirmaExecucao"))){
		JANELA_AGUARDE_MENU.show();
		document.formGerenciamento.idConexaoBI.value = idConexaoBI;
		document.formGerenciamento.fireEvent('executarAgora');
	}
}

function conexaoInativaMsg(){
	alert(i18n_message("bi.painelControle.conexao.linkJaExiste"));
}

function StatusMsg(){
	alert(i18n_message("bi.painelControle.conexao.statusAlteradoSucesso"));
	document.location.reload();
}
//agendamento excecao e especifico utilizam o mesmo modal por isso o titulo do mesmo é definido no load
function setTituloModalAgendamento(titulo){
	if (titulo == "excecao"){
		$('.modal-header h3').text(i18n_message("bi.painelControle.conexao.agendamentoExcecao"));
	} else {
		$('.modal-header h3').text(i18n_message("bi.painelControle.conexao.agendamentoEspecifico"));
	}
}
