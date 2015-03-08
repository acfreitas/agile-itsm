var locale_format = locale === 'en' ? 'MM/dd/yyyy' : 'dd/MM/yyyy';

function i18n_message(lbl) {
	var l = bundle['key'][lbl];

	if (l === undefined) {
		l = lbl;
	}
	return l;
}

function internacionalizar(parametro){
	document.getElementById('locale').value = parametro;
	document.formInternacionaliza.fireEvent('internacionaliza');
}

/* Desenvolvedor: Euler.Ramos  Data: 23/10/2013 - Horário: 16h04min  ID Citsmart: 120393  Motivo/Comentário: Para evitar o erro: "...has no method fecharTelaAguarde" na tela de pesquisa da base de conhecimento. */
function fecharJanelaAguarde(){
	JANELA_AGUARDE_MENU.hide();
}

//imprime no console qualquer erro de javascript no sistema
onerror = handleErr;
function handleErr(msg, url, l) {
	var txt;
	txt += "Erro: " + msg + " - ";
	txt += "URL: " + url + " - ";
	txt += "Linha: " + l;
	console.log(txt);
	return true;
}

/* desenvolvedor: rcs (Rafael César Soyer)
 o método abaixo limpa todos os campos de uma tela
 data: 26/12/2014
*/
function limpar() {
	document.form.clear();
}

function validaData(){
	var dataInicio = document.getElementById("dataInicio").value;
	var dataFim	= document.getElementById("dataFim").value;

	if (typeof(locale) === "undefined") locale = '';

	var dtInicio = new Date();
	var dtFim = new Date();

	var dtInicioConvert = '';
	var dtFimConvert = '';
	var dtInicioSplit = dataInicio.split("/");
	var dtFimSplit = dataFim.split("/");

	if (locale == 'en') {
		dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[0] + "/" + dtInicioSplit[1];
		dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[0] + "/" + dtFimSplit[1];
	} else {
		dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[1] + "/" + dtInicioSplit[0];
		dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[1] + "/" + dtFimSplit[0];
	}

	dtInicio.setTime(Date.parse(dtInicioConvert)).setFullYear;
	dtFim.setTime(Date.parse(dtFimConvert)).setFullYear;

	if (dataInicio.trim() == ""){
		alert(i18n_message("citcorpore.comum.validacao.datainicio"));
		return false;
	} else if(DateTimeUtil.isValidDate(dataInicio) == false){
		alert(i18n_message("citcorpore.comum.datainvalida"));
		document.getElementById("dataInicio").value = '';
		return false;
	}

	if (dataFim.trim() == ""){
		alert(i18n_message("citcorpore.comum.validacao.datafim"));
		return false;
	} else if(DateTimeUtil.isValidDate(dataFim) == false){
		alert(i18n_message("citcorpore.comum.dataFinalInvalida"));
		document.getElementById("dataFim").value = '';
		return false;
	}

	if (dtInicio > dtFim){
		alert(i18n_message("citcorpore.comum.dataInicioMenorFinal"));
		return false;
	}
	return true;
}

/**
 * @author cristian.guedes
 * @param dataInicio
 * @param dataFim
 * @returns {Boolean}
 */
function validaDataParametrizado(dataInicio, dataFim){
	if (typeof(locale) === "undefined") locale = '';

	var dtInicio = new Date();
	var dtFim = new Date();

	var dtInicioConvert = '';
	var dtFimConvert = '';
	var dtInicioSplit = dataInicio.split("/");
	var dtFimSplit = dataFim.split("/");

	if (locale == 'en') {
		dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[0] + "/" + dtInicioSplit[1];
		dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[0] + "/" + dtFimSplit[1];
	} else {
		dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[1] + "/" + dtInicioSplit[0];
		dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[1] + "/" + dtFimSplit[0];
	}

	dtInicio.setTime(Date.parse(dtInicioConvert)).setFullYear;
	dtFim.setTime(Date.parse(dtFimConvert)).setFullYear;

	if (dataInicio.trim() == ""){
		alert(i18n_message("citcorpore.comum.validacao.datainicio"));
		return false;
	} else if(DateTimeUtil.isValidDate(dataInicio) == false){
		alert(i18n_message("citcorpore.comum.datainvalida"));
		document.getElementById("dataInicio").value = '';
		return false;
	}

	if (dataFim.trim() == ""){
		alert(i18n_message("citcorpore.comum.validacao.datafim"));
		return false;
	} else if(DateTimeUtil.isValidDate(dataFim) == false){
		alert(i18n_message("citcorpore.comum.dataFinalInvalida"));
		document.getElementById("dataFim").value = '';
		return false;
	}

	if (dtInicio > dtFim){
		alert(i18n_message("citcorpore.comum.dataInicioMenorFinal"));
		return false;
	}
	return true;
}

function imprimirRelatorioPeloNomeDoRelatorio(str_nomeDoRelatorio){
	if (validaData()) {
		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent(str_nomeDoRelatorio);
	}
}

function imprimirRelatorio(str_tipoDoRelatorio){
	JANELA_AGUARDE_MENU.show();
	document.form.formatoArquivoRelatorio.value = str_tipoDoRelatorio;
	document.form.fireEvent("imprimirRelatorio");
}

function imprimirRelatorioPdf() {
	var tipoDoRelatorio = "pdf"

	if (validaData()) {
		imprimirRelatorio(tipoDoRelatorio);
	}
}

function imprimirRelatorioXls() {
	var tipoDoRelatorio = "xls"

	if (validaData()) {
		imprimirRelatorio(tipoDoRelatorio);
	}
}

function reportEmpty(){
	alert(i18n_message("citcorpore.comum.relatorioVazio"));
}
