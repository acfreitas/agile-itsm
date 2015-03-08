function abreLookupCategoriaOcorrencia(){
	$('#modal_lookupCategoriaOcorrencia').modal('show');
}

function abreLookupOrigemOcorrencia(){
	$('#modal_lookupOrigemOcorrencia').modal('show');
}

function LOOKUP_CATEGORIA_OCORRENCIA_select(id, desc) {
	$('#idCategoriaOcorrencia').val(id);				
	$('#nomeCategoriaOcorrencia').val(desc);
	$('#modal_lookupCategoriaOcorrencia').modal('hide');
}

function LOOKUP_ORIGEM_OCORRENCIA_select(id, desc) {
	$('#idOrigemOcorrencia').val(id);				
	$('#nomeOrigemOcorrencia').val(desc);
	$('#modal_lookupOrigemOcorrencia').modal('hide');
}

function abrirModalCadastroCategoriaOcorrencia(){
	document.getElementById('frameCadastroCategoriaOcorrencia').src = URL_SISTEMA+'pages/categoriaOcorrencia/categoriaOcorrencia.load?iframe=true';
	$('#modal_cadastroCategoriaOcorrencia').modal('show');
}

function abrirModalCadastroOrigemOcorrencia(){
	document.getElementById('frameCadastroOrigemOcorrencia').src = URL_SISTEMA+'pages/origemOcorrencia/origemOcorrencia.load?iframe=true';

	$('#modal_cadastroOrigemOcorrencia').modal('show');
}

function gravarOcorrencia() {
	if ($("#idOcorrencia").val() != null && !$("#idOcorrencia").val("") ) {
		alert(i18n_message("gerenciaservico.suspensaosolicitacao.validacao.alteraregistroocorrencia") );
	} else {
		var chkNotifica = document.getElementById("checkNotificarSolicitante");
		if (chkNotifica.checked){
			document.getElementById("notificarSolicitante").value = "S";
		} else {
			document.getElementById("notificarSolicitante").value = "N";
		}
		
		var chkNotificaResp = document.getElementById("checkNotificarResponsavel");
		
		if (chkNotificaResp.checked){
			document.getElementById("notificarResponsavel").value = "S"; 
		} else {
			document.getElementById("notificarResponsavel").value = "N";
		}
		if(parent.popupIE != null && parent.popupIE != undefined)
			document.getElementById("isPortal").value = "true";
		document.formOcorrenciaSolicitacao.save();
	}	
}

function limparCamposOcorrencia(){
	$('#idCategoriaOcorrencia').val('');
	$('#idOrigemOcorrencia').val('');
	$('#nomeCategoriaOcorrencia').val('');
	$("#nomeOrigemOcorrencia").val('');
	$("#tempoGasto").val('');
	$("#descricao").val( '' );
	$("#ocorrencia").val( '' );
	
	if ($("#checkNotificarSolicitante").is(":disabled")) {
		$( "#checkNotificarSolicitante" ).prop( "checked", false );
	} else {
		$( "#checkNotificarSolicitante" ).prop( "checked", true );
	}
}

function somenteNumero(e){
    var tecla=(window.event)?event.keyCode:e.which;   
    if((tecla>47 && tecla<58)) return true;
    else{
    	if (tecla==8 || tecla==0) return true;
	else  return false;
    }
}

/*comentado segundo solicitação do cristian.guedes
 * function escapeBrTextArea(){
	
	var texto = $("#taInformacoesContato").val();
	texto = texto.replace(/\<br>/g,'\n');
	$("#taInformacoesContato").val(texto);
	
}*/

function escapeBrTextArea(){

	var texto = document.getElementById("taInformacoesContato").value;
	texto = texto.replace(/\<br>/g,'\n');
	document.getElementById("taInformacoesContato").value = texto;

}

/**
 * 
 */
function disabledBtnsCategoria(){
	
	$('#frameCadastroOcorrenciaSolicitacao').contents().find('.btn-disabilitado-categoria').attr('disabled', true);
	
	$($("#frameCadastroOcorrenciaSolicitacao").contents().find('.btn-disabilitado-categoria')[1]).removeAttr("onclick");
	
	document.getElementById("nomeCategoriaOcorrencia").onclick = '';
	
	document.getElementById("buscaCategoriaOcorrencia").onclick = '';
	
	document.getElementById("cadastraCategoriaOcorrencia").onclick = '';


}

/**
 * 
 */
function disabledBtnsOrigem(){
	
	$('#frameCadastroOcorrenciaSolicitacao').contents().find('.btn-disabilitado-origem').attr('disabled', true);
	
	$($("#frameCadastroOcorrenciaSolicitacao").contents().find('.btn-disabilitado-origem')[1]).removeAttr("onclick");
	
	document.getElementById("nomeOrigemOcorrencia").onclick = '';
	
	document.getElementById("buscaOrigemOcorrencia").onclick = '';
	
	document.getElementById("cadastraOrigemOcorrencia").onclick = '';



}

function preencheCampoCategoria(nomeCategoria, idCategoria){
	if(parent.popupIE !== null && parent.popupIE !== undefined){
		document.getElementById("nomeCategoriaOcorrencia").value = nomeCategoria;
		document.getElementById("idCategoriaOcorrencia").value = idCategoria;
		disabledBtnsCategoria();
	}
}

function preencheCampoOrigem(nomeOrigem, idOrigem){
	if(parent.popupIE !== null && parent.popupIE !== undefined){
		document.getElementById("nomeOrigemOcorrencia").value = nomeOrigem;
		document.getElementById("idOrigemOcorrencia").value = idOrigem;
		disabledBtnsOrigem();
	}
}

$(window).load(function(){
	$('#conteudoCadastroCategoriaOcorrencia').html('<iframe id="frameCadastroCategoriaOcorrencia" src="about:blank" width="99%" height="530" class="iframeSemBorda"></iframe>');
	$('#conteudoCadastroOrigemOcorrencia').html('<iframe id="frameCadastroOrigemOcorrencia" src="about:blank" width="99%" height="530" class="iframeSemBorda"></iframe>');
});


