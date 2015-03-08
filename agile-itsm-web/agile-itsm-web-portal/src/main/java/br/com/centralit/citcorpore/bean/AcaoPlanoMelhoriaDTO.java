package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class AcaoPlanoMelhoriaDTO implements IDto {
	private Integer idAcaoPlanoMelhoria;
	private Integer idPlanoMelhoria;
	private Integer idObjetivoPlanoMelhoria;
	private String tituloAcao;
	private String detalhamentoAcao;
	private java.sql.Date dataInicio;
	private java.sql.Date dataFim;
	private String responsavel;
	private java.sql.Date dataConclusao;
	private String criadoPor;
	private String modificadoPor;
	private java.sql.Date dataCriacao;
	private java.sql.Date ultModificacao;

	private String tituloObjetivoPlanoMelhoria;

	private String resultadoEsperadoPlanoMelhoria;

	private String medicaoPlanoMelhoria;

	private Integer sequencialObjetivo;

	private Integer sequencialAcao;

	public Integer getIdAcaoPlanoMelhoria() {
		return this.idAcaoPlanoMelhoria;
	}

	public void setIdAcaoPlanoMelhoria(Integer parm) {
		this.idAcaoPlanoMelhoria = parm;
	}

	public Integer getIdPlanoMelhoria() {
		return this.idPlanoMelhoria;
	}

	public void setIdPlanoMelhoria(Integer parm) {
		this.idPlanoMelhoria = parm;
	}

	public Integer getIdObjetivoPlanoMelhoria() {
		return this.idObjetivoPlanoMelhoria;
	}

	public void setIdObjetivoPlanoMelhoria(Integer parm) {
		this.idObjetivoPlanoMelhoria = parm;
	}

	public String getTituloAcao() {
		return this.tituloAcao;
	}

	public void setTituloAcao(String parm) {
		this.tituloAcao = parm;
	}

	public String getDetalhamentoAcao() {
		return this.detalhamentoAcao;
	}

	public void setDetalhamentoAcao(String parm) {
		this.detalhamentoAcao = parm;
	}

	public java.sql.Date getDataInicio() {
		return this.dataInicio;
	}

	public void setDataInicio(java.sql.Date parm) {
		this.dataInicio = parm;
	}

	public java.sql.Date getDataFim() {
		return this.dataFim;
	}

	public void setDataFim(java.sql.Date parm) {
		this.dataFim = parm;
	}

	public String getResponsavel() {
		return this.responsavel;
	}

	public void setResponsavel(String parm) {
		this.responsavel = parm;
	}

	public java.sql.Date getDataConclusao() {
		return this.dataConclusao;
	}

	public void setDataConclusao(java.sql.Date parm) {
		this.dataConclusao = parm;
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
	 * @return the sequencialObjetivo
	 */
	public Integer getSequencialObjetivo() {
		return sequencialObjetivo;
	}

	/**
	 * @param sequencialObjetivo
	 *            the sequencialObjetivo to set
	 */
	public void setSequencialObjetivo(Integer sequencialObjetivo) {
		this.sequencialObjetivo = sequencialObjetivo;
	}

	/**
	 * @return the sequencialAcao
	 */
	public Integer getSequencialAcao() {
		return sequencialAcao;
	}

	/**
	 * @param sequencialAcao
	 *            the sequencialAcao to set
	 */
	public void setSequencialAcao(Integer sequencialAcao) {
		this.sequencialAcao = sequencialAcao;
	}

	/**
	 * @return the resultadoEsperadoPlanoMelhoria
	 */
	public String getResultadoEsperadoPlanoMelhoria() {
		return resultadoEsperadoPlanoMelhoria;
	}

	/**
	 * @param resultadoEsperadoPlanoMelhoria
	 *            the resultadoEsperadoPlanoMelhoria to set
	 */
	public void setResultadoEsperadoPlanoMelhoria(String resultadoEsperadoPlanoMelhoria) {
		this.resultadoEsperadoPlanoMelhoria = resultadoEsperadoPlanoMelhoria;
	}

	/**
	 * @return the medicaoPlanoMelhoria
	 */
	public String getMedicaoPlanoMelhoria() {
		return medicaoPlanoMelhoria;
	}

	/**
	 * @param medicaoPlanoMelhoria
	 *            the medicaoPlanoMelhoria to set
	 */
	public void setMedicaoPlanoMelhoria(String medicaoPlanoMelhoria) {
		this.medicaoPlanoMelhoria = medicaoPlanoMelhoria;
	}

}
