package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ProcessoNivelAutoridadeDTO implements IDto {
	private Integer idProcessoNegocio;
	private Integer idNivelAutoridade;
	private String permiteAprovacaoPropria;
	private String permiteSolicitacao;
	private Integer antecedenciaMinimaAprovacao;
	
	private Integer sequencia;
	private Integer hierarquia;
	private NivelAutoridadeDTO nivelAutoridadeDto;
	private LimiteAprovacaoDTO limiteAprovacaoDto;

	public Integer getIdProcessoNegocio(){
		return this.idProcessoNegocio;
	}
	public void setIdProcessoNegocio(Integer parm){
		this.idProcessoNegocio = parm;
	}

	public Integer getIdNivelAutoridade(){
		return this.idNivelAutoridade;
	}
	public void setIdNivelAutoridade(Integer parm){
		this.idNivelAutoridade = parm;
	}

	public String getPermiteAprovacaoPropria(){
		return this.permiteAprovacaoPropria;
	}
	public void setPermiteAprovacaoPropria(String parm){
		this.permiteAprovacaoPropria = parm;
	}

	public String getPermiteSolicitacao(){
		return this.permiteSolicitacao;
	}
	public void setPermiteSolicitacao(String parm){
		this.permiteSolicitacao = parm;
	}

	public Integer getAntecedenciaMinimaAprovacao(){
		return this.antecedenciaMinimaAprovacao;
	}
	public void setAntecedenciaMinimaAprovacao(Integer parm){
		this.antecedenciaMinimaAprovacao = parm;
	}
	public Integer getSequencia() {
		return sequencia;
	}
	public void setSequencia(Integer sequencia) {
		this.sequencia = sequencia;
	}
	public Integer getHierarquia() {
		return hierarquia;
	}
	public void setHierarquia(Integer hierarquia) {
		this.hierarquia = hierarquia;
	}
	public NivelAutoridadeDTO getNivelAutoridadeDto() {
		return nivelAutoridadeDto;
	}
	public void setNivelAutoridadeDto(NivelAutoridadeDTO nivelAutoridadeDto) {
		this.nivelAutoridadeDto = nivelAutoridadeDto;
	}
	public LimiteAprovacaoDTO getLimiteAprovacaoDto() {
		return limiteAprovacaoDto;
	}
	public void setLimiteAprovacaoDto(LimiteAprovacaoDTO limiteAprovacaoDto) {
		this.limiteAprovacaoDto = limiteAprovacaoDto;
	}

}
