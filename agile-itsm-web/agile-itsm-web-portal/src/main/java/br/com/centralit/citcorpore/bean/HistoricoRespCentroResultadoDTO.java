package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class HistoricoRespCentroResultadoDTO implements IDto {
	private Integer idHistoricoRespCentroResultado;
	private Integer idResponsavel;
	private Integer idCentroResultado;
	private Date dataInicio;
	private Date dataFim;
	
	private String nomeEmpregado;

	public Integer getIdHistoricoRespCentroResultado(){
		return this.idHistoricoRespCentroResultado;
	}
	public void setIdHistoricoRespCentroResultado(Integer parm){
		this.idHistoricoRespCentroResultado = parm;
	}

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

	public Date getDataInicio(){
		return this.dataInicio;
	}
	public void setDataInicio(Date parm){
		this.dataInicio = parm;
	}

	public Date getDataFim(){
		return this.dataFim;
	}
	public void setDataFim(Date parm){
		this.dataFim = parm;
	}
	public String getNomeEmpregado() {
		return nomeEmpregado;
	}
	public void setNomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
	}

}
