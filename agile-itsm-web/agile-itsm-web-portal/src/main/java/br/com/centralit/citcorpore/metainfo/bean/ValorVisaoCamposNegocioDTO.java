package br.com.centralit.citcorpore.metainfo.bean;

import br.com.citframework.dto.IDto;

public class ValorVisaoCamposNegocioDTO implements IDto {
	private Integer idValorVisaoCamposNegocio;
	private Integer idGrupoVisao;
	private Integer idCamposObjetoNegocio;
	private String valor;
	private String situacao;
	private String descricao;

	public Integer getIdValorVisaoCamposNegocio(){
		return this.idValorVisaoCamposNegocio;
	}
	public void setIdValorVisaoCamposNegocio(Integer parm){
		this.idValorVisaoCamposNegocio = parm;
	}

	public Integer getIdGrupoVisao(){
		return this.idGrupoVisao;
	}
	public void setIdGrupoVisao(Integer parm){
		this.idGrupoVisao = parm;
	}

	public Integer getIdCamposObjetoNegocio(){
		return this.idCamposObjetoNegocio;
	}
	public void setIdCamposObjetoNegocio(Integer parm){
		this.idCamposObjetoNegocio = parm;
	}

	public String getValor(){
		return this.valor;
	}
	public void setValor(String parm){
		this.valor = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getValorDescricao() {
		return valor + "#" + descricao;
	}
	public String getValorDescricaoMostrar() {
		return valor + " - " + descricao;
	}	
}
