/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

/**
 * @author flavio.santana
 * DTO de referencia de item de configuração para os parametros do sistema
 */
public class ICDTO implements IDto {

	private static final long serialVersionUID = -3593079788503253157L;

	private String idAtributoIC;
	private String atributoIC;
	private String valorAtributoIC;
	private Collection<ICDTO> listICDTO;
	
	public String getIdAtributoIC() {
		return idAtributoIC;
	}
	public String getAtributoIC() {
		return atributoIC;
	}
	public String getValorAtributoIC() {
		return valorAtributoIC;
	}
	public Collection<ICDTO> getListICDTO() {
		return listICDTO;
	}
	public void setIdAtributoIC(String idAtributoIC) {
		this.idAtributoIC = idAtributoIC;
	}
	public void setAtributoIC(String atributoIC) {
		this.atributoIC = atributoIC;
	}
	public void setValorAtributoIC(String valorAtributoIC) {
		this.valorAtributoIC = valorAtributoIC;
	}
	public void setListICDTO(Collection<ICDTO> listICDTO) {
		this.listICDTO = listICDTO;
	}	
}
