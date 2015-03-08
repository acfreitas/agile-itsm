package br.com.centralit.bpm.dto;

import br.com.citframework.dto.IDto;

public class TipoFluxoDTO implements IDto {

    private static final long serialVersionUID = -1499776310196719937L;

    private Integer idTipoFluxo;
    private String nomeFluxo;
    private String descricao;
    private String nomeClasseFluxo;
    private Integer idProcessoNegocio;

    public Integer getIdTipoFluxo() {
        return idTipoFluxo;
    }

    public void setIdTipoFluxo(final Integer parm) {
        idTipoFluxo = parm;
    }

    public String getNomeFluxo() {
        return nomeFluxo;
    }

    public void setNomeFluxo(final String parm) {
        nomeFluxo = parm;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(final String parm) {
        descricao = parm;
    }

    public String getNomeClasseFluxo() {
        return nomeClasseFluxo;
    }

    public void setNomeClasseFluxo(final String nomeClasseFluxo) {
        this.nomeClasseFluxo = nomeClasseFluxo;
    }

    public Integer getIdProcessoNegocio() {
        return idProcessoNegocio;
    }

    public void setIdProcessoNegocio(final Integer idProcessoNegocio) {
        this.idProcessoNegocio = idProcessoNegocio;
    }

}
