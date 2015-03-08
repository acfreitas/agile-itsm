package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class GrupoItemConfiguracaoDTO implements IDto {

	private static final long serialVersionUID = -2207719425970001340L;

	public static final String GRUPO_PADRAO_DESENVOLVIMENTO = "Desenvolvimento - Padrão";
	public static final String GRUPO_PADRAO_HOMOLOGACAO = "Homologação - Padrão";
	public static final String GRUPO_PADRAO_PRODUCAO = "Produção - Padrão";
	
	private Integer idGrupoItemConfiguracao;
	private String nomeGrupoItemConfiguracao;
	private String emailGrupoItemConfiguracao;
	private Date dataInicio;
	private Date dataFim;
	private Integer idGrupoItemConfiguracaoPai;

	/**
	 * @return Retorna o id do grupo de item de configuracao.
	 */
	public Integer getIdGrupoItemConfiguracao() {
		return idGrupoItemConfiguracao;
	}

	/**
	 * @param pIdGrupoItemConfiguracao modifica o atributo idGrupoItemConfiguracao.
	 */
	public void setIdGrupoItemConfiguracao(Integer pIdGrupoItemConfiguracao) {
		idGrupoItemConfiguracao = pIdGrupoItemConfiguracao;
	}


	/**
	 * @return Retorna o nome do grupo de item de configuracao.
	 */
	public String getNomeGrupoItemConfiguracao() {
		return nomeGrupoItemConfiguracao;
	}

	/**
	 * @param pNomeGrupoItemConfiguracao modifica o atributo nomeGrupoItemConfiguracao.
	 */
	public void setNomeGrupoItemConfiguracao(String pNomeGrupoItemConfiguracao) {
		nomeGrupoItemConfiguracao = pNomeGrupoItemConfiguracao;
	}


	/**
	 * @return Retorna a data inicial do registro.
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param pDataInicio modifica o atributo dataInicio.
	 */
	public void setDataInicio(Date pDataInicio) {
		dataInicio = pDataInicio;
	}


	/**
	 * @return Retorna a data final do registro.
	 */
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * @param pDataFim modifica o atributo dataFim.
	 */
	public void setDataFim(Date pDataFim) {
		dataFim = pDataFim;
	}

	public String getEmailGrupoItemConfiguracao() {
		return emailGrupoItemConfiguracao;
	}

	public void setEmailGrupoItemConfiguracao(String emailGrupoItemConfiguracao) {
		this.emailGrupoItemConfiguracao = emailGrupoItemConfiguracao;
	}

	public Integer getIdGrupoItemConfiguracaoPai() {
		return idGrupoItemConfiguracaoPai;
	}

	public void setIdGrupoItemConfiguracaoPai(Integer idGrupoItemConfiguracaoPai) {
		this.idGrupoItemConfiguracaoPai = idGrupoItemConfiguracaoPai;
	}
}