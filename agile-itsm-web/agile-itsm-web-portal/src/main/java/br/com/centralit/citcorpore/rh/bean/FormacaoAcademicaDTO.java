package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class FormacaoAcademicaDTO implements IDto {
	
	private static final long serialVersionUID = 1L;
	private Integer idFormacaoAcademica;
	private String descricao;
	private String definicao;
	private String detalhe;
	
	public String getDetalhe() {
		return detalhe;
	}
	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}

	public Integer getIdFormacaoAcademica() {
		return idFormacaoAcademica;
	}
	public void setIdFormacaoAcademica(Integer idFormacaoAcademica) {
		this.idFormacaoAcademica = idFormacaoAcademica;
	}
	public String getDefinicao() {
		return definicao;
	}
	public void setDefinicao(String definicao) {
		this.definicao = definicao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}