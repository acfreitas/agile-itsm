addEvent(window, "load", load, false);
function load(){
	
}
function selecionarJustificativa(){
	document.form.fireEvent('getOrigemDestinoDados');
}

function gravar(acao){
//	var indice = $('#indiceTriagem').val();
	document.form.acao.value = acao;
//	document.form.idCurriculo.value = ;
	document.form.fireEvent('testaAcao');
	try {
		parent.atualizarGridPesquizaCurriculo();
	} catch (e){}
}

function contCaracteres(valor) {
    quant = 200;
    total = valor.length;
    if(total <= quant)
    {
        resto = quant - total;
        document.getElementById('cont').innerHTML = resto;
    }
    else
    {
        document.getElementById('complementoJustificativa').value = valor.substr(0,quant);
    }
}

function ocultaModal(){
	try{
		parent.fecharModal();
	} catch(e){}
}
