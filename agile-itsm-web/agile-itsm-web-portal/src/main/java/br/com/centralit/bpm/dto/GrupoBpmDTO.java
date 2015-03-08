package br.com.centralit.bpm.dto;

import br.com.citframework.dto.IDto;

public class GrupoBpmDTO implements IDto {

    private static final long serialVersionUID = 8240992661414164190L;

    private Integer idGrupo;
    private String sigla;
    private String[] emails;

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(final Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(final String sigla) {
        this.sigla = sigla;
    }

    public String[] getEmails() {
        return emails;
    }

    public void setEmails(final String[] emails) {
        this.emails = emails;
    }

}
