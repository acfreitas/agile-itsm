package br.com.centralit.citsmart.rest.v2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.centralit.citsmart.rest.schema.CtLoginResp;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"rangeAction", "locationInterval"})
public class CTLoginResp extends CtLoginResp {

    @XmlElement(name = "RangeAction")
    protected Integer rangeAction;

    @XmlElement(name = "LocationInterval")
    protected Integer locationInterval;

    public Integer getRangeAction() {
        return rangeAction;
    }

    public void setRangeAction(final Integer rangeAction) {
        this.rangeAction = rangeAction;
    }

    public Integer getLocationInterval() {
        return locationInterval;
    }

    public void setLocationInterval(final Integer locationInterval) {
        this.locationInterval = locationInterval;
    }

}
