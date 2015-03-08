package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class RelatorioAcompanhamentoDTO implements IDto {

	private static final long serialVersionUID = 1L;
	
	private Double anoDouble;

	private Integer idContrato;

	private Double custoAtividade;

	private Double valorEstimadoContrato;

	private String mes;
	
	private Double numeroMesDouble;

	private Integer peridoVigenciaContrato;

	private Integer quantidadePeriodoRealizado;

	private Date dataInicioContrato;

	private Date dataFimContrato;

	private Double valorPorRata;

	private Double saldo;

	/**
	 * @return the ano
	 */
	public Integer getAno() {
		return (int)(this.anoDouble / 1);
	}

	/**
	 * @return the idContrato
	 */
	public Integer getIdContrato() {
		return idContrato;
	}

	/**
	 * @param idContrato
	 *            the idContrato to set
	 */
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	/**
	 * @return the custoAtividade
	 */
	public Double getCustoAtividade() {
		return custoAtividade;
	}

	/**
	 * @param custoAtividade
	 *            the custoAtividade to set
	 */
	public void setCustoAtividade(Double custoAtividade) {
		this.custoAtividade = custoAtividade;
	}

	/**
	 * @return the valorEstimadoContrato
	 */
	public Double getValorEstimadoContrato() {
		return valorEstimadoContrato;
	}

	/**
	 * @param valorEstimadoContrato
	 *            the valorEstimadoContrato to set
	 */
	public void setValorEstimadoContrato(Double valorEstimadoContrato) {
		this.valorEstimadoContrato = valorEstimadoContrato;
	}

	/**
	 * @return the mes
	 */
	public String getMes() {
		return mes;
	}

	/**
	 * @param mes
	 *            the mes to set
	 */
	public void setMes(String mes) {
		this.mes = mes;
	}

	/**
	 * @return the numeroMes
	 */
	public Integer getNumeroMes() {
		return (int) (this.numeroMesDouble / 1);
	}


	/**
	 * @return the dataInicioContrato
	 */
	public Date getDataInicioContrato() {
		return dataInicioContrato;
	}

	/**
	 * @param dataInicioContrato
	 *            the dataInicioContrato to set
	 */
	public void setDataInicioContrato(Date dataInicioContrato) {
		this.dataInicioContrato = dataInicioContrato;
	}

	/**
	 * @return the dataFimContrato
	 */
	public Date getDataFimContrato() {
		return dataFimContrato;
	}

	/**
	 * @param dataFimContrato
	 *            the dataFimContrato to set
	 */
	public void setDataFimContrato(Date dataFimContrato) {
		this.dataFimContrato = dataFimContrato;
	}

	/**
	 * @return the peridoVigenciaContrato
	 */
	public Integer getPeridoVigenciaContrato() {
		return peridoVigenciaContrato;
	}

	/**
	 * @param peridoVigenciaContrato
	 *            the peridoVigenciaContrato to set
	 */
	public void setPeridoVigenciaContrato(Integer peridoVigenciaContrato) {
		this.peridoVigenciaContrato = peridoVigenciaContrato;
	}

	/**
	 * @return the quantidadePeriodoRealizado
	 */
	public Integer getQuantidadePeriodoRealizado() {
		return quantidadePeriodoRealizado;
	}

	/**
	 * @param quantidadePeriodoRealizado
	 *            the quantidadePeriodoRealizado to set
	 */
	public void setQuantidadePeriodoRealizado(Integer quantidadePeriodoRealizado) {
		this.quantidadePeriodoRealizado = quantidadePeriodoRealizado;
	}

	/**
	 * @return the valorPorRata
	 */
	public Double getValorPorRata() {
		return valorPorRata;
	}

	/**
	 * @param valorPorRata
	 *            the valorPorRata to set
	 */
	public void setValorPorRata(Double valorPorRata) {
		this.valorPorRata = valorPorRata;
	}

	/**
	 * @return the saldo
	 */
	public Double getSaldo() {
		return saldo;
	}

	/**
	 * @param saldo
	 *            the saldo to set
	 */
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Double getNumeroMesDouble() {
		return numeroMesDouble;
	}

	public void setNumeroMesDouble(Double numeroMesDouble) {
		this.numeroMesDouble = numeroMesDouble;
	}

	public Double getAnoDouble() {
		return anoDouble;
	}

	public void setAnoDouble(Double anoDouble) {
		this.anoDouble = anoDouble;
	}

}
