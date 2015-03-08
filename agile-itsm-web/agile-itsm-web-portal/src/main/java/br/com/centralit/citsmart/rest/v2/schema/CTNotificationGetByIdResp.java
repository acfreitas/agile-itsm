package br.com.centralit.citsmart.rest.v2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.centralit.citsmart.rest.schema.CtMessageResp;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = "notification")
public class CTNotificationGetByIdResp extends CtMessageResp {

    @XmlElement(name = "Notification", required = true)
    protected CTNotificationGetById notification;

    public CTNotificationGetById getNotification() {
        return notification;
    }

    public void setNotification(final CTNotificationGetById notification) {
        this.notification = notification;
    }

}
