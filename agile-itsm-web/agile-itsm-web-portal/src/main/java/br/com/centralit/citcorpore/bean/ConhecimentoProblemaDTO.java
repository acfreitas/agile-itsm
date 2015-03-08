package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ConhecimentoProblemaDTO implements IDto {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private Integer idProblema;
	private Integer idBaseConhecimento;
	private String status;
	private String titulo;
	/**
	 * @return the idProblema
	 */
	public Integer getIdProblema() {
		return idProblema;
	}
	/**
	 * @param idProblema the idProblema to set
	 */
	public void setIdProblema(Integer idProblema) {
		this.idProblema = idProblema;
	}
	/**
	 * @return the idBaseConhecimento
	 */
	public Integer getIdBaseConhecimento() {
		return idBaseConhecimento;
	}
	/**
	 * @param idBaseConhecimento the idBaseConhecimento to set
	 */
	public void setIdBaseConhecimento(Integer idBaseConhecimento) {
		this.idBaseConhecimento = idBaseConhecimento;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


}
