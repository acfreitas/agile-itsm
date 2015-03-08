	    $.fx.speeds._default = 1000;
		var myLayout;
		var popupManager;
		var LOOKUP_EMPREGADO_select;
		var popupManagerSolicitacaoServico;
		var cadastroRisco;

		var slickGridColunas;
		var slickGridOptions;
		var slickGridTabela;

		var tabelaRelacionamentoICs;
		var tabelaRelacionamentoServicos;
		var tabelaRelacionamentoLiberacao;
		var tabelaProblema;
		var tabelaRelacionamentoSolicitacaoServico;
		var tabelaRisco;
		var tabelaGrupo;

		var count = 0;
		var popup2;

		var idItemConfiguracao;
		var descricaoItemConfiguracao;
		var descricaoTratada;

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


		$(document).ready(function(){
		  $('#horaAgendamentoFinal').mask('99:99');
		  $('#horaAgendamentoInicial').mask('99:99');
		  $('#telefoneContato').mask('(999) 9999-9999');
		});

		function setarDescricao(){
			var txt = $("#DescricaoAuxliar").text();
			$("#DescricaoAuxliar").empty();
			$("#descricao").val(txt)
		}

		function zerarContadores(){
			 count = 0 ;
		}

		fecharInformacoesItemConfiguracao = function(){
			$( "#POPUP_INFORMACOESITEMCONFIGURACAO" ).dialog( 'close' );
		};

		/*Gravar baseline*/
		function gravarBaseline() {
			var tabela = document.getElementById('tblBaselines');
			var count = tabela.rows.length;
			var contadorAux = 0;
			var baselines = new Array();

			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idHistoricoMudanca' + i);
				if (!trObj) {
					continue;
				}
				baselines[contadorAux] = getbaseline(i);
				contadorAux = contadorAux + 1;
			}
			serializaBaseline();
			document.form.fireEvent("saveBaseline");
			document.form.fireEvent("restoreColaboradorSolicitante");
		}

		var seqBaseline = '';
		var aux = '';
		serializaBaseline = function() {
			var tabela = document.getElementById('tblBaselines');
			var count = tabela.rows.length;
			var contadorAux = 0;
			var baselines = new Array();
			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idHistoricoMudanca' + i);
				if (!trObj) {
					continue;
				}else if(trObj.checked){
					baselines[contadorAux] = getbaseline(i);
					contadorAux = contadorAux + 1;
					continue;
				}

			}
			var baselinesSerializadas = ObjectUtils.serializeObjects(baselines);
			document.form.baselinesSerializadas.value = baselinesSerializadas;
			return true;
		}

		getbaseline = function(seq) {
			var HistoricoMudancaDTO = new CIT_HistoricoMudancaDTO();
			HistoricoMudancaDTO.sequencia = seq;
			HistoricoMudancaDTO.idHistoricoMudanca = eval('document.form.idHistoricoMudanca' + seq + '.value');
			return HistoricoMudancaDTO;
		}
		function marcarCheckbox(elementos){
			var arrayIds = new Array();
			arrayIds = elementos;
			for ( var i = 0; i <= arrayIds.length; i++) {
				var posicao = arrayIds[i];
				$("#posicao").attr("checked",true);
			}

		}

		function restaurarHistorico(id){
			document.form.idHistoricoMudanca.value = id;
			if(confirm(i18n_message("itemConfiguracaoTree.restaurarVersao")))
				document.form.fireEvent("restaurarBaseline");
		}

		$(document).ready(function () {
	    		initPopups();
	    		initTabelasRelacionamentos();

	    		/* para visualização rápida do mapaDesenhoServico */
	    		popupManager = new PopupManager(window.screen.width - 100 , window.screen.height - 100, ctx+"/pages/");
	    		/* solicitcaoservico */
	    		popupManagerSolicitacaoServico = new PopupManager(window.screen.width - 100 , window.screen.height - 100, ctx+"/pages/");
	    		/* popup do solicitante */
	    		popup2 = new PopupManager(900, 450, ctx+"/pages/");

	    		cadastroRisco = new PopupManager(900, 450, ctx+"/pages/");


	    		document.form.afterRestore = function() {
	    			$('.tabs').tabs('select', 0);
	    		}

	    		// Murilo Almeida Pacheco - 25/10/2013 09:55 - Sol. 122294 - Popup abrindo com barra de rolagem sem necessidade alterando a altura da popup. 
	    		$("#POPUP_SOLICITANTE").dialog({
					autoOpen : false,
					width : 570,
					height : 730,
					modal : true
				});

	    		$("#POPUP_RESPONSAVEL").dialog({
	    			autoOpen : false,
	    			width : 875,
	    			height : 680,
	    			modal : true,
	    			show: "fade",
	    			hide: "fade"
	    		});

	    		$( "#POPUP_INFORMACOESITEMCONFIGURACAO" ).dialog({
	    			title: i18n_message("citcorpore.comum.visao"),
	    			width: 900,
	    			height: 600,
	    			modal: true,
	    			autoOpen: false,
	    			resizable: false,
	    			show: "fade",
	    			hide: "fade"
	    			});

	    		$("#POPUP_INFORMACOESITEMCONFIGURACAO").hide();

	    });

		/* add responsavel */
	    function addResponsavel(id, nome, cargo, tel, email ){
	        var obj = new CIT_RequisicaoMudancaResponsavelDTO();

			// Murilo Almeida Pacheco - 25/10/2013 11:05 - Sol. 122294 - Ao adicionar o responsável em papeis e responsabilidades estava vindo undefined fiz o tratamento para não aparecer mais assim. 
	        if((cargo == "") || (cargo == undefined)){
	    		cargo = "N/A"
	    	}

	        if((tel == "") || (tel == "undefined-undefined")){
	        	tel = "N/A"
	    	}

	        if((email == "") || (email == undefined)){
	        	email = "N/A"
	    	}


	        document.getElementById('responsavel#idResponsavel').value = id;
	        document.getElementById('responsavel#nomeResponsavel').value = nome;
	        document.getElementById('responsavel#nomeCargo').value = cargo;
	        document.getElementById('responsavel#telResponsavel').value = tel;
	        document.getElementById('responsavel#emailResponsavel').value = email;
	        document.getElementById('responsavel#papelResponsavel').value = prompt(i18n_message("citcorpore.comum.papel"),"");

	        HTMLUtils.addRow('tblResponsavel', document.form, 'responsavel', obj,
	                ["","idResponsavel","nomeResponsavel", "nomeCargo", "telResponsavel", "emailResponsavel" , "papelResponsavel" ], ["idResponsavel"], null, [gerarImgDelResponsavel], null, null, false);
	    	$("#POPUP_RESPONSAVEL").dialog("close");
		}

	    function adicionarResponsavel(){
			$("#POPUP_RESPONSAVEL").dialog("open");
		}

	   function verificaIdSolicitacaoNaURL(){
	    	var idRequisicao = extrairVariavelDaUrl("idRequisicao");

   		 	if(idRequisicao != null && idRequisicao != ""){
	    		restaurarRequisicao(idRequisicao)
   		 	}
	   }

	   function mostraMensagemInsercao(msg){
			document.getElementById('divMensagemInsercao').innerHTML = msg;
			$("#POPUP_INFO_INSERCAO").dialog("open");
		}

	   $(function() {
			$("#addSolicitante").click(function() {
				if (document.form.idContrato.value == ''){
					alert(i18n_message("contrato.alerta.informe_contrato"));
					return;
				}
				/* var v = document.getElementsByName("btnTodosLOOKUP_SOL_CONTRATO");
				v[0].style.display = 'none'; */
				var y = document.getElementsByName("btnLimparLOOKUP_SOL_CONTRATO");
				y[0].style.display = 'none';
				$("#POPUP_SOLICITANTE").dialog("open");
			});
		});

	   function pesquisarAD(){
			JANELA_AGUARDE_MENU.show();
			document.form.filtroADPesq.value = $("#filtroADPesqAux").val();
			if($("#filtroADPesqAux").val() == ""){
				alert(i18n_message("login.digite_login"));
				$("#filtroADPesqAux").focus();
				JANELA_AGUARDE_MENU.hide();
				return;
			}
			if($("#idContrato").val() == ""){
				alert(i18n_message("ss.escolhaContrato"));
				$("#idContrato").focus();
				JANELA_AGUARDE_MENU.hide();
				return;
			}
			document.form.fireEvent("sincronizaAD");
		}
		function initTabelasRelacionamentos(){
			/* ICs */
			tabelaRelacionamentoICs = new CITTable("tblICs",["idItemConfiguracao", "nomeItemConfiguracao", "descricao"],[]);
			tabelaRelacionamentoICs.setInsereBotaoInformacoes(true, ctx+"/imagens/order.png");
			if(display == ""){
				tabelaRelacionamentoICs.setInsereBotaoExcluir(true, ctx+"/imagens/delete.png");
			}else{
				tabelaRelacionamentoICs.setInsereBotaoExcluir(false, ctx+"/imagens/delete.png");
			}

			/* Solicitacaoservico */
			tabelaRelacionamentoSolicitacaoServico = new CITTable("tblSolicitacaoServico",["idSolicitacaoServico", "nomeServico"],[]);
			if(display == ""){
				tabelaRelacionamentoSolicitacaoServico.setInsereBotaoExcluir(true, ctx+"/imagens/delete.png");
			}else{
				tabelaRelacionamentoSolicitacaoServico.setInsereBotaoExcluir(false, ctx+"/imagens/delete.png");
			}

			/* servicos */
			tabelaRelacionamentoServicos = new CITTable("tblServicos",["idServico", "Nome", "Mapa", "Descrição"],[]);
			if(display == ""){
				tabelaRelacionamentoServicos.setInsereBotaoExcluir(true, ctx+"/imagens/delete.png");
			}else{
				tabelaRelacionamentoServicos.setInsereBotaoExcluir(false, ctx+"/imagens/delete.png");
			}

			/* problemas */
			tabelaProblema = new CITTable("tblProblema",["idProblema", "titulo", "status","Editar"],[]);
			if(display == ""){
				tabelaProblema.setInsereBotaoExcluir(true, ctx+"/imagens/delete.png");
			}else{
				tabelaProblema.setInsereBotaoExcluir(false, ctx+"/imagens/delete.png");
			}

			/* Liberação */
			tabelaRelacionamentoLiberacao = new CITTable("tblLiberacao",["idLiberacao", "titulo", "descricao","status"],[]);
			if(display == ""){
				tabelaRelacionamentoLiberacao.setInsereBotaoExcluir(true, ctx+"/imagens/delete.png");
			}else{
				tabelaRelacionamentoLiberacao.setInsereBotaoExcluir(false, ctx+"/imagens/delete.png");
			}

			/* Risco */
			tabelaRisco = new CITTable("tblRisco",["idRisco", "nomeRisco", "detalhamento"],[]);
			if(display == ""){
				tabelaRisco.setInsereBotaoExcluir(true, ctx+"/imagens/delete.png");
			}else{
				tabelaRisco.setInsereBotaoExcluir(false, ctx+"/imagens/delete.png");
			}

		    /* Grupo */
			tabelaGrupo = new CITTable("tblGrupo",["idGrupo", "nomeGrupo"],[]);
			if(display == ""){
				tabelaGrupo.setInsereBotaoExcluir(true, ctx+"/imagens/delete.png");
			}else{
				tabelaGrupo.setInsereBotaoExcluir(false, ctx+"/imagens/delete.png");
			}
		}

	    function initTextEditor(editor){
	    	editor.BasePath = basePath;
	      	editor.Config['ToolbarStartExpanded'] = false;
	      	editor.Width = '100%';
	      	editor.ReplaceTextarea();
	    }

	    // Motivo: Redimenciona a popup em tamanho compativel com o tamanho da tela
		//Autor: flavio.santana
		// Data/Hora: 02/11/2013 15:35
		function redimensionarTamhanho(identificador, tipo_variacao){
			var h;
			var w;
			switch(tipo_variacao)
			{
			case "PEQUENO":
				w = parseInt($(window).width() * 0.25);
				h = parseInt($(window).height() * 0.35);
			  break;
			case "MEDIO":
				w = parseInt($(window).width() * 0.5);
				h = parseInt($(window).height() * 0.6);
			  break;
			case "GRANDE":
				w = parseInt($(window).width() * 0.75);
				h = parseInt($(window).height() * 0.85);
			  break;
			default:
				w = parseInt($(window).width() * 0.5);
				h = parseInt($(window).height() * 0.6);
			}

			$(identificador).dialog("option","width", w)
			$(identificador).dialog("option","height", h)
		}

		function initPopups(){
			$(".POPUP_LOOKUP").dialog({
				autoOpen : false,
				width : 750,
				height : 700,
				modal : true
			});

			$(".POPUP_LOOKUP_ITEMCONFIGURACAO").dialog({
				autoOpen : false,
				width : 400,
				height : 200,
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
			$("#POPUP_INFO_BASELINE").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true,
				close: function(event, ui) {
				}
			});
			$(".POPUP_LOOKUP_SERVICO").dialog({
				autoOpen : false,
				width : 1000,
				height : 700,
				modal : true
			});
			$(".POPUP_LOOKUP_ICS").dialog({
				autoOpen : false,
				width : 850,
				height : 700,
				modal : true
			});
			$(".POPUP_LOOKUP_RISCO").dialog({
				autoOpen : false,
				width : 770,
				height : 650,
				modal : true
			});
			$(".POPUP_LOOKUP_LIBERACAO").dialog({
				autoOpen : false,
				width : 910,
				height : 650,
				modal : true
			});
		}

		function LOOKUP_SOL_CONTRATO_select(id, desc){
			document.form.idSolicitante.value = id;
			document.form.fireEvent("restoreColaboradorSolicitante");
		}

		function setaValorLookup(obj){
			document.form.idSolicitante.value = '';
			document.form.nomeSolicitante.value = '';
			document.form.emailSolicitante.value = '';
 			document.form.nomeContato.value = '';
			document.form.telefoneContato.value = '';
			document.form.observacao.value = '';
			document.form.ramal.value = '';
			document.getElementById('idLocalidade').options.length = 0;
			document.getElementById('pesqLockupLOOKUP_SOL_CONTRATO_IDCONTRATO').value = '';
			document.getElementById('pesqLockupLOOKUP_SOL_CONTRATO_IDCONTRATO').value = obj.value;
			document.form.fireEvent('carregaUnidade');
			document.getElementById("idUnidade").value= "0";
			if (document.getElementById("unidadeDes")!=null){
				document.getElementById("unidadeDes").value= "";
			}
		}


		// Funções de apoio

		 function extrairVariavelDaUrl(nome){
			var valor = null;
			var identificador = null;
			try{
				var strUrl = document.URL;
				var params = strUrl.split("")[1];
				var variaveis = params.split("&");
								for(var i = 0; i < variaveis.length; i++){
					valor = variaveis[i].split("=")[1];
					identificador = variaveis[i].split("=")[0];

					if(identificador == nome){
						return valor;
					}

					valor = null;
				}
			}catch(e){}

			return null;
		}

		function gerarImgDelResponsavel(row, obj){
		        row.cells[0].innerHTML = '<img src='+ctx+'/imagens/delete.png style="cursor: pointer;" onclick="deleteLinha(\'tblResponsavel\', this.parentNode.parentNode.rowIndex);"/>';
		};

		function deleteLinha(table, index){
			if (confirm(i18n_message("citcorpore.comum.confirmaExclusao"))) {
				teste = 'true';
				HTMLUtils.deleteRow(table, index);
				return;
			}
			teste = '';
		}

		function serializaResponsavel(){
	    	var responsavel = HTMLUtils.getObjectsByTableId('tblResponsavel');
			document.form.responsavel_serialize.value =  ObjectUtils.serializeObjects(responsavel);
			}

		function serializaProblema(){
	   		/* Serializando para pode trabalhar com quantidade atual de linhas na grid */
			document.form.problemaSerializado.value = ObjectUtils.serializeObjects(tabelaProblema.getTableObjects());
		}

		function limparFCKEditores(){
			var fckEditorAux;
			var textAreaList = document.getElementsByTagName("textarea");

			for(var i = 0; i < textAreaList.length; i++){
				if(textAreaList[i].id != null){

					fckEditorAux = FCKeditorAPI.GetInstance( textAreaList[i].id );

					if(fckEditorAux != null){
						try{
							fckEditorAux.SetData("");
						}catch(e){
							/* alert("Problemas com FCKEditor. " + e.message) */
						}
					}
				}
			}
		}

		function restaurarHistorico(id){
			document.form.idHistoricoMudanca.value = id;
			if(confirm(i18n_message("itemConfiguracaoTree.restaurarVersao")))
				document.form.fireEvent("restaurarBaseline");
		}
	 	function gravarEContinuar() {
			document.form.acaoFluxo.value = acaoIniciar;
			gravar();

	    }

	    function gravarEFinalizar() {
			document.form.acaoFluxo.value = acaoExecutar;
			document.form.fecharItensRelacionados.value = "N";
			if(document.form.fase.value == 'Avaliacao'){
				verificarItensRelacionados(true)
			}else{
				gravar();
			}
	    }

	 	function verificarItensRelacionados(validarItem){
			if(validarItem){
				document.form.fireEvent("verificarItensRelacionados");
				document.form.fecharItensRelacionados.value = "N";
		   }else{
			   if(confirm(i18n_message("citcorpore.comum.fecharItemRelacionados"))){
					document.form.fecharItensRelacionados.value = "S";
					gravar();
				}else{
					document.form.fecharItensRelacionados.value = "N";
					gravar();
				}
		    }
		}

		function limpaListasRelacionamentos(){
			tabelaRelacionamentoICs.limpaLista();
			tabelaRelacionamentoServicos.limpaLista();
			tabelaProblema.limpaLista();
			tabelaRelacionamentoSolicitacaoServico.limpaLista();
			tabelaRelacionamentoLiberacao.limpaLista();
			tabelaRisco.limpaLista();
			tabelaGrupo.limpaLista();

		}

		// INFLUENCIA PRIORIDADE 
		function atualizaPrioridade(){

			var impacto = document.getElementById('nivelImpacto').value;
			var urgencia = document.getElementById('nivelUrgencia').value;

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

		// Funções auxílio CRUD

		function limpar(form){
			try{
				form.clear();
			}catch(e){}

			limparFCKEditores();
			limpaListasRelacionamentos();
		}

		function validarDatas(){
			var inputs = document.getElementsByClassName("datepicker");
			var input = null;
			var errorMsg = i18n_message("citcorpore.comum.dataNaoDeveSerInferiorAtual") ;

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

		function gerarRelatorioPDF() {
			document.form.fireEvent("imprimirRelatorioReqMudanca");
		}

		// Ajusta dados dos textareas com fckeditor antes de gravar.
		function gravar(){

			if (document.getElementById('idUnidade').value == null || document.getElementById('idUnidade').value == undefined || document.getElementById('idUnidade').value == '0' || document.getElementById('idUnidade').value == '' || document.getElementById('idUnidade').value == ' '){
				alert(i18n_message("unidade.unidade") + i18n_message("citcorpore.comum.campo_obrigatorio"));
				return;
			}

			var statusAtual;

			for (var i = 0; i < document.getElementsByName('status').length; i++)
		    {
		    	if (document.getElementsByName('status')[i].checked)
		    	{
		    		statusAtual = document.getElementsByName('status')[i].value;
		    	}
		    }

			if (statusAtual == "Concluida"){
				if (document.form.fechamento.value == '' || document.form.fechamento.value == ' '){
					alert(i18n_message("citcorpore.comum.informeFechamento"));
					return;
				}
			}

			if (document.getElementById('dataHoraInicioAgendada').value != '' && document.getElementById('horaAgendamentoInicial').value == '') {
				alert(i18n_message("requisicaoMudanca.informacaoHoraInicialAgendada"));
				document.getElementById('horaAgendamentoInicial').focus();
				return;
			}

			if (document.getElementById('dataHoraInicioAgendada').value == '' && document.getElementById('horaAgendamentoInicial').value != '') {
				alert(i18n_message("requisicaoMudanca.informacaoDataInicioAgendada"));
				document.getElementById('dataHoraInicioAgendada').focus();
				return;
			}

			if (document.getElementById('dataHoraTerminoAgendada').value != '' && document.getElementById('horaAgendamentoFinal').value == '') {
				alert(i18n_message("requisicaoMudanca.informacaoHoraFinalAgendada"));
				document.getElementById('horaAgendamentoFinal').focus();
				return;
			}

			if (document.getElementById('dataHoraTerminoAgendada').value == '' && document.getElementById('horaAgendamentoFinal').value != '') {
				alert(i18n_message("requisicaoMudanca.informacaoDataTerminoAgendada"));
				document.getElementById('dataHoraTerminoAgendada').focus();
				return;
			}

			if (document.getElementById('dataHoraInicioAgendada').value != '' && document.getElementById('dataHoraTerminoAgendada').value == '') {
				alert(i18n_message("requisicaoMudanca.informacaoDataTerminoAgendada"));
				document.getElementById('dataHoraTerminoAgendada').focus();
				return;
			}

			if (document.getElementById('dataHoraTerminoAgendada').value != '' && document.getElementById('dataHoraInicioAgendada').value == '') {
				alert(i18n_message("requisicaoMudanca.informacaoDataInicioAgendada"));
				document.getElementById('dataHoraInicioAgendada').focus();
				return;
			}

			if (!id === null) {
			document.form.idBaseConhecimento.value = id;
			}
			document.form.itensConfiguracaoRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoICs.getTableObjects());
			document.form.servicosRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoServicos.getTableObjects());
			document.form.problemaSerializado.value = ObjectUtils.serializeObjects(tabelaProblema.getTableObjects());
			document.form.solicitacaoServicoSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoSolicitacaoServico.getTableObjects());
			document.form.liberacoesRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoLiberacao.getTableObjects());
			document.form.riscoSerializado.value = ObjectUtils.serializeObjects(tabelaRisco.getTableObjects());
			document.form.grupoMudancaSerializado.value = ObjectUtils.serializeObjects(tabelaGrupo.getTableObjects());

			serializaResponsavel();
			serializaAprovacoesProposta();
			serializaAprovacoesMudanca();

			var informacoesComplementares_serialize = '';
			try{
				informacoesComplementares_serialize = window.frames["fraInformacoesComplementares"].getObjetoSerializado();
			}catch(e){}
			if (document.form.informacoesComplementares_serialize != undefined) {
				document.form.informacoesComplementares_serialize.value = informacoesComplementares_serialize;
			}

			$("#contato").prop("disabled", false);
			$("#idGrupoComite").prop("disabled", false);
			$("#idGrupoAtual").prop("disabled", false);
			$("#idTipoMudanca").prop("disabled", false);
			$("#addSolicitante").prop("disabled", false);
			$("#idContrato").prop("disabled", false);

			validarDatas();

			document.form.save();
		}


		function restaurar(){
			var listaICs = ObjectUtils.deserializeCollectionFromString(document.form.itensConfiguracaoRelacionadosSerializado.value);
			var listaSolicitacaoServico = ObjectUtils.deserializeCollectionFromString(document.form.solicitacaoServicoSerializado.value);
			var listaServicos = ObjectUtils.deserializeCollectionFromString(document.form.servicosRelacionadosSerializado.value);
			var listaProblema = ObjectUtils.deserializeCollectionFromString(document.form.problemaSerializado.value);
			var listaLiberacoes = ObjectUtils.deserializeCollectionFromString(document.form.liberacoesRelacionadosSerializado.value);
			var listaRisco = ObjectUtils.deserializeCollectionFromString(document.form.riscoSerializado.value);
			var listaGrupo = ObjectUtils.deserializeCollectionFromString(document.form.grupoMudancaSerializado.value);
			limpaListasRelacionamentos();

			for(var i = 0; i < listaICs.length; i++){
				tabelaRelacionamentoICs.addObject([listaICs[i].idItemConfiguracao, listaICs[i].nomeItemConfiguracao , listaICs[i].descricao]);
			}

			for(var i = 0; i < listaServicos.length; i++){
				tabelaRelacionamentoServicos.addObject([listaServicos[i].idServico, listaServicos[i].nome , listaServicos[i].descricao, getBotaoVisualizarMapa(listaServicos[i].idServico)]);
			}

			for(var i = 0; i < listaLiberacoes.length; i++){
				tabelaRelacionamentoLiberacao.addObject([listaLiberacoes[i].idLiberacao, listaLiberacoes[i].titulo , listaLiberacoes[i].descricao,listaLiberacoes[i].status]);
			}

			 if(listaSolicitacaoServico.length > 0){
				for(var i = 0; i < listaSolicitacaoServico.length; i++){
					tabelaRelacionamentoSolicitacaoServico.addObject([listaSolicitacaoServico[i].idSolicitacaoServico, listaSolicitacaoServico[i].nomeServico]);
				}
			}

			 if(listaProblema.length > 0){
				for(var i = 0; i < listaProblema.length; i++){
					tabelaProblema.addObject([listaProblema[i].idProblema, listaProblema[i].titulo, listaProblema[i].status, getBotaoEditarProblema(listaProblema[i].idProblema)]);
				}
			}

			 if(listaRisco.length > 0){
				for(var i = 0; i < listaRisco.length; i++){
					tabelaRisco.addObject([listaRisco[i].idRisco, listaRisco[i].nomeRisco, listaRisco[i].detalhamento]);
				}
			}
			 if(listaGrupo.length > 0){
					for(var i = 0; i < listaGrupo.length; i++){
						tabelaGrupo.addObject([listaGrupo[i].idGrupo, listaGrupo[i].nomeGrupo]);
					}
				}
		}


		function deletar(){
			document.form.fireEvent("delete");
		}

		// Ajusta dados dos textareas com fckeditor ao restaurar. 
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

	 	// Reaproveitamento da lookup EMPREGADO
		function selecionarSolicitante(){
			LOOKUP_EMPREGADO_select =  function (id, desc){
				document.form.idSolicitante.value = id;
				document.form.nomeSolicitante.value = desc.split("-")[0];
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
		/*      -------------      */

		function restaurarRequisicao(idRequisicao){
			document.form.idRequisicaoMudanca.value = idRequisicao;
			document.form.fireEvent("restore");
		}

		// Funções de relacionamento

		
		// Adicionado por David, para validar a aba de item de configuração
		function LOOKUP_ITEMCONFIGURACAO_select(id, desc) {
			idItemConfiguracao = id;
			descricaoItemConfiguracao = desc;
			addLinhaTabelaItemConfiguracao(id, desc, true);

		}

		function addLinhaTabelaItemConfiguracao(id, desc, valida){
			var resultado = true;;
			var tbl = document.getElementById('tblICs');
			tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			if (valida){
				resultado = validaAddLinhaTabelaItemConfiguracao(lastRow, id);
				if (!resultado){
					return resultado;
				}
			}
			return resultado;

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


		function abrePopupIcs(){
			redimensionarTamhanho("#POPUPITEMCONFIGURACAO", "MEDIO");
			$("#POPUPITEMCONFIGURACAO").dialog("open");
		}

		function abrirModalPesquisaItemConfiguracao(){

			var h;
			var w;
			w = parseInt($(window).width() * 0.75);
			h = parseInt($(window).height() * 0.85);

			document.getElementById('framePesquisaItemConfiguracao').src = ctx+"/pages/pesquisaItemConfiguracao/pesquisaItemConfiguracao.load?iframe=true";

			$("#POPUP_PESQUISAITEMCONFIGURACAO").dialog("open");
		}

		function selectedItemConfiguracao(id){
			idItemConfiguracao = id;
			document.getElementById('hiddenIdItemConfiguracao').value = id;
			var tbl = document.getElementById('tblICs');
			tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			var resultado = true;
			resultado = validaAddLinhaTabelaItemConfiguracao(lastRow, id);

			if (resultado == true){
				abrePopupDescricaoIcs();
			}else{
				return;
			}
		}

		function abrePopupDescricaoIcs(){
			redimensionarTamhanho("#POPUPDESCRICAOITEMCONFIGURACAO", "PEQUENO");
			$("#POPUPDESCRICAOITEMCONFIGURACAO").dialog("open");
		}

		function adicionarIC(){
			abrePopupIcs();
		}

		//adicionar servicos
		function LOOKUP_SERVICO_select(id, desc) {
			addLinhaTabelaServicos(id, desc, true);

		};

		function LOOKUP_RESPONSAVEL_select(id, desc){
	        var str = desc.split('-');
	        addResponsavel(id, str[0], str[1], str[2]+"-"+str[3], str[4]);
	    }

		function addLinhaTabelaServicos(id, desc, valida){
			var tbl = document.getElementById('tblServicos');
			tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			if (valida){
				if (!validaAddLinhaTabelaServicos(lastRow, id)){
					return;
				}
			}

			var camposLookupServico = desc.split("-");
			tabelaRelacionamentoServicos.addObject([id, camposLookupServico[0], camposLookupServico[1], getBotaoVisualizarMapa(id)]);

			document.form.servicosRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoServicos.getTableObjects());

			$("#POPUP_SERVICO").dialog("close");

		}

		function validaAddLinhaTabelaServicos(lastRow, id){
			var listaServicos = ObjectUtils.deserializeCollectionFromString(document.form.servicosRelacionadosSerializado.value);

			if (lastRow > 1){
				for(var i = 0; i < listaServicos.length; i++){
					if (listaServicos[i].idServico == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return false;
					}
				}
			}
			return true;		}

		//adicionar problema

		function LOOKUP_LIBERACAO_MUDANCA_select(id, desc) {
 			document.getElementById('liberacao#idRequisicaoLiberacao').value = id;
			document.form.fireEvent("inserirRequisicaoLiberacao");

		};

		//Adiciona a linha da liberação
		function adicionaLiberacaoMudanca(idLiberacao,titulo,descricao,status){

			//Faz a validação para verificar pelo id que o registro já está adicionado
			var tbl = document.getElementById('tblLiberacao');
			tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			var valida = true;
			if (valida){
				if (!validaAddLinhaTabelaLiberacao(lastRow, idLiberacao)){
					return;
				}
			}

			tabelaRelacionamentoLiberacao.addObject([idLiberacao, titulo, descricao,status], [getBotaoVisualizarMapa(idLiberacao)]);

			document.form.liberacoesRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoLiberacao.getTableObjects());

			$("#POPUP_LIBERACAO").dialog("close");
		}

		function validaAddLinhaTabelaLiberacao(lastRow, id){
			var listaLiberacoes = ObjectUtils.deserializeCollectionFromString(document.form.liberacoesRelacionadosSerializado.value);

			if (lastRow > 1){
				for(var i = 0; i < listaLiberacoes.length; i++){
					if (listaLiberacoes[i].idLiberacao == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return false;
					}
				}
			}
			return true;
		}

		//adicionar problema
		function LOOKUP_PROBLEMA_select(id, desc) {
			addLinhaTabelaProblema(id, desc, true);

		};

		function LOOKUP_GRUPO_select(id, desc) {
			addLinhaTabelaGrupo(id, desc, true);

		};

		function addLinhaTabelaProblema(id, desc, valida){
			var tbl = document.getElementById('tblProblema');
			tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			if (valida){
				if (!validaAddLinhaTabelaProblema(lastRow, id)){
					return;
				}
			}

			desc = desc.replace(/['"]*/g, '');

			var camposLookupProblema = desc.split("-");
			tabelaProblema.addObject([id, camposLookupProblema[0], camposLookupProblema[1], getBotaoEditarProblema(id)]);

			document.form.problemaSerializado.value = ObjectUtils.serializeObjects(tabelaProblema.getTableObjects());

			$("#POPUP_PROBLEMA").dialog("close");

		}
		function validaAddLinhaTabelaProblema(lastRow, id){
			var listaProblema = ObjectUtils.deserializeCollectionFromString(document.form.problemaSerializado.value);

			if (lastRow > 1){
				for(var i = 0; i < listaProblema.length; i++){
					if (listaProblema[i].idProblema == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return false;
					}
				}
			}
			return true;
		}


		function addLinhaTabelaGrupo(id, desc, valida){
 			var tbl = document.getElementById('tblGrupo');
			tbl.style.display = 'block';
 			var lastRow = tbl.rows.length;
 			if (valida){
 				if (!validaAddLinhaTabelaGrupo(lastRow, id)){
 					return;
 				}
			}

 			desc = desc.replace(/['"]*/g, '');

 			var camposLookupGrupo = desc.split("-");
 			tabelaGrupo.addObject([id, camposLookupGrupo[0]]);

 			document.form.grupoMudancaSerializado.value = ObjectUtils.serializeObjects(tabelaGrupo.getTableObjects());

			$("#POPUP_GRUPO").dialog("close");

		}

		function validaAddLinhaTabelaGrupo(lastRow, id){
			var listaGrupo = ObjectUtils.deserializeCollectionFromString(document.form.grupoMudancaSerializado.value);

			if (lastRow > 1){
				for(var i = 0; i < listaGrupo.length; i++){
					if (listaGrupo[i].idGrupo == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return false;
					}
				}
			}
			return true;
		}


		function getBotaoVisualizarMapa(id){
			var botaoVisualizarMapa = new Image();

			botaoVisualizarMapa.src = botaoVisualizarMapa_src;
			botaoVisualizarMapa.setAttribute("style", "cursor: pointer;");
			botaoVisualizarMapa.id = id;
			botaoVisualizarMapa.addEventListener("click", function(evt){
				if(popupManager == null){
					alert(i18n_message("requisicaoMudanca.popupNaoConfigurada"));
				} else {
					popupManager.abrePopupParms('mapaDesenhoServico', '', '&idServico=' + this.id);
				}
			}, true);

			return botaoVisualizarMapa;
		}

		//adicionar Risco
		function LOOKUP_RISCO_select(id, desc) {
			addLinhaTabelaRisco(id, desc, true);

		};

		function addLinhaTabelaRisco(id, desc, valida){
			var tbl = document.getElementById('tblRisco');
			tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			if (valida){
				if (!validaAddLinhaTabelaRisco(lastRow, id)){
					return;
				}
			}

			var camposLookupRisco = desc.split("-");
			tabelaRisco.addObject([id, camposLookupRisco[0], camposLookupRisco[1]]);

			document.form.riscoSerializado.value = ObjectUtils.serializeObjects(tabelaRisco.getTableObjects());

			$("#POPUP_RISCO").dialog("close");

		}

		function validaAddLinhaTabelaRisco(lastRow, id){
			var listaRisco = ObjectUtils.deserializeCollectionFromString(document.form.riscoSerializado.value);

			if (lastRow > 1){
				for(var i = 0; i < listaRisco.length; i++){
					if (listaRisco[i].idRisco == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return false;
					}
				}
			}
			return true;
		}

		function abrePopupServicos(){
			$("#POPUP_SERVICO").dialog("open");
		}

		function abrePopupLiberacao(){
			$("#POPUP_LIBERACAO").dialog("open");
		}

		function abrePopupGrupo(){
			$("#POPUP_GRUPO").dialog("open");
		}

		function abrePopupRisco(){
			$("#POPUP_RISCO").dialog("open");
		}

		function abrePopupProblema(){
			limpar_LOOKUP_PROBLEMA();
			$("#POPUP_PROBLEMA").dialog("open");
		}

		function adicionarRisco(){
			abrePopupRisco();
		}

		function adicionarServico(){
			abrePopupServicos();
		}

		function adicionarProblema(){
			abrePopupProblema();
		
		//é necessário serializar para que requisicaoMudanca.java possa trabalhar com
		// quantidade atualizada de linhas na grid.
		 
			serializaProblema();
		}

		function adicionarLiberacao() {
			abrePopupLiberacao();
		}
		function adicionarGrupo() {
			abrePopupGrupo();
		}

		 //Funções alimentação tabelas de relacionamento

		 
		 // Renderiza tabela a partir de lista.
		 // @param _idCITTable id da tabela a ser tratada
		 // @param _fields Lista de campos correspondentes ao banco de dados
		 // @param _tableObjects Lista de itens. Deve corresponder aos campos de _fields
		 var contador = 0;
		 function CITTable(_idCITTable, _fields, _tableObjects){
				var self = this;
				var idCITTable = _idCITTable;
				var fields = _fields;
				var tableObjects = _tableObjects;
				var tabela = null;

				var insereBtExcluir = true;
				var insereBtInformacoes  = true;
				var imgBotaoExcluir;
				var imgBotaoInformacoes;

				this.onDeleteRow = function(deletedItem){};

				this.getTableList = function(){
					return tableObjects;
				}



				// Transforma a lista da tabela em uma lista de objetos
				// de acordo com o 'fields' passado.
				this.getTableObjects = function(){
					var objects = [];
					var object = {};

					for(var j = 0; j < tableObjects.length; j++){
						for(var i = 0 ; i < fields.length; i++){
							eval("object." + fields[i] + " = '" + tableObjects[j][i] + "'");
						}
						objects.push(object);
						object = {};
					}

					return objects;
				}

				this.setTableObjects = function(objects){
					tableObjects = objects;
					this.montaTabela();
				}

				this.addObject = function(object){
					tableObjects.push(object);
					this.montaTabela();
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

				this.montaTabela = function(){
					var linha;
					var celula;

					limpaTabela();

					var idItemConfiguracao;

					for(var i = tableObjects.length - 1; i >= 0; i--){


						var j = 0;
						linha = getTabela().insertRow(1);

						for(j = 0; j < fields.length; j++){
							celula = linha.insertCell(j);

							//tratamento caso seja um componente ao invés de texto
							try{
								celula.appendChild(tableObjects[i][j]);
							}catch(e){
								celula.innerHTML = tableObjects[i][j];
							}
						}

						if(insereBtExcluir){
							var btAux = getCopiaBotaoExcluir();
							var celExcluir = linha.insertCell(j);

							btAux.setAttribute("id", i);
							btAux.addEventListener("click", function(evt){
								//ao disparar o evento, considerará o id do botão
								self.removeObject(this.id);
								this.onDeleteRow(this);

							}, false);
							celExcluir.appendChild(btAux);
						}


						if(idCITTable == "tblICs"){

							if(insereBtInformacoes){
								var btAux = getCopiaBotaoInformacoes();
								var celInformacoes = linha.insertCell(j);
								btAux.setAttribute("id", i);

								btAux.addEventListener("click", function(evt){
									//alert(this.id);
									carregaPaginaIC(this.id);
									//contador++;

								}, false);
								celInformacoes.appendChild(btAux);

							}
							//contador++;
 						}
					}
				}

				this.removeObject = function(indice){
					removeObjectDaLista(indice);
					this.montaTabela();
				}

				// Remove item e organiza lista
				var removeObjectDaLista = function(indice){
					tableObjects[indice] = null;
					var novaLista = [];
					for(var i = 0 ; i < tableObjects.length; i++){
						if(tableObjects[i] != null){
							novaLista.push(tableObjects[i]);
						}
					}
					tableObjects = novaLista;
				}

				var getCopiaBotaoExcluir = function(){
					var novoBotao = new Image();
					novoBotao.setAttribute("style", "cursor: pointer;");
					novoBotao.src = imgBotaoExcluir;
					return novoBotao;
				}

				var getCopiaBotaoInformacoes = function(){
					var novoBotao = new Image();
					novoBotao.setAttribute("style", "cursor: pointer;");
					novoBotao.src = imgBotaoInformacoes;
					return novoBotao;
				}

				var setImgPathBotaoExcluir = function(src){
					imgBotaoExcluir = src;
				}

				var setImgPathBotaoInformacoes = function(src){
					imgBotaoInformacoes= src;
				}

				var getTabela = function(){
					if(tabela == null){
						tabela = document.getElementById(idCITTable);
					}
					return tabela;
				}

				this.setInsereBotaoExcluir = function(bool, imgSrc){
					insereBtExcluir = bool;
					setImgPathBotaoExcluir(imgSrc);
				}

				this.setInsereBotaoInformacoes = function(bool, imgSrc){
					insereBtInformacoes = bool;
					setImgPathBotaoInformacoes(imgSrc);
				}
			}

		 carregaPaginaIC = function(idLinha){
			 	var listaICs = ObjectUtils.deserializeCollectionFromString(document.form.itensConfiguracaoRelacionadosSerializado.value);
				var idItemConfiguracao = listaICs[idLinha].idItemConfiguracao;

				document.getElementById('fraInfosItemConfig').src = "about:blank";
		    	document.getElementById('fraInfosItemConfig').src = ctx+"/pages/informacoesItemConfiguracaoMudanca/informacoesItemConfiguracaoMudanca.load?idItemConfiguracao="+idItemConfiguracao;
				$( "#POPUP_INFORMACOESITEMCONFIGURACAO" ).dialog({ height: 600 });
				$( "#POPUP_INFORMACOESITEMCONFIGURACAO" ).dialog({ title: i18n_message("tipoItemConfiguracao.informacoesItemConfiguracao") });
				$( "#POPUP_INFORMACOESITEMCONFIGURACAO" ).dialog( 'open' );
			}

			 fechar = function(){
			parent.fecharMudanca();
			}

		 	// Funções de Solicitacao Servico

			$(function() {
				$("#POPUP_SOLICITACAOSERVICO").dialog({
					autoOpen : false,
					width : 863,
					height : 700,
					modal : true
				});

         		// Thiago Fernandes - 23/10/2013 14:06 - Sol. 121468 - Jquery popup cadastrar nova solicitação serviço. 
				$("#POPUP_NOVASOLICITACAOSERVICO").dialog({
					autoOpen : false,
					width : 1260,
					height : 630,
					modal : true
				});

		        // Thiago Fernandes - 29/10/2013 09:06 - Sol. 121468 - Adicionar novas opções ao comboboxes tipo requisição mudança, unidade, localidade fisica, grupo executor e grupo comitê consutivo de mudança .
				$("#POPUP_NOVOTIPOREQUISICAOMUDANCA").dialog({
					autoOpen : false,
					width : 1260,
					height : 490,
					modal : true,
					close: function() {
						document.form.fireEvent('preencherComboTipoMudanca');
					}
				});

				$("#POPUP_NOVAUNIDADE").dialog({
					autoOpen : false,
					width : 1320,
					height : 780,
					modal : true,
					close: function() {
						document.form.fireEvent('carregaUnidade');
					}
				});

				$("#POPUP_NOVALOCALIDADE").dialog({
					autoOpen : false,
					width : 1000,
					height : 400,
					modal : true,
					close: function() {
						document.form.fireEvent('preencherComboLocalidade');
					}
				});

				$("#POPUP_NOVOGRUPOEXECUTOR").dialog({
					autoOpen : false,
					width : 1400,
					height : 780,
					modal : true,
					close: function() {
						document.form.fireEvent('preencherComboGrupoExecutor');
						document.form.fireEvent('preencherComboComite');
					}
				});

			    // Thiago Fernandes - 29/10/2013 09:06 - Sol. 121468 - Retirar barra de rolagem desnecessaria da popup serviço . 
				$("#POPUP_SERVICO").dialog({
					autoOpen : false,
					width : 1100,
					height : 740,
					modal : true
				});

				$("#addSolicitacaoServico").click(function() {
					$("#POPUP_SOLICITACAOSERVICO").dialog("open");
				});

				$("#addImgSolicitacaoServico").click(function() {
					$("#POPUP_SOLICITACAOSERVICO").dialog("open");
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

				var camposLookupItem = desc.split("-");
				tabelaRelacionamentoSolicitacaoServico.addObject([id, camposLookupItem[1], camposLookupItem[2]]);

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


			function AprovacaoMudancaDTO(idEmpregado,nomeEmpregado,dataHoraVotacao,voto,comentario,i){
		 		this.idEmpregado = idEmpregado;
		 		this.nomeEmpregado = nomeEmpregado;
		 		this.dataHoraVotacao = dataHoraVotacao
		 		this.voto = voto;
		 		this.comentario = comentario;
		 	}

			function AprovacaoPropostaDTO(idEmpregado,nomeEmpregado,dataHoraVotacao,voto,comentario,i){
		 		this.idEmpregado = idEmpregado;
		 		this.nomeEmpregado = nomeEmpregado;
		 		this.dataHoraVotacao = dataHoraVotacao
		 		this.voto = voto;
		 		this.comentario = comentario;
		 	}

			function serializaAprovacoesProposta(){
		 		var tabela = document.getElementById('tabelaAprovacoesProposta');
		 		var count = tabela.rows.length;
		 		var listaDeAprovacoes = [];
		 		for(var i = 1; i < count ; i++){
		 			var voto =  '';
		 			if (document.getElementById('idEmpregadoProposta' + i) != "" && document.getElementById('idEmpregadoProposta' + i) != null){
		 			var idEmpregado = document.getElementById('idEmpregadoProposta' + i).value;
		 			var nomeEmpregado = document.getElementById('nomeEmpregadoProposta' + i).value;
		 			var dataHoraVotacao = document.getElementById('dataHoraVotacaoProposta' + i).value;
		 			if ($('#votoAProposta' + i).is(":checked")){
		 				voto = "A";
					} else{
						if ($('#votoRProposta' + i).is(":checked")){
							voto = "R";
						}
					}


		 			var comentario = document.getElementById('comentarioProposta' + i).value;
		 			var aprovacaoProposta = new AprovacaoPropostaDTO(idEmpregado, nomeEmpregado, dataHoraVotacao, voto, comentario,i);
		 			listaDeAprovacoes.push(aprovacaoProposta);
		 			}
		 		}
		 		var serializa = ObjectUtils.serializeObjects(listaDeAprovacoes);
				document.form.aprovacaoPropostaServicoSerializado.value = serializa;
		 	}

			function serializaAprovacoesMudanca(){

		 		var tabela = document.getElementById('tabelaAprovacoesMudanca');
		 		var count = tabela.rows.length;
		 		var listaDeAprovacoes = [];
		 		for(var i = 1; i < count ; i++){
		 			var voto =  '';
		 			if (document.getElementById('idEmpregadoMudanca' + i) != "" && document.getElementById('idEmpregadoMudanca' + i) != null){
			 			var idEmpregado = document.getElementById('idEmpregadoMudanca' + i).value;
			 			var nomeEmpregado = document.getElementById('nomeEmpregadoMudanca' + i).value;
			 			var dataHoraVotacao = document.getElementById('dataHoraVotacaoMudanca' + i).value;
			 			if ($('#votoAMudanca' + i).is(":checked")){
			 				voto = "A";
						} else{
							if ($('#votoRMudanca' + i).is(":checked")){
								voto = "R";
							}
						}


			 			var comentario = document.getElementById('comentarioMudanca' + i).value;
			 			var aprovacaoMudanca = new AprovacaoMudancaDTO(idEmpregado, nomeEmpregado, dataHoraVotacao, voto, comentario,i);
			 			listaDeAprovacoes.push(aprovacaoMudanca);
		 			}
		 		}

		 		var serializa = ObjectUtils.serializeObjects(listaDeAprovacoes);
				document.form.aprovacaoMudancaServicoSerializado.value = serializa;
		 	}


			function addLinhaTabelaAprovacaoProposta(idEmpregado,nomeEmpregado,comentario,dataHoraVotacao,validacao,valida){
				var tbl = document.getElementById('tabelaAprovacoesProposta');
				tbl.style.display = 'block';
				var lastRow = tbl.rows.length;
				var row = tbl.insertRow(lastRow);
				var  disabled = '';
				if(validacao == 'true' ){
					disabled =  'disabled = "true"';
				}

				count++;
				coluna = row.insertCell(0);
				coluna.innerHTML ='<input id="idEmpregadoProposta' + count + '" type="hidden" name="idEmpregadoProposta" value="' + idEmpregado + '"/><input  value = "'+nomeEmpregado+'"  type="hidden" name="nomeEmpregadoProposta" id="nomeEmpregadoProposta' + count + '" />';
 				coluna = row.insertCell(1);
 				coluna.innerHTML = nomeEmpregado ;
 				coluna = row.insertCell(2);
 				coluna.innerHTML = '<span  style="padding-right: 30px;"><input '+disabled+'  style="margin-right: 5px;" type="radio" id="votoAProposta' + count + '" name="votoProposta' + count + '" value="A"  /> '+i18n_message("citcorpore.comum.aprovada")+' </span>' +
 				'<span style="padding-right: 30px;"><input '+disabled+' style="margin-right: 5px;" type="radio" id="votoRProposta' + count + '" name="votoProposta' + count + '" value="R" /> '+i18n_message("citcorpore.comum.rejeitada")+' </span>';
 				coluna = row.insertCell(3);
 				coluna.innerHTML =  '<input  '+disabled+'  value="'+comentario+'" name="comentarioProposta' + count + '" id="comentarioProposta' + count + '" size="100"  type="text" maxlength="200" />'
 				coluna = row.insertCell(4);
 				var input ='<input  value="'+dataHoraVotacao+'" name="dataHoraVotacaoProposta' + count + '" id="dataHoraVotacaoProposta' + count + '" size="100"  type="hidden" maxlength="200" />';
				coluna.innerHTML = dataHoraVotacao + input;

			}

			function addLinhaTabelaAprovacaoMudanca(idEmpregado,nomeEmpregado,comentario,dataHoraVotacao,validacao,valida){
				var tbl = document.getElementById('tabelaAprovacoesMudanca');
				tbl.style.display = 'block';
				var lastRow = tbl.rows.length;
				var row = tbl.insertRow(lastRow);
				var  disabled = '';
				if(validacao == 'true' ){
					disabled =  'disabled = "true"';
				}

				count++;
				coluna = row.insertCell(0);
				coluna.innerHTML ='<input id="idEmpregadoMudanca' + count + '" type="hidden" name="idEmpregadoMudanca" value="' + idEmpregado + '"/><input  value = "'+nomeEmpregado+'"  type="hidden" id="nomeEmpregadoMudanca' + count + '" />';
 				coluna = row.insertCell(1);
 				coluna.innerHTML = nomeEmpregado ;
 				coluna = row.insertCell(2);
 				coluna.innerHTML = '<span  style="padding-right: 30px;"><input '+disabled+'  style="margin-right: 5px;" type="radio" id="votoAMudanca' + count + '" name="votoMudanca' + count + '" value="A"  /> '+i18n_message("citcorpore.comum.aprovada")+' </span>' +
 				'<span style="padding-right: 30px;"><input '+disabled+' style="margin-right: 5px;" type="radio" id="votoRMudanca' + count + '" name="votoMudanca' + count + '" value="R" />'+i18n_message("citcorpore.comum.rejeitada")+' </span>';
 				coluna = row.insertCell(3);
 				coluna.innerHTML =  '<input  '+disabled+'  value="'+comentario+'" name="comentarioMudanca' + count + '" id="comentarioMudanca' + count + '" size="100"  type="text" maxlength="200" />'
 				coluna = row.insertCell(4);
 				var input ='<input  value="'+dataHoraVotacao+'" name="dataHoraVotacaoMudanca' + count + '" id="dataHoraVotacaoMudanca' + count + '" size="100"  type="hidden" maxlength="200" />';
				coluna.innerHTML = dataHoraVotacao + input;

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


			function deleteAllRowsProposta() {
				var tabela = document.getElementById('tabelaAprovacoesProposta');
				var count = tabela.rows.length;

				while (count > 1) {
					tabela.deleteRow(count - 1);
					count--;
				}
			}

			function deleteAllRowsMudanca() {
				var tabela = document.getElementById('tabelaAprovacoesMudanca');
				var count = tabela.rows.length;

				while (count > 1) {
					tabela.deleteRow(count - 1);
					count--;
				}
			}

			function mostrarCategoria(){

		    	document.form.fireEvent('validacaoCategoriaMudanca');

	        }

			function atribuirCheckedVotoProposta(voto){
				if (voto == "A" && voto != null){
					$('#votoAProposta' + count).attr('checked', true);
				} else{
					if (voto == "R" && voto != null){
						$('#votoRProposta' + count).attr('checked', true);
					}
				}
			}

			function atribuirCheckedVotoMudanca(voto){
				if (voto == "A" && voto != null){
					$('#votoAMudanca' + count).attr('checked', true);
				} else{
					if (voto == "R" && voto != null){
						$('#votoRMudanca' + count).attr('checked', true);
					}
				}
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
				popup2.abrePopupParms('empregado', '', 'idContrato=' + idContrato);
			}

			//ações dos botões
			function abrirPopupAnexo() {
				$('#POPUP_menuAnexos').dialog('open');
				uploadAnexos.refresh();
			}

			function getBotaoEditarProblema(id){
				var botaoVisualizarProblemas = new Image();

				botaoVisualizarProblemas.src = botaoVisualizarProblemas_src;
				botaoVisualizarProblemas.setAttribute("style", "cursor: pointer;");
				botaoVisualizarProblemas.id = id;
				botaoVisualizarProblemas.addEventListener("click", function(evt){CarregarProblema(id)}, true);

				return botaoVisualizarProblemas;
			}

			function CarregarProblema(idProblema){
				document.getElementById('iframeEditarProblema').src = ctx+"/pages/problema/problema.load?iframe=true&chamarTelaProblema=S&acaoFluxo=E&idProblema="+idProblema;
				$("#POPUP_EDITARPROBLEMA").dialog("open");
			}
			$(function() {
				$("#POPUP_EDITARPROBLEMA").dialog({
					autoOpen : false,
					width : "98%",
					height : 1000,
					modal : true
				});
			});

			function fecharFrameProblema(){
				$("#POPUP_EDITARPROBLEMA").dialog("close");
				//document.form.fireEvent("atualizaGridProblema");
			}

			function fecharItemRequisicao() {
				$("#POPUPITEMCONFIGURACAO").dialog("close");
			}




			function fecharPopupDescricaoItemConf(){
				document.getElementById('hiddenDescricaoItemConfiguracao').value = document.getElementById('descricaoItemConfiguracao').value;
				document.form.fireEvent('tratarCaracterItemConfiguracao');
				$("#POPUPDESCRICAOITEMCONFIGURACAO").dialog("close");
			}

			function teste() {
				document.form.fireEvent('tratarCaracterItemConfiguracao');
			}

			function atualizarTabela(itemTratado, desc){
				var descricao = document.getElementById('hiddenDescricaoItemConfiguracao').value;
				descricaoItemConfiguracao = desc;
				var registroJaEssisteTabela = addLinhaTabelaItemConfiguracao(idItemConfiguracao, desc, true);
				if (registroJaEssisteTabela == false) {
					return;
				}
				tabelaRelacionamentoICs.addObject([idItemConfiguracao, descricaoItemConfiguracao, descricao]);
				document.form.itensConfiguracaoRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoICs.getTableObjects());

				//$("#POPUPDESCRICAOITEMCONFIGURACAO").dialog("close");
				fecharItemRequisicao();

				document.getElementById('descricaoItemConfiguracao').value = "";
				idItemConfiguracao = "";
				descricaoItemConfiguracao = "";

			}

			function descricaoTratadaJava(descricaoTratadaJava) {
				//alert(descricaoTratadaJava);
				descricaoTratada = descricaoTratadaJava;
			}

			    /**
		    	INFORMAÇÕES COMPLEMENTARES (TEMPLATE/QUESTIONARIO)
		    **/
		 function exibirInformacoesComplementares(url) {
	           if (url != '') {
	               JANELA_AGUARDE_MENU.show();
	               //document.getElementById('divInformacoesComplementares').style.display = 'block';
	               document.getElementById('fraInformacoesComplementares').src =ctx+url;
	           }else{
	               try{
	               	escondeJanelaAguarde();
	               }catch (e) {
	               }
	               document.getElementById('divInformacoesComplementares').style.display = 'none';
	           }
	       }

	       function exibeJanelaAguarde() {
	           JANELA_AGUARDE_MENU.show();
	       }

	       function escondeJanelaAguarde() {
	           JANELA_AGUARDE_MENU.hide();
	       }

	       function restoreImpactoUrgenciaPorTipoMudanca(){
	    	   document.form.fireEvent('restoreImpactoUrgenciaPorTipoMudanca');
	       }
	   	function chamaPopupCadastroRisco(){
	   		dimensionaPopupCadastroRapido("1300","600");
			cadastroRisco.abrePopupParms('risco', '', '');
		}
		function dimensionaPopupCadastroRapido(w, h) {
			$("#popupCadastroRapido").dialog("option","width", w)
			$("#popupCadastroRapido").dialog("option","height", h)
		}

		function addSolicitante(){
			var idContrato = document.form.idContrato.value;
			document.getElementById('iframeNovoColaborador').src = ctx+"/pages/empregado/empregado.load?iframe=true&idContrato="+idContrato;

			$("#POPUP_NOVOCOLABORADOR").dialog("open");
		}

		$(function() {
			$("#POPUP_NOVOCOLABORADOR").dialog({
				autoOpen : false,
				width : "70%",
				height : 500,
				modal : true
			});
		});

		function fecharAddSolicitante(){
			$('#POPUP_NOVOCOLABORADOR').dialog( 'close' );
		}
		// Thiago Fernandes - 23/10/2013 14:06 - Sol. 121468 - Funções retirar bug para cadastrar nova solicitação serviço.
		function atualizarLista() {
			$("#POPUP_SOLICITACAOSERVICO").dialog("close");
		}

		function fecharModalNovaSolicitacao() {
			$("#POPUP_NOVASOLICITACAOSERVICO").dialog("close");
		}

		function fecharModalFilha() {
			$("#POPUP_NOVASOLICITACAOSERVICO").dialog("close");
		}

		function pesquisarItensFiltro() {
			$("#POPUP_NOVASOLICITACAOSERVICO").dialog("close");
		}

		function abrirPopupNovaSolicitacaoServico(){
			document.getElementById('iframeNovaSolicitacao').src = ctx+"/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?iframe=true";
			$("#POPUP_NOVASOLICITACAOSERVICO").dialog("open");
		}

		// Thiago Fernandes - 29/10/2013 09:06 - Sol. 121468 - Adicionar novas opções ao comboboxes tipo requisição mudança, unidade, localidade fisica, grupo executor e grupo comitê consutivo de mudança .
		function abrirPopupNovoTipoRequisicaoMudanca(){
			document.getElementById('iframeNovoTipoRequisicaoMudanca').src = ctx+"/pages/tipoMudanca/tipoMudanca.load?iframe=true";
			$("#POPUP_NOVOTIPOREQUISICAOMUDANCA").dialog("open");
		}

		function abrirPopupNovaUnidade(){
			document.getElementById('iframeNovaUnidade').src = ctx+"/pages/unidade/unidade.load?iframe=true";
			$("#POPUP_NOVAUNIDADE").dialog("open");
		}

		function abrirPopupNovaLocalidade(){
			document.getElementById('iframeNovaLocalidade').src = ctx+"/pages/localidade/localidade.load?iframe=true";
			$("#POPUP_NOVALOCALIDADE").dialog("open");
		}

		function abrirPopupNovoGrupoExecutor(){
			document.getElementById('iframeNovoGrupoExecutor').src = ctx+"/pages/grupo/grupo.load?iframe=true";
			$("#POPUP_NOVOGRUPOEXECUTOR").dialog("open");
		}

		function abrirAbaPlanoDeReversao() {
			alert(i18n_message("informeAnexoReversao"));
			JANELA_AGUARDE_MENU.hide();
			$('.tabs').tabs('select', 11);
		}

		function event() {
			$( ".even" ).click(function() {
				var s = $(this).attr('id').split('-');
				if(s[0]=="even")
					$( "#sel-" + s[1] ).toggle();
				else if(s[0]=="evenM")
					$( "#selM-" + s[1] ).toggle();
				else if(s[0]=="evenP")
					$( "#selP-" + s[1] ).toggle();
			});
		}

		function resize_iframe() {}