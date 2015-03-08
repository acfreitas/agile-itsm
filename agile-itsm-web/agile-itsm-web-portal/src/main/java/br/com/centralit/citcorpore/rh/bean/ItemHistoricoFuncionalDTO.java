package br.com.centralit.citcorpore.rh.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
 * @author david.silva
 *
 */
public class ItemHistoricoFuncionalDTO implements IDto{

	private static final long serialVersionUID = 1L;
	
	private Integer idItemHistorico;
	private Integer idHistoricoFuncional;
	private Integer idResponsavel;
	private String titulo; 
	private String descricao;
	private String tipo;
	private Date dtCriacao;
	
	private String nomeResponsavel;
	
	public Integer getIdItemHistorico() {
		return idItemHistorico;
	}
	public void setIdItemHistorico(Integer idItemHistorico) {
		this.idItemHistorico = idItemHistorico;
	}
	public Integer getIdHistoricoFuncional() {
		return idHistoricoFuncional;
	}
	public void setIdHistoricoFuncional(Integer idHistoricoFuncional) {
		this.idHistoricoFuncional = idHistoricoFuncional;
	}
	public Integer getIdResponsavel() {
		return idResponsavel;
	}
	public void setIdResponsavel(Integer idResponsavel) {
		this.idResponsavel = idResponsavel;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDtCriacao() {
		return dtCriacao;
	}
	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}
	public String getNomeResponsavel() {
		return nomeResponsavel;
	}
	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
