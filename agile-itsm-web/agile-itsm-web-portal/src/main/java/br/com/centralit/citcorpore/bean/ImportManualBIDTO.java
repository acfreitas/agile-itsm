package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ImportManualBIDTO implements IDto {

	private static final long serialVersionUID = 2458103872245462549L;

	private Integer idConexaoBI;

	public Integer getIdConexaoBI() {
		return idConexaoBI;
	}

	public void setIdConexaoBI(Integer idConexaoBI) {
		this.idConexaoBI = idConexaoBI;
	}

}
