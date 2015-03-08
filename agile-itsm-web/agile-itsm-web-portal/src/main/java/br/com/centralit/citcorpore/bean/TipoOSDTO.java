package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class TipoOSDTO implements IDto {
	private Integer idClassificacaoOS;
	private Integer idContrato;
	private String descricao;
	private String detalhamento;

	public Integer getIdClassificacaoOS(){
		return this.idClassificacaoOS;
	}
	public void setIdClassificacaoOS(Integer parm){
		this.idClassificacaoOS = parm;
	}

	public Integer getIdContrato(){
		return this.idContrato;
	}
	public void setIdContrato(Integer parm){
		this.idContrato = parm;
	}

	public String getDescricao(){
		return this.descricao;
	}
	public void setDescricao(String parm){
		this.descricao = parm;
	}

	public String getDetalhamento(){
		return this.detalhamento;
	}
	public void setDetalhamento(String parm){
		this.detalhamento = parm;
	}

}
