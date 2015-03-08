package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class AssinaturaDTO implements IDto {

	private static final long serialVersionUID = 3430643183983341226L;
	
	private Integer idAssinatura;
	private Integer idEmpregado;
	private String nomeResponsavel;
	private String papel;
	private String fase;
	private Date dataInicio;
	private Date dataFim;
	
	public Integer getIdAssinatura() {
		return idAssinatura;
	}
	public void setIdAssinatura(Integer idAssinatura) {
		this.idAssinatura = idAssinatura;
	}
	public Integer getIdEmpregado() {
		return idEmpregado;
	}
	public void setIdEmpregado(Integer idEmpregado) {
		this.idEmpregado = idEmpregado;
	}
	public String getNomeResponsavel() {
		return nomeResponsavel;
	}
	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}
	public String getPapel() {
		return papel;
	}
	public void setPapel(String papel) {
		this.papel = papel;
	}
	public String getFase() {
		return fase;
	}
	public void setFase(String fase) {
		this.fase = fase;
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
