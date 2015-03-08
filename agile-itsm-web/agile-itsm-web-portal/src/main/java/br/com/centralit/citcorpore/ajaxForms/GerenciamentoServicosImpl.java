package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.GerenciamentoServicosDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citcorpore.util.Enumerados.ItensPorPagina;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.core.PageRequest;
import br.com.citframework.integracao.core.Pageable;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

/**
 * Esta classe é utilizada apenas para a Renderização da Paginação.
 */
@SuppressWarnings({ "rawtypes", "unused" })
public class GerenciamentoServicosImpl extends GerenciamentoServicos implements GerenciamentoProcessos {

	private static boolean verificaGrupoUsuario;

	public void iniciar(StringBuilder sb, HttpServletRequest request, Integer itensPorPagina, Integer paginaSelecionada, Integer tipoLista) throws Exception {
		// validação necessária de usuário graças ao uso da taglib GerenciamentoField, se não tiver usuário logado, retornar para a taglib
		UsuarioDTO usuarioLogado = (UsuarioDTO) request.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE");

		if (usuarioLogado == null) {
			return;
		}

		criarScriptPaginacao(sb);

		/** O método atualizarListaTarefasAndReturnTotalPaginas unificou as funcionalidades dos métodos atualizaListaTarefas e totalPaginas. 27.01.2015. Operação Usain Bolt. valdoilo.damasceno */
		Pageable pageable = new PageRequest(paginaSelecionada - 1, itensPorPagina);
		Integer totalPaginasFinal = super.getExecucaoSolicitacaoService().atualizarListaTarefasAndReturnTotalPaginas(usuarioLogado, this.getGerenciamentoServicosDTO(), pageable);

		carregarCabecalhoGerenciamento(totalPaginasFinal, sb, paginaSelecionada, request, tipoLista);
		renderizarLista(sb, request, itensPorPagina, paginaSelecionada, true, tipoLista);
		renderizarFiltroPesquisa(sb, request, true);
		carregarRodapeGerenciamento(totalPaginasFinal, sb, paginaSelecionada, request);
	}

	public void renderizarFiltroPesquisa(StringBuilder sb, HttpServletRequest request, boolean flag) throws ServiceException, Exception {
		TipoDemandaServicoService tipoDemandaService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		Collection<GrupoDTO> listaGruposUsuario = grupoService.listGruposPorUsuario(usuario.getIdUsuario());
		HttpSession session = ((HttpServletRequest) request).getSession(true);
		String locale = (String) request.getSession().getAttribute("locale");
		if (locale == null) {
			locale = "pt";
		}

		verificaGrupoUsuario = false;
		for (GrupoDTO grupoDTO : listaGruposUsuario) {
			if (grupoDTO.getPermiteSuspensaoReativacao() != null && grupoDTO.getPermiteSuspensaoReativacao().equalsIgnoreCase("S")) {
				verificaGrupoUsuario = true;
				break;
			}
		}

		Collection<GrupoDTO> listGrupoExecutor = grupoService.getGruposByIdEmpregado(WebUtil.getUsuario(request).getIdEmpregado());
		/*
		 * Desenvolvedor: Pedro Lino - Data: 08/11/2013 - Horário: 11:00 - ID Citsmart: 120948 - Motivo/Comentário: Retirado botão de busca na pagina a pedido do Jorge Santos(Consultoria)
		 */
		StringBuilder sbFiltro = new StringBuilder();
		if (flag) {
			sbFiltro.append("<div class='span12 filtro filtrobar main'>");
			sbFiltro.append("	<div class='row-fluid' >");
			sbFiltro.append("		<div class='span5'>");
			sbFiltro.append("			<div class='row-fluid'>");
			sbFiltro.append("				<div class='span4'>");
			sbFiltro.append("					<label>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.visualizar") + "</label>");
			sbFiltro.append("					<select name='tipoVisualizacao' id='tipoVisualizacao' class='span10' onChange = 'pesquisarItensFiltro();' >");
			sbFiltro.append("						<option value='-1'>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.todos") + "</option>");
			sbFiltro.append("						<option value='possoExecutar'>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.possoExecutar") + "</option>");
			sbFiltro.append("						<option value='possoVisualizar'>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.possoVisualizar") + "</option>");
			sbFiltro.append("					</select>");
			sbFiltro.append("				</div>");

			sbFiltro.append("				<div class='span4'>");
			sbFiltro.append("					<label>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.situacaoSla") + "</label>");
			sbFiltro.append("					<select name='situacaoSla' id='situacaoSla' class='span10' onChange = 'pesquisarItensFiltro();' >");
			sbFiltro.append("						<option value=''>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.todos") + "</option>");
			sbFiltro.append("						<option value='vencido'>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.vencido") + "</option>");
			sbFiltro.append("						<option value='aguardandoAprovacao'>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.aguardandoAprovacao") + "</option>");

			sbFiltro.append("						<option value='avencer30min'>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.avencer30min") + "</option>");
			sbFiltro.append("						<option value='avencer60min'>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.avencer60min") + "</option>");
			sbFiltro.append("						<option value='avencer90min'>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.avencer90min") + "</option>");
			sbFiltro.append("						<option value='avencer2h'>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.avencer2h") + "</option>");
			sbFiltro.append("						<option value='avencer3h'>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.avencer3h") + "</option>");
			sbFiltro.append("						<option value='avencerHoje'>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.avencerHoje") + "</option>");
			if (ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_CALENDARIO_PADRAO, null) != null) {
				sbFiltro.append("					<option value='avencerProxDiaUtil'>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.avencerProxDiaUtil") + "</option>");
			}

			sbFiltro.append("					</select>");
			sbFiltro.append("				</div>");

			sbFiltro.append("				<div class='span4'>");
			sbFiltro.append("					<label>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.ordenarPor") + "</label>");
			sbFiltro.append("					<select name='ordenarPor' id='ordenarPor' class='span10' onChange = 'pesquisarItensFiltro();' >");
			sbFiltro.append("						<option value='-1'>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.todos") + "</option>");
			sbFiltro.append("						<option value='NSolicitacao'>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.NSolicitacao") + "</option>");
			sbFiltro.append("						<option value='servico'>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.servico") + "</option>");
			sbFiltro.append("						<option value='responsavel'>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.responsavel") + "</option>");
			sbFiltro.append("						<option value='prioridade'>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.prioridade") + "</option>");
			sbFiltro.append("						<option value='situacao'>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.situacao") + "</option>");
			sbFiltro.append("						<option value='descricao'>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.descricao") + "</option>");
			sbFiltro.append("						<option value='dataHoraLimite'>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.dataHoraLimite") + "</option>");
			sbFiltro.append("						<option value='dataHoraLimiteCriacao'>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.dataHoraLimiteCriacao") + "</option>");
			sbFiltro.append("					</select>");
			sbFiltro.append("				</div>");
			sbFiltro.append("			</div>");
			sbFiltro.append("		</div>");

			sbFiltro.append("		<div class='span7'>");
			sbFiltro.append("			<div class='topfiltro'>");
			sbFiltro.append("				<div>");
			sbFiltro.append("					<label><br/></label>");
			sbFiltro.append("					<li style = 'margin-left: 0%;' id='acoes' class='btn-group btn-block span3'>");
			sbFiltro.append("			    		<div class='leadcontainer' data-toggle='dropdown' re='dropdownFiltro'>");
			sbFiltro.append("							<button type='button' id='idbotaoBuscar' name='idbotaoBuscar' class='btn dropdown-lead btn-default'>"
					+ UtilI18N.internacionaliza(locale, "citcorpore.comum.maisfiltros") + "</button>");
			sbFiltro.append("						</div>");
			sbFiltro.append("						<a class='btn btn-default dropdown-toggle filtro-toogle' href='#' data-toggle='dropdown' re='dropdownFiltro' ><span class='caret'></span> </a>");
			sbFiltro.append("						<ul class='dropdownFiltro dropdown-menu pull-left'>");
			sbFiltro.append("							<li class='dropdownFiltroPesquisa'>");

			sbFiltro.append("								<!-- Inicio Filtro -->");
			sbFiltro.append("								<div class='row-fluid'>");
			sbFiltro.append("									<div class='span12'>");
			sbFiltro.append("										<div class='row-fluid'>");
			sbFiltro.append("											<div class='span3'>");
			sbFiltro.append("												<label>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.numeroSolicitacao") + "</label>");
			sbFiltro.append("												<input name='idSolicitacao' onkeydown='if ( event.keyCode == 13 ){ pesquisarItensFiltro();  fechaWindow()}' maxlength='9'  type='text'  >");
			sbFiltro.append("											</div>");

			sbFiltro.append("											<div class='span9'>");
			sbFiltro.append("												<label>" + UtilI18N.internacionaliza(locale, "contrato.contrato") + "</label>");
			sbFiltro.append("												<select name='idContrato' required class='span12' >");
			if (this.getHashContratos().size() < 1) {
				sbFiltro.append("												<option value='-1' selected>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.todos") + "</option>");
			} else {
				sbFiltro.append("												<option value='-1'>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.todos") + "</option>");
				if (this.getHashContratos().size() == 1) {
					Iterator iter = this.getHashContratos().iterator();
					ContratoDTO contratoDTO = (ContratoDTO) iter.next();
					sbFiltro.append("											<option value='" + contratoDTO.getIdContrato() + "' selected>" + contratoDTO.getNome() + "</option>");
				} else {
					for (ContratoDTO element : this.getHashContratos()) {
						sbFiltro.append("										<option value='" + element.getIdContrato() + "'>" + element.getNome() + "</option>");
					}
				}
			}
			sbFiltro.append("												</select>");
			sbFiltro.append("											</div>");
			sbFiltro.append("										</div>");

			sbFiltro.append("										<div class='row-fluid'>");
			sbFiltro.append("											<div class='span3'>");
			sbFiltro.append("												<label>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.tipo") + "</label>");
			sbFiltro.append("												<select name='idTipo' class='span12' >");
			sbFiltro.append("													<option value='-1'>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.todos") + "</option>");

			Collection<TipoDemandaServicoDTO> col = tipoDemandaService.listSolicitacoes();
			if (col != null) {
				for (TipoDemandaServicoDTO element : col) {
					sbFiltro.append("											<option value='" + element.getIdTipoDemandaServico() + "'>" + element.getNomeTipoDemandaServico() + "</option>");
				}
			}
			sbFiltro.append("												</select>");
			sbFiltro.append("											</div>");

			sbFiltro.append("											<div class='span9'>");
			sbFiltro.append("												<label>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.solicitante") + "</label>");
			sbFiltro.append("												<input id='idSolicitante' name='idSolicitante' type='hidden' class='span12 marginless' />");
			sbFiltro.append("											</div>");
			sbFiltro.append("										</div>");

			sbFiltro.append("										<div class='row-fluid'>");
			sbFiltro.append("											<div class='span9'>");
			sbFiltro.append("												<label>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.responsavel") + "</label>");
			sbFiltro.append("												<input id='idResponsavelAtual' name='idResponsavelAtual' type='hidden' class='span12 marginless' />");
			sbFiltro.append("											</div>");

			sbFiltro.append("											<div class='span3'>");
			sbFiltro.append("												<label>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.grupoExecutor") + "</label>");
			sbFiltro.append("												<select name='idGrupoAtual' class='span12' >");
			sbFiltro.append("													<option value='-1'>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.todos") + "</option>");
			sbFiltro.append("													<option value='0'>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.sematribuicao") + "</option>");
			if (listGrupoExecutor != null) {
				for (GrupoDTO element : listGrupoExecutor) {
					sbFiltro.append("												<option value='" + element.getIdGrupo() + "'>" + element.getNome() + "</option>");
				}
			}
			sbFiltro.append("												</select>");
			sbFiltro.append("											</div>");
			sbFiltro.append("										</div>");

			sbFiltro.append("										<div class='row-fluid'>");
			sbFiltro.append("											<div class='span4'>");
			sbFiltro.append("												<label>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.tarefaatual.desc") + "</label>");
			sbFiltro.append("												<input id='tarefaAtual' name='tarefaAtual' type='hidden' class='span12 marginless' />");
			sbFiltro.append("											</div>");

			sbFiltro.append("											<div class='span4'>");
			sbFiltro.append("												<label>" + UtilI18N.internacionaliza(locale, "categoriaProduto.categoria_situacao") + "</label>");
			sbFiltro.append("												<select id='situacao' name='situacao' class='span12' >");
			sbFiltro.append("													<option value=''>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.todos") + "</option>");
			sbFiltro.append("													<option value='EmAndamento'>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.situacao.EmAndamento") + "</option>");
			sbFiltro.append("													<option value='Reaberta'>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.reaberta") + "</option>");
			sbFiltro.append("													<option value='Resolvida'>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.resolvida") + "</option>");
			sbFiltro.append("													<option value='Suspensa'>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.situacao.Suspensa") + "</option>");
			sbFiltro.append("													<option value='Cancelada'>" + UtilI18N.internacionaliza(locale, "solicitacaoServico.situacao.Cancelada") + "</option>");
			sbFiltro.append("												</select>");
			sbFiltro.append("											</div>");

			sbFiltro.append("											<div class='span4'>");
			sbFiltro.append("												<label>" + UtilI18N.internacionaliza(locale, "gerenciamentoservico.palavrasChave") + "</label>");
			sbFiltro.append("												<input name='palavraChave' onkeydown=\"if ( event.keyCode == 13 ) {pesquisarItensFiltro(); fechaWindow()}\" type='text' class='palavrasChave span12'>");
			sbFiltro.append("											</div>");
			sbFiltro.append("										</div>");

			sbFiltro.append("										<div class='row-fluid'>");
			sbFiltro.append("											<div class='span3'>");
			sbFiltro.append("												<button type='button' onclick='pesquisarItensFiltro();motrarQuandoFiltroEstiverAtivo();' class='btn btn-icon btn-primary glyphicons search'><i></i>"
					+ UtilI18N.internacionaliza(locale, "citcorpore.comum.buscar") + "</button>");
			sbFiltro.append("											</div>");
			sbFiltro.append("										</div>");
			sbFiltro.append("									</div>");
			sbFiltro.append("								</div>");
			sbFiltro.append("						<!-- Fim Filtro -->	");
			sbFiltro.append("							</li>");
			sbFiltro.append("						</ul>");
			sbFiltro.append("					</li>");
			sbFiltro.append("				</div>");
			sbFiltro.append("			</div>");

			sbFiltro.append("			<div class='topfiltro'>");
			sbFiltro.append("				<div class='leadcontainer'>");
			sbFiltro.append("					<button type='button' class='btn btn-default' id='btnPesquisaAvancada' onclick='chamarPesquisaSolicitacoes()'>"
					+ UtilI18N.internacionaliza(locale, "citcorpore.comum.pesquisaAvancada") + "</button>");
			sbFiltro.append("					<button type='button' class='btn btn-default' id='btnAgendaGrid' onclick='chamarAgendaGrid()'>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.agenta")
					+ "</button>");
			if (verificaGrupoUsuario == true) {
				sbFiltro.append("				<button type='button' class='btn btn-default' id='btnSuspensaoReativacao' onclick='chamarSuspenderReativarSolicitacaoGrid()'>"
						+ UtilI18N.internacionaliza(locale, "suspensaoReativacaoSolicitacao.tituloBotaoSolicitacao") + "</button>");
			}

			sbFiltro.append("				</div>");
			sbFiltro.append("			</div>");

			sbFiltro.append("		</div>");
			sbFiltro.append("	</div>");
			sbFiltro.append("</div>");

			sb.insert(0, sbFiltro.toString());
		}
	}

	public synchronized void carregarCabecalhoGerenciamento(Integer totalPaginas, StringBuilder sb, Integer paginaSelecionada, HttpServletRequest request, Integer tipoLista) throws Exception {
		// isto é necessário para passar o locale correto da seção para evitar problemas com concorrência
		String locale = (String) request.getSession().getAttribute("locale");
		if (locale == null) {
			locale = "pt";
		}
		/* Inicio Abertura da div maior */
		sb.append("<div class='row-fluid' >");
		sb.append("		<div class='span12'>");
		/* Inicio Abertura da div maior */

		sb.append("	<input type='hidden' id='paginaSelecionada' name='paginaSelecionada' />");
		sb.append("	<input type='hidden' id='quantidadeAtrasadas' name='quantidadeAtrasadas' />");
		sb.append("	<input type='hidden' id='quantidadeTotal' name='quantidadeTotal' />");
		sb.append("	<input type='hidden' id='nomeCampoOrdenacao' name='nomeCampoOrdenacao' />");
		sb.append("	<input type='hidden' id='ordenacaoAsc' name='ordenacaoAsc' />");
		sb.append("	<input type='hidden' id='tipoLista' name='tipoLista' value='" + (tipoLista == null ? 1 : tipoLista) + "' />");
		sb.append("<div id='titulo' >");
		sb.append("		<div class='row-fluid inicio'>");
		sb.append("			<div class='span6'>");
		sb.append("				<span class='btn btn-icon btn-primary' onclick='modalNovaSolicitacaoServico()'><i></i>" + UtilI18N.internacionaliza(request, "gerenciaservico.novasolicitacao") + "</span>");
		sb.append("				<button class='btn btn-default btn-primary' type='button' onclick='pesquisarItensFiltro();'><i class='icon-white icon-refresh'></i></button>");
		sb.append("				<div class='uniformjs atualizar'>");
		sb.append("      			<label class='checkbox'><div class='checker' id='uniform-undefined'><span class=''><input type='checkbox' name='chkAtualiza' id='chkAtualiza' value='X' style='opacity: 0;'></span></div> "
				+ UtilI18N.internacionaliza(request, "citcorpore.comum.atualizar") + "&nbsp;" + UtilI18N.internacionaliza(request, "citcorpore.comum.automaticamente") + " </label>");
		sb.append("				</div>");
		sb.append("			</div>");
		sb.append("			<div class='span6 paginacaoGerenciamento'>");

		paginacaoGerenciamento(totalPaginas, sb, paginaSelecionada, request);

		sb.append("			</div>");
		sb.append("		</div>");
		sb.append("		<div class='row-fluid inicio'>");
		sb.append("			<div class='span8'>");
		sb.append("				<div class='lFloat rMargin'>");
		if (tipoLista != null) {
			sb.append("					<a id='listaDetalhada' class='btn " + (tipoLista.equals(1) ? "btn-primary" : "") + "'>");
			sb.append("					<i class='icon-list lPadding " + (tipoLista.equals(1) ? "icon-white" : "") + "'></i>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.detalhado") + "</a>");
			sb.append("				</div>");
			sb.append("				<div class='lFloat rMargin'>");
			sb.append("					<a id='listaResumida' class='btn " + (tipoLista.equals(2) ? "btn-primary" : "") + "'>");
			sb.append("					<i class='icon-th-list lPadding " + (tipoLista.equals(2) ? "icon-white" : "") + "'></i>" + UtilI18N.internacionaliza(locale, "citcorpore.comum.resumido") + "</a>");
		}

		sb.append("				</div>");
		sb.append("				<select id='itensPorPagina' name='itensPorPagina' onchange='atualizarListaPorQtdItens();' class='span1 lFloat marginless' >");
		for (ItensPorPagina valor : ItensPorPagina.values()) {
			sb.append("	<option>" + valor.getValor() + "</option>");
		}
		sb.append("				</select> ");
		sb.append(UtilI18N.internacionaliza(locale, "citcorpore.comum.verporpagina"));
		sb.append("			</div>");
		sb.append("			<div class='span4 right paginacaoGerenciamentoQuantidade'>");
		carregarValoresPaginacao(totalPaginas, sb, paginaSelecionada, request);
		sb.append("			</div>");
		sb.append("		</div>");
		sb.append("</div>");
	}

	public void carregarRodapeGerenciamento(Integer totalPaginas, StringBuilder sb, Integer paginaSelecionada, HttpServletRequest request) throws Exception {
		sb.append("<div id='sub'>");
		sb.append("		<div class='row-fluid inicio'>");
		sb.append("			<div class='span12 right paginacaoGerenciamento'>");

		paginacaoGerenciamento(totalPaginas, sb, paginaSelecionada, request);

		sb.append("			</div>");
		sb.append("		</div>");
		sb.append("		<div class='row-fluid'>");
		sb.append("			<div class='span12 right paginacaoGerenciamentoQuantidade'>");

		carregarValoresPaginacao(totalPaginas, sb, paginaSelecionada, request);

		sb.append("			</div>");
		sb.append("		</div>");
		sb.append("</div>");
		/* Inicio Fechamento da div maior */
		sb.append("		</div>");
		sb.append("</div>");
		/* Fim Fechamento da div maior */
	}

	/**
	 * Cria os scipts que serão usados na tela de gerenciamento de serviços
	 *
	 * @param sb
	 */
	public void criarScriptPaginacao(StringBuilder sb) {
		sb.append("<script type='text/javascript'>");
		sb.append("	function paginarItens(paginaSelecionada) {");
		sb.append("		if (paginaSelecionada <= -1) {");
		sb.append("		paginaSelecionada = 1; }");
		sb.append("		janelaAguarde();");
		sb.append("		document.formGerenciamento.paginaSelecionada.value = paginaSelecionada;");
		sb.append("		document.formGerenciamento.fireEvent('paginarItens');");
		sb.append("	}");
		sb.append("	function atualizarLista() {");
		sb.append("		janelaAguarde();");
		sb.append("		document.form.fireEvent('atualizarLista');");
		sb.append("	}");
		sb.append("	function atualizarListaPorQtdItens() {");
		sb.append("		janelaAguarde();");
		sb.append("		$('#paginaSelecionada').val('1');");
		sb.append("		document.formGerenciamento.fireEvent('atualizarLista');");
		sb.append("	}");
		sb.append("	function atualizarListaPorOrdem(ordenacao, ordem) {");
		sb.append("		janelaAguarde();");
		sb.append("		document.formGerenciamento.ordenacaoAsc.value = ordem;");
		sb.append("		document.formGerenciamento.nomeCampoOrdenacao.value = ordenacao;");
		sb.append("		document.formGerenciamento.fireEvent('atualizarLista');");
		sb.append("	}");
		sb.append("	function janelaAguarde() {");
		sb.append("		JANELA_AGUARDE_MENU.show();");
		sb.append("	}");
		sb.append("	function fechaJanelaAguarde() {");
		sb.append("		JANELA_AGUARDE_MENU.hide();");
		sb.append("	}");
		sb.append("function carregarValorClasse(valor, classe) {");
		sb.append("		divs = document.getElementsByClassName(classe);");
		sb.append("		[].slice.call( divs ).forEach(function ( div ) {");
		sb.append("		div.innerHTML = valor;");
		sb.append("	});");
		sb.append("}");
		sb.append("	function refreshTelaGerenciamento() {");
		sb.append("		JANELA_AGUARDE_MENU.show();");
		sb.append("		document.formGerenciamento.fireEvent('pesquisarItensFiltro');");
		sb.append("	}");
		sb.append("	function pesquisarItensFiltro() {");
		sb.append("		motrarQuandoFiltroEstiverAtivo();");
		sb.append("		JANELA_AGUARDE_MENU.show();");
		sb.append("		$('#paginaSelecionada').val('1');");
		sb.append("		var numero_idSolicitacao_ = NumberUtil.apenasNumeros(document.formGerenciamento.idSolicitacao.value);");
		sb.append("		document.formGerenciamento.idSolicitacao.value = numero_idSolicitacao_;");
		sb.append("		document.formGerenciamento.fireEvent('pesquisarItensFiltro');");
		sb.append("	}");
		sb.append("function fecharJanelaAguarde() {");
		sb.append("	JANELA_AGUARDE_MENU.hide();");
		sb.append("}");
		sb.append("	function atualizaPagina() {\n");
		sb.append("		if(!flagModalAtualizacao) {\n ");
		sb.append("			if(document.formGerenciamento.chkAtualiza !== null && document.formGerenciamento.chkAtualiza !== undefined){ ");
		sb.append("			if ( document.formGerenciamento.chkAtualiza.checked ) {\n");
		sb.append("				pesquisarItensFiltro();\n");
		sb.append("			}");
		sb.append("			}");
		sb.append("		}\n");
		sb.append("	}\n");
		sb.append("	window.setInterval(atualizaPagina, 30000);\n");
		sb.append("</script>");
	}

	/**
	 * Realiza a paginação dos itens e recarrega a lista de solicitações
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void paginarItens(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setGerenciamentoServicosDTO((GerenciamentoServicosDTO) document.getBean());
		this.recarregarLista(document, request, response);
	}

}
