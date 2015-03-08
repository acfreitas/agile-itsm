/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

/**
 * @author flavio.santana
 * DTO de referencia para os parametros do sistema
 */
public class GeralDTO implements IDto {

	private static final long serialVersionUID = -3593079788503253157L;

	private String idAtributoGeral;
	private String atributoGeral;
	private String valorAtributoGeral;
	private Collection<GeralDTO> listGeralDTO;
	
	public String getIdAtributoGeral() {
		return idAtributoGeral;
	}
	public String getAtributoGeral() {
		return atributoGeral;
	}
	public String getValorAtributoGeral() {
		return valorAtributoGeral;
	}
	public Collection<GeralDTO> getListGeralDTO() {
		return listGeralDTO;
	}
	public void setIdAtributoGeral(String idAtributoGeral) {
		this.idAtributoGeral = idAtributoGeral;
	}
	public void setAtributoGeral(String atributoGeral) {
		this.atributoGeral = atributoGeral;
	}
	public void setValorAtributoGeral(String valorAtributoGeral) {
		this.valorAtributoGeral = valorAtributoGeral;
	}
	public void setListGeralDTO(Collection<GeralDTO> listGeralDTO) {
		this.listGeralDTO = listGeralDTO;
	}	
}
