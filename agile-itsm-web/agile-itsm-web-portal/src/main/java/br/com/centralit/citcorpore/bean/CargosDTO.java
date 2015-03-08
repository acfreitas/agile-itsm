package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class CargosDTO implements IDto {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private Integer idCargo;
	private String nomeCargo;
	private Date dataInicio;
	private Date dataFim;

	private Integer idDescricaoCargo;
	
	/**
	 * @return the idCargo
	 */
	public Integer getIdCargo() {
		return idCargo;
	}

	/**
	 * @param idCargo
	 *            the idCargo to set
	 */
	public void setIdCargo(Integer idCargo) {
		this.idCargo = idCargo;
	}

	/**
	 * @return the nomeCargo
	 */
	public String getNomeCargo() {
		return nomeCargo;
	}

	/**
	 * @param nomeCargo
	 *            the nomeCargo to set
	 */
	public void setNomeCargo(String nomeCargo) {
		this.nomeCargo = nomeCargo;
	}

	/**
	 * @return the dataInicio
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param dataInicio
	 *            the dataInicio to set
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return the dataFim
	 */
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * @param dataFim
	 *            the dataFim to set
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Integer getIdDescricaoCargo() {
		return idDescricaoCargo;
	}

	public void setIdDescricaoCargo(Integer idDescricaoCargo) {
		this.idDescricaoCargo = idDescricaoCargo;
	}

}
