var popup;
var menusLiberados;
var todosOsMenus;
var objTab = null;

function LOOKUP_OCORRENCIA_SOLICITACAO_select(id, desc) {
	document.formOcorrenciaSolicitacao.restore({
		idOcorrencia : id
	});
}

function salvar() {
	document.formOcorrenciaSolicitacao.descricao.value = document.formOcorrenciaSolicitacao.descricao1.value;
	if (document.getElementById("idOcorrencia").value != null && document.getElementById("idOcorrencia").value != "") {
		alert(i18n_message("gerenciaservico.suspensaosolicitacao.validacao.alteraregistroocorrencia") );
	} else {
		document.formOcorrenciaSolicitacao.save();
	}
}

function fecharBaseConhecimentoView() {
	$('#popupCadastroRapido').dialog('close');
}

$(function() {
	popupBarraFerramenta = new PopupManager(1300, 600, "${ctx}/pages/");
	popupCadastroCategoriaOcorrencia = new PopupManager(1200, 450, "${ctx}/pages/");
	popupCadastroOrigemOcorrencia = new PopupManager(1200, 450, "${ctx}/pages/");

	document.formOcorrenciaSolicitacao.afterRestore = function() {
		$('.tabs').tabs('select', 0);
	};

	$(".POPUP_barraFerramentasIncidentes").dialog({
		autoOpen : false,
		width : 1000,
		height : 580,
		modal : true
	});

	$('.POPUP_PESQUISA_CATEGORIA_OCORRENCIA').dialog({
		autoOpen: false,
		width: 600,
		height: 400,
		modal: true
	});

	$('.POPUP_PESQUISA_ORIGEM_OCORRENCIA').dialog({
		autoOpen: false,
		width: 600,
		height: 400,
		modal: true
	});

	menusLiberados = [];
	todosOsMenus = [];
	todosOsMenus.push("btAnexos");
	todosOsMenus.push("btOcorrencias");
	todosOsMenus.push("btIncidentesRelacionados");
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
			if(!isLiberado(todosOsMenus[i]) ) {
				document.getElementById(todosOsMenus[i]).style.display = "none";
			} else {
				document.getElementById(todosOsMenus[i]).style.display = "block";
			}
		}
	}

	/**
	 * Só deve ser chamada quando houver restore do serviço
	 */
	function liberaMenusDeConsulta() {
		menusLiberados.push("btAnexos");
		menusLiberados.push("btOcorrencias");
		menusLiberados.push("btIncidentesRelacionados");
		menusLiberados.push("btBaseConhecimentoView");
	}

	/**
	 * Bloqueia os menus que não devem ser acessados caso seja
	 * nova solicitação de serviço.
	 */
	function bloqueiaMenusDeConsulta() {
		deletaItemLista("btAnexos");
		deletaItemLista("btOcorrencias");
		deletaItemLista("btIncidentesRelacionados");
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
		var parametrosUploadValidos = ${parametrosUploadValidos};
		if (parametrosUploadValidos) {
			//a popup é aberta do lado do java.
			document.formUpload.fireEvent("valida");

			$('#POPUP_menuAnexos').dialog('open');
			uploadAnexos.refresh();
		} else {
			alert(i18n_message('citcorpore.comum.diretorioUploadEGedInvalidos'));
		}
	});

	$("#btOcorrencias").click(function() {
		document.formOcorrenciaSolicitacao.clear();
		document.formOcorrenciaSolicitacao.idSolicitacaoOcorrencia.value = document.form.idSolicitacaoServico.value;
		document.formOcorrenciaSolicitacao.fireEvent('load');
		document.getElementById('divRelacaoOcorrencias').innerHTML = i18n_message("citcorpore.comum.aguardecarregando");
		document.formOcorrenciaSolicitacao.fireEvent('listOcorrenciasSituacao');
		$("#POPUP_menuOcorrencias").dialog("open");
		//posteriormente trocar pelo serviço carregado
		document.getElementById("pesqLockupLOOKUP_OCORRENCIA_SOLICITACAO_IDSOLICITACAOSERVICO").value = 1;
		document.getElementById("btnTodos").style.display = "none";
	});


	$("#btIncidentesRelacionados").click(function() {
		//popup aberta do lado java
		document.formIncidentesRelacionados.idSolicitacaoIncRel.value = document.form.idSolicitacaoServico.value;
		inicializarTemporizadorRel1();
		document.formIncidentesRelacionados.fireEvent("restore");
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
		popupBarraFerramenta.titulo = "<fmt:message key='baseConhecimento.consultaABaseConhecimento' />";
		dimensionaPopupCadastroRapido("1300","700");
		popupBarraFerramenta.abrePopup('baseConhecimentoView', '');
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
	document.form.idSolicitacaoServico.disabled = false;
	document.form.fireEvent("gravarAnexo");
}

function abrirPopupCadastroCategoriaOcorrencia() {
	dimensionaPopupCadastroRapido("900","500");
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
	dimensionaPopupCadastroRapido("900","500");
	popupCadastroOrigemOcorrencia.abrePopupParms('origemOcorrencia', '');
}

function excluir() {
	if (document.getElementById("id").value != "") {
		if (confirm(i18n_message("citcorpore.comum.deleta") ) ) {
			document.formPesquisaCategoriaOcorrencia.fireEvent("delete");
		}
	}
}