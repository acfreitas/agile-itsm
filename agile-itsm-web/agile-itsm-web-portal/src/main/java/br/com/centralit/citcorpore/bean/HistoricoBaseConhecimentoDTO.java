/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

/**
 * @author Vadoilo Damasceno
 * 
 */
public class HistoricoBaseConhecimentoDTO extends BaseConhecimentoDTO implements IDto {

	private static final long serialVersionUID = 1L;

	private Integer idUsuarioAlteracao;

	private Timestamp dataHoraAlteracao;

	/**
	 * @return the idUsuarioAlteracao
	 */
	public Integer getIdUsuarioAlteracao() {
		return idUsuarioAlteracao;
	}

	/**
	 * @param idUsuarioAlteracao
	 *            the idUsuarioAlteracao to set
	 */
	public void setIdUsuarioAlteracao(Integer idUsuarioAlteracao) {
		this.idUsuarioAlteracao = idUsuarioAlteracao;
	}

	/**
	 * @return the dataHoraAlteracao
	 */
	public Timestamp getDataHoraAlteracao() {
		return dataHoraAlteracao;
	}

	/**
	 * @param dataHoraAlteracao
	 *            the dataHoraAlteracao to set
	 */
	public void setDataHoraAlteracao(Timestamp dataHoraAlteracao) {
		this.dataHoraAlteracao = dataHoraAlteracao;
	}

}
