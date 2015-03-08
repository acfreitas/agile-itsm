package br.com.centralit.citsmart.rest.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtNotificationGetByUser", propOrder = {"notificationType", "onlyApproval"})
public class CtNotificationGetByUser extends CtMessage {

    @XmlElement(name = "NotificationType")
    protected int notificationType;

    @XmlElement(name = "OnlyApproval")
    protected int onlyApproval;

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(final int notificationType) {
        this.notificationType = notificationType;
    }

    public int getOnlyApproval() {
        return onlyApproval;
    }

    public void setOnlyApproval(final int onlyApproval) {
        this.onlyApproval = onlyApproval;
    }

}
