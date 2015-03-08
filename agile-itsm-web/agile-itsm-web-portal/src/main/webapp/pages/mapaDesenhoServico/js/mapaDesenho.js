var mapa = null;
addEvent(window, "load", load, false);
function load() {
	$(".popup").dialog({
		width : "50%",
		height : window.screen.height/2,
		modal : true,
		autoOpen : false,
		resizable : false,
		show : "fade",
		hide : "fade",
		beforeClose : limpaFormItemConfiguracao
	});

	mapa = new MapaDesenhoServico(document.getElementById("mapaServicos"));
	mapa.configuraEventos();
};

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

function atualizaServicoMapa(serealizado) {
	mapa.resetaLista();
	mapa = new MapaDesenhoServico(document.getElementById("mapaServicos"));
	mapa.configuraEventos();

	//Recupera o IC com apenas dois clicks. Atualmente utilizado apenas pelo gerenciamento de problema
	if(extrairVariavelDaUrl("selecaoIc") != null){
		mapa.addAfterDBClickItemEvent(function(item){
			parent.selecionaIcPeloMapa(item.idItemConfiguracao, item.identificacao + "-" + item.descricao);
			throw i18n_message("mapaDesenhoServico.itemRecuperadoPorPaginaPai");
		})
	}

	var lista = ObjectUtils.deserializeCollectionFromString(serealizado);
	var imgAux = null;
	var listaAux = [];
	for ( var i = 0; i < lista.length; i++) {
		imgAux = new ImagemItemConfiguracao(mapa.context, lista[i].caminhoImagem);
		imgAux.idImagemItemConfiguracao = lista[i].idImagemItemConfiguracao;
		imgAux.posx = parseInt(lista[i].posx);
		imgAux.posy = parseInt(lista[i].posy);
		imgAux.idServico = lista[i].idServico;
		imgAux.idItemConfiguracao = lista[i].idItemConfiguracao;
		imgAux.idImagemItemConfiguracaoPai = lista[i].idImagemItemConfiguracaoPai != "" ? lista[i].idImagemItemConfiguracaoPai : null;
		if(lista[i].idImagemItemConfiguracaoPaiColSerializado!= null && lista[i].idImagemItemConfiguracaoPaiColSerializado!= ""){
			var colImagemPai = ObjectUtils.deserializeCollectionFromString(lista[i].idImagemItemConfiguracaoPaiColSerializado);
			for(var j = 0; j< colImagemPai.length; j++){
				imgAux.idImagemItemConfiguracaoCol.push(colImagemPai[j]);
			}
		}
		imgAux.descricao = lista[i].descricao;
		imgAux.identificacao = lista[i].identificacao;
		listaAux[i] = imgAux;
	}
	mapa.setListaItens(listaAux.slice());
	document.getElementById("listaItensConfiguracaoSerializada").value = "";
};

/**
 * Quando o item começar a ser arrastado algumas informações
 * serão salvas para serem recuperadas pelo Mapa.
 * @param e
 * Evento dragstart.
 */
function dragstart(e) {
	if (document.form.idServico.value != ""){
		e.dataTransfer.setData("text", e.target.getAttribute("id"));
	}
}

/**
 * Mostra e esconde uma popup.
 */
function togglePopup(id, openClose) {
	$("#" + id).dialog(openClose);
	limpaFormItemConfiguracao();
};

/**
 * Mostra e esconde uma popup.
 */
function togglePopupFrame(id, link, openClose) {
	if(link > "")
		document.getElementById('popupitemc').src ='${ctx}' + link + '?iframe=true';
	else
		document.getElementById('popupitemc').src ='${ctx}' + link;

	$("#" + id).dialog(openClose);
	limpaFormItemConfiguracao();
};

/**
 * Limpa campos específicos.
 */
function limpaFormItemConfiguracao() {
	document.form.idItemConfiguracao.value = "";
	document.getElementById("identificacao").value = "";
	document.getElementById("txtDescricao").value = "";
};

function LOOKUP_SERVICO_DESENHO_select(id, desc) {
	document.getElementById("listaItensConfiguracaoSerializada").value = "";
	var camposLookupServico = desc.split("-");
	document.getElementById("nomeServico").innerHTML = desc;
	document.form.idServico.value = id;
	document.form.fireEvent("selecionarServico");
	togglePopup("POPUP_SERVICO", "close");
};

function LOOKUP_ITEMCONFIGURACAO_select(id, desc) {
	var camposLookupItem = desc.split("-");
	document.form.idItemConfiguracao.value = id;
	document.getElementById("identificacao").value = camposLookupItem[0];
	document.form.fireEvent("selecionarItemConfigurcao");
	togglePopupFrame("POPUPITEMCONFIGURACAO", "", "close");
};

/**
 * Faz a seleção so item de configuração a partir da página de pesquisaItemConfiguracao
 */
function selectedItemConfiguracao(idItem) {
	document.form.idItemConfiguracao.value = idItem;
	document.form.fireEvent("selecionarItemConfigurcao");
	togglePopup("POPUPITEMCONFIGURACAO", "close");
};

/**
 * Motivo: Customização para adaptar o fechamento da popup quando for 'modal' ou 'dialog' dentro de um iframe
 * Autor: flavio.santana
 * Data/hora: 22/01/2015
 */
function voltar(){
	verificarAbandonoSistema = false;
	if(iframe) {
	parent.$("iframe").each(function(iel, el) {
		  if(el.contentWindow === window) {
			  if($(this).parents('div').is('.modal'))
			  {
				  parent.$($(this).parents('div').find('.modal')).modal('hide');
			  }
			  else if($(this).parents('div').is('.ui-dialog-content'))
			  {
				  parent.$($(this).parents('div').find('.ui-dialog-content')).dialog('close');
			  }
		  }
		});
	} else {
		window.location = retorno;
	}
}
