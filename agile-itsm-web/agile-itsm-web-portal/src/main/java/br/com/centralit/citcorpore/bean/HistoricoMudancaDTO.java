package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

public class HistoricoMudancaDTO extends RequisicaoMudancaDTO {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idHistoricoMudanca;
	private Timestamp dataHoraModificacao;
	private Integer idExecutorModificacao;
	private String tipoModificacao;
	private Double historicoVersao;
	private String nomeProprietario;
	private String baseLine;
	private String nomeExecutorModificacao;
	private String acaoFluxo;
	private String alterarSituacao;
	private String emailSolicitante;
	
	

	public Integer getIdHistoricoMudanca() {
		return idHistoricoMudanca;
	}
	public void setIdHistoricoMudanca(Integer idHistoricoMudanca) {
		this.idHistoricoMudanca = idHistoricoMudanca;
	}
	public Timestamp getDataHoraModificacao() {
		return dataHoraModificacao;
	}
	public String getAcaoFluxo() {
		return acaoFluxo;
	}
	public void setAcaoFluxo(String acaoFluxo) {
		this.acaoFluxo = acaoFluxo;
	}
	public void setDataHoraModificacao(Timestamp dataHoraModificacao) {
		this.dataHoraModificacao = dataHoraModificacao;
	}
	public Integer getIdExecutorModificacao() {
		return idExecutorModificacao;
	}
	public void setIdExecutorModificacao(Integer idExecutorModificacao) {
		this.idExecutorModificacao = idExecutorModificacao;
	}
	public String getTipoModificacao() {
		return tipoModificacao;
	}
	public void setTipoModificacao(String tipoModificacao) {
		this.tipoModificacao = tipoModificacao;
	}
	public Double getHistoricoVersao() {
		return historicoVersao;
	}
	public void setHistoricoVersao(Double historicoVersao) {
		this.historicoVersao = historicoVersao;
	}
	public String getNomeProprietario() {
		return nomeProprietario;
	}
	public void setNomeProprietario(String nomeProprietario) {
		this.nomeProprietario = nomeProprietario;
	}
	public String getBaseLine() {
		return baseLine;
	}
	public void setBaseLine(String baseLine) {
		this.baseLine = baseLine;
	}
	public String getNomeExecutorModificacao() {
		return nomeExecutorModificacao;
	}
	public void setNomeExecutorModificacao(String nomeExecutorModificacao) {
		this.nomeExecutorModificacao = nomeExecutorModificacao;
	}
	public String getAlterarSituacao() {
		return alterarSituacao;
	}
	public void setAlterarSituacao(String alterarSituacao) {
		this.alterarSituacao = alterarSituacao;
	}
	public String getEmailSolicitante() {
		return emailSolicitante;
	}
	public void setEmailSolicitante(String emailSolicitante) {
		this.emailSolicitante = emailSolicitante;
	}
	
}
