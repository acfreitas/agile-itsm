package br.com.centralit.citcorpore.metainfo.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

public class ObjetoNegocioDTO implements IDto {
	private Integer idObjetoNegocio;
	private String nomeObjetoNegocio;
	private String nomeTabelaDB;
	private String situacao;
	
	private Collection colCampos;

	public Integer getIdObjetoNegocio(){
		return this.idObjetoNegocio;
	}
	public void setIdObjetoNegocio(Integer parm){
		this.idObjetoNegocio = parm;
	}

	public String getNomeObjetoNegocio(){
		return this.nomeObjetoNegocio;
	}
	public void setNomeObjetoNegocio(String parm){
		this.nomeObjetoNegocio = parm;
	}

	public String getNomeTabelaDB(){
		return this.nomeTabelaDB;
	}
	public void setNomeTabelaDB(String parm){
		this.nomeTabelaDB = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}
	public Collection getColCampos() {
		return colCampos;
	}
	public void setColCampos(Collection colCampos) {
		this.colCampos = colCampos;
	}

}
