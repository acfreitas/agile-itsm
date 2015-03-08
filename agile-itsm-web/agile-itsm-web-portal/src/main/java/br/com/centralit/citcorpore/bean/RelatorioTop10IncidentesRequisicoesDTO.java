package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class RelatorioTop10IncidentesRequisicoesDTO implements IDto {

	private static final long serialVersionUID = -53946557530130345L;
	
	private Integer idRelatorio;
	private Date dataInicial;
	private Date dataFinal;
	private Integer idContrato;
	private Integer idPrioridade;
	private Integer idUnidade;
	private Integer idServico;
	private Integer idSolicitante;
	private String situacao;
	private Integer idTipoDemandaServico;
	private Integer idOrigem;
	private String visualizacao;
	private String formato;
	private Integer topList;
	
	public Integer getIdRelatorio() {
		return idRelatorio;
	}
	public void setIdRelatorio(Integer idRelatorio) {
		this.idRelatorio = idRelatorio;
	}
	public Date getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	public Date getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
	public Integer getIdPrioridade() {
		return idPrioridade;
	}
	public void setIdPrioridade(Integer idPrioridade) {
		this.idPrioridade = idPrioridade;
	}
	public Integer getIdUnidade() {
		return idUnidade;
	}
	public void setIdUnidade(Integer idUnidade) {
		this.idUnidade = idUnidade;
	}
	public Integer getIdServico() {
		return idServico;
	}
	public void setIdServico(Integer idServico) {
		this.idServico = idServico;
	}
	public Integer getIdSolicitante() {
		return idSolicitante;
	}
	public void setIdSolicitante(Integer idSolicitante) {
		this.idSolicitante = idSolicitante;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public Integer getIdTipoDemandaServico() {
		return idTipoDemandaServico;
	}
	public void setIdTipoDemandaServico(Integer idTipoDemandaServico) {
		this.idTipoDemandaServico = idTipoDemandaServico;
	}
	public Integer getIdOrigem() {
		return idOrigem;
	}
	public void setIdOrigem(Integer idOrigem) {
		this.idOrigem = idOrigem;
	}
	public String getVisualizacao() {
		return visualizacao;
	}
	public void setVisualizacao(String visualizacao) {
		this.visualizacao = visualizacao;
	}
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}
	public Integer getTopList() {
		return topList;
	}
	public void setTopList(Integer topList) {
		this.topList = topList;
	}
	
}
