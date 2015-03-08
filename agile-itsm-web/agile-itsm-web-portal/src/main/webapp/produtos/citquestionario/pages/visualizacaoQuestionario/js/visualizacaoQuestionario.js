	function encode64_questionario(input) {
		// base64 strings are 4/3 larger than the original string
		if(window.btoa) return window.btoa(input);
		var _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
		var output = new Array( Math.floor( (input.length + 2) / 3 ) * 4 );
		var chr1, chr2, chr3;
		var enc1, enc2, enc3, enc4;
		var i = 0, p = 0;

		do {
			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);

			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;

			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			} else if (isNaN(chr3)) {
				enc4 = 64;
			}

			output[p++] = _keyStr.charAt(enc1);
			output[p++] = _keyStr.charAt(enc2);
			output[p++] = _keyStr.charAt(enc3);
			output[p++] = _keyStr.charAt(enc4);
		} while (i < input.length);

		return output.join('');
	}
	function fecharIFrame(frameNameParm){
		$(frameNameParm).style.display='none';
	}
	function executarAposCarregar(){
		bufferAposLoad;
	}
	HTMLUtils.addEvent(window, "load", executarAposCarregar, false);
	HTMLUtils.addEvent(window, "load", load_page, false);
    function load_page(){
		try{
            parent.escondeJanelaAguarde();
        }catch(e){}
    }
    function validar(){
		if (!validacaoGeral()){
			return false;
		}
		if (!document.formQuestionario.validate()){
			return false;
		}
		try{
			mostraAguardeValidacaoQuestionario();
		}catch(e){
		}
		document.formQuestionario.fireEvent('validate');
		return true;
    }

	function exibirLoad(){

		JANELA_AGUARDE_MENU.show();
	}

	function ocultarLoad(){

		JANELA_AGUARDE_MENU.hide();
	}

    function getObjetoSerializado(){
    	document.formQuestionario.action = ctx + '/pages/solicitacaoServicoQuestionario/solicitacaoServicoQuestionario.load';
    	//alert(document.formQuestionario.action);
    	document.formQuestionario.submit();
    	return '';
    }

    function getObjetoSerializadoPortal(){

        	var obrigatorio = $('#obrigatorio').val();
  	    	document.formQuestionario.action = ctx + '/pages/solicitacaoServicoQuestionario/solicitacaoServicoQuestionario.load?mulitploQuestionario=true&respostaObrigatoria='+obrigatorio;
  	    	document.formQuestionario.submit();
  	    	return '';
 	 }