/**
 * Utilitários para uso de artefatos geográficos, como formatações, conversões e validações
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 12/09/2014
 */
(function( $ ) {
	"use strict";

	/**
	 * Valida se a(s) coordenada(s) é(são) válida(s), independente se é latitude ou longitude, validando apenas a quantidade de casas, números, etc.
	 *
	 * @returns <code>true</code> caso seja(m) válida(s). <code>false</code>, caso contrário.
	 */
	$.fn.validateCoordinates = function() {
		return GeoUtils.validateCoordinatesa(arguments);
	};

	/**
	 * Verifica se latitude e longitude são ou não válidas
	 *
	 * @param params parâmetros contendo a latitude e longitude a serem validadas
	 * @returns <code>true</code> caso latitude e longitude são válidas. <code>false</code>, caso contrário.
	 */
	$.fn.validateCoordinates = function(params) {
		return GeoUtils.validateCoordinates(params);
	};

	/**
	 * Verifica se uma latitude é ou não válida
	 *
	 * @param latitude latitude a ser validada
	 * @returns <code>true</code> caso latitude seja válida. <code>false</code>, caso contrário.
	 */
	$.fn.validLatitude = function(latitude) {
		return GeoUtils.validLatitude(latitude);
	};

	/**
	 * Verifica se uma longitude é ou não válida
	 *
	 * @param longitude longitude a ser validada
	 * @returns <code>true</code> caso longitude seja válida. <code>false</code>, caso contrário.
	 */
	$.fn.validLongitude = function(longitude) {
		return GeoUtils.validLongitude(longitude);
	};

	/**
	 * Converte uma posição decimal para uma representação DMS
	 * 
	 * @param {Number} coordenada geográfica
	 * @param {string} string que representa o ponto cardeal
	 * @return {string} 
	 */
	$.fn.decimalToDMS = function(location, hemisphere){
		return GeoUtils.decimalToDMS(location, hemisphere);
	};

	/**
	 * Converte uma latitude representada em decimal para uma representação DMS
	 * 
	 * @param {Number} latitude em decimal
	 */
	$.fn.decimalLatitudeToDMS = function(location) {
		return GeoUtils.decimalLatitudeToDMS(location);
	};

	/**
	 * Converte uma longitude representada em decimal para uma representação DMS
	 *
	 * @param {Number} longitude em decimal
	 * @return {string} longitude em DMS
	 */
	$.fn.decimalLongitudeToDMS = function(location){
		var hemisphere = (location < 0) ? west : east;  // west if negative
		return $().decimalToDMS(location, hemisphere);
	};

	var dmsPattern = /^(-?\d+(?:\.\d+)?)[º:d]?\s?(?:(\d+(?:\.\d+)?)[':]?\s?(?:(\d+(?:\.\d+)?)["]?)?)?\s?([NSELWO])?/i;

	/** 
	 * Converte representação de coordenadas em DMS em representação decimal da coordenada
	 * 
	 * @param {string}  dms string contendo uma coordenada no formato DMS
	 * @return {Number} Se DMS é uma coordenada válida, o valor decimal será retornado. Do contrário 'NaN' será retornado.
	 */
	$.fn.dmsToDecimal = function(dms) {
		return GeoUtils.dmsToDecimal(dms);
	};

	/**
	 * Valida a existência de configuração do Google
	 *
	 * @param {properties} objeto de propriedades que contenha o atributo 'key' da Google API Key
	 * @return {Boolean} 'true', caso exista a configuração do google e esta seja válida. 'false', caso contrário
	 */
	$.fn.hasGoogleConfiguration = function(properties) {
		return GeoUtils.hasGoogleConfiguration(properties);
	};

	/**
	 * Valida a existência de conexão com a internet
	 *
	 * @param {onsucces} função a ser executada no caso de falta de conexão à internet
	 * @param {onerror} função a ser executada no caso de existência de conexão à internet
	 */
	$.fn.hasInternetConection = function(onsucces, onerror) {
		return GeoUtils.hasInternetConection(onsucces, onerror);
	};

	/**
	 * Insere mensagem de alerta ao usuário informando sobre a não configuração da API Key do Google ou Client ID
	 *
	 * @param {params} parâmetros
	 */
	$.fn.messageAPIKeyOrClientIDConfiguration = function(params) {
		var hasGoogleConfig = $().hasGoogleConfiguration(params);
		if (!hasGoogleConfig) {
			var message = i18n_message("geographic.without.google.api.config.warning");
			var warning = '<div id="no-config" class="alert no-key">{0}</div>';
			$(this).html(StringUtils.format(warning, message));
		}
	};

	/**
	 * Insere mensagem de alerta ao usuário informando sobre indisponibilidade de conexão à internet
	 */
	$.fn.messageHasInternetConection = function() {
		var message = i18n_message("geographic.without.internet.connection.error");
		var warning = '<div id="no-config" class="alert alert-error no-connection">{0}</div>';
		$(this).removeClass("map-area");
		$(this).html(StringUtils.format(warning, message));
	};

}( jQuery ));
