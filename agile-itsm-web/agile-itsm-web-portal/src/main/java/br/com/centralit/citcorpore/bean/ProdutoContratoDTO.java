package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilStrings;

public class ProdutoContratoDTO implements IDto {
	private Integer idProdutoContrato;
	private Integer idContrato;
	private String nomeProduto;
	
	private Double qtde;
	private String deleted;

	public Integer getIdProdutoContrato(){
		return this.idProdutoContrato;
	}
	public void setIdProdutoContrato(Integer parm){
		this.idProdutoContrato = parm;
	}

	public Integer getIdContrato(){
		return this.idContrato;
	}
	public void setIdContrato(Integer parm){
		this.idContrato = parm;
	}

	public String getNomeProduto(){
		return this.nomeProduto;
	}
	public String getNomeProdutoHTMLEncoded(){
		return UtilHTML.encodeHTML(UtilStrings.nullToVazio(this.nomeProduto));
	}
	public void setNomeProduto(String parm){
		this.nomeProduto = parm;
	}
	public Double getQtde() {
		return qtde;
	}
	public void setQtde(Double qtde) {
		this.qtde = qtde;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

}
