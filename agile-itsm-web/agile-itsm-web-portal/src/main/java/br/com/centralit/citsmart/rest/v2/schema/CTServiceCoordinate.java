package br.com.centralit.citsmart.rest.v2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.centralit.citsmart.rest.schema.CtMessage;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"unitID", "latitude", "longitude"})
public class CTServiceCoordinate extends CtMessage {

    @XmlElement(name = "UnitID", required = true)
    private Integer unitID;

    @XmlElement(name = "Latitude", required = true)
    private Double latitude;

    @XmlElement(name = "Longitude", required = true)
    private Double longitude;

    public Integer getUnitID() {
        return unitID;
    }

    public void setUnitID(final Integer unitID) {
        this.unitID = unitID;
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
