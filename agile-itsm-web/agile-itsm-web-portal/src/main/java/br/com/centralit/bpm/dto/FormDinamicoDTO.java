package br.com.centralit.bpm.dto;

import br.com.citframework.dto.IDto;

public class FormDinamicoDTO implements IDto {

    private static final long serialVersionUID = -4000788525797569832L;

    private Integer idVisao;
    private String descricao;
    private String tipoVisao;
    private String situacao;
    private String classeName;
    private String identificador;

    public Integer getIdVisao() {
        return idVisao;
    }

    public void setIdVisao(final Integer idVisao) {
        this.idVisao = idVisao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    public String getTipoVisao() {
        return tipoVisao;
    }

    public void setTipoVisao(final String tipoVisao) {
        this.tipoVisao = tipoVisao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(final String situacao) {
        this.situacao = situacao;
    }

    public String getClasseName() {
        return classeName;
    }

    public void setClasseName(final String classeName) {
        this.classeName = classeName;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(final String identificador) {
        this.identificador = identificador;
    }

}
