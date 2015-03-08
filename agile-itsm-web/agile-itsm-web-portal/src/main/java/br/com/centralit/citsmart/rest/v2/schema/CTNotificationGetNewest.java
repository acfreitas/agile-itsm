package br.com.centralit.citsmart.rest.v2.schema;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.centralit.citsmart.rest.schema.CtNotificationGetByUser;

@XmlType(propOrder = "newestNumber")
@XmlAccessorType(XmlAccessType.FIELD)
public class CTNotificationGetNewest extends CtNotificationGetByUser {

    @XmlElement(name = "NewestNumber", required = true)
    protected BigInteger newestNumber;

    public BigInteger getNewestNumber() {
        return newestNumber;
    }

    public void setNewestNumber(final BigInteger newestNumber) {
        this.newestNumber = newestNumber;
    }

}
