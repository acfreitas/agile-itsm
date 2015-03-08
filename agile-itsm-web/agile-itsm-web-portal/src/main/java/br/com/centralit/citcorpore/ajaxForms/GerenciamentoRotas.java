package br.com.centralit.citcorpore.ajaxForms;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.AtribuicaoSolicitacaoAtendenteDTO;
import br.com.centralit.citcorpore.bean.GerenciamentoRotasDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.result.GerenciamentoRotasResultDTO;
import br.com.centralit.citcorpore.negocio.AtribuicaoSolicitacaoAtendenteService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoServiceForMobileV2;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.core.Page;
import br.com.citframework.integracao.core.PageRequest;
import br.com.citframework.integracao.core.Pageable;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.WebUtil;

import com.google.gson.reflect.TypeToken;

/**
 * Controlador para gerenciamento das rotas de atendimento
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 06/10/2014
 *
 */
public class GerenciamentoRotas extends AbstractGestaoForcaAtendimento<GerenciamentoRotasDTO, GerenciamentoRotasResultDTO> {

    private static final Logger LOGGER = Logger.getLogger(GerenciamentoRotas.class.getName());

    private static final Type LIST_TYPE_ATRIBUICAO = new TypeToken<ArrayList<AtribuicaoSolicitacaoAtendenteDTO>>() {}.getType();

    @Override
    public Class<GerenciamentoRotasDTO> getBeanClass() {
        return GerenciamentoRotasDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        super.load(document, request, response);

        final HTMLForm formSolicitacao = document.getForm("formSolicitacoes");
        formSolicitacao.clear();

        this.loadComboTiposSolicitacao(document, request);

        final List<HTMLSelect> combos = new ArrayList<>();
        combos.add(document.getSelectById(ID_UF));
        combos.add(document.getSelectById(ID_CIDADE));
        combos.add(document.getSelectById(ID_CONTRATO));
        this.inicializarCombosOnLoad(combos, request);
    }

    @Override
    public void performSearch(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) {
        try {
            final GerenciamentoRotasDTO bean = this.normalizeDates((GerenciamentoRotasDTO) document.getBean());
            final Page<GerenciamentoRotasResultDTO> page = this.getSolicitacaoServicoService().listSolicitacoesParaRoteirizacao(bean, this.getPageable(request));

            final GerenciamentoRotaResultWithPaging result = new GerenciamentoRotaResultWithPaging();
            result.setPage(page.getNumber());
            result.setPages(page.getTotalPages());
            result.setSolicitations(page.getContent());

            document.executeScript(String.format(FUNC_HANDLE_SEARCH_RESULT, GSON.toJson(result)));
        } catch (final Exception e) {
            document.alert(UtilI18N.internacionaliza(request, "MSE02"));
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
        document.executeScript(FUNC_WAIT_WINDOW_HIDE);
    }

    public void save(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String jsonAtribuicoes = request.getParameter("atribuicoes");

        final GerenciamentoRotasDTO bean = (GerenciamentoRotasDTO) document.getBean();

        try {
            final List<AtribuicaoSolicitacaoAtendenteDTO> atribuicoes = this.getListAtribuicoes(jsonAtribuicoes);
            this.getAtribuicaoSolicitacaoService().criaAtribuicaoEmBatch(atribuicoes, bean.getDataExecucao(), WebUtil.getURLFromRequest(request) + request.getContextPath());
            document.alert(UtilI18N.internacionaliza(request, "MSG05"));
            document.executeScript("$().clearSolicitacoesAtribuidasEMapa();");
        } catch (final ServiceException e) {
            document.alert(UtilI18N.internacionaliza(request, "MSE02"));
        } catch (final Exception e) {
            throw e;
        } finally {
            document.executeScript(FUNC_WAIT_WINDOW_HIDE);
        }

        document.executeScript("$(\"html, body\").animate({ scrollTop : 0 }, 800);");
    }

    @Override
    protected List<GerenciamentoRotasResultDTO> getListResultSearch(final DocumentHTML document, final HttpServletRequest request) throws Exception {
        return null;
    }

    private List<AtribuicaoSolicitacaoAtendenteDTO> getListAtribuicoes(final String jsonAtribuicoes) {
        return GSON.fromJson(jsonAtribuicoes, LIST_TYPE_ATRIBUICAO);
    }

    private void loadComboTiposSolicitacao(final DocumentHTML document, final HttpServletRequest request) throws Exception {
        final Collection<TipoDemandaServicoDTO> listTiposDemanda = this.getTipoDemandaService().listSolicitacoes();

        final HTMLSelect comboTiposSolicitacao = document.getSelectById("idTipoSolicitacao");
        this.inicializaCombo(comboTiposSolicitacao, request);
        if (listTiposDemanda != null) {
            comboTiposSolicitacao.addOptions(listTiposDemanda, "idTipoDemandaServico", "nomeTipoDemandaServico", null);
        }
        document.executeScript(FUNC_WAIT_WINDOW_HIDE);
    }

    private Pageable getPageable(final HttpServletRequest request) {
        final Integer page = Integer.parseInt(request.getParameter("page"));
        final Integer size = Integer.parseInt(request.getParameter("size"));
        return new PageRequest(page, size);
    }

    private AtribuicaoSolicitacaoAtendenteService atribuicaoSolicitacaoService;
    private SolicitacaoServicoServiceForMobileV2 solicitacaoServicoService;
    private TipoDemandaServicoService tipoDemandaService;

    private AtribuicaoSolicitacaoAtendenteService getAtribuicaoSolicitacaoService() throws Exception {
        if (atribuicaoSolicitacaoService == null) {
            atribuicaoSolicitacaoService = (AtribuicaoSolicitacaoAtendenteService) ServiceLocator.getInstance().getService(AtribuicaoSolicitacaoAtendenteService.class, null);
        }
        return atribuicaoSolicitacaoService;
    }

    private TipoDemandaServicoService getTipoDemandaService() throws Exception {
        if (tipoDemandaService == null) {
            tipoDemandaService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
        }
        return tipoDemandaService;
    }

    private SolicitacaoServicoServiceForMobileV2 getSolicitacaoServicoService() throws Exception {
        if (solicitacaoServicoService == null) {
            solicitacaoServicoService = (SolicitacaoServicoServiceForMobileV2) ServiceLocator.getInstance().getService(SolicitacaoServicoServiceForMobileV2.class, null);
        }
        return solicitacaoServicoService;
    }

    @SuppressWarnings("unused")
    private class GerenciamentoRotaResultWithPaging {

        private Integer page;
        private Integer pages;
        private List<GerenciamentoRotasResultDTO> solicitations;

        public Integer getPage() {
            return page;
        }

        public void setPage(final Integer page) {
            this.page = page;
        }

        public Integer getPages() {
            return pages;
        }

        public void setPages(final Integer pages) {
            this.pages = pages;
        }

        public List<GerenciamentoRotasResultDTO> getSolicitations() {
            return solicitations;
        }

        public void setSolicitations(final List<GerenciamentoRotasResultDTO> solicitations) {
            this.solicitations = solicitations;
        }

    }

}
