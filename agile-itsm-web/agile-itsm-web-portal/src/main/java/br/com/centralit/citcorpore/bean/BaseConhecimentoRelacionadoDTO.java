/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author Vadoilo Damasceno
 * 
 */
public class BaseConhecimentoRelacionadoDTO implements IDto {

	private static final long serialVersionUID = -6051170077872987221L;

	private Integer idBaseConhecimento;

	private Integer idBaseConhecimentoRelacionado;

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

	/**
	 * @return the idBaseConhecimentoRelacionado
	 */
	public Integer getIdBaseConhecimentoRelacionado() {
		return idBaseConhecimentoRelacionado;
	}

	/**
	 * @param idBaseConhecimentoRelacionado
	 *            the idBaseConhecimentoRelacionado to set
	 */
	public void setIdBaseConhecimentoRelacionado(Integer idBaseConhecimentoRelacionado) {
		this.idBaseConhecimentoRelacionado = idBaseConhecimentoRelacionado;
	}

}
