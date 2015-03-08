package br.com.centralit.citsmart.rest.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtAddServiceRequest", propOrder = {"serviceRequestSource"})
public class CtAddServiceRequest extends CtMessage {

    @XmlElement(name = "ServiceRequestSource", required = true)
    protected CtServiceRequest serviceRequestSource;

    public CtServiceRequest getServiceRequestSource() {
        return serviceRequestSource;
    }

    public void setServiceRequestSource(final CtServiceRequest serviceRequestSource) {
        this.serviceRequestSource = serviceRequestSource;
    }

    public CtAddServiceRequest() {
        this.setMessageID("addServiceRequest");
    }

}
