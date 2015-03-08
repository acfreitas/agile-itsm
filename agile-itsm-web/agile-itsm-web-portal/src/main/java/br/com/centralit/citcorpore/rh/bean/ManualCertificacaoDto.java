package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class ManualCertificacaoDto implements IDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idManualCertificacao;
	private String descricao;
    private String detalhe;
    private Integer idManualFuncao;
    private String RAouRF;
	
	public String getDetalhe() {
		return detalhe;
	}
	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}
	
	public Integer getIdManualCertificacao() {
		return idManualCertificacao;
	}
	public void setIdManualCertificacao(Integer idManualCertificacao) {
		this.idManualCertificacao = idManualCertificacao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getIdManualFuncao() {
		return idManualFuncao;
	}
	public void setIdManualFuncao(Integer idManualFuncao) {
		this.idManualFuncao = idManualFuncao;
	}
	public String getRAouRF() {
		return RAouRF;
	}
	public void setRAouRF(String rAouRF) {
		RAouRF = rAouRF;
	}

}
