package br.com.centralit.citsmart.rest.v2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.centralit.citsmart.rest.schema.CtNotificationFeedback;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"latitude", "longitude", "descSolution", "status", "solution"})
public class CTNotificationCheckout extends CtNotificationFeedback {

    @XmlElement(name = "Latitude", required = true)
    protected Double latitude;

    @XmlElement(name = "Longitude", required = true)
    protected Double longitude;

    @XmlElement(name = "DescSolution", required = true)
    private String descSolution;

    @XmlElement(name = "Status", required = true)
    private Integer status;

    @XmlElement(name = "Solution", required = true)
    private Integer solution;

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

    public String getDescSolution() {
        return descSolution;
    }

    public void setDescSolution(final String descSolution) {
        this.descSolution = descSolution;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(final Integer status) {
        this.status = status;
    }

    public Integer getSolution() {
        return solution;
    }

    public void setSolution(final Integer solution) {
        this.solution = solution;
    }

}
