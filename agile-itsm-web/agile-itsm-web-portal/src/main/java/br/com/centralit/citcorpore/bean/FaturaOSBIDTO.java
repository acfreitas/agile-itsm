package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class FaturaOSBIDTO implements IDto {
	private Integer idFatura;
	private Integer idOs;
	private Integer idConexaoBI;

	public Integer getIdFatura(){
		return this.idFatura;
	}
	public void setIdFatura(Integer parm){
		this.idFatura = parm;
	}

	public Integer getIdOs(){
		return this.idOs;
	}
	public void setIdOs(Integer parm){
		this.idOs = parm;
	}
	public Integer getIdConexaoBI() {
		return idConexaoBI;
	}
	public void setIdConexaoBI(Integer idConexaoBI) {
		this.idConexaoBI = idConexaoBI;
	}
	
}
