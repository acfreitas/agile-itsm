package br.com.centralit.citcorpore.metainfo.bean;

import java.util.ArrayList;
import java.util.Collection;

import br.com.citframework.dto.IDto;

public class TableSearchDTO implements IDto {
	private Integer idVisao;
	private Integer idVisaoRelacionada;
	private Integer idCamposObjetoNegocio;
	private Integer idGrupoVisao;
	private String sort;
	private String order;
	private String sSearch;
	private Integer iSortCol_0;
	private String sSortDir_0;
	
	private Integer rows;
	private String load;
	private String matriz;
	private String jsonData;
	
	Collection values = new ArrayList();

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

	public Integer getIdVisao() {
		return idVisao;
	}

	public void setIdVisao(Integer idVisao) {
		this.idVisao = idVisao;
	}

	public String getSSearch() {
		return sSearch;
	}

	public void setSSearch(String sSearch) {
		this.sSearch = sSearch;
	}

	public Integer getISortCol_0() {
		return iSortCol_0;
	}

	public void setISortCol_0(Integer iSortCol_0) {
		this.iSortCol_0 = iSortCol_0;
	}

	public String getSSortDir_0() {
		return sSortDir_0;
	}

	public void setSSortDir_0(String sSortDir_0) {
		this.sSortDir_0 = sSortDir_0;
	}

	public String getLoad() {
		return load;
	}

	public void setLoad(String load) {
		this.load = load;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public Integer getIdVisaoRelacionada() {
		return idVisaoRelacionada;
	}

	public void setIdVisaoRelacionada(Integer idVisaoRelacionada) {
		this.idVisaoRelacionada = idVisaoRelacionada;
	}
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getMatriz() {
		return matriz;
	}

	public void setMatriz(String matriz) {
		this.matriz = matriz;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}	
}
