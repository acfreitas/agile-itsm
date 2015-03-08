package br.com.centralit.citsmart.rest.v2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import br.com.centralit.citsmart.rest.schema.CtMessage;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"latitude", "longitude", "dateTime"})
public class CTCommonRequest extends CtMessage {

    @XmlElement(name = "Latitude", required = true)
    protected Double latitude;

    @XmlElement(name = "Longitude", required = true)
    protected Double longitude;

    @XmlSchemaType(name = "dateTime")
    @XmlElement(name = "DateTime", required = true)
    protected XMLGregorianCalendar dateTime;

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

    public void setDateTime(final XMLGregorianCalendar value) {
        dateTime = value;
    }

}
