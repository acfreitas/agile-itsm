package br.com.centralit.citsmart.rest.bean;

import br.com.citframework.dto.IDto;

public class RestDomainDTO implements IDto {

    private static final long serialVersionUID = 3718304778911783408L;

    private Integer idRestOperation;
    private Integer idRestParameter;
    private String value;

    private Integer sequencia;

    public Integer getIdRestOperation() {
        return idRestOperation;
    }

    public void setIdRestOperation(final Integer idRestOperation) {
        this.idRestOperation = idRestOperation;
    }

    public Integer getIdRestParameter() {
        return idRestParameter;
    }

    public void setIdRestParameter(final Integer idRestParameter) {
        this.idRestParameter = idRestParameter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public Integer getSequencia() {
        return sequencia;
    }

    public void setSequencia(final Integer sequencia) {
        this.sequencia = sequencia;
    }

}
