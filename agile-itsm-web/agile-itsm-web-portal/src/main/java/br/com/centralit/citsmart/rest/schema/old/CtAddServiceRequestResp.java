package br.com.centralit.citsmart.rest.schema.old;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import br.com.centralit.citsmart.rest.schema.CtError;
import br.com.centralit.citsmart.rest.schema.CtServiceRequest;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtAddServiceRequestResp", propOrder = {"dateTime", "operationID", "error", "serviceRequestDest"})
public class CtAddServiceRequestResp {

    @XmlSchemaType(name = "dateTime")
    @XmlElement(name = "DateTime", required = true)
    protected XMLGregorianCalendar dateTime;

    @XmlElement(name = "OperationID", required = true)
    protected BigInteger operationID;

    @XmlElement(name = "Error")
    protected CtError error;

    @XmlElement(name = "ServiceRequestDest")
    protected CtServiceRequest serviceRequestDest;

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

    public CtServiceRequest getServiceRequestDest() {
        return serviceRequestDest;
    }

    public void setServiceRequestDest(final CtServiceRequest serviceRequestDest) {
        this.serviceRequestDest = serviceRequestDest;
    }

}
