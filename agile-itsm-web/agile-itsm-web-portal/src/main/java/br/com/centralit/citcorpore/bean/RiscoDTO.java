package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
	@author Bruno Rodrigues
*/
public class RiscoDTO implements IDto{

	private Integer idRisco;
	private String nomeRisco;
	private String detalhamento;
	private Integer nivelRisco;
	private Date dataInicio;
    private Date dataFim;
	
	public Integer getIdRisco() {
		return idRisco;
	}
	public void setIdRisco(Integer idRisco) {
		this.idRisco = idRisco;
	}
	public String getNomeRisco() {
		return nomeRisco;
	}
	public void setNomeRisco(String nomeRisco) {
		this.nomeRisco = nomeRisco;
	}
	public String getDetalhamento() {
		return detalhamento;
	}
	public void setDetalhamento(String detalhamento) {
		this.detalhamento = detalhamento;
	}
	public Integer getNivelRisco() {
		return nivelRisco;
	}
	public void setNivelRisco(Integer nivelRisco) {
		this.nivelRisco = nivelRisco;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
}
