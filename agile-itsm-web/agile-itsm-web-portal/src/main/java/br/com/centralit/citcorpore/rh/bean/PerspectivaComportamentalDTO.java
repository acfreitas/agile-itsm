package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class PerspectivaComportamentalDTO implements IDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idPerspectivaComportamental;
	private String cmbCompetenciaComportamental;
    private String comportamento;
    private Integer idManualFuncao;
	private String descricaoPerspectivaComportamental;
	private String detalhePerspectivaComportamental;
	
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
	private Integer idSolicitacaoServico;
    
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public Integer getIdPerspectivaComportamental() {
		return idPerspectivaComportamental;
	}
	public void setIdPerspectivaComportamental(Integer idPerspectivaComportamental) {
		this.idPerspectivaComportamental = idPerspectivaComportamental;
	}
	public String getCmbCompetenciaComportamental() {
		return cmbCompetenciaComportamental;
	}
	public void setCmbCompetenciaComportamental(String cmbCompetenciaComportamental) {
		this.cmbCompetenciaComportamental = cmbCompetenciaComportamental;
	}
	public String getComportamento() {
		return comportamento;
	}
	public void setComportamento(String comportamento) {
		this.comportamento = comportamento;
	}
	public Integer getIdManualFuncao() {
		return idManualFuncao;
	}
	public void setIdManualFuncao(Integer idManualFuncao) {
		this.idManualFuncao = idManualFuncao;
	}
    
}
