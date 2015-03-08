package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class RequisicaoAtitudeIndividualDTO implements IDto {
	private Integer idSolicitacaoServico;
	private Integer idAtitudeIndividual;
	private String obrigatorio;
	
	private String detalhe;
	private String descricao;
	
	
	public String getDetalhe() {
		return detalhe;
	}
	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getIdSolicitacaoServico(){
		return this.idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer parm){
		this.idSolicitacaoServico = parm;
	}

	public Integer getIdAtitudeIndividual(){
		return this.idAtitudeIndividual;
	}
	public void setIdAtitudeIndividual(Integer parm){
		this.idAtitudeIndividual = parm;
	}

	public String getObrigatorio(){
		return this.obrigatorio;
	}
	public void setObrigatorio(String parm){
		this.obrigatorio = parm;
	}

}
