package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class GrupoRecursosDTO implements IDto {
	private Integer idGrupoRecurso;
	private String nomeGrupoRecurso;
	private String situacao;
	private String deleted;

	public Integer getIdGrupoRecurso(){
		return this.idGrupoRecurso;
	}
	public void setIdGrupoRecurso(Integer parm){
		this.idGrupoRecurso = parm;
	}

	public String getNomeGrupoRecurso(){
		return this.nomeGrupoRecurso;
	}
	public void setNomeGrupoRecurso(String parm){
		this.nomeGrupoRecurso = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}

	public String getDeleted(){
		return this.deleted;
	}
	public void setDeleted(String parm){
		this.deleted = parm;
	}

}
