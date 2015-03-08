package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class JustificativaRequisicaoFuncaoDTO implements IDto {

	private static final long serialVersionUID = 1L;
	private Integer idJustificativa;
	private String descricao;
	private String situacao;

	public Integer getIdJustificativa() {
		return idJustificativa;
	}
	public void setIdJustificativa(Integer idJustificativa) {
		this.idJustificativa = idJustificativa;
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

