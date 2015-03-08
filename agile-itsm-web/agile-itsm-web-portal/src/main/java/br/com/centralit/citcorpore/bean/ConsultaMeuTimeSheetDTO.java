package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class ConsultaMeuTimeSheetDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5568735089152913039L;
	private Date dataInicio;
	private Date dataFim;
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
}
