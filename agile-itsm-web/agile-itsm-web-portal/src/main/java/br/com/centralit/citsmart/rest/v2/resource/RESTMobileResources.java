package br.com.centralit.citsmart.rest.v2.resource;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import br.com.centralit.citsmart.rest.resource.RestMobileResources;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetById;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetByUser;
import br.com.centralit.citsmart.rest.util.RestOperationUtil;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationAttendRequest;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationAttendantLocation;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationCheckin;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationCheckinDenied;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationCheckout;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationGetByCoordinates;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationGetNewest;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationGetOldest;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationUpdate;
import br.com.centralit.citsmart.rest.v2.util.RESTOperations;
import br.com.citframework.util.UtilI18N;

/**
 * Endpoints REST da versão V2 dos serviços oferecidos ao Mobile
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 22/09/2014
 *
 */
@Path("/mobile/v2/notification")
public class RESTMobileResources extends RestMobileResources {

    private static final Logger LOGGER = Logger.getLogger(RESTMobileResources.class.getName());

    private final String CALL_MESSAGE = "Service handling at '%s' called with parameters '%s'";

    private static final String ATTENDREQUEST = "/attendRequest";
    private static final String ATTENDANTLOCATION = "/location";
    private static final String CHECKIN = "/checkin";
    private static final String CHECKINDENIED = CHECKIN + "/denied";
    private static final String CHECKOUT = "/checkout";
    private static final String GETBYCOORDINATES = "/getByCoordinates";
    private static final String GETNEWEST = "/getNewest";
    private static final String GETOLDEST = "/getOldest";
    private static final String UPDATENOTIFICATION = "/update";

    @Context
    private HttpServletRequest request;

    @POST
    @Path(ATTENDANTLOCATION)
    public Response attendantLocation(final CTNotificationAttendantLocation input) {
        LOGGER.log(Level.FINE, String.format(CALL_MESSAGE, ATTENDANTLOCATION, ReflectionToStringBuilder.toString(input)));
        input.setMessageID(RESTOperations.ATTENDANT_LOCATION.getMessageID());
        return RestOperationUtil.execute(input);
    }

    @POST
    @Path(ATTENDREQUEST)
    public Response attendRequest(final CTNotificationAttendRequest input) {
        LOGGER.log(Level.FINE, String.format(CALL_MESSAGE, ATTENDREQUEST, ReflectionToStringBuilder.toString(input)));
        input.setMessageID(RESTOperations.ATTEND_REQUEST.getMessageID());
        return RestOperationUtil.execute(input);
    }

    @POST
    @Path(CHECKIN)
    public Response checkin(final CTNotificationCheckin input) {
        LOGGER.log(Level.FINE, String.format(CALL_MESSAGE, CHECKIN, ReflectionToStringBuilder.toString(input)));
        input.setMessageID(RESTOperations.CHECK_IN.getMessageID());
        return RestOperationUtil.execute(input);
    }

    @POST
    @Path(CHECKINDENIED)
    public Response checkinDenied(final CTNotificationCheckinDenied input) {
        LOGGER.log(Level.FINE, String.format(CALL_MESSAGE, CHECKINDENIED, ReflectionToStringBuilder.toString(input)));
        input.setMessageID(RESTOperations.CHECK_IN_DENIED.getMessageID());
        return RestOperationUtil.execute(input);
    }

    @POST
    @Path(CHECKOUT)
    public Response checkout(final CTNotificationCheckout input) {
        LOGGER.log(Level.FINE, String.format(CALL_MESSAGE, CHECKOUT, ReflectionToStringBuilder.toString(input)));
        input.setMessageID(RESTOperations.CHECK_OUT.getMessageID());
        return RestOperationUtil.execute(input);
    }

    @POST
    @Path(GETBYCOORDINATES)
    public Response getByCoordinates(final CTNotificationGetByCoordinates input) {
        LOGGER.log(Level.FINE, String.format(CALL_MESSAGE, GETBYCOORDINATES, ReflectionToStringBuilder.toString(input)));
        input.setMessageID(RESTOperations.GET_BY_COORDINATES.getMessageID());
        return RestOperationUtil.execute(input);
    }

    @Override
    @POST
    @Path(GETBYID)
    public Response getNotificationById(final CtNotificationGetById input) {
        LOGGER.log(Level.FINE, String.format(CALL_MESSAGE, GETBYID, ReflectionToStringBuilder.toString(input)));
        input.setMessageID(RESTOperations.GET_BY_ID_V2.getMessageID());
        return RestOperationUtil.execute(input);
    }

    @POST
    @Override
    @Path(GETBYUSER)
    public Response getNotificationByUser(final CtNotificationGetByUser input) {
        LOGGER.log(Level.FINE, String.format(CALL_MESSAGE, GETBYUSER, ReflectionToStringBuilder.toString(input)));
        final String message = UtilI18N.internacionaliza(request, "rest.service.mobile.v2.getByUser");
        return Response.status(Status.OK).entity(RestOperationUtil.buildError("501", message)).build();
    }

    @POST
    @Path(GETNEWEST)
    public Response getNewest(final CTNotificationGetNewest input) {
        LOGGER.log(Level.FINE, String.format(CALL_MESSAGE, GETNEWEST, ReflectionToStringBuilder.toString(input)));
        input.setMessageID(RESTOperations.GET_NEWEST.getMessageID());
        return RestOperationUtil.execute(input);
    }

    @POST
    @Path(GETOLDEST)
    public Response getOldest(final CTNotificationGetOldest input) {
        LOGGER.log(Level.FINE, String.format(CALL_MESSAGE, GETOLDEST, ReflectionToStringBuilder.toString(input)));
        input.setMessageID(RESTOperations.GET_OLDEST.getMessageID());
        return RestOperationUtil.execute(input);
    }

    @POST
    @Path(UPDATENOTIFICATION)
    public Response updateNotification(final CTNotificationUpdate input) {
        LOGGER.log(Level.FINE, String.format(CALL_MESSAGE, UPDATENOTIFICATION, ReflectionToStringBuilder.toString(input)));
        input.setMessageID(RESTOperations.UPDATE_NOTIFCATION.getMessageID());
        return RestOperationUtil.execute(input);
    }

}
