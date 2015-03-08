package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class ObjetivoMonitoramentoDTO implements IDto {
	private Integer idObjetivoMonitoramento;
	private Integer idObjetivoPlanoMelhoria;
	private String tituloMonitoramento;
	private String fatorCriticoSucesso;
	private String kpi;
	private String metrica;
	private String medicao;
	private String relatorios;
	private String responsavel;
	private String criadoPor;
	private String modificadoPor;
	private java.sql.Date dataCriacao;
	private java.sql.Date ultModificacao;

	private String tituloObjetivoPlanoMelhoria;
	
	private Integer sequecialObjetivoMonitoramento;
	
	

	public Integer getIdObjetivoMonitoramento() {
		return this.idObjetivoMonitoramento;
	}

	public void setIdObjetivoMonitoramento(Integer parm) {
		this.idObjetivoMonitoramento = parm;
	}

	public Integer getIdObjetivoPlanoMelhoria() {
		return this.idObjetivoPlanoMelhoria;
	}

	public void setIdObjetivoPlanoMelhoria(Integer parm) {
		this.idObjetivoPlanoMelhoria = parm;
	}

	public String getTituloMonitoramento() {
		return this.tituloMonitoramento;
	}

	public void setTituloMonitoramento(String parm) {
		this.tituloMonitoramento = parm;
	}

	public String getFatorCriticoSucesso() {
		return this.fatorCriticoSucesso;
	}

	public void setFatorCriticoSucesso(String parm) {
		this.fatorCriticoSucesso = parm;
	}

	public String getKpi() {
		return this.kpi;
	}

	public void setKpi(String parm) {
		this.kpi = parm;
	}

	public String getMetrica() {
		return this.metrica;
	}

	public void setMetrica(String parm) {
		this.metrica = parm;
	}

	public String getMedicao() {
		return this.medicao;
	}

	public void setMedicao(String parm) {
		this.medicao = parm;
	}

	public String getRelatorios() {
		return this.relatorios;
	}

	public void setRelatorios(String parm) {
		this.relatorios = parm;
	}

	public String getResponsavel() {
		return this.responsavel;
	}

	public void setResponsavel(String parm) {
		this.responsavel = parm;
	}

	public String getCriadoPor() {
		return this.criadoPor;
	}

	public void setCriadoPor(String parm) {
		this.criadoPor = parm;
	}

	public String getModificadoPor() {
		return this.modificadoPor;
	}

	public void setModificadoPor(String parm) {
		this.modificadoPor = parm;
	}

	public java.sql.Date getDataCriacao() {
		return this.dataCriacao;
	}

	public void setDataCriacao(java.sql.Date parm) {
		this.dataCriacao = parm;
	}

	public java.sql.Date getUltModificacao() {
		return this.ultModificacao;
	}

	public void setUltModificacao(java.sql.Date parm) {
		this.ultModificacao = parm;
	}

	/**
	 * @return the tituloObjetivoPlanoMelhoria
	 */
	public String getTituloObjetivoPlanoMelhoria() {
		return tituloObjetivoPlanoMelhoria;
	}

	/**
	 * @param tituloObjetivoPlanoMelhoria
	 *            the tituloObjetivoPlanoMelhoria to set
	 */
	public void setTituloObjetivoPlanoMelhoria(String tituloObjetivoPlanoMelhoria) {
		this.tituloObjetivoPlanoMelhoria = tituloObjetivoPlanoMelhoria;
	}

	/**
	 * @return the sequecialObjetivoMonitoramento
	 */
	public Integer getSequecialObjetivoMonitoramento() {
		return sequecialObjetivoMonitoramento;
	}

	/**
	 * @param sequecialObjetivoMonitoramento the sequecialObjetivoMonitoramento to set
	 */
	public void setSequecialObjetivoMonitoramento(Integer sequecialObjetivoMonitoramento) {
		this.sequecialObjetivoMonitoramento = sequecialObjetivoMonitoramento;
	}

}
