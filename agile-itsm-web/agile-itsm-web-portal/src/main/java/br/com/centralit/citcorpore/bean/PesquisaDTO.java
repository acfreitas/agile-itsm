package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class PesquisaDTO implements IDto {

	private static final long serialVersionUID = 1L;
	
	private String pesquisa;

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}
    
   
}
