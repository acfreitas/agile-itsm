/* Início do código de Revisar SLA */
		
	var count3 = 0;
		
	function insereRevisarSLARow(dataRevisar, detalhes, observacao){
				
		var tabela = document.getElementById('tabelaRevisarSLA');
		var lastRow = tabela.rows.length;
		
		var row = tabela.insertRow(lastRow);
		count3++;
		
		var coluna = row.insertCell(0);
		coluna.innerHTML = '<img id="imgExcluiUnidadePrioridade' + count3 + '" style="cursor: pointer;" title="i18n_message("citcorpore.comum.excluir")" src="/citsmart/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaRevisarSLA\', this.parentNode.parentNode.rowIndex);">';
		
		coluna = row.insertCell(1);
		coluna.innerHTML = '<input type="text" class="text" id="dataRevisar' + count3 + '" name="dataRevisar" value="' + dataRevisar + '" style="width: 100%; float: left; border: 0 none;" readonly="readonly" />';
		
		coluna = row.insertCell(2);
		coluna.innerHTML = '<textarea id="detalhes' + count3 + '" name="detalhes" style="width: 100%; border: 0 none;" readonly="readonly" />'+ detalhes +'</textarea>';
		
		coluna = row.insertCell(3);
		coluna.innerHTML = '<textarea  id="observacao' + count3 + '" name="observacao" style="width: 100%; border: 0 none;" readonly="readonly" />' + observacao + '</textarea>';
		
		limpaRevisarSLA();
		
	}
	
	function limpaRevisarSLA(){
		document.form.dataRevisarSLA.value = '';
		document.form.detalhesSLA.value = '';
		document.form.observacaoSLA.value = '';
	}
	
	function removeLinhaTabela(idTabela, rowIndex) {
		if (confirm(i18n_message("citcorpore.comum.deleta"))) {
			HTMLUtils.deleteRow(idTabela, rowIndex);
		}
	}
	
	function serializaRevisarSLA(){
		
		var tabela = document.getElementById('tabelaRevisarSLA');
		var count = tabela.rows.length;
		var contadorAux = 0;
		var revisarSLA = new Array();
		for ( var i = 1; i <= count; i++) {
			var trObj = document.getElementById('dataRevisar' + i);
			if (!trObj) {
				continue;
			}
			revisarSLA[contadorAux] = getRevisarSLA(i);
			contadorAux = contadorAux + 1;
		}
		
		var revisarSlaSerializados = ObjectUtils.serializeObjects(revisarSLA);
		document.form.revisarSlaSerializados.value = revisarSlaSerializados;
		
		return true;
		
	}
	
	function getRevisarSLA(seq) {
		var RevisarSlaDTO = new CIT_RevisarSlaDTO();
		RevisarSlaDTO.sequencia = seq;
		RevisarSlaDTO.dataRevisao = document.getElementById('dataRevisar' + seq).value;
		RevisarSlaDTO.detalheRevisao = document.getElementById('detalhes' + seq).value;
		RevisarSlaDTO.observacao = document.getElementById('observacao' + seq).value;
		return RevisarSlaDTO;
	}
	
	function deleteAllRowsRevisarSLA() {
		var tabela = document.getElementById('tabelaRevisarSLA');
		var count = tabela.rows.length;

		while (count > 1) {
			tabela.deleteRow(count - 1);
			count--;
		}
		
		count3 = 0;
	}
	
	
	
	