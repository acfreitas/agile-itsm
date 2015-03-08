package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class TipoUnidadeDTO implements IDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6238123231107968776L;
	private Integer idTipoUnidade;
	private Integer idEmpresa;
	private String nomeTipoUnidade;
	private Date dataInicio;
	private Date dataFim;

	/**
	 * @return the idTipoUnidade
	 */
	public Integer getIdTipoUnidade() {
		return idTipoUnidade;
	}

	/**
	 * @param idTipoUnidade
	 *            the idTipoUnidade to set
	 */
	public void setIdTipoUnidade(Integer idTipoUnidade) {
		this.idTipoUnidade = idTipoUnidade;
	}

	/**
	 * @return the idEmpresa
	 */
	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	/**
	 * @param idEmpresa
	 *            the idEmpresa to set
	 */
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	/**
	 * @return the nomeTipoUnidade
	 */
	public String getNomeTipoUnidade() {
		return nomeTipoUnidade;
	}

	/**
	 * @param nomeTipoUnidade
	 *            the nomeTipoUnidade to set
	 */
	public void setNomeTipoUnidade(String nomeTipoUnidade) {
		this.nomeTipoUnidade = nomeTipoUnidade;
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
