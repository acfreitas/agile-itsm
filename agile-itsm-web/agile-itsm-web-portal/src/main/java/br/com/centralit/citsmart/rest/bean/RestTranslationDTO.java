package br.com.centralit.citsmart.rest.bean;

import br.com.citframework.dto.IDto;

public class RestTranslationDTO implements IDto {

    private static final long serialVersionUID = -513891671942576110L;

    private Integer idRestTranslation;
    private Integer idRestOperation;
    private Integer idBusinessObject;
    private String fromValue;
    private String toValue;

    public Integer getIdRestTranslation() {
        return idRestTranslation;
    }

    public void setIdRestTranslation(final Integer idRestTranslation) {
        this.idRestTranslation = idRestTranslation;
    }

    public Integer getIdRestOperation() {
        return idRestOperation;
    }

    public void setIdRestOperation(final Integer parm) {
        idRestOperation = parm;
    }

    public Integer getIdBusinessObject() {
        return idBusinessObject;
    }

    public void setIdBusinessObject(final Integer parm) {
        idBusinessObject = parm;
    }

    public String getFromValue() {
        return fromValue;
    }

    public void setFromValue(final String parm) {
        fromValue = parm;
    }

    public String getToValue() {
        return toValue;
    }

    public void setToValue(final String parm) {
        toValue = parm;
    }

}
