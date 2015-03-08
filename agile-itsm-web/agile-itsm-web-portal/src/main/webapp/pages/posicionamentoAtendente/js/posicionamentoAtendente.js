/**
 * FunÃ§Ãµes para a tela de acompanhamento do posicionamento dos atendentes
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 28/10/2014
 */
(function( $ ) {

	var bounds = null;

	var messages = {
		data_inicio_invalida: StringUtils.format(validation.message_pattern, i18n_message("citcorpore.comum.datainicio"), validation.data_invalida),
		data_fim_invalida: StringUtils.format(validation.message_pattern, i18n_message("citcorpore.comum.datafim"), validation.data_invalida)
	}

	$.fn.onConnection = function() {
		$("#map-area").calculeHeightByWindow();
		setTimeout(function() {
			$().initialize();
		}, 1500);

		$().registerEvents();
		$().autoCompleteEmpregadoByGrupo();
		$().autoCompleteUnidade();
		bounds = new google.maps.LatLngBounds();

		$("#idContrato").prop("disabled", false);
		$("#dataInicio").prop("disabled", false);
		$("#dataFim").prop("disabled", false);
	};

	$.fn.initialize = function() {
		$().commonInitialize();
	};

	$.fn.handleWrongMapsInitialize = function() {
		$("#idContrato").empty().prop("disabled", "disabled");
		$("#dataInicio").prop("disabled", "disabled");
		$("#dataFim").prop("disabled", "disabled");
		$("#btnSearch").addClass("disabled").prop("disabled", true);
	};

	/**
	 * Processa o resultado da busca e atualiza o mapa
	 *
	 * @param {Array} array gerado contendo as posições dos técnicos de campo
	 */
	$.fn.handleSearchResult = function(result) {
		$().mapOnDefaultState();
		var len = result.length;
		if (len > 0) {
			$("#btnLimpar").removeClass("disabled").prop("disabled", false);
			$.each(result, function() {
				var latLng = new google.maps.LatLng(this.latitude, this.longitude);
				var markerTitle = info_window_strings.tecnico + ": " + this.nomeAtendente;

				if (this.numeroSolicitacao) {
					var markerIcon = $().createSolicitationMarkerIcon(this.situacao);
					var markerParams = {
						map: mapsParams.map,
						position: latLng,
						icon: markerIcon,
						title: markerTitle
					};

					var sla = $().slaValue(this.prazoHH, this.prazoMM);
					var content = StringUtils.format(completeInfoWindowContentTemplate, this.nomeAtendente, this.numeroSolicitacao,
						sla, $().escape(this.descricao), this.lastSeem);
					var marker = $().createMarker(markerParams);
					marker.description = content;
					oms.addMarker(marker);
				} else {
					var markerIcon = $().createGreyMarkerIcon();
					var markerParams = {
						map: mapsParams.map,
						position: latLng,
						icon: markerIcon,
						title: markerTitle
					};

					var content = StringUtils.format(simpleInfoWindowContentTemplate, this.nomeAtendente, this.lastSeem);
					var marker = $().createMarker(markerParams);
					marker.description = content;
					oms.addMarker(marker);
				}

				if (!bounds.contains(latLng)) {
					bounds.extend(latLng);
				}
			});

			mapsParams.map.fitBounds(bounds);
			mapsParams.map.setCenter(bounds.getCenter());
		} else {
			alert(i18n_message("citcorpore.comum.resultado"));
		}
	};

	$.fn.createSolicitationMarkerIcon = function(situacao) {
		var markerIcon = $().createGreyMarkerIcon();
		if (situacao === status_solicitacao.fechada || situacao === status_solicitacao.resolvida) {
			markerIcon = $().createGreenMarkerIcon();
		} else if (situacao === status_solicitacao.em_andamento || situacao === status_solicitacao.re_classificada) {
			markerIcon = $().createYellowMarkerIcon();
		} else if (situacao === status_solicitacao.suspensa ) {
			markerIcon = $().createRedMarkerIcon();
		}
		return markerIcon;
	};

	/**
	 * Atualiza os valores em grupo executor após ser selecionado o contrato e habilita os filtros pertinentes
	 */
	$.fn.updateGrupoExecutorAndUnidades = function() {
		$("#idGrupo").prop("disabled", false);
		$("#unidade").prop("disabled", false);
		waitWindow.show();
		document.form.fireEvent("loadComboGrupos");
	};

	$.fn.elementsOnDefaultState = function() {
		$("#idGrupo").prop("disabled", true);
		$("#unidade").prop("disabled", true);
		$("#empregadoByGrupo").prop("disabled", true);
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
	};

	$.fn.registerEvents = function() {
		$(window).resize(function() {
			$("#map-area").calculeHeightByWindow();
		});

		$("#idContrato").unbind().change(function() {
			$("#idGrupo").val("0");
			$("#idUnidade").val("0");
			$("#unidade").val("");
			if ($("#idContrato").val() <= 0) {
				$("#idGrupo").prop("disabled", true);
				$("#unidade").prop("disabled", true);
				$("#btnSearch").addClass("disabled").prop("disabled", true);
			} else {
				if (!$().validDataInicio(messages.data_inicio_invalida)) {
					return false;
				}
				if (!$().validDataFim(messages.data_fim_invalida)) {
					return false;
				}
				$().updateGrupoExecutorAndUnidades();
				$("#btnSearch").removeClass("disabled").prop("disabled", false);
				$("#btnLimparFiltro").removeClass("disabled").prop("disabled", false);
			}
			waitWindow.hide();
			$("#empregadoByGrupo").val("");
			$("#empregadoByGrupo").prop("disabled", true);
		});

		$("#idGrupo").unbind().change(function() {
			$("#idAtendente").val("0");
			$("#empregadoByGrupo").val("");
			if ($("#idGrupo").val() <= 0) {
				$("#empregadoByGrupo").prop("disabled", true);
			} else {
				$("#empregadoByGrupo").focus();
				$("#empregadoByGrupo").prop("disabled", false);
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

		$("#empregadoByGrupo").focus(function() {
			$().makeAutocompleteEmpregadoByGrupoParams();
		});

		$("#empregadoByGrupo").blur(function() {
			var idAtendente = $("#idAtendente").val();
			if (idAtendente === "0") {
				$("#empregadoByGrupo").val("");
			}
		});

		$("#empregadoByGrupo").keypress(function() {
			$("#idAtendente").val("0");
		});

		$("#dataInicio").datepicker({
			numberOfMonths: numberOfMonths,
			onClose: function(selected) {
				var date = $(this).datepicker("getDate");
				if (date) {
					$("#dataFim").datepicker("option", "maxDate", $().addDaysToDate(date, properties.searchMaxDays));
				} else {
					$("#dataFim").datepicker("option", "maxDate", null);
				}
				$("#dataFim").datepicker("option", "minDate", selected);
			}
		});

		$("#dataInicio").change(function() {
			$("#btnLimparFiltro").removeClass("disabled").prop("disabled", false);
		});

		$("#dataFim").datepicker({
			numberOfMonths: numberOfMonths,
			onClose: function(selected) {
				var date = $(this).datepicker("getDate");
				if (date) {
					$("#dataInicio").datepicker("option", "minDate", $().subtractDaysFromDate(date, properties.searchMaxDays));
				} else {
					$("#dataInicio").datepicker("option", "minDate", null);
				}
				$("#dataInicio").datepicker("option", "maxDate", selected);
			}
		});

		$("#dataFim").change(function() {
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
		var contrato = $("#idContrato").val();
		if (contrato <= 0) {
			alert(StringUtils.format(validation.message_pattern, i18n_message("contrato.contrato"), validation.campo_obrigatorio));
			return false;
		}

		var unidade = $("#idUnidade").val();
		if (unidade > 0 && contrato <= 0) {
			alert(i18n_message("citcorpore.comum.validacao.unidade.contrato"));
			return false;
		}

		var valDataInicio = $(dataInicio).val();
		if (valDataInicio && !DateTimeUtil.isValidDate(valDataInicio)) {
			alert(messages.data_inicio_invalida);
			return false;
		}

		var valDataFim = $(dataFim).val();
		if (valDataFim && !DateTimeUtil.isValidDate(valDataFim)) {
			alert(messages.data_fim_invalida);
			return false;
		}

		return true;
	};

	var autoCompleteEmpregadoByGrupo;

	$.fn.makeAutocompleteEmpregadoByGrupoParams = function() {
		var idGrupo = $("#idGrupo").val();
		autoCompleteEmpregadoByGrupo.setOptions({ params: { idGrupo: idGrupo } });
	};

	$.fn.autoCompleteEmpregadoByGrupo = function() {
		autoCompleteEmpregadoByGrupo = $("#empregadoByGrupo").autocomplete({ 
			serviceUrl: "pages/autoCompleteEmpregadoByGrupo/autoCompleteEmpregadoByGrupo.load", 
			noCache: true,
			minChars: 3,
			onSelect: function(value, data){ 
				$("#idAtendente").val(data).trigger("change");
				$("#empregadoByGrupo").val(value);
			}
		});
	};

}( jQuery ));
