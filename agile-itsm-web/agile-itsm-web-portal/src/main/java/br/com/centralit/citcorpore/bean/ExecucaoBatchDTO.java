package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class ExecucaoBatchDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7081344075951097468L;
	private Integer idExecucaoBatch;
	private Integer idProcessamentoBatch;
	private String conteudo;
	private Timestamp dataHora;
	public Integer getIdExecucaoBatch() {
		return idExecucaoBatch;
	}
	public void setIdExecucaoBatch(Integer idExecucaoBatch) {
		this.idExecucaoBatch = idExecucaoBatch;
	}
	public Integer getIdProcessamentoBatch() {
		return idProcessamentoBatch;
	}
	public void setIdProcessamentoBatch(Integer idProcessamentoBatch) {
		this.idProcessamentoBatch = idProcessamentoBatch;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	public Timestamp getDataHora() {
		return dataHora;
	}
	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}
