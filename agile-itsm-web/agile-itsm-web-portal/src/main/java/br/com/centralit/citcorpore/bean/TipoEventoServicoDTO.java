package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class TipoEventoServicoDTO implements IDto {
	private Integer idTipoEventoServico;
	private String nomeTipoEventoServico;

	public Integer getIdTipoEventoServico(){
		return this.idTipoEventoServico;
	}
	public void setIdTipoEventoServico(Integer parm){
		this.idTipoEventoServico = parm;
	}

	public String getNomeTipoEventoServico(){
		return this.nomeTipoEventoServico;
	}
	public void setNomeTipoEventoServico(String parm){
		this.nomeTipoEventoServico = parm;
	}

}
