package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
 * @author Pedro
 *
 */
public class ControleContratoOcorrenciaDTO implements IDto {

    private Integer idCcOcorrencia;
    private Integer idControleContrato;
    private Integer idEmpregadoOcorrencia;
    
    private Date dataCcOcorrencia;
    private String assuntoCcOcorrencia;
    private String empregadoCcOcorrencia;
    
	public String getEmpregadoCcOcorrencia() {
		return empregadoCcOcorrencia;
	}
	public void setEmpregadoCcOcorrencia(String empregadoCcOcorrencia) {
		this.empregadoCcOcorrencia = empregadoCcOcorrencia;
	}
	public Integer getIdCcOcorrencia() {
		return idCcOcorrencia;
	}
	public void setIdCcOcorrencia(Integer idCcOcorrencia) {
		this.idCcOcorrencia = idCcOcorrencia;
	}
	public Integer getIdControleContrato() {
		return idControleContrato;
	}
	public void setIdControleContrato(Integer idControleContrato) {
		this.idControleContrato = idControleContrato;
	}
	public Integer getIdEmpregadoOcorrencia() {
		return idEmpregadoOcorrencia;
	}
	public void setIdEmpregadoOcorrencia(Integer idEmpregadoOcorrencia) {
		this.idEmpregadoOcorrencia = idEmpregadoOcorrencia;
	}
	public Date getDataCcOcorrencia() {
		return dataCcOcorrencia;
	}
	public void setDataCcOcorrencia(Date dataCcOcorrencia) {
		this.dataCcOcorrencia = dataCcOcorrencia;
	}
	public String getAssuntoCcOcorrencia() {
		return assuntoCcOcorrencia;
	}
	public void setAssuntoCcOcorrencia(String assuntoCcOcorrencia) {
		this.assuntoCcOcorrencia = assuntoCcOcorrencia;
	}
    
	
   

}