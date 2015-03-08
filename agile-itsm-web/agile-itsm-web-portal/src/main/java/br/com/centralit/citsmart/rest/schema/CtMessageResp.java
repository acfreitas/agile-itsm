package br.com.centralit.citsmart.rest.schema;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({BICitsmartResp.class, CtAddServiceRequestResp.class})
@XmlType(name = "CtMessageResp", propOrder = {"dateTime", "operationID", "error"})
public class CtMessageResp {

    @XmlElement(name = "DateTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateTime;

    @XmlElement(name = "OperationID", defaultValue = "123")
    protected BigInteger operationID;

    @XmlElement(name = "Error")
    protected CtError error;

    public XMLGregorianCalendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(final XMLGregorianCalendar dateTime) {
        this.dateTime = dateTime;
    }

    public BigInteger getOperationID() {
        return operationID;
    }

    public void setOperationID(final BigInteger operationID) {
        this.operationID = operationID;
    }

    public CtError getError() {
        return error;
    }

    public void setError(final CtError error) {
        this.error = error;
    }

}
