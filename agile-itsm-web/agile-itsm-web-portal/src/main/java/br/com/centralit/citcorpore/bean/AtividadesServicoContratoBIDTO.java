package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class AtividadesServicoContratoBIDTO implements IDto {
	private Integer idAtividadeServicoContrato;
	private Integer idServicoContrato;
	private String descricaoAtividade;
	private String obsAtividade;
	private String complexidade;
	private Double custoAtividade;
	private String deleted;
	private Double hora;
	private Integer quantidade;
	private String periodo;
	private String formula;
	private String contabilizar;
	private Integer idServicoContratoContabil;
	private String nomeServico;
	private String tipoCusto;
	private Integer idConexaoBI;
	
	public Integer getIdAtividadeServicoContrato(){
		return this.idAtividadeServicoContrato;
	}
	public void setIdAtividadeServicoContrato(Integer parm){
		this.idAtividadeServicoContrato = parm;
	}

	public Integer getIdServicoContrato(){
		return this.idServicoContrato;
	}
	public void setIdServicoContrato(Integer parm){
		this.idServicoContrato = parm;
	}

	public String getDescricaoAtividade(){
		return this.descricaoAtividade;
	}
	public void setDescricaoAtividade(String parm){
		this.descricaoAtividade = parm;
	}

	public Double getCustoAtividade(){
		return this.custoAtividade;
	}
	public void setCustoAtividade(Double parm){
		this.custoAtividade = parm;
	}

	public String getObsAtividade(){
		return this.obsAtividade;
	}
	public void setObsAtividade(String parm){
		this.obsAtividade = parm;
	}

	public String getDeleted(){
		if (deleted == null || deleted.trim().equalsIgnoreCase("")){
			return "N";
		}
		return deleted;
	}
	public void setDeleted(String parm){
		this.deleted = parm;
	}
	public String getComplexidade() {
		return complexidade;
	}
	public void setComplexidade(String complexidade) {
		this.complexidade = complexidade;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public Double getHora() {
		return hora;
	}
	public void setHora(Double hora) {
		this.hora = hora;
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
	public String getNomeServico() {
		return nomeServico;
	}
	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}
	public String getTipoCusto() {
		return tipoCusto;
	}
	public void setTipoCusto(String tipoCusto) {
		this.tipoCusto = tipoCusto;
	}
	public Integer getIdConexaoBI() {
		return idConexaoBI;
	}
	public void setIdConexaoBI(Integer idConexaoBI) {
		this.idConexaoBI = idConexaoBI;
	}
	
}
