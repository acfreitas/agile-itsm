/**
 * Renderiza tabela a partir de lista.
 * 
 * @param _idCITTable id da tabela a ser tratada
 * @param _fields Lista de campos correspondentes ao banco de dados
 * @param _tableObjects Lista de itens. Deve corresponder aos campos de _fields
 */
function CITTable(_idCITTable, _fields, _tableObjects) {
	var self = this;
	var idCITTable = _idCITTable;
	var fields = _fields;
	var tableObjects = _tableObjects;
	var tabela = null;

	var insereDetalhamento = false;
	var insereBtExcluir = true;
	var imgBotaoExcluir;

	this.onDeleteRow = function(deletedItem) {
	};

	this.getTableList = function() {
		return tableObjects;
	}

	/**
	 * Transforma a lista da tabela em uma lista de objetos de acordo com o
	 * 'fields' passado.
	 */
	this.getTableObjects = function() {
		var objects = [];
		var object = {};

		for ( var j = 0; j < tableObjects.length; j++) {
			for ( var i = 0; i < fields.length; i++) {
				 if (tableObjects[j][i].search(/['"]*/g) != -1 ){
					 tableObjects[j][i] = tableObjects[j][i].replace(/['"]*/g, '');
					} 
				eval("object." + fields[i] + " = '" + tableObjects[j][i] + "'");
			}
			objects.push(object);
			object = {};
		}

		return objects;
	}

	this.setTableObjects = function(objects) {
		tableObjects = objects;
		this.montaTabela();
	}

	this.addObject = function(object) {
		tableObjects.push(object);
		this.montaTabela();
	}

	this.limpaLista = function() {
		tableObjects.length = 0;
		tableObjects = null;
		tableObjects = [];
		limpaTabela();
	}

	var limpaTabela = function() {
		while (getTabela().rows.length > 1) {
			getTabela().deleteRow(1);
		}
	}

	this.montaTabela = function() {
		var linha;
		var celula;

		limpaTabela();

		for ( var i = tableObjects.length - 1; i >= 0; i--) {

			var j = 0;
			linha = getTabela().insertRow(1);

			for (j = 0; j < fields.length; j++) {
				celula = linha.insertCell(j);

				// tratamento caso seja um componente ao invés de texto
				try {
					celula.appendChild(tableObjects[i][j]);
				} catch (e) {
					celula.innerHTML = tableObjects[i][j];
				}
			}

			if (insereBtExcluir) {
				var btAux = getCopiaBotaoExcluir();
				var celExcluir = linha.insertCell(j);

				btAux.setAttribute("id", i);
				btAux.addEventListener("click", function(evt) {
					// ao disparar o evento, considerará o id do botão
					self.removeObject(this.id);
					this.onDeleteRow(this);

				}, false);
				celExcluir.appendChild(btAux);
			}
		}
	}

	this.removeObject = function(indice) {
		removeObjectDaLista(indice);
		this.montaTabela();
	}

	/**
	 * Remove item e organiza lista
	 */
	var removeObjectDaLista = function(indice) {
		tableObjects[indice] = null;
		var novaLista = [];
		for ( var i = 0; i < tableObjects.length; i++) {
			if (tableObjects[i] != null) {
				novaLista.push(tableObjects[i]);
			}
		}
		tableObjects = novaLista;
	}

	var getCopiaBotaoExcluir = function() {
		var novoBotao = new Image();
		novoBotao.setAttribute("style", "cursor: pointer;");
		novoBotao.src = imgBotaoExcluir;
		return novoBotao;
	}

	var setImgPathBotaoExcluir = function(src) {
		imgBotaoExcluir = src;
	}

	var getTabela = function() {
		if (tabela == null) {
			tabela = document.getElementById(idCITTable);
		}
		return tabela;
	}

	this.setInsereBotaoExcluir = function(bool, imgSrc) {
		insereBtExcluir = bool;
		setImgPathBotaoExcluir(imgSrc);
	}
	
	
}

