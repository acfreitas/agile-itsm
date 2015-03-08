package br.com.centralit.citsmart.rest.v2.schema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.centralit.citsmart.rest.schema.CtMessageResp;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = "reasons")
public class CTServiceListDeniedReasonsResp extends CtMessageResp {

    @XmlElement(name = "Reasons", required = true)
    private List<CTCommonContent> reasons;

    public List<CTCommonContent> getReasons() {
        if (reasons == null) {
            reasons = new ArrayList<>();
        }
        return reasons;
    }

}
