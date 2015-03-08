var popup;
addEvent(window, "load", load, false);

function load() {
	popup = new PopupManager(800, 600, ctx + "/pages/");
	document.form.afterRestore = function() {
		$('.tabs').tabs('select', 0);
	};
}

function LOOKUP_ATIVIDADE_PERIODICA_select(id, desc) {
	document.form.restore( {idAtividadePeriodica:id} );
}

function LOOKUP_OS_EXECUCAO_APROVADOEXE_select(id, desc) {
	document.formOS.idOS.value = id;
	document.formOS.fireEvent('atualizaGridOS');
}

configuraAgendamento = function(tipo) {
	document.getElementById('divConfigTarefaD').style.display = 'none';
	document.getElementById('divConfigTarefaS').style.display = 'none';
	document.getElementById('divConfigTarefaM').style.display = 'none';
	document.getElementById('divDataFinal').style.display = 'none';
	if (tipo.value != '' && tipo.value != 'U')
		document.getElementById('divConfigTarefa'+tipo.value).style.display = 'block';
	if (tipo.value != 'U')
    	document.getElementById('divDataFinal').style.display = 'block';
	configuraRepeticao(document.formAgendamento.repeticao);
};

configuraRepeticao = function(sel) {
	if (sel.checked)
    	document.getElementById('divConfigRepeticao').style.display = 'block';
	else
    	document.getElementById('divConfigRepeticao').style.display = 'none';
};

atualizaAgendamento = function() {
	document.formAgendamento.fireEvent('atualizaGrid');
};

$(function() {
	$("#POPUP_AGENDAMENTO").dialog({
		autoOpen : false,
		height : 550,
		width : 600,
		modal : true
	});
});

$(function() {
	$("#btnAddInteg").click(function() {
		$("#POPUP_AGENDAMENTO").dialog("open");
		limpaAgendamento();
		carregaComboAtividadeOs();
	});
});

$(function() {
	$("#btnAddOS").click(function() {
		if(verificaLookup() )
			$("#POPUP_OS").dialog("open");
	});
});

$(function() {
	$("#POPUP_OS").dialog({
		autoOpen : false,
		height : 550,
		width : 600,
		modal : true
	});
});

$(function() {
	$("#POPUP_MUDANCA").dialog({
		autoOpen : false,
		height : 550,
		width : 600,
		modal : true
	});
});

function fecharAgendamento() {
	$("#POPUP_AGENDAMENTO").dialog("close");
}

function fecharOS() {
	$("#POPUP_OS").dialog("close");
}

limpaAgendamento = function() {
	document.formAgendamento.clear();
	document.formAgendamento.tipoAgendamento.value = 'D';
	configuraAgendamento(document.formAgendamento.tipoAgendamento);
};

verificaLookup = function() {
	if (document.getElementById('idContrato').value == "") {
		alert(i18n_message("citcorpore.ui.alerta.mensagem.Necessario_selecionar_contrato") );
		return false;
	}
	return true;
};

exibeIconesOS = function(row, obj) {
	var id = obj.idOS;
	/* var idos = obj.idOS; */
	obj.sequenciaOS = row.rowIndex;
	if (document.form.idSolicitacaoServico.value == undefined
			|| document.form.idSolicitacaoServico.value == null
			|| document.form.idSolicitacaoServico.value == 'null'
    		|| document.form.idSolicitacaoServico.value == ''
    		|| document.form.idSolicitacaoServico.value == '0') {
		row.cells[0].innerHTML = '<img src="'+ctx+'/imagens/excluirPeq.gif" border="0" onclick="excluiOS('
				+ row.rowIndex + ', this)" style="cursor:pointer" />';
	}
};

function carregaComboAtividadeOs() {
	var objs = HTMLUtils.getObjectsByTableId('tblOS');
	document.form.colItensOS_Serialize.value = ObjectUtils.serializeObjects(objs);
	document.form.fireEvent('carregaComboAtividadeOs');
}

exibeIconesAgendamento = function(row, obj) {
	obj.sequencia = row.rowIndex;
	if (document.form.idSolicitacaoServico.value == undefined
		|| document.form.idSolicitacaoServico.value == null
		|| document.form.idSolicitacaoServico.value == 'null'
		|| document.form.idSolicitacaoServico.value == ''
		|| document.form.idSolicitacaoServico.value == '0') {
    	row.cells[0].innerHTML = '<img src="'+ctx+'/imagens/edit.png" border="0" onclick="exibeAgendamento('
    			+ row.rowIndex + ')" style="cursor:pointer" />';
    	row.cells[1].innerHTML = '<img src="'+ctx+'/imagens/excluirPeq.gif" border="0" onclick="excluiAgendamento('
    			+ row.rowIndex + ', this)" style="cursor:pointer" />';
	}
};

exibeAgendamento = function(indice) {
	document.formAgendamento.clear();
	carregaComboAtividadeOs();
	alert(i18n_message("citcorpore.ui.alerta.mensagem.Dados_recuperados_sucesso") );
	var obj = HTMLUtils.getObjectByTableIndex('tblAgendamentos', indice);
	var idAtvOs = obj.idAtividadesOs;
	HTMLUtils.setForm(document.formAgendamento);
	HTMLUtils.setValues(document.formAgendamento, null, obj);
	document.formAgendamento.dataInicio.value = obj.dataInicio;
	document.formAgendamento.dataFim.value = obj.dataFim;
	HTMLUtils.setForm(document.form);
	configuraAgendamento(document.formAgendamento.tipoAgendamento);
	$("#POPUP_AGENDAMENTO").dialog("open");
};

excluiAgendamento = function(indice) {
	if (indice > 0 && confirm(i18n_message("citcorpore.ui.confirmacao.mensagem.Confirma_exclusao") ) ) {
		HTMLUtils.deleteRow('tblAgendamentos', indice);
	}
};

excluiOS = function(indice) {
	if (indice > 0 && confirm(i18n_message("citcorpore.ui.confirmacao.mensagem.Confirma_exclusao") ) ) {
		HTMLUtils.deleteRow('tblOS', indice);
	}
};

grava = function() {
	var objs = HTMLUtils.getObjectsByTableId('tblAgendamentos');
	document.form.colItens_Serialize.value = ObjectUtils.serializeObjects(objs);
	var objs = HTMLUtils.getObjectsByTableId('tblOS');
	document.form.colItensOS_Serialize.value = ObjectUtils.serializeObjects(objs);
	document.form.save();
};

limpa = function() {
	document.form.clear();
	HTMLUtils.deleteAllRows('tblOS');
	document.getElementById('tdBotaoGravar').style.display = 'block';
	document.getElementById('tdBotaoNovoAgendamento').style.display = 'block';
	document.getElementById('tdBotaoNovaOs').style.display = 'block';
};

function abilitaDivAtividade() {
	document.getElementById('divAtividadeOs').style.display = 'block';
}

function desabilitaDivAtividade() {
	document.getElementById('divAtividadeOs').style.display = 'none';
}

function setaValorContrato(valor) {
	limpar_LOOKUP_OS_EXECUCAO_APROVADOEXE();
	document.getElementById("pesqLockupLOOKUP_OS_EXECUCAO_APROVADOEXE_IDCONTRATO").value = '';
	document.getElementById("pesqLockupLOOKUP_OS_EXECUCAO_APROVADOEXE_IDCONTRATO").value = valor.value;
}

function LOOKUP_MUDANCA_select(id,desc){
	document.form.identMudanca.value = desc;
	document.form.idRequisicaoMudanca.value = id;
	$("#POPUP_MUDANCA").dialog("close");
}
function limparMudanca(){
	document.form.identMudanca.value = '';
	document.form.idRequisicaoMudanca.value = '';
}
document.form.onClear = function() {
	HTMLUtils.deleteAllRows('tblAgendamentos');
	HTMLUtils.deleteAllRows('tblOS');
};

