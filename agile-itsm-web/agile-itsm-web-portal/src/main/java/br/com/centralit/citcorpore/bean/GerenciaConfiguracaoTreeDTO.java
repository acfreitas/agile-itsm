package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class GerenciaConfiguracaoTreeDTO implements IDto {

	private static final long serialVersionUID = 1L;
	
	/* Item de Configuracao */
	private Integer idItemConfiguracao;
	private Integer idItemConfiguracaoExport;
	private Integer idItemConfiguracaoPai;
	private String identificacao;
	private String localidadeItemConfiguracao;
	private String criticidadeItemConfiguracao;
	private Integer idBaseConhecimento;

	/* Grupo Item de Configuracao */
	private Integer idGrupoItemConfiguracao;
	private String nomeGrupoItemConfiguracao;
	private Integer idGrupoItemConfiguracaoPai;
	
	private String criticidade;
	private String status;
	
	private String comboStatus;
	private String cboCriticidade;
	private String iframe;

	//atributo auxiliar para capturar o navegador usado
	private String idBrowserName;
	
	public String getIdBrowserName() {
		return idBrowserName;
	}


	public void setIdBrowserName(String idBrowserName) {
		this.idBrowserName = idBrowserName;
	}


	public String getComboStatus() {
		return comboStatus;
	}


	public void setComboStatus(String comboStatus) {
		this.comboStatus = comboStatus;
	}


	public String getCboCriticidade() {
		return cboCriticidade;
	}


	public void setCboCriticidade(String cboCriticidade) {
		this.cboCriticidade = cboCriticidade;
	}


	public String getCriticidade() {
		return criticidade;
	}


	public void setCriticidade(String criticidade) {
		this.criticidade = criticidade;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	


	public Integer getIdItemConfiguracao() {
		return idItemConfiguracao;
	}


	public void setIdItemConfiguracao(Integer pIdItemConfiguracao) {
		idItemConfiguracao = pIdItemConfiguracao;
	}


	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String pIdentificacao) {
		identificacao = pIdentificacao;
	}


	public String getLocalidadeItemConfiguracao() {
		return localidadeItemConfiguracao;
	}

	public void setLocalidadeItemConfiguracao(String pLocalidadeItemConfiguracao) {
		localidadeItemConfiguracao = pLocalidadeItemConfiguracao;
	}


	public String getCriticidadeItemConfiguracao() {
		return criticidadeItemConfiguracao;
	}

	public void setCriticidadeItemConfiguracao(String pCriticidadeItemConfiguracao) {
		criticidadeItemConfiguracao = pCriticidadeItemConfiguracao;
	}


	public Integer getIdGrupoItemConfiguracao() {
		return idGrupoItemConfiguracao;
	}

	public void setIdGrupoItemConfiguracao(Integer pIdGrupoItemConfiguracao) {
		idGrupoItemConfiguracao = pIdGrupoItemConfiguracao;
	}


	public String getNomeGrupoItemConfiguracao() {
		return nomeGrupoItemConfiguracao;
	}

	public void setNomeGrupoItemConfiguracao(String pNomeGrupoItemConfiguracao) {
		nomeGrupoItemConfiguracao = pNomeGrupoItemConfiguracao;
	}

	public Integer getIdItemConfiguracaoPai() {
		return idItemConfiguracaoPai;
	}

	public void setIdItemConfiguracaoPai(Integer idItemConfiguracaoPai) {
		this.idItemConfiguracaoPai = idItemConfiguracaoPai;
	}

	public Integer getIdBaseConhecimento() {
		return idBaseConhecimento;
	}

	public void setIdBaseConhecimento(Integer idBaseConhecimento) {
		this.idBaseConhecimento = idBaseConhecimento;
	}

	public Integer getIdGrupoItemConfiguracaoPai() {
		return idGrupoItemConfiguracaoPai;
	}

	public void setIdGrupoItemConfiguracaoPai(Integer idGrupoItemConfiguracaoPai) {
		this.idGrupoItemConfiguracaoPai = idGrupoItemConfiguracaoPai;
	}

	public Integer getIdItemConfiguracaoExport() {
		return idItemConfiguracaoExport;
	}

	public void setIdItemConfiguracaoExport(Integer idItemConfiguracaoExport) {
		this.idItemConfiguracaoExport = idItemConfiguracaoExport;
	}


	public String getIframe() {
		return iframe;
	}


	public void setIframe(String iframe) {
		this.iframe = iframe;
	}
	
}