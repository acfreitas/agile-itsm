package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class FluxoServicoDTO implements IDto {
	
	private Integer idFluxoServico;
	private Integer idServicoContrato;
	private Integer idTipoFluxo;
	private Integer idFase;
	private String principal;
	private String deleted;

	public Integer getIdFluxoServico() {
		return idFluxoServico;
	}
	public void setIdFluxoServico(Integer idFluxoServico) {
		this.idFluxoServico = idFluxoServico;
	}
	public Integer getIdServicoContrato(){
		return this.idServicoContrato;
	}
	public void setIdServicoContrato(Integer parm){
		this.idServicoContrato = parm;
	}

	public Integer getIdTipoFluxo(){
		return this.idTipoFluxo;
	}
	public void setIdTipoFluxo(Integer parm){
		this.idTipoFluxo = parm;
	}

	public Integer getIdFase(){
		return this.idFase;
	}
	public void setIdFase(Integer parm){
		this.idFase = parm;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
}
