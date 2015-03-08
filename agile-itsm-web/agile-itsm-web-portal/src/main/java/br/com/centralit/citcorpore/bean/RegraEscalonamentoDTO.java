package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.util.Collection;

import br.com.citframework.dto.IDto;

/**
 * @author euler.ramos
 *
 */
public class RegraEscalonamentoDTO implements IDto {

	private static final long serialVersionUID = -3721270786353311618L;
	
	private Integer idRegraEscalonamento;
	private Integer idTipoGerenciamento;
	private Integer idServico;
	private Integer idContrato;
	private Integer idSolicitante;
	private Integer idGrupo;
	private Integer idTipoDemandaServico;
	private String urgencia;
	private String impacto;
	private Integer tempoExecucao;
	private Integer intervaloNotificacao;
	private String enviarEmail;
	private String criaProblema;
	private Date dataInicio;
	private Date dataFim;
	private Integer prazoCriarProblema;
	private Integer tipoDataEscalonamento;
	
	private String grupo;
	private String nomeSolicitante;
	private String servico;
	
	private Collection<EscalonamentoDTO> colEscalonamentoDTOs;
	
	public Integer getIdRegraEscalonamento() {
		return idRegraEscalonamento;
	}
	public void setIdRegraEscalonamento(Integer idRegraEscalonamento) {
		this.idRegraEscalonamento = idRegraEscalonamento;
	}
	public Integer getIdTipoGerenciamento() {
		return idTipoGerenciamento;
	}
	public void setIdTipoGerenciamento(Integer idTipoGerenciamento) {
		this.idTipoGerenciamento = idTipoGerenciamento;
	}
	public Integer getIdServico() {
		return idServico;
	}
	public void setIdServico(Integer idServico) {
		this.idServico = idServico;
	}
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
	public Integer getIdSolicitante() {
		return idSolicitante;
	}
	public void setIdSolicitante(Integer idSolicitante) {
		this.idSolicitante = idSolicitante;
	}
	public Integer getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}
	public Integer getIdTipoDemandaServico() {
		return idTipoDemandaServico;
	}
	public void setIdTipoDemandaServico(Integer idTipoDemandaServico) {
		this.idTipoDemandaServico = idTipoDemandaServico;
	}
	public String getUrgencia() {
		return urgencia;
	}
	public void setUrgencia(String urgencia) {
		this.urgencia = urgencia;
	}
	public String getImpacto() {
		return impacto;
	}
	public void setImpacto(String impacto) {
		this.impacto = impacto;
	}
	public Integer getTempoExecucao() {
		return tempoExecucao;
	}
	public void setTempoExecucao(Integer tempoExecucao) {
		this.tempoExecucao = tempoExecucao;
	}
	public Integer getIntervaloNotificacao() {
		return intervaloNotificacao;
	}
	public void setIntervaloNotificacao(Integer intervaloNotificacao) {
		this.intervaloNotificacao = intervaloNotificacao;
	}
	public String getEnviarEmail() {
		return enviarEmail;
	}
	public void setEnviarEmail(String enviarEmail) {
		this.enviarEmail = enviarEmail;
	}
	public Collection<EscalonamentoDTO> getColEscalonamentoDTOs() {
		return colEscalonamentoDTOs;
	}
	public void setColEscalonamentoDTOs(
			Collection<EscalonamentoDTO> colEscalonamentoDTOs) {
		this.colEscalonamentoDTOs = colEscalonamentoDTOs;
	}
	public String getCriaProblema() {
		return criaProblema;
	}
	public void setCriaProblema(String criaProblema) {
		this.criaProblema = criaProblema;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getNomeSolicitante() {
		return nomeSolicitante;
	}
	public void setNomeSolicitante(String nomeSolicitante) {
		this.nomeSolicitante = nomeSolicitante;
	}
	public String getServico() {
		return servico;
	}
	public void setServico(String servico) {
		this.servico = servico;
	}
	public Integer getPrazoCriarProblema() {
		return prazoCriarProblema;
	}
	public void setPrazoCriarProblema(Integer prazoCriarProblema) {
		this.prazoCriarProblema = prazoCriarProblema;
	}
	public Integer getTipoDataEscalonamento() {
		return tipoDataEscalonamento;
	}
	public void setTipoDataEscalonamento(Integer tipoDataEscalonamento) {
		this.tipoDataEscalonamento = tipoDataEscalonamento;
	}
	
}