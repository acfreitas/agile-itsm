package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ValorAjusteGlosaDTO implements IDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -645671303268213779L;
	
	private Integer idServicoContrato;
	private Integer idAcordoNivelServico;
	private Integer quantidadeFalhas;
	private Double valorAjuste;
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
	public Integer getQuantidadeFalhas() {
		return quantidadeFalhas;
	}
	public void setQuantidadeFalhas(Integer quantidadeFalhas) {
		this.quantidadeFalhas = quantidadeFalhas;
	}
	public Double getValorAjuste() {
		return valorAjuste;
	}
	public void setValorAjuste(Double valorAjuste) {
		this.valorAjuste = valorAjuste;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

}
