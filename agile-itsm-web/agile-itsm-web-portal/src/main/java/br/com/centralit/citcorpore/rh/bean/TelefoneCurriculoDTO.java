package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class TelefoneCurriculoDTO implements IDto {
	private Integer idTelefone;
	private Integer idCurriculo;
	private Integer idTipoTelefone;
	private Integer ddd;
	private String numeroTelefone;
	private String descricaoTipoTelefone;
	
	public Integer getIdCurriculo() {
		return idCurriculo;
	}
	public void setIdCurriculo(Integer idCurriculo) {
		this.idCurriculo = idCurriculo;
	}
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

		if (idTipoTelefone != null){
			if (idTipoTelefone.intValue() == 1){
				this.setDescricaoTipoTelefone("Residencial");
			}else{
				this.setDescricaoTipoTelefone("Celular");
			}
		}else{
			this.setDescricaoTipoTelefone("");
		}
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
		switch (this.idTipoTelefone.intValue()) {
			case 1: this.descricaoTipoTelefone = "Residencial"; break;
			case 2: this.descricaoTipoTelefone = "Comercial";break;
			case 3: this.descricaoTipoTelefone = "Celular";break;
			case 4: this.descricaoTipoTelefone = "Fax";break;
			case 5: this.descricaoTipoTelefone = "Ramal";break;
			case 6: this.descricaoTipoTelefone = "Recado";break;
		}
		return descricaoTipoTelefone;
	}
	public void setDescricaoTipoTelefone(String descricaoTipoTelefone) {
		this.descricaoTipoTelefone = descricaoTipoTelefone;
	}
}
