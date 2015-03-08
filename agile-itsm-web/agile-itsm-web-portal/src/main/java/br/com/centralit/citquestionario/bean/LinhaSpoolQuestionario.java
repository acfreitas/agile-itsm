package br.com.centralit.citquestionario.bean;

import br.com.citframework.dto.IDto;

public class LinhaSpoolQuestionario implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8371985567775512010L;
	private String linha;
	private boolean generateTR;
	
	public LinhaSpoolQuestionario(){
	}
	public LinhaSpoolQuestionario(String parmValorInicio){
		this.linha = parmValorInicio;
	}
	
	public String getLinha() {
		return linha;
	}
	public void setLinha(String linha) {
		this.linha = linha;
	}
	public boolean isGenerateTR() {
		return generateTR;
	}
	public void setGenerateTR(boolean generateTR) {
		this.generateTR = generateTR;
	}
}
