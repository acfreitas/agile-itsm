package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;
/**
 * @author thiago matias
 *  
 */
/**
 * @author maycon.silva
 *
 */
public class RequisicaoLiberacaoRequisicaoComprasDTO implements IDto {
	
	private static final long serialVersionUID = 1L;
	private Integer idRequisicaoLiberacao;
	private Integer idRequisicaoLiberacaoCompras;
	private Integer idSolicitacaoServico;
	private String nomeServico;
	private String situacaoServicos;
	private Date dataFim;
	private String descricaoItem;
	private String nomeProjeto;
	private String codigoCentroResultado;
	private String nomeCentroResultado;
	private String situacaoItemRequisicao;
	private Integer idItemRequisicaoProduto;


	
	/**
	 * @return the descricaoItem
	 */
	public String getDescricaoItem() {
		return descricaoItem;
	}
	/**
	 * @param descricaoItem the descricaoItem to set
	 */
	public void setDescricaoItem(String descricaoItem) {
		this.descricaoItem = descricaoItem;
	}
	/**
	 * @return the nomeProjeto
	 */
	public String getNomeProjeto() {
		return nomeProjeto;
	}
	/**
	 * @param nomeProjeto the nomeProjeto to set
	 */
	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}
	/**
	 * @return the codigoCentroResultado
	 */
	public String getCodigoCentroResultado() {
		return codigoCentroResultado;
	}
	/**
	 * @param codigoCentroResultado the codigoCentroResultado to set
	 */
	public void setCodigoCentroResultado(String codigoCentroResultado) {
		this.codigoCentroResultado = codigoCentroResultado;
	}
	/**
	 * @return the nomeCentroResultado
	 */
	public String getNomeCentroResultado() {
		return nomeCentroResultado;
	}
	/**
	 * @param nomeCentroResultado the nomeCentroResultado to set
	 */
	public void setNomeCentroResultado(String nomeCentroResultado) {
		this.nomeCentroResultado = nomeCentroResultado;
	}
	/**
	 * @return the situacaoItemRequisicao
	 */
	public String getSituacaoItemRequisicao() {
		return situacaoItemRequisicao;
	}
	/**
	 * @param situacaoItemRequisicao the situacaoItemRequisicao to set
	 */
	public void setSituacaoItemRequisicao(String situacaoItemRequisicao) {
		this.situacaoItemRequisicao = situacaoItemRequisicao;
	}

	public Integer getIdRequisicaoLiberacaoCompras() {
		return idRequisicaoLiberacaoCompras;
	}
	public void setIdRequisicaoLiberacaoCompras(Integer idRequisicaoLiberacaoCompras) {
		this.idRequisicaoLiberacaoCompras = idRequisicaoLiberacaoCompras;
	}
	
	public Integer getIdRequisicaoLiberacao(){
		return this.idRequisicaoLiberacao;
	}
	public void setIdRequisicaoLiberacao(Integer parm){
		this.idRequisicaoLiberacao = parm;
	}
	public String getNomeServico() {
		return nomeServico;
	}
	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}
	
	public String getSituacaoServicos() {
		return situacaoServicos;
	}
	
	public void setSituacaoServicos(String situacaoServicos) {
		this.situacaoServicos = situacaoServicos;
	}
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public Integer getIdItemRequisicaoProduto() {
		return idItemRequisicaoProduto;
	}
	public void setIdItemRequisicaoProduto(Integer idItemRequisicaoProduto) {
		this.idItemRequisicaoProduto = idItemRequisicaoProduto;
	}
    

}
