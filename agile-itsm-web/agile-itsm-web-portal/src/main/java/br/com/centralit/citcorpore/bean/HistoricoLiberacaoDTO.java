package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

public class HistoricoLiberacaoDTO extends RequisicaoLiberacaoDTO {

	/**@author murilo.pacheco
	 * classe de historicos de alterações das liberações
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */
	private Integer idHistoricoLiberacao;
	private Timestamp dataHoraModificacao;
	private Integer idExecutorModificacao;
	private String tipoModificacao; // C - criação / U - update
	private Double historicoVersao;
	private String nomeProprietario;
	private String baseLine;
	private String nomeExecutorModificacao;


	public Integer getIdHistoricoLiberacao() {
		return idHistoricoLiberacao;
	}
	public void setIdHistoricoLiberacao(Integer idHistoricoLiberacao) {
		this.idHistoricoLiberacao = idHistoricoLiberacao;
	}

	public Timestamp getDataHoraModificacao() {
		return dataHoraModificacao;
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
}
