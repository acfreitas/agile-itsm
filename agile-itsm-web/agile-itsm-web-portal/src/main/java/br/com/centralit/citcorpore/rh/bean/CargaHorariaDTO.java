package br.com.centralit.citcorpore.rh.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class CargaHorariaDTO implements IDto {
	
	private static final long serialVersionUID = 8926245340324495564L;
	
	private Integer idCargaHoraria;
	private Integer codCargaHor;
	private Integer diaSemana;
	private Integer entrada;
	private Integer saida;
	private String descansoEm;
	private Integer turno;
	private Timestamp dataAlter;
	
	public Integer getIdCargaHoraria() {
		return idCargaHoraria;
	}
	public void setIdCargaHoraria(Integer idCargaHoraria) {
		this.idCargaHoraria = idCargaHoraria;
	}
	public Integer getCodCargaHor() {
		return codCargaHor;
	}
	public void setCodCargaHor(Integer codCargaHor) {
		this.codCargaHor = codCargaHor;
	}
	public Integer getDiaSemana() {
		return diaSemana;
	}
	public void setDiaSemana(Integer diaSemana) {
		this.diaSemana = diaSemana;
	}
	public Integer getEntrada() {
		return entrada;
	}
	public void setEntrada(Integer entrada) {
		this.entrada = entrada;
	}
	public Integer getSaida() {
		return saida;
	}
	public void setSaida(Integer saida) {
		this.saida = saida;
	}
	public String getDescansoEm() {
		return descansoEm;
	}
	public void setDescansoEm(String descansoEm) {
		this.descansoEm = descansoEm;
	}
	public Integer getTurno() {
		return turno;
	}
	public void setTurno(Integer turno) {
		this.turno = turno;
	}
	public Timestamp getDataAlter() {
		return dataAlter;
	}
	public void setDataAlter(Timestamp dataAlter) {
		this.dataAlter = dataAlter;
	}
}