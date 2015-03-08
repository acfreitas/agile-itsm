function tamanhoCampo(obj, limit) {
		if (obj.value.length >= limit) {
			obj.value = obj.value.substring(0, limit-1);
		}
	}

function limpar(){
	document.getElementById('divByCustoTotal').style.display = 'block';
	//document.getElementById('divByCustoTotal2').style.display = 'block';
	document.getElementById('divByCustoFormula').style.display = 'none';
	document.getElementById('divByCustoFormula2').style.display = 'none';
	document.getElementById('CONTABILIZAR').value = "N";
	document.getElementById('tipoCusto').value = "C";
	document.getElementById('divComboServicoContrato').style.display = 'none';
	document.getElementById('addServicoContrato').value = "";
	document.getElementById('idServicoContratoContabil').value = "";
}

var objTab = null;

addEvent(window, "load", load, false);
function load() {
	setTimeout(function(){
		if (properties.idServicoContrato != '' && properties.idServicoContrato != '') {
			document.formInterno.fireEvent("recupera");
		}
	}, 1000);
	$("#POPUP_SERVICOCONTRATO").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});

}	$("#POPUP_SERVICOCONTRATO").dialog({
	autoOpen : false,
	width : 600,
	height : 400,
	modal : true
});
function excluir() {
	if (document.getElementById("idAtividadeServicoContrato").value != "") {
		if (confirm(i18n_message("citcorpore.comum.deleta"))) {
			document.formInterno.fireEvent("delete");
		}
	}
}

function closePopup(idServicoContrato) {
	parent.fecharVisaoPainel(idServicoContrato,"AtividadesServico");
}

/* 	function SomenteNumero(e){
    var tecla=(window.event)event.keyCode:e.which;
    if((tecla>47 && tecla<58)) return true;
    else{
    	if (tecla==8 || tecla==0) return true;
	else  return false;
    }
}

function validarInputsNumeros(obj){
	if(!SomenteNumero(obj)){
		alert("digite apens numeros");
		obj.value = "";
	}
} */
function gravar(){
	var descricaoAtividade = document.getElementById('descricaoAtividade').value;


	var complexidade = $("#complexidade").val();
	var complexidadeMontada = $("#vComplexidade").val();
	var complexidadeValorTotal = document.getElementById('complexidadeCustoTotal').value;
	document.getElementById("custoAtividade").value = document.getElementById("custoAtividade").value.replace(",", ".");


	if(descricaoAtividade == ""){
		alert(i18n_message("atividadeServico.faltaDescricaoAtividade"));
		return;
	}

	var tipoFormulaValidar = document.getElementById('tipoCusto').value;

	if(tipoFormulaValidar == "C"){
		$("#complexidade").val(complexidadeValorTotal);
		validaCustoAtividade();
		return;
	}
	if(tipoFormulaValidar == "F"){
		document.getElementById('complexidade').value = complexidadeMontada;
		var formulaFinal = montarFormulaFinal();
		if(formulaFinal != false){
			if(document.getElementById('Formulas').value == '-1'){
				alert("Favor escolher a fórmula");
				return;
			}
			document.getElementById('formula').value = formulaFinal;
			document.formInterno.fireEvent("save");
		}else{
			alert(i18n_message("atividadeServico.selecionaUmaFormula"));
			return;
		}

	}
}

function montarFormulaFinal(){
	var estrututaFormula = document.getElementById('estruturaFormulaOs').value;
	var totalValor = $(".vValor").length;
	var totalComplexidade = $(".vComplexidade").length;
	var formulaFinal = "";
	var formulaAuxiliar = estrututaFormula;

	var arrayValores = new Array();
	if(totalValor!=0){
		for(i = 0;i<totalValor;i++){
			var obj = $(".vValor")[i];
			if(obj.value==""){
				alert(i18n_message("formula.ExistemCamposEmBranco"));
				return false;
			}else{
				arrayValores[i] = obj.value;
			}

		}
	}

	if(arrayValores.length!=0){
		var aux1 = "";
		var aux2 = "";//variavel que irá armazenar a formula final sem os input
		var aux3 = formulaAuxiliar;

		for(i = 0;i<arrayValores.length;i++){
			aux1 = "";
			for(j = 0; j<aux3.length;j++){
				aux1 += aux3[j];
				if(aux1.indexOf("vValor")!=-1)
					break;
			}
			aux3 = aux3.replace(aux1,"");
			aux2 += aux1.replace("vValor",arrayValores[i]);
			if((i+1==arrayValores.length) && aux3!=""){
				aux2 += aux3;
			}
		}
		formulaAuxiliar = aux2;
	}


	var arrayComplexidade = new Array();
	if(totalComplexidade!=0){
		for(i = 0;i<totalComplexidade;i++){
			var obj = $(".vComplexidade")[i];
			if(obj.value==""){
				alert(i18n_message("formula.ExistemCamposEmBranco"));
				return false;
			}else{
				arrayComplexidade[i] = obj.value;
			}
		}
	}


	if(arrayComplexidade.length!=0){
		var aux1 = "";
		var aux2 = "";//variavel que irá armazenar a formula final sem os input
		var aux3 = formulaAuxiliar;

		for(i = 0;i<arrayComplexidade.length;i++){
			aux1 = "";
			for(j = 0; j<aux3.length;j++){
				aux1 += aux3[j];
				if(aux1.indexOf("vComplexidade")!=-1)
					break;
			}
			aux3 = aux3.replace(aux1,"");
			aux2 += aux1.replace("vComplexidade",arrayComplexidade[i]);
			if((i+1==arrayComplexidade.length) && aux3!=""){
				aux2 += aux3;
			}
		}
		formulaAuxiliar = aux2;
	}





	var str = formulaAuxiliar;//remover as chaves da string
	while(str.indexOf("{")!=-1 || str.indexOf("}")!=-1){
		str = str.replace("{", "  ");
		str = str.replace("}", "  ");
	}

	/*
	*thays.araujo 05/05/2014 16:39
	*retirar variaveis da formula
	*/
	if(str !=null){
		str = replaceAll("vDiasCorridos","Dias Corridos",str);
		str = replaceAll("vDiasUteis","Dias Úteis",str);
		str = replaceAll("vValor","Valor",str);
		str = replaceAll("vNumeroUsuarios" , "Número Usuários",str);
	}
	var strAux = formulaAuxiliar;
	var index1 = 0;
	var index2 = 0;
	var strPedaco = "";

	for(i = 0;i<formulaAuxiliar.length;i++){//remover labels, salva a formula no formato para o calculo final
			strPedaco = "";
			if(formulaAuxiliar[i].indexOf("{")!=-1){
				index1 = i;
				for(j = i;j<formulaAuxiliar.length;j++){
					if(formulaAuxiliar[j].indexOf("}")!=-1){
						index2 = j+1;
						break;
					}
				}
				for(j = index1;j<index2;j++){
					strPedaco += formulaAuxiliar[j];
				}

				strAux = strAux.replace(strPedaco,"");
			}
	}
	formulaFinal = strAux;
	document.getElementById('formulaCalculoFinal').value = formulaFinal;

	return str;
}

var chaves = new RegExp("\{[0-9|a-z|A-Z]+}", "g");

function validaCustoAtividade(){
	var custo = document.getElementById('custoAtividade').value;
	if(custo == ""){
		alert(i18n_message("atividadeServico.custoNaoInformado"));
		return false;
	}else{
		document.formInterno.fireEvent("save");
		return true;
	}
}

function avaliaTipoCusto(){
	document.getElementById('divByCustoTotal').style.display = 'none';
	document.getElementById('divByCustoFormula').style.display = 'none';
	document.getElementById('divByCustoFormula2').style.display = 'none';
	if (document.formInterno.tipoCusto.value == 'C'){
		document.getElementById('divByCustoTotal').style.display = 'block';
		limparFormula();
	}
	if (document.formInterno.tipoCusto.value == 'F'){
		document.getElementById('divByCustoFormula').style.display = 'block';
		mostrarComboFormula();
		limparCusto();
	}
}

function avaliaContabil(){
	var contabilizar = document.formInterno.CONTABILIZAR.value;
	if(contabilizar == 'S'){
		document.getElementById('divComboServicoContrato').style.display = 'block';
	}else{
		document.getElementById('idServicoContratoContabil').value = "";
		document.getElementById('addServicoContrato').value = '';
		document.getElementById('divComboServicoContrato').style.display = 'none';
	}
}

function limparFormula(){
	//document.getElementById('hora').value = "";
	document.getElementById('complexidade').value = "";
//	document.getElementById('quantidade').value = "";
//	document.getElementById('periodo').value = "";
	document.getElementById('formula').value = "";
}

function limparCusto(){
	document.getElementById('custoAtividade').value = "";
}

function setaValorServicoContrato(){
	var servicoContrato = document.getElementById('idServico').value;
	document.getElementById('idServicoContratoContabil').value = servicoContrato;
}

$(function() {
	$("#addServicoContrato").click(function() {
		var idContrato = document.formInterno.idContrato.value;
		document.formPesquisaLocalidade.pesqLockupLOOKUP_SERVICOCONTRATO_IDCONTRATO.value = idContrato;
		$("#POPUP_SERVICOCONTRATO").dialog("open");
	});
});

function LOOKUP_SERVICOCONTRATO_select(id, desc){
	document.formInterno.idServicoContratoContabil.value = id;
	document.formInterno.fireEvent("restoreAtividadeServico");
}

function fecharPopup(){
	$("#POPUP_SERVICOCONTRATO").dialog("close");
}

function mudarComplexidade(){
	$("#complexidadeCustoTotal option:first").attr('selected','selected');
	$("#complexidade option:first").attr('selected','selected');
}

function mostrarComboFormula(){
	document.formInterno.fireEvent("ListarFormulas");
}

function mostrarFormula(){
	$("#divByCustoFormula2").empty();
	var valor = $("#Formulas").val();
	if(valor!="-1"){
		$("#idFormulaOs").val(valor);
		document.formInterno.fireEvent("restoreFormula");
	}
}
function montarFormula(strFormula){
	$("#divByCustoFormula2").show();
	$("#divByCustoFormula2").append(strFormula);
}
function setEstruturaFormulaOs(estrutura){
	$("#estruturaFormulaOs").empty();
	$("#estruturaFormulaOs").val(estrutura);
}
function colocarValorNaFormula(valor){
	var arraySplit = valor.split(";");
	var cont = 0;
	var arrayFormula = $(".objFormula");
	for(i = 0;i<arrayFormula.length;i++){
		var obj = $(".objFormula")[i];
		if(arraySplit[cont]=="B" ||arraySplit[cont]=="I" ||arraySplit[cont]=="M" ||arraySplit[cont]=="A" ||arraySplit[cont]=="E"){
			obj.value = arraySplit[cont];
		}else{
			obj.value = arraySplit[cont];
		}
		cont++;
	}
}

function replaceAll(find, replace, str) {
	  return str.replace(new RegExp(find, 'g'), replace);
}
function addClassMoedaInput(){
	var vValorTamanho = $(".vValor").length;
	$(".vValor");
	for(i = 0;i<vValorTamanho;i++){
			var obj = $(".vValor")[i];
			addEvent(obj, "keydown", DEFINEALLPAGES_formataMoeda, false);
		}

}

function selecionarFormula(valor){
$('#Formulas option').each(function(){
          if($(this).val() == valor){
              $(this).attr('selected',true);
          }
      });
}


// Cristian: a propriedade maxlength não existe para o objeto TEXTAREA. Mas com esta função, você pode atribuir esta característica a este objeto.
window.onload = function() {
	  var txts = document.getElementsByTagName('TEXTAREA')

	  for(var i = 0, l = txts.length; i < l; i++) {
	    if(/^[0-9]+$/.test(txts[i].getAttribute("maxlength"))) {
	      var func = function() {
	        var len = parseInt(this.getAttribute("maxlength"), 10);

	        if(this.value.length > len) {
	          alert('Tamanho máximo excedido: ' + len);
	          this.value = this.value.substr(0, len);
	          return false;
	        }
	      }

	      txts[i].onkeyup = func;
	      txts[i].onblur = func;
	    }
	  }
	}