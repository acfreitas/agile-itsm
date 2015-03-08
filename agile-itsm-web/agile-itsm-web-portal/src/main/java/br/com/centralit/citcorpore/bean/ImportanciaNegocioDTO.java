package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author thays.araujo
 * 
 */
public class ImportanciaNegocioDTO implements IDto {

	private static final long serialVersionUID = -7848776827100833523L;
	private Integer idImportanciaNegocio;
	private Integer idEmpresa;
	private String nomeImportanciaNegocio;
	private String situacao;

	public Integer getIdImportanciaNegocio() {
		return idImportanciaNegocio;
	}

	public void setIdImportanciaNegocio(Integer idImportanciaNegocio) {
		this.idImportanciaNegocio = idImportanciaNegocio;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getNomeImportanciaNegocio() {
		return nomeImportanciaNegocio;
	}

	public void setNomeImportanciaNegocio(String nomeImportanciaNegocio) {
		this.nomeImportanciaNegocio = nomeImportanciaNegocio;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
