package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ConhecimentoLiberacaoDTO implements IDto {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private Integer idRequisicaoLiberacao;
	private Integer idBaseConhecimento;
	
	public Integer getIdRequisicaoLiberacao() {
		return idRequisicaoLiberacao;
	}
	public void setIdRequisicaoLiberacao(Integer idRequisicaoLiberacao) {
		this.idRequisicaoLiberacao = idRequisicaoLiberacao;
	}
	public Integer getIdBaseConhecimento() {
		return idBaseConhecimento;
	}
	public void setIdBaseConhecimento(Integer idBaseConhecimento) {
		this.idBaseConhecimento = idBaseConhecimento;
	}




}
