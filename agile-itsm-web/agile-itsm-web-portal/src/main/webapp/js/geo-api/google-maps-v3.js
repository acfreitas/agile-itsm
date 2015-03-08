/**
 * Utilit�rios para uso da API V3 do Google Maps
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 12/09/2014
 */
(function( $ ) {
	"use strict";

	var markerColor = {
		grey: "999",
		green: "468847",
		purple: "3d003d",
		red: "b94a48",
		yellow: "f89406"
	};

	var iconMarker = {
		greyMarker: null,
		greenMarker: null,
		purpleMarker: null,
		redMarker: null,
		yellowMarker: null
	};

	/**
	 * Cria um objeto de mapa, conforme par�metros informado
	 *
	 * @param {Object} par�metros para a cria��o do mapa
	 * @return {Object} 
	 */
	$.fn.createMap = function(params) {
		return new google.maps.Map(document.getElementById($(this).attr("id")), params);
	};

	/**
	 * Cria um �cocne para marcador no maps da cor '#999'
	 * 
	 * @return {google.maps.MarkerImage}
	 */
	$.fn.createGreyMarkerIcon = function() {
		if (!iconMarker.greyMarker) {
			iconMarker.greyMarker = $().createMarkerIcon(markerColor.grey);
		}
		return iconMarker.greyMarker;
	};

	/**
	 * Cria um �cocne para marcador no maps da cor '#468847'
	 * 
	 * @return {google.maps.MarkerImage}
	 */
	$.fn.createGreenMarkerIcon = function() {
		if (!iconMarker.greenMarker) {
			iconMarker.greenMarker = $().createMarkerIcon(markerColor.green);
		}
		return iconMarker.greenMarker;
	};

	/**
	 * Cria um �cocne para marcador no maps da cor '#3d003d'
	 * 
	 * @return {google.maps.MarkerImage}
	 */
	$.fn.createPurpleMarkerIcon = function() {
		if (!iconMarker.purpleMarker) {
			iconMarker.purpleMarker = $().createMarkerIcon(markerColor.purple);
		}
		return iconMarker.purpleMarker;
	};

	/**
	 * Cria um �cocne para marcador no maps da cor '#b94a48'
	 * 
	 * @return {google.maps.MarkerImage}
	 */
	$.fn.createRedMarkerIcon = function() {
		if (!iconMarker.redMarker) {
			iconMarker.redMarker = $().createMarkerIcon(markerColor.red);
		}
		return iconMarker.redMarker;
	};

	/**
	 * Cria um �cocne para marcador no maps da cor '#f89406'
	 * 
	 * @return {google.maps.MarkerImage}
	 */
	$.fn.createYellowMarkerIcon = function() {
		if (!iconMarker.yellowMarker) {
			iconMarker.yellowMarker = $().createMarkerIcon(markerColor.yellow);
		}
		return iconMarker.yellowMarker;
	};

	/**
	 * Cria um marcador a ser usado como icon em um Mapa
	 *
	 * @param {color} com do marcador a ser criado
	 */
	$.fn.createMarkerIcon = function(color) {
		return new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|" + color, new google.maps.Size(21, 34), new google.maps.Point(0, 0), new google.maps.Point(10, 34));
	};

	/**
	 * Cria um marcador pass�vel de ser adicionado em um mapa
	 * 
	 * @param {Object} par�metros conforme documenta��o para o Marker
	 * @return {Object} google.maps.Marker
	 */
	$.fn.createMarker = function(params) {
		return new google.maps.Marker(params);
	};

	/**
	 * Adiciona um marcador a um mapa
	 *
	 * @param {Object} mapa em que o marcador ser� adicionado
	 * @param {Object} marcador a ser adicionado
	 */
	$.fn.insertMarker = function(map, marker) {
		marker.setMap(map);
	};

	/**
	 * Remove um google.maps.Marker do mapa
	 * 
	 * @param {Object} marker a ser removido
	 */
	$.fn.removeMarker = function(marker) {
		marker.setMap(null);
	};

	/**
	 * Constr�i uma InfoWindow do Google Maps
	 *
	 * @param {Object} par�metos, conform os de uma google.maps.InfoWindow
	 * @return {Object} google.maps.InfoWindow
	 */
	$.fn.createInfoWindow = function(params) {
		if (!params.maxWidth) {
			params.maxWidth = 300;
		}

		if (!params.pixelOffset) {
			params.pixelOffset = 0;
		}

		var infowindow = new google.maps.InfoWindow({
			maxWidth: params.maxWidth,
			content : params.content,
			pixelOffset: params.pixelOffset
		});

		return infowindow;
	};

	/**
	 * Insere uma InfoWindow para um marker em um mapa do Google Maps
	 *
	 * @param {Object} mapa em que est� o marker
	 * @param {Object} mapa em que est� o marker a ser inclu�da a informa��o
	 * @param {Object} par�metos, conform os de uma google.maps.InfoWindow
	 */
	$.fn.insertInfoWindow = function(marker, map, params) {
		var infowindow = $().createInfoWindow(params);

		google.maps.event.addListener(marker, "click", function() {
			infowindow.open(map, marker);
		});
	};

	/**
	 * Atualiza a posi��o de um marcador e de foco do mapa
	 *
	 * @param {Object} google.maps.LatLng
	 * @param {Object} par�metros para configura��o
	 */
	$.fn.updatePosition = function(position, mapsParams) {
		mapsParams.marker.setPosition(position);
		mapsParams.map.panTo(position);
	};

	/**
	 * Remove todos os marcadores que est�o em um mapa
	 *
	 * @param {Object} mapa em que est�o os marcadores
	 * @param {Object} marcadores a serem removidos
	 */
	$.fn.deleteMarkers = function(markers) {
		$.each(markers, function() {
			this.setMap(null);
		});
		markers = [];
	};

	/**
	 * Remove todos as linhas (caminhos) que est�o em um mapa
	 *
	 * @param {Object} mapa em que est�o as linhas (caminhos)
	 * @param {Object} linhas (caminhos) a serem removidas
	 */
	$.fn.deletePolylines = function(polylines) {
		$.each(polylines, function() {
			this.setMap(null);
		});
		polylines = [];
	};

	/**
	 * Desenha linhas (caminhos) em um mapa
	 *
	 * @param {Object} mapa em ser� adicionada a linha (caminho)
	 * @param {Object} caminho da linha (array de coordenadas 'google.maps.LatLng')
	 */
	$.fn.drawPolylines = function(map, linePath) {
		var polyline = new google.maps.Polyline({
			path: linePath,
			strokeColor: "#FF4E43",
			strokeOpacity: 1.0,
			strokeWeight: 3.5
		}); 
		polyline.setMap(map);
		return polyline;
	};

}( jQuery ));
