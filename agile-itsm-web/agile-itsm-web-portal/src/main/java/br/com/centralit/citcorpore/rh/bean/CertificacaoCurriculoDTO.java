package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;


public class CertificacaoCurriculoDTO implements IDto {
	private Integer idCertificacao;
	private String descricao;
	private String versao;
	private Integer validade;
	private Integer idCurriculo;
	
	
	
	public Integer getIdCurriculo() {
		return idCurriculo;
	}
	public void setIdCurriculo(Integer idCurriculo) {
		this.idCurriculo = idCurriculo;
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
	public String getVersao() {
		return versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	public Integer getValidade() {
		return validade;
	}
	public void setValidade(Integer validade) {
		this.validade = validade;
	}
}