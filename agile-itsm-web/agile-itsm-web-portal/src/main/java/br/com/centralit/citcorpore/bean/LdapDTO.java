/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

/**
 * @author valdoilo.damasceno
 * 
 */
public class LdapDTO implements IDto {

	private static final long serialVersionUID = -3593079788503253157L;

	private String idAtributoLdap;

	private String atributoLdap;

	private String valorAtributoLdap;

	private Collection<LdapDTO> listLdapDTO;

	public String getIdAtributoLdap() {
		return idAtributoLdap;
	}

	public void setIdAtributoLdap(String idAtributoLdap) {
		this.idAtributoLdap = idAtributoLdap;
	}

	public String getAtributoLdap() {
		return atributoLdap;
	}

	public void setAtributoLdap(String atributoLdap) {
		this.atributoLdap = atributoLdap;
	}

	public String getValorAtributoLdap() {
		return valorAtributoLdap;
	}

	public void setValorAtributoLdap(String valorAtributoLdap) {
		this.valorAtributoLdap = valorAtributoLdap;
	}

	public Collection<LdapDTO> getListLdapDTO() {
		return listLdapDTO;
	}

	public void setListLdapDTO(Collection<LdapDTO> listLdapDTO) {
		this.listLdapDTO = listLdapDTO;
	}

}
