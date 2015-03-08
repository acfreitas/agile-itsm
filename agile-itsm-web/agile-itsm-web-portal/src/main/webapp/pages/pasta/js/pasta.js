
			var objTab = null;
			var contUsuario = 0;
			var contGrupo = 0;
			addEvent(window, "load", load, false);

			function load() {
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				}

				$(function() {
					$("#POPUP_NOTIFICACAO").dialog({
						title: 'Notifica��es',
						autoOpen : false,
						width : 1300,
						height : 600,
						modal : true,
						show: "fade",
						hide: "fade"
					});
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

			function gravar() {

				$('#titulo').val($('#tituloNotificacao').val());
				var tipoNotificacao = $('#tipo').val();
				$('#tipoNotificacao').val(tipoNotificacao);
				serializa();
				serializaUsuario();
				serializaGrupo();
				document.form.save();
			}

			function LOOKUP_PASTA_select(idPasta, desc) {
				document.form.restore( {
					id : idPasta
				});
			}

			function restoreRow() {

				var tabela = document.getElementById('tabelaPerfilAcesso');
				var lastRow = tabela.rows.length;

				var row = tabela.insertRow(lastRow);
				count++;

				var coluna = row.insertCell(0);
				coluna.innerHTML = '<input type="checkbox" id="checkPerfilAcesso' + count + '" onclick="document.getElementById(\'aprovaBaseConhecimentoFalse' + count + '\' ).checked = true, document.getElementById(\'permiteLeitura' + count + '\' ).checked = true" />';

				coluna = row.insertCell(1);
				coluna.innerHTML = '<input type="hidden" id="idPerfilAcesso'
						+ count + '" name="idPerfilAcesso"/><input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="nomePerfilAcesso'
						+ count + '" name="nomePerfilAcesso"/>';

				coluna = row.insertCell(2);
				coluna.innerHTML = 	'<span style="padding-right: 30px;"><input style="margin-right: 5px;" type="radio" id="permiteLeitura' + count + '" name="permiteLeitura' + count + '" value="l" checked: true; />'+i18n_message("pasta.leitura") + ' </span>' +
									'<span style="padding-right: 30px;"><input style="margin-right: 5px;" type="radio" id="permiteLeituraGravacao' + count + '" name="permiteLeitura' + count + '" value="g"/>' + i18n_message("pasta.leituraGravacao") + ' </span>';

				coluna = row.insertCell(3);
				coluna.innerHTML = '<span style="padding-right: 30px;"><input style="margin-right: 5px;" type="radio" id="aprovaBaseConhecimentoTrue' + count + '" name="aprovaBaseConhecimento' + count + '" value="S"/>' + i18n_message("citcorpore.comum.sim") + ' </span>' +
									'<span><input type="radio" id="aprovaBaseConhecimentoFalse' + count + '" name="aprovaBaseConhecimento' + count + '" value="N"/>' + i18n_message("citcorpore.comum.nao") + ' </span>';

			}

			var seqSelecionada = '';
			function setRestorePerfilAcesso(idPerfilAcesso, nomePerfilAcesso, aprovaBaseConhecimento) {

				if (seqSelecionada != '') {

					eval('document.form.idPerfilAcesso' + seqSelecionada + '.value = "' + idPerfilAcesso + '"');

					eval('document.form.nomePerfilAcesso' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + nomePerfilAcesso + '\')');
				}

				exibirGridPerfilAcesso();
			}

			function atribuirChecked(idPerfilAcesso, aprovaBaseConhecimento, permiteLeitura, permiteLeituraGravacao){

				if ($('#idPerfilAcesso' + seqSelecionada).val() == idPerfilAcesso){

					$('#checkPerfilAcesso' + seqSelecionada).attr('checked', true);

					if (aprovaBaseConhecimento == "N"){

						$('#aprovaBaseConhecimentoFalse' + seqSelecionada).attr('checked', true);

					}else{

						$('#aprovaBaseConhecimentoTrue' + seqSelecionada).attr('checked', true);
					}

					if (permiteLeitura == "S"){

						$('#permiteLeitura' + seqSelecionada).attr('checked', true);

					} else{

						if (permiteLeituraGravacao == "S"){

							$('#permiteLeituraGravacao' + seqSelecionada).attr('checked', true);

						}

					}
				}
			}

			var seqSelecionada = '';
			var aux = '';
			serializa = function() {
				var tabela = document.getElementById('tabelaPerfilAcesso');
				var count = tabela.rows.length;
				var contadorAux = 0;
				var perfis = new Array();

				for ( var i = 1; i <= count; i++) {

					var trObj = document.getElementById('idPerfilAcesso' + i);

					if (!trObj) {
						continue;
					}

					if ($('#checkPerfilAcesso' + i).is(":checked")){

						perfis[contadorAux] = getPerfil(i);
						contadorAux = contadorAux + 1;
					}
				}

				var perfisSerializados = ObjectUtils.serializeObjects(perfis);

				document.form.perfisSerializados.value = perfisSerializados;
				return true;
			}

			getPerfil = function(seq) {
				var PerfilAcessoPastaDTO = new CIT_PerfilAcessoPastaDTO();

				PerfilAcessoPastaDTO.sequencia = seq;

				PerfilAcessoPastaDTO.idPerfilAcesso = eval('document.form.idPerfilAcesso' + seq + '.value');

				if ($('#aprovaBaseConhecimentoTrue' + seq).is(":checked")){
					PerfilAcessoPastaDTO.aprovaBaseConhecimento = "S";
				}else{
					PerfilAcessoPastaDTO.aprovaBaseConhecimento = "N";
				}

				if ($('#permiteLeitura' + seq).is(":checked")){
					PerfilAcessoPastaDTO.permiteLeitura = "S";
					PerfilAcessoPastaDTO.permiteLeituraGravacao = "N";
				} else{
					if ($('#permiteLeituraGravacao' + seq).is(":checked")){
						PerfilAcessoPastaDTO.permiteLeitura = "N";
						PerfilAcessoPastaDTO.permiteLeituraGravacao = "S";
					}
				}
				return PerfilAcessoPastaDTO;
			}

			function deleteAllRows() {
				var tabela = document.getElementById('tabelaPerfilAcesso');
				var count = tabela.rows.length;

				while (count > 1) {
					tabela.deleteRow(count - 1);
					count--;
				}
				ocultarGridPerfilAcesso();
			}

			function limpar() {
				deleteAllRows();
				deleteAllRowsUsuario();
				deleteAllRowsGrupo();
				document.form.clear();
				desativarCheckHerdarPermissoes();
				document.form.fireEvent("load");
			}

			function excluir(){
				if (document.getElementById("id").value != "") {
					if (confirm(i18n_message("citcorpore.comum.deleta"))) {
						document.form.fireEvent("excluirPasta");
						limpar();
					}
				}
			}

			function ativarHerdarPemissoes(){
				document.form.fireEvent('ativarHerdarPemissoes');
			}

			function desativarCheckHerdarPermissoes(){
				$('#herdarPermissao').attr('checked', false);
				$('#mainHerdarPermissao').hide();
				exibirGridPerfilAcesso();
			}

			function ativarCheckHerdarPermissoes(){
				exibirHerdaPermissao();
				$('#herdarPermissao').attr('checked', true);
				ocultarGridPerfilAcesso();
			}

			function exibirHerdaPermissao(){
				$('#mainHerdarPermissao').show();

				ocultarGridPerfilAcesso();
			}

			function ocultarHerdaPermissao(){
				$('#mainHerdarPermissao').hide();

				exibirGridPerfilAcesso();
			}


			function exibirGridPerfilAcesso() {
				$('#gridPerfilAcesso').show();
			}

			function ocultarGridPerfilAcesso() {
				$('#gridPerfilAcesso').hide();
			}

			function exibirOcultarGridPerfilAcesso() {
				if ($('#herdarPermissao').is(':checked')){
					$('#gridPerfilAcesso').hide();
				} else{
					exibirGridPerfilAcesso();
				}
			}

			function abrirPopupNotificacao(){
				$('#POPUP_NOTIFICACAO').dialog("open");
			}



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
				coluna.innerHTML = '<img id="imgDelUsuario' + contUsuario + '" style="cursor: pointer;" title="Clique para excluir" src="${ctx}/imagens/delete.png" onclick="removeLinhaTabelaUsuario(\'tabelaUsuario\', this.parentNode.parentNode.rowIndex);">';
				coluna = row.insertCell(1);
				coluna.innerHTML = desc + '<input type="hidden" id="idUsuario' + contUsuario + '" name="idUsuario" value="' + id + '" />';
				$("#POPUP_USUARIO").dialog("close");

			}

			function validaAddLinhaTabelaUsuario(lastRow, id){
				if (lastRow > 2){
					var arrayIdUsuario = document.getElementsByName("idUsuario");
					for (var i = 0; i < arrayIdUsuario.length; i++){
						if (arrayIdUsuario[i].value == id){
							alert('Regristro j� adicionado!');
							return false;
						}

					}
				} else if (lastRow == 2){
					var idUsuario = document.getElementsByName("idUsuario");
					if (idUsuario[0].value == id){
						alert('Regristro j� adicionado!');
						return false;
					}
				}
				return true;
			}

			function removeLinhaTabelaUsuario(idTabela, rowIndex) {
				if (confirm('Deseja realmente excluir')){
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
				coluna.innerHTML = '<img id="imgDelGrupo' + contGrupo + '" style="cursor: pointer;" title="Clique para excluir" src="${ctx}/imagens/delete.png" onclick="removeLinhaTabelaGrupo(\'tabelaGrupo\', this.parentNode.parentNode.rowIndex);">';
			    coluna = row.insertCell(1);
				coluna.innerHTML = desc
					+ '<input type="hidden" id="idGrupo' + contGrupo + '" name="idGrupo" value="' + id + '" />';
					$("#POPUP_GRUPO").dialog("close");
			}

			function validaAddLinhaTabelaGrupo(lastRow, id) {
				if (lastRow > 2) {
					var arrayIdGrupo = document.getElementsByName("idGrupo");
					for ( var i = 0; i < arrayIdGrupo.length; i++) {
						if (arrayIdGrupo[i].value == id) {
							alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
							return false;
						}

					}
				} else if (lastRow == 2) {
					var idGrupo =  document.getElementsByName("idGrupo");
					if (idGrupo[0].value == id) {
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return false;
					}
				}
				return true;
			}

			function removeLinhaTabelaGrupo(idTabela, rowIndex) {
					if (confirm(i18n_message("citcorpore.comum.deleta"))){
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


			function gravarNotificacao() {

				$('#titulo').val($('#tituloNotificacao').val());
				var tipoNotificacao = $('#tipo').val();
				$('#tipoNotificacao').val(tipoNotificacao);
				serializaUsuario();
				serializaGrupo();
				document.form.fireEvent("gravarNotificacao");
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

			function setarValoresPopupNotificacao() {
				$('#tituloNotificacao').val($('#titulo').val());
				var tipoNotificacao = $('#tipoNotificacao').val();
				$('#tipo').val(tipoNotificacao);
			}

			function fecharNotificacao(){
				$("#POPUP_NOTIFICACAO").dialog("close");
			}
			aguarde = function(){
				JANELA_AGUARDE_MENU.show();
			}

			fechar_aguarde = function(){
		    	JANELA_AGUARDE_MENU.hide();
			}

			function validarHerancadePermissao() {
				document.form.fireEvent("validarHerancadePermissao");
			}
		
