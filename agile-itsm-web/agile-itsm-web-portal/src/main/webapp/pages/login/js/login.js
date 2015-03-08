(function(a) {
	a(window)
			.bind(
					"load",
					function() {
						if (a.browser.msie && a.browser.version < 10) {
						  var b='<div id="flv-back" class="flv-back"></div>' +
						  
								'<div id="flv-ie10" class="box-generic" style="margin: 0 auto;"><div class="row-fluid">' +
								'	<div class="span4">' +
								'		<h2 class="red">'+i18n_message("login.atualizeSeuNavegador")+'</h2>' +
								'		<span class="red">'+i18n_message("login.incompativelComNavegador")+'</span>' +
								'	</div>' +
								'	<div class="span3">' +
								'		<ul>' +
								'			<li>'+i18n_message("login.atualizaVantagem1")+'</li>' +
								'			<li>'+i18n_message("login.atualizaVantagem2")+'</li>' +
								'			<li>'+i18n_message("login.atualizaVantagem3")+'</li>' +
								'			<li>'+i18n_message("login.atualizaVantagem4")+'</li>' +
								'		</ul>' +
								'	</div>' +
								'	<div class="span5">' +
								'		<div class="row-fluid">' +
								'			<div class="span12">' +
								'				<div class="row-fluid">' +
								'					<div class="span4">' +
								'						<a target="_blank" href="http://windows.microsoft.com/pt-BR/internet-explorer/downloads/ie" class="navega">' +
								'							<img alt="" src="'+URL_INITIAL+'/novoLayout/common/include/images/ie.png">' +
								'							<span class="txt">Internet Explorer</span>' +
								'						</a>' +
								'					</div>' +
								'					<div class="span4">' +
								'						<a target="_blank" href="http://br.mozdev.org/firefox/download/" class="navega">' +
								'							<img alt="" src="'+URL_INITIAL+'/novoLayout/common/include/images/firefox.png">' +
								'							<span class="txt">Firefox</span>' +
								'						</a>' +
								'					</div>' +
								'					<div class="span4">' +
								'						<a target="_blank" href="http://www.google.com/chrome?hl=pt-BR" class="navega">' +
								'							<img alt="" src="'+URL_INITIAL+'/novoLayout/common/include/images/chrome.png">' +
								'							<span class="txt">Chrome</span>' +
								'						</a>' +
								'					</div>' +
								'				</div>' +
								'			</div>' +
								'		</div>' +
								'	</div>' +
								'</div>' +
							'</div>';
						
							function c(b) {
								var c = a(window), d = c.height(), e = c
										.width(), f = c.scrollTop(), g = c
										.scrollLeft(), h = parseFloat(a("body")
										.css("margin-top")), i = parseFloat(a(
										"body").css("margin-left"));
								a("body").css({
									height : d - h - 20,
									width : e - i - 20
								}), a("#flv-back").css({
									height : d + f,
									width : e + g
								}), a("#flv-ie10").css({
									top : a("body").height() / 2 + h + f
								})
							}
							a("body").css({
								overflow : "hidden",
								display : "block"
							}).append(b);
						}
					})
}(jQuery))

addEvent(window, "load", load, false);

function load() {

	if (window.location != window.top.location) {
		window.top.location = window.location;
	}

	$("#user").focus();
}

/*
 * ERRO com a tela nova de login
 * 
 * function abrirPopupAlteracaoSenha() {
 * popupAlteracaoSenha.abrePopupParms("alteracaoSenha", ""); }
 */

validar = function() {
	if($.browser.msie && $.browser.version < 10) {
		fechar_aguarde();
	} else {
		aguarde();
		document.form.save();
		$("#user").focus();
	}
}

function logar() {
	window.location = URL_SISTEMA+"pages/index/index.load";
}

aguarde = function() {
	JANELA_AGUARDE_MENU.show();
}

fechar_aguarde = function() {
	JANELA_AGUARDE_MENU.hide();
}

/*function internacionalizar(parametro) {
	document.getElementById('locale').value = parametro;
	document.formInternacionaliza.fireEvent('internacionaliza');
	window.location.reload();
}*/

function encaminhaAosErrosDeScript() {
	document.form.submit();
	window.location = URL_SISTEMA + "pages/scripts/scripts.load?upgrade=sim";
}

function alterarSenha() {
	document.form.submit();
	window.location = URL_SISTEMA + "pages/alterarSenha/alterarSenha.load";
}

/*
 * function alterarSenha() { document.form.submit(); window.location =
 * URL_SISTEMA+"pages/alterarSenha/alterarSenha.load"; }
 */

function abrirPopupAlteracaoSenha() {
	$('#popupAlteracaoSenha').dialog('open');
}

function mostraMensagemInsercao(msg){
	document.getElementById('divInsercao').innerHTML = msg;
	$("#mensagem_insercao").modal("show");
}