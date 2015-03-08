package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class NagiosConexaoDTO implements IDto {
	private Integer idNagiosConexao;
	private String nome;
	private String nomeJNDI;
	private String criadoPor;
	private String modificadoPor;
	private java.sql.Date dataCriacao;
	private java.sql.Date ultModificacao;

	public Integer getIdNagiosConexao(){
		return this.idNagiosConexao;
	}
	public void setIdNagiosConexao(Integer parm){
		this.idNagiosConexao = parm;
	}

	public String getNome(){
		return this.nome;
	}
	public void setNome(String parm){
		this.nome = parm;
	}

	public String getNomeJNDI(){
		return this.nomeJNDI;
	}
	public void setNomeJNDI(String parm){
		this.nomeJNDI = parm;
	}

	public String getCriadoPor(){
		return this.criadoPor;
	}
	public void setCriadoPor(String parm){
		this.criadoPor = parm;
	}

	public String getModificadoPor(){
		return this.modificadoPor;
	}
	public void setModificadoPor(String parm){
		this.modificadoPor = parm;
	}

	public java.sql.Date getDataCriacao(){
		return this.dataCriacao;
	}
	public void setDataCriacao(java.sql.Date parm){
		this.dataCriacao = parm;
	}

	public java.sql.Date getUltModificacao(){
		return this.ultModificacao;
	}
	public void setUltModificacao(java.sql.Date parm){
		this.ultModificacao = parm;
	}

}
