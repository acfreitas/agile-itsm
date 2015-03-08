package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class ObjetivoPlanoMelhoriaDTO implements IDto {
	private Integer idObjetivoPlanoMelhoria;
	private Integer idPlanoMelhoria;
	private Integer idPlanoMelhoriaAux1;
	private String tituloObjetivo;
	private String detalhamento;
	private String resultadoEsperado;
	private String medicao;
	private String responsavel;
	private String criadoPor;
	private String modificadoPor;
	private java.sql.Date dataCriacao;
	private java.sql.Date ultModificacao;

	private Collection<AcaoPlanoMelhoriaDTO> listAcaoPlanoMelhoria;

	private Collection<ObjetivoPlanoMelhoriaDTO> listObjetivosPlanoMelhoria;

	private Collection<ObjetivoMonitoramentoDTO> listObjetivosMonitoramento;
	
	//private Object listObjetivosMonitoramento;

	private Integer sequencialObjetivo;

	public Integer getIdObjetivoPlanoMelhoria() {
		return this.idObjetivoPlanoMelhoria;
	}

	public void setIdObjetivoPlanoMelhoria(Integer parm) {
		this.idObjetivoPlanoMelhoria = parm;
	}

	public Integer getIdPlanoMelhoria() {
		return this.idPlanoMelhoria;
	}

	public void setIdPlanoMelhoria(Integer parm) {
		this.idPlanoMelhoria = parm;
	}

	public String getTituloObjetivo() {
		return this.tituloObjetivo;
	}

	public void setTituloObjetivo(String parm) {
		this.tituloObjetivo = parm;
	}

	public String getDetalhamento() {
		return this.detalhamento;
	}

	public void setDetalhamento(String parm) {
		this.detalhamento = parm;
	}

	public String getResultadoEsperado() {
		return this.resultadoEsperado;
	}

	public void setResultadoEsperado(String parm) {
		this.resultadoEsperado = parm;
	}

	public String getMedicao() {
		return this.medicao;
	}

	public void setMedicao(String parm) {
		this.medicao = parm;
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
	 * @return the listAcaoPlanoMelhoria
	 */
	public Collection<AcaoPlanoMelhoriaDTO> getListAcaoPlanoMelhoria() {
		return listAcaoPlanoMelhoria;
	}

	/**
	 * @param listAcaoPlanoMelhoria
	 *            the listAcaoPlanoMelhoria to set
	 */
	public void setListAcaoPlanoMelhoria(Collection<AcaoPlanoMelhoriaDTO> listAcaoPlanoMelhoria) {
		this.listAcaoPlanoMelhoria = listAcaoPlanoMelhoria;
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
	 * @return the listObjetivosPlanoMelhoria
	 */
	public Collection<ObjetivoPlanoMelhoriaDTO> getListObjetivosPlanoMelhoria() {
		return listObjetivosPlanoMelhoria;
	}

	/**
	 * @param listObjetivosPlanoMelhoria
	 *            the listObjetivosPlanoMelhoria to set
	 */
	public void setListObjetivosPlanoMelhoria(Collection<ObjetivoPlanoMelhoriaDTO> listObjetivosPlanoMelhoria) {
		this.listObjetivosPlanoMelhoria = listObjetivosPlanoMelhoria;
	}

	public Integer getIdPlanoMelhoriaAux1() {
		return idPlanoMelhoriaAux1;
	}

	public void setIdPlanoMelhoriaAux1(Integer idPlanoMelhoriaAux1) {
		this.idPlanoMelhoriaAux1 = idPlanoMelhoriaAux1;
	}

	/**
	 * @return the listObjetivosMonitoramento
	 */
	public Collection<ObjetivoMonitoramentoDTO> getListObjetivosMonitoramento() {
		return listObjetivosMonitoramento;
	}

	/**
	 * @param listObjetivosMonitoramento the listObjetivosMonitoramento to set
	 */
	public void setListObjetivosMonitoramento(Collection<ObjetivoMonitoramentoDTO> listObjetivosMonitoramento) {
		this.listObjetivosMonitoramento = listObjetivosMonitoramento;
	}


}
