package br.com.centralit.citcorpore.bean;

import br.com.centralit.bpm.dto.ExecucaoFluxoDTO;

public class ExecucaoLiberacaoDTO extends ExecucaoFluxoDTO {
	private Integer idRequisicaoLiberacao;
	private Integer seqReabertura;

	public Integer getIdRequisicaoLiberacao() {
		return idRequisicaoLiberacao;
	}
	public void setIdRequisicaoLiberacao(Integer idRequisicaoLiberacao) {
		this.idRequisicaoLiberacao = idRequisicaoLiberacao;
	}
	public Integer getSeqReabertura() {
		return seqReabertura;
	}
	public void setSeqReabertura(Integer seqReabertura) {
		this.seqReabertura = seqReabertura;
	}

}
