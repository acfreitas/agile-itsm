package br.com.centralit.citcorpore.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class BaseConhecimentoPesquisaDTO {

    protected Long key;

    private Integer idBaseConhecimento;

    protected String userBaseConhecimento;

    private String titulo;

    private String conteudo;

    private Date dataExpiracao;

    private String linkDaPastaBaseConhecimento;
    
    private Integer contadorCliques;

    private Collection<AnexoBaseConhecimentoPesquisaDTO> anexosDaBaseConhecimento = new ArrayList<AnexoBaseConhecimentoPesquisaDTO>();

    public BaseConhecimentoPesquisaDTO() {
	key = null;
	userBaseConhecimento = null;
    }

    public BaseConhecimentoPesquisaDTO(String userBaseConhecimento) {
	key = null;
	this.userBaseConhecimento = userBaseConhecimento;
    }

    public Long getKey() {
	return key;
    }

    public void setKey(Long key) {
	this.key = key;
    }

    public Long getId() {
	return key;
    }

    public void setUserBaseConhecimento(String userBaseConhecimento) {
	this.userBaseConhecimento = userBaseConhecimento;
    }

    public String getUserBaseConhecimento() {
	return userBaseConhecimento;
    }

    public Integer getIdBaseConhecimento() {
	return idBaseConhecimento;
    }

    public void setIdBaseConhecimento(Integer idBaseConhecimento) {
	this.idBaseConhecimento = idBaseConhecimento;
    }

    /**
     * @return the linkDaPastaBaseConhecimento
     */
    public String getLinkDaPastaBaseConhecimento() {
	return linkDaPastaBaseConhecimento;
    }

    /**
     * @param linkDaPastaBaseConhecimento
     *            the linkDaPastaBaseConhecimento to set
     */
    public void setLinkDaPastaBaseConhecimento(String linkDaPastaBaseConhecimento) {
	this.linkDaPastaBaseConhecimento = linkDaPastaBaseConhecimento;
    }

    /**
     * @return the anexosDaBaseConhecimento
     */
    public Collection<AnexoBaseConhecimentoPesquisaDTO> getAnexosDaBaseConhecimento() {
	return anexosDaBaseConhecimento;
    }

    /**
     * @param anexosDaBaseConhecimento
     *            the anexosDaBaseConhecimento to set
     */
    public void setAnexosDaBaseConhecimento(Collection<AnexoBaseConhecimentoPesquisaDTO> anexosDaBaseConhecimento) {
	this.anexosDaBaseConhecimento = anexosDaBaseConhecimento;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
	return titulo;
    }

    /**
     * @param titulo
     *            the titulo to set
     */
    public void setTitulo(String titulo) {
	this.titulo = titulo;
    }

    /**
     * @return the conteudo
     */
    public String getConteudo() {
	return conteudo;
    }

    /**
     * @param conteudo
     *            the conteudo to set
     */
    public void setConteudo(String conteudo) {
	this.conteudo = conteudo;
    }

    /**
     * @return the dataExpiracao
     */
    public Date getDataExpiracao() {
	return dataExpiracao;
    }

    /**
     * @param dataExpiracao
     *            the dataExpiracao to set
     */
    public void setDataExpiracao(Date dataExpiracao) {
	this.dataExpiracao = dataExpiracao;
    }

    /**
     * @return valor do atributo contadorCliques.
     */
    public Integer getContadorCliques() {
        return contadorCliques;
    }

    /**
     * Define valor do atributo contadorCliques.
     *
     * @param contadorCliques
     */
    public void setContadorCliques(Integer contadorCliques) {
        this.contadorCliques = contadorCliques;
    }

}
