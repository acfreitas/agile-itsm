/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
 * @author Vadoilo Damasceno
 * 
 */
public class EventoMonitoramentoDTO implements IDto {

	private static final long serialVersionUID = 594136415602018544L;

	private Integer idEventoMonitoramento;

	private String nomeEvento;

	private String detalhamento;

	private String criadoPor;

	private String modificadoPor;

	private Date dataCriacao;

	private Date ultimaModificacao;
	
	public static String staticCriadoPor;
	public static Date staticDataCriacao;
	
	/**
	 * @return the idEventoMonitoramento
	 */
	public Integer getIdEventoMonitoramento() {
		return idEventoMonitoramento;
	}

	/**
	 * @param idEventoMonitoramento
	 *            the idEventoMonitoramento to set
	 */
	public void setIdEventoMonitoramento(Integer idEventoMonitoramento) {
		this.idEventoMonitoramento = idEventoMonitoramento;
	}

	/**
	 * @return the nomeEvento
	 */
	public String getNomeEvento() {
		return nomeEvento;
	}

	/**
	 * @param nomeEvento
	 *            the nomeEvento to set
	 */
	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}

	/**
	 * @return the detalhamento
	 */
	public String getDetalhamento() {
		return detalhamento;
	}

	/**
	 * @param detalhamento
	 *            the detalhamento to set
	 */
	public void setDetalhamento(String detalhamento) {
		this.detalhamento = detalhamento;
	}

	/**
	 * @return the criadoPor
	 */
	public String getCriadoPor() {
		return criadoPor;
	}

	/**
	 * @param criadoPor
	 *            the criadoPor to set
	 */
	public void setCriadoPor(String criadoPor) {
		this.criadoPor = criadoPor;
	}

	/**
	 * @return the modificadoPor
	 */
	public String getModificadoPor() {
		return modificadoPor;
	}

	/**
	 * @param modificadoPor
	 *            the modificadoPor to set
	 */
	public void setModificadoPor(String modificadoPor) {
		this.modificadoPor = modificadoPor;
	}

	/**
	 * @return the dataCriacao
	 */
	public Date getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * @param dataCriacao
	 *            the dataCriacao to set
	 */
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * @return the ultimaModificacao
	 */
	public Date getUltimaModificacao() {
		return ultimaModificacao;
	}

	/**
	 * @param ultimaModificacao
	 *            the ultimaModificacao to set
	 */
	public void setUltimaModificacao(Date ultimaModificacao) {
		this.ultimaModificacao = ultimaModificacao;
	}

}
