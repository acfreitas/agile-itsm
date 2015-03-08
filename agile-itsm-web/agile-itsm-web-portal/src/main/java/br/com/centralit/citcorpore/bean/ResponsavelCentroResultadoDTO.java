package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ResponsavelCentroResultadoDTO implements IDto {
	private Integer idResponsavel;
	private Integer idCentroResultado;
	private Integer[] idProcessoNegocio;
	private Integer sequencia;
	private String nomeEmpregado;
	
	private String idProcessoNegocioStr;
	
	public Integer getIdResponsavel(){
		return this.idResponsavel;
	}
	public void setIdResponsavel(Integer parm){
		this.idResponsavel = parm;
	}

	public Integer getIdCentroResultado(){
		return this.idCentroResultado;
	}
	public void setIdCentroResultado(Integer parm){
		this.idCentroResultado = parm;
	}
	public Integer[] getIdProcessoNegocio() {
		return idProcessoNegocio;
	}
	public void setIdProcessoNegocio(Integer[] idProcessoNegocio) {
		this.idProcessoNegocio = idProcessoNegocio;
	}
	public Integer getSequencia() {
		return sequencia;
	}
	public void setSequencia(Integer sequencia) {
		this.sequencia = sequencia;
	}
	public String getNomeEmpregado() {
		return nomeEmpregado;
	}
	public void setNomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
	}
	public String getIdProcessoNegocioStr() {
		return idProcessoNegocioStr;
	}
	public void setIdProcessoNegocioStr(String idProcessoNegocioStr) {
		this.idProcessoNegocioStr = idProcessoNegocioStr;
	}

}
