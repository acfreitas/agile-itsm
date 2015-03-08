package br.com.centralit.citsmart.rest.util;

import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citsmart.rest.bean.RestExecutionDTO;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.operation.IRestOperation;
import br.com.centralit.citsmart.rest.schema.CtError;
import br.com.centralit.citsmart.rest.schema.CtMessage;
import br.com.centralit.citsmart.rest.schema.CtMessageResp;
import br.com.centralit.citsmart.rest.util.RestEnum.ClassType;
import br.com.centralit.citsmart.rest.util.RestEnum.OperationType;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilXMLDate;

public class RestOperationUtil {

    private static final Logger LOGGER = Logger.getLogger(RestOperationUtil.class.getName());

    public static RestExecutionDTO initialize(final RestSessionDTO restSession, final RestOperationDTO restOperation, final Object input) throws JAXBException {
        RestExecutionDTO restExecution = null;
        try {
            restExecution = RestUtil.getRestExecutionService(restSession).start(restSession, restOperation, input);
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new JAXBException(e.getMessage());
        }
        if (restExecution != null) {
            restExecution.setRestOperationDto(restOperation);
            restExecution.setInput(input);
        }
        return restExecution;
    }

    public static void finalize(final RestOperationDTO restOperation, final RestExecutionDTO restExecution, final RestSessionDTO restSession, final CtMessageResp resp) {
        resp.setOperationID(BigInteger.valueOf(restExecution.getIdRestExecution()));
        resp.setDateTime(UtilXMLDate.toXMLGregorianCalendar(UtilDatas.getDataHoraAtual()));

        try {
            RestUtil.getRestExecutionService(restSession).end(restOperation, restExecution, resp);
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            resp.setError(RestUtil.getRestLogService(restSession).create(restExecution, e));
        }
    }

    private static boolean validAttributes(final RestOperationDTO restOperation) {
        if (restOperation.getClassType().equalsIgnoreCase(ClassType.Java.name())) {
            return StringUtils.isNotBlank(restOperation.getJavaClass());
        } else {
            return StringUtils.isNotBlank(restOperation.getJavaScript());
        }
    }

    public static CtMessageResp execute(final RestSessionDTO restSession, final RestOperationDTO restOperation, final CtMessage input) throws JAXBException {
        if (!validAttributes(restOperation)) {
            throw new JAXBException("Classe de execução não foi parametrizada");
        }
        if (restOperation.getClassType().equalsIgnoreCase(ClassType.Java.name())) {
            return executeJavaClass(restSession, restOperation, input);
        } else {
            return executeJavaScript(restSession, restOperation, input);
        }
    }

    private static CtMessageResp executeJavaScript(final RestSessionDTO restSession, final RestOperationDTO restOperation, final CtMessage input) {
        return null;
    }

    private static CtMessageResp executeJavaClass(final RestSessionDTO restSession, final RestOperationDTO restOperation, final CtMessage input) throws JAXBException {
        try {
            final Class<?> restClass = Class.forName(restOperation.getJavaClass());
            final IRestOperation<CtMessage, CtMessageResp> peration = (IRestOperation<CtMessage, CtMessageResp>) restClass.newInstance();
            return peration.execute(restSession, restOperation, input);
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new JAXBException(e.getLocalizedMessage());
        }
    }

    public static CtMessageResp buildOperationError(final String code, final String descr, final CtMessage input) {
        CtMessageResp resp;
        try {
            resp = (CtMessageResp) Class.forName(input.getClass().getName() + "Resp").newInstance();
        } catch (final Exception e) {
            resp = new CtMessageResp();
        }
        resp.setDateTime(UtilXMLDate.toXMLGregorianCalendar(UtilDatas.getDataHoraAtual()));
        resp.setError(buildError(code, descr));
        return resp;
    }

    public static CtError buildError(final String code, final String descr) {
        final CtError error = new CtError();
        error.setCode(code);
        error.setDescription(descr);
        return error;
    }

    public static CtError buildError(final Exception e) {
        final CtError error = new CtError();
        error.setCode(RestEnum.INTERNAL_ERROR);
        error.setDescription(RestUtil.stackToString(e));
        return error;
    }

    public static CtError buildSimpleError(final Throwable e) {
        final CtError error = new CtError();
        error.setCode(RestEnum.INPUT_ERROR);
        error.setDescription(e.getMessage());
        return error;
    }

    public static Response execute(final CtMessage input) {
        final RestSessionDTO restSession = RestUtil.getRestSessionService(null).getSession(input.getSessionID());
        if (!RestUtil.isValidSession(restSession)) {
            return Response.status(Status.PRECONDITION_FAILED).entity(RestOperationUtil.buildOperationError(RestEnum.SESSION_ERROR, "Sessão não existe ou está expirada", input))
                    .build();
        }

        final RestOperationDTO restOperation = RestUtil.getRestOperationService(restSession).findByName(input.getMessageID());
        if (restOperation == null) {
            return Response.status(Status.PRECONDITION_FAILED).entity(RestOperationUtil.buildOperationError(RestEnum.INTERNAL_ERROR, "Operação não cadastrada", input)).build();
        }

        if (!RestUtil.getRestPermissionService(restSession).allowedAccess(restSession, restOperation)) {
            return Response.status(Status.PRECONDITION_FAILED).entity(RestOperationUtil.buildOperationError(RestEnum.PERMISSION_ERROR, "Usuário não tem acesso à operação", input))
                    .build();
        }

        RestExecutionDTO restExecution = null;
        try {
            restExecution = RestOperationUtil.initialize(restSession, restOperation, input);
        } catch (final Exception e) {
            return Response.status(Status.PRECONDITION_FAILED).entity(RestOperationUtil.buildOperationError(RestEnum.INTERNAL_ERROR, RestUtil.stackToString(e), input)).build();
        }

        if (restOperation.getOperationType().equalsIgnoreCase(OperationType.Sync.name())) {
            CtMessageResp resp = null;
            try {
                resp = RestOperationUtil.execute(restSession, restOperation, input);
            } catch (final Exception e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
                final CtError error = RestUtil.getRestLogService(restSession).create(restExecution, e);
                resp = new CtMessageResp();
                resp.setError(error);
            }
            RestOperationUtil.finalize(restOperation, restExecution, restSession, resp);
            if (resp.getError() == null) {
                return Response.status(Status.OK).entity(resp).build();
            }
            return Response.status(Status.PRECONDITION_FAILED).entity(resp).build();
        }

        final CtMessageResp resp = new CtMessageResp();
        resp.setDateTime(UtilXMLDate.toXMLGregorianCalendar(UtilDatas.getDataHoraAtual()));
        resp.setOperationID(BigInteger.valueOf(restExecution.getIdRestExecution()));
        return Response.status(Status.OK).entity(resp).build();
    }

}
