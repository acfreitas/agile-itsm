package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class RelatorioQuantitativoProblemaDTO implements IDto{
	
	private Date dataInicio;
	private Date dataFim;
	private String formatoArquivoRelatorio;
	private Integer idContrato;
	
	private String situacao;
	private Integer quantidadeSituacao;
	
	private String grupo ;
	private Integer quantidadeGrupo;
	
	private String origem;
	private Integer quantidadeOrigem;
	
	private String solicitante;
	private Integer quantidadeSolicitante;
	
	private String prioridade ;
	private Integer quantidadePrioridade;
	
	private String categoriaProblema;
	private Integer quantidadeCategoriaProblema;
	
	private String proprietario;
	private Integer quantidadeProprietario;
	
	private String impacto;
	private Integer quantidadeImpacto;
	
	private String urgencia;
	private Integer quantidadeUrgencia;
	
	private Object listaQuantidadeProblemaPorSituacao;
	private Object listaQuantidadeProblemaPorGrupo;
	private Object listaQuantidadeProblemaPorOrigem;
	private Object listaQuantidadeProblemaPorSolicitante;
	private Object listaQuantidadeProblemaPorPrioridade;
	private Object listaQuantidadeProblemaPorCategoriaProblema;
	private Object listaQuantidadeProblemaPorProprietario;
	private Object listaQuantidadeProblemaPorImpacto;
	private Object listaQuantidadeProblemaPorUrgencia;
	
	
	
	
	/**
	 * @return the urgencia
	 */
	public String getUrgencia() {
		return urgencia;
	}
	/**
	 * @param urgencia the urgencia to set
	 */
	public void setUrgencia(String urgencia) {
		this.urgencia = urgencia;
	}
	/**
	 * @return the quantidadeUrgencia
	 */
	public Integer getQuantidadeUrgencia() {
		return quantidadeUrgencia;
	}
	/**
	 * @param quantidadeUrgencia the quantidadeUrgencia to set
	 */
	public void setQuantidadeUrgencia(Integer quantidadeUrgencia) {
		this.quantidadeUrgencia = quantidadeUrgencia;
	}
	/**
	 * @return the listaQuantidadeProblemaPorUrgencia
	 */
	public Object getListaQuantidadeProblemaPorUrgencia() {
		return listaQuantidadeProblemaPorUrgencia;
	}
	/**
	 * @param listaQuantidadeProblemaPorUrgencia the listaQuantidadeProblemaPorUrgencia to set
	 */
	public void setListaQuantidadeProblemaPorUrgencia(Object listaQuantidadeProblemaPorUrgencia) {
		this.listaQuantidadeProblemaPorUrgencia = listaQuantidadeProblemaPorUrgencia;
	}
	/**
	 * @return the impacto
	 */
	public String getImpacto() {
		return impacto;
	}
	/**
	 * @param impacto the impacto to set
	 */
	public void setImpacto(String impacto) {
		this.impacto = impacto;
	}
	/**
	 * @return the quantidadeImpacto
	 */
	public Integer getQuantidadeImpacto() {
		return quantidadeImpacto;
	}
	/**
	 * @param quantidadeImpacto the quantidadeImpacto to set
	 */
	public void setQuantidadeImpacto(Integer quantidadeImpacto) {
		this.quantidadeImpacto = quantidadeImpacto;
	}
	/**
	 * @return the listaQuantidadeProblemaPorImpacto
	 */
	public Object getListaQuantidadeProblemaPorImpacto() {
		return listaQuantidadeProblemaPorImpacto;
	}
	/**
	 * @param listaQuantidadeProblemaPorImpacto the listaQuantidadeProblemaPorImpacto to set
	 */
	public void setListaQuantidadeProblemaPorImpacto(Object listaQuantidadeProblemaPorImpacto) {
		this.listaQuantidadeProblemaPorImpacto = listaQuantidadeProblemaPorImpacto;
	}
	/**
	 * @return the proprietario
	 */
	public String getProprietario() {
		return proprietario;
	}
	/**
	 * @param proprietario the proprietario to set
	 */
	public void setProprietario(String proprietario) {
		this.proprietario = proprietario;
	}
	/**
	 * @return the quantidadeProprietario
	 */
	public Integer getQuantidadeProprietario() {
		return quantidadeProprietario;
	}
	/**
	 * @param quantidadeProprietario the quantidadeProprietario to set
	 */
	public void setQuantidadeProprietario(Integer quantidadeProprietario) {
		this.quantidadeProprietario = quantidadeProprietario;
	}
	/**
	 * @return the listaQuantidadeProblemaPorProprietario
	 */
	public Object getListaQuantidadeProblemaPorProprietario() {
		return listaQuantidadeProblemaPorProprietario;
	}
	/**
	 * @param listaQuantidadeProblemaPorProprietario the listaQuantidadeProblemaPorProprietario to set
	 */
	public void setListaQuantidadeProblemaPorProprietario(Object listaQuantidadeProblemaPorProprietario) {
		this.listaQuantidadeProblemaPorProprietario = listaQuantidadeProblemaPorProprietario;
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
	/**
	 * @return the situacao
	 */
	public String getSituacao() {
		return situacao;
	}
	/**
	 * @param situacao the situacao to set
	 */
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	/**
	 * @return the quantidadeSituacao
	 */
	public Integer getQuantidadeSituacao() {
		return quantidadeSituacao;
	}
	/**
	 * @param quantidadeSituacao the quantidadeSituacao to set
	 */
	public void setQuantidadeSituacao(Integer quantidadeSituacao) {
		this.quantidadeSituacao = quantidadeSituacao;
	}
	/**
	 * @return the grupo
	 */
	public String getGrupo() {
		return grupo;
	}
	/**
	 * @param grupo the grupo to set
	 */
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	/**
	 * @return the quantidadeGrupo
	 */
	public Integer getQuantidadeGrupo() {
		return quantidadeGrupo;
	}
	/**
	 * @param quantidadeGrupo the quantidadeGrupo to set
	 */
	public void setQuantidadeGrupo(Integer quantidadeGrupo) {
		this.quantidadeGrupo = quantidadeGrupo;
	}
	/**
	 * @return the origem
	 */
	public String getOrigem() {
		return origem;
	}
	/**
	 * @param origem the origem to set
	 */
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	/**
	 * @return the quantidadeOrigem
	 */
	public Integer getQuantidadeOrigem() {
		return quantidadeOrigem;
	}
	/**
	 * @param quantidadeOrigem the quantidadeOrigem to set
	 */
	public void setQuantidadeOrigem(Integer quantidadeOrigem) {
		this.quantidadeOrigem = quantidadeOrigem;
	}
	/**
	 * @return the solicitante
	 */
	public String getSolicitante() {
		return solicitante;
	}
	/**
	 * @param solicitante the solicitante to set
	 */
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	/**
	 * @return the quantidadeSolicitante
	 */
	public Integer getQuantidadeSolicitante() {
		return quantidadeSolicitante;
	}
	/**
	 * @param quantidadeSolicitante the quantidadeSolicitante to set
	 */
	public void setQuantidadeSolicitante(Integer quantidadeSolicitante) {
		this.quantidadeSolicitante = quantidadeSolicitante;
	}
	/**
	 * @return the prioridade
	 */
	public String getPrioridade() {
		return prioridade;
	}
	/**
	 * @param prioridade the prioridade to set
	 */
	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}
	/**
	 * @return the quantidadePrioridade
	 */
	public Integer getQuantidadePrioridade() {
		return quantidadePrioridade;
	}
	/**
	 * @param quantidadePrioridade the quantidadePrioridade to set
	 */
	public void setQuantidadePrioridade(Integer quantidadePrioridade) {
		this.quantidadePrioridade = quantidadePrioridade;
	}
	/**
	 * @return the categoriaProblema
	 */
	public String getCategoriaProblema() {
		return categoriaProblema;
	}
	/**
	 * @param categoriaProblema the categoriaProblema to set
	 */
	public void setCategoriaProblema(String categoriaProblema) {
		this.categoriaProblema = categoriaProblema;
	}
	/**
	 * @return the quantidadeCategoriaProblema
	 */
	public Integer getQuantidadeCategoriaProblema() {
		return quantidadeCategoriaProblema;
	}
	/**
	 * @param quantidadeCategoriaProblema the quantidadeCategoriaProblema to set
	 */
	public void setQuantidadeCategoriaProblema(Integer quantidadeCategoriaProblema) {
		this.quantidadeCategoriaProblema = quantidadeCategoriaProblema;
	}
	/**
	 * @return the listaQuantidadeProblemaPorSituacao
	 */
	public Object getListaQuantidadeProblemaPorSituacao() {
		return listaQuantidadeProblemaPorSituacao;
	}
	/**
	 * @param listaQuantidadeProblemaPorSituacao the listaQuantidadeProblemaPorSituacao to set
	 */
	public void setListaQuantidadeProblemaPorSituacao(Object listaQuantidadeProblemaPorSituacao) {
		this.listaQuantidadeProblemaPorSituacao = listaQuantidadeProblemaPorSituacao;
	}
	/**
	 * @return the listaQuantidadeProblemaPorGrupo
	 */
	public Object getListaQuantidadeProblemaPorGrupo() {
		return listaQuantidadeProblemaPorGrupo;
	}
	/**
	 * @param listaQuantidadeProblemaPorGrupo the listaQuantidadeProblemaPorGrupo to set
	 */
	public void setListaQuantidadeProblemaPorGrupo(Object listaQuantidadeProblemaPorGrupo) {
		this.listaQuantidadeProblemaPorGrupo = listaQuantidadeProblemaPorGrupo;
	}
	/**
	 * @return the listaQuantidadeProblemaPorOrigem
	 */
	public Object getListaQuantidadeProblemaPorOrigem() {
		return listaQuantidadeProblemaPorOrigem;
	}
	/**
	 * @param listaQuantidadeProblemaPorOrigem the listaQuantidadeProblemaPorOrigem to set
	 */
	public void setListaQuantidadeProblemaPorOrigem(Object listaQuantidadeProblemaPorOrigem) {
		this.listaQuantidadeProblemaPorOrigem = listaQuantidadeProblemaPorOrigem;
	}
	/**
	 * @return the listaQuantidadeProblemaPorSolicitante
	 */
	public Object getListaQuantidadeProblemaPorSolicitante() {
		return listaQuantidadeProblemaPorSolicitante;
	}
	/**
	 * @param listaQuantidadeProblemaPorSolicitante the listaQuantidadeProblemaPorSolicitante to set
	 */
	public void setListaQuantidadeProblemaPorSolicitante(Object listaQuantidadeProblemaPorSolicitante) {
		this.listaQuantidadeProblemaPorSolicitante = listaQuantidadeProblemaPorSolicitante;
	}
	/**
	 * @return the listaQuantidadeProblemaPorPrioridade
	 */
	public Object getListaQuantidadeProblemaPorPrioridade() {
		return listaQuantidadeProblemaPorPrioridade;
	}
	/**
	 * @param listaQuantidadeProblemaPorPrioridade the listaQuantidadeProblemaPorPrioridade to set
	 */
	public void setListaQuantidadeProblemaPorPrioridade(Object listaQuantidadeProblemaPorPrioridade) {
		this.listaQuantidadeProblemaPorPrioridade = listaQuantidadeProblemaPorPrioridade;
	}
	/**
	 * @return the listaQuantidadeProblemaPorCategoriaProblema
	 */
	public Object getListaQuantidadeProblemaPorCategoriaProblema() {
		return listaQuantidadeProblemaPorCategoriaProblema;
	}
	/**
	 * @param listaQuantidadeProblemaPorCategoriaProblema the listaQuantidadeProblemaPorCategoriaProblema to set
	 */
	public void setListaQuantidadeProblemaPorCategoriaProblema(Object listaQuantidadeProblemaPorCategoriaProblema) {
		this.listaQuantidadeProblemaPorCategoriaProblema = listaQuantidadeProblemaPorCategoriaProblema;
	}


}
