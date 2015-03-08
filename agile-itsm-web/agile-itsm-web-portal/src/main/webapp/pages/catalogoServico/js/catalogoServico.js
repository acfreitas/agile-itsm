		var objTab = null;
		var contTable = 0;
		var contInfo = 0;
		addEvent(window, "load", load, false);
		function load() {
			document.form.afterRestore = function() {
				$('.tabs').tabs('select', 0);
			}
		}

		function LOOKUP_CATALOGOSERVICO_select(id, desc) {
			document.form.restore({idcatalogoServico : id});
			document.form.nomeContrato.disabled = true;
		}

		function LOOKUP_CATALOGOSERVICOCONTRATO_select(id, desc) {
			document.form.idServicoContrato.value = id;
			document.form.nomeServicoContrato.value = desc;
			//document.form.fireEvent('adicionaGridServico');
			$("#POPUP_DETALHES").dialog("close");
			deleteAllRowsNovoContrato()

		}
		function LOOKUP_CONTRATOS_select(id, desc) {
			//função para limpar lookup dos serviços
			//deve ser chamado aqui antes de chamar a função abrePopupServico() para que funcione
			limpar_LOOKUP_CATALOGOSERVICOCONTRATO();
			document.form.nomeContrato.value = desc;
			document.form.idContrato.value = id;
			document.formDetalhe.pesqLockupLOOKUP_CATALOGOSERVICOCONTRATO_IDCONTRATO.value = id;
			$("#POPUP_CONTRATO").dialog("close");
			deleteAllRowsNovoContrato();
		}

		function abrePopupContrato(){
			$("#POPUP_CONTRATO").dialog("open");
			document.getElementsByName('btnLimparLOOKUP_CATALOGOSERVICOCONTRATO')[0].style.display = 'none'
			document.getElementsByName('btnTodosLOOKUP_CATALOGOSERVICOCONTRATO')[0].style.display = 'none'
		}
		function abrePopupServico(){
			if(StringUtils.isBlank(document.form.nomeContrato.value)){
				alert(i18n_message("contrato.contrato"));
				document.form.nomeContrato.focus();
				return;
			}else{
				document.form.fireEvent('verificarContratoServico');
				document.getElementsByName('btnLimparLOOKUP_CATALOGOSERVICOCONTRATO')[0].style.display = 'none'
				document.getElementsByName('btnTodosLOOKUP_CATALOGOSERVICOCONTRATO')[0].style.display = 'none'

			}
		}

		function validarContratoServico(){
			alert(i18n_message("condicao.contratoServico"));
		}

		$(function() {
			$("#POPUP_SERVICOCONTRATO").dialog({
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
			$("#POPUP_DETALHES").dialog({
				autoOpen : false,
				width : 750,
				height : 600,
				modal : true,
				show: "fade",
				hide: "fade"
			});
		});

		function setDataEditor(){
		}

	    function gravar(){

			var objs = HTMLUtils.getObjectsByTableId('tblInfoCatalogoServico');
			document.form.infoCatalogoServicoSerialize.value = ObjectUtils.serializeObjects(objs)

	    	document.form.save();

	    }
	    function limpar(){
	    	document.form.clear();
			document.form.nomeContrato.disabled = false;
	    	deleteAllRows();
	     }

	    function novoInfoServico(){
	    	document.form.idInfoCatalogoServico.value = "";
	    	document.form.nomeServicoContrato.value = "";
	 		document.form.nomeInfoCatalogoServico.value = "";
	 		document.form.rowIndex.value = "";
	    }

	    addItemInfo = function() {
	    	if(StringUtils.isBlank(document.form.nomeCatalogoServico.value) || document.form.nomeCatalogoServico.value == null){
	    		alert(i18n_message("catalogoServico.nomeItemInformacaoCatalogo"));
	    		document.form.nomeCatalogoServico.focus();
	    		return;
	    	}
	    	if(StringUtils.isBlank(document.form.nomeServicoContrato.value) || document.form.nomeServicoContrato.value == null){
	    		alert(i18n_message("catalogoServico.informeServico"));
	    		document.form.nomeServicoContrato.focus();
	    		return;
	    	}

	        if( document.getElementById('rowIndex').value == null ||  document.getElementById('rowIndex').value == undefined || document.getElementById('rowIndex').value < 1){
	            var obj = new CIT_InfoCatalogoServicoDTO();
	             obj.nomeInfoCatalogoServico = document.form.nomeCatalogoServico.value;
	             obj.nomeServicoContrato = document.form.nomeServicoContrato.value;
	             obj.idServicoCatalogo = document.form.idServicoContrato.value;

	             obj.descInfoCatalogoServico = document.form.descCatalogoServico.value;

	            HTMLUtils.addRow('tblInfoCatalogoServico', document.form, null, obj, ['','idServicoCatalogo', 'nomeServicoContrato', 'nomeInfoCatalogoServico', 'descInfoCatalogoServico'],["idServicoCatalogo"], "Serviço já adicionado!", [gerarButtonDelete2], funcaoClickRow, null, false);

	        } else {
		        var obj = HTMLUtils.getObjectByTableIndex('tblInfoCatalogoServico', document.getElementById('rowIndex').value);
		        obj.nomeInfoCatalogoServico = document.form.nomeCatalogoServico.value;
		        obj.nomeServicoContrato = document.form.nomeServicoContrato.value;
	            obj.idServicoCatalogo = document.form.idServicoContrato.value;
	            obj.descInfoCatalogoServico = document.form.descCatalogoServico.value;

		        HTMLUtils.updateRow('tblInfoCatalogoServico', document.form, null, obj, ['','idServicoCatalogo', 'nomeServicoContrato', 'nomeInfoCatalogoServico', 'descInfoCatalogoServico'],null, '', [gerarButtonDelete2], funcaoClickRow, null, document.getElementById('rowIndex').value, false);
	        }
	        limpaDadosTableInfo();
	        HTMLUtils.applyStyleClassInAllCells('tblInfoCatalogoServico', 'celulaGrid');

		}

	    function funcaoClickRow(row, obj){
	    	if(row == null){
	            document.getElementById('rowIndex').value = null;
	            document.form.clear();
	        }else{
	        	document.getElementById('rowIndex').value = row.rowIndex;

	        	document.form.nomeServicoContrato.value = obj.nomeServicoContrato;
	        	document.form.idServicoContrato.value = obj.idServicoCatalogo;
	        	document.form.nomeCatalogoServico.value = obj.nomeInfoCatalogoServico;
	        	document.form.descCatalogoServico.value = obj.descInfoCatalogoServico;

	        }
	    }
	    function deleteAllRows() {
		/* 	var tabela = document.getElementById('tblServicoContrato'); */
			var tabela1 = document.getElementById('tblInfoCatalogoServico');
			//var count = tabela.rows.length;
			var count1 = tabela1.rows.length;

			/* while (count > 1) {
				tabela.deleteRow(count - 1);
				count--;
			} */
			while (count1 > 1) {
				tabela1.deleteRow(count1 - 1);
				count1--;
			}
		}

	    function deleteAllRowsNovoContrato(){
	    /* 	var tabela = document.getElementById('tblServicoContrato'); */
	    	var count = tabela.rows.length;
	    	while (count > 1) {
				tabela.deleteRow(count - 1);
				count--;
			}
	    }

	function limpaDadosTableInfo(){
		document.form.idServicoContrato.value = "";
		document.form.nomeServicoContrato.value = "";
		document.form.nomeCatalogoServico.value = "";
		document.form.descCatalogoServico.value = "";
	}

	function gerarButtonDelete2(row) {
		row.cells[0].innerHTML = '<img id="imgDelInfo" style="cursor: pointer;"  title="'+i18n_message("citcorpore.comum.excluir")+'" src="'+ctx+'/imagens/delete.png" onclick="deleteLinha(\'tblInfoCatalogoServico\', this.parentNode.parentNode.rowIndex);">'
	}
	function deleteLinha(table, index){

		HTMLUtils.deleteRow(table, index);
		limpaDadosTableInfo();
	}
	function excluir() {
		if (document.getElementById("idCatalogoServico").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
				document.form.nomeContrato.disabled = false;
			}
		}
	}
	function contCaracteres(valor) {
	    quant = 255;
	    total = valor.length;
	    if(total <= quant)
	    {
	        resto = quant - total;
	        document.getElementById('cont').innerHTML = resto;
	    }
	    else
	    {
	        document.getElementById('descCatalogoServico').value = valor.substr(0,quant);
	    }
	}