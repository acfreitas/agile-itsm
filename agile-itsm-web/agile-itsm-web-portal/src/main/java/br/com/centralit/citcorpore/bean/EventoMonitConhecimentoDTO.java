/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author Vadoilo Damasceno
 * 
 */
public class EventoMonitConhecimentoDTO implements IDto {

	private static final long serialVersionUID = -2467321613106192462L;

	private Integer idEventoMonitoramento;

	private Integer idBaseConhecimento;

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
	 * @return the idBaseConhecimento
	 */
	public Integer getIdBaseConhecimento() {
		return idBaseConhecimento;
	}

	/**
	 * @param idBaseConhecimento
	 *            the idBaseConhecimento to set
	 */
	public void setIdBaseConhecimento(Integer idBaseConhecimento) {
		this.idBaseConhecimento = idBaseConhecimento;
	}

}
