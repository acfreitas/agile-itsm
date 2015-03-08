	        $(function() {
	        	// As duas popup a seguir são variáveis globais pois não foram
	        	// declaradas com o uso da keyword var.
	        	popupCadastroCentroResultado = new PopupManager(1200, 700, caminho);
		        popupCadastroAlcada = new PopupManager(1200, 700, caminho);

	        	document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				};

// 				$('#LOOKUP_ALCADACENTRORESULTADO').dialog({
// 					autoOpen: false,
// 					width: 705,
// 					height: 500,
// 					modal: true
// 				});

				$('#POPUP_PESQUISA_COLABORADOR').dialog({
					autoOpen: false,
					width: 705,
					height: 500,
					modal: true
				});

				$('#POPUP_PESQUISA_CENTRORESULTADO').dialog({
					autoOpen: false,
					width: 705,
					height: 500,
					modal: true
				});

				$('#POPUP_PESQUISA_ALCADA').dialog({
					autoOpen: false,
					width: 705,
					height: 500,
					modal: true
				});
	        });

	        // FUNÇÕES PARA ABERTURA DAS JANELAS DE PESQUISA

	        function pesquisarColaborador() {
				$('#POPUP_PESQUISA_COLABORADOR').dialog('open');
			}

			function pesquisarCentroResultado() {
				$('#POPUP_PESQUISA_CENTRORESULTADO').dialog('open');
			}

			function pesquisarAlcada() {
				$('#POPUP_PESQUISA_ALCADA').dialog('open');
			}

			// CONFIGURANDO O COMPORTAMENTO DAS JANELAS DE PESQUISA APÓS A SELEÇÃO DE UM DOS RESULTADOS

			function LOOKUP_PESQUISA_COLABORADOR_select(id, desc) {
				// Configurar o atributo idEmpregado do DTO (AlcadaCentroResultado) associado a esta página jsp.
				// com o valor do id do colaborador selecionada na janela de pesquisa de colaboradores.
				$('#idEmpregado').val(id);
				// Configura uma caixa de texto com o nome do colaborador.
				$('#nomeEmpregado').val(desc);
				// Fecha a janela de pesquisa
				$('#POPUP_PESQUISA_COLABORADOR').dialog('close');
			}

			function LOOKUP_PESQUISA_CENTRORESULTADO_select(id, desc) {
				$('#idCentroResultado').val(id);

				desc = desc.split('-');
				desc = desc[0].replace(' ', '');

				$('#nomeCentroResultado').val(desc);
				$('#POPUP_PESQUISA_CENTRORESULTADO').dialog('close');
			}

			function LOOKUP_PESQUISA_ALCADA_select(id, desc) {
				$('#idAlcada').val(id);
				$('#nomeAlcada').val(desc);
				$('#POPUP_PESQUISA_ALCADA').dialog('close');
			}

			// LOOKUP_ALCADACENTRORESULTADO
			function LOOKUP_ALCADACENTRORESULTADO_select(id, desc) {
				document.form.restore({
					idAlcadaCentroResultado: id
				});
			}

			// FUNÇÕES PARA ABERTURA DA PÁGINA DE CADASTRO DE OUTRAS ENTIDADES

			function abrirPopupCadastroCentroResultado() {
				popupCadastroCentroResultado.abrePopupParms("centroResultado", "");
			}

			function abrirPopupCadastroAlcada() {
				popupCadastroAlcada.abrePopupParms("alcada", "");
			}

			// FUNÇÃO DE EXCLUSÃO DE ALÇADA CENTRO RESULTADO

			function excluir() {
				var idAlcadaCentroResultado = $('#idAlcadaCentroResultado').val();

				if (idAlcadaCentroResultado != "") {
					if (confirm(i18n_message("citcorpore.comum.deleta") ) ) {
						document.form.fireEvent("delete");
					}
				} else {
					alert(i18n_message("citcorpore.comum.necessarioSelecionarRegistro") );
					return false;
				}
			}