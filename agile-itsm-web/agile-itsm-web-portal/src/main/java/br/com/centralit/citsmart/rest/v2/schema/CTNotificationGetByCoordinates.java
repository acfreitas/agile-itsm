package br.com.centralit.citsmart.rest.v2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.centralit.citsmart.rest.schema.CtNotificationGetByUser;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"latitude", "longitude", "pager"})
public class CTNotificationGetByCoordinates extends CtNotificationGetByUser {

    @XmlElement(name = "Latitude", required = true)
    protected Double latitude;

    @XmlElement(name = "Longitude", required = true)
    protected Double longitude;

    @XmlElement(name = "Pager", required = true)
    protected CTPageRequest pager;

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

    public CTPageRequest getPager() {
        return pager;
    }

    public void setPager(final CTPageRequest pager) {
        this.pager = pager;
    }

}
