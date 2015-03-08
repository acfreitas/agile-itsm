package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class CustoAdicionalProjetoDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7325677451875705649L;
	private Integer idCustoAdicional;
	private Integer idProjeto;
	private String tipoCusto;
	private Double valor;
	private String detalhamento;
	private Integer idCliente;
	private Date dataCusto;
	public String getDetalhamento() {
		return detalhamento;
	}
	public void setDetalhamento(String detalhamento) {
		this.detalhamento = detalhamento;
	}
	public Integer getIdCustoAdicional() {
		return idCustoAdicional;
	}
	public void setIdCustoAdicional(Integer idCustoAdicional) {
		this.idCustoAdicional = idCustoAdicional;
	}
	public Integer getIdProjeto() {
		return idProjeto;
	}
	public void setIdProjeto(Integer idProjeto) {
		this.idProjeto = idProjeto;
	}
	public String getTipoCusto() {
		return tipoCusto;
	}
	public void setTipoCusto(String tipoCusto) {
		this.tipoCusto = tipoCusto;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public Date getDataCusto() {
		return dataCusto;
	}
	public void setDataCusto(Date dataCusto) {
		this.dataCusto = dataCusto;
	}
}
