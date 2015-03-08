package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class DesignerWorkflowDTO implements IDto {
	private String nome;
	private String type;
	private Integer numero;
	
	private Integer numeroDecisoes;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getNumeroDecisoes() {
		return numeroDecisoes;
	}

	public void setNumeroDecisoes(Integer numeroDecisoes) {
		this.numeroDecisoes = numeroDecisoes;
	}
}
