
/**Autocompletes **/
var completeDescricao;
var completeCertificacaoRA;
var completeCertificacaoRF;
var cursoRA;
var cursoRF;
var competenciaTecnica;
var comportamento;

/** Inicialização da tela **/
$(document).ready(function() {
	
	completeDescricao = $('#descricaoPerspectivaComplexidade').autocomplete({ 
		serviceUrl:'pages/autoCompleteDescricaoAtribuicaoResponsabilidade/autoCompleteDescricaoAtribuicaoResponsabilidade.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idDescricao').val(data);
			$('#descricaoPerspectivaComplexidade').val(value);
		}
	});
	
	completeCertificacaoRA = $('#CertificacaoRA').autocomplete({ 
		serviceUrl:'pages/autoCompleteCertificacao/autoCompleteCertificacao.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idCertificacaoRA').val(data);
			$('#CertificacaoRA').val(value);
		}
	});
	
	completeCertificacaoRF = $('#CertificacaoRF').autocomplete({ 
		serviceUrl:'pages/autoCompleteCertificacao/autoCompleteCertificacao.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idCertificacaoRF').val(data);
			$('#CertificacaoRF').val(value);
		}
	});
	
	cursoRA = $('#cursoRA').autocomplete({ 
		serviceUrl:'pages/autoCompleteCurso/autoCompleteCurso.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idCursoRA').val(data);
			$('#cursoRA').val(value);
		}
	});
	
	cursoRF = $('#cursoRF').autocomplete({ 
		serviceUrl:'pages/autoCompleteCurso/autoCompleteCurso.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idCursoRF').val(data);
			$('#cursoRF').val(value);
		}
	});
	
	competenciaTecnica = $('#competencia').autocomplete({ 
		serviceUrl:'pages/autoCompleteCompetenciaTecnica/autoCompleteCompetenciaTecnica.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idCompetenciaTecnica').val(data);
			$('#competencia').val(value);
		}
	});
	
	comportamento = $('#comportamento').autocomplete({ 
		serviceUrl:'pages/autoCompleteAtitudeIndividual/autoCompleteAtitudeIndividual.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idComportamento').val(data);
			$('#comportamento').val(value);
		}
	});
	
});

function gravar() {
	var itens = HTMLUtils.getObjectsByTableId('tblResponsabilidades');
    document.form.colResponsabilidades_Serialize.value = ObjectUtils.serializeObjects(itens);
    
    itens = HTMLUtils.getObjectsByTableId('tblCertificacoesRA');
    document.form.colCertificacoes_Serialize.value = ObjectUtils.serializeObjects(itens);
    
    itens = HTMLUtils.getObjectsByTableId('tblCursosRA');
    document.form.colCursos_Serialize.value = ObjectUtils.serializeObjects(itens);
    
    itens = HTMLUtils.getObjectsByTableId('tblCertificacoesRF');
    document.form.colCertificacoesRF_Serialize.value = ObjectUtils.serializeObjects(itens);
    
    itens = HTMLUtils.getObjectsByTableId('tblCursoRF');
    document.form.colCursosRF_Serialize.value = ObjectUtils.serializeObjects(itens);
    
    itens = HTMLUtils.getObjectsByTableId('tblCompetencias');
    document.form.colCompetencias_Serialize.value = ObjectUtils.serializeObjects(itens);

    itens = HTMLUtils.getObjectsByTableId('tblPerspectivaComportamental');
    document.form.colPerspectivaComportamental_Serialize.value = ObjectUtils.serializeObjects(itens);
	
	document.form.save();
}

function gravarManualFuncaoComplexidade() {
	document.form.fireEvent('gravarManualFuncaoComplexidade');
}

function gravarCompetenciaTecniva() {
	document.form.fireEvent('gravarCompetenciaTecniva');
}

function gravarComportamento() {
	document.form.fireEvent('gravarComportamento');
}

function adicionarDescricaoAtribuicaoResponsabilidade() {
	var obj = new CIT_AtribuicaoResponsabilidadeDTO();
	obj.descricaoPerspectivaComplexidade = $("#descricaoPerspectivaComplexidade").val();
	obj.idNivel = $('#idNivel').val();

	if((obj.descricaoPerspectivaComplexidade == null || obj.idNivel == null) || (obj.descricaoPerspectivaComplexidade == "" || obj.idNivel == ""))
		alert(i18n_message("citcorpore.comum.validacao.descricao"));
	else  
		HTMLUtils.addRow('tblResponsabilidades', null, '', obj, [ 'descricaoPerspectivaComplexidade', 'idNivel', '' ], [ 'descricaoPerspectivaComplexidade' ], i18n_message("citcorpore.comum.registroJaAdicionado"), [ gerarButtonDeleteResponsabilidade ], null, null, false);
	
	$('#descricaoPerspectivaComplexidade').val("");
	$('#idNivel').val("");
	
	$('#descricaoPerspectivaComplexidade').focus();
	
}

function adicionaCertificacao() {
	var obj = new CIT_CertificacaoDTO();
	obj.descricao = $("#CertificacaoRA").val();

	if((obj.descricao == null || obj.descricao == ""))
		alert(i18n_message("citcorpore.comum.validacao.descricao"));
	else  
		HTMLUtils.addRow('tblCertificacoesRA', null, '', obj, [ 'descricao', '' ], [ 'descricao' ], i18n_message("citcorpore.comum.registroJaAdicionado"), [ gerarButtonDeleteCertificacao ], null, null, false);
	
	$('#CertificacaoRA').val("");
	
	$('#CertificacaoRA').focus();
	
}

function adicionarCurso() {
	var obj = new CIT_CursoDTO();
	obj.descricao = $("#cursoRA").val();

	if((obj.descricao == null || obj.descricao == ""))
		alert(i18n_message("citcorpore.comum.validacao.descricao"));
	else  
		HTMLUtils.addRow('tblCursosRA', null, '', obj, [ 'descricao', '' ], [ 'descricao' ], i18n_message("citcorpore.comum.registroJaAdicionado"), [ gerarButtonDeleteCurso ], null, null, false);
	
	$('#cursoRA').val("");
	
	$('#cursoRA').focus();
	
}

function adicionaCertificacaoRF() {
	var obj = new CIT_CertificacaoDTO();
	obj.descricao = $("#CertificacaoRF").val();

	if((obj.descricao == null || obj.descricao == ""))
		alert(i18n_message("citcorpore.comum.validacao.descricao"));
	else  
		HTMLUtils.addRow('tblCertificacoesRF', null, '', obj, [ 'descricao', '' ], [ 'descricao' ], i18n_message("citcorpore.comum.registroJaAdicionado"), [ gerarButtonDeleteCertificacaoRF ], null, null, false);
	
	$('#CertificacaoRF').val("");
	
	$('#CertificacaoRF').focus();
	
}

function adicionaCursoRF() {
	var obj = new CIT_CursoDTO();
	obj.descricao = $("#cursoRF").val();

	if((obj.descricao == null || obj.descricao == ""))
		alert(i18n_message("citcorpore.comum.validacao.descricao"));
	else  
		HTMLUtils.addRow('tblCursoRF', null, '', obj, [ 'descricao', '' ], [ 'descricao' ], i18n_message("citcorpore.comum.registroJaAdicionado"), [ gerarButtonDeleteCursoRF ], null, null, false);
	
	$('#cursoRF').val("");
	
	$('#cursoRF').focus();
	
}

function adicionaCompetencia() {
	var obj = new CIT_ManualCompetenciaTecnicaDTO();
	obj.idCompetenciaTecnica = $("#idCompetenciaTecnica").val();
	obj.idNivelCompetenciaAcesso = $("#idNivelCompetenciaAcesso").val();
	obj.idNivelCompetenciaFuncao = $("#idNivelCompetenciaFuncao").val();
	
	var idAcesso = parseInt(obj.idNivelCompetenciaAcesso);
	switch(idAcesso)
	{
	case 1:
		obj.nomeCompetenciaAcesso = "Não tem conhecimento"
		break;
	case 2:
		obj.nomeCompetenciaAcesso = "Tem Conhecimento"
		break;
	case 3:
		obj.nomeCompetenciaAcesso = "Tem Conhecimento e Prática em Nivel Básico"
		break;
	case 4:
		obj.nomeCompetenciaAcesso = "Tem Conhecimento e Prática em Nivel Intermediário"
		break;
	case 5:
		obj.nomeCompetenciaAcesso = "Tem Conhecimento e Prática em Nivel Avançado"
		break;
	case 6:
		obj.nomeCompetenciaAcesso = "É multiplicador"
		break;
	default:
	}
	
	var idFuncao = parseInt(obj.idNivelCompetenciaFuncao);
	switch(idFuncao)
	{
	case 1:
		obj.nomeCompetenciaFuncao = "Não tem conhecimento"
		break;
	case 2:
		obj.nomeCompetenciaFuncao = "Tem Conhecimento"
		break;
	case 3:
		obj.nomeCompetenciaFuncao = "Tem Conhecimento e Prática em Nivel Básico"
		break;
	case 4:
		obj.nomeCompetenciaFuncao = "Tem Conhecimento e Prática em Nivel Intermediário"
		break;
	case 5:
		obj.nomeCompetenciaFuncao = "Tem Conhecimento e Prática em Nivel Avançado"
		break;
	case 6:
		obj.nomeCompetenciaFuncao = "É multiplicador"
		break;
	default:
	}
	
	obj.nomeCompetenciaTecnica = $('#competencia').val();

	if((obj.idCompetenciaTecnica == null || obj.idNivelCompetenciaAcesso == null || obj.idNivelCompetenciaFuncao == null
			|| obj.idCompetenciaTecnica == "" || obj.idNivelCompetenciaAcesso == "" || obj.idNivelCompetenciaFuncao == ""))
		alert(i18n_message("acordoNivelServico.informarCampos"));
	else { 
		HTMLUtils.addRow('tblCompetencias', null, '', obj, [ 'nomeCompetenciaTecnica','nomeCompetenciaAcesso','nomeCompetenciaFuncao', '' ], [ 'idCompetenciaTecnica' ], i18n_message("citcorpore.comum.registroJaAdicionado"), [ gerarButtonDeleteCompetencia ], null, null, false);
		$('#idCompetenciaTecnica').val("");
		$('#idCompetenciaAcesso').val("");
		$('#idCompetenciaFuncao').val("");
	}
	
	$('#competencia').focus();
}

function addCompetenciaJava(lista) {
	arrayServicoContrato = ObjectUtils.deserializeCollectionFromStringSemQuebraEnter(lista); 
	for (var int = 0; int < arrayServicoContrato.length; int++) {
		
		var array_element = arrayServicoContrato[int];
		
		var obj = new CIT_ManualCompetenciaTecnicaDTO();
		obj.idCompetenciaTecnica = array_element.idCompetenciaTecnica;
		obj.idNivelCompetenciaAcesso =  array_element.idNivelCompetenciaAcesso;
		obj.idNivelCompetenciaFuncao =  array_element.idNivelCompetenciaFuncao;
		obj.nomeCompetenciaAcesso = array_element.nomeCompetenciaAcesso;
		obj.nomeCompetenciaTecnica = array_element.nomeCompetenciaTecnica;
		obj.nomeCompetenciaFuncao = array_element.nomeCompetenciaFuncao;
		
		HTMLUtils.addRow('tblCompetencias', null, '', obj, [ 'nomeCompetenciaTecnica','nomeCompetenciaAcesso','nomeCompetenciaFuncao', '' ], [ 'idCompetenciaTecnica' ], i18n_message("citcorpore.comum.registroJaAdicionado"), [ gerarButtonDeleteCompetencia ], null, null, false);

	}	
}

function adicionaPerspectivaComportamental() {
	var obj = new CIT_AtitudeIndividualDTO();
	obj.idCmbCompetenciaComportamental = $("#cmbCompetenciaComportamental").val();
	obj.comportamento = $("#comportamento").val();

	var idCompetencia = parseInt(obj.idCmbCompetenciaComportamental);
	switch(idCompetencia)
	{
	case 1:
		obj.descricaoCmbCompetenciaComportamental = "Comprometimento"
		break;
	case 2:
		obj.descricaoCmbCompetenciaComportamental = "Organizacional"
		break;
	default:
	}
	
	if((obj.idCmbCompetenciaComportamental == null || obj.idCmbCompetenciaComportamental == "" 
		|| obj.comportamento == null || obj.comportamento == ""))
		alert(i18n_message("citcorpore.comum.validacao.descricao"));
	else  
		HTMLUtils.addRow('tblPerspectivaComportamental', null, '', obj, [ 'descricaoCmbCompetenciaComportamental','comportamento', '' ], [ 'descricaoCmbCompetenciaComportamental' ], i18n_message("citcorpore.comum.registroJaAdicionado"), [ gerarButtonDeletePerspectivaComportamental ], null, null, false);
	
	$('#cmbCompetenciaComportamental').val("");
	
	$('#comportamento').focus();
	
}
function gerarButtonDeleteResponsabilidade(row, obj) {
	row.cells[2].innerHTML += '<a href="#" class="btn-action glyphicons remove_2 btn-danger titulo" onclick="removerLinhaTabelaResponsabilidade(this.parentNode.parentNode.rowIndex)"><i></i></a>        ';
}

function removerLinhaTabelaResponsabilidade(indice) {
	HTMLUtils.deleteRow('tblResponsabilidades', indice);
}
function gerarButtonVisualizaHistorico(row, obj) {
	row.cells[5].innerHTML += '<a href="#" class="btn-action glyphicons btn-primary eye_open  titulo" onclick="visualizarHistoricoVersao('+obj.idhistManualFuncao+')"><i></i></a>        ';
}
function visualizarHistoricoVersao(idhistManualFuncao) {
	document.getElementById('iframeHistoricoVersoes').src = URL_SISTEMA+"/pages/histManualFuncao/histManualFuncao.load?iframe=true&idHistManualFuncao="+idhistManualFuncao;
	$("#modal_HistoricoVersoes").modal("show");
}

function gerarButtonDeleteCertificacao(row, obj) {
	row.cells[1].innerHTML += '<a href="#" class="btn-action glyphicons remove_2 btn-danger titulo" onclick="removerLinhaTabelaCertificacao(this.parentNode.parentNode.rowIndex)"><i></i></a>        ';
}

function removerLinhaTabelaCertificacao(indice) {
	HTMLUtils.deleteRow('tblCertificacoesRA', indice);
}

function gerarButtonDeleteCurso(row, obj) {
	row.cells[1].innerHTML += '<a href="#" class="btn-action glyphicons remove_2 btn-danger titulo" onclick="removerLinhaTabelaCurso(this.parentNode.parentNode.rowIndex)"><i></i></a>        ';
}

function removerLinhaTabelaCurso(indice) {
	HTMLUtils.deleteRow('tblCursosRA', indice);
}

function gerarButtonDeleteCertificacaoRF(row, obj) {
	row.cells[1].innerHTML += '<a href="#" class="btn-action glyphicons remove_2 btn-danger titulo" onclick="removerLinhaTabelaCertificacaoRF(this.parentNode.parentNode.rowIndex)"><i></i></a>        ';
}

function removerLinhaTabelaCertificacaoRF(indice) {
	HTMLUtils.deleteRow('tblCertificacoesRF', indice);
}

function gerarButtonDeleteCursoRF(row, obj) {
	row.cells[1].innerHTML += '<a href="#" class="btn-action glyphicons remove_2 btn-danger titulo" onclick="removerLinhaTabelaCursoRF(this.parentNode.parentNode.rowIndex)"><i></i></a>        ';
}

function removerLinhaTabelaCursoRF(indice) {
	HTMLUtils.deleteRow('tblCursoRF', indice);
}

function gerarButtonDeleteCompetencia(row, obj) {
	row.cells[3].innerHTML += '<a href="#" class="btn-action glyphicons remove_2 btn-danger titulo" onclick="removerLinhaTabelaCompetencia(this.parentNode.parentNode.rowIndex)"><i></i></a>        ';
}

function removerLinhaTabelaCompetencia(indice) {
	HTMLUtils.deleteRow('tblCompetencias', indice);
}

function gerarButtonDeletePerspectivaComportamental(row, obj) {
	row.cells[2].innerHTML += '<a href="#" class="btn-action glyphicons remove_2 btn-danger titulo" onclick="removerLinhaTabelaPerspectivaComportamental(this.parentNode.parentNode.rowIndex)"><i></i></a>        ';
}

function removerLinhaTabelaPerspectivaComportamental(indice) {
	HTMLUtils.deleteRow('tblPerspectivaComportamental', indice);
}

function LOOKUP_MANUALFUNCAO_select(id, desc) {
	$('.tabsbar a[href="#tab1-3"]').tab('show');
	document.form.restore({
		idManualFuncao : id
	});
}

function LOOKUP_REQUISICAOFUNCAO_select(idRequisicao, idCargo){
	document.form.idRequisicaoFuncao.value = idRequisicao;
	document.form.fireEvent("restoreRequisicaoFuncaoCargo");
}

$(function() {
	$("#POPUP_REQUISICAOFUNCAO").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});
	
	$("#tituloFuncao").click(function() {
		$("#POPUP_REQUISICAOFUNCAO").dialog("open");
	});
});

function fecharPopup(){
	$("#POPUP_REQUISICAOFUNCAO").dialog("close");
}

addEvent(window, "load", load, false);
function load() {
	document.form.afterRestore = function() {
		$('.tabs').tabs('select', 0);

	}
}

/*
* Limpar todas as linhas das tabelas
* @autor flavio.santana
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

function limparDados() {
	var arr = ["tblResponsabilidades", "tblCertificacoesRA", "tblCursosRA", "tblCertificacoesRF", "tblCursoRF", "tblCompetencias", "tblPerspectivaComportamental","tblHistoricoVersoes"];
	limparTabelas(arr);
}

function limpar() {
	document.form.clear();
	limparDados();
}