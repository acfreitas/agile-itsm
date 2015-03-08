package br.com.centralit.citsmart.rest.v2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"page", "size"})
public class CTPageRequest {

    @XmlElement(name = "Page", required = true, defaultValue = "1")
    private Integer page;

    @XmlElement(name = "Size", required = true, defaultValue = "10")
    private Integer size;

    public Integer getPage() {
        return page;
    }

    public void setPage(final Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(final Integer size) {
        this.size = size;
    }

}
