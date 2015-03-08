package br.com.centralit.citsmart.rest.v2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"page", "elements", "totalPages", "totalElements"})
public class CTPageResponse {

    @XmlElement(name = "Page", required = true)
    private Integer page;

    @XmlElement(name = "Elements", required = true)
    private Integer elements;

    @XmlElement(name = "TotalPages", required = true)
    private Integer totalPages;

    @XmlElement(name = "TotalElements", required = true)
    private Integer totalElements;

    public Integer getPage() {
        return page;
    }

    public void setPage(final Integer page) {
        this.page = page;
    }

    public Integer getElements() {
        return elements;
    }

    public void setElements(final Integer elements) {
        this.elements = elements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(final Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(final Integer totalElements) {
        this.totalElements = totalElements;
    }

}
