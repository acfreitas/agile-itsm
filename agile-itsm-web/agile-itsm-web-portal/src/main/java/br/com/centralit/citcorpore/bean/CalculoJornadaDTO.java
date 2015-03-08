package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class CalculoJornadaDTO implements IDto {
	private Integer idCalendario;
	private Timestamp dataHoraInicial;
	private Integer prazoHH;
	private Integer prazoMM;
	private Integer tempoDecorridoHH;
	private Integer tempoDecorridoMM;
	private Integer tempoDecorridoSS;
	
	private Timestamp dataHoraFinal;

	public CalculoJornadaDTO(Integer idCalendario, Timestamp dataHoraInicial, Integer prazoHH, Integer prazoMM) {
		this.idCalendario = idCalendario;
		this.dataHoraInicial = dataHoraInicial;
		this.prazoHH = prazoHH;
		this.prazoMM = prazoMM;
	}
	
	public CalculoJornadaDTO(Integer idCalendario, Timestamp dataHoraInicial) {
		this.idCalendario = idCalendario;
		this.dataHoraInicial = dataHoraInicial;
	}

	public Integer getIdCalendario() {
		return idCalendario;
	}

	public void setIdCalendario(Integer idCalendario) {
		this.idCalendario = idCalendario;
	}

	public Timestamp getDataHoraInicial() {
		return dataHoraInicial;
	}

	public void setDataHoraInicial(Timestamp dataHoraInicial) {
		this.dataHoraInicial = dataHoraInicial;
	}

	public Integer getPrazoHH() {
		return prazoHH;
	}

	public void setPrazoHH(Integer prazoHH) {
		this.prazoHH = prazoHH;
	}

	public Integer getPrazoMM() {
		return prazoMM;
	}

	public void setPrazoMM(Integer prazoMM) {
		this.prazoMM = prazoMM;
	}

	public Timestamp getDataHoraFinal() {
		return dataHoraFinal;
	}

	public void setDataHoraFinal(Timestamp dataHoraFinal) {
		this.dataHoraFinal = dataHoraFinal;
	}

	public Integer getTempoDecorridoHH() {
		return tempoDecorridoHH;
	}

	public void setTempoDecorridoHH(Integer tempoDecorridoHH) {
		this.tempoDecorridoHH = tempoDecorridoHH;
	}

	public Integer getTempoDecorridoMM() {
		return tempoDecorridoMM;
	}

	public void setTempoDecorridoMM(Integer tempoDecorridoMM) {
		this.tempoDecorridoMM = tempoDecorridoMM;
	}

	public Integer getTempoDecorridoSS() {
		return tempoDecorridoSS;
	}

	public void setTempoDecorridoSS(Integer tempoDecorridoSS) {
		this.tempoDecorridoSS = tempoDecorridoSS;
	}

}
