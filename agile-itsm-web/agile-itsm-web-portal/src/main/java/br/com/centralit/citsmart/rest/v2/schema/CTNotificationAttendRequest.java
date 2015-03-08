package br.com.centralit.citsmart.rest.v2.schema;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = "number")
@XmlAccessorType(XmlAccessType.FIELD)
public class CTNotificationAttendRequest extends CTCommonRequest {

    @XmlElement(name = "Number", required = true)
    protected BigInteger number;

    public BigInteger getNumber() {
        return number;
    }

    public void setNumber(final BigInteger number) {
        this.number = number;
    }

}
