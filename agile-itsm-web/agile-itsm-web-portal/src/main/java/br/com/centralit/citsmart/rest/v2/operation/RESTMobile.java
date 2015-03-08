package br.com.centralit.citsmart.rest.v2.operation;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.bpm.util.Enumerados.TipoAtribuicao;
import br.com.centralit.citcorpore.bean.AtribuicaoSolicitacaoAtendenteDTO;
import br.com.centralit.citcorpore.bean.CheckinDTO;
import br.com.centralit.citcorpore.bean.CheckinDeniedDTO;
import br.com.centralit.citcorpore.bean.CheckoutDTO;
import br.com.centralit.citcorpore.bean.PosicionamentoAtendenteDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.AtribuicaoSolicitacaoAtendenteService;
import br.com.centralit.citcorpore.negocio.CheckinDeniedService;
import br.com.centralit.citcorpore.negocio.CheckinService;
import br.com.centralit.citcorpore.negocio.CheckoutService;
import br.com.centralit.citcorpore.negocio.PermissoesFluxoService;
import br.com.centralit.citcorpore.negocio.PosicionamentoAtendenteService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoServiceForMobileV2;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.operation.RestMobile;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetById;
import br.com.centralit.citsmart.rest.util.RestEnum;
import br.com.centralit.citsmart.rest.util.RestOperationUtil;
import br.com.centralit.citsmart.rest.util.RestUtil;
import br.com.centralit.citsmart.rest.v2.schema.CTNotification;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationAttendRequest;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationAttendRequestResp;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationAttendantLocation;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationAttendantLocationResp;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationCheckin;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationCheckinDenied;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationCheckinDeniedResp;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationCheckinResp;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationCheckout;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationCheckoutResp;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationCommonResp;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationGetByCoordinates;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationGetByCoordinatesResp;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationGetById;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationGetByIdResp;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationGetNewest;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationGetNewestResp;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationGetOldest;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationGetOldestResp;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationUpdate;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationUpdateResp;
import br.com.centralit.citsmart.rest.v2.schema.CTPageRequest;
import br.com.centralit.citsmart.rest.v2.schema.CTPageResponse;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.core.Page;
import br.com.citframework.integracao.core.PageRequest;
import br.com.citframework.integracao.core.Pageable;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.UtilXMLDate;

/**
 * Implementação das operaçãos que respondem em {@code /mobile} da versão V2 de apis consumidas pelo mobile
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 22/09/2014
 *
 */
public class RESTMobile extends RestMobile {

    private static final Logger LOGGER = Logger.getLogger(RESTMobile.class.getName());

    protected CTNotificationAttendantLocationResp attendantLocation(final RestSessionDTO restSession, final CTNotificationAttendantLocation message) {
        final CTNotificationAttendantLocationResp resp = new CTNotificationAttendantLocationResp();

        final PosicionamentoAtendenteDTO posicao = new PosicionamentoAtendenteDTO();
        posicao.setLatitude(message.getLatitude());
        posicao.setLongitude(message.getLongitude());

        posicao.setDateTime(UtilXMLDate.toTimeStamp(message.getDateTime()));
        posicao.setIdUsuario(restSession.getUserId());

        try {
            this.getPosicionamentoAtendenteService().create(posicao);
            resp.setSuccess(true);
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildSimpleError(e));
        }

        return resp;
    }

    protected CTNotificationAttendRequestResp attendRequest(final RestSessionDTO restSession, final CTNotificationAttendRequest message) {
        final CTNotificationAttendRequestResp resp = new CTNotificationAttendRequestResp();

        final Double latitude = message.getLatitude();
        final Double longitude = message.getLongitude();
        final Integer idSolicitacao = message.getNumber().intValue();
        final Integer idUsuario = restSession.getUserId();
        final Timestamp dataInicioAtendimento = UtilXMLDate.toTimeStamp(message.getDateTime());

        final AtribuicaoSolicitacaoAtendenteDTO atribuicao = new AtribuicaoSolicitacaoAtendenteDTO();
        atribuicao.setIdSolicitacao(idSolicitacao);
        atribuicao.setIdUsuario(idUsuario);
        atribuicao.setLatitude(latitude);
        atribuicao.setLongitude(longitude);
        atribuicao.setDataInicioAtendimento(dataInicioAtendimento);

        try {
            this.getAtribuicaoSolicitacaoAtendenteService().criarAtribuicao(atribuicao);
            resp.setSuccess(true);
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildSimpleError(e));
        }

        return resp;
    }

    protected CTNotificationCheckinResp checkin(final RestSessionDTO restSession, final CTNotificationCheckin message) {
        final CTNotificationCheckinResp resp = new CTNotificationCheckinResp();

        if (message.getTaskId() == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.task.id.null")));
            return resp;
        }

        try {
            final Integer numeroSolicitacao = this.getCheckinService().realizarCheckin(this.setCheckin(restSession, message), restSession.getUser());
            resp.setNumber(numeroSolicitacao);
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildSimpleError(e));
        }
        return resp;
    }

    private CheckinDTO setCheckin(final RestSessionDTO restSession, final CTNotificationCheckin message) {
        final CheckinDTO checkin = new CheckinDTO();
        checkin.setIdUsuario(restSession.getUserId());
        checkin.setDataHoraCheckin(UtilXMLDate.toTimeStamp(message.getStartTime()));
        checkin.setIdTarefa(message.getTaskId().intValue());
        checkin.setLatitude(message.getLatitude());
        checkin.setLongitude(message.getLongitude());
        return checkin;
    }

    protected CTNotificationCheckinDeniedResp checkinDenied(final RestSessionDTO restSession, final CTNotificationCheckinDenied message) {
        final CTNotificationCheckinDeniedResp resp = new CTNotificationCheckinDeniedResp();

        if (message.getTaskId() == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.task.id.null")));
            return resp;
        }

        try {
            this.getCheckinDenied().create(this.setCheckinDenied(restSession, message));
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildSimpleError(e));
        }
        return resp;
    }

    private CheckinDeniedDTO setCheckinDenied(final RestSessionDTO restSession, final CTNotificationCheckinDenied message) {
        final CheckinDeniedDTO checkinDenied = new CheckinDeniedDTO();
        checkinDenied.setIdTarefa(message.getTaskId().intValue());
        checkinDenied.setIdUsuario(restSession.getUserId());
        checkinDenied.setIdJustificativa(message.getReasonId());
        checkinDenied.setLatitude(message.getLatitude());
        checkinDenied.setLongitude(message.getLongitude());
        checkinDenied.setDataHora(UtilXMLDate.toTimeStamp(message.getDateTime()));
        return checkinDenied;
    }

    protected CTNotificationCheckoutResp checkout(final RestSessionDTO restSession, final CTNotificationCheckout message) throws JAXBException {
        final CTNotificationCheckoutResp resp = new CTNotificationCheckoutResp();

        if (message.getTaskId() == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.task.id.null")));
            return resp;
        }

        try {
            final Integer numeroSolicitacao = this.getCheckoutService().realizarCheckout(this.setCheckout(restSession, message), restSession.getUser());
            resp.setNumber(numeroSolicitacao);
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildSimpleError(e));
        }
        return resp;
    }

    private CheckoutDTO setCheckout(final RestSessionDTO restSession, final CTNotificationCheckout message) {
        final CheckoutDTO checkout = new CheckoutDTO();
        checkout.setIdTarefa(message.getTaskId().intValue());
        checkout.setIdResposta(message.getSolution());
        checkout.setLatitude(message.getLatitude());
        checkout.setLongitude(message.getLongitude());
        checkout.setDescricao(message.getDescSolution());
        checkout.setStatus(message.getStatus());
        checkout.setIdUsuario(restSession.getUserId().intValue());
        return checkout;
    }

    protected CTNotificationGetByCoordinatesResp getByCoordinates(final RestSessionDTO restSession, final CTNotificationGetByCoordinates message) {
        final CTNotificationGetByCoordinatesResp resp = new CTNotificationGetByCoordinatesResp();
        try {
            final int tipo = message.getNotificationType();

            if (!this.validNotificationType(restSession, resp, tipo)) {
                return resp;
            }

            final Pageable pageable = this.makePageable(message.getPager());

            final Page<SolicitacaoServicoDTO> page = this.getSolicitacaoServicoService().listByCoordinates(message.getLatitude(), message.getLongitude(), restSession.getUser(),
                    this.getTiposArrayFromType(tipo), this.getAprovacao(message.getOnlyApproval()), pageable);
            resp.setPaging(this.makePageResponse(page));

            final List<SolicitacaoServicoDTO> solicitacoes = page.getContent();
            this.makeNotificationsResponse(resp, solicitacoes, restSession.getUser());
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildSimpleError(e));
        }
        return resp;
    }

    @Override
    protected CTNotificationGetByIdResp getById(final RestSessionDTO restSession, final CtNotificationGetById input) {
        final CTNotificationGetByIdResp resp = new CTNotificationGetByIdResp();

        try {
            this.doGetByIDValidation(restSession, input);
        } catch (final Exception ex) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, ex.getMessage()));
            return resp;
        }

        NotificationDetail detail = null;
        try {
            detail = this.populateNotificationDetail(restSession, input.getTaskId().intValue());
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildError(e));
            return resp;
        }

        final CTNotificationGetById notification = new CTNotificationGetById();

        notification.setEndSLA(detail.getEndSLA());
        notification.setTask(detail.getTask());
        notification.setService(detail.getService());
        notification.setDescription(detail.getDescription());
        notification.setStatus(detail.getStatus());
        notification.setTaskStatus(detail.getTaskStatus());
        notification.setTimeSLA(detail.getTimeSLA());

        resp.setNotification(notification);

        return resp;
    }

    protected CTNotificationGetNewestResp getNewest(final RestSessionDTO restSession, final CTNotificationGetNewest message) {
        final CTNotificationGetNewestResp resp = new CTNotificationGetNewestResp();
        try {
            final int tipo = message.getNotificationType();

            if (!this.validNotificationType(restSession, resp, tipo)) {
                return resp;
            }

            final Page<SolicitacaoServicoDTO> page = this.getSolicitacaoServicoService().listNewest(message.getNewestNumber().intValue(), restSession.getUser(),
                    this.getTiposArrayFromType(tipo), this.getAprovacao(message.getOnlyApproval()));

            final List<SolicitacaoServicoDTO> solicitacoes = page.getContent();
            this.makeNotificationsResponse(resp, solicitacoes, restSession.getUser());
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildSimpleError(e));
        }
        return resp;
    }

    protected CTNotificationGetOldestResp getOldest(final RestSessionDTO restSession, final CTNotificationGetOldest message) {
        final CTNotificationGetOldestResp resp = new CTNotificationGetOldestResp();
        try {
            final int tipo = message.getNotificationType();

            if (!this.validNotificationType(restSession, resp, tipo)) {
                return resp;
            }

            final Page<SolicitacaoServicoDTO> page = this.getSolicitacaoServicoService().listOldest(message.getOldestNumber().intValue(), restSession.getUser(),
                    this.getTiposArrayFromType(tipo), this.getAprovacao(message.getOnlyApproval()));

            final List<SolicitacaoServicoDTO> solicitacoes = page.getContent();
            this.makeNotificationsResponse(resp, solicitacoes, restSession.getUser());
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildSimpleError(e));
        }
        return resp;
    }

    protected CTNotificationUpdateResp updateNotification(final RestSessionDTO restSession, final CTNotificationUpdate message) {
        final CTNotificationUpdateResp resp = new CTNotificationUpdateResp();
        try {

            final Page<SolicitacaoServicoDTO> page = this.getSolicitacaoServicoService().listNotificationByNumberAndUser(message.getNumber().intValue(), restSession.getUser());

            final List<SolicitacaoServicoDTO> solicitacoes = page.getContent();
            this.makeNotificationsResponse(resp, solicitacoes, restSession.getUser());
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildSimpleError(e));
        }
        return resp;
    }

    private CTPageResponse makePageResponse(final Page<SolicitacaoServicoDTO> page) {
        CTPageResponse paging = null;
        if (page.hasContent()) {
            paging = new CTPageResponse();
            paging.setPage(page.getNumber() + 1);
            paging.setElements(page.getNumberOfElements());
            paging.setTotalPages(page.getTotalPages());
            paging.setTotalElements((int) page.getTotalElements());
        }
        return paging;
    }

    private <E extends CTNotificationCommonResp> void makeNotificationsResponse(final E resp, final List<SolicitacaoServicoDTO> solicitacoes, final UsuarioDTO usuario) {
        for (final SolicitacaoServicoDTO solicitacao : solicitacoes) {
            final CTNotification notification = new CTNotification();

            final Integer idSolicitacao = solicitacao.getIdSolicitacaoServico();
            final Integer idTarefa = solicitacao.getIdTarefa();

            notification.setNumber(BigInteger.valueOf(idSolicitacao));
            notification.setTaskId(BigInteger.valueOf(idTarefa));

            if (solicitacao.getDataHoraInicio() != null) {
                notification.setDate(UtilDatas.dateToSTR(solicitacao.getDataHoraInicio()));
            }

            notification.setLatitude(solicitacao.getLatitude());
            notification.setLongitude(solicitacao.getLongitude());

            notification.setEndSLA(" ");
            if (UtilStrings.nullToVazio(solicitacao.getSituacaoSLA()).equalsIgnoreCase("A") && solicitacao.getDataHoraLimite() != null
                    && solicitacao.getSlaACombinar().equalsIgnoreCase("N")) {
                notification.setEndSLA(solicitacao.getDataHoraLimiteStr());
            }

            notification.setTask(solicitacao.getNomeElementoFluxo());
            notification.setService(solicitacao.getNomeServico());

            notification.setTypeRequest(0);
            if (solicitacao.getAprovacao() != null && solicitacao.getAprovacao().trim().equalsIgnoreCase("S")) {
                notification.setTypeRequest(1);
                notification.setWaiting(1);
            }

            if (this.validaPermissaoFluxo(usuario, solicitacao.getSituacao(), idTarefa) && notification.getTypeRequest() == 0
                    && !solicitacao.getTipoAtribuicao().trim().equalsIgnoreCase(TipoAtribuicao.Acompanhamento.toString()) && solicitacao.getIdentificacaoTemplate() == null) {
                notification.setWaiting(1);
            }

            final Integer idUsuario = usuario.getIdUsuario();

            final Integer idResponsavel = solicitacao.getIdResponsavel();
            if (idResponsavel != null && idResponsavel.equals(idUsuario) || solicitacao.getIdSolicitacaoIndividual() != null) {
                notification.setPersonal(true);
            }

            if (solicitacao.getDataInicioAtendimento() != null) {
                notification.setInService(true);
            }

            notification.setInChekin(0);
            final boolean isCheckinAtivo = this.isCheckinAtivo(idTarefa, idSolicitacao, idUsuario);
            if (isCheckinAtivo) {
                notification.setInChekin(1);
            }

            Integer timeSLA = 0;
            if (solicitacao.getPrazoHH().intValue() != 0) {
                timeSLA += 60 * solicitacao.getPrazoHH();
            }
            if (solicitacao.getPrazoMM().intValue() != 0) {
                timeSLA += solicitacao.getPrazoMM();
            }

            notification.setContract(solicitacao.getIdContrato());
            notification.setUnit(solicitacao.getIdUnidade());

            notification.setTimeSLA(timeSLA);

            notification.setPriorityorder(solicitacao.getPriorityorder());
            notification.setTimeFlag(this.determinaTimeFlag(solicitacao));
            notification.setType(this.determinaTipoSolicitacao(solicitacao));

            resp.getNotifications().add(notification);
        }
    }

    private boolean validaPermissaoFluxo(final UsuarioDTO usuario, final String situacaoSolicitacao, final Integer idTarefa) {
        final PermissoesFluxoDTO permissoesFluxo = this.getPermissoesFluxoService().findByIdFluxoAndIdUsuario(usuario.getIdUsuario(), idTarefa);

        // Valida Permissão de reativação
        if (situacaoSolicitacao.trim().equalsIgnoreCase(SituacaoSolicitacaoServico.Suspensa.name()) && permissoesFluxo != null
                && permissoesFluxo.getReativar().trim().equalsIgnoreCase("S")) {
            return true;
        }

        if (permissoesFluxo != null && permissoesFluxo.getExecutar().trim().equalsIgnoreCase("S")) {
            return true;
        }

        return false;
    }

    private boolean isCheckinAtivo(final Integer idTarefa, final Integer idSolicitacao, final Integer idUsuario) {
        try {
            return this.getCheckinService().isCheckinAtivo(idTarefa, idSolicitacao, idUsuario);
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
        return false;
    }

    private AtribuicaoSolicitacaoAtendenteService atribuicaoSolicitacaoAtendenteService;
    private CheckinService checkinService;
    private CheckinDeniedService checkinDeniedService;
    private CheckoutService checkoutService;
    private PermissoesFluxoService permissoesFluxoService;
    private PosicionamentoAtendenteService posicionamentoAtendenteService;
    private SolicitacaoServicoServiceForMobileV2 solicitacaoServicoService;

    private AtribuicaoSolicitacaoAtendenteService getAtribuicaoSolicitacaoAtendenteService() {
        if (atribuicaoSolicitacaoAtendenteService == null) {
            try {
                atribuicaoSolicitacaoAtendenteService = (AtribuicaoSolicitacaoAtendenteService) ServiceLocator.getInstance().getService(
                        AtribuicaoSolicitacaoAtendenteService.class, null);
            } catch (final ServiceException e) {
                RestUtil.handleServiceException(LOGGER, e);
            }
        }
        return atribuicaoSolicitacaoAtendenteService;
    }

    private CheckinService getCheckinService() {
        if (checkinService == null) {
            try {
                checkinService = (CheckinService) ServiceLocator.getInstance().getService(CheckinService.class, null);
            } catch (final ServiceException e) {
                RestUtil.handleServiceException(LOGGER, e);
            }
        }
        return checkinService;
    }

    private CheckinDeniedService getCheckinDenied() {
        if (checkinDeniedService == null) {
            try {
                checkinDeniedService = (CheckinDeniedService) ServiceLocator.getInstance().getService(CheckinDeniedService.class, null);
            } catch (final ServiceException e) {
                RestUtil.handleServiceException(LOGGER, e);
            }
        }
        return checkinDeniedService;
    }

    private CheckoutService getCheckoutService() {
        if (checkoutService == null) {
            try {
                checkoutService = (CheckoutService) ServiceLocator.getInstance().getService(CheckoutService.class, null);
            } catch (final ServiceException e) {
                RestUtil.handleServiceException(LOGGER, e);
            }
        }
        return checkoutService;
    }

    private PermissoesFluxoService getPermissoesFluxoService() {
        if (permissoesFluxoService == null) {
            try {
                permissoesFluxoService = (PermissoesFluxoService) ServiceLocator.getInstance().getService(PermissoesFluxoService.class, null);
            } catch (final ServiceException e) {
                RestUtil.handleServiceException(LOGGER, e);
            }
        }
        return permissoesFluxoService;
    }

    private PosicionamentoAtendenteService getPosicionamentoAtendenteService() {
        if (posicionamentoAtendenteService == null) {
            try {
                posicionamentoAtendenteService = (PosicionamentoAtendenteService) ServiceLocator.getInstance().getService(PosicionamentoAtendenteService.class, null);
            } catch (final ServiceException e) {
                RestUtil.handleServiceException(LOGGER, e);
            }
        }
        return posicionamentoAtendenteService;
    }

    private SolicitacaoServicoServiceForMobileV2 getSolicitacaoServicoService() {
        if (solicitacaoServicoService == null) {
            try {
                solicitacaoServicoService = (SolicitacaoServicoServiceForMobileV2) ServiceLocator.getInstance().getService(SolicitacaoServicoServiceForMobileV2.class, null);
            } catch (final ServiceException e) {
                RestUtil.handleServiceException(LOGGER, e);
            }
        }
        return solicitacaoServicoService;
    }

    private Pageable makePageable(final CTPageRequest pageRequest) {
        final Integer page = pageRequest.getPage() != null ? pageRequest.getPage() : 1;
        final Integer size = pageRequest.getSize() != null ? pageRequest.getSize() : ParametroUtil.getValorParametro(ParametroSistema.REST_SERVICES_DEFAULT_PAGE_SIZE, "10");
        return new PageRequest(page - 1, size);
    }

}
