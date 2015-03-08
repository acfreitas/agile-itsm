package br.com.centralit.citcorpore.ajaxForms;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.GerenciamentoServicosDTO;
import br.com.centralit.citcorpore.bean.ItemCfgSolicitacaoServDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.OrigemAtendimentoDTO;
import br.com.centralit.citcorpore.bean.ParamRecuperacaoTarefasDTO;
import br.com.centralit.citcorpore.bean.PrioridadeDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ExecucaoSolicitacaoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.ItemCfgSolicitacaoServService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.OrigemAtendimentoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.core.Page;
import br.com.citframework.integracao.core.PageRequest;
import br.com.citframework.integracao.core.Pageable;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

/**
 * Controller da Página GerenciamentoServicos.jsp.
 */
@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
public class GerenciamentoServicos extends AjaxFormAction {

	private GerenciamentoServicosDTO gerenciamentoServicosDTO = new GerenciamentoServicosDTO();

	private Set<ContratoDTO> hashContratos = new HashSet<ContratoDTO>();

	private ContratoService contratoService;

	private SolicitacaoServicoService solicitacaoServicoService;

	private ExecucaoSolicitacaoService execucaoSolicitacaoService;

	@Override
	public Class<GerenciamentoServicosDTO> getBeanClass() {
		return GerenciamentoServicosDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
	}

	/**
	 * Renderiza os Gráficos da Guia Gráficos da Tela de Gerenciamento de Serviços.
	 *
	 * @param document
	 *            - DocumentHTML
	 * @param request
	 *            - HttpServletRequest
	 * @param response
	 *            - HttpServletResponse
	 * @throws Exception
	 */
	public void renderizarGraficos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		this.setGerenciamentoServicosDTO((GerenciamentoServicosDTO) document.getBean());
		GerenciamentoServicosDTO gerenciamentoServicoDto = this.getGerenciamentoServicosDTO();
		List<TarefaFluxoDTO> colecao = (List<TarefaFluxoDTO>) listarTarefas(null, null, usuario, request, true, gerenciamentoServicoDto);

		HashMap<String, Object> mapGrupoAtual = new HashMap<String, Object>();
		document.getSelectById("idGrupoAtual").removeAllOptions();
		document.getSelectById("idGrupoAtual").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idGrupoAtual").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.sematribuicao") + " --");

		for (TarefaFluxoDTO tarefaDto : colecao) {
			SolicitacaoServicoDTO dtoSol = (SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto();
			if (dtoSol.getGrupoAtual() != null && StringUtils.isNotBlank(dtoSol.getGrupoAtual())) {
				if (!mapGrupoAtual.containsKey(dtoSol.getIdGrupoAtual().toString())) {
					String idGrupo = UtilStrings.removeCaracteresEspeciais(dtoSol.getIdGrupoAtual().toString());
					String nome = UtilStrings.removeCaracteresEspeciais(dtoSol.getGrupoAtual());
					mapGrupoAtual.put(UtilStrings.removeCaracteresEspeciais(dtoSol.getIdGrupoAtual().toString()), dtoSol.getGrupoAtual());
					document.getSelectById("idGrupoAtual").addOption(idGrupo, nome);
				}
			}
		}

		String tarefasStr = serializaTarefas(colecao, request);
		document.executeScript("exibirGraficos('" + tarefasStr + "');");
	}

	/**
	 * Retorna a lista de tarefas do fluxo juntamente com as solicitações de serviços.
	 *
	 * @param itensPorPagina
	 *            - Quantidade de registros por página.
	 * @param paginaSelecionada
	 *            - Página selecionada.
	 * @param usuario
	 *            - usuário logado.
	 * @param request
	 *            - HttpServletRequest
	 * @param grafico
	 *            - boolean (true - tela de gráficos)
	 * @param gerenciamento
	 *            - GerenciamentoServicosDTO
	 * @return Collection<TarefaFluxoDTO>
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 23.10.2013 - ás 20:30
	 */
	public Collection<TarefaFluxoDTO> listarTarefas(Integer itensPorPagina, Integer paginaSelecionada, UsuarioDTO usuario, HttpServletRequest request, boolean grafico,
			GerenciamentoServicosDTO gerenciamento) throws Exception {
		ExecucaoSolicitacaoService execucaoSolicitacaoService = getExecucaoSolicitacaoService();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);

		List<TarefaFluxoDTO> colTarefas = null;
		List<SolicitacaoServicoDTO> listSolicitacaoServico = new ArrayList<SolicitacaoServicoDTO>();

		if (paginaSelecionada == null) {
			paginaSelecionada = 1;
		}

		UsuarioDTO usuarioResponsavelDto = new UsuarioDTO();

		if (this.getGerenciamentoServicosDTO().getIdResponsavelAtual() != null && this.getGerenciamentoServicosDTO().getIdResponsavelAtual().intValue() != -1) {
			UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

			usuarioResponsavelDto = usuarioService.restoreByIdEmpregado(this.getGerenciamentoServicosDTO().getIdResponsavelAtual());
		} else {
			/* Criado para permitir filtrar as solicitações que não possuem responsável. valdoilo.damasceno */
			if (this.getGerenciamentoServicosDTO().getIdResponsavelAtual() != null && this.getGerenciamentoServicosDTO().getIdResponsavelAtual().intValue() == -1) {
				usuarioResponsavelDto.setIdUsuario(-1);
			}
		}

		if (itensPorPagina != null) {
			if (this.getGerenciamentoServicosDTO() != null && this.getGerenciamentoServicosDTO().getIdContrato() == null && this.getGerenciamentoServicosDTO().getIdTipo() == null
					&& gerenciamento != null) {
				this.setGerenciamentoServicosDTO(gerenciamento);
			}
		}

		String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");

		if (COLABORADORES_VINC_CONTRATOS == null) {
			COLABORADORES_VINC_CONTRATOS = "N";
		}

		Collection<ContratoDTO> listContratoUsuarioLogado = null;

		if (COLABORADORES_VINC_CONTRATOS != null && COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {
			listContratoUsuarioLogado = contratoService.findAtivosByIdEmpregado(usuario.getIdEmpregado());
		} else {
			listContratoUsuarioLogado = contratoService.listAtivos();
		}

		if (!grafico) {
			/** Chamada do método recuperaTarefas otimizado. Operação Usain Bolt - 27.01.2015 - carlos.santos */
			final ParamRecuperacaoTarefasDTO param = new ParamRecuperacaoTarefasDTO(usuario.getLogin(), gerenciamento, listContratoUsuarioLogado);
			final Pageable pageable = new PageRequest(paginaSelecionada - 1, itensPorPagina);
			final Page<TarefaFluxoDTO> resultPage = execucaoSolicitacaoService.recuperaTarefas(param, pageable);
			colTarefas = resultPage.getContent();
		} else {
			colTarefas = execucaoSolicitacaoService.recuperaTarefas(usuario.getLogin(), this.getGerenciamentoServicosDTO(), listContratoUsuarioLogado);
		}

		List<TarefaFluxoDTO> colTarefasFiltradasFinal = new ArrayList<>();

		boolean asc = true;
		for (TarefaFluxoDTO tarefaDto : colTarefas) {
			SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto();
			listSolicitacaoServico.add(solicitacaoServicoDto);
			solicitacaoServicoDto.setDataHoraLimiteToString(""); // Apenas forca atualizacao
			solicitacaoServicoDto.setDataHoraSolicitacaoToString(""); // Apenas forca atualizacao
			solicitacaoServicoDto.setDescricaoSemFormatacao(Util.tratarAspasSimples(UtilStrings.nullToNaoDisponivel(solicitacaoServicoDto.getDescricaoSemFormatacao())));
			solicitacaoServicoDto.setDescricaoForTitle(Util.tratarAspasSimples(solicitacaoServicoDto.getDescricao()));
			solicitacaoServicoDto.setDescricao(Util.tratarAspasSimples(solicitacaoServicoDto.getDescricao()));// tratamento para Aspas Simples
			solicitacaoServicoDto.setDescricao(Util.converterHtmlQuadro(solicitacaoServicoDto.getDescricao())); // tratamento para codigo html
			solicitacaoServicoDto.setDescricao(Util.converterCaracteresInadequadosParaTexto(solicitacaoServicoDto.getDescricao())); // tratamento de caracteres inadequados para texto
			solicitacaoServicoDto.setResposta("");
			solicitacaoServicoDto.setDetalhamentoCausa("");
			if (solicitacaoServicoDto.getSlaACombinar() == null) {
				solicitacaoServicoDto.setSlaACombinar("N");
			}
			int prazoHH = 0;
			int prazoMM = 0;

			if (solicitacaoServicoDto.getPrazoHH() != null) {
				prazoHH = solicitacaoServicoDto.getPrazoHH();
			}
			if (solicitacaoServicoDto.getPrazoMM() != null) {
				prazoMM = solicitacaoServicoDto.getPrazoMM();
			}
			if (prazoHH == 0 && prazoMM == 0) {
				solicitacaoServicoDto.setSlaACombinar("S");
				solicitacaoServicoDto.setAtrasoSLA(0);
				solicitacaoServicoDto.setAtrasoSLAStr("");
				solicitacaoServicoDto.setDataHoraLimiteStr("");
			}
			if (solicitacaoServicoDto.getSlaACombinar().equalsIgnoreCase("S")) {
				solicitacaoServicoDto.setDataHoraLimite(null);
				solicitacaoServicoDto.setAtrasoSLA(0);
				solicitacaoServicoDto.setAtrasoSLAStr("");
				if (asc) {
					tarefaDto.setDataHoraLimite(new Timestamp(UtilDatas.alteraData(UtilDatas.getDataAtual(), 10, Calendar.YEAR).getTime()));
				} else {
					tarefaDto.setDataHoraLimite(new Timestamp(UtilDatas.alteraData(UtilDatas.getDataAtual(), -10, Calendar.YEAR).getTime()));
				}
			}
			if (solicitacaoServicoDto.getSituacao().equalsIgnoreCase(br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico.Suspensa.name())) {
				solicitacaoServicoDto.setDataHoraLimite(null);
				solicitacaoServicoDto.setAtrasoSLA(0);
				solicitacaoServicoDto.setAtrasoSLAStr("");
				if (asc) {
					tarefaDto.setDataHoraLimite(new Timestamp(UtilDatas.alteraData(UtilDatas.getDataAtual(), 10, Calendar.YEAR).getTime()));
				} else {
					tarefaDto.setDataHoraLimite(new Timestamp(UtilDatas.alteraData(UtilDatas.getDataAtual(), -10, Calendar.YEAR).getTime()));
				}
			}

			if (solicitacaoServicoDto.getSolicitanteUnidade() != null && StringUtils.isNotBlank(solicitacaoServicoDto.getSolicitanteUnidade())) {
				solicitacaoServicoDto.setSolicitante(""); // pra nao enviar no JSON
			} else {
				if (solicitacaoServicoDto.getSolicitante() != null && StringUtils.isNotBlank(solicitacaoServicoDto.getSolicitante())) {
					solicitacaoServicoDto.setSolicitanteUnidade(Util.tratarAspasSimples(solicitacaoServicoDto.getSolicitante()));
				} else {
					solicitacaoServicoDto.setSolicitanteUnidade(UtilI18N.internacionaliza(request, "citcorpore.comum.naoInformado"));
				}
			}

			/**
			 * A Filtragem de Responsável e Tarefa Atual são realizadas aqui. As demais são realizadas no BD.
			 */

			/** Quando os dois filtros foram selecionados */
			if ((usuarioResponsavelDto != null && usuarioResponsavelDto.getIdUsuario() != null && StringUtils.isNotBlank(usuarioResponsavelDto.getIdUsuario().toString()))
					&& (this.getGerenciamentoServicosDTO().getTarefaAtual() != null && StringUtils.isNotBlank(this.getGerenciamentoServicosDTO().getTarefaAtual()))) {

				if ((tarefaDto.getIdResponsavelAtual() != null && StringUtils.isNotBlank(tarefaDto.getIdResponsavelAtual().toString()))
						&& (tarefaDto.getElementoFluxoDto() != null && tarefaDto.getElementoFluxoDto().getDocumentacao() != null && StringUtils.isNotBlank(tarefaDto.getElementoFluxoDto()
								.getDocumentacao()))
						&& (usuarioResponsavelDto.getIdUsuario().toString().equalsIgnoreCase(tarefaDto.getIdResponsavelAtual().toString()) && this.getGerenciamentoServicosDTO().getTarefaAtual()
								.toString().equalsIgnoreCase(tarefaDto.getElementoFluxoDto().getDocumentacao().toString()))) {

					colTarefasFiltradasFinal.add(tarefaDto);

				} else {
					/* Criado para permitir filtrar as solicitações que não possuem responsável. valdoilo.damasceno */
					if ((tarefaDto.getIdResponsavelAtual() == null || StringUtils.isBlank(tarefaDto.getIdResponsavelAtual().toString()))
							&& (tarefaDto.getElementoFluxoDto() != null && tarefaDto.getElementoFluxoDto().getDocumentacao() != null && StringUtils.isNotBlank(tarefaDto.getElementoFluxoDto()
									.getDocumentacao()))
							&& (usuarioResponsavelDto.getIdUsuario().intValue() == -1 && this.getGerenciamentoServicosDTO().getTarefaAtual().toString()
									.equalsIgnoreCase(tarefaDto.getElementoFluxoDto().getDocumentacao().toString()))) {

						colTarefasFiltradasFinal.add(tarefaDto);

					}
				}
			} else
			/** Quando um ou o outro foi selecionado. */
			if ((usuarioResponsavelDto != null && usuarioResponsavelDto.getIdUsuario() != null && StringUtils.isNotBlank(usuarioResponsavelDto.getIdUsuario().toString()))
					|| (this.getGerenciamentoServicosDTO().getTarefaAtual() != null && StringUtils.isNotBlank(this.getGerenciamentoServicosDTO().getTarefaAtual()))) {

				/** Quando apenas o responsável foi selecionado. */
				if (usuarioResponsavelDto != null && usuarioResponsavelDto.getIdUsuario() != null && tarefaDto.getIdResponsavelAtual() != null
						&& StringUtils.isNotBlank(tarefaDto.getIdResponsavelAtual().toString())
						&& usuarioResponsavelDto.getIdUsuario().toString().equalsIgnoreCase(tarefaDto.getIdResponsavelAtual().toString())) {

					colTarefasFiltradasFinal.add(tarefaDto);

				} else {
					/* Criado para permitir filtrar as solicitações que não possuem responsável. valdoilo.damasceno */
					if (usuarioResponsavelDto != null && usuarioResponsavelDto.getIdUsuario() != null && usuarioResponsavelDto.getIdUsuario().intValue() == -1
							&& (tarefaDto.getIdResponsavelAtual() == null || StringUtils.isBlank(tarefaDto.getIdResponsavelAtual().toString()))) {

						colTarefasFiltradasFinal.add(tarefaDto);

					} else

					/** Quando apenas a tarefa atual foi selecionada. */
					if ((this.getGerenciamentoServicosDTO().getTarefaAtual() != null && StringUtils.isNotBlank(this.getGerenciamentoServicosDTO().getTarefaAtual()))
							&& (tarefaDto.getElementoFluxoDto() != null && tarefaDto.getElementoFluxoDto().getDocumentacao() != null && StringUtils.isNotBlank(tarefaDto.getElementoFluxoDto()
									.getDocumentacao()))
							&& this.getGerenciamentoServicosDTO().getTarefaAtual().toString().equalsIgnoreCase(tarefaDto.getElementoFluxoDto().getDocumentacao().toString())) {

						colTarefasFiltradasFinal.add(tarefaDto);

					}
				}

			} else {
				colTarefasFiltradasFinal.add(tarefaDto);
			}
		}
		/**
		 * Motivo: Adicionado para corrigir nullPoinerException quando renderizava gráficos Autor: flavio.santana Data/Hora: 13/11/2013
		 */
		if (!grafico) {
			/**
			 * Utilizado apenas quando foi selecionado o filtro de Responsável Atual ou Tarefa atual. Obtém o número total de páginas e filtro a lista para exibir apenas o número de ítens selecionado.
			 */
			if ((this.getGerenciamentoServicosDTO() != null && usuarioResponsavelDto != null && usuarioResponsavelDto.getIdUsuario() != null)
					|| (this.getGerenciamentoServicosDTO() != null && this.getGerenciamentoServicosDTO().getTarefaAtual() != null && StringUtils.isNotBlank(this.getGerenciamentoServicosDTO()
							.getTarefaAtual()))) {

				int start = (paginaSelecionada * itensPorPagina) - itensPorPagina;
				int end = start + itensPorPagina;

				if (end >= colTarefasFiltradasFinal.size()) {
					end = colTarefasFiltradasFinal.size();
				}

				this.getGerenciamentoServicosDTO().setTotalPaginas(colTarefasFiltradasFinal.size());

				colTarefasFiltradasFinal = colTarefasFiltradasFinal.subList(((paginaSelecionada * itensPorPagina) - itensPorPagina), end);
			}
		}

		return colTarefasFiltradasFinal;
	}

	/**
	 * Responsável por renderizar a listagem de Solicitações na tela de Gerenciamento de Serviços.
	 *
	 * @param sb
	 * @param request
	 * @param itensPorPagina
	 * @param paginaSelecionada
	 * @param flag
	 * @param tipoLista
	 * @throws Exception
	 */
	public void renderizarLista(StringBuilder sb, HttpServletRequest request, Integer itensPorPagina, Integer paginaSelecionada, boolean flag, Integer tipoLista) throws Exception {
		/**
		 * Motivo: Adiocinar mais informações na listagem das solicitações São elas: Data de Criação, Criado por e Contrato
		 *
		 * @author flavio.santana
		 * @since 23/10/2013 14:00
		 */
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		GerenciamentoServicosDTO gerenciamentoServicoDto = this.getGerenciamentoServicosDTO();

		String language = (String) request.getSession().getAttribute("locale");

		Collection<TarefaFluxoDTO> listTarefaSolicitacaoServico = this.listarTarefas(itensPorPagina, paginaSelecionada, usuario, request, false, gerenciamentoServicoDto);

		String locale = (String) request.getSession().getAttribute("locale");
		if (locale == null) {
			locale = "pt";
		}
		int i = 0;
		if (flag)
			sb.append("<div  id='esquerda' class='innerTB'>");
		sb.append("<!-- Inicio do loop de solicitações abertas -->");

		if (listTarefaSolicitacaoServico != null && !listTarefaSolicitacaoServico.isEmpty()) {
			ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);

			for (TarefaFluxoDTO tarefaFluxoDto : listTarefaSolicitacaoServico) {

				SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) tarefaFluxoDto.getSolicitacaoDto();
				// Faz o cálculo da SLA
				String hh = "";
				String mm = "";
				if (solicitacaoServicoDto.getIdContrato() != null || solicitacaoServicoDto.getIdServico() != null) {

					hh = solicitacaoServicoDto.getPrazoHH().toString();
					mm = solicitacaoServicoDto.getPrazoMM().toString();

					if (hh.length() == 1)
						hh = "0" + hh;
					if (mm.length() == 1)
						mm = "0" + mm;
				}

				String cssPrioridade = setarCssPrioridade(solicitacaoServicoDto.getPrioridade());
				String cssPrazo = setarCssPrazo(solicitacaoServicoDto);
				String HTMLprazo = setarHtmlPrazo(solicitacaoServicoDto, request);
				String situacaoSLA = "";
				if (solicitacaoServicoDto.getSituacaoSLA().equalsIgnoreCase("N")) {
					situacaoSLA = UtilI18N.internacionaliza(locale, "citcorpore.comum.naoIniciado");
				} else if (solicitacaoServicoDto.getSituacaoSLA().equalsIgnoreCase("S")) {
					situacaoSLA = UtilI18N.internacionaliza(locale, "citcorpore.comum.suspenso");
				}

				sb.append("<div class='box-generic content-area " + (i % 2 == 0 ? "alternado" : "") + " listaDetalhada " + (tipoLista.equals(1) ? "ativo" : "") + "'> ");
				sb.append(HTMLprazo);
				sb.append("	<div class='row-fluid'>");
				sb.append("		<div class='span2'>");
				sb.append("			<label class='content-row'>");
				sb.append("		      <div>" + UtilI18N.internacionaliza(locale, "gerenciaservico.numerosolicitacao") + "</div>");
				sb.append("		      <span class='fontSize15'>[" + solicitacaoServicoDto.getIdSolicitacaoServico() + "]</span>");
				sb.append("		    </label>");
				sb.append("		</div>");
				sb.append("		<div class='span2'>");
				sb.append("			<label class='content-row'>");
				sb.append("		      <div>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.tipo") + "</div>");
				sb.append("		      <span class='verde-negrito'>" + solicitacaoServicoDto.getDemanda() + "</span>");
				sb.append("		    </label>");
				sb.append("		</div>");
				sb.append("		<div class='span2'>");
				sb.append("			<label class='content-row'>");
				sb.append("		      <div>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.dataHoraCriacao") + "</div>");
				sb.append("		      <span class='verde-negrito'>" + solicitacaoServicoDto.obterDataHoraSolicitacaoStrWithLanguage(language) + "</span>");
				sb.append("		    </label>");
				sb.append("		</div>");
				sb.append("		<div class='span1'>");
				sb.append("		    <label class='content-row'>");
				sb.append("		      <div>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.prioridade") + "</div>");
				sb.append("			  <span class= '" + cssPrioridade + "'> " + solicitacaoServicoDto.getPrioridade() + "</span>");
				sb.append("		    </label>");
				sb.append("	    </div>");
				sb.append("		<div class='span2'>");
				sb.append("		    <label class='content-row'>");
				sb.append("		      <div>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.prazoLimite") + "</div>");
				if (!solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					if (!solicitacaoServicoDto.getSituacaoSLA().equalsIgnoreCase("S") || solicitacaoServicoDto.getAtrasoSLA() > 0) {
						sb.append("		      <span class= '" + cssPrazo + "'> " + UtilStrings.nullToVazio(solicitacaoServicoDto.obterDataHoraLimiteStrWithLanguage(language)) + "</span>");
					}
				}
				sb.append("		    </label>");
				sb.append("	    </div>");
				sb.append("		<div class='span2'>");
				sb.append("		    <label class='content-row'>");
				sb.append("		      <div>" + UtilI18N.internacionaliza(locale, "gerenciaservico.sla") + "</div>");
				if ((solicitacaoServicoDto.getPrazoHH().equals(0) || solicitacaoServicoDto.getPrazoHH() == null)
						&& (solicitacaoServicoDto.getPrazoMM().equals(0) || solicitacaoServicoDto.getPrazoMM() == null)
						&& !solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("		      <span class='verde-normal'>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.aCombinar") + "</span>");
				} else if (!hh.equals("") && !mm.equals("") && !solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("		      <span class= '" + cssPrazo + "'> " + hh + " : " + mm + " " + situacaoSLA + "</span>");
				} else {
					sb.append("		      <span class= '" + cssPrazo + "'></span>");
				}
				sb.append("		    </label>");
				sb.append("	    </div>");
				sb.append("	    <div class='span1'>");
				sb.append("		    <label class='content-row'>");
				if (solicitacaoServicoDto.getAtrasoSLA() > 0 && !solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("		      <div>" + UtilI18N.internacionaliza(locale, "tarefa.atraso") + "</div>");
					sb.append("		      <span class= '" + cssPrazo + "'>" + solicitacaoServicoDto.getAtrasoSLAStr() + "</span>");
				}
				sb.append("		    </label>");
				sb.append("	    </div>");
				sb.append("   </div>");
				sb.append("   <div class='row-fluid'>");
				sb.append("		<div class='span2'>");
				sb.append("			<label class='content-row labelOverflowTresPontinhos'>");
				sb.append("		      <div>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.servico") + "</div>");
				sb.append("		      <span class='servico escuro-negrito' title='" + solicitacaoServicoDto.getNomeServico() + "'>" + solicitacaoServicoDto.getNomeServico() + "</span>");
				sb.append("		    </label>");
				sb.append("		</div>");
				if (solicitacaoServicoDto.getIdSolicitacaoRelacionada() != null) {
					sb.append("		<div class='span2'>");
					sb.append("			<label class='content-row'>");
					sb.append("		      <div>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.filhadaSolicitacao") + "</div>");
					sb.append("		      <a onclick='visualizarSolicitacaoPaiEnvioDoAction(" + solicitacaoServicoDto.getIdSolicitacaoRelacionada() + ")' class='verde-negrito'>"
							+ solicitacaoServicoDto.getIdSolicitacaoRelacionada() + "</a>");
					sb.append("		    </label>");
					sb.append("		</div>");
				}
				sb.append("		<div class='span2'>");
				sb.append("			<label class='content-row'>");
				sb.append("		      <div>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.grupoExecutor") + "</div>");
				sb.append("		      <span>" + UtilStrings.nullToVazio(solicitacaoServicoDto.getGrupoAtual()) + "</span>");
				sb.append("		    </label>");
				sb.append("		</div>");
				if (solicitacaoServicoDto.getIdSolicitacaoRelacionada() != null) {
					sb.append("	    <div class='span1'>");
					sb.append("		    <label class='content-row'>");
					sb.append("		      <div>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.tarefaatual.desc") + "</div>");
					sb.append("		      <span>" + tarefaFluxoDto.getElementoFluxoDto().getDocumentacao() + "</span>");
					sb.append("		    </label>");
					sb.append("	    </div>");
				} else {
					sb.append("	    <div class='span2'>");
					sb.append("		    <label class='content-row'>");
					sb.append("		      <div>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.tarefaatual.desc") + "</div>");
					sb.append("		      <span>" + tarefaFluxoDto.getElementoFluxoDto().getDocumentacao() + "</span>");
					sb.append("		    </label>");
					sb.append("	    </div>");
				}
				sb.append("		<div class='span4'>");
				sb.append("		    <label class='content-row'>");
				sb.append("		      <div>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.responsavel") + "</div>");
				sb.append("		      <span class='escuro-negrito'>" + tarefaFluxoDto.getResponsavelAtual() + "</span>");
				sb.append("		    </label>");
				sb.append("		</div>");
				sb.append("	</div>");

				if (solicitacaoServicoDto.getQtdefilhas() != null && !solicitacaoServicoDto.getQtdefilhas().equals(0)) {
					sb.append("	<div class='row-fluid'>");
					sb.append("		<div class='span2'>");
					sb.append("			<label class='content-row labelOverflowTresPontinhos'>");
					sb.append("		      <div>" + UtilI18N.internacionaliza(locale, "requisitosla.contrato") + "</div>");
					sb.append("		      <span title='" + solicitacaoServicoDto.getContrato() + "'>" + solicitacaoServicoDto.getContrato() + "</span>");
					sb.append("		    </label>");
					sb.append("		</div>");

					sb.append("		<div class='span2'>");
					sb.append("		    <label class='content-row'>");
					sb.append("		        <div>&nbsp;</div>");
					sb.append("		        <a onclick='modalSolicitacoesFilhas(\"" + solicitacaoServicoDto.getIdSolicitacaoServico() + "\");'>"
							+ "<span class='count' id='countSolicitacoesAbertasSolicitante'>" + solicitacaoServicoDto.getQtdefilhas() + "</span>");
					sb.append(" " + UtilI18N.internacionaliza(locale, "solicitacaoservico.solicitacaofilha") + "</a>");
					sb.append("		    </label>");
					sb.append("	    </div>");
				} else {
					sb.append("	<div class='row-fluid'>");
					sb.append("		<div class='span4'>");
					sb.append("			<label class='content-row'>");
					sb.append("		      <div>" + UtilI18N.internacionaliza(locale, "requisitosla.contrato") + "</div>");
					sb.append("		      <span title='" + solicitacaoServicoDto.getContrato() + "'>" + solicitacaoServicoDto.getContrato() + "</span>");
					sb.append("		    </label>");
					sb.append("		</div>");
				}

				sb.append("		<div class='span4'>");
				sb.append("		    <label class='content-row'>");
				sb.append("		      <div>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.solicitante") + "</div>");
				sb.append("		      <span>" + solicitacaoServicoDto.getSolicitanteUnidade() + "</span>");
				sb.append("		        <a class='informacoesSolicitante' onmouseover='geraPopoverInformacoesSolicitante(\""
						+ Util.tratarAspasSimples(UtilStrings.nullToNaoDisponivel(solicitacaoServicoDto.getTelefonecontato())) + "\",\""
						+ Util.tratarAspasSimples(UtilStrings.nullToNaoDisponivel(solicitacaoServicoDto.getEmailcontato())) + "\");'><strong><u>"
						+ UtilI18N.internacionaliza(locale, "citcorpore.comum.saibamais") + "</u></strong></a>");

				sb.append("		    </label>");
				sb.append("	    </div>");

				/** Otimização da verificação de anexos. Operação Usain Bolt - 27.01.2015 - carlos.santos */
				if (solicitacaoServicoDto.getQtdeAnexos() == null) {
					solicitacaoServicoDto.setQtdeAnexos(new Integer(0));
					Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_SOLICITACAOSERVICO, solicitacaoServicoDto.getIdSolicitacaoServico());
					if (colAnexos != null)
						solicitacaoServicoDto.setQtdeAnexos(colAnexos.size());
				}

				if (solicitacaoServicoDto.getQtdeAnexos() > 0) {
					sb.append("		<div class='span" + (tarefaFluxoDto.getCompartilhamento() != null && !tarefaFluxoDto.getCompartilhamento().trim().equals("") ? "2" : "4") + "'>");
					sb.append("		    <label class='content-row'>");
					sb.append("		      ");
					sb.append("		    </label>");
					sb.append("			<span class='count label label-inverse'>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.possuiAnexo") + "</span>");
					sb.append("	    </div>");
				}

				if (tarefaFluxoDto.getCompartilhamento() != null && !tarefaFluxoDto.getCompartilhamento().trim().equals("")) {
					sb.append("	<div class='span2'>");
					sb.append("	   <label class='content-row'>");
					sb.append("	     <div>" + UtilI18N.internacionaliza(locale, "tarefa.compartilhadacom") + "</div>");
					sb.append("	     <span>" + tarefaFluxoDto.getCompartilhamento() + "</span>");
					sb.append("	   </label>");
					sb.append("	</div>");
				}

				if (solicitacaoServicoDto.getQtdeItensConfiguracaoRelacionados() != null && solicitacaoServicoDto.getQtdeItensConfiguracaoRelacionados() != 0) {
					sb.append("    </div>");
					sb.append("	<div class='row-fluid'>");
					sb.append("		<div class='span12'>");
					sb.append("			<div class='row-fluid'>");
					sb.append("				<div class='span2'>");
					sb.append("		            <label class='content-row labelOverflowTresPontinhos'>");
					sb.append("		              <div>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.criadopor") + "</div>");
					sb.append("		              <span  title='" + solicitacaoServicoDto.getResponsavel() + "' >" + solicitacaoServicoDto.getResponsavel() + "</span>");
					sb.append("		           </label>");
					sb.append("	            </div>");
				} else {
					sb.append("    </div>");
					sb.append("	<div class='row-fluid'>");
					sb.append("		<div class='span12'>");
					sb.append("			<div class='row-fluid'>");
					sb.append("				<div class='span4'>");
					sb.append("		            <label class='content-row'>");
					sb.append("		              <div>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.criadopor") + "</div>");
					sb.append("		              <span  title='" + solicitacaoServicoDto.getResponsavel() + "' >" + solicitacaoServicoDto.getResponsavel() + "</span>");
					sb.append("		           </label>");
					sb.append("	            </div>");
				}

				if (solicitacaoServicoDto.getQtdeItensConfiguracaoRelacionados() != null && solicitacaoServicoDto.getQtdeItensConfiguracaoRelacionados() != 0) {
					sb.append("		<div class='span2'>");
					sb.append("		    <label class='content-row'>");
					sb.append("		        <div>&nbsp;</div>");
					sb.append("		        <a data-toggle='modal' data-target='#modal_itemConfiguracao' onclick='pesquisarItensDeConfiguracaoRelacionados(\""
							+ solicitacaoServicoDto.getIdSolicitacaoServico() + "\");'>" + solicitacaoServicoDto.getQtdeItensConfiguracaoRelacionados() + " "
							+ UtilI18N.internacionaliza(locale, "solicitacaoServico.itemDeConfiguracao") + "</a>");
					sb.append("		    </label>");
					sb.append("	    </div>");
				}
				sb.append("				<div class='span2'>");
				sb.append("				    <label class='content-row'>");
				sb.append("				      <div>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.situacao") + "</div>");
				if (solicitacaoServicoDto.getSituacao().equalsIgnoreCase("EmAndamento")) {
					sb.append("				      <span>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.situacao.EmAndamento") + "</span>");
				} else if (solicitacaoServicoDto.getSituacao().equalsIgnoreCase("Cancelada")) {
					sb.append("				      <span>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.situacao.Cancelada") + "</span>");
				} else if (solicitacaoServicoDto.getSituacao().equalsIgnoreCase("Suspensa")) {
					sb.append("				      <span>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.situacao.Suspensa") + "</span>");
				} else {
					sb.append("				      <span>" + solicitacaoServicoDto.getSituacao() + "</span>");
				}
				sb.append("					</label>");
				sb.append("				</div>");
				sb.append("				<div class='span6 right'>");
				sb.append("				    <div class='content-row'>");

				sb.append("					  <button type='button' class='btn btn-default maisInfo' onclick='chamarModalDescricaoSolicitacao(\""
						+ StringEscapeUtils.escapeJavaScript(solicitacaoServicoDto.getDescricao()) + "\"," + solicitacaoServicoDto.getIdSolicitacaoServico() + " )'>"
						+ UtilI18N.internacionaliza(locale, "citcorpore.comum.descricao") + "</button>");

				if (tarefaFluxoDto.getExecutar().equals("S") && !solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("	      			  <button id='executar' type='button' class='btn btn-default' onclick='prepararExecucaoTarefa(" + tarefaFluxoDto.getIdItemTrabalho() + ","
							+ solicitacaoServicoDto.getIdSolicitacaoServico() + ",\"E\");'>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.executar") + "</button>");
				}
				if (tarefaFluxoDto.getExecutar().equals("S") && !tarefaFluxoDto.getResponsavelAtual().trim().equals(usuario.getNomeUsuario().trim())
						&& !solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("				      <button id='capturar' type='button' class='btn btn-default' onclick='capturarTarefa(\"" + tarefaFluxoDto.getResponsavelAtual() + "\","
							+ tarefaFluxoDto.getIdItemTrabalho() + ");'>" + UtilI18N.internacionaliza(locale, "gerenciaservico.capturartarefa") + "</button>");
				}
				// Mario Júnior - 23/10/2013 - 16:00 - Passando Parametro no vizualiza
				sb.append("				      <button type='button' id='visualizar' onclick='visualizarSolicitacao(" + solicitacaoServicoDto.getIdSolicitacaoServico() + ","
						+ tarefaFluxoDto.getIdItemTrabalho() + ")' class='btn btn-default'>" + UtilI18N.internacionaliza(locale, "gerenciaservico.visualizar") + "</button>");
				sb.append("			      	  <div id='acoes' class='btn-group btn-block w15 aLeft dropup'>");
				sb.append("						   <div class='leadcontainer' data-toggle='dropdown'>");
				sb.append("								<button type='button' class='btn dropdown-lead btn-default'>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.acao") + "</button>");
				sb.append("							</div>");
				sb.append("							<a class='btn btn-default dropdown-toggle' data-toggle='dropdown' ><span class='caret'></span> </a>");
				sb.append("							<ul class='dropdown-menu pull-right'>");
				if (tarefaFluxoDto.getDelegar().equals("S") && !solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("								<li onclick='exibirDelegacaoTarefa(" + tarefaFluxoDto.getIdItemTrabalho() + "," + solicitacaoServicoDto.getIdSolicitacaoServico() + ",\""
							+ tarefaFluxoDto.getElementoFluxoDto().getDocumentacao() + "\");'><a >" + UtilI18N.internacionaliza(locale, "citcorpore.comum.delegar") + "</a></li>");
				}
				if (tarefaFluxoDto.getSuspender().equals("S") && !solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("								<li onclick='prepararSuspensao(" + solicitacaoServicoDto.getIdSolicitacaoServico() + ")'><a >" + UtilI18N.internacionaliza(locale, "citcorpore.comum.suspender")
							+ "</a></li>");
				}
				if (solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.EmAndamento.toString())) {
					sb.append("  							<li onclick='adicionarIdContratoNaLookup(" + solicitacaoServicoDto.getIdContrato() + ");carregarModalDuplicarSolicitacao("
							+ solicitacaoServicoDto.getIdSolicitacaoServico() + ");montaParametrosAutocompleteSol(" + solicitacaoServicoDto.getIdContrato() + ")';><a >"
							+ UtilI18N.internacionaliza(locale, "gerenciaservico.duplicarSolicitacao") + "</a></li>");
				}
				if (solicitacaoServicoDto.getPossuiFilho() == true) {
					sb.append("  							<li onclick='exibirSubSolicitacoes(" + solicitacaoServicoDto.getIdSolicitacaoServico() + ")';><a >"
							+ UtilI18N.internacionaliza(locale, "gerenciaservico.exibirsubsolicitacoes") + "</a></li>");
					sb.append("								<li class='divider'></li>");
				}
				if (tarefaFluxoDto.getReativar().equals("S") && solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("								<li onclick='reativarSolicitacao(" + solicitacaoServicoDto.getIdSolicitacaoServico() + ")'><a >"
							+ UtilI18N.internacionaliza(locale, "gerenciaservico.reativarsolicitacao") + "</a></li>");
				}
				/**
				 * Motivo: Foi visualizado no código da página antiga que o mesmo validava a Situação de SLA Autor: flavio.santana Data/Hora: 13/11/2013 17:30
				 */
				if (tarefaFluxoDto.getAlterarSLA().equals("S") && !solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())
						&& !solicitacaoServicoDto.getSituacaoSLA().equals(Enumerados.SituacaoSLA.S.toString()) && !solicitacaoServicoDto.getSituacaoSLA().equals(Enumerados.SituacaoSLA.N.toString())) {
					sb.append("								<li onclick='prepararMudancaSLA(" + tarefaFluxoDto.getIdItemTrabalho() + "," + solicitacaoServicoDto.getIdSolicitacaoServico() + ")'><a >"
							+ UtilI18N.internacionaliza(locale, "citcorpore.comum.alterarSLA") + "</a></li>");
				}
				if (tarefaFluxoDto.getAlterarSLA().equals("S") && !solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("								<li onclick='reclassificarSolicitacao(" + solicitacaoServicoDto.getIdSolicitacaoServico() + ", " + tarefaFluxoDto.getIdItemTrabalho() + ")'><a >"
							+ UtilI18N.internacionaliza(locale, "citcorpore.comum.reclassificaosolicitacao") + "</a></li>");
				}
				sb.append("								<li onclick='agendaAtividade(" + solicitacaoServicoDto.getIdSolicitacaoServico() + ")'><a >" + UtilI18N.internacionaliza(locale, "gerenciaservico.agendaratividade")
						+ "</a></li>");
				sb.append("							</ul>");
				sb.append("					 </div>");
				sb.append("			      </div>");
				sb.append("				</div>");
				sb.append("			</div>");
				sb.append("		</div>");
				sb.append("	</div>");
				sb.append("</div>");

				sb.append("<div class='box-generic content-area " + (i % 2 == 0 ? "alternado" : "") + " listaResumida " + (tipoLista.equals(2) ? "ativo" : "") + "'>");
				sb.append(HTMLprazo);
				sb.append("	<div class='row-fluid'>");
				sb.append("		<div class='span2'>");
				sb.append("			<label class='content-row'>");
				sb.append("		      	<div>" + UtilI18N.internacionaliza(locale, "gerenciaservico.numerosolicitacao") + "</div>");
				sb.append("		      	<span class='verde-negrito'>[" + solicitacaoServicoDto.getIdSolicitacaoServico() + "]</span>");
				sb.append("			</label>");
				sb.append("		</div>");
				sb.append("		<div class='span6'>");
				sb.append("			<label class='content-row'>");
				sb.append("		      	<div>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.servico") + "</div>");
				sb.append("		      	<span class='servico escuro-negrito'>" + solicitacaoServicoDto.getNomeServico() + "</span>");
				sb.append("			</label>");
				sb.append("					</div>");
				sb.append("					<div class='span1'>");
				sb.append("						<label class='content-row'>");
				sb.append("		     				<div>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.prioridade") + "</div>");
				sb.append("			  				<span class='" + cssPrioridade + "'>" + solicitacaoServicoDto.getPrioridade() + "</span>");
				sb.append("						</label>");
				sb.append("					</div>");
				sb.append("					<div class='span2'>");
				sb.append("						<label class='content-row'>");
				sb.append("		      				<div>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.prazoLimite") + "</div>");
				if (!solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("		     				<span class= '" + cssPrazo + "'> " + UtilStrings.nullToVazio(solicitacaoServicoDto.obterDataHoraLimiteStrWithLanguage(language)) + "</span>");
				}
				sb.append("						</label>");
				sb.append("					</div>");
				sb.append("					<div class='span1'>");
				sb.append("						<label class='content-row'>");
				sb.append("		     			<div>" + UtilI18N.internacionaliza(locale, "gerenciaservico.sla") + "</div>");
				if (solicitacaoServicoDto.getSlaACombinar().equals("S") && !solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("		      <span class='verde-normal'>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.acombinar") + "</span>");
				} else if (!solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("		      <span class= '" + cssPrazo + "'> " + solicitacaoServicoDto.getPrazoHH() + " : " + solicitacaoServicoDto.getPrazoMM() + "</span>");
				}
				sb.append("						</label>");
				sb.append("					</div>");
				sb.append("				</div>");
				sb.append("				<div class='row-fluid'>");
				sb.append("					<div class='span4'>");
				sb.append("		            	<label class='content-row'>");
				sb.append("		              		<div>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.criadopor") + "</div>");
				sb.append("		             	 	<span  >" + solicitacaoServicoDto.getResponsavel() + "</span>");
				sb.append("		           		</label>");
				sb.append("	            	</div>");
				sb.append("					<div class='span3'>");
				sb.append("						<label class='content-row'>");
				sb.append("				      		<div>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.responsavel") + "</div>");
				sb.append("				      		<span class='escuro-negrito'>" + tarefaFluxoDto.getResponsavelAtual() + "</span>");
				sb.append("						</label>");
				sb.append("					</div>");
				sb.append("					<div class='span5 right'>");
				sb.append("				    	<div class='content-row'>");
				sb.append("					  <button type='button' class='btn btn-default maisInfo' onclick='chamarModalDescricaoSolicitacao(\""
						+ StringEscapeUtils.escapeJavaScript(solicitacaoServicoDto.getDescricao()) + "\"," + solicitacaoServicoDto.getIdSolicitacaoServico() + " )'>"
						+ UtilI18N.internacionaliza(locale, "citcorpore.comum.descricao") + "</button>");
				if (tarefaFluxoDto.getExecutar().equals("S") && !solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("	      			  	<button id='executar' type='button' class='btn btn-default' onclick='prepararExecucaoTarefa(" + tarefaFluxoDto.getIdItemTrabalho() + ","
							+ solicitacaoServicoDto.getIdSolicitacaoServico() + ",\"E\");'>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.executar") + "</button>");
				}
				if (tarefaFluxoDto.getExecutar().equals("S") && !tarefaFluxoDto.getResponsavelAtual().equalsIgnoreCase(usuario.getNomeUsuario().trim())
						&& !solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("				      		<button id='capturar' type='button' class='btn btn-default' onclick='capturarTarefa(\"" + tarefaFluxoDto.getResponsavelAtual() + "\","
							+ tarefaFluxoDto.getIdItemTrabalho() + ");'>" + UtilI18N.internacionaliza(locale, "gerenciaservico.capturartarefa") + "</button>");
				}
				sb.append(" 				    <button type='button' id='visualizar' onclick='visualizarSolicitacao(" + solicitacaoServicoDto.getIdSolicitacaoServico() + "," + tarefaFluxoDto.getIdItemTrabalho()
						+ ")' class='btn btn-default'>" + UtilI18N.internacionaliza(locale, "gerenciaservico.visualizar") + "</button>");
				sb.append("			      	  	<div id='acoes' class='btn-group btn-block w15 aLeft  dropup'>");
				sb.append("						   <div class='leadcontainer' data-toggle='dropdown'>");
				sb.append("								<button type='button' class='btn dropdown-lead btn-default'>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.acao") + "</button>");
				sb.append("							</div>");
				sb.append("							<a class='btn btn-default dropdown-toggle' data-toggle='dropdown' ><span class='caret'></span> </a>");
				sb.append("							<ul class='dropdown-menu pull-right'>");
				if (tarefaFluxoDto.getDelegar().equals("S") && !solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("								<li onclick='exibirDelegacaoTarefa(" + tarefaFluxoDto.getIdItemTrabalho() + "," + solicitacaoServicoDto.getIdSolicitacaoServico() + ",\""
							+ tarefaFluxoDto.getElementoFluxoDto().getDocumentacao() + "\");'><a >" + UtilI18N.internacionaliza(locale, "citcorpore.comum.delegar") + "</a></li>");
				}
				if (tarefaFluxoDto.getSuspender().equals("S") && !solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("								<li onclick='prepararSuspensao(" + solicitacaoServicoDto.getIdSolicitacaoServico() + ")'><a >" + UtilI18N.internacionaliza(locale, "citcorpore.comum.suspender")
							+ "</a></li>");
				}
				if (solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.EmAndamento.toString())) {
					sb.append("  							<li onclick='adicionarIdContratoNaLookup(" + solicitacaoServicoDto.getIdContrato() + ");carregarModalDuplicarSolicitacao("
							+ solicitacaoServicoDto.getIdSolicitacaoServico() + ");montaParametrosAutocompleteSol(" + solicitacaoServicoDto.getIdContrato() + ")';><a >"
							+ UtilI18N.internacionaliza(locale, "gerenciaservico.duplicarSolicitacao") + "</a></li>");
				}
				if (solicitacaoServicoDto.getPossuiFilho() == true) {
					sb.append("  							<li onclick='exibirSubSolicitacoes(" + solicitacaoServicoDto.getIdSolicitacaoServico() + ")';><a >"
							+ UtilI18N.internacionaliza(locale, "gerenciaservico.exibirsubsolicitacoes") + "</a></li>");
					sb.append("								<li class='divider'></li>");
				}
				if (tarefaFluxoDto.getReativar().equals("S") && solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("								<li onclick='reativarSolicitacao(" + solicitacaoServicoDto.getIdSolicitacaoServico() + ")'><a >"
							+ UtilI18N.internacionaliza(locale, "gerenciaservico.reativarsolicitacao") + "</a></li>");
				}
				/**
				 * Motivo: Foi visualizado no código da página antiga que o mesmo validava a Situação de SLA Autor: flavio.santana Data/Hora: 13/11/2013 17:30
				 */
				if (tarefaFluxoDto.getAlterarSLA().equals("S") && !solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())
						&& !solicitacaoServicoDto.getSituacaoSLA().equals(Enumerados.SituacaoSLA.S.toString()) && !solicitacaoServicoDto.getSituacaoSLA().equals(Enumerados.SituacaoSLA.N.toString())) {
					sb.append("								<li onclick='prepararMudancaSLA(" + tarefaFluxoDto.getIdItemTrabalho() + "," + solicitacaoServicoDto.getIdSolicitacaoServico() + ")'><a >"
							+ UtilI18N.internacionaliza(locale, "citcorpore.comum.alterarSLA") + "</a></li>");
				}
				if (tarefaFluxoDto.getAlterarSLA().equals("S") && !solicitacaoServicoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("								<li onclick='reclassificarSolicitacao(" + solicitacaoServicoDto.getIdSolicitacaoServico() + ", " + tarefaFluxoDto.getIdItemTrabalho() + ")'><a >"
							+ UtilI18N.internacionaliza(locale, "citcorpore.comum.reclassificaosolicitacao") + "</a></li>");
				}
				sb.append("								<li onclick='agendaAtividade(" + solicitacaoServicoDto.getIdSolicitacaoServico() + ")'><a >" + UtilI18N.internacionaliza(locale, "gerenciaservico.agendaratividade")
						+ "</a></li>");
				sb.append("							</ul>");
				sb.append("					 	</div>");
				sb.append("			      	</div>");
				sb.append("					</div>");
				sb.append("				</div>");
				sb.append("			</div>");

				i++;
			}
		} else {
			sb.append("<div class='content-area'>");
			sb.append("		<div class='row-fluid'>");
			sb.append("			<div class='innerTB'>");
			sb.append("				<h4>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.resultado") + "</h4>");
			sb.append("			</div>");
			sb.append("		</div>");
			sb.append("</div>");
		}
		sb.append("<!-- Fim do loop de solicitações abertas -->");
		if (flag)
			sb.append("</div>");
		carregarItensFiltro(request);
	}

	/**
	 * Verifica se idContrato está na Coleção de Contratos informado.
	 *
	 * @param idContrato
	 *            - id do Contrato.
	 * @param colContratosColab
	 *            - Coleção de contratos.
	 * @return boolean (true - contrato está na lista)
	 */
	protected boolean isContratoInList(Integer idContrato, Collection colContratosColab) {
		if (idContrato == null) {
			return false;
		}
		if (colContratosColab != null) {
			for (Iterator it = colContratosColab.iterator(); it.hasNext();) {
				ContratosGruposDTO contratosGruposDTO = (ContratosGruposDTO) it.next();
				if (contratosGruposDTO.getIdContrato() == null) {
					contratosGruposDTO.setIdContrato(new Integer(0));
				}
				if (contratosGruposDTO.getIdContrato().intValue() == idContrato.intValue()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Prepada a execução da tarefa.
	 *
	 * @param document
	 *            - DocumentHTML
	 * @param request
	 *            - HttpServletRequest
	 * @param response
	 *            - HttpServletResponse
	 * @throws Exception
	 */
	public void preparaExecucaoTarefa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		GerenciamentoServicosDTO gerenciamentoBean = (GerenciamentoServicosDTO) document.getBean();
		if (gerenciamentoBean.getIdTarefa() == null)
			return;

		TarefaFluxoDTO tarefaDto = getExecucaoSolicitacaoService().recuperaTarefa(usuario.getLogin(), gerenciamentoBean.getIdTarefa());
		if (tarefaDto == null || tarefaDto.getElementoFluxoDto() == null || !tarefaDto.getExecutar().equals("S") || tarefaDto.getElementoFluxoDto().getTipoInteracao() == null)
			return;

		if (tarefaDto.getElementoFluxoDto().getTipoInteracao().equals(br.com.centralit.bpm.util.Enumerados.INTERACAO_VISAO)) {
			if (tarefaDto.getIdVisao() != null) {
				document.executeScript("exibirVisao('" + UtilI18N.internacionaliza(request, "gerenciaservico.executartarefa") + " " + tarefaDto.getElementoFluxoDto().getDocumentacao() + "','"
						+ tarefaDto.getIdVisao() + "','" + tarefaDto.getElementoFluxoDto().getIdFluxo() + "','" + tarefaDto.getIdItemTrabalho() + "','" + gerenciamentoBean.getAcaoFluxo() + "');");
			} else {
				document.alert(UtilI18N.internacionaliza(request, "gerenciaservico.visaotarefa") + " \"" + tarefaDto.getElementoFluxoDto().getDocumentacao() + "\" "
						+ UtilI18N.internacionaliza(request, "gerenciaservico.naoencontrado"));
			}
		} else {

			String caracterParmURL = "?";
			if (tarefaDto.getElementoFluxoDto().getUrl().indexOf("?") > -1) { // Se na URL jÃ¡ conter ?, entao colocar &
				caracterParmURL = "&";
			}
			document.executeScript("exibirUrl('" + UtilI18N.internacionaliza(request, "gerenciaservico.executartarefa") + " " + tarefaDto.getElementoFluxoDto().getDocumentacao() + "','"
					+ tarefaDto.getElementoFluxoDto().getUrl() + caracterParmURL + "idSolicitacaoServico=" + ((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto()).getIdSolicitacaoServico()
					+ "&idTarefa=" + tarefaDto.getIdItemTrabalho() + "&acaoFluxo=" + gerenciamentoBean.getAcaoFluxo() + "');");
		}

		tarefaDto = null;
	}

	/**
	 * Reativa a solicitação suspensa.
	 *
	 * @param document
	 *            - DocumentHTML
	 * @param request
	 *            - HttpServletRequest
	 * @param response
	 *            - HttpServletResponse
	 * @throws Exception
	 */
	public void reativaSolicitacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		GerenciamentoServicosDTO gerenciamentoBean = (GerenciamentoServicosDTO) document.getBean();
		if (gerenciamentoBean.getIdSolicitacaoSel() == null) {
			return;
		} else {
			Integer idSolicitacao = Integer.parseInt(gerenciamentoBean.getIdSolicitacaoSel());
			gerenciamentoBean.setIdSolicitacao(idSolicitacao);
		}

		SolicitacaoServicoService solicitacaoServicoService = getSolicitacaoServicoService(request);
		SolicitacaoServicoDTO solicitacaoServicoDto = solicitacaoServicoService.restoreAll(gerenciamentoBean.getIdSolicitacao());
		solicitacaoServicoService.reativa(usuario, solicitacaoServicoDto);
		document.executeScript("refreshTelaGerenciamento();");

		gerenciamentoBean = null;
		solicitacaoServicoDto = null;
	}

	/**
	 * Realiza a captura da Tarefa para o Usuário logado.
	 *
	 * @param document
	 *            - DocumentHTML
	 * @param request
	 *            - HttpServletRequest
	 * @param response
	 *            - HttpServletRequest
	 * @throws Exception
	 */
	public void capturaTarefa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		GerenciamentoServicosDTO gerenciamentoBean = (GerenciamentoServicosDTO) document.getBean();
		if (gerenciamentoBean.getIdTarefa() == null)
			return;

		getExecucaoSolicitacaoService().executa(usuario, gerenciamentoBean.getIdTarefa(), br.com.centralit.bpm.util.Enumerados.ACAO_INICIAR);
		gerenciamentoBean = null;
		document.executeScript("refreshTelaGerenciamento();");
	}

	/**
	 * Serializa a Lista de Tarefas.
	 *
	 * @param colTarefas
	 *            - List<TarefaFluxoDTO>
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	private String serializaTarefas(List<TarefaFluxoDTO> colTarefas, HttpServletRequest request) throws Exception {
		if (colTarefas == null)
			return null;
		for (TarefaFluxoDTO tarefaDto : colTarefas) {
			tarefaDto.setElementoFluxo_serialize(br.com.citframework.util.WebUtil.serializeObject(tarefaDto.getElementoFluxoDto(), WebUtil.getLanguage(request)));
			tarefaDto.setSolicitacao_serialize(br.com.citframework.util.WebUtil.serializeObject(tarefaDto.getSolicitacaoDto(), WebUtil.getLanguage(request)));
		}
		return br.com.citframework.util.WebUtil.serializeObjects(colTarefas, WebUtil.getLanguage(request));
	}

	public void exibirResumoSolicitacoes(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		List<TarefaFluxoDTO> colecao = (List<TarefaFluxoDTO>) listarTarefas(null, null, usuario, request, true, null);

		renderizarResumoSolicitacoes(document, request, colecao);

		document.executeScript("JANELA_AGUARDE_MENU.hide()");
	}

	/*
	 * Desenvolvedor: Pedro Lino - Data: 06/11/2013 - Horário: 09:45 - ID Citsmart: 120948 - Motivo/Comentário: Comentado links: ver todos e prioridades, pois não tinham nenhuma função
	 */

	public void renderizarResumoSolicitacoes(DocumentHTML document, HttpServletRequest request, List<TarefaFluxoDTO> listTarefas) throws ServiceException, Exception {

		UsuarioDTO usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		StringBuilder sb = new StringBuilder();
		HashMap HashMapSituacao = new HashMap();
		HashMap hashMapPrazoLimite = new HashMap();
		// Mário Júnior - 29/10/2013 - 17:00 - Inserido para mostrar total dos resumos
		Integer qtdPrazoLimite = 0;
		Integer qtdTipoSolicitacao = 0;
		Integer qtdSituacao = 0;
		Integer qtdPrioridade = 0;

		hashMapPrazoLimite = resumoPorPrazoLimite(listTarefas);
		Collection<PrioridadeDTO> colResumoPorPrioridade = resumoPorPrioridade(listTarefas);
		Collection<TipoDemandaServicoDTO> colTipoSolicitacaoServico = resumoPorTipoSolicitacaoServico(listTarefas);

		Integer quantidadeSolicitacoes = 0;
		for (TipoDemandaServicoDTO dto : colTipoSolicitacaoServico) {
			if (dto.getQuantidade() != null) {
				quantidadeSolicitacoes += dto.getQuantidade();
			}
		}
		if (quantidadeSolicitacoes > 0) {
			String quantidadeSolicitacoesStr = String.valueOf(quantidadeSolicitacoes);
			document.getElementById("quantidadeSolicitacoes").setValue(quantidadeSolicitacoesStr);
		}

		HashMapSituacao = resumoPorSituacao(listTarefas);
		HTMLElement tab3 = document.getElementById("tab3-3");

		sb.append("<div class='tab-pane' id='tab3-3'> ");
		sb.append("	<div class='row-fluid'> ");
		sb.append("		<div class='span12'> ");
		sb.append("			<div class='row-fluid'> ");
		sb.append("				<div class='span6'>	");
		sb.append("					<div class='widget'> ");
		sb.append("						<div class='widget-head'> ");
		sb.append("							<h4 class='heading'><i></i>" + UtilI18N.internacionaliza(request, "solicitacaoServico.prazoLimite") + "</h4> ");
		/* sb.append("							<a href='' class='details pull-right'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.verTodos") + "</a> "); */
		sb.append("						</div> ");
		sb.append("						<div class='widget-body'> ");
		sb.append("							<table class='table table-bordered table-striped'> ");
		sb.append("								<tbody> ");
		for (Object resumo : hashMapPrazoLimite.entrySet()) {
			String resumoSTR = resumo.toString();
			String[] arrayResumo = resumoSTR.split("=");
			for (int i = 0; i < arrayResumo.length; i++) {
				if (i >= 1)
					continue;
				sb.append("									<tr> ");
				if (arrayResumo[0].equalsIgnoreCase("Prazo Normal")) {
					sb.append("										<td class='center'>" + UtilI18N.internacionaliza(request, "solicitacaoservico.prazonormal") + "</td> ");
				} else if (arrayResumo[0].equalsIgnoreCase("Prazo a Vencer")) {
					sb.append("										<td class='center'>" + UtilI18N.internacionaliza(request, "solicitacaoservico.prazoavencer") + "</td> ");
				} else if (arrayResumo[0].equalsIgnoreCase("Prazo Vencido")) {
					sb.append("										<td class='center'>" + UtilI18N.internacionaliza(request, "solicitacaoservico.prazovencido") + "</td> ");
				} else {
					sb.append("										<td class='center'>" + arrayResumo[0] + "</td> ");
				}
				sb.append("										<td>" + arrayResumo[1] + "</td> ");
				sb.append("									</tr> ");
				if (arrayResumo[1] != null) {
					qtdPrazoLimite += Integer.parseInt(arrayResumo[1]);
				}
			}
		}
		sb.append("										<td class='center'><h4 class='blue'><b>" + UtilI18N.internacionaliza(request, "pagamentoProjeto.total") + "</b></h4></td> ");
		sb.append("										<td><h4 class='blue'><b>" + qtdPrazoLimite + "</b></h4></td> ");
		sb.append("								</tbody> ");
		sb.append("							</table> ");
		sb.append("						</div> ");
		sb.append("					</div> ");
		sb.append("				</div> ");
		sb.append("				<div class='span6'>	");
		sb.append("					<div class='widget'> ");
		sb.append("						<div class='widget-head'> ");
		sb.append("							<h4 class='heading'><i></i>" + UtilI18N.internacionaliza(request, "solicitacaoServico.prioridade") + "</h4> ");
		/* sb.append("							<a href='' class='details pull-right'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.prioridade") + "</a> "); */
		sb.append("						</div> ");
		sb.append("						<div class='widget-body'> ");
		sb.append("							<table class='table table-bordered table-striped'> ");
		sb.append("								<tbody> ");
		for (PrioridadeDTO dto : colResumoPorPrioridade) {
			sb.append("									<tr> ");
			sb.append("										<td class='center'>" + dto.getNomePrioridade() + "</td> ");
			sb.append("										<td>" + dto.getQuantidade() + "</td> ");
			sb.append("									</tr> ");
			if (dto.getQuantidade() != null) {
				qtdPrioridade += dto.getQuantidade();
			}
		}
		sb.append("										<td class='center'><h4 class='blue'><b>" + UtilI18N.internacionaliza(request, "pagamentoProjeto.total") + "</b></h4></td> ");
		sb.append("										<td><h4 class='blue'><b>" + qtdPrioridade + "</b></h4></td> ");
		sb.append("								</tbody> ");
		sb.append("							</table> ");
		sb.append("						</div> ");
		sb.append("					</div> ");
		sb.append("				</div> ");
		sb.append("			</div> ");
		sb.append("		</div> ");
		sb.append("	</div> ");
		sb.append("	<div class='row-fluid'> ");
		sb.append("		<div class='span12'> ");
		sb.append("			<div class='row-fluid'> ");
		sb.append("				<div class='span6'>	");
		sb.append("					<div class='widget'> ");
		sb.append("						<div class='widget-head'> ");
		sb.append("							<h4 class='heading'><i></i>" + UtilI18N.internacionaliza(request, "solicitacaoServico.tipo") + "</h4> ");
		/* sb.append("							<a href='' class='details pull-right'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.verTodos") + "</a> "); */
		sb.append("						</div> ");
		sb.append("						<div class='widget-body'> ");
		sb.append("							<table class='table table-bordered table-striped'> ");
		sb.append("								<tbody> ");
		for (TipoDemandaServicoDTO dto : colTipoSolicitacaoServico) {
			sb.append("									<tr> ");
			if (dto.getNomeTipoDemandaServico().equalsIgnoreCase("Incidente")) {
				sb.append("										<td class='center'>" + UtilI18N.internacionaliza(request, "grupovisao.incidente") + "</td> ");
			} else if (dto.getNomeTipoDemandaServico().equalsIgnoreCase("Requisição")) {
				sb.append("										<td class='center'>" + UtilI18N.internacionaliza(request, "requisitosla.requisicao") + "</td> ");
			} else {
				sb.append("										<td class='center'>" + dto.getNomeTipoDemandaServico() + "</td> ");
			}
			sb.append("										<td>" + dto.getQuantidade() + "</td> ");
			sb.append("									</tr> ");
			if (dto.getQuantidade() != null) {
				qtdTipoSolicitacao += dto.getQuantidade();
			}
		}
		sb.append("										<td class='center'><h4 class='blue'><b>" + UtilI18N.internacionaliza(request, "pagamentoProjeto.total") + "</b></h4></td> ");
		sb.append("										<td><h4 class='blue'><b>" + qtdTipoSolicitacao + "</b></h4></td> ");
		sb.append("								</tbody> ");
		sb.append("							</table> ");
		sb.append("						</div> ");
		sb.append("					</div> ");
		sb.append("				</div> ");
		sb.append("				<div class='span6'>	");
		sb.append("					<div class='widget'> ");
		sb.append("						<div class='widget-head'> ");
		sb.append("							<h4 class='heading'><i></i>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao") + "</h4> ");
		/* sb.append("							<a href='' class='details pull-right'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.verTodos") + "</a> "); */
		sb.append("						</div> ");
		sb.append("						<div class='widget-body'> ");
		sb.append("							<table class='table table-bordered table-striped'> ");
		sb.append("								<tbody> ");
		for (Enumerados.SituacaoSolicitacaoServico str : Enumerados.SituacaoSolicitacaoServico.values()) {
			if (HashMapSituacao.get(str) == null)
				continue;
			sb.append("									<tr> ");
			if (str.name().equalsIgnoreCase("EmAndamento")) {
				sb.append("										<td class='center'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.EmAndamento") + "</td> ");
			} else if (str.name().equalsIgnoreCase("Suspensa")) {
				sb.append("										<td class='center'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Suspensa") + "</td> ");
			} else if (str.name().equalsIgnoreCase("Reaberta")) {
				sb.append("										<td class='center'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Reaberta") + "</td> ");
			} else if (str.name().equalsIgnoreCase("Resolvida")) {
				sb.append("										<td class='center'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Resolvida") + "</td> ");
			} else if (str.name().equalsIgnoreCase("Cancelada")) {
				sb.append("										<td class='center'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Cancelada") + "</td> ");
			} else {
				sb.append("										<td class='center'>" + str + "</td> ");
			}
			sb.append("										<td>" + HashMapSituacao.get(str) + "</td> ");
			sb.append("									</tr> ");
			if (HashMapSituacao.get(str) != null) {
				qtdSituacao += (Integer) HashMapSituacao.get(str);
			}
		}
		sb.append("										<td class='center'><h4 class='blue'><b>" + UtilI18N.internacionaliza(request, "pagamentoProjeto.total") + "</b></h4></td> ");
		sb.append("										<td><h4 class='blue'><b>" + qtdSituacao + "</b></h4></td> ");
		sb.append("								</tbody> ");
		sb.append("							</table> ");
		sb.append("						</div> ");
		sb.append("					</div> ");
		sb.append("				</div> ");
		sb.append("			</div> ");
		sb.append("		</div> ");
		sb.append("	</div> ");
		sb.append("</div> ");

		tab3.setInnerHTML(sb.toString());

	}

	public Collection<TipoDemandaServicoDTO> resumoPorTipoSolicitacaoServico(List<TarefaFluxoDTO> listTarefas) throws Exception {
		Collection<TipoDemandaServicoDTO> colTipoDemandaServico = new ArrayList<TipoDemandaServicoDTO>();
		colTipoDemandaServico = getSolicitacaoServicoService(null).resumoTipoDemandaServico(listTarefas);
		return colTipoDemandaServico;
	}

	public Collection<PrioridadeDTO> resumoPorPrioridade(List<TarefaFluxoDTO> listTarefas) throws Exception {
		Collection<PrioridadeDTO> colPrioridade = new ArrayList<PrioridadeDTO>();
		colPrioridade = getSolicitacaoServicoService(null).resumoPrioridade(listTarefas);
		return colPrioridade;
	}

	public HashMap resumoPorSituacao(Collection<TarefaFluxoDTO> listTarefas) throws Exception {
		Integer qtdEmAndamento = 0;
		Integer qtdReaberta = 0;
		Integer qtdSuspensa = 0;
		Integer qtdResolvida = 0;
		Integer qtdCancelada = 0;
		HashMap<Enumerados.SituacaoSolicitacaoServico, Integer> hashMap = new HashMap<Enumerados.SituacaoSolicitacaoServico, Integer>();
		if (listTarefas != null) {
			for (TarefaFluxoDTO tarefasStr : listTarefas) {
				SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) tarefasStr.getSolicitacaoDto();
				if (solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("EmAndamento")) {
					qtdEmAndamento++;
				}
				if (solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Reaberta")) {
					qtdReaberta++;
				}
				if (solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Suspensa")) {
					qtdSuspensa++;
				}
				if (solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Resolvida")) {
					qtdResolvida++;
				}
				if (solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Cancelada")) {
					qtdCancelada++;
				}
			}
		}

		hashMap.put(Enumerados.SituacaoSolicitacaoServico.EmAndamento, qtdEmAndamento);
		hashMap.put(Enumerados.SituacaoSolicitacaoServico.Reaberta, qtdReaberta);
		hashMap.put(Enumerados.SituacaoSolicitacaoServico.Suspensa, qtdSuspensa);
		hashMap.put(Enumerados.SituacaoSolicitacaoServico.Resolvida, qtdResolvida);
		hashMap.put(Enumerados.SituacaoSolicitacaoServico.Cancelada, qtdCancelada);

		return hashMap;
	}

	public HashMap resumoPorPrazoLimite(Collection<TarefaFluxoDTO> listTarefas) throws Exception {
		HashMap hashMap = new HashMap();
		hashMap = getSolicitacaoServicoService(null).resumoPorPrazoLimite(listTarefas);
		return hashMap;
	}

	/**
	 * Atualiza e recarrega a lista de solicitações de serviços
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void atualizarLista(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		UsuarioDTO usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		this.setGerenciamentoServicosDTO((GerenciamentoServicosDTO) document.getBean());
		String paginaselecionada = document.getElementById("paginaselecionada").getValue();
		if (paginaselecionada != null) {
			this.getGerenciamentoServicosDTO().setPaginaSelecionada(Integer.parseInt(paginaselecionada));
		}
		this.recarregarLista(document, request, response);
		/**
		 * A linha foi adicionada porque estava fechando o modal e o load antes de recarregar a grid de solicitação. assim ele so vai fechar o modal depois que carregar a grid.
		 *
		 * @author maycon.fernandes
		 * @since 25/10/2013 14:35
		 */
		document.executeScript("fecharModal()");
	}

	/**
	 * FireEvent chamado para recarregar a Lista de Solicitação de Serviço de acordo com o Página Selecionada.
	 *
	 * @param document
	 *            - DocumentHTML
	 * @param request
	 *            - HttpServletRequest
	 * @param response
	 *            - HttpServletResponse
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 27.01.2015. Melhoria implementada na Operação Usain Bolt
	 */
	public void recarregarLista(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuarioLogado = WebUtil.getUsuario(request);

		if (usuarioLogado == null) {
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		GerenciamentoServicosDTO gerenciamentoServicosDTO = (GerenciamentoServicosDTO) document.getBean();

		StringBuilder sb = new StringBuilder();

		Integer itensPorPagina = (gerenciamentoServicosDTO.getItensPorPagina() == null ? Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.QUANT_RETORNO_PESQUISA, "5"))
				: gerenciamentoServicosDTO.getItensPorPagina());

		Integer paginaSelecionada = gerenciamentoServicosDTO.getPaginaSelecionada();

		if (paginaSelecionada == null)
			paginaSelecionada = 1;

		Integer tipoLista = (gerenciamentoServicosDTO.getTipoLista() == null ? 1 : gerenciamentoServicosDTO.getTipoLista());

		/** O método atualizarListaTarefasAndReturnTotalPaginas unificou as funcionalidades dos métodos atualizaListaTarefas e totalPaginas. Operação Usain Bolt. valdoilo.damasceno */
		Pageable pageable = new PageRequest(paginaSelecionada - 1, itensPorPagina);
		Integer totalPaginasFinal = this.getExecucaoSolicitacaoService().atualizarListaTarefasAndReturnTotalPaginas(usuarioLogado, this.getGerenciamentoServicosDTO(), pageable);

		renderizarLista(sb, request, itensPorPagina, paginaSelecionada, false, tipoLista);

		/**
		 * Alterado: Mário Motivo: Invertendo as posições para quantificar o total de páginas e definir no objeto de gerenciamentoDto Autor: flavio.santana Data/Hora: 13/11/2013
		 */
		paginaSelecionada = (totalPaginasFinal == 1 ? 1 : paginaSelecionada);

		HTMLElement divPrincipal = document.getElementById("esquerda");

		divPrincipal.setInnerHTML(sb.toString());

		document.executeScript("inicializaPopover()");

		carregarItensPaginacao(document, request, totalPaginasFinal);

		HTMLForm form = document.getForm("formGerenciamento");

		form.setValues(gerenciamentoServicosDTO);

		document.executeScript("fechaJanelaAguarde()");
	}

	/**
	 * Preenche os campos (Filtros) da tela de Gerenciamento de Serviços.
	 *
	 * @param sb
	 *            - StringBuilder
	 * @param tarefa
	 *            - TarefaFluxoDTO
	 * @param request
	 *            - HttpServletRequest
	 * @param flag
	 *            - boolean
	 * @throws Exception
	 */

	/** Alteração da assinatura do método e do local onde é chamado. Operação Usain Bolt - 27.01.2015 - carlos.santos */
	public void carregarItensFiltro(HttpServletRequest request) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			return;
		}

		ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);

		Collection<ContratoDTO> listContratoDtoAtivo = null;

		String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");
		if (COLABORADORES_VINC_CONTRATOS == null) {
			COLABORADORES_VINC_CONTRATOS = "N";
		}

		if (COLABORADORES_VINC_CONTRATOS != null && COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {
			listContratoDtoAtivo = getContratoService().findAtivosByIdEmpregado(usuario.getIdEmpregado());
		} else {
			listContratoDtoAtivo = getContratoService().listAtivos();
		}

		if (listContratoDtoAtivo != null && !listContratoDtoAtivo.isEmpty()) {

			for (ContratoDTO contratoDto : listContratoDtoAtivo) {

				String nomeCliente = "";
				String nomeFornecedor = "";

				ClienteDTO clienteDto = new ClienteDTO();

				clienteDto.setIdCliente(contratoDto.getIdCliente());

				clienteDto = (ClienteDTO) clienteService.restore(clienteDto);

				if (clienteDto != null) {
					nomeCliente = clienteDto.getNomeRazaoSocial();
				}

				FornecedorDTO fornecedorDto = new FornecedorDTO();

				fornecedorDto.setIdFornecedor(contratoDto.getIdFornecedor());

				fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);

				if (fornecedorDto != null) {
					nomeFornecedor = fornecedorDto.getRazaoSocial();
				}

				String nomeContrato = "" + contratoDto.getNumero() + " de " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoDto.getDataContrato(), WebUtil.getLanguage(request)) + " ("
						+ nomeCliente + " - " + nomeFornecedor + ")";

				contratoDto.setNome(nomeContrato);

				getHashContratos().add(contratoDto);
			}
		}

	}

	/**
	 * Carrega e atualiza os itens da paginação numerada e informa a quantidade de resultados Ex.: Primeiro « 1 2 3 4 5 » Último 1 De 7 Resultados
	 *
	 * @param document
	 * @param request
	 * @param totalPaginasFinal
	 */
	public void carregarItensPaginacao(DocumentHTML document, HttpServletRequest request, Integer totalPaginasFinal) throws Exception {
		StringBuilder sb = new StringBuilder();
		GerenciamentoServicosDTO gerenciamentoServicosDTO = (GerenciamentoServicosDTO) document.getBean();
		Integer paginaSelecionada = gerenciamentoServicosDTO.getPaginaSelecionada();
		if (paginaSelecionada == null)
			paginaSelecionada = 1;
		paginacaoGerenciamento(totalPaginasFinal, sb, paginaSelecionada, request);
		document.executeScript("carregarValorClasse(\"" + sb.toString() + "\", \"paginacaoGerenciamento\")");

		StringBuilder valores = new StringBuilder();
		carregarValoresPaginacao(totalPaginasFinal, valores, paginaSelecionada, request);
		document.executeScript("carregarValorClasse(\"" + valores.toString() + "\",\"paginacaoGerenciamentoQuantidade\")");
	}

	/**
	 * Realiza a regra de paginação das solicitações Explicação: Se o número de páginas for maior do que cinco, já é possível criar os intervalos. Se a página atual for menor do que cinco (o adjacente
	 * esta configurado com 2) é feito um laço. No for, enquanto a variável 'i' for menor do que seis os números são mostrados fazendo uma verificação para saber qual é a página atual que exige uma
	 * estilização diferente. Mas se a página atual for maior do que quatro e menor do que a última menos três, é uma página intermediária. Primeiro são anexadas a primeira e última páginas. Depois é
	 * feito um laço para definir as adjacentes. A variável 'adjacentes' recebeu neste código o valor dois. Para enteder melhor este laço vamos supor que estamos na página seis. A variável 'i' vai
	 * receber quatro (atual - adjacentes), enquanto ela for menor do que oito (atual + adjacentes) os números links gerados com uma verificação para saber qual é a página atual. Por fim são anexadas
	 * a última e penúltima páginas. O último else é para quando a página atual esta perto do final da numeração. São anexadas a primeira e última páginas além dos três pontos. A variável 'i' recebe o
	 * resultado da última página menos oito (4+2*2) enquanto não for menor ou igual a este número, os links são gerados.
	 *
	 * @param totalPaginas
	 * @param sb
	 * @param paginaSelecionada
	 * @param request
	 */
	public void paginacaoGerenciamento(Integer totalPaginas, StringBuilder sb, Integer paginaSelecionada, HttpServletRequest request) throws Exception {
		String locale = (String) request.getSession().getAttribute("locale");
		if (locale == null) {
			locale = "pt";
		}

		final Integer adjacentes = 2;
		if (paginaSelecionada == null)
			paginaSelecionada = 1;
		sb.append("	<div id='itenPaginacaoGerenciamento' class='pagination pagination-right margin-none'>");
		sb.append("		<ul>");
		sb.append("			<li " + (totalPaginas == 0 || paginaSelecionada == 1 ? "class='disabled'" : "value='1' onclick='paginarItens(this.value);'") + " ><a>"
				+ UtilI18N.internacionaliza(locale, "citcorpore.comum.primeiro") + "</a></li>");
		sb.append("			<li " + ((totalPaginas == 0 || totalPaginas == 1 || paginaSelecionada == 1) ? "class='disabled'" : "value='" + (paginaSelecionada - 1) + "' onclick='paginarItens(this.value);'")
				+ "><a>&laquo;</a></li>");
		if (totalPaginas <= 5) {
			for (int i = 1; i <= totalPaginas; i++) {
				if (i == paginaSelecionada) {
					sb.append("<li id='" + i + "' value='" + i + "' onclick='paginarItens(this.value);' class='active'><a >" + i + "</a></li> ");
				} else {
					sb.append("<li id='" + i + "' value='" + i + "' onclick='paginarItens(this.value);'><a >" + i + "</a></li> ");
				}
			}
		} else {
			if (totalPaginas > 5) {
				if (paginaSelecionada < 1 + (2 * adjacentes)) {
					for (int i = 1; i < 2 + (2 * adjacentes); i++) {
						if (i == paginaSelecionada) {
							sb.append("<li id='" + i + "' value='" + i + "' onclick='paginarItens(this.value);' class='active'><a >" + i + "</a></li> ");
						} else {
							sb.append("<li id='" + i + "' value='" + i + "' onclick='paginarItens(this.value);'><a >" + i + "</a></li> ");
						}
					}
				} else if (paginaSelecionada > (2 * adjacentes) && paginaSelecionada < totalPaginas - 3) {
					for (int i = paginaSelecionada - adjacentes; i <= paginaSelecionada + adjacentes; i++) {
						if (i == paginaSelecionada) {
							sb.append("<li id='" + i + "' value='" + i + "' onclick='paginarItens(this.value);' class='active'><a >" + i + "</a></li> ");
						} else {
							sb.append("<li id='" + i + "' value='" + i + "' onclick='paginarItens(this.value);'><a >" + i + "</a></li> ");
						}
					}
				} else {
					for (int i = totalPaginas - (/* 4 + */(2 * adjacentes)); i <= totalPaginas; i++) {
						if (i == paginaSelecionada) {
							sb.append("<li id='" + i + "' value='" + i + "' onclick='paginarItens(this.value);' class='active'><a >" + i + "</a></li> ");
						} else {
							sb.append("<li id='" + i + "' value='" + i + "' onclick='paginarItens(this.value);'><a >" + i + "</a></li> ");
						}
					}
				}
			}
		}
		sb.append("			<li "
				+ ((totalPaginas == 0 || totalPaginas == 1 || paginaSelecionada.equals(totalPaginas)) ? "class='disabled'" : "value='" + (paginaSelecionada + 1)
						+ "' onclick='paginarItens(this.value);'") + " ><a >&raquo;</a></li>");
		sb.append("			<li " + (totalPaginas == 0 || paginaSelecionada.equals(totalPaginas) ? "class='disabled'" : "value='" + totalPaginas + "' onclick='paginarItens(this.value);'") + " ><a >"
				+ UtilI18N.internacionaliza(locale, "citcorpore.comum.ultimo") + "</a></li> ");
		sb.append("		</ul>");
		sb.append("	</div>");
	}

	/**
	 * @param solicitacaoServicoDTO
	 * @param request
	 * @throws Exception
	 *             Seta o ribbon (Etiqueta) na lateral do box mostrando se está norma, suspensa, vencido ou menos de 1 hora p/ vencer
	 * **/
	private String setarHtmlPrazo(SolicitacaoServicoDTO solicitacaoServicoDTO, HttpServletRequest request) throws Exception {
		String htmlPrioridade = "";
		// VENCIDA
		if (solicitacaoServicoDTO.getAtrasoSLA() > 0 && !solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Cancelada")) {
			if (solicitacaoServicoDTO.getDataHoraLimite() != null) {
				Timestamp dataHoraLimite = solicitacaoServicoDTO.getDataHoraLimite();
				Timestamp dataHoraComparacao = UtilDatas.getDataHoraAtual();
				if (solicitacaoServicoDTO.encerrada())
					dataHoraComparacao = solicitacaoServicoDTO.getDataHoraFim();
				if (dataHoraComparacao != null) {
					if (dataHoraComparacao.compareTo(dataHoraLimite) > 0) {
						htmlPrioridade = "<div class='ribbon-wrapper small'><div class='ribbon danger'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.vencido") + "</div></div>";
					}
				}
			}
		} else {
			if (solicitacaoServicoDTO.getSituacao().equalsIgnoreCase(SituacaoSolicitacaoServico.Suspensa.toString())) {
				// SUSPENSA
				htmlPrioridade = "<div class='ribbon-wrapper small'><div class='ribbon'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.suspensa") + "</div></div>";
			} else if (UtilStrings.nullToVazio(solicitacaoServicoDTO.getSituacaoSLA()).equalsIgnoreCase("S")) {
				// MENOS DE 1zh
				htmlPrioridade = "<div class='ribbon-wrapper small'><div class='ribbon'>" + UtilI18N.internacionaliza(request, "gerenciaservico.sla.suspenso") + "</div></div>";
			} else if (solicitacaoServicoDTO.getFalta1Hora() == true) {
				// A VENCER
				htmlPrioridade = "<div class='ribbon-wrapper small'><div class='ribbon warning'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.avencer") + "</div></div>";
			} else {
				// NORMAL
				htmlPrioridade = "<div class='ribbon-wrapper small'><div class='ribbon success'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.normal") + "</div></div>";
			}
		}
		return htmlPrioridade;
	}

	/**
	 * @param solicitacaoServicoDTO
	 * @throws Exception
	 *             Seta o css (cor) em "prioridade" alterando a cor de acordo com a mesma
	 * **/
	private String setarCssPrioridade(String prioridade) throws Exception {
		String cssPrioridade = "";
		if (prioridade.equalsIgnoreCase("1") || prioridade.equalsIgnoreCase("2")) {
			cssPrioridade = "content-row prioridade Alta";
		}
		if (prioridade.equalsIgnoreCase("3")) {
			cssPrioridade = "content-row prioridade Media";
		}
		if (prioridade.equalsIgnoreCase("4") || prioridade.equalsIgnoreCase("5")) {
			cssPrioridade = "content-row prioridade Baixa";
		}
		return cssPrioridade;
	}

	/**
	 * @param solicitacaoServicoDTO
	 * @throws Exception
	 *             Seta o css (cor) em "prazo limite" do box mostrando se está norma, suspensa, vencido ou menos de 1 hora p/ vencer
	 * **/
	private String setarCssPrazo(SolicitacaoServicoDTO solicitacaoServicoDTO) throws Exception {
		String cssPrioridade = "";
		if (solicitacaoServicoDTO.getAtrasoSLA() > 0 && !solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Cancelada")) {
			if (solicitacaoServicoDTO.getDataHoraLimite() != null) {
				Timestamp dataHoraLimite = solicitacaoServicoDTO.getDataHoraLimite();
				Timestamp dataHoraComparacao = UtilDatas.getDataHoraAtual();
				if (solicitacaoServicoDTO.encerrada())
					dataHoraComparacao = solicitacaoServicoDTO.getDataHoraFim();
				if (dataHoraComparacao != null) {
					if (dataHoraComparacao.compareTo(dataHoraLimite) > 0) {
						cssPrioridade = "prazoVencido strong";

					}
				}
			}
		} else {
			if (solicitacaoServicoDTO.getFalta1Hora() == true) {
				cssPrioridade = "prazoAVencer strong";
			} else {
				cssPrioridade = "prazoNormal strong";
			}
		}
		return cssPrioridade;
	}

	/***
	 * Realiza o filtro de pesquisa da página de gerenciamento - Seta os itens do filtro no GerenciamentoServicosDTO - Recarrega a lista de solicitações de acordo com os filtros informados
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void pesquisarItensFiltro(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		this.setGerenciamentoServicosDTO((GerenciamentoServicosDTO) document.getBean());
		this.recarregarLista(document, request, response);
		document.executeScript("fecharModal()");
	}

	public void carregarValoresPaginacao(Integer totalPaginas, StringBuilder sb, Integer paginaSelecionada, HttpServletRequest request) {
		if (totalPaginas == 0)
			paginaSelecionada = 0;
		sb.append(paginaSelecionada + " " + UtilI18N.internacionaliza(request, "citcorpore.comum.de") + " " + totalPaginas + " " + UtilI18N.internacionaliza(request, "citcorpore.comum.paginas"));
	}

	public Set<ContratoDTO> getHashContratos() {
		return hashContratos;
	}

	public void setHashContratos(Set<ContratoDTO> hashContratos) {
		this.hashContratos = hashContratos;
	}

	public GerenciamentoServicosDTO getGerenciamentoServicosDTO() {
		return gerenciamentoServicosDTO;
	}

	public void setGerenciamentoServicosDTO(GerenciamentoServicosDTO gerenciamentoServicosDTO) {
		this.gerenciamentoServicosDTO = gerenciamentoServicosDTO;
	}

	public void abrirListaDeSubSolicitacoes(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		GerenciamentoServicosDTO gerenciamentoServicosDTO = (GerenciamentoServicosDTO) document.getBean();
		SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
		String html = "";
		SolicitacaoServicoService solicitacaoServicoService = getSolicitacaoServicoService(null);
		if (gerenciamentoServicosDTO != null && gerenciamentoServicosDTO.getIdSolicitacaoSel() != null) {
			solicitacaoServicoDto.setIdSolicitacaoServico(Integer.valueOf(gerenciamentoServicosDTO.getIdSolicitacaoSel()));
			SolicitacaoServicoDTO solicitacaoServicoAux = (SolicitacaoServicoDTO) solicitacaoServicoService.restore(solicitacaoServicoDto);
			Collection<SolicitacaoServicoDTO> colFinal = new ArrayList<SolicitacaoServicoDTO>();
			if (solicitacaoServicoAux != null) {
				if (solicitacaoServicoAux.getIdSolicitacaoRelacionada() != null) {
					solicitacaoServicoAux.setIdSolicitacaoServico(solicitacaoServicoAux.getIdSolicitacaoRelacionada());
					SolicitacaoServicoDTO solicitacaoServicoAux2 = (SolicitacaoServicoDTO) solicitacaoServicoService.restore(solicitacaoServicoAux);
					if (solicitacaoServicoAux2 != null) {
						colFinal.add(solicitacaoServicoAux2);
					}
				}
			}

			Collection<SolicitacaoServicoDTO> solicitacoesRelacionadas = solicitacaoServicoService.listSolicitacaoServicoRelacionadaPai(solicitacaoServicoDto.getIdSolicitacaoServico());
			if (solicitacoesRelacionadas != null) {
				colFinal.addAll(solicitacoesRelacionadas);
			}

			StringBuilder script = new StringBuilder();

			if (solicitacaoServicoAux != null && solicitacaoServicoAux.getIdSolicitacaoRelacionada() != null)
				html = this.gerarHtmlComListaSubSolicitacoes(colFinal, script, request, solicitacaoServicoAux.getIdSolicitacaoRelacionada());
			else
				html = this.gerarHtmlComListaSubSolicitacoes(colFinal, script, request, 0);

			document.getElementById("solicitacaoRelacionada").setInnerHTML(html);
		}

		solicitacaoServicoDto = null;
	}

	private String gerarHtmlComListaSubSolicitacoes(Collection<SolicitacaoServicoDTO> listSolicitacaoServicoRelacionada, StringBuilder script, HttpServletRequest request, int idsolicitacaoPai) {
		StringBuilder html = new StringBuilder();

		html.append("<table class='dynamicTable table table-striped table-bordered table-condensed dataTable' width='100%'");
		html.append("<tr>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.numerosolicitacao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.dataabertura") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.prazo") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.descricao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.resposta") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.prazoLimite") + "</th>");
		html.append("</tr>");

		if (listSolicitacaoServicoRelacionada != null && !listSolicitacaoServicoRelacionada.isEmpty()) {

			for (SolicitacaoServicoDTO solicitacaoServicoRelacionada : listSolicitacaoServicoRelacionada) {
				if (idsolicitacaoPai != solicitacaoServicoRelacionada.getIdSolicitacaoServico()) {
					html.append("<tr>");
					html.append("<hidden id='idSolicitante' value='" + solicitacaoServicoRelacionada.getIdSolicitante() + "'/>");
					html.append("<hidden id='idResponsavel' value='" + solicitacaoServicoRelacionada.getIdResponsavel() + "'/>");
					html.append("<td style='text-align: center;'>" + solicitacaoServicoRelacionada.getIdSolicitacaoServico() + "</td>");
					html.append("<td id='dataHoraSolicitacao'>" + UtilDatas.formatTimestamp(solicitacaoServicoRelacionada.getDataHoraSolicitacao()) + "</td>");
					html.append("<td>" + solicitacaoServicoRelacionada.getPrazoHH() + ":" + solicitacaoServicoRelacionada.getPrazoMM() + "</td>");
					html.append("<td>" + solicitacaoServicoRelacionada.getDescricao() + "</td>");
					html.append("<td>" + (solicitacaoServicoRelacionada.getResposta() != null ? solicitacaoServicoRelacionada.getResposta() : "-") + "</td>");
					if (solicitacaoServicoRelacionada.getSituacao().equals("EmAndamento")) {
						html.append("<td>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.EmAndamento") + "</td>");
					} else if (solicitacaoServicoRelacionada.getSituacao().equals("Suspensa")) {
						html.append("<td>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Suspensa") + "</td>");
					} else if (solicitacaoServicoRelacionada.getSituacao().equals("Cancelada")) {
						html.append("<td>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Cancelada") + "</td>");
					} else if (solicitacaoServicoRelacionada.getSituacao().equals("Resolvida")) {
						html.append("<td>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Resolvida") + "</td>");
					} else {
						html.append("<td>" + solicitacaoServicoRelacionada.getSituacao() + "</td>");
					}

					if (solicitacaoServicoRelacionada.getDataHoraLimite() != null)
						html.append("<td>" + UtilDatas.formatTimestamp(solicitacaoServicoRelacionada.getDataHoraLimite()) + "</td>");
					else
						html.append("<td>À combinar</td>");

					html.append("</tr>");
				}
			}
		}
		html.append("</table>");
		return html.toString();
	}

	public void abrirListaDeItensDeConfiguracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GerenciamentoServicosDTO gerenciamentoServicosDTO = (GerenciamentoServicosDTO) document.getBean();
		SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

		if (gerenciamentoServicosDTO != null && gerenciamentoServicosDTO.getIdSolicitacaoSel() != null) {
			solicitacaoServicoDto.setIdSolicitacaoServico(Integer.valueOf(gerenciamentoServicosDTO.getIdSolicitacaoSel()));
			SolicitacaoServicoDTO solicitacaoServicoAux = (SolicitacaoServicoDTO) solicitacaoServicoService.restore(solicitacaoServicoDto);

			if (solicitacaoServicoDto != null) {
				ItemCfgSolicitacaoServService serviceItem = (ItemCfgSolicitacaoServService) ServiceLocator.getInstance().getService(ItemCfgSolicitacaoServService.class, null);
				ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);

				Collection<ItemCfgSolicitacaoServDTO> col = (Collection<ItemCfgSolicitacaoServDTO>) serviceItem.findByIdSolicitacaoServico(solicitacaoServicoAux.getIdSolicitacaoServico());

				if (col != null && !col.isEmpty()) {
					for (ItemCfgSolicitacaoServDTO itemCfgSolicitacaoServAux : col) {
						ItemConfiguracaoDTO itemConfiguracaoAux = new ItemConfiguracaoDTO();
						itemConfiguracaoAux.setIdItemConfiguracao(itemCfgSolicitacaoServAux.getIdItemConfiguracao());
						itemConfiguracaoAux = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemConfiguracaoAux);
						if (itemConfiguracaoAux != null) {
							itemCfgSolicitacaoServAux.setIdentificacaoStatus(itemConfiguracaoAux.getIdentificacaoStatus());
							itemCfgSolicitacaoServAux.setIdentificacao(itemConfiguracaoAux.getIdentificacao());
						}
					}
				}

				StringBuilder script = new StringBuilder();

				String html = this.gerarHtmlComListaItensConfiguracao(col, gerenciamentoServicosDTO.getIdSolicitacaoSel(), script, request);

				document.getElementById("MudancaSolicitacao").setInnerHTML(html);
			}
		}

		solicitacaoServicoDto = null;
	}

	private String gerarHtmlComListaItensConfiguracao(Collection<ItemCfgSolicitacaoServDTO> listItemCfgSolicitacaoServDTO, String numeroSolicitacaoPai, StringBuilder script, HttpServletRequest request) {
		StringBuilder html = new StringBuilder();

		html.append("<table class='dynamicTable table table-striped table-bordered table-condensed dataTable' width='100%'>");
		html.append("<tr>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.numero") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.identificacao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.informacao") + "</th>");
		html.append("</tr>");

		if (listItemCfgSolicitacaoServDTO != null && !listItemCfgSolicitacaoServDTO.isEmpty()) {

			for (ItemCfgSolicitacaoServDTO dto : listItemCfgSolicitacaoServDTO) {
				html.append("<tr>");
				html.append("<hidden id='idItemConfiguracao' value='" + dto.getIdItemConfiguracao() + "'/>");
				html.append("<hidden id='idIdentificacaoItem' value='" + dto.getIdentificacaoStatus() + "'/>");
				html.append("<td>" + dto.getIdItemConfiguracao() + "</td>");
				html.append("<td>" + dto.getIdentificacao() + "</td>");
				html.append("<td>" + "<a class='btn-action glyphicons circle_info btn-default' onclick='popupAtivos(\"" + dto.getIdItemConfiguracao() + "\")'><i></i></a>" + "</td>");
				html.append("</tr>");
			}
		}
		html.append("</table>");
		return html.toString();
	}

	public void buscarItemTtrabalhoSolicitacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GerenciamentoServicosDTO gerenciamentoServicosDTO = (GerenciamentoServicosDTO) document.getBean();
		SolicitacaoServicoDTO valor = getSolicitacaoServicoService(null).buscarNumeroItemTrabalhoPorNumeroSolicitacao(Integer.valueOf(gerenciamentoServicosDTO.getIdSolicitacaoSel()));
		document.executeScript("visualizarSolicitacaoPaiEnvio(" + Integer.valueOf(gerenciamentoServicosDTO.getIdSolicitacaoSel()) + "," + valor.getIdItemFluxoTrabalho() + ");");
	}

	public void chamaComboOrigem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
		HTMLSelect selectOrigem = (HTMLSelect) document.getSelectById("idOrigem");
		selectOrigem.removeAllOptions();
		List<OrigemAtendimentoDTO> todasOrigens = (List) origemAtendimentoService.list();
		List<OrigemAtendimentoDTO> origensNaoExcluidas = new ArrayList<OrigemAtendimentoDTO>();
		String origemPadrao = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_PADRAO, "");

		selectOrigem.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (todasOrigens != null) {
			for (OrigemAtendimentoDTO origemAtendimento : todasOrigens) {
				if (origemAtendimento.getDataFim() == null) {
					origensNaoExcluidas.add(origemAtendimento);
				}
			}
			selectOrigem.addOptions(origensNaoExcluidas, "idOrigem", "descricao", origemPadrao);
		}
	}

	private ContratoService getContratoService() throws Exception {
		if (contratoService == null) {
			contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		}
		return contratoService;
	}

	protected ExecucaoSolicitacaoService getExecucaoSolicitacaoService() throws Exception {
		if (execucaoSolicitacaoService == null) {
			execucaoSolicitacaoService = (ExecucaoSolicitacaoService) ServiceLocator.getInstance().getService(ExecucaoSolicitacaoService.class, null);
		}
		return execucaoSolicitacaoService;
	}

	private SolicitacaoServicoService getSolicitacaoServicoService(HttpServletRequest request) throws Exception {
		if (solicitacaoServicoService == null) {
			if (request == null) {
				solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
			} else {
				solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
			}
		}
		return solicitacaoServicoService;
	}

}
