package br.com.centralit.citsmart.rest.v2.schema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = "justifications")
@XmlAccessorType(XmlAccessType.FIELD)
public class CTServiceListStatusSuspensionReasons extends CTCommonContent {

    @XmlElement(name = "Justification", required = true)
    private List<CTCommonContent> justifications;

    public List<CTCommonContent> getJustifications() {
        if (justifications == null) {
            justifications = new ArrayList<>();
        }
        return justifications;
    }

}
