package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.citcorpore.bean.AtribuicaoSolicitacaoAtendenteDTO;
import br.com.centralit.citcorpore.bean.CheckoutDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.result.QuantidadeResultDTO;
import br.com.centralit.citcorpore.integracao.CheckoutDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

public class CheckoutServiceEjb extends CrudServiceImpl implements CheckoutService {

    private static final String ACAO = Enumerados.ACAO_EXECUTAR;

    private CheckoutDao checkoutDAO;

    @Override
    protected CheckoutDao getDao() {
        if (checkoutDAO == null) {
            checkoutDAO = new CheckoutDao();
        }
        return checkoutDAO;
    }

    @Override
    public Integer realizarCheckout(final CheckoutDTO checkout, final UsuarioDTO usuario) throws ServiceException, PersistenceException {
        Integer numeroSolicitacao = null;
        try {
            this.getDao().setTransactionControler(this.getTransaction());
            this.getTransaction().start();

            final SolicitacaoServicoDTO solicitacaoServico = this.getSolicitacaoServico(checkout.getIdTarefa());
            numeroSolicitacao = solicitacaoServico.getIdSolicitacaoServico();
            checkout.setIdSolicitacao(solicitacaoServico.getIdSolicitacaoServico());

            this.verificarExistenciaDeCheckin(checkout, usuario);
            final AtribuicaoSolicitacaoAtendenteDTO atribuicao = this.verificarCheckoutSemAtribuicao(checkout, usuario);

            switch (checkout.getStatus()) {
            case 2:
                this.validaPermissaoFluxo(usuario, checkout);
                this.suspenderSolicitacao(usuario, solicitacaoServico, checkout);
                break;
            case 3:
                this.cancelarSolicitacao(usuario, solicitacaoServico, checkout);
                break;
            case 4:
                this.resolverSolicitacao(usuario, solicitacaoServico, checkout);
                break;
            default:
                this.atualizarSolicitacaoServico(usuario, solicitacaoServico, checkout);
            }
            checkout.setDataHoraCheckout(UtilDatas.getDataHoraAtual());
            this.getDao().create(checkout);
            this.getAtribuicaoSolicitacaoAtendenteService().delete(atribuicao);
            this.getTransaction().commit();
        } catch (final Exception e) {
            this.getTransaction().rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            this.getTransaction().closeQuietly();
        }

        return numeroSolicitacao;
    }

    private void validaPermissaoFluxo(final UsuarioDTO usuarioDto, final CheckoutDTO checkoutDto) throws ServiceException {
        final PermissoesFluxoServiceEjb permissoesFluxoServiceEjb = new PermissoesFluxoServiceEjb();
        final PermissoesFluxoDTO permissoesFluxo = permissoesFluxoServiceEjb.findByIdFluxoAndIdUsuario(usuarioDto.getIdUsuario(), checkoutDto.getIdTarefa());

        if (permissoesFluxo == null || permissoesFluxo.getSuspender() == null || !permissoesFluxo.getSuspender().trim().equalsIgnoreCase("S")) {
            throw new ServiceException(this.i18nMessage(usuarioDto.getLocale(), "rest.service.mobile.v2.checkout.usuario.sem.permissao.de.suspensao"));
        }
    }

    private void verificarExistenciaDeCheckin(final CheckoutDTO checkout, final UsuarioDTO usuario) throws Exception {
        final List<QuantidadeResultDTO> quantidades = this.getDao().listQuantidadesCheckoutAndCheckin(checkout);
        final Integer qtdCheckin = quantidades.get(0).getQuantidade();
        final Integer qtdCheckout = quantidades.get(1).getQuantidade();
        if (qtdCheckin <= qtdCheckout) {
            throw new ServiceException(this.i18nMessage(usuario.getLocale(), "rest.service.mobile.v2.checkout.checkin.inexistente", checkout.getIdSolicitacao()));
        }
    }

    private void suspenderSolicitacao(final UsuarioDTO usuario, final SolicitacaoServicoDTO solicitacaoServico, final CheckoutDTO checkout) throws Exception {
        solicitacaoServico.setIdJustificativa(checkout.getIdResposta());
        solicitacaoServico.setComplementoJustificativa(checkout.getDescricao());
        this.getSolicitacaoServicoService().suspende(usuario, solicitacaoServico, this.getTransaction());
    }

    private void cancelarSolicitacao(final UsuarioDTO usuario, final SolicitacaoServicoDTO solicitacaoServico, final CheckoutDTO checkout) throws Exception {
        solicitacaoServico.setSituacao(SituacaoSolicitacaoServico.Cancelada.getDescricao());
        solicitacaoServico.setUsuarioDto(usuario);
        solicitacaoServico.setReclassificar("N");
        solicitacaoServico.setAlterarSituacao("S");
        solicitacaoServico.setAcaoFluxo(ACAO);
        this.getSolicitacaoServicoService().updateInfo(solicitacaoServico, this.getTransaction(), false);
    }

    private void resolverSolicitacao(final UsuarioDTO usuario, final SolicitacaoServicoDTO solicitacaoServico, final CheckoutDTO checkout) throws Exception {
        solicitacaoServico.setSituacao(SituacaoSolicitacaoServico.Resolvida.getDescricao());
        solicitacaoServico.setUsuarioDto(usuario);
        solicitacaoServico.setAcaoFluxo(ACAO);
        solicitacaoServico.setResposta(checkout.getDescricao());
        solicitacaoServico.setReclassificar("N");
        solicitacaoServico.setAlterarSituacao("S");
        this.getSolicitacaoServicoService().updateInfo(solicitacaoServico, this.getTransaction(), false);
    }

    private void atualizarSolicitacaoServico(final UsuarioDTO usuario, final SolicitacaoServicoDTO solicitacaoServico, final CheckoutDTO checkout) throws Exception {
        solicitacaoServico.setAcaoFluxo(Enumerados.ACAO_INICIAR);
        solicitacaoServico.setUsuarioDto(usuario);
        this.getSolicitacaoServicoService().updateInfo(solicitacaoServico, this.getTransaction(), false);
    }

    private AtribuicaoSolicitacaoAtendenteDTO verificarCheckoutSemAtribuicao(final CheckoutDTO checkout, final UsuarioDTO user) throws Exception {
        final AtribuicaoSolicitacaoAtendenteDTO atribuicao = new AtribuicaoSolicitacaoAtendenteDTO();
        atribuicao.setIdUsuario(user.getIdUsuario());
        atribuicao.setIdSolicitacao(checkout.getIdSolicitacao());
        final List<AtribuicaoSolicitacaoAtendenteDTO> atribuicoesOnDB = this.getAtribuicaoSolicitacaoAtendenteService().findByIDUsuarioAndIDSolicitacao(atribuicao);
        final int atribuicoesSizeOnDB = atribuicoesOnDB.size();
        if (atribuicoesSizeOnDB == 0) {
            final String message = this.i18nMessage(user.getLocale(), "rest.service.mobile.v2.ckeckout.atribuicao.inexistente", checkout.getIdSolicitacao());
            throw new ServiceException(message);
        }
        return atribuicoesOnDB.get(atribuicoesSizeOnDB - 1);
    }

    private SolicitacaoServicoDTO getSolicitacaoServico(final Integer idTarefa) throws Exception {
        return this.getSolicitacaoServicoDAO().restoreByIdTarefa(idTarefa);
    }

    private SolicitacaoServicoServiceEjb solicitacaoServicoService;

    public SolicitacaoServicoServiceEjb getSolicitacaoServicoService() {
        if (solicitacaoServicoService == null) {
            solicitacaoServicoService = new SolicitacaoServicoServiceEjb();
        }
        return solicitacaoServicoService;
    }

    private SolicitacaoServicoDao solicitacaoServicoDAO;

    private SolicitacaoServicoDao getSolicitacaoServicoDAO() {
        if (solicitacaoServicoDAO == null) {
            solicitacaoServicoDAO = new SolicitacaoServicoDao();
        }
        return solicitacaoServicoDAO;
    }

    private TransactionControler transaction;

    private TransactionControler getTransaction() throws ServiceException {
        if (transaction == null) {
            transaction = new TransactionControlerImpl(this.getDao().getAliasDB());
        }
        return transaction;
    }

    private AtribuicaoSolicitacaoAtendenteService atribuicaoSolicitacaoAtendenteService;

    private AtribuicaoSolicitacaoAtendenteService getAtribuicaoSolicitacaoAtendenteService() throws ServiceException {
        if (atribuicaoSolicitacaoAtendenteService == null) {
            atribuicaoSolicitacaoAtendenteService = (AtribuicaoSolicitacaoAtendenteService) ServiceLocator.getInstance().getService(AtribuicaoSolicitacaoAtendenteService.class,
                    null);
        }
        return atribuicaoSolicitacaoAtendenteService;
    }

}
