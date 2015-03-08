package br.com.centralit.citsmart.rest.v2.util;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.schema.CtNotificationFeedback;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetById;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetByUser;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetReasons;
import br.com.centralit.citsmart.rest.schema.CtNotificationNew;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationAttendRequest;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationAttendantLocation;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationCheckin;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationCheckinDenied;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationCheckout;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationGetByCoordinates;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationGetNewest;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationGetOldest;
import br.com.centralit.citsmart.rest.v2.schema.CTNotificationUpdate;
import br.com.centralit.citsmart.rest.v2.schema.CTServiceCoordinate;
import br.com.centralit.citsmart.rest.v2.schema.CTServiceDeviceDissassociate;
import br.com.centralit.citsmart.rest.v2.schema.CTServiceListUnits;

/**
 * Enumerado contendo as operações realizadas pelos serviços REST do Mobile
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 23/09/2014
 *
 */
public enum RESTOperations {

    ATTENDANT_LOCATION("notification_attendantLocation", "attendantLocation", RestSessionDTO.class, CTNotificationAttendantLocation.class),
    ATTEND_REQUEST("notification_attendRequest", "attendRequest", RestSessionDTO.class, CTNotificationAttendRequest.class),
    CHECK_IN("notification_checkin", "checkin", RestSessionDTO.class, CTNotificationCheckin.class),
    CHECK_IN_DENIED("notification_checkinDenied", "checkinDenied", RestSessionDTO.class, CTNotificationCheckinDenied.class),
    CHECK_OUT("notification_checkout", "checkout", RestSessionDTO.class, CTNotificationCheckout.class),
    FEEDBACK("notification_feedback", "feedback", RestSessionDTO.class, CtNotificationFeedback.class),
    GET_BY_COORDINATES("notification_getByCoordinates", "getByCoordinates", RestSessionDTO.class, CTNotificationGetByCoordinates.class),
    GET_BY_ID("notification_getById", "getById", RestSessionDTO.class, CtNotificationGetById.class),
    GET_BY_ID_V2("notification_getById_v2", "getById", RestSessionDTO.class, CtNotificationGetById.class),
    GET_REASONS("notification_getReasons", "getReasons", RestSessionDTO.class, CtNotificationGetReasons.class),
    GET_BY_USER("notification_getByUser", "getByUser", RestSessionDTO.class, CtNotificationGetByUser.class),
    GET_NEWEST("notification_getNewest", "getNewest", RestSessionDTO.class, CTNotificationGetNewest.class),
    GET_OLDEST("notification_getOldest", "getOldest", RestSessionDTO.class, CTNotificationGetOldest.class),
    NEW("notification_new", "add", RestSessionDTO.class, RestOperationDTO.class, CtNotificationNew.class),
    UPDATE_NOTIFCATION("notification_updateNotification", "updateNotification", RestSessionDTO.class, CTNotificationUpdate.class),

    COORDINATES("service_coordinates", "coordinates", RestSessionDTO.class, CTServiceCoordinate.class),
    DEVICE_DISASSOCIATE("service_deviceDisassociate", "deviceDisassociate", RestSessionDTO.class, CTServiceDeviceDissassociate.class),
    LIST_CONTRACTS("service_listContracts", "listContracts", RestSessionDTO.class),
    LIST_DENIED_REASONS("service_listDeniedReasons", "listDeniedReasons"),
    LIST_SOLICITATION_STATUS("service_listSolicitationStatus", "listSolicitationStatus"),
    LIST_UNITS("service_listUnits", "listUnits", RestSessionDTO.class, CTServiceListUnits.class);

    private final String messageID;
    private final String methodName;
    private final Class<?>[] methodArgs;

    private RESTOperations(final String messageID, final String methodName, final Class<?>... methodArgs) {
        this.messageID = messageID;
        this.methodName = methodName;
        this.methodArgs = methodArgs;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?>[] getMethodArgs() {
        return Arrays.copyOf(methodArgs, methodArgs.length);
    }

    /**
     * Recupera uma {@link RESTOperations} de acordo com seu identificador
     *
     * @param messageId
     *            identificador da mensagem
     * @return {@link RESTOperations} de acordo com o identificador da mensagem. {@link IllegalArgumentException}, caso contrário
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 23/09/2014
     */
    public static RESTOperations fromMessageId(final String messageId) {
        final RESTOperations[] values = RESTOperations.values();
        for (final RESTOperations value : values) {
            if (StringUtils.equalsIgnoreCase(messageId, value.getMessageID())) {
                return value;
            }
        }
        throw new IllegalArgumentException(String.format("RESTOperation not found for messageId '%s'", messageId));
    }

}
