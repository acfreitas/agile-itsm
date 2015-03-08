/**
 * @author breno.guimaraes
 */

function Utils() {
}
/**
 * Elimina espaços em branco em qualquer lugar da string
 * 
 * @param str
 * String a ser modificada.
 */
Utils.trim = function(str) {
	var novaStr = "";
	for ( var i = 0; i < str.length; i++) {
		novaStr += str.charAt(i) == ' ' ? '' : str.charAt(i);
	}
	return novaStr;
};

/**
 * Mostra uma mensagem definida em um componente definido por um tempo definido.
 * 
 * @param componenteId
 * Id do componente onde a mensagem será colocada.
 * @param segundos
 * Tempo em segundos que a mensagem ficará na tela.
 * @param texto
 * Texto que deverá aparecer no componente pelo tempo determinado.
 */
this.mostrarMsgTemporaria = function(componenteId, segundos, texto) {
	var t;
	var componente = document.getElementById(componenteId);

	componente.innerText = texto;

	t = setTimeout(function() {
		componente.innerText = '';
	}, segundos * 1000);
};