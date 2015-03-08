package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class UnidadeMedidaDTO implements IDto {
	private Integer idUnidadeMedida;
	private String nomeUnidadeMedida;
	private String siglaUnidadeMedida;
	private String situacao;

	public Integer getIdUnidadeMedida(){
		return this.idUnidadeMedida;
	}
	public void setIdUnidadeMedida(Integer parm){
		this.idUnidadeMedida = parm;
	}

	public String getNomeUnidadeMedida(){
		return this.nomeUnidadeMedida;
	}
	public void setNomeUnidadeMedida(String parm){
		this.nomeUnidadeMedida = parm;
	}

	public String getSiglaUnidadeMedida(){
		return this.siglaUnidadeMedida;
	}
	public void setSiglaUnidadeMedida(String parm){
		this.siglaUnidadeMedida = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}

}
