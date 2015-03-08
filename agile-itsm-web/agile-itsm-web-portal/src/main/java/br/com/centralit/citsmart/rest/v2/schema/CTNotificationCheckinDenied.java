package br.com.centralit.citsmart.rest.v2.schema;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.datatype.XMLGregorianCalendar;

import br.com.centralit.citsmart.rest.schema.CtMessage;

@XmlAccessorType(XmlAccessType.FIELD)
public class CTNotificationCheckinDenied extends CtMessage {

    @XmlElement(name = "TaskId", required = true)
    protected BigInteger taskId;

    @XmlElement(name = "Latitude", required = true)
    protected Double latitude;

    @XmlElement(name = "Longitude", required = true)
    protected Double longitude;

    @XmlElement(name = "DateTime", required = true)
    protected XMLGregorianCalendar dateTime;

    @XmlElement(name = "ReasonId", required = true)
    protected Integer reasonId;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }

    public XMLGregorianCalendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(final XMLGregorianCalendar dateTime) {
        this.dateTime = dateTime;
    }

    public BigInteger getTaskId() {
        return taskId;
    }

    public void setTaskId(final BigInteger taskId) {
        this.taskId = taskId;
    }

    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(final Integer reasonId) {
        this.reasonId = reasonId;
    }

}
