/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.DateAdapter;

/**
 * Representa tabela característica.
 * 
 * @author VMD
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Caracteristica") 
public class CaracteristicaDTO implements IDto {

    public CaracteristicaDTO(String nome, ValorDTO valor) {
		super();
		this.nome = nome;
		this.valor = valor;
	}

	public CaracteristicaDTO() {
		super();
	}

	private static final long serialVersionUID = 2781613715966482350L;

    private Integer idCaracteristica;

    private Integer idEmpresa;

    private String nome;

    private String tag;

    private String descricao;

    private String sistema;

    private String tipo;

	@XmlElement(name = "dataInicio")
	@XmlJavaTypeAdapter(DateAdapter.class)	
    private Date dataInicio;

	@XmlElement(name = "dataFim")
	@XmlJavaTypeAdapter(DateAdapter.class)	
    private Date dataFim;

    private ValorDTO valor;

    private String valorString;

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

    /**
     * @return valor do atributo tipo.
     */
    public String getTipo() {
	return tipo;
    }

    /**
     * Define valor do atributo tipo.
     * 
     * @param tipo
     */
    public void setTipo(String tipo) {
	this.tipo = tipo;
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
     * @param valor
     */
    public void setValor(ValorDTO valor) {
	this.valor = valor;
    }

    /**
     * @return valor do dto ValorDTO
     */
    public ValorDTO getValor() {
	return valor;
    }

    /**
	 * @return the sistema
	 */
	public String getSistema() {
		return sistema;
	}

	/**
	 * @param sistema the sistema to set
	 */
	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	/**
     * @return valor do atributo valorString.
     */
    public String getValorString() {
	return valorString;
    }

    /**
     * Define valor do atributo valorString.
     * 
     * @param valorString
     */
    public void setValorString(String valorString) {
	this.valorString = valorString;
    }

	/**
	 * Necessário sobrescrever para gerar o equals
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		return result;
	}

	/**
	 * Sobrescrito o equals de Object para comparar uma Caracteristica com a outra através da tag,
	 * se tiverem a mesma tag ele retornará que o objeto é igual, sem comparar instancia do objeto;
	 * 
	 * @param Object outro {@link CaracteristicaDTO}
	 * @return boolean se objeto tem mesma tag
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CaracteristicaDTO other = (CaracteristicaDTO) obj;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		return true;
	}

}
