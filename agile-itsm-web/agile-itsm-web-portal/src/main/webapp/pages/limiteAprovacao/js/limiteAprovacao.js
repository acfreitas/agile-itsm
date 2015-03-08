var objTab = null;

addEvent(window, "load", load, false);

function load() {
	document.form.afterRestore = function() {
		$('.tabs').tabs('select', 0);
	};
}

function LOOKUP_LIMITEAPROVACAO_select(id, desc) {
	document.form.restore({
		idLimiteAprovacao: id
	});
}

var seqValor = '';
incluirValor = function() {
    GRID_VALORES.addRow();
    seqValor = NumberUtil.zerosAEsquerda(GRID_VALORES.getMaxIndex(),5);
    eval('document.form.tipoUtilizacao' + seqValor + '.focus()');
}

exibeValor = function(serializeValor) {
    if (seqValor != '') {
        if (!StringUtils.isBlank(serializeValor)) {
            var obj = new CIT_ValorLimiteAprovacaoDTO();
            obj = ObjectUtils.deserializeObject(serializeValor);
            eval('document.form.tipoUtilizacao' + seqValor + '.value = "' + obj.tipoUtilizacao + '"');
            eval('document.form.tipoLimite' + seqValor + '.value = "' + obj.tipoLimite + '"');
            eval('document.form.valorLimite' + seqValor + '.value = "' + obj.valorLimite + '"');
        }
    }
}

getValor = function(seq) {
    var obj = new CIT_ValorLimiteAprovacaoDTO();

    seqValor = NumberUtil.zerosAEsquerda(seq,5);
    obj.sequencia = seq;
    obj.tipoUtilizacao = eval('document.form.tipoUtilizacao' + seqValor + '.value');
    obj.tipoLimite = eval('document.form.tipoLimite' + seqValor + '.value');
    obj.valorLimite = eval('document.form.valorLimite' + seqValor + '.value');
    return obj;
}

verificarValor = function(seq) {
    return true;
}

function tratarValores(){
    //try{
        var count = GRID_VALORES.getMaxIndex();
        var contadorAux = 0;
        var objs = new Array();
        for (var i = 1; i <= count; i++){
            var trObj = document.getElementById('GRID_VALORES_TD_' + NumberUtil.zerosAEsquerda(i,5));
            if (!trObj){
                continue;
            }

            var obj = getValor(i);
            if  (!verificarValor(NumberUtil.zerosAEsquerda(i,5))) {
                return false;
            }
            objs[contadorAux] = obj;
            contadorAux = contadorAux + 1;
        }
        document.form.colValores_Serialize.value = ObjectUtils.serializeObjects(objs);
        return true;
    //}catch(e){
    //}
}

function gravar() {
    if (!tratarValores()){
        return;
    }

    document.form.save();
}

function clearAllCheckBoxIdProcessoNegocio(){
    if(document.form.idProcessoNegocio != undefined && document.form.idProcessoNegocio.length > 0){
        for(i = 0; i < document.form.idProcessoNegocio.length; i++){
            document.form.idProcessoNegocio[i].checked = false;
        }
    }
}

function selectCheckBoxIdProcessoNegocio(value){
    if(document.form.idProcessoNegocio != undefined && document.form.idProcessoNegocio.length > 0){
        for(i = 0; i < document.form.idProcessoNegocio.length; i++){
            if(document.form.idProcessoNegocio[i].value == value){
                document.form.idProcessoNegocio[i].checked = true;
            }
        }
    }
}

function clearAllCheckBoxIdNivelAutoridade(){
    if(document.form.idNivelAutoridade != undefined && document.form.idNivelAutoridade.length > 0){
        for(i = 0; i < document.form.idNivelAutoridade.length; i++){
            document.form.idNivelAutoridade[i].checked = false;
        }
    }
}

function selectCheckBoxIdNivelAutoridade(value){
    if(document.form.idNivelAutoridade != undefined && document.form.idNivelAutoridade.length > 0){
        for(i = 0; i < document.form.idNivelAutoridade.length; i++){
            if(document.form.idNivelAutoridade[i].value == value){
                document.form.idNivelAutoridade[i].checked = true;
            }
        }
    }
}