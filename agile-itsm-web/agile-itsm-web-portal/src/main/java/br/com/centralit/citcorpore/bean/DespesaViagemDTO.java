package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import br.com.citframework.dto.IDto;

public class DespesaViagemDTO implements IDto {
	private static final long serialVersionUID = 1L;
	
	// Campos do banco de dados
	private Integer idDespesaViagem;
	private Date dataInicio;
	private Date dataFim;
	private Integer idRoteiro;
	private Integer idTipo;
	private String tipoDespesa;
	private Integer idFornecedor;
	private String nomeFornecedor;
	private Double valor;
	private Double valorTotal;
	private Integer quantidade;
	private Timestamp validade;
	private String validadeDate;
	private String validadeHora;
	private String original;
	private Integer idSolicitacaoServico;
	private String prestacaoContas;
	private String situacao;
	private Integer idMoeda;
	private String nomeMoeda;
	private Integer idFormaPagamento;
	private String nomeFormaPagamento;
	private String colIntegrantesViagem_Serialize;
	private String colIntegrantesViagem_SerializeAux;
	private String observacoes;
	
	//atributos para controle de requisição em compras
	private String cancelarRequisicao;
	private String confirma;
	private Integer idResponsavelCompra;
	private Timestamp dataHoraCompra;
	
	// Campos para controle de formulario
	private Integer idContrato;
	private Integer idTarefa;
	private Integer idSolicitacaoServicoAux;
	private Date prazoCotacaoAux;
	private String horaCotacaoAux;
	
	public Integer getIdDespesaViagem() {
		return idDespesaViagem;
	}
	public void setIdDespesaViagem(Integer idDespesaViagem) {
		this.idDespesaViagem = idDespesaViagem;
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
	public Integer getIdRoteiro() {
		return idRoteiro;
	}
	public void setIdRoteiro(Integer idRoteiro) {
		this.idRoteiro = idRoteiro;
	}
	public Integer getIdTipo() {
		return idTipo;
	}
	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}
	public Integer getIdFornecedor() {
		return idFornecedor;
	}
	public void setIdFornecedor(Integer idFornecedor) {
		this.idFornecedor = idFornecedor;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public String getOriginal() {
		return original;
	}
	public void setOriginal(String original) {
		this.original = original;
	}
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
		this.idSolicitacaoServicoAux = idSolicitacaoServico;
	}
	public String getPrestacaoContas() {
		return prestacaoContas;
	}
	public void setPrestacaoContas(String prestacaoContas) {
		this.prestacaoContas = prestacaoContas;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public Timestamp getValidade() {
		return validade;
	}
	public void setValidade(Timestamp validade) {
		this.validade = validade;
	}
	public Integer getIdMoeda() {
		return idMoeda;
	}
	public void setIdMoeda(Integer idMoeda) {
		this.idMoeda = idMoeda;
	}
	public Integer getIdFormaPagamento() {
		return idFormaPagamento;
	}
	public void setIdFormaPagamento(Integer idFormaPagamento) {
		this.idFormaPagamento = idFormaPagamento;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
	public Double getTotal() {
		Double total = 0.0;
		
		if(this.valor != null && this.quantidade != null) {
			total = this.valor * this.quantidade;
		}
		
		return total;
	}
	
	public String getTotalFormatado() {
		Double total = 0.0;
		
		if(this.valor != null && this.quantidade != null) {
			total = this.valor * this.quantidade;
		}
		
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		
		DecimalFormat decimal = (DecimalFormat) nf;
		
		decimal.applyPattern("#,##0.00");
		
		return decimal.format(total);
	}
	
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
	public Integer getIdTarefa() {
		return idTarefa;
	}
	public void setIdTarefa(Integer idTarefa) {
		this.idTarefa = idTarefa;
	}
	public Integer getIdSolicitacaoServicoAux() {
		return idSolicitacaoServicoAux;
	}
	public void setIdSolicitacaoServicoAux(Integer idSolicitacaoServicoAux) {
		this.idSolicitacaoServico = idSolicitacaoServicoAux;
		this.idSolicitacaoServicoAux = idSolicitacaoServicoAux;
	}
	public String getValidadeHora() {
		return validadeHora;
	}
	public void setValidadeHora(String validadeHora) {
		this.validadeHora = validadeHora;
	}
	public String getValidadeDate() {
		return validadeDate;
	}
	public void setValidadeDate(String validadeDate) {
		this.validadeDate = validadeDate;
	}
	public Date getPrazoCotacaoAux() {
		return prazoCotacaoAux;
	}
	public void setPrazoCotacaoAux(Date prazoCotacaoAux) {
		this.prazoCotacaoAux = prazoCotacaoAux;
	}
	public String getHoraCotacaoAux() {
		return horaCotacaoAux;
	}
	public void setHoraCotacaoAux(String horaCotacaoAux) {
		this.horaCotacaoAux = horaCotacaoAux;
	}
	public String getCancelarRequisicao() {
		return cancelarRequisicao;
	}
	public void setCancelarRequisicao(String cancelarRequisicao) {
		this.cancelarRequisicao = cancelarRequisicao;
	}
	public String getConfirma() {
		return confirma;
	}
	public void setConfirma(String confirma) {
		this.confirma = confirma;
	}
	public Integer getIdResponsavelCompra() {
		return idResponsavelCompra;
	}
	public void setIdResponsavelCompra(Integer idResponsavelCompra) {
		this.idResponsavelCompra = idResponsavelCompra;
	}
	public Timestamp getDataHoraCompra() {
		return dataHoraCompra;
	}
	public void setDataHoraCompra(Timestamp dataHoraCompra) {
		this.dataHoraCompra = dataHoraCompra;
	}
	public String getColIntegrantesViagem_Serialize() {
		return colIntegrantesViagem_Serialize;
	}
	public void setColIntegrantesViagem_Serialize(String colIntegrantesViagem_Serialize) {
		this.colIntegrantesViagem_Serialize = colIntegrantesViagem_Serialize;
		this.colIntegrantesViagem_SerializeAux = colIntegrantesViagem_Serialize;
	}
	public String getColIntegrantesViagem_SerializeAux() {
		return colIntegrantesViagem_SerializeAux;
	}
	public void setColIntegrantesViagem_SerializeAux(String colIntegrantesViagem_SerializeAux) {
		this.colIntegrantesViagem_Serialize = colIntegrantesViagem_SerializeAux;
		this.colIntegrantesViagem_SerializeAux = colIntegrantesViagem_SerializeAux;
	}
	public String getTipoDespesa() {
		return tipoDespesa;
	}
	public void setTipoDespesa(String tipoDespesa) {
		this.tipoDespesa = tipoDespesa;
	}
	public String getNomeFornecedor() {
		return nomeFornecedor;
	}
	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}
	public String getNomeFormaPagamento() {
		return nomeFormaPagamento;
	}
	public void setNomeFormaPagamento(String nomeFormaPagamento) {
		this.nomeFormaPagamento = nomeFormaPagamento;
	}
	public String getNomeMoeda() {
		return nomeMoeda;
	}
	public void setNomeMoeda(String nomeMoeda) {
		this.nomeMoeda = nomeMoeda;
	}
	public Double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
}