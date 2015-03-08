package br.com.centralit.citsmart.rest.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

public class RestOperationDTO implements IDto {

    private static final long serialVersionUID = -71258999067214258L;

    private Integer idRestOperation;
    private Integer idBatchProcessing;
    private String name;
    private String description;
    private String descriptionParameter;
    private String identifier;
    private String operationType;
    private String classType;
    private String javaClass;
    private String javaScript;
    private String status;
    private String generateLog;

    private Collection<RestPermissionDTO> colGrupos;
    private Collection<RestDomainDTO> colDominios;

    public String getDescriptionParameter() {
        return descriptionParameter;
    }

    public void setDescriptionParameter(final String descriptionParameter) {
        this.descriptionParameter = descriptionParameter;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(final String identifier) {
        this.identifier = identifier;
    }

    public Collection<RestPermissionDTO> getColGrupos() {
        return colGrupos;
    }

    public void setColGrupos(final Collection<RestPermissionDTO> colGrupos) {
        this.colGrupos = colGrupos;
    }

    public Integer getIdRestOperation() {
        return idRestOperation;
    }

    public void setIdRestOperation(final Integer parm) {
        idRestOperation = parm;
    }

    public Integer getIdBatchProcessing() {
        return idBatchProcessing;
    }

    public void setIdBatchProcessing(final Integer parm) {
        idBatchProcessing = parm;
    }

    public String getName() {
        return name;
    }

    public void setName(final String parm) {
        name = parm;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String parm) {
        description = parm;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(final String parm) {
        operationType = parm;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(final String parm) {
        classType = parm;
    }

    public String getJavaClass() {
        return javaClass;
    }

    public void setJavaClass(final String parm) {
        javaClass = parm;
    }

    public String getJavaScript() {
        return javaScript;
    }

    public void setJavaScript(final String parm) {
        javaScript = parm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String parm) {
        status = parm;
    }

    public String getGenerateLog() {
        return generateLog;
    }

    public void setGenerateLog(final String generateLog) {
        this.generateLog = generateLog;
    }

    public Collection<RestDomainDTO> getColDominios() {
        return colDominios;
    }

    public void setColDominios(final Collection<RestDomainDTO> colDominios) {
        this.colDominios = colDominios;
    }

}
