package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class TipoDemandaDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4413980874410791397L;
	private Integer idTipoDemanda;
	private String nomeTipoDemanda;
	private String classificacao;
	private String deleted;
	
	public Integer getIdTipoDemanda() {
		return idTipoDemanda;
	}
	public void setIdTipoDemanda(Integer idTipoDemanda) {
		this.idTipoDemanda = idTipoDemanda;
	}
	public String getNomeTipoDemanda() {
		return nomeTipoDemanda;
	}
	public void setNomeTipoDemanda(String nomeTipoDemanda) {
		this.nomeTipoDemanda = nomeTipoDemanda;
	}
	public String getClassificacao() {
		return classificacao;
	}
	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}
	/**
	 * @return the deleted
	 */
	public String getDeleted() {
		return deleted;
	}
	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
}
