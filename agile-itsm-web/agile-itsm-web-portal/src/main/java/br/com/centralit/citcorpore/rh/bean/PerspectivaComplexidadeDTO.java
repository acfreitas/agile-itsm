package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class PerspectivaComplexidadeDTO implements IDto {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idPerspectivaComplexidade;
	private String descricaoPerspectivaComplexidade;
	private Integer nivelPerspectivaComplexidade;
	private Integer idSolicitacaoServico;

	
	public Integer getIdPerspectivaComplexidade() {
		return idPerspectivaComplexidade;
	}
	public void setIdPerspectivaComplexidade(Integer idPerspectivaComplexidade) {
		this.idPerspectivaComplexidade = idPerspectivaComplexidade;
	}
	public String getDescricaoPerspectivaComplexidade() {
		return descricaoPerspectivaComplexidade;
	}
	public void setDescricaoPerspectivaComplexidade(
			String descricaoPerspectivaComplexidade) {
		this.descricaoPerspectivaComplexidade = descricaoPerspectivaComplexidade;
	}
	public Integer getNivelPerspectivaComplexidade() {
		return nivelPerspectivaComplexidade;
	}
	public void setNivelPerspectivaComplexidade(Integer nivelPerspectivaComplexidade) {
		this.nivelPerspectivaComplexidade = nivelPerspectivaComplexidade;
	}
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	
	
	
	
}