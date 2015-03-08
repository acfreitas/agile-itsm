function FormatUtils() { }

/* -----------------------------------------------------------------------
 * Formata campo (qualquer formato, principalmente data).
   -----------------------------------------------------------------------
 */
FormatUtils.formataCampo = function(objForm, strField, sMask, evtKeyPress) {
  var i, nCount, sValue, fldLen, mskLen,bolMask, sCod, nTecla;
  var bRetorno = true;
  if (evtKeyPress == null || evtKeyPress == undefined){
  	evtKeyPress = window.event;
  }
  nTecla = evtKeyPress.charCode? evtKeyPress.charCode : evtKeyPress.keyCode
  
  if (nTecla == 9 || nTecla == 46 || nTecla == 37 || nTecla == 38 || nTecla == 39 || nTecla == 40){
  	 //TAB ou Delete... e SETAS
  	 bRetorno = true;
  }else{
	  if ((nTecla < 48 || nTecla > 57) && (nTecla < 96 || nTecla > 105)){ //Letras devem ser descartadas
		 	if (nTecla != 8 && nTecla != 39){ // Somentes caracteres de 0 a 9, e backspace
				evtKeyPress.returnValue = false;
				evtKeyPress.cancelBubble = true;
				bRetorno = false;		
	 		}
	  }
  }
  sValue = objForm[strField].value;
  var bMaior = false;
  /*
  if (sMask.length <= sValue.length){
		evtKeyPress.returnValue = false;
		evtKeyPress.cancelBubble = true;  
      	return false;
  }
  */

  // Limpa todos os caracteres de formatacao que je estiverem no campo.
  sValue = sValue.toString().replace( "-", "" );
  sValue = sValue.toString().replace( "-", "" );
  sValue = sValue.toString().replace( ".", "" );
  sValue = sValue.toString().replace( ".", "" );
  sValue = sValue.toString().replace( ".", "" );
  sValue = sValue.toString().replace( ".", "" );
  sValue = sValue.toString().replace( "/", "" );
  sValue = sValue.toString().replace( "/", "" );
  sValue = sValue.toString().replace( "(", "" );
  sValue = sValue.toString().replace( "(", "" );
  sValue = sValue.toString().replace( ")", "" );
  sValue = sValue.toString().replace( ")", "" );
  sValue = sValue.toString().replace( ":", "" );
  sValue = sValue.toString().replace( ":", "" );
  sValue = sValue.toString().replace( ":", "" );
  sValue = sValue.toString().replace( ":", "" );
  
  var sAux = '';
  for(var j = 0; j < sValue.length; j++){
  	sAux += FormatUtils.apenasNumeros(sValue.charAt(j));
  }
  sValue = sAux;
  
  fldLen = sValue.length;
  mskLen = sMask.length;

  i = 0;
  nCount = 0;
  sCod = "";
  mskLen = fldLen;

	if (nTecla != 8 && nTecla != 46 && nTecla != 37 && nTecla != 38 && nTecla != 39 && nTecla != 40) {
	  while (i <= mskLen) {
		bolMask = ((sMask.charAt(i) == "-") || (sMask.charAt(i) == ".") || (sMask.charAt(i) == "/"))
		bolMask = bolMask || ((sMask.charAt(i) == "(") || (sMask.charAt(i) == ")") || (sMask.charAt(i) == " ") || (sMask.charAt(i) == ":"))
		if (bolMask) {
		  sCod += sMask.charAt(i);
		  mskLen++; 
		}else {
		  sCod += FormatUtils.apenasNumeros(sValue.charAt(nCount));
		  nCount++;
		}
		i++;
	  }
	  objForm[strField].value = sCod;
	} else {
		//sCod = left(objForm[strField].value, objForm[strField].value.length-1);
	}
	return bRetorno;
}

/* -----------------------------------------------------------------------
 * Formata campo tipo hora. hh:mm
   -----------------------------------------------------------------------
 */
FormatUtils.formataCampoHora = function(fieldObj) {
  var fmt = "";
  var fieldValue = fieldObj.value;
  var currentChar;
  for (var i = 0; i < fieldValue.length; i++) {
    currentChar = fieldValue.charAt(i);
    if (fmt.length == 2) {
      fmt += ":";
    }
    if (isInteger(currentChar)) {
      fmt += currentChar;
    }
    if (fmt.length == 5) {
      break;
    }
  }
  fieldObj.value = fmt;
}
/* -----------------------------------------------------------------------
 * formataCampo2 
   -----------------------------------------------------------------------
 */
FormatUtils.formataCampo2 = function(objeto, sMask, evtKeyPress) {
  var i, nCount, sValue, fldLen, mskLen,bolMask, sCod, nTecla;
  
  if(document.all) { // Internet Explorer
	nTecla = evtKeyPress.keyCode; }
  else if(document.layers) { // Nestcape
	nTecla = evtKeyPress.which;
  }
  sValue = objeto.value;
  // Limpa todos os caracteres de formata??o que j? estiverem no campo.
  sValue = sValue.toString().replace( "-", "" );
  sValue = sValue.toString().replace( "-", "" );
  sValue = sValue.toString().replace( ".", "" );
  sValue = sValue.toString().replace( ".", "" );
  sValue = sValue.toString().replace( ".", "" );
  sValue = sValue.toString().replace( ".", "" );
  sValue = sValue.toString().replace( "/", "" );
  sValue = sValue.toString().replace( "/", "" );
  sValue = sValue.toString().replace( "(", "" );
  sValue = sValue.toString().replace( "(", "" );
  sValue = sValue.toString().replace( ")", "" );
  sValue = sValue.toString().replace( ")", "" );
  sValue = sValue.toString().replace( ":", "" );
  sValue = sValue.toString().replace( ":", "" );
  sValue = sValue.toString().replace( ":", "" );
  sValue = sValue.toString().replace( ":", "" );
  fldLen = sValue.length;
  mskLen = sMask.length;

  i = 0;
  nCount = 0;
  sCod = "";
  mskLen = fldLen;

  while (i <= mskLen) {
	bolMask = ((sMask.charAt(i) == "-") || (sMask.charAt(i) == ".") || (sMask.charAt(i) == "/"))
	bolMask = bolMask || ((sMask.charAt(i) == "(") || (sMask.charAt(i) == ")") || (sMask.charAt(i) == " ") || (sMask.charAt(i) == ":"))

	if (bolMask) {
	  sCod += sMask.charAt(i);
	  mskLen++; }
	else {
	  sCod += sValue.charAt(nCount);
	  nCount++;
	}
	i++;
  }

  objeto.value = sCod;
  if (nTecla != 8) { // backspace
	if (sMask.charAt(i-1) == "9") { // apenas n?meros...
	  return ((nTecla > 47) && (nTecla < 58)); } // n?meros de 0 a 9
	else { // qualquer caracter...
	  return true;
	} }
  else {
	return true;
  }
}

/* -----------------------------------------------------------------------
 * Bloqueia que seja digitado Letras e caracteres que nao sejam numeros.
   -----------------------------------------------------------------------
 */
FormatUtils.bloqueiaNaoNumerico = function(evt) {

    var kc = evt.keyCode || evt.which;
    var bReturn = false;

	if(kc==9 || kc==8 || kc==39 || kc==46){
	    bReturn = true;
	}else if( (kc < 48 || kc > 57) && (kc < 96 || kc > 105) ){
		evt.returnValue = false;
		evt.cancelBubble = true;
		bReturn = false;
	}else{
		bReturn = true;
	}
	
	var obj = null;
	if (evt.srcElement){
		obj = evt.srcElement;
	}else{
		obj = evt.target;
	}
	var valor = obj.value;
    var sAux = '';
    for(var j = 0; j < valor.length; j++){
  	   sAux += FormatUtils.apenasNumeros(valor.charAt(j));
    }
    obj.value = sAux;	
    
    return bReturn;
}
/* -----------------------------------------------------------------------
 * Bloqueia que seja digitado Letras e caracteres que nao sejam numeros.
   Mas permite , (formando assim valores monetarios)
   -----------------------------------------------------------------------
 */
FormatUtils.bloqueiaNaoNumericoCurrency = function(evt, valor) {
	//colocar esta funcao no keypress
	//var evento = (evt) ? evt : event;
	var bRetorno = true;
	var tecla = (evt.keyCode) ? evt.keyCode : evt.which;
	
	if (tecla == undefined) tecla = 0;
	
	if(tecla == 8) return true;
	if (tecla == 9 || tecla == 46 || tecla == 37 || tecla == 38 || tecla == 39 || tecla == 40){
		//TAB ou Delete... e SETAS
		if (tecla == 9){
			if (!isMozilla){
				return true;
			}
		}else{
			return true;
		}
	}else{
		if(tecla != 44 && tecla != 188 && tecla != 110){
			if ((tecla < 48 || tecla > 57) && (tecla < 96 || tecla > 105)){
				//tecla = 0;
				evt.returnValue = false;
				evt.cancelBubble = true;			
				bRetorno = false;
			}else{					
				bRetorno = true;
			}
		}else{
			if (valor.search(',') != -1){
				//tecla = 0;
				bRetorno = false;
			}
		}
	}
	
    var sAux = '';
    for(var j = 0; j < valor.length; j++){
  	   sAux += FormatUtils.apenasCurrency(valor.charAt(j));
    }
    var valorAux = sAux;
    sAux = '';
    var bVirgula = false;
    for(var j = 0; j < valorAux.length; j++){
    	if (valorAux.charAt(j) == ','){
    		if (!bVirgula){
    			sAux += valorAux.charAt(j);
    		}
    		bVirgula = true;
    	}else{
    		sAux += valorAux.charAt(j);
    	}
    }
  
	var element;
	if (isMozilla){
		element = evt.target;
	}else{
		element = evt.srcElement;
	}
	if (element == null) return bRetorno;
	
	element.value = sAux;
	
	return bRetorno;
}
FormatUtils.apenasNumeros = function(str){
	if (str == null) return '';
	if (str == '0' || str == '1' || str == '2' || str == '3' || str == '4' ||
		str == '5' || str == '6' || str == '7' || str == '8' || str == '9'){
		return str
	}
	return '';
};
FormatUtils.apenasCurrency = function(str){
	if (str == null) return '';
	if (str == '0' || str == '1' || str == '2' || str == '3' || str == '4' ||
		str == '5' || str == '6' || str == '7' || str == '8' || str == '9' ||
		str == ','){
		return str
	}
	return '';
}
/* -----------------------------------------------------------------------
   Funcoes Internas
   -----------------------------------------------------------------------
 */
function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}


