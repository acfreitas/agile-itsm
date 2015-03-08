/**
 * Funções para a tela de gerenciamento de rotas
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 29/10/2014
 */
(function( $ ) {

	var count = 0;
	var bounds = null;
	var markers = new Object();
	var solicitations = new Object();
	var solicitationsToAdd = new Object();

	var templateSolicitacao =   '<div id="sol{0}" class="table-row">' +
									'<div class="table-cell"><input id="chk{1}" type="checkbox" onclick="$().updateSolicitations({2});" /></div>' +
									'<div class="table-cell"><a href="javascript:void(0);" onclick="$().showSolicitationInfo({3}, {4});">{5}</a></div>' +
									'<div class="table-cell">{6}</div>' +
									'<div class="table-cell">{7}</div>' +
									'<div class="table-cell">{8}</div>' +
									'<div class="table-cell"><span class="badge{9}">{10}</span></div>' +
									'<div for="spinner-{11}" class="table-cell">' +
										'<input id="spinner-{12}" name="spinner-{13}" maxlength="2" class="spinner">' +
									'</div>' +
								'</div>';

	var infoWindowContentTemplate = "<div>" +
										"<h4>" + info_window_strings.numero_solicitacao + "</b>: {0}</h4>" +
										"<p>" +
											"<b>" + info_window_strings.contrato + "</b>: {1}<br>" +
											"<b>" + info_window_strings.unidade + "</b>: {2}<br>" +
											"<b>" + info_window_strings.sla + "</b>: {3}<br>" +
											"<b>" + info_window_strings.descricao + "</b>: {4}" +
										"</p>" +
									"</div>";

	fecharJanelaAguarde = function() {
		waitWindow.hide();
	};
	
	var messages = {
		periodo_abertura_invalido: StringUtils.format(validation.message_pattern, i18n_message("citcorpore.comum.periodo.abertura"), validation.data_invalida),
		periodo_abertura_obrigatorio: StringUtils.format(validation.message_pattern, i18n_message("citcorpore.comum.periodo.abertura"), validation.campo_obrigatorio)
	}

	$.fn.onConnection = function() {
		$().resizeBoxes();
		setTimeout(function() {
			$().initialize();
		}, 1500);

		$().registerEvents();
		$().autoCompleteUnidade();
		$().autoCompleteUsuario();
		$("#usuario").focus();

		bounds = new google.maps.LatLngBounds();

		$("img.ui-datepicker-trigger").hide();
		$("#usuario").prop("disabled", false);
	};

	$.fn.initialize = function() {
		$().commonInitialize();
	};

	$.fn.handleWrongMapsInitialize = function() {
		$("#usuario").prop("disabled", true);
	};

	$.fn.elementsFilterOnDefaultState = function() {
		$("#usuario").focus();
		$("#idUF").prop("disabled", true);
		$("#dataInicio").prop("disabled", true);
		$("#dataFim").prop("disabled", true);
		$("#idCidade").prop("disabled", true);
		$("#idContrato").prop("disabled", true);
		$("#unidade").prop("disabled", true);
		$("#idTipoSolicitacao").prop("disabled", true);
		$("#btnSearch").addClass("disabled").prop("disabled", true);
		$("#btnLimparFiltro").addClass("disabled").prop("disabled", true);

		$("#dataInicio").datepicker("option", "minDate", null);
		$("#dataInicio").datepicker("option", "maxDate", null);
		$("#dataFim").datepicker("option", "minDate", null);
		$("#dataFim").datepicker("option", "maxDate", null);
	};

	$.fn.resizeBoxes = function() {
		var bigMap = $("#rotaMapa.span12");
		if (bigMap.length > 0) {
			$("#map-area").calculeHeightByWindow();
		} 
	};

	$.fn.registerEvents = function() {
		$(window).resize(function() {
			$().resizeBoxes();
		});

		$("#idUsuario").change(function() {
			$("#dataInicio").prop("disabled", false);
			$("#dataInicio").focus();
			$("#btnLimparFiltro").removeClass("disabled").prop("disabled", false);
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
			if (!$().validDataInicio(messages.periodo_abertura_invalido)) {
				return false;
			}
			if (!$().validDataFim(messages.periodo_abertura_invalido)) {
				return false;
			}
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
					$("#dataInicio").datepicker("option", "minDate", $().subtractDaysFromDate(date, properties.searchMaxDays));
				} else {
					$("#dataInicio").datepicker("option", "minDate", null);
				}
				$("#dataInicio").datepicker("option", "maxDate", selected);
			}
		});

		$("#dataFim").unbind().change(function() {
			$("#idUF").focus();
			if ($("#idUF").val() == 0) {
				if (!$().validDataInicio(messages.periodo_abertura_invalido)) {
					return false;
				}
				if (!$().validDataFim(messages.periodo_abertura_invalido)) {
					return false;
				}
				waitWindow.show();
				document.form.fireEvent("loadComboUFs");
				$("#idUF").prop("disabled", false);
			}
			waitWindow.hide();
		});

		$("#idUF").unbind().change(function() {
			if ($("#idUF").val() <= 0) {
				$("#idCidade").val(0);
				$("#idCidade").prop("disabled", true);
			} else {
				if (!$().validDataInicio(messages.periodo_abertura_invalido)) {
					return false;
				}
				if (!$().validDataFim(messages.periodo_abertura_invalido)) {
					return false;
				}
				waitWindow.show();
				document.form.fireEvent("loadComboCidades");
				$("#idCidade").focus();
				$("#idCidade").prop("disabled", false);
			}
			waitWindow.hide();
		});

		$("#idCidade").unbind().change(function() {
			if (!$().validDataInicio(messages.periodo_abertura_invalido)) {
				return false;
			}
			if (!$().validDataFim(messages.periodo_abertura_invalido)) {
				return false;
			}
			waitWindow.show();
			document.form.fireEvent("loadComboContratos");
			$("#usuario").focus();
			$("#idContrato").prop("disabled", false);
			$("#idTipoSolicitacao").prop("disabled", false);
			if ($("#idCidade").val() <= 0) {
				$("#btnSearch").addClass("disabled").prop("disabled", true);
			} else {
				$("#btnSearch").removeClass("disabled").prop("disabled", false);
			}
			waitWindow.hide();
		});

		$("#idContrato").change(function() {
			$("#unidade").focus();
			$("#unidade").prop("disabled", false);
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

		$("#btnSearch").unbind().click(function() {
			document.form.page.value = 0;
			document.form.size.value = 10;
			$().validFieldsAndPerformSearch();
			$().clearSolicitacoesAtribuidasEMapa();
			$("#idAtendente").val($("#idUsuario").val());
		});

		$("#btnLimparFiltro").unbind().click(function() {
			document.form.clear();
			$().elementsFilterOnDefaultState();
		});

		$("#dataExecucao").datepicker({
			showOn: "button",
      		buttonImage: properties.context + "/imagens/forms/stock_form_date_field.png",
			numberOfMonths: numberOfMonths,
			buttonImageOnly: true,
			minDate: new Date()
		});

		$("#dataExecucao").change(function() {
			if (count > 0) {
				$("#btnGravar").removeClass("disabled").prop("disabled", false);
			}
		});

		$("#btnGravar").unbind().click(function() {
			$().validAndSaveAtribuicoes();
		});

		$("#btnLimpar").unbind().click(function() {
			$().clearSolicitacoesAtribuidasEMapa();
			$("#rowSolicitacoes").empty();
			$("#caption").show();
		});
	};

	$.fn.specificValidation = function(dataInicio, dataFim) {
		var usuario = $.trim($("#usuario").val());

		var idUsuario = $("#idUsuario").val();
		if (!usuario || !idUsuario || idUsuario <= 0) {
			alert(StringUtils.format(validation.message_pattern, i18n_message("citcorpore.comum.tecnico"), validation.campo_obrigatorio));
			return false;
		}

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

		if (!$(dataInicio).val()) {
			alert(messages.periodo_abertura_obrigatorio);
			return false;
		}

		if (!DateTimeUtil.isValidDate($(dataInicio).val())) {
			alert(messages.periodo_abertura_invalido);
			return false;
		}

		if (!$(dataFim).val()) {
			alert(messages.periodo_abertura_obrigatorio);
			return false;
		}

		if (!DateTimeUtil.isValidDate($(dataFim).val())) {
			alert(messages.periodo_abertura_invalido);
			return false;
		}

		return true;
	};

	$.fn.validAndSaveAtribuicoes = function() {
		if (count <= 0) {
			alert(i18n_message("citcorpore.comum.nenhumaSelecao"));
			return false;
		}

		var currentTime = new Date();
		currentTime.setHours(0, 0, 0, 0);

		var dataExecucao = DateTimeUtil.converteData($("#dataExecucao").val());

		if (dataExecucao.getTime() < currentTime.getTime()) {
			alert(i18n_message("gestao.forca.atendimento.data.execucao.invalida"));
			return false;
		}
		var atribuicoes = [];
		var idAtendente = $("#idAtendente").val();
		$.each(solicitationsToAdd, function() {
			var atribuicao = new Object();
			atribuicao.idUsuario = idAtendente;
			atribuicao.idSolicitacao = this.idSolicitacao;
			atribuicao.priorityOrder = this.priorityOrder;
			atribuicoes.push(atribuicao);
		});
		document.formSolicitacoes.atribuicoes.value = JSON.stringify(atribuicoes);
		waitWindow.show();
		document.formSolicitacoes.save();
		$("#rowSolicitacoes").empty();
		$("#caption").show();
		$("img.ui-datepicker-trigger").hide();
		$("#dataExecucao").prop("disabled", true);
		return true;
	};

	var rota_status_solicitacao = {
		nao_atendida: i18n_message("gestao.forca.atendimento.status.atendimento.nao.atendido"),
		em_atendimento: i18n_message("gestao.forca.atendimento.status.atendimento.atendendo"),
		com_pendencia: i18n_message("gestao.forca.atendimento.status.atendimento.pendencia"),
		nao_atendida_class: " badge-important",
		em_atendimento_class: " badge-warning",
		com_pendencia_class: ""
	};

	$.fn.adjustSituacao = function(solicitation) {
		var result = new Object();

		var situacao = solicitation.situacao;
		var atribuicao = solicitation.idAtribuicao;
		var iniciada = solicitation.iniciada;

		result.name = rota_status_solicitacao.nao_atendida;
		result.class = rota_status_solicitacao.nao_atendida_class;

		if (situacao === status_solicitacao.em_andamento && atribuicao != null && iniciada) {
			result.name = rota_status_solicitacao.em_atendimento;
			result.class = rota_status_solicitacao.em_atendimento_class;
		} else if (situacao === status_solicitacao.em_andamento && atribuicao != null) {
			result.name = rota_status_solicitacao.nao_atendida;
			result.class = rota_status_solicitacao.nao_atendida_class;
		} else if(situacao === status_solicitacao.suspensa){
			result.name = rota_status_solicitacao.com_pendencia;
			result.class = rota_status_solicitacao.com_pendencia_class;
		}

		return result;
	};

	var searched = false;

	$.fn.handleSearchResult = function(result) {
		$("#caption").hide();
		$("#rowSolicitacoes").empty();

		var sols = result.solicitations;
		var solsLen = sols.length;
		if (solsLen > 0) {
			$("#rotaSolicitacoes").show();
			$("img.ui-datepicker-trigger").show();
			$("#dataExecucao").prop("disabled", false);
			$("#rotaMapa").removeClass("span12").addClass("span6");

			var width = $("#map-area").calculeHeightByParent();
			$("#boxSolicitacoes").css({"min-height": width + "px"});

			$.each(sols, function() {
				var _self = this;

				_self.oldPriorityOrder = _self.priorityOrder;

				if (solicitations[this.idSolicitacao] != null) {
					_self = solicitations[this.idSolicitacao];
				}

				var solicitacao = _self.idSolicitacao;

				var sla = $().slaValue(_self.prazoHH, _self.prazoMM);

				var situacao = $().adjustSituacao(_self);

				var solicitacaoHTML = StringUtils.format(templateSolicitacao, solicitacao, solicitacao, solicitacao, solicitacao, _self.idTarefa, 
					solicitacao, _self.nomeContrato, sla, _self.tipo, situacao.class, situacao.name, solicitacao, solicitacao, solicitacao);
				$("#rowSolicitacoes").append(solicitacaoHTML);
				solicitations[solicitacao] = _self;

				var priorityOrder = "";
				if (_self.oldPriorityOrder) {
					priorityOrder = _self.oldPriorityOrder;
				} else if (_self.priorityOrder) {
					priorityOrder = _self.priorityOrder;
				}

				var $spinElement = $("#spinner-" + solicitacao);
				$spinElement.spinner({
					disabled: true,
					min : 0,
					numberFormat : "n",
					change: function() {
						$().spinnerChangeFunction(solicitacao);
					}
				}).blur(function () {
					var value = $spinElement.val();
					if (value < 0 || isNaN(value)) {
						$spinElement.val(priorityOrder ? priorityOrder : 0);
					}
				}).on("drop", function (event) {
					event.preventDefault();
					var value = $spinElement.val();
					$spinElement.val("");
					$spinElement.val(value)
				});
				$spinElement.spinner("value", priorityOrder);
			});

			if (!searched) {
				searched = true;
				google.maps.event.trigger(mapsParams.map, "resize");
				mapsParams.map.setCenter(mapsParams.latLng);
			}
			$("#btnLimpar").removeClass("disabled").prop("disabled", false);
			$("#paginator").makePaginationElement({ page: result.page, pages: result.pages });
		} else {
			if (searched) {
				$("#caption").show();
			}
			alert(i18n_message("citcorpore.comum.resultado"));
		}

		if (count > 0) {
			$.each(solicitationsToAdd, function() {
				var number = this.idSolicitacao;
				var check = $("#chk" + number);
				if (check.length > 0) {
					$(check).attr("checked", true);
				}
				$("#spinner-" + number).spinner("disable");
				$("#spinner-" + number).spinner("value", this.priorityOrder);
			});
		}
	};

	$.fn.spinnerChangeFunction = function(solicitacao) {
		var value = $("#spinner-" + solicitacao).spinner("value");
		var solicitation = solicitations[solicitacao];
		solicitation.priorityOrder = value;
	};

	$.fn.doPagination = function(page) {
		document.form.page.value = page;
		$().validFieldsAndPerformSearch();
	};

	$.fn.makePaginationElement = function(pager) {
		$(this).empty();

		var pages = pager.pages;
		if (pages > 1) {
			var page = pager.page;

			var first = page == 0;
			var last = page == pages - 1;

			var paginator = "<ul>";

			paginator += StringUtils.format('<li class="{0}"><a href="javascript:void(0);" {1}><span>&larr;</span></a></li>',
				first ? 'disabled' : '', !first ? 'onclick="$().doPagination(0)"' : '');
			paginator += StringUtils.format('<li class="{0}"><a href="javascript:void(0);" {1}><span>&laquo;</span></a></li>',
				first ? 'disabled' : '', !first ? 'onclick="$().doPagination(' + (page - 1) + ')"' : '');

			for (var i = 0; i < pages; i++) {
				paginator += StringUtils.format('<li class="{0}"><a href="javascript:void(0);" {1}><span>{2}</span></a></li>', 
					i == page ? 'active' : '', i != page ? 'onclick="$().doPagination(' + i + ')"' : '', (i + 1));
			};

			paginator += StringUtils.format('<li class="{0}"><a href="javascript:void(0);" {1}><span>&raquo;</span></a></li>',
				last ? 'disabled' : '', !last ? 'onclick="$().doPagination(' + (page + 1) + ')"' : '');
			paginator += StringUtils.format('<li class="{0}"><a href="javascript:void(0);" {1}><span>&rarr;</span></a></li>',
				last ? 'disabled' : '', !last ? 'onclick="$().doPagination(' + (pages - 1) + ')"' : '');
			paginator += "</ul>";

			$(this).append(paginator);
			$(this).show();
		} else {
			$(this).hide();
		}
	};

	$.fn.clearSolicitacoesAtribuidasEMapa = function() {
		var markersArray = oms.getMarkers();
		$().deleteMarkers(markersArray);

		oms.clearMarkers();

		count = 0;
		markers = new Object();
		solicitations = new Object();
		solicitationsToAdd = new Object();
		bounds = new google.maps.LatLngBounds();

		mapsParams.map.setZoom(params.zoom);
		mapsParams.map.setCenter(mapsParams.latLng);

		document.formSolicitacoes.clear();

		$("#paginator").empty();
		$("#dataExecucao").datepicker("hide");
		$("img.ui-datepicker-trigger").hide();
		$("#dataExecucao").prop("disabled", true);
		$("#btnGravar").addClass("disabled").prop("disabled", true);
		$("#btnLimpar").addClass("disabled").prop("disabled", true);
	};

	var url = "{0}/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?idSolicitacaoServico={1}&idTarefa={2}&escalar=N&alterarSituacao=N&editar=N&acaoFluxo=V";

	$.fn.showSolicitationInfo = function(solicitacao, tarefa) {
		var finalURL = StringUtils.format(url, properties.context, solicitacao, tarefa);
		$("#frameSolicitacao").attr("src", finalURL);

		$("#modalSolicitacao").css({
			"width": "80%",
			"height": "auto",
			"margin-top": "0% !important",
			"margin-left": "-40% "
		});

		waitWindow.show();
		$("#modalSolicitacao").modal("show");
		
		$("#modalSolicitacao .modal-body").css("max-height", "700px");
		$("#frameSolicitacao").attr("style", "min-height: 690px !important;");
	};

	$.fn.updateSolicitations = function(solicitacao) {
		var marker = markers[solicitacao];
		var solicitation = solicitations[solicitacao];
		var spinPriorityOrder = $("#spinner-" + solicitacao);
		if($("#chk" + solicitacao).is(":checked")) {
			solicitation.priorityOrder = ++count;
			solicitationsToAdd[solicitacao] = solicitation;
			var latLng = new google.maps.LatLng(solicitation.latitude, solicitation.longitude);
			if (marker == null) {
				var markerTitle = info_window_strings.numero_solicitacao + ": " + solicitation.idSolicitacao;
				var markerIcon = $().createSolicitationMarkerIcon(solicitation);
				var markerParams = {
					map: mapsParams.map,
					position: latLng,
					icon: markerIcon,
					title: markerTitle
				};

				var sla = $().slaValue(solicitation.prazoHH, solicitation.prazoMM);
				
				var content = StringUtils.format(infoWindowContentTemplate, solicitation.idSolicitacao, solicitation.nomeContrato,
					solicitation.nomeUnidade, sla, $().escape(solicitation.descricao));

				marker = $().createMarker(markerParams);
				marker.description = content;
				oms.addMarker(marker);
				markers[solicitacao] = marker;
			} else {
				marker.setMap(mapsParams.map);
			}

			$(spinPriorityOrder).spinner("enable");
			$(spinPriorityOrder).spinner("value", count);

			if (!bounds.contains(latLng)) {
				bounds.extend(latLng);
			}
		} else {
			count--;
			marker.setMap(null);
			var priorityOrder = solicitation.oldPriorityOrder ? solicitation.oldPriorityOrder : "";
			$(spinPriorityOrder).spinner("value", priorityOrder);
			$(spinPriorityOrder).spinner("disable");
			solicitation.priorityOrder = null;
			solicitationsToAdd[solicitacao] = null;
		}

		mapsParams.map.fitBounds(bounds);
		mapsParams.map.setCenter(bounds.getCenter());

		if (count <= 0) {
			mapsParams.map.setZoom(params.zoom);
			mapsParams.map.setCenter(mapsParams.latLng);
			$("#btnGravar").addClass("disabled").prop("disabled", true);
		} else if (count > 0 && $("#dataExecucao").val()) {
			$("#btnGravar").removeClass("disabled").prop("disabled", false);
		}
	};

	$.fn.createSolicitationMarkerIcon = function(solicitation) {
		var markerIcon = $().createRedMarkerIcon();

		var situacao = solicitation.situacao;
		var atribuicao = solicitation.idAtribuicao;
		var iniciada = solicitation.iniciada;

		if (situacao === status_solicitacao.em_andamento && atribuicao != null && iniciada) {
			markerIcon = $().createYellowMarkerIcon();
		} else if (situacao === status_solicitacao.em_andamento && atribuicao != null) {
			markerIcon = $().createRedMarkerIcon();
		}else if(situacao === status_solicitacao.suspensa){
		    markerIcon = $().createGreyMarkerIcon();
		}

		return markerIcon;
	};

}( jQuery ));
