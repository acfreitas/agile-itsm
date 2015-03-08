package br.com.centralit.citcorpore.bean;

//Criado por Bruno.Aquino

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO implements IDto {

	private static final long serialVersionUID = 1L;
	private Date dataInicio;
	private Date dataFim;
	private String formatoArquivoRelatorio;
	private Integer idContrato;
	private String contrato;
	private String listaServicos;
	private String nomeServico;
	private Integer totalAberto;
	private Integer qtdeSoliciatacoesCanceladasFinalizadas;
	private String porcentagemExecutada;

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
		return nomeServico;
	}

	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}

	public Integer getTotalAberto() {
		return totalAberto;
	}

	public void setTotalAberto(Integer totalAberto) {
		this.totalAberto = totalAberto;
	}

	public Integer getQtdeSoliciatacoesCanceladasFinalizadas() {
		return qtdeSoliciatacoesCanceladasFinalizadas;
	}

	public void setQtdeSoliciatacoesCanceladasFinalizadas(Integer qtdeSoliciatacoesCanceladasFinalizadas) {
		this.qtdeSoliciatacoesCanceladasFinalizadas = qtdeSoliciatacoesCanceladasFinalizadas;
	}

	public String getPorcentagemExecutada() {
		return porcentagemExecutada;
	}

	public void setPorcentagemExecutada(String porcentagemExecutada) {
		this.porcentagemExecutada = porcentagemExecutada;
	}

}
