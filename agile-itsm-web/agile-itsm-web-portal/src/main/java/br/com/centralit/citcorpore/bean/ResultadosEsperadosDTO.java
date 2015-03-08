package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author rodrigo.oliveira
 *
 */
public class ResultadosEsperadosDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7484677113933240224L;
	
	private Integer idServicoContrato;
	private Integer idAcordoNivelServico;
	private String descricaoResultados;
	private String limites;
	private String glosa;
	private String limiteGlosa;
	private String deleted;
	
	public Integer getIdServicoContrato() {
		return idServicoContrato;
	}
	public void setIdServicoContrato(Integer idServicoContrato) {
		this.idServicoContrato = idServicoContrato;
	}
	public Integer getIdAcordoNivelServico() {
		return idAcordoNivelServico;
	}
	public void setIdAcordoNivelServico(Integer idAcordoNivelServico) {
		this.idAcordoNivelServico = idAcordoNivelServico;
	}
	public String getLimites() {
		return limites;
	}
	public void setLimites(String limites) {
		this.limites = limites;
	}
	public String getLimiteGlosa() {
		return limiteGlosa;
	}
	public void setLimiteGlosa(String limiteGlosa) {
		this.limiteGlosa = limiteGlosa;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getGlosa() {
		return glosa;
	}
	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}
	public String getDescricaoResultados() {
		return descricaoResultados;
	}
	public void setDescricaoResultados(String descricaoResultados) {
		this.descricaoResultados = descricaoResultados;
	}
	
}
