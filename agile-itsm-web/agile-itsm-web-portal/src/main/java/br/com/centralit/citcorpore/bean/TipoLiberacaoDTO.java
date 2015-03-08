package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class TipoLiberacaoDTO implements IDto{
	
	/**
	 * @author geber.costa
	 * Baseado em Categoria Mudança, porém sem 2 atributos : 
	 * idCategoriaLiberacaoPai
	 * idGrupoNivel1
	 */
	private static final long serialVersionUID = 4864126394598758208L;
	private Integer idTipoLiberacao;
	//Foi verificado não utilizar os atributos comentados, porém por precaução não foram excluidos
	//private Integer idCategoriaLiberacaoPai;
	private String nomeTipoLiberacao;
	private Date dataInicio;
	private Date dataFim;
	private Integer idTipoFluxo;
	private Integer idModeloEmailCriacao;
	private Integer idModeloEmailFinalizacao;
	private Integer idModeloEmailAcoes;
	//private Integer idGrupoNivel1;
	private Integer idGrupoExecutor;
	private Integer idCalendario;
	
	public Integer getIdTipoLiberacao() {
		return idTipoLiberacao;
	}
	public void setIdTipoLiberacao(Integer idTipoLiberacao) {
		this.idTipoLiberacao = idTipoLiberacao;
	}
	public String getNomeTipoLiberacao() {
		return nomeTipoLiberacao;
	}
	public void setNomeTipoLiberacao(String nomeTipoLiberacao) {
		this.nomeTipoLiberacao = nomeTipoLiberacao;
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
}
