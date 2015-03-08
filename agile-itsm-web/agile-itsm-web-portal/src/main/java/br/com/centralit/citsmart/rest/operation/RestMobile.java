package br.com.centralit.citsmart.rest.operation;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.ItemTrabalhoFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AcordoServicoContratoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.JustificativaParecerDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TemplateSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.AcordoServicoContratoService;
import br.com.centralit.citcorpore.negocio.ComplemInfSolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.JustificativaParecerService;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TemplateSolicitacaoServicoService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSLA;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.centralit.citcorpore.util.Enumerados.TipoSolicitacaoServico;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citsmart.rest.bean.RestDomainDTO;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.schema.CtMessage;
import br.com.centralit.citsmart.rest.schema.CtMessageResp;
import br.com.centralit.citsmart.rest.schema.CtNotification;
import br.com.centralit.citsmart.rest.schema.CtNotificationDetail;
import br.com.centralit.citsmart.rest.schema.CtNotificationFeedback;
import br.com.centralit.citsmart.rest.schema.CtNotificationFeedbackResp;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetById;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetByIdResp;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetByUser;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetByUserResp;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetReasons;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetReasonsResp;
import br.com.centralit.citsmart.rest.schema.CtNotificationNew;
import br.com.centralit.citsmart.rest.schema.CtNotificationNewResp;
import br.com.centralit.citsmart.rest.schema.CtReason;
import br.com.centralit.citsmart.rest.util.RestEnum;
import br.com.centralit.citsmart.rest.util.RestOperationUtil;
import br.com.centralit.citsmart.rest.util.RestUtil;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.geo.GeoUtils;

public class RestMobile implements IRestOperation<CtMessage, CtMessageResp> {

    private static final Logger LOGGER = Logger.getLogger(RestMobile.class.getName());

    private static final Integer NOPRAZO = 0;
    private static final Integer NOLIMITE = 1;
    private static final Integer ATRASADA = 2;
    private static final Integer SUSPENSA = 3;

    private static final int DEFAULT_VALUE = 123;
    private static final String DEFAULT_IMPACT_URGENCY = "B";

    @Override
    public CtMessageResp execute(final RestSessionDTO restSession, final RestOperationDTO restOperation, final CtMessage message) throws JAXBException {
        return RestUtil.execute(this, restSession, restOperation, message);
    }

    protected Integer determinaTimeFlag(final SolicitacaoServicoDTO solicitacao) {
        Integer result = NOPRAZO;
        final String situacaoSolicitacaoServico = solicitacao.getSituacao();
        if (solicitacao.getAtrasada() && !situacaoSolicitacaoServico.equals(SituacaoSolicitacaoServico.Suspensa.name())) {
            result = ATRASADA;
        } else if (situacaoSolicitacaoServico.equals(SituacaoSolicitacaoServico.Suspensa.name())) {
            result = SUSPENSA;
        } else if (solicitacao.getFalta1Hora()) {
            result = NOLIMITE;
        }
        return result;
    }

    protected SolicitacaoServicoDTO getSolicitacaoServicoByTarefa(final RestSessionDTO restSession, final TarefaFluxoDTO tarefaFluxo) throws Exception {
        SolicitacaoServicoDTO solicitacao = null;
        final Collection<TarefaFluxoDTO> listTarefas = new ArrayList<>();
        listTarefas.add(tarefaFluxo);
        final TipoSolicitacaoServico[] tipos = TipoSolicitacaoServico.values();
        final Collection<SolicitacaoServicoDTO> colSolicitacao = this.getSolicitacaoServicoService(restSession).listByTarefas(listTarefas, tipos);
        if (colSolicitacao != null && !colSolicitacao.isEmpty()) {
            solicitacao = ((List<SolicitacaoServicoDTO>) colSolicitacao).get(0);
        }
        return solicitacao;
    }

    protected boolean emAprovacao(final RestSessionDTO restSession, final ItemTrabalhoFluxoDTO itemTrabalho) throws Exception {
        boolean result = false;
        final ElementoFluxoDTO elementoFluxo = itemTrabalho.getElementoFluxoDto();
        if (itemTrabalho.getSituacao().trim().equals(SituacaoItemTrabalho.Disponivel.name()) && StringUtils.isNotBlank(elementoFluxo.getTemplate())) {
            final TemplateSolicitacaoServicoDTO template = this.getTemplateSolicitacaoServicoService(restSession).findByIdentificacao(elementoFluxo.getTemplate().trim());
            if (template != null && template.getAprovacao().equals("S")) {
                result = true;
            }
        }
        return result;
    }

    protected int determinaTipoSolicitacao(final SolicitacaoServicoDTO solicitacao) {
        int tipo = 0;
        final TipoSolicitacaoServico tipoSolicitacaoServico = solicitacao.getTipoSolicitacao();
        if (tipoSolicitacaoServico != null) {
            tipo = tipoSolicitacaoServico.getIdentifier();
        }
        return tipo;
    }

    protected void verificaImpactoUrgencia(final RestSessionDTO restSession, final SolicitacaoServicoDTO solicitacao) throws Exception {
        if (solicitacao.getIdServico() == null || solicitacao.getIdContrato() == null || solicitacao.getIdContrato().intValue() == 0) {
            return;
        }

        final ServicoContratoDTO servicoContrato = this.getServicoContratoService().findByIdContratoAndIdServico(solicitacao.getIdContrato(), solicitacao.getIdServico());

        solicitacao.setImpacto(DEFAULT_IMPACT_URGENCY);
        solicitacao.setUrgencia(DEFAULT_IMPACT_URGENCY);

        if (servicoContrato != null) {
            AcordoNivelServicoDTO acordoNivelServico = this.getAcordoNivelServicoService().findAtivoByIdServicoContrato(servicoContrato.getIdServicoContrato(), "T");
            if (acordoNivelServico == null) {
                // Se nao houver acordo especifico, ou seja, associado direto ao servicocontrato, entao busca um acordo geral que esteja vinculado ao servicocontrato.
                final AcordoServicoContratoDTO acordoServicoContrato = this.getAcordoServicoContratoService().findAtivoByIdServicoContrato(servicoContrato.getIdServicoContrato(),
                        "T");
                if (acordoServicoContrato == null) {
                    throw new LogicException(RestUtil.i18nMessage(restSession, "rest.service.mobile.sla.not.set"));
                }
                // Apos achar a vinculacao do acordo com o servicocontrato, entao faz um restore do acordo de nivel de servico.
                acordoNivelServico = new AcordoNivelServicoDTO();
                acordoNivelServico.setIdAcordoNivelServico(acordoServicoContrato.getIdAcordoNivelServico());
                acordoNivelServico = (AcordoNivelServicoDTO) this.getAcordoNivelServicoService().restore(acordoNivelServico);
                if (acordoNivelServico == null) {
                    throw new LogicException(RestUtil.i18nMessage(restSession, "rest.service.mobile.sla.not.set"));
                }
            }
            if (acordoNivelServico.getImpacto() != null) {
                solicitacao.setImpacto(acordoNivelServico.getImpacto());
            }

            if (acordoNivelServico.getUrgencia() != null) {
                solicitacao.setUrgencia(acordoNivelServico.getUrgencia());
            }
        }
    }

    protected CtNotificationGetByUserResp getByUser(final RestSessionDTO restSession, final CtNotificationGetByUser input) {
        final CtNotificationGetByUserResp resp = new CtNotificationGetByUserResp();

        if (restSession.getUser() == null || StringUtils.isBlank(restSession.getUser().getLogin())) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.login.not.identified")));
            return resp;
        }

        final int tipo = input.getNotificationType();

        if (!this.validNotificationType(restSession, resp, tipo)) {
            return resp;
        }

        try {
            final List<TarefaFluxoDTO> tarefas = RestUtil.getExecucaoSolicitacaoService(restSession).recuperaTarefas(restSession.getUser().getLogin(),
                    this.getTiposArrayFromType(tipo), this.getAprovacao(input.getOnlyApproval()));
            if (tarefas != null) {
                List<TarefaFluxoDTO> tarefasAux = null;
                // Alteração feito para limitar a lista enviada para o mobile, estava indo uma lista grande e estava estourando o banco.
                // Após a criação da paginação do mobile retirar esse tratamento.
                if (tarefas.size() > 200) {
                    tarefasAux = tarefas.subList(0, 200);
                } else {
                    tarefasAux = tarefas;
                }

                for (final TarefaFluxoDTO tarefa : tarefasAux) {
                    if (!tarefa.getExecutar().equalsIgnoreCase("S")) {
                        continue;
                    }
                    final CtNotification notification = new CtNotification();
                    final SolicitacaoServicoDTO solicitacao = (SolicitacaoServicoDTO) tarefa.getSolicitacaoDto();
                    notification.setNumber(BigInteger.valueOf(solicitacao.getIdSolicitacaoServico()));
                    if (solicitacao.getDataHoraInicio() != null) {
                        notification.setDate(UtilDatas.dateToSTR(solicitacao.getDataHoraInicio()));
                    }

                    notification.setTaskId(BigInteger.valueOf(tarefa.getIdItemTrabalho() == null ? DEFAULT_VALUE : tarefa.getIdItemTrabalho()));
                    if (solicitacao != null) {
                        notification.setType(this.determinaTipoSolicitacao(solicitacao));
                    }
                    if (StringUtils.isNotBlank(tarefa.getElementoFluxoDto().getDocumentacao())) {
                        notification.setTask(tarefa.getElementoFluxoDto().getDocumentacao());
                    }
                    if (UtilStrings.nullToVazio(solicitacao.getSituacaoSLA()).equalsIgnoreCase("A") && solicitacao.getDataHoraLimite() != null
                            && solicitacao.getSlaACombinar().equalsIgnoreCase("N")) {
                        notification.setEndSLA(solicitacao.getDataHoraLimiteStr());
                    }
                    if (StringUtils.isNotBlank(solicitacao.getServico())) {
                        notification.setService(solicitacao.getServico());
                    }
                    notification.setTimeFlag(this.determinaTimeFlag(solicitacao));
                    resp.getNotifications().add(notification);

                    if (this.emAprovacao(restSession, tarefa)) {
                        notification.setWaiting(1);
                    }
                }
                if (resp.getNotifications().size() == 0) {
                    resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.mandatory.fields.are.empty")));
                }
            }
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildError(e));
            resp.getNotifications().clear();
        }

        return resp;
    }

    protected void doGetByIDValidation(final RestSessionDTO restSession, final CtNotificationGetById input) throws Exception {
        if (input.getTaskId() == null) {
            throw new Exception(RestUtil.i18nMessage(restSession, "rest.service.mobile.task.id.null"));
        }
    }

    protected CtMessageResp getById(final RestSessionDTO restSession, final CtNotificationGetById input) {
        final CtNotificationGetByIdResp resp = new CtNotificationGetByIdResp();

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

        final CtNotificationDetail notification = new CtNotificationDetail();

        notification.setWaiting(detail.getWaiting());
        notification.setNumber(detail.getNumber());
        if (StringUtils.isNotBlank(detail.getDate())) {
            notification.setDate(detail.getDate());
        }
        notification.setTaskId(detail.getTaskId());
        notification.setType(detail.getType());
        notification.setDescription(detail.getDescription());
        notification.setStatus(detail.getStatus());
        notification.setTask(detail.getTask());
        notification.setTaskStatus(detail.getTaskStatus());
        notification.setService(detail.getService());
        notification.setTimeFlag(detail.getTimeFlag());

        resp.setNotification(notification);

        final String url = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.URL_Sistema, "").trim() + Constantes.getValue("CONTEXTO_APLICACAO");
        resp.setSite(url.replaceAll("//", "/"));

        return resp;
    }

    protected NotificationDetail populateNotificationDetail(final RestSessionDTO restSession, final Integer taskId) throws Exception {
        TarefaFluxoDTO tarefaFluxo = null;
        ElementoFluxoDTO elementoFluxo = null;
        final ItemTrabalho itemTrabalho = ItemTrabalho.getItemTrabalho(taskId);
        if (itemTrabalho != null) {
            tarefaFluxo = new TarefaFluxoDTO();
            Reflexao.copyPropertyValues(itemTrabalho.getItemTrabalhoDto(), tarefaFluxo);
            elementoFluxo = itemTrabalho.getElementoFluxoDto();
            tarefaFluxo.setElementoFluxoDto(elementoFluxo);
        }

        if (tarefaFluxo == null) {
            throw new Exception(RestUtil.i18nMessage(restSession, "rest.service.mobile.task.notfound"));
        }

        final SolicitacaoServicoDTO solicitacao = this.getSolicitacaoServicoByTarefa(restSession, tarefaFluxo);

        if (solicitacao == null) {
            throw new Exception(RestUtil.i18nMessage(restSession, "rest.service.mobile.service.solicitation.notfound"));
        }

        this.getSolicitacaoServicoService(restSession).determinaPrazoLimite(solicitacao, null, null);

        final NotificationDetail detail = new NotificationDetail();

        // específicos da V1
        final Integer id = solicitacao.getIdSolicitacaoServico();
        detail.setNumber(BigInteger.valueOf(id == null ? DEFAULT_VALUE : id));
        if (solicitacao.getDataHoraInicio() != null) {
            detail.setDate(UtilDatas.dateToSTR(solicitacao.getDataHoraInicio()));
        }
        detail.setTaskId(BigInteger.valueOf(tarefaFluxo.getIdItemTrabalho() == null ? DEFAULT_VALUE : tarefaFluxo.getIdItemTrabalho()));
        detail.setType(this.determinaTipoSolicitacao(solicitacao));
        detail.setTimeFlag(this.determinaTimeFlag(solicitacao));

        if (this.emAprovacao(restSession, tarefaFluxo)) {
            detail.setWaiting(1);
        }

        // específicos da V2
        detail.setTimeSLA(this.getTimeSLA(solicitacao));

        if (UtilStrings.nullToVazio(solicitacao.getSituacaoSLA()).equalsIgnoreCase("A") && solicitacao.getDataHoraLimite() != null
                && solicitacao.getSlaACombinar().equalsIgnoreCase("N")) {
            detail.setEndSLA(solicitacao.getDataHoraLimiteStr());
        }

        detail.setTask(elementoFluxo.getDocumentacao());
        detail.setService(solicitacao.getServico());

        String descricao = "";
        try {
            final ComplemInfSolicitacaoServicoService complemInfSolicitacaoServicoService = this.getSolicitacaoServicoService(restSession).getInformacoesComplementaresService(
                    itemTrabalho);
            if (complemInfSolicitacaoServicoService != null) {
                descricao = complemInfSolicitacaoServicoService.getInformacoesComplementaresFmtTexto(solicitacao, itemTrabalho);
            }
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }

        if (StringUtils.isBlank(descricao)) {
            descricao = solicitacao.getDescricaoSemFormatacao();
        }

        detail.setDescription(descricao);
        detail.setStatus(solicitacao.getDescrSituacao());
        detail.setTaskStatus(tarefaFluxo.getDescrSituacao());

        return detail;
    }

    private int getTimeSLA(final SolicitacaoServicoDTO solicitacao) throws Exception {
        int result = 0;

        final SituacaoSLA situacaoSLA = SituacaoSLA.fromNameIgnoreCase(solicitacao.getSituacaoSLA());
        if (SituacaoSLA.A.equals(situacaoSLA)) {
            final int atraso = (int) solicitacao.getAtrasoSLA();
            if (atraso > 0) {
                result = -(atraso / 60);
            } else {
                final Timestamp inicio = solicitacao.getDataHoraInicioSLA();
                final Timestamp fim = solicitacao.getDataHoraLimite();
                if (inicio != null && fim != null) {
                    final long timeInMillis = UtilDatas.calculaDiferencaTempoEmMilisegundos(fim, inicio);
                    result = (int) (timeInMillis / 1000 / 60);
                }
            }
        }

        return result;
    }

    protected CtNotificationFeedbackResp feedback(final RestSessionDTO restSession, final CtNotificationFeedback input) {
        final CtNotificationFeedbackResp resp = new CtNotificationFeedbackResp();

        if (input.getTaskId() == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.task.id.null")));
            return resp;
        }

        if (input.getFeedback() == 0 && input.getReasonId() == 0) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.task.justification.null")));
            return resp;
        }

        TarefaFluxoDTO tarefaFluxo = null;
        ElementoFluxoDTO elementoFluxo = null;
        ItemTrabalho itemTrabalho = null;
        try {
            itemTrabalho = ItemTrabalho.getItemTrabalho(input.getTaskId().intValue());
            if (itemTrabalho != null) {
                tarefaFluxo = new TarefaFluxoDTO();
                Reflexao.copyPropertyValues(itemTrabalho.getItemTrabalhoDto(), tarefaFluxo);
                elementoFluxo = itemTrabalho.getElementoFluxoDto();
                tarefaFluxo.setElementoFluxoDto(elementoFluxo);
            }
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildError(e));
            return resp;
        }

        try {
            if (!this.emAprovacao(restSession, tarefaFluxo)) {
                resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.task.already.approved")));
                return resp;
            }
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildError(e));
            return resp;
        }

        SolicitacaoServicoDTO solicitacao = null;
        try {
            solicitacao = this.getSolicitacaoServicoByTarefa(restSession, tarefaFluxo);
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildError(e));
            return resp;
        }

        if (solicitacao == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.service.solicitation.notfound")));
            return resp;
        }

        try {
            final ComplemInfSolicitacaoServicoService complemInfSolicitacaoServicoService = this.getSolicitacaoServicoService(restSession).getInformacoesComplementaresService(
                    itemTrabalho);

            if (complemInfSolicitacaoServicoService != null) {
                complemInfSolicitacaoServicoService.preparaSolicitacaoParaAprovacao(solicitacao, itemTrabalho, this.getAprovacao(input.getFeedback()), input.getReasonId(),
                        input.getComments());
            }

            if (solicitacao.getAcaoFluxo() != null) {
                solicitacao.setUsuarioDto(restSession.getUser());
                this.getSolicitacaoServicoService(restSession).updateInfo(solicitacao);
            } else {
                resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.action.not.defined")));
                return resp;
            }
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildError(e));
            return resp;
        }

        return resp;
    }

    protected CtNotificationNewResp add(final RestSessionDTO restSession, final RestOperationDTO restOperation, final CtNotificationNew input) {
        final CtNotificationNewResp resp = new CtNotificationNewResp();

        if (StringUtils.isBlank(input.getDescription())) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.description.solicitation.not.informed")));
            return resp;
        }

        if (restSession.getUser() == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.requestor.not.informed")));
            return resp;
        }

        final EmpregadoDTO empregadoDto = RestUtil.getEmpregadoByLogin(restSession.getUser().getLogin());
        if (empregadoDto == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.user.notfound")));
            return resp;
        }

        final String idContrato = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CONTRATO_PADRAO, "").trim();
        if (StringUtils.isBlank(idContrato)) {
            resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.contract.default.not.parametrized")));
            return resp;
        }

        final String idServico = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SERVICO_PADRAO_SOLICITACAO, "").trim();
        if (StringUtils.isBlank(idServico)) {
            resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.service.default.not.parametrized")));
            return resp;
        }

        ServicoDTO servico = null;
        try {
            final ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, RestUtil.getUsuarioSistema(restSession));
            servico = new ServicoDTO();
            servico.setIdServico(Integer.valueOf(idServico));
            servico = (ServicoDTO) servicoService.restore(servico);
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }

        if (servico == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.service.not.parametrized")));
            return resp;
        }

        ServicoContratoDTO servicoContrato = null;
        try {
            final ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class,
                    RestUtil.getUsuarioSistema(restSession));
            servicoContrato = servicoContratoService.findByIdContratoAndIdServico(Integer.valueOf(idContrato), Integer.valueOf(idServico));
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }

        if (servicoContrato == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.service.not.related")));
            return resp;
        }

        final String idOrigem = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_PADRAO_SOLICITACAO, "").trim();
        if (StringUtils.isBlank(idOrigem)) {
            resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.treatment.id.not.parametrized")));
            return resp;
        }

        Integer idUnidade = restSession.getDptoId();
        if (idUnidade == null) {
            final Map<String, RestDomainDTO> mapParam = RestUtil.getRestParameterService(restSession).findParameters(restSession, restOperation);
            if (mapParam == null || mapParam.isEmpty()) {
                resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.operation.params.not.registered")));
                return resp;
            }

            final String idUnidadeStr = RestUtil.getRestParameterService(restSession).getParamValue(mapParam, RestEnum.PARAM_DEFAULT_DEPTO_ID);
            if (idUnidadeStr == null) {
                resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.unit.id.undefined")));
            }
            idUnidade = Integer.valueOf(idUnidadeStr);
        }

        SolicitacaoServicoDTO solicitacaoServico = new SolicitacaoServicoDTO();
        solicitacaoServico.setUsuarioDto(restSession.getUser());
        solicitacaoServico.setIdContrato(Integer.valueOf(idContrato));
        solicitacaoServico.setIdOrigem(Integer.valueOf(idOrigem));
        solicitacaoServico.setIdServico(Integer.valueOf(idServico));
        solicitacaoServico.setIdSolicitante(empregadoDto.getIdEmpregado());
        solicitacaoServico.setIdTipoDemandaServico(servico.getIdTipoDemandaServico());
        solicitacaoServico.setIdUnidade(idUnidade);
        solicitacaoServico.setIdGrupoNivel1(servicoContrato.getIdGrupoNivel1());
        solicitacaoServico.setIdGrupoAtual(servicoContrato.getIdGrupoExecutor());
        solicitacaoServico.setSituacao(SituacaoSolicitacaoServico.EmAndamento.name());

        if (!this.setCoordinatesIfExists(solicitacaoServico, input)) {
            resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.v2.invalid.coordinates")));
            return resp;
        }

        try {
            this.verificaImpactoUrgencia(restSession, solicitacaoServico);
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildError(e));
            return resp;
        }

        solicitacaoServico.setNomecontato(empregadoDto.getNome());
        solicitacaoServico.setEmailcontato(empregadoDto.getEmail());
        solicitacaoServico.setTelefonecontato(empregadoDto.getTelefone());
        solicitacaoServico.setRamal(empregadoDto.getRamal());
        solicitacaoServico.setEnviaEmailCriacao("S");
        solicitacaoServico.setEnviaEmailAcoes("N");
        solicitacaoServico.setEnviaEmailFinalizacao("S");
        solicitacaoServico.setDescricao(input.getDescription());
        solicitacaoServico.setRegistroexecucao("");

        try {
            solicitacaoServico = (SolicitacaoServicoDTO) this.getSolicitacaoServicoService(restSession).create(solicitacaoServico);
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildError(e));
            return resp;
        }

        resp.setNumber(BigInteger.valueOf(solicitacaoServico.getIdSolicitacaoServico()));

        return resp;
    }

    protected CtNotificationGetReasonsResp getReasons(final RestSessionDTO restSession, final CtNotificationGetReasons input) {
        final CtNotificationGetReasonsResp resp = new CtNotificationGetReasonsResp();

        if (input.getTaskId() == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.task.id.null")));
            return resp;
        }

        TarefaFluxoDTO tarefaFluxo = null;
        ElementoFluxoDTO elementoFluxo = null;
        try {
            final ItemTrabalho itemTrabalho = ItemTrabalho.getItemTrabalho(input.getTaskId().intValue());
            if (itemTrabalho != null) {
                tarefaFluxo = new TarefaFluxoDTO();
                Reflexao.copyPropertyValues(itemTrabalho.getItemTrabalhoDto(), tarefaFluxo);
                elementoFluxo = itemTrabalho.getElementoFluxoDto();
                tarefaFluxo.setElementoFluxoDto(elementoFluxo);
            }
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildError(e));
            return resp;
        }

        SolicitacaoServicoDTO solicitacaoServico = null;
        try {
            solicitacaoServico = this.getSolicitacaoServicoByTarefa(restSession, tarefaFluxo);
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildError(e));
            return resp;
        }

        if (solicitacaoServico == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.service.solicitation.notfound")));
            return resp;
        }

        try {
            final TipoSolicitacaoServico tipoSolicitacaoServico = solicitacaoServico.getTipoSolicitacao();
            switch (tipoSolicitacaoServico) {
            case INCIDENTE:
            case REQUISICAO:
            case VIAGEM:
                Collection<JustificativaSolicitacaoDTO> justificativasSolicitacao = null;
                final JustificativaSolicitacaoService justificativaSolicitacaoService = (JustificativaSolicitacaoService) ServiceLocator.getInstance().getService(
                        JustificativaSolicitacaoService.class, RestUtil.getUsuarioSistema(restSession));
                if (tipoSolicitacaoServico.equals(TipoSolicitacaoServico.VIAGEM)) {
                    justificativasSolicitacao = justificativaSolicitacaoService.listAtivasParaViagem();
                } else {
                    justificativasSolicitacao = justificativaSolicitacaoService.listAtivasParaAprovacao();
                }
                if (justificativasSolicitacao != null) {
                    for (final JustificativaSolicitacaoDTO justificativaDto : justificativasSolicitacao) {
                        final CtReason justificativa = new CtReason();
                        justificativa.setId(justificativaDto.getIdJustificativa());
                        justificativa.setDesc(justificativaDto.getDescricaoJustificativa());
                        resp.getReasons().add(justificativa);
                    }
                }
                break;
            default:
                Collection<JustificativaParecerDTO> justificativasParecer = null;
                final JustificativaParecerService justificativaParecerService = (JustificativaParecerService) ServiceLocator.getInstance().getService(
                        JustificativaParecerService.class, RestUtil.getUsuarioSistema(restSession));
                if (tipoSolicitacaoServico.equals(TipoSolicitacaoServico.COMPRA)) {
                    if (StringUtils.containsIgnoreCase(elementoFluxo.getTemplate(), "APROVACAO")) {
                        justificativasParecer = justificativaParecerService.listAplicaveisCotacao();
                    } else if (StringUtils.containsIgnoreCase(elementoFluxo.getTemplate(), "AUTORIZACAO")) {
                        justificativasParecer = justificativaParecerService.listAplicaveisRequisicao();
                    }
                } else if (tipoSolicitacaoServico.equals(TipoSolicitacaoServico.VIAGEM) || tipoSolicitacaoServico.equals(TipoSolicitacaoServico.RH)) {
                    justificativasParecer = justificativaParecerService.list();
                }

                if (justificativasParecer != null) {
                    for (final JustificativaParecerDTO justificativaDto : justificativasParecer) {
                        final CtReason justificativa = new CtReason();
                        justificativa.setId(justificativaDto.getIdJustificativa());
                        justificativa.setDesc(justificativaDto.getDescricaoJustificativa());
                        resp.getReasons().add(justificativa);
                    }
                }
                break;
            }
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildError(e));
            return resp;
        }

        return resp;
    }

    protected <E extends CtMessageResp> boolean validNotificationType(final RestSessionDTO restSession, final E resp, final int tipo) {
        if (tipo < 0 || tipo > 5) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, RestUtil.i18nMessage(restSession, "rest.service.mobile.select.atleast.one.type")));
            return false;
        }
        return true;
    }

    private boolean setCoordinatesIfExists(final SolicitacaoServicoDTO solicitacaoServico, final CtNotificationNew input) {
        final Double latitude = input.getLatitude();
        final Double longitude = input.getLongitude();
        boolean result = true;
        if (latitude != null && longitude != null) {
            result = GeoUtils.validCoordinates(latitude, longitude);
            if (result) {
                solicitacaoServico.setLatitude(latitude);
                solicitacaoServico.setLongitude(longitude);
            }
        }
        return result;
    }

    protected String getAprovacao(final int approval) {
        return approval == 1 ? "S" : "N";
    }

    protected TipoSolicitacaoServico[] getTiposArrayFromType(final Integer type) {
        final List<TipoSolicitacaoServico> types = new ArrayList<>();
        if (type == 0) {
            types.addAll(Arrays.asList(TipoSolicitacaoServico.values()));
        } else {
            types.add(TipoSolicitacaoServico.fromIdentifier(type));
        }
        return types.toArray(new TipoSolicitacaoServico[types.size()]);
    }

    private AcordoNivelServicoService acordoNivelServicoService;
    private AcordoServicoContratoService acordoServicoContratoService;
    private ServicoContratoService servicoContratoService;
    private SolicitacaoServicoService solicitacaoServicoService;
    private TemplateSolicitacaoServicoService templateSolicitacaoServicoService;

    private AcordoNivelServicoService getAcordoNivelServicoService() {
        if (acordoNivelServicoService == null) {
            try {
                acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
            } catch (final ServiceException e) {
                RestUtil.handleServiceException(LOGGER, e);
            }
        }
        return acordoNivelServicoService;
    }

    private AcordoServicoContratoService getAcordoServicoContratoService() {
        if (acordoServicoContratoService == null) {
            try {
                acordoServicoContratoService = (AcordoServicoContratoService) ServiceLocator.getInstance().getService(AcordoServicoContratoService.class, null);
            } catch (final ServiceException e) {
                RestUtil.handleServiceException(LOGGER, e);
            }
        }
        return acordoServicoContratoService;
    }

    private ServicoContratoService getServicoContratoService() {
        if (servicoContratoService == null) {
            try {
                servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
            } catch (final ServiceException e) {
                RestUtil.handleServiceException(LOGGER, e);
            }
        }
        return servicoContratoService;
    }

    private SolicitacaoServicoService getSolicitacaoServicoService(final RestSessionDTO restSession) throws Exception {
        if (solicitacaoServicoService == null) {
            solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class,
                    RestUtil.getUsuarioSistema(restSession));
        }
        return solicitacaoServicoService;
    }

    private TemplateSolicitacaoServicoService getTemplateSolicitacaoServicoService(final RestSessionDTO restSession) throws Exception {
        if (templateSolicitacaoServicoService == null) {
            templateSolicitacaoServicoService = (TemplateSolicitacaoServicoService) ServiceLocator.getInstance().getService(TemplateSolicitacaoServicoService.class,
                    RestUtil.getUsuarioSistema(restSession));
        }
        return templateSolicitacaoServicoService;
    }

    protected class NotificationDetail {

        private String endSLA;
        private String task;
        private String service;
        private String description;
        private String status;
        private String taskStatus;

        // específicos da V1
        private BigInteger number;
        private String date;
        private BigInteger taskId;
        private Integer type;
        private Integer timeFlag;
        private int waiting;

        // específicos da V2
        private Integer timeSLA;

        public String getEndSLA() {
            return endSLA;
        }

        public void setEndSLA(final String endSLA) {
            this.endSLA = endSLA;
        }

        public String getTask() {
            return task;
        }

        public void setTask(final String task) {
            this.task = task;
        }

        public String getService() {
            return service;
        }

        public void setService(final String service) {
            this.service = service;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(final String description) {
            this.description = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(final String status) {
            this.status = status;
        }

        public String getTaskStatus() {
            return taskStatus;
        }

        public void setTaskStatus(final String taskStatus) {
            this.taskStatus = taskStatus;
        }

        // específicos da V1
        public BigInteger getNumber() {
            return number;
        }

        public void setNumber(final BigInteger number) {
            this.number = number;
        }

        public String getDate() {
            return date;
        }

        public void setDate(final String date) {
            this.date = date;
        }

        public BigInteger getTaskId() {
            return taskId;
        }

        public void setTaskId(final BigInteger taskId) {
            this.taskId = taskId;
        }

        public Integer getType() {
            return type;
        }

        public void setType(final Integer type) {
            this.type = type;
        }

        public Integer getTimeFlag() {
            return timeFlag;
        }

        public void setTimeFlag(final Integer timeFlag) {
            this.timeFlag = timeFlag;
        }

        public int getWaiting() {
            return waiting;
        }

        public void setWaiting(final int waiting) {
            this.waiting = waiting;
        }

        // específicos da V2
        public Integer getTimeSLA() {
            return timeSLA;
        }

        public void setTimeSLA(final Integer timeSLA) {
            this.timeSLA = timeSLA;
        }

    }

}
