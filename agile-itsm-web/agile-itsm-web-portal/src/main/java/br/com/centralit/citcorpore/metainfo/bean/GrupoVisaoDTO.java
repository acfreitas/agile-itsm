package br.com.centralit.citcorpore.metainfo.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

public class GrupoVisaoDTO implements IDto {
	private Integer idGrupoVisao;
	private Integer idVisao;
	private String descricaoGrupoVisao;
	private String forma;
	private Integer ordem;
	private String situacao;
	
	private Collection colCamposVisao;

	public Integer getIdGrupoVisao(){
		return this.idGrupoVisao;
	}
	public void setIdGrupoVisao(Integer parm){
		this.idGrupoVisao = parm;
	}

	public Integer getIdVisao(){
		return this.idVisao;
	}
	public void setIdVisao(Integer parm){
		this.idVisao = parm;
	}

	public String getDescricaoGrupoVisao(){
		return this.descricaoGrupoVisao;
	}
	public void setDescricaoGrupoVisao(String parm){
		this.descricaoGrupoVisao = parm;
	}

	public String getForma(){
		return this.forma;
	}
	public void setForma(String parm){
		this.forma = parm;
	}

	public Integer getOrdem(){
		return this.ordem;
	}
	public void setOrdem(Integer parm){
		this.ordem = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}
	public Collection getColCamposVisao() {
		return colCamposVisao;
	}
	public void setColCamposVisao(Collection colCamposVisao) {
		this.colCamposVisao = colCamposVisao;
	}

}
