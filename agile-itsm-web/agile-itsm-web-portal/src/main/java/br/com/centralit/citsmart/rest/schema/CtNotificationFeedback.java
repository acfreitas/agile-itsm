package br.com.centralit.citsmart.rest.schema;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CtNotificationFeedback", propOrder = {"taskId", "feedback", "reasonId", "comments"})
public class CtNotificationFeedback extends CtMessage {

    @XmlElement(name = "TaskId", required = true)
    protected BigInteger taskId;

    @XmlElement(name = "Feedback")
    protected int feedback;

    @XmlElement(name = "ReasonId")
    protected Integer reasonId;

    @XmlElement(name = "Comments")
    protected String comments;

    public BigInteger getTaskId() {
        return taskId;
    }

    public void setTaskId(final BigInteger taskId) {
        this.taskId = taskId;
    }

    public int getFeedback() {
        return feedback;
    }

    public void setFeedback(final int feedback) {
        this.feedback = feedback;
    }

    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(final Integer reasonId) {
        this.reasonId = reasonId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(final String comments) {
        this.comments = comments;
    }

}
