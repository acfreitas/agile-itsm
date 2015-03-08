
		var objTab = null;

		addEvent(window, "load", load, false);
		function load(){
			document.form.afterRestore = function () {
				$('.tabs').tabs('select', 0);
			}
		}

		function LOOKUP_SITUACAOSERVICO_select(id,desc){
			document.form.restore({idSituacaoServico:id});
		}

		function deleteObj() {

			var idSituacaoServico = document.getElementById("idSituacaoServico");
			if (idSituacaoServico.value != '') {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					document.form.fireEvent("delete");
				}
			}
		}
	
