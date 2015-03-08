package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class ItemCfgSolicitacaoServDTO implements IDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer idSolicitacaoServico;
	private Integer idItemConfiguracao;
	private Integer idItemConfiguracaoPai;
	private String identificacao;
	private Date dataInicio;
	private Date dataFim;
	
	private String identificacaoStatus;
	
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public Integer getIdItemConfiguracao() {
		return idItemConfiguracao;
	}
	public void setIdItemConfiguracao(Integer idItemConfiguracao) {
		this.idItemConfiguracao = idItemConfiguracao;
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
	/**
	 * @return the identificacao
	 */
	public String getIdentificacao() {
		return identificacao;
	}
	/**
	 * @param identificacao the identificacao to set
	 */
	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}
	/**
	 * @return the idItemConfiguracaoPai
	 */
	public Integer getIdItemConfiguracaoPai() {
		return idItemConfiguracaoPai;
	}
	/**
	 * @param idItemConfiguracaoPai the idItemConfiguracaoPai to set
	 */
	public void setIdItemConfiguracaoPai(Integer idItemConfiguracaoPai) {
		this.idItemConfiguracaoPai = idItemConfiguracaoPai;
	}
	public String getIdentificacaoStatus() {
		return identificacaoStatus;
	}
	public void setIdentificacaoStatus(String identificacaoStatus) {
		this.identificacaoStatus = identificacaoStatus;
	}


}
