/**
 * Utilit�rios para uso de artefatos geogr�ficos, como formata��es, convers�es e valida��es
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 12/09/2014
 */
(function( $ ) {
	"use strict";

	/**
	 * Valida se a(s) coordenada(s) �(s�o) v�lida(s), independente se � latitude ou longitude, validando apenas a quantidade de casas, n�meros, etc.
	 *
	 * @returns <code>true</code> caso seja(m) v�lida(s). <code>false</code>, caso contr�rio.
	 */
	$.fn.validateCoordinates = function() {
		return GeoUtils.validateCoordinatesa(arguments);
	};

	/**
	 * Verifica se latitude e longitude s�o ou n�o v�lidas
	 *
	 * @param params par�metros contendo a latitude e longitude a serem validadas
	 * @returns <code>true</code> caso latitude e longitude s�o v�lidas. <code>false</code>, caso contr�rio.
	 */
	$.fn.validateCoordinates = function(params) {
		return GeoUtils.validateCoordinates(params);
	};

	/**
	 * Verifica se uma latitude � ou n�o v�lida
	 *
	 * @param latitude latitude a ser validada
	 * @returns <code>true</code> caso latitude seja v�lida. <code>false</code>, caso contr�rio.
	 */
	$.fn.validLatitude = function(latitude) {
		return GeoUtils.validLatitude(latitude);
	};

	/**
	 * Verifica se uma longitude � ou n�o v�lida
	 *
	 * @param longitude longitude a ser validada
	 * @returns <code>true</code> caso longitude seja v�lida. <code>false</code>, caso contr�rio.
	 */
	$.fn.validLongitude = function(longitude) {
		return GeoUtils.validLongitude(longitude);
	};

	/**
	 * Converte uma posi��o decimal para uma representa��o DMS
	 * 
	 * @param {Number} coordenada geogr�fica
	 * @param {string} string que representa o ponto cardeal
	 * @return {string} 
	 */
	$.fn.decimalToDMS = function(location, hemisphere){
		return GeoUtils.decimalToDMS(location, hemisphere);
	};

	/**
	 * Converte uma latitude representada em decimal para uma representa��o DMS
	 * 
	 * @param {Number} latitude em decimal
	 */
	$.fn.decimalLatitudeToDMS = function(location) {
		return GeoUtils.decimalLatitudeToDMS(location);
	};

	/**
	 * Converte uma longitude representada em decimal para uma representa��o DMS
	 *
	 * @param {Number} longitude em decimal
	 * @return {string} longitude em DMS
	 */
	$.fn.decimalLongitudeToDMS = function(location){
		var hemisphere = (location < 0) ? west : east;  // west if negative
		return $().decimalToDMS(location, hemisphere);
	};

	var dmsPattern = /^(-?\d+(?:\.\d+)?)[�:d]?\s?(?:(\d+(?:\.\d+)?)[':]?\s?(?:(\d+(?:\.\d+)?)["]?)?)?\s?([NSELWO])?/i;

	/** 
	 * Converte representa��o de coordenadas em DMS em representa��o decimal da coordenada
	 * 
	 * @param {string}  dms string contendo uma coordenada no formato DMS
	 * @return {Number} Se DMS � uma coordenada v�lida, o valor decimal ser� retornado. Do contr�rio 'NaN' ser� retornado.
	 */
	$.fn.dmsToDecimal = function(dms) {
		return GeoUtils.dmsToDecimal(dms);
	};

	/**
	 * Valida a exist�ncia de configura��o do Google
	 *
	 * @param {properties} objeto de propriedades que contenha o atributo 'key' da Google API Key
	 * @return {Boolean} 'true', caso exista a configura��o do google e esta seja v�lida. 'false', caso contr�rio
	 */
	$.fn.hasGoogleConfiguration = function(properties) {
		return GeoUtils.hasGoogleConfiguration(properties);
	};

	/**
	 * Valida a exist�ncia de conex�o com a internet
	 *
	 * @param {onsucces} fun��o a ser executada no caso de falta de conex�o � internet
	 * @param {onerror} fun��o a ser executada no caso de exist�ncia de conex�o � internet
	 */
	$.fn.hasInternetConection = function(onsucces, onerror) {
		return GeoUtils.hasInternetConection(onsucces, onerror);
	};

	/**
	 * Insere mensagem de alerta ao usu�rio informando sobre a n�o configura��o da API Key do Google ou Client ID
	 *
	 * @param {params} par�metros
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
	 * Insere mensagem de alerta ao usu�rio informando sobre indisponibilidade de conex�o � internet
	 */
	$.fn.messageHasInternetConection = function() {
		var message = i18n_message("geographic.without.internet.connection.error");
		var warning = '<div id="no-config" class="alert alert-error no-connection">{0}</div>';
		$(this).removeClass("map-area");
		$(this).html(StringUtils.format(warning, message));
	};

}( jQuery ));
