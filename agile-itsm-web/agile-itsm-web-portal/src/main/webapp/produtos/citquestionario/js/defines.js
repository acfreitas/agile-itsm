/*
 * DEFINICOES INICIAIS.
 */
var isMozilla = true;
var isOpera = (navigator.userAgent.indexOf('Opera') != -1);
var isIE = (!isOpera && navigator.userAgent.indexOf('MSIE') != -1);
if (isOpera || isIE) isMozilla = false;

var nn6 = document.getElementById && !document.all;

var MOUSE_PosX = 0;
var MOUSE_PosY = 0;

var DEFINEALLPAGES_tamRequest = 0;
var DEFINEALLPAGES_requisicao;
var DEFINEALLPAGES_reqEventos;

var IS_VERIFICA_BARRA_DIV = true;

// ********* FUNCOES **********
/*
 * Permite que os substitua document.getElementById('') por $(''). É mais simples e rapido.
 */
function $(id){
	return document.getElementById(id);
}
/*
 * Adiciona eventos a um objeto.
 */
function addEvent(object, evType, func, useCapture)
{
	if (object == null || object == undefined) return;
    if(object.addEventListener){
        object.addEventListener(evType, func, useCapture);
    } else {
	    if(object.attachEvent){
	        object.attachEvent("on" + evType, func);
	    }
	}
}

function DEFINEALLPAGES_getObjectAJAX(){
	  var http_request = false;
      if (window.XMLHttpRequest) { // Mozilla, Safari,...
         http_request = new XMLHttpRequest();
         if (http_request.overrideMimeType) {
         	//set type accordingly to anticipated content type
            //http_request.overrideMimeType('text/xml');
            http_request.overrideMimeType('text/html');
         }
      } else if (window.ActiveXObject) { // IE
         try {
            http_request = new ActiveXObject("Msxml2.XMLHTTP");
         } catch (e) {
            try {
               http_request = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e) {}
         }
      }
      if (!http_request) {
         alert('Cannot create XMLHTTP instance #####');
         return false;
      }
	  return http_request;
}
function DEFINEALLPAGES_submitObject(requestObj, theForm, obj, action, fCallBack){
	var queryString = '';
	//Prepara os dados do form e dados adicionais para enviar ao servidor.
	if (theForm != null && theForm != undefined){
		//Se o form foi passado, entao envia todos os dados do form.
		queryString = AjaxUtils.generateQueryStringByForm(theForm);
	}
	if (obj != null){
		for (var property in obj) {
			if (queryString.length>0) {
				queryString += "&";
			}		
		  	queryString += encodeURIComponent(property) + "=" + AjaxUtils.converteCaracteresEspeciais(obj[property]);
	    }	
    }
    //Adiciona controle de cache.
	if (queryString.length>0) {
		queryString += "&";
	}
	queryString += "nocache=" + new Date();
	requestObj.onreadystatechange = fCallBack;
	try{
		requestObj.open("POST", action, true);
     	requestObj.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
     	requestObj.setRequestHeader("encoding", "UTF-8"); 
     	requestObj.setRequestHeader("charset", "UTF-8"); 
     	requestObj.setRequestHeader("Content-length", queryString.length);
     	requestObj.setRequestHeader("Connection", "close");
     	//Tratamento de CACHE
		requestObj.setRequestHeader('Cache-Control', 'no-store, no-cache, must-revalidate');
		requestObj.setRequestHeader('Cache-Control', 'post-check=0, pre-check=0');
		requestObj.setRequestHeader('Pragma', 'no-cache');
	}catch(e){
		alert(e);
	}
	requestObj.send(queryString);	
}	

function DEFINEALLPAGES_setaIds(){
	var fs = document.forms.length;
	if (fs != null && fs != undefined){
		for(var i = 0; i < fs; i++){
			for(var j = 0; j <  document.forms[i].length; j++){
				var id = document.forms[i].elements[j].id;
				if (id == null || id == undefined || id == ''){
					document.forms[i].elements[j].id = document.forms[i].elements[j].name;
				}
			}
		}
	}
}
var DEFINEALLPAGES_requisicao = null;
function DEFINEALLPAGES_fCallBackValuesLoad(){
	if (DEFINEALLPAGES_requisicao.readyState == 4){
		if (DEFINEALLPAGES_requisicao.status == 200){
			DEFINEALLPAGES_fCallBackLoadDirect(DEFINEALLPAGES_requisicao.responseText);
			try{
				JANELA_AGUARDE_MENU.hide();
			}catch(ex){
			}
		}	
	}
}
function DEFINEALLPAGES_loadAdicional(){
	var tabberArgs = {};
	tabberAutomatic(tabberArgs);
	
	//Faz o ajuste para que o firefox tenha um comportamento parecido com o do IE.
	//Sem necessidade de especificar o Id. A funcao abaixo faz isto.
	DEFINEALLPAGES_setaIds();
	var url = new String(window.location);
	if(url.indexOf('.load') > -1){
		var urlGetValuesProcessed = url.replace('.load','.get');
		DEFINEALLPAGES_requisicao = DEFINEALLPAGES_getObjectAJAX();
		DEFINEALLPAGES_submitObject(DEFINEALLPAGES_requisicao, null, null, urlGetValuesProcessed, DEFINEALLPAGES_fCallBackValuesLoad);
	}
}
function DEFINEALLPAGES_processaLoadSubPage(urlChamada, textResponse){
	//Faz o ajuste para que o firefox tenha um comportamento parecido com o do IE.
	//Sem necessidade de especificar o Id. A funcao abaixo faz isto.
	DEFINEALLPAGES_setaIds();
	
	DEFINEALLPAGES_trataScripts(textResponse);
	
	var urlGetValuesProcessed = urlChamada.replace('.load','.get');
	DEFINEALLPAGES_requisicao = DEFINEALLPAGES_getObjectAJAX();
	DEFINEALLPAGES_submitObject(DEFINEALLPAGES_requisicao, null, null, urlGetValuesProcessed, DEFINEALLPAGES_fCallBackValuesLoad);
}
function DEFINEALLPAGES_fCallBackLoad(){
	for (var i = 0; i < DEFINEALLPAGES_tamRequest; i++){
		if (DEFINEALLPAGES_requisicao[i] == null || DEFINEALLPAGES_requisicao[i] == undefined) continue;
		if (DEFINEALLPAGES_requisicao[i].readyState == 4){
			if (DEFINEALLPAGES_requisicao[i].status == 200){
				var resposta = DEFINEALLPAGES_requisicao[i].responseText;
				//var objs = ObjectUtils.deserializeCollectionFromString(resposta);
				var objs = ObjectUtils.deserializeCollectionFromStringSemQuebraEnter(resposta);
				if (objs != null){
					for(var j = 0; j < objs.length; j++){
						eval(ObjectUtils.decodificaAspasApostrofe(objs[j].script));
					}
				}
			}
		}
	}
}
function DEFINEALLPAGES_fCallBackLoadDirect(text){
	DEFINEALLPAGES_trataRespostaServidor(text);
	
	//Gera configuracoes necessarias para o tratamento do form.
	var fs = document.forms.length;
	if (fs != null && fs != undefined){
		for(var i = 0; i < fs; i++){
			//Gera funcao validate para o form
			document.forms[i].validate = function () {
				return DEFINEALLPAGES_validarForm(this);
			};
			//Gera funcao save para o form
			document.forms[i].save = function () {
				var aux = this.onValidate;
				var b = true;
				if (aux != null && aux != undefined){
					try{
						b = this.onValidate(this);
					}catch(e){
					}
				}
				if (!b){
					return;
				}
				if (this.validate()) {
					var aux = this.onSave;
					if (aux != null && aux != undefined){
						try{
							this.onSave(this);
						}catch(e){
						}
					}
					this.ajaxAction = new AjaxAction();
					document.formSubmiting = this;
					this.ajaxAction.submitForm(this, this.action + '.save', DEFINEALLPAGES_trataSave);					
				} 
			};
			document.forms[i].restore = function (key) { //Key eh um dicionario
				var aux = this.onValidateRestore;
				if (aux != null && aux != undefined){
					this.onValidateRestore(this);
				}
				var aux = this.onRestore;
				if (aux != null && aux != undefined){
					this.onRestore(this);
				}
				this.ajaxAction = new AjaxAction();
				document.formSubmiting = this;
				this.ajaxAction.submitObject(key, this.action + '.restore', DEFINEALLPAGES_trataRestore);
			};	
			document.forms[i].clear = function () {
				var aux = this.onClear;
				if (aux != null && aux != undefined){
					this.onClear(this);
				}			
				HTMLUtils.clearForm(this);
				HTMLUtils.focusInFirstActivateField(this);
			};	
			document.forms[i].fireEvent = function (evt) {
				document.formSubmiting = this;
				var obj = new Object();
				obj.method = 'execute';
				obj.parmCount = 3;  
				obj.parm1 = DEFINEALLPAGES_getFacadeName(this.action);
				obj.parm2 = '';
				obj.parm3 = evt;
				DEFINEALLPAGES_reqEventos = DEFINEALLPAGES_getObjectAJAX();
				DEFINEALLPAGES_submitObject(DEFINEALLPAGES_reqEventos, this, obj, this.action + '.event', DEFINEALLPAGES_fCallBackEvent);
			}
			for(var j = 0; j <  document.forms[i].length; j++){
				DEFINEALLPAGES_geraConfiguracao(document.forms[i].elements[j], document.forms[i].elements[j].className);
			}
		}	
	}
}
function DEFINEALLPAGES_trataRespostaServidor(resposta){
	//var objs = ObjectUtils.deserializeCollectionFromString(resposta);
	var objs = ObjectUtils.deserializeCollectionFromStringSemQuebraEnter(resposta);
	var strScript = '';
	if (objs != null){
		for(var j = 0; j < objs.length; j++){
			strScript = objs[j].script;
			try{
				eval(strScript);
			}catch(ex){
				alert('ERRO EM DEFINEALLPAGES_trataRespostaServidor: ' + ex.message);
				alert('Problema ao executar o comando: ' + strScript);
			}
		}
	}
}
/**
 * Identifica os scripts na pagina e processa.
 */
function DEFINEALLPAGES_trataScripts(text){
	var i = 0; 
	var j = 0;
	var scriptExecutar;
	while(true){
		i = text.indexOf('<script');
		if (i == -1) break;
		try{
			while(text.charAt(i) != '>'){
				i++;
				if (i >= text.length){
					break;
				}
			}
		}catch(ex){
			alert(ex.message);
			break;
		}
		i++;
		
		j = text.indexOf('</script>');
		if (j == -1) continue;
		try{
			scriptExecutar = text.substring(i,j);
		}catch(ex){
			alert(ex.message);
			break;
		}
		if (scriptExecutar != '' && scriptExecutar != ' '){
			eval(scriptExecutar);
		}
		try{
			text = text.substr(j+1);
		}catch(ex){
			alert(ex.message);
			break;
		}
	}
}
function DEFINEALLPAGES_trataSave(){
	var form = document.formSubmiting;
	if (form == null || form == undefined) return;
	if (form.ajaxAction == null || form.ajaxAction == undefined) return;
	if (form.ajaxAction.req.readyState == 4){
		if (form.ajaxAction.req.status == 200){
			DEFINEALLPAGES_trataRespostaServidor(form.ajaxAction.req.responseText);
		}
	}	
}
function DEFINEALLPAGES_trataRestore(){
	var form = document.formSubmiting;
	if (form == null || form == undefined) return;
	if (form.ajaxAction == null || form.ajaxAction == undefined) return;
	if (form.ajaxAction.req.readyState == 4){
		if (form.ajaxAction.req.status == 200){
			DEFINEALLPAGES_trataRespostaServidor(form.ajaxAction.req.responseText);
			try{
				form.afterRestore();
			}catch (e){
			}
		}
	}	
}
function DEFINEALLPAGES_validarForm(form){
	for(var j = 0; j <  form.length; j++){
		var valid = form.elements[j].validacao;
		var description = form.elements[j].descricao;
		if (valid == null || valid == undefined || valid == '') continue;
		if (description == null || description == undefined){
			description = form.elements[j].name;
		}
		description += ': ';
		
		var arrayValidacao = valid.split(',');
		if (arrayValidacao != null && arrayValidacao != undefined){
			for(var i = 0; i < arrayValidacao.length; i++){
				var ret = null;
				eval('ret = ' + arrayValidacao[i] + '(form.elements[j], \'' + description + '\')');
				if (!ret){
					return false;
				}
			}
		}		
	}
	return true;
}
function DEFINEALLPAGES_geraConfiguracao(element, className){
	var valid = DEFINEALLPAGES_getOperacao(className, 'Valid');
	if (valid == ''){
		valid = DEFINEALLPAGES_getOperacao(className, 'valid');
	}
	var description = DEFINEALLPAGES_getOperacao(className, 'Description');
	//
	if (valid != ''){
		var arrayValidacao = valid.split(',');
		if (arrayValidacao != null && arrayValidacao != undefined){
			for(var i = 0; i < arrayValidacao.length; i++){
				DEFINEALLPAGES_processaValidacao(element, arrayValidacao[i], description);
			}
		}
	}
	//
	var format = DEFINEALLPAGES_getOperacao(className, 'Format');
	if (format == ''){
		format = DEFINEALLPAGES_getOperacao(className, 'format');
	}
	if (format != ''){
		var arrayFormatacao = format.split(',');
		if (arrayFormatacao != null && arrayFormatacao != undefined){
			for(var i = 0; i < arrayFormatacao.length; i++){
				DEFINEALLPAGES_processaFormatacao(element, arrayFormatacao[i]);
			}
		}
	}		
}
function DEFINEALLPAGES_processaFormatacao(element, formatacao){
	if (formatacao == 'Data' || formatacao == 'Date'){
		addEvent(element, 
				"keydown", 
				DEFINEALLPAGES_formataData, 
				false);
		addEvent(element, 
				"blur", 
				DEFINEALLPAGES_formataData, 
				false);				
	}
	if (formatacao == 'Hora' || formatacao == 'Hour' || formatacao == 'Time'){
		addEvent(element, 
				"keydown", 
				DEFINEALLPAGES_formataHora, 
				false);
		addEvent(element, 
				"blur", 
				DEFINEALLPAGES_formataHora, 
				false);				
	}	
	if (formatacao == 'CNPJ' || formatacao == 'cnpj'){
		addEvent(element, 
				"keydown", 
				DEFINEALLPAGES_formataCNPJ, 
				false);
	}
	if (formatacao == 'CPF' || formatacao == 'cpf'){
		addEvent(element, 
				"keydown", 
				DEFINEALLPAGES_formataCPF, 
				false);
	}	
	if (formatacao == 'NUMERO' || formatacao == 'Numero' || formatacao == 'numero'){
		addEvent(element, 
				"keydown", 
				DEFINEALLPAGES_formataNumero, 
				false);
		addEvent(element, 
				"blur", 
				DEFINEALLPAGES_formataNumero, 
				false);					
	}
	if (formatacao == 'MOEDA' || formatacao == 'Moeda' || formatacao == 'moeda' ||
		formatacao == 'MONEY' || formatacao == 'Money' || formatacao == 'money' ||
		formatacao == 'CURRENCY' || formatacao == 'Currency' || formatacao == 'currency'){
		addEvent(element, 
				"keydown", 
				DEFINEALLPAGES_formataMoeda, 
				false);
		addEvent(element, 
				"blur", 
				DEFINEALLPAGES_formataMoedaSaidaCampo, 
				false);				
	}			
}
function DEFINEALLPAGES_formataMoedaSaidaCampo(e){
	var element;
	if (isMozilla){
		element = e.target;
	}else{
		element = e.srcElement;
	}
	if (element == null) return true;
	element.value = NumberUtil.format(NumberUtil.toDouble(element.value), 2, ',', '.');
}
function DEFINEALLPAGES_formataData(e){
	var element;
	if (isMozilla){
		element = e.target;
	}else{
		element = e.srcElement;
	}
	if (element == null) return true;
	return FormatUtils.formataCampo(element.form, element.name, '99/99/9999', e);
}
function DEFINEALLPAGES_formataHora(e){
	var element;
	if (isMozilla){
		element = e.target;
	}else{
		element = e.srcElement;
	}
	if (element == null) return true;
	return FormatUtils.formataCampo(element.form, element.name, '99:99', e);
}
function DEFINEALLPAGES_formataCNPJ(e){
	var element;
	if (isMozilla){
		element = e.target;
	}else{
		element = e.srcElement;
	}
	if (element == null) return true;
	return FormatUtils.formataCampo(element.form, element.name, '99.999.999/9999-99', e);
}
function DEFINEALLPAGES_formataCPF(e){
	var element;
	if (isMozilla){
		element = e.target;
	}else{
		element = e.srcElement;
	}
	if (element == null) return true;
	return FormatUtils.formataCampo(element.form, element.name, '999.999.999-99', e);
}
function DEFINEALLPAGES_formataNumero(e){
	return FormatUtils.bloqueiaNaoNumerico(e);
}
function DEFINEALLPAGES_formataMoeda(e){
	var element;
	if (isMozilla){
		element = e.target;
	}else{
		element = e.srcElement;
	}
	if (element == null) return true;
	return FormatUtils.bloqueiaNaoNumericoCurrency(e, element.value);
}
function DEFINEALLPAGES_processaValidacao(element, validacao, description){
	if (validacao == null || validacao == undefined || validacao == '') return;
	if (description == null || description == ''){
		description = element.name;
	}
	var aux = element.validacao;
	if (aux == null || aux == undefined){
		element.validacao = '';
	}
	var desc = element.descricao;
	if (desc == null || desc == undefined){
		element.descricao = '';
	}
	if (element.descricao == ''){
		element.descricao = description;
	}
	
	aux = element.validacao;
	if (aux != '') aux = aux + ',';
	element.validacao = aux + 'ValidacaoUtils.valida' + StringUtils.trim(validacao);
}
function DEFINEALLPAGES_getOperacao(className, operacao){
	if (className == null || className == undefined || className == '') return '';
	var ini = className.indexOf(operacao);
	if (ini == -1){
		ini = className.indexOf(operacao);
	}
	var classOper = '';
	if (ini > -1){
		ini = ini + operacao.length + 1; //Considera o [ (primeiro colchete)
		for(var i = ini; i < className.length; i++){
			if (className.charAt(i) == ']'){
				break;
			}else{
				classOper = classOper + className.charAt(i);
			}
		}
	}
	return classOper;
}
function DEFINEALLPAGES_trataEventos_Click(e){
  DEFINEALLPAGES_reqEventos = DEFINEALLPAGES_getObjectAJAX();
  var elem;
  if (isMozilla){
  	elem = e.target;
  }else{
  	elem = event.srcElement;
  }
  var obj = new Object();
  obj.method = 'execute';
  obj.parmCount = 3;  
  obj.parm1 = DEFINEALLPAGES_getFacadeName(elem.form.action);
  obj.parm2 = elem.name;
  obj.parm3 = 'click';
  DEFINEALLPAGES_submitObject(DEFINEALLPAGES_reqEventos, elem.form, obj, elem.form.action + '.event', DEFINEALLPAGES_fCallBackEvent);
}
function DEFINEALLPAGES_trataEventos_Change(e){
  DEFINEALLPAGES_reqEventos = DEFINEALLPAGES_getObjectAJAX();
  var elem;
  if (isMozilla){
  	elem = e.target;
  }else{
  	elem = event.srcElement;
  }
  var obj = new Object();
  obj.method = 'execute';
  obj.parmCount = 3;  
  obj.parm1 = DEFINEALLPAGES_getFacadeName(elem.form.action);
  obj.parm2 = elem.name;
  obj.parm3 = 'change';
  DEFINEALLPAGES_submitObject(DEFINEALLPAGES_reqEventos, elem.form, obj, elem.form.action + '.event', DEFINEALLPAGES_fCallBackEvent);
}
function DEFINEALLPAGES_fCallBackEvent(){
	if (DEFINEALLPAGES_reqEventos.readyState == 4){
		if (DEFINEALLPAGES_reqEventos.status == 200){
			var resposta = DEFINEALLPAGES_reqEventos.responseText;
			var objs = ObjectUtils.deserializeCollectionFromStringSemQuebraEnter(resposta);
			if (objs != null){
				for(var j = 0; j < objs.length; j++){
					eval(ObjectUtils.decodificaAspasApostrofe(objs[j].script));
				}
			}
		}
	}
}
function DEFINEALLPAGES_getFacadeName(str){
	var ret = '';
	for(var i = str.length; i > 0; i--){
		if (str.charAt(i) == '/'){
			break;
		}else{
			ret = str.charAt(i) + ret;
		}
	}
	return ret;
}

addEvent(window, "load", DEFINEALLPAGES_loadAdicional, false);
document.onmousedown = DEFINEALLPAGES_SelectMouse;
document.onmouseup = new Function("isdrag = false");

function DEFINEALLPAGES_SelectMouse(e) {
  if (e){
	  MOUSE_PosX = e.clientX+document.body.scrollLeft;
	  MOUSE_PosY = e.clientY+document.body.scrollTop;
  }else{
	  MOUSE_PosX = event.clientX+document.body.scrollLeft;
	  MOUSE_PosY = event.clientY+document.body.scrollTop;  
  }

  var fobj       = nn6 ? e.target : event.srcElement;
  var topelement = nn6 ? "HTML" : "BODY";
  
  var ignoredNames = 'INPUT,TEXTAREA,SELECT-ONE,SELECT-MULTIPLE';
  if(ignoredNames.indexOf(fobj.nodeName) >= 0) return;
    
  var fobjValido = fobj;
  var cl = '';
  var tg = '';
  if(fobj.tagName != null){
  	tg = fobj.tagName;
  }
  if(fobj.className != null){
  	cl = fobj.className;
  }
  while (tg != topelement && cl != "dragme") {
  	if (fobj!=null){
		  if(fobj.className != null){
		  	cl = fobj.className;
		  	tg = fobj.tagName;
		  	fobjValido = fobj;
		  }  	
	      fobj = fobj.parentNode;
	}else{
		break;
	}
    //fobj = nn6 ? fobj.parentNode : fobj.parentElement;
  }
  if (cl == "dragme") {
    dobj = fobjValido;
    
    tx = parseInt(dobj.style.left + 0);
    ty = parseInt(dobj.style.top + 0);
    x = nn6 ? e.clientX : event.clientX;
    y = nn6 ? e.clientY : event.clientY;
        
	var bX = (fobjValido.offsetWidth - 20) + fobjValido.offsetLeft;
	var bY = (fobjValido.offsetHeight - 30) + fobjValido.offsetTop;
	
	if (IS_VERIFICA_BARRA_DIV){
		if ((fobjValido.offsetTop + 30) < y){
			return;
		}
	
	    //if(bX < x) return;    
	    //if(bY < y) return;
	}	
	
	isdrag = true;
	document.onmousemove = movemouse;
    return;
  }
}
function movemouse(e) {
  if (isdrag) {
    dobj.style.left = nn6 ? (tx + e.clientX - x) + 'px' : (tx + event.clientX - x) + 'px';
	dobj.style.top = nn6 ? (ty + e.clientY - y) + 'px' : (ty + event.clientY - y) + 'px';
	
    return false;
  }
}

