package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ResumoProjetosDTO implements IDto {
	private String condicaoProjeto;

	public String getCondicaoProjeto() {
		return condicaoProjeto;
	}

	public void setCondicaoProjeto(String condicaoProjeto) {
		this.condicaoProjeto = condicaoProjeto;
	}
}
