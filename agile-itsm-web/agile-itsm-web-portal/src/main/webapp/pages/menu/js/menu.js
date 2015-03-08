    addEvent(window, "load", load, false);
    function load() {
		document.form.afterRestore = function () {
			$('.tabs').tabs('select', 0);
		}
    }
    
    $(function() {
    	$("#ordem").focusout(function (){
    		validacaoOrdem();
    	});
    	$("#btnTodos").css('display','none');
		$("#btnLimpar").css('display','none');
		
		/* Ao clicar na imagem o item é checado
		 * Autor: flavio.santana
		 * Data/Hora 31/10/2013 08:53
		 * */
		$('#docs_icons a.glyphicons').click(
				function(){
					$(this).parent().each( 
					    function() { 
					    	$(this).find('.checked').removeClass('checked');
					});
				$(this).find('input[name="imagem"]').attr('checked',true).parent().addClass('checked');
		});
		
		desativaMenuRapido();
    });
    
    
    function validacaoOrdem(){
		if($("#ordem").val() == ""){
			alert(i18n_message("citcorpore.comum.campoOrdemMenusObrigatorio")); 
			return false;
		}else{
			if (!$("#ordem").val().match(/^\d{1,10}$/) ) {
    			alert(i18n_message("citcorpore.comum.campoOrdemMenus"));
    			return false;
    		}else{
    			return true;
    		}
		}
    }
    
    function setaLingua(idioma){
    	document.getElementById("pesqLockupLOOKUP_MENU_D_IDLINGUA").value = idioma;
    }
	function LOOKUP_MENU_select(id,desc){
		$('.tabsbar a[href="#tab1-3"]').tab('show');
		document.form.restore({idMenu:id});
	}
	function excluir() {
		if (document.getElementById("idMenu").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("update");
			}
		}
	}
	
	function gerar(){
		document.form.fireEvent("exportarMenuXml");
	}
	
	function atualizar(){
		document.form.fireEvent("atualizarMenuXml");
	}
	
	/**
	* @author rodrigo.oliveira
	*/
	function desativaMenuPai(){
		var combobox = document.getElementById("idMenuPai")
		combobox.selectedIndex = 0;		
		combobox.disabled = true;		
	}
	
	/**
	* @author rodrigo.oliveira
	*/
	function ativaMenuPai(){
		document.getElementById("idMenuPai").disabled = false;
	}

	function desativaMenuRapido(){
		var checkbox = document.getElementById("menuRapido")
		$('#menuRapido').parent().removeClass('checked');
		$('#uniform-menuRapido').addClass('disabled');
		checkbox.checked = false;	
		checkbox.disabled = true;
		$("#painelImagens").show();
	}

	function ativaMenuRapido(){
		document.getElementById("menuRapido").disabled = false;
		$('#uniform-menuRapido').removeClass('disabled');
		$("#painelImagens").hide();
	}
	
	/**
	* @author rodrigo.oliveira
	*/
	function limpar(){
		document.getElementById("idMenuPai").disabled = false;
		document.form.clear();
		desativaMenuRapido();
	}
	
	function gravar(){
		if(!validacaoOrdem()){
			return;
		}
		document.form.save();
	}
