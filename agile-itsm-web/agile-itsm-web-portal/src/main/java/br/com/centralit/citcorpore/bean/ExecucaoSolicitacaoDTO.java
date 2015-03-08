package br.com.centralit.citcorpore.bean;

import br.com.centralit.bpm.dto.ExecucaoFluxoDTO;

public class ExecucaoSolicitacaoDTO extends ExecucaoFluxoDTO {
	private Integer idSolicitacaoServico;
	private Integer idFase;
	private Integer prazoHH;
	private Integer prazoMM;
	private Integer seqReabertura;
	
	public Integer getIdSolicitacaoServico(){
		return this.idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer parm){
		this.idSolicitacaoServico = parm;
	}

	public Integer getIdFase(){
		return this.idFase;
	}
	public void setIdFase(Integer parm){
		this.idFase = parm;
	}

	public Integer getPrazoHH(){
		return this.prazoHH;
	}
	public void setPrazoHH(Integer parm){
		this.prazoHH = parm;
	}

	public Integer getPrazoMM(){
		return this.prazoMM;
	}
	public void setPrazoMM(Integer parm){
		this.prazoMM = parm;
	}
	public Integer getSeqReabertura() {
		return seqReabertura;
	}
	public void setSeqReabertura(Integer seqReabertura) {
		this.seqReabertura = seqReabertura;
	}

}
