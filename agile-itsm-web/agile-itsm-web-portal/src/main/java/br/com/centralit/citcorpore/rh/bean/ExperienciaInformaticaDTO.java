package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class ExperienciaInformaticaDTO implements IDto {
	private Integer idExperienciaInformatica;
	private String descricao;
	private String detalhe;
	
	public String getDetalhe() {
		return detalhe;
	}
	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}
	
	public Integer getIdExperienciaInformatica() {
		return idExperienciaInformatica;
	}
	public void setIdExperienciaInformatica(Integer idExperienciaInformatica) {
		this.idExperienciaInformatica = idExperienciaInformatica;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}