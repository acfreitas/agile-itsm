$(function() {
	ativaClienteAsterisk();
});

function exibirNotificacaoAsterisk(lista){
	if(!$("#modal_Telefonia").is(':visible')) {
		document.formCtrlAsterisk.listaChamadas.value = lista;
		document.formCtrlAsterisk.fireEvent("abrePopUpAsterisk");
	}
}

function ativaClienteAsterisk() {
	if (asterisk_ativo === "S") {
		dwr.engine.setActiveReverseAjax(true);
		dwr.engine.setErrorHandler(errorHandler);
	}
}

function errorHandler(message, ex) {
	dwr.util.setValue("error", "Cannot connect to server. Initializing retry logic.", {escapeHtml:false});
}

function abreRamalTelefone(){
	document.formCtrlAsterisk.fireEvent("abreRamalTelefone");
	$('#modal_ramalTelefone').on('shown.bs.modal', function(){
		$("#ramalTelefone").focus();
	});
}

gravarRamalTelefone = function(){
	document.formCtrlAsterisk.fireEvent("gravarRamalTelefone");
}

function abrirSolicitacao(){
	var idEmpregado = $('#idEmpregado').val();
	var id =  parseInt(idEmpregado);
	/**
	* Motivo: Adicionando a url o parametro modalAsterisk=true para possivel callback
	* Autor: flavio.santana
	* Data/Hora: 11/12/2013 10:00
	**/
	$('#conteudoframeSolicitacaoServico').html("<iframe height='700' id='frameSolicitacaoServico' class='iframeSemBorda' width='100%' src ='${ctx}/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?iframe=true&modalAsterisk=true&idEmpregado="+ id + "'></iframe>");
	$("#modal_INCIDENTE").modal('show');
}

function fecharModalSolicitacaoAsterisk() {
	if($("#modal_INCIDENTE").is(':visible')) {
		$("#modal_INCIDENTE").modal('hide');
	}
}

$(document).ready(function () {
	$('input').keypress(function (e) {
		var code = null;
		code = (e.keyCode ? e.keyCode : e.which);
		return (code == 13) ? false : true;
	});
});

$(document).on('hide.bs.modal','#modal_INCIDENTE', function () {
	if($("#modal_Telefonia").is(':visible')) {
		$("#modal_Telefonia").modal('hide');
	}
});
