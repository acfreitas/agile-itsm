package br.com.centralit.citcorpore.metainfo.bean;

import br.com.citframework.dto.IDto;

public class BibliotecasExternasDTO implements IDto {
	private Integer idBibliotecasExterna;
	private String nome;
	private String caminho;

	public Integer getIdBibliotecasExterna(){
		return this.idBibliotecasExterna;
	}
	public void setIdBibliotecasExterna(Integer parm){
		this.idBibliotecasExterna = parm;
	}

	public String getNome(){
		return this.nome;
	}
	public void setNome(String parm){
		this.nome = parm;
	}

	public String getCaminho(){
		return this.caminho;
	}
	public void setCaminho(String parm){
		this.caminho = parm;
	}

}
