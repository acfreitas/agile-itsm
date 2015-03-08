package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

/**
 * @author valdoilo.damasceno
 * 
 */
public class ReleaseDTO implements IDto {

	private static final long serialVersionUID = 3784166762154444481L;

	private String versao;

	private Collection<String> conteudo;

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public Collection<String> getConteudo() {
		return conteudo;
	}

	public void setConteudo(Collection<String> conteudo) {
		this.conteudo = conteudo;
	}

}