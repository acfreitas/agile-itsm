
		addEvent(window, "load", load, false);
	    function load() {
			document.form.afterRestore = function () {
				$('.tabs').tabs('select', 0);

			}
	    }

	    function LOOKUP_CONEXOES_select(id,desc){
			$('.tabsbar a[href="#tab1-3"]').tab('show');
			document.form.restore({idExternalConnection:id});
			$("#divSenha").hide();
			$("#divAlterarSenha").show();
		}

	    function alterarSenha(){
	    	$("#divAlterarSenha").hide();
			$("#divSenha").show();
			$("#senha").val("");
		}

	    function fechaAlterarSenha(){
	    	$("#divSenha").show();
			$("#divAlterarSenha").hide();
		}

		function excluir() {
			if (document.getElementById("idExternalConnection").value != "") {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					document.form.fireEvent("delete");
					load();
					fechaAlterarSenha();
				}
			}
		}

		function confirmarSalvarDadosInvalidos(){

			if (confirm(i18n_message("externalconnection.falha.conexao"))) {
				document.form.fireEvent("salvar");
			}
		}
		
