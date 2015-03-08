package br.com.centralit.citsmart.rest.v2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = "paging")
@XmlAccessorType(XmlAccessType.FIELD)
public class CTNotificationGetByCoordinatesResp extends CTNotificationCommonResp {

    @XmlElement(name = "Paging", required = true)
    private CTPageResponse paging;

    public CTPageResponse getPaging() {
        return paging;
    }

    public void setPaging(final CTPageResponse paging) {
        this.paging = paging;
    }
}
