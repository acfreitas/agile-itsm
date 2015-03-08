package br.com.centralit.citcorpore.bean;


import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class HistoricoSolicitacaoServicoDTO implements IDto {

	private static final long serialVersionUID = 1L;
	
	private Integer idHistoricoSolicitacao;
	private Integer idSolicitacaoServico;
	private Integer idResponsavelAtual;
	private Integer idGrupo;
	private Integer idOcorrencia;
	private Timestamp dataCriacao;
	private Timestamp dataFinal;
	private Double horasTrabalhadas;
	private Integer idServicoContrato;
	private Integer idCalendario;
	private String status;
//	private String nomeUsuario;
//	private String nomeGrupo;
//	private Double soma;
	
	public Integer getIdHistoricoSolicitacao() {
		return idHistoricoSolicitacao;
	}
	public void setIdHistoricoSolicitacao(Integer idHistoricoSolicitacao) {
		this.idHistoricoSolicitacao = idHistoricoSolicitacao;
	}

	public Integer getIdResponsavelAtual() {
		return idResponsavelAtual;
	}
	public void setIdResponsavelAtual(Integer idResponsavelAtual) {
		this.idResponsavelAtual = idResponsavelAtual;
	}
	public Integer getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}
	public Integer getIdOcorrencia() {
		return idOcorrencia;
	}
	public void setIdOcorrencia(Integer idOcorrencia) {
		this.idOcorrencia = idOcorrencia;
	}
	public Double getHorasTrabalhadas() {
		return horasTrabalhadas;
	}
	public void setHorasTrabalhadas(Double horasTrabalhadas) {
		this.horasTrabalhadas = horasTrabalhadas;
	}
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public Timestamp getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Timestamp timestamp) {
		this.dataCriacao = timestamp;
	}
	public Timestamp getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Timestamp dataFinal) {
		this.dataFinal = dataFinal;
	}
	public Integer getIdServicoContrato() {
		return idServicoContrato;
	}
	public void setIdServicoContrato(Integer idServicoContrato) {
		this.idServicoContrato = idServicoContrato;
	}
	public Integer getIdCalendario() {
		return idCalendario;
	}
	public void setIdCalendario(Integer idCalendario) {
		this.idCalendario = idCalendario;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
//	public String getNomeUsuario() {
//		return nomeUsuario;
//	}
//	public void setNomeUsuario(String nomeUsuario) {
//		this.nomeUsuario = nomeUsuario;
//	}
//	public Double getSoma() {
//		return soma;
//	}
//	public void setSoma(Double soma) {
//		this.soma = soma;
//	}
//	public String getNomeGrupo() {
//		return nomeGrupo;
//	}
//	public void setNomeGrupo(String nomeGrupo) {
//		this.nomeGrupo = nomeGrupo;
//	}
}
