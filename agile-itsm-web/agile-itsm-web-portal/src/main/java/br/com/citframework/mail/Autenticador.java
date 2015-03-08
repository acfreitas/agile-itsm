/*
 * Created on 29/09/2005
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.com.citframework.mail;



import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author rogerio
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Autenticador extends Authenticator {
		
	public String username = null;
	public String password = null;
	
	public Autenticador(String user, String pwd){
		username = user;
		password = pwd;
	}
	
	  protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username,password);
    }

}
