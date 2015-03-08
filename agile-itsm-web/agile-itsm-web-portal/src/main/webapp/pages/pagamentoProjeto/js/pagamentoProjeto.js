

var objTab = null;

addEvent(window, "load", load, false);

function load() {
	document.form.afterRestore = function() {
	}
}

function selecionaProjeto(obj){
	document.form.idProjeto.value = obj.value;
	document.form.fireEvent('getInformacoes');
}

function refreshInfo(){
	$("#POPUP_EDITAR").dialog("close");
	$("#POPUP_MUDA_SIT_PAGAMENTO").dialog("close");
	document.getElementById("divTarefasParaPagamento").innerHTML = "";
	document.getElementById("divTarefasParaPagamentoVis").innerHTML = "";
	document.getElementById("divPagamentosEfetuados").innerHTML = "";
	document.form.fireEvent('getInformacoes');
}

function limpar(){
	document.getElementById("divTarefasParaPagamento").innerHTML = "";
	document.getElementById("divTarefasParaPagamentoVis").innerHTML = "";
	document.getElementById("divPagamentosEfetuados").innerHTML = "";
	document.formConsulta.clear();
	document.form.clear();
}

function gerarPagamento(){
	if (document.form.idProjeto.value == ''){
		alert(i18n_message("pagamentoProjeto.informeProjeto"));
		return;
	}
	document.form.fireEvent('getMarcosFinanceiros');
	$("#POPUP_EDITAR").dialog("open");
	//document.form.save();
}

function selecionaMarcoFin(){
	HTMLUtils.setForm(document.form);
	document.form.fireEvent('setaTarefasMarcoFinanceiro');
}

function save(){
	document.form.save();
}
function saveAtu(){
	if (document.formAtuSit.validate()){
		document.formAtuSit.fireEvent('atualizaPagamento');
	}
}
function indicarPagamento(id){
	document.formAtuSit.idPagamentoProjeto.value = id;
	$("#POPUP_MUDA_SIT_PAGAMENTO").dialog("open");
}

$(function() {
	$("#POPUP_EDITAR").dialog({
		autoOpen : false,
		width : 1100,
		height : 700,
		modal : true,
		buttons: {
			"Fechar": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	$("#POPUP_MUDA_SIT_PAGAMENTO").dialog({
		autoOpen : false,
		width : 500,
		height : 300,
		modal : true,
		buttons: {
			"Fechar": function() {
				$( this ).dialog( "close" );
			}
		}
	});
});


