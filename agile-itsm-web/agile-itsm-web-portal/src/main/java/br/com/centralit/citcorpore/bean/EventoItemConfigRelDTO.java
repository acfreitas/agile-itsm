package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class EventoItemConfigRelDTO implements IDto {
	
	private Integer idItemConfiguracao;
	private Integer idEvento;

	public Integer getIdItemConfiguracao() {
		return idItemConfiguracao;
	}

	public void setIdItemConfiguracao(Integer idItemConfiguracao) {
		this.idItemConfiguracao = idItemConfiguracao;
	}

	public Integer getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idItemConfiguracao == null) ? 0 : idItemConfiguracao
						.hashCode());
		return result;
	}

	/**
	 * Gerado para comparar se tem Item de configuração repetido na lista de disparo de evento
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventoItemConfigRelDTO other = (EventoItemConfigRelDTO) obj;
		if (idItemConfiguracao == null) {
			if (other.idItemConfiguracao != null)
				return false;
		} else if (!idItemConfiguracao.equals(other.idItemConfiguracao))
			return false;
		return true;
	}

}
