package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class DemandaDTO implements IDto {

	/**
	 * 
	 */
	public static final Integer SITUACAO_AGUARDANDO_APROVACAO =  new Integer(0); // Aguardando aprovacao
	public static final Integer SITUACAO_NAO_INICIADA =  new Integer(1); // Não Iniciada
	//public static final Integer SITUACAO_ATRIBUIDA =  new Integer(2); // Atribuída
	public static final Integer SITUACAO_EM_EXECUCAO =  new Integer(2); // Em Execução
	public static final Integer SITUACAO_FINALIZADA =  new Integer(3); // Finalizada
	public static final Integer SITUACAO_PARALISADA =  new Integer(4); // Paralisada
	public static final Integer SITUACAO_AGUARDANDO_RESPOSTA =  new Integer(5); // Aguardando Resposta
	public static final Integer SITUACAO_CANCELADA =  new Integer(6); // Cancelada
		
	private static final long serialVersionUID = -6548478261130612070L;
	private Integer idDemanda;
	private Integer idContrato;
	private Integer idSituacaoDemanda;
	private Integer idTipoDemanda;
	private Integer idProjeto;
	private Integer idDemandaPai;
	private Integer idFluxo;
	private Date previsaoInicio;
	private Date dataInicio;
	private Date previsaoFim;
	private Date dataFim;
	private String detalhamento;
	private String prioridade;
	private Date expectativaFim;
	private Integer idCliente;
	private Integer complexidade;
	private Double custoTotal;
	private String observacao;
	private Integer idOS;
	private Double glosa;
	private Integer idAtividadeServicoContrato;
	private String formula;
	private String contabilizar;
	private Integer idServicoContratoContabil;	
	
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
	public Integer getIdDemanda() {
		return idDemanda;
	}
	public void setIdDemanda(Integer idDemanda) {
		this.idDemanda = idDemanda;
	}
	public Integer getIdDemandaPai() {
		return idDemandaPai;
	}
	public void setIdDemandaPai(Integer idDemandaPai) {
		this.idDemandaPai = idDemandaPai;
	}
	public Integer getIdFluxo() {
		return idFluxo;
	}
	public void setIdFluxo(Integer idFluxo) {
		this.idFluxo = idFluxo;
	}
	public Integer getIdProjeto() {
		return idProjeto;
	}
	public void setIdProjeto(Integer idProjeto) {
		this.idProjeto = idProjeto;
	}
	public Integer getIdSituacaoDemanda() {
		return idSituacaoDemanda;
	}
	public void setIdSituacaoDemanda(Integer idSituacaoDemanda) {
		this.idSituacaoDemanda = idSituacaoDemanda;
	}
	public Integer getIdTipoDemanda() {
		return idTipoDemanda;
	}
	public void setIdTipoDemanda(Integer idTipoDemanda) {
		this.idTipoDemanda = idTipoDemanda;
	}
	public Date getPrevisaoFim() {
		return previsaoFim;
	}
	public void setPrevisaoFim(Date previsaoFim) {
		this.previsaoFim = previsaoFim;
	}
	public Date getPrevisaoInicio() {
		return previsaoInicio;
	}
	public void setPrevisaoInicio(Date previsaoInicio) {
		this.previsaoInicio = previsaoInicio;
	}
	public String getDetalhamento() {
		return detalhamento;
	}
	public void setDetalhamento(String detalhamento) {
		this.detalhamento = detalhamento;
	}
	public String getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public Date getExpectativaFim() {
		return expectativaFim;
	}
	public void setExpectativaFim(Date expectativaFim) {
		this.expectativaFim = expectativaFim;
	}
	public Integer getComplexidade() {
		return complexidade;
	}
	public void setComplexidade(Integer complexidade) {
		this.complexidade = complexidade;
	}
	public Double getCustoTotal() {
		return custoTotal;
	}
	public void setCustoTotal(Double custoTotal) {
		this.custoTotal = custoTotal;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Integer getIdOS() {
		return idOS;
	}
	public void setIdOS(Integer idOS) {
		this.idOS = idOS;
	}
	public Double getGlosa() {
		return glosa;
	}
	public void setGlosa(Double glosa) {
		this.glosa = glosa;
	}
	public Integer getIdAtividadeServicoContrato() {
		return idAtividadeServicoContrato;
	}
	public void setIdAtividadeServicoContrato(Integer idAtividadeServicoContrato) {
		this.idAtividadeServicoContrato = idAtividadeServicoContrato;
	}
	/**
	 * @return the formula
	 */
	public String getFormula() {
		return formula;
	}
	/**
	 * @param formula the formula to set
	 */
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getContabilizar() {
		return contabilizar;
	}
	public void setContabilizar(String contabilizar) {
		this.contabilizar = contabilizar;
	}
	public Integer getIdServicoContratoContabil() {
		return idServicoContratoContabil;
	}
	public void setIdServicoContratoContabil(Integer idServicoContratoContabil) {
		this.idServicoContratoContabil = idServicoContratoContabil;
	}
}
