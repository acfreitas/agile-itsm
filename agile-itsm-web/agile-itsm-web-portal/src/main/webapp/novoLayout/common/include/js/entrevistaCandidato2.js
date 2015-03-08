    	function configuraAtividade()
		{
			if(document.form.possuiOutraAtividade[1].checked)
				document.getElementById('divAtividade').style.display = 'none'; 
			else
				document.getElementById('divAtividade').style.display = 'block';
		}  

	      function desabilitarTela() {
	          var f = document.form;
	          for(i=0;i<f.length;i++){
	              var el =  f.elements[i];
	              if (el.type != 'hidden') { 
	                  if (el.disabled != null && el.disabled != undefined) {
	                      el.disabled = 'disabled';
	                  }
	              }
	          }  
	      }
	      
		    
	      addEvent(window, "load", load, false);
	      
	      function load(){        
	          document.form.afterLoad = function () {
	              configuraCampos();
	          }    
	      }

	    configuraCampos = function() {
	    	if (document.form.tipoEntrevista.value == 'RH') {
	    		document.form.observacaoGestor.disabled = true;
	    		document.form.notaGestor.disabled = true;
	    		document.form.resultado[0].disabled = true;	
	    		document.form.resultado[1].disabled = true;
	    		document.form.resultado[2].disabled = true;
	    		document.form.resultado[3].disabled = true;
	    		document.form.resultado[4].disabled = true;
	    	}else{
	    		if (document.form.preRequisitoEntrevistaGestor.value == 'S'){
		    		document.form.planoCarreira.disabled = true;
		    		document.form.caracteristicas.disabled = true;
		    		document.form.trabalhoEmEquipe.disabled = true;
		    		document.form.possuiOutraAtividade[0].disabled = true;
		    		document.form.possuiOutraAtividade[1].disabled = true;
		    		document.form.outraAtividade.disabled = true;
		    		document.form.concordaExclusividade[0].disabled = true;
		    		document.form.concordaExclusividade[1].disabled = true;
		    		document.form.salarioAtual.disabled = true;	    		
		    		document.form.pretensaoSalarial.disabled = true;	  
		    		document.form.dataDisponibilidade.disabled = true;
		    		document.form.competencias.disabled = true;
		    		document.form.notaAvaliacao.disabled = true;
	    		}
	    	}	
	    }
	    
     	function serializa() {
     		
     	    var atitudes = HTMLUtils.getObjectsByTableId('tblAtitudes');
     	    
     		document.getElementById('serializeAtitudes').value = ObjectUtils.serializeObjects(atitudes);

     		return true;
	    }
	    
	    function gravar(){
	    	
	    	if (!serializa()) {
	    		
	    		return;
	    	}
	    	
			document.form.save();
		}
		
		function gerarCampoAvaliacao(row, obj) {
			
			var disabled = '';
			
			if ((document.form.tipoEntrevista.value != 'RH')&&(document.form.preRequisitoEntrevistaGestor.value == 'S')) {
				
				disabled = " disabled='disabled' ";
			}
			
			var str = "<select name='avaliacao_" + obj.idAtitudeOrganizacional + "' id='avaliacao_" + obj.idAtitudeOrganizacional + "' onchange='atribuiAvaliacao(this.value,"+row.rowIndex+") '"+disabled+">"+ 
					  "   <option value='' ";
					  if (obj.avaliacao == '' )
					  	str += "selected = '' ";
					  str += " >" +i18n_message("citcorpore.comum.selecione")+"</option>";
					  
					  str += "<option value='N'";
					  if (obj.avaliacao == 'N' )
					  	str += "selected";
					  str += ">" + i18n_message("entrevistaCandidato.naoApresenta") + "</option>";
					  
					  str += "<option value='P' ";
					  if (obj.avaliacao == 'P' )
					  	str += "selected";
					  str += ">" + i18n_message("entrevistaCandidato.apresentaParcialmente") + "</option>";
					  
					  str += "<option value='A' ";
					  if (obj.avaliacao == 'A' )
					  	str += "selected";
					  str += ">" + i18n_message("entrevistaCandidato.apresenta") +"</option>"+
					  "</select>";
		
            row.cells[1].innerHTML = str;
        };
        
        function atribuiAvaliacao(val,indice) {
        	
        	var obj = HTMLUtils.getObjectByTableIndex('tblAtitudes', indice);
        	
        	obj.avaliacao = val;
        }