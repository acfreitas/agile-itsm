/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
 * @author valdoilo.damasceno
 * 
 */
public class ParametroCorporeDTO implements IDto {

	private static final long serialVersionUID = 3449929803357182887L;

	private Integer id;

	private String nome;

	private String valor;

	private Integer idEmpresa;

	private Date dataInicio;

	private Date dataFim;
	
	private String tipoDado;

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
		return nome;
	}

	/**
	 * Define valor do atributo nome.
	 * 
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return valor do atributo valor.
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * Define valor do atributo valor.
	 * 
	 * @param valor
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return valor do atributo idEmpresa.
	 */
	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	/**
	 * Define valor do atributo idEmpresa.
	 * 
	 * @param idEmpresa
	 */
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	/**
	 * @return valor do atributo dataInicio.
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * Define valor do atributo dataInicio.
	 * 
	 * @param dataInicio
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return valor do atributo dataFim.
	 */
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * Define valor do atributo dataFim.
	 * 
	 * @param dataFim
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	/**
	 * @return the tipoDado
	 */
	public String getTipoDado() {
		return tipoDado;
	}

	/**
	 * @param tipoDado the tipoDado to set
	 */
	public void setTipoDado(String tipoDado) {
		this.tipoDado = tipoDado;
	}

}
