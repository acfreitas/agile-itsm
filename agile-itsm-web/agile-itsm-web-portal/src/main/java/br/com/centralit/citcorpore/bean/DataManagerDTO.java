package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class DataManagerDTO implements IDto {
	private Integer idObjetoNegocio;

	public Integer getIdObjetoNegocio() {
		return idObjetoNegocio;
	}

	public void setIdObjetoNegocio(Integer idObjetoNegocio) {
		this.idObjetoNegocio = idObjetoNegocio;
	}
}
