package br.com.centralit.citcorpore.metainfo.bean;

import br.com.citframework.dto.IDto;

public class ColumnsDTO implements IDto {
	private String[] coluna;

	public String[] getColuna() {
		return coluna;
	}

	public void setColuna(String[] coluna) {
		this.coluna = coluna;
	}
}
