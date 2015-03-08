package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ChecklistQuestionarioDTO implements IDto {

	private Integer idChecklistQuestionario;
	private Integer idContrato;
	private Integer idQuestionario;
	private Integer idQuestionarioOrigem;
	private Integer idRequisicao;
	private Integer idTarefa;
	
	private Integer idTipoAba;
	private Integer idTipoRequisicao;
	
	private Integer idRequisicaoQuestionario;
	
	private String valorConfirmacao;
	
	
	
	public Integer getIdQuestionarioOrigem() {
		return idQuestionarioOrigem;
	}
	public void setIdQuestionarioOrigem(Integer idQuestionarioOrigem) {
		this.idQuestionarioOrigem = idQuestionarioOrigem;
	}

	public Integer getIdTipoAba() {
		return idTipoAba;
	}
	public void setIdTipoAba(Integer idTipoAba) {
		this.idTipoAba = idTipoAba;
	}
	public Integer getIdTipoRequisicao() {
		return idTipoRequisicao;
	}
	public void setIdTipoRequisicao(Integer idTipoRequisicao) {
		this.idTipoRequisicao = idTipoRequisicao;
	}
	public Integer getIdQuestionario() {
		return idQuestionario;
	}
	public void setIdQuestionario(Integer idQuestionario) {
		this.idQuestionario = idQuestionario;
	}
	private String tipoApresResumo;
	private String ultimoComando;
	
	public Integer getIdChecklistQuestionario() {
		return idChecklistQuestionario;
	}
	public void setIdChecklistQuestionario(Integer idChecklistQuestionario) {
		this.idChecklistQuestionario = idChecklistQuestionario;
	}
	
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
	
	public String getTipoApresResumo() {
		return tipoApresResumo;
	}
	public void setTipoApresResumo(String tipoApresResumo) {
		this.tipoApresResumo = tipoApresResumo;
	}
	public String getUltimoComando() {
		return ultimoComando;
	}
	public void setUltimoComando(String ultimoComando) {
		this.ultimoComando = ultimoComando;
	}
	/**
	 * @return the idRequisicao
	 */
	public Integer getIdRequisicao() {
		return idRequisicao;
	}
	/**
	 * @param idRequisicao the idRequisicao to set
	 */
	public void setIdRequisicao(Integer idRequisicao) {
		this.idRequisicao = idRequisicao;
	}
	public Integer getIdRequisicaoQuestionario() {
		return idRequisicaoQuestionario;
	}
	public void setIdRequisicaoQuestionario(Integer idRequisicaoQuestionario) {
		this.idRequisicaoQuestionario = idRequisicaoQuestionario;
	}
	public String getValorConfirmacao() {
		return valorConfirmacao;
	}
	public void setValorConfirmacao(String valorConfirmacao) {
		this.valorConfirmacao = valorConfirmacao;
	}
	/**
	 * @return the idTarefa
	 */
	public Integer getIdTarefa() {
		return idTarefa;
	}
	/**
	 * @param idTarefa the idTarefa to set
	 */
	public void setIdTarefa(Integer idTarefa) {
		this.idTarefa = idTarefa;
	}
}
