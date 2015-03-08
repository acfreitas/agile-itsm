/**
 * Funções comuns às telas da Gestão da Força de Atendimento
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 30/10/2014
 */
(function( $ ) {

	oms = null;

	infoWindow = null;

	numberOfMonths = (properties.searchMaxDays / 30) <= 3 ? (properties.searchMaxDays / 30) : 3;

	validation = {
		message_pattern : '"{0}": {1}.',
		data_invalida : i18n_message("citcorpore.validacao.dataInvalida"),
		campo_obrigatorio : i18n_message("citcorpore.comum.campo_obrigatorio")
	};

	status_solicitacao = {
		em_andamento : "EmAndamento", // em andamento
		suspensa : "Suspensa",  // atendida com pendencia
		fechada : "Fechada",  // atendida com sucesso
		reaberta : "Reaberta", // em andamento
		re_classificada : "ReClassificada", // em andamento
		resolvida : "Resolvida" // atendida com sucesso
	};

	info_window_strings = {
		tecnico : i18n_message("citcorpore.comum.tecnico"),
		numero_solicitacao : i18n_message("problema.numero_solicitacao"),
		contrato : i18n_message("projeto.contrato"),
		unidade : i18n_message("unidade.unidade"),
		servico : i18n_message("citcorpore.comum.servico"),
		sla : i18n_message("gerenciaservico.sla"),
		solicitante : i18n_message("solicitacaoServico.solicitante"),
		status : i18n_message("citcorpore.comum.situacao"),
		descricao : i18n_message("gerenciamentoservico.descricao"),
		ultima_atualizacao : i18n_message("ocorrenciaProblema.dataHoraUltimaAtualizacao"),
		a_combinar : i18n_message("citcorpore.comum.acombinar")
	};

	completeInfoWindowContentTemplate = "<div>" +
											"<h4>" + info_window_strings.tecnico + ": {0}</h4>" +
											"<p>" +
												"<b>" + info_window_strings.numero_solicitacao + "</b>: {1}<br>" +
												"<b>" + info_window_strings.sla + "</b>: {2}<br>" +
												"<b>" + info_window_strings.descricao + "</b>: {3}<br>" +
												"<b>" + info_window_strings.ultima_atualizacao + "</b>: {4}" +
											"</p>" +
										"</div>";

	simpleInfoWindowContentTemplate = 	"<div>" +
											"<h4>" + info_window_strings.tecnico + ": {0}</h4>" +
											"<p>" +
												"<b>" + info_window_strings.ultima_atualizacao + "</b>: {1}<br>" +
											"</p>" +
										"</div>";

	params = { latitude: -15.794803, longitude: -47.882205, zoom: 12 };

	mapsParams = { latLng : null, map : null, mapElement : null, mapOptions : null };

	configParams = { key: properties.key, clientID: properties.clientID, selector: "map-area" };

	$(function() {
		mapsParams.mapElement = $("#map-area");

		$().hasInternetConection(function() {
				if (mapsParams.mapElement.length > 0) {
					infoWindow = $().createInfoWindow({ maxWidth: 600 });
				}
				$().onConnection();
			}, function() {
				$(mapsParams.mapElement).messageHasInternetConection();
			}
		);

		infoWindowMaxWidth = $(mapsParams.mapElement).width() * 0.33;
		$("#alerts").messageAPIKeyOrClientIDConfiguration(configParams);
	});

	$.fn.commonInitialize = function() {
		google.maps.visualRefresh = true;

		mapsParams.latLng = new google.maps.LatLng(params.latitude, params.longitude);

		mapsParams.mapOptions = {
			center: mapsParams.latLng,
			mapTypeControl: false,
			panControl: false,
			scaleControl: true,
			streetViewControl: true,
			zoom: params.zoom,
			zoomControl: true,
			zoomControlOptions: {
				style: google.maps.ZoomControlStyle.LARGE
			}
		}

		mapsParams.map = $(mapsParams.mapElement).createMap(mapsParams.mapOptions);
		$().navigatorGeolocation(mapsParams);

		oms = new OverlappingMarkerSpiderfier(mapsParams.map);
		oms.addListener("click", function(marker) {
			if (marker.description) {
				infoWindow.setContent(marker.description);
				infoWindow.open(mapsParams.map, marker);
			}
		});

		oms.addListener("spiderfy", function(markers) {
			infoWindow.close();
		});

		google.maps.event.addDomListener(window, "resize", function() {
			var center = mapsParams.map.getCenter();
			google.maps.event.trigger(mapsParams.map, "resize");
			mapsParams.map.setCenter(center);
		});
	};

	$.fn.calculeHeightByParent = function() {
		var width = $(this).parent().width() * 0.75;
		$(this).css({
			"height": width + "px"
		});
		return width;
	};

	$.fn.calculeHeightByParentSetParent = function() {
		var width = $(this).parent().width() * 0.75;
		$((this).parent()).css({
			"height": width + "px"
		});
		return width;
	};

	$.fn.calculeHeightByWindow = function() {
		var width = $(document).width() * 0.5;
		$(this).css({
			"height": width + "px"
		});
		return width;
	};

	$.fn.slaValue = function(hh, mm) {
		var sla = info_window_strings.a_combinar;
		if (hh > 0 || mm > 0) {
			if (mm < 10) {
				sla = hh + ":0" + mm;
			} else {
				sla = hh + ":" + mm;
			}
		}
		return sla;
	};

	var autoCompleteAtendente;
	var autoCompleteUnidade;
	var autoCompleteUsuario;

	$.fn.makeAutocompleteUnidadeParams = function() {
		var idContrato = $("#idContrato").val();
		autoCompleteUnidade.setOptions({ params: { idContrato: idContrato } });
	};

	$.fn.autoCompleteAtendente = function() {
		autoCompleteAtendente = $("#atendente").autocomplete({
			serviceUrl: "pages/autoCompleteEmpregado/autoCompleteEmpregado.load",
			noCache: true,
			minChars: 3,
			onSelect: function(value, data){
				$("#idAtendente").val(data).trigger("change");
				$("#atendente").val(value);
			}
		});
	};

	$.fn.autoCompleteUnidade = function() {
		autoCompleteUnidade = $("#unidade").autocomplete({
			serviceUrl: "pages/autoCompleteUnidade/autoCompleteUnidade.load",
			noCache: true,
			minChars: 3,
			onSelect: function(value, data){
				$("#idUnidade").val(data).trigger("change");
				$("#unidade").val(value.replace(/-*/, ""));
			}
		});
	};

	$.fn.autoCompleteUsuario = function() {
		autoCompleteUsuario = $("#usuario").autocomplete({
			serviceUrl: "pages/autoCompleteUsuario/autoCompleteUsuario.load",
			noCache: true,
			minChars: 3,
			onSelect: function(value, data){
				$("#idUsuario").val(data).trigger("change");
				$("#usuario").val(value.replace(/-*/, ""));
			}
		});
	};

	$.fn.validFieldsAndPerformSearch = function(optional) {
		var dataInicio = $("#dataInicio");
		var dataFim = $("#dataFim");

		var validated = $().specificValidation(dataInicio, dataFim);

		if (!validated) {
			return false;
		}

		if ($(dataInicio).val() && $(dataFim).val()) {
			var dataInicioMaior = DateTimeUtil.comparaDatas(document.getElementById("dataInicio"), document.getElementById("dataFim"),
				i18n_message("citcorpore.comum.validacao.intervaloInvalidoDaDataDoPeriodo"));
			if (!dataInicioMaior) {
				return false;
			}
			var result = DateTimeUtil.diferencaEmDias(DateTimeUtil.converteData($(dataInicio).val()), DateTimeUtil.converteData($(dataFim).val()));
			if (result > properties.searchMaxDays) {
				alert(StringUtils.format(i18n_message("citcorpore.comum.validacao.nao.pode.ser.superior"), properties.searchMaxDays));
				return false;
			}
		}
		waitWindow.show();
		document.form.fireEvent("performSearch");
	};

	$.fn.addDaysToDate = function(date, days) {
		date.setDate(date.getDate() + days);
		return date;
	};

	$.fn.subtractDaysFromDate = function(date, days) {
		date.setDate(date.getDate() - days);
		return date;
	};

	$.fn.validDataInicio = function(message) {
		var dataInicio = $("#dataInicio").val();
		if (dataInicio && !DateTimeUtil.isValidDate(dataInicio)) {
			alert(message);
			return false;
		}
		return true;
	}

	$.fn.validDataFim = function(message) {
		var dataFim = $("#dataFim").val();
		if (dataFim && !DateTimeUtil.isValidDate(dataFim)) {
			alert(message);
			return false;
		}
		return true;
	}

	var htmlEscapes = {
		'&': '&amp;',
		'<': '&lt;',
		'>': '&gt;',
		'"': '&quot;',
		"'": '&#x27;',
		'/': '&#x2F;'
	};

	var htmlEscaper = /[&<>"'\/]/g;

	$.fn.escape = function(string) {
		return ('' + string).replace(htmlEscaper, function(match) {
			return htmlEscapes[match];
		});
	};

}( jQuery ));
