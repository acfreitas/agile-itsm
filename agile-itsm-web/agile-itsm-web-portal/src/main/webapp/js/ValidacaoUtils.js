/*
 * Para validacao de data e hora consulte:     DateTimeUtil.js
 * Para validacao de string consulte:          StringUtils.js
 * Esta funcao dependete de:          HTMLUtils.js 
 */

function ValidacaoUtils() { }

ValidacaoUtils.validaRequired = function(field, label){
	var bTexto = false;
	
	if (HTMLUtils._isHTMLElement(field, "input")){
		if (field.type == "text"){
			bTexto = true;
		}
	}
	
	if (bTexto){
		if(StringUtils.isBlank(field.value)){
			alert(label + i18n_message("citcorpore.comum.campo_obrigatorio"));
			try{
				field.focus();
			}catch(e){
			}
		    return false;		
		}
	}else{
		if(StringUtils.isBlank(HTMLUtils.getValue(field.id, field.form))){
			alert(label + i18n_message("citcorpore.comum.campo_obrigatorio"));
			try{
				field.focus();
			}catch(e){
			}
		    return false;
		}
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
             alert(label + i18n_message("citcorpore.validacao.cepInvalido"));
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
	if (cpf.length < 11) erro += label + i18n_message("citcorpore.validacao.digitosVerificacaoCPF");
	
/*============= INICIO DA ADAPTAÇÃO PARA O TRF ===============================*/
/* ESTE TRECHO DE CODIGO PERMITE QUE OS CPF'S QUE CONTENHAM MATRICULAS NÃO    */
/* SEJAM VALIDADOS. APENAS DEPENDENTES POSSUEM MATRICULAS NO LUGAR DO CPF.    */
/*============================================================================*/
	var SIGLAS = new Array("AC", "AM", "AP", "BA", 
    "DF", "DS", "GO", "JU", "MA", "MG", "MT", "PA", "PI", "RO", "RR", 
    "TO", "TR");
	for(var i = 0; i < SIGLAS.length; i++){
        if(cpf.toUpperCase().indexOf(SIGLAS[i]) >= 0){
            return true;
        }
    }
/*============= FINAL DA ADAPTAÇÃO PARA O TRF ===============================*/
	
	var nonNumbers = /\D/;
	if (nonNumbers.test(cpf)) erro += label + i18n_message("citcorpore.validacao.suporteVerificacaoNumeroCPF"); 
	if (cpf == "00000000000" || cpf == "11111111111" || cpf == "22222222222" || cpf == "33333333333" || cpf == "44444444444" || cpf == "55555555555" || cpf == "66666666666" || cpf == "77777777777" || cpf == "88888888888" || cpf == "99999999999"){
		erro += label + i18n_message("citcorpore.validacao.numeroCPFInvalido")
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
		erro += label + i18n_message("citcorpore.validacao.problemaDigitoVerificador");
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
    if (CNPJ.length < 18) erro += label + i18n_message("citcorpore.validacao.preenchaCorretamenteCNPJ"); 
    if ((CNPJ.charAt(2) != ".") || (CNPJ.charAt(6) != ".") || (CNPJ.charAt(10) != "/") || (CNPJ.charAt(15) != "-")){
    if (erro.length == 0) erro += label + i18n_message("citcorpore.validacao.preenchaCorretamenteCNPJ");
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
    if (nonNumbers.test(CNPJ)) erro += label + i18n_message("citcorpore.validacao.suporteVerificacaoNumeroCNPJ"); 
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
            erro += label + i18n_message("citcorpore.validacao.digitoVerificadorInvalido");
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
	if(mail.value == "") return;
	var prim = mail.value.indexOf("@")
	if(prim < 2) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	} else
	if(mail.value.indexOf("@",prim + 1) != -1) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf(".") < 1) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf(" ") != -1) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("zipmeil.com") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("hotmeil.com") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf(".@") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("@.") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf(".com.br.") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("/") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("[") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("]") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("(") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf(")") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("..") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("ç") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("Ç") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("á") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("Á") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("à") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("À") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("é") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("É") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}if(mail.value.indexOf("í") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("Í") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("ó") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("Ó") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("ú") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("Ú") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("â") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("Â") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("ê") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("Ê") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("î") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("Î") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("ô") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("Ô") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}
	if(mail.value.indexOf("û") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("Û") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("ã") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("Ã") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("õ") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("Õ") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("&") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("'") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
	if(mail.value.indexOf("\"") >= 0) {
		tratarMensagemEmailInvalido(mail, label);
		return false;
	}else
		return true;
};

function tratarMensagemEmailInvalido(mail, label){
	alert(label + i18n_message("citcorpore.validacao.emailInvalido"));
	mail.focus();
	mail.select();
}
/*
 * Valida Hora
 */
ValidacaoUtils.validaHora = function(campoHora, label){
     hora = campoHora.value;
     if(hora == null || hora.length == 0){
         return true;
     }
     if(hora.length != 5){
         alert(label + i18n_message("citcorpore.validacao.formatoHoraInvalido")); 
         campoHora.focus();
         campoHora.select();
         return false;
     }
     
     h  = hora.substring(0,2);
     m  = hora.substring(3,5);
     
     if(h > 23 || h < 0){
         alert(label + i18n_message("citcorpore.validacao.horaInvalida"));  
         campoHora.focus();
         campoHora.select();
         return false;
     }
      
     if(m>59 || m<0){
     
         alert(label + i18n_message("citcorpore.validacao.minutoInvalido"));
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
         alert(label + i18n_message("citcorpore.validacao.formatoHoraInvalido"));
         campoHora.focus();
         campoHora.select();
         return false;
     }
     
     h  = hora.substring(0,2);
     m  = hora.substring(3,5);
     
     if(h<0){
         alert(label + i18n_message("citcorpore.validacao.horaInvalida"));
         campoHora.focus();
         campoHora.select();
         return false;
     }
      
     if(m>59 || m<0){
     
         alert(label + i18n_message("citcorpore.validacao.minutoInvalido"));
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
		alert(label + i18n_message("citcorpore.validacao.dataInvalida"));
		field.focus();
	}
	return ret;
};

ValidacaoUtils.validaDate = function(field, label){
	return ValidacaoUtils.validaData(field, label);
};

/**
 * Converte a data no formato dd/mm/yyyy.
 */
function converteData(data){
	if (data != null && data.length > 0){
		tmp = new String(data);
		dia = tmp.substring(0, 2);
		mes = tmp.substring(3, 5);
		ano = tmp.substring(6, 10);
		resp = new Date(ano, mes - 1, dia);
		return resp;
	} else {
		return null;
	}
}

/**
 * Compara a data com a data atual.
 */
function comparaComDataAtual(dataStr){
    dAtual = new Date();
    dAtual.setHours(0, 0, 0, 0);
    if (dataStr.value == ''){
    	return 2;
    }
    	
    var dt = converteData(dataStr.value);
    dt.setHours(0, 0, 0, 0);
    
    if (dAtual == null){
    	return 1;
    }
    if (dAtual.getTime() < dt.getTime()){
    	return 1;
    } else if (dAtual.getTime() > dt.getTime()){
    	return -1;
    } else if (dAtual.getTime() == dt.getTime()){
    	return 0;
    }
}

/**
 * Compara a hora com a hora atual.
 */
function comparaComHoraAtual(horaStr){
	hAtual = getHoraAtual();
	
	horarioInicial = hAtual.split(':');
	horarioFinal = horaStr.split(':');
	
	var horaInicial = parseInt(retiraZerosEsquerda(horarioInicial[0]));
	var minutoInicial = parseInt(retiraZerosEsquerda(horarioInicial[1]));
	var horaFinal = parseInt(retiraZerosEsquerda(horarioFinal[0]));
	var minutoFinal = parseInt(retiraZerosEsquerda(horarioFinal[1]));
				
	if(horaFinal < horaInicial){
		return false;
	} else if(horaFinal == horaInicial){
		if(minutoFinal <= minutoInicial){
			return false;
		}
	}
	return true;
}

function getHoraAtual(){
	var now = new Date();
	var hours = now.getHours();
	var minutes = now.getMinutes();
	return ((hours < 10) ? "0" : "") + hours + ((minutes < 10) ? ":0" : ":") + minutes;
}

function retiraZerosEsquerda(str){
	var strRetorno = '';
	var bVerificar = true;
	for(var i = 0; i < str.length; i++){
		if (str.charAt(i) != '0' || !bVerificar){
			bVerificar = false;
			strRetorno = strRetorno + str.charAt(i);
		}
	}
	return strRetorno;
}

/* ===== MÉTODO CRIADO PARA VALIDAÇÃO DO TIPO DE DOCUMENTO PRODEMGE ===== 
Identidade:
	Só será válido se existir pelo menos um caractere de 1 a 9 ou de A a Z em seu conteúdo.
	Tamanho máxim o: 11
	Tipo: Alfanumérico

	CTPS:
	Só será válido se existir pelo menos um caractere de 1 a 9 em seu conteúdo. Não será válido valor com números repetidos. Exemplo: 1111111, 2222222, etc.
	Tamanho: 7
	Tipo: Numérico

	CPF:
	Deverá ser um número válido de acordo com a rotina de validação. Não será válido valor com números repetidos. Exemplo: 11111111111, 22222222222, etc.
	Tamanho: 11
	Tipo: Numérico

	Titulo de Eleitor:
	Deverá ser um número válido de acordo com a rotina de validação.
	Tamanho: 13
	Tipo: Numérico */

ValidacaoUtils.validaNumeroDocumento = function(field, tipo) { 
	if (StringUtils.isBlank(tipo)){
	     return true;
	}

	var documento = field.value;
	if (StringUtils.isBlank(documento)){
	     return true;
	}

	if (tipo == '2') {  // CPF
		return ValidacaoUtils.validaCPF(field, 'CPF - ');
	}
	
	return true;
}	
	

ValidacaoUtils.limpar = function(valor, validos) {
	// retira caracteres invalidos da string
	var result = "";
	var aux;
	for (var i=0; i < valor.length; i++) {
		aux = validos.indexOf(valor.charAt(i));
		if (aux>=0) {
			result += aux;
		}
	}
	return result;
}

//Formata número tipo moeda usando o evento onKeyDown

ValidacaoUtils.formataMoeda = function(objTextBox, SeparadorMilesimo, SeparadorDecimal, e){
	var SeparadorDecimal = ","
	var SeparadorMilesimo = "."
	var sep = 0;
	var key = '';
	var i = j = 0;
	var len = len2 = 0;
	var strCheck = '0123456789';
	var aux = aux2 = '';
	var whichCode = (window.Event) ? e.which : e.keyCode;

	if (whichCode == 13)
		return true;
	key = String.fromCharCode(whichCode); // Valor para o código da Chave  

	if (strCheck.indexOf(key) == -1)
		return true; // Chave inválida  
	len = campo.value.length;
	for (i = 0; i < len; i++)

		if ((campo.value.charAt(i) != '0')
				&& (campo.value.charAt(i) != SeparadorDecimal))
			break;
	aux = '';
	for (; i < len; i++)

		if (strCheck.indexOf(campo.value.charAt(i)) != -1)
			aux += campo.value.charAt(i);
	aux += key;
	len = aux.length;

	if (len == 0)
		campo.value = '';
	if (len == 1)
		campo.value = '0' + SeparadorDecimal + '0' + aux;
	if (len == 2)
		campo.value = '0' + SeparadorDecimal + aux;
	if (len > 2) {
		aux2 = '';
		for (j = 0, i = len - 3; i >= 0; i--) {
			if (j == 3) {
				aux2 += SeparadorMilesimo;
				j = 0;
			}
			aux2 += aux.charAt(i);
			j++;
		}
		campo.value = '';
		len2 = aux2.length;
		for (i = len2 - 1; i >= 0; i--)
			campo.value += aux2.charAt(i);
		campo.value += SeparadorDecimal + aux.substr(len - 2, len);
	}
	return false;  
}
/*
 * Valida Se algum item do CheckBox foi selecionado
 */
ValidacaoUtils.validaCheck = function(field, label){
	if (field == undefined) return false;
	checkbox = document.getElementsByName(field.name);
	var count = checkbox.length;
	var flag = false;
	for ( var i = 0; i < count; i++) {
		if (checkbox[i].checked) {
			return true;
		}
	}
	if(!flag) {
		alert(label + i18n_message("citcorpore.comum.selecioneItens"));
		return false;
	}else
		return true;
};

