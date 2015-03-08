function NumberUtil(){}

/*
 * Coloca zeros a esquerda de um numero.
 */
NumberUtil.zerosAEsquerda = function(numero,tamanhoTotal){
	if (tamanhoTotal == null) return numero;
	if (tamanhoTotal == 0) return numero;
	
	var numStr = numero.toString();
	for(var i = numStr.length; i < tamanhoTotal; i++){
		numStr = '0' + numStr;
	}
	return numStr;
};
/*
 * Verifica se um numero eh Inteiro.
 */
NumberUtil.isInteger = function(val) {
	var digits="1234567890";
	for (var i=0; i < val.length; i++) {
		if (digits.indexOf(val.charAt(i))==-1) { return false; }
		}
	return true;
};
/*
 * ----------------------------------------------------------------
 * Retorna apenas numeros de uma string passada como parametro:
 *		exemplo: 
 *				StringUtils.apenasNumeros('500px');
 *					retornar�: 500
 * ----------------------------------------------------------------
 */
NumberUtil.apenasNumeros = function(str){
	if (str == null) return '';
	var strReturn = '';
	for (var i = 0; i < str.length; i++){
		if (str.charAt(i) == '0' || str.charAt(i) == '1' || str.charAt(i) == '2' || str.charAt(i) == '3' || str.charAt(i) == '4' ||
			str.charAt(i) == '5' || str.charAt(i) == '6' || str.charAt(i) == '7' || str.charAt(i) == '8' || str.charAt(i) == '9'){
			strReturn += str.charAt(i);
		}
	}
	return strReturn;
};
/*
 * ----------------------------------------------------------------
 * Retorna apenas a representacao de decimal de uma string passada como parametro:
 *		exemplo: 
 *				StringUtils.apenasCurrency('1.500,78px');
 *					retornar�: 1500,78
 * ----------------------------------------------------------------
 */
NumberUtil.apenasCurrency = function(str){
	if (str == null) return '';
	var strReturn = '';
	for (var i = 0; i < str.length; i++){	
		if (str.charAt(i) == '0' || str.charAt(i) == '1' || str.charAt(i) == '2' || str.charAt(i) == '3' || str.charAt(i) == '4' ||
			str.charAt(i) == '5' || str.charAt(i) == '6' || str.charAt(i) == '7' || str.charAt(i) == '8' || str.charAt(i) == '9' ||
			str.charAt(i) == ','){
			strReturn += str.charAt(i);
		}
	}
	return strReturn;
}
/*
 * Transforma uma string passada como parametro para inteiro.
 */
NumberUtil.toInteger = function(val) {
	if (val == null || val == '' || val == undefined) return 0;
	var vAux = NumberUtil.apenasNumeros(val);
	//Os trechos abaixo sao pra corrigir problemas do engine de javascript de alguns browsers.
	if (vAux == '01'){
		vAux = '1';
	}
	if (vAux == '02'){
		vAux = '2';
	}
	if (vAux == '03'){
		vAux = '3';
	}
	if (vAux == '04'){
		vAux = '4';
	}
	if (vAux == '05'){
		vAux = '5';
	}
	if (vAux == '06'){
		vAux = '6';
	}
	if (vAux == '07'){
		vAux = '7';
	}
	if (vAux == '08'){
		vAux = '8';
	}
	if (vAux == '09'){
		vAux = '9';
	}
	var ret = parseInt(vAux);
	if (ret == null || ret == undefined) return 0;
	return ret;
};

/*
 * Transforma uma string passada como parametro para Float (JavaScript).
 */
NumberUtil.toDouble = function(val) {
	if (val == null || val == '' || val == undefined) return 0;
	vAux = NumberUtil.apenasCurrency(val);
	vAux = vAux.replace(/,/g,'.');
	
	var v = parseFloat(vAux);
	if(isNaN(v)){
		return 0;
	}else{
		return v;
	}	
};

/*
  Faz a formatacao de um numero (decimal)
  
  Exemplo de Utilizacao:
  var numero = 2195440.3517;
  alert(NumberUtil.format(numero, 2, ",", ".")); 
  // exibe: "2.195.440,35"
  
  A funcao arrendondara os valores.
  Exemplo de Utilizacao:
  var numero = 2195440.3599;
  alert(NumberUtil.format(numero, 2, ",", ".")); 
  // exibe: "2.195.440,36"  
  
  Esta funcao depende de StringUtils.js
*/
NumberUtil.format = function(valor, d_len, d_pt, t_pt) {
  var d_len = d_len || 0;
  var d_pt = d_pt || ".";
  var t_pt = t_pt || ",";
  if ((typeof d_len != "number")
    || (typeof d_pt != "string")
    || (typeof t_pt != "string")) {
    throw new Error("wrong parameters for method 'String.pad()'.");
  }
  var integer = "", decimal = "";
  var aux = new String(valor);
  var n = aux.split(/\./);
  var i_len = n[0].length;
  var i = 0;
  if (d_len > 0) {
  	if (n[1] != undefined){
	    if (n[1].length > d_len){//Arredonda.
	    	var r = n[1].substr(d_len,1);
	    	if (r > '5'){
				valor = valor + parseFloat('0.' + StringUtils.pad('1', d_len, "0", 0));
				aux = new String(valor);
				n = aux.split(/\./);
				i_len = n[0].length;
	    	}
	    }
	}
    n[1] = (typeof n[1] != "undefined") ? n[1].substr(0, d_len) : "";
    decimal = d_pt.concat(StringUtils.pad(n[1], d_len, "0", 1));
  }
  while (i_len > 0) {
    if ((++i % 3 == 1) && (i_len != n[0].length)) {
      integer = t_pt.concat(integer);
    }
    integer = n[0].substr(--i_len, 1).concat(integer);
  }
  return (integer + decimal);
};

/**
 * Ordena ascendentemente um array num�rico
 */
NumberUtil.sortArrayAscending = function(array) {
	return array.sort(function(a, b) {
		return a - b;
	});
};

/**
 * * Recupera o menor valor de um array num�rico
 */
NumberUtil.smallerFromArray = function(array) {
	var newArray = NumberUtil.sortArrayAscending(array);
	return newArray[0];
};

/**
 * Recupera o maior valor de um array num�rico
 */
NumberUtil.largerFromArray = function(array) {
	var newArray = NumberUtil.sortArrayAscending(array);
	return newArray[array.length - 1];
};

/**
 * Calcula a m�dia entre o maior n�mero e o menor n�mero em um array
 */
NumberUtil.avgBetweenSmallerAndLargerFromArray = function(array) {
	var smaller = NumberUtil.smallerFromArray(array);
	var larger = NumberUtil.largerFromArray(array);
	return (smaller + larger) / 2;
};
