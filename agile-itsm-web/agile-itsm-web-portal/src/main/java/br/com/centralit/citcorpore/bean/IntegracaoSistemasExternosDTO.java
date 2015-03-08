/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

/**
 * @author Carlos Santos
 * DTO para integração de sistemas externos
 */
public class IntegracaoSistemasExternosDTO implements IDto {

	private static final long serialVersionUID = -3593079788503253157L;
	
	public static final String NAO_INICIADA = "N";
	public static final String EXECUTADA = "P";
	public static final String ERRO = "E";

	private Integer idIntegracao;
	private Timestamp dataHora;
	private String processo;
	private String identificador;
	private String idobjeto;
	private String situacao;
	
	public Integer getIdIntegracao() {
		return idIntegracao;
	}
	public void setIdIntegracao(Integer idIntegracao) {
		this.idIntegracao = idIntegracao;
	}
	public Timestamp getDataHora() {
		return dataHora;
	}
	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}
	public String getProcesso() {
		return processo;
	}
	public void setProcesso(String processo) {
		this.processo = processo;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getIdobjeto() {
		return idobjeto;
	}
	public void setIdobjeto(String idobjeto) {
		this.idobjeto = idobjeto;
	}
	
}
