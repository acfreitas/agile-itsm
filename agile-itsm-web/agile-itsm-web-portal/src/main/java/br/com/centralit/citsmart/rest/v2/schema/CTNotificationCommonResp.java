package br.com.centralit.citsmart.rest.v2.schema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.centralit.citsmart.rest.schema.CtMessageResp;

@XmlType(propOrder = {"notifications"})
@XmlAccessorType(XmlAccessType.FIELD)
public class CTNotificationCommonResp extends CtMessageResp {

    @XmlElement(name = "Notification", required = true)
    protected List<CTNotification> notifications;

    public List<CTNotification> getNotifications() {
        if (notifications == null) {
            notifications = new ArrayList<>();
        }
        return notifications;
    }

}
