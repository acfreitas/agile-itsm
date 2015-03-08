package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class RequisicaoFormacaoAcademicaDTO implements IDto {
	private Integer idSolicitacaoServico;
	private Integer idFormacaoAcademica;
	private String obrigatorio;
	
	private String descricao;
	private String detalhe;
	
	
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public String getDetalhe() {
		return detalhe;
	}
	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}
	
	public String getObrigatorio() {
		return obrigatorio;
	}
	public void setObrigatorio(String obrigatorio) {
		this.obrigatorio = obrigatorio;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getIdFormacaoAcademica() {
		return idFormacaoAcademica;
	}
	public void setIdFormacaoAcademica(Integer idFormacaoAcademica) {
		this.idFormacaoAcademica = idFormacaoAcademica;
	}
	
	

}
