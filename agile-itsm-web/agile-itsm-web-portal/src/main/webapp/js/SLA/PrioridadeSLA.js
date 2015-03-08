/* Início do código que contempla a adição de prioridade a Unidade no cadastro de SLA */

	var count1 = 0;
		
	function insereRowUnidade(idUnidade, unidadeNome, prioridadeValor){
		
		
		var tabela = document.getElementById('tabelaPrioridadeUnidade');
		var lastRow = tabela.rows.length;
		
		var row = tabela.insertRow(lastRow);
		count1++;
		
		var coluna = row.insertCell(0);
		coluna.innerHTML = '<input type="hidden" id="idUnidade' + count1 + '" name="idUnidade" value="' + idUnidade + '"/><img id="imgExcluiUnidadePrioridade' + count1 + '" style="cursor: pointer;" title="i18n_message("citcorpore.comum.excluir")" src="/citsmart/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaPrioridadeUnidade\', this.parentNode.parentNode.rowIndex);">';
		
		coluna = row.insertCell(1);
		coluna.innerHTML = '<input type="text" class="text" id="nomeUnidade' + count1 + '" name="nomeUnidade" value="' + unidadeNome + '" style="width: 100%; border: 0 none;" readonly="readonly" />';
		
		coluna = row.insertCell(2);
		coluna.innerHTML = '<input type="text" class="text" id="prioridadeValor' + count1 + '" name="prioridadeValor" value="' + prioridadeValor + '" style="width: 100%; border: 0 none;" readonly="readonly" />';
		
		limpaCamposUnidade();
		
	}

	var count2 = 0;
		
	function insereRowUsuario(idUsuario, usuarioNome, prioridadeValor){
		
		var tabela = document.getElementById('tabelaPrioridadeUsuario');
		var lastRow = tabela.rows.length;
		
		var row = tabela.insertRow(lastRow);
		count2++;
		
		var coluna = row.insertCell(0);
		coluna.innerHTML = '<input type="hidden" id="idUsuario' + count2 + '" name="idUsuario" value="' + idUsuario + '"/><img id="imgExcluiUsuarioPrioridade' + count2 + '" style="cursor: pointer;" title="i18n_message("citcorpore.comum.excluir")" src="/citsmart/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaPrioridadeUsuario\', this.parentNode.parentNode.rowIndex);">';
		
		coluna = row.insertCell(1);
		coluna.innerHTML = '<input type="text" class="text" id="nomeUsuario' + count2 + '" name="nomeUsuario" value="' + usuarioNome + '" style="width: 100%; border: 0 none;" readonly="readonly" />';
		
		coluna = row.insertCell(2);
		coluna.innerHTML = '<input type="text" class="text" id="prioridadeValorUsuario' + count2 + '" name="prioridadeValorUsuario" value="' + prioridadeValor + '" style="width: 100%; border: 0 none;" readonly="readonly" />';
		
		limpaCamposUsuario();
		
	}
	
	function limpaCamposUnidade(){
		document.form.idUnidadePrioridade.value = '';
		document.form.addUnidade.value = '';
		document.form.prioridadeUnidade.value = '';
	}
	
	function limpaCamposUsuario(){
		document.form.idUsuarioPrioridade.value = '';
		document.form.addUsuario.value = '';
		document.form.prioridadeUsuario.value = '';
	}
	
	function removeLinhaTabela(idTabela, rowIndex) {
		if (confirm(i18n_message("citcorpore.comum.deleta"))) {
			HTMLUtils.deleteRow(idTabela, rowIndex);
		}
	}
	
	function deleteAllRowsPrioridadeUnidade() {
		var tabela = document.getElementById('tabelaPrioridadeUnidade');
		var count = tabela.rows.length;

		while (count > 1) {
			tabela.deleteRow(count - 1);
			count--;
		}
		
		count1 = 0;
	}
	
	function deleteAllRowsPrioridadeUsuario() {
		var tabela = document.getElementById('tabelaPrioridadeUsuario');
		var count = tabela.rows.length;

		while (count > 1) {
			tabela.deleteRow(count - 1);
			count--;
		}
		
		count2 = 0;
	}
	
	function serializaPrioridadeUnidade(){
		
		var tabela = document.getElementById('tabelaPrioridadeUnidade');
		var count = tabela.rows.length;
		var contadorAux = 0;
		var prioridadeUnidade = new Array();
		for ( var i = 1; i <= count; i++) {
			var trObj = document.getElementById('idUnidade' + i);
			if (!trObj) {
				continue;
			}
			prioridadeUnidade[contadorAux] = getPrioridadeUnidade(i);
			contadorAux = contadorAux + 1;
		}
		
		var prioridadeUnidadeSerializada = ObjectUtils.serializeObjects(prioridadeUnidade);
		document.form.prioridadeUnidadeSerializados.value = prioridadeUnidadeSerializada;
		
		return true;		
		
	}
	
	function serializaPrioridadeUsuario(){
				
		var tabela = document.getElementById('tabelaPrioridadeUsuario');
		var count = tabela.rows.length;
		var contadorAux = 0;
		var prioridadeUsuario = new Array();
		for ( var i = 1; i <= count; i++) {
			var trObj = document.getElementById('idUsuario' + i);
			if (!trObj) {
				continue;
			}
			prioridadeUsuario[contadorAux] = getPrioridadeUsuario(i);
			contadorAux = contadorAux + 1;
		}
		
		var prioridadeUsuarioSerializados = ObjectUtils.serializeObjects(prioridadeUsuario);
		document.form.prioridadeUsuarioSerializados.value = prioridadeUsuarioSerializados;
		
		return true;
		
	}
	
	function getPrioridadeUnidade(seq) {
		var PrioridadeAcordoNivelServicoDTO = new CIT_PrioridadeAcordoNivelServicoDTO();
		PrioridadeAcordoNivelServicoDTO.sequencia = seq;
		PrioridadeAcordoNivelServicoDTO.idUnidade = document.getElementById('idUnidade' + seq).value;
		PrioridadeAcordoNivelServicoDTO.idPrioridade = document.getElementById('prioridadeValor' + seq).value;
		return PrioridadeAcordoNivelServicoDTO;
	}
	
	function getPrioridadeUsuario(seq) {
		var PrioridadeServicoUsuarioDTO = new CIT_PrioridadeServicoUsuarioDTO();
		PrioridadeServicoUsuarioDTO.sequencia = seq;
		PrioridadeServicoUsuarioDTO.idUsuario = document.getElementById('idUsuario' + seq).value;
		PrioridadeServicoUsuarioDTO.idPrioridade = document.getElementById('prioridadeValorUsuario' + seq).value;
		return PrioridadeServicoUsuarioDTO;
	}
	
	