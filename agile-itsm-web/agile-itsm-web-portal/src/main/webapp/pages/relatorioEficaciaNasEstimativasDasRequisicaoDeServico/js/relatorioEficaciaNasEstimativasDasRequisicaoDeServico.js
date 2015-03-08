
		/**
		* @author Bruno.Aquino
		*/
		function validaData(dataInicio, dataFim) {
			if (typeof(locale) === "undefined") locale = '';

			var dataInicio = document.getElementById("dataInicio").value;
			var dataFim = document.getElementById("dataFim").value;

			var dtInicio = new Date();
			var dtFim = new Date();

			var dtInicioConvert = '';
			var dtFimConvert = '';
			var dtInicioSplit = dataInicio.split("/");
			var dtFimSplit = dataFim.split("/");

			if (locale == 'en') {
				dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[0] + "/" + dtInicioSplit[1];
				dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[0] + "/" + dtFimSplit[1];
			} else {
				dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[1] + "/" + dtInicioSplit[0];
				dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[1] + "/" + dtFimSplit[0];
			}

			dtInicio.setTime(Date.parse(dtInicioConvert)).setFullYear;
			dtFim.setTime(Date.parse(dtFimConvert)).setFullYear;

			if (dtInicio > dtFim){
				alert(i18n_message("citcorpore.comum.dataInicioMenorFinal"));
				return false;
			}else
				return true;
		}

		function limpar() {
			document.form.clear();
			limparListaEnvio();
			limparLista("segundaLista");
			limparLista("primeiraLista");
			dataInicio.focus();

		}

		function reportEmpty(){
			alert(i18n_message("citcorpore.comum.relatorioVazio"));
		}
		function mostrarFuncionario(valor){
			if(valor.value=="S"){
				$("#funcionario").show();
			}else{
				$("#funcionario").hide();
				inserirTodosDaLista();
			}
		}

		function mostrarGrupoUnidade(valor){
			if(valor.value=="grupo"){
				$("#Grupo").show();
				$("#Unidade").hide();
			}else{
				$("#Unidade").show();
				$("#Grupo").hide();
				inserirTodosDaLista();
			}
		}

		function inserirNaLista(lista1, lista2,usuarios){
			var texto = $("#"+lista1+" option:selected").text();
			var valor = $("#"+lista1+" option:selected").val();
			if(texto!="" & valor!=""){
				$('#'+lista2).append("<option value='"+valor+"' selected='selected'>"+texto+"</option>");
				$('#'+lista1+' option:selected').remove();
				if(usuarios!=true)
					preencherComboUsuarios(lista2);
			}
		}

		function RetirarDaLista(lista1, lista2,usuarios){
			var texto = $("#"+lista2+" option:selected").text();
			var valor = $("#"+lista2+" option:selected").val();
			if(texto!="" & valor!=""){
				$('#'+lista1).append("<option value='"+valor+"' selected='selected'>"+texto+"</option>");
				$('#'+lista2+' option:selected').remove();
				if(usuarios!=true)
					preencherComboUsuarios(lista2);
			}
		}

		function inserirTodosDaLista(lista1, lista2,usuarios){
			$("#"+lista1+" > option").each(function(i){
				$('#'+lista2).append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");
				$(this).remove();
		    });
			if(usuarios!=true)
				preencherComboUsuarios(lista2);
		}

		function retirarTodosDaLista(lista1, lista2,usuarios){
			$("#"+lista2+" > option").each(function(i){
				$('#'+lista1).append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");
				$(this).remove();
		    });
			if(usuarios!=true)
				preencherComboUsuarios(lista2);
		}

		function limparListaEnvio(){
			$("#segundaLista > option").each(function(i){
				$(this).remove();
		    });
		}

		function limparLista(lista){
			$("#"+lista+" > option").each(function(i){
				$(this).remove();
		    });
		}

		function preencherGrupoUnidade(opcao){
			document.form.fireEvent("preencherGrupoUnidade");
		}


		function preencherComboUsuarios(segundaTabela){
			var valorTipoFitroUsuarios = document.getElementById("selecionarGrupoUnidade").value;
			var listaGrupoUnidade = "";
			if(valorTipoFitroUsuarios=="grupo"){
				$("#"+segundaTabela+" > option").each(function(i){
					listaGrupoUnidade+=this.value+";"
			    });
			}else{
				$("#"+segundaTabela+" > option").each(function(i){
					listaGrupoUnidade+=this.value+";"
			    });
			}
			document.form.listaGrupoUnidade.value = listaGrupoUnidade;
			document.form.fireEvent("preencherComboUsuarios");
			JANELA_AGUARDE_MENU.show();
		}

		function imprimirRelatorio(formato) {
			var dataInicio = document.getElementById("dataInicio");
			var dataFim = document.getElementById("dataFim");
			var contrato = document.getElementById("idContrato");
			var incidente = document.getElementById("checkMostrarIncidentes");
			var requisisao = document.getElementById("checkMostrarRequisicoes");
			var lista1Unidade = document.getElementById("primeiraListaUnidade");
			var lista1Grupo = document.getElementById("primeiraListaGrupo");

			if (validaData(dataInicio,dataFim) ) {
				var listaUsuarios = "";
				var listaGrupos = "";
				var listaUnidades = "";
				$("#segundaLista > option").each(function(i){
					listaUsuarios+=this.value+";"
			    });
				if (dataInicio.value == ""){
					alert(i18n_message("citcorpore.comum.validacao.datainicio"));
					document.getElementById("dataInicio").value = '';
					dataInicio.focus();
					return false;
				}
				if (dataFim.value == ""){
					alert(i18n_message("citcorpore.comum.validacao.datafim"));
					document.getElementById("dataFim").value = '';
					dataFim.focus();
					return false;
				}
				if (contrato.value == ""){
					alert(i18n_message("solicitacaoservico.validacao.contrato"));
					contrato.focus();
					return false;
				}
				if(document.getElementById("selecionarGrupoUnidade1").checked){
					$("#segundaListaGrupo > option").each(function(i){
						listaGrupos+=this.value+";"
				    });
					if(listaGrupos==""){
						alert(i18n_message("controle.grupoObrigatorio"));
						lista1Grupo.focus();
						return false;
					}
				}else{
					$("#segundaListaUnidade > option").each(function(i){
						listaUnidades+=this.value+";"
				    });
					if(listaUnidades==""){
						alert(i18n_message("controle.UnidadeObrigatorio"));
						lista1Unidade.focus();
						return false;
					}
				}
				if(incidente.checked == false && requisisao.checked ==false){
					alert(i18n_message("relatorioEficaciaDasNasEstimativasdeServico.preencherUmDosCamposTipoSolicitacao"));
					incidente.focus();
					return false;
				}
				if(listaUsuarios!=""){
					document.form.listaUsuarios.value = listaUsuarios;
					document.form.formatoArquivoRelatorio.value = formato.value;
					document.form.fireEvent("imprimirRelatorio");
				}else{
					alert(i18n_message("relatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodo.selecioneFuncionario"));
					return false;
				}
				JANELA_AGUARDE_MENU.show();

			}
		}
  	
