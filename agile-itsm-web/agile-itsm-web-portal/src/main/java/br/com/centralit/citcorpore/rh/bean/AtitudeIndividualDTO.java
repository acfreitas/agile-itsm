package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class AtitudeIndividualDTO implements IDto {

	private static final long serialVersionUID = 1955373691755479895L;

	private Integer idAtitudeIndividual;

	private String descricao;

	private String detalhe;

	// campos auxiliares
	private String idCmbCompetenciaComportamental;

	private String descricaoCmbCompetenciaComportamental;

	private String comportamento;

	public String getDetalhe() {
		return detalhe;
	}

	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}

	public Integer getIdAtitudeIndividual() {
		return idAtitudeIndividual;
	}

	public void setIdAtitudeIndividual(Integer idAtitudeIndividual) {
		this.idAtitudeIndividual = idAtitudeIndividual;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getIdCmbCompetenciaComportamental() {
		return idCmbCompetenciaComportamental;
	}

	public void setIdCmbCompetenciaComportamental(String idCmbCompetenciaComportamental) {
		this.idCmbCompetenciaComportamental = idCmbCompetenciaComportamental;
	}

	public String getDescricaoCmbCompetenciaComportamental() {
		return descricaoCmbCompetenciaComportamental;
	}

	public void setDescricaoCmbCompetenciaComportamental(String descricaoCmbCompetenciaComportamental) {
		this.descricaoCmbCompetenciaComportamental = descricaoCmbCompetenciaComportamental;
	}

	public String getComportamento() {
		return comportamento;
	}

	public void setComportamento(String comportamento) {
		this.comportamento = comportamento;
	}

}