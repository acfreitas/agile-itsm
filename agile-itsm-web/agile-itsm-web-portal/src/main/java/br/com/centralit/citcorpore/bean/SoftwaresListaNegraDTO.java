package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author ronnie.lopes
 *
 */
@SuppressWarnings("serial")
public class SoftwaresListaNegraDTO implements IDto{
	
	private Integer idSoftwaresListaNegra;
	private String nomeSoftwaresListaNegra;
	
	public Integer getIdSoftwaresListaNegra() {
		return idSoftwaresListaNegra;
	}
	
	public void setIdSoftwaresListaNegra(Integer idSoftwaresListaNegra) {
		this.idSoftwaresListaNegra = idSoftwaresListaNegra;
	}
	
	public String getNomeSoftwaresListaNegra() {
		return nomeSoftwaresListaNegra;
	}
	
	public void setNomeSoftwaresListaNegra(String nomeSoftwaresListaNegra) {
		this.nomeSoftwaresListaNegra = nomeSoftwaresListaNegra;
	}
	
}
