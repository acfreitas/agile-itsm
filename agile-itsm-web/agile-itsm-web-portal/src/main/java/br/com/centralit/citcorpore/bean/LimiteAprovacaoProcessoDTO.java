package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class LimiteAprovacaoProcessoDTO implements IDto {
	private Integer idLimiteAprovacao;
	private Integer idProcessoNegocio;

	public Integer getIdLimiteAprovacao(){
		return this.idLimiteAprovacao;
	}
	public void setIdLimiteAprovacao(Integer parm){
		this.idLimiteAprovacao = parm;
	}

	public Integer getIdProcessoNegocio(){
		return this.idProcessoNegocio;
	}
	public void setIdProcessoNegocio(Integer parm){
		this.idProcessoNegocio = parm;
	}

}
