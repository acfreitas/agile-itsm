package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class PrioridadeAcordoNivelServicoDTO implements IDto {
	private Integer idUnidade;
	private Integer idAcordoNivelServico;
	private Integer idPrioridade;
	private java.sql.Date dataInicio;
	private java.sql.Date dataFim;

	public Integer getIdUnidade(){
		return this.idUnidade;
	}
	public void setIdUnidade(Integer parm){
		this.idUnidade = parm;
	}

	public Integer getIdAcordoNivelServico(){
		return this.idAcordoNivelServico;
	}
	public void setIdAcordoNivelServico(Integer parm){
		this.idAcordoNivelServico = parm;
	}

	public Integer getIdPrioridade(){
		return this.idPrioridade;
	}
	public void setIdPrioridade(Integer parm){
		this.idPrioridade = parm;
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
