package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class DepartamentoDTO implements IDto {
	
	private static final long serialVersionUID = 3530827249740252618L;

	private Integer idDepartamento;
	private String descricao;
	private Integer idCentroCusto;
	private Integer lotacaoPai;
	private String situacao;
	private String analitico;
	private Integer idParceiro;
	private Integer codDep;
	
	
	public Integer getIdDepartamento() {
		return idDepartamento;
	}
	public void setIdDepartamento(Integer idDepartamento) {
		this.idDepartamento = idDepartamento;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getIdCentroCusto() {
		return idCentroCusto;
	}
	public void setIdCentroCusto(Integer idCentroCusto) {
		this.idCentroCusto = idCentroCusto;
	}
	public Integer getLotacaoPai() {
		return lotacaoPai;
	}
	public void setLotacaoPai(Integer lotacaoPai) {
		this.lotacaoPai = lotacaoPai;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getAnalitico() {
		return analitico;
	}
	public void setAnalitico(String analitico) {
		this.analitico = analitico;
	}
	public Integer getIdParceiro() {
		return idParceiro;
	}
	public void setIdParceiro(Integer idParceiro) {
		this.idParceiro = idParceiro;
	}
	public Integer getCodDep() {
		return codDep;
	}
	public void setCodDep(Integer codDep) {
		this.codDep = codDep;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}