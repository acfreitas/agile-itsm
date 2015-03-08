package br.com.centralit.citsmart.rest.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class RestExecutionDTO implements IDto {

    private static final long serialVersionUID = 4360912318625452051L;

    private Integer idRestExecution;
    private Integer idRestOperation;
    private Timestamp inputDateTime;
    private Integer idUser;
    private String inputClass;
    private String inputData;
    private String status;

    private RestOperationDTO restOperationDto;
    private Object input;

    public Integer getIdRestExecution() {
        return idRestExecution;
    }

    public void setIdRestExecution(final Integer parm) {
        idRestExecution = parm;
    }

    public Integer getIdRestOperation() {
        return idRestOperation;
    }

    public void setIdRestOperation(final Integer parm) {
        idRestOperation = parm;
    }

    public Timestamp getInputDateTime() {
        return inputDateTime;
    }

    public void setInputDateTime(final Timestamp parm) {
        inputDateTime = parm;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(final String parm) {
        inputData = parm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String parm) {
        status = parm;
    }

    public RestOperationDTO getRestOperationDto() {
        return restOperationDto;
    }

    public void setRestOperationDto(final RestOperationDTO restOperationDto) {
        this.restOperationDto = restOperationDto;
    }

    public String getInputClass() {
        return inputClass;
    }

    public void setInputClass(final String inputClass) {
        this.inputClass = inputClass;
    }

    public Object getInput() {
        return input;
    }

    public void setInput(final Object input) {
        this.input = input;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(final Integer idUser) {
        this.idUser = idUser;
    }

}
