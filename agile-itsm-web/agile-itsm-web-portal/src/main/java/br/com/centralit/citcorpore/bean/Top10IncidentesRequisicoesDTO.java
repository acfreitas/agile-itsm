package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author euler.ramos
 * Utilizado no relatório Top10 Incidentes Requisições, trará todas as informações requisitadas por este relatório e já totalizadas.
 */
public class Top10IncidentesRequisicoesDTO implements IDto {
	
	private static final long serialVersionUID = -6857315975603321041L;
	
	private Integer id;
	private String descricao;
	private Integer idServico;
	private String nomeServico;
	private Integer qtde;
	private Object listaDetalhe;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getIdServico() {
		return idServico;
	}
	public void setIdServico(Integer idServico) {
		this.idServico = idServico;
	}
	public String getNomeServico() {
		return nomeServico;
	}
	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}
	public Integer getQtde() {
		return qtde;
	}
	public void setQtde(Integer qtde) {
		this.qtde = qtde;
	}
	public Object getListaDetalhe() {
		return listaDetalhe;
	}
	public void setListaDetalhe(Object listaDetalhe) {
		this.listaDetalhe = listaDetalhe;
	}
	
}