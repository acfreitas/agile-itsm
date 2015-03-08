package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ResponsavelCentroResultadoProcessoDTO implements IDto {
	private Integer idResponsavel;
	private Integer idCentroResultado;
	private Integer idProcessoNegocio;

	public Integer getIdResponsavel(){
		return this.idResponsavel;
	}
	public void setIdResponsavel(Integer parm){
		this.idResponsavel = parm;
	}

	public Integer getIdCentroResultado(){
		return this.idCentroResultado;
	}
	public void setIdCentroResultado(Integer parm){
		this.idCentroResultado = parm;
	}

	public Integer getIdProcessoNegocio(){
		return this.idProcessoNegocio;
	}
	public void setIdProcessoNegocio(Integer parm){
		this.idProcessoNegocio = parm;
	}

}
