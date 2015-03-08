package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class CronogramaDTO implements IDto {
	private String ta;
	private Integer idProjeto;
	private Integer idLinhaBaseProjeto;
	private String novaLinhaBaseProjeto;
	private String situacaoLinhaBaseProjetoSelecionada;
	private Integer idTemplateImpressao;
	private String marcosProjeto;

	public String getTa() {
		return ta;
	}

	public void setTa(String ta) {
		this.ta = ta;
	}

	public Integer getIdProjeto() {
		return idProjeto;
	}

	public void setIdProjeto(Integer idProjeto) {
		this.idProjeto = idProjeto;
	}

	public Integer getIdLinhaBaseProjeto() {
		return idLinhaBaseProjeto;
	}

	public void setIdLinhaBaseProjeto(Integer idLinhaBaseProjeto) {
		this.idLinhaBaseProjeto = idLinhaBaseProjeto;
	}

	public String getNovaLinhaBaseProjeto() {
		return novaLinhaBaseProjeto;
	}

	public void setNovaLinhaBaseProjeto(String novaLinhaBaseProjeto) {
		this.novaLinhaBaseProjeto = novaLinhaBaseProjeto;
	}

	public String getSituacaoLinhaBaseProjetoSelecionada() {
		return situacaoLinhaBaseProjetoSelecionada;
	}

	public void setSituacaoLinhaBaseProjetoSelecionada(
			String situacaoLinhaBaseProjetoSelecionada) {
		this.situacaoLinhaBaseProjetoSelecionada = situacaoLinhaBaseProjetoSelecionada;
	}

	public Integer getIdTemplateImpressao() {
		return idTemplateImpressao;
	}

	public void setIdTemplateImpressao(Integer idTemplateImpressao) {
		this.idTemplateImpressao = idTemplateImpressao;
	}

	public String getMarcosProjeto() {
		return marcosProjeto;
	}

	public void setMarcosProjeto(String marcosProjeto) {
		this.marcosProjeto = marcosProjeto;
	}
}
