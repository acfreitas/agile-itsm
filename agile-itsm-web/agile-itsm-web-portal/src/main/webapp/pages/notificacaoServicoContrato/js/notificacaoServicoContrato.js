		var objTab = null;
		var contUsuario = 0;
		var contGrupo = 0;
		
		
		$(window).load(function(){
			document.form.afterRestore = function() {
				$('.tabs').tabs('select', 0);
			}

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
			
		});

		


		function LOOKUP_NOTIFICACAO_select(id, desc) {
			document.form.restore({
				idNotificacao : id
			});
		}

		function excluir() {
			if (document.getElementById("idNotificacao").value != "") {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					document.form.fireEvent("delete");
				}
			}
		}

	 	$(function() {
			$("#addUsuario").click(function() {
				$("#POPUP_USUARIO").dialog("open");
			});
		});

	 	$(function() {
			$("#addGrupo").click(function() {
				$("#POPUP_GRUPO").dialog("open");
			});
		});

	 	function LOOKUP_USUARIO_select(id, desc) {
		 addLinhaTabelaUsuario(id, desc, true);

		}

		function exibirTabelaUsuario (){
			document.getElementById('tabelaUsuario').style.display = 'block';
		}

		function exibirTabelaGrupo (){
			document.getElementById('tabelaGrupo').style.display = 'block';
		}

		function addLinhaTabelaUsuario(id, desc, valida){
			var tbl = document.getElementById('tabelaUsuario');
			$('#tabelaUsuario').show();
			$('#gridUsuario').show();
			//tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			if (valida){
				if (!validaAddLinhaTabelaUsuario(lastRow, id)){
					return;
				}
			}
			var row = tbl.insertRow(lastRow);
			var coluna = row.insertCell(0);
			contUsuario++;
			coluna.innerHTML = '<img id="imgDelUsuario' + contUsuario + '" style="cursor: pointer;" title=" '+i18n_message("citcorpore.comum.cliquaParaExcluir")+'" src="'+ctx+'/imagens/delete.png" onclick="removeLinhaTabelaUsuario(\'tabelaUsuario\', this.parentNode.parentNode.rowIndex);">';
			coluna = row.insertCell(1);
			coluna.innerHTML = desc + '<input type="hidden" id="idUsuario' + contUsuario + '" name="idUsuario" value="' + id + '" />';
			$("#POPUP_USUARIO").dialog("close");

		}

		 function validaAddLinhaTabelaUsuario(lastRow, id){
			if (lastRow > 2){
				var arrayIdUsuario = document.form.idUsuario;
				for (var i = 0; i < arrayIdUsuario.length; i++){
					if (arrayIdUsuario[i].value == id){
						alert('Regristro já adicionado!');
						return false;
					}

				}
			} else if (lastRow == 2){
				var idUsuario = document.form.idUsuario;
				if (idUsuario.value == id){
					alert('Regristro já adicionado!');
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

		function LOOKUP_GRUPO_select(id, desc) {
			addLinhaTabelaGrupo(id, desc, true);

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
				coluna.innerHTML = '<img id="imgDelGrupo' + contGrupo + '" style="cursor: pointer;" title="'+i18n_message("citcorpore.comum.cliquaParaExcluir")+'" src="'+ctx+'/imagens/delete.png" onclick="removeLinhaTabelaGrupo(\'tabelaGrupo\', this.parentNode.parentNode.rowIndex);">';
			coluna = row.insertCell(1);
			coluna.innerHTML = desc
					+ '<input type="hidden" id="idGrupo' + contGrupo + '" name="idGrupo" value="' + id + '" />';
			$("#POPUP_GRUPO").dialog("close");
		}

		function validaAddLinhaTabelaGrupo(lastRow, id) {
			if (lastRow > 2) {
				var arrayIdGrupo = document.form.idGrupo;
				for ( var i = 0; i < arrayIdGrupo.length; i++) {
					if (arrayIdGrupo[i].value == id) {
						alert('Regristro já adicionado!');
						return false;
					}

				}
			} else if (lastRow == 2) {
				var idGrupo = document.form.idGrupo;
				if (idGrupo.value == id) {
					alert('Regristro já adicionado!');
					return false;
				}
			}
			return true;
		}

		function removeLinhaTabelaGrupo(idTabela, rowIndex) {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				HTMLUtils.deleteRow(idTabela, rowIndex);
				var tabela = document.getElementById(idTabela);
				if (tabela.rows.length == 1) {
					if (idTabela == 'tabelaGrupo') {
						$('#gridGrupo').hide();
						return;
					}
					$('#tabelaGrupo').hide();
				}
			}
		}

		function NotificacaoUsuarioDTO(_id, i){
	 		this.idUsuario = _id;
	 	}

		function serializaUsuario() {
			var tabela = document.getElementById('tabelaUsuario');
			var count = contUsuario + 1;
			var listaDeUsuario = [];
			for ( var i = 1; i < count; i++) {
				if (document.getElementById('idUsuario' + i) != ""
						&& document.getElementById('idUsuario' + i) != null) {
					var trObj = document.getElementById('idUsuario' + i).value;
					var usuario = new NotificacaoUsuarioDTO(trObj, i);
					listaDeUsuario.push(usuario);
				}
			}
			var serializaUsuario = ObjectUtils.serializeObjects(listaDeUsuario);
			document.form.usuariosSerializados.value = serializaUsuario;
		}

		function NotificacaoGrupoDTO(_id, i){
	 		this.idGrupo = _id;
	 	}

		function serializaGrupo() {
			var tabela = document.getElementById('tabelaGrupo');
			var count = contGrupo + 1;
			var listaDeGrupo = [];
			for ( var i = 1; i < count; i++) {
				if (document.getElementById('idGrupo' + i) != ""
						&& document.getElementById('idGrupo' + i) != null) {
					var trObj = document.getElementById('idGrupo' + i).value;
					var grupo = new NotificacaoGrupoDTO(trObj, i);
					listaDeGrupo.push(grupo);
				}
			}
			var serializaGrupo = ObjectUtils.serializeObjects(listaDeGrupo);
			document.form.gruposSerializados.value = serializaGrupo;
		}

		function lancarServicos() {
			checkboxServico = document.getElementsByName('idServico');
			var count = checkboxServico.length;
			var contadorAux = 0;
			var baselines = new Array();

			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idServico' + i);
				if (!trObj) {
					continue;
				}
				baselines[contadorAux] = getServico(i);
				contadorAux = contadorAux + 1;
			}
			serializaServico();
		}

		serializaServico = function() {
			var checkboxServico = document.getElementsByName('idServico');
			var count = checkboxServico.length;
			var contadorAux = 0;
			var servicos = new Array();
			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idServico' + i);
				if (!trObj) {
					continue;
				}else if(trObj.checked){
					servicos[contadorAux] = getServico(i);
					contadorAux = contadorAux + 1;
					continue;
				}
			}
			var servicosSerializadas = ObjectUtils.serializeObjects(servicos);
			document.form.servicosLancados.value = servicosSerializadas;
			return true;
		}

		getServico = function(seq) {
			var NotificacaoServicoDTO = new CIT_NotificacaoServicoDTO();
			NotificacaoServicoDTO.sequencia = seq;
			NotificacaoServicoDTO.idServico = eval('document.form.idServico' + seq + '.value');
			return NotificacaoServicoDTO;
		}

		function gravar(){
			/*
			 * Rodrigo Pecci Acorse - 27/11/2013 17h30 - #125019
			 * Adicionado a validação para garantir que ao menos um usuário ou grupo foi adicionado.
			 */
			if ($('input[type="hidden"][name="idUsuario"]').length == 0 && $('input[type="hidden"][name="idGrupo"]').length == 0) {
				alert(i18n_message("gerenciaservico.delegartarefa.validacao.informeprazo"));
				return false;
			}

			serializaUsuario();
			serializaGrupo();
			document.getElementById('origemNotificacao').value = 'S';
			lancarServicos();
			document.form.save();
		}

		function limpar(){
			 deleteAllRowsUsuario();
			  deleteAllRowsGrupo();
			document.form.clear();

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

		function deleteAllRowsGrupo() {
			var tabela = document.getElementById('tabelaGrupo');
			var count = tabela.rows.length;

			while (count > 1) {
				tabela.deleteRow(count - 1);
				count--;

			}
			$('#gridGrupo').hide();
		}

		function check() {
			var tabela = document.getElementById('tbServicos');
			var count = tabela.rows.length;
			if ($('#todos').is(":checked")) {
				for ( var i = 1; i < count; i++) {
					$('#idServico' + i).attr('checked', true);
				}
			} else {
				for ( var i = 1; i < count; i++) {
					$('#idServico' + i).attr('checked', false);
				}
			}
		}
		function fecharPopup() {
			parent.fecharPopupNotificacoesServicos();
		}