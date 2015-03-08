package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class AtividadesOSBIDTO implements IDto {
	private Integer idAtividadesOS;
	private Integer idOS;
	private Integer sequencia;
	private Integer idAtividadeServicoContrato;
	private String descricaoAtividade;
	private String obsAtividade;
	private Double custoAtividade ;
	private Double glosaAtividade;
	private Double qtdeExecutada;
	private String complexidade;
	private String numeroOS;
	private String deleted;
	private Double custoRealizado;
	private Double custos;
	private Object listaAcordoNivelServico;
	private Object listaAtividadeOs;
	private Object listaGlosasOs;
	private String formula;
	private String contabilizar;
	private Integer idServicoContratoContabil;
	
	private Integer idConexaoBI;

	public Object getListaGlosasOs() {
		return listaGlosasOs;
	}

	public void setListaGlosasOs(Object listaGlosasOs) {
		this.listaGlosasOs = listaGlosasOs;
	}

	public Double getCustos() {
		return custos;
	}

	public void setCustos(Double custos) {
		this.custos = custos;
	}

	public Double getCustoRealizado() {
		return custoRealizado;
	}

	public void setCustoRealizado(Double custoRealizado) {
		this.custoRealizado = custoRealizado;
	}

	public Object getListaAtividadeOs() {
		return listaAtividadeOs;
	}

	public void setListaAtividadeOs(Object listaAtividadeOs) {
		this.listaAtividadeOs = listaAtividadeOs;
	}

	public Object getListaAcordoNivelServico() {
		return listaAcordoNivelServico;
	}

	public void setListaAcordoNivelServico(Object listaAcordoNivelServico) {
		this.listaAcordoNivelServico = listaAcordoNivelServico;
	}

	public Integer getIdAtividadesOS() {
		return this.idAtividadesOS;
	}

	public void setIdAtividadesOS(Integer parm) {
		this.idAtividadesOS = parm;
	}

	public Integer getIdOS() {
		return this.idOS;
	}

	public void setIdOS(Integer parm) {
		this.idOS = parm;
	}

	public Integer getSequencia() {
		return this.sequencia;
	}

	public void setSequencia(Integer parm) {
		this.sequencia = parm;
	}

	public Integer getIdAtividadeServicoContrato() {
		return this.idAtividadeServicoContrato;
	}

	public void setIdAtividadeServicoContrato(Integer parm) {
		this.idAtividadeServicoContrato = parm;
	}

	public String getDescricaoAtividade() {
		return this.descricaoAtividade;
	}

	public void setDescricaoAtividade(String parm) {
		this.descricaoAtividade = parm;
	}

	public String getObsAtividade() {
		return this.obsAtividade;
	}

	public void setObsAtividade(String parm) {
		this.obsAtividade = parm;
	}

	public Double getCustoAtividade() {
		return this.custoAtividade;
	}

	public void setCustoAtividade(Double parm) {
		this.custoAtividade = parm;
	}

	public String getComplexidade() {
		return this.complexidade;
	}

	public void setComplexidade(String parm) {
		this.complexidade = parm;
	}
	
	public String getDeleted() {
		return this.deleted;
	}
	public void setDeleted(String parm) {
		this.deleted = parm;
	}
	
	public Double getGlosaAtividade() {
		return glosaAtividade;
	}
	
	public void setGlosaAtividade(Double glosaAtividade) {
		this.glosaAtividade = glosaAtividade;
	}
	
	public Double getQtdeExecutada() {
		return qtdeExecutada;
	}
	
	public void setQtdeExecutada(Double qtdeExecutada) {
		this.qtdeExecutada = qtdeExecutada;
	}
	
	public String getNumeroOS() {
		return numeroOS;
	}
	
	public void setNumeroOS(String numeroOS) {
		this.numeroOS = numeroOS;
	}
	
	public String getFormula() {
		return formula;
	}
	
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

	public Integer getIdConexaoBI() {
		return idConexaoBI;
	}

	public void setIdConexaoBI(Integer idConexaoBI) {
		this.idConexaoBI = idConexaoBI;
	}
	
}
