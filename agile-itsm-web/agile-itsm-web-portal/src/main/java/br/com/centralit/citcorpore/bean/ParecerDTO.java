package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class ParecerDTO implements IDto {
	private static final long serialVersionUID = 1L;
	private Integer idParecer;
	private Integer idAlcada;
	private Integer idJustificativa;
	private Integer idResponsavel;
	private Timestamp dataHoraParecer;
	private String complementoJustificativa;
	private String aprovado;
	private String observacoes;

	public Integer getIdParecer(){
		return this.idParecer;
	}
	public void setIdParecer(Integer parm){
		this.idParecer = parm;
	}

	public Integer getIdAlcada(){
		return this.idAlcada;
	}
	public void setIdAlcada(Integer parm){
		this.idAlcada = parm;
	}

	public Integer getIdJustificativa(){
		return this.idJustificativa;
	}
	public void setIdJustificativa(Integer parm){
		this.idJustificativa = parm;
	}

	public Integer getIdResponsavel(){
		return this.idResponsavel;
	}
	public void setIdResponsavel(Integer parm){
		this.idResponsavel = parm;
	}

	public Timestamp getDataHoraParecer(){
		return this.dataHoraParecer;
	}
	public void setDataHoraParecer(Timestamp parm){
		this.dataHoraParecer = parm;
	}

	public String getComplementoJustificativa(){
		return this.complementoJustificativa;
	}
	public void setComplementoJustificativa(String parm){
		this.complementoJustificativa = parm;
	}

    public String getAprovado() {
        return aprovado;
    }
    public void setAprovado(String aprovado) {
        this.aprovado = aprovado;
    }
    public String getObservacoes() {
        return observacoes;
    }
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

}
