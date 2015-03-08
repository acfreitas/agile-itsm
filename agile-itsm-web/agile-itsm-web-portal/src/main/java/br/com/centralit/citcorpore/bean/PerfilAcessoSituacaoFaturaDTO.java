package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class PerfilAcessoSituacaoFaturaDTO implements IDto {
	private Integer idPerfil;
	private String situacaoFatura;
	private java.sql.Date dataInicio;
	private java.sql.Date dataFim;

	public Integer getIdPerfil(){
		return this.idPerfil;
	}
	public void setIdPerfil(Integer parm){
		this.idPerfil = parm;
	}

	public String getSituacaoFatura(){
		return this.situacaoFatura;
	}
	public void setSituacaoFatura(String parm){
		this.situacaoFatura = parm;
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

}
