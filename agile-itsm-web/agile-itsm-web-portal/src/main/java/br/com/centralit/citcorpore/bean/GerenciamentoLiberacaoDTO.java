package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class GerenciamentoLiberacaoDTO implements IDto {
	private Integer idFluxo;
	private Integer idTarefa;
	private Integer idRequisicao;
	private String acaoFluxo;
	
	private String numeroContratoSel;
	private String idSolicitacaoSel;
	private String atribuidaCompartilhada;
	
	private String idRequisicaoSel;
	
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

	public Integer getIdRequisicao() {
		return idRequisicao;
	}
	public void setIdRequisicao(Integer idRequisicao) {
		this.idRequisicao = idRequisicao;
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
	public String getIdRequisicaoSel() {
		return idRequisicaoSel;
	}
	/**
	 * @param idRequisicaoSel the idRequisicaoSel to set
	 */
	public void setIdRequisicaoSel(String idRequisicaoSel) {
		this.idRequisicaoSel = idRequisicaoSel;
	}

}
