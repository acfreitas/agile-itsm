package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class ExcecaoCalendarioDTO implements IDto {
	private Integer idExcecaoCalendario;
	private Integer idCalendario;
	private Integer idJornada;
	private String tipo;
	private Date dataInicio;
	private Date dataTermino;

	public Integer getIdExcecaoCalendario(){
		return this.idExcecaoCalendario;
	}
	public void setIdExcecaoCalendario(Integer parm){
		this.idExcecaoCalendario = parm;
	}

	public Integer getIdCalendario(){
		return this.idCalendario;
	}
	public void setIdCalendario(Integer parm){
		this.idCalendario = parm;
	}

	public Integer getIdJornada(){
		return this.idJornada;
	}
	public void setIdJornada(Integer parm){
		this.idJornada = parm;
	}

	public String getTipo(){
		return this.tipo;
	}
	public void setTipo(String parm){
		this.tipo = parm;
	}

	public Date getDataInicio(){
		return this.dataInicio;
	}
	public void setDataInicio(Date parm){
		this.dataInicio = parm;
	}

	public Date getDataTermino(){
		return this.dataTermino;
	}
	public void setDataTermino(Date parm){
		this.dataTermino = parm;
	}

}
