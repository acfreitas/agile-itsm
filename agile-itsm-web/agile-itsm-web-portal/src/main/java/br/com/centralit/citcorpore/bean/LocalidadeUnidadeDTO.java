package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class LocalidadeUnidadeDTO implements IDto {

	private Integer idLocalidadeUnidade;
	private Integer idLocalidade;
	private Integer idUnidade;
	private Date dataInicio;
	private Date dataFim;

	/**
	 * @return the idLocalidadeUnidade
	 */
	public Integer getIdLocalidadeUnidade() {
		return idLocalidadeUnidade;
	}

	/**
	 * @param idLocalidadeUnidade
	 *            the idLocalidadeUnidade to set
	 */
	public void setIdLocalidadeUnidade(Integer idLocalidadeUnidade) {
		this.idLocalidadeUnidade = idLocalidadeUnidade;
	}

	/**
	 * @return the idLocalidade
	 */
	public Integer getIdLocalidade() {
		return idLocalidade;
	}

	/**
	 * @param idLocalidade
	 *            the idLocalidade to set
	 */
	public void setIdLocalidade(Integer idLocalidade) {
		this.idLocalidade = idLocalidade;
	}

	/**
	 * @return the idUnidade
	 */
	public Integer getIdUnidade() {
		return idUnidade;
	}

	/**
	 * @param idUnidade
	 *            the idUnidade to set
	 */
	public void setIdUnidade(Integer idUnidade) {
		this.idUnidade = idUnidade;
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

}
