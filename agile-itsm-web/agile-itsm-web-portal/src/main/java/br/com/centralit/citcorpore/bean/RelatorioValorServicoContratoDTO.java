/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import java.math.BigDecimal;
import java.util.Date;

import br.com.citframework.dto.IDto;

public class RelatorioValorServicoContratoDTO implements IDto {

	private static final long serialVersionUID = 5769173299912237423L;
	
	private String nomeServico;

	private Integer quantidade;
	private BigDecimal valorServico;
	private BigDecimal totalValorServico;
	private BigDecimal totalGeral;
	private java.util.Date dataInicio;
	private java.util.Date dataFim;
	private Integer idServicoContrato;
	
	public String getNomeServico() {
		return nomeServico;
	}
	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getValorServico() {
		return valorServico;
	}
	public void setValorServico(BigDecimal valorServico) {
		this.valorServico = valorServico;
	}
	public BigDecimal getTotalValorServico() {
		return totalValorServico;
	}
	public void setTotalValorServico(BigDecimal totalValorServico) {
		this.totalValorServico = totalValorServico;
	}
	public BigDecimal getTotalGeral() {
		return totalGeral;
	}
	public void setTotalGeral(BigDecimal totalGeral) {
		this.totalGeral = totalGeral;
	}

	public Date getDataInicio() {
		return dataInicio;
	}
	
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public Date getDataFim() {
		return dataFim;
	}
	
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	public Integer getIdServicoContrato() {
		return idServicoContrato;
	}
	
	public void setIdServicoContrato(Integer idServicoContrato) {
		this.idServicoContrato = idServicoContrato;
	}
}
