package br.com.centralit.citsmart.rest.bean;

import br.com.citframework.dto.IDto;

public class RestPermissionDTO implements IDto {

    private static final long serialVersionUID = 4936344305771369885L;

    private Integer idRestOperation;
    private Integer idGroup;
    private String status;

    private Integer idGrupo;
    private String sigla;

    public Integer getIdRestOperation() {
        return idRestOperation;
    }

    public void setIdRestOperation(final Integer idRestOperation) {
        this.idRestOperation = idRestOperation;
    }

    public Integer getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(final Integer idGroup) {
        this.idGroup = idGroup;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(final String sigla) {
        this.sigla = sigla;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(final Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

}
