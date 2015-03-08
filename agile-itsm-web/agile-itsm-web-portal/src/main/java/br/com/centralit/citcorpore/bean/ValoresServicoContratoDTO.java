package br.com.centralit.citcorpore.bean;

import java.math.BigDecimal;
import java.sql.Date;

import br.com.citframework.dto.IDto;

public class ValoresServicoContratoDTO implements IDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idValorServicoContrato;
	private Integer idServicoContrato;
	private Integer idServico;
	private Integer idContrato;	
	private BigDecimal valorServico;
	private Date dataInicio;
	private Date dataFim;

	
	
	public Integer getIdValorServicoContrato() {
		return idValorServicoContrato;
	}
	public void setIdValorServicoContrato(Integer idValorServicoContrato) {
		this.idValorServicoContrato = idValorServicoContrato;
	}
	public Integer getIdServicoContrato() {
		return idServicoContrato;
	}
	public void setIdServicoContrato(Integer idServicoContrato) {
		this.idServicoContrato = idServicoContrato;
	}
	public BigDecimal getValorServico() {
		return valorServico;
	}
	public void setValorServico(BigDecimal valorServico) {
		this.valorServico = valorServico;
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
	public Integer getIdServico() {
		return idServico;
	}
	public void setIdServico(Integer idServico) {
		this.idServico = idServico;
	}
	
	public Integer getIdContrato() {
		return idContrato;
	}
	
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
}
