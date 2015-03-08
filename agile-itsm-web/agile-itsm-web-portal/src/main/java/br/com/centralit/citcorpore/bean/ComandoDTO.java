package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author ygor.magalhaes
 *
 */
public class ComandoDTO implements IDto {

    private Integer id;
    private String descricao;

    /**
     * @return valor do atributo id.
     */
    public Integer getId() {
	return id;
    }

    /**
     * Define valor do atributo id.
     * 
     * @param id
     */
    public void setId(Integer id) {
	this.id = id;
    }

    /**
     * @return valor do atributo descricao.
     */
    public String getDescricao() {
	return descricao;
    }

    /**
     * Define valor do atributo descricao.
     * 
     * @param descricao
     */
    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

}