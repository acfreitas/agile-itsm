$(function(){
	$('.filtro-toogle').click(function(e) {
		var t = $(this).attr('id');
		if ($('.' + t).is('.visualizaFiltro')) {
			$('.' + t).removeClass('visualizaFiltro');
		} else {
			$('.' + t).addClass('visualizaFiltro');
		}

	});
});

function load() {
	document.form.afterRestore = function() {
	}
}
function deleteLinha(table, index){
	HTMLUtils.deleteRow(table, index);
	
}
addEvent(window, "load", load, false);
function carregarServicos(idServ){
	document.form.idCatalogoServico.value = idServ; 
	document.form.fireEvent("contentServicos");
}
function marcarTodos(){
	classe = 'perm';
	if ($("#checkboxCheckAll").is(':checked')) {
		$("." + classe).each(function() {
			$(this).attr("checked", true);
		});					 
	}else {
		$("." + classe).each(function() {
				$(this).attr("checked", false);
		});
	}
}

function setarValoresTabela(param) {
	bootbox.dialog(param, [{
		"label" : "Adicionar ao Carrinho",
		"class" : "btn-primary",
		"callback": function() {
			adicionarCarrinho();
		}
	}, {
		"label" : "Cancelar",
		"class" : "btn",
		"callback":function() {
			$("#tblDescricao").hide();
		}
		}
	]);
}

function adicionarCarrinho() {
	lancarServicos()
}

function lancarServicos() {
	checkboxServico = document.getElementsByName('idServicoContrato');
	var count = checkboxServico.length;
	var contadorAux = 0;
	var baselines = new Array();
	for ( var i = 1; i <= count; i++) {
		var trObj = document.getElementById('idServicoContrato' + i);	
		if (!trObj) {
			continue;
		}	
		baselines[contadorAux] = getServicoContrato(i);
		contadorAux = contadorAux + 1;
	}
	serializaServico();
}

serializaServico = function() {
	var checkboxServico = document.getElementsByName('idServicoContrato');
	var count = checkboxServico.length;
	var contadorAux = 0;
	var servicos = new Array();
	for ( var i = 1; i <= count; i++) {
		var trObj = document.getElementById('idServicoContrato' + i);
		if (!trObj) {
			continue;
		}else if(trObj.checked){
			servicos[contadorAux] = getServicoContrato(i);
			contadorAux = contadorAux + 1;
			continue;
		}	
	}
	var servicosSerializados = ObjectUtils.serializeObjects(servicos);
	document.form.servicosLancados.value = servicosSerializados;
	return true;
}

getServicoContrato = function(seq) {
	var infoCatalogoServicoDTO = new CIT_InfoCatalogoServicoDTO();
	infoCatalogoServicoDTO.sequencia = seq;
	infoCatalogoServicoDTO.idServicoContrato = eval(document.getElementById('idServicoContrato' + seq).value);
	return infoCatalogoServicoDTO;
}
