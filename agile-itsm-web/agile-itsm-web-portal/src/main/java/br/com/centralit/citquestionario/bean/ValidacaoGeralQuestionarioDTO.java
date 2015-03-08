package br.com.centralit.citquestionario.bean;

import br.com.citframework.dto.IDto;

public class ValidacaoGeralQuestionarioDTO implements IDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = -581444197536445647L;
	private String validacao;

	public String getValidacao() {
		if (this.validacao == null){
			this.validacao = "";
		}
		return validacao;
	}

	public void setValidacao(String validacao) {
		this.validacao = validacao;
	}
	
	public void addValidacao(String validacaoParm) {
		if (this.validacao == null){
			this.validacao = "";
		}
		this.validacao += validacaoParm;
	}
}
