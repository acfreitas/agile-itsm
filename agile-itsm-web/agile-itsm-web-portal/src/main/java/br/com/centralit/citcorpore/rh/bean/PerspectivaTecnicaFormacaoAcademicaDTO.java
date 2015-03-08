package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class PerspectivaTecnicaFormacaoAcademicaDTO implements IDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idPerspectivaTecnicaFormacaoAcademica;
	private String descricaoFormacaoAcademica;
	private String detalheFormacaoAcademica;
	private String obrigatorioFormacaoAcademica;
	private Integer idSolicitacaoServico;
	private Integer idFormacaoAcademica;
		
	
	public Integer getIdPerspectivaTecnicaFormacaoAcademica() {
		return idPerspectivaTecnicaFormacaoAcademica;
	}
	public void setIdPerspectivaTecnicaFormacaoAcademica(Integer idPerspectivaTecnicaFormacaoAcademica) {
		this.idPerspectivaTecnicaFormacaoAcademica = idPerspectivaTecnicaFormacaoAcademica;
	}
	public String getDescricaoFormacaoAcademica() {
		return descricaoFormacaoAcademica;
	}
	public void setDescricaoFormacaoAcademica(String descricaoFormacaoAcademica) {
		this.descricaoFormacaoAcademica = descricaoFormacaoAcademica;
	}
	public String getDetalheFormacaoAcademica() {
		return detalheFormacaoAcademica;
	}
	public void setDetalheFormacaoAcademica(String detalheFormacaoAcademica) {
		this.detalheFormacaoAcademica = detalheFormacaoAcademica;
	}
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public String getObrigatorioFormacaoAcademica() {
		return obrigatorioFormacaoAcademica;
	}
	public void setObrigatorioFormacaoAcademica(String obrigatorioFormacaoAcademica) {
		this.obrigatorioFormacaoAcademica = obrigatorioFormacaoAcademica;
	}
	public Integer getIdFormacaoAcademica() {
		return idFormacaoAcademica;
	}
	public void setIdFormacaoAcademica(Integer idFormacaoAcademica) {
		this.idFormacaoAcademica = idFormacaoAcademica;
	}
	
	
}