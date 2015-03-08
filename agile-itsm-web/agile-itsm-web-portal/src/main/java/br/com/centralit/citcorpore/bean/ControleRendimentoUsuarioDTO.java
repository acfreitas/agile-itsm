package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ControleRendimentoUsuarioDTO implements IDto, Comparable<ControleRendimentoUsuarioDTO>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idControleRendimentoUsuario;
	private Integer idControleRendimento;
	private Integer idGrupo;
	private Integer idUsuario;
	
	private Double qtdTotalPontos;
	private String aprovacao;
	private String ano;
	private String mes;
	
	//campos auxiliares para o relatorio
	private String nomeUsuario;
	private String qtdPontosPositivos;
	private String qtdPontosNegativos;
	private String qtdItensEntregues;
	private String qtdItensRetornados;
	
	public Integer getIdControleRendimentoUsuario() {
		return idControleRendimentoUsuario;
	}
	public void setIdControleRendimentoUsuario(Integer idControleRendimentoUsuario) {
		this.idControleRendimentoUsuario = idControleRendimentoUsuario;
	}
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
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Double getQtdTotalPontos() {
		return qtdTotalPontos;
	}
	public void setQtdTotalPontos(Double qtdTotalPontos) {
		this.qtdTotalPontos = qtdTotalPontos;
	}
	public String getAprovacao() {
		return aprovacao;
	}
	public void setAprovacao(String aprovacao) {
		this.aprovacao = aprovacao;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	public String getQtdPontosPositivos() {
		return qtdPontosPositivos;
	}
	public void setQtdPontosPositivos(String qtdPontosPositivos) {
		this.qtdPontosPositivos = qtdPontosPositivos;
	}
	public String getQtdPontosNegativos() {
		return qtdPontosNegativos;
	}
	public void setQtdPontosNegativos(String qtdPontosNegativos) {
		this.qtdPontosNegativos = qtdPontosNegativos;
	}
	public String getQtdItensEntregues() {
		return qtdItensEntregues;
	}
	public void setQtdItensEntregues(String qtdItensEntregues) {
		this.qtdItensEntregues = qtdItensEntregues;
	}
	public String getQtdItensRetornados() {
		return qtdItensRetornados;
	}
	public void setQtdItensRetornados(String qtdItensRetornados) {
		this.qtdItensRetornados = qtdItensRetornados;
	}

	@Override
	public int compareTo(ControleRendimentoUsuarioDTO o) {
		return qtdTotalPontos.compareTo(o.getQtdTotalPontos());
	}
	
}
