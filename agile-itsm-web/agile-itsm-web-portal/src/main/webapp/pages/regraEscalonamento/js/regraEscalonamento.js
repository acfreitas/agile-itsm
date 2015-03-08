

			var objTab = null;

			addEvent(window, "load", load, false);

			function load() {
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				}

				$("#POPUP_SERVICO").dialog({
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

				$("#POPUP_SOLICITANTE").dialog({
					autoOpen : false,
					width : 600,
					height : 400,
					modal : true
				});

			}

			function excluir() {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					document.form.fireEvent("excluir");
				}
			}

			function LOOKUP_REGRAESCALONAMENTO_select(idParam, desc) {
				document.form.restore({
					idRegraEscalonamento : idParam
				});
			}

			function LOOKUP_SERVICO_select(id, desc){
				document.getElementById("idServico").value = id;
				document.getElementById("servico").value = desc;
				$("#POPUP_SERVICO").dialog("close");
			}

			function LOOKUP_GRUPO_select(id, desc){
				document.getElementById("idGrupo").value = id;
				document.getElementById("grupo").value = desc;
				$("#POPUP_GRUPO").dialog("close");
			}

			function LOOKUP_SOLICITANTE_select(id, desc){
				document.getElementById("idSolicitante").value = id;
				document.getElementById("nomeSolicitante").value = desc;
				$("#POPUP_SOLICITANTE").dialog("close");
			}

			function abrePopupServico(){
				$("#POPUP_SERVICO").dialog("open");
			}

			function abrePopupGrupo(){
				$("#POPUP_GRUPO").dialog("open");
			}

			function abrePopupUsuario(){
				$("#POPUP_SOLICITANTE").dialog("open");
			}

			function addGrupoExecutor(){
				if(document.getElementById('idGrupoAtual').value == ""){
					alert(i18n_message("regraEscalonamento.alerta.informeGrupoExecutor"));
					return;
				}else if(document.getElementById('prazoExecucao').value == ""){
					alert(i18n_message("regraEscalonamento.alerta.informePrazoExecucao"));
					return;
				}
		        var obj = new CIT_EscalonamentoDTO();
		        obj.idGrupoExecutor = document.getElementById('idGrupoAtual').value;
		        obj.descricao = document.getElementById('idGrupoAtual').options[document.getElementById('idGrupoAtual').selectedIndex].text;
				obj.prazoExecucao = document.getElementById('prazoExecucao').value;
				obj.idPrioridade = document.getElementById('idPrioridade').value;
				obj.descrPrioridade = document.getElementById('idPrioridade').options[document.getElementById('idPrioridade').selectedIndex].text;

				HTMLUtils.addRow('tblGrupoExecutor', document.form, '', obj,
						['', 'idGrupoExecutor', 'descricao', 'prazoExecucao', 'idPrioridade', 'descrPrioridade'], ["idGrupoExecutor"], i18n_message("regraEscalonamento.alerta.grupoJaAdicionado"), [gerarImgDelGrupoExecutor], null, null, false);

			}

			function deleteLinha(table, index){
				if (index > 0 && confirm(i18n_message("regraEscalonamento.alerta.exclusaoEscalonamento"))) {
					HTMLUtils.deleteRow(table, index);
				}
			}

			function gerarImgDelGrupoExecutor(row, obj){
			        row.cells[0].innerHTML = '<img src="' + ctx + '/imagens/delete.png" style="cursor: pointer;" onclick="deleteLinha(\'tblGrupoExecutor\', this.parentNode.parentNode.rowIndex);"/>';
}

function serializaTabelaGrupos() {
	var itens = HTMLUtils.getObjectsByTableId('tblGrupoExecutor');
	document.form.grupos_serialize.value = ObjectUtils
			.serializeObjects(itens);
}

limpar = function() {
	document.form.clear();
	HTMLUtils.deleteAllRows("tblGrupoExecutor");
}

salvar = function() {
	serializaTabelaGrupos();

	if (document.getElementById("tempoExecucao").value == ""
			&& document.getElementById("idTipoGerenciamento").value == 1) {
		alert(i18n_message("regraEscalonamento.alerta.informePrazoRestanteExecucao"));
		document.getElementById("tempoExecucao").focus();
		return;
	}
	if (document.getElementById("enviarEmail").value == "S"
			&& document.getElementById("intervaloNotificacao").value == "") {
		alert(i18n_message("regraEscalonamento.alerta.informeIntervaloNotificacao"));
		document.getElementById("enviarEmail").focus();
		return;
	}
	document.form.save();
}

function LimparCampoServico() {
	document.getElementById("servico").value = "";
	document.getElementById("idServico").value = "";
}
function LimparCampoGrupo() {
	document.getElementById("grupo").value = "";
	document.getElementById("idGrupo").value = "";
}
function LimparCampoNomeSolicitante() {
	document.getElementById("nomeSolicitante").value = "";
	document.getElementById("idSolicitante").value = "";
}

