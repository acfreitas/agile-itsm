package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilStrings;

public class ContratoBIDTO implements IDto {

	private static final long serialVersionUID = -5899256214152706919L;
	
	private String nome;
	
	private Integer idContrato;

	private Integer idCliente;

	private String numero;

	private String objeto;

	private java.sql.Date dataContrato;
	
	private java.sql.Date dataFimContrato;

	private Double valorEstimado;

	private String tipoTempoEstimado;

	private Integer tempoEstimado;

	private String tipo;

	private String situacao;

	private Integer idMoeda;

	private Integer idFluxo;

	private Double cotacaoMoeda;

	private Integer idFornecedor;

	private String deleted;

	private Integer idGrupoSolicitante;
	
	private java.sql.Date criadoEm;
	private String criadoPor;
	private java.sql.Date modificadoEm;
	private String modificadoPor;
	
	private Integer idConexaoBI;
	private Integer idSuperintendente;

	public Integer getIdContrato() {
		return this.idContrato;
	}

	public void setIdContrato(Integer parm) {
		this.idContrato = parm;
	}

	public Integer getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(Integer parm) {
		this.idCliente = parm;
	}

	public String getNumero() {
		return this.numero;
	}
	
	public String getNumeroHTMLEncoded() {
		return UtilHTML.encodeHTML(UtilStrings.nullToVazio(this.numero));
	}

	public void setNumero(String parm) {
		this.numero = parm;
	}

	public String getObjeto() {
		return this.objeto;
	}

	public void setObjeto(String parm) {
		this.objeto = parm;
	}

	public java.sql.Date getDataContrato() {
		return this.dataContrato;
	}

	public void setDataContrato(java.sql.Date parm) {
		this.dataContrato = parm;
	}

	public Double getValorEstimado() {
		return this.valorEstimado;
	}

	public void setValorEstimado(Double parm) {
		this.valorEstimado = parm;
	}

	public String getTipoTempoEstimado() {
		return this.tipoTempoEstimado;
	}

	public void setTipoTempoEstimado(String parm) {
		this.tipoTempoEstimado = parm;
	}

	public Integer getTempoEstimado() {
		return this.tempoEstimado;
	}

	public void setTempoEstimado(Integer parm) {
		this.tempoEstimado = parm;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String parm) {
		this.tipo = parm;
	}

	public String getSituacao() {
		return this.situacao;
	}

	public void setSituacao(String parm) {
		this.situacao = parm;
	}

	public Integer getIdMoeda() {
		return idMoeda;
	}

	public void setIdMoeda(Integer idMoeda) {
		this.idMoeda = idMoeda;
	}

	public Integer getIdFluxo() {
		return idFluxo;
	}

	public void setIdFluxo(Integer idFluxo) {
		this.idFluxo = idFluxo;
	}

	public Double getCotacaoMoeda() {
		return cotacaoMoeda;
	}

	public void setCotacaoMoeda(Double cotacaoMoeda) {
		this.cotacaoMoeda = cotacaoMoeda;
	}

	public Integer getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(Integer idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Integer getIdGrupoSolicitante() {
		return idGrupoSolicitante;
	}

	public void setIdGrupoSolicitante(Integer idGrupoSolicitante) {
		this.idGrupoSolicitante = idGrupoSolicitante;
	}

	/**
	 * @return the dataFimContrato
	 */
	public java.sql.Date getDataFimContrato() {
		return dataFimContrato;
	}

	/**
	 * @param dataFimContrato the dataFimContrato to set
	 */
	public void setDataFimContrato(java.sql.Date dataFimContrato) {
		this.dataFimContrato = dataFimContrato;
	}

	public java.sql.Date getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(java.sql.Date criadoEm) {
		this.criadoEm = criadoEm;
	}

	public String getCriadoPor() {
		return criadoPor;
	}

	public void setCriadoPor(String criadoPor) {
		this.criadoPor = criadoPor;
	}

	public java.sql.Date getModificadoEm() {
		return modificadoEm;
	}

	public void setModificadoEm(java.sql.Date modificadoEm) {
		this.modificadoEm = modificadoEm;
	}

	public String getModificadoPor() {
		return modificadoPor;
	}

	public void setModificadoPor(String modificadoPor) {
		this.modificadoPor = modificadoPor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idContrato == null) ? 0 : idContrato.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContratoBIDTO other = (ContratoBIDTO) obj;
		if (idContrato == null) {
			if (other.idContrato != null)
				return false;
		} else if (!idContrato.equals(other.idContrato))
			return false;
		return true;
	}

	public Integer getIdConexaoBI() {
		return idConexaoBI;
	}

	public void setIdConexaoBI(Integer idConexaoBI) {
		this.idConexaoBI = idConexaoBI;
	}

	public Integer getIdSuperintendente() {
		return idSuperintendente;
	}

	public void setIdSuperintendente(Integer idSuperintendente) {
		this.idSuperintendente = idSuperintendente;
	}
	
	
}
