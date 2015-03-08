package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class CertificacaoDTO implements IDto {
	private Integer idCertificacao;
	private String descricao;
    private String detalhe;
	
	public String getDetalhe() {
		return detalhe;
	}
	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}
	
	public Integer getIdCertificacao() {
		return idCertificacao;
	}
	public void setIdCertificacao(Integer idCertificacao) {
		this.idCertificacao = idCertificacao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}