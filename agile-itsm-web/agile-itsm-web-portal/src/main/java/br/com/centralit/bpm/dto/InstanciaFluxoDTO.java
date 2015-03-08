package br.com.centralit.bpm.dto;

import java.sql.Timestamp;
import java.util.List;

import br.com.citframework.dto.IDto;

public class InstanciaFluxoDTO implements IDto {

    private static final long serialVersionUID = -7198923723510246056L;

    private Integer idInstancia;
    private Integer idFluxo;
    private Timestamp dataHoraCriacao;
    private String situacao;
    private Timestamp dataHoraFinalizacao;
    private List<ObjetoInstanciaFluxoDTO> colObjetos;
    private FluxoDTO fluxoDto;

    public Integer getIdInstancia() {
        return idInstancia;
    }

    public void setIdInstancia(final Integer idInstancia) {
        this.idInstancia = idInstancia;
    }

    public Integer getIdFluxo() {
        return idFluxo;
    }

    public void setIdFluxo(final Integer parm) {
        idFluxo = parm;
    }

    public Timestamp getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public void setDataHoraCriacao(final Timestamp parm) {
        dataHoraCriacao = parm;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(final String parm) {
        situacao = parm;
    }

    public Timestamp getDataHoraFinalizacao() {
        return dataHoraFinalizacao;
    }

    public void setDataHoraFinalizacao(final Timestamp parm) {
        dataHoraFinalizacao = parm;
    }

    public FluxoDTO getFluxoDto() {
        return fluxoDto;
    }

    public void setFluxoDto(final FluxoDTO fluxoDto) {
        this.fluxoDto = fluxoDto;
    }

    public List<ObjetoInstanciaFluxoDTO> getColObjetos() {
        return colObjetos;
    }

    public void setColObjetos(final List<ObjetoInstanciaFluxoDTO> colObjetos) {
        this.colObjetos = colObjetos;
    }
}
