package br.com.centralit.citcorpore.metainfo.bean;

import br.com.citframework.dto.IDto;

public class DadosAdicionaisDTO implements IDto {
	private String nome;
	private String[] dados;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String[] getDados() {
		return dados;
	}
	public void setDados(String[] dados) {
		this.dados = dados;
	}
}
