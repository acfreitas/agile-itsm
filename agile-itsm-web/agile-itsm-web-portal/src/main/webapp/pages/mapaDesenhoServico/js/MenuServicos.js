/**
 * @author breno.guimaraes
 * @param elementoMenu
 * @param mapaServ
 * @returns {MenuServicos}
 */
function MenuServicos(elementoMenu){
	var self = this;
	var menu = elementoMenu;
	
	this.inicializaMenu = function(){

		var listaItens = new Array();
		listaItens[0] = TipoItemConfiguracao.SOFTWARE;
		listaItens[1] = TipoItemConfiguracao.SERVIDOR;
		listaItens[2] = TipoItemConfiguracao.WINDOWS;
		listaItens[3] = TipoItemConfiguracao.LINUX;
		listaItens[4] = TipoItemConfiguracao.DESKTOP;
	
		var ul = document.createElement('ul');
		var imagem = null;

		for ( var i = 0; i < listaItens.length; i++) {
			// novo item da lista.
			var li = document.createElement('li');

			// nova imagem que será anexada ao item da lista.
			imagem = new Image();
			imagem.src = listaItens[i];
			imagem.setAttribute('id', i);
			// torna arrastável
			imagem.setAttribute('draggable', 'true');
			// classe necessária para ser identificada ao dropar no mapa.
			imagem.setAttribute('class', 'itemInMenu');
			
			imagem.setAttribute("width", "30");
			imagem.setAttribute("height", "30");
			
			// evento de drag.
			imagem.addEventListener("dragstart", dragstart, true);
			
			// anexa a imagem ao item e o item à lista de itens.
			li.appendChild(imagem);
			ul.appendChild(li);

		}		
		menu.appendChild(ul);
		
	};
	
	this.setListaServicos = function(lista){
		listaServicos = lista;
	};
	
	this.getListaServicos = function(){
		return listaServicos;
	};
	
	/**
	 * Quando o item começar a ser arrastado algumas informações
	 * serão salvas para serem recuperadas pelo MapaItemConfiguracao.
	 * @param e
	 * Evento dragstart.
	 */
	function dragstart(e) {
		
		e.dataTransfer.setData('text', e.target.getAttribute('id'));
	}
}

