/*
 * Para validacao de data e hora consulte:     DateTimeUtil.js
 * Para validacao de string consulte:          StringUtils.js
 */

function ValidacaoUtils() { }

ValidacaoUtils.validaRequired = function(field, label){
	if(StringUtils.isBlank(HTMLUtils.getValue(field.id))){
		alert(label + 'Campo obrigatório');
		try{
			field.focus();
		}catch(e){
		}
	    return false;
	}
	return true;
}
/*
 * Valida CEP
 */
ValidacaoUtils.validaCEP = function(field, label){
      var valor = field.value;
      if(!StringUtils.isBlank(valor)){
         if(valor.length!=10){
             alert(label + 'CEP invalido');
             field.focus();
             return false;
         }else{
             return true;
         }
      }else{
          return false;
      }
};

/*
 * Valida CPF
 */
ValidacaoUtils.validaCPF = function(field, label) {
	var cpf = field.value;
	
	if(StringUtils.isBlank(cpf)){
	     return true;
	}
	     
	cpf = cpf.replace(".","");
	cpf = cpf.replace(".","");
	cpf = cpf.replace("-","");                 
	var erro = new String;
	if (cpf.length < 11) erro += label+": Sao necessarios 11 digitos para verificacao do CPF! \n\n"; 
	var nonNumbers = /\D/;
	if (nonNumbers.test(cpf)) erro += label+": A verificacao de CPF suporta apenas numeros! \n\n"; 
	if (cpf == "00000000000" || cpf == "11111111111" || cpf == "22222222222" || cpf == "33333333333" || cpf == "44444444444" || cpf == "55555555555" || cpf == "66666666666" || cpf == "77777777777" || cpf == "88888888888" || cpf == "99999999999"){
		erro += label+"Numero de CPF invalido!"
	}
	var a = [];
	var b = new Number;
	var c = 11;
	for (i=0; i<11; i++){
		a[i] = cpf.charAt(i);
	    if (i < 9) b += (a[i] * --c);
	}
	if ((x = b % 11) < 2) { a[9] = 0 } else { a[9] = 11-x }
	b = 0;
	c = 11;
	for (y=0; y<10; y++) b += (a[y] * c--); 
	if ((x = b % 11) < 2) { a[10] = 0; } else { a[10] = 11-x; }
	if ((cpf.charAt(9) != a[9]) || (cpf.charAt(10) != a[10])){
		erro +=label+"Digito verificador com problema!";
	}
	if (erro.length > 0){
		alert(erro);
		field.focus();
		return false;
	}
	return true;
};

/*
 * Valida CNPJ
 */
ValidacaoUtils.validaCNPJ = function(field, label) {
    var CNPJ = field.value;
    if(StringUtils.isBlank(CNPJ)){
         return true;
 	}
    var erro = new String;
    if (CNPJ.length < 18) erro += label+"Preencha corretamente o CNPJ! \n\n"; 
    if ((CNPJ.charAt(2) != ".") || (CNPJ.charAt(6) != ".") || (CNPJ.charAt(10) != "/") || (CNPJ.charAt(15) != "-")){
    if (erro.length == 0) erro += label+"Preencha corretamente o CNPJ! \n\n";
    }
    //substituir os caracteres que n?o s?o n?meros
    if(document.layers && parseInt(navigator.appVersion) == 4){
            x = CNPJ.substring(0,2);
            x += CNPJ. substring (3,6);
            x += CNPJ. substring (7,10);
            x += CNPJ. substring (11,15);
            x += CNPJ. substring (16,18);
            CNPJ = x; 
    } else {
            CNPJ = CNPJ. replace (".","");
            CNPJ = CNPJ. replace (".","");
            CNPJ = CNPJ. replace ("-","");
            CNPJ = CNPJ. replace ("/","");
    }
    var nonNumbers = /\D/;
    if (nonNumbers.test(CNPJ)) erro += label+"A verificao de CNPJ suporta apenas numeros! \n\n"; 
    var a = [];
    var b = new Number;
    var c = [6,5,4,3,2,9,8,7,6,5,4,3,2];
    for (i=0; i<12; i++){
            a[i] = CNPJ.charAt(i);
            b += a[i] * c[i+1];
    }
    if ((x = b % 11) < 2) { a[12] = 0 } else { a[12] = 11-x }
    b = 0;
    for (y=0; y<13; y++) {
            b += (a[y] * c[y]); 
    }
    if ((x = b % 11) < 2) { a[13] = 0; } else { a[13] = 11-x; }
    if ((CNPJ.charAt(12) != a[12]) || (CNPJ.charAt(13) != a[13])){
            erro +=label+"Digito verificador invalido!";
    }
    if (erro.length > 0){
            alert(erro);
            field.focus();
            return false;
    } else {
    	    return true;       
    }
    return true;
};
/*
 * Valida Email
 */
ValidacaoUtils.validaEmail = function(mail, label) {
		var prim = mail.value.indexOf("@")
		if(prim < 2) {
			alert(label + "E-mail invalido");
			mail.focus();
			mail.select();
			return false;
		}
		if(mail.value.indexOf("@",prim + 1) != -1) {
			alert(label + "E-mail invalido");
			mail.focus();
			mail.select();
			return false;
		}
		if(mail.value.indexOf(".") < 1) {
			alert(label + "E-mail invalido");
			mail.focus();
			mail.select();
			return false;
		}
		if(mail.value.indexOf(" ") != -1) {
			alert(label + "E-mail invalido");
			mail.focus();
			mail.select();
			return false;
		}
		if(mail.value.indexOf("zipmeil.com") > 0) {
			alert(label + "E-mail invalido");
			mail.focus();
			mail.select();
			return false;
		}
		if(mail.value.indexOf("hotmeil.com") > 0) {
			alert(label + "E-mail invalido");
			mail.focus();
			mail.select();
			return false;
		}
		if(mail.value.indexOf(".@") > 0) {
			alert(label + "E-mail invalido");
			mail.focus();
			mail.select();
			return false;
		}
		if(mail.value.indexOf("@.") > 0) {
			alert(label + "E-mail invalido");
			mail.focus();
			mail.select();
			return false;
		}
		if(mail.value.indexOf(".com.br.") > 0) {
			alert(label + "E-mail invalido");
			mail.focus();
			mail.select();
			return false;
		}
		if(mail.value.indexOf("/") > 0) {
			alert(label + "E-mail invalido");
			mail.focus();
			mail.select();
			return false;
		}
		if(mail.value.indexOf("[") > 0) {
			alert(label + "E-mail invalido");
			mail.focus();
			mail.select();
			return false;
		}
		if(mail.value.indexOf("]") > 0) {
			alert(label + "E-mail invalido");
			mail.focus();
			mail.select();
			return false;
		}
		if(mail.value.indexOf("(") > 0) {
			alert(label + "E-mail invalido");
			mail.focus();
			mail.select();
			return false;
		}
		if(mail.value.indexOf(")") > 0) {
			alert(label + "E-mail invalido");
			mail.focus();
			mail.select();
			return false;
		}
		if(mail.value.indexOf("..") > 0) {
			alert(label + "E-mail invalido");
			mail.focus();
			mail.select();
			return false;
		}
	
		return true;
};
/*
 * Valida Hora
 */
ValidacaoUtils.validaHora = function(campoHora, label){
     hora = campoHora.value;
     if(hora == null || hora.length == 0){
         return true;
     }
     if(hora.length != 5){
         alert(label + 'Formato de hora inválido (hh:mm)');
         campoHora.focus();
         campoHora.select();
         return false;
     }
     
     h  = hora.substring(0,2);
     m  = hora.substring(3,5);
     
     if(h > 23 || h < 0){
         alert(label + 'Hora inválida(de 00 a 23)');
         campoHora.focus();
         campoHora.select();
         return false;
     }
      
     if(m>59 || m<0){
     
         alert(label + 'Minuto inválido(de 00 a 59)');
         campoHora.focus();
         campoHora.select();
         return false;
     
     }
     
     return true;
};
/*
 * Valida Hora Semanal
 */
ValidacaoUtils.validaHoraSemanal = function(campoHora, label){

     hora = campoHora.value;
     if(hora==null || hora.length==0){
         return true;
     }
     
     if(hora.length!=5){
         alert(label + 'Formato de hora invalido(hh:mm)');
         campoHora.focus();
         campoHora.select();
         return false;
     }
     
     h  = hora.substring(0,2);
     m  = hora.substring(3,5);
     
     if(h<0){
         alert(label + 'Hora invalida(de 00 a 99)');
         campoHora.focus();
         campoHora.select();
         return false;
     }
      
     if(m>59 || m<0){
     
         alert(label + 'Minuto invalido(de 00 a 59)');
         campoHora.focus();
         campoHora.select();
         return false;
     
     }
     
     return true;
};
/*
 * Valida Data
 */
ValidacaoUtils.validaData = function(field, label){
	if (field == undefined) return false;
	if(StringUtils.isBlank(field.value)){
		return true; //Deixa passar em branco. O valida Required que trata isso.
	}
	var ret = DateTimeUtil.isValidDate(field.value);
	if (!ret){
		alert(label + 'Data Inválida');
		field.focus();
	}
	return ret;
};

ValidacaoUtils.validaDate = function(field, label){
	return ValidacaoUtils.validaData(field, label);
};

