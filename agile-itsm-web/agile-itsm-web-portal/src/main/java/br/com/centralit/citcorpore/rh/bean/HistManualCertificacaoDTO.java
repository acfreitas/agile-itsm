package br.com.centralit.citcorpore.rh.bean;

import java.sql.Date;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class HistManualCertificacaoDTO extends MovimentacaoPessoalDTO {

	private Integer idhistManualCertificacao;
	private String descricao;
	private String detalhe;
	private Integer idManualFuncao;
	private String RAouRF;
	private Date dataAlteracao;
	private Timestamp horaAlteracao;
	private Integer idUsuarioAlteracao;
	private Double versao;
	private Integer idhistManualFuncao;
	

	public Integer getIdhistManualFuncao() {
		return idhistManualFuncao;
	}

	public void setIdhistManualFuncao(Integer idhistManualFuncao) {
		this.idhistManualFuncao = idhistManualFuncao;
	}

	public Integer getIdhistManualCertificacao() {
		return idhistManualCertificacao;
	}

	public void setIdhistManualCertificacao(Integer idhistManualCertificacao) {
		this.idhistManualCertificacao = idhistManualCertificacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDetalhe() {
		return detalhe;
	}

	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}

	public Integer getIdManualFuncao() {
		return idManualFuncao;
	}

	public void setIdManualFuncao(Integer idManualFuncao) {
		this.idManualFuncao = idManualFuncao;
	}

	public String getRAouRF() {
		return RAouRF;
	}

	public void setRAouRF(String rAouRF) {
		RAouRF = rAouRF;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Timestamp getHoraAlteracao() {
		return horaAlteracao;
	}

	public void setHoraAlteracao(Timestamp horaAlteracao) {
		this.horaAlteracao = horaAlteracao;
	}

	public Integer getIdUsuarioAlteracao() {
		return idUsuarioAlteracao;
	}

	public void setIdUsuarioAlteracao(Integer idUsuarioAlteracao) {
		this.idUsuarioAlteracao = idUsuarioAlteracao;
	}

	public Double getVersao() {
		return versao;
	}

	public void setVersao(Double versao) {
		this.versao = versao;
	}

}