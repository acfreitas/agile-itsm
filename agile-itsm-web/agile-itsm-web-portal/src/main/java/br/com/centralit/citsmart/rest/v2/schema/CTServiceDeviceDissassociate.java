package br.com.centralit.citsmart.rest.v2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.centralit.citsmart.rest.schema.CtMessage;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"connection", "token"})
public class CTServiceDeviceDissassociate extends CtMessage {

    @XmlElement(name = "Connection", required = true)
    private String connection;

    @XmlElement(name = "Token", required = true)
    private String token;

    public String getConnection() {
        return connection;
    }

    public void setConnection(final String connection) {
        this.connection = connection;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

}
