/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

/**
 * @author Carlos Santos
 * DTO para log da integração de sistemas externos
 */
public class LogIntegracaoDTO implements IDto {

	private static final long serialVersionUID = -3593079788503253157L;
	
	private static final String EXECUTADA = "S";
	private static final String ERRO = "E";

	private Integer idLogIntegracao;
	private Integer idIntegracao;
	private Timestamp dataHora;
	private String resultado;
	private String situacao;
	
	public Integer getIdLogIntegracao() {
		return idLogIntegracao;
	}
	public void setIdLogIntegracao(Integer idLogIntegracao) {
		this.idLogIntegracao = idLogIntegracao;
	}
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
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
}
