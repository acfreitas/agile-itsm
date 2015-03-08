package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class CausaIncidenteDTO implements IDto {
	private Integer idCausaIncidente;
	private Integer idCausaIncidentePai;
	private String descricaoCausa;
	private java.sql.Date dataInicio;
	private java.sql.Date dataFim;
	
	private Integer nivel;

	public Integer getIdCausaIncidente(){
		return this.idCausaIncidente;
	}
	public void setIdCausaIncidente(Integer parm){
		this.idCausaIncidente = parm;
	}

	public Integer getIdCausaIncidentePai(){
		return this.idCausaIncidentePai;
	}
	public void setIdCausaIncidentePai(Integer parm){
		this.idCausaIncidentePai = parm;
	}

	public String getDescricaoCausa(){
		return this.descricaoCausa;
	}
	public void setDescricaoCausa(String parm){
		this.descricaoCausa = parm;
	}
	
	public String getDescricaoCausaNivel(){
	    if (this.getNivel() == null){
		return this.descricaoCausa;
	    }
	    String str = "";
	    for (int i = 0; i < this.getNivel().intValue(); i++){
		str += "....";
	    }
	    return str + this.descricaoCausa;
	}	

	public java.sql.Date getDataInicio(){
		return this.dataInicio;
	}
	public void setDataInicio(java.sql.Date parm){
		this.dataInicio = parm;
	}

	public java.sql.Date getDataFim(){
		return this.dataFim;
	}
	public void setDataFim(java.sql.Date parm){
		this.dataFim = parm;
	}
	public Integer getNivel() {
	    return nivel;
	}
	public void setNivel(Integer nivel) {
	    this.nivel = nivel;
	}

}
