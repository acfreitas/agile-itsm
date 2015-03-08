package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class LimiteAprovacaoAutoridadeDTO implements IDto {
	private Integer idLimiteAprovacao;
	private Integer idNivelAutoridade;

	public Integer getIdLimiteAprovacao(){
		return this.idLimiteAprovacao;
	}
	public void setIdLimiteAprovacao(Integer parm){
		this.idLimiteAprovacao = parm;
	}

	public Integer getIdNivelAutoridade(){
		return this.idNivelAutoridade;
	}
	public void setIdNivelAutoridade(Integer parm){
		this.idNivelAutoridade = parm;
	}

}
