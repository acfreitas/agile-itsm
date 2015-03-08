package br.com.centralit.citsmart.rest.schema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtNotificationGetReasonsResp", propOrder = {"amount", "reasons"})
public class CtNotificationGetReasonsResp extends CtMessageResp {

    @XmlElement(name = "Amount", defaultValue = "123")
    protected int amount;

    @XmlElement(name = "Reason", required = true)
    protected List<CtReason> reasons;

    public int getAmount() {
        return amount = this.getReasons().size();
    }

    public void setAmount(final int amount) {
        this.amount = amount;
    }

    public List<CtReason> getReasons() {
        if (reasons == null) {
            reasons = new ArrayList<>();
        }
        return reasons;
    }

}
