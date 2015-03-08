package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ImportacaoContratosDTO implements IDto {
	private static final long serialVersionUID = -7252057961936714136L;
	private Integer idContrato;
	private boolean resultado;
	private String mensagem; 
	
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
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

}
