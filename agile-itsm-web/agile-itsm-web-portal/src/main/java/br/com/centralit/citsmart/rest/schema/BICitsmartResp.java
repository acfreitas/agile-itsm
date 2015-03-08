package br.com.centralit.citsmart.rest.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BICitsmartResp", propOrder = "xml")
public class BICitsmartResp extends CtMessageResp {

    @XmlElement(name = "Xml", required = true)
    protected String xml;

    public String getXml() {
        return xml;
    }

    public void setXml(final String xml) {
        this.xml = xml;
    }

}
