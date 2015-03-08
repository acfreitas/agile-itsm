/**
 * Utilitários para consumo da API de Geocoding do Google
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 12/09/2014
 */
(function( $ ) {
	"use strict";

	var vars = {
		geocoder : null
	};

	var strings = {
		geocoder_error : i18n_message("geographic.geocoding.failed"),
		geocode_zero_results : i18n_message("geographic.notfound.coordinates.warning")
	};

	$.fn.populateProperties = function() {
		if (!vars.geocoder) {
			vars.geocoder = new google.maps.Geocoder();
		}
	}
	
	/**
	 * Realiza a busca por coordenadas de acordo com um endereço informado
	 * 
	 * @param {address} endereço a ser consultado
	 * @param {function} callback chamado ao térmico da chamada e que recebe um objeto google.maps.LatLng
	 */
	$.fn.performSearch = function(address, callback) {
		$().populateProperties();

		vars.geocoder.geocode({"address": address}, function(results, status) {
			if (status === google.maps.GeocoderStatus.OK && results.length > 0) {
				callback(results[0].geometry.location);
			} else if (results.length === 0) {
				alert(StringUtils.format(strings.geocode_zero_results, address));
			} else {
				alert(strings.geocoder_error);
			}
		});
	};

	/**
	 * Geocoding reverso: recupera o endereço a partir de uma posição
	 *
	 * @param {Object} google.maps.LatLng
	 * @param {function} callback chamado ao térmico da chamada e que recebe uma string
	 */
	$.fn.locationName = function(position, callback) {
		var latlng = new google.maps.LatLng(position.lat(), position.lng());

		$().populateProperties();

		vars.geocoder.geocode({"latLng": latlng}, function(results, status) {
			if (status === google.maps.GeocoderStatus.OK && results[1]) {
				callback(results[1].formatted_address);
			}
		});
	};

}( jQuery ));
