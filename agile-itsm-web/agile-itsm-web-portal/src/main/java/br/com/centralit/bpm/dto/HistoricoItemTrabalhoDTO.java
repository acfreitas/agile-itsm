package br.com.centralit.bpm.dto;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class HistoricoItemTrabalhoDTO implements IDto {

    private static final long serialVersionUID = -1918532743708439850L;

    private Integer idHistoricoItemTrabalho;
    private Integer idItemTrabalho;
    private Integer idResponsavel;
    private Integer idUsuario;
    private Integer idGrupo;
    private Timestamp dataHora;
    private String acao;

    public Integer getIdHistoricoItemTrabalho() {
        return idHistoricoItemTrabalho;
    }

    public void setIdHistoricoItemTrabalho(final Integer parm) {
        idHistoricoItemTrabalho = parm;
    }

    public Integer getIdItemTrabalho() {
        return idItemTrabalho;
    }

    public void setIdItemTrabalho(final Integer parm) {
        idItemTrabalho = parm;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(final Integer parm) {
        idUsuario = parm;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(final Timestamp parm) {
        dataHora = parm;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(final String parm) {
        acao = parm;
    }

    public Integer getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(final Integer idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(final Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

}
