package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.util.List;

import br.com.citframework.dto.IDto;

public class PedidoPortalDTO implements IDto {

	private static final long serialVersionUID = 1L;
	
	private Integer idPedidoPortal;
	private Integer idEmpregado;
	private Date dataPedido;
	private Double precoTotal;
	private String status;
	private List<ServicoContratoDTO> listaServicoContrato;
	private List<SolicitacaoServicoDTO> listaSolicitacoes;

	
	public Integer getIdPedidoPortal() {
		return idPedidoPortal;
	}
	public void setIdPedidoPortal(Integer idPedidoPortal) {
		this.idPedidoPortal = idPedidoPortal;
	}
	public Integer getIdEmpregado() {
		return idEmpregado;
	}
	public void setIdEmpregado(Integer idEmpregado) {
		this.idEmpregado = idEmpregado;
	}
	public Date getDataPedido() {
		return dataPedido;
	}
	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}
	public Double getPrecoTotal() {
		return precoTotal;
	}
	public void setPrecoTotal(Double precoTotal) {
		this.precoTotal = precoTotal;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<SolicitacaoServicoDTO> getListaSolicitacoes() {
		return listaSolicitacoes;
	}
	public void setListaSolicitacoes(List<SolicitacaoServicoDTO> listaSolicitacoes) {
		this.listaSolicitacoes = listaSolicitacoes;
	}
	public List<ServicoContratoDTO> getListaServicoContrato() {
		return listaServicoContrato;
	}
	public void setListaServicoContrato(List<ServicoContratoDTO> listaServicoContrato) {
		this.listaServicoContrato = listaServicoContrato;
	}
	
}