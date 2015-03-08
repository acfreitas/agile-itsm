package br.com.centralit.citsmart.rest.v2.schema;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import br.com.centralit.citsmart.rest.schema.CtMessage;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"taskId", "latitude", "longitude", "startTime"})
public class CTNotificationCheckin extends CtMessage {

    @XmlElement(name = "TaskId", required = true)
    protected BigInteger taskId;

    @XmlElement(name = "Latitude", required = true)
    protected Double latitude;

    @XmlElement(name = "Longitude", required = true)
    protected Double longitude;

    @XmlElement(name = "StartTime", required = true)
    protected XMLGregorianCalendar startTime;

    public BigInteger getTaskId() {
        return taskId;
    }

    public XMLGregorianCalendar getStartTime() {
        return startTime;
    }

    public void setStartTime(final XMLGregorianCalendar startTime) {
        this.startTime = startTime;
    }

    public void setTaskId(final BigInteger taskId) {
        this.taskId = taskId;
    }

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

}
