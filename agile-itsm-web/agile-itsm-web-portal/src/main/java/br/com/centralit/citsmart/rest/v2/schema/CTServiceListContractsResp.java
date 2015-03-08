package br.com.centralit.citsmart.rest.v2.schema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.centralit.citsmart.rest.schema.CtMessageResp;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"contracts"})
public class CTServiceListContractsResp extends CtMessageResp {

    @XmlElement(name = "Contract", required = true)
    private List<CTCommonContent> contracts;

    public List<CTCommonContent> getContracts() {
        if (contracts == null) {
            contracts = new ArrayList<>();
        }
        return contracts;
    }

}
