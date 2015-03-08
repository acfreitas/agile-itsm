package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author ygor.magalhaes
 *
 */
public class ComandoSistemaOperacionalDTO implements IDto {

    private static final long serialVersionUID = -6847753633682691321L;

    private Integer id;

    private Integer idComando;

    private Integer idSistemaOperacional;

    private String comando;

    /**
     * @return valor do atributo idComando.
     */
    public Integer getIdComando() {
	return idComando;
    }

    /**
     * Define valor do atributo idComando.
     * 
     * @param idComando
     */
    public void setIdComando(Integer idComando) {
	this.idComando = idComando;
    }

    /**
     * @return valor do atributo idSistemaOperacional.
     */
    public Integer getIdSistemaOperacional() {
	return idSistemaOperacional;
    }

    /**
     * Define valor do atributo idSistemaOperacional.
     * 
     * @param idSistemaOperacional
     */
    public void setIdSistemaOperacional(Integer idSistemaOperacional) {
	this.idSistemaOperacional = idSistemaOperacional;
    }

    /**
     * @return valor do atributo comando.
     */
    public String getComando() {
	return comando;
    }

    /**
     * Define valor do atributo comando.
     * 
     * @param comando
     */
    public void setComando(String comando) {
	this.comando = comando;
    }

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

}
