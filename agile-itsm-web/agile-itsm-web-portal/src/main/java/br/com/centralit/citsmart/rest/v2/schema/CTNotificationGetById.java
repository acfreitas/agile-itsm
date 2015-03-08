package br.com.centralit.citsmart.rest.v2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"endSLA", "task", "service", "description", "status", "taskStatus", "timeSLA"})
public class CTNotificationGetById {

    @XmlElement(name = "EndSLA", defaultValue = " ", required = true)
    private String endSLA;

    @XmlElement(name = "Task", defaultValue = " ", required = true)
    private String task;

    @XmlElement(name = "Service", defaultValue = " ", required = true)
    private String service;

    @XmlElement(name = "Description", defaultValue = " ", required = true)
    private String description;

    @XmlElement(name = "Status", defaultValue = " ", required = true)
    private String status;

    @XmlElement(name = "TaskStatus", defaultValue = " ", required = true)
    private String taskStatus;

    @XmlElement(name = "TimeSLA", required = true)
    private Integer timeSLA;

    public String getEndSLA() {
        return endSLA;
    }

    public void setEndSLA(final String endSLA) {
        this.endSLA = endSLA;
    }

    public String getTask() {
        return task;
    }

    public void setTask(final String task) {
        this.task = task;
    }

    public String getService() {
        return service;
    }

    public void setService(final String service) {
        this.service = service;
    }

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

    public Integer getTimeSLA() {
        return timeSLA;
    }

    public void setTimeSLA(final Integer timeSLA) {
        this.timeSLA = timeSLA;
    }

}
