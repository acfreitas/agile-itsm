package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ResultResponsaveisDTO implements IDto {

	private static final long serialVersionUID = 1L;
	private boolean resultado;
	private String mensagem;
	private String sessionID;
	
	public boolean isResultado() {
		return resultado;
	}
	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public void concatMensagem(String mensagem) {
		if (this.mensagem == null || this.mensagem.trim().equals("")) this.mensagem = mensagem;
		else this.mensagem += "\r\n" + mensagem;
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
}
