
var TipoLinha = {RETA : 0, DOIS_PONTOS : 1, TRES_PONTOS : 2};

var ACIMA = 0;
var DIREITA = 1;
var ABAIXO = 2;
var ESQUERDA = 3;

/**
 * 
 */
function MapaDesenhoServico(_canvas){
	this.canvas = _canvas;
	this.context = this.canvas.getContext("2d");
	this.listaItens = [];
	this.listaItens.length = 0;
	this.canvas.width = document.body.offsetWidth/1.016;
	this.canvas.height = window.screen.height/1.36;

	var self = this;
	var tipoLinha = TipoLinha.TRES_PONTOS;
	
	var itemSelecionado = null;
	var interval = null;
	var salvo = false;
	
	var afterDBClickItemEvents = [];
	
	/**
	 * 
	 * @param bool
	 * @returns
	 */
	var setSalvo = function(bool){
		salvo = bool;
	}
	
	this.addAfterDBClickItemEvent = function(funcao){
		afterDBClickItemEvents.push(funcao);
	}
	
	var fireDBClickEvents = function(item){
		if(afterDBClickItemEvents == null){
			return;
		}
		
		for(var i = 0; i < afterDBClickItemEvents.length; i++){
			var f = afterDBClickItemEvents[i];
			f(item);
		}
	}
	
	/**
	 *
	 */
	this.salvarServico = function() {
		setSalvo(true);
		if (document.form.idServico.value == null || document.form.idServico.value == "") {
			
			$.growl.notice({ message: i18n_message("citcorpore.comum.selecioneUmServico") });
			//UtilMapa.mostrarMsgTemporaria("msg_erro", 4, i18n_message("citcorpore.comum.selecioneUmServico"));
			return;
		}
		
		for(var i = 0; i< mapa.listaItens.length;i++){
			mapa.listaItens[i].idImagemItemConfiguracaoPaiColSerializado = ObjectUtils.serializeObjects(mapa.listaItens[i].idImagemItemConfiguracaoCol);
		}
		var listaSerializada = ObjectUtils.serializeObjects(mapa.listaItens);
		document.form.listaItensConfiguracaoSerializada.value = listaSerializada;
		document.form.fireEvent("salvarServico");
	};
	
	/**
	 * 
	 */
	this.imprimirDesenhoMapaServicos = function(){
		 if(document.form.idServico.value == null || document.form.idServico.value == ""){
			 $.growl.notice({ message: i18n_message("citcorpore.comum.selecioneUmServico") });
			 //UtilMapa.mostrarMsgTemporaria("msg_erro", 4, i18n_message("citcorpore.comum.selecioneUmServico"));
			 }else{
				 document.getElementById('btnVoltar').style.display = 'none';
				 document.getElementById('menu').style.display = 'none';
				 window.print();
				 document.getElementById('btnVoltar').style.display = 'block';
				 document.getElementById('menu').style.display = 'block';
			 }
		};
	/**
	 * 
	 */
	this.resetaLista = function(){
		this.listaItens = [];
	};
	
	this.configuraEventos = function(){
		//eventos para prevenir bugs
		this.canvas.addEventListener("dragenter", prevent, true);
		this.canvas.addEventListener("dragover", handleDragOver, true);
		this.canvas.addEventListener("dragexit", prevent, true);
		//quando um item for solto dentro do canvas
		this.canvas.addEventListener("drop", droped, true);

		this.canvas.addEventListener("dblclick", dblclick, true);
		this.canvas.addEventListener("mousedown", mouseDown, true);
		this.canvas.addEventListener("mouseup", mouseUp, true);
		//se o mouse sair do canvas executo a ação de mouse up e a ação de arrastar termina
		this.canvas.addEventListener("mouseout", mouseUp, true);
	}
	
	// drag over
    function handleDragOver(event) {
        event.stopPropagation();
        event.preventDefault();
        self.canvas.style.background = '#EDEDED';
    }

	/**
	 * Evento de click que será tratado posteriormente para edição.
	 * @param e
	 * Evento de click.
	 * @returns
	 */
	var dblclick = function(e){
		itemSelecionado = findItemByPosition(getRelativeCursorPoint(e));
		
		
		if(itemSelecionado != null){			
			fireDBClickEvents(itemSelecionado.objeto);

			$("#editarItemConfiguracao").dialog("open");			

			var btSalvar = document.getElementById("btSalvar");
			var btExcluir = document.getElementById("btExcluir");
			var editarItemConfiguracao = document.getElementById("editarItemConfiguracao");

			btExcluir.style.display = "block";
			document.form.idImagemItemConfiguracao.value = itemSelecionado.objeto.idImagemItemConfiguracao;
			document.form.idItemConfiguracao.value = itemSelecionado.objeto.idItemConfiguracao;
			document.getElementById("identificacao").value = itemSelecionado.objeto.identificacao;
			document.getElementById("txtDescricao").value = itemSelecionado.objeto.descricao;
//			document.form.identificacao.value = itemSelecionado.objeto.identificacao;
//			document.form.txtDescricao.value = itemSelecionado.objeto.descricao;			
			btSalvar.onclick = function(){
				itemSelecionado.objeto.idItemConfiguracao = document.form.idItemConfiguracao.value;
				//itemSelecionado.objeto.identificacao = document.form.identificacao.value;
				//itemSelecionado.objeto.descricao = document.form.txtDescricao.value;				
				itemSelecionado.objeto.identificacao = document.getElementById("identificacao").value;
				itemSelecionado.objeto.descricao = document.getElementById("txtDescricao").value;
				limparFecharPopup();			
				self.salvarServico();
			};			
			btExcluir.onclick = function(){
				if(itemSelecionado.objeto.idImagemItemConfiguracao != 0){					
					document.form.idImagemItemConfiguracao.value = itemSelecionado.objeto.idImagemItemConfiguracao;
					document.form.fireEvent("excluirImagemItemConfiguracao");				
				} else {
					self.listaItens.splice(itemSelecionado.index, 1);
					atualizarCanvas();
				}			
				self.salvarServico();
				limparFecharPopup();
			};		
		}
		
	};
	
	/**
	 * 
	 * @param e
	 * @returns
	 */
	var mouseDown = function(e) {
		//A funcionalidade sï¿½ funcionarï¿½ se esse evento for criado aqui.
		self.canvas.addEventListener("mousemove", mouseClickedMove, false);
		itemSelecionado = findItemByPosition(getRelativeCursorPoint(e));					
		if(interval == null){
			/*
			 * Quanto menor for o segundo parï¿½metro, mais rï¿½pido a tela serï¿½ atualizada,
			 * porï¿½m pode deixar tudo mais lento dependendo do volume de itens no mapa. 
			 */
			//interval = setInterval(atualizarCanvas, 10);
		}
	};
	
	/**
	 * Quando o mouse se mover (cada milï¿½metro), se houver
	 * um item selecionado executa aï¿½ï¿½es de movimento.
	 * @param e
	 * Evento.
	 */
	var posicaoMouse;
	var mouseClickedMove = function(e) {
		self.canvas.style.cursor = 'move';	
		//self.canvas.style.background = '#EDEDED';
		posicaoMouse = getRelativeCursorPoint(e);
		if(itemSelecionado != null){
			/*
			 * Se o usuï¿½rio tiver clicado na ï¿½rea de opces da figura, uma
			 * linha serï¿½ traï¿½ada do ponto da imagem atï¿½ o ponto do mouse.
			 */
			atualizarCanvas();
			if(itemSelecionado.opcoes){	
				self.context.beginPath(); 
				var centroXAreaOpcoes = itemSelecionado.objeto.posx + 
										itemSelecionado.objeto.areaOpcoes.posx + 
										(itemSelecionado.objeto.areaOpcoes.width/2);
				var centroYAreaOpcoes = itemSelecionado.objeto.posy + 
										itemSelecionado.objeto.areaOpcoes.posy + 
										(itemSelecionado.objeto.areaOpcoes.height/2);
				self.context.moveTo(centroXAreaOpcoes ,centroYAreaOpcoes);
				self.context.lineTo(posicaoMouse.x, posicaoMouse.y);
				self.context.stroke();
				self.context.closePath();
			} else {		
				var limiteTempX = (self.canvas.width - itemSelecionado.objeto.width);
				var limiteTempY = (self.canvas.height - itemSelecionado.objeto.height);		
				itemSelecionado.objeto.posx = parseInt(posicaoMouse.x <= limiteTempX ? (posicaoMouse.x - itemSelecionado.objeto.width/2) : limiteTempX);
				itemSelecionado.objeto.posy = parseInt(posicaoMouse.y <= limiteTempY ? (posicaoMouse.y - itemSelecionado.objeto.height/2): limiteTempY);	
			}		
		}		
	};
	
	/**
	 * Quando o mouse nï¿½o estiver mais sendo pressionado, armazena
	 * algumas configuraï¿½ï¿½es e finaliza a aï¿½ï¿½o de arrastar.
	 * @param e
	 * Evento.
	 */
	var mouseUp = function(e) {
		self.canvas.removeEventListener("mousemove", mouseClickedMove, false);		
		//Verifica se a linha foi solta em cima de um outro item para tornï¿½-lo filho.
		if(itemSelecionado != null && itemSelecionado.opcoes){
			//Conclui o desenho da linha, caso haja.
//			self.context.closePath();	
			var filho = findItemByPosition(getRelativeCursorPoint(e));			
			if(filho != null && filho.objeto.idImagemItemConfiguracao != itemSelecionado.objeto.idImagemItemConfiguracao){
				if(itemSelecionado.objeto.idImagemItemConfiguracao == 0){
					 $.growl.notice({ message: i18n_message("mapaDesenhoServico.esteItemAindaNaoFoiSalvo") });
					//alert(i18n_message("mapaDesenhoServico.esteItemAindaNaoFoiSalvo"));
				} else {
					var idImagemItemConfiguracaoPai = itemSelecionado.objeto.idImagemItemConfiguracao;
					var idImagemItemConfiguracaoFilho = filho.objeto.idImagemItemConfiguracao;
					var imgItemConfiguracao = new ImagemItemConfiguracaoRelacaoDTO(idImagemItemConfiguracaoFilho, idImagemItemConfiguracaoPai);
					filho.objeto.idImagemItemConfiguracaoCol.push(imgItemConfiguracao);

				}				
			}
		}
		if(interval != null){
			clearInterval(interval);
			interval = null;
		}		
		self.canvas.style.cursor = 'default';
		self.canvas.style.background = '';
		atualizarCanvas();
	};
	
	/**
	 * Executador em eventos especï¿½ficos para evitar bugs.
	 * @param e
	 * Evento.
	 */
	var prevent = function(e) {
		e.preventDefault();
		if (e.stopPropagation) {
			// this code is for Mozilla and Opera
			e.stopPropagation();
		} else if (window.e) {
			// this code is for IE
			window.e.cancelBubble = true;
		}
		return false;
	};

	/**
	 * Quando um item do menu ï¿½ solto dentro do canvas
	 * anexa este novo item com configuraï¿½ï¿½es default.
	 * @param e
	 * Evento.
	 */
	var droped = function(e) {
		prevent(e);
		 self.canvas.style.background = '';
		if(UtilMapa.trim(document.getElementById("idServico").value) == ""){
			 $.growl({ title: i18n_message("citcorpore.comum.aviso"), message: i18n_message("citcorpore.comum.selecioneUmServico") });
			//UtilMapa.mostrarMsgTemporaria("msg_erro", 4,  i18n_message("citcorpore.comum.selecioneUmServico"));
			return;
		}		
		/*
		 * Pega o id passado no dataTransfer do objeto MenuItemConfiguracao
		 * e busca o elemento da pï¿½gina que tenha esse id para copiï¿½-lo para o canvas.
		 */
		var imagemObj = document.getElementById(e.dataTransfer.getData("text"));	
		if(imagemObj == null){
			 $.growl.notice({ message: i18n_message("mapaDesenhoServico.itemInvalido") });
			//UtilMapa.mostrarMsgTemporaria("msg_erro", 4, i18n_message("mapaDesenhoServico.itemInvalido"));
			return;
		}
		var classValue = imagemObj.getAttribute('class');		
		//Mapa sï¿½ aceita objetos da classe itemInMenu.	
		if (classValue.indexOf("itemInMenu") >= 0) {
			var editarItemConfiguracao = document.getElementById("editarItemConfiguracao");
			$("#editarItemConfiguracao").dialog("open");
			var btSalvar = document.getElementById("btSalvar");
			/*
			 * Sï¿½ serï¿½ efetivado o anexo do item quando usuï¿½rio clicar em SALVAR. 
			 */
			btSalvar.onclick = function(){	
				if(document.getElementById("identificacao").value == ""){
					$.growl.notice({ message: i18n_message("mapaDesenhoServico.selecioneItemConfiguracao") });
					//UtilMapa.mostrarMsgTemporaria("msg_erro_form_item", 4, i18n_message("mapaDesenhoServico.selecioneItemConfiguracao"));
					return
				}
				var imagemItemConfiguracao = new ImagemItemConfiguracao(self.context, imagemObj.getAttribute('src'));
				imagemItemConfiguracao.idServico = document.getElementById("idServico").value;
				imagemItemConfiguracao.idItemConfiguracao = document.getElementById("idItemConfiguracao").value
				imagemItemConfiguracao.identificacao = document.getElementById("identificacao").value
				imagemItemConfiguracao.descricao = document.getElementById("txtDescricao").value
				var cursorPoint = getRelativeCursorPoint(e);
				imagemItemConfiguracao.posx = parseInt(cursorPoint.x-30);
				imagemItemConfiguracao.posy = parseInt(cursorPoint.y-80);			
				imagemItemConfiguracao.carregaDesenhaImagem();
				self.listaItens.push(imagemItemConfiguracao);
				limparFecharPopup();
				self.salvarServico();
				//UtilMapa.mostrarMsgTemporaria("msg", 4, "Item adicionado com sucesso!");
			}
		}
	};
	
	/**
	 * 
	 */
	this.addImagemItemConfiguracao = function(imagemItemConfiguracao){
		//this.listaItens[this.listaItens.length] = imagemItemConfiguracao;
		this.listaItens.push(imagemItemConfiguracao);
		imagemItemConfiguracao.carregaDesenhaImagem();
		atualizarCanvas();
		this.salvarServico();
	};
	
	/**
	 * 
	 */
	this.setListaItens = function(novaLista){
		this.listaItens = novaLista;
		for(var i = 0 ; i < this.listaItens.length; i++){
			this.listaItens[i].carregaDesenhaImagem();
		}
		//espera meio segundo para dar tempo de carregar tudo certinho.
		//posteriormente tratar com um 'preloader'.
		var tout = setTimeout(atualizarCanvas, 500);
	};
	
	/**
	 * Apaga todo que est no canvas e desenha novamente.
	 * ï¿½ assim que sï¿½o feitas as animaï¿½ï¿½es de arrastar os itens.
	 */
	var atualizarCanvas = function() {	
		//limpa toda a tela.
		self.context.save();
		self.context.clearRect(0, 0, self.canvas.width, self.canvas.height);
		self.context.restore();
		/*
		 * Como a imagem jï¿½ foi carregada posteriormente, aqui
		 * apenas percorre a lista redesenhando estes itens.
		 * Assim ganha-se muito em desempenho.
		 */
		for ( var i = 0; i < self.listaItens.length; i++) {
			if(self.listaItens[i] == null){
				continue;
			}
			if (self.listaItens[i].idImagemItemConfiguracaoCol != null && self.listaItens[i].idImagemItemConfiguracaoCol.length > 0) {
				for(var j = 0; j < self.listaItens[i].idImagemItemConfiguracaoCol.length; j++){
					var imgPai = findImagemById(self.listaItens[i].idImagemItemConfiguracaoCol[j].idImagemItemConfiguracaoPai);
					if (imgPai != null) {
						connectPoints(imgPai.objeto, self.listaItens[i]);
					} else {
						self.listaItens[i].idImagemItemConfiguracaoPai = null;
					}
				}
			}			
			self.listaItens[i].desenhaImagemJaCarregada();
			self.listaItens[i].desenhaAreaOpcoes();
		}
	}
	
	
	/**
	 * Identifica uma imagemItemConfiguracao na lista pelo id.
	 * @param id
	 * id do item pesquisado. 
	 */
	var findImagemById = function(id) {
		var i = 0;
		for (i = 0; i < self.listaItens.length; i++) {
			if (self.listaItens[i] != null) {
				if (self.listaItens[i].idImagemItemConfiguracao == id) {
					return {
						objeto : self.listaItens[i],
						index : i
					}
				}
			}
		}
	};
	
	/**
	 * Identifica um elemento no mapa de acordo
	 * com as coordenadas passadas.
	 * @param posicao
	 * Um objeto anï¿½nimo com caracterï¿½sticas x e y.
	 * Exemplo: posicao = {x:50, y:100}
	 */
	var findItemByPosition = function(posicao){		
		var opcoes = false;
		var itemRetorno = null;
		var i = null;
		for(i = 0; i < self.listaItens.length; i++){			
			if(self.listaItens[i] != null){
				if(posicao.x >= self.listaItens[i].posx && posicao.x <= self.listaItens[i].getPosX2() && 
				   posicao.y >= self.listaItens[i].posy && posicao.y <= self.listaItens[i].getPosY2()){					
					//o retorno ï¿½ um objeto anï¿½nimo com a posiï¿½ï¿½o do objeto na lista e o objeto em si.
					itemRetorno = self.listaItens[i];
					break;
				}			
				if(posicao.x >= (self.listaItens[i].posx + self.listaItens[i].areaOpcoes.posx + 10) && 
				   posicao.x <= (self.listaItens[i].posx + self.listaItens[i].areaOpcoes.posx + self.listaItens[i].areaOpcoes.width + 10) &&
				   posicao.y >= (self.listaItens[i].posy + self.listaItens[i].areaOpcoes.posy + 10) && 
				   posicao.y <= (self.listaItens[i].posy + self.listaItens[i].areaOpcoes.posy + self.listaItens[i].areaOpcoes.height + 10)){
					
					opcoes = true; 
					itemRetorno = self.listaItens[i];
					break;
				}
			}
		}
		return itemRetorno == null ? null : {
			objeto : itemRetorno,
			index : i,
			opcoes : opcoes
		}
	};

	/**
	 * Desenha a linha com base nas configuraï¿½ï¿½es do enum TipoLinha.
	 * @param pai
	 * @param filho
	 * @returns
	 */
	var connectPoints = function(pai, filho){		
		self.context.beginPath();
		
		var centralXPai = pai.posx + pai.areaOpcoes.posx + (pai.areaOpcoes.width/2);
		var centralYPai = pai.posy + pai.areaOpcoes.posy + (pai.areaOpcoes.height/2);
		var centralXFilho = filho.posx + filho.areaOpcoes.posx + (filho.areaOpcoes.width/2);
		var centralYFilho = filho.posy + filho.areaOpcoes.posy + (filho.areaOpcoes.height/2);	
		var meioX;
		var meioY;
		self.context.moveTo(centralXPai,centralYPai);
		//self.context.moveTo(pai.getXCentral(), pai.getYCentral());
		switch(tipoLinha){
			case TipoLinha.TRES_PONTOS :
				meioX = centralXPai+(centralXFilho - centralXPai)/2; 
				meioY = centralYPai+(centralYFilho - centralYPai)/2;				
//				self.context.lineTo(meioX, pai.getYCentral());
				self.context.lineTo(meioX, centralYPai);
				self.context.lineTo(meioX, meioY);
				self.context.lineTo(centralXFilho, meioY);
				self.context.lineTo(centralXFilho, centralYFilho);
//				self.context.lineTo(filho.getXCentral(), meioY);
//				self.context.lineTo(filho.getXCentral(), filho.getYCentral());
				break;
			case TipoLinha.DOIS_PONTOS :	
				self.context.lineTo(filho.getXCentral(), pai.getYCentral());
				self.context.lineTo(filho.getXCentral(), filho.getYCentral());
				break;
			case TipoLinha.RETA :		
				self.context.lineTo(filho.getXCentral(), filho.getYCentral());
				break;
		}		
		
		//self.context.moveTo(centralXFilho, centralYFilho);
		
		//self.context.lineTo(pai.posx+(filho.posx - pai.posx)-10), pai.posy+(filho.posy - pai.posy)-10));
		//self.context.lineTo(filho.getXCentral(), filho.getYCentral());
		
	///self.context.beginPath();
	
		
		//var rightX = filho.areaOpcoes.posx + (filho.areaOpcoes.width/2);
		//var baseY = filho.areaOpcoes.posy ;
		
		//var rightX = filho.getPosX2();
    	//var baseY = filho.getPosY2();
		//self.context.moveTo(rightX, baseY);
		//self.context.lineTo(rightX - 5, baseY + 5);
		//self.context.moveTo(rightX, baseY);
		//self.context.lineTo(rightX - 5, baseY - 5);
		//self.context.fill();
		//self.context.closePath();

		//self.context.fillStyle = "#FF0000";
		
		self.context.arc(centralXFilho, centralYFilho,10,0,Math.PI*2,true);
		self.context.stroke();
		self.context.closePath();
		
		var pontoPai = new Point(centralXFilho, meioY);
		var pontoFilho = new Point(centralXFilho,centralYFilho);
		desenharSeta(self.context,pontoPai, pontoFilho);
	};	
	
	function desenharSeta(ctx,fromPoint,toPoint){
		var dx = toPoint.x - fromPoint.x;
		var dy = toPoint.y - fromPoint.y;
		
        var length = Math.sqrt(dx * dx + dy * dy);
        var unitDx = dx / length;
        var unitDy = dy / length;
        //Tamanho seta
        var tamanhoSeta = 5;
 
        var pontoSeta1 = new Point(
            (toPoint.x - unitDx * tamanhoSeta - unitDy * tamanhoSeta),
            (toPoint.y - unitDy * tamanhoSeta + unitDx * tamanhoSeta));
        var pontoSeta2 = new Point(
            (toPoint.x - unitDx * tamanhoSeta + unitDy * tamanhoSeta),
            (toPoint.y - unitDy * tamanhoSeta - unitDx * tamanhoSeta));
        			 
        insereSeta(ctx,toPoint,pontoSeta1,pontoSeta2,dy);
	}
		
	function insereSeta(ctx,toPoint,pontoSeta1,pontoSeta2,valorY){
		 ctx.beginPath();
		 if(valorY > 0){
			 ctx.moveTo(toPoint.x,toPoint.y-10);
			 ctx.lineTo(pontoSeta1.x,pontoSeta1.y-10);
			 ctx.lineTo(pontoSeta2.x,pontoSeta2.y-10);
			 ctx.lineTo(toPoint.x,toPoint.y-9);
		 }else{
			 ctx.moveTo(toPoint.x,toPoint.y+9);
			 ctx.lineTo(pontoSeta1.x,pontoSeta1.y+10);
			 ctx.lineTo(pontoSeta2.x,pontoSeta2.y+10);
			 ctx.lineTo(toPoint.x,toPoint.y+10); 
		 }
		 
		 ctx.fillStyle = 'black';
		 ctx.fill();
		 ctx.stroke();
		 ctx.closePath();
	}
		
	function Point(x, y) {
		  this.x = x;
		  this.y = y;
	}
		
		
		
	
	/**
	 * 
	 * @returns
	 */	
	var limparFecharPopup = function(){
		var editarItemConfiguracao = document.getElementById("editarItemConfiguracao");
		document.form.idItemConfiguracao.value = "";
		document.getElementById("identificacao").value = "";
		document.getElementById("txtDescricao").value = "";
		var btExcluir = document.getElementById("btExcluir");
		btExcluir.style.display = "none";
//		document.form.identificacao.value = "";
//		document.form.txtDescricao.value = "";
		document.form.idImagemItemConfiguracao.value = "";
		$("#editarItemConfiguracao").dialog("close");
	}
	
	/**
	 * Retorna a posiï¿½ï¿½o do cursor relativa ao canvas.
	 * @param evt
	 * Evento de mouse.
	 */
	var getRelativeCursorPoint = function(evt) {
		// get canvas position
		var obj = self.canvas;
		var top = 0;
		var left = 0;
		while (obj && obj.tagName != 'BODY') {
			top += obj.offsetTop;
			left += obj.offsetLeft;
			obj = obj.offsetParent;
		}
		// return relative mouse position
		var mouseX = evt.clientX - left + window.pageXOffset;
		var mouseY = evt.clientY - top + window.pageYOffset;
		return {
			x : mouseX,
			y : mouseY
		};
	};
};
function ImagemItemConfiguracao(_context, _caminho){
	this.idImagemItemConfiguracao = 0;
	this.idServico;
    this.idItemConfiguracao;
    this.idImagemItemConfiguracaoPai = null;
    this.idImagemItemConfiguracaoCol = [];
    this.idImagemItemConfiguracaoPaiColSerializado;
    this.caminhoImagem = _caminho;
    this.identificacao;	
    this.descricao;
    this.posicaoSeta;

    this.posx;
	this.posy;
	this.width;
	this.height;
	
	this.context = _context;
	var pastaImagens = "imagens/";	
	this.areaOpcoes = {
			width : 15,
			height : 15,
			posx : 40,
			posy : 40
	};
	this.imageObj = null;
	var self = this;
	
	this.getPosX2 = function(){
		return this.posx + this.width;
	};
	
	this.getPosY2 = function(){
		return this.posy + this.height;
	};
	
	this.getXCentral = function(){
		return this.posx + (this.width/2);
	};
	
	this.getYCentral = function(){
		return this.posy + (this.height/2);
	};
	
	/**
	 * Desenha bordas em torno da imagem.
	 */
	this.desenharBorda = function() {
		this.context.beginPath();	
		this.context.moveTo(this.posx, this.posy);
//		this.context.lineTo(this.getX2Pos(), this.getYPos());
//		this.context.lineTo(this.getX2Pos(), this.getY2Pos());
//		this.context.lineTo(this.getXPos(), this.getY2Pos());
//		this.context.lineTo(this.getXPos(), this.getYPos());
		this.context.stroke();
		this.context.closePath();
	};
	
	this.desenhaAreaOpcoes = function(){	
		this.context.beginPath();	
		this.context.fillStyle = "#000000";	
		this.context.fillRect((this.posx + this.areaOpcoes.posx),
							  (this.posy + this.areaOpcoes.posy),
							  this.areaOpcoes.width, 
							  this.areaOpcoes.height);
		this.context.fill();
		this.context.stroke();
		this.context.closePath();
	};
	
	/**
	 * Mostra uma imagem na tela.
	 * 
	 * @param myImage
	 * Objeto da classe MyImage com as propriedades da imagem.
	 */
	this.carregaDesenhaImagem = function() {
		this.imageObj = new Image();
		//ao carregar, torna visï¿½vel
		this.imageObj.onload = function() {
			self.desenhaImagemJaCarregada();
			self.width = self.imageObj.width;
			self.height = self.imageObj.height;
			self.desenhaAreaOpcoes();
		};
		this.imageObj.src = this.caminhoImagem;
	};
	
	/**
	 * Apenas para tornar a imagem visï¿½veis apï¿½s o carregamento. 
	 */
	this.desenhaImagemJaCarregada = function() {
		if (this.height != null && this.width != null) {
			this.context.drawImage(this.imageObj, this.posx, this.posy, this.width, this.height);
		} else {
			this.context.drawImage(this.imageObj, this.posx, this.posy);
		}
	};
};
var UtilMapa = {		
		/**
		 * Elimina espaï¿½os em branco em qualquer lugar da string
		 * @param str
		 * String a ser modificada.
		 */
		trim : function(str){
			var novaStr = "";
			for(var i = 0 ; i<str.length; i++){
				novaStr += str.charAt(i) == ' ' ? '' : str.charAt(i); 
			}
			return novaStr;
		},		
		/**
		 * Mostra uma mensagem definida em um componente definido por um tempo
		 * definido.
		 * @param componenteId
		 * Id do componente onde a mensagem serï¿½ colocada.
		 * @param segundos
		 * Tempo em segundos que a mensagem ficarï¿½ na tela.
		 * @param texto
		 * Texto que deverï¿½ aparecer no componente pelo tempo determinado.
		 */
		mostrarMsgTemporaria : function(componenteId, segundos, texto){
			var t;
			var componente = document.getElementById(componenteId);			
			componente.innerText = texto;			
			t = setTimeout(function(){
				componente.innerText = '';
			}, segundos*1000);
		}
}

function ImagemItemConfiguracaoRelacaoDTO(idImagemItemConfiguracao, idImagemItemConfiguracaoPai){
		this.idImagemItemConfiguracao = idImagemItemConfiguracao;
		this.idImagemItemConfiguracaoPai = idImagemItemConfiguracaoPai;
	}
