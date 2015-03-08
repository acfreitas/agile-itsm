package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class RequisicaoConhecimentoDTO implements IDto {
	private Integer idSolicitacaoServico;
	private Integer idConhecimento;
	private String obrigatorio;
	
	private String descricao;
	private String detalhe;
	

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getDetalhe() {
		return detalhe;
	}
	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}

	public Integer getIdSolicitacaoServico(){
		return this.idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer parm){
		this.idSolicitacaoServico = parm;
	}

	public Integer getIdConhecimento(){
		return this.idConhecimento;
	}
	public void setIdConhecimento(Integer parm){
		this.idConhecimento = parm;
	}

	public String getObrigatorio(){
		return this.obrigatorio;
	}
	public void setObrigatorio(String parm){
		this.obrigatorio = parm;
	}

}
