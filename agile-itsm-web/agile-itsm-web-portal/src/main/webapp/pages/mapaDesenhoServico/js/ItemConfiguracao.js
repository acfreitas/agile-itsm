/**
 *  @author breno.guimaraes
 */

function ItemConfiguracao(_nome, _id, imagemItemConfiguracao){
	var idItemConfiguracao = _id;
	var identificacao = _nome;
	var imagem = imagemItemConfiguracao;
	var itemPai;
	
	//GETTERS SETTERS
	this.getTipo = function(){
		return tipo;
	};
	
	this.getIdItemConfiguracao = function(){
		return idItemConfiguracao;
	};
	
	this.setIdItemConfiguracao = function(value){
		idItemConfiguracao = value;
	};
	
	this.getIdentificacao = function(){
		return identificacao;
	};
	
	this.setIdentificacao = function(value){
		identificacao = value;
	};
	
	this.getImagem = function(){
		return imagem;
	};
	
	this.setImagem = function(imgItemConfig){
		imagem = imgItemConfig;
	};
	
	this.getItemPai = function(){
		return itemPai;
	};
	
	this.setItemPai = function(itemConfigPai){
		itemPai = itemConfigPai;
	};
}