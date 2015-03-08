package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class RelEscalonamentoMudancaDto implements IDto{

	/**
	 * Relaciona as solicitações de serviço ao escalonamento
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idRequisicaoMudanca;
	private Integer idEscalonamento;
	
	public Integer getIdRequisicaoMudanca() {
		return idRequisicaoMudanca;
	}
	public void setIdRequisicaoMudanca(Integer idRequisicaoMudanca) {
		this.idRequisicaoMudanca = idRequisicaoMudanca;
	}
	public Integer getIdEscalonamento() {
		return idEscalonamento;
	}
	public void setIdEscalonamento(Integer idEscalonamento) {
		this.idEscalonamento = idEscalonamento;
	}
	
}
