package br.com.centralit.citsmart.rest.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlEnum
@XmlType(name = "StServiceRequestPriority")
public enum StServiceRequestPriority {

    H,
    M,
    L;

    public String value() {
        return this.name();
    }

    public static StServiceRequestPriority fromValue(final String v) {
        return valueOf(v);
    }

}
