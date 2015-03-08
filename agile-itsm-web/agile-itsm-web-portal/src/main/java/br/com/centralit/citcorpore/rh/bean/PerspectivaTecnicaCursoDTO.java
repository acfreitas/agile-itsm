package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class PerspectivaTecnicaCursoDTO implements IDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idPerspectivaTecnicaCurso;
	private String descricaoCurso;
	private String detalheCurso;
	private String obrigatorioCurso;
	private Integer idSolicitacaoServico;
	private Integer idCurso;
	
	public Integer getIdPerspectivaTecnicaCurso() {
		return idPerspectivaTecnicaCurso;
	}
	public void setIdPerspectivaTecnicaCurso(Integer idPerspectivaTecnicaCurso) {
		this.idPerspectivaTecnicaCurso = idPerspectivaTecnicaCurso;
	}
	public String getDescricaoCurso() {
		return descricaoCurso;
	}
	public void setDescricaoCurso(String descricaoCurso) {
		this.descricaoCurso = descricaoCurso;
	}
	public String getDetalheCurso() {
		return detalheCurso;
	}
	public void setDetalheCurso(String detalheCurso) {
		this.detalheCurso = detalheCurso;
	}
	public String getObrigatorioCurso() {
		return obrigatorioCurso;
	}
	public void setObrigatorioCurso(String obrigatorioCurso) {
		this.obrigatorioCurso = obrigatorioCurso;
	}
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public Integer getIdCurso() {
		return idCurso;
	}
	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
	}
	
	
	
	
}