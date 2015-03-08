package br.com.centralit.citquestionario.bean;

import br.com.citframework.dto.IDto;

public class ListagemItemDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7915036904228171712L;
	private String nome;
	private String descricao;
	private String valor;
	
	public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
    
}
