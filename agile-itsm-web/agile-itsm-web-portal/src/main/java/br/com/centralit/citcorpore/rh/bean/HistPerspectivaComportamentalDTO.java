package br.com.centralit.citcorpore.rh.bean;

import java.sql.Date;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class HistPerspectivaComportamentalDTO extends MovimentacaoPessoalDTO {

	private Integer idhistPerspectivaComportamental;
	private String cmbCompetenciaComportamental;
	private String comportamento;
	private Integer idManualFuncao;
	private Date dataAlteracao;
	private Timestamp horaAlteracao;
	private int idUsuarioAlteracao;
	private Double versao;
	private Integer idhistManualFuncao;
	

	public Integer getIdhistManualFuncao() {
		return idhistManualFuncao;
	}

	public void setIdhistManualFuncao(Integer idhistManualFuncao) {
		this.idhistManualFuncao = idhistManualFuncao;
	}

	public Integer getIdhistPerspectivaComportamental() {
		return idhistPerspectivaComportamental;
	}

	public void setIdhistPerspectivaComportamental(Integer idhistPerspectivaComportamental) {
		this.idhistPerspectivaComportamental = idhistPerspectivaComportamental;
	}

	public String getCmbCompetenciaComportamental() {
		return cmbCompetenciaComportamental;
	}

	public void setCmbCompetenciaComportamental(String cmbCompetenciaComportamental) {
		this.cmbCompetenciaComportamental = cmbCompetenciaComportamental;
	}

	public String getComportamento() {
		return comportamento;
	}

	public void setComportamento(String comportamento) {
		this.comportamento = comportamento;
	}

	public Integer getIdManualFuncao() {
		return idManualFuncao;
	}

	public void setIdManualFuncao(Integer idManualFuncao) {
		this.idManualFuncao = idManualFuncao;
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