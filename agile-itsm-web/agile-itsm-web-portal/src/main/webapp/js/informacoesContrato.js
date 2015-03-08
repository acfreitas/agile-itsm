var procedimentosSolicitados = null;
var verificarAbandonoSistema = true;
function fecharJanela(evt) {
	if (verificarAbandonoSistema) {
		var message = 'Você clicou no botão fechar do Browser, você quer fechar esta janela e sair do sistema ?';
		if (typeof evt == 'undefined') {
			evt = window.event;
		}
		if (evt) {
			evt.returnValue = message;
		}
		return message;
	}
}

function Screen() {
	return {
		w : document.body.clientWidth || window.innerWidth || self.innerWidth,
		h : document.body.clientHeight || window.innerHeight
				|| self.innerHeight
	};
}

function setDatePicker(idDate) {
	$("#" + idDate).datepicker();
}

ajustaTamanhoTela = function() {
	var o = Screen();

	var e = document.getElementById('areaUtilContrato');

	var dimW = 170;
	var dimH = 86;
	if (document.getElementById('tblMenuProntuario').style.display == 'none') {
		dimW = 31;
		dimH = 86;
	}

	e.style.width = (o.w - dimW) + "px";
	e.style.height = (o.h - dimH) + "px";
};

addEvent(window, "load", ajustaTamanhoTela, false);
addEvent(window, "scroll", ajustaTamanhoTela, false);
addEvent(window, "resize", ajustaTamanhoTela, false);

var tabberOptions = {

	'manualStartup' : true,

	/*---- Gera um evento click ----*/
	'onClick' : function(argsObj) {

		var t = argsObj.tabber; /* Tabber object */
		var id = t.id; /* ID of the main tabber DIV */
		var i = argsObj.index; /*
								 * Which tab was clicked (0 is the first tab)
								 */
		var e = argsObj.event; /* Event object */
	},

	'addLinkId' : true
};

function LOOKUP_CONTRATOS_select(id, desc) {
	document.formProntuario.idContrato.value = id;

	document.formProntuario.fireEvent('setaContrato');

	POPUP_QUESTIONARIO.setTitle('NÃºmero Contrato: ' + desc);

	document.getElementById('areaUtilContrato').innerHTML = '';
}

function imprimirConteudoDiv(nameDiv) {
	document.formProntuario.conteudoDivImprimir.value = document
			.getElementById(nameDiv).innerHTML;
	document.formProntuario.fireEvent('imprimirConteudoDiv');
}

function imprimirFatura(idFaturaParm) {
	document.formProntuario.idFatura.value = idFaturaParm;
	document.formProntuario.fireEvent('imprimirFaturaContrato');
}

var ajaxAction = new AjaxAction();
var ajaxAction2 = new AjaxAction();
var ajaxAction3 = new AjaxAction();
var ajaxAction4 = new AjaxAction();
var ajaxAction5 = new AjaxAction();
var urlChamada = '';
var acaoSubmit = '';
var objSubmit;
var arrayItensMenu = new Array();
var arrayNomesItensMenu = new Array();
var iItemMenu = 0;
var idQuestaoReceitaAux = 0;
var arrayItensMenu2 = new Array();
var arrayNomesItensMenu2 = new Array();
var iItemMenu2 = 0;
var abaSel2 = '';

var abaSelecionada = '';

function imprimirFatura(idFaturaParm) {
	document.formProntuario.idFatura.value = idFaturaParm;
	document.formProntuario.fireEvent('imprimirFaturaContrato');
}

var ajaxAction = new AjaxAction();
var ajaxAction2 = new AjaxAction();
var ajaxAction3 = new AjaxAction();
var ajaxAction4 = new AjaxAction();
var ajaxAction5 = new AjaxAction();
var urlChamada = '';
var acaoSubmit = '';
var objSubmit;
var arrayItensMenu = new Array();
var arrayNomesItensMenu = new Array();
var iItemMenu = 0;
var idQuestaoReceitaAux = 0;
var arrayItensMenu2 = new Array();
var arrayNomesItensMenu2 = new Array();
var iItemMenu2 = 0;
var abaSel2 = '';

var abaSelecionada = '';

abrePDFAnexo = function(id) {
	document.formListaAnexos.idControleGED.value = '';
	document.formListaAnexos.nomeArquivo.value = '';
	document.formListaAnexos.idControleGED.value = id;
	document.formListaAnexos.nomeArquivo.value = id;
	document.formListaAnexos.fireEvent('abrePDF');
};

funcaoChamaInformacoesHistoricas = function(nomeAbaParm, idProntCfg) {
	document.getElementById('divRegistroInfoHistoricas').innerHTML = '';
	document.formInformacoesHistoricas.idContrato.value = document.formProntuario.idContrato.value;
	document.formInformacoesHistoricas.idInformacoesContratoConfig.value = idProntCfg;
	document.formInformacoesHistoricas.fireEvent('load');
	POPUP_INFO_HISTORICAS.showInYPosition({
		top : 30
	});
};

refreshFuncionalidade = function() {
	if (acaoSubmit != '') {
		document.getElementById('areaUtilContrato').innerHTML = 'Aguarde... executando...';

		urlChamada = acaoSubmit;

		ajaxAction = new AjaxAction();
		ajaxAction.submitObject(objSubmit, acaoSubmit, callBackFuncProntuario);
	} else {
		document.getElementById('areaUtilContrato').innerHTML = '';
	}
};

callBackFuncProntuario = function() {
	if (ajaxAction.req.readyState == 4) {
		if (ajaxAction.req.status == 200) {
			document.getElementById('areaUtilContrato').innerHTML = ajaxAction.req.responseText;

			ajustaTamanhoTela();

			// tabberAutomatic(tabberOptions);

			DEFINEALLPAGES_processaLoadSubPage(urlChamada,
					ajaxAction.req.responseText);
			DEFINEALLPAGES_atribuiCaracteristicasCitAjax();

			try {
				setDatePicker('dataInicioOS');
			} catch (e) {
			}
			try {
				setDatePicker('dataFimOS');
			} catch (e) {
			}
			try {
				setDatePicker('dataInicioFatura');
			} catch (e) {
			}
			try {
				setDatePicker('dataFimFatura');
			} catch (e) {
			}
		}
		if (ajaxAction.req.status == 403) {
			document.getElementById('areaUtilContrato').innerHTML = '';
			alert('Acesso bloqueado ao recurso!');
		}
	}
};

mostraPesquisaPaciente = function() {
};

function showHCrescente() {
	ordemHistorico = 'C';
	if (tipoHistorico == 'PDF') {
		showHistorico();
	} else {
		showHistoricoHTML();
	}
}
function showHDecrescente() {
	ordemHistorico = 'D';
	if (tipoHistorico == 'PDF') {
		showHistorico();
	} else {
		showHistoricoHTML();
	}
}

function showDocsRecentes() {
	if (document.formProntuario.idContrato.value == '') {
		alert('Informe o Contrato!');
		return;
	}
	document.formDocRecente.idContratoDocRecente.value = document.formProntuario.idContrato.value;
	document.getElementById('divDocsRecentes').innerHTML = 'Aguarde... carregando...';
	document.formDocRecente.fireEvent('listar');
	POPUP_DOCS_RECENTES.showInYPosition({
		top : 30
	});
}
function imprimeDocRecente(idDoc) {
	if (document.formProntuario.idContrato.value == '') {
		alert('Informe o Contrato!');
		return;
	}
	document.formDocRecente.idDocRecente.value = idDoc;
	document.formDocRecente.fireEvent('abrir');
}

function mostrarTelaCadAlerta() {
	if (document.formProntuario.idContrato.value == '') {
		alert('Informe o Contrato!');
		return;
	}
	document.formAlerta.idContrato.value = document.formProntuario.idContrato.value;
	POPUP_CAD_ALERTA.showInYPosition({
		top : 30
	});
}

function arquivoCarregadoUpload() {
	var funcao = document.formProntuario.funcaoUpload.value;
	if (funcao == 'res') {
		document.formSelecaoProdutos.fireEvent('listarCertificados');
	}
	if (funcao == 'anexos') {
		document.formListaCertificadosAnexos.fireEvent('listarCertificados');
	}
}

setDataNow = function(obj) {
	var now = new Date();
	var dayStr = "0";
	if (now.getDate() <= 9)
		dayStr += now.getDate();
	else
		dayStr = now.getDate();
	var monStr = "0";
	if (now.getMonth() <= 8)
		monStr += (now.getMonth() + 1);
	else
		monStr = (now.getMonth() + 1);
	var dtStr = dayStr + "/" + monStr + "/" + now.getFullYear();
	obj.value = dtStr;
	return true;
}

function listarOS(typeList, objTd) {
	document.formProntuario.funcaoListarOS.value = typeList;

	document.formProntuario.dataOS1.value = '';
	document.formProntuario.dataOS2.value = '';
	try {
		document.formProntuario.dataOS1.value = document
				.getElementById('dataInicioOS').value;
	} catch (e) {
	}
	try {
		document.formProntuario.dataOS2.value = document
				.getElementById('dataFimOS').value;
	} catch (e) {
	}
	document.formProntuario.fireEvent('listarOSContrato');

	document.getElementById('tdEmExecucao').style.backgroundColor = 'white';
	document.getElementById('tdEmCriacao').style.backgroundColor = 'white';
	document.getElementById('tdAnaliseAceitacao').style.backgroundColor = 'white';
	document.getElementById('tdAguardHomologacao').style.backgroundColor = 'white';
	document.getElementById('tdHomologadas').style.backgroundColor = 'white';
	document.getElementById('tdRejeitadas').style.backgroundColor = 'white';
	document.getElementById('tdTodas').style.backgroundColor = 'white';

	objTd.style.backgroundColor = '#E2E2E2';
}

function listarFatura(typeList, objTd) {
	document.formProntuario.funcaoListarFatura.value = typeList;

	document.formProntuario.dataFatura1.value = '';
	document.formProntuario.dataFatura2.value = '';
	try {
		document.formProntuario.dataFatura1.value = document
				.getElementById('dataInicioFatura').value;
	} catch (e) {
	}
	try {
		document.formProntuario.dataFatura2.value = document
				.getElementById('dataFimFatura').value;
	} catch (e) {
	}
	document.formProntuario.fireEvent('listarFaturaContrato');

	document.getElementById('tdEmCriacao').style.backgroundColor = 'white';
	document.getElementById('tdAguardandoAprovacao').style.backgroundColor = 'white';
	document.getElementById('tdAprovadas').style.backgroundColor = 'white';
	document.getElementById('tdAguardHomologacao').style.backgroundColor = 'white';
	document.getElementById('tdHomologadas').style.backgroundColor = 'white';
	document.getElementById('tdRejeitadas').style.backgroundColor = 'white';
	document.getElementById('tdCanceladas').style.backgroundColor = 'white';
	document.getElementById('tdTodas').style.backgroundColor = 'white';

	objTd.style.backgroundColor = '#E2E2E2';
}

callBackFuncOS = function() {
	if (ajaxAction.req.readyState == 4) {
		if (ajaxAction.req.status == 200) {
			document.getElementById('areaUtilContrato').innerHTML = ajaxAction.req.responseText;

			ajustaTamanhoTela();

			DEFINEALLPAGES_processaLoadSubPage(urlChamada,
					ajaxAction.req.responseText);
			DEFINEALLPAGES_atribuiCaracteristicasCitAjax();

			setDatePicker('dataInicioOS');
			setDatePicker('dataFimOS');
		}
		if (ajaxAction.req.status == 403) {
			document.getElementById('areaUtilContrato').innerHTML = '';
			alert('Acesso bloqueado ao recurso!');
		}
	}
};
callBackFuncFaturas = function() {
	if (ajaxAction.req.readyState == 4) {
		if (ajaxAction.req.status == 200) {
			document.getElementById('areaUtilContrato').innerHTML = ajaxAction.req.responseText;

			ajustaTamanhoTela();

			DEFINEALLPAGES_processaLoadSubPage(urlChamada,
					ajaxAction.req.responseText);
			DEFINEALLPAGES_atribuiCaracteristicasCitAjax();

			setDatePicker('dataInicioOS');
			setDatePicker('dataFimOS');
		}
		if (ajaxAction.req.status == 403) {
			document.getElementById('areaUtilContrato').innerHTML = '';
			alert('Acesso bloqueado ao recurso!');
		}
	}
};
function POPUP_CADASTRO_ALTERACAO_onmaximize() {
	var w = NumberUtil
			.apenasNumeros(document
					.getElementById('divIntJanelaPopup_POPUP_CADASTRO_ALTERACAO').style.width);
	var h = NumberUtil
			.apenasNumeros(document
					.getElementById('divIntJanelaPopup_POPUP_CADASTRO_ALTERACAO').style.height);
	var wInt = NumberUtil.toInteger(w);
	var hInt = NumberUtil.toInteger(h);
	wInt = wInt - 10;
	hInt = hInt - 60;
	document.getElementById('frameCadastro').width = wInt + '';
	document.getElementById('frameCadastro').height = hInt + '';
}
function POPUP_CADASTRO_ALTERACAO_onminimize() {
	var w = NumberUtil
			.apenasNumeros(document
					.getElementById('divIntJanelaPopup_POPUP_CADASTRO_ALTERACAO').style.width);
	var h = NumberUtil
			.apenasNumeros(document
					.getElementById('divIntJanelaPopup_POPUP_CADASTRO_ALTERACAO').style.height);
	var wInt = NumberUtil.toInteger(w);
	var hInt = NumberUtil.toInteger(h);
	wInt = wInt - 10;
	hInt = hInt - 60;
	document.getElementById('frameCadastro').width = wInt + '';
	document.getElementById('frameCadastro').height = hInt + '';
}
function POPUP_QUESTIONARIO2_onmaximize() {
	document.getElementById('divQuestionario2').style.width = '100%';
	document.getElementById('divQuestionario2').style.height = '100%';
	document.getElementById('tblQuestionario2').style.width = '100%';
	document.getElementById('tblQuestionario2').style.height = '100%';
	document.getElementById('tdFrameQuestionario2').style.width = '100%';
	document.getElementById('tdFrameQuestionario2').style.height = '100%';

	var w = NumberUtil
			.apenasNumeros(document
					.getElementById('divIntJanelaPopup_POPUP_QUESTIONARIO2').style.width);
	var h = NumberUtil
			.apenasNumeros(document
					.getElementById('divIntJanelaPopup_POPUP_QUESTIONARIO2').style.height);

	var wInt = NumberUtil.toInteger(w);
	var hInt = NumberUtil.toInteger(h);

	wInt = wInt - 10;
	hInt = hInt - 80;

	document.getElementById('frameQuestionario2').width = wInt + '';
	document.getElementById('frameQuestionario2').height = hInt + '';
}
function POPUP_QUESTIONARIO2_onminimize() {
	document.getElementById('divQuestionario2').style.width = '100%';
	document.getElementById('divQuestionario2').style.height = '100%';
	document.getElementById('tblQuestionario2').style.width = '100%';
	document.getElementById('tblQuestionario2').style.height = '100%';
	document.getElementById('tdFrameQuestionario2').style.width = '100%';
	document.getElementById('tdFrameQuestionario2').style.height = '100%';

	var w = NumberUtil
			.apenasNumeros(document
					.getElementById('divIntJanelaPopup_POPUP_QUESTIONARIO2').style.width);
	var h = NumberUtil
			.apenasNumeros(document
					.getElementById('divIntJanelaPopup_POPUP_QUESTIONARIO2').style.height);

	var wInt = NumberUtil.toInteger(w);
	var hInt = NumberUtil.toInteger(h);

	wInt = wInt - 10;
	hInt = hInt - 80;

	document.getElementById('frameQuestionario2').width = wInt + '';
	document.getElementById('frameQuestionario2').height = hInt + '';
}

function POPUP_QUESTIONARIO3_onmaximize() {
	document.getElementById('divQuestionario3').style.width = '100%';
	document.getElementById('divQuestionario3').style.height = '100%';
	document.getElementById('tblQuestionario3').style.width = '100%';
	document.getElementById('tblQuestionario3').style.height = '100%';
	document.getElementById('tdFrameQuestionario3').style.width = '100%';
	document.getElementById('tdFrameQuestionario3').style.height = '100%';

	var w = NumberUtil
			.apenasNumeros(document
					.getElementById('divIntJanelaPopup_POPUP_QUESTIONARIO3').style.width);
	var h = NumberUtil
			.apenasNumeros(document
					.getElementById('divIntJanelaPopup_POPUP_QUESTIONARIO3').style.height);

	var wInt = NumberUtil.toInteger(w);
	var hInt = NumberUtil.toInteger(h);

	wInt = wInt - 10;
	hInt = hInt - 60;

	document.getElementById('frameQuestionario3').width = wInt + '';
	document.getElementById('frameQuestionario3').height = hInt + '';
}
function POPUP_QUESTIONARIO3_onminimize() {
	document.getElementById('divQuestionario3').style.width = '100%';
	document.getElementById('divQuestionario3').style.height = '100%';
	document.getElementById('tblQuestionario3').style.width = '100%';
	document.getElementById('tblQuestionario3').style.height = '100%';
	document.getElementById('tdFrameQuestionario3').style.width = '100%';
	document.getElementById('tdFrameQuestionario3').style.height = '100%';

	var w = NumberUtil
			.apenasNumeros(document
					.getElementById('divIntJanelaPopup_POPUP_QUESTIONARIO3').style.width);
	var h = NumberUtil
			.apenasNumeros(document
					.getElementById('divIntJanelaPopup_POPUP_QUESTIONARIO3').style.height);

	var wInt = NumberUtil.toInteger(w);
	var hInt = NumberUtil.toInteger(h);

	wInt = wInt - 10;
	hInt = hInt - 60;

	document.getElementById('frameQuestionario3').width = wInt + '';
	document.getElementById('frameQuestionario3').height = hInt + '';
}