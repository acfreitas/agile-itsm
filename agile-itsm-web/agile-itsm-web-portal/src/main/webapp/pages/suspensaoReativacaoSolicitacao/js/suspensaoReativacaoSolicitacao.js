/**
 * 
 */

		function suspenderSolicitacoes(){
			document.getElementById("tipoAcao").value = "suspender";
			if(validarCampos(document.getElementById("tipoAcao").value)!=true){
				var confimacao = confirm(i18n_message("suspensaoReativacaoSolicitacao.MensagemConfirmacaoSuspensao"));
				if(confimacao==true){
					parent.janelaAguarde();
					document.form.fireEvent("processarSolicitacoes");
					document.form.clear();
				}
			}

		}

		function reativarSolicitacoes(){
			document.getElementById("tipoAcao").value = "reativar";
			if(validarCampos(document.getElementById("tipoAcao").value)!=true){
				var confimacao = confirm(i18n_message("suspensaoReativacaoSolicitacao.MensagemConfirmacaoReativacao"));
				if(confimacao==true){
					parent.janelaAguarde();
					document.form.fireEvent("processarSolicitacoes");
					document.form.clear();
				}

			}
		}

		function validarCampos(tipoAcao){

			if(document.getElementById("idContrato").value==""){
				alert(i18n_message("suspensaoReativacaoSolicitacao.alertaCampoVazioContrato"));
				return true;
			}
			if(document.getElementById("solicitante").value==""){
				alert(i18n_message("suspensaoReativacaoSolicitacao.alertaCampoVazioSolicitante"));
				return true;
			}
			if(document.getElementById("idGrupo").value==""){
				alert(i18n_message("suspensaoReativacaoSolicitacao.alertaCampoVazioidGrupo"));
				return true;
			}
			if(tipoAcao=='suspender'){
				if(document.getElementById("justificativa").value==""){
					alert(i18n_message("suspensaoReativacaoSolicitacao.alertaCampoVaziojustificativa"));
					return true;
				}
				if(document.getElementById("idJustificativa").value==""){
					alert(i18n_message("suspensaoReativacaoSolicitacao.alertaCampoVazioidJustificativa"));
					return true;
				}

			}
			return false;

		}

		var completeSolicitante;
		$(function(){
			$('#idSelectTipo').on('change', function() {
				  if(this.value=='Suspender'){
					  $("#idReativacao").hide();
					  $("#idSuspensao").show();
					  $("#solicitante").attr("disabled", "disabled");
					  document.getElementById("solicitanteSuspensao").style.display = 'block';
					  document.getElementById("solicitanteReativacao").style.display = 'none';
				  }
				  else{
					  $("#idSuspensao").hide();
					  $("#idReativacao").show();
					  $("#solicitante").attr("disabled", "disabled");
					  document.getElementById("solicitanteReativacao").style.display = 'block';
					  document.getElementById("solicitanteSuspensao").style.display = 'none';
				  }
				  document.form.reset();
			});

			$('#idContrato').on('change', function() {
				if(this.value!=""){
					$("#solicitante").removeAttr("disabled");
					$("#btnPesqAvancada").removeAttr("disabled");
				}else{
					$("#solicitante").attr("disabled", "disabled");
					$("#btnPesqAvancada").attr("disabled", "disabled");
				}
				$("#solicitante").val("");
			});

			completeSolicitante = $('#solicitante').autocomplete({
				serviceUrl:'pages/autoCompleteSolicitante/autoCompleteSolicitante.load',
				noCache: true,
				onSelect: function(value, data){
					$('#idSolicitante').val(data);
					$('#solicitante').val(value);
					$('#nomecontato').val(value);
					document.form.fireEvent("restoreColaboradorSolicitante");
					document.form.fireEvent('renderizaHistoricoSolicitacoesEmAndamentoUsuario');

				}
			});
		});


		/**Monta os parametros para a buscas do autocomplete**/
		function montaParametrosAutocompleteServico(){
		 	contrato = $("#idContrato").val();
		 	completeSolicitante.setOptions({params: {contrato: contrato} });
		}

		function LOOKUP_SOLICITANTE_select(id, desc){
			document.form.solicitante.value = desc;
			$('#modal_lookupSolicitante').modal('hide');
		}