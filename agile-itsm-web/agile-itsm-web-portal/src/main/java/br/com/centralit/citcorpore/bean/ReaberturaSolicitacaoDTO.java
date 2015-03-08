package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class ReaberturaSolicitacaoDTO implements IDto {
	private Integer idSolicitacaoServico;
	private Integer seqReabertura;
	private Integer idResponsavel;
	private Timestamp dataHora;
	private String observacoes;

	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public Integer getSeqReabertura() {
		return seqReabertura;
	}
	public void setSeqReabertura(Integer seqReabertura) {
		this.seqReabertura = seqReabertura;
	}
	public Integer getIdResponsavel() {
		return idResponsavel;
	}
	public void setIdResponsavel(Integer idResponsavel) {
		this.idResponsavel = idResponsavel;
	}
	public Timestamp getDataHora() {
		return dataHora;
	}
	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

}
