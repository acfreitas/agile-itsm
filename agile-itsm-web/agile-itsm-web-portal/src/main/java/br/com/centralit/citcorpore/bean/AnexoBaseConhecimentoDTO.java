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
public class AnexoBaseConhecimentoDTO implements IDto {

    private static final long serialVersionUID = 808193377991557947L;

    private Integer idAnexoBaseConhecimento;

    private Integer idBaseConhecimento;

    private Date dataInicio;

    private Date dataFim;

    private String nomeAnexo;

    private String descricao;

    private String link;

    private String extensao;

    private String textoDocumento;

    /**
     * @return valor do atributo idAnexoBaseConhecimento.
     */
    public Integer getIdAnexoBaseConhecimento() {
	return idAnexoBaseConhecimento;
    }

    /**
     * Define valor do atributo idAnexoBaseConhecimento.
     * 
     * @param idAnexoBaseConhecimento
     */
    public void setIdAnexoBaseConhecimento(Integer idAnexoBaseConhecimento) {
	this.idAnexoBaseConhecimento = idAnexoBaseConhecimento;
    }

    /**
     * @return valor do atributo idBaseConhecimento.
     */
    public Integer getIdBaseConhecimento() {
	return idBaseConhecimento;
    }

    /**
     * Define valor do atributo idBaseConhecimento.
     * 
     * @param idBaseConhecimento
     */
    public void setIdBaseConhecimento(Integer idBaseConhecimento) {
	this.idBaseConhecimento = idBaseConhecimento;
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
     * @return valor do atributo nomeAnexo.
     */
    public String getNomeAnexo() {
	return nomeAnexo;
    }

    /**
     * Define valor do atributo nomeAnexo.
     * 
     * @param nomeAnexo
     */
    public void setNomeAnexo(String nomeAnexo) {
	this.nomeAnexo = nomeAnexo;
    }

    /**
     * @return valor do atributo link.
     */
    public String getLink() {
	return link;
    }

    /**
     * Define valor do atributo link.
     * 
     * @param link
     */
    public void setLink(String link) {
	this.link = link;
    }

    /**
     * @return valor do atributo extensao.
     */
    public String getExtensao() {
	return extensao;
    }

    /**
     * Define valor do atributo extensao.
     * 
     * @param extensao
     */
    public void setExtensao(String extensao) {
	this.extensao = extensao;
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
     * @return valor do atributo textoDocumento.
     */
    public String getTextoDocumento() {
	return textoDocumento;
    }

    /**
     * Define valor do atributo textoDocumento.
     * 
     * @param textoDocumento
     */
    public void setTextoDocumento(String textoDocumento) {
	this.textoDocumento = textoDocumento;
    }

}
