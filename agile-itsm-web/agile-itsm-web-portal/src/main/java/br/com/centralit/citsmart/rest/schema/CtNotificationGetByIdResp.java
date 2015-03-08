package br.com.centralit.citsmart.rest.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtNotificationGetByIdResp", propOrder = {"site", "notification"})
public class CtNotificationGetByIdResp extends CtMessageResp {

    @XmlElement(name = "Site")
    protected String site;

    @XmlElement(name = "Notification", required = true)
    protected CtNotificationDetail notification;

    public String getSite() {
        return site;
    }

    public void setSite(final String site) {
        this.site = site;
    }

    public CtNotificationDetail getNotification() {
        return notification;
    }

    public void setNotification(final CtNotificationDetail notification) {
        this.notification = notification;
    }

}
