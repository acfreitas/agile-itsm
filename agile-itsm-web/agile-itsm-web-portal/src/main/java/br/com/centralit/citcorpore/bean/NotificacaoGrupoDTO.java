package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class NotificacaoGrupoDTO implements IDto {

	private Integer idNotificacao;

	private Integer idGrupo;

	/**
	 * @return the idNotificacao
	 */
	public Integer getIdNotificacao() {
		return idNotificacao;
	}

	/**
	 * @param idNotificacao
	 *            the idNotificacao to set
	 */
	public void setIdNotificacao(Integer idNotificacao) {
		this.idNotificacao = idNotificacao;
	}

	/**
	 * @return the idGrupo
	 */
	public Integer getIdGrupo() {
		return idGrupo;
	}

	/**
	 * @param idGrupo
	 *            the idGrupo to set
	 */
	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

}
