package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.util.Collection;

import br.com.centralit.citcorpore.util.Enumerados.SituacaoPedidoCompra;
import br.com.citframework.dto.IDto;

public class PedidoCompraDTO implements IDto {
	private Integer idPedido;
	private Integer idCotacao;
	private Integer idEmpresa;
	private Integer idFornecedor;
	private Date dataPedido;
	private String numeroPedido;
	private String identificacaoEntrega;
	private Double valorFrete;
	private Double valorSeguro;
	private Double outrasDespesas;
	private String numeroNF;
	private Integer idEnderecoEntrega;
	private Date dataEntrega;
	private Date dataPrevistaEntrega;
	private String situacao;
	private String observacoes;
	
	private Integer idColetaPreco;
	private Integer idItemPedido;
	private Double quantidade;
    private String descrSituacao;
	
    private UsuarioDTO usuarioDto;
    
	private String nomeFornecedor;
	private Collection<UploadDTO> anexos;
	private Collection<ItemPedidoCompraDTO> colItens;
	private Collection<InspecaoPedidoCompraDTO> colInspecao;

	public Integer getIdPedido(){
		return this.idPedido;
	}
	public void setIdPedido(Integer parm){
		this.idPedido = parm;
	}

	public Integer getIdCotacao(){
		return this.idCotacao;
	}
	public void setIdCotacao(Integer parm){
		this.idCotacao = parm;
	}

	public Integer getIdEmpresa(){
		return this.idEmpresa;
	}
	public void setIdEmpresa(Integer parm){
		this.idEmpresa = parm;
	}

	public Integer getIdFornecedor(){
		return this.idFornecedor;
	}
	public void setIdFornecedor(Integer parm){
		this.idFornecedor = parm;
	}

	public Date getDataPedido(){
		return this.dataPedido;
	}
	public void setDataPedido(Date parm){
		this.dataPedido = parm;
	}

	public String getNumeroPedido(){
		return this.numeroPedido;
	}
	public void setNumeroPedido(String parm){
		this.numeroPedido = parm;
	}

	public String getIdentificacaoEntrega(){
		return this.identificacaoEntrega;
	}
	public void setIdentificacaoEntrega(String parm){
		this.identificacaoEntrega = parm;
	}

	public Double getValorFrete(){
		return this.valorFrete;
	}
	public void setValorFrete(Double parm){
		this.valorFrete = parm;
	}

	public Double getValorSeguro(){
		return this.valorSeguro;
	}
	public void setValorSeguro(Double parm){
		this.valorSeguro = parm;
	}

	public Double getOutrasDespesas(){
		return this.outrasDespesas;
	}
	public void setOutrasDespesas(Double parm){
		this.outrasDespesas = parm;
	}

	public String getNumeroNF(){
		return this.numeroNF;
	}
	public void setNumeroNF(String parm){
		this.numeroNF = parm;
	}

	public Integer getIdEnderecoEntrega(){
		return this.idEnderecoEntrega;
	}
	public void setIdEnderecoEntrega(Integer parm){
		this.idEnderecoEntrega = parm;
	}

	public Date getDataEntrega(){
		return this.dataEntrega;
	}
	public void setDataEntrega(Date parm){
		this.dataEntrega = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}
    public String getNomeFornecedor() {
        return nomeFornecedor;
    }
    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }
    public Collection<UploadDTO> getAnexos() {
        return anexos;
    }
    public void setAnexos(Collection<UploadDTO> anexos) {
        this.anexos = anexos;
    }
    public Date getDataPrevistaEntrega() {
        return dataPrevistaEntrega;
    }
    public void setDataPrevistaEntrega(Date dataPrevistaEntrega) {
        this.dataPrevistaEntrega = dataPrevistaEntrega;
    }
    public Integer getIdColetaPreco() {
        return idColetaPreco;
    }
    public void setIdColetaPreco(Integer idColetaPreco) {
        this.idColetaPreco = idColetaPreco;
    }
    public Integer getIdItemPedido() {
        return idItemPedido;
    }
    public void setIdItemPedido(Integer idItemPedido) {
        this.idItemPedido = idItemPedido;
    }
    public Double getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }
    public Collection<ItemPedidoCompraDTO> getColItens() {
        return colItens;
    }
    public void setColItens(Collection<ItemPedidoCompraDTO> colItens) {
        this.colItens = colItens;
    }
    public String getDescrSituacao() {
        descrSituacao = "";
        if (situacao != null)
            descrSituacao = SituacaoPedidoCompra.valueOf(situacao).getDescricao();
        return descrSituacao;
    }
    public void setDescrSituacao(String descrSituacao) {
        this.descrSituacao = descrSituacao;
    }
    public String getObservacoes() {
        return observacoes;
    }
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    public Collection<InspecaoPedidoCompraDTO> getColInspecao() {
        return colInspecao;
    }
    public void setColInspecao(Collection<InspecaoPedidoCompraDTO> colInspecao) {
        this.colInspecao = colInspecao;
    }
    public UsuarioDTO getUsuarioDto() {
        return usuarioDto;
    }
    public void setUsuarioDto(UsuarioDTO usuarioDto) {
        this.usuarioDto = usuarioDto;
    }

}
