package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class GerenciamentoProblemasDTO implements IDto {
	private Integer idFluxo;
	private Integer idTarefa;
	private Integer idProblema;
	private String acaoFluxo;
	
	private String numeroContratoSel;
	private String idSolicitacaoSel;
	private String atribuidaCompartilhada;
	private String idProblemaSel;

	public Integer getIdFluxo() {
		return idFluxo;
	}
	public void setIdFluxo(Integer idFluxo) {
		this.idFluxo = idFluxo;
	}
	public Integer getIdTarefa() {
		return idTarefa;
	}
	public void setIdTarefa(Integer idTarefa) {
		this.idTarefa = idTarefa;
	}
	public String getAcaoFluxo() {
		return acaoFluxo;
	}
	public void setAcaoFluxo(String acaoFluxo) {
		this.acaoFluxo = acaoFluxo;
	}
	public String getNumeroContratoSel() {
		return numeroContratoSel;
	}
	public void setNumeroContratoSel(String numeroContratoSel) {
		this.numeroContratoSel = numeroContratoSel;
	}
	public String getIdSolicitacaoSel() {
		return idSolicitacaoSel;
	}
	public void setIdSolicitacaoSel(String idSolicitacaoSel) {
		this.idSolicitacaoSel = idSolicitacaoSel;
	}

	public Integer getIdProblema() {
		return idProblema;
	}
	public void setIdProblema(Integer idProblema) {
		this.idProblema = idProblema;
	}
	public String getAtribuidaCompartilhada() {
	    return atribuidaCompartilhada;
	}
	public void setAtribuidaCompartilhada(String atribuidaCompartilhada) {
	    this.atribuidaCompartilhada = atribuidaCompartilhada;
	}
	/**
	 * @return the idRequisicaoSel
	 */
	public String getIdProblemaSel() {
		return idProblemaSel;
	}
	/**
	 * @param idRequisicaoSel the idRequisicaoSel to set
	 */
	public void setIdProblemaSel(String idProblemaSel) {
		this.idProblemaSel = idProblemaSel;
	}

}
