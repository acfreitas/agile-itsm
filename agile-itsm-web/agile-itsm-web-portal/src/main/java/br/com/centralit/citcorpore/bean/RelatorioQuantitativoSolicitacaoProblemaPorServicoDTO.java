package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO implements IDto {
	
	
	private Date dataInicio;
	
	private Date dataFim;
	
	private String formatoArquivoRelatorio;
	
	private Integer idContrato;
	
	
	private String servico;
	private Integer idServico;
	
	private Integer idSolicitacaoServico;
	private Integer idProblema;
	
	private Object listaSolicitacaoServicoProblema;
	
	
	
	
	
	
	/**
	 * @return the listaSolicitacaoServicoProblema
	 */
	public Object getListaSolicitacaoServicoProblema() {
		return listaSolicitacaoServicoProblema;
	}
	/**
	 * @param listaSolicitacaoServicoProblema the listaSolicitacaoServicoProblema to set
	 */
	public void setListaSolicitacaoServicoProblema(Object listaSolicitacaoServicoProblema) {
		this.listaSolicitacaoServicoProblema = listaSolicitacaoServicoProblema;
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
	 * @return the idProblema
	 */
	public Integer getIdProblema() {
		return idProblema;
	}
	/**
	 * @param idProblema the idProblema to set
	 */
	public void setIdProblema(Integer idProblema) {
		this.idProblema = idProblema;
	}
	/**
	 * @return the servico
	 */
	public String getServico() {
		return servico;
	}
	/**
	 * @param servico the servico to set
	 */
	public void setServico(String servico) {
		this.servico = servico;
	}
	/**
	 * @return the idServico
	 */
	public Integer getIdServico() {
		return idServico;
	}
	/**
	 * @param idServico the idServico to set
	 */
	public void setIdServico(Integer idServico) {
		this.idServico = idServico;
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
	
	
	
	
	

}
