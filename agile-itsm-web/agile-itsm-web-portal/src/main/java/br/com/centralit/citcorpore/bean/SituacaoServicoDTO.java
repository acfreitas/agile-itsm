package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class SituacaoServicoDTO implements IDto {

	private Integer idSituacaoServico;
	private Integer idEmpresa;
	private String nomeSituacaoServico;
	private Date dataInicio;
	private Date dataFim;

	public Integer getIdSituacaoServico() {
		return idSituacaoServico;
	}

	public void setIdSituacaoServico(Integer idSituacaoServico) {
		this.idSituacaoServico = idSituacaoServico;
	}

	public String getNomeSituacaoServico() {
		return nomeSituacaoServico;
	}

	public void setNomeSituacaoServico(String nomeSituacaoServico) {
		this.nomeSituacaoServico = nomeSituacaoServico;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
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