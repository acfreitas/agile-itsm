package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class RelatorioSolicitacaoPorExecutanteDTO  implements IDto {

	private Integer idSolicitacaoServico;
	
	private Integer idContrato;
	
	private Date dataInicio;
	
	private Date dataFim;
		
	private String formatoArquivoRelatorio;
	
	private Integer idResponsavelAtual;
	
	private String nomeServico;
	
	private String nomeResponsavelAtual;
	
	private String situacao;

	/**
	 * Valor do top List
	 * 
	 * @author thyen.chang
	 */
	private Integer topList;

	public Integer getTopList() {
		return topList;
	}

	public void setTopList(Integer topList) {
		this.topList = topList;
	}

	/**
	 * @return the idContrato
	 */
	public Integer getIdContrato() {
		return idContrato;
	}

	/**
	 * @param idContrato the idContrato to set
	 */
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	/**
	 * @return the dataInicio
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param dataInicio the dataInicio to set
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return the dataFim
	 */
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * @param dataFim the dataFim to set
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	/**
	 * @return the formatoArquivoRelatorio
	 */
	public String getFormatoArquivoRelatorio() {
		return formatoArquivoRelatorio;
	}

	/**
	 * @param formatoArquivoRelatorio the formatoArquivoRelatorio to set
	 */
	public void setFormatoArquivoRelatorio(String formatoArquivoRelatorio) {
		this.formatoArquivoRelatorio = formatoArquivoRelatorio;
	}

	/**
	 * @return the idSolicitacaoServico
	 */
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}

	/**
	 * @param idSolicitacaoServico the idSolicitacaoServico to set
	 */
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}

	/**
	 * @return the idResponsavelAtual
	 */
	public Integer getIdResponsavelAtual() {
		return idResponsavelAtual;
	}

	/**
	 * @param idResponsavelAtual the idResponsavelAtual to set
	 */
	public void setIdResponsavelAtual(Integer idResponsavelAtual) {
		this.idResponsavelAtual = idResponsavelAtual;
	}

	/**
	 * @return the nomeServico
	 */
	public String getNomeServico() {
		return nomeServico;
	}

	/**
	 * @param nomeServico the nomeServico to set
	 */
	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}

	/**
	 * @return the nomeResponsavelAtual
	 */
	public String getNomeResponsavelAtual() {
		return nomeResponsavelAtual;
	}

	/**
	 * @param nomeResponsavelAtual the nomeResponsavelAtual to set
	 */
	public void setNomeResponsavelAtual(String nomeResponsavelAtual) {
		this.nomeResponsavelAtual = nomeResponsavelAtual;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	
	
}
