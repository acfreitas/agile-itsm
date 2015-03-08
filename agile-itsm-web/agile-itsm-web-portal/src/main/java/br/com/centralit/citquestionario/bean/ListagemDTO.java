package br.com.centralit.citquestionario.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

public class ListagemDTO implements IDto {

	/**
     * 
     */
    private static final long serialVersionUID = -832399751420919436L;
	private String nome;
	private String descricao;
	private String SQL;
	private Collection campos;
	private Collection linhas;
	
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
    public String getSQL() {
        return SQL;
    }
    public void setSQL(String sql) {
        SQL = sql;
    }
    public Collection getCampos() {
        return campos;
    }
    public void setCampos(Collection campos) {
        this.campos = campos;
    }
    public Collection getLinhas() {
        return linhas;
    }
    public void setLinhas(Collection linhas) {
        this.linhas = linhas;
    }
}
