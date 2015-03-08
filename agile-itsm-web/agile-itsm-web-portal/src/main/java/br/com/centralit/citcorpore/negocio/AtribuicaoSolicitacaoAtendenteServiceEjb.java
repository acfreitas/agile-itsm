package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.AssociacaoDeviceAtendenteDTO;
import br.com.centralit.citcorpore.bean.AtribuicaoSolicitacaoAtendenteDTO;
import br.com.centralit.citcorpore.bean.HistoricoPushMessageDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.AtribuicaoSolicitacaoAtendenteDAO;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.push.ConfigPushService;
import br.com.citframework.push.MessageRequest;
import br.com.citframework.push.MobilePushMessage;
import br.com.citframework.push.PushMessageService;
import br.com.citframework.push.google.GoogleCloudMessageRequest;
import br.com.citframework.push.google.GoogleCloudMessageResponse;
import br.com.citframework.push.google.GoogleCloudMessageServiceImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Assert;
import br.com.citframework.util.UtilDatas;

import com.google.gson.Gson;

public class AtribuicaoSolicitacaoAtendenteServiceEjb extends CrudServiceImpl implements AtribuicaoSolicitacaoAtendenteService {

    private static final Logger LOGGER = Logger.getLogger(AtribuicaoSolicitacaoAtendenteServiceEjb.class.getName());

    private static final Gson GSON = new Gson();

    private static final ConfigPushService CONFIG = new ConfigPushService();
    private static final PushMessageService<GoogleCloudMessageRequest<?>, GoogleCloudMessageResponse> PUSH_MESSAGE_SERVICE = new GoogleCloudMessageServiceImpl();

    private AtribuicaoSolicitacaoAtendenteDAO dao;

    @Override
    protected AtribuicaoSolicitacaoAtendenteDAO getDao() {
        if (dao == null) {
            dao = new AtribuicaoSolicitacaoAtendenteDAO();
        }
        return dao;
    }

    @Override
    public void delete(final IDto bean) throws ServiceException, LogicException {
        final AtribuicaoSolicitacaoAtendenteDTO atribuicao = (AtribuicaoSolicitacaoAtendenteDTO) bean;
        atribuicao.setActive(0);
        super.update(atribuicao);
    }

    @Override
    public boolean existeAtribuicao(final Integer taskId, final UsuarioDTO user) throws ServiceException, Exception {
        return this.getDao().existeAtribuicao(taskId, user);
    }

    @Override
    public AtribuicaoSolicitacaoAtendenteDTO criarAtribuicao(final AtribuicaoSolicitacaoAtendenteDTO atribuicao) throws ServiceException {
        return this.criarAtribuicao(atribuicao, true);
    }

    @Override
    public List<AtribuicaoSolicitacaoAtendenteDTO> criaAtribuicaoEmBatch(final List<AtribuicaoSolicitacaoAtendenteDTO> atribuicoes, final Date dataExecucao, final String connection)
            throws ServiceException {
        Assert.isTrue(atribuicoes != null && atribuicoes.size() > 0, "'Atribuições' must not be null or empty.");

        final List<AtribuicaoSolicitacaoAtendenteDTO> newAtribuicoes = new ArrayList<>();
        try {
            for (final AtribuicaoSolicitacaoAtendenteDTO atribuicao : atribuicoes) {
                atribuicao.setDataExecucao(dataExecucao);
                newAtribuicoes.add(this.criarAtribuicao(atribuicao, false));
            }
        } catch (final ServiceException e) {
            throw new ServiceException(e);
        }

        this.sendPushMessage(atribuicoes.get(0).getIdUsuario(), connection);
        return newAtribuicoes;
    }

    @Override
    public List<AtribuicaoSolicitacaoAtendenteDTO> findByIDUsuarioAndIDSolicitacao(final AtribuicaoSolicitacaoAtendenteDTO atribuicao) throws ServiceException {
        Assert.notNull(atribuicao, "'Atribuição' must not be null.");
        try {
            return this.getDao().findByIDUsuarioAndIDSolicitacao(atribuicao.getIdUsuario(), atribuicao.getIdSolicitacao());
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    private AtribuicaoSolicitacaoAtendenteDTO criarAtribuicao(final AtribuicaoSolicitacaoAtendenteDTO atribuicao, final boolean fromMobile) throws ServiceException {
        AtribuicaoSolicitacaoAtendenteDTO result = null;
        try {
            final List<AtribuicaoSolicitacaoAtendenteDTO> atribuicoesOnDB = this.findByIDUsuarioAndIDSolicitacao(atribuicao);
            final Integer atribuicoesSizeOnDB = atribuicoesOnDB.size();
            if (atribuicoesSizeOnDB > 0) {
                final AtribuicaoSolicitacaoAtendenteDTO toUpdate = atribuicoesOnDB.get(atribuicoesSizeOnDB - 1);
                if (fromMobile) {
                    toUpdate.setLatitude(atribuicao.getLatitude());
                    toUpdate.setLongitude(atribuicao.getLongitude());
                    toUpdate.setDataInicioAtendimento(atribuicao.getDataInicioAtendimento());
                } else {
                    toUpdate.setDataExecucao(atribuicao.getDataExecucao());
                    toUpdate.setPriorityOrder(atribuicao.getPriorityOrder());
                }
                this.update(toUpdate);
                result = toUpdate;
            } else {
                atribuicao.setActive(1);
                result = (AtribuicaoSolicitacaoAtendenteDTO) this.create(atribuicao);
            }
        } catch (final LogicException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return result;
    }

    private void sendPushMessage(final Integer idUsuario, final String connection) {
        try {
            final PushMessageService<GoogleCloudMessageRequest<?>, GoogleCloudMessageResponse> service = getPushMessageService();

            final UsuarioDTO usuario = this.getUsuarioService().restoreByID(idUsuario);
            final List<String> tokens = new ArrayList<>();
            final List<AssociacaoDeviceAtendenteDTO> associacoes = this.getDeviceAtendenteService().listActiveAssociationsForUserAndConnection(usuario, connection);

            if (associacoes != null && associacoes.size() > 0) {
                for (final AssociacaoDeviceAtendenteDTO associacao : associacoes) {
                    tokens.add(associacao.getToken());
                }

                final MobilePushMessage message = new MobilePushMessage();
                message.setConnection(connection);
                message.setUserName(usuario.getLogin());

                final GoogleCloudMessageRequest<MobilePushMessage> request = new GoogleCloudMessageRequest<>();
                request.setData(message);
                request.setRegistratioIds(tokens);

                LOGGER.fine(String.format("Sending push message (GCM) to %s device(s): Request: %s", tokens.size(), request.toString()));
                final GoogleCloudMessageResponse response = service.sendMessage(request);
                LOGGER.fine("Success on send Push message. Response: " + response.toString());

                this.createHistoricoPushMessage(usuario, request);
            } else {
                LOGGER.fine(String.format("Não existe associação de devices para o usuário '%s'", usuario.getLogin()));
            }
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, "Problem on sending PushMessage: " + e.getMessage(), e);
        }
    }

    private void createHistoricoPushMessage(final UsuarioDTO usuario, final MessageRequest<MobilePushMessage> request) throws Exception {
        final HistoricoPushMessageDTO historico = new HistoricoPushMessageDTO();
        historico.setIdUsuario(usuario.getIdUsuario());
        historico.setMessage(GSON.toJson(request));
        historico.setDateTime(UtilDatas.getDataHoraAtual());
        this.getHistoricoPushMessageService().create(historico);
    }

    private static PushMessageService<GoogleCloudMessageRequest<?>, GoogleCloudMessageResponse> getPushMessageService() throws ServiceException {
        final String googleAPIKey = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.GOOGLE_API_KEY_WEB, "");
        if (StringUtils.isBlank(googleAPIKey)) {
            throw new ServiceException("Google API Key for server must be configured.");
        }
        CONFIG.setValue(ConfigPushService.Key.GOOLE_API_KEY, googleAPIKey);
        PUSH_MESSAGE_SERVICE.configPushService(CONFIG);
        return PUSH_MESSAGE_SERVICE;
    }

    private AssociacaoDeviceAtendenteService deviceAtendenteService;
    private HistoricoPushMessageService historicoPushMessageService;
    private UsuarioService usuarioService;

    private AssociacaoDeviceAtendenteService getDeviceAtendenteService() throws ServiceException {
        if (deviceAtendenteService == null) {
            deviceAtendenteService = (AssociacaoDeviceAtendenteService) ServiceLocator.getInstance().getService(AssociacaoDeviceAtendenteService.class, null);
        }
        return deviceAtendenteService;
    }

    private HistoricoPushMessageService getHistoricoPushMessageService() throws ServiceException {
        if (historicoPushMessageService == null) {
            historicoPushMessageService = (HistoricoPushMessageService) ServiceLocator.getInstance().getService(HistoricoPushMessageService.class, null);
        }
        return historicoPushMessageService;
    }

    private UsuarioService getUsuarioService() throws ServiceException {
        if (usuarioService == null) {
            usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
        }
        return usuarioService;
    }

}
