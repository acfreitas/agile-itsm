package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class EventMonitorDTO implements IDto {
	private Integer idGrupoRecurso;
	private String nomeGrupoRecurso;

	public Integer getIdGrupoRecurso() {
		return idGrupoRecurso;
	}

	public void setIdGrupoRecurso(Integer idGrupoRecurso) {
		this.idGrupoRecurso = idGrupoRecurso;
	}

	public String getNomeGrupoRecurso() {
		return nomeGrupoRecurso;
	}

	public void setNomeGrupoRecurso(String nomeGrupoRecurso) {
		this.nomeGrupoRecurso = nomeGrupoRecurso;
	}
}
