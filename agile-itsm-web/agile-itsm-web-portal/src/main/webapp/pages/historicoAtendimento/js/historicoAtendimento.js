/**
 * Funções para a tela de histórico de atendimentos
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 29/10/2014
 */
(function( $ ) {

	var bounds = null;
	var polylines = [];

	var infoWindowContentTemplate = "<div>" +
										"<h4>" + info_window_strings.tecnico + ": {0}</h4>" +
										"<p>" +
											"<b>" + info_window_strings.numero_solicitacao + "</b>: {1}<br>" +
											"<b>" + info_window_strings.unidade + "</b>: {2}<br>" +
											"<b>" + info_window_strings.servico + "</b>: {3}<br>" +
											"<b>" + info_window_strings.status + "</b>: {4}<br>" +
											"<b>" + info_window_strings.sla + "</b>: {5}<br>" +
											"<b>" + info_window_strings.solicitante + "</b>: {6}<br>" +
											"<b>" + info_window_strings.descricao + "</b>: {7}" +
										"</p>" +
									"</div>";

	var messages = {
		data_inicio_invalida: StringUtils.format(validation.message_pattern, i18n_message("citcorpore.comum.datainicio"), validation.data_invalida),
		data_fim_invalida: StringUtils.format(validation.message_pattern, i18n_message("citcorpore.comum.datafim"), validation.data_invalida)
	}

	$.fn.onConnection = function() {
		$("#map-area").calculeHeightByWindow();
		setTimeout(function() {
			$().initialize();

			bounds = new google.maps.LatLngBounds();
		}, 1500);

		$().registerEvents();
		$().autoCompleteAtendente();
		$().autoCompleteUnidade();

		$("#idUF").prop("disabled", false);
		$("#idSituacao").prop("disabled", false);
	};

	$.fn.handleWrongMapsInitialize = function() {
		$("#idUF").prop("disabled", true);
		$("#idSituacao").prop("disabled", true);
	};

	$.fn.initialize = function() {
		$().commonInitialize();
	};

	$.fn.handleSearchResult = function(result) {
		$().mapOnDefaultState();
		var len = result.length;
		if (len > 0) {
			$("#btnLimpar").removeClass("disabled").prop("disabled", false);
			$.each(result, function() {
				var points = [];
				var atendente = this.atendente;

				var start = new google.maps.LatLng(this.start.lat, this.start.lng);
				$().drawStartMarker(start, atendente);

				$.each(this.steps, function() {
					var point = new google.maps.LatLng(this.lat, this.lng);
					points.push(point);
					bounds.extend(point);
				});

				$.each(this.solicitations, function() {
					var latLng = new google.maps.LatLng(this.coord.lat, this.coord.lng);

					var markerTitle = info_window_strings.numero_solicitacao + ": " + this.num;
					var markerIcon = $().createSolicitationMarkerIcon(this.sit);
					var markerParams = {
						map: mapsParams.map,
						position: latLng,
						icon: markerIcon,
						title: markerTitle
					};

					var marker = $().createMarker(markerParams);
					var sla = $().slaValue(this.hh, this.mm);
					var content = StringUtils.format(infoWindowContentTemplate, atendente, this.num, this.unid, this.serv, 
						this.status, sla, this.sol, $().escape(this.desc));
					marker.description = content;
					oms.addMarker(marker);
				});

				var finish = new google.maps.LatLng(this.finish.lat, this.finish.lng);
				$().drawFinishMarker(finish, atendente);

				var polyline = $().drawPolylines(mapsParams.map, points);
				polylines.push(polyline);
			});

			mapsParams.map.fitBounds(bounds);
			mapsParams.map.setCenter(bounds.getCenter());
		} else {
			alert(i18n_message("citcorpore.comum.resultado"));
		}
	};

	$.fn.createSolicitationMarkerIcon = function(situacao) {
		var markerIcon = $().createGreyMarkerIcon();
		switch(situacao) {
			case 1:
				markerIcon = $().createGreenMarkerIcon();
				break;
			case 2:
				markerIcon = $().createYellowMarkerIcon();
				break;
			case 3:
				markerIcon = $().createRedMarkerIcon();
				break;
			case 4:
				var markerIcon = $().createGreyMarkerIcon();
				break;
		}
		return markerIcon;
	};

	$.fn.elementsOnDefaultState = function() {
		$("#idCidade").prop("disabled", true);
		$("#idContrato").prop("disabled", true);
		$("#unidade").prop("disabled", true);
		$("#idGrupo").prop("disabled", true);
		$("#atendente").prop("disabled", true);
		$("#dataInicio").prop("disabled", true);
		$("#dataFim").prop("disabled", true);
		$("#btnSearch").addClass("disabled").prop("disabled", true);
		$("#btnLimparFiltro").addClass("disabled").prop("disabled", true);

		$("#dataInicio").datepicker("option", "minDate", null);
		$("#dataInicio").datepicker("option", "maxDate", null);
		$("#dataFim").datepicker("option", "minDate", null);
		$("#dataFim").datepicker("option", "maxDate", null);
	};

	$.fn.mapOnDefaultState = function() {
		mapsParams.map.setZoom(params.zoom);
		mapsParams.map.setCenter(mapsParams.latLng);

		var markersArray = oms.getMarkers();
		$().deleteMarkers(markersArray);

		oms.clearMarkers();

		$().deletePolylines(polylines);
	};

	$.fn.registerEvents = function() {
		$(window).resize(function() {
			$("#map-area").calculeHeightByWindow();
		});

		$("#idUF").unbind().change(function() {
			if ($("#idUF").val() <= 0) {
				$("#idCidade").val(0);
				$("#idCidade").prop("disabled", true);
				$("#idContrato").val(0);
				$("#idContrato").prop("disabled", true);
			} else {
				if (!$().validDataInicio(messages.data_inicio_invalida)) {
					return false;
				}
				if (!$().validDataFim(messages.data_fim_invalida)) {
					return false;
				}
				waitWindow.show();
				document.form.fireEvent("loadComboCidades");
				$("#idCidade").focus();
				$("#idCidade").prop("disabled", false);
				$("#btnLimparFiltro").removeClass("disabled").prop("disabled", false);
			}
			waitWindow.hide();
		});

		$("#idCidade").unbind().change(function() {
			if ($("#idCidade").val() <= 0) {
				$("#idContrato").val(0);
				$("#idContrato").prop("disabled", true);
			} else {
				if (!$().validDataInicio(messages.data_inicio_invalida)) {
					return false;
				}
				if (!$().validDataFim(messages.data_fim_invalida)) {
					return false;
				}
				waitWindow.show();
				document.form.fireEvent("loadComboContratos");
				$("#idContrato").focus();
				$("#idContrato").prop("disabled", false);
			}
			waitWindow.hide();
		});

		$("#idContrato").unbind().change(function() {
			if ($("#idContrato").val() <= 0) {
				$("#unidade").val("");
			} else {
				$().updateGrupoExecutorAndUnidades();
			}
			waitWindow.hide();
		});

		$("#unidade").focus(function() {
			$().makeAutocompleteUnidadeParams();
		});

		$("#unidade").blur(function() {
			var idUnidade = $("#idUnidade").val();
			if (idUnidade === "0") {
				$("#unidade").val("");
			}
		});

		$("#unidade").keypress(function() {
			$("#idUnidade").val("0");
		});

		$("#idUnidade").unbind().change(function() {
			if (!$().validDataInicio(messages.data_inicio_invalida)) {
				return false;
			}
			if (!$().validDataFim(messages.data_fim_invalida)) {
				return false;
			}
			$("#atendente").focus();
			$("#atendente").prop("disabled", false);
			waitWindow.show();
			document.form.fireEvent("loadComboGrupos");
		});

		$("#idGrupo").change(function() {
			$("#atendente").prop("disabled", false);
		});

		$("#atendente").blur(function() {
			var idAtendente = $("#idAtendente").val();
			if (idAtendente === "0") {
				$("#atendente").val("");
			}
		});

		$("#atendente").keypress(function() {
			$("#idAtendente").val("0");
		});

		$("#idAtendente").change(function() {
			$("#dataInicio").prop("disabled", false);
			$("#dataInicio").focus();
		});

		$("#dataInicio").datepicker({
			numberOfMonths: numberOfMonths,
			onClose: function(selected) {
				var date = $(this).datepicker("getDate");
				if (date) {
					$("#dataFim").datepicker("option", "maxDate", $().addDaysToDate($(this).datepicker("getDate"), 14));
				} else {
					$("#dataFim").datepicker("option", "maxDate", null);
				}
				$("#dataFim").datepicker("option", "minDate", selected);
			}
		});

		$("#dataInicio").change(function() {
			$("#dataFim").prop("disabled", false);
			setTimeout(function() {
				$("#dataFim").datepicker("show");
			}, 200);
		});

		$("#dataFim").datepicker({
			numberOfMonths: numberOfMonths,
			onClose: function(selected) {
				var date = $(this).datepicker("getDate");
				if (date) {
					$("#dataInicio").datepicker("option", "minDate", $().subtractDaysFromDate($(this).datepicker("getDate"), 14));
				} else {
					$("#dataInicio").datepicker("option", "minDate", null);
				}
				$("#dataInicio").datepicker("option", "maxDate", selected);
			}
		});

		$("#dataFim").change(function() {
			$("#btnSearch").removeClass("disabled").prop("disabled", false);
			$("#btnLimparFiltro").removeClass("disabled").prop("disabled", false);
		});

		$("#idSituacao").change(function() {
			$("#btnLimparFiltro").removeClass("disabled").prop("disabled", false);
		});

		$("#btnSearch").unbind().click(function() {
			$().validFieldsAndPerformSearch();
			$("#btnLimpar").addClass("disabled").prop("disabled", false);
		});

		$("#btnLimparFiltro").unbind().click(function() {
			document.form.clear();
			$().elementsOnDefaultState();
		});

		$("#btnLimpar").unbind().click(function() {
			$().mapOnDefaultState();
			$("#btnLimpar").addClass("disabled").prop("disabled", true);
		});
	};

	$.fn.specificValidation = function(dataInicio, dataFim) {
		var estado = $("#idUF").val();
		if (!estado || estado <= 0) {
			alert(StringUtils.format(validation.message_pattern, i18n_message("localidade.estado"), validation.campo_obrigatorio));
			return false;
		}

		var cidade = $("#idCidade").val();
		if (!cidade || cidade <= 0) {
			alert(StringUtils.format(validation.message_pattern, i18n_message("localidade.cidade"), validation.campo_obrigatorio));
			return false;
		}

		var contrato = $("#idContrato").val();
		if (!contrato || contrato <= 0) {
			alert(StringUtils.format(validation.message_pattern, i18n_message("contrato.contrato"), validation.campo_obrigatorio));
			return false;
		}
		
		if (!$(dataInicio).val()) {
			alert(StringUtils.format(validation.message_pattern, i18n_message("citcorpore.comum.datainicio"), validation.campo_obrigatorio));
			return false;
		}

		if (!DateTimeUtil.isValidDate($(dataInicio).val())) {
			alert(StringUtils.format(validation.message_pattern, i18n_message("citcorpore.comum.datainicio"), validation.data_invalida));
			return false;
		}

		if (!$(dataFim).val()) {
			alert(StringUtils.format(validation.message_pattern, i18n_message("citcorpore.comum.datafim"), validation.campo_obrigatorio));
			return false;
		}

		if (!DateTimeUtil.isValidDate($(dataFim).val())) {
			alert(StringUtils.format(validation.message_pattern, i18n_message("citcorpore.comum.datafim"), validation.data_invalida));
			return false;
		}

		return true;
	};

	$.fn.updateGrupoExecutorAndUnidades = function() {
		waitWindow.show();
		document.form.fireEvent("loadComboGrupos");
		$("#idGrupo").prop("disabled", false);
		$("#unidade").prop("disabled", false);
		$("#unidade").val("");
		$("#unidade").focus();
		$("#dataInicio").prop("disabled", false);
	};

	var startIcon = properties.context + "/imagens/start-icon.png";

	$.fn.drawStartMarker = function(latLng, markerTitle) {
		var markerParams = {
			map: mapsParams.map,
			position: latLng,
			icon: startIcon,
			title: markerTitle
		};
		var marker = $().createMarker(markerParams);
		oms.addMarker(marker);
	};

	var finishIcon = properties.context + "/imagens/finish-icon.png";

	$.fn.drawFinishMarker = function(latLng, markerTitle) {
		var markerParams = {
			map: mapsParams.map,
			position: latLng,
			icon: finishIcon,
			title: markerTitle
		};
		var marker = $().createMarker(markerParams);
		oms.addMarker(marker);
	};

}( jQuery ));
