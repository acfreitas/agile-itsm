package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class BICitsmartResultRotinaDTO implements IDto {

	private static final long serialVersionUID = 1L;
	private boolean resultado;
	private StringBuilder mensagem;
	private String sessionID;

	public BICitsmartResultRotinaDTO() {
		super();
		this.mensagem = new StringBuilder();
	}
	
	public boolean isResultado() {
		return resultado;
	}
	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}
	public String getMensagem() {
		if (this.mensagem==null){
			return null;
		} else {
			return mensagem.toString();
		}
	}
	public void setMensagem(String mensagem) {
		this.mensagem.delete(0, this.mensagem.length());
		this.concatMensagem(mensagem);
	}
	public void concatMensagem(String mensagem) {
		if (this.mensagem.length()>0){
			this.mensagem.append("\r\n");
		}
		this.mensagem.append(mensagem);
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
}
