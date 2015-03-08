package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class JornadaDTO implements IDto {
	
	private static final long serialVersionUID = 7148778459804164951L;
	
	private Integer idJornada;
	private String descricao;
	private String escala;
	private String considerarFeriados;
	private Integer codCargaHor;
	
	public Integer getIdJornada() {
		return idJornada;
	}
	public void setIdJornada(Integer idJornada) {
		this.idJornada = idJornada;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getEscala() {
		return escala;
	}
	public void setEscala(String escala) {
		this.escala = escala;
	}
	public String getConsiderarFeriados() {
		return considerarFeriados;
	}
	public void setConsiderarFeriados(String considerarFeriados) {
		this.considerarFeriados = considerarFeriados;
	}
	public Integer getCodCargaHor() {
		return codCargaHor;
	}
	public void setCodCargaHor(Integer codCargaHor) {
		this.codCargaHor = codCargaHor;
	}
	
}