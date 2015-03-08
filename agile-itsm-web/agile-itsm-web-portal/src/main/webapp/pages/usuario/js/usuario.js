
	    var contGrupo = 0;
	    var popup;
	    
	    $(window).load(function() {
	    	
			popup = new PopupManager(1000, 900, ctx+"/pages/");
			document.form.afterRestore = function () {
				$('.tabs').tabs('select', 0);
			}
	    	
					
		});
				
				
				
		function LOOKUP_USUARIO_select(id,desc){
			document.form.restore({idUsuario:id});

			$("#divGrupo").show();
			$("#divSenha").hide();
		}

		function LOOKUP_EMPREGADO_USUARIO_select(id, desc){
			document.form.idEmpregado.value = id;
			document.form.fireEvent("restoreEmpregado");

			$("#divGrupo").show();
		}

		function fecharPopup(){
			$('.tabs').tabs('select', 0);

		}

		function validar(){

			document.form.colGrupoSerialize.value = '';

			var objOcorrencia = HTMLUtils.getObjectsByTableId('tabelaGrupo');
			document.form.colGrupoSerialize.value = ObjectUtils.serializeObjects(objOcorrencia);

			var idUsuario = document.getElementById("idUsuario").value;
			var ldap = document.getElementById("ldap").value;
	    	var senha = document.getElementById("senha").value;
	    	var senha2 = document.getElementById("senhaNovamente").value;

	    	if (idUsuario != null && idUsuario != ""){
	    		//N�o � um usu�rio LDAP
		    	if (ldap != null && ldap == "S"){
		    		document.form.save();
			    }else{
					//� um usu�rio j� cadastrado no BD
					if ($('#divSenha').is(':visible')) {
						if( senha === null || senha === undefined || senha === ""){
							alert(i18n_message("candidato.senhaObrigatorio"));
						} else if (senha2 === null || senha2 === undefined || senha2 === ""){
							alert(i18n_message("candidato.repetirSenha"));
						} else {
							if (senha === senha2){
								document.form.save();
							}else{
								alert(i18n_message("usuario.senhaDiferente"));
								document.getElementById("senha").value = "";
								document.getElementById("senhaNovamente").value = "";
								document.getElementById("senha").focus();
							}
						}
					}else{
						document.form.save();
					}
			   }
			}else{
				if( senha === null || senha === undefined || senha === ""){
					alert(i18n_message("candidato.senhaObrigatorio"));
				} else if (senha2 === null || senha2 === undefined || senha2 === ""){
					alert(i18n_message("candidato.repetirSenha"));
				} else {
					if (senha === senha2){
						document.form.save();
					}else{
						alert(i18n_message("usuario.senhaDiferente"));
						document.getElementById("senha").value = "";
						document.getElementById("senhaNovamente").value = "";
						document.getElementById("senha").focus();
					}
				}
			}
		}

		function excluir() {
			if (document.getElementById("idUsuario").value != "") {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					document.form.fireEvent("delete");
				}
			}
		}

		$(function() {
			$("#POPUP_EMPREGADO").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});

			$("#POPUP_GRUPO").dialog({
				autoOpen : false,
				width : 650,
				height : 400,
				modal : true,
			});

			$("#divAlterarSenha").hide();
			$("#divGrupo").hide();
		});

		function alterarSenha(){
			$("#divSenha").show();
		}

		$(function() {
			$("#addEmpregado").click(function() {
				$("#POPUP_EMPREGADO").dialog("open");
			});
		});

	 	function fecharPopup(){
			$("#POPUP_EMPREGADO").dialog("close");
		}

	 	function limpar(){
	 		deleteAllRows();
			$("#divSenha").show();
			$("#divAlterarSenha").hide();
			$("#divGrupo").hide();
	 		document.form.clear();
	 	}

	 	function fecharAddSolicitante(){
			$("#popupCadastroRapido").dialog('close');
		}

	 	var nomeGrupo;
	 	function LOOKUP_GRUPO_EVENTO_select(id, desc){
	 		$("#divGrupo").show();
	 		document.form.idGrupo.value = id;
	 		nomeGrupo = desc;
	 		addGridGrupo();
	 		$("#POPUP_GRUPO").dialog("close");
		}


	    addGridGrupo = function() {
	    	var tbl = document.getElementById('tabelaGrupo');
			tbl.style.display = 'block';
			var lastRow = tbl.rows.length;

	        if( document.getElementById('rowIndex').value == null ||  document.getElementById('rowIndex').value == undefined || document.getElementById('rowIndex').value < 1){
	            var obj = new CIT_GrupoDTO();
	            obj.idGrupo = document.form.idGrupo.value;
	            obj.nome = nomeGrupo;

	            HTMLUtils.addRow('tabelaGrupo', document.form, null, obj, ['','idGrupo','nome'], ['idGrupo'], i18n_message("citcorpore.comum.registroJaAdicionado"), [gerarButtonDeleteGrupo], null, null, false);
	            nomeGrupo = "";
	            document.form.idGrupo.value = "";
	            novoItem();
	        } else {
		        var obj = HTMLUtils.getObjectByTableIndex('tabelaGrupo', document.getElementById('rowIndex').value);
		        obj.idGrupo = document.form.idGrupo.value;
	            obj.nome = nomeGrupo;

		        HTMLUtils.updateRow('tabelaGrupo', document.form, null, obj, ['','idGrupo','nome'], null, '', [gerarButtonDeleteGrupo], null, null, document.getElementById('rowIndex').value, false);
		        nomeGrupo = "";
		        document.form.idGrupo.value = "";
	            novoItem();
	        }
	     //  limpaDadosTableInfo();
	        HTMLUtils.applyStyleClassInAllCells('tabelaGrupo', 'celulaGrid');
		}

	 	$(function() {
			$("#addGrupo").click(function() {
				$("#POPUP_GRUPO").dialog("open");
			});
		});

	 	function deleteAllRows() {
			var tabela = document.getElementById('tabelaGrupo');
			var count = tabela.rows.length;

			while (count > 1) {
				tabela.deleteRow(count - 1);
				count--;
			}
			//document.getElementById('tabelaGrupo').style.display = "none";
			contGrupo = 0;
	 	}

	    function gerarButtonDeleteGrupo(row) {
			row.cells[0].innerHTML = '<img id="imgDelInfo" style="cursor: pointer;"  title="'+i18n_message("citcorpore.comum.excluir")+'" src="'+ctx+'/imagens/delete.png" onclick="deleteLinha(\'tabelaGrupo\', this.parentNode.parentNode.rowIndex);">'
		}
	    function novoItem(){
	 		document.form.rowIndex.value = "";
	    }
	    function deleteLinha(table, index){
	    	if (table == "tabelaGrupo"){
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
			HTMLUtils.deleteRow(table, index);
				}
	    	}
		}

	
