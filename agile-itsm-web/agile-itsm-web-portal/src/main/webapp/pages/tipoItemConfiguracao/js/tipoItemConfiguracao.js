
			var objTab = null;

			addEvent(window, "load", load, false);

			function load() {
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				}
			}

			function excluir() {
				if (document.getElementById("nome").value == "") {
					alert(i18n_message("citcorpore.comum.necessarioSelecionarRegistro"));
				}else if(document.getElementById("nome").value != ""){
					if (confirm(i18n_message("citcorpore.comum.deleta"))) {
						document.form.fireEvent("excluirTipoItemConfiguracao");
					}
				}
			}

			function LOOKUP_TIPOITEMCONFIGURACAO_select(idTipo, desc) {

				JANELA_AGUARDE_MENU.show();
				document.form.restore( {
					id : idTipo
				});
			}

			function LOOKUP_CARACTERISTICA_select(id, desc) {
				var tabela = document.getElementById('tabelaCaracteristica');
				var lastRow = tabela.rows.length;
				if (lastRow > 2) {
					var arrayIdCaracteristica = document.form.idCaracteristica;
					for ( var i = 0; i < arrayIdCaracteristica.length; i++) {
						if (arrayIdCaracteristica[i].value == id) {
							alert(i18n_message('tipoItemConfiguracao.jaAdicionadaCaracteristicas'));
							return;
						}
					}
				} else if (lastRow == 2) {
					var idCaracteristica = document.form.idCaracteristica;
					if (idCaracteristica.value == id) {
						alert(i18n_message('tipoItemConfiguracao.jaAdicionadaCaracteristicas'));
						return;
					}
				}

				insereRow(id, desc);

				$("#POPUP_CARACTERISTICA").dialog("close");
				exibeGrid();
			}


			var popupA;
		    addEvent(window, "load", load, false);
		    function load(){
				popupA = new PopupManager(600, 450, ctx + "/pages/");
				document.form.afterRestore = function () {
					$('.tabs').tabs('select', 0);
				}
		    }

	       /*desenvolvedor: rcs (Rafael César Soyer)
            data: 12/12/2014
            A função reinicia, a partir de 0, a variável global 'countCaracteristica'
           */
		    function reiniciaVarCountCaracteristica(){
		    	countCaracteristica = 0;
			}


           /*desenvolvedor: rcs (Rafael César Soyer)
            data: 16/12/2014
           */
            function decrementaVarCountCaracteristica(){
                countCaracteristica--;
            }

            /*desenvolvedor: rcs (Rafael César Soyer)
            data: 17/12/2014
            */
            function setVarCountCaracteristica(novoValorCountCaracteristica){
            	countCaracteristica = novoValorCountCaracteristica;
            }

			var countCaracteristica = 0;
			function insereRow(id, desc) {
				var tabela = document.getElementById('tabelaCaracteristica');
				var lastRow = tabela.rows.length;

				var row = tabela.insertRow(lastRow);

				setVarCountCaracteristica(lastRow);

				var valor = desc.split(' - ');

				var coluna = row.insertCell(0);
				coluna.innerHTML = '<img id="imgExcluiCaracteristica' + countCaracteristica + '" style="cursor: pointer;" title="Excluir Característica!" src="' + ctx +'/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaCaracteristica\', this.parentNode.parentNode.rowIndex);">';

				coluna = row.insertCell(1);
				coluna.innerHTML = valor[0] + '<input type="hidden" id="idCaracteristica' + countCaracteristica + '" name="idCaracteristica" value="' + id + '" />';

				coluna = row.insertCell(2);
				coluna.innerHTML = valor[1];

				coluna = row.insertCell(3);
				coluna.innerHTML = valor[2]
			}

			function restoreRow() {
				var tabela = document.getElementById('tabelaCaracteristica');
				var lastRow = tabela.rows.length;
				var row = tabela.insertRow(lastRow);

				setVarCountCaracteristica(lastRow);

				var coluna = row.insertCell(0);
				coluna.innerHTML = '<img id="imgExcluiCaracteristica' + countCaracteristica	+ '" style="cursor: pointer;" title="Excluir Característica!" src="'+ctx+'/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaCaracteristica\', this.parentNode.parentNode.rowIndex);">';

				coluna = row.insertCell(1);
				coluna.innerHTML = '<input type="hidden" id="idCaracteristica' + countCaracteristica + '" name="idCaracteristica"/><input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="caracteristica' + countCaracteristica + '" name="caracteristica"/>';

				coluna = row.insertCell(2);
				coluna.innerHTML = '<input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="tagCaracteristica' + countCaracteristica + '" name="tagCaracteristica"/>';

				coluna = row.insertCell(3);
				coluna.innerHTML = '<input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="descricao' + countCaracteristica + '" name="descricao"/>';
			}

			var seqSelecionada = '';
			function setRestoreCaracteristica(idCaracteristica, caracteristica, tag, valorString,
					descricao, idEmpresa, dataInicio, dataFim) {
				if (seqSelecionada != '') {
					eval('document.form.idCaracteristica' + seqSelecionada + '.value = "' + idCaracteristica + '"');
					eval('document.form.caracteristica' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + caracteristica + '\')');
					eval('document.form.tagCaracteristica' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + tag + '\')');
					eval('document.form.descricao' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + descricao + '\')');
				}
				exibeGrid();
			}

			function removeLinhaTabela(idTabela, rowIndex){
				if (confirm(i18n_message("tipoItemConfiguracao.excluirCaracteristicas"))) {
					document.form.caracteristicaSerializada.value = $('#idCaracteristica' + rowIndex).attr('value');

					HTMLUtils.deleteRow(idTabela, rowIndex);

					document.form.fireEvent("excluirAssociacaoCaracteristicaTipoItemConfiguracao");

					reiniciaTagsId();

					decrementaVarCountCaracteristica();
				}
			}

			/*desenvolvedor: rcs (Rafael César Soyer)
			  data: 12/12/2014
			  A função reinicia, a partir de 1, o numero que acompanha as tag's id
			  Inicialmente, foi pensado para ser usado logo após uma remoção de uma linha da tabela característica.
			*/
			function reiniciaTagsId(){
                var lenghtTableCaracteristicas = document.getElementById('tabelaCaracteristica').rows.length - 1;

                var countCaracteristicasExcluidas;

                for (var countTodasCaracteristicas = 1; countTodasCaracteristicas <= lenghtTableCaracteristicas; countTodasCaracteristicas++){

                   countCaracteristicasExcluidas = countTodasCaracteristicas;

                   while ( !($('#idCaracteristica' + countCaracteristicasExcluidas).attr('value')) ){
                	   countCaracteristicasExcluidas++;
                   }

                   $("#idCaracteristica" + countCaracteristicasExcluidas).attr("id","idCaracteristica" + countTodasCaracteristicas);
                   $("#imgExcluiCaracteristica" + countCaracteristicasExcluidas).attr("id","imgExcluiCaracteristica" + countTodasCaracteristicas);
                   $("#tagCaracteristica" + countCaracteristicasExcluidas).attr("id","tagCaracteristica" + countTodasCaracteristicas);
                   $("#descricao" + countCaracteristicasExcluidas).attr("id","descricao" + countTodasCaracteristicas);
               }
			}

			function deleteAllRows() {
				var tabela = document.getElementById('tabelaCaracteristica');
				var count = tabela.rows.length;

				while (count > 1) {
					tabela.deleteRow(count - 1);
					count--;
				}
				ocultaGrid();
			}

			function gravar() {
				var tabela = document.getElementById('tabelaCaracteristica');
				var count = tabela.rows.length;
				var contadorAux = 0;
				var caracteristicas = new Array();

				for ( var i = 1; i <= count; i++) {
					var trObj = document.getElementById('idCaracteristica' + i);

					if (!trObj) {
						continue;
					}

					caracteristicas[contadorAux] = getCaracteristica(i);
					contadorAux = contadorAux + 1;
				}

				serializa();
				document.form.save ();
			}

			var seqSelecionada = '';
			var aux = '';
			serializa = function() {
				var tabela = document.getElementById('tabelaCaracteristica');
				var count = tabela.rows.length;
				var contadorAux = 0;
				var caracteristicas = new Array();
				for ( var i = 1; i <= count; i++) {
					var trObj = document.getElementById('idCaracteristica' + i);

					if (!trObj) {
						continue;
					}

					caracteristicas[contadorAux] = getCaracteristica(i);
					contadorAux = contadorAux + 1;
				}
				var caracteristicasSerializadas = ObjectUtils
						.serializeObjects(caracteristicas);
				document.form.caracteristicasSerializadas.value = caracteristicasSerializadas;
				return true;
			}

			getCaracteristica = function(seq) {
				var CaracteristicaDTO = new CIT_CaracteristicaDTO();
				CaracteristicaDTO.sequencia = seq;
				CaracteristicaDTO.idCaracteristica = eval('document.form.idCaracteristica' + seq + '.value');
				return CaracteristicaDTO;
			}

			$(function() {
				$("#POPUP_CARACTERISTICA").dialog( {
					autoOpen : false,
					width : 600,
					height : 400,
					modal : true
				});
			});

			$(function() {
				$("#addCaracteristica").click(function() {
					$("#POPUP_CARACTERISTICA").dialog("open");
				});
			});

			function limpar() {
				deleteAllRows();
				document.getElementById('gridCaracteristica').style.display = 'none';
				document.form.clear();
				bloquearTag(false);
			}

			function exibeGrid() {
				document.getElementById('gridCaracteristica').style.display = 'block';
			}

			function ocultaGrid() {
				document.getElementById('gridCaracteristica').style.display = 'none';
			}

			function bloquearTag(valor){
				document.getElementById('tag').readOnly  = valor;
			}
		
