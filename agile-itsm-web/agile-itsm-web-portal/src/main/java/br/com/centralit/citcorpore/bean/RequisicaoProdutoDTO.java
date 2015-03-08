package br.com.centralit.citcorpore.bean;

import java.util.Collection;


public class RequisicaoProdutoDTO extends SolicitacaoServicoDTO {
    public static final String ACAO_CRIACAO = "criacao";
    public static final String ACAO_VALIDACAO = "validacao";
    public static final String ACAO_AUTORIZACAO = "autorizacao";
    public static final String ACAO_ACOMPANHAMENTO = "acompanhamento";
    public static final String ACAO_APROVACAO = "aprovacao";
    public static final String ACAO_INSPECAO = "inspecao";   
    public static final String ACAO_GARANTIA = "garantia";  
    
	private Integer idProjeto;
	private Integer idCentroCusto;
	private String finalidade;
	private Integer idEnderecoEntrega;
	private Integer idCategoriaProduto;
	private Integer idProduto;
	private String tipoIdentificacaoItem;
	private String rejeitada;
	private String exigeNovaAprovacao;
	private String itemAlterado;
	
	private Integer idFornecedorColeta;
	private Integer idItemColeta;
	private Integer idItemCotacao;
	private Integer idEntrega;
	
	private String acao;
	
	private Double valorAprovado;
	
	private Integer idColetaPreco;
	private Integer idItemRequisicaoProduto;
	
	private String centroCusto;
	private String projeto;
	
	private Collection<ItemRequisicaoProdutoDTO> itensRequisicao;
    private Collection<ItemRequisicaoProdutoDTO> itensValidos;
    private Collection<CotacaoItemRequisicaoDTO> itensCotacao;
    private Collection<EntregaItemRequisicaoDTO> itensEntrega;

    private String itensRequisicao_serialize;
    private String itensCotacao_serialize;
    private String itensEntrega_serialize;
	
    private String loginAprovadores;
    
    private RequisicaoProdutoDTO requisicaoAnteriorDto;
    private CentroResultadoDTO centroCustoDto;
    
	public Integer getIdProjeto(){
		return this.idProjeto;
	}
	public void setIdProjeto(Integer parm){
		this.idProjeto = parm;
	}

	public String getFinalidade(){
		return this.finalidade;
	}

    public void setFinalidade(String parm){
		this.finalidade = parm;
	}
    public Collection<ItemRequisicaoProdutoDTO> getItensRequisicao() {
        return itensRequisicao;
    }
    public void setItensRequisicao(
            Collection<ItemRequisicaoProdutoDTO> itensRequisicao) {
        this.itensRequisicao = itensRequisicao;
    }
    public String getItensRequisicao_serialize() {
        return itensRequisicao_serialize;
    }
    public void setItensRequisicao_serialize(String itensRequisicao_serialize) {
        this.itensRequisicao_serialize = itensRequisicao_serialize;
    }
    public String getAcao() {
        return acao;
    }
    public void setAcao(String acao) {
        this.acao = acao;
    }
    public Integer getIdCentroCusto() {
        return idCentroCusto;
    }
    public void setIdCentroCusto(Integer idCentroCusto) {
        this.idCentroCusto = idCentroCusto;
    }
    public Double getValorAprovado() {
        return valorAprovado;
    }
    public void setValorAprovado(Double valorAprovado) {
        this.valorAprovado = valorAprovado;
    }
    public Integer getIdEnderecoEntrega() {
        return idEnderecoEntrega;
    }
    public void setIdEnderecoEntrega(Integer idEnderecoEntrega) {
        this.idEnderecoEntrega = idEnderecoEntrega;
    }
    public Integer getIdCategoriaProduto() {
        return idCategoriaProduto;
    }
    public void setIdCategoriaProduto(Integer idCategoriaProduto) {
        this.idCategoriaProduto = idCategoriaProduto;
    }
    public Integer getIdProduto() {
        return idProduto;
    }
    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }
    public String getTipoIdentificacaoItem() {
        return tipoIdentificacaoItem;
    }
    public void setTipoIdentificacaoItem(String tipoIdentificacaoItem) {
        this.tipoIdentificacaoItem = tipoIdentificacaoItem;
    }
    public String getRejeitada() {
        return rejeitada;
    }
    public void setRejeitada(String rejeitada) {
        this.rejeitada = rejeitada;
    }
    public Integer getIdFornecedorColeta() {
        return idFornecedorColeta;
    }
    public void setIdFornecedorColeta(Integer idFornecedorColeta) {
        this.idFornecedorColeta = idFornecedorColeta;
    }
    public Integer getIdItemColeta() {
        return idItemColeta;
    }
    public void setIdItemColeta(Integer idItemColeta) {
        this.idItemColeta = idItemColeta;
    }
    public Collection<ItemRequisicaoProdutoDTO> getItensValidos() {
        return itensValidos;
    }
    public void setItensValidos(Collection<ItemRequisicaoProdutoDTO> itensValidos) {
        this.itensValidos = itensValidos;
    }
    public Integer getIdColetaPreco() {
        return idColetaPreco;
    }
    public void setIdColetaPreco(Integer idColetaPreco) {
        this.idColetaPreco = idColetaPreco;
    }
    public Integer getIdItemRequisicaoProduto() {
        return idItemRequisicaoProduto;
    }
    public void setIdItemRequisicaoProduto(Integer idItemRequisicaoProduto) {
        this.idItemRequisicaoProduto = idItemRequisicaoProduto;
    }
    public Collection<CotacaoItemRequisicaoDTO> getItensCotacao() {
        return itensCotacao;
    }
    public void setItensCotacao(Collection<CotacaoItemRequisicaoDTO> itensCotacao) {
        this.itensCotacao = itensCotacao;
    }
    public String getItensCotacao_serialize() {
        return itensCotacao_serialize;
    }
    public void setItensCotacao_serialize(String itensCotacao_serialize) {
        this.itensCotacao_serialize = itensCotacao_serialize;
    }
    public Integer getIdEntrega() {
        return idEntrega;
    }
    public void setIdEntrega(Integer idEntrega) {
        this.idEntrega = idEntrega;
    }
    public Collection<EntregaItemRequisicaoDTO> getItensEntrega() {
        return itensEntrega;
    }
    public void setItensEntrega(Collection<EntregaItemRequisicaoDTO> itensEntrega) {
        this.itensEntrega = itensEntrega;
    }
    public String getItensEntrega_serialize() {
        return itensEntrega_serialize;
    }
    public void setItensEntrega_serialize(String itensEntrega_serialize) {
        this.itensEntrega_serialize = itensEntrega_serialize;
    }
    public String getCentroCusto() {
        return centroCusto;
    }
    public void setCentroCusto(String centroCusto) {
        this.centroCusto = centroCusto;
    }
    public String getProjeto() {
        return projeto;
    }
    public void setProjeto(String projeto) {
        this.projeto = projeto;
    }
    public String getLoginAprovadores() {
        return loginAprovadores;
    }
    public void setLoginAprovadores(String loginAprovadores) {
        this.loginAprovadores = loginAprovadores;
    }
	public RequisicaoProdutoDTO getRequisicaoAnteriorDto() {
		return requisicaoAnteriorDto;
	}
	public void setRequisicaoAnteriorDto(RequisicaoProdutoDTO requisicaoAnteriorDto) {
		this.requisicaoAnteriorDto = requisicaoAnteriorDto;
	}
	public boolean dadosAlterados() {
		if (this.requisicaoAnteriorDto == null)
			return false;
		
		return  this.requisicaoAnteriorDto.getIdCentroCusto().intValue() != this.getIdCentroCusto().intValue() 
			 || this.requisicaoAnteriorDto.getIdProjeto().intValue() != this.getIdProjeto().intValue()	
			 || this.requisicaoAnteriorDto.getIdEnderecoEntrega().intValue() != this.getIdEnderecoEntrega().intValue()	
			 || !this.requisicaoAnteriorDto.getFinalidade().equals(this.getFinalidade());	
	}
	public boolean centroCustoAlterado() {
		if (this.requisicaoAnteriorDto == null)
			return false;
		
		return  this.requisicaoAnteriorDto.getIdCentroCusto().intValue() != this.getIdCentroCusto().intValue();	
	}
	public CentroResultadoDTO getCentroCustoDto() {
		return centroCustoDto;
	}
	public void setCentroCustoDto(CentroResultadoDTO centroCustoDto) {
		this.centroCustoDto = centroCustoDto;
	}
	public String getExigeNovaAprovacao() {
		return exigeNovaAprovacao;
	}
	public void setExigeNovaAprovacao(String exigeNovaAprovacao) {
		this.exigeNovaAprovacao = exigeNovaAprovacao;
	}
	public String getItemAlterado() {
		return itemAlterado;
	}
	public void setItemAlterado(String itemAlterado) {
		this.itemAlterado = itemAlterado;
	}
	public Integer getIdItemCotacao() {
		return idItemCotacao;
	}
	public void setIdItemCotacao(Integer idItemCotacao) {
		this.idItemCotacao = idItemCotacao;
	}

}

