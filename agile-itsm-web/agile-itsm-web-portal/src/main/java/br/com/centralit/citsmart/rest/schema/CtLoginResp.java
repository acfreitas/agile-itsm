package br.com.centralit.citsmart.rest.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtLoginResp", propOrder = {"error", "sessionID"})
public class CtLoginResp {

    @XmlElement(name = "Error", nillable = true)
    protected CtError error;

    @XmlElement(name = "SessionID", defaultValue = " ")
    protected String sessionID;

    public CtError getError() {
        return error;
    }

    public void setError(final CtError error) {
        this.error = error;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(final String sessionID) {
        this.sessionID = sessionID;
    }

}
