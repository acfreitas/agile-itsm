package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class TipoMudancaDTO implements IDto{
	
	/**
	 * @author geber.costa
	 * Baseado em Categoria Mudança, porém sem 2 atributos : 
	 * idCategoriaMudancaPai
	 * idGrupoNivel1
	 */
	private static final long serialVersionUID = 4864126394598758208L;
	private Integer idTipoMudanca;
	//Foi verificado não utilizar os atributos comentados, porém por precaução não foram excluidos
	//private Integer idCategoriaMudancaPai;
	private String nomeTipoMudanca;
	private Date dataInicio;
	private Date dataFim;
	private Integer idTipoFluxo;
	private Integer idModeloEmailCriacao;
	private Integer idModeloEmailFinalizacao;
	private Integer idModeloEmailAcoes;
	//private Integer idGrupoNivel1;
	private Integer idGrupoExecutor;
	private Integer idCalendario;
	private String exigeAprovacao;
	
	private String impacto;
	
	private String urgencia;
	
	public Integer getIdTipoMudanca() {
		return idTipoMudanca;
	}
	public void setIdTipoMudanca(Integer idTipoMudanca) {
		this.idTipoMudanca = idTipoMudanca;
	}
	public String getNomeTipoMudanca() {
		return nomeTipoMudanca;
	}
	public void setNomeTipoMudanca(String nomeTipoMudanca) {
		this.nomeTipoMudanca = nomeTipoMudanca;
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
	public Integer getIdTipoFluxo() {
		return idTipoFluxo;
	}
	public void setIdTipoFluxo(Integer idTipoFluxo) {
		this.idTipoFluxo = idTipoFluxo;
	}
	public Integer getIdModeloEmailCriacao() {
		return idModeloEmailCriacao;
	}
	public void setIdModeloEmailCriacao(Integer idModeloEmailCriacao) {
		this.idModeloEmailCriacao = idModeloEmailCriacao;
	}
	public Integer getIdModeloEmailFinalizacao() {
		return idModeloEmailFinalizacao;
	}
	public void setIdModeloEmailFinalizacao(Integer idModeloEmailFinalizacao) {
		this.idModeloEmailFinalizacao = idModeloEmailFinalizacao;
	}
	public Integer getIdModeloEmailAcoes() {
		return idModeloEmailAcoes;
	}
	public void setIdModeloEmailAcoes(Integer idModeloEmailAcoes) {
		this.idModeloEmailAcoes = idModeloEmailAcoes;
	}
	public Integer getIdGrupoExecutor() {
		return idGrupoExecutor;
	}
	public void setIdGrupoExecutor(Integer idGrupoExecutor) {
		this.idGrupoExecutor = idGrupoExecutor;
	}
	public Integer getIdCalendario() {
		return idCalendario;
	}
	public void setIdCalendario(Integer idCalendario) {
		this.idCalendario = idCalendario;
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
	public String getExigeAprovacao() {
		return exigeAprovacao;
	}
	public void setExigeAprovacao(String exigeAprovacao) {
		this.exigeAprovacao = exigeAprovacao;
	}
	
	
}
