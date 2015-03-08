package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class CategoriaSolucaoDTO implements IDto {
	private Integer idCategoriaSolucao;
	private Integer idCategoriaSolucaoPai;
	private String descricaoCategoriaSolucao;
	private java.sql.Date dataInicio;
	private java.sql.Date dataFim;
	
	private Integer nivel;

	public Integer getIdCategoriaSolucao(){
		return this.idCategoriaSolucao;
	}
	public void setIdCategoriaSolucao(Integer parm){
		this.idCategoriaSolucao = parm;
	}

	public Integer getIdCategoriaSolucaoPai(){
		return this.idCategoriaSolucaoPai;
	}
	public void setIdCategoriaSolucaoPai(Integer parm){
		this.idCategoriaSolucaoPai = parm;
	}

	public String getDescricaoCategoriaSolucao(){
		return this.descricaoCategoriaSolucao;
	}
	public void setDescricaoCategoriaSolucao(String parm){
		this.descricaoCategoriaSolucao = parm;
	}
	
	public String getDescricaoCategoriaNivel(){
	    if (this.getNivel() == null){
		return this.descricaoCategoriaSolucao;
	    }
	    String str = "";
	    for (int i = 0; i < this.getNivel().intValue(); i++){
		str += "....";
	    }
	    return str + this.descricaoCategoriaSolucao;
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
