package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.citcorpore.bean.AtribuicaoSolicitacaoAtendenteDTO;
import br.com.centralit.citcorpore.bean.CheckinDTO;
import br.com.centralit.citcorpore.bean.CheckoutDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.result.QuantidadeResultDTO;
import br.com.centralit.citcorpore.integracao.AtribuicaoSolicitacaoAtendenteDAO;
import br.com.centralit.citcorpore.integracao.CheckinDao;
import br.com.centralit.citcorpore.integracao.CheckoutDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

public class CheckinServiceEjb extends CrudServiceImpl implements CheckinService {

    private static final Logger LOGGER = Logger.getLogger(CheckinServiceEjb.class.getName());

    private CheckinDao checkinDAO;

    @Override
    protected CheckinDao getDao() {
        if (checkinDAO == null) {
            checkinDAO = new CheckinDao();
        }
        return checkinDAO;
    }

    @Override
    public Integer realizarCheckin(final CheckinDTO checkin, final UsuarioDTO user) throws Exception {
        this.verificarCheckinUsuarioSemCheckout(user);

        final SolicitacaoServicoDTO solicitacaoServico = this.getSolicitacaoServicoDao().restoreByIdTarefa(checkin.getIdTarefa());
        final Integer numeroSolicitacao = solicitacaoServico.getIdSolicitacaoServico();
        checkin.setIdSolicitacao(numeroSolicitacao);

        this.verificarCheckinSemAtribuicao(checkin, user);
        this.verificarCheckinSolicitacaoSemCheckout(checkin, user);

        this.getDao().setTransactionControler(this.getTransaction());
        this.getTransaction().start();

        try {
            if (solicitacaoServico.getSituacao().trim().equalsIgnoreCase(SituacaoSolicitacaoServico.Suspensa.name())) {
                this.validaPermissaoReativacao(checkin, user);
                this.reativarSolicitacaoSuspensa(checkin, user, solicitacaoServico);
            }

            this.capturarSolicitacao(checkin, solicitacaoServico, user);

            this.getDao().create(checkin);
            this.getTransaction().commit();
        } catch (final Exception e) {
            this.getTransaction().rollback();
        } finally {
            this.getTransaction().closeQuietly();
        }

        return numeroSolicitacao;
    }

    @Override
    public boolean isCheckinAtivo(final Integer idTarefa, final Integer idSolicitacao, final Integer idUsuario) throws Exception {
        final CheckoutDao checkoutDao = new CheckoutDao();
        final CheckoutDTO checkoutDto = new CheckoutDTO();
        checkoutDto.setIdTarefa(idTarefa);
        checkoutDto.setIdSolicitacao(idSolicitacao);
        checkoutDto.setIdUsuario(idUsuario);

        final List<QuantidadeResultDTO> quantidades = checkoutDao.listQuantidadesCheckoutAndCheckin(checkoutDto);
        final Integer qtdCheckin = quantidades.get(0).getQuantidade();
        final Integer qtdCheckout = quantidades.get(1).getQuantidade();

        if (qtdCheckin <= qtdCheckout) {
            return false;
        }
        return true;
    }

    @Override
    public List<CheckinDTO> listCheckinsDoUsuarioSemCheckout(final UsuarioDTO usuario) throws ServiceException {
        List<CheckinDTO> result = new ArrayList<>();
        try {
            result = this.getDao().listCheckinDoUsuarioSemCheckout(usuario);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<CheckinDTO> listCheckinSolicitacaoSemCheckout(final CheckinDTO checkin) throws ServiceException {
        List<CheckinDTO> result = new ArrayList<>();
        try {
            result = this.getDao().listCheckinSolicitacaoSemCheckout(checkin);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
        return result;
    }

    private void validaPermissaoReativacao(final CheckinDTO checkin, final UsuarioDTO user) throws ServiceException {
        final PermissoesFluxoServiceEjb permissoesFluxoServiceEjb = new PermissoesFluxoServiceEjb();
        final PermissoesFluxoDTO permissoesFluxo = permissoesFluxoServiceEjb.findByIdFluxoAndIdUsuario(user.getIdUsuario(), checkin.getIdTarefa());

        if (permissoesFluxo == null || permissoesFluxo.getReativar() == null || !permissoesFluxo.getReativar().trim().equalsIgnoreCase("S")) {
            throw new ServiceException(this.i18nMessage(user.getLocale(), "rest.service.mobile.v2.checkout.usuario.sem.permissao.de.reativacao"));
        }
    }

    private void verificarCheckinUsuarioSemCheckout(final UsuarioDTO user) throws ServiceException {
        final List<CheckinDTO> checkins = this.listCheckinsDoUsuarioSemCheckout(user);
        if (checkins.size() > 0) {
            final int checkinsSize = checkins.size();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < checkinsSize; i++) {
                final CheckinDTO withoutCheckout = checkins.get(i);
                sb.append(withoutCheckout.getIdSolicitacao());
                if (checkinsSize != i + 1) {
                    sb.append(", ");
                }
            }
            throw new ServiceException(this.i18nMessage(user.getLocale(), "rest.service.mobile.v2.checkin.atendente.com.checkin.sem.checkout", sb.toString()));
        }
    }

    private void verificarCheckinSolicitacaoSemCheckout(final CheckinDTO checkin, final UsuarioDTO user) throws ServiceException {
        final List<CheckinDTO> checkins = this.listCheckinSolicitacaoSemCheckout(checkin);
        if (checkins.size() > 0) {
            final String message = this.i18nMessage(user.getLocale(), "rest.service.mobile.v2.checkin.atendente.com.checkin.sem.checkout", checkin.getNomeEmpregado(),
                    checkin.getIdSolicitacao());
            throw new ServiceException(message);
        }
    }

    public void verificarCheckinSemAtribuicao(final CheckinDTO checkin, final UsuarioDTO user) throws Exception {
        final List<AtribuicaoSolicitacaoAtendenteDTO> checkins = this.getAtribuicaoDAO().findByIDUsuarioAndIDSolicitacao(user.getIdUsuario(), checkin.getIdSolicitacao());

        if (checkins.size() == 0) {
            final String message = this.i18nMessage(user.getLocale(), "rest.service.mobile.v2.checkin.atribuicao.inexistente", checkin.getIdSolicitacao());
            throw new ServiceException(message);
        }
    }

    private void reativarSolicitacaoSuspensa(final CheckinDTO checkin, final UsuarioDTO user, final SolicitacaoServicoDTO solicitacaoServico) throws Exception {
        this.getSolicitacaoServicoService().reativa(user, solicitacaoServico, this.getTransaction());
    }

    private void capturarSolicitacao(final CheckinDTO checkin, final SolicitacaoServicoDTO solicitacaoServico, final UsuarioDTO user) throws Exception {
        final TarefaFluxoDTO tarefa = this.getExecucaoSolicitacaoService().recuperaTarefa(user.getLogin(), checkin.getIdTarefa());
        try {
            if (tarefa.getExecutar().equals("S") && !user.getNomeUsuario().trim().equals(tarefa.getResponsavelAtual().trim())
                    && !solicitacaoServico.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
                this.getExecucaoSolicitacaoService().executar(user, checkin.getIdTarefa(), Enumerados.ACAO_INICIAR, this.getTransaction());
            }
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    private TransactionControler transaction;

    private TransactionControler getTransaction() throws ServiceException {
        if (transaction == null) {
            transaction = new TransactionControlerImpl(this.getDao().getAliasDB());
        }
        return transaction;
    }

    private AtribuicaoSolicitacaoAtendenteDAO atribuicaoDAO;
    private SolicitacaoServicoDao solicitacaoServicoDAO;

    private AtribuicaoSolicitacaoAtendenteDAO getAtribuicaoDAO() {
        if (atribuicaoDAO == null) {
            atribuicaoDAO = new AtribuicaoSolicitacaoAtendenteDAO();
        }
        return atribuicaoDAO;
    }

    private SolicitacaoServicoDao getSolicitacaoServicoDao() {
        if (solicitacaoServicoDAO == null) {
            solicitacaoServicoDAO = new SolicitacaoServicoDao();
        }
        return solicitacaoServicoDAO;
    }

    private ExecucaoSolicitacaoServiceEjb execucaoSolicitacaoService;
    private SolicitacaoServicoServiceEjb solicitacaoServicoService;

    private ExecucaoSolicitacaoServiceEjb getExecucaoSolicitacaoService() {
        if (execucaoSolicitacaoService == null) {
            execucaoSolicitacaoService = new ExecucaoSolicitacaoServiceEjb();
        }
        return execucaoSolicitacaoService;
    }

    private SolicitacaoServicoServiceEjb getSolicitacaoServicoService() {
        if (solicitacaoServicoService == null) {
            solicitacaoServicoService = new SolicitacaoServicoServiceEjb();
        }
        return solicitacaoServicoService;
    }

}
