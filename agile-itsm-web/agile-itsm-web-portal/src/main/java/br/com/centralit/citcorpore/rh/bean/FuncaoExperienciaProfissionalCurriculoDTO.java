package br.com.centralit.citcorpore.rh.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;


public class FuncaoExperienciaProfissionalCurriculoDTO implements IDto {
	private static final long serialVersionUID = 1L;
	
	private Integer idExperienciaProfissional;
	private Integer idFuncao;
	private String nomeFuncao;
	private String descricaoFuncao;
	private Date inicioFuncao;
	private Date fimFuncao;
	
	public String getNomeFuncao() {
		return nomeFuncao;
	}
	public void setNomeFuncao(String nomeFuncao) {
		this.nomeFuncao = nomeFuncao;
	}
	public Integer getIdExperienciaProfissional() {
		return idExperienciaProfissional;
	}
	public void setIdExperienciaProfissional(Integer idExperienciaProfissional) {
		this.idExperienciaProfissional = idExperienciaProfissional;
	}
	public Integer getIdFuncao() {
		return idFuncao;
	}
	public void setIdFuncao(Integer idFuncao) {
		this.idFuncao = idFuncao;
	}
	public String getDescricaoFuncao() {
		return descricaoFuncao;
	}
	public void setDescricaoFuncao(String descricaoFuncao) {
		this.descricaoFuncao = descricaoFuncao;
	}
	public Date getInicioFuncao() {
		return inicioFuncao;
	}
	public void setInicioFuncao(Date inicioFuncao) {
		this.inicioFuncao = inicioFuncao;
	}
	public Date getFimFuncao() {
		return fimFuncao;
	}
	public void setFimFuncao(Date fimFuncao) {
		this.fimFuncao = fimFuncao;
	}
}