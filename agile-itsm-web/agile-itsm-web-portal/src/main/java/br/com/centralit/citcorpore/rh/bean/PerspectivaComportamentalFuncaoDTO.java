package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class PerspectivaComportamentalFuncaoDTO implements IDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idPerspectivaComportamental;
	private String descricaoPerspectivaComportamental;
	private String detalhePerspectivaComportamental;
	private Integer idSolicitacaoServico;
	private Integer idAtitudeIndividual;
	
	
	public Integer getIdPerspectivaComportamental() {
		return idPerspectivaComportamental;
	}
	public void setIdPerspectivaComportamental(Integer idPerspectivaComportamental) {
		this.idPerspectivaComportamental = idPerspectivaComportamental;
	}
	public String getDescricaoPerspectivaComportamental() {
		return descricaoPerspectivaComportamental;
	}
	public void setDescricaoPerspectivaComportamental(String descricaoPerspectivaComportamental) {
		this.descricaoPerspectivaComportamental = descricaoPerspectivaComportamental;
	}
	public String getDetalhePerspectivaComportamental() {
		return detalhePerspectivaComportamental;
	}
	public void setDetalhePerspectivaComportamental(String detalhePerspectivaComportamental) {
		this.detalhePerspectivaComportamental = detalhePerspectivaComportamental;
	}
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public Integer getIdAtitudeIndividual() {
		return idAtitudeIndividual;
	}
	public void setIdAtitudeIndividual(Integer idAtitudeIndividual) {
		this.idAtitudeIndividual = idAtitudeIndividual;
	}
	
	
}
