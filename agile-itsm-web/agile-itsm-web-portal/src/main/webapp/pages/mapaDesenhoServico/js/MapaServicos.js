
var TipoLinha = {"RETA" : 0, "DOIS_PONTOS" : 1, "TRES_PONTOS" : 2};

/**
 *  @author breno.guimaraes
 *  Controla todos os eventos, desenhos, drops, etc, do mapa de servi�o.
 *  [INUTILIZADO]
 */
function MapaServicos(_canvas){

	var tipoLinha = TipoLinha.TRES_PONTOS;
	
	//Variavel para resolver problemas de refer�ncia e escopo.
	var self = this;
	
	//Recebe o componente de desenho ao construir.
	var canvas = _canvas;
	var context = _canvas.getContext("2d");
	
	//Dimens�es padr�o do canvas
	var w = 1000;
	var h = 400;
	
	var zoom = 1;
	//Objeto gerenciador de imagens.
	var imgManager = new GerenciadorImagem();
	
	/* 
	 * Lista de objetos ItemConfiguracao com suas 
	 * configuracoes de posicionamento que 
	 * ser�o inclusos no mapa.
	 */
	var listaItensMapa = new Array();
	
	
	//Variaveis de controle para movimentacao de itens
	var itemSelecionado = null;
	var interval = null;
	
	/* --------------------------- Eventos Movimento --------------------------- */
	/*
	 * Aqui ser�o configurados os eventos de mouse para movimenta��o
	 * dos itens no mapa.
	 */
	
	/**
	 * Aciona os gatilhos de eventos
	 */
	var configuraEventosMouse = function() {
		//eventos para prevenir bugs
		canvas.addEventListener("dragenter", prevent, true);
		canvas.addEventListener("dragover", prevent, true);
		canvas.addEventListener("dragexit", prevent, true);

		canvas.addEventListener("dblclick", dblclick, true);

		canvas.addEventListener("mousedown", mouseDown, true);
		canvas.addEventListener("mouseup", mouseUp, true);
		//se o mouse sair do canvas executo a a��o de mouse up e a a��o de arrastar termina
		canvas.addEventListener("mouseout", mouseUp, true);

	};
	
	/**
	 * Evento de click que ser� tratado posteriormente para edi��o.
	 * @param e
	 * Evento de click.
	 * @returns
	 */
	var dblclick = function(e){
		itemSelecionado = findItemByPosition(getRelativeCursorPoint(e));
		if(itemSelecionado != null){			
			//alert("DB CLICK: " + itemSelecionado.objeto.getImagem().idImagemItemConfiguracao);
			var btSalvar = document.getElementById("btSalvar");
			var btExcluir = document.getElementById("btExcluir");
			btExcluir.hidden = false;
			btExcluir.style.display = "block";
			
			var editarItemConfiguracao = document.getElementById("editarItemConfiguracao");
			editarItemConfiguracao.hidden = false;
			editarItemConfiguracao.style.display = "block";
			
			document.form.idItemConfiguracao.value = itemSelecionado.objeto.getImagem().idImagemItemConfiguracao;
			document.form.identificacao.value = itemSelecionado.objeto.getImagem().identificacao;
			document.form.txtDescricao.value = itemSelecionado.objeto.getImagem().descricao;	
			
			
			
			btSalvar.onclick = function(){
				var imgAux = findImagemById(itemSelecionado.objeto.getImagem().idImagemItemConfiguracao);
				imgAux.objeto.idItemConfiguracao = document.form.idItemConfiguracao.value;
				imgAux.objeto.identificacao = document.form.identificacao.value;
				imgAux.objeto.descricao = document.form.txtDescricao.value;
				
				
				editarItemConfiguracao.hidden = true;
				editarItemConfiguracao.style.display = "none";
				
				document.form.idItemConfiguracao.value = "";
				document.form.identificacao.value = "";
				document.form.txtDescricao.value = "";
				
				alert("Atualizado!");
			};
			
			btExcluir.onclick = function(){
				document.form.idImagemItemConfiguracao.value = itemSelecionado.objeto.getImagem().idImagemItemConfiguracao;
				document.form.fireEvent("excluirImagemItemConfiguracao");
				
				
				editarItemConfiguracao.hidden = true;
				editarItemConfiguracao.style.display = "none";

				document.form.idItemConfiguracao.value = "";
				document.form.identificacao.value = "";
				document.form.txtDescricao.value = "";
			};
		
			
		}	
	};

	/**
	 * Executador em eventos espec�ficos para evitar bugs.
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
	 * Quando o mouse for pressionado, ele adiciona o
	 * gatilho para o evento de movimento do mouse e
	 * executa o metodo findItemByPosition(posicao) para
	 * encontrar um poss�vel item selecionado. 
	 * @param e
	 * Evento.
	 */
	var mouseDown = function(e) {
		//A funcionalidade s� funcionar� se esse evento for criado aqui.
		canvas.addEventListener("mousemove", mouseClickedMove, false);

		itemSelecionado = findItemByPosition(getRelativeCursorPoint(e));
					
		if(interval == null){
			/*
			 * Quanto menor for o segundo par�metro, mais r�pido a tela ser� atualizada,
			 * por�m pode deixar tudo mais lento dependendo do volume de itens no mapa. 
			 */
			interval = setInterval(atualizarTela, 10);
		}
		
		if(itemSelecionado != null && itemSelecionado.centro) {
			context.beginPath();
		}
	}
	
	
	var posicaoMouse;
	/**
	 * Quando o mouse se mover (cada mil�metro), se houver
	 * um item selecionado executa a��es de movimento.
	 * @param e
	 * Evento.
	 */
	var mouseClickedMove = function(e) {
		canvas.style.cursor = 'move';
		
		posicaoMouse = getRelativeCursorPoint(e);
		
		/*
		 * Se o mouse tiver sido pressionado em cima de um item, a variavel
		 * itemSelecionado ser� diferente de null, ent�o esse objeto deve
		 * ser movimentado.
		 */
		if(itemSelecionado != null){
			/*
			 * Se o usu�rio tiver clicado na �rea de configuracao da figura, uma
			 * linha ser� tra�ada do ponto da imagem at� o ponto
			 * do mouse para que ele possa ligar dois pontos.
			 */
			if(itemSelecionado.centro){		
				
				context.moveTo(itemSelecionado.objeto.getImagem().getXCentral(),itemSelecionado.objeto.getImagem().getYCentral());
				context.lineTo(posicaoMouse.x, posicaoMouse.y);
				context.stroke();		

			} else {		
				//Define os limites de area para o item.
				var limiteTempX = (w - itemSelecionado.objeto.getImagem().getWidth());
				var limiteTempY = (h - itemSelecionado.objeto.getImagem().getHeight());		
				itemSelecionado.objeto.getImagem().setXPos(posicaoMouse.x <= limiteTempX ? (posicaoMouse.x - itemSelecionado.objeto.getImagem().getWidth()/2) 
																							: limiteTempX);
				itemSelecionado.objeto.getImagem().setYPos(posicaoMouse.y <= limiteTempY ? (posicaoMouse.y - itemSelecionado.objeto.getImagem().getHeight()/2)
																							: limiteTempY);	
			}
			
		}
		
	}

	/**
	 * Quando o mouse n�o estiver mais sendo pressionado, armazena
	 * algumas configura��es e finaliza a a��o de arrastar.
	 * @param e
	 * Evento.
	 */
	var mouseUp = function(e) {
		//Conclui o desenho.
		context.closePath();
	
		canvas.removeEventListener("mousemove", mouseClickedMove, false);


		if(itemSelecionado != null && itemSelecionado.centro){
			//Verifica se a linha foi solta em cima de um outro item.
			var filho = findItemByPosition(getRelativeCursorPoint(e));
			
			//Se tiver sido solto em cima de um item filho, faz a liga��o.
			if(filho != null && filho.objeto.getIdItemConfiguracao() != itemSelecionado.objeto.getIdItemConfiguracao()){
				if(itemSelecionado.objeto.getImagem().idImagemItemConfiguracao == 0){
					alert("Este item ainda n�o foi salvo, por isso n�o pode ser setado como pai.");
				} else {					
					filho.objeto.getImagem().idImagemItemConfiguracaoPai =  itemSelecionado.objeto.getImagem().idImagemItemConfiguracao;
				}				
			}
		}
		

		if(interval != null){
			clearInterval(interval);
			interval = null;
		}
		
		canvas.style.cursor = 'default';
		atualizarTela();

	}
	/* --------------------------- FIM --------------------------- */
	
	
	/* --------------------------- GETTERS E SETTERS --------------------------- */
	
	this.getContext = function(){
		return context;
	}
	
	this.getListaItens = function(){
		return listaItensMapa;
	}
	
	/**
	 * Atribui um tipo de linha conectora dos pontos.
	 * @param tipo
	 * Constante TipoLinha
	 */
	this.setTipoLinha = function(tipo){
		//atualiza o tipo
		tipoLinha = tipo;
		//atualiza tela
		atualizarTela();
	}
	/* --------------------------- FIM --------------------------- */
	
	
	
	/* --------------------------- Eventos Mapa --------------------------- */
	/*
	 * Aqui ser�o configurados os eventos para quando o usu�rio
	 * soltar um item do menu para o canvas.
	 */

	/**
	 * Configura��es iniciais dos eventos de drop.
	 */
	var configuraEventosDrop = function() {
		//eventos para prevenir bugs
		canvas.addEventListener("dragenter", prevent, true);
		canvas.addEventListener("dragover", prevent, true);
		canvas.addEventListener("dragexit", prevent, true);
		//quando um item for solto dentro do canvas
		canvas.addEventListener("drop", droped, true);
		
		//canvas.addEventListener("mousewheel", mouseWheel ,true);
	}

	/**
	 * Aumenta ou diminui todas as imagens para simular zoom.
	 */
	this.setZoom = function(value){
		if(value < 0)
			zoom = 0;
		else if (value > 2)
			zoom = 2;
		else
			zoom = value;
			
		for(var i = 0; i < listaItensMapa.length; i++){
			listaItensMapa[i].getImagem().setWidth(listaItensMapa[i].getImagem().getWidthRef()*zoom); 
			listaItensMapa[i].getImagem().setHeight(listaItensMapa[i].getImagem().getHeightRef()*zoom);
		}
		
		//context.scale(value, value);
		atualizarTela();
	}
	
	
	/**
	 * Quando um item do menu � solto dentro do canvas
	 * anexa este novo item com configura��es default.
	 * @param e
	 * Evento.
	 */
	var droped = function(e) {
		if(trim(document.getElementById("idServico").value) == ""){
			alert("Selecione um servi�o primeiro.");
			return;
		}
		
		prevent(e);
		/*
		 * Pega o id passado no dataTransfer do objeto MenuItemConfiguracao
		 * e busca o elemento da p�gina que tenha esse id para copi�-lo para o canvas.
		 */
		var imagemObj = document.getElementById(e.dataTransfer.getData('text'));
		
		var classValue = imagemObj.getAttribute('class');
		
		/*
		 * Mapa s� aceita objetos da classe itemInMenu.
		 */		
		if (classValue.indexOf("itemInMenu") >= 0) {
		
			var editarItemConfiguracao = document.getElementById("editarItemConfiguracao");
			editarItemConfiguracao.hidden = false;
			editarItemConfiguracao.style.display = "block";
		
			//efeito de dropagem (nunca vi a real diferen�a).
			e.dropEffect = 'copy';

			var imagemItemConfiguracao = new ImagemItemConfiguracao(context, imagemObj.getAttribute('src'));
			/*
			 * recupera a posi��o do mouse no momento em que o item foi solto
			 * para posicionar a imagem neste mesmo local.
			 */
			var posicaoMouse = getRelativeCursorPoint(e);
			
			imagemItemConfiguracao.setXPos(posicaoMouse.x);
			imagemItemConfiguracao.setYPos(posicaoMouse.y);
			
			var nomeItem = document.getElementById("identificacao");
			var idItem = document.getElementById("idItemConfiguracao");
			var desc = document.getElementById("txtDescricao");
			
			var item = new ItemConfiguracao("qualquer nome" , parseInt(Math.random()*1000), imagemItemConfiguracao);		
		
			var btSalvar = document.getElementById("btSalvar");

			/*
			 * S� ser� efetivado o anexo do item quando usu�rio clicar em SALVAR. 
			 */
			btSalvar.onclick = function(){				
				if(trim(idItem.value) != "" && idItem.value != null){ 
					item.setIdItemConfiguracao(idItem.value);
					item.setIdentificacao(nomeItem.value);
					item.getImagem().descricao = desc.value;
					
					self.addItemToMapa(item, null);					


					nomeItem.value = "";
					desc.value = "";
					
					editarItemConfiguracao.hidden = true;
					editarItemConfiguracao.style.display = "none";
					
				}else{
					alert("Escolha um item.");
				}
					
				
			};
		
		}
	//bind para alinhar o escopo da classe com o do m�todo.
	}.bind(this);

	/* --------------------------- FIM --------------------------- */
	
	
	/* --------------------------- Fun��es de controle --------------------------- */
	
	/**
	 * Configura��es iniciais do mapa.
	 */
	this.configuraMapa = function() {
		configuraEventosMouse();
		configuraEventosDrop();
		
		resize();
		window.onresize = resize;
		
	}
	
	function resize(){		
		w = document.body.offsetWidth/1.016;
		h = window.screen.height/1.36;
		
		canvas.width = w;
		canvas.height = h;
		
	
		//document.body.innerHTML = window.screen.height + " - " + document.body.offsetHeight;
	}
	
	
	/**
	 * Carrega e posiciona um novo item no mapa.
	 * @param itemConfiguracao
	 * O objeto tipo ItemConfiguracao que deve ser
	 * aneaxo ao canvas.
	 * @param posicaoLista
	 * Caso necess�rio, passa a posi��o na lista em que deseja
	 * adicionar o item, caso contr�rio passe 'null'.
	 */
	this.addItemToMapa = function(itemConfiguracao) {
		//seta o NOVO contexto � imagem do itemConfiguracao
		itemConfiguracao.getImagem().setContext(context);

		itemConfiguracao.getImagem().setXPos(itemConfiguracao.getImagem().getXPos() == null ? 0
																		: itemConfiguracao.getImagem().getXPos());
		itemConfiguracao.getImagem().setYPos(itemConfiguracao.getImagem().getYPos() == null ? 0
																		: itemConfiguracao.getImagem().getYPos());
		
		//utilizado apenas at� que seja feita a busca no banco
		//itemConfiguracao.setIdentificacao(itemConfiguracao.getIdentificacao() == null ? 'Nome - ' + parseInt(Math.random() * 100) : itemConfiguracao.getIdentificacao()); 
		
		var imgAux = imgManager.attachImage(itemConfiguracao.getImagem());
		/*
		 * Ap�s carregar a imagem com o imgManager recebemos como retorno
		 * o objeto Image() criado. Nesse momento temos os atributos
		 * da dimens�o da imagem. Ent�o atribuimos estes ao objeto ImagemItemConfiguracao,
		 * dentro do itemConfiguracao.
		 */
		itemConfiguracao.getImagem().setWidth(imgAux.width*zoom);
		itemConfiguracao.getImagem().setHeight(imgAux.height*zoom);
		
		itemConfiguracao.getImagem().setImageObj(imgAux);
		
		
		listaItensMapa[listaItensMapa.length] = itemConfiguracao;
		
		atualizarTela();
	};
	
	
	/**
	 * Apaga todo que est� no canvas e desenha novamente.
	 * � assim que s�o feitas as anima��es de arrastar os itens.
	 */
	var atualizarTela = function() {		
		//limpa toda a tela.
		context.clearRect(0, 0, canvas.width, canvas.height);
		/*
		 * Como a imagem j� foi carregada posteriormente, aqui
		 * apenas percorre a lista redesenhando estes itens.
		 * Assim ganha-se muito em desempenho.
		 */
		var myImageListAux = new Array();
		for ( var i = 0; i < listaItensMapa.length; i++) {				
				myImageListAux[i] = listaItensMapa[i].getImagem();
				
				if(listaItensMapa[i].getImagem().idImagemItemConfiguracaoPai != null && 
				   listaItensMapa[i].getImagem().idImagemItemConfiguracaoPai != ""){
					
					var imgPai = findImagemById(listaItensMapa[i].getImagem().idImagemItemConfiguracaoPai);
					if(imgPai != null){						
						connectPoints(imgPai.objeto, listaItensMapa[i].getImagem());
					}
				}
				
				//caso considere importante, desenha as bordas em torno da imagem.
				//listaItensMapa[i].myImage.desenharBorda();
		}
		
		imgManager.drawImages(myImageListAux);

		for(var i = 0 ; i < listaItensMapa.length; i++){
			listaItensMapa[i].getImagem().desenhaCentro();
		}
		
	}.bind(this);
	
	
	
	/**
	 * Identifica um elemento no mapa de acordo
	 * com as coordenadas passadas.
	 * @param posicao
	 * Um objeto an�nimo com caracter�sticas x e y.
	 * Exemplo: posicao = {x:50, y:100}
	 */
	var findItemByPosition = function(posicao){		
		var centro = false;
		
		for(var i = 0; i < listaItensMapa.length; i++){
			
			if(listaItensMapa[i] != null){
				if(posicao.x >= listaItensMapa[i].getImagem().getXPos() && posicao.x <= listaItensMapa[i].getImagem().getX2Pos() && 
						posicao.y >= listaItensMapa[i].getImagem().getYPos() && posicao.y <= listaItensMapa[i].getImagem().getY2Pos()){
					
					
					
					//o retorno � um objeto an�nimo com a posi��o do objeto na lista e o objeto em s�.
					return {
						objeto : listaItensMapa[i],
						index : i,
						centro : centro
					}
					
				}
				
				
				if(posicao.x >= listaItensMapa[i].getImagem().getPosXAreaOpcoes() && 
						   posicao.x <= listaItensMapa[i].getImagem().getPosXAreaOpcoes() + listaItensMapa[i].getImagem().getAreaOpcoes().width &&
						   posicao.y >= listaItensMapa[i].getImagem().getPosYAreaOpcoes() &&
						   posicao.y <= listaItensMapa[i].getImagem().getPosYAreaOpcoes() + listaItensMapa[i].getImagem().getAreaOpcoes().height){
							
							//alert("centro");
							centro = true;   					
						}
			}
				//se o posicionamento passado como argumento estiver dentro do posicionamento do item, retorna-o
			}
		};
	
	/**
	 * Identifica uma imagemItemConfiguracao na lista pelo id.
	 * @param id
	 * id do item pesquisado. 
	 */
	var findImagemById = function(id){
		//Percorre a lista tentando encontrar o item
		for(var i = 0; i < listaItensMapa.length; i++){
			
			if(listaItensMapa[i] != null){
				//se o posicionamento passado como argumento estiver dentro do posicionamento do item, retorna-o
				if(listaItensMapa[i].getImagem().idImagemItemConfiguracao == id){					
						//o retorno � um objeto an�nimo com a posi��o do objeto na lista e o objeto em s�.
						return {
							objeto : listaItensMapa[i].getImagem(),
							index : i
						}
					}
				}
			}
	};
	
	
	/**
	 * Identifica um item na lista pelo id.
	 * @param id
	 * id do item pesquisado. 
	 */
	var findItemById = function(id){
		//Percorre a lista tentando encontrar o item
		for(var i = 0; i < listaItensMapa.length; i++){
			
			if(listaItensMapa[i] != null){
				//se o posicionamento passado como argumento estiver dentro do posicionamento do item, retorna-o
				if(listaItensMapa[i].idItemConfiguracao == id){					
						//o retorno � um objeto an�nimo com a posi��o do objeto na lista e o objeto em s�.
						return {
							objeto : listaItensMapa[i],
							index : i
						}
					}
				}
			}
	};
	
	
	this.getListaImagens = function(){
		var myImageListAux = new Array();
		var idServico = document.getElementById("idServico");
		
		var str = "";
		for ( var i = 0; i < listaItensMapa.length; i++) {				
				myImageListAux[i] = listaItensMapa[i].getImagem(); 
				myImageListAux[i].setIdItemConfiguracao(listaItensMapa[i].getIdItemConfiguracao());
				myImageListAux[i].setIdServico(idServico.value);
				if(listaItensMapa[i].getItemPai() != null){					
					myImageListAux[i].idImagemItemConfiguracaoPai = listaItensMapa[i].getItemPai().getImagem().idImagemItemConfiguracao;
				}
				
				str += "id imagem: " + myImageListAux[i].idImagemItemConfiguracao;
				str += "id item: " + myImageListAux[i].idItemConfiguracao;
				str += "\n id servico: " + myImageListAux[i].idServico;
				str += "\n x: " + myImageListAux[i].posx;
				str += "\n y: " + myImageListAux[i].posy;
				str += "\n caminho imagem: " + myImageListAux[i].caminhoImagem;
				str += "\n descricao imagem: " + myImageListAux[i].descricao;
				str += "\n ------------------------------------ \n";
		}
		//alert(str);
		return myImageListAux;
	}
	
	
	
	
	var connectPoints = function(pai, filho){
		
		//desenha a linha com base nas configura��es do enum TipoLinha.
		context.beginPath();
		
		context.moveTo(pai.getXCentral(), pai.getYCentral());
		switch(tipoLinha){
			case TipoLinha.TRES_PONTOS :
				var meioX = pai.getXCentral()+(filho.getXCentral() - pai.getXCentral())/2; 
				var meioY = pai.getYCentral()+(filho.getYCentral() - pai.getYCentral())/2;
				
				context.lineTo(meioX, pai.getYCentral());
				context.lineTo(meioX, meioY);
				context.lineTo(filho.getXCentral(), meioY);
				context.lineTo(filho.getXCentral(), filho.getYCentral());
				break;
			case TipoLinha.DOIS_PONTOS :		
				context.lineTo(filho.getXCentral(), pai.getYCentral());
				context.lineTo(filho.getXCentral(), filho.getYCentral());
				break;
			case TipoLinha.RETA :		
				context.lineTo(filho.getXCentral(), filho.getYCentral());
				break;
		}
		
		context.stroke();
		context.closePath()
		
	}
	
	
	/* --------------------------- Fun��es �teis --------------------------- */

	/**
	 * Retorna a posi��o do cursor relativa ao canvas.
	 * @param evt
	 * Evento de mouse.
	 */
	var getRelativeCursorPoint = function(evt) {
		// get canvas position
		var obj = canvas;
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
	}
	
	/**
	 * Elimina espa�os em branco em qualquer lugar da string
	 * @param str
	 * String a ser modificada.
	 */
	var trim = function(str){
		var novaStr = "";
		for(var i = 0 ; i<str.length; i++){
			novaStr += str.charAt(i) == ' ' ? '' : str.charAt(i); 
		}
		return novaStr;
	}
	
	/**
	 * Mostra uma mensagem definida em um componente definido por um tempo definido.
	 * @param componenteId
	 * Id do componente onde a mensagem ser� colocada.
	 * @param segundos
	 * Tempo em segundos que a mensagem ficar� na tela.
	 * @param texto
	 * Texto que dever� aparecer no componente pelo tempo determinado.
	 */
	var mostrarMsgTemporaria = function(componenteId, segundos, texto){
		var t;
		var componente = document.getElementById(componenteId);
		
		componente.innerText = texto;
		
		t = setTimeout(function(){
			componente.innerText = '';
		}, segundos*1000);
	}

	/**
	 * Elimita os espa�os null no array e os
	 * registros de pais que n�o existem mais.
	 */
	var organizarArray = function(){
		var listaAux = new Array();
		var j = 0;
		for(var i = 0; i < listaItensMapa.length; i++){
			//se o item for null n�o � adicionado � nova lista limpa
			if(listaItensMapa[i] != null){
				/*
				 * se ele referenciar um pai, verifica se este pai ainda existe, caso contr�rio
				 * seta null para esta refer�ncia.
				 */
				if(listaItensMapa[i].itemPai != null){
					var aux = findImagemById(listaItensMapa[i].getItemPai().getId());
					listaItensMapa[i].getItemPai() = aux == null ? null : aux.objeto;
				}
				
				listaAux[j] = listaItensMapa[i];
				j++;
			} 
		}
		//atualiza a lista e a tela
		//listaItensMapa = null;
		listaItensMapa = listaAux;
		atualizarTela();
		//editarItem(listaAux[0]);
		
	}
	

		/* --------------------------- FIM --------------------------- */

}