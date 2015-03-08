/**
 * Utilit�rios para o consumo dos servi�os do Google Static Maps
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 12/09/2014
 */
(function( $ ) {
	"use strict";

	var plusKey = "=";
	var queryKey = "&";

	var urlBaseGoogleStaticMaps = "http://maps.googleapis.com/maps/api/staticmap?";

	/**
	 * Monta uma URL para montar uma imagem est�tica do mapa<br>
	 * 
	 * Os par�metros v�lidos s�o os mesmos encontrados na documenta��o oficial do Google: https://developers.google.com/maps/documentation/staticmaps/
	 * 
	 * @param {Object} par�metros a serem usados para montar a URL.
	 * @return {string} url contendo os par�metros para buscar a imagem est�tica do mapa
	 */
	$.fn.buildURL = function(params) {
		var result = urlBaseGoogleStaticMaps;

		var control = 1;
		for(var prop in params) {
			if (control != 1) {
				result += queryKey;
			}
			result += prop + "=" + params[prop];
		}

		return result;
	};

}( jQuery ));
