package br.com.centralit.citcorpore.bean;

import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacaoItemRequisicao;
import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilStrings;

public class CotacaoItemRequisicaoDTO implements IDto {
	private Integer idColetaPreco;
	private Integer idItemRequisicaoProduto;
	private Integer idParecer;
    private Integer idItemTrabalho;
    private Integer idSolicitacaoServico;
    private Integer idCotacao;
	private Double quantidade;
	private Double quantidadeEntregue;
	private String situacao;
	
	private Double percVariacaoPreco;
	
    private Integer idProduto;
    private Integer idMarca;  
    private Integer idUnidadeMedida;
    private String descricaoItem;
    
    private Integer idCategoriaProduto;
    private String codigoProduto;
    private String nomeProduto;
    private String nomeCategoria;
    private Integer idJustificativa;
    private String complementoJustificativa;

    private String siglaUnidadeMedida;
    private String descrSituacao;
    
    private String cpfCnpjFornecedor;
    private String nomeFornecedor;
    private String tipoFornecedor;
    
    private String aprovado;
    
    private String especificacoes;
    private Double preco;
    private Double valorDesconto;
    private Double valorAcrescimo;
    private Double valorFrete;
    private Integer prazoEntrega;
    private Double taxaJuros;
    private Double valorTotal;

    private Integer idItemCotacao;
    
    private String descricaoFmtHtml;
    
    private Integer idParecerAutorizacao;
    private String finalidade;
    
	public Integer getIdColetaPreco(){
		return this.idColetaPreco;
	}
	public void setIdColetaPreco(Integer parm){
		this.idColetaPreco = parm;
	}

	public Integer getIdItemRequisicaoProduto(){
		return this.idItemRequisicaoProduto;
	}
	public void setIdItemRequisicaoProduto(Integer parm){
		this.idItemRequisicaoProduto = parm;
	}

	public Integer getIdParecer(){
		return this.idParecer;
	}
	public void setIdParecer(Integer parm){
		this.idParecer = parm;
	}

	public Double getQuantidade(){
		return this.quantidade;
	}
	public void setQuantidade(Double parm){
		this.quantidade = parm;
	}
    public String getSituacao() {
        return situacao;
    }
    public Integer getIdSolicitacaoServico() {
        return idSolicitacaoServico;
    }
    public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
        this.idSolicitacaoServico = idSolicitacaoServico;
    }
    public Integer getIdProduto() {
        return idProduto;
    }
    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }
    public Integer getIdMarca() {
        return idMarca;
    }
    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }
    public Integer getIdUnidadeMedida() {
        return idUnidadeMedida;
    }
    public void setIdUnidadeMedida(Integer idUnidadeMedida) {
        this.idUnidadeMedida = idUnidadeMedida;
    }
    public String getDescricaoItem() {
        return descricaoItem;
    }
    public void setDescricaoItem(String descricaoItem) {
        this.descricaoItem = descricaoItem;
    }
    public Integer getIdCategoriaProduto() {
        return idCategoriaProduto;
    }
    public void setIdCategoriaProduto(Integer idCategoriaProduto) {
        this.idCategoriaProduto = idCategoriaProduto;
    }
    public String getCodigoProduto() {
        return codigoProduto;
    }
    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }
    public String getNomeProduto() {
        return nomeProduto;
    }
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
    public String getComplementoJustificativa() {
        return complementoJustificativa;
    }
    public void setComplementoJustificativa(String complementoJustificativa) {
        this.complementoJustificativa = complementoJustificativa;
    }
    public String getSiglaUnidadeMedida() {
        return siglaUnidadeMedida;
    }
    public void setSiglaUnidadeMedida(String siglaUnidadeMedida) {
        this.siglaUnidadeMedida = siglaUnidadeMedida;
    }
    public String getDescrSituacao() {
        return descrSituacao;
    }
    public void setDescrSituacao(String descrSituacao) {
        this.descrSituacao = descrSituacao;
    }
    public String getCpfCnpjFornecedor() {
        return cpfCnpjFornecedor;
    }
    public void setCpfCnpjFornecedor(String cpfCnpjFornecedor) {
        this.cpfCnpjFornecedor = cpfCnpjFornecedor;
    }
    public String getNomeFornecedor() {
        return nomeFornecedor;
    }
    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }
    public String getNomeCategoria() {
        return nomeCategoria;
    }
    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
    public void setSituacao(String parm){
        this.descrSituacao = "";
        this.situacao = parm;
        try {
            if (this.situacao != null)
                this.descrSituacao = SituacaoCotacaoItemRequisicao.valueOf(this.situacao.trim()).getDescricao();
        } catch (Exception e) {
        }       
    }
    public String getAprovado() {
        return aprovado;
    }
    public void setAprovado(String aprovado) {
        this.aprovado = aprovado;
    }
    public String getEspecificacoes() {
        return especificacoes;
    }
    public void setEspecificacoes(String especificacoes) {
        this.especificacoes = especificacoes;
    }
    public Double getPreco() {
        return preco;
    }
    public void setPreco(Double preco) {
        this.preco = preco;
    }
    public Double getValorDesconto() {
        return valorDesconto;
    }
    public void setValorDesconto(Double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }
    public Double getValorAcrescimo() {
        return valorAcrescimo;
    }
    public void setValorAcrescimo(Double valorAcrescimo) {
        this.valorAcrescimo = valorAcrescimo;
    }
    public Double getValorFrete() {
        return valorFrete;
    }
    public void setValorFrete(Double valorFrete) {
        this.valorFrete = valorFrete;
    }
    public Integer getPrazoEntrega() {
        return prazoEntrega;
    }
    public void setPrazoEntrega(Integer prazoEntrega) {
        this.prazoEntrega = prazoEntrega;
    }
    public Double getTaxaJuros() {
        return taxaJuros;
    }
    public void setTaxaJuros(Double taxaJuros) {
        this.taxaJuros = taxaJuros;
    }
    public Integer getIdJustificativa() {
        return idJustificativa;
    }
    public void setIdJustificativa(Integer idJustificativa) {
        this.idJustificativa = idJustificativa;
    }
    public Integer getIdCotacao() {
        return idCotacao;
    }
    public void setIdCotacao(Integer idCotacao) {
        this.idCotacao = idCotacao;
    }
    public Integer getIdItemTrabalho() {
        return idItemTrabalho;
    }
    public void setIdItemTrabalho(Integer idItemTrabalho) {
        this.idItemTrabalho = idItemTrabalho;
    }
    public Double getQuantidadeEntregue() {
        return quantidadeEntregue;
    }
    public void setQuantidadeEntregue(Double quantidadeEntregue) {
        this.quantidadeEntregue = quantidadeEntregue;
    }
    public Double getPercVariacaoPreco() {
        return percVariacaoPreco;
    }
    public void setPercVariacaoPreco(Double percVariacaoPreco) {
        this.percVariacaoPreco = percVariacaoPreco;
    }
    public Double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
	public String getDescricaoFmtHtml() {
		descricaoFmtHtml = "";
    	if (UtilStrings.isNotVazio(this.especificacoes))
        	descricaoFmtHtml += "<p><b>Especificações:</b> "+this.especificacoes+"</p>";
    	if (UtilStrings.isNotVazio(this.nomeFornecedor)) {
        	descricaoFmtHtml += "<p><b>CPF/CNPJ Fornecedor:</b> ";
        	if (UtilStrings.nullToVazio(this.tipoFornecedor).equalsIgnoreCase("J"))
        		descricaoFmtHtml += UtilFormatacao.formataCnpj(cpfCnpjFornecedor);
        	else
        		descricaoFmtHtml += UtilFormatacao.formataCpf(cpfCnpjFornecedor);
        	descricaoFmtHtml += "<br><b>Nome Fornecedor:</b> "+this.nomeFornecedor+"</p>";
    	}
       	//descricaoFmtHtml += "<p><b>Situação:</b> "+this.descrSituacao+"</p>";
    	return descricaoFmtHtml;
	}
	
	public void setDescricaoFmtHtml(String descricaoFmtHtml) {
		this.descricaoFmtHtml = descricaoFmtHtml;
	}
	public String getTipoFornecedor() {
		return tipoFornecedor;
	}
	public void setTipoFornecedor(String tipoFornecedor) {
		this.tipoFornecedor = tipoFornecedor;
	}
	public Integer getIdItemCotacao() {
		return idItemCotacao;
	}
	public void setIdItemCotacao(Integer idItemCotacao) {
		this.idItemCotacao = idItemCotacao;
	}
	public Integer getIdParecerAutorizacao() {
		return idParecerAutorizacao;
	}
	public void setIdParecerAutorizacao(Integer idParecerAutorizacao) {
		this.idParecerAutorizacao = idParecerAutorizacao;
	}
	public String getFinalidade() {
		return finalidade;
	}
	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
	}

}
