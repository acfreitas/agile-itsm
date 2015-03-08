/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author flavio.santana
 * 
 */
public class CicloIcDto implements IDto {

	private static final long serialVersionUID = 3449929803357182887L;

	private Integer id;
	private String nomeCiclo;

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
	 * @return valor do atributo nome.
	 */
	public String getNome() {
		return nomeCiclo;
	}

	/**
	 * Define valor do atributo nome.
	 * 
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nomeCiclo = nome;
	}

}
