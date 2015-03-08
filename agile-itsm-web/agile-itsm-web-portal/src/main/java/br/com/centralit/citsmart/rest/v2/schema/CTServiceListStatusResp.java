package br.com.centralit.citsmart.rest.v2.schema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.centralit.citsmart.rest.schema.CtMessageResp;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"status"})
public class CTServiceListStatusResp extends CtMessageResp {

    @XmlElement(name = "Status", required = true)
    private List<CTCommonContent> status;

    public List<CTCommonContent> getStatus() {
        if (status == null) {
            status = new ArrayList<>();
        }
        return status;
    }

}
