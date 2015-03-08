package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class PerspectivaTecnicaExperienciaDTO implements IDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idPerspectivaTecnicaExperiencia;
	private String descricaoExperiencia;
	private String detalheExperiencia;
	private String obrigatorioExperiencia;
	private Integer idSolicitacaoServico;
	private Integer idConhecimento;
	
	public Integer getIdPerspectivaTecnicaExperiencia() {
		return idPerspectivaTecnicaExperiencia;
	}
	public void setIdPerspectivaTecnicaExperiencia(Integer idPerspectivaTecnicaExperiencia) {
		this.idPerspectivaTecnicaExperiencia = idPerspectivaTecnicaExperiencia;
	}
	public String getDescricaoExperiencia() {
		return descricaoExperiencia;
	}
	public void setDescricaoExperiencia(String descricaoExperiencia) {
		this.descricaoExperiencia = descricaoExperiencia;
	}
	public String getDetalheExperiencia() {
		return detalheExperiencia;
	}
	public void setDetalheExperiencia(String detalheExperiencia) {
		this.detalheExperiencia = detalheExperiencia;
	}
	public String getObrigatorioExperiencia() {
		return obrigatorioExperiencia;
	}
	public void setObrigatorioExperiencia(String obrigatorioExperiencia) {
		this.obrigatorioExperiencia = obrigatorioExperiencia;
	}
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public Integer getIdConhecimento() {
		return idConhecimento;
	}
	public void setIdConhecimento(Integer idConhecimento) {
		this.idConhecimento = idConhecimento;
	}
	
	
}