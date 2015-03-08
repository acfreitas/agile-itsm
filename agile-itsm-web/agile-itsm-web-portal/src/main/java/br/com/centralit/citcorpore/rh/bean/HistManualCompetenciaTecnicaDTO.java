package br.com.centralit.citcorpore.rh.bean;

import java.sql.Date;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class HistManualCompetenciaTecnicaDTO extends MovimentacaoPessoalDTO {

	private Integer idhistmanualcompetenciatecnica;
	private String descricao;
	private String situacao;
	private Integer idManualFuncao;
	private String idNivelCompetenciaAcesso;
	private String idNivelCompetenciaFuncao;
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

	public Integer getIdhistmanualcompetenciatecnica() {
		return idhistmanualcompetenciatecnica;
	}

	public void setIdhistmanualcompetenciatecnica(Integer idhistmanualcompetenciatecnica) {
		this.idhistmanualcompetenciatecnica = idhistmanualcompetenciatecnica;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Integer getIdManualFuncao() {
		return idManualFuncao;
	}

	public void setIdManualFuncao(Integer idManualFuncao) {
		this.idManualFuncao = idManualFuncao;
	}

	public String getIdNivelCompetenciaAcesso() {
		return idNivelCompetenciaAcesso;
	}

	public void setIdNivelCompetenciaAcesso(String idNivelCompetenciaAcesso) {
		this.idNivelCompetenciaAcesso = idNivelCompetenciaAcesso;
	}

	public String getIdNivelCompetenciaFuncao() {
		return idNivelCompetenciaFuncao;
	}

	public void setIdNivelCompetenciaFuncao(String idNivelCompetenciaFuncao) {
		this.idNivelCompetenciaFuncao = idNivelCompetenciaFuncao;
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