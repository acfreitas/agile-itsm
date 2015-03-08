package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ProblemaRelacionadoDTO implements IDto {
	private static final long serialVersionUID = 1L;
	
	private Integer idProblema;
	private Integer idProblemaRelacionado;
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
	 * @return the idProblemaRelacionado
	 */
	public Integer getIdProblemaRelacionado() {
		return idProblemaRelacionado;
	}
	/**
	 * @param idProblemaRelacionado the idProblemaRelacionado to set
	 */
	public void setIdProblemaRelacionado(Integer idProblemaRelacionado) {
		this.idProblemaRelacionado = idProblemaRelacionado;
	}
}
