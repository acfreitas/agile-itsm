package br.com.centralit.bpm.dto;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class AtribuicaoFluxoDTO implements IDto {

    private static final long serialVersionUID = -1385745853594371619L;

    private Integer idAtribuicao;
    private Integer idItemTrabalho;
    private String tipo;
    private Integer idUsuario;
    private Integer idGrupo;
    private Timestamp dataHora;

    public Integer getIdAtribuicao() {
        return idAtribuicao;
    }

    public void setIdAtribuicao(final Integer idAtribuicao) {
        this.idAtribuicao = idAtribuicao;
    }

    public Integer getIdItemTrabalho() {
        return idItemTrabalho;
    }

    public void setIdItemTrabalho(final Integer idItemTrabalho) {
        this.idItemTrabalho = idItemTrabalho;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(final Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(final Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(final Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }

}
