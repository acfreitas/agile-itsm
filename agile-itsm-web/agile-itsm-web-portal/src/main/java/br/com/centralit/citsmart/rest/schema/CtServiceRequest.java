package br.com.centralit.citsmart.rest.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtServiceRequest", propOrder = {"number", "type", "description", "startDateTime", "startSLA", "endSLA", "urgency", "impact", "groupId", "userID", "contact", "service"})
public class CtServiceRequest {

    @XmlElement(name = "Number", required = true)
    protected String number;

    @XmlElement(name = "Type", required = true)
    protected StServiceRequestType type;

    @XmlElement(name = "Description", required = true)
    protected String description;

    @XmlSchemaType(name = "dateTime")
    @XmlElement(name = "StartDateTime", required = true)
    protected XMLGregorianCalendar startDateTime;

    @XmlSchemaType(name = "dateTime")
    @XmlElement(name = "StartSLA", required = true, nillable = true)
    protected XMLGregorianCalendar startSLA;

    @XmlSchemaType(name = "dateTime")
    @XmlElement(name = "EndSLA", required = true, nillable = true)
    protected XMLGregorianCalendar endSLA;

    @XmlElement(name = "Urgency", required = true, nillable = true)
    protected StServiceRequestPriority urgency;

    @XmlElement(name = "Impact", required = true, nillable = true)
    protected StServiceRequestPriority impact;

    @XmlElement(name = "GroupId", required = true, nillable = true)
    protected String groupId;

    @XmlElement(name = "UserID", required = true, nillable = true)
    protected String userID;

    @XmlElement(name = "Contact")
    protected CtContact contact;

    @XmlElement(name = "Service", required = true)
    protected CtService service;

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public StServiceRequestType getType() {
        return type;
    }

    public void setType(final StServiceRequestType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public XMLGregorianCalendar getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(final XMLGregorianCalendar startDateTime) {
        this.startDateTime = startDateTime;
    }

    public XMLGregorianCalendar getStartSLA() {
        return startSLA;
    }

    public void setStartSLA(final XMLGregorianCalendar startSLA) {
        this.startSLA = startSLA;
    }

    public XMLGregorianCalendar getEndSLA() {
        return endSLA;
    }

    public void setEndSLA(final XMLGregorianCalendar endSLA) {
        this.endSLA = endSLA;
    }

    public StServiceRequestPriority getUrgency() {
        return urgency;
    }

    public void setUrgency(final StServiceRequestPriority urgency) {
        this.urgency = urgency;
    }

    public StServiceRequestPriority getImpact() {
        return impact;
    }

    public void setImpact(final StServiceRequestPriority impact) {
        this.impact = impact;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(final String groupId) {
        this.groupId = groupId;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(final String userID) {
        this.userID = userID;
    }

    public CtContact getContact() {
        return contact;
    }

    public void setContact(final CtContact contact) {
        this.contact = contact;
    }

    public CtService getService() {
        return service;
    }

    public void setService(final CtService service) {
        this.service = service;
    }

}
