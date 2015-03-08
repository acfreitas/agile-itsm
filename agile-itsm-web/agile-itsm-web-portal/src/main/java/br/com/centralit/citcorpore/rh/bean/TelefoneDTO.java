package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class TelefoneDTO implements IDto {
	private Integer idTelefone;
	private Integer idTipoTelefone;
	private Integer ddd;
	private String numeroTelefone;
	
	private String descricaoTipoTelefone;
	public Integer getDdd() {
		return ddd;
	}
	public void setDdd(Integer ddd) {
		this.ddd = ddd;
	}
	public Integer getIdTelefone() {
		return idTelefone;
	}
	public void setIdTelefone(Integer idTelefone) {
		this.idTelefone = idTelefone;
	}
	public Integer getIdTipoTelefone() {
		return idTipoTelefone;
	}
	public void setIdTipoTelefone(Integer idTipoTelefone) {
		this.idTipoTelefone = idTipoTelefone;
	}
	public String getNumeroTelefone() {
		if (numeroTelefone == null){
			return null;
		}
		if (numeroTelefone.trim().equalsIgnoreCase("null")){
			return "";
		}
		return numeroTelefone;
	}
	public void setNumeroTelefone(String numeroTelefone) {
		this.numeroTelefone = numeroTelefone;
	}
	public String getDescricaoTipoTelefone() {
		return descricaoTipoTelefone;
	}
	public void setDescricaoTipoTelefone(String descricaoTipoTelefone) {
		this.descricaoTipoTelefone = descricaoTipoTelefone;
	}
}
