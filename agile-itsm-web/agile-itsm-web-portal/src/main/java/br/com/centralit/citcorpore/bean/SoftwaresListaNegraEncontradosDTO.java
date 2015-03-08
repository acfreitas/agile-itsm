package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

/**
 * @author ronnie.lopes
 *
 */
@SuppressWarnings("serial")
public class SoftwaresListaNegraEncontradosDTO implements IDto{
	
	private Integer idsoftwareslistanegraencontrados;
	private Integer iditemconfiguracao;
	private Integer idsoftwareslistanegra;
	private String softwarelistanegraencontrado;
	private Timestamp data;
	
	public Integer getIdsoftwareslistanegraencontrados() {
		return idsoftwareslistanegraencontrados;
	}
	public void setIdsoftwareslistanegraencontrados(Integer idsoftwareslistanegraencontrados) {
		this.idsoftwareslistanegraencontrados = idsoftwareslistanegraencontrados;
	}
	public Integer getIditemconfiguracao() {
		return iditemconfiguracao;
	}
	public void setIditemconfiguracao(Integer iditemconfiguracao) {
		this.iditemconfiguracao = iditemconfiguracao;
	}
	public Integer getIdsoftwareslistanegra() {
		return idsoftwareslistanegra;
	}
	public void setIdsoftwareslistanegra(Integer idsoftwareslistanegra) {
		this.idsoftwareslistanegra = idsoftwareslistanegra;
	}
	public String getSoftwarelistanegraencontrado() {
		return softwarelistanegraencontrado;
	}
	public void setSoftwarelistanegraencontrado(String softwarelistanegraencontrado) {
		this.softwarelistanegraencontrado = softwarelistanegraencontrado;
	}
	public Timestamp getData() {
		return data;
	}
	public void setData(Timestamp data) {
		this.data = data;
	}
}
