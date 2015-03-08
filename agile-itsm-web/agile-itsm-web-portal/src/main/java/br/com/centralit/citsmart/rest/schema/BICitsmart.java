package br.com.centralit.citsmart.rest.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BICitsmart", propOrder = "biCitsmartType")
public class BICitsmart extends CtMessage {

    @XmlElement(name = "BICitsmartType", required = true)
    protected String biCitsmartType;

    public String getBICitsmartType() {
        return biCitsmartType;
    }

    public void setBICitsmartType(final String biCitsmartType) {
        this.biCitsmartType = biCitsmartType;
    }

}
