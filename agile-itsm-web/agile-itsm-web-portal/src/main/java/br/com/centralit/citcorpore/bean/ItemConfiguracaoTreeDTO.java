package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ItemConfiguracaoTreeDTO extends ItemConfiguracaoDTO implements IDto {

	private static final long serialVersionUID = 1L;
	
	private String nomeItemConfiguracao;

	public String getNomeItemConfiguracao() {
		return nomeItemConfiguracao;
	}

	public void setNomeItemConfiguracao(String nomeItemConfiguracao) {
		this.nomeItemConfiguracao = nomeItemConfiguracao;
	}

}