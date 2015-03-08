package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class TabFederacaoDadosDTO implements IDto {
	private String nomeTabela;
	private String chaveFinal;
	private String chaveOriginal;
	private String origem;
	private java.sql.Timestamp criacao;
	private java.sql.Timestamp ultAtualiz;

	public String getNomeTabela(){
		return this.nomeTabela;
	}
	public void setNomeTabela(String parm){
		this.nomeTabela = parm;
	}

	public String getChaveFinal(){
		return this.chaveFinal;
	}
	public void setChaveFinal(String parm){
		this.chaveFinal = parm;
	}

	public String getChaveOriginal(){
		return this.chaveOriginal;
	}
	public void setChaveOriginal(String parm){
		this.chaveOriginal = parm;
	}

	public String getOrigem(){
		return this.origem;
	}
	public void setOrigem(String parm){
		this.origem = parm;
	}
	public java.sql.Timestamp getCriacao() {
		return criacao;
	}
	public void setCriacao(java.sql.Timestamp criacao) {
		this.criacao = criacao;
	}
	public java.sql.Timestamp getUltAtualiz() {
		return ultAtualiz;
	}
	public void setUltAtualiz(java.sql.Timestamp ultAtualiz) {
		this.ultAtualiz = ultAtualiz;
	}

}
