addEvent(window, "load", load, false);

var previousTabOrigem;
var previousTabDestino;

/**
 * Inicia a tela
 */
function load() {
	
	//Inicia a tela da forma padr�o
	$("#divHora").hide();
	$("#divPeriodo").show("slow");
	
	
	$('#agendarRotina').val("N");
    $("#executarPor").attr("disabled", true);  // unchecked
	$("#horaExecucao").attr("disabled", true); 
	$("#periodoHora").attr("disabled", true);
	
	$("#tabelaOrigem").on('focus', function () {
    	previousTabOrigem = this.value;
    }).change(function() {
    	
    	if(!validarGridVazia())
    		this.value = previousTabOrigem;
    	
    	previousTabOrigem = this.value;
    	
    });
    
    $("#tabelaDestino").on('focus', function () {
   		previousTabDestino = this.value;
    }).change(function() {
    	
    	if(!validarGridVazia())
    		this.value = previousTabDestino;
    	
    	previousTabDestino = this.value;
    	
    });
    
    $("#idExternalConnection").on('focus', function () {
    	document.form.valorDaConexaoAntiga.value = this.value;
    });
    
    $("#idExternalConnection").on('change', function () {
    	carregarConexoes();
    });
    
	//$("#uploadAnexos").click(function() {
	$("#file_uploadAnexos").click(function() {
		document.getElementById('file_uploadAnexos').accept = '.jar';
		//$("#file_uploadAnexos").accept = '.jar';
	});
    
	$('#horaExecucao').mask('99:99');

	mostrarDiv();
	alterarExecutarPor();
	
	
}

	
/**
 * Valida se a grid de importa��o est� vazia
 * @returns true: est� vazia || false: existem dados inseridos
 */
function validarGridVazia() {
	
	var count = gridImport.getMaxIndex();
	var dadosStrMatriz = '';
	var jsonAuxMatriz = '';
	var json_data;
	
    var contadorAux = 0;
    var objs = new Array();
    
    //Linha da grid
    var trObj;
    
    //Objeto
    var obj;
    
    for (var i = 1; i <= count; i++){
    	
    	trObj = document.getElementById('gridImport_TD_' + NumberUtil.zerosAEsquerda(i,5));
    	
        if (trObj){
        	alert(i18n_message("importarDados.naoPermiteAlterarExisteItensCadastrados"));
        	return false;
        }
	}
    
    return true;
    
}

function agendamentoParaRotina(){
	
	if($('#agendarRotina').attr('checked')){
		$('#agendarRotina').val("S");
	    $("#executarPor").attr("disabled", false); // checked
		$("#horaExecucao").attr("disabled", false);
		$("#periodoHora").attr("disabled", false);	

	}else{
		$('#agendarRotina').val("N");
	    $("#executarPor").attr("disabled", true);  // unchecked
		$("#horaExecucao").attr("disabled", true); 
		$("#periodoHora").attr("disabled", true); 
	}
}

function alterarExecutarPor(){
	
	var msg = $('#executarPor').val();
	
	if(msg == "P" || msg == "p"){
		$("#divHora").hide();
		$("#divPeriodo").show();
		
	} else if(msg == "H" || msg == "h") {
		$('#horaExecucao').unmask();
		$('#horaExecucao').mask('99:99');
		$("#divHora").show();
		$("#divPeriodo").hide();
		
	}
	
}

function validaHoras(obj) {
	
	var valor = obj.value;
	var hh = valor.substring(0, 2);
	var mm = valor.substring(3, 5);
	var hora = parseInt(hh);
	var min = parseInt(mm);
	if (hora < 0 || hora > 23) {
		alert(i18n_message("jornadaTrabalho.horaInvalida"));
		$('#periodoHora').val("");
		return false;
	}
	
	if (min < 0 || min > 59) {
		alert(i18n_message("jornadaTrabalho.minutoInvalido"));
		$('#periodoHora').val("");
		return false;
	}
	
}

function mostrarDiv(){
	
	if($("#importarPor option:selected").val() == "T"){
		
		$("#divImportarPorScriptOuJar").hide('slow');
		$("#divTabela").show('slow');
		$('#agendarRotina').val("N");

		var tabelaOrigem = $('#tabelaOrigem').val();
		var tabelaDestino = $('#tabelaDestino').val();
		
		if((tabelaOrigem == null && tabelaDestino == null) || (tabelaOrigem == "" && tabelaDestino == ""))
			carregarConexoes();
		
	} else {
		
		$("#divTabela").hide('slow');
		$("#divImportarPorScriptOuJar").show('slow');
	
		if($("#importarPor option:selected").val() == "E"){
			$('#divScriptConversao').hide('slow');
			$('#divScriptConversao').hide('slow');
			$('#divArquivo').show('slow');
		}else{
			if($("#importarPor option:selected").val() == "S"){
				$('#divScriptConversao').show('slow');
				$('#divArquivo').hide('slow');
			}
		}
	}
	
}

function carregarConexoes() {
	
	if($("#importarPor option:selected").val() == "T"){
		
		JANELA_AGUARDE_MENU.show();
		var tabela = $('#tabelaOrigem').val();
		
		if(tabela == null || tabela == "")
			document.form.fireEvent("carregarTabelas");
		else {
			document.form.fireEvent("validarAlteracaoDaConexao");
		}
	}
}

function LOOKUP_IMPORTARDADOS_select(id, desc) {
	JANELA_AGUARDE_MENU.show();
	document.form.restore({idImportarDados : id});
	$('.tabsbar a[href="#tab1-3"]').tab('show');
	
}

function salvar(){
	
	if($("#importarPor option:selected").val() == "T"){
		
		var tabela = $('#tabelaOrigem').val();
		
		if(tabela == ""){
			alert(i18n_message("importarDados.tabelaOrigemObrigatorio"));
			return;
		}
		
		tabela = $('#tabelaDestino').val();
		
		if(tabela == ""){
			alert(i18n_message("importarDados.tabelaDestinoObrigatorio"));
			return;
		}
		
		var count = gridImport.getMaxIndex();
		var dadosStrMatriz = '';
		var jsonAuxMatriz = '';
		var json_data;
		
        var contadorAux = 0;
        var objs = new Array();
        
        //Linha da grid
        var trObj;
        
        //Objeto
        var obj;
        
        for (var i = 1; i <= count; i++){
        	
        	trObj = document.getElementById('gridImport_TD_' + NumberUtil.zerosAEsquerda(i,5));
            if (!trObj)
                continue;
            
            obj = new Object();
            
            obj.camposTabelaOrigem = document.getElementById('camposTabelaOrigem' + NumberUtil.zerosAEsquerda(i,5)).value;
            obj.camposTabelaDestino = document.getElementById('camposTabelaDestino' + NumberUtil.zerosAEsquerda(i,5)).value;
            obj.script = document.getElementById('script' + NumberUtil.zerosAEsquerda(i,5)).value;
		
			json_data = JSON.stringify(obj);
			
			if (dadosStrMatriz != ''){
				dadosStrMatriz = dadosStrMatriz + ',';
			}
			dadosStrMatriz = dadosStrMatriz + json_data;					
		}
		
		if (dadosStrMatriz != '')
			jsonAuxMatriz = jsonAuxMatriz + '{"MATRIZ": [' + dadosStrMatriz + ']}';
		
		document.form.jsonMatriz.value = jsonAuxMatriz;
		
		$('#agendarRotina').val("N");
		$('#agendarRotina').attr('checked', false);
		
	}
	
	if($("#importarPor option:selected").val() == "S"){
			
		var text = $('textarea#script').val();
			
		if(text == ""){
			alert(i18n_message("importarDados.campoScriptObrigatorio"));
			return;
		}
		
	}
	
	if($("#agendarRotina").attr('checked')){
		
		var aux = $('#executarPor').val();
		if(aux == "P"){
			
			aux = $("#periodoHora").val();
			if(aux == ""){
				alert(i18n_message("importarDados.periodo"));
				return;
			}
			
		} else {
			
			aux = $("#horaExecucao").val();
			if(aux == ""){
				alert(i18n_message("importarDados.periodo"));
				return;
			}
			
		}
	}	
	
	document.form.save();
	
}

function executarRotina(){
	
	document.form.fireEvent("executarRotina");
	
}

function limparFormulario() {
	
	document.form.clear();
	mostrarDiv();
	document.form.fireEvent("load");
	alterarExecutarPor();
}

function numeros(){
	var tecla = event.keyCode;
	if (tecla >= 48 && tecla <= 57){
		return true;
	}
	else{
		return false;
	}
}

function Verificar(){
	var ctrl=window.event.ctrlKey;
	var tecla=window.event.keyCode;
		if (ctrl && tecla==67) {
			event.keyCode=0; event.returnValue=false;
		}
		if (ctrl && tecla==86) {
			event.keyCode=0; event.returnValue=false;
		}
}

function excluir() {
	if (document.getElementById("idImportarDados").value != "") {
		if (confirm(i18n_message("citcorpore.comum.deleta"))) {
			document.form.fireEvent("delete");
		}
	}
}

function adicionarLinhaGrid(){

	var tblOrigem = document.getElementById("tabelaOrigem").value;
	var tblDestino = document.getElementById("tabelaDestino").value;
	
	if(tblOrigem == null || tblOrigem == ""){
		alert(i18n_message("importarDados.tabelaOrigemObrigatorio"));
		return;
	}
	
	if(tblDestino == null || tblDestino == ""){
		alert(i18n_message("importarDados.tabelaDestinoObrigatorio"));
		return;
	}

	JANELA_AGUARDE_MENU.show();
	
	gridImport.addRow();

	document.form.seqGrid.value = NumberUtil.zerosAEsquerda(gridImport.getMaxIndex(),5);
	
	document.form.fireEvent('consultarCamposDaTabelaDestino');
	document.form.fireEvent('consultarCamposDaTabelaOrigem');
	
}


function executarRotina() {
	JANELA_AGUARDE_MENU.show();
	document.form.fireEvent("executarRotina");
}