package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ControleRendimentoGrupoDTO implements IDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idControleRendimentoGrupo;
	private Integer idControleRendimento;
	private Integer idGrupo;
	
	public Integer getIdControleRendimentoGrupo() {
		return idControleRendimentoGrupo;
	}
	public void setIdControleRendimentoGrupo(Integer idControleRendimentoGrupo) {
		this.idControleRendimentoGrupo = idControleRendimentoGrupo;
	}
	public Integer getIdControleRendimento() {
		return idControleRendimento;
	}
	public void setIdControleRendimento(Integer idControleRendimento) {
		this.idControleRendimento = idControleRendimento;
	}
	public Integer getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}
	
	
}
