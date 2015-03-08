/**
 * CentralIT - CITCorpore
 *
 */
package br.com.centralit.citcorpore.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.DateAdapter;

/**
 * Bean de Valor.
 * 
 * @author valdoilo.damasceno
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Valor") 
public class ValorDTO implements IDto {

    public ValorDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ValorDTO(String valorStr) {
		super();
		this.valorStr = valorStr;
	}

	private static final long serialVersionUID = 6983999542590171032L;

    private Integer idValor;
    private Integer idItemConfiguracao;
    private Integer idCaracteristica;
    private String nomeCaracteristica;
    private Integer idBaseItemConfiguracao;
    private String valorStr;
    private String valorLongo;
    private Double valorDecimal;

    @XmlElement(name = "valorDate")
	@XmlJavaTypeAdapter(DateAdapter.class)	
    private java.sql.Date valorDate;    

    private String tag;     
    private String tagtipoitemconfiguracao;

    /**
     * @return valor do atributo tag.
     */
    public String getTag() {
        return tag;
    }

    /**
     * Define valor do atributo tag.
     *
     * @param tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @return valor do atributo idValor.
     */
    public Integer getIdValor() {
	return idValor;
    }

    /**
     * Define valor do atributo idValor.
     * 
     * @param idValor
     */
    public void setIdValor(Integer idValor) {
	this.idValor = idValor;
    }

    /**
     * @return valor do atributo idItemConfiguracao.
     */
    public Integer getIdItemConfiguracao() {
	return idItemConfiguracao;
    }

    /**
     * Define valor do atributo idItemConfiguracao.
     * 
     * @param idItemConfiguracao
     */
    public void setIdItemConfiguracao(Integer idItemConfiguracao) {
	this.idItemConfiguracao = idItemConfiguracao;
    }

    /**
     * @return valor do atributo idCaracteristica.
     */
    public Integer getIdCaracteristica() {
	return idCaracteristica;
    }

    /**
     * Define valor do atributo idCaracteristica.
     * 
     * @param idCaracteristica
     */
    public void setIdCaracteristica(Integer idCaracteristica) {
	this.idCaracteristica = idCaracteristica;
    }

    /**
     * @return valor do atributo idBaseItemConfiguracao.
     */
    public Integer getIdBaseItemConfiguracao() {
	return idBaseItemConfiguracao;
    }

    /**
     * Define valor do atributo idBaseItemConfiguracao.
     * 
     * @param idBaseItemConfiguracao
     */
    public void setIdBaseItemConfiguracao(Integer idBaseItemConfiguracao) {
	this.idBaseItemConfiguracao = idBaseItemConfiguracao;
    }

    /**
     * @return valor do atributo valorStr.
     */
    public String getValorStr() {
	return valorStr;
    }

    /**
     * Define valor do atributo valorStr.
     * 
     * @param valorStr
     */
    public void setValorStr(String valorStr) {
	if(valorStr == null)
	    valorStr = "";
	this.valorStr = valorStr;
    }

    /**
     * @return valor do atributo valorLongo.
     */
    public String getValorLongo() {
	return valorLongo;
    }

    /**
     * Define valor do atributo valorLongo.
     * 
     * @param valorLongo
     */
    public void setValorLongo(String valorLongo) {
	this.valorLongo = valorLongo;
    }

    /**
     * @return valor do atributo valorDecimal.
     */
    public Double getValorDecimal() {
	return valorDecimal;
    }

    /**
     * Define valor do atributo valorDecimal.
     * 
     * @param valorDecimal
     */
    public void setValorDecimal(Double valorDecimal) {
	this.valorDecimal = valorDecimal;
    }

    /**
     * @return valor do atributo valorDate.
     */
    public java.sql.Date getValorDate() {
	return valorDate;
    }

    /**
     * Define valor do atributo valorDate.
     * 
     * @param valorDate
     */
    public void setValorDate(java.sql.Date valorDate) {
	this.valorDate = valorDate;
    }

    /**
     * @return valor do atributo nomeCaracteristica.
     */
    public String getNomeCaracteristica() {
	return nomeCaracteristica;
    }

    /**
     * Define valor do atributo nomeCaracteristica.
     * 
     * @param nomeCaracteristica
     */
    public void setNomeCaracteristica(String nomeCaracteristica) {
	this.nomeCaracteristica = nomeCaracteristica;
    }

    /**
     * @return the tagtipoitemconfiguracao
     */
    public String getTagtipoitemconfiguracao() {
        return tagtipoitemconfiguracao;
    }

    /**
     * @param tagtipoitemconfiguracao the tagtipoitemconfiguracao to set
     */
    public void setTagtipoitemconfiguracao(String tagtipoitemconfiguracao) {
        this.tagtipoitemconfiguracao = tagtipoitemconfiguracao;
    }

}
