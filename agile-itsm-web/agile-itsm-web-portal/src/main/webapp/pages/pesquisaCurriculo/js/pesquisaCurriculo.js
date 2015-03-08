    addEvent(window, "load", load, false);
    function load() {
    	limparDadostableCurriculo();
    	$('#conteudoVisualizarHistorico').html('<iframe id="frameVisualizarHistorico" src="about:blank" width="100%" height="455" style="border: 0px none;"></iframe>');
    	ocultarCamposCurriculo();
    }
    
    /**
     * Metodo para ocutação de campos do currículo, quando o mesmo for aberto pela Pesquisa de Currículo.
     * Na triagem de currículo não tera efeito.
     * 
     * @author david.silva
     */
    function ocultarCamposCurriculo(){
    	var nameFrame = GetParameterValues('iframe');
    	if(!(nameFrame == "true")){
    		$("#acoes").css("display","none");
    		$("#collapse1").css("display","none");
    		$("#limparDadosCurriculo").css("display","none");
    	}
    }
    
    /**
	* @author thiago.borges
	*/
    
    /** Mudar pagina da paginação **/
    function paginarItens(pgSel) {
    	JANELA_AGUARDE_MENU.show();
    	document.getElementById('paginaSelecionada').value = pgSel;
    	document.formSugestaoCurriculos.fireEvent("triagemManual");
    }
	
	/**
	* @author thiago.borges
	*/
    
    /** Visualizar Historico **/
    function visualizarHistorico(idHistorico, idCandidato){
    	var url = URL_SISTEMA+'pages/visualizarHistoricoFuncional/visualizarHistoricoFuncional.load?noVoltar=true&idHistoricoFuncional='+idHistorico+'&idCandidato='+idCandidato;
    	document.getElementById('frameVisualizarHistorico').src = url;
    	$('#modal_visualizarHistorico').modal('show');
    }
    
	function limpar(){
		document.formSugestaoCurriculos.clear();
		limparDadostableCurriculo();
	}
	
	function limparDadostableCurriculo(){
		 HTMLUtils.deleteAllRows('tblCurriculos');
	}
	
	function adicionaDadosTable(curriculo){
		var triagem = new CIT_TriagemRequisicaoPessoalDTO();
		var aplicarCss = "style='font-size: 150%;'";
		
		var detalheCurriculo = "<b><a title='"+i18n_message("rh.visualizarCurriculo")+"' href='javascript:abrirModalCurriculo(0,"+curriculo.idCurriculo+");' "+aplicarCss+" >";
			detalheCurriculo += curriculo.nome+"</a></b>";
			detalheCurriculo += "<br>"+curriculo.descricaoTipoFormacao+", "+curriculo.descricao;
			detalheCurriculo += "<br>"+curriculo.nomeCidade;
			detalheCurriculo += " - "+curriculo.nomeUF+" | Pretenção Salarial: "+ curriculo.pretensaoSalarial;
			
		triagem.idCurriculo = curriculo.idCurriculo;
		triagem.caminhoFoto = curriculo.caminhoFoto;
        triagem.nome = detalheCurriculo;
        triagem.listaNegra = curriculo.listaNegra;
        
        /**
         * Tratamento para exibir/ocultar Botão Incluir dependento do contexto.
         * 
         * @author david.silva
         */
        var nameFrame = GetParameterValues('iframe');
        
        if(nameFrame == "true"){
        	if(triagem.listaNegra == 'S'){
        		HTMLUtils.addRowsByCollection('tblCurriculos', null, '', triagem, ["", "nome", ""], null, '', [exibeIconesCurriculo, corLinhaItemListaNegra], null, null, false);
        	}else{
        		HTMLUtils.addRowsByCollection('tblCurriculos', null, '', triagem, ["", "nome", ""], null, '', [exibeIconesCurriculo], null, null, false);
        	}
        }else if(nameFrame == "" || nameFrame == null){
        	
        	$('#coluna3').css('text-indent', '-99999em');
        	
        	if(triagem.listaNegra == 'S'){
        		HTMLUtils.addRowsByCollection('tblCurriculos', null, '', triagem, ["", "nome", ""], null, '', [exibeIconesCurriculoSemIncluir, corLinhaItemListaNegra], null, null, false);
        	}else{
        		HTMLUtils.addRowsByCollection('tblCurriculos', null, '', triagem, ["", "nome", ""], null, '', [exibeIconesCurriculoSemIncluir], null, null, false);
        	}
        }
    	
    	geraTooltip();
	}
	
	function GetParameterValues(param) {
		var url = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
		for (var i = 0; i < url.length; i++) {
			var urlparam = url[i].split('=');
				if (urlparam[0] == param) {
					return urlparam[1];
				}
		}
	}
	
	function adicionarLinhaSelecionada(objeto, id, desc, obrigatorio, detalhe){
		var contLinha = parseInt(document.getElementById('cont'+objeto).value);
		var checked = "";
		if (obrigatorio == "S")	checked = "checked='true'";	
		contLinha ++;
		eval("document.getElementById('cont"+objeto+"').value = '"+contLinha+"'");
		var nomeTabela = 'tbl'+objeto;
		var tbl = document.getElementById(nomeTabela);
		tbl.style.display = 'block';
		var ultimaLinha = tbl.rows.length;
		var linha = tbl.insertRow(ultimaLinha);
		var coluna = linha.insertCell(0);

		coluna.innerHTML = desc + '<input type="hidden" id="id' + objeto + contLinha + '" name="id'+objeto+'" value="' + id + '" />';
		coluna = linha.insertCell(1);
		
		coluna.innerHTML = detalhe.substring(0,11) +'<span style="cursor: pointer;" title = "'+ detalhe +'"> ... </span>';
		coluna = linha.insertCell(2);
		
		if (obrigatorio == "S") {
			coluna.innerHTML = i18n_message("citcorpore.comum.sim");
		} else {
			coluna.innerHTML = i18n_message("citcorpore.comum.nao");
		}
		
		$(".div"+objeto).show();
	}

	function incluirColecaoTable(curriculoStr) {
    	var curriculo = new CIT_CurriculoDTO();
    	curriculo = ObjectUtils.deserializeObject(curriculoStr);
    	adicionaDadosTable(curriculo);
    } 
	
	exibeIconesCurriculo = function(row, obj){
		var id = obj.idCurriculo;
		row.cells[2].innerHTML = '<a href="#" class="btn btn-icon btn-primary" title="Adicionar na lista de triagem"  onclick="adicionarCurriculoNaTriagem(this.parentNode.parentNode.rowIndex,'+obj.idCurriculo+')"><i></i>Incluir</a><br><br> ';
		
		if (obj.caminhoFoto != "") {
			row.cells[0].innerHTML = '<div class="col_100"><img src="' + obj.caminhoFoto + '" border=0 width="70px" heigth="70px" /></div>';
		}else {
			row.cells[0].innerHTML = '<div class="col_100"><img src="' + URL_SISTEMA + '/novoLayout/common/theme/images/avatar.jpg" border=0 width="70px" heigth="70px" /></div>';
		}
	}
	
	exibeIconesCurriculoSemIncluir = function(row, obj){
		var id = obj.idCurriculo;
		
		if (obj.caminhoFoto != "") {
			row.cells[0].innerHTML = '<div class="col_100"><img src="' + obj.caminhoFoto + '" border=0 width="70px" heigth="70px" /></div>';
		}else {
			row.cells[0].innerHTML = '<div class="col_100"><img src="' + URL_SISTEMA + '/novoLayout/common/theme/images/avatar.jpg" border=0 width="70px" heigth="70px" /></div>';
		}
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
	 
	function abrirModalCurriculo(row, obj){
		window.open(URL_SISTEMA+'modalCurriculo/modalCurriculo.load?iframe=true&idCurriculo='+obj, "_blank");
	}
	 
	 function abrirHistoricaFuncional(obj){
		 $("#idCurriculo").val(obj);
		 document.formSugestaoCurriculos.fireEvent("buscaHistorico");
	}
	 
	 $(window).load(function(){	 
		 $('#modalCurriculo').html('<iframe id="frameModalCurriculo" src="about:blank" width="100%" height="650px" class="iframeSemBorda"></iframe>');
	 });
	 
	 function sugereCurriculos() {
		 JANELA_AGUARDE_MENU.show();
		 document.formSugestaoCurriculos.idSolicitacaoServico.value = parent.document.form.idSolicitacaoServico.value;
		 document.formSugestaoCurriculos.fireEvent('sugereCurriculos');
	 }
	 
	 function limpaPaginacao(){
		 $('#paginas').empty();
	 }