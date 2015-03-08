package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class AnaliseTendenciasDTO implements IDto {
	
	private Date dataInicio;

	private Date dataFim;

	private Integer idContrato;

	private Integer idServico;

	private Integer idGrupoExecutor;

	private Integer idEmpregado;

	private Integer idTipoDemandaServico;

	private String urgencia;

	private String impacto;

	private Integer idItemConfiguracao;

	private Integer idCausaIncidente;

	private Integer qtdeCritica;
	
	private Integer idRelatorio;
	
	private String tipoRelatorio;

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

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public Integer getIdServico() {
		return idServico;
	}

	public void setIdServico(Integer idServico) {
		this.idServico = idServico;
	}

	public Integer getIdGrupoExecutor() {
		return idGrupoExecutor;
	}

	public void setIdGrupoExecutor(Integer idGrupoExecutor) {
		this.idGrupoExecutor = idGrupoExecutor;
	}

	public Integer getIdEmpregado() {
		return idEmpregado;
	}

	public void setIdEmpregado(Integer idEmpregado) {
		this.idEmpregado = idEmpregado;
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

	public Integer getIdItemConfiguracao() {
		return idItemConfiguracao;
	}

	public void setIdItemConfiguracao(Integer idItemConfiguracao) {
		this.idItemConfiguracao = idItemConfiguracao;
	}

	public Integer getIdCausaIncidente() {
		return idCausaIncidente;
	}

	public void setIdCausaIncidente(Integer idCausaIncidente) {
		this.idCausaIncidente = idCausaIncidente;
	}

	public Integer getQtdeCritica() {
		return qtdeCritica;
	}

	public void setQtdeCritica(Integer qtdeCritica) {
		this.qtdeCritica = qtdeCritica;
	}

	public Integer getIdRelatorio() {
		return idRelatorio;
	}

	public void setIdRelatorio(Integer idRelatorio) {
		this.idRelatorio = idRelatorio;
	}

	public String getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(String tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}

}
