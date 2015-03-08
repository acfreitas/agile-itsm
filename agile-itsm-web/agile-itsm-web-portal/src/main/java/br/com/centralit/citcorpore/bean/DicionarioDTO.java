package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class DicionarioDTO implements IDto {

	private Integer idDicionario;

	private Integer idLingua;

	private Integer idLingua2;

	private Integer idLingua3;

	private String sigla;

	private String nome;

	private String valor;

	private String keyIdioma;

	private String keyDescricao;

	private String dicionarioSerializados;

	private String personalizado;

	private Integer qtdCustomizados;

	private String existemItensCustomizados;

	public String getExistemItensCustomizados() {
		return existemItensCustomizados;
	}

	public void setExistemItensCustomizados(String existemItensCustomizados) {
		this.existemItensCustomizados = existemItensCustomizados;
	}

	public Integer getQtdCustomizados() {
		return qtdCustomizados;
	}

	public void setQtdCustomizados(Integer qtdCustomizados) {
		this.qtdCustomizados = qtdCustomizados;
	}

	public String getPersonalizado() {
		return personalizado;
	}

	public void setPersonalizado(String personalizado) {
		this.personalizado = personalizado;
	}

	public String getDicionarioSerializados() {
		return dicionarioSerializados;
	}

	public void setDicionarioSerializados(String dicionarioSerializados) {
		this.dicionarioSerializados = dicionarioSerializados;
	}

	public String getKeyIdioma() {
		return keyIdioma;
	}

	public void setKeyIdioma(String keyIdioma) {
		this.keyIdioma = keyIdioma;
	}

	public String getKeyDescricao() {
		return keyDescricao;
	}

	public void setKeyDescricao(String keyDescricao) {
		this.keyDescricao = keyDescricao;
	}

	public Integer getIdLingua3() {
		return idLingua3;
	}

	public void setIdLingua3(Integer idLingua3) {
		this.idLingua3 = idLingua3;
	}

	public Integer getIdLingua2() {
		return idLingua2;
	}

	public void setIdLingua2(Integer idLingua2) {
		this.idLingua2 = idLingua2;
	}

	private Collection<DicionarioDTO> listDicionario;

	/**
	 * @return the idDicionario
	 */
	public Integer getIdDicionario() {
		return idDicionario;
	}

	/**
	 * @param idDicionario
	 *            the idDicionario to set
	 */
	public void setIdDicionario(Integer idDicionario) {
		this.idDicionario = idDicionario;
	}

	/**
	 * @return the idLingua
	 */
	public Integer getIdLingua() {
		return idLingua;
	}

	/**
	 * @param idLingua
	 *            the idLingua to set
	 */
	public void setIdLingua(Integer idLingua) {
		this.idLingua = idLingua;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return the listDicionario
	 */
	public Collection<DicionarioDTO> getListDicionario() {
		return listDicionario;
	}

	/**
	 * @param listDicionario
	 *            the listDicionario to set
	 */
	public void setListDicionario(Collection<DicionarioDTO> listDicionario) {
		this.listDicionario = listDicionario;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

}
