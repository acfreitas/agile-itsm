/**
 * Utilitários para consumo da API de Geolocation do Google
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 28/10/2014
 */
(function( $ ) {

	var browserSupportGeolocation = new Boolean();

	var strings = {
		geolocation_service_failed: "Geolocation service failed.",
		geolocation_browser_doesnt_support: "Your browser doesn't support geolocation. We've placed you in Brasilia."
	};

	/**
	 * Caso o browser dê suporte à geolocalização, recupera a localização e seta como centro do mapa
	 *
	 * @param {params} objeto de parâmetros a serem usados. Deve possuir ao menos os atributos 'map' e  'latLng'
	 */
	$.fn.navigatorGeolocation = function(params) {
		if (navigator.geolocation) {
			browserSupportGeolocation = true;
			navigator.geolocation.getCurrentPosition(function(position) {
			var location = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
			params.map.setCenter(location);
			params.latLng = params.map.getCenter();
			}, function() {
				$().handleNoGeolocation(browserSupportGeolocation, params);
			});
		} else { // browser não dá suporte a geolocalização
			browserSupportGeolocation = false;
			handleNoGeolocation(browserSupportGeolocation);
		}
	};

	$.fn.handleNoGeolocation = function(errorFlag, params) {
		if (errorFlag == true) {
			console.log(strings.geolocation_service_failed);
		} else {
			console.log(strings.geolocation_browser_doesnt_support);
		}
		params.map.setCenter(params.latLng);
	};

}( jQuery ));