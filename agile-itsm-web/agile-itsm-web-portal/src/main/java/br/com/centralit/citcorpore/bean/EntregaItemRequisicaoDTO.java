package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.util.Collection;

import br.com.centralit.citcorpore.util.Enumerados.SituacaoEntregaItemRequisicao;
import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilStrings;

public class EntregaItemRequisicaoDTO implements IDto {
	private Integer idEntrega;
	private Integer idPedido;
	private Integer idColetaPreco;
	private Integer idItemRequisicaoProduto;
	private Integer idSolicitacaoServico;
    private Integer idItemTrabalho;	
    private Integer idParecer;
	private Double quantidadeEntregue;
    private String situacao;
	
    private Integer idJustificativa;
    private String complementoJustificativa;
    private String aprovado;
    private String descrSituacao;

    private String descricaoItem;
	private String numeroPedido;
	private Date dataEntrega;
	private String cpfCnpjFornecedor;
	private String nomeFornecedor;
    private String tipoFornecedor;
    
    private String descricaoFmtHtml;
	
	private UsuarioDTO usuarioDto;
	Collection<InspecaoEntregaItemDTO> colInspecao;
	
    
    public String getSituacao() {
        return situacao;
    }
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    public Integer getIdEntrega() {
        return idEntrega;
    }
    public void setIdEntrega(Integer idEntrega) {
        this.idEntrega = idEntrega;
    }
    public Integer getIdPedido() {
        return idPedido;
    }
    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
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
    public Integer getIdSolicitacaoServico() {
        return idSolicitacaoServico;
    }
    public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
        this.idSolicitacaoServico = idSolicitacaoServico;
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
    public String getNumeroPedido() {
        return numeroPedido;
    }
    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }
    public Date getDataEntrega() {
        return dataEntrega;
    }
    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
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
    public String getDescricaoItem() {
        return descricaoItem;
    }
    public void setDescricaoItem(String descricaoItem) {
        this.descricaoItem = descricaoItem;
    }
    public Collection<InspecaoEntregaItemDTO> getColInspecao() {
        return colInspecao;
    }
    public void setColInspecao(Collection<InspecaoEntregaItemDTO> colInspecao) {
        this.colInspecao = colInspecao;
    }
    public UsuarioDTO getUsuarioDto() {
        return usuarioDto;
    }
    public void setUsuarioDto(UsuarioDTO usuarioDto) {
        this.usuarioDto = usuarioDto;
    }
    public Integer getIdParecer() {
        return idParecer;
    }
    public void setIdParecer(Integer idParecer) {
        this.idParecer = idParecer;
    }
    public Integer getIdJustificativa() {
        return idJustificativa;
    }
    public void setIdJustificativa(Integer idJustificativa) {
        this.idJustificativa = idJustificativa;
    }
    public String getComplementoJustificativa() {
        return complementoJustificativa;
    }
    public void setComplementoJustificativa(String complementoJustificativa) {
        this.complementoJustificativa = complementoJustificativa;
    }
    public String getAprovado() {
        return aprovado;
    }
    public void setAprovado(String aprovado) {
        this.aprovado = aprovado;
    }
    public String getDescrSituacao() {
        this.descrSituacao = "";
        try {
            if (this.situacao != null)
                this.descrSituacao = SituacaoEntregaItemRequisicao.valueOf(this.situacao.trim()).getDescricao();
        } catch (Exception e) {
        } 
        return descrSituacao;
    }
    public void setDescrSituacao(String descrSituacao) {
        this.descrSituacao = descrSituacao;
    }
	public String getTipoFornecedor() {
		return tipoFornecedor;
	}
	public void setTipoFornecedor(String tipoFornecedor) {
		this.tipoFornecedor = tipoFornecedor;
	}
	public String getDescricaoFmtHtml() {
		descricaoFmtHtml = "";
    	if (UtilStrings.isNotVazio(this.nomeFornecedor)) {
        	descricaoFmtHtml += "<p><b>CPF/CNPJ Fornecedor:</b> ";
        	if (UtilStrings.nullToVazio(this.tipoFornecedor).equalsIgnoreCase("J"))
        		descricaoFmtHtml += UtilFormatacao.formataCnpj(cpfCnpjFornecedor);
        	else
        		descricaoFmtHtml += UtilFormatacao.formataCpf(cpfCnpjFornecedor);
        	descricaoFmtHtml += "<br><b>Nome Fornecedor:</b> "+this.nomeFornecedor+"</p>";
    	}
    	if (UtilStrings.isNotVazio(this.numeroPedido)) 
        	descricaoFmtHtml += "<p><b>Número do pedido:</b> "+this.numeroPedido+"</p>";
    	if (this.dataEntrega != null) 
        	descricaoFmtHtml += "<p><b>Data da entrega:</b> "+UtilDatas.dateToSTR(this.dataEntrega)+"</p>";
       	//descricaoFmtHtml += "<p><b>Situação:</b> "+this.getDescrSituacao()+"</p>";
    	return descricaoFmtHtml;
	}
	public void setDescricaoFmtHtml(String descricaoFmtHtml) {
		this.descricaoFmtHtml = descricaoFmtHtml;
	}
	
}
