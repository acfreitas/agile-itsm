package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class ItemPrestacaoContasViagemDTO implements IDto {

	private Integer idItemPrestContasViagem;
	private Integer idPrestacaoContasViagem;
	private Integer idItemDespesaViagem;
	private Integer idFornecedor;
	private Date data;
	private String nomeFornecedor;
	private String numeroDocumento;
	private String descricao;
    private Double valor;
    private String valorAux;
    
	private static final long serialVersionUID = 1L;
    

	public Integer getIdItemPrestContasViagem() {
		return idItemPrestContasViagem;
	}
	public void setIdItemPrestContasViagem(Integer idItemPrestContasViagem) {
		this.idItemPrestContasViagem = idItemPrestContasViagem;
	}
	public Integer getIdPrestacaoContasViagem() {
		return idPrestacaoContasViagem;
	}
	public void setIdPrestacaoContasViagem(Integer idPrestacaoContasViagem) {
		this.idPrestacaoContasViagem = idPrestacaoContasViagem;
	}
	public Integer getIdItemDespesaViagem() {
		return idItemDespesaViagem;
	}
	public void setIdItemDespesaViagem(Integer idItemDespesaViagem) {
		this.idItemDespesaViagem = idItemDespesaViagem;
	}
	public Integer getIdFornecedor() {
		return idFornecedor;
	}
	public void setIdFornecedor(Integer idFornecedor) {
		this.idFornecedor = idFornecedor;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getNomeFornecedor() {
		return nomeFornecedor;
	}
	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
		this.valorAux = valor.toString();
	}
	public String getValorAux() {
		return valorAux;
	}
	public void setValorAux(String valorAux) {
		this.valorAux = valorAux;
	}
}
