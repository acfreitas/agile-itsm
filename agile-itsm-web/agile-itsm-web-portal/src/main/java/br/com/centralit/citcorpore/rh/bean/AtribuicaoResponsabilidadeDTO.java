package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class AtribuicaoResponsabilidadeDTO implements IDto{

	private static final long serialVersionUID = 1L;
	
	private Integer idAtribuicaoResponsabilidade;
	private String descricaoPerspectivaComplexidade;
	private Integer idNivel;
	private Integer idManualFuncao;
	
	public Integer getIdAtribuicaoResponsabilidade() {
		return idAtribuicaoResponsabilidade;
	}
	public void setIdAtribuicaoResponsabilidade(Integer idAtribuicaoResponsabilidade) {
		this.idAtribuicaoResponsabilidade = idAtribuicaoResponsabilidade;
	}
	public String getDescricaoPerspectivaComplexidade() {
		return descricaoPerspectivaComplexidade;
	}
	public void setDescricaoPerspectivaComplexidade(String descricaoPerspectivaComplexidade) {
		this.descricaoPerspectivaComplexidade = descricaoPerspectivaComplexidade;
	}
	public Integer getIdNivel() {
		return idNivel;
	}
	public void setIdNivel(Integer idNivel) {
		this.idNivel = idNivel;
	}
	public Integer getIdManualFuncao() {
		return idManualFuncao;
	}
	public void setIdManualFuncao(Integer idManualFuncao) {
		this.idManualFuncao = idManualFuncao;
	}

}
