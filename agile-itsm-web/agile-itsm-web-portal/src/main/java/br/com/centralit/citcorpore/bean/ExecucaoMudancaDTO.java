package br.com.centralit.citcorpore.bean;

import br.com.centralit.bpm.dto.ExecucaoFluxoDTO;

public class ExecucaoMudancaDTO extends ExecucaoFluxoDTO {
	private Integer idRequisicaoMudanca;
	private Integer seqReabertura;

	public Integer getIdRequisicaoMudanca() {
		return idRequisicaoMudanca;
	}
	public void setIdRequisicaoMudanca(Integer idRequisicaoMudanca) {
		this.idRequisicaoMudanca = idRequisicaoMudanca;
	}
	public Integer getSeqReabertura() {
		return seqReabertura;
	}
	public void setSeqReabertura(Integer seqReabertura) {
		this.seqReabertura = seqReabertura;
	}

}
