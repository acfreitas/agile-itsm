/**
 * Esta classe faz uso de:   DateTimeUtil.js (acrescentar na pagina)
 */
function HTMLUtils() { }

var HTMLUtils_idGenerated = 0;
var HTMLUtil_colorOn = '#F1F1F1';
var HTMLUtil_colorOff = 'white';
var HTMLUtil_form = null;

var HTMLUtils_ObjectHash = {
	set : function(id,object) {this[id] = object;},
	get : function(id) {return this[id];}
};

var HTMLUtils_ObjectLock = {
	set : function(id,object) {this[id] = object;},
	get : function(id) {return this[id];}
};

HTMLUtils.setForm = function(f){
	HTMLUtil_form = f;	
}

HTMLUtils.getForm = function(){
	if (HTMLUtil_form == null){
		return document.forms[0];
	}
	return HTMLUtil_form;
}

HTMLUtils.setColorOn = function(cor){
	HTMLUtil_colorOn = cor;
};

HTMLUtils.setColorOff = function(cor){
	HTMLUtil_colorOff = cor;
};

HTMLUtils.getIdGenerated = function(){
	HTMLUtils_idGenerated++;
	return HTMLUtils_idGenerated;
};

HTMLUtils.getObjectById = function(id){
	return HTMLUtils_ObjectHash.get(id);
};

HTMLUtils.saveObjectInHash = function(id, object){
	HTMLUtils_ObjectHash.set(id, object);
};

HTMLUtils.getObjectByTableIndex = function(tableId,index){
	var tbl = $(tableId);
	if (tbl == null) return null;
	for(var i = 1; i < tbl.rows.length; i++){
		if (index == tbl.rows[i].rowIndex){
			return HTMLUtils.getObjectById(tbl.rows[i].id);
		}
	}
	return null;
};

HTMLUtils.getObjectsByTableId = function(tableId){
	var tbl = $(tableId);
	if (tbl == null) return null;
	if (tbl.rows.length == 0) return null;
	var objs = new Array();
	for(var i = 1; i < tbl.rows.length; i++){
		var ident = tbl.rows[i].id;
		if (ident != null && ident != undefined && ident != ''){
			var o = HTMLUtils.getObjectById(tbl.rows[i].id);
			if (o != null && o != undefined){
				objs[i-1] = o;
			}
		}
	}
	return objs;
};
/**
 * Verifica se existe um objeto igual na tabela, sendo parametros.
 */
HTMLUtils.hasObjectEqual = function(ele,object,arColunasVerifDuplicidade,id){
  if (arColunasVerifDuplicidade == null) return false;
  var objs = HTMLUtils.getObjectsByTableId(ele);
  var b = true;
  if (id == null) id = 'Is new Object';
  for(var i = 0; i < objs.length; i++){
  	b = true;
  	var o = objs[i];
  	for(var j = 0; j < arColunasVerifDuplicidade.length; j++){
  		if (o[arColunasVerifDuplicidade[j]] != object[arColunasVerifDuplicidade[j]]){
  			b = false;
  		}
  	}
  	if (b && o.idControleCITFramework != id){ //Verifica (no caso de update) se nao eh ele mesmo (objeto).
  		return true;
  	}
  }
  return false;
};
/**
 * Adiciona uma linha na tabela
 */
HTMLUtils.addRow = function(ele, form, name, object, arColunas, arColunasVerifDuplicidade, msgDup, arFuncoesExec, funcaoClick, funcaoVerificacao, bIgnorarForm) {
  var orig = ele;
  var tbl = $(ele);
  if (tbl == null) return null;
  if (ele == null) {
    alert("addRow() nao conseguiu encontrar um elemento com ID: " + orig + ".");
    return false;
  }
  
  var bIgnore = false;
  if (arguments.length == 11){ //Foi passado o bIgnorarForm
  	bIgnore = bIgnorarForm;
  }
  
  if (!bIgnore){
	  HTMLUtils.setValuesObjectByGroupName(form,name,object);
  }
  
  object = ObjectUtils.convertDatesOfObject(object);  
    
  if (arColunasVerifDuplicidade != null){
  	if (HTMLUtils.hasObjectEqual(ele,object,arColunasVerifDuplicidade,null)){
  		if (msgDup == null) msgDup = 'Duplicidade de informa??es n?o permitida!';
  		alert(msgDup);
  		return false;
  	}
  }

  if (funcaoVerificacao != null){
  	if (!funcaoVerificacao(object)){
  		return false;
  	}
  }
  
  var lastRow = tbl.rows.length;
  var row = tbl.insertRow(lastRow);  
  
  row.id = 'HTMLUtils_' + ele + '_row_' + HTMLUtils.getIdGenerated();
  
  object.idControleCITFramework = row.id;
    
  HTMLUtils.saveObjectInHash(object.idControleCITFramework, object);
  
  row.style.cursor = 'pointer';
  
  row.onmouseover = function() {HTMLUtil_TrowOn(row,HTMLUtil_colorOn)};
  row.onmouseout = function() {HTMLUtil_TrowOn(row,HTMLUtil_colorOff)};
  
  for(var i = 0; i < arColunas.length; i++){
	var coluna = row.insertCell(i);
	var value = '';
	if (object[arColunas[i]] instanceof Date){ //Se for data, pega o valor Str
		var aux = object[arColunas[i] + 'Str'];
		if (aux == undefined){
			value = object[arColunas[i]];
		}else{
			value = aux;
		}
	}else{
		value = object[arColunas[i]];
	}
	if (value == null){
		coluna.innerHTML = '&nbsp;';
	}else{
   		coluna.innerHTML = value;
   		if (coluna.innerHTML == ''){
   			coluna.innerHTML = '&nbsp;';
   		}
   	}	
   	if (funcaoClick!=null){
	   	coluna.onclick=function(){funcaoClick(row, object)};
	}
  }
  if (arFuncoesExec != null){
	  for(var i = 0; i < arFuncoesExec.length; i++){
		var func = arFuncoesExec[i];
	   	func(row, object);
	  }  
  }
  return true;
};

HTMLUtils.updateRow = function(ele, form, name, object, arColunas, arColunasVerifDuplicidade, msgDup, arFuncoesExec, funcaoClick, funcaoVerificacao, indexItem, bIgnorarForm) {
  var orig = ele;
  var tbl = $(ele);
  if (tbl == null) return null;
  if (ele == null) {
    alert("updateRow() nao conseguiu encontrar um elemento com ID: " + orig + ".");
    return false;
  }   
  var bIgnore = false;
  if (arguments.length == 12){ //Foi passado o bIgnorarForm
  	bIgnore = bIgnorarForm;
  }
  if (!bIgnore){      
	 HTMLUtils.setValuesObjectByGroupName(form,name,object);
  }
  if (arColunasVerifDuplicidade != null){
  	if (HTMLUtils.hasObjectEqual(ele,object,arColunasVerifDuplicidade,object.idControleCITFramework)){
  		if (msgDup == null) msgDup = 'Duplicidade de informa??es n?o permitida!';
  		alert(msgDup);
  		return false;
  	}
  }
  object = ObjectUtils.convertDatesOfObject(object);
  if (funcaoVerificacao != null){
  	if (!funcaoVerificacao(object)){
  		return false;
  	}
  }  
  
  object.idControleCITFramework = tbl.rows[indexItem].id;
   
  HTMLUtils.saveObjectInHash(object.idControleCITFramework, object);  
  
  for(var i = 0; i < arColunas.length; i++){
	var value = '';
	if (object[arColunas[i]] instanceof Date){ //Se for data, pega o valor Str
		var aux = object[arColunas[i] + 'Str'];
		if (aux == undefined){
			value = object[arColunas[i]];
		}else{
			value = aux;
		}
	}else{
		value = object[arColunas[i]];
	}
	if (value == null){
		tbl.rows[indexItem].cells[i].innerHTML = '';
	}else{
   		tbl.rows[indexItem].cells[i].innerHTML = value;
   	}	
   	if (funcaoClick!=null){
	   	tbl.rows[indexItem].cells[i].onclick=function(){funcaoClick(tbl.rows[indexItem], object)};
	}   	
  }
  if (arFuncoesExec != null){
	  for(var i = 0; i < arFuncoesExec.length; i++){
		var func = arFuncoesExec[i];
	   	func(tbl.rows[indexItem], object);
	  }  
  } 
  return true;
};

HTMLUtils.deleteRow = function(idTabela, rowIndex){
   var tbl = document.getElementById(idTabela);
   tbl.deleteRow(rowIndex);
}

HTMLUtils.deleteAllRows = function(idTabela){
   var tbl = document.getElementById(idTabela);
   for(var i = tbl.rows.length - 1; i >= 1; i--){
	   HTMLUtils.deleteRow(idTabela, tbl.rows[i].rowIndex);
   }
}
HTMLUtils.moveRow = function(idTabela, rowIndexOld, rowIndexNew){
	var tbl = document.getElementById(idTabela);
	
	var rowOld = tbl.rows[rowIndexOld];
	var rowNew = tbl.rows[rowIndexNew];
	
	var idOld = null;
	var idNew = null;
	if (rowOld.id){
		idOld = rowOld.id;
		idNew = rowNew.id;
	}
	
	var objs = new Array();
	for (var i = 0; i < rowOld.cells.length; i++){
		objs[i] = rowOld.cells[i].innerHTML;
				
		rowOld.cells[i].innerHTML = rowNew.cells[i].innerHTML;
		rowNew.cells[i].innerHTML = objs[i];		
	}
	
	if (rowOld.id){
		rowNew.id = idOld;
		rowOld.id = idNew;
	}
}
/*
 * Adiciona as linhas em uma tabela atraves de uma collection
 */
HTMLUtils.addRowsByCollection = function(ele, form, name, collection, arColunas, arColunasVerifDuplicidade, msgDup, arFuncoesExec, funcaoClick, funcaoVerificacao) {
	if (HTMLUtils._isArray(collection)) {
		for (var i = 0; i < collection.length; i++) {
			var objeto = collection[i];
			HTMLUtils.addRow(ele, form, name, objeto, arColunas, arColunasVerifDuplicidade, msgDup, arFuncoesExec, funcaoClick, funcaoVerificacao, true);
		}
	}else{
		var objeto = collection;
		HTMLUtils.addRow(ele, form, name, objeto, arColunas, arColunasVerifDuplicidade, msgDup, arFuncoesExec, funcaoClick, funcaoVerificacao, true);
	}
}
/*
	Seta o valor de uma coluna de uma Tabela
	Exemplo: HTMLUtils.setValueColumnTable('tabelaCargos', '', 0, null, 1);
					Fará a atribuição de '' para a tabela Cargos da linha 0 até a última (null representa a última) para a coluna 1.
*/
HTMLUtils.setValueColumnTable = function(tableId, value, iRowIni, iRowFim, indexColumn){
	var tbl = $(tableId);
	var iRowIniAux = 0;
	var iRowFimAux = tbl.rows.length;
	
	if (iRowIni != null) iRowIniAux = iRowIni;
	if (iRowFim != null) iRowFimAux = iRowFim;
	
	for(var i = iRowIniAux; i < iRowFimAux; i++){
		try{
			tbl.rows[i].cells[indexColumn].innerHTML = value;
		}catch(ex){
		}
	}
}
/*
 * Seta valores para elementos de dados no form - padrao do framework CIT
 */
HTMLUtils.setValuesForm = function(form,valores){
	var j = 0;
	var fAux = HTMLUtils.getForm();
	HTMLUtils.setForm(form);
	for(var i = 0; i < form.length; i++){
		var elem = form.elements[i];
		if (elem.name == null) continue;
	    var propriedade = elem.name;
		for (var property in valores) {
		  	if (property == propriedade){
		  		var value = valores[property];
		  		if (typeof(value) == 'object'){
		  			if (elem.name.indexOf('data') || elem.name.indexOf('dt') ||
		  				elem.name.indexOf('previsao') || elem.name.indexOf('conclusao')){
		  				//Se for data procura por uma propriedade <nome>+Str
		  				value = valores[property + 'Str'];
		  			}
		  		}
		  		HTMLUtils.setValue(elem.id, value);
		  		break;
		  	}
	    }
	}
	HTMLUtils.setForm(fAux);
}
/**
 * Limpa o formulario passado como parametro.
 *     Limpa os textos, coloca a combo no 1.o item e desmarca os check e radios.
 */
HTMLUtils.clearForm = function(form){
	var fAux = HTMLUtils.getForm();
	HTMLUtils.setForm(form);
	for(var i = 0; i < form.length; i++){
		var elem = form.elements[i];
		if (elem.name == null) continue;
		
		if (HTMLUtils._isHTMLElement(elem, "textarea")) {
			HTMLUtils.setValue(elem.id, '');
		}
		if (HTMLUtils._isHTMLElement(elem, "select")) {
			elem.selectedIndex = 0;
		}		
		if (HTMLUtils._isHTMLElement(elem, "input")) { 
			if (elem.type == "button"){ //Ignora os botoes.
				continue;
			}
			if (elem.type == "text"){
				HTMLUtils.setValue(elem.id, '');
			}		
			if (elem.type == "hidden"){
				HTMLUtils.setValue(elem.id, '');
			}				
			if (elem.type == "radio" || elem.type == "checkbox" || elem.type == "check-box") {
				elem.checked = false;
			}
		}		
	}
	HTMLUtils.setForm(fAux);
}
/*
 * Seta valores para elementos de dados de subclasses no form - padrao do framework CIT
 */
HTMLUtils.setValues = function(form,name,valores){
	var j = 0;
	var nameAux = '';
	if (name != null && name != undefined && name != '' && name != ' '){
		nameAux = name + '#';
	}
	for(var i = 0; i < form.length; i++){
		var elem = form.elements[i];
		if (elem.name == null) continue;
		var indice = elem.name.indexOf(nameAux);
		if (indice >= 0){ //Pertence ao objeto
		    var propriedade = elem.name.substr(indice+nameAux.length);
			for (var property in valores) {			
			  	if (property == propriedade){
			  		var value = valores[property];
			  		//if (typeof(value) == 'object'){
			  			if (elem.name.indexOf('data') >= 0 || elem.name.indexOf('dt') >= 0 ||
			  				elem.name.indexOf('previsao') >= 0 || elem.name.indexOf('conclusao') >= 0){
			  				//Se for data procura por uma propriedade <nome>+Str
			  				value = valores[property + 'Str'];
			  			}
			  		//}			  		
			  		HTMLUtils.setValue(elem.id, value);
			  		break;
			  	}
		    }			
		}
	}
}
/*
 * Seta os valores de um pedacao do formulacao (Agrupador) para um objeto javascript.
 */
HTMLUtils.setValuesObjectByGroupName = function(form,name,object){
	var j = 0;
	var nameAux = name + '#';
	if (form == null) return;
	for(var i = 0; i < form.length; i++){
		var elem = form.elements[i];
		if (elem.name == null) continue;
		var indice = elem.name.indexOf(nameAux);
		if (indice >= 0){ //Pertence ao objeto
		    var propriedade = elem.name.substr(indice+nameAux.length);
			for (var property in object) {
			  	if (property == propriedade){
			  		object[property] = HTMLUtils.getValue(elem.id);
			  		break;
			  	}
		    }			
		}
	}
}
/*
 * Seta os valores de um formulacao para um objeto javascript.
 */
HTMLUtils.setValuesObject = function(form,object){
	var j = 0;
	if (form == null) return;
	
	//Pega o form setado originalmente
	var fAux = HTMLUtils.getForm();
	//Atribui o form passado como parametro como o atual
	HTMLUtils.setForm(form);
	
	for(var i = 0; i < form.length; i++){
		var elem = form.elements[i];
		if (elem.name == null) continue;
	    var propriedade = elem.name;
		for (var property in object) {
		  	if (property == propriedade){
		  		object[property] = HTMLUtils.getValue(elem.id);
		  		break;
		  	}
	    }			
	}
	//Volta o form anterior.
	HTMLUtils.setForm(fAux);
}
/*
 * Bloqueia um subform conforme o nome (exemplo: risco#idrisco, risco#detalhamento, ...
 */
HTMLUtils.lockFormGroupName = function(form,name){
	HTMLUtils._lockUnlockFormGroupName(form,name,'L');
}
/*
 * Desbloqueia um subform conforme o nome (exemplo: risco#idrisco, risco#detalhamento, ...
 */
HTMLUtils.unLockFormGroupName = function(form,name){
	HTMLUtils._lockUnlockFormGroupName(form,name,'U');
} 
HTMLUtils._lockUnlockFormGroupName = function(form,name,type){
	var b = false;
	if (type == 'L'){
		b = true;
	}
	if (type == 'U'){
		b = false;
	}
	var j = 0;
	var nameAux = name + '#';
	for(var i = 0; i < form.length; i++){
		var elem = form.elements[i];
		if (HTMLUtils._isHTMLElement(elem, "input")) { 
			if (elem.type == "button"){ //Ignora os botoes.
				continue;
			}
		}
		if (elem.name == null) continue;
		var indice = elem.name.indexOf(nameAux);
		if (indice >= 0){ //Pertence ao objeto
			if (HTMLUtils._isHTMLElement(elem, "textarea")) {
				elem.readonly = b;
				if (b){
					if (HTMLUtils_ObjectLock.get(form.name + '_' + elem.name) == undefined){
						HTMLUtils_ObjectLock.set(form.name + '_' + elem.name, elem.onkeydown); //Guarda o evento original
					}
					elem.onkeydown = HTMLUtils_TrataLockTextArea;
				}else{
					if (HTMLUtils_ObjectLock.get(form.name + '_' + elem.name) == undefined){
						elem.onkeydown = null;
					}else{
						elem.onkeydown = HTMLUtils_ObjectLock.get(form.name + '_' + elem.name); //Restaura o evento original
					}				
				}
			}else{
				elem.disabled = b;
			}
		}
	}
}
function HTMLUtils_TrataLockTextArea(e){ //Faz com que o objeto fique readonly.
	if (e != undefined && e != null){
		e.returnValue = false;
		e.cancelBubble = true;	
	}
	return false;
}
HTMLUtils.lockField = function(elemRef){
	if (HTMLUtils._isHTMLElement(elemRef, "textarea")) {
		elemRef.readonly = true;
		if (HTMLUtils_ObjectLock.get(elemRef.form.name + '_' + elemRef.name) == undefined){
			HTMLUtils_ObjectLock.set(elemRef.form.name + '_' + elemRef.name, elemRef.onkeydown); //Guarda o evento original
		}
		elemRef.onkeydown = HTMLUtils_TrataLockTextArea;
	}else{
		elemRef.disabled = true;
	}
}
HTMLUtils.unlockField = function(elemRef){
	if (HTMLUtils._isHTMLElement(elemRef, "textarea")) {
		elemRef.readonly = false;
		if (HTMLUtils_ObjectLock.get(elemRef.form.name + '_' + elemRef.name) == undefined){
			elemRef.onkeydown = null;
		}else{
			elemRef.onkeydown = HTMLUtils_ObjectLock.get(elemRef.form.name + '_' + elemRef.name); //Restaura o evento original
		}	
	}else{
		elemRef.disabled = false;
	}
}
/*
 * Bloqueia um form todo
 */
HTMLUtils.lockForm = function(form){
	HTMLUtils._lockUnlockForm(form,'L');
}
/*
 * Desbloqueia um form todo
 */
HTMLUtils.unLockForm = function(form){
	HTMLUtils._lockUnlockForm(form,'U');
} 
HTMLUtils._lockUnlockForm = function(form,type){
	var b = false;
	if (type == 'L'){
		b = true;
	}
	if (type == 'U'){
		b = false;
	}
	var nameAux = name + '#';
	for(var i = 0; i < form.length; i++){
		var elem = form.elements[i];
		if (HTMLUtils._isHTMLElement(elem, "input")) { 
			if (elem.type == "button"){ //Ignora os botoes.
				continue;
			}
		}
		if (HTMLUtils._isHTMLElement(elem, "textarea")) {
			elem.readonly = b;
			if (b){
				if (HTMLUtils_ObjectLock.get(form.name + '_' + elem.name) == undefined){
					HTMLUtils_ObjectLock.set(form.name + '_' + elem.name, elem.onkeydown); //Guarda o evento original
				}
				elem.onkeydown = HTMLUtils_TrataLockTextArea;
			}else{
				if (HTMLUtils_ObjectLock.get(form.name + '_' + elem.name) == undefined){
					elem.onkeydown = null;
				}else{
					elem.onkeydown = HTMLUtils_ObjectLock.get(form.name + '_' + elem.name); //Restaura o evento original
				}				
			}			
		}else{
			elem.disabled = b;
		}
	}
}
/*
 * Seta o valor p/ um elemento de HTML
 */
HTMLUtils.setValue = function(ele, val) {
  if (val == null) val = "";

  var orig = ele;
  var nodes, i;

  //Tenta pegar o Elemento do Formulario. Caso nao consiga vai pelo Id.
  var formAux = HTMLUtils.getForm();
  var eleAux = null;
  eleAux = formAux[ele];
  if (eleAux == null || eleAux == undefined || eleAux == ''){
  	  //Se nao encontrar o elemento do Form, pega pelo Id.
	  ele = $(ele);
  }else{
  	  if (eleAux.length){
  	  	if (eleAux[0].type == 'radio'){
	  	  	ele = eleAux[0];
	  	}else{
	  		ele = eleAux;
	  	}
  	  }else{
  	  	ele = eleAux;
  	  }
  }
  
  // We can work with names and need to sometimes for radio buttons
  if (ele == null) {
    nodes = document.getElementsByName(orig);
    if (nodes.length >= 1) {
      ele = nodes.item(0);
    }
  }
  if (ele == null) {
    //alert("setValue() n?o conseguiu encontrar um elemento com o ID/NOME: " + orig + ".");
    return;
  }

  if (HTMLUtils._isHTMLElement(ele, "select")) {
    if (ele.type == "select-multiple" && HTMLUtils._isArray(val)) {
      HTMLUtils._selectListItems(ele, val);
    }
    else {
      HTMLUtils._selectListItem(ele, val);
    }
    return;
  }

  if (HTMLUtils._isHTMLElement(ele, "input")) {
    if (ele.type == "radio" || ele.type == "checkbox" || ele.type == "check-box") {
    	  var f = HTMLUtils.getForm();
    	  eval('var elemento = f["' + ele.name + '"]');
    	  if (elemento.length){
		      for (var i = 0; i < elemento.length; i++) {
		          if (elemento[i].value == val) {
		            elemento[i].checked = true;
		          }else{
		            elemento[i].checked = false;
		          }
		      }
		  }else{
		      if (ele.value == val){
		      	ele.checked = true;
		      }else{
		      	ele.checked = false;
		      }
		  }
		  return;
    }else{
      ele.value = val;
      return;
    }  
  }

  if (HTMLUtils._isHTMLElement(ele, "textarea")) {
    ele.value = val;
    return;
  }

  // If the value to be set is a DOM object then we try importing the node
  // rather than serializing it out
  if (val.nodeType) {
    if (val.nodeType == 9 /*Node.DOCUMENT_NODE*/) {
      val = val.documentElement;
    }

    val = HTMLUtils._importNode(ele.ownerDocument, val, true);
    ele.appendChild(val);
    return;
  }

  ele.innerHTML = val;
};
/*
 * Seta o valor p/ um elemento de CheckBox do HTML
 */
HTMLUtils.setValueCheckBox = function(ele, val) {
  var orig = ele;
  var nodes, i;

  ele = $(ele);
  // We can work with names and need to sometimes for radio buttons
  if (ele == null) {
    nodes = document.getElementsByName(orig);
    if (nodes.length >= 1) {
      ele = nodes.item(0);
    }
  }
  if (ele == null) {
    //alert("setValue() n?o conseguiu encontrar um elemento com o ID/NOME: " + orig + ".");
    return;
  }
  if (HTMLUtils._isHTMLElement(ele, "input")) {
    if (ele.type == "checkbox" || ele.type == "check-box") {
    	  var f = HTMLUtils.getForm();
    	  var bOk = false;
    	  eval('var elemento = f["' + ele.name + '"]');
    	  if (elemento.length){
		      for (var i = 0; i < elemento.length; i++) {
		      	bOk = false;
		      	if (val != null){
		      		if (val.length){
				      	for(var j = 0; j < val.length; j++){
				      		if (elemento[i].value == val[j]) {
				      			bOk = true;
				      		}
				      	}
				    }else{
			      		if (elemento[i].value == val) {
			      			bOk = true;
			      		}				    
				    }
			    }
		      	if (bOk){
		            elemento[i].checked = true;
	            }else{
		            elemento[i].checked = false;
		        }
		      }
		  }else{
		  	  bOk = false;
		  	  if (val != null){
		  	  	if (val.length){
			      for(var j = 0; j < val.length; j++){
			      	if (ele.value == val[j]) {
			      		bOk = true;
			      	}
			      }	
			    }else{
			      	if (ele.value == val) {
			      		bOk = true;
			      	}			    	
			    }
			  }	  
		      if (bOk){
		      	ele.checked = true;
		      }else{
		      	ele.checked = false;
		      }
		  }
		  return;
    }
  }
}
/*
 * Seta o checkbox. Se encontrar o valor no array de checkbox entao atribui checked = true.
 *    Exemplo HTMLUtils.setMultiCheckBox.(form.instrumentosAvaliacaoComplementar, 100);
 *                          --> Caso encontre um valor 100 no array de checkbox (instrumentosAvaliacaoComplementar)
 *                          --> setar? checked para true.
 */
HTMLUtils.setMultiCheckBox = function(ele, val){
	if (ele != null) {
		for(var i = 0; i < ele.length; i++){
			if (ele[i].value == val){
				ele[i].checked = true;
			}
		}
	}
};

HTMLUtils._isHTMLElement = function(ele, nodeName) {
  if (ele == null || typeof ele != "object" || ele.nodeName == null) {
    return false;
  }

  if (nodeName != null) {
    var test = ele.nodeName.toLowerCase();

    if (typeof nodeName == "string") {
      return test == nodeName.toLowerCase();
    }

    if (HTMLUtils._isArray(nodeName)) {
      var match = false;
      for (var i = 0; i < nodeName.length && !match; i++) {
        if (test == nodeName[i].toLowerCase()) {
          match =  true;
        }
      }
      return match;
    }

    alert("HTMLUtils._isHTMLElement recebeu um n? de teste que nem ? uma String nem um array de Strings");
    return false;
  }

  return true;
};

HTMLUtils._selectListItem = function(ele, val) {
  // We deal with select list elements by selecting the matching option
  // Begin by searching through the values
  var found  = false;
  var i;
  for (i = 0; i < ele.options.length; i++) {
    if (ele.options[i].value == val) {
      ele.options[i].selected = true;
      found = true;
    }
    else {
      ele.options[i].selected = false;
    }
  }

  // If that fails then try searching through the visible text
  if (found) return;

  for (i = 0; i < ele.options.length; i++) {
    if (ele.options[i].text == val) {
      ele.options[i].selected = true;
      break;
    }
  }
}

HTMLUtils._selectListItems = function(ele, val) {
  var found  = false;
  var i;
  var j;
  for (i = 0; i < ele.options.length; i++) {
    ele.options[i].selected = false;
    for (j = 0; j < val.length; j++) {
      if (ele.options[i].value == val[j]) {
        ele.options[i].selected = true;
      }
    }
  }

  if (found) return;

  for (i = 0; i < ele.options.length; i++) {
    for (j = 0; j < val.length; j++) {
      if (ele.options[i].text == val[j]) {
        ele.options[i].selected = true;
      }
    }
  }
};

HTMLUtils._isArray = function(data) {
  return (data && data.join) ? true : false;
};

HTMLUtils._importNode = function(doc, importedNode, deep) {
  var newNode;

  if (importedNode.nodeType == 1 /*Node.ELEMENT_NODE*/) {
    newNode = doc.createElement(importedNode.nodeName);

    for (var i = 0; i < importedNode.attributes.length; i++) {
      var attr = importedNode.attributes[i];
      if (attr.nodeValue != null && attr.nodeValue != '') {
        newNode.setAttribute(attr.name, attr.nodeValue);
      }
    }

    if (typeof importedNode.style != "undefined") {
      newNode.style.cssText = importedNode.style.cssText;
    }
  }
  else if (importedNode.nodeType == 3 /*Node.TEXT_NODE*/) {
    newNode = doc.createTextNode(importedNode.nodeValue);
  }

  if (deep && importedNode.hasChildNodes()) {
    for (i = 0; i < importedNode.childNodes.length; i++) {
      newNode.appendChild(HTMLUtils._importNode(doc, importedNode.childNodes[i], true));
    }
  }

  return newNode;
}
/*
 * Obtem o valor de um elemento de HTML
 */
HTMLUtils.getValue = function(ele) {
  var orig = ele;
  ele = $(ele);
  // We can work with names and need to sometimes for radio buttons, and IE has
  // an annoying bug where
  var nodes = document.getElementsByName(orig);
  if (ele == null && nodes.length >= 1) {
    ele = nodes.item(0);
  }
  if (ele == null) {
    alert("getValue() n?o conseguiu encontrar um elemento com o ID/NOME: " + orig + ".");
    return "";
  }

  if (HTMLUtils._isHTMLElement(ele, "select")) {
    // This is a bit of a scam because it assumes single select
    // but I'm not sure how we should treat multi-select.
    var sel = ele.selectedIndex;
    if (sel != -1) {
      var reply = ele.options[sel].value;
      return reply;
    }
    else {
      return "";
    }
  }

  if (HTMLUtils._isHTMLElement(ele, "input")) {
    if (ele.type == "radio" || ele.type == "checkbox" || ele.type == "check-box") {
    	  var f = HTMLUtils.getForm();
    	  eval('var elemento = f["' + ele.name + '"]');
    	  if (elemento.length){
		      for (var i = 0; i < elemento.length; i++) {
		          if (elemento[i].checked) {
		            return elemento[i].value;
		          }
		      }
		      return '';
		  }else{
		  	  if (elemento.checked) {
		  	  	return elemento.value;
		  	  }else{
		  	  	return '';
		  	  }
		  }
    }else{
      return ele.value;
    }
  }

  if (HTMLUtils._isHTMLElement(ele, "textarea")) {
    return ele.value;
  }

  return ele.innerHTML;
};

function HTMLUtil_TrowOn(src,OnColor){
    src.bgColor = OnColor;
}
function HTMLUtil_TrowOff(src,OffColor){
    src.bgColor = OffColor;
}
/*
 * Coloca um elemento do HTML invisivel ou visivel.
 */
HTMLUtils.setVisible = function(ele,vis) {
  ele = $(ele);
  if (vis){
	  ele.style.display = 'block';
  }else{
  	  ele.style.display = 'none';
  }
}
/*
 * Coloca o foco no elemento do HTML invisivel ou visivel.
 */
HTMLUtils.setFocus = function(ele) {
  var orig = ele;
  ele = $(ele);
  if (ele == undefined || ele == null){
  	var f = HTMLUtils.getForm();
  	eval('ele = f["' + orig + '"]');
  }
  if (ele != undefined && ele != null){
	  if (ele.type != "hidden" && !ele.disabled) {
	  	try{
	     ele.focus();
	    }catch(ex){
	    }
	  }
  }  
}
/*
 * Adiciona uma option em um select
 */
HTMLUtils.addOption = function(idCombo, texto, valor){
    var cbo = $(idCombo);
    if(cbo == null || cbo == undefined){
	  	var f = HTMLUtils.getForm();
	  	eval('cbo = f["' + idCombo + '"]');    	
    }
    if(cbo != null && cbo != undefined){
	  var o = new Option(texto, valor);
      cbo.options[cbo.options.length] = o;
      return o;
    }
}
/*
 * Adiciona uma option em um select se ele ainda nao existir.
 *    Se existir ignora.
 */
HTMLUtils.addOptionIfNotExists = function(idCombo, texto, valor){
    var cbo = $(idCombo);
    if(cbo == null || cbo == undefined){
	  	var f = HTMLUtils.getForm();
	  	eval('cbo = f["' + idCombo + '"]');    	
    }
    if(cbo != null && cbo != undefined){
		for(var i = 0; i < cbo.options.length; i++){
			if (cbo.options[i].value == valor){ //Se ja existir, cai fora... nada a fazer.
				return;
			}
		}
		//Se chegar aqui eh que nao existe, entao inclui.
		HTMLUtils.addOption(idCombo, texto, valor);
    }	
}
/*
 * Adiciona options no Select.
 *     idCombo = Identificacao do Combo no HTML
 *     array = o Array de Objetos a ser acrescentado no Select
 *     propId = Propriedade do Objeto que será colocado no value
 *     propText = Propriedade do Objeto que será colocado no Text
 *     defaultId = valor default que deve aparecer marcado (selecionado)
 */
HTMLUtils.addOptions = function(idCombo,array,propId,propText,defaultId){
	if (array == null || array == undefined) return;
    var cbo = $(idCombo);
    if(cbo == null || cbo == undefined){
	  	var f = HTMLUtils.getForm();
	  	eval('cbo = f["' + idCombo + '"]');    	
    }
    if(cbo != null && cbo != undefined){ //Se a combo existir
    	for(var i = 0; i < array.length; i++){
    		var obj = array[i];
			var o = new Option(obj[propText], obj[propId]);
		    cbo.options[cbo.options.length] = o;    		
			if (obj[propId] == defaultId){
				cbo.options[cbo.options.length].selected = true;
			}
    	}
    }
}
/*
 * Seta o indice selecionado no objeto Select.
 */
HTMLUtils.setOptionSelected = function(idCombo, indice){
    var cbo = $(idCombo);
    if(cbo == null || cbo == undefined){
	  	var f = HTMLUtils.getForm();
	  	eval('cbo = f["' + idCombo + '"]');    	
    }
    if(cbo != null && cbo != undefined){ //Se a combo existir
    	try{
    		cbo.options[indice].selected = true;
    	}catch(ex){
    	}
    }	
}
/*
 * Remove uma option em um select
 */
HTMLUtils.removeOption = function(idCombo, index){
    var cbo = $(idCombo);
    if(cbo == null || cbo == undefined){
	  	var f = HTMLUtils.getForm();
	  	eval('cbo = f["' + idCombo + '"]');    	
    }    
    if(cbo != null && cbo != undefined){
	  cbo.options[index] = null;
    }
}
/*
 * Remove todos os elementos de um Select
 */
HTMLUtils.removeAllOptions = function(idCombo){
    var cbo = $(idCombo);
    if(cbo == null || cbo == undefined){
	  	var f = HTMLUtils.getForm();
	  	eval('cbo = f["' + idCombo + '"]');    	
    }
    if(cbo != null && cbo != undefined){
    	for(var i = cbo.length -1; i >= 0; i--){
	    	cbo.options[i] = null;
	    }
    }
}
/*
 * Remove a option selecionado do select
 */
HTMLUtils.removeOptionSelected = function(idCombo){
    var cbo = $(idCombo);
    if(cbo == null || cbo == undefined){
	  	var f = HTMLUtils.getForm();
	  	eval('cbo = f["' + idCombo + '"]');    	
    }
    if(cbo != null && cbo != undefined){
    	if (cbo.selectedIndex > -1){
    		cbo.options[cbo.selectedIndex] = null;
    	}
    }
}
/*
 * Adiciona evento a um determinado objeto (window, input, ...)
 *    Este evento novo será adicionado apos a execucao do evento que ja estiver associado.
 *    Exemplo de utilizacao: HTMLUtils.addEvent(window, "load", loadAdicional, false);
 *           No exemplo sera adicionado ao evento "load" a funcao "loadAdicional".
 */
HTMLUtils.addEvent = function(object, evType, func, useCapture){
    if(object.addEventListener){
        object.addEventListener(evType, func, useCapture);
    } else {
	    if(object.attachEvent){
	        object.attachEvent("on" + evType, func);
	    }
	}
}
HTMLUtils.removeEvent = function( obj, type, fn ) {
	if ( obj.detachEvent ) {
	    obj.detachEvent( 'on'+type, fn );  
	} else {    
		obj.removeEventListener( type, fn, false ); 
	}
}
/*
 * Faz a verificacao para identificar se um determinado valor ja existe no select
 *       retornando true em caso de existir, e
 *                  false em caso de nao existir.
 */
HTMLUtils.existInSelect = function(idSelect, value){
	var select = document.getElementById(idSelect);
	if (select != null && select != undefined){
		for(var i = 0; i < select.options.length; i++){
			if (select.options[i].value == value){
				return true;
			}
		}
	}
	return false;
}
/*
 * Coloca o foco no 1.o elemento do form
 */
HTMLUtils.focusInFirstActivateField = function(form){
	if (arguments.length <= 0 || form == null || form == undefined){
		form = HTMLUtils.getForm();
	}
	for(var i = 0; i < form.length; i++){
		var elem = form.elements[i];
		if (HTMLUtils._isHTMLElement(elem, "input")) { 
			if (elem.type == "button" || elem.type == "hidden"){ //Ignora os botoes e hiddens.
				continue;
			}
		}
		if (elem.readOnly != undefined){
			if (elem.readOnly == false && elem.disabled == false){
				try{
					elem.focus();
					return;
				}catch (ex) {
					//Nao deu certo, este campo nao aceitava foco.
					continue;
				}
			}
		}else{
			if (elem.disabled == false){
				try{
					elem.focus();
					return;
				}catch (ex) {
					//Nao deu certo, este campo nao aceitava foco.
					continue;
				}
			}		
		}
	}	
}
/*
 * Aplica um estilo ou classe de css a todas as celulas de uma tabela.
 *    Parametros: ele - representa o ID da tabela (String)
 *                classNameParm - String com o nome da classe a ser aplicada.
 */
HTMLUtils.applyStyleClassInAllCells = function(ele, classNameParm){
   if (classNameParm == null) return;
   var tbl = document.getElementById(ele);
   if (tbl == null || tbl == undefined) return;
   for(var i = tbl.rows.length - 1; i >= 1; i--){
   		for(var j = 0; j < tbl.rows[i].cells.length; j++){
   			if (classNameParm != null){
	   			tbl.rows[i].cells[j].className = classNameParm;
	   		}
	   	}
   }	
}

/**
  Obtem a posicao de um elemento do html
  
  retorna um objeto, contendo o 'x' e o 'y'
  exemplo:
  	var obj = HTMLUtils.getPosElement('campoNome');
  	alert(obj.x);
  	alert(obj.y);
  OBS: o referencial de posicao a tela ou o objeto pai com posicao absoluta.
*/
HTMLUtils.getPosElement = function ( ele )
{
	var ele = document.getElementById(ele);

	var obj = ele;

	var curleft = 0;
	if (obj.offsetParent)
	{
		while (obj.offsetParent)
		{
			if (obj.offsetParent.style.position == 'absolute'){
				break;
			}		
			curleft += obj.offsetLeft
			obj = obj.offsetParent;
		}
	}
	else if (obj.x)
		curleft += obj.x;


	var obj = ele;
	
	var curtop = 0;
	if (obj.offsetParent)
	{
		while (obj.offsetParent)
		{
			if (obj.offsetParent.style.position == 'absolute'){
				break;
			}
			curtop += obj.offsetTop
			obj = obj.offsetParent;
		}
	}
	else if (obj.y)
		curtop += obj.y;

	return {x:curleft, y:curtop}
}
/*
  Atribui cor para uma TR
  	Deve-se passar a TR como referencia.
  		Exemplo: na declaracao da TR, coloque:
  					<TR onMouseOver="HTMLUtils.setColorTR(this,'#FFCC99')" onMouseOut="HTMLUtils.setColorTR(this,'white')" >
  						...
  					</TR>
*/
HTMLUtils.setColorTR = function(src,color){
	src.bgColor = color;
}
/*
    Mostra uma DIV suspensa na posicao a esquerda de um objeto de referencia.
    	Exemplo: queremos apresentar uma div, log abaixo de um botão:
    			<input ... onclick='HTMLUtils.showPopupInObjectReferenceLeft(this, "telaGrafico", "menuCfg, menuOutros")' />
    				Isto fara que quando clicado no botao a DIV apareca logo abaixo do botão do lado esquerdo. 
    				As divs de IDs = menuCfg e menuOutros serao colocadas invisiveis.
    				Se clicado novamente (enquanto a DIV estiver visivel) a DIV desaparece.
    			Obs.: Para torna invisivel atraves de comando, utilize:   HTMLUtils.hide("telaGrafico");
    			
    				O parametro idsDIVsToHide deve ser separado por virgulas. Se nao quiser passar este parametro utilize null.
    				
 ------> Importante!!!!!: Este funcao e dependente de:  NumberUtil.js, StringUtils.js
    			
*/
HTMLUtils.showPopupInObjectReferenceLeft = function(objComparar, idDIV, idsDIVsToHide){
	if (idsDIVsToHide != null && idsDIVsToHide != undefined && idsDIVsToHide != ''){
		var divToHide = idsDIVsToHide + ',';
		var divSplit = divToHide.split(',');
		for(var j = 0; j < divSplit.length; j++){
			if (!StringUtils.isBlank(StringUtils.trim(divSplit[j]))){
				HTMLUtils.hide(StringUtils.trim(divSplit[j]));
			}
		}
	}

	var objDiv = document.getElementById(idDIV);
	if (objDiv.style.display == 'block'){
		objDiv.style.display='none';
		return;
	}
	
	var objComp = HTMLUtils.getPosElement(objComparar.id);

	objDiv.style.top = objComp.y + objComparar.offsetHeight;
	
	var w = objDiv.offsetWidth;
	if (w == 0){
		try{
			w = NumberUtil.toInteger(objDiv.style.width);
		}catch(ex){
		}
	}
	objDiv.style.left = (objComp.x - w) + objComparar.offsetWidth; 
	
	objDiv.style.display='block';
} 
/*
    Mostra uma DIV suspensa na posicao a esquerda de um objeto de referencia.
    	Exemplo: queremos apresentar uma div, log abaixo de um botão:
    			<input ... onclick='HTMLUtils.showPopupInObjectReferenceRight(this, "telaGrafico", "menuCfg, menuOutros")' />
    				Isto fara que quando clicado no botao a DIV apareca logo abaixo do botão do lado direito. 
    				As divs de IDs = menuCfg e menuOutros serao colocadas invisiveis.
    				Se clicado novamente (enquanto a DIV estiver visivel) a DIV desaparece.
    			Obs.: Para torna invisivel atraves de comando, utilize:   HTMLUtils.hide("telaGrafico");
    			
    				O parametro idsDIVsToHide deve ser separado por virgulas. Se nao quiser passar este parametro utilize null.
    				
 ------> Importante!!!!!: Este funcao e dependente de:  NumberUtil.js, StringUtils.js
    			
*/
HTMLUtils.showPopupInObjectReferenceRight = function(objComparar, idDIV, idsDIVsToHide){
	if (idsDIVsToHide != null && idsDIVsToHide != undefined && idsDIVsToHide != ''){
		var divToHide = idsDIVsToHide + ',';
		var divSplit = divToHide.split(',');
		for(var j = 0; j < divSplit.length; j++){
			if (!StringUtils.isBlank(StringUtils.trim(divSplit[j]))){
				HTMLUtils.hide(StringUtils.trim(divSplit[j]));
			}
		}
	}

	var objDiv = document.getElementById(idDIV);
	if (objDiv.style.display == 'block'){
		objDiv.style.display='none';
		return;
	}
	
	var objComp = HTMLUtils.getPosElement(objComparar.id);

	objDiv.style.top = objComp.y + objComparar.offsetHeight;
	
	objDiv.style.left = objComp.x; 
	
	objDiv.style.display='block';
} 
/*
	Torna invisivel um elemento da tela.
		Deve ser passado o ID do objeto que deseja tornar invisivel.
*/
HTMLUtils.hide = function(idObjeto){
	var obj = document.getElementById(idObjeto);
	if (obj != null && obj != undefined){
		obj.style.display='none';
	}
}
/*
	Torna visivel um elemento da tela.
		Deve ser passado o ID do objeto que deseja tornar visivel.
*/
HTMLUtils.show = function(idObjeto){
	var obj = document.getElementById(idObjeto);
	if (obj != null && obj != undefined){
		obj.style.display='block';
	}
}
/*
	Seta o valor de INNER HTML para um objeto
		Deve ser passado o ID do objeto que deseja atribuir o texto + o texto.
*/
HTMLUtils.setInnerHTML = function(idObjeto, str){
	var obj = document.getElementById(idObjeto);
	if (obj != null && obj != undefined){
		try{
			obj.innerHTML = str;
		}catch(ex){
			alert('HTMLUtils Erro: Este objeto nao possui [innerHTML].');
		}
	}
}
/*
gera um botao fechar para um div, atraves de outra

A DIV DEVE ESTAR DENTRO DA OUTRA.
*/
HTMLUtils.generateCloseButton = function(idDivColocarBotao, idDivBotaoFechar){
	var objColocar = document.getElementById(idDivColocarBotao);
	var objFechar = document.getElementById(idDivBotaoFechar);
	
	//var objComp = HTMLUtils.getPosElement(objComparar.id);
	
	objFechar.style.top = objColocar.offsetTop + 'px';
	objFechar.style.left = ((objColocar.offsetWidth - objFechar.offsetWidth) - 20) + 'px'; 
	objFechar.style.display = 'block';
}
/*
Centraliza uma DIV no meio da Tela.

Para centrazalizar sempre que a barra de rolagem se mexe, basta fazer como abaixo:
window.onload = function(){
       refreshDiv();
       window.onresize = refreshDiv;
       window.onscroll = refreshDiv;
}    
*/
HTMLUtils.centralizaDIV = function(idDiv){
   document.getElementById(idDiv).style.top = ((((document.body.clientHeight*50)/100)+window.pageYOffset)-(parseInt(document.getElementById(idDiv).style.height.substr(0,document.getElementById(idDiv).style.width.length-2))))+'px';
   document.getElementById(idDiv).style.left = (((document.body.clientWidth*50)/100)-(parseInt(document.getElementById(idDiv).style.height.substr(0,document.getElementById(idDiv).style.height.length-2))))+'px';
}
HTMLUtils.getYOffset = function() {
    var pageY;
    if(typeof(window.pageYOffset)=='number') {
       pageY=window.pageYOffset;
    }
    else {
       pageY=document.documentElement.scrollTop;
    }
    return pageY;
}