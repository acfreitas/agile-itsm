package br.com.centralit.citsmart.rest.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.com.centralit.citsmart.rest.schema.CtNotificationFeedback;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetById;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetByUser;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetReasons;
import br.com.centralit.citsmart.rest.schema.CtNotificationNew;
import br.com.centralit.citsmart.rest.util.RestOperationUtil;

@Path("/mobile/notification")
public class RestMobileResources {

    protected static final String GETBYUSER = "/getByUser";
    protected static final String GETBYID = "/getById";

    @POST
    @Path(GETBYUSER)
    public Response getNotificationByUser(final CtNotificationGetByUser input) {
        input.setMessageID("notification_getByUser");
        return RestOperationUtil.execute(input);
    }

    @POST
    @Path(GETBYID)
    public Response getNotificationById(final CtNotificationGetById input) {
        input.setMessageID("notification_getById");
        return RestOperationUtil.execute(input);
    }

    @POST
    @Path("/feedback")
    public Response feedback(final CtNotificationFeedback input) {
        input.setMessageID("notification_feedback");
        return RestOperationUtil.execute(input);
    }

    @POST
    @Path("/new")
    public Response newNotification(final CtNotificationNew input) {
        input.setMessageID("notification_new");
        return RestOperationUtil.execute(input);
    }

    @POST
    @Path("/getReasons")
    public Response getReasons(final CtNotificationGetReasons input) {
        input.setMessageID("notification_getReasons");
        return RestOperationUtil.execute(input);
    }

}
