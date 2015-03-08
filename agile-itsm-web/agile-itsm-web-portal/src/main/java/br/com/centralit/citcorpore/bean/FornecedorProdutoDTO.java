package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class FornecedorProdutoDTO implements IDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4527722170327910788L;

	private Integer idFornecedorProduto;

	private Integer idFornecedor;

	private Integer idTipoProduto;

	private Integer idMarca;

	private Date dataInicio;

	private Date dataFim;

	

	public Integer getIdFornecedor() {
		return this.idFornecedor;
	}

	public void setIdFornecedor(Integer parm) {
		this.idFornecedor = parm;
	}

	public Date getDataInicio() {
		return this.dataInicio;
	}

	public void setDataInicio(Date parm) {
		this.dataInicio = parm;
	}

	public Date getDataFim() {
		return this.dataFim;
	}

	public void setDataFim(Date parm) {
		this.dataFim = parm;
	}

	public Integer getIdTipoProduto() {
		return idTipoProduto;
	}

	public void setIdTipoProduto(Integer idTipoProduto) {
		this.idTipoProduto = idTipoProduto;
	}

	public Integer getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(Integer idMarca) {
		this.idMarca = idMarca;
	}

	public Integer getIdFornecedorProduto() {
		return idFornecedorProduto;
	}

	public void setIdFornecedorProduto(Integer idFornecedorProduto) {
		this.idFornecedorProduto = idFornecedorProduto;
	}

}
