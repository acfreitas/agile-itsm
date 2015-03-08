package br.com.centralit.citquestionario.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

public class ItemSubQuestionarioDTO implements IDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Collection colSubQuestionario;
	private Integer idItem;
	public Collection getColSubQuestionario() {
		return colSubQuestionario;
	}
	public void setColSubQuestionario(Collection colSubQuestionario) {
		this.colSubQuestionario = colSubQuestionario;
	}
	public Integer getIdItem() {
		return idItem;
	}
	public void setIdItem(Integer idItem) {
		this.idItem = idItem;
	}
}
