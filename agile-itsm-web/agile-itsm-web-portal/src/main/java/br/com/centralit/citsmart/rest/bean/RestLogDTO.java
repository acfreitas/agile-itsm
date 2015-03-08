package br.com.centralit.citsmart.rest.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class RestLogDTO implements IDto {

    private static final long serialVersionUID = -1906283961500145476L;

    private Integer idRestLog;
    private Integer idRestExecution;
    private Timestamp dateTime;
    private String status;
    private String resultClass;
    private String resultData;

    public Integer getIdRestLog() {
        return idRestLog;
    }

    public void setIdRestLog(final Integer parm) {
        idRestLog = parm;
    }

    public Integer getIdRestExecution() {
        return idRestExecution;
    }

    public void setIdRestExecution(final Integer parm) {
        idRestExecution = parm;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(final Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getResultData() {
        return resultData;
    }

    public void setResultData(final String parm) {
        resultData = parm;
    }

    public String getResultClass() {
        return resultClass;
    }

    public void setResultClass(final String resultClass) {
        this.resultClass = resultClass;
    }

}
