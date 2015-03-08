/**
 * @author david.silva
 *
 */

addEvent(window, "load", load, false);
function load() {
	mascaraCPF();
	$('#conteudoItemHistorico').html('<iframe id="frameItemHistorico" src="about:blank" width="100%" height="455" style="border: 0px none;"></iframe>');
	$('#conteudoBlackList').html('<iframe id="frameAddBlackList" src="about:blank" width="100%" height="455" style="border: 0px none;"></iframe>');
	$('#conteudoVisualizarHistorico').html('<iframe id="frameVisualizarHistorico" src="about:blank" width="100%" height="455" style="border: 0px none;"></iframe>');
}

/** Bot�o Pesquisar**/
function pesquisar(){
	JANELA_AGUARDE_MENU.show();
	document.form.fireEvent("montarTabelaRetorno");
//	document.form.clear();
}

function paginarItens(paginaSelecionada) {
	document.form.paginaSelecionada.value = paginaSelecionada;
	document.form.fireEvent("montarTabelaRetorno");
}

function testaCheck(){

	//declara��o de vars. A var checados ir� contar quantos est�o checados.
	var inputs,i,checados=0;

	//pegando os inputs e jogando num array
	inputs = document.getElementsByTagName("input");


	//varrendo o array que tem os inputs
	for(i=0;i<inputs.length;i++){

		if(inputs[i].type=="checkbox"){ //se os inputs forem checkbox
			if(inputs[i].checked==true){
				checados++;

			}

		}

	}

	if(checados>1){
	  alert("S� Pode Ter Um Item Selecionado.");
	  return false;
	}
}


/** Mais Filtros **/
function checarSituacao(){
	JANELA_AGUARDE_MENU.show();

	testaCheck();

	if(document.getElementById("chkColaborador").checked){
		$('#tipo').val("C");
		document.getElementById("chkColaborador").checked = true;
		document.getElementById("chkExColaborador").checked = false;
		document.getElementById("chkColaboradorAfastado").checked = false;
		document.getElementById("chkCandidatoExterno").checked = false;
		document.getElementById("chkBlackList").checked = false;
	}
	else if(document.getElementById("chkExColaborador").checked){
		$('#tipo').val("E");
		document.getElementById("chkColaborador").checked = false;
		document.getElementById("chkExColaborador").checked = true;
		document.getElementById("chkColaboradorAfastado").checked = false;
		document.getElementById("chkCandidatoExterno").checked = false;
		document.getElementById("chkBlackList").checked = false;
	}
	else if(document.getElementById("chkColaboradorAfastado").checked){
		$('#tipo').val("A");
		document.getElementById("chkColaborador").checked = false;
		document.getElementById("chkExColaborador").checked = false;
		document.getElementById("chkColaboradorAfastado").checked = true;
		document.getElementById("chkCandidatoExterno").checked = false;
		document.getElementById("chkBlackList").checked = false;
	}
	else if(document.getElementById("chkCandidatoExterno").checked){
		$('#tipo').val("F");
		document.getElementById("chkColaborador").checked = false;
		document.getElementById("chkExColaborador").checked = false;
		document.getElementById("chkColaboradorAfastado").checked = false;
		document.getElementById("chkCandidatoExterno").checked = true;
		document.getElementById("chkBlackList").checked = false;
	}
	else if(document.getElementById("chkBlackList").checked){
		$('#chkBlackList').val("B");
		$('#chkListaNegra').val("B");
		document.getElementById("chkColaborador").checked = false;
		document.getElementById("chkExColaborador").checked = false;
		document.getElementById("chkColaboradorAfastado").checked = false;
		document.getElementById("chkCandidatoExterno").checked = false;
		document.getElementById("chkBlackList").checked = true;
	}

	document.form.fireEvent("montarTabelaRetorno");
	$('#chkBlackList').val("");
}

/** Adicionar Black List **/
function addBlackList(id){
	var url = URL_SISTEMA+'pages/blackList/blackList.load?noVoltar=true&idCandidato='+id;
	document.getElementById('frameAddBlackList').src = url;
	$('#modal_blackList').modal('show');
}

/** Remover Black List **/
function rmvBlackList(id){
	var url = URL_SISTEMA+'pages/blackList/blackList.load?noVoltar=true&idCandidato='+id;
	document.getElementById('frameAddBlackList').src = url;
	$('#modal_blackList').modal('show');
}

function atualizarTabela(){
	document.form.fireEvent("montarTabelaRetorno");
}

/** Visualizar Historico **/
function visualizarHistorico(idHistorico, idCandidato){
	var url = URL_SISTEMA+'pages/visualizarHistoricoFuncional/visualizarHistoricoFuncional.load?noVoltar=true&idHistoricoFuncional='+idHistorico+'&idCandidato='+idCandidato;
	document.getElementById('frameVisualizarHistorico').src = url;
	$('#modal_visualizarHistorico').modal('show');
}

/** Adicionar Item ao Historico **/
function addItemHistorico(id){
	var url = URL_SISTEMA+'pages/itemHistoricoFuncional/itemHistoricoFuncional.load?noVoltar=true&idHistoricoFuncional='+id;
	document.getElementById('frameItemHistorico').src = url;
	$('#modal_itemHistorico').modal('show');
}

/** Validar campos vazios **/
function camposVazio() {
	var valorNome = document.getElementById('nome').value;
	var valorCpf = document.getElementById('cpf').value;

	if (valorNome.length < 1 && valorCpf.length < 1){
		alert ("Informe Nome ou CPF");
		return true;
	}
	return false;
}

/** Validar CPF **/
function cpfValido() {
	var resultado = validaCPF(document.getElementById('cpf'),'');
	if (resultado == false) {
		document.getElementById.cpf.focus();
	}
}

function mascaraCPF(){
	if(LOCALE_SISTEMA=="en"){
		$('#cpf').unmask();
	}
	else{
		$("#cpf").mask("999.999.999-99");
	}
}

/** Metodo Valida��o de CPF **/
function validaCPF(field, label) {
	var cpf = field.value;

	if(StringUtils.isBlank(cpf)){
	     return true;
	}

	cpf = cpf.replace(".","");
	cpf = cpf.replace(".","");
	cpf = cpf.replace("-","");
	var erro = new String;
	if (cpf.length < 11) erro += label+i18n_message("rh.saoNecessariosDigitosVerificacaoCPF")+"\n\n";
	var nonNumbers = /\D/;
	if (nonNumbers.test(cpf)) erro += label+i18n_message("rh.verificacaoCPFSuportaApenasNumeros")+" \n\n";
	if (cpf == "00000000000" || cpf == "11111111111" || cpf == "22222222222" || cpf == "33333333333" || cpf == "44444444444" || cpf == "55555555555" || cpf == "66666666666" || cpf == "77777777777" || cpf == "88888888888" || cpf == "99999999999"){
		erro += label+i18n_message('citcorpore.validacao.numeroCPFInvalido');
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
		erro +=label+i18n_message("citcorpore.validacao.numeroCPFInvalido");
	}
	if (erro.length > 0){
		alert(erro);
		field.focus();
		return false;
	}
	return true;
};

function fecharModalItemHistorico(){
	$('#modal_itemHistorico').modal('hide');
}
function fecharModalAddBlackList(){
	$('#modal_blackList').modal('hide');
}
function fecharModalVisualizarHistorico(){
	$('#modal_visualizarHistorico').modal('hide');
}

function limpar(){
	document.form.clear();
	window.location.reload();
}

corLinhaItemListaNegra = function (row, obj){
	if(obj.constaListaNegra == 'S') {
		row.style.backgroundColor = '#BD362F',
	   row.style.color = 'white',
	   row.title =  i18n_message("rh.curriculoConstaListaNegra"),
	   row.className = 'titulo';
	}
}
