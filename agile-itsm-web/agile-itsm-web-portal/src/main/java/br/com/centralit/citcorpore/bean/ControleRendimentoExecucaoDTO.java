package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
	@author Bruno Rodrigues
*/
public class ControleRendimentoExecucaoDTO implements IDto{

	private Integer id;
	private Integer idGrupo;
	private Integer idGrupoExecucao;
	private Integer idGrupoRelatorio;
	private Integer idPessoa;
	private Date dataInicio;
	private Date dataFim;
	
	//atributos auxiliares para setar as informações na tabela de informações do grupo
	private String tipoSla;
	private String qtdSolicitacoes;
	private String qtdTotalPontos;
	private String qtdPontosPositivos;
	private String qtdPontosNegativos;
	private String mediaRelativa;
	
	//atributos auxiliares para setar as informações na tabela de informações da pessoa
	private String nomePessoa;
	private String aprovacao;
	
	//atributos auxiliares do ControleRendimentoDTO
	private String mes;
	private String ano;
	
	private String qtdItensEntregues;
	private String qtdItensRetornados;
	
	public ControleRendimentoExecucaoDTO(){
		
	}
	
	public ControleRendimentoExecucaoDTO(String tipoSla, String qtdSolicitacoes, String qtdTotalPontos, String qtdPontosPositivos, String qtdPontosNegativos, String mediareString){
		this.tipoSla = tipoSla;
		this.qtdSolicitacoes = qtdSolicitacoes;
		this.qtdTotalPontos = qtdTotalPontos;
		this.qtdPontosPositivos = qtdPontosPositivos;
		this.qtdPontosNegativos = qtdPontosNegativos;
		this.mediaRelativa = mediareString;
	}
	
	public ControleRendimentoExecucaoDTO(String nomePessoa, String qtdTotalPontos, String aprovacao, Integer idPessoa){
		this.nomePessoa = nomePessoa;
		this.qtdTotalPontos = qtdTotalPontos;
		this.aprovacao = aprovacao;
		this.idPessoa = idPessoa;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
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
	public Integer getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(Integer idPessoa) {
		this.idPessoa = idPessoa;
	}
	public String getTipoSla() {
		return tipoSla;
	}
	public void setTipoSla(String tipoSla) {
		this.tipoSla = tipoSla;
	}
	public String getQtdSolicitacoes() {
		return qtdSolicitacoes;
	}
	public void setQtdSolicitacoes(String qtdSolicitacoes) {
		this.qtdSolicitacoes = qtdSolicitacoes;
	}

	public String getQtdTotalPontos() {
		return qtdTotalPontos;
	}

	public void setQtdTotalPontos(String qtdTotalPontos) {
		this.qtdTotalPontos = qtdTotalPontos;
	}

	public String getQtdPontosPositivos() {
		return qtdPontosPositivos;
	}

	public void setQtdPontosPositivos(String qtdPontosPositivos) {
		this.qtdPontosPositivos = qtdPontosPositivos;
	}

	public String getMediaRelativa() {
		return mediaRelativa;
	}

	public void setMediaRelativa(String mediaRelativa) {
		this.mediaRelativa = mediaRelativa;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public String getAprovacao() {
		return aprovacao;
	}

	public void setAprovacao(String aprovacao) {
		this.aprovacao = aprovacao;
	}

	public String getQtdPontosNegativos() {
		return qtdPontosNegativos;
	}

	public void setQtdPontosNegativos(String qtdPontosNegativos) {
		this.qtdPontosNegativos = qtdPontosNegativos;
	}

	public Integer getIdGrupoExecucao() {
		return idGrupoExecucao;
	}

	public void setIdGrupoExecucao(Integer idGrupoExecucao) {
		this.idGrupoExecucao = idGrupoExecucao;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
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

	public Integer getIdGrupoRelatorio() {
		return idGrupoRelatorio;
	}

	public void setIdGrupoRelatorio(Integer idGrupoRelatorio) {
		this.idGrupoRelatorio = idGrupoRelatorio;
	}

}
