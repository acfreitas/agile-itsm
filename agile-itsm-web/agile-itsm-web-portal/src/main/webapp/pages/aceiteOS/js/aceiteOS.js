/**
 * 
 */

var objTab = null;

addEvent(window, "load", load, false);
function load(){
	document.form.afterRestore = function () {
		document.getElementById('tabTela').tabber.tabShow(0);
	}
}


document.form.onClear = function(){
	GRID_ITENS.deleteAllRows();
};


function LOOKUP_OS_select(id,desc){
	document.form.restore({idOS:id});
}
function gravarForm(){
	var count = GRID_ITENS.getMaxIndex();
	var existeErro = false;
	var contadorAux = 0;
	var objs = new Array();
	for (var i = 1; i <= count; i++){
		var trObj = document.getElementById('GRID_ITENS_TD_' + NumberUtil.zerosAEsquerda(i,5));
		if (!trObj){
			continue;
		}
		var quantidadeObj = document.getElementById('quantidade' + NumberUtil.zerosAEsquerda(i,5));
		var complexidadeObj = document.getElementById('complexidade' + NumberUtil.zerosAEsquerda(i,5));
		var demandaObj = document.getElementById('demanda' + NumberUtil.zerosAEsquerda(i,5));
		var objObj = document.getElementById('obs' + NumberUtil.zerosAEsquerda(i,5));
		trObj.bgColor = 'white';
		complexidadeObj.style.backgroundColor = 'white';
		quantidadeObj.style.backgroundColor = 'white';
		demandaObj.style.backgroundColor = 'white';		
		objObj.style.backgroundColor = 'white';		
		if (complexidadeObj.value == ''){
			trObj.bgColor = 'orange';
			complexidadeObj.style.backgroundColor = 'orange';
			quantidadeObj.style.backgroundColor = 'orange';
			demandaObj.style.backgroundColor = 'orange';		
			objObj.style.backgroundColor = 'orange';				
			alert('Informe a complexidade! Linha: ' + i);
			return;
		}
		if (demandaObj.value == ''){
			trObj.bgColor = 'orange';
			complexidadeObj.style.backgroundColor = 'orange';
			quantidadeObj.style.backgroundColor = 'orange';
			demandaObj.style.backgroundColor = 'orange';		
			objObj.style.backgroundColor = 'orange';				
			alert('Informe a demanda! Linha: ' + i);
			return;
		}
		if (quantidadeObj.value == ''){
			trObj.bgColor = 'orange';
			complexidadeObj.style.backgroundColor = 'orange';
			quantidadeObj.style.backgroundColor = 'orange';
			demandaObj.style.backgroundColor = 'orange';		
			objObj.style.backgroundColor = 'orange';				
			alert('Informe o custo! Linha: ' + i);
			return;
		}						
		var objItem = new CIT_DemandaDTO();
		objItem.complexidade = complexidadeObj.value;
		objItem.custoTotal = quantidadeObj.value;
		objItem.detalhamento = demandaObj.value;
		objItem.observacao = objObj.value;
		objs[contadorAux] = objItem;
		contadorAux = contadorAux + 1;
		
		if (permiteValorZeroAtv=='S'){
 			if (quantidadeObj.value == '' || quantidadeObj.value == '0,00' || quantidadeObj.value == '0'){
 				trObj.bgColor = 'orange';
				complexidadeObj.style.backgroundColor = 'orange';
 				quantidadeObj.style.backgroundColor = 'orange';
 				demandaObj.style.backgroundColor = 'orange';		
 				objObj.style.backgroundColor = 'orange';
 				alert('Falta definir custo da atividade ! Linha: ' + i);
 				existeErro = true;
 			}
		}
	}
	if (existeErro){
		return;
	}
	document.form.colItens_Serialize.value = ObjectUtils.serializeObjects(objs);
	document.form.save();
}	
var seqSelecionada = '';
function setaRestoreItem(complex, det, obs, custo){
	if (seqSelecionada != ''){
		eval('HTMLUtils.setValue(\'complexidade' + seqSelecionada + '\', \'' + complex + '\')');
		eval('document.form.demanda' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + det + '\')');
		eval('document.form.obs' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + obs + '\')');
		eval('document.form.quantidade' + seqSelecionada + '.value = "' + custo + '"');
	}
}	

function GRID_ITENS.deleteRowByImgRef(objImg){
	alert('Não é permitido a exclusão de itens!');
	return false;
}