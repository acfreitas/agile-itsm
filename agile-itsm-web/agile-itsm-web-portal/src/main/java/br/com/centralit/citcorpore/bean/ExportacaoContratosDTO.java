package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ExportacaoContratosDTO implements IDto {
	private static final long serialVersionUID = -7252057961936714136L;
	private Integer idContrato;
	private String export;
	private Integer[] idGrupos;
	private String exportarUnidades;
	private String exportarAcordoNivelServico;
	private String exportarCatalogoServico;
	
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
	public String getExport() {
		return export;
	}
	public void setExport(String export) {
		this.export = export;
	}
	public Integer[] getIdGrupos() {
		return idGrupos;
	}
	public void setIdGrupos(Integer[] idGrupos) {
		this.idGrupos = idGrupos;
	}
	public String getExportarUnidades() {
		return exportarUnidades;
	}
	public void setExportarUnidades(String exportarUnidades) {
		this.exportarUnidades = exportarUnidades;
	}
	public String getExportarAcordoNivelServico() {
		return exportarAcordoNivelServico;
	}
	public void setExportarAcordoNivelServico(String exportarAcordoNivelServico) {
		this.exportarAcordoNivelServico = exportarAcordoNivelServico;
	}
	public String getExportarCatalogoServico() {
		return exportarCatalogoServico;
	}
	public void setExportarCatalogoServico(String exportarCatalogoServico) {
		this.exportarCatalogoServico = exportarCatalogoServico;
	}

}
