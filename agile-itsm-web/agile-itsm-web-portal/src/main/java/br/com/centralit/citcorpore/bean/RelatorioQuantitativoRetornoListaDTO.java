package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

public class RelatorioQuantitativoRetornoListaDTO implements IDto {
	private static final long serialVersionUID = 5769173299912237423L;
	private Collection<RelatorioQuantitativoRetornoDTO> listaPorPeriodo;
	public Collection<RelatorioQuantitativoRetornoDTO> getListaPorPeriodo() {
		return listaPorPeriodo;
	}
	public void setListaPorPeriodo(
			Collection<RelatorioQuantitativoRetornoDTO> listaPorPeriodo) {
		this.listaPorPeriodo = listaPorPeriodo;
	}
}