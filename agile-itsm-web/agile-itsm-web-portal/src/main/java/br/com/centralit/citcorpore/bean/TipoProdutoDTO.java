package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class TipoProdutoDTO implements IDto {
	private Integer idTipoProduto;
	private Integer idCategoria;
	private Integer idUnidadeMedida;
	private String nomeProduto;
	private String situacao;
	private String aceitaRequisicao;
	private String nomeUnidadeMedida;
	private String nomeCategoria;
	private String siglaUnidMedida;

	public Integer getIdCategoria(){
		return this.idCategoria;
	}
	public void setIdCategoria(Integer parm){
		this.idCategoria = parm;
	}

	public Integer getIdUnidadeMedida(){
		return this.idUnidadeMedida;
	}
	public void setIdUnidadeMedida(Integer parm){
		this.idUnidadeMedida = parm;
	}

	public String getNomeProduto(){
		return this.nomeProduto;
	}
	public void setNomeProduto(String parm){
		this.nomeProduto = parm;
	}
	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}

	public String getAceitaRequisicao(){
		return this.aceitaRequisicao;
	}
	public void setAceitaRequisicao(String parm){
		this.aceitaRequisicao = parm;
	}
    public String getNomeCategoria() {
        return nomeCategoria;
    }
    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
    public String getSiglaUnidMedida() {
        return siglaUnidMedida;
    }
    public void setSiglaUnidMedida(String siglaUnidMedida) {
        this.siglaUnidMedida = siglaUnidMedida;
    }
    public Integer getIdTipoProduto() {
        return idTipoProduto;
    }
    public void setIdTipoProduto(Integer idTipoProduto) {
        this.idTipoProduto = idTipoProduto;
    }
	public String getNomeUnidadeMedida() {
		return nomeUnidadeMedida;
	}
	public void setNomeUnidadeMedida(String nomeUnidadeMedida) {
		this.nomeUnidadeMedida = nomeUnidadeMedida;
	}

}
