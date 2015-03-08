package br.com.centralit.citsmart.rest.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtNotificationDetail", propOrder = {"description", "status", "taskStatus"})
public class CtNotificationDetail extends CtNotification {

    @XmlElement(name = "Description", defaultValue = " ", required = true)
    protected String description;

    @XmlElement(name = "Status", defaultValue = " ", required = true)
    protected String status;

    @XmlElement(name = "TaskStatus", defaultValue = " ", required = true)
    protected String taskStatus;

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(final String taskStatus) {
        this.taskStatus = taskStatus;
    }

}
