var TAMANHO_CONECTOR = 10;
var TOLERANCIA_SELECAO = 2;
var TOLERANCIA_BORDA = 5;
var TAMANHO_LINHA_HORIZONTAL = 12; 

var ACIMA = 0;
var DIREITA = 1;
var ABAIXO = 2;
var ESQUERDA = 3;

var TEXT = "text";
var NUM = "num";
var DEC = "dec";
var TEXT_AREA = "textarea";
var SELECT = "select";

var idElemento = 0;
var desenhoFluxo = null;
var selecao = null;

var objCanvas = null;
var canvasParent = null;

var bAlterado = false;

// ========================================== FUNÇÕES DE TRATAMENTO ========================================== //
function pressionou_TECLA_DEL(shift, ctrl, alt, node){
	if (desenhoFluxo != null && ctrl) {
		desenhoFluxo.excluiElemento();
	}
}

function clone(obj,copy) { 
    if (null == obj || "object" != typeof obj) return obj; 
    for (var attr in obj) { 
    	if (obj.hasOwnProperty(attr)) {
           	copy[attr] = obj[attr]; 
    	}
    }  
}

function doubleParaStr(double) {
	var str = new String(double);
	return str.replace('.',',');	
}

function strParaDouble(str) {
	var str = new String(str);
	return parseFloat(str.replace(',','.'));	
}

function exibePropriedades(bExibirSempre) {
	if (selecao == null || selecao.elemento == null)
		return;
	
	if (!bExibirSempre && !selecao.isListaDePropriedades())
		return;
	
	document.formPropriedades.idElemento.value = selecao.elemento.idElemento;
	var bExistePropriedade = false;
	var elementoFluxo = selecao.elemento;
	if (elementoFluxo.existemPropriedades()) {
		var titulo = i18n_message(selecao.elemento.label);
		if (selecao.elemento.nome != '' && selecao.elemento.nome != undefined)
			titulo += " '"+selecao.elemento.nome + "'";
		var strTable = "<table>";
		for ( var i = 0; i < elementoFluxo.getPropriedades().length; i++) {
			var propriedade = elementoFluxo.getPropriedades()[i];
			strTable += "<tr class='trPropriedade'>";
			strTable += "   <td class='tdPropriedade' style='width:120px'>"+i18n_message(propriedade.nome)+"</td>";
			strTable += "   <td class='tdPropriedade'>"+montaEntrada(elementoFluxo, propriedade)+"</td>";
			strTable += "</tr>";
			bExistePropriedade = true;
		}
		strTable += "</table>";
	}
	if (bExistePropriedade) {
		document.getElementById("divPropriedades").innerHTML = strTable;
		HTMLUtils.setValuesForm(document.formPropriedades, selecao.elemento);
		DEFINEALLPAGES_generateConfiguracaoCampos();
		$("#POPUP_PROPRIEDADES").dialog('option', 'title', titulo);
        $("#POPUP_PROPRIEDADES").dialog('open');
	}else{
		document.getElementById("divPropriedades").innerHTML = "";
        $("#POPUP_PROPRIEDADES").dialog("close"); 
	}
}

function montaEntrada(elementoFluxo, propriedade) {
	var result = "";
	
	if (propriedade.tipo == TEXT) {
		var tamanho = propriedade.tamanho;
		if (tamanho == undefined || tamanho == "" || parseInt(tamanho) == 0)
			tamanho = "95";
		result = "<input class='campoPropriedade' type='text' name='"+propriedade.id+"' id='"+propriedade.id+"' maxlength='"+ propriedade.tamanhoMaximo +
				 "' size='"+ tamanho + "px !important' onblur='desenhoFluxo.defineValorPropriedade("+elementoFluxo.idElemento+",\""+propriedade.id+"\")' />";
	}else if (propriedade.tipo == NUM) {
		var tamanho = propriedade.tamanho;
		if (tamanho == undefined || tamanho == "" || parseInt(tamanho) == 0)
			tamanho = "50";
		result = "<input class='campoPropriedade Format[Numero]' type='text' name='"+propriedade.id+"' id='"+propriedade.id+"' maxlength='"+ propriedade.tamanhoMaximo +
				 "' size='"+ propriedade.tamanho + " !important' onblur='desenhoFluxo.defineValorPropriedade("+elementoFluxo.idElemento+",\""+propriedade.id+"\")'/>";
	}else if (propriedade.tipo == DEC) {
		var tamanho = propriedade.tamanho;
		if (tamanho == undefined || tamanho == "" || parseInt(tamanho) == 0)
			tamanho = "50";
		result = "<input class='campoPropriedade Format[Moeda]' type='text' name='"+propriedade.id+"' id='"+propriedade.id+"' maxlength='"+ propriedade.tamanhoMaximo +
				 "' size='"+ propriedade.tamanho + " !important' onblur='desenhoFluxo.defineValorPropriedade("+elementoFluxo.idElemento+",\""+propriedade.id+"\")'/>";
	}else if (propriedade.tipo == TEXT_AREA) {
		var tamanho = propriedade.tamanho;
		if (tamanho == undefined || tamanho == "" || parseInt(tamanho) == 0)
			tamanho = "100";
		result = "<textarea class='campoPropriedade' name='"+propriedade.id+"' id='"+propriedade.id+
				 "' rows='4' cols='"+ propriedade.tamanho + "' onblur='desenhoFluxo.defineValorPropriedade("+elementoFluxo.idElemento+",\""+propriedade.id+"\")' ></textarea>";
	}else if (propriedade.tipo == SELECT) {
		var tam = "";
		if (propriedade.tamanho && parseInt(propriedade.tamanho) > 0) 
			tam = "width:" + propriedade.tamanho + "px";
		result = "<select class='campoPropriedade' style='"+tam+" !important' name='"+propriedade.id+"' id='"+propriedade.id + "' onclick='desenhoFluxo.defineValorPropriedade("+elementoFluxo.idElemento+",\""+propriedade.id+"\")' >";
		//result += "<option value=''>--- Selecione ---</option>";
		for ( var i = 0; i < propriedade.opcoes.length; i++) {
			var opcao = propriedade.opcoes[i];
			result += "<option value='"+opcao.id + "'>"+i18n_message(opcao.texto)+ "</option>";
		}
		result += "</select>";
	}
	return result;
}

function redimensionaCanvas(elemento) {
	if (!elemento || !elemento.largura)
		return;
	var limiteTempX = (objCanvas.width - elemento.largura);
	var limiteTempY = (objCanvas.height - elemento.altura);	
	if (limiteTempX < elemento.getPosX2()) {
		objCanvas.width = objCanvas.width + elemento.largura;
		if (canvasParent != null) 
			canvasParent.scrollLeft = canvasParent.scrollLeft + elemento.largura;
	}
	if (limiteTempY < elemento.getPosY2()) {
		objCanvas.height = objCanvas.height + elemento.altura;		
		if (canvasParent != null) 
			canvasParent.scrollTop = canvasParent.scrollTop + elemento.altura;
	}
}

// ========================================== CLASSES ============================================ //
// **** Coordenada
function Coordenada(x, y) {
	this.x = x;
	this.y = y;
}

//**** Seleção
function Selecao(elemento, conexao, borda, bListaDePropriedades) {
	this.elemento = elemento;
	this.conexao = conexao;
	this.borda = borda;
	this.bListaDePropriedades = bListaDePropriedades;
	
	this.isConexao = function() {
		return !this.elemento.isSequencia() && this.conexao != null;
	}
	
	this.isBorda = function() {
		return this.borda != null;
	}

	this.isSequencia = function() {
		return this.elemento.isSequencia();
	}
	
	this.isListaDePropriedades = function() {
		return this.bListaDePropriedades;
	}

	this.getElementoSerializado = function() {
		return JSON.stringify(this.elemento);
	}
}

//**** Lista de propriedades
function ListaDePropriedades(contexto, elemento) {
	this.elemento = elemento;
	this.contexto = contexto;
	this.img = new Image();
	this.img.src = "imagens/propriedades.png";
	this.propriedades = [];
	
	this.parse = function(serialize) {
		this.propriedades = JSON.parse(serialize);
	}
	this.getPosX = function() {
		return this.elemento.getPosicaoPropriedades().x;
	}
	this.getPosY = function() {
		return this.elemento.getPosicaoPropriedades().y;
	}
	this.getAltura = function() {
		return this.img.height;
	}
	this.getLargura = function() {
		return this.img.width;
	}
	this.getPosX2 = function() {
		return this.getPosX() + this.getLargura();
	}
	this.getPosY2 = function() {
		return this.getPosY() + this.getAltura();
	}
	this.getPropriedades = function() {
		return this.propriedades;
	}
	this.desenha = function() {
		this.contexto.drawImage(this.img, this.getPosX(), this.getPosY(), this.getLargura(), this.getAltura());
		
		this.contexto.beginPath();
		this.contexto.strokeStyle = "gray";
		this.contexto.lineWidth = 1;
		//this.contexto.strokeRect(this.getPosX(), this.getPosY(), this.getLargura(), this.getAltura());
		this.contexto.fillStyle = "rgba(211, 211, 211, 0.3)";
		this.contexto.fillRect(this.getPosX(), this.getPosY(), this.getLargura(), this.getAltura());
		this.contexto.closePath();
		this.contexto.fill();
		this.contexto.stroke();
	}
	
	this.isSequencia = function() {
		return false;
	}
}

//**** Propriedade
function Propriedade() {
	this.id = "";
	this.nome = "";
	this.tipo = "";
	this.tamanho = "";
	this.tamanhoMaximo = "";
	this.valorDefault = ""; 
}

//**** Sequência 
function SequenciaFluxo(_contexto) {
	idElemento ++;
	this.idElemento = idElemento;
	this.contexto = _contexto;
	this.conexaoOrigem;
	this.conexaoDestino;
	this.nome;
	this.tipoElemento = 'desenhoFluxo.elemento.sequencia';
	this.label = 'desenhoFluxo.elemento.sequencia';
	
	this.destacado = false;
	
	this.posicaoAlterada;
	
	this.idElementoOrigem;
	this.idElementoDestino;

	this.idConexaoOrigem;
	this.idConexaoDestino;
	this.bordaX;
	this.bordaY;
	
	this.condicao = '';
	
	this.largura;
	this.altura;
	this.posX;
	this.posY;
	
	this.elementoOrigem;
	this.elementoDestino;
	
	this.borda = null;
	
	this.listaDePropriedades = new ListaDePropriedades(this.contexto,this);
	
	var propriedade = new Propriedade();
	propriedade.id = "nome";
	propriedade.nome = "desenhoFluxo.nome";
	propriedade.tipo = TEXT;
	this.listaDePropriedades.getPropriedades().push(propriedade);
	
	propriedade = new Propriedade();
	propriedade.id = "condicao";
	propriedade.nome = "desenhoFluxo.condicao";
	propriedade.tipo = TEXT_AREA;
	propriedade.tamanho = 100;
	this.listaDePropriedades.getPropriedades().push(propriedade);
	
	this.existemPropriedades = function() {
		return true;
	}
	
	this.getPropriedades = function() {
		return this.getListaDePropriedades().getPropriedades();
	}
	
	this.getListaDePropriedades = function() {
		return this.listaDePropriedades;
	} 
	
	this.clone = function(origem) {
		var id = this.idElemento;
		clone(origem, this);
		this.idElemento = id;
		this.idElementoOrigem = NumberUtil.toInteger(this.idElementoOrigem);
		this.idElementoDestino = NumberUtil.toInteger(this.idElementoDestino);
		this.idConexaoOrigem = NumberUtil.toInteger(this.idConexaoOrigem);
		this.idConexaoDestino = NumberUtil.toInteger(this.idConexaoDestino);
		this.bordaX = NumberUtil.toDouble(this.bordaX);
		this.bordaY = NumberUtil.toDouble(this.bordaY);
	}
	
	this.inicializa = function(_selecaoOrigem, _selecaoDestino) {
		this.elementoOrigem = _selecaoOrigem.elemento;
		this.elementoDestino = _selecaoDestino.elemento;
		this.conexaoOrigem = _selecaoOrigem.conexao;
		this.conexaoDestino = _selecaoDestino.conexao;
		this.idElementoOrigem = _selecaoOrigem.elemento.idElemento;
		this.idElementoDestino = _selecaoDestino.elemento.idElemento;
		this.idConexaoOrigem = this.conexaoOrigem.posicao;
		this.idConexaoDestino = this.conexaoDestino.posicao;
		this.bordaX = -1;
		this.bordaY = -1;
		this.posicaoAlterada = "N";
	}

	this.getPosX2 = function(){
		return this.posX + this.largura;
	};
	
	this.getPosY2 = function(){
		return this.posY + this.altura;
	};	
	
	this.getPosX = function(){
		return this.posX;
	};
	
	this.getPosY = function(){
		return this.posY;
	};	

	this.getBordaX = function(){
		return this.bordaX;
	};

	this.getBordaY = function(){
		return this.bordaY;
	};

	this.isSequencia = function() {
		return true;
	};
	
	this.getPosicaoPropriedades = function() {
		return new Conexao(this.getBordaX()+TOLERANCIA_BORDA*2, this.getBordaY());
	}
	
	this.isSelecionado = function() {
		return selecao != null && selecao.isSequencia() && 
					  selecao.elemento.idElementoOrigem == this.idElementoOrigem && selecao.elemento.idElementoDestino == this.idElementoDestino;
	}
	
	this.isDestacado = function() {
		return this.destacado;
	}
	
	this.destaca = function() {
		this.contexto.lineWidth = 1;
		this.contexto.beginPath();
		this.contexto.strokeStyle = "#eee";
		this.contexto.strokeRect(this.posX, this.posY, this.largura, this.altura);
		this.contexto.closePath();
		this.contexto.stroke();
		
		this.listaDePropriedades.desenha();
	}	
	
	this.localizaConexoes = function() {
		if (this.elementoOrigem  != null && this.idConexaoOrigem != null) 
			this.conexaoOrigem = this.elementoOrigem.conexoes[this.idConexaoOrigem];
		if (this.elementoDestino  != null && this.idConexaoDestino != null)
			this.conexaoDestino = this.elementoDestino.conexoes[this.idConexaoDestino];
	}
	
	this.desenha = function() {		
		var centroXOrigem = this.conexaoOrigem.getCentroX();
		var centroYOrigem = this.conexaoOrigem.getCentroY();
		
		var centroXDestino = this.conexaoDestino.getCentroX();
		var centroYDestino = this.conexaoDestino.getCentroY();
		
		if (centroYDestino == centroYOrigem && centroXDestino == centroXOrigem)
			return;
				
		this.destacado = false;

		var posOrigemX;
		var posOrigemY;
		var posDestinoX;
		var posDestinoY;

		switch (this.conexaoOrigem.posicao) {
			case ACIMA:
				posOrigemX = centroXOrigem;
				posOrigemY = centroYOrigem - TAMANHO_LINHA_HORIZONTAL;
				break;
			case DIREITA:
				posOrigemX = centroXOrigem + TAMANHO_LINHA_HORIZONTAL;
				posOrigemY = centroYOrigem;
				break;
			case ABAIXO:
				posOrigemX = centroXOrigem;
				posOrigemY = centroYOrigem + TAMANHO_LINHA_HORIZONTAL;
				break;
			case ESQUERDA:
				posOrigemX = centroXOrigem - TAMANHO_LINHA_HORIZONTAL;
				posOrigemY = centroYOrigem;
				break;
		}

		switch (this.conexaoDestino.posicao) {
			case ACIMA:
				posDestinoX = centroXDestino;
				posDestinoY = centroYDestino - TAMANHO_LINHA_HORIZONTAL;
				break;
			case DIREITA:
				posDestinoX = centroXDestino + TAMANHO_LINHA_HORIZONTAL;
				posDestinoY = centroYDestino;
				break;
			case ABAIXO:
				posDestinoX = centroXDestino;
				posDestinoY = centroYDestino + TAMANHO_LINHA_HORIZONTAL;
				break;
			case ESQUERDA:
				posDestinoX = centroXDestino - TAMANHO_LINHA_HORIZONTAL;
				posDestinoY = centroYDestino;
				break;
		}

		if (posOrigemX > posDestinoX) {
			this.largura = posOrigemX - posDestinoX;
			this.posX = posDestinoX; 
		}else{
			this.largura = posDestinoX - posOrigemX;
			this.posX = posOrigemX; 
		}
			
		if (posOrigemY > posDestinoY) {
			this.altura = posOrigemY - posDestinoY;
			this.posY = posDestinoY;
		}else{
			this.altura = posDestinoY - posOrigemY;
			this.posY = posOrigemY;
		}		
		
		if (this.posicaoAlterada == undefined || this.posicaoAlterada == 'N') { 
			this.borda = new Coordenada(this.posX+this.largura/2, this.posY+this.altura/2);
		}
		
		this.bordaX = this.borda.x;
		this.bordaY = this.borda.y;

		this.contexto.beginPath();
		this.contexto.strokeStyle = "black";
		this.contexto.fillStyle = "black";
		if (this.isSelecionado()) {
			this.contexto.lineWidth = 3;
		}else{
			this.contexto.lineWidth = 1;
		}

		this.contexto.moveTo(posOrigemX,posOrigemY);
		this.contexto.lineTo(centroXOrigem,centroYOrigem);
		
		this.contexto.moveTo(posDestinoX,posDestinoY);
		this.contexto.lineTo(centroXDestino,centroYDestino);
		
		
		this.contexto.moveTo(posOrigemX,posOrigemY);
		this.contexto.lineTo(this.borda.x, this.borda.y);
		this.contexto.moveTo(this.borda.x, this.borda.y);
		this.contexto.lineTo(posDestinoX, posDestinoY);

		this.contexto.closePath();	
		this.contexto.stroke();
		
		this.desenhaSeta(this.conexaoDestino);

		this.contexto.beginPath();
		this.contexto.strokeStyle = "rgba(64, 64, 64, 0.3)";
		this.contexto.lineWidth = 1;
		this.contexto.moveTo(this.borda.x, this.borda.y - 1.5*TOLERANCIA_SELECAO);
		this.contexto.lineTo(this.borda.x, this.borda.y + 1.5*TOLERANCIA_SELECAO);
		this.contexto.moveTo(this.borda.x-1.5*TOLERANCIA_SELECAO, this.borda.y);
		this.contexto.lineTo(this.borda.x+1.5*TOLERANCIA_SELECAO, this.borda.y);		
		this.contexto.closePath();	
		this.contexto.stroke();
		this.exibeNome();
		
		if (this.isSelecionado()) {
			this.listaDePropriedades.desenha();
			this.destacado = true;
		}
	}	
	
	this.desenhaSeta = function(conexao) {
		this.contexto.beginPath();
		switch (conexao.posicao) {
			case ACIMA:
			this.contexto.moveTo(conexao.x, conexao.y);
			this.contexto.lineTo(conexao.x+conexao.largura/2, conexao.y+conexao.altura/2);
			this.contexto.lineTo(conexao.x+conexao.largura, conexao.y);
			break;
		case DIREITA:
			this.contexto.moveTo(conexao.x+conexao.largura, conexao.y);
			this.contexto.lineTo(conexao.x+conexao.largura/2, conexao.y+conexao.altura/2);
			this.contexto.lineTo(conexao.x+conexao.largura, conexao.y+conexao.altura);
			break;
		case ABAIXO:
			this.contexto.moveTo(conexao.x, conexao.y+conexao.altura);
			this.contexto.lineTo(conexao.x+conexao.largura/2, conexao.y+conexao.altura/2);
			this.contexto.lineTo(conexao.x+conexao.largura, conexao.y+conexao.altura);
			break;
		case ESQUERDA:
			this.contexto.moveTo(conexao.x, conexao.y);
			this.contexto.lineTo(conexao.x+conexao.largura/2, conexao.y+conexao.altura/2);
			this.contexto.lineTo(conexao.x, conexao.y+conexao.altura);
			break;
		}
		this.contexto.closePath();	
		this.contexto.stroke();
		this.contexto.fill();
	}		
	
	this.defineValorPropriedade = function(id, valor) {
		this[id] = valor;
		if (id == 'nome')
			this.nome = valor;
	}

	this.exibeNome = function() {
		if (this.nome == '' || this.nome == undefined)
			return;
		var tamanho = this.nome.length * 8;
		this.contexto.beginPath();		
		this.contexto.fillStyle = "rgba(48, 48, 48, 0.8)";
		this.contexto.font = "8pt Tahoma";
		this.contexto.fillText("["+this.nome+"]", this.borda.x-tamanho/3, this.borda.y-10);			
		this.contexto.closePath();		
	}
	
}

//**** Conexão
function Conexao(x, y, l, a, posicao) {
	this.x = x;
	this.y = y;
	this.largura = l;
	this.altura = a;
	this.posicao = posicao;
	
	this.getCentroX = function() {
		return this.x + (this.largura / 2);
	}
	this.getCentroY = function() {
		return this.y + (this.altura / 2);	
	}
}

//**** Elemento do fluxo
function ElementoFluxo(_context) {
	idElemento ++;
	
	this.idElemento = idElemento;
	
	this.posX;
    this.posY;

	this.largura = 0;
	this.altura = 0;

	this.imagem;
	
	this.contexto = _context;
    this.imagemObj;
    this.propriedades = [];
    
	this.listaDePropriedades = new ListaDePropriedades(this.contexto,this);
	
	this.conexoes = [];
	this.conexoes.length = 0;
	
	this.bordas = [];
	this.bordas.length = 0;
	
	this.larguraPadrao;
	this.alturaPadrao;
	
	this.borda;
	this.ajustavel;
	
	var self = this;	

	this.clone = function(origem) {
		clone(origem, this);
		this.idElemento = NumberUtil.toInteger(this.idElemento);
		this.posX = NumberUtil.toDouble(this.posX);
		this.posY = NumberUtil.toDouble(this.posY);
		this.largura = NumberUtil.toDouble(this.largura);
		this.altura = NumberUtil.toDouble(this.altura);
		this.larguraPadrao = NumberUtil.toDouble(this.larguraPadrao);
		this.alturaPadrao = NumberUtil.toDouble(this.alturaPadrao);
		if (origem.borda == 'true')
			this.borda = true;
		else
			this.borda = false;
		if (origem.ajustavel == 'true')
			this.ajustavel = true;
		else
			this.ajustavel = false;
	}
	
	this.getPosX = function() {
		return this.posX;
	}
	
	this.getPosY = function() {
		return this.posY;
	}
	
	this.getAltura = function() {
		return this.altura;
	}
	
	this.getLargura = function() {
		return this.largura;
	}

	this.getPosX2 = function(){
		return this.posX + this.largura;
	};
	
	this.getPosY2 = function(){
		return this.posY + this.altura;
	};
	
	this.getXCentral = function(){
		return this.posX + (this.largura/2);
	};
	
	this.getYCentral = function(){
		return this.posY + (this.altura/2);
	};
	
	this.getPosicaoPropriedades = function() {
		return new Conexao(this.getPosX2()+TOLERANCIA_BORDA, this.getYCentral()+TOLERANCIA_BORDA);
	}
	
	this.isSelecionado = function() {
		return selecao != null && !selecao.isSequencia() && selecao.elemento != null && selecao.elemento.idElemento == this.idElemento;
	}
	
	this.getListaDePropriedades = function() {
		return this.listaDePropriedades;
	} 

	this.posiciona = function(_posicao) {
		this.imagemObj = new Image();
		if (this.largura == 0)
			this.largura = this.larguraPadrao;
		if (this.altura == 0)
			this.altura = this.alturaPadrao;
		this.posX = parseInt(_posicao.x);
		this.posY = parseInt(_posicao.y);
		this.imagemObj.src = "imagens/" + this.imagem;
		this.listaDePropriedades = new ListaDePropriedades(this.contexto,this);
	};
	
	this.desenha = function() {
		if (this.imagemObj == null) {
			alert('Erro! Imagem não localizada.');
			return;
		}
		if (this.borda) 
			this.contexto.drawImage(this.imagemObj, this.posX + 3, this.posY + 3, this.imagemObj.width, this.imagemObj.height);
		else
			this.contexto.drawImage(this.imagemObj, this.posX, this.posY, this.imagemObj.width, this.imagemObj.height);
		this.desenhaConexoes();
		this.exibeNome();
	};
	
	this.getPropriedades = function() {
		return this.getListaDePropriedades().getPropriedades();
	}
	
	this.existemPropriedades = function() {
		return this.getPropriedades() != null && this.getPropriedades() != undefined && this.getPropriedades().length > 0;
	}
	
	this.destaca = function() {
		this.contexto.beginPath();
		if (this.isSelecionado() && !this.borda) {
			this.contexto.strokeStyle = "black";
			this.contexto.lineWidth = 3;
		}else{
			this.contexto.strokeStyle = "#eee";
			this.contexto.lineWidth = 1;
		}
		this.contexto.strokeRect(this.posX - TOLERANCIA_SELECAO, this.posY - TOLERANCIA_SELECAO, this.largura + 2*TOLERANCIA_SELECAO, this.altura + 2*TOLERANCIA_SELECAO);
		this.contexto.closePath();
		this.contexto.stroke();
		
		if (this.conexoes.lenght == 0)
			return;
		
		var c1 = this.conexoes[0]; 
		var c2 = this.conexoes[1]; 
		var c3 = this.conexoes[2]; 
		var c4 = this.conexoes[3]; 
		var c5 = this.conexoes[4];
		
		this.contexto.beginPath();
		this.contexto.fillStyle = "rgba(64, 64, 64, 0.3)";
		//this.contexto.fillStyle = "rgba(255, 166, 94, 0.3)";
		this.contexto.fillRect(c1.x, c1.y, c1.largura, c1.altura);
		this.contexto.fillRect(c2.x, c2.y, c2.largura, c2.altura);
		this.contexto.fillRect(c3.x, c3.y, c3.largura, c3.altura);
		this.contexto.fillRect(c4.x, c4.y, c4.largura, c4.altura);
		this.contexto.closePath();
		this.contexto.fill();
		this.contexto.stroke();	
		
		if (this.existemPropriedades()) 
			this.listaDePropriedades.desenha();
	}
	
	this.desenhaConexoes = function(){
		var c1;
		var c2;
		var c3;
		var c4;
		if (this.conexoes.length == 0) {
			c1 = new Conexao((this.posX + (this.largura / 2)  - TAMANHO_CONECTOR / 2), (this.posY   - TAMANHO_CONECTOR / 2), TAMANHO_CONECTOR, TAMANHO_CONECTOR, ACIMA);
			c2 = new Conexao((this.posX + this.largura - TAMANHO_CONECTOR / 2), (this.posY + (this.altura / 2) - TAMANHO_CONECTOR / 2), TAMANHO_CONECTOR, TAMANHO_CONECTOR, DIREITA);
			c3 = new Conexao((this.posX + (this.largura / 2)  - TAMANHO_CONECTOR / 2), (this.posY + this.altura  - TAMANHO_CONECTOR / 2), TAMANHO_CONECTOR, TAMANHO_CONECTOR, ABAIXO);
			c4 = new Conexao((this.posX  - TAMANHO_CONECTOR / 2), (this.posY + (this.altura / 2) - TAMANHO_CONECTOR / 2), TAMANHO_CONECTOR, TAMANHO_CONECTOR, ESQUERDA);
			this.conexoes[0] = c1;
			this.conexoes[1] = c2;
			this.conexoes[2] = c3;
			this.conexoes[3] = c4;
		}else{
			c1 = this.conexoes[0]; 
			c2 = this.conexoes[1]; 
			c3 = this.conexoes[2]; 
			c4 = this.conexoes[3]; 
			
			c1.x = this.posX + (this.largura / 2)  - TAMANHO_CONECTOR / 2;
			c1.y = this.posY - TAMANHO_CONECTOR / 2;

			c2.x = this.posX + this.largura - TAMANHO_CONECTOR / 2;
			c2.y = this.posY + (this.altura / 2) - TAMANHO_CONECTOR / 2;

			c3.x = this.posX + (this.largura / 2)  - TAMANHO_CONECTOR / 2;
			c3.y = this.posY + this.altura  - TAMANHO_CONECTOR / 2;
			
			c4.x = this.posX  - TAMANHO_CONECTOR / 2;
			c4.y = this.posY + (this.altura / 2) - TAMANHO_CONECTOR / 2;
		}
	
		this.bordas = [];
		//this.bordas.push(new Coordenada(this.posX, this.posY));
		//this.bordas.push(new Coordenada(this.posX + this.largura, this.posY));
		//this.bordas.push(new Coordenada(this.posX, this.posY + this.altura));
		
		
		if (this.isSelecionado()) {
			this.destaca();
			document.getElementById('spanSelecionado').innerHTML = this.posX + ", " + this.posY ;
			document.getElementById('spanCentro').innerHTML = this.getPosX2()/2 + ", " + this.getPosY2()/2 ;
		}
	
		if (this.ajustavel) {
			var borda = new Coordenada(this.posX + this.largura + TOLERANCIA_SELECAO, this.posY + this.altura + TOLERANCIA_SELECAO);
			this.bordas.push(borda);
			
			this.contexto.beginPath();
			this.contexto.strokeStyle = "rgba(64, 64, 64, 0.3)";
			this.contexto.lineWidth = 1;
			this.contexto.moveTo(borda.x,	borda.y);
			this.contexto.lineTo(borda.x + TOLERANCIA_SELECAO*3,   borda.y);
			this.contexto.moveTo(borda.x,   borda.y);
			this.contexto.lineTo(borda.x,   borda.y + TOLERANCIA_SELECAO*3);		
			this.contexto.closePath();	
			this.contexto.stroke();
		}
		
		if (this.borda) {
			this.contexto.beginPath();
			if (this.isSelecionado()) {
				this.contexto.lineWidth = 3;
			}else{
				this.contexto.lineWidth = 1;
			}
			this.contexto.strokeStyle = "rgba(0, 0, 0, 1)";
			this.contexto.lineJoin = "round";
			this.contexto.fillStyle = "rgba(255, 255, 154, 0.3)";
			this.contexto.fillRect(this.posX, this.posY, this.largura, this.altura);
			this.contexto.strokeRect(this.posX, this.posY, this.largura, this.altura);
			this.contexto.closePath();
			this.contexto.stroke();
			this.contexto.fill();
		}

	};
	
	this.isSequencia = function() {
		return false;
	}
	
	this.defineValorPropriedade = function(id, valor) {
		this[id] = valor;
		if (id == 'nome')
			this.nome = valor;
	}
	
	this.exibeNome = function() {
		if (this.nome == '' || this.nome == undefined)
			return;
		this.contexto.beginPath();		
		this.contexto.fillStyle = "rgba(48, 48, 48, 0.8)";
		this.contexto.font = "8pt Tahoma";
		this.contexto.fillText(this.nome, this.posX+20, this.getYCentral()+5, this.largura-25);			
		this.contexto.closePath();		
	}
	
	this.atualizaPropriedades = function() {
		this.listaDePropriedades.parse(this.propriedades_serializadas);
	}
};

//========================================== DESENHO DO FLUXO ============================================ //
function DesenhoFluxo(_canvas){
	objCanvas = _canvas;
	this.canvas = _canvas;
	this.tiposDeElemento = [];
	this.contexto = this.canvas.getContext("2d");
	
	this.elementos = [];
	this.elementos.length = 0;
	
	this.sequencias = [];
	this.sequencias.length = 0;
	
	var self = this;
	
	var interval = null;
	
	this.isAlterado = function() {
		return bAlterado;
	}
	
	this.setTiposDeElemento = function(_tiposDeElemento) {
		this.tiposDeElemento = _tiposDeElemento;
	}
	
	this.resetaLista = function(){
		this.elementos = [];
		this.elementos.length = 0;
		
		this.sequencias = [];
		this.sequencias.length = 0;
		idElemento = 0;
	};
	
	this.configuraEventos = function(){
		//eventos para prevenir bugs
		this.canvas.addEventListener("dragenter", prevent, true);
		this.canvas.addEventListener("dragover", prevent, true);
		this.canvas.addEventListener("dragexit", prevent, true);
		//quando um item for solto dentro do canvas
		this.canvas.addEventListener("drop", droped, true);

		this.canvas.addEventListener("mousedown", mouseDown, true);
		this.canvas.addEventListener("mouseup", mouseUp, true);
		//se o mouse sair do canvas executo a aÃ§Ã£o de mouse up e a aÃ§Ã£o de arrastar termina
		this.canvas.addEventListener("mouseout", mouseOut, true);
		this.canvas.addEventListener("mousemove", mouseMove, true);
		this.canvas.addEventListener("dblclick", dblClick, true);
	}

	var mouseMove = function(e){
		var posicao = getPosicaoRelativaCursor(e);
		var sel = buscaElementoNaPosicao(posicao);
		if(sel != null) {
			if (sel.isBorda()){
				if (sel.isSequencia())
					self.canvas.style.cursor = 'move';	
				else	
					self.canvas.style.cursor = 'se-resize';	
			} else if (sel.isConexao()) {	
				self.canvas.style.cursor = 'crosshair';
			}else{
				self.canvas.style.cursor = 'pointer';
			}
			document.getElementById('spanPosicao').innerHTML = sel.elemento.posX + ", " + sel.elemento.posY ;
		}else{
			self.canvas.style.cursor = 'default';
			document.getElementById('spanPosicao').innerHTML = posicao.x + ", " + posicao.y ;
		}
	}
	
	var mouseDown = function(e) {
		self.canvas.addEventListener("mousemove", mouseClickedMove, false);
		selecao = buscaElementoNaPosicao(getPosicaoRelativaCursor(e));
		if (selecao != null) {
			self.canvas.style.cursor = 'progress';
			exibePropriedades(false);
		}
	};
	
	var dblClick = function(e) {
		selecao = buscaElementoNaPosicao(getPosicaoRelativaCursor(e));
		if (selecao != null) {
			self.canvas.style.cursor = 'progress';
			exibePropriedades(true);
		}
	};

	this.defineValorPropriedade = function(idElemento, idPropriedade) {
		var elemento = buscaElementoPorId(idElemento);
		if (elemento == null) 
			return;
		
		if (document.formPropriedades.idElemento.value != idElemento)
			return;
		
		var elem = document.formPropriedades.elements[idPropriedade];
		if (elem.name == null) 
			return;

		elemento.defineValorPropriedade(idPropriedade, HTMLUtils.getValue(elem.id));
		atualizarCanvas();
	};
	
	var posicaoMouse;
	var mouseClickedMove = function(e) {
		self.canvas.style.cursor = 'move';	
		var posicaoMouse = getPosicaoRelativaCursor(e);
		if(selecao != null) {
			if (selecao.isSequencia()) {
				if (selecao.isBorda()) {
					bAlterado = true;
					selecao.elemento.borda.x = posicaoMouse.x;
					selecao.elemento.borda.y = posicaoMouse.y;
					selecao.elemento.posicaoAlterada = "S";
					atualizarCanvas();	
				}
			} else if (selecao.isConexao()){	
				self.canvas.style.cursor = 'crosshair';	
				atualizarCanvas();			
				bAlterado = true;

				self.contexto.beginPath(); 
				self.contexto.strokeStyle = "black";	
				self.contexto.lineWidth = 1;
				self.contexto.moveTo(selecao.conexao.getCentroX(), selecao.conexao.getCentroY());
				self.contexto.lineTo(posicaoMouse.x, posicaoMouse.y);
				self.contexto.closePath();
				self.contexto.stroke();
				
				var selecaoDestino = buscaElementoNaPosicao(getPosicaoRelativaCursor(e));
				if (selecaoDestino != null && selecaoDestino.isConexao()) {
					selecaoDestino.elemento.destaca();
			 	}
				
			} else if (selecao.isBorda()){
				self.canvas.style.cursor = 'se-resize';	
				atualizarCanvas();			
				bAlterado = true;
				if (posicaoMouse.x > selecao.elemento.posX) {
					var borda = selecao.borda;
					var variacaoX = posicaoMouse.x - (selecao.elemento.posX + selecao.elemento.largura);
					var variacaoY = posicaoMouse.y - (selecao.elemento.posY + selecao.elemento.altura);
					if (selecao.elemento.largura + variacaoX < selecao.elemento.larguraPadrao ||
						selecao.elemento.altura + variacaoY < selecao.elemento.alturaPadrao ||
						posicaoMouse.x < selecao.elemento.posX ||
						posicaoMouse.y < selecao.elemento.posY) {
						borda.x = selecao.elemento.posX + selecao.elemento.larguraPadrao;
						borda.y = selecao.elemento.posY + selecao.elemento.alturaPadrao;
					}else{ 
						borda.x = posicaoMouse.x;
						borda.y = posicaoMouse.y;
					}
					self.contexto.fillStyle = "rgba(255, 232, 165, 0.4)";
					self.contexto.strokeStyle = "#eee";
					self.contexto.lineWidth = 1;
					self.contexto.beginPath();
					self.contexto.fillRect(selecao.elemento.posX-TOLERANCIA_BORDA, selecao.elemento.posY-TOLERANCIA_BORDA, 
										 (borda.x - selecao.elemento.posX),	(borda.y - selecao.elemento.posY));
					self.contexto.strokeRect(selecao.elemento.posX-TOLERANCIA_BORDA, selecao.elemento.posY-TOLERANCIA_BORDA, 
							 			 (borda.x - selecao.elemento.posX),	(borda.y - selecao.elemento.posY));
					self.contexto.closePath();
					self.contexto.fill();
					self.contexto.stroke();   
				}
			} else {		
				bAlterado = true;
				determinaPosicaoElemento(posicaoMouse, selecao.elemento);
				atualizarCanvas();
			}		
		}		
	};
	
	var mouseUp = function(e) {
		self.canvas.style.cursor = 'default';	
		self.canvas.removeEventListener("mousemove", mouseClickedMove, false);	
		if(selecao != null){
			if (!selecao.isSequencia()) {
				var elemento = selecao.elemento;
				if (selecao.isBorda()){
					var borda = selecao.borda;
					var variacao = (borda.x - (elemento.posX + elemento.largura));
					var perc = (elemento.largura + variacao) / elemento.largura;
					elemento.largura = parseInt(elemento.largura * perc);
					elemento.altura = parseInt(elemento.altura * perc);
					if (elemento.largura < elemento.larguraPadrao) 
						elemento.largura = elemento.larguraPadrao;
					if (elemento.altura < elemento.alturaPadrao) 
						elemento.altura = elemento.alturaPadrao;
					bAlterado = true;
				}else if (selecao.isConexao()) {
					var selecaoOrigem = selecao;
					var selecaoDestino = buscaElementoNaPosicao(getPosicaoRelativaCursor(e));
					if (selecaoDestino != null && selecaoDestino.isConexao() && !existeConexao(elemento, selecaoDestino.elemento)) {
						var sequencia = new SequenciaFluxo(self.contexto); 
						sequencia.inicializa(selecaoOrigem, selecaoDestino); 
						self.sequencias.push(sequencia);
						selecaoDestino.elemento.desenhaConexoes();
					}
				}
			}
			atualizarCanvas();
		}
	};
	
	var existeConexao = function(elementoOrigem, elementoDestino) {
		var bExisteConexao = elementoOrigem.idElemento == elementoDestino.idElemento;
		if (!bExisteConexao) {
			for ( var i = 0; i < self.sequencias.length; i++) {
				var sequenciaFluxo = self.sequencias[i];
				if (sequenciaFluxo == null)
					continue;
				if (sequenciaFluxo.idElementoOrigem == elementoOrigem.idElemento && sequenciaFluxo.idElementoDestino == elementoDestino.idElemento) {
					bExisteConexao = true;
					break;
				}
			}
		}
		return bExisteConexao;
	}
	
	var mouseOut = function(e) {
		self.canvas.style.cursor = 'default';	
		self.canvas.removeEventListener("mousemove", mouseClickedMove, false);	
	};

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

	var droped = function(e) {
		prevent(e);

		var idTipoElemento = parseInt(e.dataTransfer.getData("text"));
		var tipo = self.tiposDeElemento[idTipoElemento];
		if(tipo == null){
			UtilMapa.mostrarMsgTemporaria("msg_erro", 4, "Item inválido!");
			return;
		}
		var elemento = new ElementoFluxo(self.contexto);
		clone(tipo, elemento);
		elemento.posiciona(getPosicaoRelativaCursor(e));
		redimensionaCanvas(elemento);
		if (elemento.propriedades != undefined && elemento.propriedades.length > 0) {
			for ( var i = 0; i < elemento.propriedades.length; i++) {
				var propriedade = elemento.propriedades[i];
				elemento.listaDePropriedades.getPropriedades().push(propriedade);
			}
		}	
		self.elementos.push(elemento);
		selecao = new Selecao(elemento, null, null, true);
		atualizarCanvas();
		bAlterado = true;
		exibePropriedades(true);
	};	
	
	this.limpa = function() {
		self.contexto.save();
		self.contexto.clearRect(0, 0, self.canvas.width, self.canvas.height);
		self.contexto.restore();
		
		/*
		 * Rodrigo Pecci Acorse - 04/12/2013 17h09 - #125804
		 * Comentado pois esse if reseta a posição do scroll mas não deve ser chamado dentro do método limpa().
		 */
		
		/*if (canvasParent != null) {
			canvasParent.scrollTop = 0;
			canvasParent.scrollLeft = 0;
		}*/
	}
	
	/*
	 * Rodrigo Pecci Acorse - 04/12/2013 17h09 - #125804
	 * Novo método para fazer o reset da posição do scroll.
	 */
	this.resetScroll = function() {
		if (canvasParent != null) {
			canvasParent.scrollTop = 0;
			canvasParent.scrollLeft = 0;
		}
	}
	
	this.atualiza = function() {
		self.limpa();
		for ( var i = 0; i < self.elementos.length; i++) {
			var elemento = self.elementos[i];
			
			if(elemento == null)
				continue;
			elemento.desenha();
		}

		for (s = 0; s < self.sequencias.length; s++) {
			var seq = self.sequencias[s];
			if (seq == null)
				continue;
			try{
				seq.desenha();
			}catch (e) {
				alert('s: '+s+'  origem: '+seq.idElementoOrigem);
			}
		}

	}
	
	var atualizarCanvas = function() {
		self.atualiza();
	}
		
	var buscaElementoPorId = function(id) {
		for (var i = 0; i < self.elementos.length; i++) {
			if (self.elementos[i] != null) {
				if (self.elementos[i].idElemento == id) {
					return self.elementos[i];
				}
			}
		}
		for (i = 0; i < self.sequencias.length; i++) {
			if (self.sequencias[i] != null) {
				if (self.sequencias[i].idElemento == id) {
					return self.sequencias[i];
				}
			}
		}
	};
	
	var buscaElementoNaPosicao = function(posicao){	
		var retorno = null;
		for(i = 0; i < self.sequencias.length; i++){
			var sequencia = self.sequencias[i];
			if (sequencia == null || sequencia.borda == null)
				continue;
			if((posicao.x >= sequencia.borda.x - TOLERANCIA_BORDA) && 
			   (posicao.x <= sequencia.borda.x + TOLERANCIA_BORDA) &&
			   (posicao.y >= sequencia.borda.y - TOLERANCIA_BORDA) && 
			   (posicao.y <= sequencia.borda.y + TOLERANCIA_BORDA)){
				retorno = new Selecao(sequencia, null, sequencia.borda, false);
				break;
			}
		}
		if (retorno == null) {
			for(var i = 0; i < self.elementos.length; i++){
				var elemento = self.elementos[i];
				if(elemento != null){
					if (elemento.isSelecionado() && elemento.existemPropriedades()) {
						if(posicao.x >= elemento.getListaDePropriedades().getPosX() && posicao.x <= elemento.getListaDePropriedades().getPosX2() && 
						   posicao.y >= elemento.getListaDePropriedades().getPosY() && posicao.y <= elemento.getListaDePropriedades().getPosY2()){	
							retorno = new Selecao(elemento, null, null, true);
							break;
						}
					}
				}
			}
		}
		if (retorno == null) {
			for(i = 0; i < self.sequencias.length; i++){
				var sequencia = self.sequencias[i];
				if (sequencia == null || sequencia.borda == null)
					continue;
				if (sequencia.isSelecionado() && sequencia.isDestacado()) {
					if(posicao.x >= sequencia.getListaDePropriedades().getPosX() && posicao.x <= sequencia.getListaDePropriedades().getPosX2() && 
					   posicao.y >= sequencia.getListaDePropriedades().getPosY() && posicao.y <= sequencia.getListaDePropriedades().getPosY2()){	
						retorno = new Selecao(sequencia, null, null, true);
						break;
					}
				}				
			}
		}
		if (retorno == null) {
			for(var i = 0; i < self.elementos.length; i++){
				var elemento = self.elementos[i];
				if(elemento != null){
					if (retorno == null) {
						for(var p = 0; p < elemento.conexoes.length; p++){
							var conexao = elemento.conexoes[p];
							if((posicao.x >= conexao.x - TOLERANCIA_SELECAO) && 
							   (posicao.x <= conexao.x + conexao.largura + TOLERANCIA_SELECAO) &&
							   (posicao.y >= conexao.y - TOLERANCIA_SELECAO) && 
							   (posicao.y <= conexao.y + conexao.altura + TOLERANCIA_SELECAO)){
								retorno = new Selecao(elemento, conexao, null, false);
								break;
							}
						}
					}
					if (retorno == null) {
						for(p = 0; p < elemento.bordas.length; p++){
							var borda = elemento.bordas[p];
							if((posicao.x >= borda.x) && 
							   (posicao.x <= borda.x + TOLERANCIA_SELECAO) &&
							   (posicao.y >= borda.y) && 
							   (posicao.y <= borda.y + TOLERANCIA_SELECAO)){
								retorno = new Selecao(elemento, null, borda, false);
								break;
							}
						}
					}
					if (retorno == null) {
						if(posicao.x >= elemento.posX && posicao.x <= elemento.getPosX2() && 
						   posicao.y >= elemento.posY && posicao.y <= elemento.getPosY2()){	
							retorno = new Selecao(elemento, null, null, false);
							break;
						}
					}
				}
			}
			if (retorno == null) {
				for(i = 0; i < self.sequencias.length; i++){
					var sequencia = self.sequencias[i];
					if (sequencia == null || sequencia.borda == null)
						continue;
					if(posicao.x >= sequencia.posX && posicao.x <= sequencia.getPosX2() && 
					   posicao.y >= sequencia.posY && posicao.y <= sequencia.getPosY2()){
						var cor = self.contexto.getImageData(posicao.x, posicao.y, 1, 1);
						if (cor.data[0] == 0 && cor.data[1] == 0 && cor.data[2] == 0) {
							retorno = new Selecao(sequencia, null, null, false);
							break;
						}
					}
				}
			}
		}
		return retorno;
	};

	var getPosicaoRelativaCursor = function(evt) {
		var scrollLeft = 0;
		var scrollTop = 0;
		if (canvasParent != null) {
			scrollLeft = canvasParent.scrollLeft;
			scrollTop = canvasParent.scrollTop;
		}
			
		var obj = self.canvas;
		var top = 0;
		var left = 0;
		while (obj && obj.tagName != 'BODY') {
			top += obj.offsetTop;
			left += obj.offsetLeft;
			obj = obj.offsetParent;
		}
		var mouseX = evt.clientX - left + pageXOffset + scrollLeft;
		var mouseY = evt.clientY - top + pageYOffset + scrollTop;
		return {
			x : mouseX,
			y : mouseY
		};
	};
	
	var determinaPosicaoElemento = function(posicaoMouse, elemento) {
		redimensionaCanvas(elemento);
		elemento.posX = parseInt(posicaoMouse.x - elemento.largura/2);
		elemento.posY = parseInt(posicaoMouse.y - elemento.altura/2);
		//elemento.posX = parseInt(posicaoMouse.x <= limiteTempX ? (posicaoMouse.x - elemento.largura/2) : limiteTempX);
		//elemento.posY = parseInt(posicaoMouse.y <= limiteTempY ? (posicaoMouse.y - elemento.altura/2): limiteTempY);	
	}
	
	this.excluiElemento = function() {
		if (selecao != null) {
			if (!selecao.isSequencia()) {
				var i = 0;
				while (i < self.elementos.length){
					var elemento = self.elementos[i];
					if (elemento == null)
						return;
					if (elemento.isSelecionado()) {
						self.elementos.splice(i,1);
						var s = 0;
						while (s < self.sequencias.length){
							var sequencia = self.sequencias[s];
							if (sequencia == null)
								return;
							if (sequencia.idElementoOrigem == elemento.idElemento || sequencia.idElementoDestino == elemento.idElemento) {
								self.sequencias.splice(s,1);
							}else{
								s ++;
							}
						}
					}else{
						i++;
					}
				}
			}else{
				var s = 0;
				while (s < self.sequencias.length){
					var sequencia = self.sequencias[s];
					if (sequencia == null)
						return;
					if (sequencia.isSelecionado()) {
						self.sequencias.splice(s,1);
					}else{
						s ++;
					}
				}
			}
			atualizarCanvas();
		}
	}
	
	
	this.renderizaElementos = function(elementos_serializados, sequencias_serializadas) {
		self.resetaLista();
		self.limpa();
		self.resetScroll();
		bAlterado = false;
		
		var elementos_fluxo = ObjectUtils.deserializeCollectionFromString(elementos_serializados);
		for(var i = 0; i < elementos_fluxo.length; i++){
			var elementoFluxo = elementos_fluxo[i];
			var elemento = new ElementoFluxo(self.contexto);
			elemento.clone(elementoFluxo);
			elemento.posiciona(new Coordenada(elemento.posX, elemento.posY));
			elemento.atualizaPropriedades();
			elemento.desenha();
			self.elementos.push(elemento);
			redimensionaCanvas(elemento);
			if (elemento.idElemento > idElemento)
				idElemento = elemento.idElemento;
		}
		var sequencias_fluxo = ObjectUtils.deserializeCollectionFromString(sequencias_serializadas);
		for(i = 0; i < sequencias_fluxo.length; i++){
			var sequenciaFluxo = sequencias_fluxo[i];
			var seq = new SequenciaFluxo(self.contexto);
			seq.clone(sequenciaFluxo);
			seq.elementoOrigem = buscaElementoPorId(seq.idElementoOrigem);
			seq.elementoDestino = buscaElementoPorId(seq.idElementoDestino);
			seq.localizaConexoes();
			if (seq.bordaX > 0 && seq.bordaY > 0)
				seq.borda = new Coordenada(seq.bordaX, seq.bordaY);
			seq.desenha();
			self.sequencias.push(seq);
		}
	}	
	
	this.serializa = function() {
		var elem = [];
		var seq = []
		for(var i = 0; i < self.elementos.length; i++){
			var elemento = self.elementos[i];
			if (elemento == null)
				continue;
			elem.push(elemento);
		}
		for(i = 0; i < self.sequencias.length; i++){
			var sequencia = self.sequencias[i];
			if (sequencia == null)
				continue;
			
			if (sequencia.borda != null) {
				sequencia.bordaX = sequencia.borda.x;
				sequencia.bordaY = sequencia.borda.y;
			}else{
				sequencia.bordaX = -1;
				sequencia.bordaY = -1;
			}
			sequencia.bordaX = doubleParaStr(sequencia.bordaX);
			sequencia.bordaY = doubleParaStr(sequencia.bordaY);
			seq.push(sequencia);
		}
		
		document.form.elementos_serializados.value = ObjectUtils.serializeObjects(elem);
		document.form.sequencias_serializadas.value = ObjectUtils.serializeObjects(seq);
		
		for(i = 0; i < self.sequencias.length; i++){
			var sequencia = self.sequencias[i];
			if (sequencia == null)
				continue;
			sequencia.bordaX = strParaDouble(sequencia.bordaX);
			sequencia.bordaY = strParaDouble(sequencia.bordaY);
		}		
	}
};



	addEvent(window, "load", load, false);
	function load() {
		document.form.afterLoad = function() {
			if (document.form.idFluxo.value != '') {
				exibeElementosFluxo(document.form.linhaAtual.value, document.form.idFluxo.value);
				controlarExibicaoBotoes('block');
			}else{
				if (document.form.acao.value == 'I'){
					document.getElementById('btnImportarFluxo').style.display = 'none';
					document.getElementById('btnExportarFluxo').style.display = 'none';
					document.getElementById('btnExcluirFluxo').style.display = 'none';
					$("#POPUP_FLUXO").dialog('open');
				}
				controlarExibicaoBotoes('none');
			}
		}
	}

dragstart = function(e) {
	e.dataTransfer.setData("text", e.target.getAttribute("id"));
}

var myLayout;
exibirElementos = function(json_elementos) {
	elementos     = [];
	arrayElementos = JSON.parse(json_elementos);
	var str = "<table>"
	for(var i = 0; i < arrayElementos.length; i++){
		var elementoDto = arrayElementos[i];
		str += "<tr><td class='tdIcone' id='tdIcone"+i+"' style='display:none'><img src='imagens/" + elementoDto.icone + "' draggable='true' class='icone' title='"+ i18n_message(elementoDto.label) +"' ondragstart='dragstart(event)' id='" + i + "'></img></td></tr>";
	}
	str += "<tr><td class='tdIcone'>&nbsp;</td></tr>";
	str += "<tr><td class='tdIcone' style='display:none' id='tdGravarFluxo'><img src='imagens/gravar.png' title='"+i18n_message("desenhoFluxo.msg.atualizarDesenho")+"' onclick='gravar()' class='icone'></img></td></tr>";
	str += "</table>";
	document.getElementById('divElementos').innerHTML = str;
	desenhoFluxo.setTiposDeElemento(arrayElementos);
};


controlarExibicaoBotoes = function(controle) {
	document.getElementById('imgAlterarFluxo').style.display = controle;
	document.getElementById('imgExportarFluxo').style.display = controle;
	document.getElementById('tdGravarFluxo').style.display = controle;
	for(var i = 0; i < arrayElementos.length; i++){
		document.getElementById('tdIcone'+i).style.display = controle;
	}
}


$(document).ready(function () {


    // create the layout - with data-table wrapper as the layout-content element
    myLayout = $('body').layout({
    	west__size:		 .2
    ,	center__onresize:     function (pane, $pane, state, options) {
                                var $content    = $pane.children('.ui-layout-content')
                                ,   gridHdrH    = $content.children('.slick-header').outerHeight()
                                ,   paneHdrH    = $pane.children(':first').outerHeight()
                                ,   paneFtrH    = $pane.children(':last').outerHeight()
                                ,   $gridList   = $content.children('.slick-viewport') ;
                                $gridList.height( state.innerHeight - paneHdrH - paneFtrH - gridHdrH );
                            }
    ,	 south__size:		 0
    ,    south__initClosed:   true
    ,    east__initClosed:   true
    ,	 east__size:		 0
    ,    north__initClosed:   true
    ,	 north__size:		 0
    , center: {
    		onresize_end: function(){
    			redimensionaAreaDesenho();
    		}
    	}
    , west: {
    		onresize_end: function(){
    			redimensionaAreaDesenho();
    		}
    	}

    });

    $('body > h2.loading').hide(); // hide Loading msg
	desenhoFluxo = new DesenhoFluxo(document.getElementById("canvas"));
	desenhoFluxo.configuraEventos();
   	redimensionaAreaDesenho();
});

var bCarregou = false;
var altCanvasInicial = 0;
var largCanvasInicial = 0;
redimensionaAreaDesenho = function() {
	canvasParent = document.getElementById('divCanvas');

	var l = parseInt(document.getElementById('divCenter').style.width);
	var a = parseInt(document.getElementById('divCenter').style.height);
	var t = parseInt(document.getElementById('divCenter').style.top);

	document.getElementById('divElementos').style.top = '0px';
	document.getElementById('divElementos').style.left = '0px';
	document.getElementById('divElementos').style.height = a+'px';
	document.getElementById('divElementos').style.width = '28px';

	document.getElementById('divCanvas').style.top = '0px';
	document.getElementById('divCanvas').style.left = '28px';
	document.getElementById('divCanvas').style.height = (a - 23) + 'px';
	document.getElementById('divCanvas').style.width = (l - 28)+'px';

	if (!bCarregou) {
		var altCanvas = a - 50;
		var largCanvas = l - 50;
		altCanvasInicial = altCanvas;
		largCanvasInicial = largCanvas;
		document.getElementById('canvas').height = altCanvas;
		document.getElementById('canvas').width = largCanvas;
	}

	document.getElementById('imgPosicao').style.top = '0px';
	document.getElementById('divPosicao').style.top = (a - 23) + 'px';
	document.getElementById('divPosicao').style.left = '28px';
	document.getElementById('divPosicao').style.height = '23px';
	document.getElementById('divPosicao').style.width = (l-28)+'px';

	bCarregou = true;
	desenhoFluxo.atualiza();
}

voltar = function(){
	window.location = ctx + '/pages/index/index.load';
};

var trAnterior = null;
exibeElementosFluxo = function(linha,idFluxo) {
    if (desenhoFluxo.isAlterado() && !confirm((i18n_message("desenhoFluxo.msg.confirmaSelecao")).replace("\\n\\r",""))){
        return;
    }

	if (trAnterior != null)
		trAnterior.style.background	= '';

	var tr = document.getElementById('tr'+linha);
	if (tr)
		tr.style.background	= '#FFCC99';

	trAnterior = tr;
	document.form.idFluxo.value = idFluxo;
	document.form.linhaAtual.value = linha;

	document.getElementById('canvas').height = altCanvasInicial;
	document.getElementById('canvas').width = largCanvasInicial;

	controlarExibicaoBotoes('none');
	JANELA_AGUARDE_MENU.show();
	document.form.fireEvent('exibeElementosFluxo');

	window.setTimeout(exibeElementosFluxoNovamente,300);

}

exibeElementosFluxoNovamente = function(){
	document.form.fireEvent('exibeElementosFluxo');
}

gravar = function(idFluxo) {
	if (document.form.idFluxo.value == '') {
		alert('Nenhum fluxo selecionado');
		return;
	}
	JANELA_AGUARDE_MENU.show();
	desenhoFluxo.serializa();
	document.form.fireEvent('atualizaDiagrama');
}

atualizar = function(idFluxo) {
    JANELA_AGUARDE_MENU.show();

    document.form.action = document.form.action + ".load";
    document.form.submit();
}

$(function() {
    $("#POPUP_PROPRIEDADES").dialog({
        autoOpen : false,
        width : 700,
        height : 400,
        modal : true
    });
    $("#POPUP_FLUXO").dialog({
        autoOpen : false,
        width : 700,
        height : 380,
        modal : true
    });
    $("#POPUP_IMPORTACAO").dialog({
        autoOpen : false,
        width : 700,
        height : 200,
        modal : true
    });
});

function gravarCadastroFluxo(){
    if (StringUtils.isBlank(document.form.nomeFluxo.value)){
        alert(i18n_message("citcorpore.comum.nome")+" "+i18n_message("citcorpore.comum.naoInformado"));
        document.form.nomeFluxo.focus();
        return;
    }
    if (StringUtils.isBlank(document.form.descricao.value)){
        alert(i18n_message("citcorpore.comum.descricao")+" "+i18n_message("citcorpore.comum.naoInformado"));
        document.form.descricao.focus();
        return;
    }
    if (StringUtils.isBlank(document.form.nomeClasseFluxo.value)){
        alert(i18n_message("cadastroFluxo.nomeClasse")+" "+i18n_message("citcorpore.comum.naoInformado"));
        document.form.nomeClasseFluxo.focus();
        return;
    }
	JANELA_AGUARDE_MENU.show();
	document.form.fireEvent('gravaCadastroFluxo');
}
function excluirFluxo(){
	if (!confirm(i18n_message("cadastroFLuxo.confirma_exclusao")))
		return;
	JANELA_AGUARDE_MENU.show();
	document.form.fireEvent('excluiCadastroFluxo');
}

function exibirCadastroFluxo() {
	if (document.form.idFluxo.value == '')
		return;
	var id = document.form.idFluxo.value;
	document.form.clear();
	document.form.idFluxo.value = id;
	document.form.fireEvent('recuperaCadastroFluxo');
	document.getElementById('btnExcluirFluxo').style.display = 'block';
	document.getElementById('btnImportarFluxo').style.display = 'none';
	document.getElementById('btnExportarFluxo').style.display = 'block';
	document.getElementById('imgExportarFluxo').style.display = 'block';
	$("#POPUP_FLUXO").dialog('open');
}

function exibirNovoFluxo() {
	document.form.clear();
	document.getElementById('btnExcluirFluxo').style.display = 'none';
	document.getElementById('btnImportarFluxo').style.display = 'block';
		document.getElementById('btnExportarFluxo').style.display = 'none';
		document.getElementById('imgExportarFluxo').style.display = 'none';
		$("#POPUP_FLUXO").dialog('open');
	}

	function exportarFluxo() {
		if (document.form.idFluxo.value == '')
			return;
		document.form.idFluxo.value = document.form.idFluxo.value;
    	JANELA_AGUARDE_MENU.show();
		document.form.fireEvent('exportaFluxo');
	}

	exibeImportacaoFluxo = function(){
        if (!confirm(i18n_message("desenhoFluxo.msg.confirmaNovoFluxo"))){
            return;
        }
		$("#POPUP_IMPORTACAO").dialog("open");
	}
	importarFluxo = function(){
        JANELA_AGUARDE_MENU.show();

	    document.formImportar.setAttribute("enctype","multipart/form-data");
	    document.formImportar.setAttribute("encoding","multipart/form-data");
        document.formImportar.submit();
    }


