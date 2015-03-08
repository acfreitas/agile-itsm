package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
 * @author Pedro
 *
 */
public class ControleContratoTreinamentoDTO implements IDto {

	private Integer idCcTreinamento;
	private Integer idControleContrato;
	private Integer idEmpregadoTreinamento;
	private Date dataCcTreinamento;
	private String nomeCcTreinamento;
	private String nomeInstrutorCcTreinamento;
	
	public String getNomeInstrutorCcTreinamento() {
		return nomeInstrutorCcTreinamento;
	}
	public void setNomeInstrutorCcTreinamento(String nomeInstrutorCcTreinamento) {
		this.nomeInstrutorCcTreinamento = nomeInstrutorCcTreinamento;
	}
	public Integer getIdCcTreinamento() {
		return idCcTreinamento;
	}
	public void setIdCcTreinamento(Integer idCcTreinamento) {
		this.idCcTreinamento = idCcTreinamento;
	}
	public Integer getIdControleContrato() {
		return idControleContrato;
	}
	public void setIdControleContrato(Integer idControleContrato) {
		this.idControleContrato = idControleContrato;
	}
	public Integer getIdEmpregadoTreinamento() {
		return idEmpregadoTreinamento;
	}
	public void setIdEmpregadoTreinamento(Integer idEmpregadoTreinamento) {
		this.idEmpregadoTreinamento = idEmpregadoTreinamento;
	}
	public Date getDataCcTreinamento() {
		return dataCcTreinamento;
	}
	public void setDataCcTreinamento(Date dataCcTreinamento) {
		this.dataCcTreinamento = dataCcTreinamento;
	}
	public String getNomeCcTreinamento() {
		return nomeCcTreinamento;
	}
	public void setNomeCcTreinamento(String nomeCcTreinamento) {
		this.nomeCcTreinamento = nomeCcTreinamento;
	}
	

   

}