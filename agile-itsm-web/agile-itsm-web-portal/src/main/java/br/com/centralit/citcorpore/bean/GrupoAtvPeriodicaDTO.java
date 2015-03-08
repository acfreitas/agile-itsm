package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class GrupoAtvPeriodicaDTO implements IDto {
	private Integer idGrupoAtvPeriodica;
	private String nomeGrupoAtvPeriodica;
	private String descGrupoAtvPeriodica;
	private String deleted;
	private java.sql.Date dataInicio;
	private java.sql.Date dataFim;

	public Integer getIdGrupoAtvPeriodica(){
		return this.idGrupoAtvPeriodica;
	}
	public void setIdGrupoAtvPeriodica(Integer parm){
		this.idGrupoAtvPeriodica = parm;
	}

	public String getNomeGrupoAtvPeriodica(){
		return this.nomeGrupoAtvPeriodica;
	}
	public void setNomeGrupoAtvPeriodica(String parm){
		this.nomeGrupoAtvPeriodica = parm;
	}

	public String getDescGrupoAtvPeriodica(){
		return this.descGrupoAtvPeriodica;
	}
	public void setDescGrupoAtvPeriodica(String parm){
		this.descGrupoAtvPeriodica = parm;
	}

	public java.sql.Date getDataInicio(){
		return this.dataInicio;
	}
	public void setDataInicio(java.sql.Date parm){
		this.dataInicio = parm;
	}

	public java.sql.Date getDataFim(){
		return this.dataFim;
	}
	public void setDataFim(java.sql.Date parm){
		this.dataFim = parm;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

}
