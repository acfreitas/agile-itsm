package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class BICategoriasDTO implements IDto {
	private Integer idCategoria;
	private String nomeCategoria;
	private String identificacao;
	private Integer idCategoriaPai;
	private String situacao;

	public Integer getIdCategoria(){
		return this.idCategoria;
	}
	public void setIdCategoria(Integer parm){
		this.idCategoria = parm;
	}

	public String getNomeCategoria(){
		return this.nomeCategoria;
	}
	public void setNomeCategoria(String parm){
		this.nomeCategoria = parm;
	}

	public String getIdentificacao(){
		return this.identificacao;
	}
	public void setIdentificacao(String parm){
		this.identificacao = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}
	public Integer getIdCategoriaPai() {
		return idCategoriaPai;
	}
	public void setIdCategoriaPai(Integer idCategoriaPai) {
		this.idCategoriaPai = idCategoriaPai;
	}

}
