package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class InspecaoEntregaItemDTO implements IDto {
	private Integer idEntrega;
	private Integer idCriterio;
	private Timestamp dataHoraInspecao;
	private Integer idResponsavel;
	private String avaliacao;
	private String observacoes;
	
    private String tipoAvaliacao;
	
	private Integer sequencia;

	public Integer getIdEntrega(){
		return this.idEntrega;
	}
	public void setIdEntrega(Integer parm){
		this.idEntrega = parm;
	}

	public Integer getIdCriterio(){
		return this.idCriterio;
	}
	public void setIdCriterio(Integer parm){
		this.idCriterio = parm;
	}

	public Timestamp getDataHoraInspecao(){
		return this.dataHoraInspecao;
	}
	public void setDataHoraInspecao(Timestamp parm){
		this.dataHoraInspecao = parm;
	}

	public Integer getIdResponsavel(){
		return this.idResponsavel;
	}
	public void setIdResponsavel(Integer parm){
		this.idResponsavel = parm;
	}

	public String getAvaliacao(){
		return this.avaliacao;
	}
	public void setAvaliacao(String parm){
		this.avaliacao = parm;
	}

	public String getObservacoes(){
		return this.observacoes;
	}
	public void setObservacoes(String parm){
		this.observacoes = parm;
	}
    public Integer getSequencia() {
        return sequencia;
    }
    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }
    public String getTipoAvaliacao() {
        return tipoAvaliacao;
    }
    public void setTipoAvaliacao(String tipoAvaliacao) {
        this.tipoAvaliacao = tipoAvaliacao;
    }

}
