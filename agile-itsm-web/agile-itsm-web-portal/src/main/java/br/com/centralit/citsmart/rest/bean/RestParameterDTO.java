package br.com.centralit.citsmart.rest.bean;

import br.com.citframework.dto.IDto;

public class RestParameterDTO implements IDto {

    private static final long serialVersionUID = 1747262424750256746L;

    private Integer idRestParameter;
    private String identifier;
    private String description;

    public Integer getIdRestParameter() {
        return idRestParameter;
    }

    public void setIdRestParameter(final Integer idRestParameter) {
        this.idRestParameter = idRestParameter;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(final String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

}
