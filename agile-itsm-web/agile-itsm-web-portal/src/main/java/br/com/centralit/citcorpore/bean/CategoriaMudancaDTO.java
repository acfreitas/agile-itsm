package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class CategoriaMudancaDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer idCategoriaMudanca;
	private Integer idCategoriaMudancaPai;
	private String nomeCategoria;
	private Date dataInicio;
	private Date dataFim;
	private Integer idTipoFluxo;
	private Integer idModeloEmailCriacao;
	private Integer idModeloEmailFinalizacao;
	private Integer idModeloEmailAcoes;
	private Integer idGrupoNivel1;
	private Integer idGrupoExecutor;
	private Integer idCalendario;	

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

	public Integer getIdGrupoNivel1() {
		return idGrupoNivel1;
	}

	public void setIdGrupoNivel1(Integer idGrupoNivel1) {
		this.idGrupoNivel1 = idGrupoNivel1;
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

	private int nivel;

	public String getNomeNivel() {
		if (this.getNomeCategoria() == null) {
			return this.nomeCategoria;
		}
		String str = "";
		for (int i = 0; i < this.getNivel(); i++) {
			str += "....";
		}
		return str + this.nomeCategoria;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public Integer getIdCategoriaMudanca() {
		return this.idCategoriaMudanca;
	}

	public void setIdCategoriaMudanca(Integer parm) {
		this.idCategoriaMudanca = parm;
	}

	public Integer getIdCategoriaMudancaPai() {
		return this.idCategoriaMudancaPai;
	}

	public void setIdCategoriaMudancaPai(Integer parm) {
		this.idCategoriaMudancaPai = parm;
	}

	public String getNomeCategoria() {
		return this.nomeCategoria;
	}

	public void setNomeCategoria(String parm) {
		this.nomeCategoria = parm;
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

}
