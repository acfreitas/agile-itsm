package br.com.citframework.util;

import java.io.Serializable;

public class Dominio implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -842699222832633863L;
	private String codigo;
	private String descricao;
	
	
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Dominio(){
		super();
	}
	public Dominio(String codigo, String descricao) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	
	
	

}
