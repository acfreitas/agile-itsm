package br.com.centralit.citcorpore.bean;

import br.com.centralit.bpm.dto.ObjetoNegocioFluxoDTO;

/**
 * 
 * @author geber.costa
 *
 */
public class SituacaoLiberacaoMudancaDTO extends ObjetoNegocioFluxoDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7812138132515297896L;
	private Integer idSituacaoLiberacaoMudanca;
	private String situacao;
	

	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public Integer getIdSituacaoLiberacaoMudanca() {
		return idSituacaoLiberacaoMudanca;
	}
	public void setIdSituacaoLiberacaoMudanca(Integer idSituacaoLiberacaoMudanca) {
		this.idSituacaoLiberacaoMudanca = idSituacaoLiberacaoMudanca;
	}
	
}
