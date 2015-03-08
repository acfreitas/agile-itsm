/**
 * @author breno.guimaraes
 */

function GerenciadorImagem() {

	/**
	 * Mostra uma imagem na tela.
	 * 
	 * @param myImage
	 * Objeto da classe MyImage com as propriedades da imagem.
	 */
	this.attachImage = function(myImage) {
		var imageObj = new Image();
		// ao carregar, torna visível
		imageObj.onload = function() {
			if (myImage.getHeight() != null && myImage.getWidth() != null) {
				myImage.getContext().drawImage(imageObj, myImage.getXPos(), myImage.getYPos(), myImage.getWidth(), myImage.getHeight());
			} else {
				myImage.getContext().drawImage(imageObj, myImage.getXPos(), myImage.getYPos());

			}
		};

		// carrega imagem
		imageObj.src = myImage.getCaminho();

		return imageObj;
	};
	
	
	/**
	 * Método privado apenas para tornar as imagens visíveis após
	 * o carregamento.
	 * @myImages
	 * Lista de imagens (MyImages) que foram carregadas
	 */
	this.drawImages = function(myImages) {
		for ( var j = 0; j < myImages.length; j++ ) {
			if(myImages[j].getHeight() != null && myImages[j].getWidth() != null){
				myImages[j].getContext().drawImage(myImages[j].getImageObj(),
						  					  	   myImages[j].getXPos(), 
						  					  	   myImages[j].getYPos(),
						  					  	   myImages[j].getWidth(), 
						  					  	   myImages[j].getHeight());
			} else {				
				myImages[j].getContext().drawImage(myImages[j].getImgObj(),
											  	   myImages[j].getXPos(), 
											  	   myImages[j].getYPos());
			}
		}
	};
}