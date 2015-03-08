package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class DelegacaoCentroResultadoFluxoDTO implements IDto {
	private Integer idDelegacaoCentroResultado;
	private Integer idInstanciaFluxo;

	public Integer getIdDelegacaoCentroResultado(){
		return this.idDelegacaoCentroResultado;
	}
	public void setIdDelegacaoCentroResultado(Integer parm){
		this.idDelegacaoCentroResultado = parm;
	}

	public Integer getIdInstanciaFluxo(){
		return this.idInstanciaFluxo;
	}
	public void setIdInstanciaFluxo(Integer parm){
		this.idInstanciaFluxo = parm;
	}

}
