package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.citframework.dto.IDto;

public class ValorRecuperadoMatrizDTO implements IDto {
	private ObjetoNegocioDTO objetoNegocioDTO;
	private CamposObjetoNegocioDTO camposObjetoNegocioChaveDTO; 
	private CamposObjetoNegocioDTO camposObjetoNegocioApres1DTO;
	private CamposObjetoNegocioDTO camposObjetoNegocioApres2DTO;
	private Collection colDados;
	public CamposObjetoNegocioDTO getCamposObjetoNegocioChaveDTO() {
		return camposObjetoNegocioChaveDTO;
	}
	public void setCamposObjetoNegocioChaveDTO(
			CamposObjetoNegocioDTO camposObjetoNegocioChaveDTO) {
		this.camposObjetoNegocioChaveDTO = camposObjetoNegocioChaveDTO;
	}
	public CamposObjetoNegocioDTO getCamposObjetoNegocioApres1DTO() {
		return camposObjetoNegocioApres1DTO;
	}
	public void setCamposObjetoNegocioApres1DTO(
			CamposObjetoNegocioDTO camposObjetoNegocioApres1DTO) {
		this.camposObjetoNegocioApres1DTO = camposObjetoNegocioApres1DTO;
	}
	public CamposObjetoNegocioDTO getCamposObjetoNegocioApres2DTO() {
		return camposObjetoNegocioApres2DTO;
	}
	public void setCamposObjetoNegocioApres2DTO(
			CamposObjetoNegocioDTO camposObjetoNegocioApres2DTO) {
		this.camposObjetoNegocioApres2DTO = camposObjetoNegocioApres2DTO;
	}
	public Collection getColDados() {
		return colDados;
	}
	public void setColDados(Collection colDados) {
		this.colDados = colDados;
	}
	public ObjetoNegocioDTO getObjetoNegocioDTO() {
		return objetoNegocioDTO;
	}
	public void setObjetoNegocioDTO(ObjetoNegocioDTO objetoNegocioDTO) {
		this.objetoNegocioDTO = objetoNegocioDTO;
	}
}
