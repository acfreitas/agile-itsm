package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class CriterioAvaliacaoFornecedorDTO implements IDto {
	private Integer idAvaliacaoFornecedor;

	private Integer idCriterio;

	private String valor;

	private Integer valorInteger;

	private String obs;
	
	private String descricao;
	
	

	public Integer getIdAvaliacaoFornecedor() {
		return this.idAvaliacaoFornecedor;
	}

	public void setIdAvaliacaoFornecedor(Integer parm) {
		this.idAvaliacaoFornecedor = parm;
	}

	public Integer getIdCriterio() {
		return this.idCriterio;
	}

	public void setIdCriterio(Integer parm) {
		this.idCriterio = parm;
	}

	/**
	 * @return the obs
	 */
	public String getObs() {
		return obs;
	}

	/**
	 * @param obs
	 *            the obs to set
	 */
	public void setObs(String obs) {
		this.obs = obs;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return the valorInteger
	 */
	public Integer getValorInteger() {
		return valorInteger;
	}

	/**
	 * @param valorInteger
	 *            the valorInteger to set
	 */
	public void setValorInteger(Integer valorInteger) {
		this.valorInteger = valorInteger;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
