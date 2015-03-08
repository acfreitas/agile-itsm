package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class MidiaDTO implements IDto  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3534747605951252279L;

	private Integer idMidia;
	private String nome;
	
	
	public Integer getIdMidia() {
		return idMidia;
	}
	public void setIdMidia(Integer idMidia) {
		this.idMidia = idMidia;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
