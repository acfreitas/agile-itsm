var UploadUtils_id_iframe = '';
var UploadUtils_id_elemento = '';
var UploadUtils_Action_Anterior = '';

function $m(quem){ 
	return document.getElementById(quem)
}
function remove(quem){ 
	quem.parentNode.removeChild(quem);
}
function addEvent(obj, evType, fn){ 
	if (obj.addEventListener)        
		obj.addEventListener(evType, fn, true)    
	if (obj.attachEvent)        
		obj.attachEvent("on"+evType, fn)
}
function removeEvent( obj, type, fn ) {
	if ( obj.detachEvent ) {
	    obj.detachEvent( 'on'+type, fn );  
	} else {    
		obj.removeEventListener( type, fn, false ); 
	}
} 
/* Funcao que faz o upload */
function uploadFile(form, url_action, id_elemento_retorno, html_exibe_carregando, html_erro_http, id_iframe, id_campo_file){
	var erro=""; 
	
	/* Obtendo o nome do arquivo para tratamento no servlet. */
	var hiddenArquivo = $m('upFileNameHidden');
	var campoArquivo = $m(id_campo_file);
	var nomeArquivo = campoArquivo.value;
	hiddenArquivo.value = nomeArquivo;
	
	/* Thiago Fernandes - 29/10/2013 - 18:49 - Sol. 121468 - Mensagem pedindo para selecionar um arquivo caso n�o seja selecionado.  */
	if(nomeArquivo == ''){ 
		alert(i18n_message("uploadAgente.nenhum_arquivo_selecionado"));
		return;
	}
	
	if($m(id_elemento_retorno)==null){ 
		erro += "O elemento passado no como parametro nao existe na pagina.\n";
	} 
	if(erro.length>0) {   
		alert("Erro ao chamar a fun��o uploadFile:\n" + erro);  
		return; 
	} 
	if (id_iframe == null || id_iframe == ''){
		alert("Informe o id do iframe!");  
		return; 	
	}
	$m(id_elemento_retorno).style.display = 'block';
    //adicionando o evento ao carregar 
    var carregou = function() {   
    	form.action = UploadUtils_Action_Anterior; 
    	removeEvent($m(id_iframe),"load", carregou);
    	$m(id_elemento_retorno).style.display = 'none';
    } 
    addEvent($m(id_iframe),"load", carregou);
    //setando propriedades do form 
    
    UploadUtils_Action_Anterior = form.action;
    
    form.setAttribute("target",id_iframe);
    form.setAttribute("action",url_action);
    form.setAttribute("method","post"); 
    form.setAttribute("enctype","multipart/form-data"); 
    form.setAttribute("encoding","multipart/form-data"); 
    
    //submetendo 
    form.submit();  
    
    form.action = UploadUtils_Action_Anterior;
    
    //se for pra exibir alguma imagem ou texto enquanto carrega 
    if(html_exibe_carregando.length > 0){
    	$m(id_elemento_retorno ).innerHTML = html_exibe_carregando;
    } 
    
    /* Thiago Fernandes - 29/10/2013 - 18:49 - Sol. 121468 - Limpar campos ao clicar em adicionar um arquivo .  */
    if (id_iframe == 'fraUpload_uploadRequisicaoMudanca') {
    	document.getElementById('descUploadFile_uploadRequisicaoMudanca').value = '';
    }
    if (id_iframe == 'fraUpload_uploadAnexos') {
    	document.getElementById('descUploadFile_uploadAnexos').value = '';
    }
    if (id_iframe == 'fraUpload_uploadPlanoDeReversao') {
    	document.getElementById('descUploadFile_uploadPlanoDeReversao').value = '';
    	document.getElementById('versao_uploadPlanoDeReversao').value = '';
    }
    if (id_iframe == 'fraUpload_uploadAnexosdocsLegais') {
    	document.getElementById('descUploadFile_uploadAnexosdocsLegais').value = '';
    }
    if (id_iframe == 'fraUpload_uploadAnexosDocsGerais') {
    	document.getElementById('descUploadFile_uploadAnexosDocsGerais').value = '';
    }
    if (id_iframe == 'fraUpload_uploadRequisicaoProblema') {
    	document.getElementById('descUploadFile_uploadRequisicaoProblema').value = '';
    }
    
    campoArquivo.value = '';
    setTimeout( function(){uploadAnexos.refresh()},5000);
}