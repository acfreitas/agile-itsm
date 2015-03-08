

				var objTab = null;

				addEvent(window, "load", load, false);

				function load() {
					document.form.afterRestore = function() {
						$('.tabs').tabs('select', 0);
					}
				}

				function excluir() {
					if (confirm(i18n_message("citcorpore.comum.deleta"))) {
						document.form.fireEvent("excluir");
					}
				}

				function LOOKUP_PALAVRAGEMEA_select(idParam, desc) {
					document.form.restore({
						idPalavraGemea : idParam
					});
				}
		
