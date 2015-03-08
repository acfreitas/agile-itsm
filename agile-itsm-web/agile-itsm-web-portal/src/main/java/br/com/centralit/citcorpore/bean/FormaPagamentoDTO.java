package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class FormaPagamentoDTO implements IDto{
	
	private Integer idFormaPagamento;
	private String nomeFormaPagamento;
	private String situacao;
	
	public Integer getIdFormaPagamento() {
		return idFormaPagamento;
	}
	public void setIdFormaPagamento(Integer idFormaPagamento) {
		this.idFormaPagamento = idFormaPagamento;
	}
	public String getNomeFormaPagamento() {
		return nomeFormaPagamento;
	}
	public void setNomeFormaPagamento(String nomeFormaPagamento) {
		this.nomeFormaPagamento = nomeFormaPagamento;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	
}
