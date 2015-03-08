
		/**
		 *Recebe a coleção enviada de triagemRequisicaoPessoal e adiciona na tblCurriculos
		 **/
		 /* function tooltip() {	
			  $(document).ready(function() {$('.titulo').tooltip()});
		  }*/

		   function adicionaDadosTable(curriculo){
			   
			   var triagem = new CIT_TriagemRequisicaoPessoalDTO();
			   
	           triagem.idCurriculo = curriculo.idCurriculo;
	           triagem.nome = curriculo.nome;
	       	   triagem.dataNascimento = curriculo.dataNascimento;
	       	   triagem.cpf = curriculo.cpf;
	       	   triagem.sexo = curriculo.sexo;
	       	   triagem.listaNegra = curriculo.listaNegra;
		           if(curriculo.listaNegra == 'N'){
		       	   		HTMLUtils.addRowsByCollection('tblCurriculos', null, '', triagem, ["nome","dataNascimento","cpf","sexo", ""], null, '', [exibeIconesCurriculo], null, null, false);
		           }else if(curriculo.listaNegra == 'S'){
		        	 	HTMLUtils.addRowsByCollection('tblCurriculos', null, '', triagem, ["nome","dataNascimento","cpf","sexo", ""], null, '', [exibeIconesCurriculo, corLinhaItemListaNegra], null, null, false);
		           }else{
		        	   HTMLUtils.addRowsByCollection('tblCurriculos', null, '', triagem, ["nome","dataNascimento","cpf","sexo", ""], null, '', [exibeIconesCurriculo], null, null, false);
		           }
		           
		           geraTooltip();
			   }

		   exibeIconesCurriculo = function(row, obj){
				var id = obj.idCurriculo;
				if(obj.listaNegra == 'N' || obj.listaNegra == ''){
					row.cells[4].innerHTML = '<a href="#" class="btn-action glyphicons circle_plus btn-success titulo" title="Adicionar na lista de triagem"  onclick="adicionarCurriculoNaTriagem(this.parentNode.parentNode.rowIndex,'+obj.idCurriculo+')"><i></i></a>     ';
				}
				row.cells[4].innerHTML += '<a href="#" class="btn-action glyphicons nameplate btn-success titulo"  title="Ver currículo" onclick="abrirModalInformacoesCurriculo('+ row.rowIndex + ','+obj.idCurriculo+')"><i></i></a>  | ';
				row.cells[4].innerHTML += '<a href="#" class="btn-action glyphicons thumbs_down btn-warning titulo"  title="Adicionar/Remover na lista Negra" onclick="abrirModalListanegra('+ row.rowIndex + ','+obj.idCurriculo+')"><i></i></a>  |  ';
				row.cells[4].innerHTML += '<a href="#" class="btn-action glyphicons remove_2 btn-danger titulo"  title="Excluir item" onclick="excluirLinhaTable(this.parentNode.parentNode.rowIndex,\'tblCurriculos\')"><i></i></a>';
			    
			}
		   	/*
		   	 * Alterado por luiz.borges em 28/11/2013 às 15:13 hrs
		   	 * Motivo: Internacionalização através do método i18n_message("")
		   	 */
		   corLinhaItemListaNegra = function (row, obj){
			   row.style.backgroundColor = '#BD362F',
			   row.style.color = 'white',
			   row.title =  i18n_message("rh.curriculoConstaListaNegra"),
			   row.className = 'titulo';
		   }
		   function geraTooltip() {	
				  $('.titulo').tooltip();
			  } 

			function limparDadostableCurriculo(){

				HTMLUtils.deleteAllRows('tblCurriculos');
				}
			excluirLinhaTable = function(indice, table) {
				if (indice > 0 && confirm('Confirma exclusão?')) {
					HTMLUtils.deleteRow(table, indice);
				}
			}
		    function abrirModalInformacoesCurriculo(row, idCurriculo){
			    //document.getElementById('frameVisualizacaoCurriculo').src = URL_SISTEMA+'templateCurriculo/templateCurriculo.load?iframe=true&idCurriculoPesquisa='+idCurriculo;
			    window.open(URL_SISTEMA+'templateCurriculo/templateCurriculo.load?iframe=true&idCurriculoPesquisa='+idCurriculo, "_blank");
			    //$('#modal_curriculo').modal('show');
			 }
		    function abrirModalListanegra(row, idCurriculo){

			    document.getElementById('frameListaNegra').src = URL_SISTEMA+'historicoAcaoCurriculo/historicoAcaoCurriculo.load?iframe=true&idCurriculoPesquisa='+idCurriculo;
			    $('#modal_listaNegra').modal('show');

			
			    }
		    function adicionarCurriculoNaTriagem(index, idCurriculo){  
		    	 HTMLUtils.deleteRow('tblCurriculos', index);
		          var curriculos = [];
		          var curriculoDto = new CIT_CurriculoDTO();
		          curriculoDto.idCurriculo = idCurriculo;
		          curriculos.push(curriculoDto);  
		          document.formSugestaoCurriculos.curriculos_serialize.value = ObjectUtils.serializeObjects(curriculos);
		         
		          document.formSugestaoCurriculos.fireEvent("tratarColecaoTriagem");
		      }
		    
		    function chamarFuncaoAbrirTriagemManual(evt) {
		    	var key_code = evt.keyCode  ? evt.keyCode  : evt.charCode ? evt.charCode : evt.which ? evt.which : void 0;
		    	if (key_code == 13) {
		    		var chave = document.getElementById('chave').value;
		    		var formacao = document.getElementById('formacao').value;
		    		var certificacao = document.getElementById('certificacao').value;
		    		var idiomas = document.getElementById('idiomas').value;
		    		var cidade = document.getElementById('cidade').value;
			    	if (chave.trim().length > 0 || formacao.trim().lngth > 0 || certificacao.trim().length > 0 || idiomas.trim().length > 0 || cidade.trim().length > 0) {
			    		parent.abrirTriagemManual();
			    	}
		    	}
		    }
		    
		    function atualizarGridPesquizaCurriculo() {
		    	parent.abrirTriagemManual();
		    }
		      