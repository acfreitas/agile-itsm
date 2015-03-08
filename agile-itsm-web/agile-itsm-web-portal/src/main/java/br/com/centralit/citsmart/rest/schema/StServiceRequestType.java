package br.com.centralit.citsmart.rest.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlEnum
@XmlType(name = "StServiceRequestType")
public enum StServiceRequestType {

    R,
    I;

    public String value() {
        return this.name();
    }

    public static StServiceRequestType fromValue(final String v) {
        return valueOf(v);
    }

}
