/**
 *  @author breno.guimaraes
 */

function ImagemItemConfiguracao(_context, _caminho){
	
	this.idImagemItemConfiguracao = 0;
	this.idServico;
    this.idItemConfiguracao;
    this.idImagemItemConfiguracaoPai ;
    this.idImagemItemConfiguracaoCol = [];
    this.caminhoImagem = _caminho;
    this.identificacao;
	
    this.descricao;

    var self = this;
	var context = _context;
	var pastaImagens = "imagens/";
	
	
	var imageObj;
	var areaOpcoes = {
			width : 15,
			height : 15,
			xPos : 30,
			yPos : 30
	};
	
	this.posx;
	this.posy;
	
	var width;
	var height;
	
	
	var xpos2;
	var ypos2;

	var widthRef;
	var heightRef;
	
	function atualizaAreaOpcoes(){
		areaOpcoes.xPos = areaOpcoes.xPos+(areaOpcoes.width/2);
		areaOpcoes.yPos = areaOpcoes.xPos+(areaOpcoes.height/2);
	};
	
	this.getPosXAreaOpcoes = function(){
		return this.getXPos() + areaOpcoes.xPos;
	};
	
	this.getPosYAreaOpcoes = function(){
		return this.getYPos() + areaOpcoes.yPos;
	};

	// GETTERS & SETTERS
	/**
	 * Retorna o caminho da imagem com o nome da pasta
	 * raiz + o nome do tipo de item configura��o.
	 */
	this.getCaminhoImagem = function(){
		return pastaImagens + this.caminhoImagem;
	};
	
	this.setImageObj = function(obj){
		imageObj = obj;
	};
	
	this.getImageObj = function(){
		return imageObj;
	};
	
	this.getWidthRef = function(){
		return widthRef;
	};
	
	this.getHeightRef = function(){
		return heightRef;
	};
	
	this.getCaminho = function(){
		return this.caminhoImagem;
	};
	
	this.getContext = function(){
		return context;
	};
	
	this.setContext = function(ctx){
		context = ctx;
	};
	
	this.getAreaOpcoes = function(){
		return areaOpcoes;
	};
	
	this.setYPos = function(value){
		this.posy = parseInt(value);
		//atualizaAreaOpcoes();
	};
	
	this.getYPos = function(){
		return this.posy;
	};
	
	this.setXPos = function(value){
		this.posx = parseInt(value);
		//atualizaAreaOpcoes();
	};
	
	this.getXPos = function(){
		return this.posx;
	};
	
	
	this.getX2Pos = function(){
		return this.getXPos() + this.getWidth();
	};
	
	this.getY2Pos = function(){
		return this.getYPos() + this.getHeight();
	};
	
	
	this.getHeight = function(){
		return height;
	};
	
	this.setHeight = function(value){
		height = value;
		this.heightRef = this.heightRef == null ? this.getHeight() : this.heightRef;
		atualizaAreaOpcoes();
	};
	
	this.getWidth = function(){
		return width;
	};
	
	this.setWidth = function(value){
		width = value;
		this.widthRef = this.widthRef == null ? this.getWidth() : this.widthRef;
		atualizaAreaOpcoes();
	};
	
	this.getXCentral = function(){
		return this.getXPos() + (this.getWidth()/2);
	};
	
	this.getYCentral = function(){
		return this.getYPos() + (this.getWidth()/2);
	};
	
	this.getAreaOpcoes = function(){
		return areaOpcoes;
	};
	
	this.setIdServico = function(value){
		this.idServico = value;
	};
	
	this.getIdServico = function(){
		return this.idServico;
	};
	
	this.setIdItemConfiguracao = function(value){
		this.idItemConfiguracao = value;
	};
	
	this.getIdItemConfiguracao = function(){
		return this.idItemConfiguracao;
	};
	
	this.setDescricao = function(value){
		this.descricao = value;
	};
	
	this.getDescricao = function(){
		return this.descricao;
	};
	

	/**
	 * Desenha bordas em torno da imagem.
	 */
	this.desenharBorda = function() {
		this.getContext().beginPath();	
		this.getContext().moveTo(this.getXPos(), this.getYPos());
		this.getContext().lineTo(this.getX2Pos(), this.getYPos());
		this.getContext().lineTo(this.getX2Pos(), this.getY2Pos());
		this.getContext().lineTo(this.getXPos(), this.getY2Pos());
		this.getContext().lineTo(this.getXPos(), this.getYPos());
		this.getContext().stroke();
		this.getContext().closePath();
	}
	
	this.desenhaCentro = function(){
		
		this.getContext().beginPath();
		
		this.getContext().fillStyle = "#000000";
		
		this.getContext().fillRect(this.getPosXAreaOpcoes(),
								   this.getPosYAreaOpcoes(),
							  	   areaOpcoes.width, areaOpcoes.height);
		this.getContext().fill();
		
		
		this.getContext().stroke();
		this.getContext().closePath();
	}
}