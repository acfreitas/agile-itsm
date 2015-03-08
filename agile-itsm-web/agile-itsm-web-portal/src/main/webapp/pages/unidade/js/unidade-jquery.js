/**
 * Componentes JQuery para inclus�o e manipula��o de Mapas na tela de Unidade
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 12/09/2014
 */
(function( $ ) {
	"use strict";

	var hasSelectInValue = /--/;

	var configParams = { key: properties.key, clientID: properties.clientID, selector: "map-area" };

	var unidadeParams = {
		latitudeId: "#latitude",
		longitudeId: "#longitude",
		latitudeDMSId: "#latitude-dms",
		longitudeDMSId: "#longitude-dms",

		bairroId: "#bairro",
		cepId: "#cep",
		cidadeId: "#idCidade",
		complementoId: "#complemento",
		logradouroId: "#logradouro",
		nomeId: "#nome",
		numeroId: "#numero",
		paisId: "#idPais",
		ufId: "#idUf",

		mapAreaId: "#map-area",

		botaoUpdateLocationId: "#btnUpdateUnidadeLocation",

		optionSelected: " option:selected"
	};

	$(function() {
		mapsParams.mapElement = $(unidadeParams.mapAreaId);

		$().hasInternetConection(
			function() {
				setTimeout(function() {
					$().initialize();

					google.maps.event.addListener(mapsParams.map, "dblclick", function(event) {
						mapsParams.latLng = event.latLng;
						$().updatePosition(mapsParams.latLng, mapsParams);
						$().setPosition(mapsParams.latLng);
					});

					google.maps.event.addListener(mapsParams.marker, "dragend", function(event) {
						$().updatePosition(mapsParams.marker.position, mapsParams);
						$().setPosition(mapsParams.marker.position);
					});

					$("#alerts").messageAPIKeyOrClientIDConfiguration(configParams);
				}, 1500);

				$(unidadeParams.logradouroId + ", " + unidadeParams.cepId + ", " + unidadeParams.numeroId + ", " + unidadeParams.bairroId).keyup(function() {
					$().enableDisableButton();
				});

				$(unidadeParams.paisId + ", " + unidadeParams.ufId + ", " + unidadeParams.cidadeId).change(function() {
					$().enableDisableButton();
				});

				$(unidadeParams.botaoUpdateLocationId).unbind().click(function() {
					$().updateUnidadeLocation();
				});
			}, function() {
				$(mapsParams.mapElement).messageHasInternetConection();
			}
		);
	});

	/**
	 * Constr�i o endere�o a ser consultado ou exibido na InfoWindow
	 *
	 * @param {string} endere�o completo da unidade
	 */
	$.fn.constructFullAddress = function(separator) {
		var content = "";

		var state = $.trim($(unidadeParams.ufId + unidadeParams.optionSelected).text());
		var city = $.trim($(unidadeParams.cidadeId + unidadeParams.optionSelected).text());
		var number = $.trim($(unidadeParams.numeroId).val());
		var zipCode = $.trim($(unidadeParams.cepId).val());
		var neighborhood = $.trim($(unidadeParams.bairroId).val());
		var street = $.trim($(unidadeParams.logradouroId).val());
		var country = $.trim($(unidadeParams.paisId + unidadeParams.optionSelected).text());

		if (street) {
			content = content.concat(street);
		}

		if (number) {
			content = content === "" ? content.concat(number) : content.concat(separator).concat(number);
		}

		if (zipCode) {
			content = content === "" ? content.concat(zipCode) : content.concat(separator).concat(zipCode);
		}

		if (neighborhood) {
			content = content === "" ? content.concat(neighborhood) : content.concat(separator).concat(neighborhood);
		}

		if (city && !hasSelectInValue.test(city)) {
			content = content === "" ? content.concat(city) : content.concat(separator).concat(city);
		}

		if (state && !hasSelectInValue.test(state)) {
			content = content === "" ? content.concat(state) : content.concat(separator).concat(state);
		}

		if (country && !hasSelectInValue.test(country)) {
			content = content === "" ? content.concat(country) : content.concat(separator).concat(country);
		}

		return content;
	};

	/**
	 * Inicializa o mapa na tela
	 */
	$.fn.initialize = function() {
		google.maps.visualRefresh = true;

		mapsParams.latLng = new google.maps.LatLng(defaultParams.latitude, defaultParams.longitude);
		defaultParams.latLng = mapsParams.latLng;

		mapsParams.mapOptions = {
			center: mapsParams.latLng,
			mapTypeControl: false,
			panControl: false,
			scaleControl: true,
			streetViewControl: true,
			zoom: defaultParams.zoom,
			zoomControl: true,
			zoomControlOptions: {
				style: google.maps.ZoomControlStyle.SMALL
			}
		}

		mapsParams.map = $(mapsParams.mapElement).createMap(mapsParams.mapOptions);

		var markerOptions = {
			position : mapsParams.latLng,
			map : mapsParams.map,
			title : i18n_message("geographic.drag.marker"),
			draggable : true
		}

		mapsParams.marker = $().createMarker(markerOptions);
	};

	/**
	 * Habilita ou desabilita o bot�o para recuperar as coordenadas geogr�ficas da unidade,
	 * de acordo com as informa��es contidas no form
	 */
	$.fn.enableDisableButton = function() {
		if ($(unidadeParams.mapAreaId).length > 0 && $().hasValueInFields()) {
			$(unidadeParams.botaoUpdateLocationId).removeClass("disabled").prop("disabled", false);
		} else {
			$(unidadeParams.botaoUpdateLocationId).addClass("disabled").prop("disabled", true);
		}
	};

	/**
	 * Verifica a exist�ncia de conte�do em campos de endere�o para habilitar o bot�o.
	 *
	 * @return {boolean} true, caso tenha valor em algum campos. false, caso contr�rio
	 */
	$.fn.hasValueInFields = function() {
		var country = $.trim($(unidadeParams.paisId).val());
		var hasCountry = country && !hasSelectInValue.test(country);

		var state = $.trim($(unidadeParams.ufId).val());
		var hasState = state && !hasSelectInValue.test(state);

		var city = $.trim($(unidadeParams.cidadeId).val());
		var hasCity = city && !hasSelectInValue.test(city);

		var hasStreet = $.trim($(unidadeParams.logradouroId).val());

		var zipCode = $.trim($(unidadeParams.cepId).val());
		var hasZipCode = zipCode && zipCode !== "________";

		var hasNeighborhood = $.trim($(unidadeParams.bairroId).val());

		return hasCountry || hasState || hasCity || hasStreet || hasZipCode || hasNeighborhood;
	};

	/**
	 * Reseta o mapa para a configura��o padr�o, quando o form � "limpado"
	 */
	$.fn.resetMapsToDefault = function() {
		mapsParams.latLng = defaultParams.latLng;

		mapsParams.map.setZoom(defaultParams.zoom);

		$().updatePosition(mapsParams.latLng, mapsParams);
	};

	/**
	 * Atualiza as informa��es de latitude e longitude da unidade
	 */
	$.fn.setPosition = function(position) {
		$(unidadeParams.latitudeId).val(position.lat());
		$(unidadeParams.latitudeDMSId).val($().decimalLatitudeToDMS(position.lat()));
		$(unidadeParams.longitudeId).val(position.lng());
		$(unidadeParams.longitudeDMSId).val($().decimalLongitudeToDMS(position.lng()));
	};

	/**
	 * Recupera os pontos geogr�ficos de acordo com o endere�o informado no form
	 */
	$.fn.updateUnidadeLocation = function() {
		var fullAddress = $().constructFullAddress(", ");

		if (fullAddress) {
			$().performSearch(fullAddress, function(latLng) {
				if (latLng) {
					$().setPosition(latLng);
					$().updatePosition(latLng, mapsParams);
				} else {
					alert(StringUtils.format(i18n_message("geographic.notfound.coordinates.warning"), fullAddress));
				}
			});
		} else {
			$().enableDisableButton();
			alert(i18n_message("geographic.empty.address"));
		}
	};

	/**
	 * Atualiza o mapa da tela de unidade quando uma nova unidade � recuperada
	 */
	$.fn.updateMapsOnRestore = function() {
		var latitude = $(unidadeParams.latitudeId).val();
		var longitude = $(unidadeParams.latitudeId).val();

		if (latitude && longitude) {
			mapsParams.latLng = new google.maps.LatLng(latitude, longitude);
			$().updatePosition(mapsParams.latLng);
			$().attachWindowMessage();
		}
	};

	/**
	 * Inclui uma google.maps.InfoWindow no marcador da unidade
	 */
	$.fn.attachWindowMessage = function() {
		var windowContent = $().makeInfoWindowContent();

		$().insertInfoWindow(mapsParams.map, mapsParams.marker, {
			maxWidth: 40,
			content : windowContent
		});
	};

	/**
	 * Constr�i o conte�do a ser inclu�do na InfoWindow da unidade
	 *
	 * @return {string} conte�do a ser colocado na InfoWindow
	 */
	$.fn.makeInfoWindowContent = function() {
		var template = "<div>" +
							"<b>{0}</b>" +
							"<p>{1}</p>" +
						"</div>";
		var nomeUnidade = $.trim($(unidadeParams.nomeId).val());
		var content = $().constructFullAddress(" - ");

		var complemento = $.trim($(unidadeParams.complementoId).val());
		if (complemento !== "") {
			content = content.concat(" - ").concat(complemento);
		}

		return StringUtils.format(template, nomeUnidade, content);
	};

	$.fn.validOnSave = function() {
		var latitude = $.trim($(mapsParams.latitudeId).val());
		if(!$().validLatitude(latitude)) {
			var messageLatitude = i18n_message("geographic.latitude.invalid");
			alert(StringUtils.format(messageLatitude, latitude));
			return false;
		}
		var longitude = $.trim($(mapsParams.longitudeId).val());
		if(!$().validLongitude(longitude)) {
			var messageLongitude = i18n_message("geographic.longitude.invalid");
			alert(StringUtils.format(messageLatitude, longitude));
			return false;
		}
		return true;
	};

}( jQuery ));
