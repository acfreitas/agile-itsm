package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;
/**
 * 
 * @author flavio.santana
 *
 */
public class RelEscalonamentoSolServicoDto implements IDto {

	/**
	 * Relaciona as solicitações de serviço ao escalonamento
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idSolicitacaoServico;
	private Integer idEscalonamento;
	
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public Integer getIdEscalonamento() {
		return idEscalonamento;
	}
	public void setIdEscalonamento(Integer idEscalonamento) {
		this.idEscalonamento = idEscalonamento;
	}
	

}
