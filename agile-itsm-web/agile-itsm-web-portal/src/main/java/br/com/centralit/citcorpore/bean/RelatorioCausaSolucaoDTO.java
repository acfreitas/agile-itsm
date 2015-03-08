package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class RelatorioCausaSolucaoDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date dataInicio;
	private Date dataFim;
	private Integer idContrato;
	private Integer idTipoDemandaServico;
	private String situacao;
	private Integer[] idServicos;
	private Integer[] idGrupos;
	private Integer[] idCausas;
	private Integer[] idSolucoes;
	private Integer idSolicitacaoServico;
	private String descricaoCausa;
	private String descricaoCategoriaSolucao;
	private String status;
	private String nomeServico;
	private Integer numeroSolicitacoes;
	private String generationType;
	private String exibeSemCausa;
	private String exibeSemSolucao;
	
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
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
	public Integer getIdTipoDemandaServico() {
		return idTipoDemandaServico;
	}
	public void setIdTipoDemandaServico(Integer idTipoDemandaServico) {
		this.idTipoDemandaServico = idTipoDemandaServico;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public Integer[] getIdServicos() {
		return idServicos;
	}
	public void setIdServicos(Integer[] idServicos) {
		this.idServicos = idServicos;
	}
	public Integer[] getIdGrupos() {
		return idGrupos;
	}
	public void setIdGrupos(Integer[] idGrupos) {
		this.idGrupos = idGrupos;
	}
	public Integer[] getIdCausas() {
		return idCausas;
	}
	public void setIdCausas(Integer[] idCausas) {
		this.idCausas = idCausas;
	}
	public Integer[] getIdSolucoes() {
		return idSolucoes;
	}
	public void setIdSolucoes(Integer[] idSolucoes) {
		this.idSolucoes = idSolucoes;
	}
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public String getDescricaoCausa() {
		return descricaoCausa;
	}
	public void setDescricaoCausa(String descricaoCausa) {
		this.descricaoCausa = descricaoCausa;
	}
	public String getDescricaoCategoriaSolucao() {
		return descricaoCategoriaSolucao;
	}
	public void setDescricaoCategoriaSolucao(String descricaoCategoriaSolucao) {
		this.descricaoCategoriaSolucao = descricaoCategoriaSolucao;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNomeServico() {
		return nomeServico;
	}
	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}
	public Integer getNumeroSolicitacoes() {
		return numeroSolicitacoes;
	}
	public void setNumeroSolicitacoes(Integer numeroSolicitacoes) {
		this.numeroSolicitacoes = numeroSolicitacoes;
	}
	public String getGenerationType() {
		return generationType;
	}
	public void setGenerationType(String generationType) {
		this.generationType = generationType;
	}
	public String getExibeSemCausa() {
		return exibeSemCausa;
	}
	public void setExibeSemCausa(String exibeSemCausa) {
		this.exibeSemCausa = exibeSemCausa;
	}
	public String getExibeSemSolucao() {
		return exibeSemSolucao;
	}
	public void setExibeSemSolucao(String exibeSemSolucao) {
		this.exibeSemSolucao = exibeSemSolucao;
	}
}

