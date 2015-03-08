/**
 * 
 */
package br.com.citframework.dto;


/**
 * @author karem.ricarte
 *
 */
public class LogTabela implements IDto {
	

	private static final long serialVersionUID = 1L;
	
	private Long idLog;
	private String nomeTabela;
	
	public Long getIdLog() {
		return idLog;
	}
	public void setIdLog(Long idLog) {
		this.idLog = idLog;
	}
	public String getNomeTabela() {
		return nomeTabela;
	}
	public void setNomeTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}
	
	

}
