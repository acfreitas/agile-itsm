package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class EmailAlteracaoSenhaDTO implements IDto {

	private static final long serialVersionUID = 5593259062378322864L;
	
	private String login;
	
	private String nomeEmpregado;
	
	private String novaSenha;
	
	private String link;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNomeEmpregado() {
		return nomeEmpregado;
	}

	public void setNomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getLink() {
		return this.link;
	}
}
