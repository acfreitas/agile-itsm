package br.com.centralit.citcorpore.bean;

import java.io.Serializable;
import java.util.Collection;

public class InformacoesContratoItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7915036904228171712L;
	private String nome;
	private String descricao;
	private String path;
	private boolean grupo;
	private Integer idQuestionario;
	private String funcItem;
	private Collection colSubItens;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Collection getColSubItens() {
		return colSubItens;
	}
	public void setColSubItens(Collection colSubItens) {
		this.colSubItens = colSubItens;
	}
	public boolean isGrupo() {
		return grupo;
	}
	public void setGrupo(boolean grupo) {
		this.grupo = grupo;
	}
	public Integer getIdQuestionario() {
		return idQuestionario;
	}
	public void setIdQuestionario(Integer idQuestionario) {
		this.idQuestionario = idQuestionario;
	}
	public String getFuncItem() {
		return funcItem;
	}
	public void setFuncItem(String funcItem) {
		this.funcItem = funcItem;
	}
	
}
