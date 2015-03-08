package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
 * @author Pedro
 *
 */
public class ControleContratoPagamentoDTO implements IDto {

	private Integer idCcPagamento;
	private Integer idControleContrato;
	private Date dataCcPagamento;
	private Date dataAtrasoCcPagamento;
	private Integer parcelaCcPagamento;
	
	public Integer getIdCcPagamento() {
		return idCcPagamento;
	}
	public void setIdCcPagamento(Integer idCcPagamento) {
		this.idCcPagamento = idCcPagamento;
	}
	public Integer getIdControleContrato() {
		return idControleContrato;
	}
	public void setIdControleContrato(Integer idControleContrato) {
		this.idControleContrato = idControleContrato;
	}
	public Date getDataCcPagamento() {
		return dataCcPagamento;
	}
	public void setDataCcPagamento(Date dataCcPagamento) {
		this.dataCcPagamento = dataCcPagamento;
	}
	public Date getDataAtrasoCcPagamento() {
		return dataAtrasoCcPagamento;
	}
	public void setDataAtrasoCcPagamento(Date dataAtrasoCcPagamento) {
		this.dataAtrasoCcPagamento = dataAtrasoCcPagamento;
	}
	public Integer getParcelaCcPagamento() {
		return parcelaCcPagamento;
	}
	public void setParcelaCcPagamento(Integer parcelaCcPagamento) {
		this.parcelaCcPagamento = parcelaCcPagamento;
	}
	
	

}