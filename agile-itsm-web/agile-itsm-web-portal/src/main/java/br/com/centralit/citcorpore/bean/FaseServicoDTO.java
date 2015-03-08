package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class FaseServicoDTO implements IDto {
	private Integer idFase;
	private String nomeFase;
	private String faseCaptura;

	public Integer getIdFase(){
		return this.idFase;
	}
	public void setIdFase(Integer parm){
		this.idFase = parm;
	}

	public String getNomeFase(){
		return this.nomeFase;
	}
	public void setNomeFase(String parm){
		this.nomeFase = parm;
	}

	public String getFaseCaptura(){
		return this.faseCaptura;
	}
	public void setFaseCaptura(String parm){
		this.faseCaptura = parm;
	}

}
