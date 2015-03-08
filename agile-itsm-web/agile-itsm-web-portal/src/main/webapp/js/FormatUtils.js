function FormatUtils() {
}
FormatUtils.noNumbers = function(component) {
	var txt = component.value;
	var strAux = "";
	for ( var i = 0; i < txt.length; i++) {
		if (!parseInt(txt[i])) {
			strAux += txt[i];
		}
	}
	component.value = strAux;
}
FormatUtils.formataCampo = function(objForm, strField, sMask, evtKeyPress) {
	var i, nCount, sValue, fldLen, mskLen, bolMask, sCod, nTecla;
	var bRetorno = true;
	if (evtKeyPress == null || evtKeyPress == undefined) {
		evtKeyPress = window.event;
	}
	nTecla = evtKeyPress.charCode ? evtKeyPress.charCode : evtKeyPress.keyCode
	if (nTecla == 9 || nTecla == 13 || nTecla == 46 || nTecla == 37
			|| nTecla == 38 || nTecla == 39 || nTecla == 40) {
		bRetorno = true;
	} else {
		if ((nTecla < 48 || nTecla > 57) && (nTecla < 96 || nTecla > 105)) {
			if (nTecla != 8 && nTecla != 39) {
				evtKeyPress.returnValue = false;
				evtKeyPress.cancelBubble = true;
				bRetorno = false;
			}
		}
	}
	sValue = objForm[strField].value;
	var element = objForm[strField];
	if (sValue == undefined) {
		if (isMozilla) {
			element = evtKeyPress.target;
		} else {
			element = evtKeyPress.srcElement;
		}
		sValue = element.value;
	}
	var bMaior = false;
	sValue = sValue.toString().replace("-", "");
	sValue = sValue.toString().replace("-", "");
	sValue = sValue.toString().replace(".", "");
	sValue = sValue.toString().replace(".", "");
	sValue = sValue.toString().replace(".", "");
	sValue = sValue.toString().replace(".", "");
	sValue = sValue.toString().replace("/", "");
	sValue = sValue.toString().replace("/", "");
	sValue = sValue.toString().replace("(", "");
	sValue = sValue.toString().replace("(", "");
	sValue = sValue.toString().replace(")", "");
	sValue = sValue.toString().replace(")", "");
	sValue = sValue.toString().replace(":", "");
	sValue = sValue.toString().replace(":", "");
	sValue = sValue.toString().replace(":", "");
	sValue = sValue.toString().replace(":", "");
	var sAux = '';
	for ( var j = 0; j < sValue.length; j++) {
		sAux += FormatUtils.apenasNumeros(sValue.charAt(j));
	}
	sValue = sAux;
	fldLen = sValue.length;
	mskLen = sMask.length;
	i = 0;
	nCount = 0;
	sCod = "";
	mskLen = fldLen;
	if (nTecla != 8 && nTecla != 46 && nTecla != 37 && nTecla != 38
			&& nTecla != 39 && nTecla != 40) {
		while (i <= mskLen) {
			bolMask = ((sMask.charAt(i) == "-") || (sMask.charAt(i) == ".") || (sMask
					.charAt(i) == "/"))
			bolMask = bolMask
					|| ((sMask.charAt(i) == "(") || (sMask.charAt(i) == ")")
							|| (sMask.charAt(i) == " ") || (sMask.charAt(i) == ":"))
			if (bolMask) {
				sCod += sMask.charAt(i);
				mskLen++;
			} else {
				sCod += FormatUtils.apenasNumeros(sValue.charAt(nCount));
				nCount++;
			}
			i++;
		}
		element.value = sCod;
	} else {
	}
	return bRetorno;
}
FormatUtils.formataCampoHora = function(fieldObj) {
	var fmt = "";
	var fieldValue = fieldObj.value;
	var currentChar;
	for ( var i = 0; i < fieldValue.length; i++) {
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
FormatUtils.formataCampo2 = function(objeto, sMask, evtKeyPress) {
	var i, nCount, sValue, fldLen, mskLen, bolMask, sCod, nTecla;
	if (document.all) {
		nTecla = evtKeyPress.keyCode;
	} else if (document.layers) {
		nTecla = evtKeyPress.which;
	}
	sValue = objeto.value;
	sValue = sValue.toString().replace("-", "");
	sValue = sValue.toString().replace("-", "");
	sValue = sValue.toString().replace(".", "");
	sValue = sValue.toString().replace(".", "");
	sValue = sValue.toString().replace(".", "");
	sValue = sValue.toString().replace(".", "");
	sValue = sValue.toString().replace("/", "");
	sValue = sValue.toString().replace("/", "");
	sValue = sValue.toString().replace("(", "");
	sValue = sValue.toString().replace("(", "");
	sValue = sValue.toString().replace(")", "");
	sValue = sValue.toString().replace(")", "");
	sValue = sValue.toString().replace(":", "");
	sValue = sValue.toString().replace(":", "");
	sValue = sValue.toString().replace(":", "");
	sValue = sValue.toString().replace(":", "");
	fldLen = sValue.length;
	mskLen = sMask.length;
	i = 0;
	nCount = 0;
	sCod = "";
	mskLen = fldLen;
	while (i <= mskLen) {
		bolMask = ((sMask.charAt(i) == "-") || (sMask.charAt(i) == ".") || (sMask
				.charAt(i) == "/"))
		bolMask = bolMask
				|| ((sMask.charAt(i) == "(") || (sMask.charAt(i) == ")")
						|| (sMask.charAt(i) == " ") || (sMask.charAt(i) == ":"))
		if (bolMask) {
			sCod += sMask.charAt(i);
			mskLen++;
		} else {
			sCod += sValue.charAt(nCount);
			nCount++;
		}
		i++;
	}
	objeto.value = sCod;
	if (nTecla != 8) {
		if (sMask.charAt(i - 1) == "9") {
			return ((nTecla > 47) && (nTecla < 58));
		} else {
			return true;
		}
	} else {
		return true;
	}
}
FormatUtils.bloqueiaNaoNumerico = function(evt) {
	var bReturn = true; //padrão é true
    var e = evt || window.event; //evento
    var element = $(e.target); //elemento do input que está sendo digitado
    var key = e.keyCode || e.which; //chave da tecla que foi digitada
    var allowed = [48,49,50,51,52,53,54,55,56,57,96,97,98,99,100,101,102,103,104,105,8,9,13,35,36,37,39,46,45]; //chaves permitidas (0123456789, backspace, delete, home, end, tab, etc..)

    if ($.inArray(key, allowed) === -1 || (evt.shiftKey == true || evt.altKey == true || evt.ctrlKey == true)) { 
    	//se não estiver entre as chaves permitidas ou o shift, alt, ctrl estiver digitado, não permite o caracter
        
    	e.returnValue = false;
        if (e.preventDefault) e.preventDefault(); 
    }
    element.val(element.val().replace(/[^0-9]+/g, '')); //garante que o valor digitado (não permitido) não entrou no input
	
	return bReturn;
}
FormatUtils.bloqueiaNaoNumericoSaidaCampo = function(evt){
	var obj = null;
	if (evt.srcElement) {
		obj = evt.srcElement;
	} else {
		obj = evt.target;
	}
	obj.value = FormatUtils.retornaApenasNumeros(obj.value);
}
FormatUtils.bloqueiaNaoNumericoCurrency = function(evt,valor) {
	var kc = evt.keyCode || evt.which;
	var bReturn = false;

	if (kc == 9 || kc == 8 || kc == 13 || kc == 16 || kc == 35 || kc == 36 || kc == 37 || kc == 39 || kc == 46) {
		bReturn = true;
	} else {
		if (evt.ctrlKey!=1) {
			if ((kc < 48 || kc > 57) && (kc < 96 || kc > 105) && (kc != 188) && (kc != 110)) {
				evt.preventDefault? evt.preventDefault() : evt.returnValue = false;
				bReturn = false;
			} else {
				if ((kc == 188)||(kc == 110)){
					if (valor.search(',') != -1) {
						evt.preventDefault? evt.preventDefault() : evt.returnValue = false;
						bReturn = false;
					} else {
						bReturn = true;
					}
				} else {
					bReturn = true;
				}
			}
		} else {
			bReturn = true;
		}
	}
	return bReturn;
}
FormatUtils.retornaApenasNumeros = function(texto) {
	if (texto == null)
		return '';
	var sAux = '';
	for ( var j = 0; j < texto.length; j++) {
		sAux += FormatUtils.apenasNumeros(texto.charAt(j));
	}
	return sAux;
}
FormatUtils.apenasNumeros = function(str) {
	if (str == null)
		return '';
	if (str == '0' || str == '1' || str == '2' || str == '3' || str == '4'
			|| str == '5' || str == '6' || str == '7' || str == '8'
			|| str == '9') {
		return str
	}
	return '';
}
FormatUtils.bloqueiaNaoMoedaSaidaCampo = function(evt) {
	var obj = null;
	if (evt.srcElement) {
		obj = evt.srcElement;
	} else {
		obj = evt.target;
	}
	obj.value = FormatUtils.retornaApenasCurrency(obj.value);
}
FormatUtils.retornaApenasCurrency = function(texto) {
	if (texto == null)
		return '';
	var sAux = '';
	for ( var j = 0; j < texto.length; j++) {
		sAux += FormatUtils.apenasCurrency(texto.charAt(j));
	}
	return sAux;
}
FormatUtils.apenasCurrency = function(str) {
	if (str == null)
		return '';
	if (str == '0' || str == '1' || str == '2' || str == '3' || str == '4'
			|| str == '5' || str == '6' || str == '7' || str == '8'
			|| str == '9' || str == ',') {
		return str
	}
	return '';
}
function isInteger(s) {
	var i;
	for (i = 0; i < s.length; i++) {
		var c = s.charAt(i);
		if (((c < "0") || (c > "9")))
			return false;
	}
	return true;
}