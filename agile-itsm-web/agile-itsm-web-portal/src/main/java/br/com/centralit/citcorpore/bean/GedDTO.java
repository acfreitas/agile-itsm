/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

/**
 * @author flavio.santana
 * DTO de referencia de ged para os parametros do sistema
 */
public class GedDTO implements IDto {

	private static final long serialVersionUID = -3593079788503253157L;

	private String idAtributoGed;
	private String atributoGed;
	private String valorAtributoGed;
	private Collection<GedDTO> listGedDTO;
	
	public String getIdAtributoGed() {
		return idAtributoGed;
	}
	public String getAtributoGed() {
		return atributoGed;
	}
	public String getValorAtributoGed() {
		return valorAtributoGed;
	}
	public Collection<GedDTO> getListGedDTO() {
		return listGedDTO;
	}
	public void setIdAtributoGed(String idAtributoGed) {
		this.idAtributoGed = idAtributoGed;
	}
	public void setAtributoGed(String atributoGed) {
		this.atributoGed = atributoGed;
	}
	public void setValorAtributoGed(String valorAtributoGed) {
		this.valorAtributoGed = valorAtributoGed;
	}
	public void setListGedDTO(Collection<GedDTO> listGedDTO) {
		this.listGedDTO = listGedDTO;
	}	
}
