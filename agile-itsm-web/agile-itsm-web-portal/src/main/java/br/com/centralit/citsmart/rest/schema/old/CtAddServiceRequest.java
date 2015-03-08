package br.com.centralit.citsmart.rest.schema.old;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.centralit.citsmart.rest.schema.CtServiceRequest;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtAddServiceRequest", propOrder = {"sessionID", "serviceRequestSource"})
public class CtAddServiceRequest {

    @XmlElement(name = "SessionID", required = true)
    protected String sessionID;

    @XmlElement(name = "ServiceRequestSource", required = true)
    protected CtServiceRequest serviceRequestSource;

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(final String sessionID) {
        this.sessionID = sessionID;
    }

    public CtServiceRequest getServiceRequestSource() {
        return serviceRequestSource;
    }

    public void setServiceRequestSource(final CtServiceRequest serviceRequestSource) {
        this.serviceRequestSource = serviceRequestSource;
    }

}
