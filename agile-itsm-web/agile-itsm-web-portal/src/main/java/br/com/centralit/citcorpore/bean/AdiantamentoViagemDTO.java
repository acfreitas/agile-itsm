package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class AdiantamentoViagemDTO implements IDto {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idAdiantamentoViagem;
	private Integer idResponsavel;
	private Integer idSolicitacaoServico;
	private Integer idEmpregado;
	private Timestamp dataHora;
	private Double valorTotalAdiantado;
	private String situacao;
	private String observacoes;
	private String cancelarRequisicao;
	
	private String adiantamentoViagemSerialize;
	
	private Integer idContrato;
	
	private String integranteFuncionario;
	
	private String nomeNaoFuncionario;

	public Integer getIdAdiantamentoViagem() {
		return idAdiantamentoViagem;
	}

	public void setIdAdiantamentoViagem(Integer idAdiantamentoViagem) {
		this.idAdiantamentoViagem = idAdiantamentoViagem;
	}

	public Integer getIdResponsavel() {
		return idResponsavel;
	}

	public void setIdResponsavel(Integer idResponsavel) {
		this.idResponsavel = idResponsavel;
	}

	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}

	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}

	public Integer getIdEmpregado() {
		return idEmpregado;
	}

	public void setIdEmpregado(Integer idEmpregado) {
		this.idEmpregado = idEmpregado;
	}

	public Timestamp getDataHora() {
		return dataHora;
	}

	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}

	public Double getValorTotalAdiantado() {
		return valorTotalAdiantado;
	}

	public void setValorTotalAdiantado(Double valorTotalAdiantado) {
		this.valorTotalAdiantado = valorTotalAdiantado;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String getAdiantamentoViagemSerialize() {
		return adiantamentoViagemSerialize;
	}

	public void setAdiantamentoViagemSerialize(String adiantamentoViagemSerialize) {
		this.adiantamentoViagemSerialize = adiantamentoViagemSerialize;
	}

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public String getIntegranteFuncionario() {
		return integranteFuncionario;
	}

	public void setIntegranteFuncionario(String integranteFuncionario) {
		this.integranteFuncionario = integranteFuncionario;
	}

	public String getNomeNaoFuncionario() {
		return nomeNaoFuncionario;
	}

	public void setNomeNaoFuncionario(String nomeNaoFuncionario) {
		this.nomeNaoFuncionario = nomeNaoFuncionario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the cancelarRequisicao
	 */
	public String getCancelarRequisicao() {
		return cancelarRequisicao;
	}

	/**
	 * @param cancelarRequisicao the cancelarRequisicao to set
	 */
	public void setCancelarRequisicao(String cancelarRequisicao) {
		this.cancelarRequisicao = cancelarRequisicao;
	}
}