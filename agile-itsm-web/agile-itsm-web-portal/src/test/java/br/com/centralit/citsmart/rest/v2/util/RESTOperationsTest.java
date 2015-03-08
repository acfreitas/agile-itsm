package br.com.centralit.citsmart.rest.v2.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de para confirmar comportamento de métodos de {@link RESTOperations}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 25/09/2014
 *
 */
public final class RESTOperationsTest {

    private static final String OPERATION_ATTENDANT_LOCATION = "notification_attendantLocation";
    private static final String OPERATION_ATTEND_REQUEST = "notification_attendRequest";
    private static final String OPERATION_CHECK_IN = "notification_checkin";
    private static final String OPERATION_CHECK_IN_DENIED = "notification_checkinDenied";
    private static final String OPERATION_CHECK_OUT = "notification_checkout";
    private static final String OPERATION_COORDINATES = "service_coordinates";
    private static final String OPERATION_DEVICE_DISASSOCIATE = "service_deviceDisassociate";
    private static final String OPERATION_FEEDBACK = "notification_feedback";
    private static final String OPERATION_GET_BY_ID = "notification_getById";
    private static final String OPERATION_GET_BY_ID_V2 = "notification_getById_v2";
    private static final String OPERATION_GET_BY_USER = "notification_getByUser";
    private static final String OPERATION_GET_NEWEST = "notification_getNewest";
    private static final String OPERATION_GET_OLDEST = "notification_getOldest";
    private static final String OPERATION_GET_REASONS = "notification_getReasons";
    private static final String OPERATION_UPDATE_NOTIFICATION = "notification_updateNotification";
    private static final String OPERATION_LIST_CONTRACTS = "service_listContracts";
    private static final String OPERATION_LIST_DENIED_REASONS = "service_listDeniedReasons";
    private static final String OPERATION_LIST_SOLICITATION_STATUS = "service_listSolicitationStatus";
    private static final String OPERATION_LIST_UNITS = "service_listUnits";
    private static final String OPERATION_NEW = "notification_new";
    private static final String OPERATION_NOTFOUND = "method_notfound";

    @Test
    public void testOPERATION_ATTENDANT_LOCATION() {
        final RESTOperations result = RESTOperations.fromMessageId(OPERATION_ATTENDANT_LOCATION);
        Assert.assertSame(result, RESTOperations.ATTENDANT_LOCATION);
    }

    @Test
    public void testOPERATION_ATTEND_REQUEST() {
        final RESTOperations result = RESTOperations.fromMessageId(OPERATION_ATTEND_REQUEST);
        Assert.assertSame(result, RESTOperations.ATTEND_REQUEST);
    }

    @Test
    public void testOPERATION_CHECK_IN() {
        final RESTOperations result = RESTOperations.fromMessageId(OPERATION_CHECK_IN);
        Assert.assertSame(result, RESTOperations.CHECK_IN);
    }

    @Test
    public void testOPERATION_CHECK_IN_DENIED() {
        final RESTOperations result = RESTOperations.fromMessageId(OPERATION_CHECK_IN_DENIED);
        Assert.assertSame(result, RESTOperations.CHECK_IN_DENIED);
    }

    @Test
    public void testOPERATION_CHECK_OUT() {
        final RESTOperations result = RESTOperations.fromMessageId(OPERATION_CHECK_OUT);
        Assert.assertSame(result, RESTOperations.CHECK_OUT);
    }

    @Test
    public void testOPERATION_COORDINATES() {
        final RESTOperations result = RESTOperations.fromMessageId(OPERATION_COORDINATES);
        Assert.assertSame(result, RESTOperations.COORDINATES);
    }

    @Test
    public void testDEVICE_DISASSOCIATE() {
        final RESTOperations result = RESTOperations.fromMessageId(OPERATION_DEVICE_DISASSOCIATE);
        Assert.assertSame(result, RESTOperations.DEVICE_DISASSOCIATE);
    }

    @Test
    public void testOPERATION_FEEDBACK() {
        final RESTOperations result = RESTOperations.fromMessageId(OPERATION_FEEDBACK);
        Assert.assertSame(result, RESTOperations.FEEDBACK);
    }

    @Test
    public void testOPERATION_GET_BY_ID() {
        final RESTOperations result = RESTOperations.fromMessageId(OPERATION_GET_BY_ID);
        Assert.assertSame(result, RESTOperations.GET_BY_ID);
    }

    @Test
    public void testOPERATION_GET_BY_ID_V2() {
        final RESTOperations result = RESTOperations.fromMessageId(OPERATION_GET_BY_ID_V2);
        Assert.assertSame(result, RESTOperations.GET_BY_ID_V2);
    }

    @Test
    public void testOPERATION_GET_BY_USER() {
        final RESTOperations result = RESTOperations.fromMessageId(OPERATION_GET_BY_USER);
        Assert.assertSame(result, RESTOperations.GET_BY_USER);
    }

    @Test
    public void testOPERATION_GET_NEWEST() {
        final RESTOperations result = RESTOperations.fromMessageId(OPERATION_GET_NEWEST);
        Assert.assertSame(result, RESTOperations.GET_NEWEST);
    }

    @Test
    public void testOPERATION_GET_OLDEST() {
        final RESTOperations result = RESTOperations.fromMessageId(OPERATION_GET_OLDEST);
        Assert.assertSame(result, RESTOperations.GET_OLDEST);
    }

    @Test
    public void testOPERATION_GET_REASONS() {
        final RESTOperations result = RESTOperations.fromMessageId(OPERATION_GET_REASONS);
        Assert.assertSame(result, RESTOperations.GET_REASONS);
    }

    @Test
    public void testOPERATION_UPDATE_NOTIFICATION() {
        final RESTOperations result = RESTOperations.fromMessageId(OPERATION_UPDATE_NOTIFICATION);
        Assert.assertSame(result, RESTOperations.UPDATE_NOTIFCATION);
    }

    @Test
    public void testOPERATION_LIST_CONTRACTS() {
        final RESTOperations result = RESTOperations.fromMessageId(OPERATION_LIST_CONTRACTS);
        Assert.assertSame(result, RESTOperations.LIST_CONTRACTS);
    }

    @Test
    public void testOPERATION_LIST_DENIED_REASONS() {
        final RESTOperations result = RESTOperations.fromMessageId(OPERATION_LIST_DENIED_REASONS);
        Assert.assertSame(result, RESTOperations.LIST_DENIED_REASONS);
    }

    @Test
    public void testOPERATION_LIST_SOLICITATION_STATUS() {
        final RESTOperations result = RESTOperations.fromMessageId(OPERATION_LIST_SOLICITATION_STATUS);
        Assert.assertSame(result, RESTOperations.LIST_SOLICITATION_STATUS);
    }

    @Test
    public void testOPERATION_LIST_UNITS() {
        final RESTOperations result = RESTOperations.fromMessageId(OPERATION_LIST_UNITS);
        Assert.assertSame(result, RESTOperations.LIST_UNITS);
    }

    @Test
    public void testOPERATION_NEW() {
        final RESTOperations result = RESTOperations.fromMessageId(OPERATION_NEW);
        Assert.assertSame(result, RESTOperations.NEW);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOPERATION_NOTFOUND() {
        RESTOperations.fromMessageId(OPERATION_NOTFOUND);
    }

}
