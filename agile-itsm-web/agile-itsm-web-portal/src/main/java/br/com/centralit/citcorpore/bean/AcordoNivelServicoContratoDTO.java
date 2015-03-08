package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class AcordoNivelServicoContratoDTO implements IDto {
	private Integer idAcordoNivelServicoContrato;
	private Integer idContrato;
	private String descricaoAcordo;
	private String detalhamentoAcordo;
	private Double valorLimite;
	private String unidadeValorLimite;
	private java.sql.Date dataInicio;
	private java.sql.Date dataFim;
	private String descricaoGlosa;
	private Integer idFormula;

	public Integer getIdAcordoNivelServicoContrato(){
		return this.idAcordoNivelServicoContrato;
	}
	public void setIdAcordoNivelServicoContrato(Integer parm){
		this.idAcordoNivelServicoContrato = parm;
	}

	public Integer getIdContrato(){
		return this.idContrato;
	}
	public void setIdContrato(Integer parm){
		this.idContrato = parm;
	}

	public String getDescricaoAcordo(){
		return this.descricaoAcordo;
	}
	public void setDescricaoAcordo(String parm){
		this.descricaoAcordo = parm;
	}

	public String getDetalhamentoAcordo(){
		return this.detalhamentoAcordo;
	}
	public void setDetalhamentoAcordo(String parm){
		this.detalhamentoAcordo = parm;
	}

	public Double getValorLimite(){
		return this.valorLimite;
	}
	public void setValorLimite(Double parm){
		this.valorLimite = parm;
	}

	public String getUnidadeValorLimite(){
		return this.unidadeValorLimite;
	}
	public void setUnidadeValorLimite(String parm){
		this.unidadeValorLimite = parm;
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

	public String getDescricaoGlosa(){
		return this.descricaoGlosa;
	}
	public void setDescricaoGlosa(String parm){
		this.descricaoGlosa = parm;
	}
	public Integer getIdFormula() {
	    return idFormula;
	}
	public void setIdFormula(Integer idFormula) {
	    this.idFormula = idFormula;
	}

}
