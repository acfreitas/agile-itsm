package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class PerspectivaTecnicaCertificacaoDTO implements IDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idPerspectivaTecnicaCertificacao;
	private String descricaoCertificacao;
	private String versaoCertificacao;
	private String obrigatorioCertificacao;
	private Integer idSolicitacaoServico;
	private Integer idCertificacao;
	private String detalhe;
	
	
	public Integer getIdPerspectivaTecnicaCertificacao() {
		return idPerspectivaTecnicaCertificacao;
	}
	public void setIdPerspectivaTecnicaCertificacao(Integer idPerspectivaTecnicaCertificacao) {
		this.idPerspectivaTecnicaCertificacao = idPerspectivaTecnicaCertificacao;
	}
	public String getDescricaoCertificacao() {
		return descricaoCertificacao;
	}
	public void setDescricaoCertificacao(String descricaoCertificacao) {
		this.descricaoCertificacao = descricaoCertificacao;
	}
	
	public String getObrigatorioCertificacao() {
		return obrigatorioCertificacao;
	}
	public void setObrigatorioCertificacao(String obrigatorioCertificacao) {
		this.obrigatorioCertificacao = obrigatorioCertificacao;
	}
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public String getVersaoCertificacao() {
		return versaoCertificacao;
	}
	public void setVersaoCertificacao(String versaoCertificacao) {
		this.versaoCertificacao = versaoCertificacao;
	}
	public Integer getIdCertificacao() {
		return idCertificacao;
	}
	public void setIdCertificacao(Integer idCertificacao) {
		this.idCertificacao = idCertificacao;
	}
	/**
	 * @return the detalhe
	 */
	public String getDetalhe() {
		return detalhe;
	}
	/**
	 * @param detalhe the detalhe to set
	 */
	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}
	
}