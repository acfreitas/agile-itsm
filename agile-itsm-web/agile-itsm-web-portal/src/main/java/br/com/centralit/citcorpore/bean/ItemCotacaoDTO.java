package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ItemCotacaoDTO extends ItemRegraNegocioDTO {
	private Integer idItemCotacao;
	private Integer idCotacao;
	private Integer idProduto;
    private Integer idUnidadeMedida;
    private Double quantidade;
    private String situacao;
    private Integer idMarca;  
    private String descricaoItem;
    private String especificacoes;
    private String marcaPreferencial;
    private Double precoAproximado;
    private String tipoIdentificacao;    
    private Integer idCategoriaProduto; 
    private Integer pesoPreco;
    private Integer pesoPrazoEntrega;
    private Integer pesoPrazoPagto;
    private Integer pesoTaxaJuros;
    private Integer pesoPrazoGarantia;
    private String exigeFornecedorQualificado;

    private String codigoProduto;
    private String nomeProduto;
    private String nomeMarca;
    
    private Integer idCentroCusto;
    private Integer idProjeto;
    private Integer idEnderecoEntrega;

    private String solicitacoesAtendidas;
    private String descrSituacao;
    private String nomeCentroCusto;
    private String nomeProjeto;
    
    private String tipoCriacaoItem;

    private Timestamp dataHoraLimite;
    private String dataHoraLimiteStr;
    
    private String identificacao;
    private String complemento;
    private String modelo;
    
    private Integer idSolicitacaoServico;
    private Date dataInicio;
    private Date dataFim;
    
    private Double quantidadeCotada;
    
    private UsuarioDTO usuarioDto;
    
    public Integer getIdItemCotacao(){
		return this.idItemCotacao;
	}
	public void setIdItemCotacao(Integer parm){
		this.idItemCotacao = parm;
	}

	public Integer getIdCotacao(){
		return this.idCotacao;
	}
	public void setIdCotacao(Integer parm){
		this.idCotacao = parm;
	}

	public Integer getIdProduto(){
		return this.idProduto;
	}
	public void setIdProduto(Integer parm){
		this.idProduto = parm;
	}

	public Double getQuantidade(){
		return this.quantidade;
	}
	public void setQuantidade(Double parm){
		this.quantidade = parm;
	}
    public Integer getIdUnidadeMedida() {
        return idUnidadeMedida;
    }
    public void setIdUnidadeMedida(Integer idUnidadeMedida) {
        this.idUnidadeMedida = idUnidadeMedida;
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
    public String getDescrSituacao() {
        return descrSituacao;
    }
    public void setDescrSituacao(String descrSituacao) {
        this.descrSituacao = descrSituacao;
    }
    public String getNomeCentroCusto() {
        return nomeCentroCusto;
    }
    public void setNomeCentroCusto(String nomeCentroCusto) {
        this.nomeCentroCusto = nomeCentroCusto;
    }
    public String getNomeProjeto() {
        return nomeProjeto;
    }
    public void setNomeProjeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }

    public void setDataHoraLimite(Timestamp dataHoraLimite) {
        this.dataHoraLimite = dataHoraLimite;
        if (dataHoraLimite != null) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            this.dataHoraLimiteStr = format.format(dataHoraLimite);
        }
    }

    public String getDataHoraLimiteStr() {
        if (dataHoraLimite != null) {
            return dataHoraLimiteStr;
        } else {
            return "--";
        }
    }
    public String getNomeMarca() {
        return nomeMarca;
    }
    public void setNomeMarca(String nomeMarca) {
        this.nomeMarca = nomeMarca;
    }
    public void setDataHoraLimiteStr(String dataHoraLimiteStr) {
        this.dataHoraLimiteStr = dataHoraLimiteStr;
    }
    
    public String getIdentificacao() {
        identificacao = "";
        if (descricaoItem != null) {
            identificacao += descricaoItem;
        }
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
        }else if (marcaPreferencial != null) {
            if (identificacao.length() > 0)
                identificacao += " - ";
            identificacao += marcaPreferencial;            
        }
        if (nomeProduto != null) {
            if (identificacao.length() > 0)
                identificacao += " ("+nomeProduto+")";
            else
            	identificacao += nomeProduto;
        }
        return identificacao;
    }
    public String getComplemento() {
        return complemento;
    }
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }
    public Integer getIdMarca() {
        return idMarca;
    }
    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }
    public String getDescricaoItem() {
        return descricaoItem;
    }
    public void setDescricaoItem(String descricaoItem) {
        this.descricaoItem = descricaoItem;
    }
    public String getEspecificacoes() {
        return especificacoes;
    }
    public void setEspecificacoes(String especificacoes) {
        this.especificacoes = especificacoes;
    }
    public String getMarcaPreferencial() {
        return marcaPreferencial;
    }
    public void setMarcaPreferencial(String marcaPreferencial) {
        this.marcaPreferencial = marcaPreferencial;
    }
    public Double getPrecoAproximado() {
        return precoAproximado;
    }
    public void setPrecoAproximado(Double precoAproximado) {
        this.precoAproximado = precoAproximado;
    }
    public String getTipoIdentificacao() {
        return tipoIdentificacao;
    }
    public void setTipoIdentificacao(String tipoIdentificacao) {
        this.tipoIdentificacao = tipoIdentificacao;
    }
    public Integer getIdCategoriaProduto() {
        return idCategoriaProduto;
    }
    public void setIdCategoriaProduto(Integer idCategoriaProduto) {
        this.idCategoriaProduto = idCategoriaProduto;
    }
    public String getSolicitacoesAtendidas() {
        return solicitacoesAtendidas;
    }
    public void setSolicitacoesAtendidas(String solicitacoesAtendidas) {
        this.solicitacoesAtendidas = solicitacoesAtendidas;
    }

    public String getTipoCriacaoItem() {
        return tipoCriacaoItem;
    }
    public void setTipoCriacaoItem(String tipoCriacaoItem) {
        this.tipoCriacaoItem = tipoCriacaoItem;
    }
    public Timestamp getDataHoraLimite() {
        return dataHoraLimite;
    }
    public Integer getIdSolicitacaoServico() {
        return idSolicitacaoServico;
    }
    public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
        this.idSolicitacaoServico = idSolicitacaoServico;
    }
    public Date getDataInicio() {
        return dataInicio;
    }
    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }
    public Date getDataFim() {
        return dataFim;
    }
    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
    public Integer getIdCentroCusto() {
        return idCentroCusto;
    }
    public void setIdCentroCusto(Integer idCentroCusto) {
        this.idCentroCusto = idCentroCusto;
    }
    public Integer getIdProjeto() {
        return idProjeto;
    }
    public void setIdProjeto(Integer idProjeto) {
        this.idProjeto = idProjeto;
    }
    public String getSituacao() {
        return situacao;
    }
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    public Integer getPesoPreco() {
        return pesoPreco;
    }
    public void setPesoPreco(Integer pesoPreco) {
        this.pesoPreco = pesoPreco;
    }
    public Integer getPesoPrazoEntrega() {
        return pesoPrazoEntrega;
    }
    public void setPesoPrazoEntrega(Integer pesoPrazoEntrega) {
        this.pesoPrazoEntrega = pesoPrazoEntrega;
    }
    public Integer getPesoPrazoPagto() {
        return pesoPrazoPagto;
    }
    public void setPesoPrazoPagto(Integer pesoPrazoPagto) {
        this.pesoPrazoPagto = pesoPrazoPagto;
    }
    public Integer getPesoTaxaJuros() {
        return pesoTaxaJuros;
    }
    public void setPesoTaxaJuros(Integer pesoTaxaJuros) {
        this.pesoTaxaJuros = pesoTaxaJuros;
    }
    public Integer getPesoPrazoGarantia() {
        return pesoPrazoGarantia;
    }
    public void setPesoPrazoGarantia(Integer pesoPrazoGarantia) {
        this.pesoPrazoGarantia = pesoPrazoGarantia;
    }
    public String getExigeFornecedorQualificado() {
        return exigeFornecedorQualificado;
    }
    public void setExigeFornecedorQualificado(String exigeFornecedorQualificado) {
        this.exigeFornecedorQualificado = exigeFornecedorQualificado;
    }
    public Integer getIdEnderecoEntrega() {
        return idEnderecoEntrega;
    }
    public void setIdEnderecoEntrega(Integer idEnderecoEntrega) {
        this.idEnderecoEntrega = idEnderecoEntrega;
    }
    public Double getQuantidadeCotada() {
        return quantidadeCotada;
    }
    public void setQuantidadeCotada(Double quantidadeCotada) {
        this.quantidadeCotada = quantidadeCotada;
    }
    public UsuarioDTO getUsuarioDto() {
        return usuarioDto;
    }
    public void setUsuarioDto(UsuarioDTO usuarioDto) {
        this.usuarioDto = usuarioDto;
    }
}
