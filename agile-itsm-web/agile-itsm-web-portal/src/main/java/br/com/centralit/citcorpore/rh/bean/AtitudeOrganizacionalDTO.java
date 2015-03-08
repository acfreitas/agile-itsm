package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class AtitudeOrganizacionalDTO implements IDto {
	private Integer idAtitudeOrganizacional;
	private String descricao;
	private String detalhe;
	
	public Integer getIdAtitudeOrganizacional() {
		return idAtitudeOrganizacional;
	}
	public void setIdAtitudeOrganizacional(Integer idAtitudeOrganizacional) {
		this.idAtitudeOrganizacional = idAtitudeOrganizacional;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getDetalhe() {
		return detalhe;
	}
	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}
	
}