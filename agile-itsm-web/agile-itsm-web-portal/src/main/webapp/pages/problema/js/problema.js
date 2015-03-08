 $.fx.speeds._default = 1000;
		var myLayout;
		var popupManager;
		var popupManagerSolicitacaoServico;
		var LOOKUP_EMPREGADO_select;
		var atualizarListaRegistros;

		var slickGridColunas;
		var slickGridOptions;
		var slickGridTabela;

		var tabelaRelacionamentoICs;
		var tabelaRelacionamentoSolicitacaoServico;

		var popup2;


		function onkeypressUnidadeDes(){
			document.getElementById("idUnidade").value= "0";
		}


		/**Monta os parametros para a buscas do autocomplete**/
		function montaParametrosAutocompleteUnidade(){
			$( "#unidadeDes" ).autocomplete({
				  source: function (request, response) {
				  $.ajax({
			          	  url: "pages/autoCompleteUnidade/autoCompleteUnidade.load",
			          	  dataType: "json",
			          		data: {
			        			query: request.term, colection : 1, idContrato : document.form.idContrato.value
			         		},
			          		success: function(data) {
                        	response($.map(data, function(item) {
	                              return {
	                                  		label: item.nome,
	                                  		value: item.nome.replace(/-*/, ""),
	                                  		id: item.idUnidade,
	                                  	 };
	                        	}));
	                    	}
				        });
				  },
				  minLength: 3,
				  select: function(e, ui) {
					  $('#idUnidade').val(ui.item.id);
					  document.form.fireEvent("preencherComboLocalidade");
				  }
			});
		}

		$(document).ready(function () {

    		//initTextEditors();
    		initPopups();
    		initTabelasRelacionamentos();

    		//para visualização rápida do mapaDesenhoServico
    		popupManager = new PopupManager("98%" , $(window).height()-100, properties.ctx + "/pages/");
    		popupManagerSolicitacaoServico = new PopupManager("98%" , $(window).height()-100, properties.ctx + "/pages/");
    		popupManagerRequisicaoMudanca = new PopupManager("98%" , $(window).height()-100, properties.ctx + "/pages/");
    		popup2 = new PopupManager("98%", 520 , "${ctx}/pages/");
    		popupCategoriaProblema = new PopupManager("98%", $(window).height()-100, properties.ctx + "/pages/");

    		$( "#tabs" ).tabs();

    		$("#POPUP_SOLICITANTE").dialog({
				autoOpen : false,
				width : 600,
				height : 550,
				modal : true

			});

    		$("#POPUP_INFO_INSERCAO").dialog({
    			autoOpen : false,
    			width : 400,
    			height : 280,
    			modal : true,
    			close: function(event, ui) {
    				fechar();
    			}
    		});

    		/*  geber.costa
    			Inserido no começo para ocultar a aba de Revisão, por padrão a prioridade é 5, e a aba de Revisão de problema grave deve ficar inativa ,
    			somente mudará quando a prioridade for 1 ou 2.
    		*/
    		atualizaPrioridade();

	    });

		function setarDescricao(){
			var txt = $("#DescricaoAuxliar").text();
			$("#DescricaoAuxliar").empty();
			$("#descricao").val(txt)
		}

		/*
		 * Funções de inicialização
		 */
		function initTabelasRelacionamentos(){
			//ICs
			tabelaRelacionamentoICs = new CITTable("tblICs",["idItemConfiguracao", "nomeItemConfiguracao","descricaoProblema"],[]);
			tabelaRelacionamentoICs.setInsereBotaoExcluir(true, properties.ctx + "/imagens/delete.png");
			//Incidentes
			tabelaRelacionamentoSolicitacaoServico = new CITTable("tblSolicitacaoServico",["idSolicitacaoServico", "nomeServico"],[]);
			tabelaRelacionamentoSolicitacaoServico.setInsereBotaoExcluir(true, properties.ctx + "/imagens/delete.png");
			//Mudancas
			tabelaRelacionamentoMudancas = new CITTable("tblRDM",["idRequisicaoMudanca", "titulo", "status"],[]);
			tabelaRelacionamentoMudancas.setInsereBotaoExcluir(true, properties.ctx + "/imagens/delete.png");
		}

	    function initTextEditor(editor){
	    	editor.BasePath = properties.ctx + '/fckeditor/';
	      	editor.Config['ToolbarStartExpanded'] = false ;
	      	editor.Width = '100%' ;
	      	editor.ReplaceTextarea() ;
	    }

	    /**
	     * Popups à partir de lookups devem ter suas classes setadas como POPUP_LOOKUP.
	     * Assim, serão todas inicializadas aqui.
	     */
		function initPopups(){
			$(".POPUP_LOOKUP").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});

			$(".popup").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});

			$("#POPUP_PESQUISARPROBLEMA").dialog({
				autoOpen : false,
				width : 802,
				height : 400,
				modal : true
			});

		}

		/*
		 * Funções de Solicitacao Servico
		 */


		$(function() {
			$("#POPUP_SOLICITACAOSERVICO").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});

			$("#addSolicitacaoServico").click(function() {
				$("#POPUP_SOLICITACAOSERVICO").dialog("open");
			});

			$("#addImgSolicitacaoServico").click(function() {
				$("#POPUP_SOLICITACAOSERVICO").dialog("open");
			});

			$("#POPUP_PESQUISAITEMCONFIGURACAO").dialog({
				autoOpen : false,
				width : 1200,
				height : 650,
				modal : true
			});

		});

		function fecharProblema(){
			$("#POPUP_SOLICITACAOSERVICO").dialog("close");
		}

		function LOOKUP_SOLICITACAOSERVICO_select(id, desc){

			addLinhaTabelaSolicitacaoServico(id, desc, true);

		}

		function addLinhaTabelaSolicitacaoServico(id, desc, valida){
			var tbl = document.getElementById('tblSolicitacaoServico');
			tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			if (valida){
				if (!validaAddLinhaTabelaSolicitacaoServico(lastRow, id)){
					return;
				}
			}

			tabelaRelacionamentoSolicitacaoServico.addObject([id, desc]);

			document.form.solicitacaoServicoSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoSolicitacaoServico.getTableObjects());

			$("#POPUP_SOLICITACAOSERVICO").dialog("close");

		}

		function validaAddLinhaTabelaSolicitacaoServico(lastRow, id){
			var listaSolicitacaoServico = ObjectUtils.deserializeCollectionFromString(document.form.solicitacaoServicoSerializado.value);

			if (lastRow > 1){
				for(var i = 0; i < listaSolicitacaoServico.length; i++){
					if (listaSolicitacaoServico[i].idSolicitacaoServico == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return false;
					}
				}
			}
			return true;
		}

		/*
		 * Funções de apoio
		 */

		function mostrarFormulario(){;
			limpar(document.form);
		}

		/**
		 * Textarea com FCKEditores devem ter ID.
		 */
		function limparFCKEditores(){
			var fckEditorAux = null;
			var textAreaList = document.getElementsByTagName("textarea");

			for(var i = 0; i < textAreaList.length; i++){
				if(textAreaList[i].id != null){

					fckEditorAux = FCKeditorAPI.GetInstance( textAreaList[i].id );

					if(fckEditorAux != null){
						try{
							fckEditorAux.SetData("");
						}catch(e){
						}
					}
				}
			}
		}

		function atualizaPrioridade(){

			var impacto = document.getElementById('impacto').value;
			var urgencia = document.getElementById('urgencia').value;

			/*
			geber.costa
			a aba de Revisão de Problema Grave depende da prioridade
			, sempre que muda a prioridade irá mudar ou não a disponibilidade dos campos da aba
			*/

			if (urgencia == "B"){
				if (impacto == "B"){
					document.form.prioridade.value = 5;
				}else if (impacto == "M"){
					document.form.prioridade.value = 4;
				}else if (impacto == "A"){
					document.form.prioridade.value = 3;
				}
			}

			if (urgencia == "M"){
				if (impacto == "B"){
					document.form.prioridade.value = 4;
				}else if (impacto == "M"){
					document.form.prioridade.value = 3;
				}else if (impacto == "A"){
					document.form.prioridade.value = 2;
				}
			}

			if (urgencia == "A"){
				if (impacto == "B"){
					document.form.prioridade.value = 3;
				}else if (impacto == "M"){
					document.form.prioridade.value = 2;
				}else if (impacto == "A"){
					document.form.prioridade.value = 1;
				}
			}
		}

		/*
		 * Funções auxílio CRUD
		 */

		function limpar(form){
			try{
				form.clear();
			}catch(e){}
			//limparFCKEditores();
		}

		function validarDatas(){
			var inputs = document.getElementsByClassName("datepicker");
			var input = null;
			var errorMsg = i18n_message("citcorpore.comum.nenhumaDataDeveSerInferiorHoje");

			for(var i = 0; i < inputs.length; i++){
				input = inputs[i];

				if(input == null){
					continue;
				}

				if(comparaComDataAtual(input) < 0){
					alert(errorMsg);
					input.focus();
					throw errorMsg;
				}
			}
		}

		function fecharMudanca(){
			popupManagerRequisicaoMudanca.fecharPopup();
		}

		function gravar(form){

			document.form.itensConfiguracaoRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoICs.getTableObjects());
			document.form.solicitacaoServicoSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoSolicitacaoServico.getTableObjects());
			document.form.requisicaoMudancaSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoMudancas.getTableObjects());
			serializaProblema();
			if(properties.id !=null){
			document.form.idBaseConhecimento.value = properties.id;
			 }

			if (document.form.idContrato.value == '' || document.form.idContrato.value == ' ' || document.form.idContrato.value == undefined || document.form.idContrato.value == null){
				alert(i18n_message("problema.contrato") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
				return;
			}

			if (document.form.idUnidade.value == '0' || document.form.idUnidade.value == '' || document.form.idUnidade.value == ' ' || document.form.idUnidade.value == undefined || document.form.idUnidade.value == null){
				alert(i18n_message("unidade.unidade") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
				return;
			}

			var informacoesComplementares_serialize = '';
			try{
				informacoesComplementares_serialize = window.frames["fraInformacoesComplementares"].getObjetoSerializado();
			}catch(e){}


			document.form.informacoesComplementares_serialize.value = informacoesComplementares_serialize;


			validarDatas();
			document.form.save();
		}

		function validafkeditor(){
			var oEditor = FCKeditorAPI.GetInstance("descricao") ;
			document.form.descricao.value = oEditor.GetXHTML();
			document.form.descricao.innerHTML = oEditor.GetXHTML();

			if(document.form.descricao.innerHTML == "<br />" || document.form.descricao.innerHTML == "&lt;br /&gt;"){
				alert(i18n_message("problema.informe_descricao"));
				return false;
			}

			if (document.form.descricao.value == '' || document.form.descricao.value == '&nbsp;'
				|| document.form.descricao.value == '<p></p>'){
				alert(i18n_message("problema.informe_descricao"));
				return false;
			}

			return true;
		}



		function restaurar(){
			var listaICs = ObjectUtils.deserializeCollectionFromString(document.form.itensConfiguracaoRelacionadosSerializado.value);
			var listaSolicitacaoServico = ObjectUtils.deserializeCollectionFromString(document.form.solicitacaoServicoSerializado.value);
			var listaRequisicaoMudanca = ObjectUtils.deserializeCollectionFromString(document.form.requisicaoMudancaSerializado.value);
			limpaListasRelacionamentos();

			if(listaICs.length > 0){
				for(var i = 0; i < listaICs.length; i++){
					tabelaRelacionamentoICs.addObject([
					                   				   listaICs[i].idItemConfiguracao,
					                   				   listaICs[i].nomeItemConfiguracao,
					                   				   listaICs[i].descricaoProblema,
					                   				   getBotaoMostrarServicosRelacionados(listaICs[i].idItemConfiguracao)
					                   				  ]);


				}
			}
			if(listaSolicitacaoServico.length > 0){
				for(var i = 0; i < listaSolicitacaoServico.length; i++){
					tabelaRelacionamentoSolicitacaoServico.addObject([
					                   				    listaSolicitacaoServico[i].idSolicitacaoServico,
						                   				listaSolicitacaoServico[i].nomeServico,
					                   				  ]);


				}
			}

			if(listaRequisicaoMudanca.length > 0){
				for(var i = 0; i < listaRequisicaoMudanca.length; i++){
					tabelaRelacionamentoMudancas.addObject([
					                   				    listaRequisicaoMudanca[i].idRequisicaoMudanca,
					                   				 	listaRequisicaoMudanca[i].titulo,
					                   				 	listaRequisicaoMudanca[i].status,
					                   				  ]);


				}
			}
			/*
			geber.costa

			Na hora de restaurar o problema será verificado a prioridade
			para validar a aba de Revisão de problema de Grave
			*/
			atualizaPrioridade();
		}

		function getBotaoMostrarServicosRelacionados(idItemConfiguracao){
			var botao = new Image();

			botao.src = properties.ctx + '/template_new/images/icons/small/grey/magnifying_glass.png';
			botao.setAttribute("style", "cursor: pointer;");
			botao.id = idItemConfiguracao;
			botao.addEventListener("click", function(evt){
				$("#popupServicosRelacionados").dialog("open");
				//fireevent
			}, true);

			return botao;
		}

		function mostrarServicosRelacionados(idItemConfiguracao){
			alert(idItemConfiguracao);
		}

		function deletar(){
			if(confirm(i18n_message("problema.deseja_deletar"))){
				document.form.fireEvent("delete");
			}
		}

		/**
		 * Ajusta dados dos textareas com fckeditor ao restaurar.
		 */
		function restauraFckEditores(){
			var textAreaList = document.getElementsByTagName("textarea");

			for(var i = 0; i < textAreaList.length; i++){
				if(textAreaList[i].id != null){

					fckEditorAux = FCKeditorAPI.GetInstance( textAreaList[i].id );

					if(fckEditorAux != null){
						try{
							fckEditorAux.SetData(document.getElementById( textAreaList[i].id ).value);
						}catch(e){}
					}
				}
			}
		}

	 	/*
	 	 * Reaproveitamento da lookup EMPREGADO
	 	 */

		function selecionarCriador(){
			limpar_LOOKUP_EMPREGADO();
			LOOKUP_EMPREGADO_select =  function (id, desc){
				document.form.idCriador.value = id;
				document.form.nomeCriador.value = desc.split("-")[0];
				$("#POPUP_EMPREGADO").dialog("close");
			}

			$("#POPUP_EMPREGADO").dialog("open");
		}

		function selecionarProprietario(){
			limpar_LOOKUP_EMPREGADO();
			LOOKUP_EMPREGADO_select =  function (id, desc){
				document.form.idProprietario.value = id;
				document.form.nomeProprietario.value = desc.split("-")[0];
				$("#POPUP_EMPREGADO").dialog("close");
			}

			$("#POPUP_EMPREGADO").dialog("open");
		}


		function restaurarRegistro(idRegistro){
			mostrarFormulario();
			document.form.fireEvent("restore");
		}

		/*
		 * Funções de relacionamento
		 */

		//adicionar ics
		function LOOKUP_ITEMCONFIGURACAO_select(id, desc) {

			addLinhaTabelaItemConfiguracao(id, desc, true);

		}

		function addLinhaTabelaItemConfiguracao(id, desc, valida){
			var tbl = document.getElementById('tblICs');
			tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			if (valida){
				if (!validaAddLinhaTabelaItemConfiguracao(lastRow, id)){
					return;
				}
			}

			tabelaRelacionamentoICs.addObject([id, desc, prompt(i18n_message("problema.descrevaResumidamenteProblema"),""), getBotaoMostrarServicosRelacionados(id)]);

			document.form.itensConfiguracaoRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoICs.getTableObjects());

			$("#POPUPITEMCONFIGURACAO").dialog("close");

		}

		function validaAddLinhaTabelaItemConfiguracao(lastRow, id){
			var listaICs = ObjectUtils.deserializeCollectionFromString(document.form.itensConfiguracaoRelacionadosSerializado.value);

			if (lastRow > 1){
				for(var i = 0; i < listaICs.length; i++){
					if (listaICs[i].idItemConfiguracao == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return false;
					}
				}
			}
			return true;
		}

		function selecionaIcPeloMapa(id, desc){

			tabelaRelacionamentoICs.addObject([id, desc, prompt(i18n_message("problema.descrevaResumidamenteProblema"),""), getBotaoMostrarServicosRelacionados(id)]);

			$("#popupCadastroRapido").dialog("close");
		}


		function LOOKUP_PROBLEMA_EMEXECUCAO_select(id, desc) {
			document.form.idProblemaRelacionado.value = id;
			document.form.fireEvent('atualizaGridProblemaRelacionados');

		}

		exibeIconesProblema = function(row, obj){
			var id = obj.idProblema;
		    obj.sequenciaOS = row.rowIndex;
	   		 row.cells[2].innerHTML = '<img src="' + properties.requestpage +'/imagens/delete.png" border="0" onclick="excluiProblema(this.parentNode.parentNode.rowIndex,this)" style="cursor:pointer" />'
		};

		excluiProblema = function(indice) {
			if (indice > 0 && confirm('Confirma exclusão?')) {
				HTMLUtils.deleteRow('tblProblema', indice);
			}
		}

		function cadastrarProblema(){
			document.getElementById('iframeEditarCadastrarProblema').src = properties.ctx + "/pages/problema/problema.load?iframe=true&relacionarProblema=S&tarefaAssociada=N";
			$("#POPUP_IFRAMEPROBLEMARELACIONADO").dialog("open");
		}

		function resize_iframe()
		{
			var height=window.innerWidth;//Firefox
			if (document.body.clientHeight)
			{
				height=document.body.clientHeight;//IE
			}
			document.getElementById("iframeEditarCadastrarProblema").style.height=parseInt(height - document.getElementById("iframeEditarCadastrarProblema").offsetTop-135)+"px";
		}


		$(function() {
			$("#POPUP_IFRAMEPROBLEMARELACIONADO").dialog({
				autoOpen : false,
				width : "98%",
				height : $(window).height()-100,
				modal : true
			});
		});

		function LOOKUP_MUDANCA_select(id, desc) {

			addLinhaTabelaMudanca(id, desc, true);

		}

		function addLinhaTabelaMudanca(id, desc, valida){
			var tbl = document.getElementById('tblRDM');
			tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			if (valida){
				if (!validaAddLinhaTabelaMudanca(lastRow, id)){
					return;
				}
			}

			var camposLookupItem = desc.split("-");
			tabelaRelacionamentoMudancas.addObject([id, camposLookupItem[1], camposLookupItem[2]]);

			document.form.requisicaoMudancaSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoMudancas.getTableObjects());

			$("#POPUP_REQUISICAOMUDANCA").dialog("close");

		}

		function validaAddLinhaTabelaMudanca(lastRow, id){
			var listaRequisicaoMudanca = ObjectUtils.deserializeCollectionFromString(document.form.requisicaoMudancaSerializado.value);

			if (lastRow > 1){
				for(var i = 0; i < listaRequisicaoMudanca.length; i++){
					if (listaRequisicaoMudanca[i].idRequisicaoMudanca == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return false;
					}
				}
			}
			return true;
		}

		function abrePopupIcs(){
			$("#POPUPITEMCONFIGURACAO").dialog("open");
		}

		function adicionarIC(){
			abrePopupIcs();
		}

		function addProblemaRelacionado(){
			$("#POPUP_PESQUISARPROBLEMA").dialog("open");
		}

		function fecharModalProblema(){
			$("#POPUP_PESQUISARPROBLEMA").dialog("close");
		}

		function pesquisarRequisicaoMudanca(){
			abrePopupRequisicaoMudanca();
		}

		function abrePopupRequisicaoMudanca(){
			$("#POPUP_REQUISICAOMUDANCA").dialog("open");
		}

		function alternaVisibilidadePropsBase(){
			var check = document.getElementById("adicionarBDCE");
			var propsBaseConhecimento = null;

			if(check.checked){
				propsBaseConhecimento = document.getElementById("propsBaseConhecimento");
				$("#propsBaseConhecimento").show("clip");
			} else {
				$("#propsBaseConhecimento").hide("clip");
			}
		}

		fechar = function(){
			parent.fecharProblema();
		}

	    function fecharProblema(){
	    	$("#POPUP_IFRAMEPROBLEMARELACIONADO").dialog("close");
	    }


		function addGridProblemaRelacionado(id){
			document.form.idProblemaRelacionado.value = id;
			document.form.fireEvent('atualizaGridProblemaRelacionados');

		}

		function getServicos(){
			document.form.fireEvent('carregaServicosMulti');
		}

		 $(function() {
				$("#addSolicitante").click(function() {
					if (document.form.idContrato.value == ''){
						alert('Informe o contrato!');
						document.form.idContrato.focus();
						return;
					}
					var y = document.getElementsByName("btnLimparLOOKUP_SOL_CONTRATO");
					y[0].style.display = 'none';
					$("#POPUP_SOLICITANTE").dialog("open");
				});
			});

		 function setaValorLookup(obj){
			document.form.idSolicitante.value = '';
			document.form.solicitante.value = '';
			document.form.emailContato.value = '';
	 		document.form.nomeContato.value = '';
			document.form.telefoneContato.value = '';
			document.form.observacao.value = '';
			document.form.ramal.value = '';
			document.getElementById('idLocalidade').options.length = 0;
			document.getElementById("idUnidade").value= "0";

			if (document.getElementById("unidadeDes")!=null){
				document.getElementById("unidadeDes").value= "";
			}

			document.getElementById('pesqLockupLOOKUP_SOL_CONTRATO_IDCONTRATO').value = '';
			document.getElementById('pesqLockupLOOKUP_SOL_CONTRATO_IDCONTRATO').value = (typeof(obj) == "object" ? obj.value : obj);

		}

		function LOOKUP_SOL_CONTRATO_select(id, desc){
				document.form.idSolicitante.value = id;
				document.form.fireEvent("restoreColaboradorSolicitante");
		}

		function selecionarSolicitante(){
			LOOKUP_EMPREGADO_select =  function (id, desc){
				document.form.idSolicitante.value = id;
				document.form.solicitante.value = desc.split("-")[0];
				$("#POPUP_EMPREGADO").dialog("close");
			}

			$("#POPUP_EMPREGADO").dialog("open");
		}



		function mostrarEscondeRegExec(){
			if (document.getElementById('divMostraRegistroExecucao').style.display == 'none'){
				document.getElementById('divMostraRegistroExecucao').style.display = 'block';
				document.getElementById('lblMsgregistroexecucao').style.display = 'block';
				document.getElementById('btnAddRegExec').innerHTML = i18n_message("solicitacaoServico.addregistroexecucao_menos");
			}else{
				document.getElementById('divMostraRegistroExecucao').style.display = 'none';
				document.getElementById('lblMsgregistroexecucao').style.display = 'none';
				document.getElementById('btnAddRegExec').innerHTML = i18n_message("solicitacaoServico.addregistroexecucao_mais");
			}
		}


		function gravarErroConhecido(){
			var idProblema = document.form.idProblema.value;
			document.getElementById('iframeBaseConhecimento').src = properties.ctx + "/pages/baseConhecimento/baseConhecimento.load?iframe=true&erroConhecido=S&idProblema="+idProblema;
			$("#POPUP_BASECONHECIMENTO").dialog("open");
		}

		$(function() {
			$("#POPUP_BASECONHECIMENTO").dialog({
				autoOpen : false,
				width : "98%",
				height : $(window).height()-100,
				modal : true
			});
		});

		function resize_iframe(){
			var height=window.innerWidth;//Firefox
			if (document.body.clientHeight)
			{
				height=document.body.clientHeight;//IE
			}
			document.getElementById("iframeBaseConhecimento").style.height=parseInt(height - document.getElementById("iframeBaseConhecimento").offsetTop-8)+"px";
		}

		function fecharBaseConhecimento(){
			$("#POPUP_BASECONHECIMENTO").dialog("close");
			document.form.fireEvent("atualizaGridProblema");
		}

		exibeIconesEditarBaseConhecimento = function(row, obj){
			var id = obj.idBaseConhecimento;
	        obj.sequenciaOS = row.rowIndex;
	        row.cells[0].innerHTML = '<img src="' + properties.requestpage + '/imagens/edit.png" border="0" onclick="editarBaseConhecimento('
	        		+ id + ')" style="cursor:pointer" />';
		}

			/*geber.costa
		validador do checkbox acompanhamento
		*/
		function validarAcompanhamento(){

			if (document.getElementById('acompanhamento').checked == "N"){
				window.alert("sem acompanhamento!");
			}else if (document.getElementById('acompanhamento').checked == "S"){
				window.alert("com acompanhamento!");
			}
		}

		function editarBaseConhecimento(idBaseConhecimento){
			var idProblema = document.form.idProblema.value;
			document.getElementById('iframeBaseConhecimento').src = properties.ctx + "/pages/baseConhecimento/baseConhecimento.load?iframe=true&erroConhecido=S&idBaseConhecimento=" + idBaseConhecimento+"&idProblema="+idProblema;
			$("#POPUP_BASECONHECIMENTO").dialog("open");

		}

		function limpaListasRelacionamentos(){
			tabelaRelacionamentoICs.limpaLista();
			tabelaRelacionamentoSolicitacaoServico.limpaLista();
			tabelaRelacionamentoMudancas.limpaLista();

		}

		this.limpaLista = function(){
			tableObjects.length = 0;
			tableObjects = null;
			tableObjects = [];
			limpaTabela();
		}

		var limpaTabela = function(){
			while (getTabela().rows.length > 1){
				getTabela().deleteRow(1);
			}
		}

		function gravarEContinuar() {
			document.form.acaoFluxo.value = properties.acaoIniciar;
			gravar();
	    }


	    function gravarEFinalizar() {
			document.form.acaoFluxo.value = properties.acaoExecutar;
			document.form.fecharItensRelacionados.value = "N";

			if($("input[name='status']:checked").val() == 'Resolução'){
				verificarItensRelacionados(true)
			}else{
				document.form.fireEvent('validacaoAvancaFluxo');
			}
	    }

	 	function verificarItensRelacionados(validarItem){
	 		if(validarItem){
				document.form.fireEvent("verificarItensRelacionados");
				document.form.fecharItensRelacionados.value = "N";
		   }else{
			   if(confirm(i18n_message("citcorpore.comum.fecharItemRelacionados"))){
					document.form.fecharItensRelacionados.value = "S";
					document.form.fireEvent('validacaoAvancaFluxo');
				}else{
					document.form.fecharItensRelacionados.value = "N";
					document.form.fireEvent('validacaoAvancaFluxo');
				}
		    }
		}

		$(function() {

			// Manipulador de evento para o radio button de confirmação de solução de contorno.
			// Se S (sim), torna obrigatório o preenchimento do campo solução de contorno na aba Diagnóstico.
			$('input[type=radio][name=precisaSolucaoContorno][value="S"]').click(function() {

				$('#rotuloSolucaoContorno').addClass('campoObrigatorio');

			});

			$('input[type=radio][name=precisaSolucaoContorno][value="N"]').click(function() {

				$('#rotuloSolucaoContorno').removeClass('campoObrigatorio');

			});

			$('input[type=radio][name=precisaMudanca][value="S"]').click(function() {

				$('#abaMudancas').show();

				$('#relacionarMudancas').show();

			});

			$('input[type=radio][name=precisaMudanca][value="N"]').click(function() {

				$('#tabs').tabs('select', '#relacionaIcs');

				$('#abaMudancas').hide();

				$('#relacionarMudancas').hide();

			});

			$('input[type=radio][name=grave][value="S"]').click(function() {

				$('#abaRevisaoProblemaGrave').show();

				$('#relacionarRevisaoProblemaGrave').show();

			});

			$('input[type=radio][name=grave][value="N"]').click(function() {

				$('#tabs').tabs('select', '#relacionaIcs');

				$('#abaRevisaoProblemaGrave').hide();

				$('#relacionarRevisaoProblemaGrave').hide();


			});

		});
		function informaNumeroSolicitacao(numero){
			document.getElementById('divTituloProblema').innerHTML =
				'<h2>' + i18n_message("problema.numero") + '&nbsp;' + numero + '</h2>';
		}

		function chamaPopupCadastroSol(){
			if (document.form.idContrato.value == ''){
				alert(i18n_message("solicitacaoservico.validacao.contrato"));
				return;
			}
			var idContrato = 0;
			try{
				idContrato = document.form.idContrato.value;
			}catch(e){
			}
			dimensionaPopupCadastroRapido(1000, 530);
			popup2.abrePopupParms('empregado', '', 'idContrato=' + idContrato);
		}

		function fecharAddSolicitante(){
			$("#popupCadastroRapido").dialog('close');
		}

		function abreCadastroCategoriaProblema(){
			dimensionaPopupCadastroRapido(900, 500);
			popupCategoriaProblema.abrePopup('categoriaProblema', 'alimentaComboCatProblemaAposCadastro');
		}

		function gravarGestaoItemConfiguracao(idProblema){

			var id =  parseInt(idProblema);

			document.getElementById('iframeItemConfiguracao').src = properties.ctx + '/pages/gerenciaConfiguracaoTree/gerenciaConfiguracaoTree.load?iframeProblema=true&idProblema='+id;

			$("#POPUP_ITEMCONFIGURACAO").dialog("open");
		}
		$(function() {
			$("#POPUP_ITEMCONFIGURACAO").dialog({
				autoOpen : false,
				width : "98%",
				height : $(window).height()-100,
				modal : true
			});
		});

		$(function() {
			$("#POPUP_SOLUCAO_CONTORNO").dialog({
				title: i18n_message("problema.solucao_contorno"),
				autoOpen : false,
				width : "98%",
				height : $(window).height()-100,
				modal : true,
				show: "fade",
				hide: "fade"
			});

			$("#POPUP_NOVASOLICITACAOSERVICO").dialog({
				autoOpen : false,
				width : 1520,
				height : 800,
				modal : true
			});

		});

		function abrirPopupSolContorno(){
			$('#POPUP_SOLUCAO_CONTORNO').dialog("open");
		}

		function fecharSolContorno(){
			$("#POPUP_SOLUCAO_CONTORNO").dialog("close");
		}

		function gravarSolContorno() {
			var tituloSolucaoContorno = $('#tituloSolCon').val()
			$('#tituloSolucaoContorno').val(tituloSolucaoContorno);
			var solucaoContorno = $('#descSolCon').val();
			$('#solucaoContorno').val(solucaoContorno);
			document.form.fireEvent("gravarSolContorno");
		}

		$(function() {
			$("#POPUP_SOLUCAO_DEFINITIVA").dialog({
				title: i18n_message("problema.solucao_definitiva"),
				autoOpen : false,
				width : "98%",
				height : $(window).height()-100,
				modal : true,
				show: "fade",
				hide: "fade"
			});
		});

		function abrirPopupSolDefinitiva(){
			$('#POPUP_SOLUCAO_DEFINITIVA').dialog("open");
		}

		function fecharSolDefinitiva(){
			$("#POPUP_SOLUCAO_DEFINITIVA").dialog("close");
		}

		function gravarSolDefinitiva() {
			var tituloSolucaoDefinitiva = $('#tituloSolDefinitiva').val()
			$('#tituloSolucaoDefinitiva').val(tituloSolucaoDefinitiva);
			var solucaoDefinitiva = $('#descSolDefinitiva').val();
			$('#solucaoDefinitiva').val(solucaoDefinitiva);
			document.form.fireEvent("gravarSolDefinitiva");
		}

		function fecharFrameProblema(){
			limpar(document.form);
			parent.fecharFrameProblema();
		}

		function carregarInformacoesComplementares() {
            document.form.fireEvent('carregaInformacoesComplementares');
        }

		function exibirInformacoesComplementares(url) {
            if (url != '') {
                JANELA_AGUARDE_MENU.show();
                document.getElementById('divInformacoesComplementares').style.display = 'block';
                document.getElementById('fraInformacoesComplementares').src = url;
            }else{
                try{
                	escondeJanelaAguarde();
                }catch (e) {
                }
                document.getElementById('divInformacoesComplementares').style.display = 'none';
            }
        }

		function exibirInformacoesAprovacao(url) {
            if (url != '') {
            	var urlAtual = window.location;
    			urlAtual = urlAtual.toString();
    			var n = urlAtual.indexOf("pages/");
    			n = n + 6;
    			var urlFinal = urlAtual.substring(0, n);
    			url = urlFinal + url;
                JANELA_AGUARDE_MENU.show();
                document.getElementById('divInformacoesComplementares').style.display = 'block';
                document.getElementById('fraInformacoesComplementares').src = url;
            }else{
                try{
                	escondeJanelaAguarde();
                }catch (e) {
                }
                document.getElementById('divInformacoesComplementares').style.display = 'none';
            }
        }

		function escondeJanelaAguarde() {
            JANELA_AGUARDE_MENU.hide();
        }

		function mostraMensagemInsercao(msg, id){
			document.getElementById('divMensagemInsercao').innerHTML = msg;
			$("#POPUP_INFO_INSERCAO").dialog("open");
		}

		function verificaRelacionado (idproblema) {
			if (properties.relacionarProblema != null && properties.relacionarProblema == "S") {
				parent.addGridProblemaRelacionado(idproblema);
			}
		}

		function verificaContrato() {
			if (document.form.idContrato.value == ''){
				alert(i18n_message("solicitacaoservico.validacao.contrato"));
				return;
			}
		}
		function restoreImpactoUrgenciaPorTipoProblema(){
    	   document.form.fireEvent('restoreImpactoUrgenciaPorCategoriaProblema');
       	}

		$(function() {
		   $('#telefoneContato').mask('(999) 9999-9999');
		});

		function dimensionaPopupCadastroRapido(w, h) {
			$("#popupCadastroRapido").dialog("option","width", w);
			$("#popupCadastroRapido").dialog("option","height", h);
		}

		function fecharModalFilha() {
			$("#POPUP_NOVASOLICITACAOSERVICO").dialog("close");
		}

		function pesquisarItensFiltro() {
			$("#POPUP_NOVASOLICITACAOSERVICO").dialog("close");
		}

		function abrirPopupNovaSolicitacaoServico(){
			document.getElementById('iframeNovaSolicitacao').src = properties.ctx + "/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?iframe=true";
			redimencionarTamhanho();
			$("#POPUP_NOVASOLICITACAOSERVICO").dialog("open");
		}
		/**
		* Motivo: Redimenciona a popup em tamanho compativel com o tamanho da tela
		* Autor: flavio.santana
		* Data/Hora: 02/11/2013 15:35
		*/
		function redimencionarTamhanho(){
			var altura = parseInt($(window).height() * 0.75);
			var largura = parseInt($(window).width() * 0.75);
			$("#POPUP_NOVASOLICITACAOSERVICO").dialog("option","width", largura)
			$("#POPUP_NOVASOLICITACAOSERVICO").dialog("option","height", altura)
		}

		function abrirModalPesquisaItemConfiguracao(){

			var h;
			var w;
			w = parseInt($(window).width() * 0.75);
			h = parseInt($(window).height() * 0.85);

			document.getElementById('framePesquisaItemConfiguracao').src = properties.ctx + "/pages/pesquisaItemConfiguracao/pesquisaItemConfiguracao.load?iframe=true";

			$("#POPUP_PESQUISAITEMCONFIGURACAO").dialog("open");
		}

		function selectedItemConfiguracao(id){
			document.getElementById('hiddenIdItemConfiguracao').value = id;
			document.form.fireEvent("restaurarItemConfiguracao");
		}

		function atualizarTabelaICs(id, desc) {
			addLinhaTabelaItemConfiguracao(id, desc, true);
		}

		function serializaProblema(){
	    	var problemas = HTMLUtils.getObjectsByTableId('tblProblema');
			document.form.colItensProblema_Serialize.value =  ObjectUtils.serializeObjects(problemas);
			}

		function setEmail(){
			if (document.form.idContrato.value == ''){
				alert('Informe o contrato!');
				return;
			}

			document.formEmail.idContrato.value = document.form.idContrato.value;
			JANELA_AGUARDE_MENU.show();

			document.formEmail.fireEvent('loadMails');
		}

		function setDescricao(descricao){
			descricao = stripHtml(descricao);
			descricao = descricao.replace(/<!--[\s\S]*?-->/g, "");
			descricao = descricao.trim();

			document.form.descricao.value = descricao;
		}

		function copiaEmail(messageNumber){
			JANELA_AGUARDE_MENU.show();
			document.formEmail.emailMessageId.value = document.getElementById('emailMessageId' + messageNumber).value;
			document.formEmail.fireEvent('readMail');
		}

		function fechaModalLeituraEmail(){
			$("#POPUP_leituraEmails").dialog("close");
		}

		function toggleClass(classe, type){
			if (type == 'show') {
				$('.' + classe).show();
			} else if (type == 'hide') {
				$('.' + classe).hide();
			}
		}

		function stripHtml(html) {
		   var tmp = document.createElement("DIV");
		   tmp.innerHTML = html;
		   return tmp.textContent || tmp.innerText || "";
		}

		function htmlDecode(input){
			var e = document.createElement('div');
			e.innerHTML = input;
			return e.childNodes.length === 0 ? "" : e.childNodes[0].nodeValue;
		}