package br.com.centralit.citsmart.rest.v2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.centralit.citsmart.rest.schema.CtMessage;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"contractID"})
public class CTServiceListUnits extends CtMessage {

    @XmlElement(name = "ContractID", required = true)
    private Integer contractID;

    public Integer getContractID() {
        return contractID;
    }

    public void setContractID(final Integer contractID) {
        this.contractID = contractID;
    }

}
