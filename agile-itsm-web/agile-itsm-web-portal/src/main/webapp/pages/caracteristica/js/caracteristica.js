
			var objTab = null;

			addEvent(window, "load", load, false);

			function load(){
				document.form.afterRestore = function () {
					$('.tabs').tabs('select', 0);
				}
			}

			function excluir(){
				if (document.getElementById("idCaracteristica").value != "") {
					if (confirm(i18n_message("citcorpore.comum.deleta"))) {
						document.form.fireEvent("delete");
					}
				}

			}

			function LOOKUP_CARACTERISTICA_select(id,desc){
				document.form.restore({idCaracteristica:id});
			}

			function bloquearTag(valor){
				document.getElementById('tag').readOnly  = valor;
			}

			function limpar() {
				document.form.clear();
				bloquearTag(false);
			}
		
