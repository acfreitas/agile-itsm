function marcarTodosCheckbox(id) {
		var classe = "excluir";

		if(!$(id).is(':checked')){
			$("." + classe).each(function() {
				$(this).attr("checked", false);
			});
		}else{
			$("." + classe).each(function() {
					$(this).attr("checked", true);
			});
		}

	}
	$(function() {
	/* 	$("#POPUP_CADASTRO_ALTERACAO").dialog({
			autoOpen : false,
			width : 1110,
			height : 620,
			modal : true,
			show: "fade",
			hide: "fade"
		}); */
	});

		function excluirCheckedBoxes() {
			 var checkboxes = document.getElementsByName("excluir");
			 var cont = 0;
		  var listInformacaoContratoDto = new Array();
		  for (var i=0; i<checkboxes.length; i++) {
		     if (checkboxes[i].checked) {
		    	 var informacaoContratoDto = new InformacoesContratoDTO(checkboxes[i].value);
		    	 listInformacaoContratoDto.push(informacaoContratoDto);
		    	 cont++;
		 	 }
		  }
		  if (cont == 0){
				alert(i18n_message("contrato.excluirOrdemServico"));
				return;
			}
			if (document.getElementById("excluir").value != "") {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					aguarde();
					var servicosContratosSerializadas = ObjectUtils.serializeObjects(listInformacaoContratoDto);
					document.formProntuario.servicosContratosSerializadas.value = servicosContratosSerializadas;
					document.formProntuario.fireEvent("delete");
				}
			}
		}

		function setaContratoSessao(){
			if(properties.parametroPortal == null){
				 document.formProntuario.fireEvent('setaContrato');
			}
		}

		var verificarAbandonoSistema = true;

		function fecharJanela(evt){
			if (verificarAbandonoSistema){
				var message = i18n_message("citcorpore.comum.fecharJanelaSairSistema");
				if (typeof evt == 'undefined') {
					evt = window.event;
				}
				if (evt) {
					evt.returnValue = message;
				}
				return message;
			}
		}


		var procedimentosSolicitados = null;
		var DATA_ATUAL_PRONTUARIO = properties.dataAtual;

		function Screen() {
			return {w: document.body.clientWidth || window.innerWidth || self.innerWidth,
			h: document.body.clientHeight || window.innerHeight || self.innerHeight};
		}

		function setDatePicker(idDate){
			$( "#" + idDate ).datepicker();
		}

		ajustaTamanhoTela = function() {
			var o = Screen();

			var e = document.getElementById('direita');

			var dimW = 295;
			var dimH = 220;
			if (document.getElementById('tblMenuProntuario').style.display == 'none'){
				dimW = 295;
				dimH = 220;
			}
			//e.style.width = (o.w - dimW) + "px";
			//e.style.height = (o.h - dimH) + "px";
			e.style.height = 350;

		};
		function preparaTela(){
			window.setTimeout('ajustaTela()', 500);
			$( "#POPUP_CADASTRO_ALTERACAO" ).draggable();
		}

		function ajustaTela(){
			var e = document.getElementById('direita');
			e.style.display = 'block';
			ajustaTamanhoTela();
		}

		addEvent(window, "load", ajustaTamanhoTela, false);
		addEvent(window, "load", preparaTela, false);
		addEvent(window, "scroll", ajustaTamanhoTela, false);
		addEvent(window, "resize", ajustaTamanhoTela, false);

		var tabberOptions = {

		  'manualStartup':true,

		  /*---- Gera um evento click ----*/
		  'onClick': function(argsObj) {

		    var t = argsObj.tabber; /* Tabber object */
		    var id = t.id; /* ID of the main tabber DIV */
		    var i = argsObj.index; /* Which tab was clicked (0
		 					is the first tab) */
		    var e = argsObj.event; /* Event object */
		  },

		  'addLinkId': true
		};


		function LOOKUP_CONTRATOS_select(id,desc){
			document.formProntuario.idContrato.value = id;
			document.formProntuario.nomeContrato.value = desc;
			$('#modal_lookupContrato').modal('hide');

			document.formProntuario.fireEvent('setaContrato');

			POPUP_QUESTIONARIO.setTitle( i18n_message("citcorpore.comum.numeroContrato") + ': ' + desc);

			document.getElementById('direita').innerHTML = '';

			document.formProntuario.fireEvent('setarSessao');
			window.setInterval(function(){setaContratoSessao()},15000);
		}

		function imprimirConteudoDiv(nameDiv){
			document.formProntuario.conteudoDivImprimir.value = document.getElementById(nameDiv).innerHTML;
			document.formProntuario.fireEvent('imprimirConteudoDiv');
		}

		function adicionaServico(){
			//É necessário passar como parametro o IdServicoContrato pois o jsp servicoContrato captura o id do mesmo
			document.formProntuario.idServicoContrato.value = "";
			document.getElementById('frameCadastro').src = properties.ctx + '/pages/servicoContrato/servicoContrato.load?idContrato=' + document.formProntuario.idContrato.value + '&idServicoContrato=' + '&iframe=true';
			$('#POPUP_CADASTRO_ALTERACAO').find('.modal-header h3').text(i18n_message("visao.servicoContrato"));
			$('#POPUP_CADASTRO_ALTERACAO').modal('show')
		}

		function gerenciarSlas() {
			document.getElementById('frameCadastro').src = properties.ctx + '/pages/ansServicoContratoRelacionado/ansServicoContratoRelacionado.load?idContrato=' + $('#idContrato').val();
			$('#POPUP_CADASTRO_ALTERACAO').modal('show')
		}

		function adicionaVariosServico(){
			//É necessário passar como parametro o IdServicoContrato pois o jsp servicoContrato captura o id do mesmo
			document.formProntuario.idServicoContrato.value = "";
			document.getElementById('frameCadastro').src = properties.ctx + '/pages/servicoContratoMulti/servicoContratoMulti.load?idContrato=' + document.formProntuario.idContrato.value + '&idServicoContrato=' + '&iframe=true';
			$('#POPUP_CADASTRO_ALTERACAO').find('.modal-header h3').text(i18n_message("visao.servicoContrato"));
			$('#POPUP_CADASTRO_ALTERACAO').modal('show')
		}

		function editarServicoContrato(idServContrato){
			document.getElementById('frameCadastro').src =
			properties.ctx + '/pages/servicoContrato/servicoContrato.load?idContrato=' + document.formProntuario.idContrato.value + '&idServicoContrato=' + idServContrato + '&iframe=true';
			$('#POPUP_CADASTRO_ALTERACAO').modal('show')
		}
		function editarNotificaoServico(idNotificacao){
			document.getElementById('frameCadastro').src =
			properties.ctx + '/pages/notificacaoServicoContrato/notificacaoServicoContrato.load?idNotificacao=' + idNotificacao + '&iframe=true';
			$('#POPUP_CADASTRO_ALTERACAO').modal('show')
		}
		function excluiNotificaoServico(idNotificacaoExcluir){
			if (!confirm(i18n_message("citcorpore.comum.deleta"))){
				return;
			}else{
				document.getElementById('frameCadastro').src =
				properties.ctx + '/pages/notificacaoServicoContrato/notificacaoServicoContrato.load?idNotificacaoExcluir=' + idNotificacaoExcluir + '&iframe=true';
				window.setTimeout('refreshFuncionalidade()', 1000);
		}
		}

		function agendamentoOS(idOSParm){
			limpar();
			document.formProntuario.idOS.value = idOSParm;
			abrirAgendamentoOS();
		}

		function pesquisarAgendaOs() {

			if (!ValidacaoUtils.validaData(document.getElementById('dataInicio'), i18n_message("citcorpore.comum.datainicio") + ': ')){
				return;
			}
			if (!ValidacaoUtils.validaData(document.getElementById('dataFim'), i18n_message("citcorpore.comum.datafim") + ': ')){
				return;
			}
			document.getElementById('dataInicioAtividade').value = document.getElementById('dataInicio').value;
			document.getElementById('dataFimAtividade').value = document.getElementById('dataFim').value;

			document.formProntuario.fireEvent('agendamentoOS');
		}

		function gravaRegistroExecucao() {
			if(!validarRegistroExecucao()){
				return false;
			}
			document.formRegistroExecucao.fireEvent('gravarRegistroExecucao');
		}

		function aguarde(){
			JANELA_AGUARDE_MENU.show();
		}

		function fechar_aguarde(){
	    	JANELA_AGUARDE_MENU.hide();
		}

		function validarRegistroExecucao(){

			if (!ValidacaoUtils.validaRequired (document.getElementById('dataInicioExecucao'),i18n_message("citcorpore.comum.datainicio") + ': ')){
				return false;
			}

			if (!ValidacaoUtils.validaRequired (document.getElementById('dataFimExecucao'), i18n_message("citcorpore.comum.datafim") + ': ')){
				return false;
			}

			if (!ValidacaoUtils.validaData(document.getElementById('dataInicioExecucao'), i18n_message("citcorpore.comum.datainicio") + ': ')){
				return false;
			}

			if (!ValidacaoUtils.validaData(document.getElementById('dataFimExecucao'), i18n_message("citcorpore.comum.datafim") + ': ')){
				return false;
			}

			if (!validaData(document.getElementById('dataInicioExecucao').value, document.getElementById('dataFimExecucao').value)){
				return false;
			}

			if (!ValidacaoUtils.validaRequired (document.getElementById('quantidade'), i18n_message("citcorpore.comum.quantidade") + ': ')){
				return false;
			}

			return true;
		}

		function limpar(){
			document.getElementById("AgendamentoOS").innerHTML="";
			document.formAgendamentoOS.clear();
		}

		function adicionaOS(){
			document.getElementById('frameCadastro').src = properties.ctx + '/pages/os/os.load?idContrato=' + document.formProntuario.idContrato.value;
			$('#POPUP_CADASTRO_ALTERACAO').modal('show')
		}
		function editaOS(idOSParm){
			document.getElementById('frameCadastro').src = properties.ctx + '/pages/os/os.load?idContrato=' + document.formProntuario.idContrato.value + '&idOS=' + idOSParm;
			$('#POPUP_CADASTRO_ALTERACAO').modal('show')
		}
		function imprimirOS(idOSParm){
			document.formProntuario.idOS.value = idOSParm;
			JANELA_AGUARDE_MENU.show();
			document.formProntuario.fireEvent('imprimirRelOSPDF');
		}
		function imprimirOSXls(idOSParm){
			document.formProntuario.idOS.value = idOSParm;
			JANELA_AGUARDE_MENU.show();
			document.formProntuario.fireEvent('imprimirRelOSXLS');
		}

		function duplicarOS(idOSParm){
			if (confirm(i18n_message("informacoesContrato.duplicarOS"))) {
				document.formProntuario.idOS.value = idOSParm;
				document.formProntuario.fireEvent('duplicarOS');
			}
		}

		function imprimirRA(idOSParm){
			document.formProntuario.idOS.value = idOSParm;
			JANELA_AGUARDE_MENU.show();
			document.formProntuario.fireEvent('imprimirRelRAOSPDF');
		}

		function imprimirRAXls(idOSParm){
			document.formProntuario.idOS.value = idOSParm;
			JANELA_AGUARDE_MENU.show();
			document.formProntuario.fireEvent('imprimirRelRAOSXLS');
		}
		function excluiRAOS(idOSParm){
			if (confirm(i18n_message("citcorpore.comum.deleta"),"")){
				document.formProntuario.idOS.value = idOSParm;
				document.formProntuario.fireEvent('excluiRAOrdemServicoContrato');
			}
		}
		function setaSituacaoOS(idOSParm){
			document.getElementById('frameCadastro').src = properties.ctx + '/pages/osSetSituacao/osSetSituacao.load?idContrato=' + document.formProntuario.idContrato.value + '&idOS=' + idOSParm;
			$('#POPUP_CADASTRO_ALTERACAO').modal('show')
		}
		function adicionaFatura(){
			document.getElementById('frameCadastro').src = properties.ctx + '/pages/fatura/fatura.load?idContrato=' + document.formProntuario.idContrato.value;
			$('#POPUP_CADASTRO_ALTERACAO').modal('show')
		}
		function editaFatura(idFaturaParm){
			document.getElementById('frameCadastro').src = properties.ctx + '/pages/fatura/fatura.load?idContrato=' + document.formProntuario.idContrato.value + '&idFatura=' + idFaturaParm;
			$('#POPUP_CADASTRO_ALTERACAO').modal('show')
		}
		function imprimirFatura(idFaturaParm){
			document.formProntuario.idFatura.value = idFaturaParm;
			document.formProntuario.fireEvent('imprimirRelatorioFaturaContrato');
		}

		function imprimirFaturaXls(idFaturaParm){
			document.formProntuario.idFatura.value = idFaturaParm;
			document.formProntuario.fireEvent('imprimirRelatorioFaturaContratoXls');
		}

		var ajaxAction = new AjaxAction();
		var ajaxAction2 = new AjaxAction();
		var ajaxAction3 = new AjaxAction();
		var ajaxAction4 = new AjaxAction();
		var ajaxAction5 = new AjaxAction();
		var urlChamada = '';
		var acaoSubmit = '';
		var objSubmit;
		var arrayItensMenu = new Array();
		var arrayNomesItensMenu = new Array();
		var iItemMenu = 0;
		var idQuestaoReceitaAux = 0;
		var arrayItensMenu2 = new Array();
		var arrayNomesItensMenu2 = new Array();
		var iItemMenu2 = 0;
		var abaSel2 = '';

		var abaSelecionada = '';
		setaAbaSelecionada = function(nomeItem, temSubItens, acao, itemMenu, isGrupo){
			if (StringUtils.isBlank(document.formProntuario.idContrato.value)){
				alert(i18n_message("contrato.alerta.informe_contrato"));
				return;
			}
			ajustaTamanhoTela();
			try{
				document.getElementById('divBarra2Adicionais').style.display = 'none';
			}catch(e){
			}
			try{
				document.getElementById('divImpressosAdicionais').style.display = 'none';
			}catch(e){
			}

			if (!temSubItens){
				for(var i = 0; i < iItemMenu; i++){
					document.getElementById(arrayItensMenu[i]).className = 'bordaNaoSelecionaProntuario';
					try{
						document.getElementById('img_' + arrayNomesItensMenu[i]).src = properties.caminho + '/produtos/citsaude/imagens/im_mais1.jpg';
					}catch(e){
					}
				}
			}
			if (!isGrupo){
				abaSelecionada = nomeItem;
			}
			if (temSubItens){
				var divs = document.getElementsByTagName("div");
				for(var i = 0; i < divs.length; i++){
					if (divs[i].id.substr(0,8) == 'divMenu_'){
						var nnn = divs[i].id.substr(8);
						divs[i].className = 'bordaNaoSelecionaProntuario';
						divs[i].style.display = 'none';
						try{
							if (nomeItem != nnn){
								document.getElementById('img_' + nnn).src = properties.caminho + '/produtos/citsaude/imagens/im_mais1.jpg';
							}
						}catch(e){
						}
					}
				}
				document.getElementById(itemMenu).className = 'bordaSelecionaProntuario';
				document.getElementById('divMenu_' + nomeItem).style.display = 'block';
				try{
					if (document.getElementById('img_' + nomeItem).src == properties.caminho + '/produtos/citsaude/imagens/im_menos1.jpg'){
						document.getElementById('divMenu_' + nomeItem).style.display = 'none';
						document.getElementById('img_' + nomeItem).src = properties.caminho + '/produtos/citsaude/imagens/im_mais1.jpg';
					}else{
						document.getElementById('img_' + nomeItem).src = properties.caminho + '/produtos/citsaude/imagens/im_menos1.jpg';
					}
				}catch(e){
				}
			}else{
				document.getElementById(itemMenu).className = 'bordaSelecionaProntuario';

				document.getElementById('direita').innerHTML = i18n_message("citcorpore.comum.aguardeExecutando");

				objSubmit = {idContrato:document.formProntuario.idContrato.value,aba:nomeItem};

				acaoSubmit = properties.ctx + '' + acao;

				urlChamada = acaoSubmit;

				ajaxAction = new AjaxAction();
				ajaxAction.submitObject(objSubmit, acaoSubmit, callBackFuncProntuario);
			}
		};

		setaAbaSel2 = function(nomeItem, temSubItens, acao, itemMenu, isGrupo, idQuest){
			if (StringUtils.isBlank(document.formProntuario.idContrato.value)){
				alert(i18n_message("contrato.alerta.informe_contrato"));
				return;
			}
			try{
				document.getElementById('divBarra2Adicionais').style.display = 'none';
			}catch(e){
			}
			try{
				document.getElementById('divImpressosAdicionais').style.display = 'none';
			}catch(e){
			}
			if (!temSubItens){
				for(var i = 0; i < iItemMenu2; i++){
					try{
						document.getElementById(arrayItensMenu2[i]).className = 'bordaNaoSelecionaProntuario';
					}catch(e){
					}
					try{
						document.getElementById('img2_' + arrayNomesItensMenu2[i]).src = properties.caminho + '/produtos/citsaude/imagens/im_mais1.jpg';
					}catch(e){
					}
				}
			}

			if (!isGrupo){
				if (idQuest == undefined || idQuest == null || idQuest == ''){
					alert('Esta opção não é uma ficha/formulário! Selecione outra opção!');
					return;
				}
				abaSel2 = nomeItem;
			}

			if (temSubItens){
				var divs = document.getElementsByTagName("div");
				for(var i = 0; i < divs.length; i++){
					if (divs[i].id.substr(0,8) == 'divMenu2_'){
						var nnn = divs[i].id.substr(8);
						divs[i].className = 'bordaNaoSelecionaProntuario';
						divs[i].style.display = 'none';
						try{
							if (nomeItem != nnn){
								document.getElementById('img2_' + nnn).src = properties.caminho + '/produtos/citsaude/imagens/im_mais1.jpg';
							}
						}catch(e){
						}
					}
				}
				document.getElementById(itemMenu).className = 'bordaSelecionaProntuario';
				document.getElementById('divMenu2_' + nomeItem).style.display = 'block';
				try{
					if (document.getElementById('img2_' + nomeItem).src == properties.caminho + '/produtos/citsaude/imagens/im_menos1.jpg'){
						document.getElementById('divMenu2_' + nomeItem).style.display = 'none';
						document.getElementById('img2_' + nomeItem).src = properties.caminho + '/produtos/citsaude/imagens/im_mais1.jpg';
					}else{
						document.getElementById('img2_' + nomeItem).src = properties.caminho + '/produtos/citsaude/imagens/im_menos1.jpg';
					}
				}catch(e){
				}
			}else{
				document.getElementById(itemMenu).className = 'bordaSelecionaProntuario';
				document.getElementById('divRegPacienteNovo').style.display = 'none';
				acaoSubmit = '';
				chamaEdicaoQuestionario(document.formProntuario.idContrato.value,idQuest,0, null, false, "N", nomeItem);
			}
		};

		abrePDFAnexo = function(id){
			document.formListaAnexos.idControleGED.value = '';
			document.formListaAnexos.nomeArquivo.value = '';
			document.formListaAnexos.idControleGED.value = id;
			document.formListaAnexos.nomeArquivo.value = id;
			document.formListaAnexos.fireEvent('abrePDF');
		};

		funcaoChamaInformacoesHistoricas = function(nomeAbaParm, idProntCfg){
			document.getElementById('divRegistroInfoHistoricas').innerHTML = '';
			document.formInformacoesHistoricas.idContrato.value = document.formProntuario.idContrato.value;
			document.formInformacoesHistoricas.idInformacoesContratoConfig.value = idProntCfg;
			document.formInformacoesHistoricas.fireEvent('load');
			POPUP_INFO_HISTORICAS.showInYPosition({top:30});
		};

		refreshFuncionalidade = function(){
			if (acaoSubmit != ''){
				document.getElementById('direita').innerHTML = i18n_message("citcorpore.comum.aguardeExecutando");

				urlChamada = acaoSubmit;

				ajaxAction = new AjaxAction();
				ajaxAction.submitObject(objSubmit, acaoSubmit, callBackFuncProntuario);
			}else{
				document.getElementById('direita').innerHTML = '';
			}
		};

		callBackFuncProntuario = function(){
			if (ajaxAction.req.readyState == 4){
				if (ajaxAction.req.status == 200){
					document.getElementById('direita').innerHTML = ajaxAction.req.responseText;

					ajustaTamanhoTela();

					//tabberAutomatic(tabberOptions);

					DEFINEALLPAGES_processaLoadSubPage(urlChamada, ajaxAction.req.responseText);
					DEFINEALLPAGES_atribuiCaracteristicasCitAjax();

					try{
						setDatePicker('dataInicioOS');
					}catch(e){}
					try{
						setDatePicker('dataFimOS');
					}catch(e){}
					try{
						setDatePicker('dataInicioFatura');
					}catch(e){}
					try{
						setDatePicker('dataFimFatura');
					}catch(e){}
				}
				if (ajaxAction.req.status == 403){
					document.getElementById('direita').innerHTML = '';
					alert('Acesso bloqueado ao recurso!');
				}
			}
		};

		callBackFuncNotificacao = function(){
			if (ajaxAction.req.readyState == 4){
				if (ajaxAction.req.status == 200){
					document.getElementById('direita').innerHTML = ajaxAction.req.responseText;

					ajustaTamanhoTela();

					//tabberAutomatic(tabberOptions);

					DEFINEALLPAGES_processaLoadSubPage(urlChamada, ajaxAction.req.responseText);
					DEFINEALLPAGES_atribuiCaracteristicasCitAjax();

					try{

					}catch(e){}

				}
				if (ajaxAction.req.status == 403){
					document.getElementById('direita').innerHTML = '';
					alert('Acesso bloqueado ao recurso!');
				}
			}
		};


		mostraPesquisaPaciente = function(){
		};

		atualizaTamanhoDIV = function(){
			if (document.getElementById('tblMenuProntuario').style.display == 'none'){
				document.getElementById('tblMenuProntuario').style.display = 'block';
				document.getElementById('esquerda').style.width = 160;
				document.getElementById('imgSetaProntuario').src = properties.ctx + '/imagens/arrow_right.png';
			}else{
				document.getElementById('tblMenuProntuario').style.display = 'none';
				document.getElementById('esquerda').style.width = 0;

				document.getElementById('imgSetaProntuario').src = properties.ctx + '/imagens/arrow_left.png';
			}
			ajustaTamanhoTela();
		};

		var tipoHistorico = '';
		function showHistorico(){
			if (document.formProntuario.idContrato.value == ''){
				alert(i18n_message("contrato.alerta.informe_contrato"));
				return;
			}
			try{
				window.frames["frameHistorico"].document.write("");
			}catch (e) {
			}
			document.getElementById('frameHistorico').src = properties.ctx + '/pages/imprimeHistoricoProntuario/imprimeHistoricoProntuario.load?idContrato=' + document.formProntuario.idContrato.value + '&ordemHistorico=' + ordemHistorico;
			tipoHistorico = 'PDF';
			POPUP_HISTORICO.showInYPosition({top:30});
		}
		var ordemHistorico = 'D';
		function showHistoricoHTML(){
			if (document.formProntuario.idContrato.value == ''){
				alert(i18n_message("contrato.alerta.informe_contrato"));
				return;
			}
			try{
				window.frames["frameHistorico"].document.write("<font color='red'><b>Aguarde...carregando ...</b></font>");
			}catch (e) {
			}
			document.getElementById('frameHistorico').src = properties.ctx + '/pages/imprimeHistoricoProntuarioHTML/imprimeHistoricoProntuarioHTML.load?idContrato=' + document.formProntuario.idContrato.value + '&ordemHistorico=' + ordemHistorico;
			tipoHistorico = 'HTML';
			POPUP_HISTORICO.showInYPosition({top:30});
		}
		function showHCrescente(){
			ordemHistorico = 'C';
			if (tipoHistorico == 'PDF'){
				showHistorico();
			}else{
				showHistoricoHTML();
			}
		}
		function showHDecrescente(){
			ordemHistorico = 'D';
			if (tipoHistorico == 'PDF'){
				showHistorico();
			}else{
				showHistoricoHTML();
			}
		}
		function showHistoricoAba(abaParm){
			if (document.formProntuario.idContrato.value == ''){
				alert(i18n_message("contrato.alerta.informe_contrato"));
				return;
			}
			var abaAux = abaSelecionada;
			if (abaParm != undefined && abaParm != null && abaParm != '' && abaParm != ' '){
				abaAux = abaParm;
			}
			try{
				window.frames["frameHistorico"].document.write("");
			}catch (e) {
			}
			document.getElementById('frameHistorico').src = properties.ctx + '/pages/imprimeHistoricoProntuarioHTML/imprimeHistoricoProntuarioHTML.load?idContrato=' + document.formProntuario.idContrato.value + '&aba=' + abaAux;
			POPUP_HISTORICO.showInYPosition({top:30});
		}
		function showDocsRecentes(){
			if (document.formProntuario.idContrato.value == ''){
				alert(i18n_message("contrato.alerta.informe_contrato"));
				return;
			}
			document.formDocRecente.idContratoDocRecente.value = document.formProntuario.idContrato.value;
			document.getElementById('divDocsRecentes').innerHTML = 'Aguarde... carregando...';
			document.formDocRecente.fireEvent('listar');
			POPUP_DOCS_RECENTES.showInYPosition({top:30});
		}
	    function imprimeDocRecente(idDoc){
			if (document.formProntuario.idContrato.value == ''){
				alert(i18n_message("contrato.alerta.informe_contrato"));
				return;
			}
			document.formDocRecente.idDocRecente.value = idDoc;
			document.formDocRecente.fireEvent('abrir');
	    }

		function mostrarTelaCadAlerta(){
			if (document.formProntuario.idContrato.value == ''){
				alert(i18n_message("contrato.alerta.informe_contrato"));
				return;
			}
			document.formAlerta.idContrato.value = document.formProntuario.idContrato.value;
			POPUP_CAD_ALERTA.showInYPosition({top:30});
		}

		function arquivoCarregadoUpload(){
			var funcao = document.formProntuario.funcaoUpload.value;
			if (funcao == 'res'){
				document.formSelecaoProdutos.fireEvent('listarCertificados');
			}
			if (funcao == 'anexos'){
				document.formListaCertificadosAnexos.fireEvent('listarCertificados');
			}
		}

		setDataNow = function(obj)
	    {
	        var now = new Date();
	        var dayStr = "0";
	        if(now.getDate() <= 9)
	            dayStr += now.getDate();
	        else dayStr = now.getDate();
	        var monStr = "0";
	        if(now.getMonth() <= 8)
	            monStr += (now.getMonth() + 1);
	        else monStr = (now.getMonth() + 1);
	        var dtStr = dayStr + "/" + monStr + "/" + now.getFullYear();
	        obj.value = dtStr;
	        return true;
	    }

		function voltar(){
			verificarAbandonoSistema = false;
			window.location = properties.retorno;
		}

		function listarOS(typeList, objTd){
			if (typeList != null && typeList != undefined){
				document.formProntuario.funcaoListarOS.value = typeList;
			}

			document.formProntuario.dataOS1.value = '';
			document.formProntuario.dataOS2.value = '';
			try{
				document.formProntuario.dataOS1.value = document.getElementById('dataInicioOS').value;
			}catch(e){}
			try{
				document.formProntuario.dataOS2.value = document.getElementById('dataFimOS').value;
			}catch(e){}

			var dataInicio = document.getElementById('dataInicioOS').value;
			var dataFim = document.getElementById('dataFimOS').value;

			if(validaData(dataInicio,dataFim)){
				document.formProntuario.fireEvent('listarOSContrato');
				document.getElementById('tdEmCriacao').style.backgroundColor='white';
				document.getElementById('tdSolicitada').style.backgroundColor='white';
				document.getElementById('tdAutorizada').style.backgroundColor='white';
				document.getElementById('tdAprovada').style.backgroundColor='white';
				document.getElementById('tdEmExecucao').style.backgroundColor='white';
				document.getElementById('tdExecutada').style.backgroundColor='white';
				document.getElementById('tdCancelada').style.backgroundColor='white';
				document.getElementById('tdTodas').style.backgroundColor='white';

				try{
					if (objTd != null && objTd != undefined){
						objTd.style.backgroundColor='#E2E2E2';
					}
				}catch(e){}
			}
		}
		function atualizaOSs(){
			document.formProntuario.fireEvent('listarOSContrato');
		}

		function addRegistroExecucao(idOs){
			limparRegistroExecucao();
			document.getElementById('idOSPai').value = idOs;
			$("#POPUP_REGISTRO_EXECUCAO").dialog("open");
		}

	// 	function verificaSituacaoOS(idOs){
	// 		limparRegistroExecucao();
	// 		document.getElementById('idOSPai').value = idOs;
	// 		document.formProntuario.fireEvent('verificaSituacaoOSValida');
	// 	}

		function limparRegistroExecucao(){
			document.getElementById('idOSPai').value = "";
			limparDados();
		}

		function limparDados(){
			document.getElementById('dataInicioExecucao').value = "";
			document.getElementById('dataFimExecucao').value = "";
			document.getElementById('quantidade').value = "";
		}

		function listarFatura(typeList, objTd){
			document.formProntuario.funcaoListarFatura.value = typeList;

			document.formProntuario.dataFatura1.value = '';
			document.formProntuario.dataFatura2.value = '';
			try{
				document.formProntuario.dataFatura1.value = document.getElementById('dataInicioFatura').value;
			}catch(e){}
			try{
				document.formProntuario.dataFatura2.value = document.getElementById('dataFimFatura').value;
			}catch(e){}

			var dataInicio = document.getElementById('dataInicioFatura').value;
			var dataFim = document.getElementById('dataFimFatura').value;

			if(validaData(dataInicio,dataFim)){
				document.formProntuario.fireEvent('listarFaturaContrato');

				document.getElementById('tdEmCriacao').style.backgroundColor='white';
				document.getElementById('tdAguardandoAprovacao').style.backgroundColor='white';
				document.getElementById('tdAprovadas').style.backgroundColor='white';
				document.getElementById('tdAguardHomologacao').style.backgroundColor='white';
				document.getElementById('tdHomologadas').style.backgroundColor='white';
				document.getElementById('tdRejeitadas').style.backgroundColor='white';
				document.getElementById('tdCanceladas').style.backgroundColor='white';
				document.getElementById('tdTodas').style.backgroundColor='white';

				objTd.style.backgroundColor='#E2E2E2';
			}
		}
		function atualizaFaturas(){
			document.formProntuario.fireEvent('listarFaturaContrato');
		}

		/**
		* @author rodrigo.oliveira
		*/
		function validaData(dataInicio, dataFim) {
			if (typeof(locale) === "undefined") locale = '';

			var dtInicio = new Date();
			var dtFim = new Date();

			var dtInicioConvert = '';
			var dtFimConvert = '';
			var dtInicioSplit = dataInicio.split("/");
			var dtFimSplit = dataFim.split("/");

			if (locale == 'en') {
				dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[0] + "/" + dtInicioSplit[1];
				dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[0] + "/" + dtFimSplit[1];
			} else {
				dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[1] + "/" + dtInicioSplit[0];
				dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[1] + "/" + dtFimSplit[0];
			}

			dtInicio.setTime(Date.parse(dtInicioConvert)).setFullYear;
			dtFim.setTime(Date.parse(dtFimConvert)).setFullYear;

			if (dtInicio > dtFim){
				alert(i18n_message("citcorpore.comum.dataInicioMenorFinal"));
				return false;
			}else
				return true;
		}

		$(function() {
			$("#POPUP_AGENDAMENTO_OS").dialog({
				autoOpen : false,
				height : 700,
				width : 900,
				modal : true
			});

			$("#POPUP_REGISTRO_EXECUCAO").dialog({
				autoOpen : false,
				height : 350,
				width : 400,
				modal : true,
				close: function (event, wi){
					$("#dataInicioExecucao").removeClass("hasDatepicker");
				}
			});

			$(function() {
				$("#POPUP_NOTIFICACAO_SERVICO_CONTRATO").dialog({
					autoOpen : false,
					width : 1500,
					height : 800,
					modal : true
				});
			});
		});

		function fecharAgendamentoOS(){
			$("#POPUP_AGENDAMENTO_OS").dialog("close");
		}

		function abrirAgendamentoOS(){
			limpar();
			$("#POPUP_AGENDAMENTO_OS").dialog("open");
		}

		function renderizaList(dados){
			document.getElementById('AgendamentoOS').innerHTML = dados;
		}

		function expandirOS(parametro, obj){
			var div;
			div = 'divExpandeOs'+ parametro;

			if(document.getElementById(div).style.display == 'block'){
				document.getElementById(div).style.display = 'none';
				obj.src = properties.ctx + "/imagens/go-rt-off.gif";

			}else{
				document.getElementById(div).style.display = 'block';
				obj.src = properties.ctx + "/imagens/go-dn-on.gif";
			}
		}

		function fecharAnexo(){
			POPUP_ANEXO.hide();
		}

		function mostraOpcoesRegPacNovo(obj){
			if (StringUtils.isBlank(document.formProntuario.idContrato.value)){
				alert(i18n_message("contrato.alerta.informe_contrato"));
				return;
			}
			POPUP_REGISTROS.hide();
			document.getElementById('divRegPacienteNovo').style.top = '70px';
			document.getElementById('divRegPacienteNovo').style.left = '250px';
			document.getElementById('divRegPacienteNovo').style.display='block';
		}

		function fecharVisao(){
			var idServico = $('#servicoDinamicView').attr('value');

			if (idServico != '' && idServico != 0) {
				acordosServicoContrato(idServico);
				$('#servicoDinamicView').attr('value', '');
			}

			/* POPUP_CADASTRO_ALTERACAO.hide(); */
			$('#POPUP_CADASTRO_ALTERACAO').modal('hide')
		}
		function fecharVisaoFatura(){
			/* POPUP_CADASTRO_ALTERACAO.hide(); */
			$('#POPUP_CADASTRO_ALTERACAO').modal('hide')
		}

		function fecharVisaoPainel(idServico,botao){
			if (botao=="AtividadesServico"){
				atividadesServicoContrato(idServico);
			} else {
				if (botao=="ValorServico"){
					valorServicoContrato(idServico);
				}
			}
			$('#POPUP_CADASTRO_ALTERACAO').modal('hide');
		}

		function showCadNovoContrato(){
			document.getElementById('frameCadastro').src = properties.ctx + '/pages/dinamicViews/dinamicViews.load?modoExibicao=J&identificacao=Contratos';
			/* $('#POPUP_CADASTRO_ALTERACAO').modal('show') */
			$('#POPUP_CADASTRO_ALTERACAO').modal('show')
		}
		function limparAreaInformacao(){
			document.getElementById('direita').innerHTML = '';
		}
		function showServicosContrato(){
			if (StringUtils.isBlank(document.formProntuario.idContrato.value)){
				alert(i18n_message("contrato.alerta.informe_contrato"));
				return;
			}

			habilitaCorpo();
			var nocache = new Date();
			var idContrato = document.getElementById('idContrato');

			setaContratoSessao();
			if(document.getElementById('campoPesquisa').value == null || document.getElementById('campoPesquisa').value  == ""){
				acao = '/pages/listaServicosContrato/listaServicosContrato.load?idContrato='+idContrato.value;
			}
			else{
					acao = '/pages/listaServicosContrato/listaServicosContrato.load?idContrato='+idContrato.value+'&pesquisa='+document.getElementById('campoPesquisa').value ;
			}

			document.getElementById('direita').innerHTML = '<b>'+i18n_message("citcorpore.comum.aguarde")+'</b>';

			objSubmit = {idContrato:document.formProntuario.idContrato.value,aba:'SERVICOSCONTRATO',paginacao:true};

			acaoSubmit = properties.ctx + '' + acao;

			urlChamada = acaoSubmit;

			ajaxAction = new AjaxAction();
			ajaxAction.submitObject(objSubmit, acaoSubmit, callBackFuncProntuario);
		}

		function showServicosContratoExclusao(id){
			if (StringUtils.isBlank(document.formProntuario.idContrato.value)){
				alert(i18n_message("contrato.alerta.informe_contrato"));
				return;
			}

			setaContratoSessao();
			acao = '/pages/listaServicosContrato/listaServicosContrato.load';

			document.getElementById('direita').innerHTML = '<b>'+i18n_message("citcorpore.comum.aguarde")+'</b>';

			objSubmit = {idContrato:document.formProntuario.idContrato.value,aba:'SERVICOSCONTRATO'};

			acaoSubmit = properties.ctx + '' + acao;

			urlChamada = acaoSubmit;

			ajaxAction = new AjaxAction();
			ajaxAction.submitObject(objSubmit, acaoSubmit, callBackFuncProntuario);
			document.formProntuario.idContrato.value = id;
		}

		function showDesempenhoContrato(){
			if (StringUtils.isBlank(document.formProntuario.idContrato.value)){
				alert(i18n_message("contrato.alerta.informe_contrato"));
				return;
			}
			setaContratoSessao();
			acao = '/pages/visualizarDesempenhoServicosContrato/visualizarDesempenhoServicosContrato.load';

			document.getElementById('direita').innerHTML = '<b>'+i18n_message("citcorpore.comum.aguarde")+'</b>';

			objSubmit = {idContrato:document.formProntuario.idContrato.value,aba:'SERVICOSCONTRATO'};

			acaoSubmit = properties.ctx + '' + acao;

			urlChamada = acaoSubmit;

			ajaxAction = new AjaxAction();
			ajaxAction.submitObject(objSubmit, acaoSubmit, callBackFuncProntuario);
		}
		// NOTIFICACAO
		function showNotificacoes(){
			if (StringUtils.isBlank(document.formProntuario.idContrato.value)){
				alert(i18n_message("contrato.alerta.informe_contrato"));
				return;
			}

			habilitaCorpo();
			setaContratoSessao();
			acao = '/pages/visualizarNotificacoes/visualizarNotificacoes.load?idContratoNotificacao=' + document.formProntuario.idContrato.value;

			document.getElementById('direita').innerHTML = '<b>'+i18n_message("citcorpore.comum.aguarde")+'</b>';

			objSubmit = {idContrato:document.formProntuario.idContrato.value,aba:'NOTIFICACAO'};

			acaoSubmit = properties.ctx + '' + acao;

			urlChamada = acaoSubmit;
			ajaxAction = new AjaxAction();
			ajaxAction.submitObject(objSubmit, acaoSubmit, callBackFuncNotificacao);
		}
		callBackFuncOS = function(){
			if (ajaxAction.req.readyState == 4){
				if (ajaxAction.req.status == 200){
					document.getElementById('direita').innerHTML = ajaxAction.req.responseText;

					ajustaTamanhoTela();

					DEFINEALLPAGES_processaLoadSubPage(urlChamada, ajaxAction.req.responseText);
					DEFINEALLPAGES_atribuiCaracteristicasCitAjax();

					setDatePicker('dataInicioOS');
					setDatePicker('dataFimOS');
				}
				if (ajaxAction.req.status == 403){
					document.getElementById('direita').innerHTML = '';
					alert('Acesso bloqueado ao recurso!');
				}
			}
		};
		function showOS(){
			if (StringUtils.isBlank(document.formProntuario.idContrato.value)){
				alert(i18n_message("contrato.alerta.informe_contrato"));
				return;
			}

			habilitaCorpo();
			setaContratoSessao();
			acao = '/pages/listaOSContrato/listaOSContrato.load';

			document.getElementById('direita').innerHTML = '<b>'+i18n_message("citcorpore.comum.aguarde")+'</b>';

			objSubmit = {idContrato:document.formProntuario.idContrato.value,aba:'OSCONTRATO'};

			acaoSubmit = properties.ctx + '' + acao;

			urlChamada = acaoSubmit;

			ajaxAction = new AjaxAction();
			ajaxAction.submitObject(objSubmit, acaoSubmit, callBackFuncOS);
		}
		callBackFuncFaturas = function(){
			if (ajaxAction.req.readyState == 4){
				if (ajaxAction.req.status == 200){
					document.getElementById('direita').innerHTML = ajaxAction.req.responseText;

					ajustaTamanhoTela();

					DEFINEALLPAGES_processaLoadSubPage(urlChamada, ajaxAction.req.responseText);
					DEFINEALLPAGES_atribuiCaracteristicasCitAjax();

					setDatePicker('dataInicioOS');
					setDatePicker('dataFimOS');
				}
				if (ajaxAction.req.status == 403){
					document.getElementById('direita').innerHTML = '';
					alert('Acesso bloqueado ao recurso!');
				}
			}
		};
		function showFaturas(){
			if (StringUtils.isBlank(document.formProntuario.idContrato.value)){
				alert(i18n_message("contrato.alerta.informe_contrato"));
				return;
			}

			habilitaCorpo();
			setaContratoSessao();
			acao = '/pages/listaFaturasContrato/listaFaturasContrato.load';

			document.getElementById('direita').innerHTML = '<b>'+i18n_message("citcorpore.comum.aguarde")+'</b>';

			objSubmit = {idContrato:document.formProntuario.idContrato.value,aba:'FATURASCONTRATO'};

			acaoSubmit = properties.ctx + '' + acao;

			urlChamada = acaoSubmit;

			ajaxAction = new AjaxAction();
			ajaxAction.submitObject(objSubmit, acaoSubmit, callBackFuncFaturas);
		}
		function acordosServicoContrato(idServicoContrato){
			document.formProntuario.idServicoContrato.value = idServicoContrato;
			document.getElementById('divContratoServico_' + idServicoContrato).style.display = 'block';
			document.getElementById('divContratoServico_' + idServicoContrato).innerHTML = '<b>'+i18n_message("citcorpore.comum.aguardecarregando")+'</b>';
			document.formProntuario.fireEvent('listarSLAsContrato');
		}
		function atividadesServicoContrato(idServicoContrato){
			document.formProntuario.idServicoContrato.value = idServicoContrato;
			document.getElementById('divContratoServico_' + idServicoContrato).style.display = 'block';
			document.getElementById('divContratoServico_' + idServicoContrato).innerHTML = '<b>'+i18n_message("citcorpore.comum.aguardecarregando")+'</b>';
			document.formProntuario.fireEvent('listarAtividadesContrato');
		}
		function valorServicoContrato(idServicoContrato) {
			document.formProntuario.idServicoContrato.value = idServicoContrato;
			document.getElementById('divContratoServico_' + idServicoContrato).style.display = 'block';
			document.getElementById('divContratoServico_' + idServicoContrato).innerHTML = '<b>'+i18n_message("citcorpore.comum.aguardecarregando")+'</b>';
			document.formProntuario.fireEvent('listarValoresContrato');
		}
		function adicionarSLA(idServicoContrato){
			$('#servicoDinamicView').attr('value', idServicoContrato);
			document.getElementById('frameCadastro').src = properties.ctx + '/pages/dinamicViews/dinamicViews.load?modoExibicao=J&identificacao=Acordo_Nivel_Servico&saveInfo=' + idServicoContrato;
			$('#POPUP_CADASTRO_ALTERACAO').find('.modal-header h3').text(i18n_message("menu.nome.acordoNivelServico"));
			$('#POPUP_CADASTRO_ALTERACAO').modal('show')
		}
		function vincularSLA(idServicoContrato){
			$('#servicoDinamicView').attr('value', idServicoContrato);
			document.getElementById('frameCadastro').src = properties.ctx + '/pages/dinamicViews/dinamicViews.load?modoExibicao=J&identificacao=Vinculacao_SLA_Servico&saveInfo=' + idServicoContrato;
			$('#POPUP_CADASTRO_ALTERACAO').find('.modal-header h3').text(i18n_message("citcorpore.comum.vincularAcordoNivelServico"));
			$('#POPUP_CADASTRO_ALTERACAO').modal('show')
		}
		function editarSLA(idAcordoSLA, idServicoContrato){
			document.getElementById('frameCadastro').src = properties.ctx + '/pages/dinamicViews/dinamicViews.load?modoExibicao=J&identificacao=Acordo_Nivel_Servico&id=' + idAcordoSLA + '&saveInfo=' + idServicoContrato;
			$('#POPUP_CADASTRO_ALTERACAO').find('.modal-header h3').text(i18n_message("menu.nome.acordoNivelServico"));
			$('#POPUP_CADASTRO_ALTERACAO').modal('show')
		}
		function editarVincSLA(id, idServicoContrato){
			document.getElementById('frameCadastro').src = properties.ctx + '/pages/dinamicViews/dinamicViews.load?modoExibicao=J&identificacao=Vinculacao_SLA_Servico&id=' + id + '&saveInfo=' + idServicoContrato;
			$('#POPUP_CADASTRO_ALTERACAO').find('.modal-header h3').text(i18n_message("citcorpore.comum.copiarAcordoNivelServico"));
			$('#POPUP_CADASTRO_ALTERACAO').modal('show')
		}
		function desabilitarVinculoAtivo(idAcordoServicoContrato, idServicoContrato) {
			document.formProntuario.idAcordoServicoContrato.value = idAcordoServicoContrato;
			document.formProntuario.idServicoContrato.value = idServicoContrato;
			document.formProntuario.fireEvent('desabilitarVinculoAtivo');
		}
		function habilitarVinculoAtivo(idAcordoServicoContrato, idServicoContrato) {
			if(confirm(i18n_message('sla.confirmaVinculo'))) {
				document.formProntuario.idAcordoServicoContrato.value = idAcordoServicoContrato;
				document.formProntuario.idServicoContrato.value = idServicoContrato;
				document.formProntuario.fireEvent('habilitarVinculoAtivo');
			}
		}
		function copiarSLA(tituloSLA, idAcordoSLA, idServicoContrato){
			var idContrato = document.formProntuario.idContrato.value;
			document.getElementById('frameCadastro').src = properties.ctx + '/pages/copiarSLA/copiarSLA.load?idAcordoNivelServico=' + idAcordoSLA + '&idServicoContrato=' + idServicoContrato + '&idContrato=' + idContrato + '&tituloSLA=' + tituloSLA;
			$('#POPUP_CADASTRO_ALTERACAO').find('.modal-header h3').text(i18n_message("citcorpore.comum.copiarAcordoNivelServico"));
			$('#POPUP_CADASTRO_ALTERACAO').modal('show')
		}
		function adicionarAtividade(idServicoContrato){
			var idContrato = document.formProntuario.idContrato.value;
			document.getElementById('frameCadastro').src =
			properties.ctx + '/pages/atividadesServico/atividadesServico.load?idAtividadeServicoContrato=' + '&idServicoContrato=' + idServicoContrato + '&iframe=true' + '&idContrato=' + idContrato;
			$('#POPUP_CADASTRO_ALTERACAO').find('.modal-header h3').text(i18n_message("grupovisao.atividades_servico_conforme"));
			$('#POPUP_CADASTRO_ALTERACAO').modal('show')
		}
		function editarAtividade(idAtividade, idServicoContrato){
			var idContrato = document.formProntuario.idContrato.value;
			document.getElementById('frameCadastro').src =
			properties.ctx + '/pages/atividadesServico/atividadesServico.load?idAtividadeServicoContrato=' + idAtividade + '&idServicoContrato=' + idServicoContrato + '&iframe=true' + '&idContrato=' + idContrato;
			$('#POPUP_CADASTRO_ALTERACAO').find('.modal-header h3').text(i18n_message("citcorpore.comum.atividade"));
			$('#POPUP_CADASTRO_ALTERACAO').modal('show')
		}
		function adicionarValorServico(idServicoContrato){
			document.getElementById('frameCadastro').src =
			properties.ctx + '/pages/valorServicoContrato/valorServicoContrato.load?idValorServico=' + '&idServicoContrato=' + idServicoContrato + '&iframe=true';
			$('#POPUP_CADASTRO_ALTERACAO').find('.modal-header h3').text(i18n_message("citcorpore.comum.valorServico"));
			$('#POPUP_CADASTRO_ALTERACAO').modal('show')
		}
		function editarValorServico(idValorServico, idServicoContrato){
			document.getElementById('frameCadastro').src =
			properties.ctx + '/pages/valorServicoContrato/valorServicoContrato.load?idValorServico=' + idValorServico + '&idServicoContrato=' + idServicoContrato + '&iframe=true';
			$('#POPUP_CADASTRO_ALTERACAO').find('.modal-header h3').text(i18n_message("citcorpore.comum.valorServico"));
			$('#POPUP_CADASTRO_ALTERACAO').modal('show')
		}

		function POPUP_CADASTRO_ALTERACAO_onmaximize(){
			var w = NumberUtil.apenasNumeros(document.getElementById('divIntJanelaPopup_POPUP_CADASTRO_ALTERACAO').style.width);
			var h = NumberUtil.apenasNumeros(document.getElementById('divIntJanelaPopup_POPUP_CADASTRO_ALTERACAO').style.height);
			var wInt = NumberUtil.toInteger(w);
			var hInt = NumberUtil.toInteger(h);
			wInt = wInt - 10;
			hInt = hInt - 60;
			document.getElementById('frameCadastro').width = wInt + '';
			document.getElementById('frameCadastro').height = hInt + '';
		}
		function POPUP_CADASTRO_ALTERACAO_onminimize(){
			var w = NumberUtil.apenasNumeros(document.getElementById('divIntJanelaPopup_POPUP_CADASTRO_ALTERACAO').style.width);
			var h = NumberUtil.apenasNumeros(document.getElementById('divIntJanelaPopup_POPUP_CADASTRO_ALTERACAO').style.height);
			var wInt = NumberUtil.toInteger(w);
			var hInt = NumberUtil.toInteger(h);
			wInt = wInt - 10;
			hInt = hInt - 60;
			document.getElementById('frameCadastro').width = wInt + '';
			document.getElementById('frameCadastro').height = hInt + '';
		}


		/** -- Esta chamada eh necessaria para gerar o objeto TAB na tela -- **/
		tabberAutomatic(tabberOptions);

		function imprimeQuestionario(idContrato, idQuestionario, idItemParm, idIdentificador, somenteLeitura, subForm){
			if (idQuestionario == '0' || idQuestionario == '' || idQuestionario == 'null' || idQuestionario == ' '){
				alert(i18n_message("citcorpore.comum.naoExisteQuestionario"));
				return;
			}
			if (idIdentificador == undefined || idIdentificador == null){
				idIdentificador = '';
			}
			if (subForm == undefined || subForm == null){
				subForm = 'N';
			}
			document.formImprimirFormulario.idQuestionario.value = idQuestionario;
			document.formImprimirFormulario.idContratoQuestionario.value = idIdentificador;

			document.getElementById('btnGravarQuestionario').style.display = 'none';
			document.getElementById('imgGravarEditar').style.display = 'none';
			document.getElementById('divOpcoesSituacaoQuest').style.display = 'none';

			document.getElementById('frameQuestionario').src = 'about:blank';

		    document.formImprimirFormulario.setAttribute("target","frameQuestionario");
		    document.formImprimirFormulario.setAttribute("method","post");

			document.formImprimirFormulario.action=properties.ctx + '/pages/contratoQuestionarios/contratoQuestionarios';

			document.formImprimirFormulario.parmCount.value = '3';
			document.formImprimirFormulario.parm1.value = DEFINEALLPAGES_getFacadeName(document.formImprimirFormulario.action);
			document.formImprimirFormulario.parm2.value = '';
			document.formImprimirFormulario.parm3.value = 'imprimir';

			if (subForm == 'S'){
				POPUP_QUESTIONARIO3.showInYPosition({top:30});

				document.formImprimirFormulario.action=properties.ctx + '/pages/contratoQuestionarios/contratoQuestionarios.event';
				document.formImprimirFormulario.submit();
			}else{
				document.formPessoaQuestionario.situacao.selectedIndex = 0;
				POPUP_QUESTIONARIO.showInYPosition({top:0});

				window.scroll(0, 0);
				document.body.style.overflow='hidden';

				document.formImprimirFormulario.action=properties.ctx + '/pages/contratoQuestionarios/contratoQuestionarios.event';
				document.formImprimirFormulario.submit();
			}
			//JANELA_AGUARDE_MENU.show();
		}
		function chamaEdicaoQuestionario(idContrato, idQuestionario, idItemParm, idIdentificador, somenteLeitura, subForm, abaSusp, dataQuest){
			if (idQuestionario == '0' || idQuestionario == '' || idQuestionario == 'null' || idQuestionario == ' '){
				alert(i18n_message("citcorpore.comum.naoExisteQuestionario"));
				return;
			}
			if (idIdentificador == undefined || idIdentificador == null){
				idIdentificador = '';
			}
			var subFormulario = 'N'
			if (subForm != undefined){
				subFormulario = subForm;
			}
			if (subFormulario == 'N'){
				document.formPessoaQuestionario.idContrato.value = idContrato;
				document.formPessoaQuestionario.idQuestionario.value = idQuestionario;
				document.formPessoaQuestionario.aba.value = abaSelecionada;
				document.formPessoaQuestionario.idItem.value = idItemParm;
				document.formPessoaQuestionario.idContratoQuestionario.value = idIdentificador;
			}else{
				var abaAux = abaSelecionada;
				if (abaSusp != undefined && abaSusp != null && abaSusp != ''){
					abaAux = abaSusp;
				}
				document.formPessoaQuestionario3.idContrato.value = idContrato;
				document.formPessoaQuestionario3.idQuestionario.value = idQuestionario;
				document.formPessoaQuestionario3.aba.value = abaAux;
				document.formPessoaQuestionario3.idItem.value = idItemParm;
				document.formPessoaQuestionario3.idContratoQuestionario.value = idIdentificador;
			}
			var modo = '';
			if (subFormulario == 'N'){
				if (somenteLeitura){
					modo = 'somenteleitura';
					document.getElementById('btnGravarQuestionario').style.display = 'none';
					document.getElementById('imgGravarEditar').style.display = 'none';
					document.getElementById('divOpcoesSituacaoQuest').style.display = 'none';
				}else{
					modo = 'edicao';
					document.getElementById('btnGravarQuestionario').style.display = 'block';
					document.getElementById('imgGravarEditar').style.display = 'block';
					document.getElementById('divOpcoesSituacaoQuest').style.display = 'block';
				}
			}else{
				if (somenteLeitura){
					modo = 'somenteleitura';
					document.getElementById('btnGravarQuestionario3').style.display = 'none';
					document.getElementById('divOpcoesSituacaoQuest3').style.display = 'none';
				}else{
					modo = 'edicao';
					document.getElementById('btnGravarQuestionario3').style.display = 'block';
					document.getElementById('divOpcoesSituacaoQuest3').style.display = 'block';
				}
			}
			if (subFormulario == 'N'){
				window.scroll(0, 0);
				document.body.style.overflow='hidden';
				document.formPessoaQuestionario.situacao.selectedIndex = 0;
				if (dataQuest != undefined && dataQuest != null && dataQuest != ''){
					document.formPessoaQuestionario.dataQuestionario.value = dataQuest;
				}else{
					document.formPessoaQuestionario.dataQuestionario.value = DATA_ATUAL_PRONTUARIO;
				}
				POPUP_QUESTIONARIO.showInYPosition({top:30});
				document.getElementById('frameQuestionario').src = properties.ctx + '/pages/visualizacaoQuestionario/visualizacaoQuestionario.load?modo=' + modo + '&idQuestionario=' + idQuestionario + '&idIdentificadorResposta=' + idIdentificador + '&idContrato=' + idContrato + '&tabela100=true';
			}else{
				POPUP_QUESTIONARIO3.showInYPosition({top:30});
				try{
					window.frames["frameQuestionario3"].document.write("<font color='red'><b>"+ i18n_message("citcorpore.comum.aguardeCarregandoFormulario") +"</b></font>");
				}catch (e) {
				}
				document.getElementById('frameQuestionario3').src = properties.ctx + '/pages/visualizacaoQuestionario/visualizacaoQuestionario.load?modo=' + modo + '&idQuestionario=' + idQuestionario + '&idIdentificadorResposta=' + idIdentificador + '&idContrato=' + idContrato + '&tabela100=true&subForm=S';
			}
		}
		function chamaEdicaoComplemento(idContrato, idQuestionario, idItemParm, idIdentificador, somenteLeitura, subForm, abaSusp){
			if (idQuestionario == '0' || idQuestionario == '' || idQuestionario == 'null' || idQuestionario == ' '){
				alert(i18n_message("citcorpore.comum.naoExisteQuestionario"));
				return;
			}
			if (idIdentificador == undefined || idIdentificador == null){
				idIdentificador = '';
			}
			var subFormulario = 'N'
			if (subForm != undefined){
				subFormulario = subForm;
			}
			if (subFormulario == 'N'){
				document.formPessoaQuestionario.idContrato.value = idContrato;
				document.formPessoaQuestionario.idQuestionario.value = idQuestionario;
				document.formPessoaQuestionario.aba.value = abaSelecionada;
				document.formPessoaQuestionario.idItem.value = idItemParm;
				document.formPessoaQuestionario.idContratoQuestionario.value = idIdentificador;
			}else{
				var abaAux = abaSelecionada;
				if (abaSusp != undefined && abaSusp != null && abaSusp != ''){
					abaAux = abaSusp;
				}
				document.formPessoaQuestionario3.idContrato.value = idContrato;
				document.formPessoaQuestionario3.idQuestionario.value = idQuestionario;
				document.formPessoaQuestionario3.aba.value = abaAux;
				document.formPessoaQuestionario3.idItem.value = idItemParm;
				document.formPessoaQuestionario3.idContratoQuestionario.value = idIdentificador;
			}

			var modo = '';
			if (subFormulario == 'N'){
				if (somenteLeitura){
					modo = 'somenteleitura';
					document.getElementById('btnGravarQuestionario').style.display = 'none';
					document.getElementById('imgGravarEditar').style.display = 'none';
					document.getElementById('divOpcoesSituacaoQuest').style.display = 'none';
				}else{
					modo = 'edicao';
					document.getElementById('btnGravarQuestionario').style.display = 'block';
					document.getElementById('imgGravarEditar').style.display = 'block';
					document.getElementById('divOpcoesSituacaoQuest').style.display = 'block';
				}
			}else{
				if (somenteLeitura){
					modo = 'somenteleitura';
					document.getElementById('btnGravarQuestionario3').style.display = 'none';
					document.getElementById('divOpcoesSituacaoQuest3').style.display = 'none';
				}else{
					modo = 'edicao';
					document.getElementById('btnGravarQuestionario3').style.display = 'block';
					document.getElementById('divOpcoesSituacaoQuest3').style.display = 'block';
				}
			}
			//JANELA_AGUARDE_MENU.setTitle('Aguarde... carregando formulário...');
			//addEvent(document.getElementById("frameQuestionario"),"load", carregouIFrame);
			//POPUP_QUESTIONARIO.showInYPosition({top:30});
			if (subFormulario == 'N'){
				window.scroll(0, 0);
				document.body.style.overflow='hidden';
				POPUP_QUESTIONARIO.showInYPosition({top:0});
				try{
					window.frames["frameQuestionario"].document.write("<font color='red'><b>"+i18n_message("citcorpore.comum.aguardeCarregandoFormulario")+"</b></font>");
				}catch (e) {
				}
				document.getElementById('frameQuestionario').src = properties.ctx + '/pages/complementoPessoasQuestionarios/complementoPessoasQuestionarios.load?modo=' + modo + '&idQuestionario=' + idQuestionario + '&idIdentificadorResposta=' + idIdentificador + '&idContrato=' + idContrato + '&tabela100=true&aba=' + abaSelecionada;
			}else{
				POPUP_QUESTIONARIO3.showInYPosition({top:30});
				try{
					window.frames["frameQuestionario3"].document.write("<font color='red'><b>"+i18n_message("citcorpore.comum.aguardeCarregandoFormulario")+"</b></font>");
				}catch (e) {
				}
				document.getElementById('frameQuestionario3').src = properties.ctx + '/pages/complementoPessoasQuestionarios/complementoPessoasQuestionarios.load?modo=' + modo + '&idQuestionario=' + idQuestionario + '&idIdentificadorResposta=' + idIdentificador + '&idContrato=' + idContrato + '&tabela100=true&subForm=S&aba=' + abaSelecionada;
			}
			//JANELA_AGUARDE_MENU.showInYPosition({top:30});
		}
		function POPUP_QUESTIONARIO_onhide(){
			document.body.style.overflow='auto';
			refreshFuncionalidade();
		}
		function POPUP_QUESTIONARIO2_onhide(){
			document.body.style.overflow='auto';
			refreshFuncionalidade();
		}
	    function carregouIFrame() {
	    	JANELA_AGUARDE_MENU.hide();
	    	HTMLUtils.removeEvent(document.getElementById("frameQuestionario"),"load", carregouIFrame);
	    }
		function chamaReceituarioSuspenso(idQuestaoParm){
			if (arguments != undefined && arguments != null && arguments.length > 0){ //Se foi passado o parametro
				idQuestaoReceitaAux = idQuestaoParm;
			}
			POPUP_RECEITUARIO.showInYPosition({top:30});
			document.getElementById('divReceituarioSuspenso').innerHTML = 'Aguarde carregando a tela de receituário...';

			var objSubmitAux = {idProntuario:document.formProntuario.idContrato.value,aba:'#Receituario_Suspenso#'};

			acao = '/pages/prontuarioEletronicoReceituario/prontuarioEletronicoReceituario.load';
			var acaoSubmitAux = properties.ctx + '' + acao;

			urlChamada = acaoSubmitAux;

			ajaxAction = new AjaxAction();
			ajaxAction.submitObject(objSubmitAux, acaoSubmitAux, callBackFuncProntuarioReceitaSuspensa);
		}
		function chamaHistoricoQuestionario(idSubQuest, nomeAba){
			POPUP_HIST_QUESTIONARIO.showInYPosition({top:30});
			document.getElementById('divHistQuestionarioSuspenso').innerHTML = 'Aguarde carregando a tela ...';
			var objSubmitAux = {idProntuario:document.formProntuario.idContrato.value,aba:nomeAba,subForm:'S'};

			acao = '/pages/prontuarioEletronicoPessoasQuestionario/prontuarioEletronicoPessoasQuestionario.load?parm=' + idSubQuest;
			var acaoSubmitAux = properties.ctx + '' + acao;

			urlChamada = acaoSubmitAux;

			ajaxAction = new AjaxAction();
			ajaxAction.submitObject(objSubmitAux, acaoSubmitAux, callBackFuncProntuarioQuestSusp);
		}
		function escondeQuestionarioSubForm(){
			POPUP_QUESTIONARIO3.hide();
		}
		function POPUP_RECEITUARIO_onhide(){
			var acaoX = '/pages/receita/receita.load';
			var acaoSubmitAux = properties.ctx + '' + acaoX;

			var objSubmitAux = {idProntuario:document.formProntuario.idContrato.value,aba:'#Receituario_Suspenso#'};

			window.frames["frameQuestionario"].document.getElementById('divReceitaSuspensa_' + idQuestaoReceitaAux).innerHTML = 'Aguarde... atualizando...';

			ajaxAction2 = new AjaxAction();
			ajaxAction2.submitObject(objSubmitAux, acaoSubmitAux, callBackFuncProntuarioReceitaSuspensaApres);
			document.getElementById('divReceituario').innerHTML = '';
		}

		function gravarFormulario(continuarEditando){
			if (document.getElementById('btnGravarQuestionario').style.display == 'none'){
				//Se entrar aqui, eh que nao colocou como invisivel
				try{
					document.getElementById('imgGravarEditar').style.display = 'none';
				}catch(e){
				}
			}
			window.frames["frameQuestionario"].document.formQuestionario.action = properties.ctx + '/pages/contratoQuestionarios/contratoQuestionarios.load';
			window.frames["frameQuestionario"].document.formQuestionario.idContrato.value = document.formPessoaQuestionario.idContrato.value;
	//		window.frames["frameQuestionario"].document.formQuestionario.idQuestionario.value = document.formPessoaQuestionario.idQuestionario.value;
			window.frames["frameQuestionario"].document.formQuestionario.aba.value = document.formPessoaQuestionario.aba.value;
			window.frames["frameQuestionario"].document.formQuestionario.idItem.value = document.formPessoaQuestionario.idItem.value;
			window.frames["frameQuestionario"].document.formQuestionario.idContratoQuestionario.value = document.formPessoaQuestionario.idContratoQuestionario.value;
			if (continuarEditando == undefined || continuarEditando == null || continuarEditando != 'E'){
				window.frames["frameQuestionario"].document.formQuestionario.situacao.value = document.formPessoaQuestionario.situacao.value;
			}else{
				if (document.getElementById('btnGravarQuestionario').style.display == 'none'){
					return;
				}
				window.frames["frameQuestionario"].document.formQuestionario.situacao.value = '*';
			}
			if (!DateTimeUtil.isValidDate(document.formPessoaQuestionario.dataQuestionario.value)){
				alert('Data inválida!');
				document.formPessoaQuestionario.dataQuestionario.focus();
				document.formPessoaQuestionario.dataQuestionario.selected();
				return;
			}
			document.formPessoaQuestionario.dataAtual.value = DATA_ATUAL_PRONTUARIO;
			if (!DateTimeUtil.comparaDatas(document.formPessoaQuestionario.dataQuestionario, document.formPessoaQuestionario.dataAtual, 'A data do registro não pode ser superior a data atual!')){
				document.formPessoaQuestionario.dataQuestionario.focus();
				document.formPessoaQuestionario.dataQuestionario.selected();
				return;
			}
			window.frames["frameQuestionario"].document.formQuestionario.dataQuestionario.value = document.formPessoaQuestionario.dataQuestionario.value;

			if (window.frames["frameQuestionario"].document.formQuestionario.situacao.value == 'F'){ //Se estiver fechando o formulario, entao valida!
				if (!window.frames["frameQuestionario"].validacaoGeral()){
					return;
				}
				if (!window.frames["frameQuestionario"].document.formQuestionario.validate()){
					return;
				}
				try{
					window.frames["frameQuestionario"].mostraAguardeValidacaoQuestionario();
				}catch(e){
				}
				window.frames["frameQuestionario"].document.formQuestionario.fireEvent('validate');
				return;
				/*
				if (!confirm("Confirma a gravação do formulário  \nApós gravação não será permitida alterações!")){
					return;
				}
				*/
			}
			window.frames["frameQuestionario"].tratamentoAntesSubmissao();
			try{
				window.frames["frameQuestionario"].mostraAguardeGravacaoQuestionario();
			}catch(e){
			}
			window.frames["frameQuestionario"].document.formQuestionario.submit();
		}
		function finalizacao(){
			try{
				window.frames["frameQuestionario"].escondeAguardeValidacaoQuestionario();
			}catch(e){
			}
			document.getElementById('divCertificados').innerHTML = '';
			document.formProntuario.funcaoUpload.value = 'res';
			//document.getElementById('frameCertificadoApplet').src=properties.ctx + '/produtos/certificadodigital/appCertDigital.jsp';
			POPUP_FINALIZACAO_ITEM.showInYPosition({top:30});
			document.formSelecaoProdutos.idContrato.value = document.formPessoaQuestionario.idContrato.value;
			document.formSelecaoProdutos.aba.value = document.formPessoaQuestionario.aba.value;
			document.formSelecaoProdutos.fireEvent('listarProdutos');
			document.formSelecaoProdutos.nomeFrame.value = 'frameQuestionario';
		}
		function mostraMensagemNaoPodeConcluir(msg){
			try{
				window.frames["frameQuestionario"].escondeAguardeValidacaoQuestionario();
			}catch(e){
			}
			alert(msg);
		}
		function gravarFormulario3(){
			window.frames["frameQuestionario3"].document.formQuestionario.action = properties.ctx + '/pages/contratoQuestionarios/contratoQuestionarios.load';

			window.frames["frameQuestionario3"].document.formQuestionario.idContrato.value = document.formPessoaQuestionario3.idContrato.value;
	//		window.frames["frameQuestionario"].document.formQuestionario.idQuestionario.value = document.formPessoaQuestionario.idQuestionario.value;
			window.frames["frameQuestionario3"].document.formQuestionario.aba.value = document.formPessoaQuestionario3.aba.value;
			window.frames["frameQuestionario3"].document.formQuestionario.idItem.value = document.formPessoaQuestionario3.idItem.value;
			window.frames["frameQuestionario3"].document.formQuestionario.idContratoQuestionario.value = document.formPessoaQuestionario3.idContratoQuestionario.value;
			window.frames["frameQuestionario3"].document.formQuestionario.situacao.value = document.formPessoaQuestionario3.situacao.value;

			if (document.formPessoaQuestionario3.situacao3.value == 'F'){ //Se estiver fechando o formulario, entao valida!
				if (!window.frames["frameQuestionario3"].validacaoGeral()){
					return;
				}
				if (!window.frames["frameQuestionario3"].document.formQuestionario.validate()){
					return;
				}
				document.getElementById('divCertificados').innerHTML = '';
				POPUP_FINALIZACAO_ITEM.showInYPosition({top:30});
				document.formSelecaoProdutos.idContrato.value = document.formPessoaQuestionario3.idContrato.value;
				document.formSelecaoProdutos.aba.value = document.formPessoaQuestionario3.aba.value;
				document.formSelecaoProdutos.fireEvent('listarProdutos');
				document.formSelecaoProdutos.nomeFrame.value = 'frameQuestionario3';
				return;
				/*
				if (!confirm("Confirma a gravação do formulário  \nApós gravação não será permitida alterações!")){
					return;
				}
				*/
			}
			window.frames["frameQuestionario3"].tratamentoAntesSubmissao();
			window.frames["frameQuestionario3"].document.formQuestionario.submit();
		}
		function gravarItemRES(){
			var prods = '';
			if (document.getElementById('divSelecaoProdutos').style.display == 'block'){ //Se esta visivel, entao deve validar os produtos!
				if (document.formSelecaoProdutos.idProduto != undefined){
					if (document.formSelecaoProdutos.idProduto.length){
						var b = false;
						for(var i = 0; i < document.formSelecaoProdutos.idProduto.length; i++){
							if (document.formSelecaoProdutos.idProduto[i].checked){
								if (prods != ''){
									prods = prods + ';';
								}
								prods = prods + document.formSelecaoProdutos.idProduto[i].value;
								b = true;
							}
						}
						if (!b){
							alert('Selecione o(s) produto(s) desta atividade!');
							return;
						}
					}else{
						if (!document.formSelecaoProdutos.idProduto.checked){
							alert('Selecione o(s) produto(s) desta atividade!');
							return;
						}
					}
				}
			}
			if (document.getElementById('divSelecaoProdutos').style.display == 'block'){ //Se esta visivel, entao deve validar os produtos!
				if (prods == ''){
					alert('Selecione o(s) produto(s) desta atividade!');
					return;
				}
			}
			var nomeFrame = document.formSelecaoProdutos.nomeFrame.value;
			if (nomeFrame == '' || nomeFrame == null || nomeFrame == undefined){
				nomeFrame = 'frameQuestionario';
			}
			window.frames[nomeFrame].document.formQuestionario.produtos.value = prods;
			window.frames[nomeFrame].tratamentoAntesSubmissao();
			try{
				window.frames[nomeFrame].mostraAguardeGravacaoQuestionario();
			}catch(e){
			}
			window.frames[nomeFrame].document.formQuestionario.submit();

			POPUP_FINALIZACAO_ITEM.hide();
		};

		function visualizarEstatistica(idContrato, idQuestionario){
			document.formPessoaQuestionarioEstatistica.idQuestionario.value = idQuestionario;
			document.formPessoaQuestionarioEstatistica.fireEvent('carregaQuestoesQuestionario');
			document.getElementById('divPainel').innerHTML = '';
			POPUP_ESTATISTICA.showInYPosition({top:30});
		}

			function atualizaPainel(){
				document.getElementById('divPainel').innerHTML = i18n_message("citcorpore.comum.aguarde");

				document.formPainel.idQuestaoQuestionario.value = document.formPessoaQuestionarioEstatistica.idQuestaoQuestionario.value;
				document.formPainel.tipoGrafico.value = document.formPessoaQuestionarioEstatistica.tipoGrafico.value;

				if (document.formPessoaQuestionarioEstatistica.idQuestaoQuestionario.options[document.formPessoaQuestionarioEstatistica.idQuestaoQuestionario.selectedIndex].value == 'N'){
					alert('Este item é agrupador não pode executar estatisticas!');
					return;
				}

				document.formPainel.nomeQuestao.value = document.formPessoaQuestionarioEstatistica.idQuestaoQuestionario.options[document.formPessoaQuestionarioEstatistica.idQuestaoQuestionario.selectedIndex].text +
					' : ' + document.formProntuario.numeroContrato.value;
				document.formPainel.idContrato.value = document.formProntuario.idContrato.value;

				document.formPainel.fireEvent('geraPainel');
			}

			function hideAguarde(){
			}

		function abreASO(idAso){
			window.open(properties.ctx + '/gedcitsaude/aso/' + idAso + '.pdf');
		}

		var umaUnicaVez = true;
		function funcaoStart(){
			if (umaUnicaVez){
				umaUnicaVez = false;
			}
		}

		function POPUP_QUESTIONARIO_onmaximize(){
			document.getElementById('divQuestionario').style.width = '100%';
			document.getElementById('divQuestionario').style.height = '100%';
			document.getElementById('tblQuestionario').style.width = '100%';
			document.getElementById('tblQuestionario').style.height = '100%';
			document.getElementById('tdFrameQuestionario').style.width = '100%';
			document.getElementById('tdFrameQuestionario').style.height = '100%';

			var w = NumberUtil.apenasNumeros(document.getElementById('divIntJanelaPopup_POPUP_QUESTIONARIO').style.width);
			var h = NumberUtil.apenasNumeros(document.getElementById('divIntJanelaPopup_POPUP_QUESTIONARIO').style.height);

			var wInt = NumberUtil.toInteger(w);
			var hInt = NumberUtil.toInteger(h);

			wInt = wInt - 10;
			hInt = hInt - 90;

			document.getElementById('frameQuestionario').width = wInt + '';
			document.getElementById('frameQuestionario').height = hInt + '';

			document.getElementById('imgMaxMinInterno').src = document.getElementById('IMG_POPUP_QUESTIONARIO_MAXMIN').src;
		}
		function POPUP_QUESTIONARIO_onminimize(){
			document.getElementById('divQuestionario').style.width = '100%';
			document.getElementById('divQuestionario').style.height = '100%';
			document.getElementById('tblQuestionario').style.width = '100%';
			document.getElementById('tblQuestionario').style.height = '100%';
			document.getElementById('tdFrameQuestionario').style.width = '100%';
			document.getElementById('tdFrameQuestionario').style.height = '100%';

			var w = NumberUtil.apenasNumeros(document.getElementById('divIntJanelaPopup_POPUP_QUESTIONARIO').style.width);
			var h = NumberUtil.apenasNumeros(document.getElementById('divIntJanelaPopup_POPUP_QUESTIONARIO').style.height);

			var wInt = NumberUtil.toInteger(w);
			var hInt = NumberUtil.toInteger(h);

			wInt = wInt - 10;
			hInt = hInt - 90;

			document.getElementById('frameQuestionario').width = wInt + '';
			document.getElementById('frameQuestionario').height = hInt + '';

			document.getElementById('imgMaxMinInterno').src = document.getElementById('IMG_POPUP_QUESTIONARIO_MAXMIN').src;
		}
		function POPUP_QUESTIONARIO_onCloseButton(){
			if (document.getElementById('btnGravarQuestionario').style.display != 'none'){
				if (!confirm('Deseja realmente fechar este formulário sem salvar ')){
					return false;
				}
			}
			document.body.style.overflow='auto';
			return true;
		}
		function fecharQuestionario(){
			if (document.getElementById('btnGravarQuestionario').style.display != 'none'){
				if (!confirm('Deseja realmente fechar este formulário sem salvar ')){
					return;
				}
			}
			document.body.style.overflow='auto';
			POPUP_QUESTIONARIO.hide();
		}
		function maxMinInterna(){
			POPUP_QUESTIONARIO.maximize();
		}


		function POPUP_QUESTIONARIO2_onmaximize(){
			document.getElementById('divQuestionario2').style.width = '100%';
			document.getElementById('divQuestionario2').style.height = '100%';
			document.getElementById('tblQuestionario2').style.width = '100%';
			document.getElementById('tblQuestionario2').style.height = '100%';
			document.getElementById('tdFrameQuestionario2').style.width = '100%';
			document.getElementById('tdFrameQuestionario2').style.height = '100%';

			var w = NumberUtil.apenasNumeros(document.getElementById('divIntJanelaPopup_POPUP_QUESTIONARIO2').style.width);
			var h = NumberUtil.apenasNumeros(document.getElementById('divIntJanelaPopup_POPUP_QUESTIONARIO2').style.height);

			var wInt = NumberUtil.toInteger(w);
			var hInt = NumberUtil.toInteger(h);

			wInt = wInt - 10;
			hInt = hInt - 80;

			document.getElementById('frameQuestionario2').width = wInt + '';
			document.getElementById('frameQuestionario2').height = hInt + '';
		}
		function POPUP_QUESTIONARIO2_onminimize(){
			document.getElementById('divQuestionario2').style.width = '100%';
			document.getElementById('divQuestionario2').style.height = '100%';
			document.getElementById('tblQuestionario2').style.width = '100%';
			document.getElementById('tblQuestionario2').style.height = '100%';
			document.getElementById('tdFrameQuestionario2').style.width = '100%';
			document.getElementById('tdFrameQuestionario2').style.height = '100%';

			var w = NumberUtil.apenasNumeros(document.getElementById('divIntJanelaPopup_POPUP_QUESTIONARIO2').style.width);
			var h = NumberUtil.apenasNumeros(document.getElementById('divIntJanelaPopup_POPUP_QUESTIONARIO2').style.height);

			var wInt = NumberUtil.toInteger(w);
			var hInt = NumberUtil.toInteger(h);

			wInt = wInt - 10;
			hInt = hInt - 80;

			document.getElementById('frameQuestionario2').width = wInt + '';
			document.getElementById('frameQuestionario2').height = hInt + '';
		}

		function POPUP_QUESTIONARIO3_onmaximize(){
			document.getElementById('divQuestionario3').style.width = '100%';
			document.getElementById('divQuestionario3').style.height = '100%';
			document.getElementById('tblQuestionario3').style.width = '100%';
			document.getElementById('tblQuestionario3').style.height = '100%';
			document.getElementById('tdFrameQuestionario3').style.width = '100%';
			document.getElementById('tdFrameQuestionario3').style.height = '100%';

			var w = NumberUtil.apenasNumeros(document.getElementById('divIntJanelaPopup_POPUP_QUESTIONARIO3').style.width);
			var h = NumberUtil.apenasNumeros(document.getElementById('divIntJanelaPopup_POPUP_QUESTIONARIO3').style.height);

			var wInt = NumberUtil.toInteger(w);
			var hInt = NumberUtil.toInteger(h);

			wInt = wInt - 10;
			hInt = hInt - 60;

			document.getElementById('frameQuestionario3').width = wInt + '';
			document.getElementById('frameQuestionario3').height = hInt + '';
		}
		function POPUP_QUESTIONARIO3_onminimize(){
			document.getElementById('divQuestionario3').style.width = '100%';
			document.getElementById('divQuestionario3').style.height = '100%';
			document.getElementById('tblQuestionario3').style.width = '100%';
			document.getElementById('tblQuestionario3').style.height = '100%';
			document.getElementById('tdFrameQuestionario3').style.width = '100%';
			document.getElementById('tdFrameQuestionario3').style.height = '100%';

			var w = NumberUtil.apenasNumeros(document.getElementById('divIntJanelaPopup_POPUP_QUESTIONARIO3').style.width);
			var h = NumberUtil.apenasNumeros(document.getElementById('divIntJanelaPopup_POPUP_QUESTIONARIO3').style.height);

			var wInt = NumberUtil.toInteger(w);
			var hInt = NumberUtil.toInteger(h);

			wInt = wInt - 10;
			hInt = hInt - 60;

			document.getElementById('frameQuestionario3').width = wInt + '';
			document.getElementById('frameQuestionario3').height = hInt + '';
		}

		function abrirPopupNotificacoesServicos() {
			if (document.getElementById('nomeContrato').value == '') {
				alert('Informe o Contrato.');
				document.getElementById('nomeContrato').focus();
				return;
			}

			document.getElementById('iframeNotificacaoServicoContrato').src =properties.ctx + '/pages/notificacaoServicoContrato/notificacaoServicoContrato.load?idContrato=' + document.formProntuario.idContrato.value;

			$("#POPUP_NOTIFICACAO_SERVICO_CONTRATO").dialog("open");
		}

		function fecharPopupNotificacoesServicos() {
			$("#POPUP_NOTIFICACAO_SERVICO_CONTRATO").dialog("close");
			$('#POPUP_CADASTRO_ALTERACAO').modal('hide');
			showNotificacoes();
		}

		function InformacoesContratoDTO(idServicoContrato){
			this.idServicoContrato = idServicoContrato;
		}

		function paginacao(pag){
			document.getElementById('retPesq').innerHTML = i18n_message("citcorpore.comum.processando_paginacao");
			req = AjaxUtils.defineBrowserAJAX();
			var nocache = new Date();
			var idContrato = document.getElementById('idContrato');

			if (StringUtils.isBlank(document.formProntuario.idContrato.value)){
				alert(i18n_message("contrato.alerta.informe_contrato"));
				return;
			}
			setaContratoSessao();
			if(document.getElementById('campoPesquisa').value  != null && document.getElementById('campoPesquisa').value  != ""){
				acao = '/pages/listaServicosContrato/listaServicosContrato.load?paginacao='+pag+'&idContrato='+idContrato.value+'&pesquisa='+document.getElementById('campoPesquisa').value ;
			}else{
				acao = '/pages/listaServicosContrato/listaServicosContrato.load?paginacao='+pag+'&idContrato='+idContrato.value;
			}

			document.getElementById('direita').innerHTML = '<b>'+i18n_message("citcorpore.comum.aguarde")+'</b>';

			objSubmit = {idContrato:document.formProntuario.idContrato.value,aba:'SERVICOSCONTRATO',paginacao:pag};

			acaoSubmit = properties.ctx + '' + acao;

			urlChamada = acaoSubmit;

			ajaxAction = new AjaxAction();
			ajaxAction.submitObject(objSubmit, acaoSubmit, callBackFuncProntuario);

		}
		function pesquisarServicosContrato(){
			if (StringUtils.isBlank(document.formProntuario.idContrato.value)){
				alert(i18n_message("contrato.alerta.informe_contrato"));
				return;
			}

			habilitaCorpo();
			$('#idMostrarOS').removeClass("active");
			$('#idMostrarFaturas').removeClass("active");
			$('#idMostrarNotificacoes').removeClass("active");

			$('#idMostrarServicosContrato').addClass("active");

			req = AjaxUtils.defineBrowserAJAX();
			var nocache = new Date();
			var idContrato = document.getElementById('idContrato');
			setaContratoSessao();
			if(document.getElementById('campoPesquisa').value  == null || document.getElementById('campoPesquisa').value  == ""){
				acao = '/pages/listaServicosContrato/listaServicosContrato.load?idContrato='+idContrato.value;
			}
			else{


					acao = '/pages/listaServicosContrato/listaServicosContrato.load?idContrato='+idContrato.value+'&pesquisa='+encodeURI(document.getElementById('campoPesquisa').value) ;
			}


			document.getElementById('direita').innerHTML = '<b>'+i18n_message("citcorpore.comum.aguarde")+'</b>';

			objSubmit = {idContrato:document.formProntuario.idContrato.value,aba:'SERVICOSCONTRATO',paginacao:true};

			acaoSubmit = properties.ctx + '' + acao;

			urlChamada = acaoSubmit;

			ajaxAction = new AjaxAction();
			ajaxAction.submitObject(objSubmit, acaoSubmit, callBackFuncProntuario);
		}

		function habilitaCorpo(){
			document.getElementById("corpo").style.display = "block";
		}

		function limpaCamposEGrid(){
			document.formProntuario.clear();
			document.getElementById("corpo").style.display = "none";
		}

		function acaoD(id , desc){
			document.formProntuario.nomeContrato.value = desc;
			document.formProntuario.idContrato.value = id;
			showOS();
		}