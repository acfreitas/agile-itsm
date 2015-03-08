			var objTab = null;
			var contadorGlobal = 1;

			addEvent(window, "load", load, false);

			function load() {
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				}

				$("#POPUP_GRUPO").dialog({
					autoOpen : false,
					width : 620,
					height : 400,
					modal : true
				});

				$("#POPUP_ADICIONARLIMITE").dialog({
					autoOpen : false,
					width : 1050,
					height : 310,
					modal : true
				});
			}

			function LOOKUP_ALCADA_select(id, desc) {
				document.form.restore({
					idAlcada : id
				});
			}

			function LOOKUP_GRUPO_select(id, desc) {
				document.getElementById("idGrupoLimite").value = id;

			    var valor = desc.split('-');
			    var nome = "";
				for(var i = 0 ; i < valor.length; i++){
					if(i < (valor.length - 1)){
						nome += valor[i];
					}
					if(i < (valor.length - 2) && valor.length > 2){
						nome += "-";
					}
				}
				document.getElementById("grupoLimite").value = nome;
				fecharPopupGrupo();
			}

			function excluir() {
				if (document.getElementById("idAlcada").value != "") {
					if (confirm(i18n_message("citcorpore.comum.deleta"))) {
						document.form.fireEvent("delete");
					}
				}
			}

			$(function() {
				$("#addGrupoItemConfig").click(function() {
					$("#POPUP_ADICIONARLIMITE").dialog("open");
				});
			});

			function pesquisaGrupos(){
				$("#POPUP_GRUPO").dialog("open");
			}

			function fecharPopupGrupo(){
				$("#POPUP_GRUPO").dialog("close");
			}

			function adicionarLimite(){
				var idGrupoLimite = document.getElementById("idGrupoLimite").value;
				var nomeGrupoLimite = document.getElementById("grupoLimite").value;
				var tipoLimite = document.getElementById("tipoLimite").value;
				var abrangenciaCentroCustoLimite = document.getElementById("abrangenciaCentroCustoLimite").value;
				var limiteItemUsoInterno = document.getElementById("limiteItemUsoInterno").value;
				var limiteMensalUsoInterno = document.getElementById("limiteMensalUsoInterno").value;
                var limiteItemAtendCliente = document.getElementById("limiteItemAtendCliente").value;
                var limiteMensalAtendCliente = document.getElementById("limiteMensalAtendCliente").value;
				var situacaoLimite = "";

				var radioSituacao = document.formLimite.situacaoLimite;
				var radioLength = radioSituacao.length;
				for(var i = 0; i < radioLength; i++) {
					if(radioSituacao[i].checked) {
						situacaoLimite = radioSituacao[i].value;
					}
				}

				if ( idGrupoLimite < 1 ) {
					alert(i18n_message("grupo.grupo") + ": " + i18n_message("alcada.limite.campo_obrigatorio"));
					return;
				}

				if ( situacaoLimite == "" ) {
					alert(i18n_message("citcorpore.comum.situacao") + ": " + i18n_message("alcada.limite.campo_obrigatorio"));
					return;
				}
				addLinhaTabelaLimite(idGrupoLimite, nomeGrupoLimite, tipoLimite, abrangenciaCentroCustoLimite, limiteItemUsoInterno, limiteMensalUsoInterno, limiteItemAtendCliente, limiteMensalAtendCliente, situacaoLimite);
			}

			function addLinhaTabelaLimite(idGrupoLimite, nomeGrupoLimite, tipoLimite, abrangenciaCentroCustoLimite, limiteItemUsoInterno, limiteMensalUsoInterno, limiteItemAtendCliente, limiteMensalAtendCliente, situacaoLimite){
				var tbl = document.getElementById('tabelaLimites');
				tbl.style.display = 'block';
				var tamanhoTabela = tbl.rows.length;
				var nomeTipoLimite = "";
				var nomeAbrangenciaCentroCustoLimite = "";
				var nomeSituacaoLimite = "";

				if (document.getElementById('idGrupoLimite_'+idGrupoLimite) != null) {
					alert(i18n_message("alcada.limite.existe_limite_grupo"));
					return;
				}

				if (tipoLimite == "F") {
					nomeTipoLimite = i18n_message("alcada.limite.faixaValores");
				} else if (tipoLimite == "Q") {
					nomeTipoLimite = i18n_message("alcada.limite.qualquerValor");
				}

				if (abrangenciaCentroCustoLimite == "T") {
					nomeAbrangenciaCentroCustoLimite = i18n_message("alcada.limite.todos");
				} else if (abrangenciaCentroCustoLimite == "R") {
					nomeAbrangenciaCentroCustoLimite = i18n_message("alcada.limite.somenteResponsal");
				}

				if (situacaoLimite == "A") {
					nomeSituacaoLimite = i18n_message("citcorpore.comum.ativo");
				} else if (situacaoLimite == "I") {
					nomeSituacaoLimite = i18n_message("citcorpore.comum.inativo");
				}

				var row = tbl.insertRow(tamanhoTabela);
				var coluna = row.insertCell(0);
				coluna.innerHTML = '<img id="imgDelLimite' + contadorGlobal + '" style="cursor: pointer;" title="'+i18n_message("citcorpore.comum.excluir")+'" src="'+ctx+'/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaLimites\', this.parentNode.parentNode.rowIndex);">';
				coluna = row.insertCell(1);
				coluna.innerHTML = nomeGrupoLimite + '<input type="hidden" id="idGrupoLimite' + contadorGlobal + '" name="idGrupoLimite" value="' + idGrupoLimite + '" />' + '<input type="hidden" id="idGrupoLimite_' + idGrupoLimite + '" name="idGrupoLimite" value="' + idGrupoLimite + '" />';
				coluna = row.insertCell(2);
				coluna.innerHTML = nomeTipoLimite + '<input type="hidden" id="idTipoLimite' + contadorGlobal + '" name="idTipoLimite" value="' + tipoLimite + '" />';
				coluna = row.insertCell(3);
				coluna.innerHTML = nomeAbrangenciaCentroCustoLimite + '<input type="hidden" id="idAbrangenciaCentroCustoLimite' + contadorGlobal + '" name="idAbrangenciaCentroCustoLimite" value="' + abrangenciaCentroCustoLimite + '" />';

				coluna = row.insertCell(4);
				coluna.innerHTML = nomeSituacaoLimite + '<input type="hidden" id="idSituacaoLimite' + contadorGlobal + '" name="SituacaoLimite" value="' + situacaoLimite + '" />';

                coluna = row.insertCell(5);
                var limiteStr = ""+limiteItemUsoInterno;
                if (limiteStr == 'null')
                    limiteStr = "&nbsp;";
                coluna.innerHTML = limiteStr + '<input type="hidden" id="idLimiteItemUsoInterno' + contadorGlobal + '" name="idLimiteItemUsoInterno" value="' + limiteStr + '" />' + '<input type="hidden" id="idLimiteItemUsoInterno' + contadorGlobal + '" name="idLimiteItemUsoInterno" value="' + limiteMensalUsoInterno + '" />';

                coluna = row.insertCell(6);
                limiteStr = ""+limiteMensalUsoInterno;
                if (limiteStr == 'null')
                    limiteStr = "&nbsp;";
                coluna.innerHTML = limiteStr + '<input type="hidden" id="idLimiteMensalUsoInterno' + contadorGlobal + '" name="idLimiteMensalUsoInterno" value="' + limiteStr + '" />' + '<input type="hidden" id="idLimiteMensalUsoInterno' + contadorGlobal + '" name="idLimiteMensalUsoInterno" value="' + limiteMensalUsoInterno + '" />';

                coluna = row.insertCell(7);
                var limiteStr = ""+limiteItemAtendCliente;
                if (limiteStr == 'null')
                    limiteStr = "&nbsp;";
                coluna.innerHTML = limiteStr + '<input type="hidden" id="idLimiteItemAtendCliente' + contadorGlobal + '" name="idLimiteItemAtendCliente" value="' + limiteStr + '" />' + '<input type="hidden" id="idLimiteItemAtendCliente' + contadorGlobal + '" name="idLimiteItemAtendCliente" value="' + limiteMensalUsoInterno + '" />';

                coluna = row.insertCell(8);
                limiteStr = ""+limiteMensalAtendCliente;
                if (limiteStr == 'null')
                    limiteStr = "&nbsp;";
                coluna.innerHTML = limiteStr + '<input type="hidden" id="idLimiteMensalAtendCliente' + contadorGlobal + '" name="idLimiteMensalAtendCliente" value="' + limiteStr + '" />' + '<input type="hidden" id="idLimiteMensalAtendCliente' + contadorGlobal + '" name="idLimiteMensalAtendCliente" value="' + limiteMensalUsoInterno + '" />';

                $("#POPUP_ADICIONARLIMITE").dialog("close");
				document.formLimite.clear();
				setSituacaoLimite();
				contadorGlobal++;
			}

			function desabilitaValores(){
				var tipo = document.getElementById('tipoLimite').options[document.getElementById('tipoLimite').selectedIndex].value;
				if(tipo == "Q"){
					document.getElementById("limiteItemUsoInterno").value = "";
					document.getElementById("limiteItemUsoInterno").disabled = -1;
                    document.getElementById("limiteItemAtendCliente").value = "";
                    document.getElementById("limiteItemAtendCliente").disabled = -1;
					document.getElementById("divValorMensal").style.display = "none";
					document.getElementById("limiteMensalUsoInterno").value = "";
					document.getElementById("limiteMensalUsoInterno").disabled = -1;
                    document.getElementById("limiteMensalAtendCliente").value = "";
                    document.getElementById("limiteMensalAtendCliente").disabled = -1;
					document.getElementById("divValorItem").style.display = "none";
				}else{
					document.getElementById("limiteItemUsoInterno").disabled = 0;
                    document.getElementById("limiteItemAtendCliente").disabled = 0;
					document.getElementById("divValorItem").style.display = "block";
					document.getElementById("limiteMensalUsoInterno").disabled = 0;
                    document.getElementById("limiteMensalAtendCliente").disabled = 0;
					document.getElementById("divValorMensal").style.display = "block";
				}
			}

			function removeLinhaTabela(idTabela, rowIndex) {
				 if(idTabela == "tabelaLimites"){
						if (confirm(i18n_message("citcorpore.comum.deleta"))) {
							HTMLUtils.deleteRow(idTabela, rowIndex);
						}
				 }
			}

			function LimiteAlcadaDTO(idGrupo, tipoLimite, abrangenciaCentroCusto, limiteItemUsoInterno, limiteMensalUsoInterno, limiteItemAtendCliente, limiteMensalAtendCliente, situacao){
				this.idGrupo = idGrupo;
		 		this.tipoLimite = tipoLimite;
		 		this.abrangenciaCentroCusto = abrangenciaCentroCusto;
		 		this.limiteItemUsoInterno = limiteItemUsoInterno;
		 		this.limiteItemAtendCliente = limiteItemAtendCliente;
                this.limiteMensalUsoInterno = limiteMensalUsoInterno;
                this.limiteMensalAtendCliente = limiteMensalAtendCliente;
		 		this.situacao = situacao;
		 	}

			function serializaLimite() {
				var tabela = document.getElementById('tabelaLimites');
				var count = tabela.rows.length;
				var listaLimites = [];

				for ( var i = 1; i < contadorGlobal; i++) {
					if (document.getElementById('idGrupoLimite' + i) != "" && document.getElementById('idGrupoLimite' + i) != null) {
						var idGrupoLimite = document.getElementById('idGrupoLimite' + i).value;
						var idTipoLimite = document.getElementById('idTipoLimite' + i).value;
						var idAbrangenciaCentroCustoLimite = document.getElementById('idAbrangenciaCentroCustoLimite' + i).value;
						var idLimiteItemUsoInterno = document.getElementById('idLimiteItemUsoInterno' + i).value;
						var idLimiteMensalUsoInterno = document.getElementById('idLimiteMensalUsoInterno' + i).value;
				        var idLimiteItemAtendCliente = document.getElementById('idLimiteItemAtendCliente' + i).value;
	                    var idLimiteMensalAtendCliente = document.getElementById('idLimiteMensalAtendCliente' + i).value;
	            		var idSituacaoLimite = document.getElementById('idSituacaoLimite' + i).value;
						var limite = new LimiteAlcadaDTO(idGrupoLimite, idTipoLimite, idAbrangenciaCentroCustoLimite, idLimiteItemUsoInterno, idLimiteMensalUsoInterno, idLimiteItemAtendCliente, idLimiteMensalAtendCliente, idSituacaoLimite);
						listaLimites.push(limite);
					}
				}
				document.form.listLimites.value = ObjectUtils.serializeObjects(listaLimites);
				document.form.save();/*
				document.getElementById('tabelaLimites').style.display = "none"; */
			}

			function setSituacao() {
				var radioSituacao = document.form.situacao;
				var radioLength = radioSituacao.length;
				for(var i = 0; i < radioLength; i++) {
					radioSituacao[i].checked = false;
					if(radioSituacao[i].value == "A") {
						radioSituacao[i].checked = true;
					}
				}
			}

			function setSituacaoLimite() {
				var radioSituacao = document.formLimite.situacaoLimite;
				var radioLength = radioSituacao.length;
				for(var i = 0; i < radioLength; i++) {
					radioSituacao[i].checked = false;
					if(radioSituacao[i].value == "A") {
						radioSituacao[i].checked = true;
					}
				}

				document.getElementById("limiteItemUsoInterno").disabled = 0;
                document.getElementById("limiteItemAtendCliente").disabled = 0;
				document.getElementById("divValorItem").style.display = "block";
				document.getElementById("limiteMensalUsoInterno").disabled = 0;
                document.getElementById("limiteMensalAtendCliente").disabled = 0;
				document.getElementById("divValorMensal").style.display = "block";
			}



			function deleteAllRows() {
				var tabela = document.getElementById('tabelaLimites');
				var count = tabela.rows.length;

				while (count > 1) {
					tabela.deleteRow(count - 1);
					count--;
				}
				contadorGlobal = 1;

				document.getElementById('tabelaLimites').style.display = "none";
			}