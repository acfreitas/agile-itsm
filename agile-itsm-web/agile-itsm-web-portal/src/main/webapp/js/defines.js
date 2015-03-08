var isdrag = false;
var x, y;
var tx, ty;
var dobj = null;
var isMozilla = true;
var isOpera = (navigator.userAgent.indexOf('Opera') != -1);
var isIE = (!isOpera && navigator.userAgent.indexOf('MSIE') != -1);
if (isOpera || isIE)
	isMozilla = false;
var nn6 = document.getElementById && !document.all;
var MOUSE_PosX = 0;
var MOUSE_PosY = 0;
var DEFINEALLPAGES_tamRequest = 0;
var DEFINEALLPAGES_requisicao;
var DEFINEALLPAGES_reqEventos;
var DEFINEALLPAGES_Array_reqEventos = new Array();
var DEFINEALLPAGES_controle_Array_reqEventos = 0;
var IS_VERIFICA_BARRA_DIV = true;
function $cit(id) {
	return document.getElementById(id);
}
function addEvent(object, evType, func, useCapture) {
	if (object == null || object == undefined)
		return;
	if (object.addEventListener) {
		object.addEventListener(evType, func, useCapture);
	} else {
		if (object.attachEvent) {
			object.attachEvent("on" + evType, func);
		}
	}
}
function DEFINEALLPAGES_getObjectAJAX() {
	var http_request = false;
	if (window.XMLHttpRequest) {
		http_request = new XMLHttpRequest();
		if (http_request.overrideMimeType) {
			http_request.overrideMimeType('text/html');
		}
	} else if (window.ActiveXObject) {
		try {
			http_request = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				http_request = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
			}
		}
	}
	if (!http_request) {
		alert('Cannot create XMLHTTP instance #####');
		return false;
	}
	return http_request;
}
function DEFINEALLPAGES_submitObject(requestObj, theForm, obj, action,
		fCallBack) {
	var queryString = '';
	if (theForm != null && theForm != undefined) {
		queryString = AjaxUtils.generateQueryStringByForm(theForm);
	}
	if (obj != null) {
		for ( var property in obj) {
			if (queryString.length > 0) {
				queryString += "&";
			}
			queryString += encodeURIComponent(property) + "="
					+ AjaxUtils.converteCaracteresEspeciais(obj[property]);
		}
	}
	if (queryString.length > 0) {
		queryString += "&";
	}
	queryString += "nocache=" + new Date();
	if (isIE) {
		queryString += "&isIE=S";
	}
	requestObj.onreadystatechange = fCallBack;
	try {
		requestObj.open("POST", action, true);
		requestObj.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		requestObj.setRequestHeader("encoding", "UTF-8");
		requestObj.setRequestHeader("charset", "UTF-8");
		requestObj.setRequestHeader('Cache-Control', 'no-store, no-cache, must-revalidate');
		requestObj.setRequestHeader('Cache-Control', 'post-check=0, pre-check=0');
		requestObj.setRequestHeader('Pragma', 'no-cache');
	} catch (e) {
		alert(e);
	}
	requestObj.send(queryString);
}
function DEFINEALLPAGES_setaIds() {
	var textAreaList = document.getElementsByTagName("textarea");
	if (textAreaList != null) {
		for ( var i = 0; i < textAreaList.length; i++) {
			var maxLength = textAreaList[i].getAttribute("maxLength");
			if (maxLength == null) {
				continue;
			}
			textAreaList[i]
					.addEventListener(
							"keydown",
							function() {
								this.value = this.value.length > parseInt(maxLength) ? this.value
										.substring(0, maxLength - 1)
										: this.value;
							}, true);
		}
	}
	Array.prototype.clone = function() {
		return this.slice(0);
	}
	var fs = document.forms.length;
	if (fs != null && fs != undefined) {
		for ( var i = 0; i < fs; i++) {
			for ( var j = 0; j < document.forms[i].length; j++) {
				var id = document.forms[i].elements[j].id;
				var clName = document.forms[i].elements[j].className;
				if (id == null || id == undefined || id == '') {
					document.forms[i].elements[j].id = document.forms[i].elements[j].name;
				}
				if (isIE) {
					var el = document.forms[i].elements[j];
					if ((el.type == 'hidden' || el.type == 'text'
							|| el.type == 'textarea' || el.type == 'select-one'
							|| el.type == 'select-multiple'
							|| el.type == 'file' || el.type == 'radio')) {
						document.forms[i].elements[j].className = document.forms[i].elements[j].className
								+ ' inputPadrao';
					}
					if ((el.type == 'button' || el.type == 'submit' || el.type == 'reset')) {
						document.forms[i].elements[j].className = document.forms[i].elements[j].className
								+ ' botaoPadrao';
					}
				}
			}
		}
	}
}
var DEFINEALLPAGES_requisicao = null;
function DEFINEALLPAGES_fCallBackValuesLoad() {
	if (DEFINEALLPAGES_requisicao.readyState == 4) {
		if (DEFINEALLPAGES_requisicao.status == 200) {
			DEFINEALLPAGES_fCallBackLoadDirect(DEFINEALLPAGES_requisicao.responseText);
			try {
				JANELA_AGUARDE_MENU.hide();
			} catch (ex) {
			}
		}
	}
}
function DEFINEALLPAGES_loadAdicional() {
	var tabberArgs = {};
	try {
		tabberAutomatic(tabberArgs);
	} catch (e) {
	}
	DEFINEALLPAGES_setaIds();
	var url = new String(window.location);
	if (url.indexOf('.load') > -1) {
		var urlGetValuesProcessed = url.replace('.load', '.get');
		var reqLoad = DEFINEALLPAGES_getObjectAJAX();
		DEFINEALLPAGES_submitObject(
				reqLoad,
				null,
				null,
				urlGetValuesProcessed,
				function() {
					if (reqLoad.readyState == 4) {
						if (reqLoad.status == 200) {
							DEFINEALLPAGES_fCallBackLoadDirect(reqLoad.responseText);
						}
					}
				});
	}
}
function DEFINEALLPAGES_processaLoadSubPage(urlChamada, textResponse) {
	DEFINEALLPAGES_setaIds();
	DEFINEALLPAGES_trataScripts(textResponse);
	var urlGetValuesProcessed = urlChamada.replace('.load', '.get');
	var reqLoadSubPage = DEFINEALLPAGES_getObjectAJAX();
	DEFINEALLPAGES_submitObject(
			reqLoadSubPage,
			null,
			null,
			urlGetValuesProcessed,
			function() {
				if (reqLoadSubPage.readyState == 4) {
					if (reqLoadSubPage.status == 200) {
						DEFINEALLPAGES_fCallBackLoadDirect(reqLoadSubPage.responseText);
					}
				}
			});
}
function DEFINEALLPAGES_fCallBackLoad() {
	for ( var i = 0; i < DEFINEALLPAGES_tamRequest; i++) {
		if (DEFINEALLPAGES_requisicao[i] == null
				|| DEFINEALLPAGES_requisicao[i] == undefined)
			continue;
		if (DEFINEALLPAGES_requisicao[i].readyState == 4) {
			if (DEFINEALLPAGES_requisicao[i].status == 200) {
				var resposta = DEFINEALLPAGES_requisicao[i].responseText;
				var objs = ObjectUtils
						.deserializeCollectionFromStringSemQuebraEnter(resposta);
				if (objs != null) {
					for ( var j = 0; j < objs.length; j++) {
						eval(ObjectUtils
								.decodificaAspasApostrofe(objs[j].script));
					}
				}
			}
		}
	}
}
function DEFINEALLPAGES_atribuiCaracteristicasCitAjax() {
	DEFINEALLPAGES_fCallBackLoadDirect('');
}
function DEFINEALLPAGES_fCallBackLoadDirect(text) {
	DEFINEALLPAGES_trataRespostaServidor(text);
	var fs = document.forms.length;
	if (fs != null && fs != undefined) {
		for ( var i = 0; i < fs; i++) {
			document.forms[i].validate = function() {
				try {
					var retorno = DEFINEALLPAGES_validarForm(this);
					/*(flag) Elemento que  oculta o janela aguarde quando submetido o form*/
					if(!retorno) {
						if (this.ocultaJanelaAguarde || this.ocultaJanelaAguardeParent) {
							if(this.ocultaJanelaAguarde)
								JANELA_AGUARDE_MENU.hide();
							if(this.ocultaJanelaAguardeParent)
								parent.JANELA_AGUARDE_MENU.hide()
						}
					}
					return retorno;
				} catch (err) {
					alert(err.message);
				}
			};
			document.forms[i].save = function() {
				var aux = this.onValidate;
				var b = true;
				if (aux != null && aux != undefined) {
					try {
						b = this.onValidate(this);
					} catch (e) {
					}
				}
				if (!b) {
					return;
				}
				if (this.validate()) {
					var aux = this.onSave;
					if (aux != null && aux != undefined) {
						try {
							this.onSave(this);
						} catch (e) {
						}
					}
					this.ajaxAction = new AjaxAction();
					document.formSubmiting = this;
					var formSubmit = this;
					this.ajaxAction
							.submitForm(
									this,
									this.action + '.save',
									function() {
										if (formSubmit == null
												|| formSubmit == undefined)
											return;
										if (formSubmit.ajaxAction == null
												|| formSubmit.ajaxAction == undefined)
											return;
										if (formSubmit.ajaxAction.req.readyState == 4) {
											if (formSubmit.ajaxAction.req.status == 200) {
												DEFINEALLPAGES_trataRespostaServidor(formSubmit.ajaxAction.req.responseText);
											}
										}
									});
				}
			};
			document.forms[i].restore = function(key) {
				var aux = this.onValidateRestore;
				if (aux != null && aux != undefined) {
					this.onValidateRestore(this);
				}
				var aux = this.onRestore;
				if (aux != null && aux != undefined) {
					this.onRestore(this);
				}
				this.ajaxAction = new AjaxAction();
				document.formSubmiting = this;
				var formSubmit = this;
				this.ajaxAction
						.submitObject(
								key,
								this.action + '.restore',
								function() {
									if (formSubmit == null
											|| formSubmit == undefined)
										return;
									if (formSubmit.ajaxAction == null
											|| formSubmit.ajaxAction == undefined)
										return;
									if (formSubmit.ajaxAction.req.readyState == 4) {
										if (formSubmit.ajaxAction.req.status == 200) {
											DEFINEALLPAGES_trataRespostaServidor(formSubmit.ajaxAction.req.responseText);
											try {
												formSubmit.afterRestore();
											} catch (e) {
											}
										}
									}
								});
			};
			document.forms[i].clear = function() {
				var aux = this.onClear;
				if (aux != null && aux != undefined) {
					this.onClear(this);
				}
				HTMLUtils.clearForm(this);
				HTMLUtils.focusInFirstActivateField(this);
			};
			document.forms[i].fireEvent = function(evt) {
				document.formSubmiting = this;
				var obj = new Object();
				obj.method = 'execute';
				obj.parmCount = 3;
				obj.parm1 = DEFINEALLPAGES_getFacadeName(this.action);
				obj.parm2 = '';
				obj.parm3 = evt;
				DEFINEALLPAGES_controle_Array_reqEventos = DEFINEALLPAGES_controle_Array_reqEventos + 1;
				var indice = DEFINEALLPAGES_controle_Array_reqEventos;
				DEFINEALLPAGES_Array_reqEventos[indice] = DEFINEALLPAGES_getObjectAJAX();
				DEFINEALLPAGES_submitObject(
						DEFINEALLPAGES_Array_reqEventos[indice],
						this,
						obj,
						this.action + '.event',
						function() {
							if (DEFINEALLPAGES_Array_reqEventos[indice].readyState == 4) {
								if (DEFINEALLPAGES_Array_reqEventos[indice].status == 200) {
									var resposta = DEFINEALLPAGES_Array_reqEventos[indice].responseText;
									var objs = ObjectUtils
											.deserializeCollectionFromStringSemQuebraEnter(resposta);
									if (objs != null) {
										for ( var j = 0; j < objs.length; j++) {
											try {
												eval(ObjectUtils
														.decodificaAspasApostrofe(objs[j].script));
											} catch (e) {
											}
										}
									}
								}
							}
						});
			}
			for ( var j = 0; j < document.forms[i].length; j++) {
				DEFINEALLPAGES_geraConfiguracao(document.forms[i].elements[j],
						document.forms[i].elements[j].className);
			}
		}
		for ( var i = 0; i < fs; i++) {
			try {
				document.forms[i].afterLoad();
			} catch (e) {
			}
		}
	}
}
function DEFINEALLPAGES_generateConfiguracaoCampos() {
	var fs = document.forms.length;
	if (fs != null && fs != undefined) {
		for ( var i = 0; i < fs; i++) {
			for ( var j = 0; j < document.forms[i].length; j++) {
				DEFINEALLPAGES_geraConfiguracao(document.forms[i].elements[j],
						document.forms[i].elements[j].className);
			}
		}
	}
}
var erroGrid = false;
function DEFINEALLPAGES_trataRespostaServidor(resposta) {
	var objs = ObjectUtils
			.deserializeCollectionFromStringSemQuebraEnter(resposta);
	var strScript = '';
	var iCont = 0;
	var o = [];
	var t = [];
	if (objs != null) {
		for ( var j = 0; j < objs.length; j++) {
			strScript = objs[j].script;
			try {
				eval(strScript);
			} catch (ex) {
				if (strScript.indexOf("exibirTarefas(") == 0
						|| strScript.indexOf("setinha()") == 0) {
					alert("Ocorreu um erro ao renderizar a grid! Entre em contato com o administrador do sistema.");
					erroGrid = true;
				} else
					alert('ERRO EM DEFINEALLPAGES_trataRespostaServidor: '
							+ ex.message + ' Problema ao executar o comando: '
							+ strScript);
			}
		}
	}
}
function DEFINEALLPAGES_trataScripts(text) {
	var i = 0;
	var j = 0;
	var scriptExecutar;
	while (true) {
		i = text.indexOf('<script');
		if (i == -1)
			break;
		try {
			while (text.charAt(i) != '>') {
				i++;
				if (i >= text.length) {
					break;
				}
			}
		} catch (ex) {
			alert(ex.message);
			break;
		}
		i++;
		j = text.indexOf('</script>');
		if (j == -1)
			continue;
		try {
			scriptExecutar = text.substring(i, j);
		} catch (ex) {
			alert(ex.message);
			break;
		}
		if (scriptExecutar != '' && scriptExecutar != ' ') {
			eval(scriptExecutar);
		}
		try {
			text = text.substr(j + 1);
		} catch (ex) {
			alert(ex.message);
			break;
		}
	}
}
function DEFINEALLPAGES_validarForm(form) {
	for ( var j = 0; j < form.length; j++) {
		try {
			var valid = form.elements[j].validacao;
			var description = form.elements[j].descricao;
			if (valid == null || valid == undefined || valid == '')
				continue;
			if (description == null || description == undefined) {
				description = form.elements[j].name;
			}
			if (i18n_message(description) != null) {
				description = i18n_message(description)
			}
			description += ': ';
			var arrayValidacao = valid.split(',');
			if (arrayValidacao != null && arrayValidacao != undefined) {
				for ( var i = 0; i < arrayValidacao.length; i++) {
					var ret = null;
					eval('ret = ' + arrayValidacao[i] + '(form.elements[j], \''
							+ description + '\')');
					if (!ret) {
						return false;
					}
				}
			}
		} catch (err) {
			alert(err.message);
		}
	}
	return true;
}
function DEFINEALLPAGES_geraConfiguracao(element, className) {
	var valid = DEFINEALLPAGES_getOperacao(className, 'Valid');
	if (valid == '') {
		valid = DEFINEALLPAGES_getOperacao(className, 'valid');
	}
	var description = DEFINEALLPAGES_getOperacao(className, 'Description');
	if (valid != '') {
		var arrayValidacao = valid.split(',');
		if (arrayValidacao != null && arrayValidacao != undefined) {
			for ( var i = 0; i < arrayValidacao.length; i++) {
				DEFINEALLPAGES_processaValidacao(element, arrayValidacao[i],
						description);
			}
		}
	}
	var format = DEFINEALLPAGES_getOperacao(className, 'Format');
	if (format == '') {
		format = DEFINEALLPAGES_getOperacao(className, 'format');
	}
	if (format != '') {
		var arrayFormatacao = format.split(',');
		if (arrayFormatacao != null && arrayFormatacao != undefined) {
			for ( var i = 0; i < arrayFormatacao.length; i++) {
				DEFINEALLPAGES_processaFormatacao(element, arrayFormatacao[i]);
			}
		}
	}
}
function DEFINEALLPAGES_processaFormatacao(element, formatacao) {
	if (formatacao == 'Data' || formatacao == 'Date') {
		addEvent(element, "keydown", DEFINEALLPAGES_formataData, false);
		addEvent(element, "blur", DEFINEALLPAGES_formataData, false);
	}
	if (formatacao == 'Hora' || formatacao == 'Hour' || formatacao == 'Time') {
		addEvent(element, "keydown", DEFINEALLPAGES_formataHora, false);
		addEvent(element, "blur", DEFINEALLPAGES_formataHora, false);
	}
	if (formatacao == 'CNPJ' || formatacao == 'cnpj') {
		addEvent(element, "keydown", DEFINEALLPAGES_formataCNPJ, false);
	}
	if (formatacao == 'CPF' || formatacao == 'cpf') {
		addEvent(element, "keydown", DEFINEALLPAGES_formataCPF, false);
	}
	if (formatacao == 'NUMERO' || formatacao == 'Numero'
			|| formatacao == 'numero') {
		addEvent(element, "keydown", DEFINEALLPAGES_formataNumero, false);
		addEvent(element, "blur", DEFINEALLPAGES_formataNumeroSaidaCampo, false);
	}
	if (formatacao == 'MOEDA' || formatacao == 'Moeda' || formatacao == 'moeda'
			|| formatacao == 'MONEY' || formatacao == 'Money'
			|| formatacao == 'money' || formatacao == 'CURRENCY'
			|| formatacao == 'Currency' || formatacao == 'currency') {
		addEvent(element, "keydown", DEFINEALLPAGES_formataMoeda, false);
		addEvent(element, "blur", DEFINEALLPAGES_formataMoedaSaidaCampo, false);
	}
	if (formatacao.toUpperCase().indexOf('DECIMAL') >= 0) {
		var qtdeDecimais = 2;
		if (formatacao.length > 6) {
			try {
				qtdeDecimais = parseInt(formatacao.substring(7,
						formatacao.length));
				if (qtdeDecimais == undefined) {
					qtdeDecimais = 2;
				}
			} catch (excp) {
				qtdeDecimais = 2;
			}
		}
		addEvent(element, "keydown", DEFINEALLPAGES_formataMoeda, false);
		addEvent(element, "blur", function(e) {
			DEFINEALLPAGES_formataDecimalSaidaCampo(e, qtdeDecimais);
		}, false);
	}
}
function DEFINEALLPAGES_formataDecimalSaidaCampo(e, qtdeDecimaisParm) {
	var qtdeDecimais = 2;
	if (qtdeDecimaisParm != undefined) {
		qtdeDecimais = qtdeDecimaisParm;
	}
	var element;
	if (isMozilla) {
		element = e.target;
	} else {
		element = e.srcElement;
	}
	if (element == null)
		return true;
	element.value = NumberUtil.format(NumberUtil.toDouble(element.value),
			qtdeDecimais, ',', '.');
}
function DEFINEALLPAGES_formataMoedaSaidaCampo(e) {
	FormatUtils.bloqueiaNaoMoedaSaidaCampo(e);
}
function DEFINEALLPAGES_formataData(e) {
	var element;
	if (isMozilla) {
		element = e.target;
	} else {
		element = e.srcElement;
	}
	if (element == null)
		return true;
	return FormatUtils
			.formataCampo(element.form, element.name, '99/99/9999', e);
}
function DEFINEALLPAGES_formataHora(e) {
	var element;
	if (isMozilla) {
		element = e.target;
	} else {
		element = e.srcElement;
	}
	if (element == null)
		return true;
	return FormatUtils.formataCampo(element.form, element.name, '99:99', e);
}
function DEFINEALLPAGES_formataCNPJ(e) {
	var element;
	if (isMozilla) {
		element = e.target;
	} else {
		element = e.srcElement;
	}
	if (element == null)
		return true;
	return FormatUtils.formataCampo(element.form, element.name,
			'99.999.999/9999-99', e);
}
function DEFINEALLPAGES_formataCPF(e) {
	var element;
	if (isMozilla) {
		element = e.target;
	} else {
		element = e.srcElement;
	}
	if (element == null)
		return true;
	return FormatUtils.formataCampo(element.form, element.name,
			'999.999.999-99', e);
}
function DEFINEALLPAGES_formataNumero(e) {
	return FormatUtils.bloqueiaNaoNumerico(e);
}
function DEFINEALLPAGES_formataNumeroSaidaCampo(e) {
	FormatUtils.bloqueiaNaoNumericoSaidaCampo(e);
}
function DEFINEALLPAGES_formataMoeda(e) {
	var element;
	if (isMozilla) {
		element = e.target;
	} else {
		element = e.srcElement;
	}
	if (element == null)
		return true;
	return FormatUtils.bloqueiaNaoNumericoCurrency(e,element.value);
}
function DEFINEALLPAGES_processaValidacao(element, validacao, description) {
	if (validacao == null || validacao == undefined || validacao == '')
		return;
	if (description == null || description == '') {
		description = element.name;
	}
	var aux = element.validacao;
	if (aux == null || aux == undefined) {
		element.validacao = '';
	}
	var desc = element.descricao;
	if (desc == null || desc == undefined) {
		element.descricao = '';
	}
	if (element.descricao == '') {
		element.descricao = description;
	}
	aux = element.validacao;
	if (aux != '')
		aux = aux + ',';
	element.validacao = aux + 'ValidacaoUtils.valida'
			+ StringUtils.trim(validacao);
}
function DEFINEALLPAGES_getOperacao(className, operacao) {
	if (className == null || className == undefined || className == '')
		return '';
	var ini = className.indexOf(operacao);
	if (ini == -1) {
		ini = className.indexOf(operacao);
	}
	var classOper = '';
	if (ini > -1) {
		if (className.charAt(ini + operacao.length) == '[') {
			ini = ini + operacao.length + 1;
			for ( var i = ini; i < className.length; i++) {
				if (className.charAt(i) == ']') {
					break;
				} else {
					classOper = classOper + className.charAt(i);
				}
			}
		}
	}
	return classOper;
}
function DEFINEALLPAGES_trataEventos_Click(e) {
	DEFINEALLPAGES_reqEventos = DEFINEALLPAGES_getObjectAJAX();
	var elem;
	if (isMozilla) {
		elem = e.target;
	} else {
		elem = event.srcElement;
	}
	var obj = new Object();
	obj.method = 'execute';
	obj.parmCount = 3;
	obj.parm1 = DEFINEALLPAGES_getFacadeName(elem.form.action);
	obj.parm2 = elem.name;
	obj.parm3 = 'click';
	DEFINEALLPAGES_submitObject(DEFINEALLPAGES_reqEventos, elem.form, obj,
			elem.form.action + '.event', DEFINEALLPAGES_fCallBackEvent);
}
function DEFINEALLPAGES_trataEventos_Change(e) {
	DEFINEALLPAGES_reqEventos = DEFINEALLPAGES_getObjectAJAX();
	var elem;
	if (isMozilla) {
		elem = e.target;
	} else {
		elem = event.srcElement;
	}
	var obj = new Object();
	obj.method = 'execute';
	obj.parmCount = 3;
	obj.parm1 = DEFINEALLPAGES_getFacadeName(elem.form.action);
	obj.parm2 = elem.name;
	obj.parm3 = 'change';
	DEFINEALLPAGES_submitObject(DEFINEALLPAGES_reqEventos, elem.form, obj,
			elem.form.action + '.event', DEFINEALLPAGES_fCallBackEvent);
}
function DEFINEALLPAGES_fCallBackEvent() {
	if (DEFINEALLPAGES_reqEventos.readyState == 4) {
		if (DEFINEALLPAGES_reqEventos.status == 200) {
			var resposta = DEFINEALLPAGES_reqEventos.responseText;
			var objs = ObjectUtils
					.deserializeCollectionFromStringSemQuebraEnter(resposta);
			if (objs != null) {
				for ( var j = 0; j < objs.length; j++) {
					eval(ObjectUtils.decodificaAspasApostrofe(objs[j].script));
				}
			}
		}
	}
}
function DEFINEALLPAGES_getFacadeName(str) {
	var ret = '';
	for ( var i = str.length; i > 0; i--) {
		if (str.charAt(i) == '/') {
			break;
		} else {
			ret = str.charAt(i) + ret;
		}
	}
	return ret;
}
addEvent(window, "load", DEFINEALLPAGES_loadAdicional, false);
function ALL_PAGES_checkKey(evt) {
	evt = (evt) ? evt : ((event) ? event : null);
	if (isIE) {
		evt.cancelBubble = true;
	}
	var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement
			: null);
	var shift = evt.shiftKey;
	var ctrl = evt.ctrlKey;
	var alt = evt.altKey;
	var tecla = (evt.keyCode) ? evt.keyCode : evt.which;
	if ((tecla == 13) && (node.type != "button") && (node.type != "textarea")) {
		evt.returnValue = false;
		evt.cancelBubble = true;
	}
	if ((tecla == 8) && (node.type != "text") && (node.type != "textarea")) {
		evt.returnValue = false;
		evt.cancelBubble = true;
	}
}
function DEFINEALLPAGES_SelectMouse(e) {
	if (e) {
		MOUSE_PosX = e.clientX + document.body.scrollLeft;
		MOUSE_PosY = e.clientY + document.body.scrollTop;
	} else {
		MOUSE_PosX = event.clientX + document.body.scrollLeft;
		MOUSE_PosY = event.clientY + document.body.scrollTop;
	}
	var fobj = nn6 ? e.target : event.srcElement;
	var topelement = nn6 ? "HTML" : "BODY";
	var ignoredNames = 'INPUT,TEXTAREA,SELECT-ONE,SELECT-MULTIPLE';
	if (ignoredNames.indexOf(fobj.nodeName) >= 0)
		return;
	var fobjValido = fobj;
	var cl = '';
	var tg = '';
	if (fobj.tagName != null) {
		tg = fobj.tagName;
	}
	if (fobj.className != null) {
		cl = fobj.className;
	}
	while (tg != topelement && cl != "dragme" && cl != "nodrag") {
		if (fobj != null) {
			if (fobj.className != null) {
				cl = fobj.className;
				tg = fobj.tagName;
				fobjValido = fobj;
			}
			fobj = fobj.parentNode;
		} else {
			break;
		}
	}
	if (cl == "dragme") {
		dobj = fobjValido;
		tx = parseInt(dobj.style.left + 0);
		ty = parseInt(dobj.style.top + 0);
		x = nn6 ? e.clientX : event.clientX;
		y = nn6 ? e.clientY : event.clientY;
		var bX = (fobjValido.offsetWidth - 20) + fobjValido.offsetLeft;
		var bY = (fobjValido.offsetHeight - 30) + fobjValido.offsetTop;
		if (IS_VERIFICA_BARRA_DIV) {
			if ((fobjValido.offsetTop + 30) < y) {
				return;
			}
		}
		isdrag = true;
		return;
	}
}
function DEFINES_movemouse(e) {
	if (isdrag) {
		if (dobj != null) {
			var valueY = nn6 ? (ty + e.clientY - y) : (ty + event.clientY - y);
			if (valueY < 0) {
				valueY = 0;
			}
			dobj.style.left = nn6 ? (tx + e.clientX - x) + 'px' : (tx
					+ event.clientX - x)
					+ 'px';
			dobj.style.top = valueY + 'px';
		}
		return false;
	}
}
function DEFINES_moveup(e) {
	isdrag = false;
	dobj = null;
}
addEvent(window, "mousedown", DEFINEALLPAGES_SelectMouse, false);
addEvent(window, "mouseup", DEFINES_moveup, false);
addEvent(window, "mousemove", DEFINES_movemouse, false);