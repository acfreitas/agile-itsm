package br.com.centralit.citsmart.rest.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtContact", propOrder = {"name", "department", "email", "phoneNumber"})
public class CtContact {

    @XmlElement(name = "Name", required = true, nillable = true)
    protected String name;

    @XmlElement(name = "Department", required = true, nillable = true)
    protected String department;

    @XmlElement(name = "Email", required = true, nillable = true)
    protected String email;

    @XmlElement(name = "PhoneNumber", required = true, nillable = true)
    protected String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(final String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
