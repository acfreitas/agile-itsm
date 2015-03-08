
		var popup;
		addEvent(window, "load", load, false);
		function load() {
			popup = new PopupManager(800, 600, ctx+"/pages/");
			document.form.afterRestore = function() {
				$('.tabs').tabs('select', 0);
			}
		}

		function update() {
			if (document.getElementById('idUnidade').value != "") {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					document.form.fireEvent("delete");
					deleteAllRows();
				}
			}
		}

		function matrizPrioridade(){
			document.form.fireEvent("cadastrarMatriz");
		}

		function exibeCadastroMatriz(){
			document.getElementById('divAdicionarMatriz').style.display = 'block';

		}

		function limpar() {
			deleteAllRows();
			document.getElementById('gridMatrizPrioridade').style.display = 'none';
			document.form.clear();
		}

		function exibeGrid() {
			document.getElementById('gridMatrizPrioridade').style.display = 'block';
		}
		function ocultaGrid() {
			document.getElementById('gridMatrizPrioridade').style.display = 'none';
		}

		var countMatriz = 0;

		function insereRow(siglaImpacto, nivelImpacto, siglaUrgencia, nivelUrgencia, valor) {
			var tabela = document.getElementById('tabelaMatrizPrioridade');
			var lastRow = tabela.rows.length;

			var row = tabela.insertRow(lastRow);
			countMatriz++;

			var coluna = row.insertCell(0);
			coluna.innerHTML = '<input type="hidden" id="idMatrizPrioridade' + countMatriz + '" name="idMatrizPrioridade"/><img id="imgExcluiMatrizPrioridade' + countMatriz + '" style="cursor: pointer;" title="'+i18n_message("citcorpore.comum.excluir")+'" src="'+ctx+'/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaMatrizPrioridade\', this.parentNode.parentNode.rowIndex);">';

			coluna = row.insertCell(1);
			coluna.innerHTML = '<input type="hidden" id="siglaImpacto'+countMatriz+'" value="'+siglaImpacto+'" style="display: none;"><input type="text" id="nivelImpacto' + countMatriz + '" name="nivelImpacto" value="' + nivelImpacto + '" style="width: 100%; border: 0 none;" readonly="readonly" />';

			coluna = row.insertCell(2);
			coluna.innerHTML = '<input type="hidden" id="siglaUrgencia'+countMatriz+'" value="'+siglaUrgencia+'" style="display: none;"><input type="text" id="nivelUrgencia' + countMatriz + '" name="nivelUrgencia" value="' + nivelUrgencia + '" style="width: 100%; border: 0 none;" readonly="readonly" />';

			coluna = row.insertCell(3);
			coluna.innerHTML = '<input type="text" id="valorPrioridade' + countMatriz + '" name="valorPrioridade" value="' + valor + '" style="width: 100%; border: 0 none;" readonly="readonly" />';

		}

		function removeLinhaTabela(idTabela, rowIndex) {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				HTMLUtils.deleteRow(idTabela, rowIndex);
				document.form.fireEvent("excluirMatrizPrioridade");
			}
		}

		function deleteAllRows() {
			var tabela = document.getElementById('tabelaMatrizPrioridade');
			var count = tabela.rows.length;

			while (count > 1) {
				tabela.deleteRow(count - 1);
				count--;

			}
			ocultaGrid();
		}

		var seqSelecionada = '';
		var aux = '';

		/* In�cio do c�digo de N�veis de Impacto e Urg�ncia */

		var cont = 0;

		function addImpacto() {

			var tabela = document.getElementById('tabelaImpacto');
			var lastRow = tabela.rows.length;

			var row = tabela.insertRow(lastRow);
			cont++;

			var coluna = row.insertCell(0);
			coluna.innerHTML = '<span style=" padding-right: 15px;width: 90%;" class="campoObrigatorio" >'+i18n_message("prioridade.nivelImpacto")+ '  </span>';

			coluna = row.insertCell(1);
			coluna.innerHTML = '<input type="TEXT" class="text" name="NIVELIMPACTO" size="100" maxlength="100"/>';

			coluna = row.insertCell(2);
			coluna.innerHTML = '<span style="padding-left: 5px; padding-right: 15px;width: 70%" class="campoObrigatorio">'+ i18n_message("prioridade.sigla") + '  </span>';

			coluna = row.insertCell(3);
			coluna.innerHTML = '<input type="TEXT" class="text" name="SIGLAIMPACTO" size="2" maxlength="2"/>';

			coluna = row.insertCell(4);
			coluna.innerHTML = '<img title="Remover Impacto" src="/citsmart/imagens/delete.png" onclick="removeNivel(\'tabelaImpacto\', this.parentNode.parentNode.rowIndex);" border="0" style="cursor:pointer">';

			coluna = row.insertCell(5);
			coluna.innerHTML = '<img title="Adicionar n�vel do Impacto" src="/citsmart/imagens/add.png" onclick="addImpacto();" border="0" style="cursor:pointer">';

		}

		var cont = 0;

		function addUrgencia() {

			var tabela = document.getElementById('tabelaUrgencia');
			var lastRow = tabela.rows.length;

			var row = tabela.insertRow(lastRow);
			cont++;

			var coluna = row.insertCell(0);
			coluna.innerHTML = '<span style=" padding-right: 15px;width: 90%;" class="campoObrigatorio">' + i18n_message("prioridade.nivelUrgencia") + '  </span>';

			coluna = row.insertCell(1);
			coluna.innerHTML = '<input type="TEXT" class="text" name="NIVELURGENCIA" size="100" maxlength="100"/>';

			coluna = row.insertCell(2);
			coluna.innerHTML = '<span style="padding-left: 5px; padding-right: 15px;width: 70%" class="campoObrigatorio">' + i18n_message("prioridade.sigla") + '  </span>';

			coluna = row.insertCell(3);
			coluna.innerHTML = '<input type="TEXT" class="text" name="SIGLAURGENCIA" size="2" maxlength="2"/>';

			coluna = row.insertCell(4);
			coluna.innerHTML = '<img title="Remover Urg�ncia" src="/citsmart/imagens/delete.png" onclick="removeNivel(\'tabelaUrgencia\', this.parentNode.parentNode.rowIndex);" border="0" style="cursor:pointer">';

			coluna = row.insertCell(5);
			coluna.innerHTML = '<img title="Adicionar n�vel da Urg�ncia" src="/citsmart/imagens/add.png" onclick="addUrgencia();" border="0" style="cursor:pointer">';

		}

		function removeNivel(idTabela, rowIndex){

			if(idTabela == "tabelaImpacto"){
				var nivelImpacto = document.form.NIVELIMPACTO[rowIndex].value
				var resp = percorreTabela(idTabela, nivelImpacto);
				if(!resp){
					alert(i18n_message("prioridade.matrizprioridade.remover"));
					return false;
				}
			}else {
				var nivelUrgencia = document.form.NIVELURGENCIA[rowIndex].value
				var resp = percorreTabela(idTabela, nivelUrgencia);
				if(!resp){
					alert(i18n_message("prioridade.matrizprioridade.remover"));
					return false;
				}
			}
			if(idTabela == "tabelaImpacto"){
				if(confirm(i18n_message("prioridade.matrizprioridade.removerNivelImpacto"))){
					HTMLUtils.deleteRow(idTabela, rowIndex);
					alert(i18n_message("MSG07"));
				}
	        }else{
		        if (confirm(i18n_message("prioridade.matrizprioridade.removerNivelUrgencia"))){
			        HTMLUtils.deleteRow(idTabela, rowIndex);
				    alert(i18n_message("MSG07"));
		        }
	        }
		}

		function removeLinha(idTabela, rowIndex){
			HTMLUtils.deleteRow(idTabela, rowIndex);
		}

		function percorreTabela(idTabela, novo){
			var tabela = document.getElementById('tabelaMatrizPrioridade');
			var count = tabela.rows.length;
			var contadorAux = 0;
			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idMatrizPrioridade' + i);
				if (!trObj) {
					continue;
				}
				if(idTabela == "tabelaImpacto"){
					var resp = document.getElementById('nivelImpacto' + i).value;
					if(novo == resp){
						return false;
					}
				}else {
					var resp = document.getElementById('nivelUrgencia' + i).value;
					if(novo == resp){
						return false;
					}
				}
			}
			return true;
		}

		/* C�digo da Matriz de Prioridade */

		function addLinhaMatriz(){
			var idImpacto = document.getElementById('IDIMPACTOSELECT').value;
			var nivelImpacto = document.getElementById('IDIMPACTOSELECT').options[document.getElementById('IDIMPACTOSELECT').selectedIndex].textContent;

			var idUrgencia = document.getElementById('IDURGENCIASELECT').value;
			var nivelUrgencia = document.getElementById('IDURGENCIASELECT').options[document.getElementById('IDURGENCIASELECT').selectedIndex].textContent;

			var valor = document.getElementById('VALORPRIORIDADE').value;

			if(!verificaRegistrosMatriz(idImpacto, idUrgencia, valor)){
				window.alert(i18n_message("prioridadesolicitacao.registrosiguais"));
				return;
			}

			//geber.costa
			//valida��o para ser inserido na matriz apenas as prioridades com impacto, urgencia e valor
			if(idImpacto <=0 || idUrgencia <= 0 || valor <=0){
				window.alert(i18n_message("prioridade.matrizprioridade.info"));
				return;
			}

			exibeGrid();

			limpaFormMatriz();

			insereRow(idImpacto, nivelImpacto, idUrgencia, nivelUrgencia, valor);
		}

		function limpaFormMatriz(){
			document.getElementById('IDIMPACTOSELECT').selectedIndex = 0;
			document.getElementById('IDURGENCIASELECT').selectedIndex = 0;
			document.getElementById('VALORPRIORIDADE').value = "";
		}

		function gravarMatriz() {
			serializaMatriz();
			document.form.fireEvent("saveMatrizPrioridade");
		}

		function serializaMatriz() {
			var tabela = document.getElementById('tabelaMatrizPrioridade');
			var count = tabela.rows.length;
			var contadorAux = 0;
			var matrizPrioridade = new Array();
			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idMatrizPrioridade' + i);
				if (!trObj) {
					continue;
				}
				matrizPrioridade[contadorAux] = getMatrizPrioridade(i);
				contadorAux = contadorAux + 1;
			}

			var matrizSerelializada = ObjectUtils.serializeObjects(matrizPrioridade);
			document.form.MATRIZPRIORIDADESERELIALIZADO.value = matrizSerelializada;

			return true;
		}

		// M�todo que verifica se ja existe um registro com os mesmos valores

		function verificaRegistrosMatriz(imp, urg , prio) {
			var tabela = document.getElementById('tabelaMatrizPrioridade');
			var count = tabela.rows.length;
			//
			var impacto;
			var urgencia;
			var prioridade;
			var verificacao = true;
			//
			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idMatrizPrioridade' + i);
				if (!trObj) {
					continue;
				}

				impacto = document.getElementById('siglaImpacto' + i).value;
				urgencia = document.getElementById('siglaUrgencia' + i).value;
				prioridade = document.getElementById('valorPrioridade' + i).value;

				if(impacto == imp && urgencia == urg && prioridade == prio){
					verificacao = false;
					break;
				}

			}

			return verificacao;
		}


		function getMatrizPrioridade(seq) {
			var MatrizPrioridadeDTO = new CIT_MatrizPrioridadeDTO();
			MatrizPrioridadeDTO.sequencia = seq;
			MatrizPrioridadeDTO.siglaImpacto = document.getElementById('siglaImpacto' + seq).value;
			MatrizPrioridadeDTO.siglaUrgencia = document.getElementById('siglaUrgencia' + seq).value;
			MatrizPrioridadeDTO.valorPrioridade = document.getElementById('valorPrioridade' + seq).value;
			return MatrizPrioridadeDTO;
		}


		/* Fim do c�digo da Matriz de Prioridade */

		/* Carrega Valores de Impacto e Urg�ncia */

		var flag = false;

		function carregaImpacto(){
			var objres = document.form.NIVELIMPACTOSERELIALIZADO.value;
			var objResultado = new Array();
			objResultado = ObjectUtils.deserializeCollectionFromString(objres);
			if(objResultado != null && objResultado.length != 0 && flag == false){
				var size = objResultado.length;
				if(size>1){
					for(var i=0;i<size-1;i++){
						addImpacto();
					}
					for(var i=0; i<size;i++){
						var ImpactoDTO = new CIT_ImpactoDTO();
						ImpactoDTO = objResultado[i];
						document.form.NIVELIMPACTO[i].value = ImpactoDTO.nivelImpacto;
						document.form.SIGLAIMPACTO[i].value = ImpactoDTO.siglaImpacto;
					}
					 flag = true;
				}else{
					var ImpactoDTO = new CIT_ImpactoDTO();
					ImpactoDTO = objResultado[0];
					document.form.NIVELIMPACTO.value = ImpactoDTO.nivelImpacto;
					document.form.SIGLAIMPACTO.value = ImpactoDTO.siglaImpacto;
				}
			}
		}

		var flag2 = false;

		function carregaUrgencia(){
			var objres = document.form.NIVELURGENCIASERELIALIZADO.value;
			var objResultado = new Array();
			objResultado = ObjectUtils.deserializeCollectionFromString(objres);
			if(objResultado != null && objResultado.length != 0 && flag2 == false){
				var size = objResultado.length;
				if(size>1){
					for(var i=0;i<size-1;i++){
						addUrgencia();
					}
					for(var i=0; i<size;i++){
						var UrgenciaDTO = new CIT_UrgenciaDTO();
						UrgenciaDTO = objResultado[i];
						document.form.NIVELURGENCIA[i].value = UrgenciaDTO.nivelUrgencia;
						document.form.SIGLAURGENCIA[i].value = UrgenciaDTO.siglaUrgencia;
					}
					flag2 = true;
				}else{
					var UrgenciaDTO = new CIT_UrgenciaDTO();
					UrgenciaDTO = objResultado[0];
					document.form.NIVELURGENCIA.value = UrgenciaDTO.nivelUrgencia;
					document.form.SIGLAURGENCIA.value = UrgenciaDTO.siglaUrgencia;
				}
			}
		}

		/**
		 * Gravando informa��es sobre de N�veis de Impacto e Urg�ncia
		 */

		 /* Impacto */

		function gravarImpacto() {
			serializaImpacto();
			document.form.fireEvent("saveImpacto");
		}

		function serializaImpacto() {
			var contadorAux = 0;
			var objNivelImpacto = new Array();
			if(document.form.NIVELIMPACTO.length == undefined){
				objNivelImpacto[contadorAux] = getNivelImpactoUnicaLinha();
				contadorAux = contadorAux + 1;
			}else{
				var count = document.form.NIVELIMPACTO.length;
				for ( var i = 0; i < count; i++) {
					objNivelImpacto[contadorAux] = getNivelImpacto(i);
					contadorAux = contadorAux + 1;
				}
			}
			var nivelImpactoSerializado = ObjectUtils.serializeObjects(objNivelImpacto);
			document.form.NIVELIMPACTOSERELIALIZADO.value = nivelImpactoSerializado;
			return true;
		}

		function getNivelImpactoUnicaLinha(){
			var ImpactoDTO = new CIT_ImpactoDTO();
			ImpactoDTO.nivelImpacto = document.form.NIVELIMPACTO.value;
			ImpactoDTO.siglaImpacto = document.form.SIGLAIMPACTO.value;
	 		return ImpactoDTO;
		}

		function getNivelImpacto(seq) {
			var ImpactoDTO = new CIT_ImpactoDTO();
			ImpactoDTO.sequence = seq;
			ImpactoDTO.nivelImpacto = document.form.NIVELIMPACTO[seq].value;
			ImpactoDTO.siglaImpacto = document.form.SIGLAIMPACTO[seq].value;
			return ImpactoDTO;
		}

		/* Urg�ncia */

		function gravarUrgencia() {
			serializaUrgencia();
			document.form.fireEvent("saveUrgencia");
		}

		function serializaUrgencia() {
			var contadorAux = 0;
			var objNivelUrgencia = new Array();
			if(document.form.NIVELURGENCIA.length == undefined){
				objNivelUrgencia[contadorAux] = getNivelUrgenciaUnicaLinha();
				contadorAux = contadorAux + 1;
			}else{
				var count = document.form.NIVELURGENCIA.length;
				for ( var i = 0; i < count; i++) {
					objNivelUrgencia[contadorAux] = getNivelUrgencia(i);
					contadorAux = contadorAux + 1;
				}
			}
			var nivelUrgenciaSerializado = ObjectUtils.serializeObjects(objNivelUrgencia);
			document.form.NIVELURGENCIASERELIALIZADO.value = nivelUrgenciaSerializado;
			return true;
		}

		function getNivelUrgenciaUnicaLinha(){
			var UrgenciaDTO = new CIT_UrgenciaDTO();
			UrgenciaDTO.nivelUrgencia = document.form.NIVELURGENCIA.value;
			UrgenciaDTO.siglaUrgencia = document.form.SIGLAURGENCIA.value;
	 		return UrgenciaDTO;
		}

		function getNivelUrgencia(seq) {
			var UrgenciaDTO = new CIT_UrgenciaDTO();
			UrgenciaDTO.sequence = seq;
			UrgenciaDTO.nivelUrgencia = document.form.NIVELURGENCIA[seq].value;
			UrgenciaDTO.siglaUrgencia = document.form.SIGLAURGENCIA[seq].value;
			return UrgenciaDTO;
		}

		/* Fim do c�digo de N�veis de Impacto e Urg�ncia */

	
