package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class EmailSolicitacaoServicoDTO implements IDto{
    
	private Integer idEmail;
	private String idMessage;
    private Integer idSolicitacao;
    private String origem;
    
	public Integer getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(Integer idEmail) {
		this.idEmail = idEmail;
	}
	public String getIdMessage() {
		return idMessage;
	}
	public void setIdMessage(String idMessage) {
		this.idMessage = idMessage;
	}
	public Integer getIdSolicitacao() {
		return idSolicitacao;
	}
	public void setIdSolicitacao(Integer idSolicitacao) {
		this.idSolicitacao = idSolicitacao;
	}
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}

}
