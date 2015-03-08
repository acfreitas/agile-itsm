function ObjectUtils() { }
/*
 * Serializa um array de objetos em string para envio ao servidor.
 */
ObjectUtils.serializeObjects = function(array){
	var result = '';
	for(var i = 0; i < array.length; i++){
		result = result + '\3\2';
		result = result + ObjectUtils.serializeObject(array[i]);
		result = result + '\5';
	}
	return result;
};
/*
 * Serializa um objeto em string para envio ao servidor.
 */
ObjectUtils.serializeObject = function(objeto){
  var strResult = '';
  for (var property in objeto) {
	  if (typeof objeto[property] == 'function'){
		  continue;
	  }
      var value = objeto[property];
      strResult = strResult + '\6\2';
      strResult = strResult + property + '\4\2' + ObjectUtils.codificaEnter(value) + '\5';
      strResult = strResult + '\5';
  }
  return strResult;
};
ObjectUtils.codificaEnter = function(str){
	var x = new String(str);
	x = x.replace(/\r/g,'#10#');
	return x.replace(/\n/g,'#13#');
};
ObjectUtils.decodificaEnter = function(str){
	var x = new String(str);
	x = x.replace(/#10#/g,'\r');
	return x.replace(/#13#/g,'\n');
};
ObjectUtils.decodificaAspasApostrofe = function(str){
	var x = new String(str);
	x = x.replace(/#32#/g,'"');
	var ret = x.replace(/#33#/g,"'");
	return ret;
};
ObjectUtils.mantemAspasApostrofe = function(str){
	var x = new String(str);
	
	x = x.replace(/\\/g, "\\\\");
	x = x.replace(/\"/g, '\\"');
	//x = x.replace(/'/g, "\\'");
	//x = x.replace(/\'/g, "\\'");	
	
	return x;
};
/**
 * Deserializa uma colecao de objetos atraves do valor passado como parametro.
 */
ObjectUtils.deserializeCollectionFromString = function(valor){
 	var col = new Array();
	var strArray = ObjectUtils._separaObjetos(valor, '\3'); //Esta string representa a colecao de objetos serializados
	if (strArray == null) return null;
	for(var j = 0; j < strArray.length; j++){
		var obj = ObjectUtils.deserializeObject(strArray[j]);
		col[j] = obj;
	}
	return col;		
};
ObjectUtils.deserializeCollectionFromStringSemQuebraEnter = function(valor){
 	var col = new Array();
	var strArray = ObjectUtils._separaObjetos(valor, '\3'); //Esta string representa a colecao de objetos serializados
	if (strArray == null) return null;
	for(var j = 0; j < strArray.length; j++){
		var obj = ObjectUtils.deserializeObjectSemQuebraEnter(strArray[j]);
		col[j] = obj;
	}
	return col;		
};
/**
 * Recebe a string contendo o objeto serializado
 * 			Exemplo: deserializeObject(idFuncao\47\6idCargo\49\6....");
 * 				Onde isso representa: idFuncao=7;idCargo=9;
 *              e gera um objeto em javascript.
 */
ObjectUtils.deserializeObject = function(value){
	var str = ObjectUtils._separaObjetos(value, '\6'); //Quebra os atributos
	var obj = new Object();
	//Faz o tratamento dos pares propriedade=valor
	var propriedadesValores;
	for(var i = 0; i < str.length; i++){
		propriedadesValores = ObjectUtils._separaByToken(str[i], '\4'); 
		try{
			eval('obj.' + propriedadesValores[0] + ' = ObjectUtils.decodificaEnter("' + ObjectUtils.mantemAspasApostrofe(propriedadesValores[1]) + '")');
		}catch(ex){
			alert('Erro em ObjectUtils.deserializeObject [' + ex.message + '] \n\n\n' + ObjectUtils.mantemAspasApostrofe(propriedadesValores[1]));
		}
	}
	return obj;
};
ObjectUtils.deserializeObjectSemQuebraEnter = function(value){
	var str = ObjectUtils._separaObjetos(value, '\6'); //Quebra os atributos
	var obj = new Object();
	//Faz o tratamento dos pares propriedade=valor
	var propriedadesValores;
	
	for(var i = 0; i < str.length; i++){
		propriedadesValores = ObjectUtils._separaByToken(str[i], '\4'); 
		try{
			eval('obj.' + propriedadesValores[0] + ' = "' + ObjectUtils.mantemAspasApostrofe(ObjectUtils.codificaEnter(propriedadesValores[1])) + '"');
		}catch(ex){
			alert('Erro na conversao: ' + 'obj.' + propriedadesValores[0] + ' = "' + propriedadesValores[1] + '"');
		}
	}
	return obj;
};
ObjectUtils._separaObjetos = function(str, token){
	if (str == undefined) return null;
	if (str == null) return null;
	var col = new Array();
	var obj = null;
	var bIniciou = false;
	var qtdeChaveAberta = 0;
	var iControle = 0;
	for(var i = 0; i < str.length; i++){
		if (str.charAt(i)==token && qtdeChaveAberta == 0){
			if (obj != null){
				col[iControle] = obj;
				iControle++;
			}
			obj = new String("");
		}else{
			if (str.charAt(i)=='\5'){
				qtdeChaveAberta--;
			}				
			if (bIniciou){
				if (obj != null && qtdeChaveAberta > 0){
					obj = obj + str.charAt(i);
				}
			}				
			if (str.charAt(i)=='\2'){
				bIniciou = true;
				qtdeChaveAberta++;
			}					
		}
	}	
	if (obj != null){
		col[iControle] = obj;
	}
	return col;
};
/**
 * Esta funcao quebra os tokens de objetos.
 * 	Ele deve ser usada no lugar do Split pois podem existir objetos dentro de objetos.
 */
ObjectUtils._separaByToken = function(str, token){
	var propriedade = "";
	var valor = "";
	var bProp = true;
	var bIniciou = false;
	var qtdeChaveAberta = 0;
	for(var i = 0; i < str.length; i++){
		if (str.charAt(i)==token){
			bProp = false;
		}
		if (bProp){
			propriedade = propriedade + str.charAt(i);
		}else{
			if (str.charAt(i)=='\5'){
				qtdeChaveAberta--;
				if (qtdeChaveAberta == 0){
					break;
				}
			}				
			if (bIniciou && qtdeChaveAberta > 0){
				valor = valor + str.charAt(i);
			}				
			if (str.charAt(i)=='\2'){
				bIniciou = true;
				qtdeChaveAberta++;
			}
		}
	}
	var strRetorno = new Array (propriedade, valor);
	return strRetorno;
};
/*
 * Converte todos os objetos Date de um obj para Date no formato dd/MM/yyyy (String).
 *    Isto eh importante quando se trabalha com o DWR.
 */
ObjectUtils.convertDatesOfObject = function(obj){
	for (var property in obj) {
		if (obj[property] instanceof Date){
			obj[property] = DateTimeUtil.formatDate(obj[property], 'dd/MM/yyyy');
		}
	}
	return obj;
}