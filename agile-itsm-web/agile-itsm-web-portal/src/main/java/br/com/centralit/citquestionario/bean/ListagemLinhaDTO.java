package br.com.centralit.citquestionario.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

public class ListagemLinhaDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7915036904228171712L;
	private String id;
	private String descricao;
	private Collection dados;
	
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Collection getDados() {
        return dados;
    }
    public void setDados(Collection dados) {
        this.dados = dados;
    }

}
