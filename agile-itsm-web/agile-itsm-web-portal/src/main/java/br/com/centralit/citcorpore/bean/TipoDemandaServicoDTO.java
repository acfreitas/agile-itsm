package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class TipoDemandaServicoDTO implements IDto {

	private static final long serialVersionUID = 4413980874410791397L;

	private Integer idTipoDemandaServico;

	private String nomeTipoDemandaServico;

	private String classificacao;

	private String deleted;
	
	private Integer quantidade;

	public Integer getIdTipoDemandaServico() {
		return idTipoDemandaServico;
	}

	public void setIdTipoDemandaServico(Integer idTipoDemandaServico) {
		this.idTipoDemandaServico = idTipoDemandaServico;
	}

	public String getNomeTipoDemandaServico() {
		return nomeTipoDemandaServico;
	}

	public void setNomeTipoDemandaServico(String nomeTipoDemandaServico) {
		this.nomeTipoDemandaServico = nomeTipoDemandaServico;
	}

	public String getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

}
