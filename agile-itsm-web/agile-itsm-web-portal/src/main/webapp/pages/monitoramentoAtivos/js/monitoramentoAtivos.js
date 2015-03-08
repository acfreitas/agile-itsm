//Inicializa��o das vari�veis globais.
var contNotificacaoUsuarios = 0;
var contNotificacaoGrupos = 0;

//Fun��o executada ap�s o restore da pesquisa.
document.form.afterRestore = function() {
	$('[data-toggle="tab"][href="#tab1-2"]').trigger('click');
}

function abrePopupTipoItemConfiguracao() {
	$("#POPUP_TIPOITEMCONFIGURACAO").dialog("open");
}

function adicionarUsuario() {
	$("#POPUP_USUARIO").dialog("open");
}

function adicionarGrupo() {
	$("#POPUP_GRUPO").dialog("open");
}

$("#POPUP_TIPOITEMCONFIGURACAO").dialog({
	autoOpen : false,
	width : 600,
	height : 400,
	modal : true
});

$("#POPUP_USUARIO").dialog({
	autoOpen : false,
	width : 600,
	height : 400,
	modal : true
});

$("#POPUP_GRUPO").dialog({
	autoOpen : false,
	width : 600,
	height : 400,
	modal : true
});

function LOOKUP_TIPOITEMCONFIGURACAO_select(id, desc){
	document.getElementById("idTipoItemConfiguracao").value = id;
	document.getElementById("tipoItemConfiguracao").value = desc;
	
	document.form.fireEvent("preencheGridCaracteristicas");
	
	$("#POPUP_TIPOITEMCONFIGURACAO").dialog("close");
}

function LOOKUP_USUARIO_select(id, desc) {
	addLinhaTabelaNotificacaoUsuarios(id, desc);
}

function LOOKUP_GRUPO_select(id, desc) {
	addLinhaTabelaNotificacaoGrupos(id, desc);
}

function LOOKUP_MONITORAMENTOATIVOS_select(id, desc) {
	document.form.restore( {
		idMonitoramentoAtivos : id
	});
}

function alterarRegra(_this) {
	if (document.getElementById('idTipoItemConfiguracao').value == '') {
		alert(i18n_message("citcorpore.comum.campo_obrigatorio") + ": " + i18n_message("itemConfiguracaoTree.tipoItemConfiguracao"));
		
		$('input[name="tipoRegra"]').prop('checked', false);
		$('input[name="tipoRegra"]').parent().removeClass('checked');
		
		$(".divScriptRhino").hide();
		$(".divCaracteristicas").hide();
		
		return false;
	} else {
		if (_this.value == "c") {
			$(".divCaracteristicas").show();
			$(".divScriptRhino").hide();
		} else if (_this.value == "s") {
			$(".divCaracteristicas").hide();
			$(".divScriptRhino").show();
		}
	}
}

function mostraEnviarEmail(_this) {
	if (_this.checked) {
		$(".divEnviarEmail").show();
	} else {
		$(".divEnviarEmail").hide();
	} 
}

function addLinhaTabelaNotificacaoUsuarios(id, desc){
	var _table = document.getElementById('tblNotificacaoUsuarios');
	var _usuarios = document.getElementsByName("usuariosNotificacao");
	
	var lastRow = _table.rows.length;
	
	if (_usuarios.length > 0) {
		for (var i = 0; i < _usuarios.length; i++) {
			if (_usuarios[i].value == id) {
				alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
				return false;
			}
		}
	}
	
	contNotificacaoUsuarios++;
	
	//Insere a linha...
	var row = _table.insertRow(lastRow);
	
	//Insere a coluna 0...
	var coluna = row.insertCell(0);
	coluna.innerHTML = '<img id="imgDelUsuario' + contNotificacaoUsuarios + '" style="cursor: pointer;" title="' + i18n_message("citcorpore.comum.cliquaParaExcluir") + '" src="' + URL_SISTEMA + '/imagens/delete.png" onclick="removeLinhaTabela(\'tblNotificacaoUsuarios\', this.parentNode.parentNode.rowIndex);">';
	
	coluna = row.insertCell(1);
	coluna.innerHTML = desc + ' <input type="hidden" id="usuariosNotificacao' + contNotificacaoUsuarios + '" name="usuariosNotificacao" value="' + id + '" />';
	
	$('#POPUP_USUARIO').dialog("close");
}
 	
function addLinhaTabelaNotificacaoGrupos(id, desc){
	var _table = document.getElementById('tblNotificacaoGrupos');
	var _grupos = document.getElementsByName("gruposNotificacao");
	
	var lastRow = _table.rows.length;
	
	if (_grupos.length > 0) {
		for (var i = 0; i < _grupos.length; i++) {
			if (_grupos[i].value == id) {
				alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
				return false;
			}
		}
	}
	
	contNotificacaoGrupos++;
	
	//Insere a linha...
	var row = _table.insertRow(lastRow);
	
	//Insere a coluna 0...
	var coluna = row.insertCell(0);
	coluna.innerHTML = '<img id="imgDelGrupo' + contNotificacaoGrupos + '" style="cursor: pointer;" title="' + i18n_message("citcorpore.comum.cliquaParaExcluir") + '" src="' + URL_SISTEMA + '/imagens/delete.png" onclick="removeLinhaTabela(\'tblNotificacaoGrupos\', this.parentNode.parentNode.rowIndex);">';
	
	coluna = row.insertCell(1);
	coluna.innerHTML = desc + ' <input type="hidden" id="gruposNotificacao' + contNotificacaoGrupos + '" name="gruposNotificacao" value="' + id + '" />';
	
	$('#POPUP_GRUPO').dialog("close");
}

function removeLinhaTabela(idTabela, rowIndex) {
	if (confirm(i18n_message("citcorpore.comum.deleta"))) {
		HTMLUtils.deleteRow(idTabela, rowIndex);
	}
}

function gerarSelecaoCaracteristicas(row, obj){
    row.cells[0].innerHTML = "<input type='radio' name='idCaracteristica' id='idCaracteristica_" + obj.idCaracteristica + "' value='" + obj.idCaracteristica + "' />";
}

$(document).on('click', '#tipoRegraScriptRhino', function(){
	document.form.fireEvent("validaExistenciaScriptTipoItemConfiguracao");
});

$(document).on('click', 'input[name="idCaracteristica"]', function(){
	document.form.fireEvent("validaExistenciaCaracteristicaTipoItemConfiguracao");
});

function informaRegistroExistente(type) {
	if (type == 's') {
		alert(i18n_message("monitoramentoAtivos.jaExisteScript"));
	} else if (type == 'c') {
		alert(i18n_message("monitoramentoAtivos.jaExisteCaracteristica"));
		
		$('input[name="idCaracteristica"]').prop('checked', false);
	}
	
	$('input[name="tipoRegra"]').prop('checked', false);
	$('input[name="tipoRegra"]').parent().removeClass('checked');
	
	$(".divScriptRhino").hide();
	$(".divCaracteristicas").hide();
}

function valida(){
	if ($.trim(document.getElementById('titulo').value) == '') {
		alert(i18n_message("citcorpore.comum.campo_obrigatorio") + ": " + i18n_message("monitoramentoAtivos.nome"));
		return false;
	}
	
	if (document.getElementById('idTipoItemConfiguracao').value == '') {
		alert(i18n_message("citcorpore.comum.campo_obrigatorio") + ": " + i18n_message("itemConfiguracaoTree.tipoItemConfiguracao"));
		return false;
	}
	
	if (!document.getElementById('tipoRegraCaracteristicas').checked && !document.getElementById('tipoRegraScriptRhino').checked) {
		alert(i18n_message("citcorpore.comum.campo_obrigatorio") + ": " + i18n_message("monitoramentoAtivos.regraMonitoramento"));
		return false;
	} else {
		if (document.getElementById('tipoRegraCaracteristicas').checked) {
			if ($("input[name='idCaracteristica']:checked").length <= 0) {
				alert(i18n_message("citcorpore.comum.campo_obrigatorio") + ": " + i18n_message("citcorpore.comum.caracteristica"));
				return false;			
			}
		} else if (document.getElementById('tipoRegraScriptRhino').checked) {
			if (document.getElementById('script').value == '') {
				alert(i18n_message("citcorpore.comum.campo_obrigatorio") + ": " + i18n_message("solicitacaoServico.script"));
				return false;
			}
		}
	}
	
	if (!document.getElementById('enviarEmail').checked && !document.getElementById('criarProblema').checked && !document.getElementById('criarIncidente').checked) {
		alert(i18n_message("monitoramentoAtivos.escolhaRegrasNotificacao") + ".");
		return false;
	}
	
	if (document.getElementById('enviarEmail').checked) {
		var t = $("input[name='usuariosNotificacao']").length + $("input[name='gruposNotificacao']").length;
		if (t <= 0) {
			alert(i18n_message("citcorpore.comum.campo_obrigatorio") + ": " + i18n_message("citcorpore.comum.usuario") + "/" + i18n_message("grupo.grupo"));
			return false;
		}
	}
	
	if ($.trim(document.getElementById('descricao').value) == '') {
		alert(i18n_message("citcorpore.comum.campo_obrigatorio") + ": " + i18n_message("monitoramentoAtivos.descricaoNotificacao"));
		return false;
	}

	return true;
}

function gravar() {
	if (valida()){
		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("save");
	}
}

function deletar() {
	var idMonitoramentoAtivos = document.getElementById('idMonitoramentoAtivos').value;
	if (idMonitoramentoAtivos != "") {
		if (confirm(i18n_message("citcorpore.comum.deleta"))) {
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("delete");
		}
	} else {
		alert(i18n_message("dinamicview.nenhumRegistroSelecionado"));
		return false;
	}
}

function limpar() {
	document.form.clear();
	
	HTMLUtils.deleteAllRows("tblNotificacaoUsuarios");
	HTMLUtils.deleteAllRows("tblNotificacaoGrupos");
	
	$(".divScriptRhino, .divCaracteristicas, .divEnviarEmail").hide();
}

function validaData(dataInicio, dataFim) {
       if (typeof(locale) === "undefined") locale = '';
       
       var dtInicio = new Date();
       var dtFim = new Date();
       
       var dtInicioConvert = '';
       var dtFimConvert = '';
       var dtInicioSplit = dataInicio.split("/");
       var dtFimSplit = dataFim.split("/");

       if (locale == 'en') {
             dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[0] + "/" + dtInicioSplit[1];
             dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[0] + "/" + dtFimSplit[1];
       } else {
             dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[1] + "/" + dtInicioSplit[0];
             dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[1] + "/" + dtFimSplit[0];
       }
       
       dtInicio.setTime(Date.parse(dtInicioConvert)).setFullYear;
       dtFim.setTime(Date.parse(dtFimConvert)).setFullYear;
       
       if (dtInicio > dtFim){
             alert(i18n_message("citcorpore.comum.dataInicioMenorFinal"));
             return false;
       }else
             return true;
}

//Realiza a limpeza dinamica de qualquer lookup na tela (conforme configura��o no link de limpar)
$(document).on('click', '.limpar', function(){
	var input = $(this).attr('data-input');
	var id = $(this).attr('data-id');
	
	$('#' + id).attr('value', '');
	$('#' + input).attr('value', '');
	
	//Se o input for de tipoItemConfiguracao, faz a limpeza tamb�m das caracteristicas
	if (input == 'tipoItemConfiguracao') {
		HTMLUtils.deleteAllRows('tblCaracteristicas');
	}
	
});

//Cria o countdown de caracteres
$(document).on('keydown keyup', '.countdown', function(){
	var _this = $(this);
	var value = _this.val();
	var _limit = _this.attr('maxlength');
	
	if (_this.val().length > _limit) {
		_this.val(_this.val().substring(0, _limit));
	} else {
		$('span[class="limiteCaracteres"][data-ref="' + _this.attr('id') + '"]').text(_limit - _this.val().length);
	}
});
