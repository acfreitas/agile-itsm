
		aguarde = function(){
			JANELA_AGUARDE_MENU.show();
		}

		fechar_aguarde = function(){
	    	JANELA_AGUARDE_MENU.hide();
		}

		function pesquisa(){
			aguarde();
			document.form.fireEvent("montaGraficoGeraDesempenho");
		}

	
