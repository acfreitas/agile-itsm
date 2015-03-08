
			var popup;
		    addEvent(window, "load", load, false);
		    function load(){
				popup = new PopupManager(850, 500, ctx+"/pages/");
		    }

			function LOOKUP_BICONSULTAS_select(id, desc) {
				document.form.restore({
					idConsulta : id
				});
			}
		    exibeColuna = function(serializeColuna) {
		        if (seqColuna != '') {
		            if (!StringUtils.isBlank(serializeColuna)) {
		                var colunaDto = new CIT_BIConsultaColunasDTO();
		                colunaDto = ObjectUtils.deserializeObject(serializeColuna);
		                eval('document.form.nomeColuna' + seqColuna + '.value = "' + colunaDto.nomeColuna + '"');
		                eval('document.form.ordem' + seqColuna + '.value = "' + colunaDto.ordem + '"');
		            }
		        }
		    }
		    function tratarColunas(){
		    	document.form.colCriterios_Serialize.value = '';
		        try{
		            var count = GRID_COLUNAS.getMaxIndex();
		            var contadorAux = 0;
		            var objs = new Array();
		            for (var i = 1; i <= count; i++){
		                var trObj = document.getElementById('GRID_COLUNAS_TD_' + NumberUtil.zerosAEsquerda(i,5));
		                if (!trObj){
		                    continue;
		                }
		                var biConsultaColunasDTO = getConsulta(i);
	                    if (StringUtils.isBlank(biConsultaColunasDTO.nomeColuna)){
	                        alert(i18n_message('construtorconsultas.nomeColuna'));
	                        eval('document.form.nomeColuna' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');
	                        return false;
	                    }
	                    if (StringUtils.isBlank(biConsultaColunasDTO.ordem)){
	                        alert(i18n_message('construtorconsultas.ordem'));
	                        eval('document.form.ordem' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');
	                        return false;
	                    }
	                    objs[contadorAux] = biConsultaColunasDTO;
	                    contadorAux = contadorAux + 1;
		            }
		            document.form.colCriterios_Serialize.value = ObjectUtils.serializeObjects(objs);
		            return true;
		        }catch(e){
		        	alert('Ocorreu um erro ao processar a solicita��o de Grava��o. Tente novamente! ' + e.message);
		        	return false;
		        }
		    }
		    getConsulta = function(seq) {
		        var biConsultaColunasDTO = new CIT_BIConsultaColunasDTO();

		        var seqCriterio = NumberUtil.zerosAEsquerda(seq,5);
		        biConsultaColunasDTO.nomeColuna = eval('document.form.nomeColuna' + seqCriterio + '.value');
		        biConsultaColunasDTO.ordem = eval('document.form.ordem' + seqCriterio + '.value');
		        return biConsultaColunasDTO;
		    }

		    var seqColuna = '';
		    incluirColuna = function() {
		    	GRID_COLUNAS.addRow();
		        seqColuna = NumberUtil.zerosAEsquerda(GRID_COLUNAS.getMaxIndex(),5);
		        eval('document.form.nomeColuna' + seqColuna + '.focus()');
		    }

		    function gravar(){
		    	if (tratarColunas()){
		    		document.form.save();
		    	}
		    }
		    function exportar(){
		    	if (tratarColunas()){
			    	document.form.fireEvent("exportar");
		    	}
		    }

		    importarReport = function(){
		        if (!confirm(i18n_message("construtorconsultas.importaraQuestionario"))){
		            return;
		        }

		        JANELA_AGUARDE_MENU.setTitle('Aguarde... importando...');
		        JANELA_AGUARDE_MENU.show();

			    document.formImportar.setAttribute("enctype","multipart/form-data");
			    document.formImportar.setAttribute("encoding","multipart/form-data");
		        document.formImportar.submit();
		    }

		
