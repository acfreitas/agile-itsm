package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ProdutoDTO implements IDto {
    private Integer idProduto;
	private Integer idTipoProduto;
	private Integer idMarca;
	private String modelo;
    private String detalhes;
    private Double precoMercado;
    private String nomeMarca;
    private String codigoProduto;
    
    private String imagem;
    private String identificacao;
    
    private Integer idCategoria;
    private Integer idUnidadeMedida;
    private String nomeProduto;
    private String situacao;
    private String aceitaRequisicao;
    
    private String nomeCategoria;
    private String siglaUnidMedida;    
    private String complemento;
    
	public Integer getIdTipoProduto(){
		return this.idTipoProduto;
	}
	public void setIdTipoProduto(Integer parm){
		this.idTipoProduto = parm;
	}

	public Integer getIdMarca(){
		return this.idMarca;
	}
	public void setIdMarca(Integer parm){
		this.idMarca = parm;
	}
    public String getDetalhes() {
        return detalhes;
    }
    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }
    public Double getPrecoMercado() {
        return precoMercado;
    }
    public void setPrecoMercado(Double precoMercado) {
        this.precoMercado = precoMercado;
    }
    public String getNomeMarca() {
        return nomeMarca;
    }
    public void setNomeMarca(String nomeMarca) {
        this.nomeMarca = nomeMarca;
    }
    public String getImagem() {
        return imagem;
    }
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public void montaIdentificacao() {
        identificacao = "";
        if (nomeProduto != null)
            identificacao += nomeProduto;
        if (complemento != null) {
            if (identificacao.length() > 0)
                identificacao += " ";
            identificacao += complemento;
        }
        if (modelo != null) {
            if (identificacao.length() > 0)
                identificacao += " ";
            identificacao += modelo;
        }
        if (nomeMarca != null) {
            if (identificacao.length() > 0)
                identificacao += " - ";
            identificacao += nomeMarca;
        }
    }
    public String getIdentificacao() {
        if (identificacao == null)
            montaIdentificacao();
        return identificacao;
    }
    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }
    public Integer getIdCategoria() {
        return idCategoria;
    }
    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }
    public Integer getIdUnidadeMedida() {
        return idUnidadeMedida;
    }
    public void setIdUnidadeMedida(Integer idUnidadeMedida) {
        this.idUnidadeMedida = idUnidadeMedida;
    }
    public String getNomeProduto() {
        return nomeProduto;
    }
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
    public String getSituacao() {
        return situacao;
    }
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    public String getAceitaRequisicao() {
        return aceitaRequisicao;
    }
    public void setAceitaRequisicao(String aceitaRequisicao) {
        this.aceitaRequisicao = aceitaRequisicao;
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
    public Integer getIdProduto() {
        return idProduto;
    }
    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }
    public String getCodigoProduto() {
        return codigoProduto;
    }
    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }
	/**
	 * @return the complemento
	 */
	public String getComplemento() {
		return complemento;
	}
	/**
	 * @param complemento the complemento to set
	 */
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
}
