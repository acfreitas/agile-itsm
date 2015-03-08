
var objTab = null;

addEvent(window, "load", load, false);

function load() {
	document.form.afterRestore = function() {
		$('.tabs').tabs('select', 0);
	};
}

pesquisar = function() {
	JANELA_AGUARDE_MENU.show();
	document.form.idResponsavel.value = '';
	document.formDelegacao.clear();
	document.form.fireEvent('pesquisa');
}

var offColor = '';
function TrowOff(src){
    var id = src.id;
	var tbl = document.getElementById('tblResponsaveis');
	for(i = 0; i < tbl.rows.length; i++){
		if (tbl.rows[i].id == id)
			tbl.rows[i].style.background = offColor;
	}
}

var rowAnterior = null;
function TrowOn(src,OnColor){
    if (rowAnterior != null) {
    	TrowOff(rowAnterior);
    }
	rowAnterior = src;
    offColor = src.style.background;
    var id = src.id;
	var tbl = document.getElementById('tblResponsaveis');
	for(i = 0; i < tbl.rows.length; i++){
		if (tbl.rows[i].id == id)
			tbl.rows[i].style.background = OnColor;
	}
}

var serialize_processos = null;
exibirDelegacoes = function(row,idResponsavel,strProcessos) {
	TrowOn(row,'#FFCC99');
	serialize_processos = strProcessos;
	JANELA_AGUARDE_MENU.show();
	document.form.idResponsavel.value = idResponsavel;
	document.formDelegacao.clear();
	document.formDelegacao.idResponsavel.value = idResponsavel;
	document.form.fireEvent('exibeDelegacoes');
}

function adicionarEmpregado() {
	$("#POPUP_EMPREGADO").dialog("open");
}

function LOOKUP_EMPREGADO_select(id, desc) {
	document.formDelegacao.idEmpregado.value = id;
	document.formDelegacao.nomeEmpregado.value = desc;
	$("#POPUP_EMPREGADO").dialog("close");
}

$(function() {
	$("#POPUP_EMPREGADO").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true,
		show: "fade",
		hide: "fade"
	});

	$("#POPUP_DELEGACAO").dialog({
		autoOpen : false,
		width : 720,
		height : 400,
		modal : true,
		show: "fade",
		hide: "fade"
	});
});

delegar = function() {
	if (StringUtils.isBlank(document.formDelegacao.idResponsavel.value) || parseInt(document.formDelegacao.idResponsavel.value) == 0)
		return;
	document.formDelegacao.clear();
	document.getElementById('divProcessos').innerHTML = '';
	document.getElementById('divRequisicoes').style.display = 'none';
	var processos = ObjectUtils.deserializeCollectionFromStringSemQuebraEnter(serialize_processos);
    if (processos != undefined && processos != null){
    	var divProcessos = '<fieldset>';
        for(var i = 0; i < processos.length; i++){
        	var processo = processos[i];
        	divProcessos += '<div class="col_100">';
        	divProcessos += '<input type="checkbox" name="idProcessoNegocio" checked="checked"  value="'+processo.idProcessoNegocio+'"/>'+processo.nomeProcessoNegocio;
        	divProcessos += '</div>';
        }
        divProcessos += '</fieldset>';
        document.getElementById('divProcessos').innerHTML = divProcessos;
    }
	document.formDelegacao.idResponsavel.value = document.form.idResponsavel.value;
	document.formDelegacao.idCentroResultado.value = document.form.idCentroResultado.value;
	document.formDelegacao.dataInicio.value = DateTimeUtil.formatDate(new Date(), 'dd/MM/yyyy');
	$("#POPUP_DELEGACAO").dialog("open");
}

gravarDelegacao = function() {
	if (!validarFormDelegacao())
		return;
	JANELA_AGUARDE_MENU.show();
	document.formDelegacao.fireEvent('gravaDelegacao');
}

validarFormDelegacao = function() {
	return document.formDelegacao.validate();
}

revogar = function(idDelegacao) {
	if (!confirm(i18n_message("delegacaoCentroResultado.confirmaRevogacao")))
		return;
	document.form.idDelegacaoCentroResultado.value = idDelegacao;
	document.form.fireEvent('revogaDelegacao');
}


