package br.com.centralit.citsmart.rest.v2.schema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.centralit.citsmart.rest.schema.CtMessageResp;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"units"})
public class CTServiceListUnitsResp extends CtMessageResp {

    @XmlElement(name = "Units", required = true)
    private List<CTCommonContent> units;

    public List<CTCommonContent> getUnits() {
        if (units == null) {
            units = new ArrayList<>();
        }
        return units;
    }

}
