package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class FaixaValoresRecursoDTO implements IDto {
	private Integer idFaixaValoresRecurso;
	private Integer idRecurso;
	private Double valorInicio;
	private Double valorFim;
	private String cor;
	private String descricao;

	public String getCorInner() {
		return "<div style=\"width:100px; height:18px; background-color: " + getCor() + "\" >&nbsp;</div>";
	}	
	public Integer getIdFaixaValoresRecurso(){
		return this.idFaixaValoresRecurso;
	}
	public void setIdFaixaValoresRecurso(Integer parm){
		this.idFaixaValoresRecurso = parm;
	}

	public Integer getIdRecurso(){
		return this.idRecurso;
	}
	public void setIdRecurso(Integer parm){
		this.idRecurso = parm;
	}

	public Double getValorInicio(){
		return this.valorInicio;
	}
	public void setValorInicio(Double parm){
		this.valorInicio = parm;
	}

	public Double getValorFim(){
		return this.valorFim;
	}
	public void setValorFim(Double parm){
		this.valorFim = parm;
	}

	public String getCor(){
		return this.cor;
	}
	public void setCor(String parm){
		this.cor = parm;
	}

	public String getDescricao(){
		return this.descricao;
	}
	public void setDescricao(String parm){
		this.descricao = parm;
	}

}
