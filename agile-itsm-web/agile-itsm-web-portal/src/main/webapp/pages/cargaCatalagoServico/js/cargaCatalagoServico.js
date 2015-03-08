
		var popup;
		
		$(window).load(function() {
			
			popup = new PopupManager(800, 600, ctx+"/pages/");
			   document.form.afterRestore = function() {
				$('.tabs').tabs('select', 0);
			   }
			
			
		});
		
		

	    carregouIFrameAnexo = function() {
	    	//POPUP_ADD_IMAGEM.hide();
			HTMLUtils.removeEvent(document.getElementById("frameUpload"),"load", carregouIFrameAnexo);
	    };


	    carregouIFrameAnexo = function() {
	    	JANELA_AGUARDE_MENU.hide();
			HTMLUtils.removeEvent(document.getElementById("frameUploadAnexo"),"load", carregouIFrameAnexo);
	    };

		submitFormAnexo = function(){
			//HTMLUtils.addEvent(document.getElementById("frameUploadAnexo"),"load", carregouIFrameAnexo, true);
		    //document.form.setAttribute("target","frameUploadAnexo");
		    document.form.setAttribute("method","post");
		    document.form.setAttribute("enctype","multipart/form-data");
		    document.form.setAttribute("encoding","multipart/form-data");

		    //submetendo
		    document.form.submit();
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

		function enviarDados(){
			JANELA_AGUARDE_MENU.show();
			submitFormAnexo();
		}




