    carregouIFrameAnexo = function() {    
    	//POPUP_ANEXO.hide();
		refreshFuncionalidade();
		HTMLUtils.removeEvent(document.getElementById("frameUploadAnexo"),"load", carregouIFrameAnexo);
    };
    
	submitFormAnexo = function(){
		HTMLUtils.addEvent(document.getElementById("frameUploadAnexo"),"load", carregouIFrameAnexo, true);
		
	    document.formAnexo.setAttribute("target","frameUploadAnexo");
	    document.formAnexo.setAttribute("method","post"); 
	    document.formAnexo.setAttribute("enctype","multipart/form-data"); 
	    document.formAnexo.setAttribute("encoding","multipart/form-data"); 
	    
	    //submetendo 
	    document.formAnexo.aba.value = abaSelecionada;
	    document.formAnexo.submit();
	};
	
	setaInfoImagens = function(){
		document.formAnexo.idContrato.value = document.formProntuario.idContrato.value;
		document.formProntuario.funcaoUpload.value = 'anexos';
		document.getElementById('divEnviarArquivo').style.display='block';
		document.getElementById('frameUploadCertificadoApplet').style.display='none';
		document.getElementById('divMsgCertDigApplet').style.display='none';
		document.getElementById('frameUploadCertificadoApplet').src = 'about:blank';		
		POPUP_ANEXO.showInYPosition({top:30});
	};
