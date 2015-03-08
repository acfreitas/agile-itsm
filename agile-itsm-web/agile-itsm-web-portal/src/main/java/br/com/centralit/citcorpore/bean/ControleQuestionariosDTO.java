package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ControleQuestionariosDTO implements IDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idControleQuestionario;

	public Integer getIdControleQuestionario() {
		return idControleQuestionario;
	}

	public void setIdControleQuestionario(Integer idControleQuestionario) {
		this.idControleQuestionario = idControleQuestionario;
	}
}
