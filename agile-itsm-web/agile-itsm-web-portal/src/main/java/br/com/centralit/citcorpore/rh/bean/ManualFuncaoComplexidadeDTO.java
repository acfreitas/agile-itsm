package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class ManualFuncaoComplexidadeDTO implements IDto{

	private static final long serialVersionUID = 1L;
	
	private Integer idComplexidade;
	private Integer nivel;
	private String descricao;
	
	public Integer getIdComplexidade() {
		return idComplexidade;
	}
	public void setIdComplexidade(Integer idComplexidade) {
		this.idComplexidade = idComplexidade;
	}
	public Integer getNivel() {
		return nivel;
	}
	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
