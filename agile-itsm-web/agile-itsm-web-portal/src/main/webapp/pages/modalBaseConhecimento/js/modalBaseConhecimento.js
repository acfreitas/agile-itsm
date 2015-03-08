	    $(function(){
			$("#pesquisaBaseConhecimento").dialog({
				title: 'Pesquisa Base de Conhecimento',
				width: 1300,
				height: 650,
				modal: true,
				autoOpen: true,
				resizable: false,
				show: "fade",
				hide: "fade",
				beforeClose: function(){
					fechaPopupIframe();
				}

			});
    	});
	    
	    function fechaPopupIframe(){
	    	if(!id)	{	
	    		window.location = ctx+"/pages/index/index.load";
	    	
	    	} else	{	
	    		window.close();
			}
	    }