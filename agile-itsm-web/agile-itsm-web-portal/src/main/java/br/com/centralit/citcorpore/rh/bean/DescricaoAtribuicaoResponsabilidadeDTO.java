package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class DescricaoAtribuicaoResponsabilidadeDTO implements IDto{

	private static final long serialVersionUID = 1L;
	
	private Integer idDescricao;
	private Integer nivel;
	private String descricao;
	private String situacao;
	
	public Integer getIdDescricao() {
		return idDescricao;
	}
	public void setIdDescricao(Integer idDescricao) {
		this.idDescricao = idDescricao;
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
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
}
