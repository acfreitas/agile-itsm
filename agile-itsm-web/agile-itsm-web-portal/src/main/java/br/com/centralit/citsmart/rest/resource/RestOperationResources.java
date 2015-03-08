package br.com.centralit.citsmart.rest.resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citsmart.rest.bean.RestExecutionDTO;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.schema.CtAddServiceRequest;
import br.com.centralit.citsmart.rest.schema.CtAddServiceRequestResp;
import br.com.centralit.citsmart.rest.schema.CtError;
import br.com.centralit.citsmart.rest.schema.CtLogin;
import br.com.centralit.citsmart.rest.schema.CtLoginResp;
import br.com.centralit.citsmart.rest.schema.CtMessage;
import br.com.centralit.citsmart.rest.service.RestSessionService;
import br.com.centralit.citsmart.rest.service.RestSessionServiceEjb;
import br.com.centralit.citsmart.rest.util.RestEnum;
import br.com.centralit.citsmart.rest.util.RestOperationUtil;
import br.com.centralit.citsmart.rest.util.RestUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilXMLDate;

@Path("/services")
public class RestOperationResources {

    private static final Logger LOGGER = Logger.getLogger(RestOperationResources.class.getName());

    @Context
    protected HttpServletRequest httpRequest;

    @POST
    @Path("/login")
    public Response login(final CtLogin login) throws JAXBException {
        final CtLoginResp resp = new CtLoginResp();
        try {
            final RestSessionDTO session = this.makeRESTSession(login);
            final String sessionID = session.getSessionID();
            if (StringUtils.isNotBlank(sessionID)) {
                resp.setSessionID(sessionID);
            } else {
                return Response.status(Status.PRECONDITION_FAILED).entity(resp).build();
            }
        } catch (final Exception e) {
            final CtError error = RestOperationUtil.buildError(RestEnum.SESSION_ERROR, e.getMessage());
            resp.setError(error);
            return Response.status(Status.PRECONDITION_FAILED).entity(resp).build();
        }

        return Response.status(Status.OK).entity(resp).build();
    }

    protected RestSessionDTO makeRESTSession(final CtLogin login) throws ServiceException, Exception {
        httpRequest.getSession().setAttribute("locale", UtilI18N.getLocale());
        return this.getRESTSessionService().newSession(httpRequest, login);
    }

    @POST
    @Path("/xml/login")
    public Response login(final String xml) throws JAXBException {
        final InputStream ioos = new ByteArrayInputStream(xml.getBytes(Charset.defaultCharset()));
        final CtLogin login = JAXB.unmarshal(ioos, CtLogin.class);
        return this.login(login);
    }

    @POST
    @Path("/addServiceRequest")
    public Response addServiceRequest(final br.com.centralit.citsmart.rest.schema.old.CtAddServiceRequest addServiceRequest) throws JAXBException {
        final CtAddServiceRequestResp resp = new CtAddServiceRequestResp();
        resp.setDateTime(UtilXMLDate.toXMLGregorianCalendar(UtilDatas.getDataHoraAtual()));

        final CtAddServiceRequest input = new CtAddServiceRequest();

        try {
            Reflexao.copyPropertyValues(addServiceRequest, input);
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            final CtError error = RestOperationUtil.buildError(RestEnum.INTERNAL_ERROR, e.getMessage());
            resp.setError(error);
            return Response.status(Status.PRECONDITION_FAILED).entity(resp).build();
        }

        input.setMessageID("addServiceRequest");
        input.setServiceRequestSource(addServiceRequest.getServiceRequestSource());

        final RestSessionDTO restSession = RestUtil.getRestSessionService(null).getSession(input.getSessionID());
        if (!RestUtil.isValidSession(restSession)) {
            final CtError error = RestOperationUtil.buildError(RestEnum.SESSION_ERROR, "Sessão não existe ou está expirada");
            resp.setError(error);
            return Response.status(Status.PRECONDITION_FAILED).entity(resp).build();
        }

        final RestOperationDTO restOperation = RestUtil.getRestOperationService(restSession).findByName(input.getMessageID());
        if (restOperation == null) {
            final CtError error = RestOperationUtil.buildError(RestEnum.PARAM_ERROR, "Operação não cadastrada");
            resp.setError(error);
            return Response.status(Status.PRECONDITION_FAILED).entity(resp).build();
        }

        if (!RestUtil.getRestPermissionService(restSession).allowedAccess(restSession, restOperation)) {
            final CtError error = RestOperationUtil.buildError(RestEnum.PARAM_ERROR, "Usuário não tem acesso à operação");
            resp.setError(error);
            return Response.status(Status.PRECONDITION_FAILED).entity(resp).build();
        }

        RestExecutionDTO restExecution = null;
        try {
            restExecution = RestOperationUtil.initialize(restSession, restOperation, input);
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            final CtError error = RestOperationUtil.buildError(RestEnum.INTERNAL_ERROR, RestUtil.stackToString(e));
            resp.setError(error);
            return Response.status(Status.PRECONDITION_FAILED).entity(resp).build();
        }

        try {
            final CtAddServiceRequestResp result = (CtAddServiceRequestResp) RestOperationUtil.execute(restSession, restOperation, input);
            resp.setDateTime(result.getDateTime());
            resp.setError(result.getError());
            resp.setOperationID(result.getOperationID());
            resp.setServiceRequestDest(result.getServiceRequestDest());
            RestOperationUtil.finalize(restOperation, restExecution, restSession, result);
            if (resp.getError() == null) {
                return Response.status(Status.OK).entity(resp).build();
            }
            return Response.status(Status.PRECONDITION_FAILED).entity(resp).build();
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            final CtError error = RestUtil.getRestLogService(restSession).create(restExecution, e);
            resp.setError(error);
            return Response.status(Status.PRECONDITION_FAILED).entity(resp).build();
        }
    }

    @POST
    @Path("/xml/addServiceRequest")
    public Response addServiceRequest(final String xml) throws JAXBException {
        final InputStream ioos = new ByteArrayInputStream(xml.getBytes(Charset.defaultCharset()));
        final br.com.centralit.citsmart.rest.schema.old.CtAddServiceRequest addServiceRequest = JAXB.unmarshal(ioos,
                br.com.centralit.citsmart.rest.schema.old.CtAddServiceRequest.class);
        return this.addServiceRequest(addServiceRequest);
    }

    @POST
    @Path("/execute")
    public Response execute(final CtMessage input) {
        return RestOperationUtil.execute(input);
    }

    private RestSessionService restSessionService;

    private RestSessionService getRESTSessionService() throws Exception {
        if (restSessionService == null) {
            restSessionService = new RestSessionServiceEjb();
        }
        return restSessionService;
    }

}
