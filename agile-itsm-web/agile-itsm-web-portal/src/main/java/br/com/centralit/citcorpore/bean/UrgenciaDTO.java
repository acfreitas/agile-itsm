package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * 
 * @author rodrigo.oliveira
 *
 */
public class UrgenciaDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1577894703743197591L;
	
	private Integer idUrgencia;
	private String nivelUrgencia;
	private String siglaUrgencia;
	
	/**
	 * @return the idUrgencia
	 */
	public Integer getIdUrgencia() {
		return idUrgencia;
	}
	/**
	 * @param idUrgencia the idUrgencia to set
	 */
	public void setIdUrgencia(Integer idUrgencia) {
		this.idUrgencia = idUrgencia;
	}
	/**
	 * @return the nivelUrgencia
	 */
	public String getNivelUrgencia() {
		return nivelUrgencia;
	}
	/**
	 * @param nivelUrgencia the nivelUrgencia to set
	 */
	public void setNivelUrgencia(String nivelUrgencia) {
		this.nivelUrgencia = nivelUrgencia;
	}
	/**
	 * @return the siglaUrgencia
	 */
	public String getSiglaUrgencia() {
		return siglaUrgencia;
	}
	/**
	 * @param siglaUrgencia the siglaUrgencia to set
	 */
	public void setSiglaUrgencia(String siglaUrgencia) {
		this.siglaUrgencia = siglaUrgencia;
	}
	
}
