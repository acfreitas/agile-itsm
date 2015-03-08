
	var objTab = null;

	addEvent(window, "load", load, false);
	function load(){
		document.form.afterRestore = function () {
			document.getElementById('tabTela').tabber.tabShow(0);
		}
	}

	function selecionaCategoriaGaleriaImagem(){
		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent('listaImagens');
	}
	function voltar(){
		verificarAbandonoSistema = false;
		window.location = retorno;
	}
	function adicionarImagem(){
		if(document.form.idCategoriaGaleriaImagem.value != ""){
			document.getElementById("arquivo").value = "";
			$('#POPUP_ADD_IMAGEM').dialog('open');
		}else{
			alert(i18n_message("galeriaImagens.selecioneUmaCategoria"));
		}
	}

	function excluirImagem(id){
		document.form.idImagem.value = id;
		if(confirm(i18n_message("citcorpore.comum.deleta"))){
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("excluirImagem");
		}
	}

	function fecharPopUpAdicionarImagem(){
		$('#POPUP_ADD_IMAGEM').dialog('close');
	}

	$(function() {
		$("#POPUP_ADD_IMAGEM").dialog({
			autoOpen : false,
			width : 650,
			height : 350,
			modal : true,
			show: "fade",
			hide: "fade"
		});
	});

    carregouIFrameAnexo = function() {
    	$('#POPUP_ADD_IMAGEM').dialog('close');
    	selecionaCategoriaGaleriaImagem();
    	alert(i18n_message("MSG05"));
		HTMLUtils.removeEvent(document.getElementById("frameUpload"),"load", carregouIFrameAnexo);
    };

	submitFormAnexo = function(){
		JANELA_AGUARDE_MENU.show();
		HTMLUtils.addEvent(document.getElementById("frameUpload"),"load", carregouIFrameAnexo, true);

	    document.formAddImagem.setAttribute("target","frameUpload");
	    document.formAddImagem.setAttribute("method","post");
	    document.formAddImagem.setAttribute("enctype","multipart/form-data");
	    document.formAddImagem.setAttribute("encoding","multipart/form-data");

	    //submetendo
	    document.formAddImagem.idCategoriaGaleriaImagem.value = document.form.idCategoriaGaleriaImagem.value;
	    document.formAddImagem.submit();
	};

