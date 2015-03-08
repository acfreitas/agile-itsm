package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;
@SuppressWarnings("serial")
public class ContatoRequisicaoLiberacaoDTO implements IDto {
	
	private Integer idContatoRequisicaoLiberacao;
    private String nomeContato;
    private String telefoneContato;
    private String emailContato;
    private String observacao;
    private Integer idLocalidade;
	private String ramal;
	private Integer idUnidade;
    
    
	public Integer getIdContatoRequisicaoLiberacao() {
		return idContatoRequisicaoLiberacao;
	}
	public void setIdContatoRequisicaoLiberacao(Integer idContatoRequisicaoLiberacao) {
		this.idContatoRequisicaoLiberacao = idContatoRequisicaoLiberacao;
	}
	public String getNomeContato() {
		return nomeContato;
	}
	public void setNomeContato(String nomeContato) {
		this.nomeContato = nomeContato;
	}
	public String getTelefoneContato() {
		return telefoneContato;
	}
	public void setTelefoneContato(String telefoneContato) {
		this.telefoneContato = telefoneContato;
	}
	public String getEmailContato() {
		return emailContato;
	}
	public void setEmailContato(String emailContato) {
		this.emailContato = emailContato;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Integer getIdLocalidade() {
		return idLocalidade;
	}
	public void setIdLocalidade(Integer idLocalidade) {
		this.idLocalidade = idLocalidade;
	}
	public String getRamal() {
		return ramal;
	}
	public void setRamal(String ramal) {
		this.ramal = ramal;
	}
	public Integer getIdUnidade() {
		return idUnidade;
	}
	public void setIdUnidade(Integer idUnidade) {
		this.idUnidade = idUnidade;
	}

	
}
