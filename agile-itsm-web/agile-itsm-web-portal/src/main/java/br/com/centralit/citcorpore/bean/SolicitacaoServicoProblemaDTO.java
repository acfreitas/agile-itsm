package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class SolicitacaoServicoProblemaDTO implements IDto {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private Integer idProblema;
	private Integer idSolicitacaoServico;
	
	private String nomeServico;
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
	 * @return the idSolicitacaoServico
	 */
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	/**
	 * @param idSolicitacaoServico the idSolicitacaoServico to set
	 */
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public String getNomeServico() {
		return nomeServico;
	}
	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}
	

}
