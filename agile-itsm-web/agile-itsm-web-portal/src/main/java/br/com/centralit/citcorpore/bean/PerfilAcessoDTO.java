package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.util.Collection;

import br.com.citframework.dto.IDto;

public class PerfilAcessoDTO implements IDto {

	private static final long serialVersionUID = 329003451678003929L;

	private Integer idPerfilAcesso;

	private Date dataInicio;

	private Date dataFim;

	private String nomePerfilAcesso;

	private String aprovaBaseConhecimento;

	private String permiteLeitura;

	private String permiteLeituraGravacao;

	private Collection<PerfilAcessoMenuDTO> acessoMenus;

	private String acessoMenuSerializados;

	private Integer[] situacaoos;

	private String[] situacaoFatura;
	
	private String acessoSistemaCitsmart;

	/**
	 * @return valor do atributo idPerfilAcesso.
	 */
	public Integer getIdPerfilAcesso() {
		return idPerfilAcesso;
	}

	/**
	 * Define valor do atributo idPerfilAcesso.
	 * 
	 * @param idPerfilAcesso
	 */
	public void setIdPerfilAcesso(Integer idPerfilAcesso) {
		this.idPerfilAcesso = idPerfilAcesso;
	}

	/**
	 * @return valor do atributo dataInicio.
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * Define valor do atributo dataInicio.
	 * 
	 * @param dataInicio
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return valor do atributo dataFim.
	 */
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * Define valor do atributo dataFim.
	 * 
	 * @param dataFim
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	/**
	 * @return valor do atributo nomePerfilAcesso.
	 */
	public String getNomePerfilAcesso() {
		return nomePerfilAcesso;
	}

	/**
	 * Define valor do atributo nomePerfilAcesso.
	 * 
	 * @param nomePerfilAcesso
	 */
	public void setNomePerfilAcesso(String nomePerfilAcesso) {
		this.nomePerfilAcesso = nomePerfilAcesso;
	}

	/**
	 * @return valor do atributo aprovaBaseConhecimento.
	 */
	public String getAprovaBaseConhecimento() {
		return aprovaBaseConhecimento;
	}

	/**
	 * Define valor do atributo aprovaBaseConhecimento.
	 * 
	 * @param aprovaBaseConhecimento
	 */
	public void setAprovaBaseConhecimento(String aprovaBaseConhecimento) {
		this.aprovaBaseConhecimento = aprovaBaseConhecimento;
	}

	/**
	 * @return valor do atributo acessoMenus.
	 */
	public Collection<PerfilAcessoMenuDTO> getAcessoMenus() {
		return acessoMenus;
	}

	/**
	 * Define valor do atributo acessoMenus.
	 * 
	 * @param acessoMenus
	 */
	public void setAcessoMenus(Collection<PerfilAcessoMenuDTO> acessoMenus) {
		this.acessoMenus = acessoMenus;
	}

	/**
	 * @return valor do atributo acessoMenuSerializados.
	 */
	public String getAcessoMenuSerializados() {
		return acessoMenuSerializados;
	}

	/**
	 * Define valor do atributo acessoMenuSerializados.
	 * 
	 * @param acessoMenuSerializados
	 */
	public void setAcessoMenuSerializados(String acessoMenuSerializados) {
		this.acessoMenuSerializados = acessoMenuSerializados;
	}

	public Integer[] getSituacaoos() {
		return situacaoos;
	}

	public void setSituacaoos(Integer[] situacaoos) {
		this.situacaoos = situacaoos;
	}

	public String[] getSituacaoFatura() {
		return situacaoFatura;
	}

	public void setSituacaoFatura(String[] situacaoFatura) {
		this.situacaoFatura = situacaoFatura;
	}

	/**
	 * @return the permiteLeitura
	 */
	public String getPermiteLeitura() {
		return permiteLeitura;
	}

	/**
	 * @param permiteLeitura
	 *            the permiteLeitura to set
	 */
	public void setPermiteLeitura(String permiteLeitura) {
		this.permiteLeitura = permiteLeitura;
	}

	/**
	 * @return the permiteLeituraGravacao
	 */
	public String getPermiteLeituraGravacao() {
		return permiteLeituraGravacao;
	}

	/**
	 * @param permiteLeituraGravacao
	 *            the permiteLeituraGravacao to set
	 */
	public void setPermiteLeituraGravacao(String permiteLeituraGravacao) {
		this.permiteLeituraGravacao = permiteLeituraGravacao;
	}

	/**
	 * @return the acessoSistemaCitsmart
	 */
	public String getAcessoSistemaCitsmart() {
		return acessoSistemaCitsmart;
	}

	/**
	 * @param acessoSistemaCitsmart the acessoSistemaCitsmart to set
	 */
	public void setAcessoSistemaCitsmart(String acessoSistemaCitsmart) {
		this.acessoSistemaCitsmart = acessoSistemaCitsmart;
	}

}
