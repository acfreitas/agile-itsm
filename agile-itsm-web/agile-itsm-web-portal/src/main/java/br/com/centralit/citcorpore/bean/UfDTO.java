package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class UfDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7955015000680151852L;

	private Integer idUf;

	private Integer idRegioes;

	private String nomeUf;

	private String siglaUf;

	private Integer idPais;

	/**
	 * @return the idRegioes
	 */
	public Integer getIdRegioes() {
		return idRegioes;
	}

	/**
	 * @param idRegioes
	 *            the idRegioes to set
	 */
	public void setIdRegioes(Integer idRegioes) {
		this.idRegioes = idRegioes;
	}

	public Integer getIdUf() {
		return idUf;
	}

	public void setIdUf(Integer idUf) {
		this.idUf = idUf;
	}

	public String getNomeUf() {
		return nomeUf;
	}

	public void setNomeUf(String nomeUf) {
		this.nomeUf = nomeUf;
	}

	public String getSiglaUf() {
		return siglaUf;
	}

	public void setSiglaUf(String siglaUf) {
		this.siglaUf = siglaUf;
	}

	/**
	 * @return the idPais
	 */
	public Integer getIdPais() {
		return idPais;
	}

	/**
	 * @param idPais
	 *            the idPais to set
	 */
	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}
}
