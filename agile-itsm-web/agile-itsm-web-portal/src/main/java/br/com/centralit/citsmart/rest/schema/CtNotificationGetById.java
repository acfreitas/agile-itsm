package br.com.centralit.citsmart.rest.schema;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtNotificationGetById", propOrder = "taskId")
public class CtNotificationGetById extends CtMessage {

    @XmlElement(name = "TaskId", required = true)
    protected BigInteger taskId;

    public BigInteger getTaskId() {
        return taskId;
    }

    public void setTaskId(final BigInteger taskId) {
        this.taskId = taskId;
    }

}
