package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class TempoAcordoNivelServicoDTO implements IDto {
	private Integer idAcordoNivelServico;
	private Integer idPrioridade;
	private Integer idFase;
	private Integer tempoHH;
	private Integer tempoMM;

	public Integer getIdAcordoNivelServico(){
		return this.idAcordoNivelServico;
	}
	public void setIdAcordoNivelServico(Integer parm){
		this.idAcordoNivelServico = parm;
	}

	public Integer getIdPrioridade(){
		return this.idPrioridade;
	}
	public void setIdPrioridade(Integer parm){
		this.idPrioridade = parm;
	}

	public Integer getIdFase(){
		return this.idFase;
	}
	public void setIdFase(Integer parm){
		this.idFase = parm;
	}

	public Integer getTempoHH(){
		return this.tempoHH;
	}
	public void setTempoHH(Integer parm){
		this.tempoHH = parm;
	}

	public Integer getTempoMM(){
		return this.tempoMM;
	}
	public void setTempoMM(Integer parm){
		this.tempoMM = parm;
	}

}
