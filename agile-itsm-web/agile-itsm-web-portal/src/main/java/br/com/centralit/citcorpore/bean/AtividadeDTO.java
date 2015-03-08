package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class AtividadeDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 644345048805944191L;
	private Integer idAtividade;
	private Integer idEtapa;
	private Integer idTipoAtividade;
	private Integer idAtividadeProxima;
	private String nomeAtividade;
	private Integer ordem;
	private String grupoExecutor;
	public String getGrupoExecutor() {
		return grupoExecutor;
	}
	public void setGrupoExecutor(String grupoExecutor) {
		this.grupoExecutor = grupoExecutor;
	}
	public Integer getIdAtividade() {
		return idAtividade;
	}
	public void setIdAtividade(Integer idAtividade) {
		this.idAtividade = idAtividade;
	}
	public Integer getIdAtividadeProxima() {
		return idAtividadeProxima;
	}
	public void setIdAtividadeProxima(Integer idAtividadeProxima) {
		this.idAtividadeProxima = idAtividadeProxima;
	}
	public Integer getIdEtapa() {
		return idEtapa;
	}
	public void setIdEtapa(Integer idEtapa) {
		this.idEtapa = idEtapa;
	}
	public Integer getIdTipoAtividade() {
		return idTipoAtividade;
	}
	public void setIdTipoAtividade(Integer idTipoAtividade) {
		this.idTipoAtividade = idTipoAtividade;
	}
	public String getNomeAtividade() {
		return nomeAtividade;
	}
	public void setNomeAtividade(String nomeAtividade) {
		this.nomeAtividade = nomeAtividade;
	}
	public Integer getOrdem() {
		return ordem;
	}
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
}
