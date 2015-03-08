package br.com.centralit.citsmart.rest.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({BICitsmart.class, CtAddServiceRequest.class})
@XmlType(name = "CtMessage", propOrder = {"sessionID", "messageID"})
public class CtMessage {

    @XmlElement(name = "SessionID", required = true)
    protected String sessionID;

    @XmlElement(name = "MessageID", nillable = true)
    protected String messageID;

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(final String sessionID) {
        this.sessionID = sessionID;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(final String messageID) {
        this.messageID = messageID;
    }

}
