package br.com.centralit.citsmart.rest.schema;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({CtNotificationDetail.class})
@XmlType(name = "CtNotification", propOrder = {"number", "taskId", "type", "date", "endSLA", "timeFlag", "task", "service", "waiting"})
public class CtNotification {

    @XmlElement(name = "Number", defaultValue = "123", required = true)
    protected BigInteger number;

    @XmlElement(name = "TaskId", required = true)
    protected BigInteger taskId;

    @XmlElement(name = "Type")
    protected int type;

    @XmlElement(name = "Date", defaultValue = " ", required = true)
    protected String date;

    @XmlElement(name = "EndSLA", defaultValue = " ", required = true)
    protected String endSLA;

    @XmlElement(name = "TimeFlag", defaultValue = "123")
    protected Integer timeFlag;

    @XmlElement(name = "Task", defaultValue = " ", required = true)
    protected String task;

    @XmlElement(name = "Service", defaultValue = " ", required = true)
    protected String service;

    @XmlElement(name = "Waiting", defaultValue = "0")
    protected int waiting;

    public BigInteger getNumber() {
        return number;
    }

    public void setNumber(final BigInteger number) {
        this.number = number;
    }

    public BigInteger getTaskId() {
        return taskId;
    }

    public void setTaskId(final BigInteger taskId) {
        this.taskId = taskId;
    }

    public int getType() {
        return type;
    }

    public void setType(final int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getEndSLA() {
        return endSLA;
    }

    public void setEndSLA(final String endSLA) {
        this.endSLA = endSLA;
    }

    public Integer getTimeFlag() {
        return timeFlag;
    }

    public void setTimeFlag(final Integer timeFlag) {
        this.timeFlag = timeFlag;
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

    public int getWaiting() {
        return waiting;
    }

    public void setWaiting(final int waiting) {
        this.waiting = waiting;
    }

}
