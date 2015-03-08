package br.com.centralit.citsmart.rest.v2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import br.com.centralit.citsmart.rest.schema.CtMessageResp;

@XmlAccessorType(XmlAccessType.FIELD)
public class CTNotificationCheckoutResp extends CtMessageResp {

    @XmlElement(name = "Number", required = true)
    private Integer number;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(final Integer number) {
        this.number = number;
    }

}
