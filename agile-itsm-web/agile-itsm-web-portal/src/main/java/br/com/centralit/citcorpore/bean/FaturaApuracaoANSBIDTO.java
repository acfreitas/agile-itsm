package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class FaturaApuracaoANSBIDTO implements IDto {
	private Integer idFaturaApuracaoANS;
	private Integer idFatura;
	private Integer idAcordoNivelServicoContrato;
	private Double valorApurado;
	private String detalhamento;
	private Double percentualGlosa;
	private Double valorGlosa;
	private java.sql.Date dataApuracao;
	private String descricao;
	private Integer idConexaoBI;

	public Integer getIdFaturaApuracaoANS() {
		return this.idFaturaApuracaoANS;
	}

	public void setIdFaturaApuracaoANS(Integer parm) {
		this.idFaturaApuracaoANS = parm;
	}

	public Integer getIdFatura() {
		return this.idFatura;
	}

	public void setIdFatura(Integer parm) {
		this.idFatura = parm;
	}

	public Integer getIdAcordoNivelServicoContrato() {
		return this.idAcordoNivelServicoContrato;
	}

	public void setIdAcordoNivelServicoContrato(Integer parm) {
		this.idAcordoNivelServicoContrato = parm;
	}

	public Double getValorApurado() {
		return this.valorApurado;
	}

	public void setValorApurado(Double parm) {
		this.valorApurado = parm;
	}

	public String getDetalhamento() {
		return this.detalhamento;
	}

	public void setDetalhamento(String parm) {
		this.detalhamento = parm;
	}

	public Double getPercentualGlosa() {
		return this.percentualGlosa;
	}

	public void setPercentualGlosa(Double parm) {
		this.percentualGlosa = parm;
	}

	public Double getValorGlosa() {
		return this.valorGlosa;
	}

	public void setValorGlosa(Double parm) {
		this.valorGlosa = parm;
	}

	public java.sql.Date getDataApuracao() {
		return this.dataApuracao;
	}

	public void setDataApuracao(java.sql.Date parm) {
		this.dataApuracao = parm;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao
	 *            the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getIdConexaoBI() {
		return idConexaoBI;
	}

	public void setIdConexaoBI(Integer idConexaoBI) {
		this.idConexaoBI = idConexaoBI;
	}
	
}
