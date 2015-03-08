package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

/**
 * @author valdoilo.damasceno
 * 
 */
public class IndexDTO implements IDto {

	private static final long serialVersionUID = -945638009975594699L;

	private Collection<ReleaseDTO> listRelease;

	public Collection<ReleaseDTO> getListRelease() {
		return listRelease;
	}

	public void setListRelease(Collection<ReleaseDTO> listRelease) {
		this.listRelease = listRelease;
	}

}
