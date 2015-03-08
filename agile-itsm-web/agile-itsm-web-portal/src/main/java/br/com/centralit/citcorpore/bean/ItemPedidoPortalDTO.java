package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ItemPedidoPortalDTO implements IDto {

	private static final long serialVersionUID = 1L;
	
	private Integer idItemPedidoPortal;
	private Integer idPedidoPortal;
	private Integer idSolicitacaoServico;
	private Double valor;
	
	public Integer getIdItemPedidoPortal() {
		return idItemPedidoPortal;
	}
	public void setIdItemPedidoPortal(Integer idItemPedidoPortal) {
		this.idItemPedidoPortal = idItemPedidoPortal;
	}
	public Integer getIdPedidoPortal() {
		return idPedidoPortal;
	}
	public void setIdPedidoPortal(Integer idPedidoPortal) {
		this.idPedidoPortal = idPedidoPortal;
	}
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}

	
	
}