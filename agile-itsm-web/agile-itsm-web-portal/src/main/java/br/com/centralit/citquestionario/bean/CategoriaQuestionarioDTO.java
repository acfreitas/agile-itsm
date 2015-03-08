package br.com.centralit.citquestionario.bean;

import br.com.citframework.dto.IDto;

public class CategoriaQuestionarioDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2984986456849222230L;
	private Integer idCategoriaQuestionario;
	private String nomeCategoriaQuestionario;
	
	public Integer getIdCategoriaQuestionario() {
		return idCategoriaQuestionario;
	}
	public void setIdCategoriaQuestionario(Integer idCategoriaQuestionario) {
		this.idCategoriaQuestionario = idCategoriaQuestionario;
	}
	public String getNomeCategoriaQuestionario() {
		return nomeCategoriaQuestionario;
	}
	public void setNomeCategoriaQuestionario(String nomeCategoriaQuestionario) {
		this.nomeCategoriaQuestionario = nomeCategoriaQuestionario;
	}
    
}
