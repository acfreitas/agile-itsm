package br.com.centralit.citsmart.rest.v2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.centralit.citsmart.rest.schema.CtNotification;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"latitude", "longitude", "timeSLA", "personal", "inService", "inChekin", "typeRequest", "contract", "unit", "priorityorder"})
public class CTNotification extends CtNotification {

    @XmlElement(name = "Latitude", required = true)
    protected Double latitude;

    @XmlElement(name = "Longitude", required = true)
    protected Double longitude;

    @XmlElement(name = "TimeSLA", required = true)
    private Integer timeSLA;

    @XmlElement(name = "Personal", required = true, defaultValue = "false")
    private boolean personal;

    @XmlElement(name = "InService", required = true, defaultValue = "false")
    private boolean inService;

    @XmlElement(name = "Unit", required = true)
    private Integer unit;

    @XmlElement(name = "Contract", required = true)
    private Integer contract;

    @XmlElement(name = "TypeRequest", defaultValue = "0", required = true)
    private Integer typeRequest;

    @XmlElement(name = "PriorityOrder", required = true)
    private Integer priorityorder;

    @XmlElement(name = "InChekin", defaultValue = "0", required = true)
    private Integer inChekin;

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

    public Integer getTimeSLA() {
        return timeSLA;
    }

    public void setTimeSLA(final Integer timeSLA) {
        this.timeSLA = timeSLA;
    }

    public boolean isPersonal() {
        return personal;
    }

    public void setPersonal(final boolean personal) {
        this.personal = personal;
    }

    public boolean isInService() {
        return inService;
    }

    public void setInService(final boolean inService) {
        this.inService = inService;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(final Integer unidade) {
        unit = unidade;
    }

    public Integer getContract() {
        return contract;
    }

    public void setContract(final Integer contrato) {
        contract = contrato;
    }

    public Integer getTypeRequest() {
        return typeRequest;
    }

    public void setTypeRequest(final Integer typeRequest) {
        this.typeRequest = typeRequest;
    }

    public Integer getPriorityorder() {
        return priorityorder;
    }

    public void setPriorityorder(final Integer priorityorder) {
        this.priorityorder = priorityorder;
    }

    public Integer getInChekin() {
        return inChekin;
    }

    public void setInChekin(final Integer inChekin) {
        this.inChekin = inChekin;
    }

}
