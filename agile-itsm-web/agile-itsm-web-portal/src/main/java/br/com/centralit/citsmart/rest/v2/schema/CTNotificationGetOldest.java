package br.com.centralit.citsmart.rest.v2.schema;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.centralit.citsmart.rest.schema.CtNotificationGetByUser;

@XmlType(propOrder = "oldestNumber")
@XmlAccessorType(XmlAccessType.FIELD)
public class CTNotificationGetOldest extends CtNotificationGetByUser {

    @XmlElement(name = "OldestNumber", required = true)
    protected BigInteger oldestNumber;

    public BigInteger getOldestNumber() {
        return oldestNumber;
    }

    public void setOldestNumber(final BigInteger oldestNumber) {
        this.oldestNumber = oldestNumber;
    }

}
