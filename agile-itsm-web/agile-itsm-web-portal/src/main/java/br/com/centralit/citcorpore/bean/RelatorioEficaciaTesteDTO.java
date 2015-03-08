package br.com.centralit.citcorpore.bean;

//Criado por Bruno.Aquino

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class RelatorioEficaciaTesteDTO implements IDto {

	private static final long serialVersionUID = 1L;
	private Date dataInicio;
	private Date dataFim;
	private String formatoArquivoRelatorio;
	private Integer idContrato;
	private String contrato;
	private String listaServicos;
	private String NomeServico;
	private Integer numeroSolicitacao;
	private String solicitante;
	private Date aberturaSolicitacao;
	private String situacao;

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Date getAberturaSolicitacao() {
		return aberturaSolicitacao;
	}

	public void setAberturaSolicitacao(Date aberturaSolicitacao) {
		this.aberturaSolicitacao = aberturaSolicitacao;
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

	public String getFormatoArquivoRelatorio() {
		return formatoArquivoRelatorio;
	}

	public void setFormatoArquivoRelatorio(String formatoArquivoRelatorio) {
		this.formatoArquivoRelatorio = formatoArquivoRelatorio;
	}

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public String getListaServicos() {
		return listaServicos;
	}

	public void setListaServicos(String listaServicos) {
		this.listaServicos = listaServicos;
	}

	public String getNomeServico() {
		return NomeServico;
	}

	public void setNomeServico(String nomeServico) {
		NomeServico = nomeServico;
	}

	public Integer getNumeroSolicitacao() {
		return numeroSolicitacao;
	}

	public void setNumeroSolicitacao(Integer numeroSolicitacao) {
		this.numeroSolicitacao = numeroSolicitacao;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

}
