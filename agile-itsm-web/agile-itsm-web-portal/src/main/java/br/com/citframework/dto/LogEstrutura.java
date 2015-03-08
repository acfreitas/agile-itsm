/**
 * 
 */
package br.com.citframework.dto;


/**
 * @author karem.ricarte
 */
public class LogEstrutura implements IDto{
	
	
	private static final long serialVersionUID = 1L;
	
	private Long logTabela_idlog;
	private String estrutura;
	
	
	public String getEstrutura() {
		return estrutura;
	}
	public void setEstrutura(String estrutura) {
		this.estrutura = estrutura;
	}
	public Long getLogTabela_idlog() {
		return logTabela_idlog;
	}
	public void setLogTabela_idlog(Long logTabela_idlog) {
		this.logTabela_idlog = logTabela_idlog;
	}
	

	
	

}
