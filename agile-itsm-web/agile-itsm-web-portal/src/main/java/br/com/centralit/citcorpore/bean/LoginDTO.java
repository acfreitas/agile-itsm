package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class LoginDTO implements IDto {

	private static final long serialVersionUID = 7585270109925183221L;

	private String user;
	
	private String senha;
	
	private String login;
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
	
		if (senha.endsWith(".save") ) {
			this.senha = senha.split(".save")[0];
		} else {
			this.senha = senha;
		}
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getLogin() {
		return this.login;
	}
}
