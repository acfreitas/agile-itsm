package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class GrupoAssinaturaDTO implements IDto {

	private static final long serialVersionUID = -7166007883463244313L;
	
	private Integer idGrupoAssinatura;
	private String titulo;
	private Date dataInicio;
	private Date dataFim;

	public Integer getIdGrupoAssinatura() {
		return idGrupoAssinatura;
	}

	public void setIdGrupoAssinatura(Integer idGrupoAssinatura) {
		this.idGrupoAssinatura = idGrupoAssinatura;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

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

}
