package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.centralit.citcorpore.bpm.negocio.ExecucaoSolicitacao;
import br.com.citframework.dto.IDto;

public class ProcessoNegocioDTO implements IDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1540754378060788134L;
	private Integer idProcessoNegocio;
	private Integer idGrupoExecutor;
	private Integer idGrupoAdministrador;
	private String nomeProcessoNegocio;
	private String permissaoSolicitacao;
	private Double percDispensaNovaAprovacao;
	private String permiteAprovacaoNivelInferior;
	private String alcadaPrimeiroNivel;
	
	private Integer[] idTipoFluxo;
	
	private Collection<ProcessoNivelAutoridadeDTO> colAutoridades;
	private Collection<ExecucaoSolicitacao> colExecucaoSolicitacao;
	private Collection<LimiteAprovacaoDTO> colLimitesAprovacao;
	
	private NivelAutoridadeDTO nivelAutoridadeDto;

	public Integer getIdProcessoNegocio(){
		return this.idProcessoNegocio;
	}
	public void setIdProcessoNegocio(Integer parm){
		this.idProcessoNegocio = parm;
	}

	public Integer getIdGrupoExecutor(){
		return this.idGrupoExecutor;
	}
	public void setIdGrupoExecutor(Integer parm){
		this.idGrupoExecutor = parm;
	}

	public Integer getIdGrupoAdministrador(){
		return this.idGrupoAdministrador;
	}
	public void setIdGrupoAdministrador(Integer parm){
		this.idGrupoAdministrador = parm;
	}

	public String getNomeProcessoNegocio(){
		return this.nomeProcessoNegocio;
	}
	public void setNomeProcessoNegocio(String parm){
		this.nomeProcessoNegocio = parm;
	}

	public String getPermissaoSolicitacao(){
		return this.permissaoSolicitacao;
	}
	public void setPermissaoSolicitacao(String parm){
		this.permissaoSolicitacao = parm;
	}

	public Double getPercDispensaNovaAprovacao(){
		return this.percDispensaNovaAprovacao;
	}
	public void setPercDispensaNovaAprovacao(Double parm){
		this.percDispensaNovaAprovacao = parm;
	}

	public String getPermiteAprovacaoNivelInferior(){
		return this.permiteAprovacaoNivelInferior;
	}
	public void setPermiteAprovacaoNivelInferior(String parm){
		this.permiteAprovacaoNivelInferior = parm;
	}
	public Collection<ProcessoNivelAutoridadeDTO> getColAutoridades() {
		return colAutoridades;
	}
	public void setColAutoridades(
			Collection<ProcessoNivelAutoridadeDTO> colAutoridades) {
		this.colAutoridades = colAutoridades;
	}
	public Integer[] getIdTipoFluxo() {
		return idTipoFluxo;
	}
	public void setIdTipoFluxo(Integer[] idTipoFluxo) {
		this.idTipoFluxo = idTipoFluxo;
	}
	public NivelAutoridadeDTO getNivelAutoridadeDto() {
		return nivelAutoridadeDto;
	}
	public void setNivelAutoridadeDto(NivelAutoridadeDTO nivelAutoridadeDto) {
		this.nivelAutoridadeDto = nivelAutoridadeDto;
	}
	public Collection<ExecucaoSolicitacao> getColExecucaoSolicitacao() {
		return colExecucaoSolicitacao;
	}
	public void setColExecucaoSolicitacao(
			Collection<ExecucaoSolicitacao> colExecucaoSolicitacao) {
		this.colExecucaoSolicitacao = colExecucaoSolicitacao;
	}
	public Collection<LimiteAprovacaoDTO> getColLimitesAprovacao() {
		return colLimitesAprovacao;
	}
	public void setColLimitesAprovacao(
			Collection<LimiteAprovacaoDTO> colLimitesAprovacao) {
		this.colLimitesAprovacao = colLimitesAprovacao;
	}
	public String getAlcadaPrimeiroNivel() {
		return alcadaPrimeiroNivel;
	}
	public void setAlcadaPrimeiroNivel(String alcadaPrimeiroNivel) {
		this.alcadaPrimeiroNivel = alcadaPrimeiroNivel;
	}

}
