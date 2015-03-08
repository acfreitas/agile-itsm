package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class AgendaAtvPeriodicasDTO implements IDto {
	private Long start;
	private Long end;
	private Integer idGrupoAtvPeriodica;
	private Integer idGrupoPesquisa;
	private Integer idEmpregado;
	
	public Long getStart() {
		return start;
	}
	public void setStart(Long start) {
		this.start = start;
	}
	public Long getEnd() {
		return end;
	}
	public void setEnd(Long end) {
		this.end = end;
	}
	public Integer getIdGrupoAtvPeriodica() {
		return idGrupoAtvPeriodica;
	}
	public void setIdGrupoAtvPeriodica(Integer idGrupoAtvPeriodica) {
		this.idGrupoAtvPeriodica = idGrupoAtvPeriodica;
	}
	public Integer getIdGrupoPesquisa() {
		return idGrupoPesquisa;
	}
	public void setIdGrupoPesquisa(Integer idGrupoPesquisa) {
		this.idGrupoPesquisa = idGrupoPesquisa;
	}
	public Integer getIdEmpregado() {
		return idEmpregado;
	}
	public void setIdEmpregado(Integer idEmpregado) {
		this.idEmpregado = idEmpregado;
	}
	

}
