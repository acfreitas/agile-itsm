package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ConhecimentoICDTO implements IDto {

	private static final long serialVersionUID = 1L;

	private Integer idItemConfiguracao;

	private Integer idBaseConhecimento;

	/**
	 * @return the idItemConfiguracao
	 */
	public Integer getIdItemConfiguracao() {
		return idItemConfiguracao;
	}

	/**
	 * @param idItemConfiguracao
	 *            the idItemConfiguracao to set
	 */
	public void setIdItemConfiguracao(Integer idItemConfiguracao) {
		this.idItemConfiguracao = idItemConfiguracao;
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
