package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class PaisDTO implements IDto {

	private Integer idPais;

	private String nomePais;

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

	/**
	 * @return the nomePais
	 */
	public String getNomePais() {
		return nomePais;
	}

	/**
	 * @param nomePais
	 *            the nomePais to set
	 */
	public void setNomePais(String nomePais) {
		this.nomePais = nomePais;
	}

}
