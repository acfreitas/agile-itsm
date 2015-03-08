package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class RegiaoDTO implements IDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer idRegioes;
	private String nome;
	/**
	 * @return the idRegioes
	 */
	public Integer getIdRegioes() {
		return idRegioes;
	}
	/**
	 * @param idRegioes the idRegioes to set
	 */
	public void setIdRegioes(Integer idRegioes) {
		this.idRegioes = idRegioes;
	}
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
	
	

}
