package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class ControleImportarDadosDTO implements IDto {
	
	private static final long serialVersionUID = 4942283426115769824L;
	
	private Integer idControleImportarDados;
	private Integer idImportarDados;
	private Timestamp dataExecucao;

	public Integer getIdControleImportarDados() {
		return idControleImportarDados;
	}
	public void setIdControleImportarDados(Integer idControleImportarDados) {
		this.idControleImportarDados = idControleImportarDados;
	}
	public Integer getIdImportarDados() {
		return idImportarDados;
	}
	public void setIdImportarDados(Integer idImportarDados) {
		this.idImportarDados = idImportarDados;
	}
	public Timestamp getDataExecucao() {
		return dataExecucao;
	}
	public void setDataExecucao(Timestamp dataExecucao) {
		this.dataExecucao = dataExecucao;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}