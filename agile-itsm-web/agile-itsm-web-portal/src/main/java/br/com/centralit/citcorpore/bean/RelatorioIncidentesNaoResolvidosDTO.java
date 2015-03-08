package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class RelatorioIncidentesNaoResolvidosDTO implements IDto {

	private static final long serialVersionUID = 1L;
	private Date dataReferencia;
	private Date periodoReferencia;
	private Integer qtdDiasAbertos;
	private Integer idContrato;
	private String contrato;
	private String funcionario;
	private String listaGrupos;
	private String listaServicos;
	private String formatoArquivoRelatorio;
	private Integer numeroSolicitacao;
	private String nomeservico;
	private String responsavel;
	private String solicitante;
	private String tipoServico;
	private String situacao;
	private String dataCriacao;
	private Integer qtdDiasAtrasos;

	public String getListaServicos() {
		return listaServicos;
	}

	public void setListaServicos(String listaServicos) {
		this.listaServicos = listaServicos;
	}

	public Integer getNumeroSolicitacao() {
		return numeroSolicitacao;
	}

	public void setNumeroSolicitacao(Integer numeroSolicitacao) {
		this.numeroSolicitacao = numeroSolicitacao;
	}

	public String getNomeservico() {
		return nomeservico;
	}

	public void setNomeservico(String nomeservico) {
		this.nomeservico = nomeservico;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public String getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(String tipoServico) {
		this.tipoServico = tipoServico;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Integer getQtdDiasAtrasos() {
		return qtdDiasAtrasos;
	}

	public void setQtdDiasAtrasos(Integer qtdDiasAtrasos) {
		this.qtdDiasAtrasos = qtdDiasAtrasos;
	}

	public Date getDataReferencia() {
		return dataReferencia;
	}

	public void setDataReferencia(Date dataReferencia) {
		this.dataReferencia = dataReferencia;
	}

	public Date getPeriodoReferencia() {
		return periodoReferencia;
	}

	public void setPeriodoReferencia(Date periodoReferencia) {
		this.periodoReferencia = periodoReferencia;
	}

	public Integer getQtdDiasAbertos() {
		return qtdDiasAbertos;
	}

	public void setQtdDiasAbertos(Integer qtdDiasAbertos) {
		this.qtdDiasAbertos = qtdDiasAbertos;
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

	public String getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
	}

	public String getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(String listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public String getFormatoArquivoRelatorio() {
		return formatoArquivoRelatorio;
	}

	public void setFormatoArquivoRelatorio(String formatoArquivoRelatorio) {
		this.formatoArquivoRelatorio = formatoArquivoRelatorio;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
