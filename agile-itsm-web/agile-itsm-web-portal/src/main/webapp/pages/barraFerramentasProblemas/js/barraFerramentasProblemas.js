var popup;
var menusLiberados;
var todosOsMenus;
var objTab = null;

function LOOKUP_OCORRENCIA_PROBLEMA_select(id, desc) {
	document.formOcorrenciaProblema.restore({
		idOcorrencia : id
	});
}

function salvar() {
	document.formOcorrenciaProblema.descricao.value = document.formOcorrenciaProblema.descricao1.value;
	if (document.getElementById("idOcorrencia").value != null && document.getElementById("idOcorrencia").value != "") {
		alert(i18n_message("gerenciaservico.suspensaosolicitacao.validacao.alteraregistroocorrencia") );
	} else {
		document.formOcorrenciaProblema.save();
	}
}

function fecharBaseConhecimentoView() {
	$('#popupCadastroRapido').dialog('close');
}

$(function() {
	popupBarraFerramenta = new PopupManager(1300, 600, "${ctx}/pages/");
	popupCadastroCategoriaOcorrencia = new PopupManager(1200, 450, "${ctx}/pages/");
	popupCadastroOrigemOcorrencia = new PopupManager(1200, 450, "${ctx}/pages/");

	document.formOcorrenciaProblema.afterRestore = function() {
		$('.tabs').tabs('select', 0);
	};

	$(".POPUP_barraFerramentasProblemas").dialog({
		autoOpen : false,
		width : "90%",
		height :  $(window).height()-100,
		modal : true
	});

	$(".POPUP_UPLOADREQUISICAOPROBLEMA").dialog({
		autoOpen : false,
		width : 1000,
		height : 580,
		modal : true
	});

	$(".POPUP_LEITURAEMAIL").dialog({
		autoOpen : false,
		width : 1000,
		height : 580,
		modal : true
	});

	$('.POPUP_PESQUISA_CATEGORIA_OCORRENCIA').dialog({
		autoOpen: false,
		width: 900,
		height: 450,
		modal: true
	});

	$('.POPUP_PESQUISA_ORIGEM_OCORRENCIA').dialog({
		autoOpen: false,
		width: 900,
		height: 450,
		modal: true
	});

	menusLiberados = [];
	todosOsMenus = [];
	todosOsMenus.push("btAnexos");
	todosOsMenus.push("btOcorrencias");
		todosOsMenus.push("btBaseConhecimentoView");
		menusLiberados.push("btBaseConhecimentoView");

	//adicionar um IF para verificar ID de Serviço quando já estier sendo feito o restore.
	liberaMenusDeConsulta();
	escondeMenusBloqueadosEMostraLiberados();

	/**
		* O nome do método já diz tudo.
		*/
	function escondeMenusBloqueadosEMostraLiberados() {
		for(var i = 0; i < todosOsMenus.length; i++) {
			if (document.getElementById(todosOsMenus[i]) != null) {
				if(!isLiberado(todosOsMenus[i]) ) {
					document.getElementById(todosOsMenus[i]).style.display = "none";
				} else {
					document.getElementById(todosOsMenus[i]).style.display = "block";
				}
			}
		}
	}

	function ocultarAnexos(){
		$('#formularioDeAnexos').hide();
	}

	function exibirAnexos(){
		$('#formularioDeAnexos').show();
	}

	/**
	 * Só deve ser chamada quando houver restore do serviço
	 */
	function liberaMenusDeConsulta() {
		menusLiberados.push("btAnexos");
		menusLiberados.push("btOcorrencias");
			menusLiberados.push("btBaseConhecimentoView");
	}

	/**
	 * Bloqueia os menus que não devem ser acessados caso seja
	 * nova solicitação de serviço.
	 */
	function bloqueiaMenusDeConsulta() {
		deletaItemLista("btAnexos");
		deletaItemLista("btOcorrencias");
		alert(1)
	}

	/**
	 * Deleta um item da lista e a reordena.
	 */
	function deletaItemLista(nomeItem) {
		var novaLista = [];
		for(var i = 0; i < menusLiberados.length; i++) {
			if(menusLiberados[i] == nomeItem) {
				menusLiberados[i] = null;
			} else {
				novaLista.push(menusLiberados[i]);
			}
		}
		menusLiberados = [];
		menusLiberados = null;
		menusLiberados = novaLista;
	}

	/**
	 * Verifica se um item está na lista de liberação.
	 */
	function isLiberado(nomeMenu) {
		for(var i = 0; i < menusLiberados.length; i++) {
			if(menusLiberados[i] == nomeMenu) {
				return true;
			}
		}
		return false;
	}

	//ações dos botões
	$("#btAnexos").click(function() {
		document.form.fireEvent("verificarParametroAnexos");
		$('#POPUP_menuAnexos').dialog('open');
		uploadRequisicaoProblema.refresh();
	});

	$("#btLeituraEmail").click(function() {
		$('#POPUP_leituraEmails').dialog('open');
	});

	$("#btOcorrencias").click(function() {
		document.formOcorrenciaProblema.clear();
		document.formOcorrenciaProblema.idProblema.value = document.form.idProblema.value;
		document.formOcorrenciaProblema.fireEvent('load');
		document.getElementById('divRelacaoOcorrencias').innerHTML = i18n_message("citcorpore.comum.aguardecarregando");
		$("#POPUP_menuOcorrencias").dialog("open");
		//posteriormente trocar pelo serviço carregado
		document.getElementById("pesqLockupLOOKUP_OCORRENCIA_PROBLEMA").value = 1;
		document.getElementById("btnTodos").style.display = "none";
	});

	$("#btRelacionarSolicitacao").click(function() {
		inicializarTemporizadorRel2();
		document.formIncidentesRelacionados.fireEvent("listarSolicitacoesServicoEmAndamento");
		$("#divSolicitacoesFilhas").dialog("open");
	});

	$("#btRelacionarSolicitacaoFechar").click(function() {
		$('#POPUP_menuIncidentesRelacionados').dialog('close');
	});

		$("#btBaseConhecimentoView").click(function() {
			//dialog tratado pelo PopupManager.js
			$("#popupCadastroRapido").dialog({height : $(window).height()-100,width :"90%"});
			popupBarraFerramenta.titulo = "<fmt:message key='baseConhecimento.consultaABaseConhecimento' />";
			popupBarraFerramenta.abrePopup('baseConhecimentoView', '');
		});

		$("#btErroConhecido").click(function() {
			//dialog tratado pelo PopupManager.js
			$("#popupCadastroRapido").dialog({height : $(window).height()-100,width :"90%"});
			popupBarraFerramenta.titulo = "<fmt:message key='categoriaProblema.consultaAErrosConhecidos' />";
			popupBarraFerramenta.abrePopup('pesquisaErroConhecido', '');
		});

	$("#btnFecharTelaAnexos").click(function() {
		$('#POPUP_menuAnexos').dialog('close');
	});

	$('#nomeCategoriaOcorrencia').click(function() {
		$('#POPUP_PESQUISA_CATEGORIA_OCORRENCIA').dialog('open')
	});

	$('#nomeOrigemOcorrencia').click(function() {
		$('#POPUP_PESQUISA_ORIGEM_OCORRENCIA').dialog('open')
	});
});

function gravarAnexo() {
	document.form.idRequisicaoProblema.disabled = false;
	document.form.fireEvent("gravarAnexo");
}

function abrirPopupCadastroCategoriaOcorrencia() {
	popupCadastroCategoriaOcorrencia.titulo = i18n_message("citcorpore.comum.categoriaOcorrencia");
	popupCadastroCategoriaOcorrencia.abrePopupParms('categoriaOcorrencia', '');
}

function LOOKUP_CATEGORIA_OCORRENCIA_select(id, desc) {
	$('#idCategoriaOcorrencia').val(id);
	$('#nomeCategoriaOcorrencia').val(desc);
	$('.POPUP_PESQUISA_CATEGORIA_OCORRENCIA').dialog('close');
}

function LOOKUP_ORIGEM_OCORRENCIA_select(id, desc) {
	$('#idOrigemOcorrencia').val(id);
	$('#nomeOrigemOcorrencia').val(desc);
	$('.POPUP_PESQUISA_ORIGEM_OCORRENCIA').dialog('close');
}

function abrirPopupCadastroOrigemOcorrencia() {
	popupCadastroOrigemOcorrencia.titulo = i18n_message("citcorpore.comum.origemOcorrencia");
	popupCadastroOrigemOcorrencia.abrePopupParms('origemOcorrencia', '');
}

function excluir() {
	if (document.getElementById("id").value != "") {
		if (confirm(i18n_message("citcorpore.comum.deleta") ) ) {
			document.formPesquisaCategoriaOcorrencia.fireEvent("delete");
		}
	}
}