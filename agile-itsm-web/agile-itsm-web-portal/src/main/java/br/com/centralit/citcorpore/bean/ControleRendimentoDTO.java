package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class ControleRendimentoDTO implements IDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idControleRendimento;
	private Integer idGrupo;
	private Integer idPessoa;
	private String mesApuracao;
	private String anoApuracao;
	private Timestamp dataHoraExecucao;
	private String aprovado;
	private Integer qtdPontosPositivos;
	private Integer qtdPontosNegativos;
	private Integer qtdSolicitacoes;
	private Double qtdPontos;
	private String mediaRelativa;
	
	//campos auxiliares para o relatório
	private String nomeGrupo;
	private String qtdItensEntreguesNoPrazo;
	private String qtdItensAtrasados;
	private String numeroSolicitacoes;
	private String mediaAtraso;
	private String qtdItensSuspensos;
	
	public Integer getIdControleRendimento() {
		return idControleRendimento;
	}
	public void setIdControleRendimento(Integer idControleRendimento) {
		this.idControleRendimento = idControleRendimento;
	}
	public Integer getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}
	public Integer getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(Integer idPessoa) {
		this.idPessoa = idPessoa;
	}
	public String getMesApuracao() {
		return mesApuracao;
	}
	public void setMesApuracao(String mesApuracao) {
		this.mesApuracao = mesApuracao;
	}
	public String getAnoApuracao() {
		return anoApuracao;
	}
	public void setAnoApuracao(String anoApuracao) {
		this.anoApuracao = anoApuracao;
	}
	public Timestamp getDataHoraExecucao() {
		return dataHoraExecucao;
	}
	public void setDataHoraExecucao(Timestamp dataHoraExecucao) {
		this.dataHoraExecucao = dataHoraExecucao;
	}
	public String getAprovado() {
		return aprovado;
	}
	public void setAprovado(String aprovado) {
		this.aprovado = aprovado;
	}
	
	public Integer getQtdPontosPositivos() {
		return qtdPontosPositivos;
	}
	public void setQtdPontosPositivos(Integer qtdPontosPositivos) {
		this.qtdPontosPositivos = qtdPontosPositivos;
	}
	public Integer getQtdPontosNegativos() {
		return qtdPontosNegativos;
	}
	public void setQtdPontosNegativos(Integer qtdPontosNegativos) {
		this.qtdPontosNegativos = qtdPontosNegativos;
	}
	public Double getQtdPontos() {
		return qtdPontos;
	}
	public void setQtdPontos(Double qtdPontos) {
		this.qtdPontos = qtdPontos;
	}
	public String getMediaRelativa() {
		return mediaRelativa;
	}
	public void setMediaRelativa(String mediaRelativa) {
		this.mediaRelativa = mediaRelativa;
	}
	public Integer getQtdSolicitacoes() {
		return qtdSolicitacoes;
	}
	public void setQtdSolicitacoes(Integer qtdSolicitacoes) {
		this.qtdSolicitacoes = qtdSolicitacoes;
	}
	public String getNomeGrupo() {
		return nomeGrupo;
	}
	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}
	public String getQtdItensEntreguesNoPrazo() {
		return qtdItensEntreguesNoPrazo;
	}
	public void setQtdItensEntreguesNoPrazo(String qtdItensEntreguesNoPrazo) {
		this.qtdItensEntreguesNoPrazo = qtdItensEntreguesNoPrazo;
	}
	public String getQtdItensAtrasados() {
		return qtdItensAtrasados;
	}
	public void setQtdItensAtrasados(String qtdItensAtrasados) {
		this.qtdItensAtrasados = qtdItensAtrasados;
	}
	public String getNumeroSolicitacoes() {
		return numeroSolicitacoes;
	}
	public void setNumeroSolicitacoes(String numeroSolicitacoes) {
		this.numeroSolicitacoes = numeroSolicitacoes;
	}
	public String getMediaAtraso() {
		return mediaAtraso;
	}
	public void setMediaAtraso(String mediaAtraso) {
		this.mediaAtraso = mediaAtraso;
	}
	public String getQtdItensSuspensos() {
		return qtdItensSuspensos;
	}
	public void setQtdItensSuspensos(String qtdItensSuspensos) {
		this.qtdItensSuspensos = qtdItensSuspensos;
	}
	
}
