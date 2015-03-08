package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class RequisicaoMudancaLiberacaoDTO implements IDto{
	
	private Integer idRequisicaoMudancaLiberacao;
	private Integer idRequisicaoMudanca;
	private Integer idLiberacao;
	
	private String titulo;
	private String descricao;
	

	public Integer getIdRequisicaoMudancaLiberacao() {
		return idRequisicaoMudancaLiberacao;
	}
	public void setIdRequisicaoMudancaLiberacao(Integer idRequisicaoMudancaLiberacao) {
		this.idRequisicaoMudancaLiberacao = idRequisicaoMudancaLiberacao;
	}
	public Integer getIdRequisicaoMudanca() {
		return idRequisicaoMudanca;
	}
	public void setIdRequisicaoMudanca(Integer idRequisicaoMudanca) {
		this.idRequisicaoMudanca = idRequisicaoMudanca;
	}
	public Integer getIdLiberacao() {
		return idLiberacao;
	}
	public void setIdLiberacao(Integer idLiberacao) {
		this.idLiberacao = idLiberacao;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
