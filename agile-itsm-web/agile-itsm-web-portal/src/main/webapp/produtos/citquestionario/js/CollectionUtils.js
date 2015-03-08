function HashMap(){
	//Variaveis
	this._length = 0;
	this._arrayControle = new Array();
	
	//prototipos de funcoes
	this.set = _set;
	this.get = _get;
	this.setArray = _setArray;
	this.remove = _remove;
	this.toArray = _toArray;	
	this.length = _flength;

	//Funcoes privadas
	function _set(id, object){
		this[id] = object;
		object.idHashMapInternalControl = id;
		this._arrayControle[this._length] = object;
		this._length++;		
	}
	function _get(id){
		return this[id];
	}
	function _toArray(){
		return this._arrayControle;
	}
	function _remove(id){
		var array = this.toArray();
		for(var i = 0; i < array.length; i++){
			if (array[i].idHashMapInternalControl == id){
				this._arrayControle.splice(i,1); //Remove o elemento do array.
				this._length--;
			}
		}
		delete this[id]; //Remove o elemento do Hash.
	}
	function _flength(){
		return this._length;
	}
	/**
	 * Seta o hash a partir de um array de objetos (deve se usar a funcao que esta exposta setArray e nao _setArray).
	 *   Parametros:
	 *       name: prefixo de cada objeto que sera adicionado no hash
	 *       propIdent: propriedade do objeto que sera concatenada junto com o name para ser o id do objeto no hash.
	 *       arr: array de objetos a ser acrescentado no hash.
	 *            Exemplo: 
	 *                  var hashEpcs = new HashMap();
	 *                  hashEpcs.setArray('EPC','idepc',objsEpc);
	 *                             onde objsEpc é um array de objetos recuperado via Ajax.
	 *                             cada elemento no hash será identificado por: 'EPC1'
	 *                                                                          'EPC2' e assim por diante.
	 */
	function _setArray(name,propIdent,arr){
		if (arr == null || arr == undefined){
			return;
		}
		for(var i = 0; i < arr.length; i++){
			var obj = arr[i];
			this.set(name + obj[propIdent], obj);
		}
	}
}
