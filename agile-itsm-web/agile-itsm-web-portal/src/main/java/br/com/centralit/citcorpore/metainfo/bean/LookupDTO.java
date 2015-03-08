package br.com.centralit.citcorpore.metainfo.bean;

import java.util.ArrayList;
import java.util.Collection;

import br.com.citframework.dto.IDto;

public class LookupDTO implements IDto {
	private Integer idCamposObjetoNegocio;
	private Integer idGrupoVisao;
	private String termoPesquisa;
	private String q;
	Collection values = new ArrayList();
	
	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public Collection getValues() {
		return values;
	}

	public void setValues(Collection values) {
		this.values = values;
	}

	public Integer getIdCamposObjetoNegocio() {
		return idCamposObjetoNegocio;
	}

	public void setIdCamposObjetoNegocio(Integer idCamposObjetoNegocio) {
		this.idCamposObjetoNegocio = idCamposObjetoNegocio;
	}

	public Integer getIdGrupoVisao() {
		return idGrupoVisao;
	}

	public void setIdGrupoVisao(Integer idGrupoVisao) {
		this.idGrupoVisao = idGrupoVisao;
	}

	public String getTermoPesquisa() {
		return termoPesquisa;
	}

	public void setTermoPesquisa(String termoPesquisa) {
		this.termoPesquisa = termoPesquisa;
	}
}
