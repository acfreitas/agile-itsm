
package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * 
 * @author rodrigo.oliveira
 *
 */
public class ImpactoDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6022715089669030485L;
	
	private Integer idImpacto;
	private String nivelImpacto;
	private String siglaImpacto;
	
	/**
	 * @return the idImpacto
	 */
	public Integer getIdImpacto() {
		return idImpacto;
	}
	/**
	 * @param idImpacto the idImpacto to set
	 */
	public void setIdImpacto(Integer idImpacto) {
		this.idImpacto = idImpacto;
	}
	/**
	 * @return the nivelImpacto
	 */
	public String getNivelImpacto() {
		return nivelImpacto;
	}
	/**
	 * @param nivelImpacto the nivelImpacto to set
	 */
	public void setNivelImpacto(String nivelImpacto) {
		this.nivelImpacto = nivelImpacto;
	}
	/**
	 * @return the siglaImpacto
	 */
	public String getSiglaImpacto() {
		return siglaImpacto;
	}
	/**
	 * @param siglaImpacto the siglaImpacto to set
	 */
	public void setSiglaImpacto(String siglaImpacto) {
		this.siglaImpacto = siglaImpacto;
	}
	
}
