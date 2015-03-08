package br.com.centralit.citcorpore.metainfo.bean;

import br.com.citframework.dto.IDto;

public class DataBaseMetaDadosDTO implements IDto {
	private String nomeTabela;

	public String getNomeTabela() {
		return nomeTabela;
	}

	public void setNomeTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}
}
