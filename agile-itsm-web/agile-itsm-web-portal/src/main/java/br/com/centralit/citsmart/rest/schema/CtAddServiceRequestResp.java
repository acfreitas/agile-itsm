package br.com.centralit.citsmart.rest.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtAddServiceRequestResp", propOrder = "serviceRequestDest")
public class CtAddServiceRequestResp extends CtMessageResp {

    @XmlElement(name = "ServiceRequestDest", required = true)
    protected CtServiceRequest serviceRequestDest;

    public CtServiceRequest getServiceRequestDest() {
        return serviceRequestDest;
    }

    public void setServiceRequestDest(final CtServiceRequest serviceRequestDest) {
        this.serviceRequestDest = serviceRequestDest;
    }

}
