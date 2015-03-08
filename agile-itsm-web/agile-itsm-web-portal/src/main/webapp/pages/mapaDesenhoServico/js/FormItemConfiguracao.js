/**
 * @author breno.guimaraes
 */

function FormItemConfiguracao(elementoForm){
	var self = this;
	var form = elementoForm;
	var listaTipos;
	var listaItens;
	var pastaImagens = "imagens/";
	
	var componenteItens;
	var componentesTipos;
	
	/**
	 * 
	 */
	this.setListaTipos = function(lista){
		listaTipos = lista;
	};
	
	this. getListaTipos = function(){
		return listaTipos;
	};
	
	/**
	 * 
	 */
	this.setListaItens = function(lista){
		listaItens = lista;
	};
	
	this.getListaItens = function(){
		return listaItens;
	};
	
	this.getListaItensByIdTipo = function(id){
		var listaAux = new Array();
		var j = 0;
		for(var it in listaItens){
			if(listaItens[it].getTipo().getId() == id){
				listaAux[j] = listaItens[it];
				j++;
			}
		}
		
		return listaAux;
	};
	
	/**
	 * 
	 */
	this.findItemById = function(id){
		var itemRetorno = null;
		for(var it in listaItens){
			if(listaItens[it].getId() == id){
				itemRetorno = listaItens[it];
				break;
			}
		}		
		return itemRetorno;
	};
	
	/**
	 * 
	 */
	this.findTipoById = function(id){
		var itemRetorno = null;
		for(var it in listaTipos){
			if(listaTipos[it].getId() == id){
				itemRetorno = listaTipos[it];
				break;
			}
		}
		
		return itemRetorno;
	};
	
	
	/**
	 * 
	 */
	this.atualizarListaTipos = function(selecionado){
		componentesTipos = document.getElementById("listaTiposItensConfiguracao");
		initComponente(componentesTipos);
		
		for(var tp in listaTipos){
			componentesTipos[componentesTipos.length] = new Option(listaTipos[tp].getNome(), listaTipos[tp].getId());
		}
		
		if(selecionado != null){
			componentesTipos.selectedIndex = selecionado;
		}
		
		componentesTipos.onchange = function(){
			var imagem = document.getElementById("imgExemplo");
			var tipoAtual = self.findTipoById(componentesTipos[componentesTipos.selectedIndex].value);
			imagem.src = tipoAtual.getCaminhoImagem();
			self.atualizarListaItens();
			
		};
	};
	
	
	this.atualizarListaItens = function(selecionado){
		componenteItens = document.getElementById("listaItensConfiguracao");
		initComponente(componenteItens);
		
		var listaAux = this.getListaItensByIdTipo(document.getElementById("listaTiposItensConfiguracao").value);
		for(var it in listaAux){
			componenteItens[componenteItens.length] = new Option(listaAux[it].getNome(), listaAux[it].getId());
		}
		
		componenteItens.onchange = function(){
			//var itemConf = self.findItemById(componente[componente.selectedIndex].value);
			//getCaminhoImagem
			
			self.atualizarImagem();
		};
	};
	
	this.atualizarImagem = function(tipo){
		var imagem = document.getElementById("imgExemplo");
		imagem.src = itemConf.getImagem().getCaminho() + ".png";
	};	
	
	
	/**
	 * 
	 * @param componente
	 * @returns
	 */
	var initComponente = function(componente){
		componente.length = 0;
		componente[0] = new Option("-- Selecione --",0);
	};
	
	
}