package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class VisaoPersonalizadaDTO implements IDto {
	private Integer idvisao;
	private String personalizada;
	private java.sql.Date dataModif;
	
	private String[] identifPersonalizado;
	private String[] carregar;

	public Integer getIdvisao(){
		return this.idvisao;
	}
	public void setIdvisao(Integer parm){
		this.idvisao = parm;
	}

	public String getPersonalizada(){
		return this.personalizada;
	}
	public void setPersonalizada(String parm){
		this.personalizada = parm;
	}

	public java.sql.Date getDataModif(){
		return this.dataModif;
	}
	public void setDataModif(java.sql.Date parm){
		this.dataModif = parm;
	}
	public String[] getIdentifPersonalizado() {
	    return identifPersonalizado;
	}
	public void setIdentifPersonalizado(String[] identifPersonalizado) {
	    this.identifPersonalizado = identifPersonalizado;
	}
	public String[] getCarregar() {
	    return carregar;
	}
	public void setCarregar(String[] carregar) {
	    this.carregar = carregar;
	}

}
