package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
 * @author Flávio.santana
 * 
 */
public class CategoriaPostDTO implements IDto {

    private static final long serialVersionUID = 2984986456849222230L;
    private Integer idCategoriaPost;
    private Integer idCategoriaPostPai;
    private String nomeCategoria;
    private Date dataInicio;
    private Date dataFim;
    
    
	public Integer getIdCategoriaPost() {
		return idCategoriaPost;
	}
	public void setIdCategoriaPost(Integer idCategoriaPost) {
		this.idCategoriaPost = idCategoriaPost;
	}
	public Integer getIdCategoriaPostPai() {
		return idCategoriaPostPai;
	}
	public void setIdCategoriaPostPai(Integer idCategoriaPostPai) {
		this.idCategoriaPostPai = idCategoriaPostPai;
	}
	public String getNomeCategoria() {
		return nomeCategoria;
	}
	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
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
