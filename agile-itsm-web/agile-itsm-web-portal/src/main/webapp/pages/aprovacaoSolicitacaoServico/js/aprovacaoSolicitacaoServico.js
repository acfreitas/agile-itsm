/**
 * 
 */
addEvent(window, "load", load, false);
function load(){        
    document.form.afterLoad = function () {
    	parent.escondeJanelaAguarde(); 
    }    
}        
function validar() {
    return document.form.validate();
}

function getObjetoSerializado() {
    var obj = new CIT_AprovacaoSolicitacaoServicoDTO();
    HTMLUtils.setValuesObject(document.form, obj);
    return ObjectUtils.serializeObject(obj);
}

function configuraJustificativa(aprovacao) {
    document.getElementById('divJustificativa').style.display = 'none';
    if (aprovacao == 'N')
    	document.getElementById('divJustificativa').style.display = 'block';
}