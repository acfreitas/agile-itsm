package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * 
 * @author rodrigo.oliveira
 *
 */
public class VinculaOsIncidenteDTO implements IDto {
	
	private Integer idOS;
	private Integer IdSolicitacaoServico;
	private Integer idAtividadesOS;
	
	public Integer getIdOS() {
		return idOS;
	}
	public void setIdOS(Integer idOS) {
		this.idOS = idOS;
	}
	public Integer getIdSolicitacaoServico() {
		return IdSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		IdSolicitacaoServico = idSolicitacaoServico;
	}
	public Integer getIdAtividadesOS() {
		return idAtividadesOS;
	}
	public void setIdAtividadesOS(Integer idAtividadesOS) {
		this.idAtividadesOS = idAtividadesOS;
	}
	
	
}
