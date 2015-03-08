$(document).ready(function() {
	$('li.dropdown').click(function() {
		$('.dropdown').removeClass('open');
		if ($(this).is('.open')) {
			$(this).removeClass('open');
		} else {
			$(this).addClass('open');
		}
	});
	$('html').off('click.dropdown.data-api');
	$('html').on('click.dropdown.data-api', function(e) {
		if(!$(e.target).parents('li').is('.open')) {
		$('.dropdown').removeClass('open');
		} else {
			e.stopPropagation();
		}
	});

	/** Luiz.borges 04/12/2013 #126174 - Adicionada linha  Corrige a sobreposição da dinamicView sobre o Menu.**/
	$('.layout-panel-south').css('z-index', '1');
});

jQuery(document).ready(function ($) {
	"use strict";
	$('.panel-body, .layout-body').perfectScrollbar();
});

function prepareStringJSON(json_data_geral){
	var ret = json_data_geral.replace(/[\\]/g, '\\\\').replace(/[\"]/g, '\\\"').replace(/[\/]/g, '\\/')
			.replace(/[\b]/g, '\\b').replace(/[\f]/g, '\\f').replace(/[\n]/g, '\\n')
			.replace(/[\r]/g, '\\r').replace(/[\t]/g, '\\t');
	return ret;
}

function abreFechaMaisMenos(obj,idObj){
	var n = obj.src.indexOf(ctx + '/imagens/mais.jpg');
	if (n > -1){
		document.getElementById(idObj).style.display='block';
		document.getElementById('img_' + idObj).src = ctx + '/imagens/menos.jpg';
	}else{
		document.getElementById(idObj).style.display='none';
		document.getElementById('img_' + idObj).src = ctx + '/imagens/mais.jpg';
	}
}

function cancelar() {
	try{
		parent.fecharVisao();
	}catch(e){}
}

function fecharSePOPUP() {
	try{
		parent.fecharVisao();
	}catch(e){}
}

function resize_iframe(){}

if (window.matchMedia("screen and (-ms-high-contrast: active), (-ms-high-contrast: none)").matches) {
	 document.documentElement.className += " " + "ie10";
}

$.fn.datebox.defaults.formatter = function(date) {
	var y = '' + date.getFullYear();
	var m = '' + (date.getMonth()+1);
	var d = '' + date.getDate();
	if (m.length < 2){
		m = '0' + m;
	}
	if (d.length < 2){
		d = '0' + d;
	}
	return d+'/'+m+'/'+y;
}

function excluir() {
	if (confirm(i18n_message("dinamicview.confirmaexclusao"))) {
		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent('delete');
	}
}

var acaoPesquisar = 'N';
function TABLE_SEARCH_CLICK(idVisao, acao, obj, action){
	if (acaoPesquisar == 'N'){
		alert(i18n_message("dinamicview.naoehpossivelpesquisar"));
		return;
	}
	document.form.dinamicViewsIdVisaoPesquisaSelecionada.value = idVisao;
	document.form.dinamicViewsAcaoPesquisaSelecionada.value = acao;
	var json_data = JSON.stringify(obj);
	document.form.dinamicViewsJson_data.value = json_data;
	document.form.fireEvent('tableSearchClick');
}

function executeTimeout(func,timeout){
	window.setTimeout(func,timeout);
}

function setDataTemp(key, data){
	document.form.dinamicViewsJson_tempData.value = data;
	document.form.keyControl.value = key;
	document.form.fireEvent('setDadosTemporarios');
}

function retiraApostrofe(str){
	var myRegExp = new RegExp("'", "g");
	var myResult = str.replace(myRegExp, "-");
	return myResult;
}

function enviaDados(urlParm, divParm, theForm){
	var dataToSend = $("#" + theForm.name).serialize();
	$.ajax({
		url : urlParm,
		type : 'post',
		data : dataToSend,
		dataType: 'html',
		beforeSend: function(){
			try{
				document.getElementById(divParm).innerHTML = i18n_message("citcorpore.comum.carregando") + '...';
			}catch(e){}
		},
		timeout: 3000,
		success: function(retorno){
			$('#' + divParm).html(retorno);
		},
		error: function(erro){
			$('#' + divParm).html(erro);
		}
	});
}
