
			var objTab = null;
			var contTable = 0;
			var contInfo = 0;
			addEvent(window, "load", load, false);
			function load() {
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				}
			}

			//somente numeros
		    jQuery.fn.numbersOnly = function(){
		      var $teclas = {8:'backspace',9:'tab',13:'enter',48:0,49:1,50:2,51:3,52:4,53:5,54:6,55:7,56:8,57:9};
		      $(this).keypress(function(e){
		        var keyCode = e.keyCode?e.keyCode:e.which?e.which:e.charCode;
		        if(keyCode in $teclas){
		          return true;
		        }else{
		          return false;
		        }
		      });
		      return $(this);
		    }

			function LOOKUP_CONTROLECONTRATO_select(id, desc) {
				JANELA_AGUARDE_MENU.show();
				document.form.restore({idControleContrato : id});

			}

			function LOOKUP_SOLICITANTE_select(id, desc) {
				document.form.idEmpregadoTreinamento.value = id;
				document.form.nomeInstrutorTreinamentoText.value = desc;
				$("#POPUP_INSTRUTOR").dialog("close");
			}

			function LOOKUP_SOLICITANTEOCORRENCIA_select(id, desc) {
				document.form.idEmpregadoOcorrencia.value = id;
				document.form.usuarioOcorrenciaText.value = desc;
				$("#POPUP_USUARIOOCORRENCIA").dialog("close");
			}

			function LOOKUP_CATALOGOSERVICOCONTRATO_select(id, desc) {
				document.form.idServicoContrato.value = id;
				document.form.fireEvent('adicionaGridServico');
				$("#POPUP_SERVICOCONTRATO").dialog("close");

			}
			function LOOKUP_CONTRATOS_select(id, desc) {
				document.form.nomeContrato.value = desc;
				document.form.idContrato.value = id;
				$("#POPUP_CONTRATO").dialog("close");
			}

			function abrePopupContrato(){
				$("#POPUP_CONTRATO").dialog("open");
			}
			function abrePopupInstrutor(){
				$("#POPUP_INSTRUTOR").dialog("open");

			}
			function abrePopupUsuarioOcorrencia(){
				$("#POPUP_USUARIOOCORRENCIA").dialog("open");

			}
			function abrePopupVersao(){
				$("#POPUP_VERSAO").dialog("open");

			}
			function abrePopupServico(){
					if(StringUtils.isBlank(document.form.colaboradorInstrutor.value)){
						alert(i18n_message("controlecontrato.colaboradorinstrutor"));
						document.form.colaboradorInstrutor.focus();
						return;
					}else{
						$("#POPUP_INSTRUTOR").dialog("open");
					}
			}
			$(function() {
				$("#POPUP_INSTRUTOR").dialog({
					autoOpen : false,
					width : 750,
					height : 600,
					modal : true,
					show: "fade",
					hide: "fade"
				});

				$("#POPUP_USUARIOOCORRENCIA").dialog({
					autoOpen : false,
					width : 750,
					height : 600,
					modal : true,
					show: "fade",
					hide: "fade"
				});

				$("#POPUP_CONTRATO").dialog({
					autoOpen : false,
					width : 750,
					height : 600,
					modal : true,
					show: "fade",
					hide: "fade"
				});
			});

			function setDataEditor(){
				var oEditor = FCKeditorAPI.GetInstance("descOcorrencia") ;
			    oEditor.SetData(document.form.descInfoCatalogoServico.value);
			}
			var oFCKeditor = new FCKeditor("") ;
		    function onInitQuestionario(){
		    }
		    HTMLUtils.addEvent(window, "load", onInitQuestionario, false);

		    function gravar(){

		    	 if(StringUtils.isBlank(document.form.cliente.value)){
		    		alert(i18n_message("citcorpore.controleContrato.cliente"));
		    		document.form.cliente.focus();
		    		return;
		    	}else{

		    	var objVersao = HTMLUtils.getObjectsByTableId('tblVersao');
				document.form.versaoSerialize.value = ObjectUtils.serializeObjects(objVersao)

				var objPag = HTMLUtils.getObjectsByTableId('tblPagamento');
				document.form.pagamentoSerialize.value = ObjectUtils.serializeObjects(objPag)

				var objTreina = HTMLUtils.getObjectsByTableId('tblTreinamento');
				document.form.treinamentoSerialize.value = ObjectUtils.serializeObjects(objTreina)

				var objOcorrencia = HTMLUtils.getObjectsByTableId('tblOcorrencia');
				document.form.ocorrenciaSerialize.value = ObjectUtils.serializeObjects(objOcorrencia)

				var checkModulo = document.form.moduloAtivo;
				var itensCheck = "";
				for (i = 1; i < (checkModulo.length); i++) {
					if (checkModulo[i].checked == true) {
						itensCheck += checkModulo[i].value;
						itensCheck += ";";

					}
				}
				document.form.lstModulos.value = itensCheck;

		    	document.form.save();
		    	 }
		    }
		    function limpar(){
		    	document.form.clear();
				document.form.nomeContrato.disabled = false;
		    	deleteAllRows();
		    	var oEditor = FCKeditorAPI.GetInstance("descCatalogoServico");
			    oEditor.SetData('');

		     }

		    function novoInfoServico(){
		    	document.form.idInfoCatalogoServico.value = "";
		 		document.form.nomeInfoCatalogoServico.value = "";
		 		document.form.rowIndex.value = "";
		    }

		    //ADDROW/UPDATEROW GRID PAGAMENTO
		    addGridPagamento = function() {
		    	if(StringUtils.isBlank(document.form.dataPagamentoText.value) || document.form.dataPagamentoText.value == null){
		    		alert(i18n_message("citcorpore.comum.data"));
		    		document.form.dataPagamentoText.focus();
		    		return;
		    	}
		    	if(StringUtils.isBlank(document.form.parcelaText.value) || document.form.parcelaText.value == null){
		    		alert(i18n_message("citcorpore.controleContrato.parcela"));
		    		document.form.parcelaText.focus();
		    		return;
		    	}
		        if( document.getElementById('rowIndex').value == null ||  document.getElementById('rowIndex').value == undefined || document.getElementById('rowIndex').value < 1){
		            var obj = new CIT_ControleContratoPagamentoDTO();
		             obj.dataCcPagamento = document.form.dataPagamentoText.value;
		             obj.parcelaCcPagamento = document.form.parcelaText.value;
		             obj.dataAtrasoCcPagamento = document.form.dataAtrasoText.value;

		            HTMLUtils.addRow('tblPagamento', document.form, null, obj, ['','dataCcPagamento', 'parcelaCcPagamento', 'dataAtrasoCcPagamento'], null, '', [gerarButtonDeletePagamento], funcaoClickRowPagamento, null, false);
		            document.form.dataPagamentoText.value = "";
			           document.form.parcelaText.value = "";
			           document.form.dataAtrasoText.value = "";
			           novoItem();
		        } else {
			        var obj = HTMLUtils.getObjectByTableIndex('tblPagamento', document.getElementById('rowIndex').value);
			        obj.dataCcPagamento = document.form.dataPagamentoText.value;
		            obj.parcelaCcPagamento = document.form.parcelaText.value;
		            obj.dataAtrasoCcPagamento = document.form.dataAtrasoText.value;

			        HTMLUtils.updateRow('tblPagamento', document.form, null, obj, ['','dataCcPagamento', 'parcelaCcPagamento','dataAtrasoCcPagamento'], null, '', [gerarButtonDeletePagamento], funcaoClickRowPagamento, null, document.getElementById('rowIndex').value, false);
			       document.form.dataPagamentoText.value = "";
		           document.form.parcelaText.value = "";
		           document.form.dataAtrasoText.value = "";
		           novoItem();
		        }
		     //  limpaDadosTableInfo();
		        HTMLUtils.applyStyleClassInAllCells('tblPagamento', 'celulaGrid');
			}

		    //ADDROW/UPDATEROW GRID TREINAMENTO
		    addGridTreinamento = function() {
		    	if(StringUtils.isBlank(document.form.dataTreinamentoText.value) || document.form.dataTreinamentoText.value == null){
		    		alert(i18n_message("citcorpore.comum.data"));
		    		document.form.dataPagamento.focus();
		    		return;
		    	}
		    	if(StringUtils.isBlank(document.form.nomeTreinamentoText.value) || document.form.nomeTreinamentoText.value == null){
		    		alert(i18n_message("citcorpore.controleContrato.treinamento"));
		    		document.form.parcela.focus();
		    		return;
		    	}
		        if( document.getElementById('rowIndex').value == null ||  document.getElementById('rowIndex').value == undefined || document.getElementById('rowIndex').value < 1){
		            var obj = new CIT_ControleContratoTreinamentoDTO();
		             obj.dataCcTreinamento = document.form.dataTreinamentoText.value;
		             obj.nomeCcTreinamento = document.form.nomeTreinamentoText.value;
		             obj.idEmpregadoTreinamento = document.form.idEmpregadoTreinamento.value;
		             obj.nomeInstrutorCcTreinamento = document.form.nomeInstrutorTreinamentoText.value;

		            HTMLUtils.addRow('tblTreinamento', document.form, null, obj, ['','dataCcTreinamento', 'nomeCcTreinamento','nomeInstrutorCcTreinamento'], null, '', [gerarButtonDeleteTreinamento], funcaoClickRowTreinamento, null, false);
		            document.form.dataTreinamentoText.value = "";
		        	document.form.nomeTreinamentoText.value = "";
		        	document.form.nomeInstrutorTreinamentoText.value = "";
		        	document.form.idEmpregadoTreinamento.value = "";
		        	novoItem()
		        } else {
			        var obj = HTMLUtils.getObjectByTableIndex('tblTreinamento', document.getElementById('rowIndex').value);

			        obj.dataCcTreinamento = document.form.dataTreinamentoText.value;
		            obj.nomeCcTreinamento = document.form.nomeTreinamentoText.value;
		            obj.nomeInstrutorCcTreinamento = document.form.nomeInstrutorTreinamentoText.value;
		            obj.idEmpregadoTreinamento = document.form.idEmpregadoTreinamento.value;

			        HTMLUtils.updateRow('tblTreinamento', document.form, null, obj, ['','dataCcTreinamento', 'nomeCcTreinamento','nomeInstrutorCcTreinamento'], null, '', [gerarButtonDeleteTreinamento], funcaoClickRowTreinamento, null, document.getElementById('rowIndex').value, false);
			        document.form.dataTreinamentoText.value = "";
		        	document.form.nomeTreinamentoText.value = "";
		        	document.form.nomeInstrutorTreinamentoText.value = "";
		        	document.form.idEmpregadoTreinamento.value = "";
		        	novoItem();

		        }
		     //  limpaDadosTableInfo();
		        HTMLUtils.applyStyleClassInAllCells('tblTreinamento', 'celulaGrid');
			}

		    //ADDROW/UPDATEROW GRID OCORRENCIA
		    addGridOcorrencia = function() {
		    	 if(StringUtils.isBlank(document.form.dataOcorrencia.value) || document.form.dataOcorrencia.value == null){
		    		alert(i18n_message("controlecontrato.dataOcorrencia"));
		    		document.form.dataOcorrencia.focus();
		    		return;
		    	}
		    	if(StringUtils.isBlank(document.form.assuntoOcorrencia.value) || document.form.assuntoOcorrencia.value == null){
		    		alert(i18n_message("controlecontrato.assuntoOcorrencia"));
		    		document.form.assuntoOcorrencia.focus();
		    		return;
		    	}
		        if( document.getElementById('rowIndex').value == null ||  document.getElementById('rowIndex').value == undefined || document.getElementById('rowIndex').value < 1){
		            var obj = new CIT_ControleContratoOcorrenciaDTO();
		             obj.dataCcOcorrencia = document.form.dataOcorrenciaText.value;
		             obj.assuntoCcOcorrencia = document.form.assuntoOcorrenciaText.value;
		             obj.empregadoCcOcorrencia = document.form.usuarioOcorrenciaText.value;
		             obj.idEmpregadoOcorrencia = document.form.idEmpregadoOcorrencia.value;

		            HTMLUtils.addRow('tblOcorrencia', document.form, null, obj, ['','dataCcOcorrencia', 'assuntoCcOcorrencia', 'empregadoCcOcorrencia'], null, '', [gerarButtonDeleteOcorrencia], funcaoClickRowOcorrencia, null, false);
		            document.form.dataOcorrenciaText.value = "";
		            document.form.assuntoOcorrenciaText.value = "";
		            document.form.usuarioOcorrenciaText.value = "";
		            document.form.idEmpregadoOcorrencia.value = "";
		            novoItem();
		        } else {
			        var obj = HTMLUtils.getObjectByTableIndex('tblOcorrencia', document.getElementById('rowIndex').value);
			        obj.dataCcOcorrencia = document.form.dataOcorrenciaText.value;
		            obj.assuntoCcOcorrencia = document.form.assuntoOcorrenciaText.value;
		            obj.empregadoCcOcorrencia = document.form.usuarioOcorrenciaText.value;
		            obj.idEmpregadoOcorrencia = document.form.idEmpregadoOcorrencia.value;


			        HTMLUtils.updateRow('tblOcorrencia', document.form, null, obj, ['','dataCcOcorrencia', 'assuntoCcOcorrencia', 'empregadoCcOcorrencia'], null, '', [gerarButtonDeleteOcorrencia], funcaoClickRowOcorrencia, null, document.getElementById('rowIndex').value, false);
			        document.form.dataOcorrenciaText.value = "";
		            document.form.assuntoOcorrenciaText.value = "";
		            document.form.usuarioOcorrenciaText.value = "";
		            document.form.idEmpregadoOcorrencia.value = "";
		            novoItem();
		        }
		     //  limpaDadosTableInfo();
		        HTMLUtils.applyStyleClassInAllCells('tblOcorrencia', 'celulaGrid');
			}

		    addGridVersao = function() {
		    	 if(StringUtils.isBlank(document.form.nomeVersaoText.value) || document.form.nomeVersaoText.value == null){
		    		alert(i18n_message("citcorpore.controleContrato.versao"));
		    		document.form.nomeVersao.focus();
		    		return;
		    	}

		        if( document.getElementById('rowIndex').value == null ||  document.getElementById('rowIndex').value == undefined || document.getElementById('rowIndex').value < 1){
		            var obj = new CIT_ControleContratoVersaoDTO();
		            obj.nomeCcVersao = document.form.nomeVersaoText.value;

		            HTMLUtils.addRow('tblVersao', document.form, null, obj, ['','nomeCcVersao'], null, '', [gerarButtonDeleteVersao], funcaoClickRowVersao, null, false);
		            document.form.nomeVersaoText.value = "";
		            novoItem();
		        } else {
			        var obj = HTMLUtils.getObjectByTableIndex('tblVersao', document.getElementById('rowIndex').value);
			        obj.nomeCcVersao = document.form.nomeVersaoText.value;


			        HTMLUtils.updateRow('tblVersao', document.form, null, obj, ['','nomeCcVersao'], null, '', [gerarButtonDeleteVersao], funcaoClickRowVersao, null, document.getElementById('rowIndex').value, false);
			        document.form.nomeVersaoText.value = "";
		            novoItem();
		        }
		     //  limpaDadosTableInfo();
		        HTMLUtils.applyStyleClassInAllCells('tblVersao', 'celulaGrid');
			}

		    function funcaoClickRowPagamento(row, obj){
		    	if(row == null){
		            document.getElementById('rowIndex').value = null;
		            document.form.clear();
		        }else{
		        	document.getElementById('rowIndex').value = row.rowIndex;

		        	document.form.dataPagamentoText.value = obj.dataCcPagamento;
		        	document.form.parcelaText.value = obj.parcelaCcPagamento;
		        	document.form.dataAtrasoText.value = obj.dataAtrasoCcPagamento;

		        }
		    }

		    function funcaoClickRowPagamentoRes(row, obj){
		    	if(row == null){
		            document.getElementById('rowIndex').value = null;
		            document.form.clear();
		        }else{
		        	document.getElementById('rowIndex').value = row.rowIndex;

		        	document.form.dataPagamento.value = obj.dataCcPagamento;
		        	document.form.parcela.value = obj.parcelaCcPagamento;
		        	document.form.dataAtraso.value = obj.dataAtrasoCcPagamento;

		        }
		    }
		    function funcaoClickRowTreinamento(row, obj){
		    	if(row == null){
		            document.getElementById('rowIndex').value = null;
		            document.form.clear();
		        }else{
		        	document.getElementById('rowIndex').value = row.rowIndex;

		        	document.form.dataTreinamentoText.value = obj.dataCcTreinamento;
		        	document.form.nomeTreinamentoText.value = obj.nomeCcTreinamento;
		        	document.form.nomeInstrutorTreinamentoText.value = obj.nomeInstrutorCcTreinamento;
		        	document.form.idEmpregadoTreinamento.value = obj.idEmpregadoTreinamento;

		        }
		    }
		    function funcaoClickRowTreinamentoRes(row, obj){
		    	if(row == null){
		            document.getElementById('rowIndex').value = null;
		            document.form.clear();
		        }else{
		        	document.getElementById('rowIndex').value = row.rowIndex;

		        	document.form.dataTreinamentoText.value = obj.dataCcTreinamento;
		        	document.form.nomeTreinamentoText.value = obj.nomeCcTreinamento;
		        	document.form.nomeInstrutorTreinamentoText.value = obj.nomeInstrutorCcTreinamento;
		        	document.form.idEmpregadoTreinamento.value = obj.idEmpregadoTreinamento;

		        }
		    }

		    function funcaoClickRowOcorrencia(row, obj){
		    	if(row == null){
		            document.getElementById('rowIndex').value = null;
		            document.form.clear();
		        }else{
		        	document.getElementById('rowIndex').value = row.rowIndex;

		        	document.form.dataOcorrenciaText.value = obj.dataCcOcorrencia;
		        	document.form.assuntoOcorrenciaText.value = obj.assuntoCcOcorrencia;
		        	document.form.usuarioOcorrenciaText.value = obj.empregadoCcOcorrencia;
		        	document.form.idEmpregadoOcorrencia.value = obj.idEmpregadoOcorrencia;


		        }
		    }
		    function funcaoClickRowOcorrenciaRes(row, obj){
		    	if(row == null){
		            document.getElementById('rowIndex').value = null;
		            document.form.clear();
		        }else{
		        	document.getElementById('rowIndex').value = row.rowIndex;

		        	document.form.dataOcorrenciaText.value = obj.dataCcOcorrencia;
		        	document.form.assuntoOcorrenciaText.value = obj.assuntoCcOcorrencia;
		        	document.form.usuarioOcorrenciaText.value = obj.empregadoCcOcorrencia;


		        }
		    }
		    function funcaoClickRowVersaoRes(row, obj){
		    	if(row == null){
		            document.getElementById('rowIndex').value = null;
		            document.form.clear();
		        }else{
		        	document.getElementById('rowIndex').value = row.rowIndex;

		        	document.form.nomeVersaoText.value = obj.nomeCcVersao;
		        }
		    }
		    function funcaoClickRowVersao(row, obj){
		    	if(row == null){
		            document.getElementById('rowIndex').value = null;
		            document.form.clear();
		        }else{
		        	document.getElementById('rowIndex').value = row.rowIndex;

		        	document.form.nomeVersaoText.value = obj.nomeCcVersao;
		        }
		    }
		    function deleteAllRows() {
				var tabela = document.getElementById('tblPagamento');
				var tabela1 = document.getElementById('tblTreinamento');
				var tabela2 = document.getElementById('tblOcorrencia');
				var tabela3 = document.getElementById('tblVersao');
				var count = tabela.rows.length;
				var count1 = tabela1.rows.length;
				var count2 = tabela2.rows.length;
				var count3 = tabela3.rows.length;

				while (count > 1) {
					tabela.deleteRow(count - 1);
					count--;
				}
				while (count1 > 1) {
					tabela1.deleteRow(count1 - 1);
					count1--;
				}
				while (count2 > 1) {
					tabela2.deleteRow(count2 - 1);
					count2--;
				}
				while (count3 > 1) {
					tabela3.deleteRow(count3 - 1);
					count3--;
				}
			}

		    function novoItem(){
		 		document.form.rowIndex.value = "";
		    }

			function gerarButtonDeletePagamento(row) {
				row.cells[0].innerHTML = '<img id="imgDelInfo" style="cursor: pointer;"  title="'+i18n_message("citcorpore.comum.excluir")+'" src="'+ctx+'/imagens/delete.png" onclick="deleteLinha(\'tblPagamento\', this.parentNode.parentNode.rowIndex);">'
			}
			function gerarButtonDeleteTreinamento(row) {
				row.cells[0].innerHTML = '<img id="imgDelInfo" style="cursor: pointer;"  title="'+i18n_message("citcorpore.comum.excluir")+'" src="'+ctx+'/imagens/delete.png" onclick="deleteLinha(\'tblTreinamento\', this.parentNode.parentNode.rowIndex);">'
			}
			function gerarButtonDeleteOcorrencia(row) {
				row.cells[0].innerHTML = '<img id="imgDelInfo" style="cursor: pointer;"  title="'+i18n_message("citcorpore.comum.excluir")+'" src="'+ctx+'/imagens/delete.png" onclick="deleteLinha(\'tblOcorrencia\', this.parentNode.parentNode.rowIndex);">'
			}
			function gerarButtonDeleteVersao(row) {
				row.cells[0].innerHTML = '<img id="imgDelInfo" style="cursor: pointer;"  title="'+i18n_message("citcorpore.comum.excluir")+'" src="'+ctx+'/imagens/delete.png" onclick="deleteLinha(\'tblVersao\', this.parentNode.parentNode.rowIndex);">'
			}
			function deleteLinha(table, index){
				HTMLUtils.deleteRow(table, index);
			//	limpaDadosTableInfo();
			}
			geraCheckButt = function(row, obj){
				row.cells[0].innerHTML = '<input type="checkbox" name="moduloAtivo" id="moduloAtivo'+obj.idModuloSistema+'" onclick="selecionaModuloAtivo('+ obj.idModuloSistema +')" value="'+ obj.idModuloSistema +'" />';
			}
			function excluir() {
				if (document.getElementById("idControleContrato").value != "") {
					if (confirm(i18n_message("citcorpore.comum.deleta"))) {
						document.form.fireEvent("delete");
					}
				}
			}

			jQuery(function($){
				$('#telefone1').mask('(999) 9999-9999');
				$('#telefone2').mask('(999) 9999-9999');
		    });

		
