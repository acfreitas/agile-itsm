var popupIE = 0; // Variável utilizada para evitar a chamada de dois alertas
					// no IE

$(window)
		.load(
				function() {
					$('#contentPesquisaGeralSolicitacao')
							.html(
									'<iframe id="framePesquisaGeralSolicitacao" src="about:blank" width="100%" height="600" class="iframeSemBorda"></iframe>');
				});

$(function() {
	$('#listaDetalhada').click(function() {

		$('#tipoLista').val(1);

		$('.listaResumida').animate({
			opacity : 0
		}, function() {
			$(this).removeClass('ativo');
			$(this).css('display', 'none');
		});

		$('.listaDetalhada').animate({
			opacity : 1
		}, function() {
			$(this).addClass('ativo');
			$(this).css('display', 'block');
			$(this).show();
			removerBotaoAtivo('#listaResumida');
			adicionarBotaoAtivo('#listaDetalhada');
		});
	});

	$('#listaResumida').click(function() {

		$('#tipoLista').val(2);

		$('.listaDetalhada').animate({
			opacity : 0
		}, function() {
			$(this).removeClass('ativo');
			$(this).css('display', 'none');
		});
		$('.listaResumida').animate({
			opacity : 1
		}, function() {
			$(this).addClass('ativo');
			$(this).css('display', 'block');
			$(this).show();
			removerBotaoAtivo('#listaDetalhada');
			adicionarBotaoAtivo('#listaResumida');
		});
	});

	$('#modal_novaSolicitacao').on('hide', function() {
		document.getElementById('frameNovaSolicitacao').src = '';
	});

});

function adicionarBotaoAtivo(param) {
	$(param).addClass('btn-primary').each(function() {
		$(this).find('i').addClass('icon-white');
	});
}

function removerBotaoAtivo(param) {
	$(param).removeClass('btn-primary').each(function() {
		$(this).find('i').removeClass('icon-white');
	});
}

function load() {
	document.form.afterRestore = function() {
	}
}

function executaModal(id) {
	window.open(URL_SISTEMA
			+ 'pages/baseConhecimentoView/modalBaseConhecimento.load?id=' + id);
}
function executaModalFaq(id) {
	window.open('/pages/pesquisaFaq/pesquisaFaq.load?id=' + id);
}

function deleteLinha(table, index) {
	HTMLUtils.deleteRow(table, index);

}

function gerarButtonDescricao(row) {
	row.cells[1].innerHTML = '<input class="btn btn-small" id="imgDetalhes" title="'
			+ i18n_message("citcorpore.comum.excluir") + '" onclick="">';
}

function gerarButtonAdd(row) {
	row.cells[2].innerHTML = '<input class="btn btn-small" id="imgDetalhes" title="'
			+ i18n_message("citcorpore.comum.excluir") + '" onclick="">';
}

function carregarServicos(idCatalogoServico, idContratoUsuario) {
	document.formListaServicos.idCatalogoServico.value = idCatalogoServico;
	document.formListaServicos.idContratoUsuario.value = idContratoUsuario;
	document.getElementsByName("observacaoPortal").value = "";
	$('#checkAnexarArquivos').prop('checked', false);
	jQuery("#anexarArquivos").val("");

	document.formListaServicos.fireEvent("contentServicos");

}

function marcarTodos(checked) {
	classe = 'perm';
	if (checked) {
		$("." + classe).each(function() {
			$(this).attr("checked", true);
		});
	} else {
		$("." + classe).each(function() {
			$(this).attr("checked", false);
		});
	}
}

function setarValoresTabela(param) {
	bootbox.dialog(param, [ {
		"label" : i18n_message("portal.cancelarSolicitacao"),
		"class" : "btn",
		"callback" : function() {
			// $("#tblDescricao").hide();
		}
	}, {
		"label" : i18n_message("portal.concluirSolicitacao"),
		"class" : "btn-primary",
		"callback" : function() {
			$('#finalizaCompra').attr('value', 's');

			adicionarCarrinho();
			$('.tabsbar a[href="#tab2"]').trigger('click');
			// Cristian - Corretiva 165205: Desabilito o botão para que o
			// usuário não clique no mesmo enquanto a compra está sendo
			// finalizada
			$("button[id='btnfinalizarCarrinho']").attr("disabled", true);
		}
	}, {
		"label" : i18n_message("portal.continuarSolicitandoServicos"),
		"class" : "btn-primary",
		"callback" : function() {
			$('#finalizaCompra').attr('value', 'n');

			adicionarCarrinhoEContinuar();
			$('.tabsbar a[href="#tab1"]').trigger('click');
		}
	}, {
		"label" : i18n_message("portal.adicionarCarrinho"),
		"class" : "btn-primary",
		"callback" : function() {
			$('#finalizaCompra').attr('value', 'n');

			adicionarCarrinho();
			$('.tabsbar a[href="#tab2"]').trigger('click');
		}
	} ]);
}

// Cristian: Reativar os objetos que foram desativados para que o usuário não
// clicasse nos mesmos
// enquanto a finalização do carrinho estava sendo processada
function reativarObjetos() {
	$("button[id='btnfinalizarCarrinho']").attr("disabled", false);
}

function limparDadosServicoCatalogo() {
	var element1 = document.getElementsByName("idServicoCatalogo");
	for (index = element1.length - 1; index >= 0; index--) {
		element1[index].parentNode.removeChild(element1[index]);
	}
	var element2 = document.getElementsByName("idContrato");
	for (index = element2.length - 1; index >= 0; index--) {
		element2[index].parentNode.removeChild(element2[index]);
	}
	var element3 = document.getElementsByName("descInfoCatalogoServico");
	for (index = element3.length - 1; index >= 0; index--) {
		element3[index].parentNode.removeChild(element3[index]);
	}
	var element4 = document.getElementsByName("observacaoPortal");
	for (index = element4.length - 1; index >= 0; index--) {
		element4[index].parentNode.removeChild(element4[index]);
	}
	var element5 = document.getElementsByName("nomeServico");
	for (index = element5.length - 1; index >= 0; index--) {
		element5[index].parentNode.removeChild(element5[index]);
	}

}

function verificarOpcaoAnexarArquivos() {

	if ($('#checkAnexarArquivos').prop('checked') != undefined
			&& $('#checkAnexarArquivos').prop('checked')) {

		document.formListaServicos.anexarArquivos.value = 's';
	}

}

function adicionarCarrinho() {
	verificarOpcaoAnexarArquivos();
	escolherServicos();
	limparDadosServicoCatalogo();
	document.formListaServicos.fireEvent("adicionaItensCarrinhoDeCompra");
}

function adicionarCarrinhoEContinuar() {
	verificarOpcaoAnexarArquivos();
	escolherServicos();
	limparDadosServicoCatalogo();
	document.formListaServicos
			.fireEvent("adicionaItensCarrinhoDeCompraEContinuar");
}

function finalizarCarrinho() {
	if (confirm(i18n_message("portal.confirmaFinalizacaoCompra"))) {
		lancarServicos();
		document.formCarrinho.fireEvent("finalizarCarrinho");
	}
}

function escolherServicos() {
	checkboxServico = document.getElementsByName('idServicoCatalogo');
	var count = checkboxServico.length;
	var contadorAux = 0;
	var baselines = new Array();
	for (var i = 1; i <= count; i++) {
		var trObj = document.getElementById('idServicoCatalogo' + i);
		if (!trObj) {
			continue;
		}
		baselines[contadorAux] = getServicoCatalogo(i);
		contadorAux = contadorAux + 1;
	}
	serializarServicoEscolhido();
}

function lancarServicos() {
	checkboxServico = document.getElementsByName('idServicoContrato');
	var count = checkboxServico.length;
	var contadorAux = 0;
	var servicos = new Array();
	for (var i = 0; i < count; i++) {
		var attr = checkboxServico[i].id.split('idServicoContrato').join('');
		var trObj = document.getElementById('idServicoContrato' + attr);
		if (!trObj) {
			continue;
		}
		servicos[contadorAux] = getServicoContrato(attr);
		contadorAux = contadorAux + 1;
	}
	serializarServicoLancados();
}

informacaoContrato = function(url) {

}

serializarServicoEscolhido = function() {
	// var tabela = document.getElementById('tblDescricao');
	// var count = tabela.rows.length;

	var checkboxServico = document.getElementsByName('idServicoCatalogo');
	var count = checkboxServico.length;
	var contadorAux = 0;
	var servicos = new Array();
	for (var i = 1; i <= count; i++) {
		var trObj = document.getElementById('idServicoCatalogo' + i);
		if (!trObj) {
			continue;
		} else if (trObj.checked) {
			servicos[contadorAux] = getServicoCatalogo(i);
			contadorAux = contadorAux + 1;
			continue;
		}
	}
	var servicosSerializadas = ObjectUtils.serializeObjects(servicos);
	document.formListaServicos.servicosEscolhidos.value = servicosSerializadas;
	document.formListaServicos.listaServicosLancados.value = $(
			"#servicosLancados").val();
	return true;
}

serializarServicoLancados = function() {
	var checkboxServico = document.getElementsByName('idServicoContrato');
	var count = checkboxServico.length;
	var contadorAux = 0;
	var servicos = new Array();
	for (var i = 0; i < count; i++) {
		var attr = checkboxServico[i].id.split('idServicoContrato').join('');
		var trObj = document.getElementById('idServicoContrato' + attr);
		if (!trObj) {
			continue;
		}
		servicos[contadorAux] = getServicoContrato(attr);
		contadorAux = contadorAux + 1;
	}
	var servicosSerializadas = ObjectUtils.serializeObjects(servicos);
	document.formCarrinho.servicosLancados.value = servicosSerializadas;
	return true;
}

getServicoCatalogo = function(seq) {
	var infoCatalogoServicoDTO = new CIT_InfoCatalogoServicoDTO();
	infoCatalogoServicoDTO.sequencia = seq;
	infoCatalogoServicoDTO.idServicoCatalogo = $('#idServicoCatalogo' + seq)
			.val();
	infoCatalogoServicoDTO.idServico = $('#idServico' + seq).val();
	infoCatalogoServicoDTO.idContrato = $('#idContrato' + seq).val();
	infoCatalogoServicoDTO.descInfoCatalogoServico = $(
			'#descInfoCatalogoServico' + seq).val();
	infoCatalogoServicoDTO.observacaoPortal = $(
			'textarea#observacaoPortal' + seq).val();
	infoCatalogoServicoDTO.nomeServico = $('#nomeServico' + seq).val();
	infoCatalogoServicoDTO.idInfoCatalogoServico = $(
			'#idInfoCatalogoServico' + seq).val();
	return infoCatalogoServicoDTO;
}

getServicoContrato = function(seq) {
	var servicoContratoDTO = new CIT_ServicoContratoDTO();
	servicoContratoDTO.sequencia = seq;
	servicoContratoDTO.idServicoContrato = $('#idServicoContrato' + seq).val();
	servicoContratoDTO.idServico = $('#idServico' + seq).val();
	servicoContratoDTO.descricao = $('#descricao' + seq).val();
	servicoContratoDTO.valorServico = $('#valorServico' + seq).val();
	servicoContratoDTO.observacaoPortal = $('#observacaoPortal' + seq).val();
	servicoContratoDTO.nomeServico = $('#nomeServico' + seq).val();
	servicoContratoDTO.existeQuestionario = $('#existeQuestionario' + seq)
			.val();
	return servicoContratoDTO;
}

function filtrarCatalogo(str) {
	document.formListaServicos.filtroCatalogo.value = str;
	document.formListaServicos.fireEvent("filtrarCatalogoServico");
}

/**
 * Autor: Thiago Matias Data: 16/08/2013 Filtra todos os dados contidos na lista
 * deve ser chamada no input via onkeyup campoBusca: valor digitado no campo de
 * filtro lista: Id da div onde ser� feito a busca
 */
function filtroListaJs(campoBusca, lista) {
	// Recupera value do campo de busca
	var term = campoBusca.value.toLowerCase();
	if (term != "") {
		var searchText = term;

		$('#' + lista + ' ul > li ')
				.each(
						function() {

							var currentLiText = $(this).text(), showCurrentLi = currentLiText
									.toLowerCase().indexOf(searchText) !== -1;

							$(this).toggle(showCurrentLi);

						});
	} else {
		// Quando n�o h� nada digitado, mostra a tabela com todos os dados
		$('#' + lista + ' ul > li')
				.each(
						function() {

							var currentLiText = $(this).text(), showCurrentLi = currentLiText
									.toLowerCase().indexOf(searchText) == -1;

							$(this).toggle(showCurrentLi);

						});
	}
}

function filtroTableJs(campoBusca, table) {
	// Recupera value do campo de busca
	var term = campoBusca.value.toLowerCase();
	if (term != "") {
		// Mostra os TR's que contem o value digitado no campoBusca
		if (table != "") {
			$("#" + table + " tbody>tr").hide();
			$("#" + table + " td").filter(function() {
				return $(this).text().toLowerCase().indexOf(term) > -1
			}).parent("tr").show();
		}
	} else {
		// Quando n�o h� nada digitado, mostra a tabela com todos os dados
		$("#" + table + " tbody>tr").show();
	}
}

function calcularTotal() {
	var checkboxServico = document.getElementsByName('valorServico');
	var count = checkboxServico.length;
	var total = 0.0;
	for (var i = 0; i < count; i++) {
		var trObj = checkboxServico[i];
		if (!trObj) {
			continue;
		}
		total += parseFloat((trObj.value == '' ? 0.0 : trObj.value.replace(".",
				"")));
	}
	$('#valorTotalServico').val(total);
	$('#imprimeTotal').html(number_format(total, 2, ',', '.'));
}

function removeLinhaTabela(rowIndex) {
	idServico = $($('#carrinho tr')[rowIndex]).find("input[name='idServico']")
			.val();
	$("#idServicoUp").val(idServico);
	document.formListaServicos.fireEvent("removerQuestionarioSessao");
	document.formListaServicos.fireEvent("removerAnexoSessao");
	HTMLUtils.deleteRow('carrinho', rowIndex);
	calcularTotal();
	ajustarGridCarrinho();

}

/**
 * Modificado 09/12/2014. Adicionado condicionais de exibição completa do portal
 *
 * @author thyen.chang
 */
function adicionarColecaoItensItens(list) {
	arrayServicoContrato = ObjectUtils
			.deserializeCollectionFromStringSemQuebraEnter(list);

	var exibirPrecoCarrinhoCompra = $('#exibirPrecoCarrinhoCompra').val();
	var string = "";
	var total = parseFloat(($('#valorTotalServico').val() == '' ? 0.0
			: parseFloat($('#valorTotalServico').val())));
	var j = (document.getElementsByName('idServicoContrato').length == 0 ? 1
			: (document.getElementsByName('idServicoContrato').length + 1));
	for (var i = 0; i < arrayServicoContrato.length; i++) {
		if (!validaItem(arrayServicoContrato[i].idServicoContrato)) {
			string += "<tr>";
			string += "	<input type='hidden' name='idServicoContrato' id='idServicoContrato"
					+ j
					+ "' value='"
					+ arrayServicoContrato[i].idServicoContrato + "' />";
			string += "	<input type='hidden' name='idServico' id='idServico"
					+ j + "' value='" + arrayServicoContrato[i].idServico
					+ "' />";
			string += "	<input type='hidden' name='descricao' id='descricao"
					+ j + "' value='" + arrayServicoContrato[i].descricao
					+ "' />";
			string += "	<input type='hidden' name='observacaoPortal' id='observacaoPortal"
					+ j
					+ "' value='"
					+ ObjectUtils
							.decodificaEnter(arrayServicoContrato[i].observacaoPortal)
					+ "' />";
			string += "	<input type='hidden' name='valorServico' id='valorServico"
					+ j
					+ "' value='"
					+ (arrayServicoContrato[i].valorServico == '' ? 0.0
							: arrayServicoContrato[i].valorServico) + "' />";
			string += "	<input type='hidden' name='idInfoCatalogoServico' id='idInfoCatalogoServico"
					+ j
					+ "' value='"
					+ arrayServicoContrato[i].idInfoCatalogoServico + "' />";
			string += "	<input type='hidden' name='existeQuestionario' id='existeQuestionario"
					+ j
					+ "' value='"
					+ arrayServicoContrato[i].existeQuestionario + "' />";
			string += "	<input type='hidden' name='respostaObrigatoria' id='respostaObrigatoria"
					+ j
					+ "' value='"
					+ arrayServicoContrato[i].respostaObrigatoria + "' />";

			if (exibirPrecoCarrinhoCompra == 'S') {
				string += "<td class='span1'>"
						+ arrayServicoContrato[i].idServicoContrato + "</td>";
				string += "<td class='span2 quebraPalavras'>"
						+ arrayServicoContrato[i].nomeServico + "</td>";
				string += "<td class='span3 quebraPalavras'>"
						+ arrayServicoContrato[i].descricao + "</td>";
				string += "<td class='span3 quebraPalavras'>"
						+ ObjectUtils
								.decodificaEnter(arrayServicoContrato[i].observacaoPortal)
						+ "</td>";
				string += "<td class='span1 quebraPalavras'>"
						+ arrayServicoContrato[i].nomeCategoriaServico
						+ "</td>";
				string += "<td class='span1'>"
						+ (arrayServicoContrato[i].valorServico == '' ? 0.0
								: arrayServicoContrato[i].valorServico)
						+ "</td>";
			} else {
				string += "	<td class='span1' style='text-align: center;'>";
				if (arrayServicoContrato[i].existeQuestionario == 'S') {
					string += "	<a href='javascript:;'onclick='exibirQuestionarioServicoPortal("
							+ arrayServicoContrato[i].idQuestionario
							+ ","
							+ arrayServicoContrato[i].idServicoContrato
							+ ","
							+ arrayServicoContrato[i].idServico
							+ ","
							+ '"'
							+ arrayServicoContrato[i].respostaObrigatoria
							+ '"'
							+ ")' class='btn-action glyphicons edit btn-default'><i></i></a>";

					if (arrayServicoContrato[i].respostaObrigatoria == 'S') {
						string += "	<a href='javascript:;'class='btn-action glyphicons warning_sign btn-danger' style='pointer-events: none;' title='Questionario de preenchimento obrigatorio!'><i></i></a>";
					}
				}
				string += "	</td>";
				string += "<td class='span2 quebraPalavras'>"
						+ arrayServicoContrato[i].nomeServico + "</td>";
				string += "<td class='span3 quebraPalavras'>"
						+ arrayServicoContrato[i].descricao + "</td>";
				string += "<td class='span3 quebraPalavras'>"
						+ ObjectUtils
								.decodificaEnter(arrayServicoContrato[i].observacaoPortal)
						+ "</td>";
				string += "<td class='center' class='span1'>";
				if (arrayServicoContrato[i].permiteAnexar == 'true') {
					string += "	<input type='hidden' name='permiteAnexar' id='permiteAnexar"
							+ j
							+ "' value='"
							+ arrayServicoContrato[i].permiteAnexar + "' />";
					string += "	<a href='javascript:;'onclick='modalAnexarArquivoServico(this,"
							+ arrayServicoContrato[i].idServico
							+ ")' class='btn-action glyphicons paperclip btn-default'><i></i>";
					string += "   </a>";
				}
				string += "</td>";
			}
			string += "<td class='center' class='span1'>";
			string += "	<a href='javascript:;' onclick='removeLinhaTabela(this.parentNode.parentNode.rowIndex);' class='btn-action glyphicons remove_2 btn-danger'><i></i></a>";
			string += "</td>";
			string += "	</tr>";
			total += (arrayServicoContrato[i].valorServico == '' ? 0.0
					: parseFloat(arrayServicoContrato[i].valorServico.replace(
							".", "")));
			j++;
		}
	}
	$('#carrinho').append(string);
	$('#valorTotalServico').val(total);
	$('#imprimeTotal').html(number_format(total, 2, ',', '.'));
	// alert($('#carrinho').html());
}
function validaItem(idServico) {
	var arrServicos = document.getElementsByName('idServicoContrato');
	var flag = false;
	for (var i = 0; i < arrServicos.length; i++) {
		if (arrServicos[i].value == idServico) {
			flag = true;
		}
	}
	return flag;
}

function mostrarMensagemSolicitacoes(param) {
	bootbox.alert(param, function(result) {
		deleteTodasLinhasCarrinho();
		document.formGerenciamento.fireEvent("atualizarLista");
	});
}
/**
 * nota 1: Para 1000.55 retorna com precis�o 1 no FF/Opera � 1,000.5, mas no IE �
 * 1,000.6 exemplo 1: number_format(1234.56); retorno 1: '1,235' exemplo 2:
 * number_format(1234.56, 2, ',', ' '); retorno 2: '1 234,56' exemplo 3:
 * number_format(1234.5678, 2, '.', ''); retorno 3: '1234.57' exemplo 4:
 * number_format(67, 2, ',', '.'); retorno 4: '67,00' exemplo 5:
 * number_format(1000); retorno 5: '1,000' exemplo 6: number_format(67.311, 2);
 * retorno 6: '67.31'
 *
 * @param number
 * @param decimals
 * @param dec_point
 * @param thousands_sep
 * @returns
 */
function number_format(number, decimals, dec_point, thousands_sep) {
	var n = number, prec = decimals;
	n = !isFinite(+n) ? 0 : +n;
	prec = !isFinite(+prec) ? 0 : Math.abs(prec);
	var sep = (typeof thousands_sep == "undefined") ? ',' : thousands_sep;
	var dec = (typeof dec_point == "undefined") ? '.' : dec_point;

	var s = (prec > 0) ? n.toFixed(prec) : Math.round(n).toFixed(prec); // fix
																		// for
																		// IE
																		// parseFloat(0.55).toFixed(0)
																		// = 0;

	var abs = Math.abs(n).toFixed(prec);
	var _, i;

	if (abs >= 1000) {
		_ = abs.split(/\D/);
		i = _[0].length % 3 || 3;

		_[0] = s.slice(0, i + (n < 0))
				+ _[0].slice(i).replace(/(\d{3})/g, sep + '$1');

		s = _.join(dec);
	} else {
		s = s.replace('.', dec);
	}

	return s;
}

function deleteTodasLinhasCarrinho() {
	var tabela = document.getElementById('carrinho');
	var count = tabela.rows.length;
	while (count > 1) {
		tabela.deleteRow(count - 1);
		count--;
	}
	calcularTotal();
}

function fecharModalNovaSolicitacao() {
	$('#modal_novaSolicitacao').modal('hide');
	JANELA_AGUARDE_MENU.hide();
}

function abrirModalNovaSolicitacao() {
	JANELA_AGUARDE_MENU.hide();
	$('#modal_novaSolicitacao').modal('show');
}

$('#tabOrdensDeServico').click(function() {

	executaTelaOrdensDeServico(3);
});

function executaTelaOrdensDeServico(id) {
	document.getElementById("iframeInformacoesContrato").src = "pages/informacoesContrato/informacoesContrato.load?iframe=true";

	// painel principal
	// $(".panel-header").hide();

	// menu principal
	// $("#tst").hide();

	// bot�o de voltar
	// $(".voltar").hide();
	// document.getElementById("iframeInformacoesContrato").style.width ="100%";
	// document.getElementById("iframeInformacoesContrato").style.height
	// ="100%";

}

// function para limitar campos
function limita(campo) {
	var texto = $('#' + campo).val();
	var tamanho = $('#' + campo).val().length;

	if (tamanho > 3000) {
		document.getElementById(campo).value = texto.substr(0, 3000);
		alert(i18n_message("portal.tamanhoMaximoCampo"));
	}
	return true;
}

function ajustarPadraoCitsmart() {

	$('#modal_novaSolicitacao').css({
		'width' : '80%',
		'height' : 'auto',
		'margin-top' : '0% !important',
		'margin-left' : '-40% '
	});

	$('#modal_novaSolicitacao .modal-body').css('max-height', '700px');
	$('#frameNovaSolicitacao').attr('style', 'min-height: 690px !important;');
}

/**
 *
 * @param editarVisualizar
 * @param idSolicitacaoServico
 */
function abreModalOcorrencia(editarVisualizar, idSolicitacaoServico) {

	document.getElementById('frameCadastroOcorrenciaSolicitacao').src = URL_SISTEMA
			+ 'pages/ocorrenciaSolicitacao/ocorrenciaSolicitacao.load?iframe=true&idSolicitacaoServico='
			+ idSolicitacaoServico
			+ '&visualizar='
			+ editarVisualizar
			+ '&resgistrarOcorrencia=' + true;

	$('#modal_ocorrencia').modal('show');

}

/**
 *
 */
function disabledBtnsCategoria() {

	$('#frameCadastroOcorrenciaSolicitacao').contents().find(
			'.btn-disabilitado-categoria').attr('disabled', true);

	$(
			$("#frameCadastroOcorrenciaSolicitacao").contents().find(
					'.btn-disabilitado-categoria')[1]).removeAttr("onclick");
}

/**
 *
 */
function disabledBtnsOrigem() {

	$('#frameCadastroOcorrenciaSolicitacao').contents().find(
			'.btn-disabilitado-origem').attr('disabled', true);

	$(
			$("#frameCadastroOcorrenciaSolicitacao").contents().find(
					'.btn-disabilitado-origem')[1]).removeAttr("onclick");
}

// Cristian: Solicitação 165508 - Movi esta função para o arquivo
// ocorrenciaSolicitacao.js
// function escapeBrTextArea(){
//
// var texto =
// jQuery("#frameCadastroOcorrenciaSolicitacao").contents().find('#informacoesContato').val();
//
// jQuery("#frameCadastroOcorrenciaSolicitacao").contents().find('#informacoesContato').val(texto.replace(/\<br>/g,'\n'));
//
// }

function chamarPesquisaSolicitacoes() {
	document.getElementById('framePesquisaGeralSolicitacao').src = URL_SISTEMA
			+ 'pages/pesquisaSolicitacoesServicosPortal/pesquisaSolicitacoesServicosPortal.load?iframe=true';
	$('#modal_pesquisaGeralSolicitacao').modal('show');
}

var loop;
function mostrarQuandoFiltroEstiverAtivo() {
	if ($("#idSolicitacao").val() == '' && $("#idContrato").val() == '-1'
			&& $("#idTipo").val() == '-1' && $("#idSolicitante").val() == ''
			&& $("#idResponsavelAtual").val() == ''
			&& $("#idGrupoAtual").val() == '-1'
			&& $("#tarefaAtual").val() == '' && $("#situacao").val() == ''
			&& $("#palavraChave").val() == '') {
		$("#idbotaoBuscar").removeClass("mudarCorBotaoFiltros");
		/* clearInterval(loop); */
	} else {
		$("#idbotaoBuscar").addClass("mudarCorBotaoFiltros");
		/*
		 * loop = setInterval(function(){ $( "#idbotaoBuscar" ).animate({
		 * opacity: 1, left: "+=50", color:'red' }, 1000, function() { //
		 * Animation complete. });
		 *  $( "#idbotaoBuscar" ).animate({ opacity: 1, left: "+=50",
		 * color:'black' }, 1000, function() { // Animation complete. });
		 * },2000);
		 */
	}

}

/*
 * Function responsavel por exibir o modal de anexar aquivo ao serviço
 *
 * @author Ezequiel Bispo Nunes @date 2014-12-03
 *
 */
function modalAnexarArquivoServico(el, idServico) {

	ajustarPadraoCitsmart();

	redefinirButtonAdicionar();

	jQuery("#idServicoUp").val(idServico);

	document.formListaServicos.fireEvent("ajustarObjetosSessao");

	setTimeout(function() {
		uploadAnexos.refresh();
	}, 2000);

}

/*
 * Function responsavel por redefinir o botão adicionar do modal de anexos do
 * serviço.
 *
 * @author Ezequiel Bispo Nunes @date 2014-12-03
 *
 */
function redefinirButtonAdicionar() {

	if (jQuery("#btnAdduploadAnexos").attr('onclick') != null
			&& jQuery("#btnAdduploadAnexos").attr('onclick') != '') {

		jQuery("#btnAdduploadAnexos").removeAttr('onclick');

		jQuery("#btnAdduploadAnexos").click(function() {

			document.formListaServicos.fireEvent("validarParametroUpload");

			return false;

		});

	}

}

function continueUpload() {

	var file = document.getElementById("file_uploadAnexos").files[0];

	if (file == null || file == '') {

		alert(i18n_message("uploadAgente.nenhum_arquivo_selecionado"))
	} else {

		validarFileUpload(file);
	}
}

/*
 * Function responsavel por verificar a extensão é JPG, PDF, Word, XLS, TXT ou
 * GIF e se o tamamho do arquivo é menor que 5MB
 *
 * @author Ezequiel Bispo Nunes @date 2014-12-03
 *
 */
function validarFileUpload(file) {

	var mimeTypes = [
			"text/plain",
			"application/msword",
			"application/pdf",
			"application/excel",
			"application/vnd.ms-excel",
			"application/x-excel",
			"application/x-msexcel",
			"image/jpeg",
			"image/png",
			"image/gif",
			"application/vnd.openxmlformats-officedocument.wordprocessingml.document",
			"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" ];

	var tamanho = file.size;

	var limiteBytes = 5242880;

	if (mimeTypes.indexOf(file.type) < 0) {

		alert(i18n_message("upload.mensagemExtensaoInvalida"));

	} else if (tamanho > limiteBytes) {

		alert(i18n_message("upload.mensagemLimiteArquivo"));

	} else {

		upload_uploadAnexos();
	}

}

function controlarCheckAnexos(el) {
	$('#checkAnexarArquivos').prop('checked', $(el).prop('checked'));
}

/**
 *
 */
function ocultarColunaAnexar() {
	var exibirPrecoCarrinhoCompra = $('#exibirPrecoCarrinhoCompra').val();
	if (exibirPrecoCarrinhoCompra === "N")
		$('#carrinho tr').each(function(i, el) {
			$($(el).find('th, td')[4]).hide();
		});
}

/**
 *
 */
function exibirColunaAnexar() {
	var exibirPrecoCarrinhoCompra = $('#exibirPrecoCarrinhoCompra').val();
	if (exibirPrecoCarrinhoCompra === "N")
		$('#carrinho tr').each(function(i, el) {
			$($(el).find('th, td')[4]).show();
		});
}

function ocultarColunaQuestionario() {
	var exibirPrecoCarrinhoCompra = $('#exibirPrecoCarrinhoCompra').val();
	if (exibirPrecoCarrinhoCompra === "N")
		$('#carrinho tr').each(function(i, el) {
			$($(el).find('th, td')[0]).hide();
		});
}

function exibirColunaQuestionario() {
	var exibirPrecoCarrinhoCompra = $('#exibirPrecoCarrinhoCompra').val();
	if (exibirPrecoCarrinhoCompra === "N")
		$('#carrinho tr').each(function(i, el) {
			$($(el).find('th, td')[0]).show();
		});
}

function exibirQuestionarioServicoPortal(idQuestionario, idServicoContrato,
		idServico, respostaObrigatoria) {
	$("#idServicoUp").val(idServico);

	$("#idServicoContratoQuest").val(idServicoContrato);

	$("#idQuestionarioQuest").val(idQuestionario);

	$("#respostaObrigatoria").val(respostaObrigatoria);

	document.formListaServicos.fireEvent("abrirModalQuestionario");

	window.frames["fraInformacoesComplementares"].document.write("");

	window.frames["fraInformacoesComplementares"].document
			.write("<font color='red'><b>"
					+ i18n_message('citcorpore.comum.aguardecarregando')
					+ "</b></font>");
}

function ajustarGridCarrinho() {

	var exibirPrecoCarrinhoCompra = $('#exibirPrecoCarrinhoCompra').val();
	var numeroLinhasQuestionario = 0;
	var numeroLinhaAnexar = 0;
	var idServico;

	$("#carrinho tr").each(function(i, el) {

		if ($(el).find("input[name='existeQuestionario']").val() == 'S') {

			numeroLinhasQuestionario++;
		}

		if ($(el).find("input[name='permiteAnexar']").val()) {

			numeroLinhaAnexar++;
		}
	});

	if (numeroLinhasQuestionario == 0) {
		ocultarColunaQuestionario();

		document.formListaServicos
				.fireEvent("limparObjetoQuestionarioCarrinho");
	}

	if (numeroLinhaAnexar == 0) {
		ocultarColunaAnexar();

		document.formListaServicos.fireEvent("limparObjetoAnexarCarrinho");
	}
}

function gravarQuestionario() {

	if (!validarInformacoesComplementares()) {
		return;
	}

	if (confirm(i18n_message("portal.servico.confirmQuestionario"))) {
		informacoesComplementaresSerialize();
		$("#modal_questionario").modal("hide");
	}

}

function informacoesComplementaresSerialize() {

	var informacoesComplementares_serialize = '';
	var link = $('#fraInformacoesComplementares').attr('src');
	try {
		informacoesComplementares_serialize = window.frames["fraInformacoesComplementares"]
				.getObjetoSerializadoPortal();
	} catch (e) {
	}
}

function validarInformacoesComplementares() {
	if (window.frames["fraInformacoesComplementares"]) {
		try {
			return window.frames["fraInformacoesComplementares"].validar();
		} catch (e) {
			return true;
		}
	} else {
		return true;
	}
}

function cancelarUsuarioContrato() { // Função para só exibir apenas um popup
										// no IE
	parent.$('#modal_novaSolicitacao').modal('hide');
	JANELA_AGUARDE_MENU.hide();
	if (parent.popupIE !== undefined)
		alert(i18n_message("solicitacaoservico.validacao.usuarioContratoNaoLocalizado"));
}

function carregaBaseConhecimento(paginaAtualBaseConhecimento){
	JANELA_AGUARDE_MENU.show();
	if(paginaAtualBaseConhecimento == null || paginaAtualBaseConhecimento == undefined)
		paginaAtualBaseConhecimento = 1;
	document.getElementById("paginaAtualBaseConhecimento").value = paginaAtualBaseConhecimento;
	document.formListaServicos.fireEvent("carregaBaseConhecimento");
}

function carregaFaq(paginaAtualFaq){
	JANELA_AGUARDE_MENU.show();
	if(paginaAtualFaq == null || paginaAtualFaq == undefined)
		paginaAtualFaq = 1;
	document.getElementById("paginaAtualFaq").value = paginaAtualFaq;
	document.formListaServicos.fireEvent("carregaFaq");
}

/**
 * Função de busca de base conhecimento
 *
 * @author thyen.chang
 * @since 09/02/2015 - OPERAÇÃO USAIN BOLT
 */
document.getElementById("campoPesquisaBaseConhecimento").onkeypress = function(e, paginaAtualBaseConhecimento){
	if(!e)
		e = window.event;
	var keyCode = e.keyCode || e.which;
	if(keyCode == '13'){
		JANELA_AGUARDE_MENU.show();
		if(paginaAtualBaseConhecimento == null || paginaAtualBaseConhecimento == undefined)
			paginaAtualBaseConhecimento = 1;
		document.getElementById("paginaAtualBaseConhecimento").value = paginaAtualBaseConhecimento;
		document.getElementById("tituloPesquisaBaseConhecimento").value = document.getElementById("campoPesquisaBaseConhecimento").value;
		document.formListaServicos.fireEvent("pesquisaBaseConhecimento");
		return false;
	}
}

function pesquisaBaseConhecimento(titulo, paginaAtualBaseConhecimento){
	JANELA_AGUARDE_MENU.show();
		if(paginaAtualBaseConhecimento == null || paginaAtualBaseConhecimento == undefined)
			paginaAtualBaseConhecimento = 1;
		document.getElementById("paginaAtualBaseConhecimento").value = paginaAtualBaseConhecimento;
		if(titulo == null || titulo == undefined)
			document.getElementById("tituloPesquisaBaseConhecimento").value = document.getElementById("campoPesquisaBaseConhecimento").value;
		else
			document.getElementById("tituloPesquisaBaseConhecimento").value = titulo;
		document.formListaServicos.fireEvent("pesquisaBaseConhecimento");
		return false;
}

document.getElementById("campoPesquisaFaq").onkeypress = function(e, paginaAtualFaq){
	if(!e)
		e = window.event;
	var keyCode = e.keyCode || e.which;
	if(keyCode == '13'){
		JANELA_AGUARDE_MENU.show();
		if(paginaAtualFaq == null || paginaAtualFaq == undefined)
			paginaAtualFaq = 1;
		document.getElementById("paginaAtualFaq").value = paginaAtualFaq;
		document.getElementById("tituloPesquisaFaq").value = document.getElementById("campoPesquisaFaq").value;
		document.formListaServicos.fireEvent("pesquisaFaq");
		return false;
	}
}

function pesquisaFaq(titulo, paginaAtualFaq){
	JANELA_AGUARDE_MENU.show();
		if(paginaAtualFaq == null || paginaAtualFaq == undefined)
			paginaAtualFaq = 1;
		document.getElementById("paginaAtualFaq").value = paginaAtualFaq;
		if(titulo == null || titulo == undefined)
			document.getElementById("tituloPesquisaFaq").value = document.getElementById("campoPesquisaFaq").value;
		else
			document.getElementById("tituloPesquisaFaq").value = titulo;
		document.formListaServicos.fireEvent("pesquisaFaq");
		return false;
}