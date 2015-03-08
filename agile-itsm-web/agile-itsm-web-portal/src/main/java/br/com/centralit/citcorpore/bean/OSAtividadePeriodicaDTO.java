package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class OSAtividadePeriodicaDTO implements IDto {
	private Integer idAtividadePeriodica;
	private Integer idOs;

	public Integer getIdOs(){
		return this.idOs;
	}
	public void setIdOs(Integer parm){
		this.idOs = parm;
	}
	/**
	 * @return the idAtividadePeriodica
	 */
	public Integer getIdAtividadePeriodica() {
		return idAtividadePeriodica;
	}
	/**
	 * @param idAtividadePeriodica the idAtividadePeriodica to set
	 */
	public void setIdAtividadePeriodica(Integer idAtividadePeriodica) {
		this.idAtividadePeriodica = idAtividadePeriodica;
	}

}
