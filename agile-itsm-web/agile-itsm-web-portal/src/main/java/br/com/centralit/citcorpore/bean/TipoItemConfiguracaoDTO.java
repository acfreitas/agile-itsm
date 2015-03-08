/**
 * CentralIT - CITSmart.
 */
package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.DateAdapter;

/**
 * DTO de TipoItemConfiguracao.
 * 
 * @author valdoilo.damasceno
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "TipoItemConfiguracao") 
public class TipoItemConfiguracaoDTO implements IDto {

    public TipoItemConfiguracaoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TipoItemConfiguracaoDTO(String nome, String tag) {
		super();
		this.nome = nome;
		this.tag = tag;
	}

	private static final long serialVersionUID = -8131964625770147319L;

    private Integer id;

    private Integer idEmpresa;

    private String nome;

    private String tag;

    private String sistema;
    
    private Integer categoria;
    
    @SuppressWarnings("rawtypes")
    private List caracteristicas;

	@XmlElement(name = "dataInicio")
	@XmlJavaTypeAdapter(DateAdapter.class)	
    private Date dataInicio;

	@XmlElement(name = "dataFim")
	@XmlJavaTypeAdapter(DateAdapter.class)	
    private Date dataFim;
    
    private String imagem;

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
     * @return valor do atributo caracteristicas.
     */
    @SuppressWarnings("rawtypes")
    public List getCaracteristicas() {
	return caracteristicas;
    }

    /**
     * Define valor do atributo caracteristicas.
     * 
     * @param caracteristicas
     */
    @SuppressWarnings("rawtypes")
    public void setCaracteristicas(List caracteristicas) {
	this.caracteristicas = caracteristicas;
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

	public Integer getCategoria() {
		return categoria;
	}

	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

}
