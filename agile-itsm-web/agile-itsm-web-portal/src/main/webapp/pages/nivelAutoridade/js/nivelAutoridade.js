			var objTab = null;

			addEvent(window, "load", load, false);

			function load() {
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				};
			}

			function LOOKUP_NIVELAUTORIDADE_select(id, desc) {
				document.form.restore({
					idNivelAutoridade: id
				});
			}

		    var seqGrupo = '';
		    incluirGrupo = function() {
		        GRID_GRUPOS.addRow();
		        seqGrupo = NumberUtil.zerosAEsquerda(GRID_GRUPOS.getMaxIndex(),5);
		        eval('document.form.idGrupo' + seqGrupo + '.focus()');
		    }

		    exibeGrupo = function(serializeGrupo) {
		        if (seqGrupo != '') {
		            if (!StringUtils.isBlank(serializeGrupo)) {
		                var grupoDto = new CIT_GrupoNivelAutoridadeDTO();
		                grupoDto = ObjectUtils.deserializeObject(serializeGrupo);
		                try{
		                    eval('HTMLUtils.setValue("idGrupo' + seqGrupo + '",' + grupoDto.idGrupo + ')');
		                }catch(e){
		                }
		            }
		        }
		    }

		    getGrupo = function(seq) {
		        var grupoDto = new CIT_GrupoNivelAutoridadeDTO();

		        seqGrupo = NumberUtil.zerosAEsquerda(seq,5);
		        grupoDto.sequencia = seq;
		        grupoDto.idGrupo = parseInt(eval('document.form.idGrupo' + seqGrupo + '.value'));
		        return grupoDto;
		    }

		    verificarGrupo = function(seq) {
		        var idGrupo = eval('document.form.idGrupo' + seq + '.value');
		        var count = GRID_GRUPOS.getMaxIndex();
		        for (var i = 1; i <= count; i++){
		            if (parseInt(seq) != i) {
		                 var trObj = document.getElementById('GRID_GRUPOS_TD_' + NumberUtil.zerosAEsquerda(i,5));
		                 if (!trObj){
		                    continue;
		                 }
		                 var idAux = eval('document.form.idGrupo' + NumberUtil.zerosAEsquerda(i,5) + '.value');
		                 if (idAux == idGrupo) {
		                      alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
		                      eval('document.form.idGrupo' + seq + '.focus()');
		                      return false;
		                 }
		            }
		        }
		        return true;
		    }

		    function tratarGrupos(){
		        try{
		            var count = GRID_GRUPOS.getMaxIndex();
		            var contadorAux = 0;
		            var objs = new Array();
		            for (var i = 1; i <= count; i++){
		                var trObj = document.getElementById('GRID_GRUPOS_TD_' + NumberUtil.zerosAEsquerda(i,5));
		                if (!trObj){
		                    continue;
		                }

		                var grupoDto = getGrupo(i);
		                if (parseInt(grupoDto.idGrupo) > 0) {
		                    if  (!verificarGrupo(NumberUtil.zerosAEsquerda(i,5))) {
		                        return false;
		                    }
		                    objs[contadorAux] = grupoDto;
		                    contadorAux = contadorAux + 1;
		                }else{
		                    alert(i18n_message("citcorpore.comum.nenhumaSelecao"));
		                    eval('document.form.idGrupo' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');
		                    return false;
		                }
		            }
		            document.form.colGrupos_Serialize.value = ObjectUtils.serializeObjects(objs);
		            return true;
		        }catch(e){
		        }
		    }

		    function gravar() {
		        if (!tratarGrupos()){
		            return;
		        }

		        document.form.save();
		    }
