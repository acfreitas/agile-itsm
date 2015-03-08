package br.com.centralit.citsmart.rest.v2.resource;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import br.com.centralit.citcorpore.bean.AssociacaoDeviceAtendenteDTO;
import br.com.centralit.citcorpore.negocio.AssociacaoDeviceAtendenteService;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.resource.RestOperationResources;
import br.com.centralit.citsmart.rest.schema.CtError;
import br.com.centralit.citsmart.rest.schema.CtLogin;
import br.com.centralit.citsmart.rest.schema.CtLoginResp;
import br.com.centralit.citsmart.rest.schema.CtMessage;
import br.com.centralit.citsmart.rest.util.RestEnum;
import br.com.centralit.citsmart.rest.util.RestOperationUtil;
import br.com.centralit.citsmart.rest.v2.schema.CTLoginResp;
import br.com.centralit.citsmart.rest.v2.schema.CTServiceCoordinate;
import br.com.centralit.citsmart.rest.v2.schema.CTServiceDeviceDissassociate;
import br.com.centralit.citsmart.rest.v2.schema.CTServiceListUnits;
import br.com.centralit.citsmart.rest.v2.util.RESTOperations;
import br.com.citframework.push.DevicePlatformType;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Assert;
import br.com.citframework.util.WebUtil;

/**
 * Serviços para versão V2 dos serviços
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 24/09/2014
 *
 */
@Path("/services/v2")
public class RESTOperationResources extends RestOperationResources {

    private static final Logger LOGGER = Logger.getLogger(RESTOperationResources.class.getName());

    private final String CALL_MESSAGE = "Service handling on '%s' called with parameters '%s'";

    private static final String COORDINATES = "/coordinates";
    private static final String DEVICEDISASSOCIATE = "/device/disassociate";
    private static final String LISTCONTRACTS = "/contracts";
    private static final String LISTDENIEDREASONS = "/deniedReasons";
    private static final String LISTSOLICITATIONSTATUS = "/status";
    private static final String LISTUNITS = "/units";
    private static final String LOGIN = "/login";

    @POST
    @Path(COORDINATES)
    public Response coordinates(final CTServiceCoordinate input) {
        LOGGER.log(Level.FINE, String.format(CALL_MESSAGE, COORDINATES, ReflectionToStringBuilder.toString(input)));
        input.setMessageID(RESTOperations.COORDINATES.getMessageID());
        return RestOperationUtil.execute(input);
    }

    @POST
    @Path(DEVICEDISASSOCIATE)
    public Response deviceDisassociate(final CTServiceDeviceDissassociate input) {
        LOGGER.log(Level.FINE, String.format(CALL_MESSAGE, DEVICEDISASSOCIATE, ReflectionToStringBuilder.toString(input)));
        input.setMessageID(RESTOperations.DEVICE_DISASSOCIATE.getMessageID());
        return RestOperationUtil.execute(input);
    }

    @POST
    @Path(LISTCONTRACTS)
    public Response listContracts(final CtMessage input) {
        LOGGER.log(Level.FINE, String.format(CALL_MESSAGE, LISTCONTRACTS, ReflectionToStringBuilder.toString(input)));
        input.setMessageID(RESTOperations.LIST_CONTRACTS.getMessageID());
        return RestOperationUtil.execute(input);
    }

    @POST
    @Path(LISTDENIEDREASONS)
    public Response listDeniedReasons(final CtMessage input) {
        LOGGER.log(Level.FINE, String.format(CALL_MESSAGE, LISTDENIEDREASONS, ReflectionToStringBuilder.toString(input)));
        input.setMessageID(RESTOperations.LIST_DENIED_REASONS.getMessageID());
        return RestOperationUtil.execute(input);
    }

    @POST
    @Path(LISTSOLICITATIONSTATUS)
    public Response listSolicitationStatus(final CtMessage input) {
        LOGGER.log(Level.FINE, String.format(CALL_MESSAGE, LISTSOLICITATIONSTATUS, ReflectionToStringBuilder.toString(input)));
        input.setMessageID(RESTOperations.LIST_SOLICITATION_STATUS.getMessageID());
        return RestOperationUtil.execute(input);
    }

    @POST
    @Path(LISTUNITS)
    public Response listUnits(final CTServiceListUnits input) {
        LOGGER.log(Level.FINE, String.format(CALL_MESSAGE, LISTUNITS, ReflectionToStringBuilder.toString(input)));
        input.setMessageID(RESTOperations.LIST_UNITS.getMessageID());
        return RestOperationUtil.execute(input);
    }

    @Override
    @POST
    @Path(LOGIN)
    public Response login(final CtLogin login) throws JAXBException {
        final CTLoginResp resp = new CTLoginResp();
        try {
            Assert.notNullAndNotEmpty(login.getToken(), "Token must not be null or empty");
            Assert.notNullAndNotEmpty(login.getPlatform(), "Platform must not be null");
            final RestSessionDTO session = this.makeRESTSession(login);
            final String sessionID = session.getSessionID();
            if (StringUtils.isNotBlank(sessionID)) {
                this.allocateDeviceToAttendant(login, session);
                this.adjustCTLoginResp(resp);
                resp.setSessionID(sessionID);
                return Response.status(Status.OK).entity(resp).build();
            }
            return Response.status(Status.PRECONDITION_FAILED).entity(resp).build();
        } catch (final Exception e) {
            final CtError error = RestOperationUtil.buildError(RestEnum.INPUT_ERROR, e.getMessage());
            resp.setError(error);
            return Response.status(Status.PRECONDITION_FAILED).entity(resp).build();
        }
    }

    protected CtLoginResp adjustCTLoginResp(final CTLoginResp resp) {
        resp.setRangeAction(ParametroUtil.getValorParametro(ParametroSistema.MOBILE_RANGE_ACTION, "30"));
        resp.setLocationInterval(ParametroUtil.getValorParametro(ParametroSistema.MOBILE_LOCATION_INTERVAL, "10"));
        return resp;
    }

    private void allocateDeviceToAttendant(final CtLogin login, final RestSessionDTO session) throws Exception {
        final AssociacaoDeviceAtendenteDTO alocacao = new AssociacaoDeviceAtendenteDTO();
        final DevicePlatformType platform = DevicePlatformType.fromDescription(login.getPlatform());
        alocacao.setDevicePlatform(platform.getId());
        alocacao.setToken(login.getToken());
        alocacao.setIdUsuario(session.getUserId());
        alocacao.setConnection(WebUtil.getURLFromRequest(httpRequest) + httpRequest.getContextPath());
        alocacao.setActive(1);
        this.getAssociacaoDeviceAtendenteService().associateDeviceToAttendant(alocacao, session.getUser());
    }

    private AssociacaoDeviceAtendenteService associacaoDeviceAtendenteService;

    private AssociacaoDeviceAtendenteService getAssociacaoDeviceAtendenteService() throws Exception {
        if (associacaoDeviceAtendenteService == null) {
            associacaoDeviceAtendenteService = (AssociacaoDeviceAtendenteService) ServiceLocator.getInstance().getService(AssociacaoDeviceAtendenteService.class, null);
        }
        return associacaoDeviceAtendenteService;
    }

}
