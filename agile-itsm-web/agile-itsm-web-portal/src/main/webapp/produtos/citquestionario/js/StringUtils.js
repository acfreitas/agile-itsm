function StringUtils() { }
/*
 * ----------------------------------------------------------------
 */
StringUtils.startsWith = function(str, strSearch){
	if(str.length < strSearch || strSearch == ''){
		return false;
	}
	if(str.substring(0, strSearch.length) == strSearch){
		return true;
	}
	return false;
};
/*
 * ----------------------------------------------------------------
 */
StringUtils.endsWith = function(str, strSearch){
	if(str.length < strSearch || strSearch == ''){
		return false;
	}
	if(str.substring(str.length - strSearch.length) == strSearch){
		return true;
	}
	return false;
};
/*
 * ----------------------------------------------------------------
 */
StringUtils.trim = function(str){
	if(str.length > 0){
		while(str.charAt( 0 ) == ' '){
			str = str.substring(1);
		}
		while(str.charAt(str.length -1) == ' '){
			str = str.substring(0, str.length - 1);
		}
	}
	return str;
};
/*
 * ----------------------------------------------------------------
 */
StringUtils.isBlank = function(parametro){
	var teste_parametro = "false"; //variavel para teste de espacos em branco
	var tamanho_parametro = parametro.length;
	if (tamanho_parametro != 0){
		for (i = 0; i < tamanho_parametro; i++){
			if (parametro.charAt(i) != " "){
				teste_parametro = "true"; /*existe caracter diferente de branco*/
			}
		}
		if (teste_parametro == "false")  //todos os caracteres digitados sao brancos
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	else
	{
		return true;
	}	
};
/*
 * ----------------------------------------------------------------
 */
StringUtils.replaceForBlank = function(valor){
   if(valor==null || valor=='null' || valor==''){
       return '';
   }
   return valor;
};
/*
 * ----------------------------------------------------------------
 */
StringUtils.nullToEmpty = function(str){
	return (str == null) ? '' : str;
};

/*
 * ----------------------------------------------------------------
 	Faz a complementacao de uma string
 
	Exemplo de Utilização:
	var mes = "9";
	alert(mes.pad(2, "0", String.PAD_LEFT)); // exibe "09"
*/
StringUtils.PAD_LEFT  = 0;
StringUtils.PAD_RIGHT = 1;
StringUtils.PAD_BOTH  = 2;

StringUtils.pad = function(str, size, pad, side) {
  append = "", size = (size - str.length);
  var pad = ((pad != null) ? pad : " ");
  if ((typeof size != "number") || ((typeof pad != "string") || (pad == ""))) {
    throw new Error("Wrong parameters for String.pad() method.");
  }
  if (side == StringUtils.PAD_BOTH) {
    str = StringUtils.pad(str, (Math.floor(size / 2) + str.length), pad, StringUtils.PAD_LEFT);
    return StringUtils.pad(str, (Math.ceil(size / 2) + str.length), pad, StringUtils.PAD_RIGHT);
  }
  while ((size -= pad.length) > 0) {
    append += pad;
  }
  append += pad.substr(0, (size + pad.length));
  return ((side == StringUtils.PAD_LEFT) ? append.concat(str) : str.concat(append));
};

StringUtils.replaceAll = function(str, strSearch, strReplace) {
	var p = str.indexOf(strSearch);
	while (p != -1) {
		str = str.replace(strSearch, strReplace);
		p = str.indexOf(strSearch);    
	}
	return str;
};
