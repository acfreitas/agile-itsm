package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;


public class TreinamentoCurriculoDTO implements IDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idTreinamento;
	private String treinamento;
	private String descricaoTreinamento;
	private Integer idCurriculo;
	private Integer idCurso;
	
	
	public Integer getIdTreinamento() {
		return idTreinamento;
	}
	public void setIdTreinamento(Integer idTreinamento) {
		this.idTreinamento = idTreinamento;
	}
	public String getTreinamento() {
		return treinamento;
	}
	public void setTreinamento(String treinamento) {
		this.treinamento = treinamento;
	}
	
	public Integer getIdCurriculo() {
		return idCurriculo;
	}
	public void setIdCurriculo(Integer idCurriculo) {
		this.idCurriculo = idCurriculo;
	}
	public Integer getIdCurso() {
		return idCurso;
	}
	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
	}
	public String getDescricaoTreinamento() {
		return descricaoTreinamento;
	}
	public void setDescricaoTreinamento(String descricaoTreinamento) {
		this.descricaoTreinamento = descricaoTreinamento;
	}
	
	
}