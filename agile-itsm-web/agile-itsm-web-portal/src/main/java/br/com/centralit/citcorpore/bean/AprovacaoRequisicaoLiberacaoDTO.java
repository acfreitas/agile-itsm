package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;


public class AprovacaoRequisicaoLiberacaoDTO implements IDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idAprovacaoRequisicaoLiberacao;
	private Integer idRequisicaoLiberacao;
	private Integer idResponsavel;
	private Integer idTarefa;
	private Integer idJustificativa;
	private Timestamp dataHora;
	private String complementoJustificativa;
	private String observacoes;
	private String aprovacao;
	
	public Integer getIdAprovacaoRequisicaoLiberacao() {
		return idAprovacaoRequisicaoLiberacao;
	}
	public void setIdAprovacaoRequisicaoLiberacao(
			Integer idAprovacaoRequisicaoLiberacao) {
		this.idAprovacaoRequisicaoLiberacao = idAprovacaoRequisicaoLiberacao;
	}
	public Integer getIdRequisicaoLiberacao() {
		return idRequisicaoLiberacao;
	}
	public void setIdRequisicaoLiberacao(Integer idRequisicaoLiberacao) {
		this.idRequisicaoLiberacao = idRequisicaoLiberacao;
	}
	public Integer getIdResponsavel() {
		return idResponsavel;
	}
	public void setIdResponsavel(Integer idResponsavel) {
		this.idResponsavel = idResponsavel;
	}
	public Integer getIdTarefa() {
		return idTarefa;
	}
	public void setIdTarefa(Integer idTarefa) {
		this.idTarefa = idTarefa;
	}
	public Integer getIdJustificativa() {
		return idJustificativa;
	}
	public void setIdJustificativa(Integer idJustificativa) {
		this.idJustificativa = idJustificativa;
	}
	public Timestamp getDataHora() {
		return dataHora;
	}
	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}
	public String getComplementoJustificativa() {
		return complementoJustificativa;
	}
	public void setComplementoJustificativa(String complementoJustificativa) {
		this.complementoJustificativa = complementoJustificativa;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	public String getAprovacao() {
		return aprovacao;
	}
	public void setAprovacao(String aprovacao) {
		this.aprovacao = aprovacao;
	}
}
