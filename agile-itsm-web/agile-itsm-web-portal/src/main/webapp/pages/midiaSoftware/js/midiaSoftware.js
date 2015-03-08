
    addEvent(window, "load", load, false);
    function load(){

	document.form.afterRestore = function () {
		$('.tabs').tabs('select', 0);
	}
    }
	function LOOKUP_MIDIASOFTWARE_select(id,desc){
		document.form.restore({idMidiaSoftware:id});
	}
	function excluir() {
		if (document.getElementById("idMidiaSoftware").value != "") {
			if (confirm( i18n_message("midiaSoftware.confirme.excluir"))) {
				document.form.fireEvent("update");
			}
		}
	}
	function limpar(){
		document.form.clear();
		deleteAllRows();
	}
	 function deleteAllRows() {
		var tabela = document.getElementById('tblMidiaSoftwareChave');
		var count = tabela.rows.length;

		while (count > 1) {
			tabela.deleteRow(count - 1);
			count--;
		}
	}

	function incrementaLicensas() {
		var tabela = document.getElementById('tblMidiaSoftwareChave');
		var count = tabela.rows.length;
		document.getElementById("licencas").value = count - 1;
	}

	function addItemInfo() {
    	if(StringUtils.isBlank(document.form.chave.value) || document.form.chave.value == null){
    		document.form.chave.focus();
    		return;
    	}

        if( document.getElementById('rowIndex').value == null ||  document.getElementById('rowIndex').value == undefined || document.getElementById('rowIndex').value < 1){
            var obj = new CIT_MidiaSoftwareChaveDTO();
             obj.chave = document.form.chave.value;
             obj.qtdPermissoes = document.form.qtdPermissoes.value;

            HTMLUtils.addRow('tblMidiaSoftwareChave', document.form, null, obj, ['','chave', 'qtdPermissoes'], null, '', [gerarButtonDelete], funcaoClickRow, null, false);
        } else {
	        var obj = HTMLUtils.getObjectByTableIndex('tblMidiaSoftwareChave', document.getElementById('rowIndex').value);
	        obj.chave = document.form.chave.value;
	        obj.qtdPermissoes = document.form.qtdPermissoes.value;
	        HTMLUtils.updateRow('tblMidiaSoftwareChave', document.form, null, obj, ['','chave', 'qtdPermissoes'], null, '', [gerarButtonDelete], funcaoClickRow, null, document.getElementById('rowIndex').value, false);
        }
        limpaDados();
        HTMLUtils.applyStyleClassInAllCells('tblMidiaSoftwareChave', 'celulaGrid');
        incrementaLicensas();

	}
	function funcaoClickRow(row, obj) {
    	if(row == null){
            document.getElementById('rowIndex').value = null;
            document.form.clear();
        }else{
        	document.getElementById('rowIndex').value = row.rowIndex;
        	document.form.chave.value = obj.chave;
        	document.form.qtdPermissoes.value = obj.qtdPermissoes;
        }
    }
	function gerarButtonDelete(row) {
		row.cells[0].innerHTML = '<img id="imgDelServ" style="cursor: pointer;"  title="'+i18n_message("citcorpore.comum.excluir")+'" src="'+ctx+'/imagens/delete.png" onclick="deleteLinha(\'tblMidiaSoftwareChave\', this.parentNode.parentNode.rowIndex);">'
	}
	function limpaDados(){
		document.form.chave.value = "";
		document.form.qtdPermissoes.value = 1;
	}
	function deleteLinha(table, index){
		HTMLUtils.deleteRow(table, index);
		 incrementaLicensas();
		limpaDados();
	}
	function salvar() {
		var objs = HTMLUtils.getObjectsByTableId('tblMidiaSoftwareChave');
		document.form.midiaSoftwareChaveSerializada.value = ObjectUtils.serializeObjects(objs)
		incrementaLicensas();
		document.form.save();
	}


