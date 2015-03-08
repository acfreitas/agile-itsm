/**
 * 
 */
package br.com.citframework.dto;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author karem.ricarte
 * 
 */
public class LogDados implements IDto {

	/**
	 * @author karem.ricarte
	 */
	private static final long serialVersionUID = 1L;

	private Long idlog;
	private Timestamp dtAtualizacao;
	private String operacao;
	private String dados;
	private Integer idUsuario;
	private String localOrigem;
	private String nomeTabela;
	/*Usado apenas para filtro de pesquisa*/
	private Date dataInicio;
	private Date dataFim;
	
	private Timestamp dataLog;
	
	private String nomeUsuario;

	public Long getIdlog() {
		return idlog;
	}

	public void setIdlog(Long idlog) {
		this.idlog = idlog;
	}

	public Timestamp getDtAtualizacao() {
		return dtAtualizacao;
	}

	public void setDtAtualizacao(Timestamp dtAtualizacao) {
		this.dtAtualizacao = dtAtualizacao;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public String getDados() {
		return dados;
	}

	public void setDados(String dados) {
		this.dados = dados;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getLocalOrigem() {
		return localOrigem;
	}

	public void setLocalOrigem(String localOrigem) {
		this.localOrigem = localOrigem;
	}

	public String getNomeTabela() {
		return nomeTabela;
	}

	public void setNomeTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public Timestamp getDataLog() {
		return dataLog;
	}

	public void setDataLog(Timestamp dataLog) {
		this.dataLog = dataLog;
	}

}
