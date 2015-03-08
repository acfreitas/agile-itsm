    addEvent(window, "load", load, false);
    function load() {
		document.form.afterRestore = function () {
			$('.tabs').tabs('select', 0);
		}
    }

    /**
     * Motivo: Criando flag de atualização 
     * Autor: flavio.santana
     * Data/Hora: 13/11/2013 15:56
     */
    var flagModalAtualizacao = false;

    $(function(){
    	
    	$('.modal').on('shown', function() {
    		 flagModalAtualizacao = true;
    	});
    	
    	$('.modal').on('hidden', function () {
    		 flagModalAtualizacao = false;
    	});

    });
    
	function LOOKUP_PROCESSAMENTO_BATCH_select(id,desc){
		$('.tabsbar a[href="#tab1-3"]').tab('show');
		document.form.restore({idProcessamentoBatch:id});
	}
	
    function mostraExecucoes(){
        var id = document.form.idProcessamentoBatch.value;
        if (id == '' || id == '0'){
            alert(i18n_message("processamentoBatch.informeProcessamento"));
            return;
        }
        document.form.fireEvent("mostrarExecucoes");
    }
    
    function executarJobService(){
        var nomeClasseJobService = document.formExecJob.nomeClasseJobService.value;
        if (nomeClasseJobService == null || nomeClasseJobService == ''){
            alert(i18n_message("processamentoBatch.informeNomeClasse"));
            return;
        }
        document.formExecJob.fireEvent("executaJobService");        
    }
