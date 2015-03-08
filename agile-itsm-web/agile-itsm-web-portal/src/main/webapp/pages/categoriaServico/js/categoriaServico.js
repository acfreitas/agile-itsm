
		var objTab = null;

		addEvent(window, "load", load, false);
		function load() {
			document.form.afterRestore = function() {
				$('.tabs').tabs('select', 0);
			}
		}

		function LOOKUP_CATEGORIASERVICO_select(id, desc) {
			document.form.restore({
				idcategoriaServico : id
			});
		}


		function LOOKUP_CATEGORIASERVICO_SUPERIOR_select(idTipo, desc) {
			document.form.idCategoriaServicoPai.value = idTipo;

		    var valor = desc.split('-');
			var nomeConcatenado = "";
			for(var i = 0 ; i < valor.length; i++){
				if(i == 0){
					document.form.nomeCategoriaServicoPai.value = valor[i];
				}
			}
			document.form.fireEvent("verificaHierarquia");
			fecharPopup();
			/* document.form.fireEvent("restoreTipoItemConfiguracao"); */
		}

		function atualizaData() {
			var idCategoriaServico = document.getElementById("idCategoriaServico");

			if (idCategoriaServico != null && idCategoriaServico.value == 0) {
				alert(i18n_message("citcorpore.comum.necessarioSelecionarRegistro"));
				return false;
			}
			if (confirm(i18n_message("citcorpore.comum.deleta")))
				document.form.fireEvent("atualizaData");
		}

		$(function() {
			$("#POPUP_CATEGORIASERVICO_SUPERIOR").dialog( {
				autoOpen : false,
				width : 705,
				height : 500,
				modal : true
			});
		});

		function consultarCategoriaServicoSuperior(){
			$("#POPUP_CATEGORIASERVICO_SUPERIOR").dialog("open");
		}

		function fecharPopup(){
			$("#POPUP_CATEGORIASERVICO_SUPERIOR").dialog("close");
		}

	
