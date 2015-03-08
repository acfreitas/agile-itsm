package br.com.centralit.citsmart.rest.operation;

import java.util.Collection;
import java.util.Map;

import javax.xml.bind.JAXBException;

import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.centralit.citsmart.rest.bean.RestDomainDTO;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.schema.CtAddServiceRequest;
import br.com.centralit.citsmart.rest.schema.CtAddServiceRequestResp;
import br.com.centralit.citsmart.rest.schema.CtContact;
import br.com.centralit.citsmart.rest.schema.CtService;
import br.com.centralit.citsmart.rest.schema.CtServiceRequest;
import br.com.centralit.citsmart.rest.schema.StServiceRequestPriority;
import br.com.centralit.citsmart.rest.schema.StServiceRequestType;
import br.com.centralit.citsmart.rest.util.RestEnum;
import br.com.centralit.citsmart.rest.util.RestOperationUtil;
import br.com.centralit.citsmart.rest.util.RestUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilXMLDate;

public class RestAddServiceRequest implements IRestOperation<CtAddServiceRequest, CtAddServiceRequestResp> {

    @Override
    public CtAddServiceRequestResp execute(final RestSessionDTO restSessionDto, final RestOperationDTO restOperationDto, final CtAddServiceRequest message) throws JAXBException {
        final CtAddServiceRequestResp resp = new CtAddServiceRequestResp();

        final CtServiceRequest serviceRequestSource = message.getServiceRequestSource();
        if (serviceRequestSource == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Dados da solicitação de serviço não informados"));
            return resp;
        }
        if (serviceRequestSource.getDescription() == null || serviceRequestSource.getDescription().trim().equals("")) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Descrição da solicitação de serviço não informada"));
            return resp;
        }
        if (serviceRequestSource.getNumber() == null || serviceRequestSource.getNumber().trim().equals("")) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Número da solicitação de serviço não informado"));
            return resp;
        }
        if (serviceRequestSource.getImpact() == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Impacto da solicitação de serviço não informado"));
            return resp;
        }
        if (serviceRequestSource.getUrgency() == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Urgência da solicitação de serviço não informada"));
            return resp;
        }
        if (serviceRequestSource.getStartDateTime() == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Data/Hora início da solicitação de serviço não informada"));
            return resp;
        }
        if (serviceRequestSource.getType() == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Tipo da solicitação de serviço não informada"));
            return resp;
        }
        if (serviceRequestSource.getUserID() == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Solicitante da solicitação de serviço não informado"));
            return resp;
        }

        final CtService service = serviceRequestSource.getService();
        if (service == null || service.getCode() == null || service.getCode().trim().equals("")) {
            resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Serviço não informado"));
            return resp;
        }

        SolicitacaoServicoService solicitacaoServicoService = null;
        try {
            solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class,
                    RestUtil.getUsuarioSistema(restSessionDto));
            final Collection<SolicitacaoServicoDTO> col = solicitacaoServicoService.findByCodigoExterno(serviceRequestSource.getNumber());
            if (col != null && !col.isEmpty()) {
                resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Solicitação de serviço já cadastrada no CITSmart"));
                return resp;
            }
        } catch (final Exception e) {
            e.printStackTrace();
            resp.setError(RestOperationUtil.buildError(e));
            return resp;
        }

        final EmpregadoDTO empregadoDto = RestUtil.getEmpregadoByLogin(serviceRequestSource.getUserID());
        if (empregadoDto == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, "Dados do usuário não cadastrados"));
            return resp;
        }

        if (!RestUtil.getRestPermissionService(restSessionDto).allowedAccess(restSessionDto, restOperationDto)) {
            resp.setError(RestOperationUtil.buildError(RestEnum.PERMISSION_ERROR, "Execução da operação não é permitida para o usuário"));
            return resp;
        }

        final Map<String, RestDomainDTO> mapParam = RestUtil.getRestParameterService(restSessionDto).findParameters(restSessionDto, restOperationDto);
        if (mapParam == null || mapParam.isEmpty()) {
            resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, "Parâmetros da operação não cadastrados"));
            return resp;
        }

        final String idContratoStr = RestUtil.getRestParameterService(restSessionDto).getParamValue(mapParam, RestEnum.PARAM_CONTRACT_ID);
        if (idContratoStr == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, "ID do contrato não definido"));
            return resp;
        }

        final String idOrigemStr = RestUtil.getRestParameterService(restSessionDto).getParamValue(mapParam, RestEnum.PARAM_ORIGIN_ID);
        if (idOrigemStr == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, "ID da origem de atendimento não definido"));
            return resp;
        }

        String obs = "Incidente criado ";
        String idTipoDemandaStr = null;
        if (serviceRequestSource.getType().equals(StServiceRequestType.R)) {
            idTipoDemandaStr = RestUtil.getRestParameterService(restSessionDto).getParamValue(mapParam, RestEnum.PARAM_REQUEST_ID);
            obs = "Requisição criada ";
        } else {
            idTipoDemandaStr = RestUtil.getRestParameterService(restSessionDto).getParamValue(mapParam, RestEnum.PARAM_INCIDENT_ID);
        }
        if (idTipoDemandaStr == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, "ID do tipo de demanda não definido"));
            return resp;
        }

        Integer idUnidade = restSessionDto.getDptoId();
        if (idUnidade == null) {
            final String idUnidadeStr = RestUtil.getRestParameterService(restSessionDto).getParamValue(mapParam, RestEnum.PARAM_DEFAULT_DEPTO_ID);
            if (idUnidadeStr == null) {
                resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, "ID da unidade não definido"));
            }
            idUnidade = new Integer(idUnidadeStr);
        }

        ServicoContratoDTO servicoContratoDto = null;
        try {
            final ServicoContratoService restSessionService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class,
                    RestUtil.getUsuarioSistema(restSessionDto));
            servicoContratoDto = restSessionService.findByIdContratoAndIdServico(new Integer(idContratoStr), new Integer(service.getCode()));
        } catch (final Exception e1) {
            e1.printStackTrace();
        }
        if (servicoContratoDto == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, "Serviço não associado ao contrato"));
            return resp;
        }
        if (servicoContratoDto.getIdGrupoExecutor() == null) {
            resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, "Grupo executor não parametrizado para o serviço"));
            return resp;
        }

        String impacto = "B";
        if (serviceRequestSource.getImpact().equals(StServiceRequestPriority.H)) {
            impacto = "A";
        } else if (serviceRequestSource.getImpact().equals(StServiceRequestPriority.M)) {
            impacto = "M";
        }

        String urgencia = "B";
        if (serviceRequestSource.getUrgency().equals(StServiceRequestPriority.H)) {
            urgencia = "A";
        } else if (serviceRequestSource.getImpact().equals(StServiceRequestPriority.M)) {
            urgencia = "M";
        }

        SolicitacaoServicoDTO solicitacaoDto = new SolicitacaoServicoDTO();
        solicitacaoDto.setUsuarioDto(restSessionDto.getUser());
        solicitacaoDto.setIdContrato(new Integer(idContratoStr));
        solicitacaoDto.setIdOrigem(new Integer(idOrigemStr));
        solicitacaoDto.setIdServico(new Integer(service.getCode()));
        solicitacaoDto.setIdSolicitante(empregadoDto.getIdEmpregado());
        solicitacaoDto.setIdTipoDemandaServico(new Integer(idTipoDemandaStr));
        solicitacaoDto.setIdUnidade(idUnidade);
        solicitacaoDto.setImpacto(impacto);
        solicitacaoDto.setUrgencia(urgencia);
        solicitacaoDto.setIdGrupoNivel1(servicoContratoDto.getIdGrupoNivel1());
        solicitacaoDto.setIdGrupoAtual(servicoContratoDto.getIdGrupoExecutor());
        solicitacaoDto.setSituacao(SituacaoSolicitacaoServico.EmAndamento.name());

        solicitacaoDto.setNomecontato(empregadoDto.getNome());
        solicitacaoDto.setEmailcontato(empregadoDto.getEmail());
        solicitacaoDto.setTelefonecontato(empregadoDto.getTelefone());
        solicitacaoDto.setRamal(empregadoDto.getRamal());
        solicitacaoDto.setEnviaEmailCriacao("S");
        solicitacaoDto.setEnviaEmailAcoes("N");
        solicitacaoDto.setEnviaEmailFinalizacao("S");

        solicitacaoDto.setDescricao(serviceRequestSource.getDescription());
        solicitacaoDto.setRegistroexecucao("");
        solicitacaoDto.setCodigoExterno(serviceRequestSource.getNumber());
        solicitacaoDto.setObservacao(obs + "pelo CA-SDM.\nNúmero de origem: " + serviceRequestSource.getNumber() + "\nData/hora de origem: "
                + serviceRequestSource.getStartDateTime());

        try {
            solicitacaoDto = (SolicitacaoServicoDTO) solicitacaoServicoService.create(solicitacaoDto);
            solicitacaoDto = solicitacaoServicoService.restoreAll(solicitacaoDto.getIdSolicitacaoServico());
        } catch (final Exception e) {
            e.printStackTrace();
            resp.setError(RestOperationUtil.buildError(e));
            return resp;
        }

        final CtServiceRequest serviceRequestDest = new CtServiceRequest();
        final CtService serviceDest = new CtService();
        final CtContact contactDest = new CtContact();

        serviceRequestDest.setDescription(solicitacaoDto.getDescricao());
        serviceRequestDest.setStartSLA(UtilXMLDate.toXMLGregorianCalendar(solicitacaoDto.getDataHoraInicio()));
        serviceRequestDest.setEndSLA(UtilXMLDate.toXMLGregorianCalendar(solicitacaoDto.getDataHoraLimite()));
        serviceRequestDest.setImpact(serviceRequestSource.getImpact());
        serviceRequestDest.setUrgency(serviceRequestSource.getUrgency());
        serviceRequestDest.setStartDateTime(UtilXMLDate.toXMLGregorianCalendar(solicitacaoDto.getDataHoraInicio()));
        serviceRequestDest.setNumber("" + solicitacaoDto.getIdSolicitacaoServico());
        serviceRequestDest.setType(serviceRequestSource.getType());
        serviceRequestDest.setGroupId(solicitacaoDto.getGrupoAtual());
        serviceRequestDest.setUserID(serviceRequestSource.getUserID());

        contactDest.setEmail(empregadoDto.getEmail());
        contactDest.setName(empregadoDto.getNome());
        contactDest.setPhoneNumber(empregadoDto.getTelefone());
        contactDest.setDepartment(solicitacaoDto.getNomeUnidadeSolicitante());

        serviceDest.setCode("" + solicitacaoDto.getIdServico());
        serviceDest.setDescription(solicitacaoDto.getNomeServico());

        serviceRequestDest.setContact(contactDest);
        serviceRequestDest.setService(service);
        resp.setServiceRequestDest(serviceRequestDest);

        return resp;
    }

}
