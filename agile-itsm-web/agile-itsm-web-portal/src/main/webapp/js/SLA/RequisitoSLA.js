/* Início do código de Requisito SLA */
		
	var count4 = 0;
		
	function insereRowRequisitoSLA(idRequisitoSLA, assuntoRequisito, dataVinculacao){
		
		var tabela = document.getElementById('tabelaRequisitoSLA');
		var lastRow = tabela.rows.length;
		
		var row = tabela.insertRow(lastRow);
		count4++;
		
		var coluna = row.insertCell(0);
		coluna.innerHTML = '<input type="hidden" id="idRequisitoSLA' + count4 + '" name="idRequisitoSLA" value="' + idRequisitoSLA + '"/><img id="imgExcluiUnidadePrioridade' + count4 + '" style="cursor: pointer;" title="i18n_message("citcorpore.comum.excluir")" src="/citsmart/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaRequisitoSLA\', this.parentNode.parentNode.rowIndex);">';
		
		coluna = row.insertCell(1);
		coluna.innerHTML = '<input type="text" class="text" id="dataVinculacao' + count4 + '" name="dataVinculacao" value="' + dataVinculacao + '" style="width: 100%; border: 0 none;" readonly="readonly" />';
		
		coluna = row.insertCell(2);
		coluna.innerHTML = '<input type="text" class="text" id="assuntoRequisito' + count4 + '" name="assuntoRequisito" value="' + assuntoRequisito + '" style="width: 100%; border: 0 none;" readonly="readonly" />';
		
		limpaRequisitoSLA();
		
	}
	
	function limpaRequisitoSLA(){
		document.form.idRequisitoSLAVinc.value = '';
		document.form.addRequisitoSLA.value = '';
		document.form.dataVinculacaoSLA.value = '';
	}
	
	function removeLinhaTabela(idTabela, rowIndex) {
		if (confirm(i18n_message("citcorpore.comum.deleta"))) {
			HTMLUtils.deleteRow(idTabela, rowIndex);
		}
	}
	
	function serializaRequisitoSLA(){
		
		var tabela = document.getElementById('tabelaRequisitoSLA');
		var count = tabela.rows.length;
		var contadorAux = 0;
		var requisitoSLA = new Array();
		for ( var i = 1; i <= count; i++) {
			var trObj = document.getElementById('idRequisitoSLA' + i);
			if (!trObj) {
				continue;
			}
			requisitoSLA[contadorAux] = getRequisitoSLA(i);
			contadorAux = contadorAux + 1;
		}
		
		var requisitoSlaSerializados = ObjectUtils.serializeObjects(requisitoSLA);
		document.form.requisitoSlaSerializados.value = requisitoSlaSerializados;
		
		return true;
		
	}
	
	function getRequisitoSLA(seq) {
		var SlaRequisitoSlaDTO = new CIT_SlaRequisitoSlaDTO();
		SlaRequisitoSlaDTO.sequencia = seq;
		SlaRequisitoSlaDTO.idRequisitoSLA = document.getElementById('idRequisitoSLA' + seq).value;
		SlaRequisitoSlaDTO.dataVinculacao = document.getElementById('dataVinculacao' + seq).value;
		return SlaRequisitoSlaDTO;
	}
	
	function deleteAllRowsRequisitoSLA() {
		var tabela = document.getElementById('tabelaRequisitoSLA');
		var count = tabela.rows.length;

		while (count > 1) {
			tabela.deleteRow(count - 1);
			count--;
		}
		
		count4 = 0;
	}
	
	