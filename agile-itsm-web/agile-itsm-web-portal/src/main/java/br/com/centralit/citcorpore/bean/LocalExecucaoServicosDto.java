package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class LocalExecucaoServicosDto implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idLocalExecucaoServico;
	private String nomeLocalExecucaoServico;
	private String deleted;
	
	public Integer getIdLocalExecucaoServico() {
		return idLocalExecucaoServico;
	}
	public void setIdLocalExecucaoServico(Integer idLocalExecucaoServico) {
		this.idLocalExecucaoServico = idLocalExecucaoServico;
	}
	public String getNomeLocalExecucaoServico() {
		return nomeLocalExecucaoServico;
	}
	public void setNomeLocalExecucaoServico(String nomeLocalExecucaoServico) {
		this.nomeLocalExecucaoServico = nomeLocalExecucaoServico;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

}
