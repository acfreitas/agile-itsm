package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class TesteCITSmartDTO implements IDto{
	
	private static final long serialVersionUID = 638687400065001805L;
	private Integer idTesteCITSmart;
	private String classe;
	private String metodo;
	private String resultado;
	private Date dataHora;
	
	public Integer getIdTesteCITSmart() {
		return idTesteCITSmart;
	}
	public void setIdTesteCITSmart(Integer idTesteCITSmart) {
		this.idTesteCITSmart = idTesteCITSmart;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public String getMetodo() {
		return metodo;
	}
	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String object) {
		this.resultado = object;
	}
	public Date getDataHora() {
		return dataHora;
	}
	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
}
