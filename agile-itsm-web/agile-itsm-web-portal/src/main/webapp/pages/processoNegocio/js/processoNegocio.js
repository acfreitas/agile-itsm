var objTab = null;

addEvent(window, "load", load, false);

function load() {
	document.form.afterRestore = function() {
		$('.tabs').tabs('select', 0);
	};
}

function LOOKUP_PROCESSONEGOCIO_select(id, desc) {
	document.form.restore({
		idProcessoNegocio: id
	});
}

var seqAutoridade = '';
incluirAutoridade = function() {
    GRID_AUTORIDADES.addRow();
    seqAutoridade = NumberUtil.zerosAEsquerda(GRID_AUTORIDADES.getMaxIndex(),5);
    eval('document.form.idNivelAutoridade' + seqAutoridade + '.focus()');
}

exibeAutoridade = function(serializeAutoridade) {
    if (seqAutoridade != '') {
        if (!StringUtils.isBlank(serializeAutoridade)) {
            var obj = new CIT_ProcessoNivelAutoridadeDTO();
            obj = ObjectUtils.deserializeObject(serializeAutoridade);
            try{
                eval('HTMLUtils.setValue("idNivelAutoridade' + seqAutoridade + '",' + obj.idNivelAutoridade + ')');
            }catch(e){
            }
            eval('document.form.permiteAprovacaoPropria' + seqAutoridade + '.value = "' + obj.permiteAprovacaoPropria + '"');
            eval('document.form.antecedenciaMinimaAprovacao' + seqAutoridade + '.value = "' + obj.antecedenciaMinimaAprovacao + '"');
        }
    }
}

getAutoridade = function(seq) {
    var obj = new CIT_ProcessoNivelAutoridadeDTO();

    seqAutoridade = NumberUtil.zerosAEsquerda(seq,5);
    obj.sequencia = seq;
    obj.idNivelAutoridade = parseInt(eval('document.form.idNivelAutoridade' + seqAutoridade + '.value'));
    obj.permiteAprovacaoPropria = eval('document.form.permiteAprovacaoPropria' + seqAutoridade + '.value');
    try{
        obj.antecedenciaMinimaAprovacao = eval('document.form.antecedenciaMinimaAprovacao' + seqAutoridade + '.value');
    }catch(e){
    	obj.antecedenciaMinimaAprovacao = 0;
    }
    return obj;
}

verificarAutoridade = function(seq) {
    var idNivelAutoridade = eval('document.form.idNivelAutoridade' + seq + '.value');
    var count = GRID_AUTORIDADES.getMaxIndex();
    for (var i = 1; i <= count; i++){
        if (parseInt(seq) != i) {
             var trObj = document.getElementById('GRID_AUTORIDADES_TD_' + NumberUtil.zerosAEsquerda(i,5));
             if (!trObj){
                continue;
             }
             var idAux = eval('document.form.idNivelAutoridade' + NumberUtil.zerosAEsquerda(i,5) + '.value');
             if (idAux == idNivelAutoridade) {
                  alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
                  eval('document.form.idNivelAutoridade' + seq + '.focus()');
                  return false;
             }
        }
    }
    return true;
}

function tratarAutoridades(){
    //try{
        var count = GRID_AUTORIDADES.getMaxIndex();
        var contadorAux = 0;
        var objs = new Array();
        for (var i = 1; i <= count; i++){
            var trObj = document.getElementById('GRID_AUTORIDADES_TD_' + NumberUtil.zerosAEsquerda(i,5));
            if (!trObj){
                continue;
            }

            var obj = getAutoridade(i);
            if (parseInt(obj.idNivelAutoridade) > 0) {
                if  (!verificarAutoridade(NumberUtil.zerosAEsquerda(i,5))) {
                    return false;
                }
                objs[contadorAux] = obj;
                contadorAux = contadorAux + 1;
            }else{
                alert(i18n_message("citcorpore.comum.nenhumaSelecao"));
                eval('document.form.idNivelAutoridade' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');
                return false;
            }
        }
        document.form.colAutoridades_Serialize.value = ObjectUtils.serializeObjects(objs);
        return true;
    //}catch(e){
    //}
}

function gravar() {
    if (!tratarAutoridades()){
        return;
    }

    document.form.save();
}

function clearAllCheckBoxIdTipoFluxo(){
    if(document.form.idTipoFluxo != undefined && document.form.idTipoFluxo.length > 0){
        for(i = 0; i < document.form.idTipoFluxo.length; i++){
            document.form.idTipoFluxo[i].checked = false;
        }
    }
}

function selectCheckBoxIdTipoFluxo(value){
    if(document.form.idTipoFluxo != undefined && document.form.idTipoFluxo.length > 0){
        for(i = 0; i < document.form.idTipoFluxo.length; i++){
            if(document.form.idTipoFluxo[i].value == value){
                document.form.idTipoFluxo[i].checked = true;
            }
        }
    }
}