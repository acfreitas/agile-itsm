/**
 * 
 */

		fechar = function(){
			parent.fecharModalSuspensao();
		}

		gravar = function() {
			if (document.form.idJustificativa.value == '') {
				alert(i18n_message("citcorpore.comum.justificativa") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
				document.form.idJustificativa.focus();
				return;
			}
			if (confirm(i18n_message("gerenciaservico.suspensaosolicitacao.confirm.suspensao"))) {
				document.form.save();
			}
		}
