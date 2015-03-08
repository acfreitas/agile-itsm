package br.com.centralit.citsmart.rest.v2.operation;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import br.com.centralit.citcorpore.bean.AssociacaoDeviceAtendenteDTO;
import br.com.centralit.citcorpore.bean.CategoriaSolucaoDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.MotivoNegacaoCheckinDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.negocio.AssociacaoDeviceAtendenteService;
import br.com.centralit.citcorpore.negocio.CategoriaSolucaoService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.MotivoNegacaoCheckinService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.operation.IRestOperation;
import br.com.centralit.citsmart.rest.schema.CtMessage;
import br.com.centralit.citsmart.rest.schema.CtMessageResp;
import br.com.centralit.citsmart.rest.util.RestOperationUtil;
import br.com.centralit.citsmart.rest.util.RestUtil;
import br.com.centralit.citsmart.rest.v2.schema.CTCommonContent;
import br.com.centralit.citsmart.rest.v2.schema.CTCommonContentWithParent;
import br.com.centralit.citsmart.rest.v2.schema.CTServiceCoordinate;
import br.com.centralit.citsmart.rest.v2.schema.CTServiceCoordinateResp;
import br.com.centralit.citsmart.rest.v2.schema.CTServiceDeviceDissassociate;
import br.com.centralit.citsmart.rest.v2.schema.CTServiceDeviceDissassociateResp;
import br.com.centralit.citsmart.rest.v2.schema.CTServiceListContractsResp;
import br.com.centralit.citsmart.rest.v2.schema.CTServiceListDeniedReasonsResp;
import br.com.centralit.citsmart.rest.v2.schema.CTServiceListStatusResp;
import br.com.centralit.citsmart.rest.v2.schema.CTServiceListStatusSolutionsResp;
import br.com.centralit.citsmart.rest.v2.schema.CTServiceListStatusSuspensionReasons;
import br.com.centralit.citsmart.rest.v2.schema.CTServiceListUnits;
import br.com.centralit.citsmart.rest.v2.schema.CTServiceListUnitsResp;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

/**
 * Implementação das operaçãos que respondem em {@code /service} da versão V2 de apis consumidas pelo mobile
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 29/09/2014
 *
 */
public class RESTOperation implements IRestOperation<CtMessage, CtMessageResp> {

    private static final Logger LOGGER = Logger.getLogger(RESTOperation.class.getName());

    @Override
    public CtMessageResp execute(final RestSessionDTO restSessionDto, final RestOperationDTO restOperationDto, final CtMessage message) throws JAXBException {
        return RestUtil.execute(this, restSessionDto, restOperationDto, message);
    }

    protected CTServiceCoordinateResp coordinates(final RestSessionDTO restSessionDto, final CTServiceCoordinate message) {
        final CTServiceCoordinateResp resp = new CTServiceCoordinateResp();
        try {
            final UnidadeDTO unidade = new UnidadeDTO();
            unidade.setIdUnidade(message.getUnitID());
            unidade.setLatitude(message.getLatitude());
            unidade.setLongitude(message.getLongitude());

            this.getUnidadeService().updateCoordenadas(restSessionDto.getUser().getLocale(), unidade);

            resp.setSuccess(true);
        } catch (final Exception e) {
            resp.setSuccess(false);
            resp.setError(RestOperationUtil.buildSimpleError(e));
        }
        return resp;
    }

    protected CTServiceDeviceDissassociateResp deviceDisassociate(final RestSessionDTO restSession, final CTServiceDeviceDissassociate message) {
        final CTServiceDeviceDissassociateResp resp = new CTServiceDeviceDissassociateResp();
        try {
            final AssociacaoDeviceAtendenteDTO associacao = new AssociacaoDeviceAtendenteDTO();
            associacao.setConnection(message.getConnection());
            associacao.setToken(message.getToken());
            associacao.setIdUsuario(restSession.getUserId());
            associacao.setActive(1);

            this.getAssociacaoDeviceAtendenteService().disassociateDeviceFromAttendant(associacao, restSession.getUser());

            resp.setSuccess(true);
        } catch (final Exception e) {
            resp.setSuccess(false);
            resp.setError(RestOperationUtil.buildSimpleError(e));
        }
        return resp;
    }

    protected CTServiceListContractsResp listContracts(final RestSessionDTO restSessionDto) {
        final CTServiceListContractsResp resp = new CTServiceListContractsResp();
        try {
            final Collection<ContratoDTO> contratos = this.getContratoService().findAtivosByIdEmpregado(restSessionDto.getUser().getIdEmpregado());
            if (contratos != null) {
                for (final ContratoDTO contratoDTO : contratos) {
                    final CTCommonContent commonMessage = new CTCommonContent();
                    commonMessage.setId(contratoDTO.getIdContrato());
                    commonMessage.setDescription(contratoDTO.getNumero());
                    resp.getContracts().add(commonMessage);
                }
            }
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildSimpleError(e));
        }
        return resp;
    }

    protected CTServiceListDeniedReasonsResp listDeniedReasons() {
        final CTServiceListDeniedReasonsResp resp = new CTServiceListDeniedReasonsResp();
        try {
            final Collection<MotivoNegacaoCheckinDTO> reasons = this.getMotivoNegacaoCheckinService().listAllActiveDeniedReasons();
            if (reasons != null) {
                for (final MotivoNegacaoCheckinDTO categoria : reasons) {
                    final CTCommonContent commonContent = new CTCommonContent();
                    commonContent.setId(categoria.getIdMotivo());
                    commonContent.setDescription(categoria.getDescricao());
                    resp.getReasons().add(commonContent);
                }
            }
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildSimpleError(e));
        }
        return resp;
    }

    protected CTServiceListStatusResp listSolicitationStatus() {
        final CTServiceListStatusResp resp = new CTServiceListStatusResp();
        try {
            final List<SituacaoSolicitacaoServico> situacoes = SituacaoSolicitacaoServico.getSituacoesSolicitacaoServicoToMobile();
            for (final SituacaoSolicitacaoServico situacao : situacoes) {
                switch (situacao) {
                case Resolvida:
                    final CTServiceListStatusSolutionsResp solutions = new CTServiceListStatusSolutionsResp();
                    solutions.setId(situacao.getId());
                    solutions.setDescription(situacao.getDescricao());
                    this.encloseSolutions(solutions);
                    resp.getStatus().add(solutions);
                    break;
                case Suspensa:
                    final CTServiceListStatusSuspensionReasons reasons = new CTServiceListStatusSuspensionReasons();
                    reasons.setId(situacao.getId());
                    reasons.setDescription(situacao.getDescricao());
                    this.encloseSuspensionReasons(reasons);
                    resp.getStatus().add(reasons);
                    break;
                default:
                    final CTCommonContent common = new CTCommonContent();
                    common.setId(situacao.getId());
                    common.setDescription(situacao.getDescricao());
                    resp.getStatus().add(common);
                    break;
                }
            }
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildSimpleError(e));
        }
        return resp;
    }

    protected void encloseSolutions(final CTServiceListStatusSolutionsResp common) throws Exception {
        final Collection<CategoriaSolucaoDTO> categorias = this.getCategoriaSolucaoService().listaCategoriasSolucaoAtivas();
        if (categorias != null) {
            for (final CategoriaSolucaoDTO categoria : categorias) {
                final CTCommonContentWithParent commonContent = new CTCommonContentWithParent();
                commonContent.setId(categoria.getIdCategoriaSolucao());
                commonContent.setDescription(categoria.getDescricaoCategoriaSolucao());
                final Integer parentId = categoria.getIdCategoriaSolucaoPai();
                if (parentId != null && parentId > 0) {
                    commonContent.setParentId(parentId);
                }
                common.getJustifications().add(commonContent);
            }
        }
    }

    protected void encloseSuspensionReasons(final CTServiceListStatusSuspensionReasons common) throws Exception {
        final Collection<JustificativaSolicitacaoDTO> motivos = this.getJustificativaSolicitacaoService().listAtivasParaSuspensao();
        if (motivos != null) {
            for (final JustificativaSolicitacaoDTO motivo : motivos) {
                final CTCommonContent commonContent = new CTCommonContent();
                commonContent.setId(motivo.getIdJustificativa());
                commonContent.setDescription(motivo.getDescricaoJustificativa());
                common.getJustifications().add(commonContent);
            }
        }
    }

    protected CTServiceListUnitsResp listUnits(final RestSessionDTO restSessionDTO, final CTServiceListUnits message) {
        final CTServiceListUnitsResp resp = new CTServiceListUnitsResp();
        try {
            final Collection<UnidadeDTO> unidades = this.getUnidadeService().listUnidadePorContrato(message.getContractID());
            if (unidades != null) {
                for (final UnidadeDTO unidade : unidades) {
                    final CTCommonContent commonMessage = new CTCommonContent();
                    commonMessage.setId(unidade.getIdUnidade());
                    commonMessage.setDescription(unidade.getNome());
                    resp.getUnits().add(commonMessage);
                }
            }
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestOperationUtil.buildSimpleError(e));
        }
        return resp;
    }

    private AssociacaoDeviceAtendenteService associacaoDeviceAtendenteService;
    private CategoriaSolucaoService categoriaSolucaoService;
    private ContratoService contratoService;
    private JustificativaSolicitacaoService justificativaSolicitacaoService;
    private MotivoNegacaoCheckinService motivoNegacaoCheckinService;
    private UnidadeService unidadeService;

    private AssociacaoDeviceAtendenteService getAssociacaoDeviceAtendenteService() {
        if (associacaoDeviceAtendenteService == null) {
            try {
                associacaoDeviceAtendenteService = (AssociacaoDeviceAtendenteService) ServiceLocator.getInstance().getService(AssociacaoDeviceAtendenteService.class, null);
            } catch (final ServiceException e) {
                RestUtil.handleServiceException(LOGGER, e);
            }
        }
        return associacaoDeviceAtendenteService;
    }

    private CategoriaSolucaoService getCategoriaSolucaoService() {
        if (categoriaSolucaoService == null) {
            try {
                categoriaSolucaoService = (CategoriaSolucaoService) ServiceLocator.getInstance().getService(CategoriaSolucaoService.class, null);
            } catch (final ServiceException e) {
                RestUtil.handleServiceException(LOGGER, e);
            }
        }
        return categoriaSolucaoService;
    }

    private ContratoService getContratoService() {
        if (contratoService == null) {
            try {
                contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
            } catch (final ServiceException e) {
                RestUtil.handleServiceException(LOGGER, e);
            }
        }
        return contratoService;
    }

    private JustificativaSolicitacaoService getJustificativaSolicitacaoService() {
        if (justificativaSolicitacaoService == null) {
            try {
                justificativaSolicitacaoService = (JustificativaSolicitacaoService) ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class, null);
            } catch (final ServiceException e) {
                RestUtil.handleServiceException(LOGGER, e);
            }
        }
        return justificativaSolicitacaoService;
    }

    private MotivoNegacaoCheckinService getMotivoNegacaoCheckinService() {
        if (motivoNegacaoCheckinService == null) {
            try {
                motivoNegacaoCheckinService = (MotivoNegacaoCheckinService) ServiceLocator.getInstance().getService(MotivoNegacaoCheckinService.class, null);
            } catch (final ServiceException e) {
                RestUtil.handleServiceException(LOGGER, e);
            }
        }
        return motivoNegacaoCheckinService;
    }

    private UnidadeService getUnidadeService() {
        if (unidadeService == null) {
            try {
                unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
            } catch (final ServiceException e) {
                RestUtil.handleServiceException(LOGGER, e);
            }
        }
        return unidadeService;
    }

}
