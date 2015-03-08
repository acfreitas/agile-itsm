package br.com.centralit.citsmart.rest.schema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtNotificationGetByUserResp", propOrder = {"amount", "notifications"})
public class CtNotificationGetByUserResp extends CtMessageResp {

    @XmlElement(name = "Amount")
    protected int amount;

    @XmlElement(name = "Notification", required = true)
    protected List<CtNotification> notifications;

    public int getAmount() {
        return amount = this.getNotifications().size();
    }

    public void setAmount(final int amount) {
        this.amount = amount;
    }

    public List<CtNotification> getNotifications() {
        if (notifications == null) {
            notifications = new ArrayList<>();
        }
        return notifications;
    }

}
