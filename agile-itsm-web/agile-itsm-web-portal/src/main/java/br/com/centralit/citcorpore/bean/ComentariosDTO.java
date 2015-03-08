/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
 * @author valdoilo
 * 
 */
public class ComentariosDTO implements IDto {

    private static final long serialVersionUID = -7676374709052524415L;

    private Integer idComentario;
    private String comentario;
    private String nome;
    private String email;
    private Integer idBaseConhecimento;
    private Date dataInicio;
    private Date dataFim;
    private String nota;
    private Double media;
    private Long votos;
    private String titulo;
    private Integer quantidadeBaseConhecimento;

    /**
     * @return the votos
     */
    public Long getVotos() {
	return votos;
    }

    /**
     * @param votos
     *            the votos to set
     */
    public void setVotos(Long votos) {
	if(votos == null){
	    this.votos = 0L;
	} else { 
	    this.votos = votos;
	}
    }

    /**
     * @return the nota
     */
    public String getNota() {
	return nota;
    }

    /**
     * @param nota
     *            the nota to set
     */
    public void setNota(String nota) {
	if(nota == null){
	    this.nota = "0";
	} else { 
	    this.nota = nota;
	}
    }
    /**
     * @return the idComentario
     */
    public Integer getIdComentario() {
	return idComentario;
    }
    /**
     * @param idComentario
     *            the idComentario to set
     */
    public void setIdComentario(Integer idComentario) {
	this.idComentario = idComentario;
    }
    /**
     * @return the comentario
     */
    public String getComentario() {
	return comentario;
    }
    /**
     * @param comentario
     *            the comentario to set
     */
    public void setComentario(String comentario) {
	this.comentario = comentario;
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
     * @return the email
     */
    public String getEmail() {
	return email;
    }
    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
	this.email = email;
    }
    /**
     * @return the idBaseConhecimento
     */
    public Integer getIdBaseConhecimento() {
	return idBaseConhecimento;
    }
    
    /**
     * @param idBaseConhecimento
     *            the idBaseConhecimento to set
     */
    public void setIdBaseConhecimento(Integer idBaseConhecimento) {
	this.idBaseConhecimento = idBaseConhecimento;
    }
    /**
     * @return the dataInicio
     */
    public Date getDataInicio() {
	return dataInicio;
    }
    
    /**
     * @param dataInicio
     *            the dataInicio to set
     */
    public void setDataInicio(Date dataInicio) {
	this.dataInicio = dataInicio;
    }
    /**
     * @return the dataFim
     */
    public Date getDataFim() {
	return dataFim;
    }

    /**
     * @param dataFim
     *            the dataFim to set
     */
    public void setDataFim(Date dataFim) {
	this.dataFim = dataFim;
    }

    /**
     * @return the media
     */
    public Double getMedia() {
	return media;
    }

    /**
     * @param media
     *            the media to set
     */
    public void setMedia(Double media) {
	this.media = media;
    }
    
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getQuantidadeBaseConhecimento() {
		return quantidadeBaseConhecimento;
	}

	public void setQuantidadeBaseConhecimento(Integer quantidadeBaseConhecimento) {
		this.quantidadeBaseConhecimento = quantidadeBaseConhecimento;
	}
    
}
