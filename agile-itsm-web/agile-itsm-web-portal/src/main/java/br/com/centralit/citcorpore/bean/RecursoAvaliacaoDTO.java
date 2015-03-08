package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class RecursoAvaliacaoDTO implements IDto{
	private Date dataInicio;
	private Date dataFim;
	private Integer idGrupoRecurso;
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public Integer getIdGrupoRecurso() {
		return idGrupoRecurso;
	}
	public void setIdGrupoRecurso(Integer idGrupoRecurso) {
		this.idGrupoRecurso = idGrupoRecurso;
	}
}
