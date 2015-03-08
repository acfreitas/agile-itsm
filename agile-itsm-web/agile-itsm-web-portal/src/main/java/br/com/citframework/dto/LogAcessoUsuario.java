/**
 * 
 */
package br.com.citframework.dto;

import java.sql.Timestamp;


/**
 * @author karem.ricarte
 *
 */
public class LogAcessoUsuario implements IDto {

	
	private static final long serialVersionUID = 1L;
	
	private Timestamp dtAcessoUsuario;
	private Integer HistAtualUsuario_idUsuario;
	private String login;
	
	
	public Timestamp getDtAcessoUsuario() {
		return dtAcessoUsuario;
	}
	public void setDtAcessoUsuario(Timestamp dtAcessoUsuario) {
		this.dtAcessoUsuario = dtAcessoUsuario;
	}
	public Integer getHistAtualUsuario_idUsuario() {
		return HistAtualUsuario_idUsuario;
	}
	public void setHistAtualUsuario_idUsuario(Integer histAtualUsuario_idUsuario) {
		HistAtualUsuario_idUsuario = histAtualUsuario_idUsuario;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	
}
