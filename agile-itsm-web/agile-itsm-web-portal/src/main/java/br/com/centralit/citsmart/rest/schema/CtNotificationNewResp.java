package br.com.centralit.citsmart.rest.schema;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtNotificationNewResp", propOrder = "number")
public class CtNotificationNewResp extends CtMessageResp {

    @XmlElement(name = "Number", defaultValue = "123", required = true)
    protected BigInteger number;

    public BigInteger getNumber() {
        return number;
    }

    public void setNumber(final BigInteger number) {
        this.number = number;
    }

}
