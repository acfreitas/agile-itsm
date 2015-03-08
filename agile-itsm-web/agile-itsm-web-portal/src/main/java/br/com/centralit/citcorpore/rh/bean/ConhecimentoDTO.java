package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class ConhecimentoDTO implements IDto {
	private Integer idConhecimento;
	private String descricao;
	private String detalhe;
	
	public String getDetalhe() {
		return detalhe;
	}
	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}
	
	public Integer getIdConhecimento() {
		return idConhecimento;
	}
	public void setIdConhecimento(Integer idConhecimento) {
		this.idConhecimento = idConhecimento;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}