package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class PostDTO implements IDto{
	
	
	/**
	 * 
	*/
	private static final long serialVersionUID = 638687400065001805L;
	
	private Integer idPost;
	private String titulo;
	private String descricao;
	private String conteudo;
	private String imagem;
	private Integer idCategoriaPost;
	private Date dataInicio;
	private Date dataFim;
	
	
	public Integer getIdPost() {
		return idPost;
	}
	public void setIdPost(Integer idPost) {
		this.idPost = idPost;
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
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	public Integer getIdCategoriaPost() {
		return idCategoriaPost;
	}
	public void setIdCategoriaPost(Integer idCategoriaPost) {
		this.idCategoriaPost = idCategoriaPost;
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
	
	
	
}
