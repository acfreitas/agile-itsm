package br.com.centralit.citcorpore.ajaxForms;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.DespesaViagemDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.JustificativaParecerDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.ParceiroDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.RoteiroViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoMovimFinanceiraViagemDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.centralit.citcorpore.negocio.CentroResultadoService;
import br.com.centralit.citcorpore.negocio.CidadesService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.DespesaViagemService;
import br.com.centralit.citcorpore.negocio.FormaPagamentoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.IntegranteViagemService;
import br.com.centralit.citcorpore.negocio.JustificativaParecerService;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.MoedaService;
import br.com.centralit.citcorpore.negocio.ParceiroService;
import br.com.centralit.citcorpore.negocio.ParecerService;
import br.com.centralit.citcorpore.negocio.PermissoesFluxoService;
import br.com.centralit.citcorpore.negocio.ProjetoService;
import br.com.centralit.citcorpore.negocio.RequisicaoViagemService;
import br.com.centralit.citcorpore.negocio.RoteiroViagemService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TipoMovimFinanceiraViagemService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;


@SuppressWarnings({"rawtypes","unused", "unchecked"})
public class DespesaViagem extends AjaxFormAction {
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		DespesaViagemDTO despesaViagemDTO = (DespesaViagemDTO) document.getBean();

		this.preencherComboCentroResultado(document, request, response);
		this.preencherComboProjeto(document, request, response);
		this.preencherComboJustificativa(document, request, response);
		this.restoreTreeIntegrantesViagem(document, request, response, true);
		this.restoreTitulo(document, request, response);
		this.removeBotoesDaTela(document, request, response, usuario, despesaViagemDTO);

		if(despesaViagemDTO.getIdSolicitacaoServico() != null) {
			this.restore(document, request, response);
		}
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		RequisicaoViagemService reqViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);
		DespesaViagemService despesaViagemService = (DespesaViagemService) ServiceLocator.getInstance().getService(DespesaViagemService.class, null);
		IntegranteViagemService integranteViagemService = (IntegranteViagemService) ServiceLocator.getInstance().getService(IntegranteViagemService.class, null);

		DespesaViagemDTO despesaViagemDTO = (DespesaViagemDTO) document.getBean();
		RequisicaoViagemDTO requisicaoViagemDTO = new RequisicaoViagemDTO();
		Collection<IntegranteViagemDTO> colIntegrantes = null;

		requisicaoViagemDTO.setIdSolicitacaoServico(despesaViagemDTO.getIdSolicitacaoServico());
		requisicaoViagemDTO.setIdContrato(despesaViagemDTO.getIdContrato());

		if(despesaViagemDTO.getIdSolicitacaoServico()!=null){
			requisicaoViagemDTO = (RequisicaoViagemDTO) reqViagemService.restore(requisicaoViagemDTO);

			if(requisicaoViagemDTO != null){
				if(requisicaoViagemDTO.getIdCidadeOrigem() != null) {
					requisicaoViagemDTO.setNomeCidadeOrigem(this.recuperaCidade(requisicaoViagemDTO.getIdCidadeOrigem()));
				}

				if(requisicaoViagemDTO.getIdCidadeDestino() != null) {
					requisicaoViagemDTO.setNomeCidadeDestino(this.recuperaCidade(requisicaoViagemDTO.getIdCidadeDestino()));
				}
			}

			colIntegrantes = integranteViagemService.recuperaIntegrantesViagemByIdSolicitacaoEstado(despesaViagemDTO.getIdSolicitacaoServico(), RequisicaoViagemDTO.AGUARDANDO_PLANEJAMENTO);
		}

		HTMLForm form = document.getForm("form");
        form.clear();
        form.setValues(requisicaoViagemDTO);

        document.getElementById("nomeCidadeOrigem").setDisabled(true);
        document.getElementById("nomeCidadeDestino").setDisabled(true);
        document.getElementById("dataInicioViagem").setDisabled(true);
        document.getElementById("dataFimViagem").setDisabled(true);
        document.getElementById("qtdeDias").setDisabled(true);
        document.getElementById("idCentroCusto").setDisabled(true);
        document.getElementById("idProjeto").setDisabled(true);
        document.getElementById("idMotivoViagem").setDisabled(true);
        document.getElementById("descricaoMotivo").setDisabled(true);

        if(colIntegrantes != null) {
        	document.getElementById("colIntegrantesViagem_Serialize").setValue(br.com.citframework.util.WebUtil.serializeObjects(colIntegrantes));
        }
	}

	/**
	 * Retorna o html com a treeview dos integrantes e seu respectivos itens
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @param collapsed
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void restoreTreeIntegrantesViagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, Boolean collapsed) throws ServiceException, Exception {
		RequisicaoViagemService reqViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);
		DespesaViagemService despesaViagemService = (DespesaViagemService) ServiceLocator.getInstance().getService(DespesaViagemService.class, null);
		RoteiroViagemService roteiroViagemService = (RoteiroViagemService) ServiceLocator.getInstance().getService(RoteiroViagemService.class, null);
		TipoMovimFinanceiraViagemService tipoMovimFinanceiraViagemService = (TipoMovimFinanceiraViagemService) ServiceLocator.getInstance().getService(TipoMovimFinanceiraViagemService.class, null);
		CidadesService cidadeService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);
		IntegranteViagemService integranteViagemService = (IntegranteViagemService) ServiceLocator.getInstance().getService(IntegranteViagemService.class, null);

		DespesaViagemDTO despesaViagemDTO = (DespesaViagemDTO) document.getBean();
		RoteiroViagemDTO roteiroViagemDTO = null;
		TipoMovimFinanceiraViagemDTO tipoMovimFinanceiraViagemDTO = null;
		CidadesDTO origem = null;
		CidadesDTO destino = null;

		Collection<IntegranteViagemDTO> colIntegrantes =  integranteViagemService.recuperaIntegrantesViagemByIdSolicitacaoEstado(despesaViagemDTO.getIdSolicitacaoServico(), RequisicaoViagemDTO.AGUARDANDO_PLANEJAMENTO);

		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		DecimalFormat decimal = (DecimalFormat) nf;
		decimal.applyPattern("#,##0.00");

		if(colIntegrantes != null) {
			StringBuilder html = new StringBuilder();

			for(IntegranteViagemDTO integrante: colIntegrantes) {
				roteiroViagemDTO = roteiroViagemService.findByIdIntegrante(integrante.getIdIntegranteViagem());
				Collection<DespesaViagemDTO> colDespesaViagem = despesaViagemService.findDespesasAtivasViagemByIdRoteiro(roteiroViagemDTO.getIdRoteiroViagem());
				origem = (CidadesDTO) ((List) cidadeService.findNomeByIdCidade(roteiroViagemDTO.getOrigem())).get(0);
				destino = (CidadesDTO) ((List) cidadeService.findNomeByIdCidade(roteiroViagemDTO.getDestino())).get(0);

				html.append("<div class='despesa-viagem-item'>");
				html.append("	<ul class='filetree treeview browser'>");
				html.append("		<li>");
				html.append("			<span class='folder'>" + integrante.getNome() +
											" - ida " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, roteiroViagemDTO.getIda(), UtilI18N.getLocale()) +
											" - volta " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, roteiroViagemDTO.getVolta(), UtilI18N.getLocale()) +
											" - " + origem.getNomeCidade() + "/" + origem.getNomeUf() +
											" - " + destino.getNomeCidade() + "/" + destino.getNomeUf() + "</span>");
				html.append("			<ul>");
				html.append("				<li>");
				html.append("					<div class='file'>");
				html.append("						<table class='table_integrante_controle'>");

				if(colDespesaViagem != null && !colDespesaViagem.isEmpty()) {
					Double total = 0.0;
					for(DespesaViagemDTO despesaViagem : colDespesaViagem) {
						tipoMovimFinanceiraViagemDTO = tipoMovimFinanceiraViagemService.findByMovimentacao(Long.parseLong(despesaViagem.getIdTipo().toString()));

						html.append("							<tr>");
						html.append("								<td width='5%'><span class='glyphicons " + tipoMovimFinanceiraViagemDTO.getImagem() + "'><i></i>&nbsp;</span></td>");
						html.append("								<td width='50%'>" + tipoMovimFinanceiraViagemDTO.getClassificacao() + "</td>");
						html.append("								<td width='15%'>" + despesaViagem.getTotalFormatado() + "</td>");
						html.append("								<td width='30%'><a class='btn-editar-item-despesa btn-action btn-success glyphicons edit' href='javascript:;' onclick='editarDespesaViagem(" + despesaViagem.getIdDespesaViagem() + ")'><i></i></a> <a class='btn-action btn-excluir-item-despesa btn-danger glyphicons remove_2' href='javascript:;' onclick='excluirDespesaViagem(" + despesaViagem.getIdDespesaViagem() + ")'><i></i></a></td>");
						html.append("							</tr>");
						total += despesaViagem.getTotal();
					}

					html.append("							<tr>");
					html.append("								<td></td>");
					html.append("								<td class='strong'>Valor total</td>");
					html.append("								<td class='strong' colspan='2'>" + decimal.format(total) + "</td>");
					html.append("							</tr>");
				} else {
					html.append("							<tr>");
					html.append("								<td colspan='3' style='padding: 0;'>Não há itens adicionados para este integrante!</td>");
					html.append("							</tr>");
				}

				html.append("						</table>");
				html.append("					</div><!-- .file -->");
				html.append("				</li>");
				html.append("			</ul>");
				html.append("		</li>");
				html.append("	</ul>");
				html.append("</div><!-- .despesa-viagem-item -->");
			}

			document.getElementById("despesa-viagem-items-container").setInnerHTML(html.toString());

			document.executeScript("$('.browser').treeview({collapsed: " + collapsed + "});");
		}
	}

	/**
	 * Carrega a popup para adicionar item de despesa
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void carregarPopup(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DespesaViagemDTO despesaViagemDTO = (DespesaViagemDTO) document.getBean();

		HTMLForm formItem = document.getForm("formItem");
		formItem.clear();

		if(despesaViagemDTO.getIdSolicitacaoServico() != null) {
			document.getElementById("idSolicitacaoServicoAux").setValue(despesaViagemDTO.getIdSolicitacaoServico().toString());
		}

		this.preencherComboTipoDespesa(document, request, response);
		this.preencherComboMoeda(document, request, response);
		this.preencherComboFormaPagamento(document, request, response);

		// Verifica se é alteração do item
		if(despesaViagemDTO.getIdDespesaViagem() != null) {
			DespesaViagemService despesaViagemService = (DespesaViagemService) ServiceLocator.getInstance().getService(DespesaViagemService.class, null);
			ParceiroService parceiroService = (ParceiroService) ServiceLocator.getInstance().getService(ParceiroService.class, null);

			ParceiroDTO parceiro = new ParceiroDTO();

			despesaViagemDTO = (DespesaViagemDTO) despesaViagemService.restore(despesaViagemDTO);

			if(despesaViagemDTO != null) {
				formItem.setValues(despesaViagemDTO);

				// Tipo da despesa
				document.getSelectById("tipoDespesa").setValue(despesaViagemDTO.getIdTipo().toString());

				// Parceiro
				parceiro.setIdParceiro(despesaViagemDTO.getIdFornecedor());
				parceiro = (ParceiroDTO) parceiroService.restore(parceiro);
				if(parceiro != null) {
					document.getElementById("nomeFornecedor").setValue(parceiro.getNome());
				}

				// Moeda
				if(despesaViagemDTO.getIdFormaPagamento() != null) {
					document.getElementById("idMoeda").setValue(despesaViagemDTO.getIdMoeda().toString());
					document.getSelectById("idMoedaAux").setValue(despesaViagemDTO.getIdMoeda().toString());
				}

				// Forma de pagamento
				if(despesaViagemDTO.getIdFormaPagamento() != null) {
					document.getSelectById("idFormaPagamento").setValue(despesaViagemDTO.getIdFormaPagamento().toString());
				}

				if(despesaViagemDTO.getValidade() != null) {
					document.getElementById("prazoCotacaoAux").setValue(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, despesaViagemDTO.getValidade(), UtilI18N.getLocale()));
					document.getElementById("horaCotacaoAux").setValue(UtilDatas.getHoraHHMM(despesaViagemDTO.getValidade()));
				}

				document.getElementById("valorAdiantamento").setValue(despesaViagemDTO.getTotalFormatado());

				this.tratarValoresTipoMovimentacao(document, request, response, despesaViagemDTO);
				this.carregaIntegranteViagem(document, request, response, despesaViagemDTO);
			}

		} else {

			this.carregaIntegrantesViagem(document, request, response);
		}
	}

	/**
	 * Realiza o calculo da quantidade vezes o valor e retorna para a tela do usuario
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void calcularTotal(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)throws ServiceException,Exception {
		DespesaViagemDTO despesaViagemDTO = (DespesaViagemDTO) document.getBean();

		document.getElementById("valorAdiantamento").setValue(despesaViagemDTO.getTotalFormatado());
	}

	/**
	 * Criado apenas para ser chamado do JS pelo .fireEvent('');
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 * @author renato.jesus
	 */
	public void tratarValoresTipoMovimentacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)throws ServiceException,Exception {
		DespesaViagemDTO despesaViagemDTO = (DespesaViagemDTO) document.getBean();

		this.tratarValoresTipoMovimentacao(document, request, response, despesaViagemDTO);
	}

	/**
	 * Faz o tratamento do tipo da movimentação financeira.
	 * Se a Classificação for igual a diária então o adiantamento = valorUnitário * (quantidade + 1), se a classificação for qualquer outro diferente
	 * então adiantamento = valorUnitário * diária
	 * O tratamento para o adiantamento é feito , ele calcula e seta o valor na tela automaticamente.
	 * Esse método também faz o tratamento para casas decimais
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @param despesaViagemDTO
	 * @throws ServiceException
	 * @throws Exception
	 * @author renato.jesus
	 */
	private void tratarValoresTipoMovimentacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, DespesaViagemDTO despesaViagemDTO)throws ServiceException,Exception {
		if(despesaViagemDTO.getIdTipo() != null && !despesaViagemDTO.getIdTipo().toString().equals("")) {
			TipoMovimFinanceiraViagemService tipoMovimentacaoService = (TipoMovimFinanceiraViagemService) ServiceLocator.getInstance().getService(TipoMovimFinanceiraViagemService.class, null);

			TipoMovimFinanceiraViagemDTO tipoMovimFinanceiraDto = new TipoMovimFinanceiraViagemDTO();

			tipoMovimFinanceiraDto.setIdtipoMovimFinanceiraViagem(despesaViagemDTO.getIdTipo());
			tipoMovimFinanceiraDto = (TipoMovimFinanceiraViagemDTO) tipoMovimentacaoService.restore(tipoMovimFinanceiraDto);

			if(tipoMovimFinanceiraDto != null) {
				if(despesaViagemDTO.getIdDespesaViagem() == null) {
					String classificacao = "";

					classificacao = UtilStrings.removeCaracteresEspeciais(tipoMovimFinanceiraDto.getClassificacao());

					if(despesaViagemDTO.getValor() == null && tipoMovimFinanceiraDto.getValorPadrao() != null) {
						NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
						DecimalFormat decimalFormat = (DecimalFormat) nf;
						decimalFormat.applyPattern("#,##0.00");
						String valorUnit = decimalFormat.format(tipoMovimFinanceiraDto.getValorPadrao());
						despesaViagemDTO.setValor(tipoMovimFinanceiraDto.getValorPadrao());
						document.getElementById("valor").setValue(valorUnit);
					} else {
						document.getElementById("valor").setValue("");
					}

					if(classificacao.equalsIgnoreCase(Enumerados.ClassificacaoMovFinViagem.Diaria.toString())) {
						RequisicaoViagemDTO requisicaoViagemDto = new RequisicaoViagemDTO();
						RequisicaoViagemService requisicaoService =  (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);

						requisicaoViagemDto.setIdSolicitacaoServico(despesaViagemDTO.getIdSolicitacaoServico());

						requisicaoViagemDto = (RequisicaoViagemDTO) requisicaoService.restore(requisicaoViagemDto);

						// Adiciona mais um dia quando a opção selecionada e diária
						requisicaoViagemDto.setQtdeDias(requisicaoViagemDto.getQtdeDias() + 1);

						despesaViagemDTO.setQuantidade(requisicaoViagemDto.getQtdeDias());
						document.getElementById("quantidade").setValue(requisicaoViagemDto.getQtdeDias().toString());
						document.getElementById("quantidade").setReadonly(true);
					} else {
						document.getElementById("quantidade").setValue("1");
						document.getElementById("quantidade").setReadonly(false);
					}

					document.getElementById("valorAdiantamento").setValue(despesaViagemDTO.getTotalFormatado());
				}

				if(tipoMovimFinanceiraDto.getExigeDataHoraCotacao().equalsIgnoreCase("S")) {
					document.executeScript("$('#labelPrazoCotacao, #labelHoraCotacao').addClass('campoObrigatorio');");
					document.getElementById("prazoCotacaoAux").setDisabled(false);
					document.getElementById("horaCotacaoAux").setDisabled(false);
				} else {
					document.executeScript("$('#labelPrazoCotacao, #labelHoraCotacao').removeClass('campoObrigatorio');");
					document.getElementById("prazoCotacaoAux").setDisabled(true);
					document.getElementById("horaCotacaoAux").setDisabled(true);
				}
			}
		} else {
			document.getElementById("quantidade").setValue("");
			document.getElementById("valor").setValue("");
			document.getElementById("valorAdiantamento").setValue("");
			document.getElementById("quantidade").setReadonly(false);
		}
	}

	/**
	 * Carrega o html de todos os integrantes de viagem e seus respectivos checkboxes
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void carregaIntegrantesViagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DespesaViagemDTO despesaViagemDTO = (DespesaViagemDTO) document.getBean();
		RoteiroViagemDTO roteiroViagemDTO = null;
		CidadesDTO origem = null;
		CidadesDTO destino = null;

		IntegranteViagemService integranteViagemService = (IntegranteViagemService) ServiceLocator.getInstance().getService(IntegranteViagemService.class, null);
		DespesaViagemService despesaViagemService = (DespesaViagemService) ServiceLocator.getInstance().getService(DespesaViagemService.class, null);
		RoteiroViagemService roteiroViagemService = (RoteiroViagemService) ServiceLocator.getInstance().getService(RoteiroViagemService.class, null);
		CidadesService cidadeService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);

		Collection<IntegranteViagemDTO> colIntegrantes =  integranteViagemService.recuperaIntegrantesViagemByIdSolicitacaoEstado(despesaViagemDTO.getIdSolicitacaoServico(), RequisicaoViagemDTO.AGUARDANDO_PLANEJAMENTO);

		document.getElementById("integrantes-viagem-heading").setInnerHTML(UtilI18N.internacionaliza(request, "requisicaoViagem.atribuirIntegrante"));

		if(colIntegrantes != null) {
			StringBuilder html = new StringBuilder();

			html.append("<li>");
			html.append("	<label class='inline checkbox'>");
			html.append("		<input id='idIntegranteMarcaTodos' type='checkbox' /> " + UtilI18N.internacionaliza(request, "citcorpore.comum.selecionarTodos"));
			html.append("	</label>");
			html.append("</li>");

			for(IntegranteViagemDTO integrante: colIntegrantes) {
				roteiroViagemDTO = roteiroViagemService.findByIdIntegrante(integrante.getIdIntegranteViagem());
				origem = (CidadesDTO) ((List) cidadeService.findNomeByIdCidade(roteiroViagemDTO.getOrigem())).get(0);
				destino = (CidadesDTO) ((List) cidadeService.findNomeByIdCidade(roteiroViagemDTO.getDestino())).get(0);

				html.append("<li>");
				html.append("	<label class='inline checkbox'>");
				html.append("		<input class='idIntegrate' type='checkbox' value='" + integrante.getIdIntegranteViagem() + "' />" + integrante.getNome() +
										" - ida " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, roteiroViagemDTO.getIda(), UtilI18N.getLocale()) +
										" - volta " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, roteiroViagemDTO.getVolta(), UtilI18N.getLocale()) +
										" - " + origem.getNomeCidade() + "/" + origem.getNomeUf() +
										" - " + destino.getNomeCidade() + "/" + destino.getNomeUf());
				html.append("	</label>");
				html.append("</li>");
			}

			document.getElementById("integrantes-itens").setInnerHTML(html.toString());
		}
	}

	/**
	 * Carrega apenas um integrante de viagem e seu respectivo checkbox
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @param despesaViagemDTO
	 * @throws Exception
	 */
	public void carregaIntegranteViagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, DespesaViagemDTO despesaViagemDTO) throws Exception{
		RoteiroViagemService roteiroViagemService = (RoteiroViagemService) ServiceLocator.getInstance().getService(RoteiroViagemService.class, WebUtil.getUsuarioSistema(request));
		IntegranteViagemService integranteViagemService = (IntegranteViagemService) ServiceLocator.getInstance().getService(IntegranteViagemService.class, WebUtil.getUsuarioSistema(request));
		CidadesService cidadeService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, WebUtil.getUsuarioSistema(request));

		RoteiroViagemDTO roteiroViagemDTO = new RoteiroViagemDTO();
		IntegranteViagemDTO integranteViagemDTO = null;
		CidadesDTO origem = null;
		CidadesDTO destino = null;

		roteiroViagemDTO.setIdRoteiroViagem(despesaViagemDTO.getIdRoteiro());
		roteiroViagemDTO = (RoteiroViagemDTO) roteiroViagemService.restore(roteiroViagemDTO);

		if(roteiroViagemDTO != null) {
			integranteViagemDTO = new IntegranteViagemDTO();

			integranteViagemDTO.setIdIntegranteViagem(roteiroViagemDTO.getIdIntegrante());
			integranteViagemDTO = (IntegranteViagemDTO) integranteViagemService.restore(integranteViagemDTO);

			origem = (CidadesDTO) ((List) cidadeService.findNomeByIdCidade(roteiroViagemDTO.getOrigem())).get(0);
			destino = (CidadesDTO) ((List) cidadeService.findNomeByIdCidade(roteiroViagemDTO.getDestino())).get(0);

			document.getElementById("integrantes-viagem-heading").setInnerHTML("Atribuir ao integrante");

			StringBuilder html = new StringBuilder();

			html.append("<li>");
			html.append("	<label class='inline checkbox'>");
			html.append("		<input class='idIntegrate' type='checkbox' checked='checked' disabled='disabled' value='" + integranteViagemDTO.getIdIntegranteViagem() + "' />" + integranteViagemDTO.getNome() +
									" - ida " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, roteiroViagemDTO.getIda(), UtilI18N.getLocale()) +
									" - volta " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, roteiroViagemDTO.getVolta(), UtilI18N.getLocale()) +
									" - " + origem.getNomeCidade() + "/" + origem.getNomeUf() +
									" - " + destino.getNomeCidade() + "/" + destino.getNomeUf());
			html.append("	</label>");
			html.append("</li>");

			document.getElementById("integrantes-itens").setInnerHTML(html.toString());
		}
	}

	/**
	 * Armazena o item de despesa e configura-o para cada integrante selecionado
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void adicionarDespesaViagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RoteiroViagemService roteiroViagemService = (RoteiroViagemService) ServiceLocator.getInstance().getService(RoteiroViagemService.class, null);
		DespesaViagemService despesaViagemService = (DespesaViagemService) ServiceLocator.getInstance().getService(DespesaViagemService.class, null);

		DespesaViagemDTO despesaViagemDTO = (DespesaViagemDTO) document.getBean();

		if(despesaViagemDTO.getPrazoCotacaoAux() != null && despesaViagemDTO.getHoraCotacaoAux() != null) {
			despesaViagemDTO.setValidade(Timestamp.valueOf(despesaViagemDTO.getPrazoCotacaoAux() + " " + Time.valueOf(despesaViagemDTO.getHoraCotacaoAux() + ":00")));
		}

		if(despesaViagemDTO.getIdDespesaViagem() == null || despesaViagemDTO.getIdDespesaViagem().equals("")) {
			despesaViagemDTO.setDataInicio(UtilDatas.getDataAtual());

			RoteiroViagemDTO roteiroViagemDTO = null;

			Collection<IntegranteViagemDTO> colIntegrantesViagem = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(IntegranteViagemDTO.class, "colIntegrantesViagem_SerializeAux", request);
			if(colIntegrantesViagem != null && !colIntegrantesViagem.isEmpty()) {
				for(IntegranteViagemDTO integranteViagemDTO : colIntegrantesViagem) {
					roteiroViagemDTO = roteiroViagemService.findByIdIntegrante(integranteViagemDTO.getIdIntegranteViagem());
					despesaViagemDTO.setIdRoteiro(roteiroViagemDTO.getIdRoteiroViagem());
					if(integranteViagemDTO.getRemarcacao().equalsIgnoreCase("S")){
						despesaViagemDTO.setOriginal("N");
					}else{
						despesaViagemDTO.setOriginal("S");
					}
					despesaViagemService.create(despesaViagemDTO);
				}
			}
		} else {
			despesaViagemService.update(despesaViagemDTO);
		}

		HTMLForm formItem = document.getForm("formItem");
		formItem.clear();

		this.restoreTreeIntegrantesViagem(document, request, response, false);
	}

	/**
	 * Exclui o item de despesa conforme dados passados
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void excluirDespesaViagem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DespesaViagemService despesaViagemService = (DespesaViagemService) ServiceLocator.getInstance().getService(DespesaViagemService.class, null);

		DespesaViagemDTO despesaViagemDTO = (DespesaViagemDTO) document.getBean();

		if(despesaViagemDTO != null && despesaViagemDTO.getIdDespesaViagem() != null) {
			despesaViagemService.delete(despesaViagemDTO);

			this.restoreTreeIntegrantesViagem(document, request, response, false);
		}
	}

	/**
	 * Preenche a combo de 'Centro Resultado' do formulário HTML
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author renato.jesus
	 */
	public void preencherComboCentroResultado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, WebUtil.getUsuarioSistema(request));

        HTMLSelect comboCentroCusto = (HTMLSelect) document.getSelectById("idCentroCusto");

        comboCentroCusto.removeAllOptions();
        comboCentroCusto.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

        Collection colCCusto = centroResultadoService.listPermiteRequisicaoProduto();
        if(colCCusto != null && !colCCusto.isEmpty()){
        	 comboCentroCusto.addOptions(colCCusto, "idCentroResultado", "nomeHierarquizado", null);
        }

	}

	/**
	 * Preenche a combo de 'Projeto' do formulário HTML
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @param requisicaoViagemDto
	 * @throws Exception
	 * @author renato.jesus
	 */
	public void preencherComboProjeto(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		DespesaViagemDTO despesaViagemDTO = (DespesaViagemDTO) document.getBean();

		HTMLSelect comboProjeto = (HTMLSelect) document.getSelectById("idProjeto");

		comboProjeto.removeAllOptions();
		comboProjeto.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		if (despesaViagemDTO.getIdContrato() != null) {
		    ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, WebUtil.getUsuarioSistema(request));
		    ContratoDTO contratoDto = new ContratoDTO();
		    contratoDto.setIdContrato(despesaViagemDTO.getIdContrato());
		    contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
		    if (contratoDto != null) {
		        ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, WebUtil.getUsuarioSistema(request));
		        Collection colProjetos = projetoService.listHierarquia(contratoDto.getIdCliente(), true);
		        if(colProjetos != null && !colProjetos.isEmpty())
		            comboProjeto.addOptions(colProjetos, "idProjeto", "nomeHierarquizado", null);
		    }
		}
	}

	/**
	 * Preenche combo de 'justificativa solicitação'.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author renato.jesus
	 */
	public void preencherComboJustificativa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JustificativaSolicitacaoService justificativaSolicitacaoService = (JustificativaSolicitacaoService)ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class, null);

		Collection<JustificativaSolicitacaoDTO> colJustificativas = justificativaSolicitacaoService.listAtivasParaViagem();

		HTMLSelect comboJustificativa = (HTMLSelect) document.getSelectById("idMotivoViagem");
		comboJustificativa.removeAllOptions();

		comboJustificativa.removeAllOptions();
		comboJustificativa.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		if (colJustificativas != null){
			comboJustificativa.addOptions(colJustificativas, "idJustificativa", "descricaoJustificativa", null);
		}
	}

	/**
	 * Preenche combo de 'Tipo de despesa'.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author renato.jesus
	 */
	public void preencherComboTipoDespesa(DocumentHTML document,HttpServletRequest request, HttpServletResponse response)throws Exception{
		TipoMovimFinanceiraViagemService tipoService = (TipoMovimFinanceiraViagemService) ServiceLocator.getInstance().getService(TipoMovimFinanceiraViagemService.class, null);

		String classificacao = "";
		List<TipoMovimFinanceiraViagemDTO> listaTipoMovimentacaoFinanceiraViagem = new ArrayList<TipoMovimFinanceiraViagemDTO>();

		HTMLSelect comboDespesa = document.getSelectById("tipoDespesa");

		comboDespesa.removeAllOptions();
		comboDespesa.addOption("", "" + UtilI18N.internacionaliza(request, "citcorpore.comum.selecione") + "");

		for(Enumerados.ClassificacaoMovFinViagem classificacaoItem : Enumerados.ClassificacaoMovFinViagem.values()){
			classificacao = UtilStrings.removeCaracteresEspeciais(classificacaoItem.getDescricao());

			listaTipoMovimentacaoFinanceiraViagem =  tipoService.listByClassificacao(classificacao);

			for(TipoMovimFinanceiraViagemDTO tipoMov : listaTipoMovimentacaoFinanceiraViagem) {
				comboDespesa.addOption(tipoMov.getIdtipoMovimFinanceiraViagem().toString(), classificacao.trim() + " (" + (tipoMov.getNome()).trim() + ")");
			}
		}
	}

	/**
	 * Preenche combo de 'Moeda'.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author renato.jesus
	 */
	public void preencherComboMoeda(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		MoedaService moedaService = (MoedaService) ServiceLocator.getInstance().getService(MoedaService.class, null);
        HTMLSelect comboMoeda = (HTMLSelect) document.getSelectById("idMoedaAux");

        comboMoeda.removeAllOptions();
        comboMoeda.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

        Collection colMoedas = moedaService.findAllAtivos();
        if(colMoedas != null && !colMoedas.isEmpty()){
        	comboMoeda.addOptions(colMoedas, "idMoeda", "nomeMoeda", null);
        }

        document.getElementById("idMoeda").setValue("1");

        comboMoeda.setValue("1");
        comboMoeda.setDisabled(true);
	}

	/**
	 * Preenche combo de 'Forma de pagamento'.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author renato.jesus
	 */
	public void preencherComboFormaPagamento(DocumentHTML document,HttpServletRequest request, HttpServletResponse response)throws Exception{
		FormaPagamentoService formaPagamentoService = (FormaPagamentoService) ServiceLocator.getInstance().getService(FormaPagamentoService.class, null);

		HTMLSelect comboFormaPagamento = document.getSelectById("idFormaPagamento");

		comboFormaPagamento.removeAllOptions();
		comboFormaPagamento.addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");

		Collection colTipoMovimentacaoFinanceira = formaPagamentoService.list();
		if(colTipoMovimentacaoFinanceira!=null){
			comboFormaPagamento.addOptions(colTipoMovimentacaoFinanceira, "idFormaPagamento", "nomeFormaPagamento", null);
		}
	}


	@Override
	public Class getBeanClass() {
		return DespesaViagemDTO.class;
	}

	/**
	 * Retorna a cidade conforme idcidade passado
	 *
	 * @param idCidade
	 * @return
	 * @throws Exception
	 * @author renato.jesus
	 */
	public String recuperaCidade(Integer idCidade) throws Exception {
		CidadesDTO cidadeDto  = new CidadesDTO();

		CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);

		if (idCidade != null) {
			cidadeDto = (CidadesDTO) cidadesService.findCidadeUF(idCidade);
			return cidadeDto.getNomeCidade() + " - " + cidadeDto.getNomeUf();
		}

		return null;
	}

	/**
	 * Remove o botão adicionar itens caso o usuario não tenha permissão
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author renato.jesus
	 */
	private void removeBotoesDaTela(DocumentHTML document,HttpServletRequest request, HttpServletResponse response, UsuarioDTO usuario, DespesaViagemDTO despesaViagemDTO) throws Exception {
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

		SolicitacaoServicoDTO solicitacaoServicoDTO = new SolicitacaoServicoDTO();

		solicitacaoServicoDTO.setIdSolicitacaoServico(despesaViagemDTO.getIdSolicitacaoServico());
		solicitacaoServicoDTO = (SolicitacaoServicoDTO) solicitacaoServicoService.restore(solicitacaoServicoDTO);
		FluxoDTO fluxoDTO = solicitacaoServicoService.recuperaFluxo(solicitacaoServicoDTO);

		PermissoesFluxoService permissoesFluxoService = (PermissoesFluxoService) ServiceLocator.getInstance().getService(PermissoesFluxoService.class, null);
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		UsuarioDTO usuarioDTO = (UsuarioDTO) (new UsuarioDao()).restore(usuario);

		usuarioDTO.setColGrupos(grupoService.getGruposByEmpregado(usuarioDTO.getIdEmpregado()));

		PermissoesFluxoDTO permissoesFluxoDTO = permissoesFluxoService.findByUsuarioAndFluxo(usuarioDTO, fluxoDTO);

		if(permissoesFluxoDTO == null || (permissoesFluxoDTO != null && !permissoesFluxoDTO.getExecutar().equalsIgnoreCase("S"))) {
			document.executeScript("$('#add_itens').remove();$('.btn-editar-item-despesa').remove();$('.btn-excluir-item-despesa').remove();");
		}
	}


	/**
	 * Restaura a justificativa informada quando a autorização é negada.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void restoreTitulo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		RequisicaoViagemService reqViagemService = (RequisicaoViagemService) ServiceLocator.getInstance().getService(RequisicaoViagemService.class, null);
		ParecerService parecerService = (ParecerService) ServiceLocator.getInstance().getService(ParecerService.class, null);
		JustificativaParecerService justificativaParecerService = (JustificativaParecerService) ServiceLocator.getInstance().getService(JustificativaParecerService.class, null);
		ParecerDTO parecerDTO = new ParecerDTO();
		JustificativaParecerDTO justificativaParecerDTO = new JustificativaParecerDTO();

		DespesaViagemDTO despesaViagemDTO = (DespesaViagemDTO) document.getBean();
		RequisicaoViagemDTO requisicaoViagemDTO = reqViagemService.recuperaRequisicaoPelaSolicitacao(despesaViagemDTO.getIdSolicitacaoServico());

		if(requisicaoViagemDTO != null && requisicaoViagemDTO.getIdAprovacao() != null){
			parecerDTO.setIdParecer(requisicaoViagemDTO.getIdAprovacao());
			parecerDTO = (ParecerDTO) parecerService.restore(parecerDTO);
			if(parecerDTO != null && parecerDTO.getAprovado().equalsIgnoreCase("N")){
				document.getElementById("titulo").setValue(UtilI18N.internacionaliza(request, "requisicaoViagem.replanejamentoReservasViagem"));
				justificativaParecerDTO.setIdJustificativa(parecerDTO.getIdJustificativa());
				justificativaParecerDTO = (JustificativaParecerDTO) justificativaParecerService.restore(justificativaParecerDTO);
				StringBuilder html = new StringBuilder();

				html.append("<label><b>"+UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa")+":</b> ");
				html.append(justificativaParecerDTO.getDescricaoJustificativa());
				if(parecerDTO.getComplementoJustificativa() != null && !parecerDTO.getComplementoJustificativa().equalsIgnoreCase("")){
					html.append(" ("+parecerDTO.getComplementoJustificativa()+")");
				}
				html.append("</label>");
				document.executeScript("$('#divNaoAprovada').show()");
				document.getElementById("naoAprovada").setInnerHTML(html.toString());

			}else{
				document.getElementById("titulo").setValue(UtilI18N.internacionaliza(request, "requisicaoViagem.planejamentoReservasViagem"));
				document.executeScript("$('#divNaoAprovada').hide()");
			}
		}else{
			document.getElementById("titulo").setValue(UtilI18N.internacionaliza(request, "requisicaoViagem.planejamentoReservasViagem"));
			document.executeScript("$('#divNaoAprovada').hide()");
		}
	}
}
