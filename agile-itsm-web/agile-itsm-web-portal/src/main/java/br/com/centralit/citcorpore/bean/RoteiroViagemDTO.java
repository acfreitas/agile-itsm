package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class RoteiroViagemDTO implements IDto {
	private static final long serialVersionUID = 1L;
	
	private Integer idRoteiroViagem;
	private Date dataInicio;
	private Date dataFim;
	private Integer idSolicitacaoServico;
	private Integer idIntegrante;
	private Integer origem;
	private Integer destino;
	private String nomeCidade;
	private Date ida;
	private Date volta;
	
	public Integer getIdRoteiroViagem() {
		return idRoteiroViagem;
	}
	public void setIdRoteiroViagem(Integer idRoteiroViagem) {
		this.idRoteiroViagem = idRoteiroViagem;
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
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public Integer getIdIntegrante() {
		return idIntegrante;
	}
	public void setIdIntegrante(Integer idIntegrante) {
		this.idIntegrante = idIntegrante;
	}
	public Integer getOrigem() {
		return origem;
	}
	public void setOrigem(Integer origem) {
		this.origem = origem;
	}
	public Integer getDestino() {
		return destino;
	}
	public void setDestino(Integer destino) {
		this.destino = destino;
	}
	public Date getIda() {
		return ida;
	}
	public void setIda(Date ida) {
		this.ida = ida;
	}
	public Date getVolta() {
		return volta;
	}
	public void setVolta(Date volta) {
		this.volta = volta;
	}
	public String getNomeCidade() {
		return nomeCidade;
	}
	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}
}